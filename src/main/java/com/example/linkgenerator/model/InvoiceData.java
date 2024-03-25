package com.example.linkgenerator.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String product;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link", nullable = false, referencedColumnName = "link")
    @JsonManagedReference
    @ToString.Exclude
    private InvoiceLink invoiceLink;
}
