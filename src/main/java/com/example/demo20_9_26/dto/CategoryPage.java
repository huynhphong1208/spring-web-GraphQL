package com.example.demo20_9_26.dto;

import com.example.demo20_9_26.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryPage {
    private List<Category> content;
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;
}
