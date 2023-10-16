package com.example.ActualApp.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtRequestFilter  extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtTokenService jwtTokenService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if(request.getCookies() != null) {
            Cookie cookie = request.getCookies()[0];

            try {
                String userName = jwtTokenService.getUserNameFromToken(cookie.getValue());
                authenticateUser(request, cookie.getValue(), userName);

            } catch (IllegalArgumentException ex) {
                log.error("Unable to parse JWT token", ex);
            } catch (ExpiredJwtException ex) {
                log.warn("JWT token {} is expired", cookie.getValue());
            }
        }

        if (authHeader != null && authHeader.startsWith(BEARER)) {
            String jwtToken = authHeader.substring(BEARER.length() + 1);

            try {
                String userName = jwtTokenService.getUserNameFromToken(jwtToken);
                authenticateUser(request, jwtToken, userName);

            } catch (IllegalArgumentException ex) {
                log.error("Unable to parse JWT token", ex);
            } catch (ExpiredJwtException ex) {
                log.warn("JWT token {} is expired", jwtToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(HttpServletRequest request, String jwtToken, String userName) {
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtTokenService.validateToken(jwtToken, userDetails)) {
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }
}
