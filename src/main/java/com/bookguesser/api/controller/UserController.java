package com.bookguesser.api.controller;

import com.bookguesser.api.model.AuthRequest;
import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.services.JwtService;
import com.bookguesser.api.services.UserInfoService;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserInfoService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    public UserController(UserInfoService userService, JwtService jwtService, AuthenticationManager authManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authManager = authManager;
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

            ResponseCookie cookie = ResponseCookie.from("token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .sameSite("Lax")
                .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(Map.of(
                "accessToken", accessToken
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of( "error","User does not exist"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication auth) {
        return ResponseEntity.ok().body(Map.of(
            "user", auth.getName()
        ));
    }

    @GetMapping("/user/refresh")
    public ResponseEntity<?> isLoggedIn(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(401).build();
        }

        String accessToken = jwtService.generateAccessToken(auth.getName());
        return ResponseEntity.ok().body(Map.of(
            "accessToken", accessToken
        ));
    }
}