package com.joker.game;

import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.JokerMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player0;
    Player player1;
    Player player2;
    Player player3;

//    public enum CardColor {
//        CLUBS,
//        DIAMONDS,
//        SPADES,
//        HEARTS,
//        NO_COLOR
//    }


    @BeforeEach
    void setUp() {
        player0 = new Player(0);
        player1 = new Player(1);
        player2 = new Player(2);
        player3 = new Player(3);

        List<Card> diamonds = new ArrayList<>(Arrays.asList (new Card(CardValue.NINE, CardColor.DIAMONDS),
                                                            new Card(CardValue.ACE, CardColor.DIAMONDS)));
        List<Card> spades = new ArrayList<>(Arrays.asList ( new Card(CardValue.KING, CardColor.SPADES),
                                                            new Card(CardValue.SEVEN, CardColor.SPADES),
                                                            new Card(CardValue.QUEEN, CardColor.SPADES)));
        List<Card> hearts = new ArrayList<>(Arrays.asList ( new Card(CardValue.JACK, CardColor.HEARTS),
                                                            new Card(CardValue.SEVEN, CardColor.HEARTS),
                                                            new Card(CardValue.TEN, CardColor.HEARTS)));
        List<Card> jokers = new ArrayList<>(Arrays.asList ( new JokerCard()));
        player0.setDealtCards(new ArrayList<>(Arrays.asList(new ArrayList<>(), diamonds, spades, hearts, jokers)));
    }

    @Test
    void setValidCardsHasSameColor() {
        player0.setValidCards(new Card(CardValue.QUEEN, CardColor.DIAMONDS), CardColor.HEARTS);
        List<Card> allCards = player0.getPlayerCards();
        for (Card curr : allCards) {
            if (curr.color == CardColor.DIAMONDS || curr instanceof JokerCard)
                assertTrue(curr.isValid());
            else
                assertFalse(curr.isValid());
        }
    }

    @Test
    void setValidCardsNoSameColor() {
        player0.setValidCards(new Card(CardValue.QUEEN, CardColor.CLUBS), CardColor.HEARTS);
        List<Card> allCards = player0.getPlayerCards();
        for (Card curr : allCards) {
            if (curr.color == CardColor.HEARTS || curr instanceof JokerCard)
                assertTrue(curr.isValid());
            else
                assertFalse(curr.isValid());
        }
    }

    @Test
    void setValidCardsSuperior() {
        player0.setValidCards(new Card(CardValue.QUEEN, CardColor.HEARTS), CardColor.HEARTS);
        List<Card> allCards = player0.getPlayerCards();
        for (Card curr : allCards) {
            if (curr.color == CardColor.HEARTS || curr instanceof JokerCard)
                assertTrue(curr.isValid());
            else
                assertFalse(curr.isValid());
        }
    }

    @Test
    void setValidCardsSuperiorHasNoSuperior() {
        player0.setValidCards(new Card(CardValue.QUEEN, CardColor.CLUBS), CardColor.CLUBS);
        List<Card> allCards = player0.getPlayerCards();
        for (Card curr : allCards) {
            assertTrue(curr.isValid());
        }
    }

    @Test
    void setValidCardsNoSuperiorDifferentColor() {
        player0.setValidCards(new Card(CardValue.QUEEN, CardColor.CLUBS), CardColor.NO_COLOR);
        List<Card> allCards = player0.getPlayerCards();
        for (Card curr : allCards) {
            assertTrue(curr.isValid());
        }
    }

    @Test
    void setValidCardsNoSuperiorSameColor() {
        player0.setValidCards(new Card(CardValue.QUEEN, CardColor.HEARTS), CardColor.NO_COLOR);
        List<Card> allCards = player0.getPlayerCards();
        for (Card curr : allCards) {
            if (curr.color == CardColor.HEARTS || curr instanceof JokerCard)
                assertTrue(curr.isValid());
            else
                assertFalse(curr.isValid());
        }
    }

    @Test
    void setValidCardsFirstJokerGiveModeNoSuperior() {
        JokerCard joker = new JokerCard();
        joker.setMode(JokerMode.GIVE, CardColor.SPADES);
        player0.setValidCards(joker, CardColor.NO_COLOR);
        List<Card> allCards = player0.getPlayerCards();

        for (Card curr : allCards) {
            if (curr.equals(new Card(CardValue.KING, CardColor.SPADES)) || curr instanceof JokerCard)
                assertTrue(curr.isValid());
            else {
                assertFalse(curr.isValid());
            }
        }
    }

    @Test
    void setValidCardsFirstJokerGiveSuperior() {
        JokerCard joker = new JokerCard();
        joker.setMode(JokerMode.GIVE, CardColor.DIAMONDS);
        player0.setValidCards(joker, CardColor.DIAMONDS);
        List<Card> allCards = player0.getPlayerCards();

        for (Card curr : allCards) {
            if (curr.equals(new Card(CardValue.ACE, CardColor.DIAMONDS)) || curr instanceof JokerCard)
                assertTrue(curr.isValid());
            else {
                assertFalse(curr.isValid());
            }
        }
    }

    @Test
    void setValidCardsFirstJokerGiveSuperiorAllValid() {
        JokerCard joker = new JokerCard();
        joker.setMode(JokerMode.GIVE, CardColor.CLUBS);
        player0.setValidCards(joker, CardColor.CLUBS);
        List<Card> allCards = player0.getPlayerCards();

        for (Card curr : allCards) {
            assertTrue(curr.isValid());
        }
    }

    @Test
    void setValidCardsFirstJokerGiveNoSuperiorAllValid() {
        JokerCard joker = new JokerCard();
        joker.setMode(JokerMode.GIVE, CardColor.CLUBS);
        player0.setValidCards(joker, CardColor.NO_COLOR);
        List<Card> allCards = player0.getPlayerCards();

        for (Card curr : allCards) {
            assertTrue(curr.isValid());
        }
    }

    @Test
    void setValidCardsFirstJokerGiveModeSuperiorExists() {
        JokerCard joker = new JokerCard();
        joker.setMode(JokerMode.GIVE, CardColor.CLUBS);
        player0.setValidCards(joker, CardColor.DIAMONDS);
        List<Card> allCards = player0.getPlayerCards();

        for (Card curr : allCards) {
            if (curr.color == CardColor.DIAMONDS   || curr instanceof JokerCard)
                assertTrue(curr.isValid());
            else {
                assertFalse(curr.isValid());
            }
        }
    }

    @Test
    void removeCard() {
    }

    @Test
    void setDealtCards() {
    }

    @Test
    void getPlayerCards() {
    }

    @Test
    void getScore() {
    }

    @Test
    void increaseTaken() {
    }

    @Test
    void setDeclared() {
    }
}