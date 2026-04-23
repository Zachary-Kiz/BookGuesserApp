package com.bookguesser.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookguesser.api.model.Book;
import com.bookguesser.api.model.Puzzle;
import com.bookguesser.api.repository.PuzzleRepo;
import com.bookguesser.api.services.PuzzleService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/puzzle/today")
public class PuzzleController {
    private final PuzzleRepo puzzleRepo;
    private final PuzzleService puzzleService;

    public PuzzleController(PuzzleRepo puzzleRepo, PuzzleService puzzleService) {
        this.puzzleRepo = puzzleRepo;
        this.puzzleService = puzzleService;
    }

    @GetMapping
    public Book getTodayPuzzle() {
        return puzzleService.getBookToday();
    }
    
    
}
