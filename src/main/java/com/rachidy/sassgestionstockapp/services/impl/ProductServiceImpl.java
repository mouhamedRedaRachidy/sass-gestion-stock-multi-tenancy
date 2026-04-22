package com.rachidy.sassgestionstockapp.services.impl;

import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.entities.Category;
import com.rachidy.sassgestionstockapp.entities.Product;
import com.rachidy.sassgestionstockapp.mappers.ProductMapper;
import com.rachidy.sassgestionstockapp.repositories.CategoryRepository;
import com.rachidy.sassgestionstockapp.repositories.ProductRepository;
import com.rachidy.sassgestionstockapp.requests.ProductRequest;
import com.rachidy.sassgestionstockapp.responses.ProductResponse;
import com.rachidy.sassgestionstockapp.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    final ProductMapper productMapper;
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;

    @Override
    public void create(ProductRequest request) {
        // Check Product already exist by reference
        checkProductAlreadyExistByReference(request.getReference());
        // Check Category  exist
        checkCategoryExist(request.getCategoryId());
        // Mapping request To entity
        Product entity=this.productMapper.toEntity(request);
        // Save Product
        this.productRepository.save(entity);
    }

    @Override
    public void update(String id, ProductRequest request) {
        // Check Product exist
        checkProductExistById(id);
        // Check Product already exist by reference
        checkProductAlreadyExistByReference(request.getReference());
        // Check Category  exist
        checkCategoryExist(request.getCategoryId());
        // Mapping Request To entity
        Product productToUpdate=this.productMapper.toEntity(request);
        // Set ID
        productToUpdate.setId(id);
        // update in DB
        this.productRepository.save(productToUpdate);

    }

    @Override
    public PageResponse<ProductResponse> findAll(int page, int size) {
        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Product>products=this.productRepository.findAll(pageRequest);
        Page<ProductResponse>productResponses=products.map(this.productMapper::toResponse);
        return PageResponse.of(productResponses);
    }

    @Override
    public ProductResponse findById(String id) {
        return this.productRepository.findById(id)
                .map(this.productMapper::toResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not exist"));
    }

    @Override
    public void delete(String id) {
        final Product product=this.productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Product not exist"));

        this.productRepository.delete(product);

    }

    private void checkProductAlreadyExistByReference(int reference){
        final Optional<Product>existingProduct=this.productRepository.findProductByReference(reference);
        if(existingProduct.isPresent()){
            log.debug("Product is already exist");
            throw new RuntimeException("Product is already exist");
        }
    }

    private void checkCategoryExist(String categoryId){
        final Optional<Category> existingCategory=this.categoryRepository.findById(categoryId);
        if(existingCategory.isEmpty()){
            log.debug("Category not exist");
            throw new EntityNotFoundException("Category not already exist");
        }
    }

    private void checkProductExistById(String id){
        final Optional<Product> existing=this.productRepository.findById(id);
        if (existing.isEmpty()){
            log.debug("Product not exist");
            throw new EntityNotFoundException("Product not exist");
        }
    }
}
