package com.joker.game;

import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BasicTable implements Table {
    protected List<Card> cards;
    protected Player[] players;
    protected int[][] declaresGrid;
    protected int[][] scoresGrid;
    protected int[][] sumsGrid;

    //current stage tracked (for calculating scores)
    protected int currStage;

    //which card takes currently and which player
    protected Card currTaker;
    protected int currTakerID;

    //keeps track of superior card color and first card color (to calculate validity)
    protected CardColor firstCardColor;
    protected CardColor superior;

    //first player idx (0 .. 4) and their first 3 cards to call from
    protected int currFirstPlayer;
    protected List<Card> threeCardList;




    protected BasicTable(long id1, long id2, long id3,long id4) {
        int first = ThreadLocalRandom.current().nextInt(0, 4);
        players = new Player[4];
        players[first++] = new Player(id1);
        first %= 4;
        players[first++] = new Player(id2);
        first %= 4;
        players[first++] = new Player(id3);
        first %= 4;
        players[first] = new Player(id4);
        initCards();
    }

    private void initCards() {
//        cards = new ArrayList<>(36);
//        for (CardColor color : CardColor.values()) {
//            for (CardValue val : CardValue.values()) {
//                if(val == CardValue.JOKER) continue;
//
//                if(val == CardValue.SIX && (color == CardColor.SPADES || color == CardColor.CLUBS)){
//                    cards.add(new JokerCard());
//                    continue;
//                }
//                cards.add(new Card(val, color));
//            }
//        }
    }

    @Override
    public List<Card> getFirst3() {
        return threeCardList;
    }

    protected void shuffle(int numCards) {
        threeCardList = new ArrayList<>(3);
        Collections.shuffle(cards);
        int cardIdx = 0;
        List<List<Card>> playerCards;
        for (int i = 0; i < Table.NUM_PLAYERS; i++) {
            playerCards = new ArrayList<>(5);
            for (int j = 0; j < 5; j++) {
                playerCards.add(new ArrayList<>(9));
            }
            for (int j = cardIdx; j < cardIdx + numCards; j++) {
                if (i == currFirstPlayer && threeCardList.size() < 3)
                    threeCardList.add(cards.get(j));
                Card curr = cards.get(j);
                int idx = ((curr instanceof JokerCard) ? 4 : curr.color.ordinal());
                playerCards.get(idx).add(curr);
            }
            players[i].setDealtCards(playerCards);
            cardIdx += numCards;
        }
    }
}
