package com.example.linkgenerator.controller;

import com.example.linkgenerator.dto.CreateInvoiceRequest;
import com.example.linkgenerator.dto.InvoiceLinkResponse;
import com.example.linkgenerator.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create-link")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<InvoiceLinkResponse> createInvoice(@Valid @RequestBody CreateInvoiceRequest invoice, @RequestParam String name) {
        InvoiceLinkResponse createdInvoice = invoiceService.createInvoiceLink(invoice,name);
        return ResponseEntity.ok(createdInvoice);
    }

}
