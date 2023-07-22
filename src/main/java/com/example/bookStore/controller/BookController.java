package com.example.bookStore.controller;

import com.example.bookStore.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @GetMapping("api/v1/books")
    public ResponseEntity<List<BookDTO>> getBooks(){
        BookDTO book = BookDTO.builder()
                .title("my first book")
                .build();
        List<BookDTO> books = new ArrayList<>();
        books.add(book);
        return ResponseEntity.ok(books);
    }
}
