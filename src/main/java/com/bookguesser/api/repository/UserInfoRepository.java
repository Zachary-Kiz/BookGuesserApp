package com.bookguesser.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookguesser.api.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUsername(String username);
    Optional<UserInfo> findByEmail(String email);
}
