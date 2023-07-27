package com.example.bookStore.service;

import com.example.bookStore.dto.BookDTO;
import com.example.bookStore.model.Book;
import com.example.bookStore.repository.BookRepo;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private ModelMapper modelMapper;
    public List<BookDTO> getBooks() {
        List<Book> books = bookRepo.findAll();
        return books.stream().map(getBookDTOFunction()).collect(Collectors.toList());
    }

    @NotNull
    private Function<Book, BookDTO> getBookDTOFunction() {
        return book -> modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> getBooksByTitle(String title) {
        List<Book> books = bookRepo.findBooksByTitleIgnoreCase(title);
        return books.stream().map(getBookDTOFunction()).collect(Collectors.toList());
    }
}
