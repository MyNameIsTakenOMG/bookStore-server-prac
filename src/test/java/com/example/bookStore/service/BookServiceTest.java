package com.example.bookStore.service;

import com.example.bookStore.dto.BookDTO;
import com.example.bookStore.model.Book;
import com.example.bookStore.repository.BookRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepo bookRepo;

    @Mock
    private ModelMapper modelMapper;
    @Test
    void shouldReturnListOfBookDTOWhenGetBooksCalled(){
        List<Book> books = new ArrayList<>();
        Book book = getBook();
        books.add(book);
        when(bookRepo.findAll()).thenReturn(books);
        BookDTO bookDTO = getBookDTO();
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        List<BookDTO> bookDTOList = bookService.getBooks();
        assertEquals(1,bookDTOList.size());
        assertNotNull(bookDTOList.get(0));
        assertEquals("test title",bookDTOList.get(0).getTitle());
        assertEquals("test description",bookDTOList.get(0).getDescription());
        assertEquals(2010,bookDTOList.get(0).getReleaseYear());

    }
    private Book getBook(){
        return Book.builder()
                .title("test title")
                .description("test description")
                .releaseYear(2010)
                .id(UUID.randomUUID())
                .build();
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