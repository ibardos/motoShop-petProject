package com.ibardos.motoShop.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of the UserDetails interface to act as a DTO for the ApplicationUser entity.
 * Instances of this class are meant to be used during Spring Security authentication processes.
 */
public class CustomUserDetails implements UserDetails {
    // Fields
    private final String username;
    private final String password;
    private final boolean isEnabled;
    @Getter
    private final Role role;
    private final List<SimpleGrantedAuthority> authorities;


    public CustomUserDetails(ApplicationUser applicationUser) {
        username = applicationUser.getUsername();
        password = applicationUser.getPassword();
        isEnabled = applicationUser.isEnabled();
        role = applicationUser.getRole();
        authorities = applicationUser.getRole().getAuthorities();
    }


    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isEnabled() { return isEnabled; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }


    // Currently, the following features are not planned to be implemented
    @Override
    public boolean isAccountNonExpired() { return true;}

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }
}
