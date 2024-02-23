package com.ibardos.motoShop.security.jwt;

import com.ibardos.motoShop.security.user.CustomUserDetails;
import com.ibardos.motoShop.security.user.CustomUserDetailsService;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * Component class to establish a custom JWT authentication filter, filtering every request once.
 * The composed custom filter is able to maintain stateless session handling by constantly checking signed JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Injected dependencies
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;


    /**
     * Custom HTTP security filter implementation for stateless JWT session handling.
     *
     * @param request     as the incoming HTTP servlet request form the client.
     * @param response    as the outgoing HTTP servlet response to the client.
     * @param filterChain managed by Spring Security, representing the next HTTP security filter in line.
     * @throws ServletException may be thrown by filterChain during filter.
     * @throws IOException      may be thrown by filterChain during filter.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Check if HTTP request has JWT token, if not, terminate and call the next filter in filterChain
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authorizationHeader.substring(7);
        String usernameFromJwtToken = "";

        // If Exception happens while processing JWT token, terminate and call the next filter in filterChain
        try {
            usernameFromJwtToken = jwtService.extractUsername(jwtToken);
        } catch (MalformedJwtException | SignatureException malformedJwtTokenException) {
            System.out.println("Malformed or invalid JWT token: " + "\"" + jwtToken + "\"");
            filterChain.doFilter(request, response);
            return;
        }

        // Check if username is present in JWT token, and user is not authenticated already
        if (usernameFromJwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails customUserDetailsFromDb = customUserDetailsService.loadUserByUsername(usernameFromJwtToken);

            // Check if JWT token is valid, and the corresponding user is enabled,
            // if so, authenticate the user by registering its authenticationToken to the SecurityContextHolder
            if (jwtService.isTokenValid(jwtToken, customUserDetailsFromDb) && customUserDetailsFromDb.isEnabled()) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetailsFromDb, null, customUserDetailsFromDb.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
