package com.example.demo.productValidator;

import com.example.demo.AmazonBackendApplicationTests;
import com.example.demo.exceptions.ProfanityFilterException;
import com.example.demo.product.productvalidator.ProfanityFilterAPIResponse;
import com.example.demo.product.productvalidator.ProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AmazonBackendApplicationTests.class)
public class ProfanityValidatorTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProfanityValidator profanityValidator;

    @BeforeEach
    void setup(){
        this.profanityValidator = new ProfanityValidator(restTemplate);
    }

    @Test
    void testHasProfanity_returnsTrue(){
        ProfanityFilterAPIResponse response = new ProfanityFilterAPIResponse(true);
        when(restTemplate
                .exchange(anyString() , eq(HttpMethod.GET) , any(HttpEntity.class) , eq(ProfanityFilterAPIResponse.class)))
                .thenReturn(new ResponseEntity<>(response , HttpStatus.OK));
        assertTrue(profanityValidator.hasProfanity("testName" , "testDescription"));
    }

    @Test
    void testNoProfanity_returnsFalse(){
        ProfanityFilterAPIResponse response = new ProfanityFilterAPIResponse(false);
        when(restTemplate
                .exchange(anyString() , eq(HttpMethod.GET) , any(HttpEntity.class) , eq(ProfanityFilterAPIResponse.class)))
                .thenReturn(new ResponseEntity<>(response , HttpStatus.OK));
        assertFalse(profanityValidator.hasProfanity("testName" , "testDescription"));
    }

    @Test
    void testProfanityValidator_throwException(){
        when(restTemplate
                .exchange(anyString() , eq(HttpMethod.GET) , any(HttpEntity.class) , eq(ProfanityFilterAPIResponse.class)))
                .thenThrow(new ProfanityFilterException());

        assertThrows(ProfanityFilterException.class , () -> profanityValidator.hasProfanity("testName" , "testDescription"));
    }
}
