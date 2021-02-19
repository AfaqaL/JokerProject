package com.joker.model.dto;

public class LoginResponse {
    private boolean success;
    private String username;
    private int rank;
    private long userId;

    public LoginResponse() {
    }

    public LoginResponse(boolean success) {
        this.success = success;
    }

    public LoginResponse(boolean success, String username, int rank, long userId) {
        this.success = success;
        this.username = username;
        this.rank = rank;
        this.userId = userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
