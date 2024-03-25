package com.example.linkgenerator.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(indexes = {
        @Index(columnList = "username")})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //make unique and not nullable
    private String username;

    // make not nullable
    private String fullName;
    private String email;
    //hash this and make not nullable
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<String> roles;
    private LocalDateTime createdDate;
    private String pin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    private List<UserAccount> accountList;
}
