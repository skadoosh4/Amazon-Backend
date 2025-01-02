package com.example.demo.category.controller;

import com.example.demo.category.service.GetCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final GetCategory getCategory;

    @Autowired
    public CategoryController(GetCategory getCategory){
        this.getCategory = getCategory;
    }

    @GetMapping
    public ResponseEntity<Iterable<String>> getCategories(){
        return getCategory.execute(null);
    }

}
