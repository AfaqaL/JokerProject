package com.joker.model;

import com.joker.model.enums.GameMode;

import java.util.List;

public class Room {

    private long id;

    private String password;

    private int bayonet;

    private GameMode gameMode;

    private List<User> players;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBayonet() {
        return bayonet;
    }

    public void setBayonet(int bayonet) {
        this.bayonet = bayonet;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }
}
