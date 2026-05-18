package com.bookguesser.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookguesser.api.model.Book;
import com.bookguesser.api.repository.PuzzleRepo;
import com.bookguesser.api.services.PuzzleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/puzzle")
public class PuzzleController {
    private final PuzzleService puzzleService;

    public PuzzleController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping("/numPuzzles")
    public int getNumPuzzles() {
        return puzzleService.getNumPuzzles();
    }

    @GetMapping("/today")
    public Book getTodayPuzzle() {
        return puzzleService.getBookToday();
    }

    @GetMapping("/{id}")
    public Book getPuzzleByDay(@PathVariable Integer id) {
        return puzzleService.getBookById(id);
    }

    
    
    
}
