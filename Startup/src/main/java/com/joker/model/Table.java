package com.joker.model;

import com.joker.game.Card;

import java.util.List;

public class Table {

    private long id;

    private Card superior;

    private List<Card> cards;

    private List<Card> playedCards;

    private List<Integer> declares;

    private List<Integer> scores;
}
