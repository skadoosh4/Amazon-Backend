package com.example.demo.product.productvalidator;

import com.example.demo.category.model.Category;
import com.example.demo.exceptions.ErrorMessage;
import com.example.demo.exceptions.InvalidProductException;
import com.example.demo.exceptions.SimpleResponse;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductRequest;
import com.example.demo.product.model.Region;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidator {

    private final ProfanityValidator profanityValidator;

    @Autowired
    public ProductValidator(ProfanityValidator profanityValidator) {
        this.profanityValidator = profanityValidator;
    }

    public Product execute(ProductRequest request  , List<Category> availableCategories){
        if(nameIsEmpty(request.getName())){
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage()),request);
        }

        if(priceIsNegative(request.getPrice())){
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_PRICE_CANNOT_BE_NEGATIVE.getMessage()),request);
        }

        if(categoryNotAvailable(request.getCategory() , availableCategories)){
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_CATEGORY_IS_NOT_AVAILABLE.getMessage()),request);
        }

        if(regionNotAvailable(request.getRegion())){
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_REGION_IS_NOT_AVAILABLE.getMessage()),request);
        }

        if(profanityValidator.hasProfanity(request.getName() , request.getDescription())){
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_HAS_PROFANITY.getMessage()) , request);
        }

        return new Product(request);
    }

    private static boolean categoryNotAvailable(String category, List<Category> availableCategories) {
        return !availableCategories.contains(new Category(category));
    }

    private static boolean regionNotAvailable(String candidateRegion) {
        for(Region region  : Region.values()){
            if(region.name().equals(candidateRegion)){
                return false;
            }
        }
        return true;
    }

    private static boolean priceIsNegative(Double price) {
        return price == null || price <= 0.0;
    }

    private static boolean nameIsEmpty(String name) {
        return StringUtils.isEmpty(name);
    }
}
