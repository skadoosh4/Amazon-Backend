package com.example.demo.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProductPair {
    private String id;
    private ProductRequest request;
}
