package com.example.demo.product.controller;

import com.example.demo.product.model.*;
import com.example.demo.product.service.*;
import com.example.demo.product.model.ProductRequest;
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
    private final CreateProduct createProduct;
    private final UpdateProduct updateProduct;

    @Autowired
    public ProductController(GetProducts getProducts , DeleteProductById deleteProductById , GetProductById getProductById , CreateProduct createProduct , UpdateProduct updateProduct) {
        this.getProducts = getProducts;
        this.deleteProductById = deleteProductById;
        this.getProductById = getProductById;
        this.createProduct = createProduct;
        this.updateProduct = updateProduct;
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

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequest request){
        return createProduct.execute(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductRequest request , @PathVariable String id){
        return updateProduct.execute(new UpdateProductPair(id , request));
    }
}
