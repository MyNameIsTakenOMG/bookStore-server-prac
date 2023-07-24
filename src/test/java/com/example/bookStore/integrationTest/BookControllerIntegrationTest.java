package com.example.bookStore.integrationTest;

import com.example.bookStore.BookStoreApplication;
import com.example.bookStore.dto.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BookStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    // this is the http client which can be used to make http request
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql(scripts = {"classpath:insertInitialBookDataForTest.sql"})
    void shouldReturnBooksWhenBookApiCalled(){
        BookDTO[] bookDTOs = testRestTemplate.getForObject("http://localhost:"+port+"/api/v1/books", BookDTO[].class);
        assertThat(bookDTOs).isNotNull();
        assertThat(bookDTOs.length).isEqualTo(2);
    }
    @Test
    @Sql(scripts = {"classpath:insertInitialBookDataForTest.sql"})
    void shouldReturnBooksWhenBookApiCalled1(){
        BookDTO[] bookDTOs = testRestTemplate.getForObject("http://localhost:"+port+"/api/v1/books", BookDTO[].class);
        assertThat(bookDTOs).isNotNull();
        assertThat(bookDTOs.length).isEqualTo(2);
    }
}
