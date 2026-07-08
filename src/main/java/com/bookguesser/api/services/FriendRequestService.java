package com.bookguesser.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookguesser.api.model.FriendRequest;
import com.bookguesser.api.model.UserInfo;
import com.bookguesser.api.repository.FriendRequestRepo;
import com.bookguesser.api.repository.UserInfoRepository;

import jakarta.transaction.Transactional;

@Service
public class FriendRequestService {

    private UserInfoRepository userInfoRepository;
    private FriendRequestRepo friendRequestRepo;

    private Integer MAX_SIZE = 10;

    public FriendRequestService(UserInfoRepository userInfoRepository, FriendRequestRepo friendRequestRepo) {
        this.userInfoRepository = userInfoRepository;
        this.friendRequestRepo = friendRequestRepo;
    }

    public List<String> getUserSearch(String username, String curUser) throws Exception {
        Optional<UserInfo> optUser = userInfoRepository.findByUsername(curUser);
        if (optUser.isEmpty()) {
            throw new Exception("User account does not exist");
        }
        UserInfo user = optUser.get();
        List<String> friends = user.getFriends();
        friends.add(curUser);
        List<String> users = userInfoRepository.findByUsernameContainingIgnoreCase(username)
            .stream()
            .map(UserInfo::getUsername)
            .filter(newUser -> !friends.contains(newUser))
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

    @Transactional
    public String deleteRequest(FriendRequest req) throws Exception {
        boolean friendReq = friendRequestRepo.existsByFromUserAndToUser(req.getFromUser(), req.getToUser());
        if (!friendReq) throw new Exception("Friend Request does not exist");
        friendRequestRepo.deleteByFromUserAndToUser(req.getFromUser(), req.getToUser());
        return "Deleted Friend Request Successfully!"; 
    }

    public String acceptRequest(FriendRequest req) throws Exception {
        String fromUsername = req.getFromUser();
        String toUsername = req.getToUser();
        Optional<UserInfo> fromUser = userInfoRepository.findByUsername(fromUsername);
        if (fromUser.isEmpty()) throw new Exception("From user does not exist");
        Optional<UserInfo> toUser = userInfoRepository.findByUsername(toUsername);
        if (toUser.isEmpty()) throw new Exception("To user does not exist");

        UserInfo from = fromUser.get();
        UserInfo to = toUser.get();

        List<String> fromFriends = from.getFriends();
        List<String> toFriends = to.getFriends();

        if (fromFriends.contains(toUsername)) throw new Exception("Users are already friends");
        if (toFriends.contains(fromUsername)) throw new Exception("Users are already friends");

        fromFriends.add(toUsername);
        toFriends.add(fromUsername);

        from.setFriends(fromFriends);
        to.setFriends(toFriends);

        userInfoRepository.save(from);
        userInfoRepository.save(to);

        friendRequestRepo.deleteByFromUserAndToUser(req.getFromUser(), req.getToUser());

        return "Added friend successfully!";
    }

    public List<String> getFriends(String username) throws Exception {
        Optional<UserInfo> optUser = userInfoRepository.findByUsername(username);
        if (optUser.isEmpty()) throw new Exception("User does not exist");

        UserInfo user = optUser.get();
        List<String> userFriends = user.getFriends();
        return userFriends;
    }

}
