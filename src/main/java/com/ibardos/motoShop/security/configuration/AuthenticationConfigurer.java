package com.ibardos.motoShop.security.configuration;

import com.ibardos.motoShop.security.user.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfigurer {
    // Injected dependencies
    private final CustomUserDetailsService customUserDetailsService;


    /**
     * Creates an AuthenticationProvider bean to be used during user authentication processes.
     * This bean establishes how user authentication is handled within the application.
     * @return a customized AuthenticationProvider object, capable of doing database-backed user authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Creates an AuthenticationManager bean to be used during user authentication processes.
     * This bean does the delegation of the authentication process to one or more AuthenticationProvider.
     * @param authenticationConfiguration object from Spring Security used to configure an AuthenticationManager.
     * @return an AuthenticationManager object.
     * @throws Exception that may be thrown while retrieving the AuthenticationManager bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a PasswordEncoder bean to be used during user authentication processes.
     * This bean is used during password encoding and decoding.
     * @return BCryptPasswordEncoder as an implementation of the PasswordEncoder interface from Spring Security.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
