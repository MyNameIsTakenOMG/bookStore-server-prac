package com.example.bookStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(generator = "UUID",strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String email;
    private String password;
}
