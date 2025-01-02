package com.example.demo.Product;

import com.example.demo.AmazonBackendApplicationTests;
import com.example.demo.category.model.Category;
import com.example.demo.product.model.GetProductsQuery;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.model.ProductSortBy;
import com.example.demo.product.repository.ProductRepository;
import com.example.demo.product.service.GetProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AmazonBackendApplicationTests.class)
public class GetProductsTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProducts getProducts;

    @BeforeEach
    void setup(){
        getProducts = new GetProducts(productRepository);
    }

    @Test
    void testGetProducts_returnsEmptyIterable(){
        when(productRepository.findByNameOrDescriptionAndRegionAndCategory(null, null , null , null)).
                thenReturn(Collections.emptyList());
        ResponseEntity<Iterable<ProductDTO>> responseEntity = getProducts.execute(new GetProductsQuery(null , null , null , null));
        assertEquals(Collections.emptyList() , responseEntity.getBody());
    }

    @Test
    void testGetProducts_returnsList(){
        when(productRepository.findByNameOrDescriptionAndRegionAndCategory(any() , any() , any() , any()))
                .thenReturn(getProductList());

        ResponseEntity<Iterable<ProductDTO>> responseEntity = getProducts.execute(new GetProductsQuery(null , null , null , null));
        Iterable<ProductDTO> actualList = responseEntity.getBody();
        assertEquals( 2 , actualList.spliterator().getExactSizeIfKnown());
    }

    @Test
    void testDefineSort_returnsUnsorted(){
        Sort sort = getProducts.defineSort(null);
        assertEquals(Sort.unsorted() , sort);
    }

    @Test
    void testDefineSort_returnsSorted(){
        Sort sort = getProducts.defineSort(ProductSortBy.name);
        assertEquals(Sort.by(Sort.Direction.ASC , "name") , sort);
    }

    private List<Product> getProductList(){
        Product product1 = new Product();
        Product product2 = new Product();

        product1.setId("1");
        product1.setCategory(new Category("Electronics"));

        product2.setId("2");
        product2.setCategory(new Category("Electronics"));

        return Arrays.asList(product1 , product2);
    }
}
