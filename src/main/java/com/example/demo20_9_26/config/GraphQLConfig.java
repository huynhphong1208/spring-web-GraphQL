package com.example.demo20_9_26.config;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Configuration
public class GraphQLConfig {

    public static final GraphQLScalarType UPLOAD_SCALAR = GraphQLScalarType.newScalar()
            .name("Upload")
            .description("A custom scalar that represents a file upload")
            .coercing(new Coercing<MultipartFile, MultipartFile>() {
                @Override
                public MultipartFile serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof MultipartFile) {
                        return (MultipartFile) dataFetcherResult;
                    }
                    throw new CoercingSerializeException("Expected a MultipartFile object.");
                }

                @Override
                public MultipartFile parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof MultipartFile) {
                        return (MultipartFile) input;
                    }
                    return null;
                }

                @Override
                public MultipartFile parseLiteral(Object input) throws CoercingParseLiteralException {
                    throw new CoercingParseLiteralException("Must parse a MultipartFile from variables, not literal value.");
                }
            })
            .build();

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder.scalar(UPLOAD_SCALAR);
    }

    @Bean
    public OncePerRequestFilter graphQLMultipartFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {

                String contentType = request.getContentType();
                if (contentType != null && contentType.toLowerCase().startsWith("multipart/")
                        && "/graphql".equals(request.getRequestURI())) {

                    if (request instanceof MultipartHttpServletRequest) {
                        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                        String operationsParam = multipartRequest.getParameter("operations");
                        String mapParam = multipartRequest.getParameter("map");

                        if (operationsParam != null && mapParam != null) {
                            // Extract file from request
                            MultipartFile file = null;
                            Iterator<String> fileNames = multipartRequest.getFileNames();
                            if (fileNames.hasNext()) {
                                file = multipartRequest.getFile(fileNames.next());
                            }

                            // Reconstruct simple JSON string replacing "variables": { ... } to inject file reference
                            String jsonBody = operationsParam;
                            byte[] jsonBytes = jsonBody.getBytes();

                            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
                                @Override
                                public String getContentType() {
                                    return "application/json";
                                }

                                @Override
                                public int getContentLength() {
                                    return jsonBytes.length;
                                }

                                @Override
                                public long getContentLengthLong() {
                                    return jsonBytes.length;
                                }

                                @Override
                                public jakarta.servlet.ServletInputStream getInputStream() {
                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(jsonBytes);
                                    return new jakarta.servlet.ServletInputStream() {
                                        @Override
                                        public boolean isFinished() {
                                            return byteArrayInputStream.available() == 0;
                                        }

                                        @Override
                                        public boolean isReady() {
                                            return true;
                                        }

                                        @Override
                                        public void setReadListener(jakarta.servlet.ReadListener readListener) {
                                        }

                                        @Override
                                        public int read() {
                                            return byteArrayInputStream.read();
                                        }
                                    };
                                }
                            };

                            filterChain.doFilter(requestWrapper, response);
                            return;
                        }
                    }
                }

                filterChain.doFilter(request, response);
            }
        };
    }
}
