package com.bookguesser.api.services;

import com.bookguesser.api.repository.UserStatsRepo;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.model.UserStats;
import com.bookguesser.api.repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserStatsRepo userStatsRepo;
    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder, UserStatsRepo userStatsRepo) {
        this.repository = repository;
        this.encoder = encoder;
        this.userStatsRepo = userStatsRepo;
    }

    // Method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database by username
        Optional<UserInfo> userInfo = repository.findByUsername(username);
        
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        // Convert UserInfo to UserDetails (UserInfoDetails)
        UserInfo user = userInfo.get();
        return new User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRoles())));
    }

    // Add any additional methods for registering or managing users
    public String addUser(UserInfo userInfo) throws Exception {
        Optional<UserInfo> username = repository.findByUsername(userInfo.getUsername());
        if (!username.isEmpty()) {
            throw new Exception("User exists with this username");
        }
        Optional<UserInfo> email = repository.findByEmail(userInfo.getEmail());

        if (!email.isEmpty()) {
            throw new Exception("User exists with this email");
        }
        // Encrypt password before saving
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        repository.save(userInfo);
        userStatsRepo.save(new UserStats(userInfo.getUsername()));
        return "User added successfully!";
    }

    public ResponseCookie accessTokenCookie(String accessToken) {
        return ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 30)
                .sameSite("None")
                .build();
    }

    public ResponseCookie refreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .sameSite("None")
                .build();
    }
}