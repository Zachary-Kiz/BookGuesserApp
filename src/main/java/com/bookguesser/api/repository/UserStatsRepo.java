package com.bookguesser.api.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookguesser.api.model.UserStats;


public interface UserStatsRepo extends JpaRepository<UserStats, Integer> {
    Optional<UserStats> findByUsername(String username);
    @Modifying
    @Query("UPDATE UserStats u SET u.stats = :stats WHERE u.username = :username")
    void updateStats(@Param("username") String username, @Param("stats") Map<String, Integer> stats);
}
