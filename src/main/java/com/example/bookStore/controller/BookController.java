package com.example.bookStore.controller;

import com.example.bookStore.dto.BookDTO;
import com.example.bookStore.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

//    @Operation(summary = "get a list of books")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",description = "successfully retrieved books",
//                    content = {@Content(
//                            mediaType = "application/json",
//                            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class)))}),
//            @ApiResponse(responseCode = "401", description = "access denied"),
//            @ApiResponse(responseCode = "404", description = "not found")
//    })
    @GetMapping("api/v1/books")
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<BookDTO> books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }
}
