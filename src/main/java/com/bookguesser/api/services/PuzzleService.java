package com.bookguesser.api.services;

import com.bookguesser.api.model.Book;
import com.bookguesser.api.model.Puzzle;
import com.bookguesser.api.repository.PuzzleRepo;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class PuzzleService {
    
    private final PuzzleRepo puzzleRepo;

    public PuzzleService(PuzzleRepo puzzleRepo) {
        this.puzzleRepo = puzzleRepo;
    }

    @PostConstruct
    public void init() {
        getBookToday();
    }


    public Book getBookToday() {
        String today = LocalDate.now().toString();
        Puzzle todayPuzzle = puzzleRepo.findByPuzzleDate(today)
        .orElseThrow(() -> new RuntimeException("No Puzzle Today"));

        return todayPuzzle.getBook();

    }
}
