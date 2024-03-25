package com.example.linkgenerator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String responseCode;
    private String responseMessage;
    private String transactionId;

}
