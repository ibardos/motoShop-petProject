package com.ibardos.motoShop.model;

import com.ibardos.motoShop.util.MotorcycleModelType;

/**
 * Model class, representing a Motorcycle model, containing specifications about a specific Motorcycle in DB.
 */
public class MotorcycleModel {
    // Properties
    private int id;
    private Manufacturer manufacturer;
    private String modelName;
    private int modelYear;
    private int weight;
    private int displacement;
    private int horsePower;
    private int topSpeed;
    private int gearbox;
    private float fuelCapacity;
    private float fuelConsumptionPer100Kms;
    private MotorcycleModelType motorcycleModelType;


    // Constructor
    public MotorcycleModel(Manufacturer manufacturer, String modelName, int modelYear, int weight, int displacement, int horsePower, int topSpeed, int gearbox, float fuelCapacity, float fuelConsumptionPer100Kms, MotorcycleModelType motorcycleModelType) {
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.weight = weight;
        this.displacement = displacement;
        this.horsePower = horsePower;
        this.topSpeed = topSpeed;
        this.gearbox = gearbox;
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumptionPer100Kms = fuelConsumptionPer100Kms;
        this.motorcycleModelType = motorcycleModelType;
    }


    // Getters and setters
    public int getId() {
        return id;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public int getModelYear() {
        return modelYear;
    }

    public int getWeight() {
        return weight;
    }

    public int getDisplacement() {
        return displacement;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public int getGearbox() {
        return gearbox;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public float getFuelConsumptionPer100Kms() {
        return fuelConsumptionPer100Kms;
    }

    public MotorcycleModelType getMotorcycleModelType() {
        return motorcycleModelType;
    }

    public void setId(int id) {
        this.id = id;
    }
}
