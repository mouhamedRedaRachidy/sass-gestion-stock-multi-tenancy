package com.rachidy.sassgestionstockapp.requests;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private int reference;
    private int quantity;
    private BigDecimal price;
    private int alertThreshold;
    private String categoryId;

}
