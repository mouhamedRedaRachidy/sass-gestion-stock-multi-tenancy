package com.rachidy.sassgestionstockapp.services.impl;

import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.entities.Category;
import com.rachidy.sassgestionstockapp.exceptions.DuplicateResourceException;
import com.rachidy.sassgestionstockapp.mappers.CategoryMapper;
import com.rachidy.sassgestionstockapp.repositories.CategoryRepository;
import com.rachidy.sassgestionstockapp.requests.CategoryRequest;
import com.rachidy.sassgestionstockapp.responses.CategoryResponse;
import com.rachidy.sassgestionstockapp.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void create(CategoryRequest request) {
        // Check if category name already exist
        checkIfCategoryNameAlreadyExist(request.getName());
       Category entity=this.categoryMapper.toEntity(request);
       this.categoryRepository.save(entity);
    }

    @Override
    public void update(String id, CategoryRequest request) {
        final Optional<Category>category=this.categoryRepository.findById(id);
        if(category.isEmpty()){
            log.debug("Category not found");
            throw new EntityNotFoundException("Category id: "+id+" not found");
        }
        // Check if category name already exist
        checkIfCategoryNameAlreadyExist(request.getName());

        Category categoryToUpdate=this.categoryMapper.toEntity(request);
        categoryToUpdate.setId(id);

        this.categoryRepository.save(categoryToUpdate);

    }

    @Override
    public PageResponse<CategoryResponse> findAll(int page, int size) {
        final PageRequest pageRequest=PageRequest.of(page,size);
        final Page<Category>categories=this.categoryRepository.findAll(pageRequest);
        final Page<CategoryResponse>categoryResponse=categories.map(this.categoryMapper::toResponse);
        return PageResponse.of(categoryResponse);


    }

    @Override
    public CategoryResponse findById(String id) {
        return this.categoryRepository.findById(id)
                .map(this.categoryMapper::toResponse)
                .orElseThrow(()-> new EntityNotFoundException("Category Not exist"));

    }

    @Override
    public void delete(String id) {
        Category  category=this.categoryRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Category Not exist"));
        this.categoryRepository.delete(category);
    }

    private void checkIfCategoryNameAlreadyExist(String categoryName){
        final Optional<Category>existing=this.categoryRepository.findCategoryByNameIgnoreCase(categoryName);
        if(existing.isPresent()){
            log.debug("category name is already exist");
            throw new DuplicateResourceException("Category name is already exist");
        }
    }
}
