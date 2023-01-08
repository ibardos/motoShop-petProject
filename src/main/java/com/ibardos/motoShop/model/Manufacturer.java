package com.ibardos.motoShop.model;

import java.sql.Date;

/**
 * Model class, representing a Manufacturer in DB.
 */
public class Manufacturer {
    // Properties
    private int id;
    private String name;
    private String country;
    private Date partnerSince;


    // Constructor
    public Manufacturer(String name, String country, Date partnerSince) {
        this.name = name;
        this.country = country;
        this.partnerSince = partnerSince;
    }


    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Date getPartnerSince() {
        return partnerSince;
    }

    public void setId(int id) {
        this.id = id;
    }
}