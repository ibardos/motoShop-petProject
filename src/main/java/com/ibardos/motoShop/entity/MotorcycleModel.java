package com.ibardos.motoShop.entity;

import com.ibardos.motoShop.util.MotorcycleModelType;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Model class, representing a Motorcycle model, containing specifications about a specific Motorcycle in DB.
 */
@Entity
@Table(name = "motorcycle_model")
@Getter
@Setter
@NoArgsConstructor
public class MotorcycleModel {
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    private String modelName;
    private int modelYear;
    private int weight;
    private int displacement;
    private int horsePower;
    private int topSpeed;
    private int gearbox;
    private float fuelCapacity;
    @Column(name = "fuel_consumption_per_100kms")
    private float fuelConsumptionPer100Kms;
    private MotorcycleModelType motorcycleModelType;
}
