package com.example.linkgenerator.service.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.linkgenerator.exception.CustomException;
import com.example.linkgenerator.model.InvoiceData;
import com.example.linkgenerator.model.User;
import com.example.linkgenerator.repo.TransactionRepo;
import com.example.linkgenerator.repo.UserRepo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PaymentGatewayServiceImpl.class, String.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class PaymentGatewayServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private PaymentGatewayServiceImpl paymentGatewayServiceImpl;

    @MockBean
    private TransactionRepo transactionRepo;

    @MockBean
    private UserRepo userRepo;

    @Test
    void testInitiatePayment() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(ofResult);
        ArrayList<InvoiceData> invoiceData = new ArrayList<>();
        assertThrows(CustomException.class,
                () -> this.paymentGatewayServiceImpl.initiatePayment(invoiceData, BigDecimal.valueOf(1L), "Pin", "janedoe"));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testInitiatePayment2() {
        when(this.userRepo.findByUsername((String) any())).thenThrow(new CustomException("An error occurred", 0));
        ArrayList<InvoiceData> invoiceData = new ArrayList<>();
        assertThrows(CustomException.class,
                () -> this.paymentGatewayServiceImpl.initiatePayment(invoiceData, BigDecimal.valueOf(1L), "Pin", "janedoe"));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testInitiatePayment3() {
        when(this.userRepo.findByUsername((String) any())).thenReturn(Optional.empty());
        ArrayList<InvoiceData> invoiceData = new ArrayList<>();
        assertThrows(CustomException.class,
                () -> this.paymentGatewayServiceImpl.initiatePayment(invoiceData, BigDecimal.valueOf(1L), "Pin", "janedoe"));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testInitiatePayment4() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(ofResult);
        ArrayList<InvoiceData> invoiceData = new ArrayList<>();
        assertThrows(CustomException.class,
                () -> this.paymentGatewayServiceImpl.initiatePayment(invoiceData, BigDecimal.valueOf(0L), "Pin", "janedoe"));
    }

    @Test
    void testInitiatePayment5() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(ofResult);
        assertThrows(CustomException.class,
                () -> this.paymentGatewayServiceImpl.initiatePayment(new ArrayList<>(), null, "Pin", "janedoe"));
    }
}

