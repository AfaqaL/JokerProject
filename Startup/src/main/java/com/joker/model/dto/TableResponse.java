package com.joker.model.dto;

import com.joker.model.enums.PlayAction;

import java.util.List;

public class TableResponse {

    private long id;

    private CardDTO superior;

    private List<CardDTO> cards;

    private List<CardDTO> playedCards;

    private List<Integer> declares;

    private List<Integer> taken;

    private List<Integer> scores;

    private int playerIndex;

    private PlayAction action;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CardDTO getSuperior() {
        return superior;
    }

    public void setSuperior(CardDTO superior) {
        this.superior = superior;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public List<CardDTO> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<CardDTO> playedCards) {
        this.playedCards = playedCards;
    }

    public List<Integer> getDeclares() {
        return declares;
    }

    public void setDeclares(List<Integer> declares) {
        this.declares = declares;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
