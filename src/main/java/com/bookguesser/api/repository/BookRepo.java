package com.bookguesser.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookguesser.api.model.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {
    
}
