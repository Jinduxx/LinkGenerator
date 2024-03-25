package com.example.linkgenerator.service.ServiceImpl;

import com.example.linkgenerator.dto.PaymentDTO;
import com.example.linkgenerator.dto.PaymentResponse;
import com.example.linkgenerator.exception.CustomException;
import com.example.linkgenerator.model.InvoiceData;
import com.example.linkgenerator.model.Transaction;
import com.example.linkgenerator.model.User;
import com.example.linkgenerator.repo.TransactionRepo;
import com.example.linkgenerator.repo.UserRepo;
import com.example.linkgenerator.service.PaymentGatewayService;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {


    @Value("${suspense.account}")
    private String destinationAccount;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public PaymentResponse initiatePayment(List<InvoiceData> invoiceData, BigDecimal totalAmount, String pin,String username) {
        PaymentDTO paymentDTO;
        try {
            if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
                throw new CustomException("Invalid Amount", HttpStatus.BAD_REQUEST.value());
            }
            User user = userRepo.findByUsername(username).orElseThrow(()
                    -> new CustomException("Invalid username", HttpStatus.BAD_REQUEST.value()));
            if(!passwordEncoder.matches(pin,user.getPin())){
                throw new CustomException("Invalid pin",400);
            }
            String transId = UUID.randomUUID().toString();
            //do name enquiry
            //do limit check
            //do balance check

            paymentDTO = PaymentDTO.builder()
                            .destinationAccountNumber(destinationAccount)
                            .sourceAccountNumber(user.getAccountList().get(0).getAccountNumber())
                            .sourceAccountName("Suspense")
                            .amount(totalAmount)
                            .pin(pin)
                            .currency("NGN")
                            .BeneficiaryPhone("")
                            .narration("PRODUCT PAY "+transId)
                            .transactionId(transId)
                            .destinationBank("0040")
                            .createdAt(LocalDateTime.now())
                            .build();
            //Post payment


            Transaction transaction = Transaction.builder()
                    .transactionId(paymentDTO.getTransactionId())
                    .amount(paymentDTO.getAmount())
                    .BeneficiaryPhone(paymentDTO.getBeneficiaryPhone())
                    .createdAt(paymentDTO.getCreatedAt())
                    .currency(paymentDTO.getCurrency())
                    .destinationAccountName(paymentDTO.getDestinationAccountName())
                    .destinationBank(paymentDTO.getDestinationBank())
                    .pin(paymentDTO.getPin())
                    .destinationAccountNumber(paymentDTO.getDestinationAccountNumber())
                    .sourceAccountName(paymentDTO.getSourceAccountName())
                    .sourceAccountNumber(paymentDTO.getSourceAccountNumber())
                    .build();
            transaction.setStatus("PAID");

            transactionRepo.save(transaction);

            return PaymentResponse.builder()
                    .responseCode("00")
                    .responseMessage("Successful")
                    .transactionId(transId)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to initiate payment", HttpStatus.BAD_REQUEST.value());
        }
    }


}
