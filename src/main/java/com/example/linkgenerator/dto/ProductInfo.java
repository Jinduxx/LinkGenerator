package com.example.linkgenerator.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    private String product;
    private int quantity;

    private BigDecimal amount;
}
