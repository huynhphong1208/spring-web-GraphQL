package com.example.demo20_9_26;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Demo20926Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Demo20926Application.class)
                .headless(false)
                .run(args);
    }

}

