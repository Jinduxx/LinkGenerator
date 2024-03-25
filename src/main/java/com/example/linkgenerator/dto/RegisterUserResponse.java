package com.example.linkgenerator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserResponse {

    private String responseMessage;
    private String responseCode;
    private boolean isSuccessful;
}
