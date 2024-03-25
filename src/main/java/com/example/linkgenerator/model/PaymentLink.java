package com.example.linkgenerator.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(indexes = {
        @Index(columnList = "link")})
public class PaymentLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;
    private String data;
    private LocalDateTime linkGeneratedAt;
    private Long linkExpiryDuration;
}
