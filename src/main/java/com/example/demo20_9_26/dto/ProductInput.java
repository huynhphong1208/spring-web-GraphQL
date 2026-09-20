package com.example.demo20_9_26.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInput {
    private String name;
    private Double price;
    private Integer quantity;
    private String description;
    private String images;
    private Long categoryId;
}
