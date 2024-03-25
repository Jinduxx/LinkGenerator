package com.example.linkgenerator.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDTO {

    private String transactionId;
    private String destinationAccountName;
    private String sourceAccountName;
    private String destinationAccountNumber;
    private String sourceAccountNumber;
    private String BeneficiaryPhone;
    private String pin;
    private BigDecimal amount;
    private String narration;
    private String currency;
    private String destinationBank;
    private LocalDateTime createdAt;

}
