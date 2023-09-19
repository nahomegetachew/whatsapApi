package com.whatsapapi.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whatsapapi.dao.request.SignUpRequest;
import com.whatsapapi.dao.request.SigninRequest;
import com.whatsapapi.dao.response.JwtAuthenticationResponse;
import com.whatsapapi.entities.Role;
import com.whatsapapi.entities.User;
import com.whatsapapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    

    public User registerUser(String username, String password, String name) {
        // Check if the username or email is already taken
    	Optional<User> existingUser = userRepository.findByUsername(username);
        if (!existingUser.isEmpty()){
            throw new RuntimeException("Username already taken " + username + " " + password + " " + name + " existing :" + existingUser.toString() );
        }

        // Create a new user entity
        User user = new User();
        user.setUsername(username);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        // Save the user to the database
        return userRepository.save(user);
    }
    
    public boolean isUserExists(String username) {
    	Optional<User> existingUser = userRepository.findByUsername(username);
        return !existingUser.isEmpty();
    }

    public User loginUser(String username, String password) {
        // Find the user by username
        Optional<User> user = userRepository.findByUsername(username);

        // Check if the user exists and verify the password
        if (user != null && passwordEncoder.matches(password, user.orElseThrow().getPassword())) {
            return user.orElseThrow();
        }
		return null;
    }
}
