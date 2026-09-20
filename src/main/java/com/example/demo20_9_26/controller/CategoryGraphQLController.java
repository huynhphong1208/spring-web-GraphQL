package com.example.demo20_9_26.controller;

import com.example.demo20_9_26.dto.CategoryInput;
import com.example.demo20_9_26.dto.CategoryPage;
import com.example.demo20_9_26.model.Category;
import com.example.demo20_9_26.repository.CategoryRepository;
import com.example.demo20_9_26.service.IStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class CategoryGraphQLController {

    private final CategoryRepository categoryRepository;
    private final IStorageService storageService;

    public CategoryGraphQLController(CategoryRepository categoryRepository, IStorageService storageService) {
        this.categoryRepository = categoryRepository;
        this.storageService = storageService;
    }

    @QueryMapping
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    @QueryMapping
    public CategoryPage searchCategories(@Argument String name, @Argument Integer page, @Argument Integer size) {
        int pageNo = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Category> categoryPage;
        if (name != null && !name.trim().isEmpty()) {
            categoryPage = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            categoryPage = categoryRepository.findAll(pageable);
        }

        return CategoryPage.builder()
                .content(categoryPage.getContent())
                .totalPages(categoryPage.getTotalPages())
                .totalElements(categoryPage.getTotalElements())
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .build();
    }

    @MutationMapping
    public Category createCategory(@Argument CategoryInput category, @Argument MultipartFile file) {
        String iconFileName = category.getIcon();
        if (file != null && !file.isEmpty()) {
            iconFileName = storageService.store(file);
        }

        Category newCategory = Category.builder()
                .name(category.getName())
                .icon(iconFileName)
                .build();
        return categoryRepository.save(newCategory);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryInput category, @Argument MultipartFile file) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        String iconFileName = existingCategory.getIcon();
        if (file != null && !file.isEmpty()) {
            iconFileName = storageService.store(file);
        } else if (category.getIcon() != null && !category.getIcon().trim().isEmpty()) {
            iconFileName = category.getIcon();
        }

        existingCategory.setName(category.getName());
        existingCategory.setIcon(iconFileName);
        return categoryRepository.save(existingCategory);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
