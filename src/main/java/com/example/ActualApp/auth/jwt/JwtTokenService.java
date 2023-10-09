package com.example.ActualApp.auth.jwt;

import com.example.ActualApp.auth.config.AuthConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtTokenService {
    private final AuthConfigProperties authConfigProperties;

    public JwtTokenService(AuthConfigProperties authConfigProperties) {
        this.authConfigProperties = authConfigProperties;
    }

    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMinutes(authConfigProperties.validity());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(transformLDTToDate(now))
                .setExpiration(transformLDTToDate(expiration))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        String userName = getUserNameFromToken(jwtToken);
        boolean isExpired = getExpirationFromToken(jwtToken).before(new Date());

        return userDetails.getUsername().equals(userName) && !isExpired;
    }

    public String getUserNameFromToken(String jwtToken) {
        return getClaims(jwtToken).getSubject();
    }

    private Claims getClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Date getExpirationFromToken(String jwtToken) {
        return getClaims(jwtToken).getExpiration();
    }

    private Key getKey() {
        byte[] keyBytes = authConfigProperties.secret().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date transformLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
}
