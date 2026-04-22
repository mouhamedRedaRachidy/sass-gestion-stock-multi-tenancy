package com.rachidy.sassgestionstockapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "stock_mvts")
public class StockMvt extends AbstractEntity{

    @Column(name = "type_mvt",nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeMvt typeMvt;

    @Column(name = "quantity", nullable = false )
    private int quantity;

    @Column(name = "date_mvt")
    private LocalDateTime dateMvt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
