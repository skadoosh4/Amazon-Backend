package com.example.demo.product.repository;

import com.example.demo.product.model.Product;
import com.example.demo.product.model.Region;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , String> {
    @Query("SELECT p from Product p WHERE " +
            "(:nameOrDescription is null or p.name like %:nameOrDescription% or p.description like %:nameOrDescription%) and "+
            "(p.region = :region) and "+
            "(:category is null or p.category.value = :category)")
    List<Product> findByNameOrDescriptionAndRegionAndCategory(
            String nameOrDescription,
            Region region,
            String category,
            Sort sort
    );
}
