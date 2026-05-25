package com.bookguesser.api.controller;

import com.bookguesser.api.model.AuthRequest;
import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.services.JwtService;
import com.bookguesser.api.services.UserInfoService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authReq) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(authReq.getUsername());

            ResponseCookie cookie = ResponseCookie.from("token", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 24)
                .sameSite("Strict")
                .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Login Successful");
        } else {
            throw new UsernameNotFoundException("Invalid User Request!");
        }
    }

    @GetMapping("/user/isLoggedIn")
    public ResponseEntity<?> isLoggedIn(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(auth.getName());
    }
}