package com.example.demo.product.service;

import com.example.demo.Command;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.product.model.Product;
import com.example.demo.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteProductById implements Command<String , Void> {

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(DeleteProductById.class);

    @Autowired
    public DeleteProductById(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    @Override
    public ResponseEntity<Void> execute(String id) {
        logger.error("Delete Product Command , id : " + id + " " + getClass().getSimpleName());
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
