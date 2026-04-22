package com.bookguesser.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookguesser.api.model.Book;
import com.bookguesser.api.repository.BookRepo;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepo repository;

    public BookController(BookRepo repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return repository.findAll();
    }
}
