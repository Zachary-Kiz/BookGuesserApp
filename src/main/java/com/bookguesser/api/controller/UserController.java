package com.bookguesser.api.controller;

import com.bookguesser.api.model.AuthRequest;
import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.services.JwtService;
import com.bookguesser.api.services.UserInfoService;


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

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authReq) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authReq.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid User Request!");
        }
    }
}