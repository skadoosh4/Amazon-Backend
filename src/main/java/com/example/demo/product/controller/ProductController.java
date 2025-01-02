package com.example.demo.product.controller;

import com.example.demo.product.model.GetProductsQuery;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.model.ProductSortBy;
import com.example.demo.product.model.Region;
import com.example.demo.product.service.DeleteProductById;
import com.example.demo.product.service.GetProductById;
import com.example.demo.product.service.GetProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final GetProducts getProducts;
    private final DeleteProductById deleteProductById;
    private final GetProductById getProductById;

    @Autowired
    public ProductController(GetProducts getProducts , DeleteProductById deleteProductById , GetProductById getProductById) {
        this.getProducts = getProducts;
        this.deleteProductById = deleteProductById;
        this.getProductById = getProductById;
    }

    @GetMapping()
    @Cacheable("products")
    public ResponseEntity<Iterable<ProductDTO>> getAllProducts(
            @RequestHeader(value = "region" , defaultValue = "US") String region,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String nameOrDescription,
            @RequestParam(required = false) String orderBy
    ){
        return getProducts.execute(new GetProductsQuery(Region.valueOf(region), category , nameOrDescription , ProductSortBy.fromValue(orderBy)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id){
        return getProductById.execute(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id){
        return deleteProductById.execute(id);
    }
}
