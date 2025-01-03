package com.example.demo.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomUser {
    @Id
    private String username;

    private String password;

    //Roles/authorities
}
