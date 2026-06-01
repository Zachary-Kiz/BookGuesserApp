package com.bookguesser.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookguesser.api.model.PlayerGuess;

public interface PlayerGuessRepo extends JpaRepository<PlayerGuess, Integer> {
    boolean existsByUsernameAndPuzzleId(String username, Integer puzzleId);
    Optional<PlayerGuess> findByUsernameAndPuzzleId(String username, Integer puzzleId);
}
