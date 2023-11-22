package com.ibardos.motoShop.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Model class, representing a Motorcycle model in Stock, containing mileage, stock and financial related data in DB.
 */
@Entity
@Table(name = "motorcycle_stock")
@Getter
@Setter
@NoArgsConstructor
public class MotorcycleStock {
    // Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "motorcycle_model_id")
    private MotorcycleModel motorcycleModel;
    private int mileage;
    private BigDecimal purchasingPrice;
    private Float profitMargin;
    private  BigDecimal profitOnUnit;
    private BigDecimal sellingPrice;
    private int inStock;
    private String color;
}
