package com.example.demo.category.service;

import com.example.demo.Query;
import com.example.demo.category.model.Category;
import com.example.demo.category.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetCategory implements Query<Void , Iterable<String>>{

    private final CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(GetCategory.class);

    @Autowired
    public GetCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<Iterable<String>> execute(Void input) {
        logger.info("Executing {} GetCategory" , getClass().getSimpleName());
        return ResponseEntity.ok(categoryRepository
                .findAll()
                .stream()
                .map(Category::getValue)
                .toList());
    }
}
