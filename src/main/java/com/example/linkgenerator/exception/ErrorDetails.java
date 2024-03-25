package com.example.linkgenerator.exception;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
