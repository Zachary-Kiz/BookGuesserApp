package com.bookguesser.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookguesser.api.model.Puzzle;

public interface PuzzleRepo extends JpaRepository<Puzzle, Integer> {
    Optional<Puzzle> findByPuzzleDate(String puzzleDate);
}