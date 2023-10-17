package com.example.ActualApp.auth.user;

import com.example.ActualApp.auth.UserDto;
import com.example.ActualApp.auth.UserRegistrationDto;
import com.example.ActualApp.auth.user.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.ActualApp.auth.config.SpringSecurityConfig.USER;
import static com.example.ActualApp.auth.config.SpringSecurityConfig.ADMIN;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto registerNewUser(UserRegistrationDto userRegistrationDto) {

        validateUserLogin(userRegistrationDto);

        User newUser = new User(
                userRegistrationDto.login(),
                passwordEncoder.encode(userRegistrationDto.password()));

        Role userRole = roleRepository.findByName(USER)
                .orElseThrow(() -> new RuntimeException("Expected user role in database"));

        newUser.addRole(userRole);
        userRole.assignToUser(newUser);

        User savedUser = userRepository.save(newUser);

        return new UserDto(savedUser.getId(), savedUser.getUserName());
    }

    private void validateUserLogin(UserRegistrationDto userRegistrationDto) {
        userRepository.findByUserName(userRegistrationDto.login()).ifPresent(
                u -> { throw new UserAlreadyExistsException(userRegistrationDto.login()); }
        );
    }
}
