package com.bookguesser.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookguesser.api.model.Cover;
import com.bookguesser.api.repository.CoverRepo;

@RestController
@RequestMapping("covers")
public class CoverController {
    private final CoverRepo repository;

    public CoverController(CoverRepo repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cover> getAllBooks() {
        return repository.findAll();
    }
}
