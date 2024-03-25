package com.example.linkgenerator.controller;

import com.example.linkgenerator.dto.AuthRequest;
import com.example.linkgenerator.dto.AuthResponse;
import com.example.linkgenerator.dto.RegisterUserResponse;
import com.example.linkgenerator.dto.RegistrationRequest;
import com.example.linkgenerator.model.User;
import com.example.linkgenerator.security.JwtTokenProvider;
import com.example.linkgenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/create")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }
}
