package com.example.linkgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ip", nullable = false)
    private String ip;
    @Column(name = "request", nullable = true)
    private String request;
    @Column(nullable = false)
    private String accessedURL;
    private LocalDateTime date;
    @Column(nullable = false)
    private String username;
}
