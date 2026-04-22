package com.rachidy.sassgestionstockapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Product extends AbstractEntity {

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "reference",nullable = false,unique = true)
    private int reference;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "alert_threshold",nullable = false)
    private int alertThreshold;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<StockMvt> stockMvts;

}
