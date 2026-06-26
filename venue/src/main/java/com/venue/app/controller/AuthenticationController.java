package com.venue.app.controller;

import com.venue.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * TODO: l'autenticazione tramite authService.login(email, password) funziona (vediamo nel service una cosina),
     *     ma manca la gestione dei token JWT. Senza token il frontend non ha alcuna
     *     informazione sull'utente, e nelle chiamate successive non è possibile
     *     verificare che la richiesta provenga da un utente effettivamente loggato.
     *
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = authService.login(email, password);

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
