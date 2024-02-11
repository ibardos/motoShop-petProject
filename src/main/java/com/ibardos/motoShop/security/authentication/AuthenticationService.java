package com.ibardos.motoShop.security.authentication;

import com.ibardos.motoShop.security.jwt.JwtService;
import com.ibardos.motoShop.security.user.CustomUserDetails;
import com.ibardos.motoShop.security.user.ApplicationUser;
import com.ibardos.motoShop.security.user.CustomUserDetailsService;
import com.ibardos.motoShop.security.user.ApplicationUserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.sql.Date;

import lombok.RequiredArgsConstructor;

/**
 * Service class providing authentication related methods.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    // Injected dependencies
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;


    /**
     * Instantiates and saves a new ApplicationUser into DB during registration.
     * Generates a signed JWT token and sends it to the client embedded into an AuthenticationResponse.
     * @param registerRequest object from client containing registration related data.
     * @return AuthenticationResponse containing a signed JWT token.
     */
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .dateOfRegistration(new Date(System.currentTimeMillis()))
                .positionAtCompany(registerRequest.getPositionAtCompany())
                .enabled(true)
                .role(registerRequest.getRole())
                .build();

        applicationUserRepository.save(applicationUser);

        String jwtToken = jwtService.buildToken(new CustomUserDetails(applicationUser));

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    /**
     * Authenticates an existing ApplicationUser.
     * Generates a signed JWT token and sends it to the client embedded into an AuthenticationResponse.
     * @param loginRequest object from client containing authentication related data.
     * @return AuthenticationResponse containing a signed JWT token.
     */
    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        CustomUserDetails customUserDetailsFromDb = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        String jwtToken = jwtService.buildToken(customUserDetailsFromDb);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}
