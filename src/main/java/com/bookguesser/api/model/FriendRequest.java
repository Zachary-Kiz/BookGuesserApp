package com.bookguesser.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "friend_request",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fromUser", "toUser"})
    }
)
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fromUser;
    private String toUser;

    public FriendRequest() {}

    public FriendRequest(String fromUser, String toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public String getFromUser() {
        return this.fromUser;
    }

    public String getToUser() {
        return this.toUser;
    }

}
