package com.example.demo.Category;

import com.example.demo.AmazonBackendApplicationTests;
import com.example.demo.category.model.Category;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.category.service.GetCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AmazonBackendApplicationTests.class)
public class GetCategoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private GetCategory getCategory;

    @BeforeEach
    void setup(){
        getCategory = new GetCategory(categoryRepository);
    }

    @Test
    void getCategory_returnsIterable(){
        List<Category> categoryList = Arrays.asList(
                new Category("Electronics"),
                new Category("Bathroom"),
                new Category("Automobile")
        );
        when(categoryRepository.findAll()).thenReturn(categoryList);
        Iterable<String> expected = Arrays.asList("Electronics", "Bathroom", "Automobile");
        ResponseEntity<Iterable<String>> actual = getCategory.execute(null);
        assertEquals(ResponseEntity.ok(expected) , actual);
    }

    @Test
    void getCategory_returnsEmptyIterable(){
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<String> expected = Collections.emptyList();
        ResponseEntity<Iterable<String>> actual = getCategory.execute(null);
        assertEquals(ResponseEntity.ok(expected) , actual);
    }
}
