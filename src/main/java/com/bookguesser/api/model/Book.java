package com.bookguesser.api.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String author;
    private Integer releaseyear;

    @OneToMany(mappedBy = "book_id", fetch = FetchType.LAZY)
    private List<Cover> covers;

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public Integer getReleaseYear() {
        return this.releaseyear;
    }

    public List<Cover> getCovers() {
        return this.covers;
    }

    @Override
    public String toString() {
        return this.title;
    }
}