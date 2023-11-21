package com.ibardos.motoShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Model class, representing a Manufacturer in DB.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Manufacturer {
    // Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String country;
    private Date partnerSince;
}