package com.example.linkgenerator.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false, referencedColumnName = "username")
    @JsonManagedReference
    private User user;
    private String accountNumber;
    private LocalDateTime createdOn = LocalDateTime.now();

    // make not nullable
    private String fullName;

}
