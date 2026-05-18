package com.bookguesser.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "covers")
public class Cover {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer book_id;
    private Integer level;
    private String image_url;

    public Integer getBookId() {
        return this.book_id;
    }

    public Integer getLevel() {
        return this.level;
    }

    public String getImageUrl() {
        return this.image_url;
    }
}
