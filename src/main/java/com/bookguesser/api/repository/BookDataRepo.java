package com.bookguesser.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookguesser.api.model.BookData;

public interface BookDataRepo extends JpaRepository<BookData, Integer> {
    
}
