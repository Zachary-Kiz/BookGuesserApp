package com.bookguesser.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookguesser.api.repository.BookDataRepo;
import com.bookguesser.api.model.BookData;

@RestController
@RequestMapping("/bookData")
public class BookDataController {
    private final BookDataRepo repository;

    public BookDataController(BookDataRepo repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BookData> getAllBooks() {
        return repository.findAll();
    }
}
