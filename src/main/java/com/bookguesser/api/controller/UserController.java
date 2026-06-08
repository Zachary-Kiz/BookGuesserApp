package com.bookguesser.api.controller;

import com.bookguesser.api.model.AuthRequest;
import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.repository.UserStatsRepo;
import com.bookguesser.api.services.JwtService;
import com.bookguesser.api.services.UserInfoService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserStatsRepo userStatsRepo;

    private final UserInfoService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    private final UserDetailsService userDetailsService;

    public UserController(UserInfoService userService, JwtService jwtService, AuthenticationManager authManager, UserStatsRepo userStatsRepo, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userStatsRepo = userStatsRepo;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
        try {
            String message = userService.addUser(userInfo);
            return ResponseEntity.ok().body(Map.of(
                "message", message
            ));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(Map.of( "error","Username / Email already exists"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authReq) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateAccessToken(authReq.getUsername());
            String refreshToken = jwtService.generateRefreshToken(authReq.getUsername());

            ResponseCookie accessCookie = userService.accessTokenCookie(accessToken);
            ResponseCookie refreshCookie = userService.refreshTokenCookie(refreshToken);

            return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .body("Tokens Sent!");
        } else {
            return ResponseEntity.status(404).body(Map.of( "error","User does not exist"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication auth) {
        return ResponseEntity.ok().body(Map.of(
            "user", auth.getName(), "stats", userStatsRepo.findByUsername(auth.getName()).get().getStats()
        ));
    }

    @GetMapping("/user/refresh")
    public ResponseEntity<?> isLoggedIn(HttpServletRequest request) {

        String refreshToken = null;
        UserDetails user = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    String username = jwtService.extractUsername(refreshToken);
                    user = userDetailsService.loadUserByUsername(username);
                }
            }
        }

        if (refreshToken == null || !jwtService.validateToken(refreshToken, user)) {
            return ResponseEntity.status(403).build();
        }

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        return ResponseEntity.ok()
                .body(Map.of("accessToken", accessToken));
    }

    @PostMapping("/user/logOut")
    public ResponseEntity<?> logout() {

        System.out.println("IS THIS WORKING");

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .maxAge(0)
                .path("/")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .build();
    }
}