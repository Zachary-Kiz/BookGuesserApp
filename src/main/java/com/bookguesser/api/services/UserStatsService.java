package com.bookguesser.api.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookguesser.api.model.PlayerGuess;
import com.bookguesser.api.model.UserStats;
import com.bookguesser.api.repository.UserStatsRepo;

import jakarta.transaction.Transactional;

@Service
public class UserStatsService {

    private UserStatsRepo userStatsRepo;

    public UserStatsService(UserStatsRepo userStatsRepo) {
        this.userStatsRepo = userStatsRepo;
    }

    @Transactional
    public String updateStats(PlayerGuess guess) throws Exception {

        Optional<UserStats> userStat = userStatsRepo.findByUsername(guess.getUsername());

        if (userStat.isEmpty()) {
            throw new Exception("User Stats do not exist");
        }
        UserStats foundStat = userStat.get();

        Map<String, Integer> stats = foundStat.getStats();
        Map<String, Integer> updatedStats = updateVal(guess, stats);
        foundStat.setStats(updatedStats);
        userStatsRepo.saveAndFlush(foundStat);

        return "Successfully updated user stats!";
    }

    public Map<String, Integer> updateVal(PlayerGuess guess, Map<String, Integer> stats) {
        if (!guess.getGuessed()) {
            stats.merge("failed", 1, Integer::sum);
        } else {
            Integer numGuesses = guess.getGuesses().size();
            String numGuessString = numGuesses.toString();
            stats.merge(numGuessString, 1, Integer::sum);
        }
        return stats;
    }

}
