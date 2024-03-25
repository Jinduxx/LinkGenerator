package com.example.linkgenerator.service;

import com.example.linkgenerator.dto.PaymentResponse;
import com.example.linkgenerator.model.InvoiceData;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentGatewayService {
    PaymentResponse initiatePayment(List<InvoiceData> invoiceData, BigDecimal totalAmount, String pin,String username);
}
