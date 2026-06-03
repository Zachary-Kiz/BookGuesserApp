package com.bookguesser.api.controller;

import com.bookguesser.api.services.UserStatsService;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bookguesser.api.model.PlayerGuess;
import com.bookguesser.api.services.PlayerGuessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/guess")
public class GuessController {

    private final UserStatsService userStatsService;
    private final PlayerGuessService guessService;

    public GuessController(PlayerGuessService guessService, UserStatsService userStatsService) {
        this.guessService = guessService;
        this.userStatsService = userStatsService;
    }

    @GetMapping("/prev")
    public PlayerGuess getPlayerGuess(@RequestParam String username, @RequestParam Integer puzzleId) {
        try {
            return guessService.getGuess(username, puzzleId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Guess not found", e);
        }
    }
    

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGuess(@RequestBody PlayerGuess guess) {
        try {
            String message = guessService.addGuess(guess);
            userStatsService.updateStats(guess);
            return ResponseEntity.ok().body(Map.of("message", message));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
