package com.example.bookStore.repository;

import com.example.bookStore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepoTest {
    @Autowired
    private BookRepo bookRepo;

    @Test
    @Sql(scripts = {"classpath:insertInitialBookDataForTest.sql"})
    void shouldAbleToFetchAllBooks(){
        List<Book> all = bookRepo.findAll();
        Long count = (long) all.size();
        Assertions.assertEquals(2,count);
    }

    @Test
    @Sql(scripts = {"classpath:insertInitialBookDataForTest.sql"})
    void shouldReturnOneRecordWhenTitleIsTestTitle(){
        List<Book> searchResults = bookRepo.findBooksByTitle("test title");
        Assertions.assertEquals(1,searchResults.size());
    }
}