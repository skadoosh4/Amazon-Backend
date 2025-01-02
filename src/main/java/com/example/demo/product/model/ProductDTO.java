package com.example.demo.product.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode // Overrides the equals method
// & compares the contents of objects instead of memory address
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private Double price;
    private String category;
    private String manufacturer;

    public ProductDTO(Product product){
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory().getValue();
        this.manufacturer = product.getManufacturer();
    }
}
