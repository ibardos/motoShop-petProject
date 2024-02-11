package com.ibardos.motoShop.security.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Model class, representing a single Permission in DB, which then should be connected to a Role.
 * The sum of existing Permissions in a Role determines the level of authorization for an ApplicationUser.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Permission {
    // Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
