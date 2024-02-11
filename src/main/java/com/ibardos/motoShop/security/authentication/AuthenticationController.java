package com.ibardos.motoShop.security.authentication;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * REST API controller for handling authentication related HTTP requests.
 */
@RestController
@RequestMapping("authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    // Injected dependencies
    private final AuthenticationService authenticationService;


    /**
     * Registers a new ApplicationUser.
     * @param registerRequest object from client containing registration related data.
     * @return AuthenticationResponse containing a signed JWT token.
     */
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    /**
     * Authenticates an existing ApplicationUser.
     * @param loginRequest object from client containing authentication related data.
     * @return AuthenticationResponse containing a signed JWT token.
     */
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }


}
