package com.example.demo.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private String manufacturer;
    private Double price;
    private String category;
    private String region;
}
