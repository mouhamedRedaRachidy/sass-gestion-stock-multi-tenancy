package com.rachidy.sassgestionstockapp.requests;

import com.rachidy.sassgestionstockapp.entities.TypeMvt;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMvtRequest {

    private TypeMvt typeMvt;
    private int quantity;
    private LocalDateTime dateMvt;
    private String comment;
    private String productId;

}
