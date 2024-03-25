package com.example.linkgenerator.exception;

public class CustomException extends RuntimeException {
    private final int responseCode;

    public CustomException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
