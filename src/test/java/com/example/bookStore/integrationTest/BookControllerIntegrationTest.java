package com.example.bookStore.integrationTest;

import com.example.bookStore.BookStoreApplication;
import com.example.bookStore.dto.BookDTO;
import com.example.bookStore.utils.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BookStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    // this is the http client which can be used to make http request
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Logger log = LoggerFactory.getLogger(BookControllerIntegrationTest.class);

    void setupHeader(){
        // generate a jwt token
        String token = jwtUtil.generateJwtToken(new UsernamePasswordAuthenticationToken("peter@peter.com",
                passwordEncoder.encode("password"),new ArrayList<>()));
        testRestTemplate.getRestTemplate().setInterceptors(Collections.singletonList(((request, body, execution) -> {
            request.getHeaders()
                    .add("Authorization","Bearer "+token);
            return execution.execute(request,body);
        })));
    }

    @AfterEach
    void tearDown(){
        log.info("run after each===========================================");
        jdbcTemplate.execute("DELETE FROM Book WHERE Title='test title'");
    }

    @Test
    @Sql(scripts = {"classpath:initialDataForTesting.sql"})
    void shouldReturnBooksWhenBookApiCalled(){
        setupHeader();
        BookDTO[] bookDTOs = testRestTemplate.getForObject("http://localhost:"+port+"/api/v1/books", BookDTO[].class);
        assertThat(bookDTOs).isNotNull();
        assertThat(bookDTOs.length).isEqualTo(1);
    }
//    @Test
//    @Sql(scripts = {"classpath:initialDataForTesting.sql"})
//    void shouldReturnBooksWhenBookApiCalled1(){
//        BookDTO[] bookDTOs = testRestTemplate.getForObject("http://localhost:"+port+"/api/v1/books", BookDTO[].class);
//        assertThat(bookDTOs).isNotNull();
//        assertThat(bookDTOs.length).isEqualTo(2);
//    }

    @Test
    @Sql(scripts = {"classpath:initialDataForTesting.sql"})
    void shouldReturnBookWithTestTitleIgnoreCase(){
        setupHeader();
        BookDTO[] bookDTOS = testRestTemplate.getForObject("http://localhost:"+port+"/api/v1/books/Test TITle", BookDTO[].class);
        assertThat(bookDTOS).isNotNull();
        assertThat(bookDTOS.length).isEqualTo(1);
    }


}
