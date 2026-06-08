package com.bookguesser.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookguesser.api.model.FriendRequest;
import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.repository.FriendRequestRepo;
import com.bookguesser.api.repository.UserInfoRepository;

@Service
public class FriendRequestService {

    private UserInfoRepository userInfoRepository;
    private FriendRequestRepo friendRequestRepo;

    private Integer MAX_SIZE = 10;

    public FriendRequestService(UserInfoRepository userInfoRepository, FriendRequestRepo friendRequestRepo) {
        this.userInfoRepository = userInfoRepository;
        this.friendRequestRepo = friendRequestRepo;
    }

    public List<String> getUserSearch(String username) {
        List<String> users = userInfoRepository.findByUsernameContainingIgnoreCase(username)
            .stream()
            .map(UserInfo::getUsername)
            .toList();
        if (users.size() > MAX_SIZE) {
            users = users.subList(0, MAX_SIZE);
        }
        return users;

    }

    public String createRequest(String username, String requestUser) throws Exception {
        Optional<UserInfo> friendUser = userInfoRepository.findByUsername(username);
        if (friendUser.isEmpty()) throw new Exception("User does not exist");
        Optional<UserInfo> curUser = userInfoRepository.findByUsername(requestUser);
        if (curUser.isEmpty()) throw new Exception("User conducting request does not exist");

        if (username.equals(requestUser)) throw new Exception("Cannot be friends with yourself :(");
        FriendRequest friendReq = new FriendRequest(requestUser, username);
        friendRequestRepo.save(friendReq);
        return "Request Created Successfully!";
    }

    public List<String> getUserReqs(String username) throws Exception {
        Optional<UserInfo> optUser = userInfoRepository.findByUsername(username);
        if (optUser.isEmpty()) throw new Exception("User does not exist");
        UserInfo user = optUser.get();

        String userId = user.getUsername();
        List<String> friendReqs = friendRequestRepo.findAllByToUser(userId)
                                        .stream()
                                        .map(FriendRequest::getFromUser)
                                        .toList();
        return friendReqs;
    }

}
