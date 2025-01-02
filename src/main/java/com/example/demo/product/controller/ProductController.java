package com.example.demo.product.controller;

import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.service.DeleteProductById;
import com.example.demo.product.service.GetProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final GetProducts getProducts;
    private final DeleteProductById deleteProductById;

    @Autowired
    public ProductController(GetProducts getProducts , DeleteProductById deleteProductById) {
        this.getProducts = getProducts;
        this.deleteProductById = deleteProductById;
    }

    @GetMapping
    public ResponseEntity<Iterable<ProductDTO>> getAllProducts(){
        return getProducts.execute(null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id){
        return deleteProductById.execute(id);
    }
}
