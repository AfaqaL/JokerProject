package com.joker.game;

import com.joker.model.enums.CardColor;
import com.joker.model.enums.JokerMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    private List<List<Card>> cards;
    private final long id;
    private int taken;
    private int declared;

    public Player(long id) {
        this.id = id;
        taken = 0;
        cards = new ArrayList<>(5);
        for (int i = 0; i < 5; i++)
            cards.add(new ArrayList<>(9));
    }

    public void setValidCards(Card firstCard, CardColor superior) {
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
        for (int i = 0; i < 5; i++) {
            ls.addAll(cards.get(i));
        }
        return ls;
    }

    public List<Card> getThreeCards(){
        List<Card> ls = getPlayerCards();
        List<Card> res = new ArrayList<>(3);

        int first = ThreadLocalRandom.current().nextInt(0, 3);
        res.add(ls.get(first));

        int second = ThreadLocalRandom.current().nextInt(3, 6);
        res.add(ls.get(second));

        int third = ThreadLocalRandom.current().nextInt(6, 9);
        res.add(ls.get(third));

        return res;
    }

    public int getScore(int maxCards) {
        if (declared == taken) {
            return taken == maxCards ? maxCards * 100 : (taken + 1) * 50;
        }
        return taken * 10;
    }

    public void increaseTaken() {
        taken++;
    }

    public void setDeclared(int x) {
        declared = x;
    }
}
