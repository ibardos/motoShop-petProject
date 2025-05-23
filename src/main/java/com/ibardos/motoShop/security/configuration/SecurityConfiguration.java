package com.ibardos.motoShop.security.configuration;

import com.ibardos.motoShop.security.jwt.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class to determine the overall security level and configuration of the application.
 * Contains security configuration for routes, API endpoints, passwordEncoder, authenticationProvider, authenticationManager.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    // Injected dependencies
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    /**
     * Creates a custom HTTP SecurityFilterChain bean to set the overall security configuration of the application.
     * @param http object containing web based security configurations, that can be altered during for customization.
     * @return the modified http object containing the defined web based security configurations.
     * @throws Exception that may be thrown by HTTP web security related configuration methods.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("authentication/login", "/error")
                                .permitAll()

                                .requestMatchers("authentication/register").hasRole("Admin")

                                .requestMatchers("service/manufacturer/add").hasAuthority("PERMISSION_Create")
                                .requestMatchers("service/manufacturer/get/**").hasAuthority("PERMISSION_Read")
                                .requestMatchers("service/manufacturer/update").hasAuthority("PERMISSION_Update")
                                .requestMatchers("service/manufacturer/delete/**").hasAuthority("PERMISSION_Delete")

                                .requestMatchers("service/motorcycle/model/add").hasAuthority("PERMISSION_Create")
                                .requestMatchers("service/motorcycle/model/get/**").hasAuthority("PERMISSION_Read")
                                .requestMatchers("service/motorcycle/model/update").hasAuthority("PERMISSION_Update")
                                .requestMatchers("service/motorcycle/model/delete/**").hasAuthority("PERMISSION_Delete")

                                .requestMatchers("service/motorcycle/stock/add").hasAuthority("PERMISSION_Create")
                                .requestMatchers("service/motorcycle/stock/get/**").hasAuthority("PERMISSION_Read")
                                .requestMatchers("service/motorcycle/stock/update").hasAuthority("PERMISSION_Update")
                                .requestMatchers("service/motorcycle/stock/delete/**").hasAuthority("PERMISSION_Delete")

                                .requestMatchers("service/customer/add").hasAuthority("PERMISSION_Create")
                                .requestMatchers("service/customer/get/**").hasAuthority("PERMISSION_Read")
                                .requestMatchers("service/customer/update").hasAuthority("PERMISSION_Update")
                                .requestMatchers("service/customer/delete/**").hasAuthority("PERMISSION_Delete")

                                .requestMatchers("service/order/add").hasAuthority("PERMISSION_Create")
                                .requestMatchers("service/order/get/**").hasAuthority("PERMISSION_Read")
                                .requestMatchers("service/order/update").hasAuthority("PERMISSION_Update")
                                .requestMatchers("service/order/delete/**").hasAuthority("PERMISSION_Delete")

                                .anyRequest()
                                .authenticated()

                )
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
