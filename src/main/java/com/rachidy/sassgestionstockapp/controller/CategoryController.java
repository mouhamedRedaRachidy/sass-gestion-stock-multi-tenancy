package com.rachidy.sassgestionstockapp.controller;

import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.requests.CategoryRequest;
import com.rachidy.sassgestionstockapp.responses.CategoryResponse;
import com.rachidy.sassgestionstockapp.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>>getAll(
            @RequestParam(name = "page", defaultValue = "0")
            final int page,
            @RequestParam(name = "size", defaultValue = "10")
            final int size
    ){
        PageResponse<CategoryResponse>body=this.categoryService.findAll(page,size);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse>findOne(
            @PathVariable
            @NotNull(message = "Category ID cannot be null")
            final String categoryId
    ){
        CategoryResponse body=this.categoryService.findById(categoryId);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<Void>create(
            @RequestBody
            @Valid
            final CategoryRequest request
    ){
        this.categoryService.create(request);
        return ResponseEntity.ok().build();
    }
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<Void>update(
            @PathVariable
            @NotNull(message = "Category ID cannot be null")
            final String categoryId,
            @RequestBody
            @Valid
            CategoryRequest request

    ){
        this.categoryService.update(categoryId,request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void>delete(
            @PathVariable
            @NotNull(message = "Category ID cannot be null")
            final String categoryId
    ){
        this.categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }
}
