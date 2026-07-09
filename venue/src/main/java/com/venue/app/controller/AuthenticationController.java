package com.venue.app.controller;

import com.venue.app.model.dto.LoginRequest;
import com.venue.app.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<String> token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return token.map(t -> {
            String jsonResponse = "{\"token\": \"" + t + "\"}";
            return ResponseEntity.ok().body(jsonResponse);
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
    }
}
