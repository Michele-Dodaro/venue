package com.venue.app.service;

import com.venue.app.model.entity.Users;
import com.venue.app.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Optional<String> login(String email, String rawPassword) {



        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            UserDetails userDetails = new User(user.getEmail(), user.getPassword(), new ArrayList<>());
            return Optional.of(jwtService.generateToken(userDetails));
        }

        return Optional.empty();
    }
}
