package com.joker.model.dto;

import com.joker.model.User;

public class UserDTO {
    private long id;
    private String username;
    private int rank;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public User toUser(){
        return new User(id, username, null, null, rank);
    }
}
