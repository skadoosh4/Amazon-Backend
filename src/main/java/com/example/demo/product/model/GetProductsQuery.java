package com.example.demo.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetProductsQuery {
    private Region region;
    private String category;
    private String nameOrDescription;
    private ProductSortBy productSortBy;
}
