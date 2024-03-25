package com.example.linkgenerator.service.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.linkgenerator.dto.CreateInvoiceRequest;
import com.example.linkgenerator.dto.GetInvoiceResponse;
import com.example.linkgenerator.exception.CustomException;
import com.example.linkgenerator.model.InvoiceLink;
import com.example.linkgenerator.repo.LinkRepo;
import com.example.linkgenerator.repo.UserRepo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InvoiceServiceImpl.class})
@ExtendWith(SpringExtension.class)
class InvoiceServiceImplTest {
    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    @MockBean
    private LinkRepo linkRepo;

    @MockBean
    private UserRepo userRepo;

    @Test
    void testCreateInvoiceLink() {
        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
        createInvoiceRequest.setProductInfoList(new ArrayList<>());
        assertThrows(CustomException.class, () -> this.invoiceServiceImpl.createInvoiceLink(createInvoiceRequest, "Name"));
    }

    @Test
    void testGetInvoiceById() {
        InvoiceLink invoiceLink = new InvoiceLink();
        invoiceLink.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        invoiceLink.setId(123L);
        invoiceLink.setInvoiceData(new ArrayList<>());
        invoiceLink.setLink("Link");
        invoiceLink.setName("Name");
        invoiceLink.setTotalAmount(BigDecimal.valueOf(1L));
        invoiceLink.setUsername("janedoe");
        Optional<InvoiceLink> ofResult = Optional.of(invoiceLink);
        when(this.linkRepo.findByLink((String) any())).thenReturn(ofResult);
        GetInvoiceResponse actualInvoiceById = this.invoiceServiceImpl
                .getInvoiceById("01234567-89AB-CDEF-FEDC-BA9876543210");
        assertEquals("GetInvoiceResponse(invoiceData=[], name=Name, uuid=null, username=janedoe, pin=null, totalAmount=1,"
                + " date=0001-01-01T01:01)", actualInvoiceById.toString());
        assertEquals("01:01", actualInvoiceById.getDate().toLocalTime().toString());
        assertNull(actualInvoiceById.getUuid());
        assertEquals("janedoe", actualInvoiceById.getUsername());
        assertNull(actualInvoiceById.getPin());
        assertTrue(actualInvoiceById.getInvoiceData().isEmpty());
        assertEquals("Name", actualInvoiceById.getName());
        assertEquals("1", actualInvoiceById.getTotalAmount().toString());
        verify(this.linkRepo).findByLink((String) any());
    }

    @Test
    void testGetInvoiceById2() {
        when(this.linkRepo.findByLink((String) any())).thenThrow(new CustomException("An error occurred", 0));
        assertThrows(CustomException.class,
                () -> this.invoiceServiceImpl.getInvoiceById("01234567-89AB-CDEF-FEDC-BA9876543210"));
        verify(this.linkRepo).findByLink((String) any());
    }

    @Test
    void testGetInvoiceById3() {
        when(this.linkRepo.findByLink((String) any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class,
                () -> this.invoiceServiceImpl.getInvoiceById("01234567-89AB-CDEF-FEDC-BA9876543210"));
        verify(this.linkRepo).findByLink((String) any());
    }
}

