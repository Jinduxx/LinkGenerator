package com.example.linkgenerator.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RegistrationRequest {

    private String username;
    private String fullName;
    @Email
    private String email;
    @Size(min = 8, message = "password must not be less than 8")
    private String password;
    @Valid
    @Size(min = 4, max = 4, message = "pin must be 4 digits")
    private String pin;
    private List<Account> accountNumber;
}
