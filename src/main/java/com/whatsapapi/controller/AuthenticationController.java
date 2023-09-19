package com.whatsapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapapi.dao.request.SignUpRequest;
import com.whatsapapi.dao.response.JwtAuthenticationResponse;
import com.whatsapapi.entities.User;
import com.whatsapapi.service.AuthenticationService;
import com.whatsapapi.service.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    
    @Autowired
    private JwtService jwtService;
    
//    @PostMapping("/signup")
//    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
//        return ResponseEntity.ok(authenticationService.signup(request));
//    }

//    @PostMapping("/signin")
//    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
//        return ResponseEntity.ok(authenticationService.signin(request));
//    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest registerRequest) {
        // Check if the username is already taken
      if (authenticationService.isUserExists(registerRequest.getUsername())) {
    	  return ResponseEntity.badRequest().body("Username is already taken");
      }
        User user = authenticationService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getName());
        
        System.out.println(user.toString());
        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Return the token in the response
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
