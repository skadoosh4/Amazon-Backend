package com.example.demo.product.productvalidator;

import com.example.demo.config.API_KeyReader;
import com.example.demo.exceptions.ProfanityFilterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProfanityValidator {

    private static final Logger logger = LoggerFactory.getLogger(ProfanityValidator.class);

    private static final String API_KEY = API_KeyReader.getApiKey();

    public final RestTemplate restTemplate;

    @Autowired
    public ProfanityValidator(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public boolean hasProfanity(String name , String description){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key" , API_KEY);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try{
            ResponseEntity<ProfanityFilterAPIResponse> responseEntity = restTemplate.exchange(
                    "https://api.api-ninjas.com/v1/profanityfilter?text="+name+" "+description,
                    HttpMethod.GET,
                    entity,
                    ProfanityFilterAPIResponse.class
            );

            logger.info("Profanity Validator -" + responseEntity.getBody());

            return responseEntity.getBody().isHas_profanity();

        }catch (Exception e){
            throw new ProfanityFilterException();
        }
    }
}
