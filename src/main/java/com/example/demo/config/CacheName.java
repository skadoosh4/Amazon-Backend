package com.example.demo.config;

public enum CacheName {

    PRODUCTS("products");
    private final String value;

    CacheName(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
