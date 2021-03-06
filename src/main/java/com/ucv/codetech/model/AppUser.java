package com.ucv.codetech.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "app_user")
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private String registrationDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @PrePersist
    private void initEntity() {
        this.registrationDate = LocalDateTime.now().format(dateTimeFormatter);
    }
}
