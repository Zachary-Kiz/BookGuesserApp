package com.bookguesser.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookguesser.api.model.PlayerGuess;
import com.bookguesser.api.model.Puzzle;
import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.repository.PlayerGuessRepo;
import com.bookguesser.api.repository.PuzzleRepo;
import com.bookguesser.api.repository.UserInfoRepository;

@Service
public class PlayerGuessService {
    private final UserInfoRepository userRepo;
    private final PuzzleRepo puzzleRepo;
    private final PlayerGuessRepo guessRepo;

    public PlayerGuessService(UserInfoRepository userRepo, PuzzleRepo puzzleRepo, PlayerGuessRepo guessRepo) {
        this.userRepo = userRepo;
        this.puzzleRepo = puzzleRepo;
        this.guessRepo = guessRepo;
    }

    public String addGuess(PlayerGuess guess) throws Exception {

        boolean alreadyGuessed = guessRepo.existsByUsernameAndPuzzleId(guess.getUsername(), guess.getPuzzleId());
        if (alreadyGuessed) {
            throw new Exception("Already guessed for this puzzle");
        }

        Optional<UserInfo> user = userRepo.findByUsername(guess.getUsername());

        if (user.isEmpty()) {
            throw new Exception("User does not exist");
        }

        Optional<Puzzle> puzzle = puzzleRepo.findById(guess.getPuzzleId());

        if (puzzle.isEmpty()) {
            throw new Exception("Puzzle does not exist");
        }

        guessRepo.save(guess);
        return "Player Guess added successfully!";
        
    }

    public PlayerGuess getGuess(String username, Integer puzzleId) throws Exception {
        Optional<PlayerGuess> prevGuess = guessRepo.findByUsernameAndPuzzleId(username, puzzleId);
        if (prevGuess.isEmpty()) {
            throw new Exception("Player has not guessed this puzzle");
        }
        PlayerGuess guess = prevGuess.get();
        return guess;
    }

    public List<PlayerGuess> getFriendGuesses(String username, Integer puzzleId) throws Exception {
        Optional<UserInfo> optUser = userRepo.findByUsername(username);
        if (optUser.isEmpty()) throw new Exception("User does not exist");

        UserInfo user = optUser.get();
        List<String> friends = user.getFriends();

        List<PlayerGuess> allGuesses = guessRepo.findByPuzzleIdAndUsernameIn(puzzleId, friends);
        return allGuesses;

    }

}
