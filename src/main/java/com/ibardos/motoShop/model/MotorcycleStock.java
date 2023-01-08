package com.ibardos.motoShop.model;

import java.math.BigDecimal;

/**
 * Model class, representing a Motorcycle model in Stock, containing mileage, stock and financial related data in DB.
 */
public class MotorcycleStock {
    // Properties
    private int id;
    private MotorcycleModel motorcycleModel;
    private int mileage;
    private BigDecimal purchasingPrice;
    private Float profitMargin;
    private  BigDecimal profitOnUnit;
    private BigDecimal sellingPrice;
    private int inStock;
    private String color;


    // Constructor
    public MotorcycleStock(MotorcycleModel motorcycleModel, int mileage, BigDecimal purchasingPrice, Float profitMargin, BigDecimal profitOnUnit, BigDecimal sellingPrice, int inStock, String color) {
        this.motorcycleModel = motorcycleModel;
        this.mileage = mileage;
        this.purchasingPrice = purchasingPrice;
        this.profitMargin = profitMargin;
        this.profitOnUnit = profitOnUnit;
        this.sellingPrice = sellingPrice;
        this.inStock = inStock;
        this.color = color;
    }


    // Getters and setters
    public int getId() { return id; }

    public MotorcycleModel getMotorcycleModel() { return motorcycleModel; }

    public int getMileage() { return mileage; }

    public BigDecimal getPurchasingPrice() { return purchasingPrice; }

    public Float getProfitMargin() { return profitMargin; }

    public BigDecimal getProfitOnUnit() { return profitOnUnit; }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public int getInStock() {
        return inStock;
    }

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfitOnUnit(BigDecimal profitOnUnit) { this.profitOnUnit = profitOnUnit; }

    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
}
