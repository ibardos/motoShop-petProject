package com.ibardos.motoShop.security.jwt;

import com.ibardos.motoShop.security.user.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for JWT token related methods.
 * Methods are used during authentication for JWT token generation, validation and manipulation.
 */
@Service
public class JwtService {
    // Secret values injected from application.yml
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;


    /**
     * Generates JWT token for an ApplicationUser.
     * @param customUserDetails object, acting as a DTO for the authenticated ApplicationUser,
     * which transfers the necessary credentials for JWT token creation.
     * @return String representation of the newly created JWT token.
     */
    public String buildToken(CustomUserDetails customUserDetails) {
        // Add granted authorities to JWT token to help role and also permission based conditional rendering at front-end
        Map<String, Object> authorities = new HashMap<>(Map.of("authorities", customUserDetails.getRole().getAuthorities()));

        return buildToken(authorities, customUserDetails);
    }

    /**
     * Extracts username from JWT token.
     * @param jwtToken coming from client.
     * @return String representation of username.
     */
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Checks if JWT token is valid: username from JWT equals to user in DB, and JWT is not expired.
     * @param jwtToken String representation of a JWT token to be checked.
     * @param customUserDetails object, acting as a DTO for the authenticated ApplicationUser,
     * to which the JWT token is claimed to belong.
     * @return true if JWT token is valid for the user AND is not expired, otherwise false.
     */
    public boolean isTokenValid(String jwtToken, CustomUserDetails customUserDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(customUserDetails.getUsername())) && !isTokenExpired(jwtToken);
    }


    // Private helper methods
    /**
     * Builds a signed JWT token utilizing the builder pattern of Jwts.builder().
     * @param extraClaims additional claims added to JWT token is necessary.
     * @param customUserDetails object, acting as a DTO for the authenticated ApplicationUser,
     * to which the JWT token will be built.
     * @return String representation of the newly built JWT token.
     */
    private String buildToken(Map<String, Object> extraClaims, CustomUserDetails customUserDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(customUserDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Extracts all the claims that are present in a JWT token.
     * @param jwtToken String representation of a JWT token from where the claims should be extracted.
     * @return Claims object containing the extracted claims.
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    /**
     * Creates a secret key value of SecretKey type, which can be used to sign a JWT token.
     * @return the created SecretKey object.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts a single claim from a JWT token.
     * @param jwtToken String representation of a JWT token from where the claim should be extracted.
     * @param claimResolver A function that resolves the desired claim from the JWT's Claims object.
     * The function takes a Claims object and returns the extracted claim.
     * @param <T> The type of the extracted claim.
     * @return The extracted claim of type T from the provided JWT token.
     */
    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    /**
     * Checks if JWT token has expired.
     * @param jwtToken String representation of a JWT token to be checked.
     * @return true if JWT token has expired, otherwise false.
     */
    private boolean isTokenExpired(String jwtToken) {
        Date jwtTokenExpiration = extractClaim(jwtToken, Claims::getExpiration);
        return jwtTokenExpiration.before(new Date());
    }
}
