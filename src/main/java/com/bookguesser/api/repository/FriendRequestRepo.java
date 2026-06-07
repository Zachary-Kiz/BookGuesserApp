package com.bookguesser.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookguesser.api.model.FriendRequest;

public interface FriendRequestRepo extends JpaRepository<FriendRequest ,Integer> {

}
