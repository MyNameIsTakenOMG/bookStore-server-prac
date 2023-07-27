package com.example.bookStore.controller;

import com.example.bookStore.dto.BookDTO;
import com.example.bookStore.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getBooks() throws Exception {
        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(getBookDTO());
        when(bookService.getBooks()).thenReturn(bookDTOs);
        ResponseEntity<List<BookDTO>> response = bookController.getBooks();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(18);
    }

    @Test
    void shouldReturnBooksByTitleIgnoreCase(){
        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(getBookDTO());
        when(bookService.getBooksByTitle(anyString())).thenReturn(bookDTOs);
        ResponseEntity<List<BookDTO>> response = bookController.getBooksByTitle("TEST titlE");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
    }

    private BookDTO getBookDTO(){
        return BookDTO.builder()
                .title("test title")
                .description("test description")
                .releaseYear(2010)
                .id(UUID.randomUUID())
                .build();
    }
}