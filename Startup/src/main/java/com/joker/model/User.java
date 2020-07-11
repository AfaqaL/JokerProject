package com.joker.model;

public class User {
    private long id;

    private String username;

    private String mail;

    private String password;

    private int rank;

    public User(long id, String username, String mail, String password, int rank) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.rank = rank;
    }

    public User(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof User)) return false;

        User other = (User) obj;

        if (this.id != other.id) return false;
        if (!this.username.equals(other.username)) return false;
        if (!this.mail.equals(other.mail)) return false;
        if (!this.password.equals(other.password)) return false;
        if (this.rank != other.rank) return false;
        return true;
    }
}
