package com.example.demo.product.service;

import com.example.demo.Query;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetProducts implements Query<Void , Iterable<ProductDTO>> {

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(GetProducts.class);

    @Autowired
    public GetProducts (ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Iterable<ProductDTO>> execute(Void input) {
        return null;
//        return ResponseEntity.ok(productRepository
//                .findAll()
//                .stream()
//                .map()
//                .toList());
    }
}
