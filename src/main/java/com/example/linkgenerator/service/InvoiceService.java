package com.example.linkgenerator.service;

import com.example.linkgenerator.dto.CreateInvoiceRequest;
import com.example.linkgenerator.dto.GetInvoiceResponse;
import com.example.linkgenerator.dto.InvoiceLinkResponse;
import com.example.linkgenerator.model.InvoiceLink;

public interface InvoiceService {

    InvoiceLinkResponse createInvoiceLink(CreateInvoiceRequest invoice, String name);

    GetInvoiceResponse getInvoiceById(String uuid);
}
