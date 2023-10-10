package com.example.ActualApp.auth;

import com.example.ActualApp.auth.jwt.JwtTokenService;
import com.example.ActualApp.auth.user.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public JwtTokenResponse login(@Valid @RequestBody JwtTokenRequest jwtTokenRequest) {
        var authentication = new UsernamePasswordAuthenticationToken(
                jwtTokenRequest.userName(), jwtTokenRequest.password()
        );

        authenticationManager.authenticate(authentication);

        return new JwtTokenResponse(jwtTokenService.generateToken(jwtTokenRequest.userName()));
    }

    @PostMapping("/register")
    public RegisteredUserDto registerUser(@Valid @RequestBody UserRegistrationDto dto) {
        return authService.registerNewUser(dto);
    }
}
