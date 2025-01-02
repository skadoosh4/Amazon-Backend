package com.example.demo.product.service;

import com.example.demo.Query;
import com.example.demo.product.model.GetProductsQuery;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.model.ProductSortBy;
import com.example.demo.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetProducts implements Query<GetProductsQuery, Iterable<ProductDTO>> {

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(GetProducts.class);

    @Autowired
    public GetProducts (ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Iterable<ProductDTO>> execute(GetProductsQuery query) {
        logger.info("Get ALL Products " + getClass().getSimpleName());

        Sort productSort = defineSort(query.getProductSortBy());

        return ResponseEntity.ok(productRepository
                .findByNameOrDescriptionAndRegionAndCategory(
                        query.getNameOrDescription(),
                        query.getRegion(),
                        query.getCategory(),
                        productSort
                )
                .stream()
                .map(ProductDTO::new)
                .toList());
    }

    public Sort defineSort(ProductSortBy productSortBy) {
        if(productSortBy == null){
            return Sort.unsorted();
        }
        ProductSortBy sortBy = ProductSortBy.valueOf(productSortBy.getValue());
        return Sort.by(String.valueOf(sortBy));
    }
}
