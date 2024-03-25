package com.example.linkgenerator.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RegistrationRequest {

    private String username;
    private String fullName;
    private String email;
    //hash this
    private String password;
    @Valid
    @Size(min = 4, max = 4, message = "pin must be 4 digits")
    private String pin;
    private List<Account> accountNumber;
}
