package com.rachidy.sassgestionstockapp.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 255, message = "Category name must be between 3 and 255 characters")
    private String name;

    private String description;
}
