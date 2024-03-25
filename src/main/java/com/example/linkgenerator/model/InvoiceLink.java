package com.example.linkgenerator.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(columnList = "link")})
public class InvoiceLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;
    @OneToMany(mappedBy = "invoiceLink", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvoiceData> invoiceData;
    private LocalDateTime date;
    private BigDecimal totalAmount;
    private String name;
    private String username;

}
