package com.rachidy.sassgestionstockapp.mappers;

import com.rachidy.sassgestionstockapp.entities.Product;
import com.rachidy.sassgestionstockapp.entities.StockMvt;
import com.rachidy.sassgestionstockapp.requests.StockMvtRequest;
import com.rachidy.sassgestionstockapp.responses.StockMvtResponse;
import org.springframework.stereotype.Component;

@Component
public class StockMvtMapper {

    public StockMvt toEntity(StockMvtRequest request){
        return StockMvt.builder()
                .typeMvt(request.getTypeMvt())
                .dateMvt(request.getDateMvt())
                .quantity(request.getQuantity())
                .comment(request.getComment())
                .product(Product.builder().id(request.getProductId()).build())
                .build();
    }

    public StockMvtResponse toResponse(StockMvt entity){
        return StockMvtResponse.builder()
                .typeMvt(entity.getTypeMvt())
                .dateMvt(entity.getDateMvt())
                .comment(entity.getComment())
                .quantity(entity.getQuantity())
                .build();
    }
}
