package com.joker.model;

import java.util.List;

public class Table {

    private long id;

    private Card superior;

    private List<Card> cards;

    private List<Card> playedCards;

    private List<Integer> declares;

    private List<Integer> scores;

    private int playerIndex;

    private PlayAction action;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Card getSuperior() {
        return superior;
    }

    public void setSuperior(Card superior) {
        this.superior = superior;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<Card> playedCards) {
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
