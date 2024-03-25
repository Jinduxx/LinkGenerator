package com.example.linkgenerator.controller.mvcController;

import com.example.linkgenerator.dto.GetInvoiceResponse;
import com.example.linkgenerator.dto.PaymentResponse;
import com.example.linkgenerator.model.InvoiceData;
import com.example.linkgenerator.model.InvoiceLink;
import com.example.linkgenerator.service.InvoiceService;
import com.example.linkgenerator.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @GetMapping("/view-invoice/{uuid}")
    public String viewInvoice(@PathVariable("uuid") String invoiceId, Model model, HttpSession session) {
        GetInvoiceResponse invoice = invoiceService.getInvoiceById(invoiceId);
        session.setAttribute("invoiceDataList", invoice.getInvoiceData());
        model.addAttribute("invoiceData", invoice);
        model.addAttribute("name", invoice.getName());
        model.addAttribute("UUID", invoice.getUuid());
        model.addAttribute("token", invoice.getUuid());
        model.addAttribute("totalAmount", invoice.getTotalAmount());
        return "invoice-details";
    }

    @PostMapping("/process-payment")
    public String processPayment(HttpServletRequest request, @ModelAttribute("invoiceData") GetInvoiceResponse invoiceData,
                                 Model model) {
        List<InvoiceData> invoiceDataList = (List<InvoiceData>) request.getSession().getAttribute("invoiceDataList");
        PaymentResponse paymentId = paymentGatewayService.initiatePayment(invoiceDataList, invoiceData.getTotalAmount(), invoiceData.getPin(),invoiceData.getUsername());

        model.addAttribute("paymentData", paymentId);
        model.addAttribute("invoiceData", invoiceDataList);
        model.addAttribute("name", invoiceData.getName());
        model.addAttribute("totalAmount", invoiceData.getTotalAmount());
        return "payment";
    }
}
