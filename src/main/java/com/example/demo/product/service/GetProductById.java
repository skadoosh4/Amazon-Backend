package com.example.demo.product.service;

import com.example.demo.Command;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProductById implements Command<String , ProductDTO> {

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(GetProducts.class);

    @Autowired
    public GetProductById(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(String id) {
        logger.info("Get Product By Id : " + id + " " + getClass().getSimpleName());
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
        }
        ProductDTO productDTO = new ProductDTO(optionalProduct.get());
        return ResponseEntity.ok(productDTO);
    }
}
