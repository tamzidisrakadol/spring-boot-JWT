package com.example.springboot.jwtAuthentication.Security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * JwtService class
 provides functionalities for generating and validating JWT tokens.
 */


@Component
public class JwtService {

    /**
     * Secret key used for signing and verifying JWT tokens. (Consider storing this securely in an environment variable)
     */
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * Generates a JWT token for the given username.
     *
     * @param userName Username of the user for whom the token is generated.
     * @return A String containing the generated JWT token.
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    /**
     * Creates a JWT token with the provided claims and username.
     *
     * @param claims
        Additional claims to be included in the token (optional).
     * @param userName Username of the user for whom the token is generated.
     * @return A String containing the generated JWT token.
     */

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token valid for 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Extracts the secret key from the base64 encoded SECRET String.
     *
     * @return A Key object representing the secret key used for signing and verifying tokens.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the
     provided JWT token.
     *
     * @param token The JWT token string.
     * @return The username extracted from the token subject claim.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Extracts the expiration date from
     the provided JWT token.
     *
     * @param token The JWT token string.
     * @return The Date object representing the expiration time of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    /**
     * Generic function to extract a specific claim from a JWT token using a provided claim resolver function.
     *
     * @param token The JWT token string.
     * @param claimsResolver A function that takes a Claims object and returns the desired claim value.
     * @param <T> The type of the claim value being extracted.
     * @return The extracted claim value of type T.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /**
     * Parses
     the provided JWT token and extracts all claims from it.
     *
     * @param token The JWT token string.
     * @return A Claims object containing all the claims present in the token.
     */

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Integer extractUserId(String token){
        return extractClaim(token,claims->(Integer) claims.get("id"));
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
