package com.ibardos.motoShop.security.authentication;

import com.ibardos.motoShop.security.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class to send registration related data from client to back-end server.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    // Properties
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String positionAtCompany;
    private Role role;
}
