package com.bookguesser.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.services.FriendRequestService;
import com.bookguesser.api.services.UserInfoDetails;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/friends")
public class FriendController {

    private FriendRequestService friendRequestService;

    public FriendController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @GetMapping("/getUsers")
    public List<String> getMethodName(@RequestParam String username) {
        return friendRequestService.getUserSearch(username);
    }

    @GetMapping("/getRequests")
    public ResponseEntity<?> getReqs(Authentication auth) {
        try {
            List<String> friendReqs = friendRequestService.getUserReqs(auth.getName());
            return ResponseEntity.ok().body(Map.of("friendReqs", friendReqs));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/createReq")
    public ResponseEntity<?> createReq(@RequestBody Map<String, String> username, Authentication auth) {
        try {
            String userString = username.get("username");
            String requesterName = auth.getName();
            String message = friendRequestService.createRequest(userString, requesterName);
            return ResponseEntity.ok().body(Map.of("message", message));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
        
    }
    
    
}
