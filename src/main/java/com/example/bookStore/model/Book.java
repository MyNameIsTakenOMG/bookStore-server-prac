package com.example.bookStore.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int releaseYear;
}
