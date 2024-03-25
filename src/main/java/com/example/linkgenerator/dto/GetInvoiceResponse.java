package com.example.linkgenerator.dto;

import com.example.linkgenerator.model.InvoiceData;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class GetInvoiceResponse {

    private List<InvoiceData> invoiceData;
    private String name;
    private String uuid;
    private String username;
    private String pin;
    private BigDecimal totalAmount;
    private LocalDateTime date;
}
