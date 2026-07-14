package com.venue.app.service;

import com.venue.app.model.dto.UserDTO;
import com.venue.app.model.entity.Users;
import com.venue.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO) {
        Users newUser = new Users();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Users savedUser = userRepository.save(newUser);

        UserDTO result = new UserDTO();
        result.setId(savedUser.getId());
        result.setEmail(savedUser.getEmail());
        return result;
    }

    public boolean deleteUser(Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        Users userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete != null && !userToDelete.getEmail().equals(username)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
