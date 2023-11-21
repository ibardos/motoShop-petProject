package com.ibardos.motoShop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Model class, representing a Manufacturer in DB.
 */
@Getter
@Setter
@NoArgsConstructor
public class Manufacturer {
    // Properties
    private int id;
    private String name;
    private String country;
    private Date partnerSince;
}