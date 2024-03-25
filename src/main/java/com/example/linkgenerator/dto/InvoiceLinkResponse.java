package com.example.linkgenerator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceLinkResponse {

    private String link;
    private String responseMessage;
    private String responseCode;
}
