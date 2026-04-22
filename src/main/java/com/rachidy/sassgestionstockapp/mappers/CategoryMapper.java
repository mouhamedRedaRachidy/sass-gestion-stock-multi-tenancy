package com.rachidy.sassgestionstockapp.mappers;

import com.rachidy.sassgestionstockapp.entities.Category;
import com.rachidy.sassgestionstockapp.requests.CategoryRequest;
import com.rachidy.sassgestionstockapp.responses.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request){
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategoryResponse toResponse(Category entity){
        int nbProducts=0;
        return CategoryResponse.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .nbProducts(nbProducts)
                .build();
    }
}
