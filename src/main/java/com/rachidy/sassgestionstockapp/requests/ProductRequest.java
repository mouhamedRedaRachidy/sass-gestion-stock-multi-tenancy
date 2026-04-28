package com.rachidy.sassgestionstockapp.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
    private String name;

    @Positive(message = "Reference must be a positive number")
    private int reference;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;

    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    @Positive(message = "Alert threshold must be greater than 0")
    private int alertThreshold;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

}
