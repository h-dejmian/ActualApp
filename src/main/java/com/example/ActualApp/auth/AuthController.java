package com.example.ActualApp.auth;

import com.example.ActualApp.auth.jwt.JwtTokenService;
import com.example.ActualApp.auth.user.AuthService;
import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.auth.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, AuthService authService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public UserDto login(@Valid @RequestBody JwtTokenRequest jwtTokenRequest, HttpServletResponse response) {
        String userName = jwtTokenRequest.userName();
//        String password = jwtTokenRequest.password();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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

        return new UserDto(user.getId(), userName);
    }

    @PostMapping("/register")
    public UserDto registerUser(@Valid @RequestBody UserRegistrationDto dto) {
        return authService.registerNewUser(dto);
    }
}
