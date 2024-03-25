package com.example.linkgenerator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;
    private String expireTime;
    private String fullName;
    private String userName;
}
