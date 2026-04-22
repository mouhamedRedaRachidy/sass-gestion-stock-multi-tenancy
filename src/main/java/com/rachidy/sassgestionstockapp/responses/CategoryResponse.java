package com.rachidy.sassgestionstockapp.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String name;
    private String description;
    private int nbProducts;
}
