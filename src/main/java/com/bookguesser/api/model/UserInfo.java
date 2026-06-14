package com.bookguesser.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String roles;

    private List<String> friends = new ArrayList<>();

    public UserInfo(UserInfo userInfo) {
        this.username = userInfo.getUsername();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.roles = userInfo.getRoles();
        this.friends = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public List<String> getFriends() {
        return this.friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
