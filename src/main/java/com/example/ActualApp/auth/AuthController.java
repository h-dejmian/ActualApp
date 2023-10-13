package com.example.ActualApp.auth;

import com.example.ActualApp.auth.jwt.JwtTokenService;
import com.example.ActualApp.auth.user.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtTokenResponse login(@Valid @RequestBody JwtTokenRequest jwtTokenRequest, HttpServletResponse response) {
        var authentication = new UsernamePasswordAuthenticationToken(
                jwtTokenRequest.userName(), jwtTokenRequest.password()
        );

        authenticationManager.authenticate(authentication);

        String token = jwtTokenService.generateToken(jwtTokenRequest.userName());

        Cookie cookie = new Cookie("jwt-token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");

        response.addCookie(cookie);


        return new JwtTokenResponse(token);
    }

    @PostMapping("/register")
    public RegisteredUserDto registerUser(@Valid @RequestBody UserRegistrationDto dto) {
        return authService.registerNewUser(dto);
    }
}
