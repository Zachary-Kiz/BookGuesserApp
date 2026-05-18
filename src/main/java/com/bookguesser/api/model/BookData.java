package com.bookguesser.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookdata")
public class BookData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String author;
    private Integer releaseyear;
    private String genre;
    private boolean downloaded;

    // Getters & Setters
    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public Integer getReleaseYear() {
        return this.releaseyear;
    }

    public String getGenre() {
        return this.genre;
    }

    public boolean getDownloaded() {
        return this.downloaded;
    }
}
