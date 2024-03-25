package com.example.linkgenerator.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Data
public class Account {

    @Valid
    @Size(min = 10, max = 10, message = "Account number must be 10 digits")
    private String accountNumber;
}
