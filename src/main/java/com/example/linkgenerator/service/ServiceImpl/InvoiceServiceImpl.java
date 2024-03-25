package com.example.linkgenerator.service.ServiceImpl;

import com.example.linkgenerator.dto.CreateInvoiceRequest;
import com.example.linkgenerator.dto.GetInvoiceResponse;
import com.example.linkgenerator.dto.InvoiceLinkResponse;
import com.example.linkgenerator.dto.ProductInfo;
import com.example.linkgenerator.exception.CustomException;
import com.example.linkgenerator.model.InvoiceData;
import com.example.linkgenerator.model.InvoiceLink;
import com.example.linkgenerator.repo.LinkRepo;
import com.example.linkgenerator.repo.UserRepo;
import com.example.linkgenerator.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private LinkRepo invoiceRepository;

    @Autowired
    private UserRepo userRepo;

    private static Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Override
    public InvoiceLinkResponse createInvoiceLink(CreateInvoiceRequest invoice, String name) {

        logger.info("create invoice link request :  {},  ===== {}",invoice,name);
        InvoiceLink invoiceLink = new InvoiceLink();
        InvoiceLinkResponse response;
        String uuid = UUID.randomUUID().toString();
        String url = "http://localhost:8083/view-invoice/"+uuid;
        try{
            List<InvoiceData> invoiceDataList = new ArrayList<>();
            invoiceLink = InvoiceLink.builder()
                    .date(LocalDateTime.now())
                    .name(name)
                    .link(uuid)
                    .username(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                    .build();
            for(ProductInfo productInfo : invoice.getProductInfoList()){
                invoiceDataList.add(
                        InvoiceData.builder()
                                .amount(productInfo.getAmount())
                                .product(productInfo.getProduct())
                                .quantity(productInfo.getQuantity())
                                .invoiceLink(invoiceLink)
                                .build()
                );
            }
            invoiceLink.setInvoiceData(invoiceDataList);
            invoiceLink.setTotalAmount(calculateTotalAmount(invoiceDataList));

            logger.info("about to save invoice link request :  {}  to the DB",invoiceLink);
            invoiceRepository.save(invoiceLink);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 400);
        }
        return InvoiceLinkResponse.builder()
                .link(url)
                .responseCode("00")
                .responseMessage("Successful")
                .build();
    }

    private static BigDecimal calculateTotalAmount(List<InvoiceData> data){

        BigDecimal sum = BigDecimal.ZERO;

        for (InvoiceData invoiceData : data) {
            sum = sum.add(invoiceData.getAmount());
        }

        return sum;
    }

    @Override
    public GetInvoiceResponse getInvoiceById(String uuid) {
        InvoiceLink resp;
        GetInvoiceResponse response;
        try {
            logger.info("about getting invoice  {} from the BD ",uuid);
            resp = invoiceRepository.findByLink(uuid).orElseThrow(()
                    -> new CustomException("Invalid Url", 400));


            return GetInvoiceResponse.builder()
                    .name(resp.getName())
                    .date(resp.getDate())
                    .username(resp.getUsername())
                    .invoiceData(resp.getInvoiceData())
                    .totalAmount(resp.getTotalAmount())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(),400);
        }
    }



//    public String generatePaymentLink(Long invoiceId) {
//        Invoice invoice = invoiceRepository.findById(invoiceId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid invoice id: " + invoiceId));
//
//        // Set the link generation time and expiry duration (e.g., 30 minutes in milliseconds)
//        invoice.setLinkGeneratedAt(LocalDateTime.now());
//        invoice.setLinkExpiryDuration(1800000L); // 30 minutes
//
//        invoiceRepository.save(invoice);
//
//        return "https://example.com/pay-invoice?id=" + invoice.getId();
//    }
}
