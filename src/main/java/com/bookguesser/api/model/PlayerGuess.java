package com.bookguesser.api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "guesses",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "puzzle_id"})
    }
)
public class PlayerGuess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "puzzle_id")
    private Integer puzzleId;
    private String username;
    private boolean guessed;
    private List<String> guesses;

    public Integer getPuzzleId() {
        return this.puzzleId;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean getGuessed() {
        return this.guessed;
    }

    public List<String> getGuesses() {
        return this.guesses;
    }
}
