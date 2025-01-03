package com.example.demo.product.service;

import com.example.demo.Command;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.model.UpdateProductPair;
import com.example.demo.product.productvalidator.ProductValidator;
import com.example.demo.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProduct implements Command<UpdateProductPair , ProductDTO> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateProduct.class);
    private final ProductValidator productValidator;

    @Autowired
    public UpdateProduct(ProductRepository productRepository , CategoryRepository categoryRepository , ProductValidator productValidator){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productValidator = productValidator;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UpdateProductPair input) {
        logger.info("Update Product "+ input.getId() + " " + input.getRequest() + " " + getClass().getSimpleName());
        Optional<Product> optionalProduct = productRepository.findById(input.getId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
        }
        Product validatedProduct = productValidator.execute(input.getRequest() , categoryRepository.findAll());
        validatedProduct.setId(input.getId());
        validatedProduct.setCreatedAt(optionalProduct.get().getCreatedAt());
        productRepository.save(validatedProduct);
        return ResponseEntity.ok(new ProductDTO(validatedProduct));
    }
}
