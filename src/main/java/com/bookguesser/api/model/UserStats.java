package com.bookguesser.api.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Integer> stats = new HashMap<>(Map.ofEntries(
        Map.entry("1", 0),
        Map.entry("2", 0),
        Map.entry("3", 0),
        Map.entry("4", 0),
        Map.entry("5", 0),
        Map.entry("6", 0),
        Map.entry("failed", 0)
    ));

    public UserStats() {
        
    }

    public UserStats(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
    
    public Map<String, Integer> getStats() {
        return this.stats;
    }

    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    } 

}
