package com.example.demo.product.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductSortBy {
    name("name"),
    price("price");

    private final String value;

    public static ProductSortBy fromValue(String value){
        for(ProductSortBy sortBy : ProductSortBy.values()){
            if(sortBy.value.equalsIgnoreCase(value)){
                return sortBy;
            }
        }
        return null;
    }

    public String getValue(){
        return this.value;
    }
}
