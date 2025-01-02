package com.example.demo.Product;


import com.example.demo.AmazonBackendApplicationTests;
import com.example.demo.category.model.Category;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.repository.ProductRepository;
import com.example.demo.product.service.GetProductById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AmazonBackendApplicationTests.class)
public class GetProductByIdTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductById getProductById;

    @BeforeEach
    void setup(){
        getProductById = new GetProductById(productRepository);
    }

    @Test
    void getProductById_returnsSuccess(){
        String id = "1";
        Product product = new Product();
        product.setId(id);
        product.setCategory(new Category("Electronics"));

        ProductDTO productDTO = new ProductDTO(product);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        ResponseEntity<ProductDTO> responseEntity = getProductById.execute(id);
        verify(productRepository).findById(id);
        assertEquals(HttpStatus.OK , responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody() , productDTO);
    }

    @Test
    void getProductById_returnsthrowsProductNotFoundException(){
        String id = "1";
        Product product = new Product();
        product.setId(id);
        product.setCategory(new Category("Electronics"));

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class , () -> getProductById.execute(id));
        assertEquals("Product not found" , exception.getSimpleResponse().getMessage());
    }
}
