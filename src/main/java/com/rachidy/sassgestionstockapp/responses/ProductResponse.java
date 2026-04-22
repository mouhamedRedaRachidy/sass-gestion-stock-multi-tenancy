package com.rachidy.sassgestionstockapp.responses;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String name;
    private int reference;
    private int quantity;
    private BigDecimal price;
    private int alertThreshold;
    private String categoryName;
    private int availableQuantity;
}
