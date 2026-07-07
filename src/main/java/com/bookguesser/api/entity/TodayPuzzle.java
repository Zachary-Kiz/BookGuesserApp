package com.bookguesser.api.entity;

import com.bookguesser.api.model.Book;

import lombok.Data;

@Data
public class TodayPuzzle {

    private Integer puzzleId;
    private Book book;

    public TodayPuzzle(Integer puzzleId, Book book) {
        this.puzzleId = puzzleId;
        this.book = book;
    }
}
