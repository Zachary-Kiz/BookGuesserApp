package com.bookguesser.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookguesser.api.model.FriendRequest;
import java.util.List;


public interface FriendRequestRepo extends JpaRepository<FriendRequest ,Integer> {
    List<FriendRequest> findAllByToUser(String username);
}
