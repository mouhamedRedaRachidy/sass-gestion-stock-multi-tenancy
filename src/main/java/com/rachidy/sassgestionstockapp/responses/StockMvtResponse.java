package com.rachidy.sassgestionstockapp.responses;

import com.rachidy.sassgestionstockapp.entities.TypeMvt;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMvtResponse {

    private TypeMvt typeMvt;
    private int quantity;
    private LocalDateTime dateMvt;
    private String comment;
    
}
