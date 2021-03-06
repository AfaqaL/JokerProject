package com.joker.game;

import com.joker.model.enums.CardColor;
import com.joker.model.enums.JokerMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private List<List<Card>> cards;

    private final long id;
    private int taken;
    private int declared;

    private boolean onStreak;

    public Player(long id) {
        this.id = id;
        taken = 0;
        cards = new ArrayList<>(5);
        for (int i = 0; i < 5; i++)
            cards.add(new ArrayList<>(9));
        onStreak = true;
    }

    public long getId() {
        return id;
    }

    public void setValidCards(Card firstCard, CardColor superior) {
        setValidityForAll(false);
        if (firstCard instanceof JokerCard) {
            setValidCardsJoker((JokerCard) firstCard, superior);
            return;
        }

        CardColor color = firstCard.color;
        setValid(cards.get(4));

        if (!cards.get(color.ordinal()).isEmpty()) {
            setValid(cards.get(color.ordinal()));
            return;
        }


        if (superior != CardColor.NO_COLOR && !cards.get(superior.ordinal()).isEmpty()) {
            setValid(cards.get(superior.ordinal()));
            return;
        }

        for (int i = 0; i < 4; i++)
            setValid(cards.get(i));
    }

    private void setValidCardsJoker(JokerCard firstCard, CardColor superior) {
        CardColor color = firstCard.color;
        setValid(cards.get(4));

        if (firstCard.mode == JokerMode.GIVE) {
            if (!cards.get(color.ordinal()).isEmpty()) {
                cards.get(color.ordinal()).get(0).setValid(true);
                return;
            }

        } else if (firstCard.mode == JokerMode.TAKE) {
            if (!cards.get(color.ordinal()).isEmpty()) {
                setValid(cards.get(color.ordinal()));
                return;
            }
        }

        if (superior != CardColor.NO_COLOR && !cards.get(superior.ordinal()).isEmpty()) {
            setValid(cards.get(superior.ordinal()));
            return;
        }

        for (int i = 0; i < 4; i++)
            setValid(cards.get(i));
    }

    private void setValid(List<Card> cards) {
        for (Card c : cards)
            c.setValid(true);
    }

    public void removeCard(Card chosen) {
        int idx = ((chosen instanceof JokerCard) ? 4 : chosen.color.ordinal());
        cards.get(idx).remove(chosen);
    }

    public void setDealtCards(List<List<Card>> dealtCards) {
        this.cards = dealtCards;

        for (int i = 0; i < 4; i++)
            Collections.sort(cards.get(i));
    }

    public List<Card> getPlayerCards() {
        List<Card> ls = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            ls.addAll(cards.get(i));
        }

        return ls;
    }

    public void resetStreak(){
        onStreak = true;
    }


    public void resetTaken(){
        taken = 0;
    }

    public boolean endedOnStreak() { return onStreak; }

    public int getScore(int maxCards, int bayonet) {
        if (declared == taken) {
            return taken == maxCards ? maxCards * 100 : (taken + 1) * 50;
        }else if(taken == 0){
            onStreak = false;
            return -1 * bayonet;
        }
        onStreak = false;
        return taken * 10;
    }

    public void increaseTaken() {
        taken++;
    }

    public void setDeclared(int x) {
        declared = x;
    }

    public void setValidityForAll(boolean value) {
        for (List<Card> ls : cards){
            for(Card c : ls){
                c.setValid(value);
            }
        }
    }
}
