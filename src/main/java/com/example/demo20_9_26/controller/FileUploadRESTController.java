package com.example.demo20_9_26.controller;

import com.example.demo20_9_26.dto.CategoryInput;
import com.example.demo20_9_26.dto.ProductInput;
import com.example.demo20_9_26.model.Category;
import com.example.demo20_9_26.model.Product;
import com.example.demo20_9_26.service.IStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class FileUploadRESTController {

    private final ProductGraphQLController productGraphQLController;
    private final CategoryGraphQLController categoryGraphQLController;
    private final IStorageService storageService;

    public FileUploadRESTController(ProductGraphQLController productGraphQLController,
                                    CategoryGraphQLController categoryGraphQLController,
                                    IStorageService storageService) {
        this.productGraphQLController = productGraphQLController;
        this.categoryGraphQLController = categoryGraphQLController;
        this.storageService = storageService;
    }

    @PostMapping("/product/create")
    public ResponseEntity<Product> createProductWithFile(
            @RequestPart("product") ProductInput productInput,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        Product created = productGraphQLController.createProduct(productInput, file);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/product/update/{id}")
    public ResponseEntity<Product> updateProductWithFile(
            @PathVariable("id") Long id,
            @RequestPart("product") ProductInput productInput,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        Product updated = productGraphQLController.updateProduct(id, productInput, file);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategoryWithFile(
            @RequestPart("category") CategoryInput categoryInput,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        Category created = categoryGraphQLController.createCategory(categoryInput, file);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/category/update/{id}")
    public ResponseEntity<Category> updateCategoryWithFile(
            @PathVariable("id") Long id,
            @RequestPart("category") CategoryInput categoryInput,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        Category updated = categoryGraphQLController.updateCategory(id, categoryInput, file);
        return ResponseEntity.ok(updated);
    }
}
