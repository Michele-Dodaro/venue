package com.venue.app.service;

import com.venue.app.model.entity.Users;
import com.venue.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean login(String email, String rawPassword) {
        Optional<Users> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }

        return false;
    }
}