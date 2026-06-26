package com.venue.app.service;

import com.venue.app.model.entity.Users;
import com.venue.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    /*Todo: qua metto un po'il pelo sull'uovo per dirti come andrebbe fatto,
           BCryptPasswordEncoder è una classe di Spring Security che fornisce un modo sicuro per gestire le password,
           per questo la metterei come configuration, per esempio:
           @Configuration
               public class SecurityConfig {
                @Bean
                public PasswordEncoder passwordEncoder() {
                    return new BCryptPasswordEncoder();
                }
            }
           e la inietterei come dipendenza, invece di istanziarla direttamente nel costruttore.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean login(String email, String rawPassword) {
        Optional<Users> userOpt = userRepository.findByEmail(email);/*Todo: magari in questo caso lanciare proprio
                                                                       un'eccezione se non trova l'utente,
                                                                        così da gestire meglio il flusso di login,
                                                                         invece di ritornare false.*/
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }

        return false;
    }
}