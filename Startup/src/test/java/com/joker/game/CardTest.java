package com.joker.game;

import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.JokerMode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void isValid() {
        Card testCard = new Card(CardValue.NINE, CardColor.CLUBS);

        testCard.setValid(true);
        assertTrue(testCard.isValid());

        testCard.setValid(false);
        assertFalse(testCard.isValid());

        Card joker = new JokerCard();
        assertTrue(joker.isValid());
    }

    @Test
    void compareSameColor() {
        Card taker = new Card(CardValue.ACE, CardColor.CLUBS);
        Card curr = new Card(CardValue.NINE, CardColor.CLUBS);
        int res = curr.compare(taker, CardColor.HEARTS);
        assertEquals(-1, res);

        taker = new Card(CardValue.SEVEN, CardColor.CLUBS);
        res = curr.compare(taker, CardColor.CLUBS);
        assertEquals(1, res);
    }

    @Test
    void compareDifferentColor() {
        Card taker = new Card(CardValue.EIGHT, CardColor.CLUBS);
        Card curr = new Card(CardValue.NINE, CardColor.DIAMONDS);
        int res = curr.compare(taker, CardColor.HEARTS);
        assertEquals(-1, res);

        res = curr.compare(taker, CardColor.DIAMONDS);
        assertEquals(1, res);

        res = curr.compare(taker, CardColor.CLUBS);
        assertEquals(-1, res);
    }

    @Test
    void compareJokerCurrent() {
        Card taker = new Card(CardValue.EIGHT, CardColor.CLUBS);
        JokerCard curr = new JokerCard();

        curr.setMode(JokerMode.OVER, null);
        int res = curr.compare(taker, CardColor.HEARTS);
        assertEquals(1, res);

        curr.setMode(JokerMode.UNDER, null);
        res = curr.compare(taker, CardColor.HEARTS);
        assertEquals(-1, res);
    }

    @Test
    void compareJokerTaker() {
        JokerCard taker = new JokerCard();
        Card curr = new Card(CardValue.EIGHT, CardColor.CLUBS);

        taker.setMode(JokerMode.OVER, null);
        int res = curr.compare(taker, CardColor.HEARTS);
        assertEquals(-1, res);

        taker.setMode(JokerMode.TAKE, CardColor.HEARTS);
        res = curr.compare(taker, CardColor.CLUBS);
        assertEquals(1, res);

        taker.setMode(JokerMode.TAKE, CardColor.CLUBS);
        res = curr.compare(taker, CardColor.DIAMONDS);
        assertEquals(1, res);

        taker.setMode(JokerMode.TAKE, CardColor.HEARTS);
        res = curr.compare(taker, CardColor.HEARTS);
        assertEquals(-1, res);

        taker.setMode(JokerMode.GIVE, CardColor.DIAMONDS);
        res = curr.compare(taker, CardColor.CLUBS);
        assertEquals(1, res);

        taker.setMode(JokerMode.GIVE, CardColor.DIAMONDS);
        res = curr.compare(taker, CardColor.SPADES);
        assertEquals(-1, res);

        taker.setMode(JokerMode.GIVE, CardColor.CLUBS);
        res = curr.compare(taker, CardColor.CLUBS);
        assertEquals(-1, res);
    }

    @Test
    void compareTo() {
        //compares for decreasing order
        Card bigger = new Card(CardValue.ACE, CardColor.CLUBS);
        Card smaller = new Card(CardValue.NINE, CardColor.CLUBS);

        int res = bigger.compareTo(smaller);
        assertTrue(res < 0);

        res = smaller.compareTo(bigger);
        assertTrue(res > 0);
    }

    @Test
    void testEquals() {
        Card bigger = new Card(CardValue.ACE, CardColor.CLUBS);
        Card smaller = new Card(CardValue.NINE, CardColor.DIAMONDS);

        assertFalse(bigger.equals(smaller));

        Card same = new Card(CardValue.NINE, CardColor.DIAMONDS);
        assertTrue(same.equals(smaller));

        Card differentValue = new Card(CardValue.KING, CardColor.CLUBS);
        assertFalse(bigger.equals(differentValue));

        Card differentColor = new Card(CardValue.ACE, CardColor.SPADES);
        assertFalse(bigger.equals(differentColor));
    }

    @Test
    void testEqualsJoker() {
        Card jokerCard = new JokerCard();
        Card jokerCard1 = new JokerCard();
        Card card = new Card(CardValue.NINE, CardColor.CLUBS);

        assertTrue(jokerCard.equals(jokerCard1));
        assertFalse(jokerCard.equals(card));
    }

    @Test
    void testJokerValidModes() {
        JokerCard joker = new JokerCard();

        List<JokerMode> validModes = joker.getValidModes(0);
        assertEquals(2, validModes.size());
        assertTrue(validModes.contains(JokerMode.GIVE));
        assertTrue(validModes.contains(JokerMode.TAKE));

        validModes = joker.getValidModes(3);
        assertEquals(2, validModes.size());
        assertTrue(validModes.contains(JokerMode.UNDER));
        assertTrue(validModes.contains(JokerMode.OVER));
    }
}