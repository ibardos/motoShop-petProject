package com.ibardos.motoShop.security.user;

import jakarta.persistence.*;

import lombok.*;

import java.sql.Date;

/**
 * Model class, representing an ApplicationUser entity in DB, for authentication purposes.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {
    // Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfRegistration;
    private String positionAtCompany;
    // Representing the status of a particular user. Users are shouldn't be deleted, only enabled or disabled.
    private boolean enabled;
    @ManyToOne
    private Role role;
}
