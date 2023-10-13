package com.example.ActualApp.auth.user;

import com.example.ActualApp.auth.RegisteredUserDto;
import com.example.ActualApp.auth.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.ActualApp.auth.config.SpringSecurityConfig.ACTIVITIES_READ;
import static com.example.ActualApp.auth.config.SpringSecurityConfig.ACTIVITIES_WRITE;

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

    public RegisteredUserDto registerNewUser(UserRegistrationDto userRegistrationDto) {

//        validateUserEmail(userRegistrationDto);

        User newUser = new User(
                userRegistrationDto.login(),
                passwordEncoder.encode(userRegistrationDto.password()));
        Role userRole = roleRepository.findByName(ACTIVITIES_READ)
                .orElseThrow(() -> new RuntimeException("Expected user role in database"));
        Role userRole2 = roleRepository.findByName(ACTIVITIES_WRITE)
                .orElseThrow(() -> new RuntimeException("Expected user role in database"));

        newUser.addRole(userRole);
        newUser.addRole(userRole2);
        userRole.assignToUser(newUser);

        User savedUser = userRepository.save(newUser);

        return new RegisteredUserDto(savedUser.getId(), savedUser.getUserName());
    }

//    private void validateUserEmail(UserRegistrationDto userRegistrationDto) {
//        userRepository.findByUserName(userRegistrationDto.login()).ifPresent(
//                u -> { throw new UserAlreadyExistsException(userRegistrationDto.login()); }
//        );
//    }
}
