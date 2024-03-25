package com.example.linkgenerator.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateInvoiceRequest {

    List<ProductInfo> productInfoList;
}
