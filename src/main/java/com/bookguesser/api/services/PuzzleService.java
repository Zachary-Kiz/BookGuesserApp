package com.bookguesser.api.services;

import com.bookguesser.api.model.Book;
import com.bookguesser.api.model.Puzzle;
import com.bookguesser.api.repository.PuzzleRepo;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class PuzzleService {
    
    private final PuzzleRepo puzzleRepo;

    public PuzzleService(PuzzleRepo puzzleRepo) {
        this.puzzleRepo = puzzleRepo;
    }

    public Puzzle getTodayPuzzle() {
        String today = LocalDate.now().toString();
        Puzzle todayPuzzle = puzzleRepo.findByPuzzleDate(today)
        .orElseThrow(() -> new RuntimeException("No Puzzle Today"));
        return todayPuzzle;
    }


    public Book getBookToday() {
        Puzzle todayPuzzle = getTodayPuzzle();
        return todayPuzzle.getBook();
    }

    public Book getBookById(Integer id) {
        Puzzle dayPuzzle = puzzleRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No Puzzle on this day"));

        return dayPuzzle.getBook();
    }

    public int getNumPuzzles() {
        Puzzle todayPuzzle = getTodayPuzzle();
        return todayPuzzle.getDay() - 1;
    }
}
