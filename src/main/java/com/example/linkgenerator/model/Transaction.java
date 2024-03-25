package com.example.linkgenerator.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(indexes = {
        @Index(columnList = "transactionId")})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private String status;
}
