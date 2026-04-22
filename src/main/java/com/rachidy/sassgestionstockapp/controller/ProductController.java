package com.rachidy.sassgestionstockapp.controller;

import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.mappers.ProductMapper;
import com.rachidy.sassgestionstockapp.requests.ProductRequest;
import com.rachidy.sassgestionstockapp.responses.ProductResponse;
import com.rachidy.sassgestionstockapp.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>>getAll(
            @RequestParam(name = "page")
            final int page,
            @RequestParam(name = "size")
            final int size
    ){
        PageResponse<ProductResponse>body=this.productService.findAll(page, size);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse>findOne(
            @PathVariable
            @NotNull(message = "Product ID cannot be null")
            final String productId
    ){
        ProductResponse productResponse=this.productService.findById(productId);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping
    public ResponseEntity<Void>create(
            @RequestBody
            @Valid
            final  ProductRequest productRequest
    ){
        this.productService.create(productRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void>update(
            @PathVariable
            @NotNull(message = "Product ID caanot be null")
            final String productId,
            @RequestBody
            @Valid
            ProductRequest productRequest
    ){
        this.productService.update(productId,productRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void>delete(
            @PathVariable
            @NotNull(message = "Product ID cannot be null")
            final String productId
    ){
        this.productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
