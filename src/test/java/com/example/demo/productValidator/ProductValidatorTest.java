package com.example.demo.productValidator;

import com.example.demo.AmazonBackendApplicationTests;
import com.example.demo.category.model.Category;
import com.example.demo.exceptions.ErrorMessage;
import com.example.demo.exceptions.InvalidProductException;
import com.example.demo.product.model.ProductRequest;
import com.example.demo.product.productvalidator.ProductValidator;
import com.example.demo.product.productvalidator.ProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AmazonBackendApplicationTests.class)
public class ProductValidatorTest {

    @Mock
    private ProfanityValidator profanityValidator;

    @InjectMocks
    private ProductValidator productValidator;

    @BeforeEach
    void setup(){
        this.productValidator = new ProductValidator(profanityValidator);
    }

    @Test
    void testNameIsEmpty_throwsInvalidProductException(){
        ProductRequest productRequestNull = getValidProductRequest();
        productRequestNull.setName(null);
        InvalidProductException exceptionNull = assertThrows(InvalidProductException.class , () -> productValidator.execute(productRequestNull , getCategories()));
        assertEquals(ErrorMessage.PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage(), exceptionNull.getSimpleResponse().getMessage());

        ProductRequest productRequestEmpty = getValidProductRequest();
        productRequestEmpty.setName("");
        InvalidProductException exceptionEmpty = assertThrows(InvalidProductException.class , () -> productValidator.execute(productRequestEmpty , getCategories()));
        assertEquals(ErrorMessage.PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage() , exceptionEmpty.getSimpleResponse().getMessage());
    }

    @Test
    void testPriceIsNegative_throwsInvalidProductException(){
        ProductRequest productRequestNull = getValidProductRequest();
        productRequestNull.setPrice(null);
        InvalidProductException exceptionNull = assertThrows(InvalidProductException.class , () -> productValidator.execute(productRequestNull , getCategories()));
        assertEquals(ErrorMessage.PRODUCT_PRICE_CANNOT_BE_NEGATIVE.getMessage(), exceptionNull.getSimpleResponse().getMessage());

        ProductRequest productRequestEmpty = getValidProductRequest();
        productRequestEmpty.setPrice(-19.99);
        InvalidProductException exceptionEmpty = assertThrows(InvalidProductException.class , () -> productValidator.execute(productRequestEmpty , getCategories()));
        assertEquals(ErrorMessage.PRODUCT_PRICE_CANNOT_BE_NEGATIVE.getMessage() , exceptionEmpty.getSimpleResponse().getMessage());
    }

    @Test
    void testCategoryNotAvailable_throwsInvalidProductException(){
        ProductRequest productRequest = getValidProductRequest();
        productRequest.setCategory("Not available");
        InvalidProductException exception = assertThrows(InvalidProductException.class , () -> productValidator.execute(productRequest , getCategories()));
        assertEquals(ErrorMessage.PRODUCT_CATEGORY_IS_NOT_AVAILABLE.getMessage() , exception.getSimpleResponse().getMessage());
    }

    @Test
    void testCategoriesEmpty_throwInvalidProductException(){
        ProductRequest request = getValidProductRequest();
        request.setCategory("SPORTS");
        InvalidProductException exception = assertThrows(InvalidProductException.class , () -> productValidator.execute(request,getCategories()));
        assertEquals(ErrorMessage.PRODUCT_CATEGORY_IS_NOT_AVAILABLE.getMessage() , exception.getSimpleResponse().getMessage());
    }

    @Test
    void testRegionNotAvailable_throwInvalidProductException(){
        ProductRequest productRequest = getValidProductRequest();
        productRequest.setRegion("JAPAN");
        InvalidProductException exception = assertThrows(InvalidProductException.class , () -> productValidator.execute(productRequest , getCategories()));
        assertEquals(ErrorMessage.PRODUCT_REGION_IS_NOT_AVAILABLE.getMessage() , exception.getSimpleResponse().getMessage());
    }

    @Test
    void productHasProfanity_throwInvalidProductException(){
        ProductRequest productRequest = getValidProductRequest();
        when(profanityValidator.hasProfanity(anyString() , anyString())).thenReturn(true);
        InvalidProductException exception = assertThrows(InvalidProductException.class , () -> productValidator.execute(productRequest , getCategories()));
        assertEquals(ErrorMessage.PRODUCT_HAS_PROFANITY.getMessage() , exception.getSimpleResponse().getMessage());
    }

    private ProductRequest getValidProductRequest(){
        return new ProductRequest("testName" , "testDescription" , "testManufacturer" , 19.99 , "Electronics" , "US");
    }
    private List<Category> getCategories(){
        return Arrays.asList(new Category("Electronics") , new Category("Food"));
    }
}
