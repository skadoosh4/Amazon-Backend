package com.example.demo.Product;

import com.example.demo.AmazonBackendApplicationTests;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.product.model.Product;
import com.example.demo.product.repository.ProductRepository;
import com.example.demo.product.service.DeleteProductById;
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
public class DeleteProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductById deleteProductById;

    @BeforeEach
    void setup(){
        deleteProductById = new DeleteProductById(productRepository);
    }

    @Test
    void deleteProductById_returnsSuccess(){
        String id = "1";
        Product product = new Product();
        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        ResponseEntity<Void> responseEntity = deleteProductById.execute(id);
        verify(productRepository).deleteById(product.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteProductById_throwsProductNotFoundException(){
        String id  = "1";
        Product product = new Product();
        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class , () -> deleteProductById.execute(id));
        assertEquals("Product not found" , exception.getSimpleResponse().getMessage());
    }
}
