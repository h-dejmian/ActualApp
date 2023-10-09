package com.example.ActualApp.auth.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    public RegisteredUserDto registerNewUser(UserRegistrationDto userRegistrationDto) {
//
//        validateUserEmail(userRegistrationDto);
//
//        User newUser = new User(
//                userRegistrationDto.login(),
//                passwordEncoder.encode(userRegistrationDto.password()));
//        Role userRole = roleRepository.findByName(DEVELOPER_READ)
//                .orElseThrow(() -> new RuntimeException("Expected user role in database"));
//
//        newUser.addRole(userRole);
//        userRole.assignToUser(newUser);
//
//        User savedUser = userRepository.save(newUser);
//
//        return new RegisteredUserDto(savedUser.getId(), savedUser.getEmail());
//    }

    // TODO: extract to separate class
//    private void validateUserEmail(UserRegistrationDto userRegistrationDto) {
//        userRepository.findByEmail(userRegistrationDto.login()).ifPresent(
//                u -> { throw new UserAlreadyExistsException(userRegistrationDto.login()); }
//        );
//    }
}
