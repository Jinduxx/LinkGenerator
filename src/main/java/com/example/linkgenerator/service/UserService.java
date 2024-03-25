package com.example.linkgenerator.service;

import com.example.linkgenerator.dto.AuthRequest;
import com.example.linkgenerator.dto.AuthResponse;
import com.example.linkgenerator.dto.RegisterUserResponse;
import com.example.linkgenerator.dto.RegistrationRequest;
import com.example.linkgenerator.model.User;

public interface UserService {
    AuthResponse authenticate(AuthRequest request);
    RegisterUserResponse registerUser(RegistrationRequest request);
    User getUserByUsername(String username);
}
