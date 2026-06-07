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
        @UniqueConstraint(columnNames = {"fromId", "toId"})
    }
)
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer fromId;
    private Integer toId;

    public FriendRequest(Integer fromId, Integer toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public Integer getFromId() {
        return this.fromId;
    }

    public Integer getToId() {
        return this.toId;
    }

}
