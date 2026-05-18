package com.bookguesser.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookguesser.api.model.Cover;;

public interface CoverRepo extends JpaRepository<Cover, Integer> {
    
}