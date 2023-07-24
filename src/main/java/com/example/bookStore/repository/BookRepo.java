package com.example.bookStore.repository;

import com.example.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID> {
    List<Book> findBooksByTitle(String title);
}
