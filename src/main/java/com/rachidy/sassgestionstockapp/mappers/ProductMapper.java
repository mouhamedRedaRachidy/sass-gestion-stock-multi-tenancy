package com.rachidy.sassgestionstockapp.mappers;

import com.rachidy.sassgestionstockapp.entities.Category;
import com.rachidy.sassgestionstockapp.entities.Product;
import com.rachidy.sassgestionstockapp.requests.ProductRequest;
import com.rachidy.sassgestionstockapp.responses.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .reference(request.getReference())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .alertThreshold(request.getAlertThreshold())
                .category(Category.builder().id(request.getCategoryId()).build())
                .build();
    }

    public ProductResponse toResponse(Product entity){
        return ProductResponse.builder()
                .name(entity.getName())
                .reference(entity.getReference())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .alertThreshold(entity.getAlertThreshold())
                .categoryName(entity.getCategory().getName())
                //availableQuantity to be later implemented
                .build();
    }
}
