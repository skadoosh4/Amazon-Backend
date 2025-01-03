package com.example.demo.product.service;

import com.example.demo.Command;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.model.ProductRequest;
import com.example.demo.product.productvalidator.ProductValidator;
import com.example.demo.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateProduct implements Command<ProductRequest , ProductDTO> {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductValidator productValidator;

    private Logger logger = LoggerFactory.getLogger(CreateProduct.class);

    @Autowired
    public CreateProduct(ProductRepository productRepository , CategoryRepository categoryRepository , ProductValidator productValidator){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productValidator = productValidator;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(ProductRequest request) {
        logger.info("Create Product Service " + request + " " + getClass().getSimpleName());

        Product validatedProduct = productValidator.execute(request , categoryRepository.findAll());

        productRepository.save(validatedProduct);

        return ResponseEntity.ok(new ProductDTO(validatedProduct));
    }
}
