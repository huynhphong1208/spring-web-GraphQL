package com.example.demo20_9_26.controller;

import com.example.demo20_9_26.dto.CategoryInput;
import com.example.demo20_9_26.dto.CategoryPage;
import com.example.demo20_9_26.model.Category;
import com.example.demo20_9_26.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CategoryGraphQLController {

    private final CategoryRepository categoryRepository;

    public CategoryGraphQLController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
    public Category createCategory(@Argument CategoryInput category) {
        Category newCategory = Category.builder()
                .name(category.getName())
                .icon(category.getIcon())
                .build();
        return categoryRepository.save(newCategory);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryInput category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existingCategory.setName(category.getName());
        existingCategory.setIcon(category.getIcon());
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
