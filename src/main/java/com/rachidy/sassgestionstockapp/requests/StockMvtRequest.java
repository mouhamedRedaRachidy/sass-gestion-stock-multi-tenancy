package com.rachidy.sassgestionstockapp.requests;

import com.rachidy.sassgestionstockapp.entities.TypeMvt;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMvtRequest {

    @NotNull(message = "Movement type is required")
    private TypeMvt typeMvt;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;

    @NotNull(message = "Movement date is required")
    @PastOrPresent(message = "Movement date cannot be in the future")
    private LocalDateTime dateMvt;

    private String comment;

    @NotBlank(message = "Product ID is required")
    private String productId;

}
