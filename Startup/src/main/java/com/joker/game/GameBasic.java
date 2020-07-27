package com.joker.game;

import com.joker.model.User;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.services.game.GameServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class GameBasic implements Table{

    private static final Logger log = LoggerFactory.getLogger(GameServiceBean.class);

    protected List<Card> cards;
    protected Player[] players;
    protected int[][] declaresGrid;
    protected int[][] scoresGrid;
    protected int[][] sumsGrid;

    protected int currActivePlayer;

    /*
        how many cards have already been played each time [0 - 4]
        should be set to 0 after 4 moves
    */
    protected int cardsPut;

    /*
        how many cards have already been taken in total
        by each player (if it is equal to max number of cards
        on that round, then it indicates the round end)
     */
    protected int totalCardsTaken;

    /*
        current taker index [0 - 4]
        will be reset each time after 4 moves (using cardsPut)
     */
    protected int currTaker;
    /*
        current highest card (which others new cards
        should be compared to), will be reset each time
        after 4 moves (using cardsPut)
     */
    protected Card currTakerCard;

    /*
        superior color for each round (gets set either
        by players or randomly before each round starts)
     */
    protected CardColor superior;

    /*
        keeps track of which round and stage we are currently in
     */
    protected int currRound;
    protected int currStage;

    public GameBasic(List<User> users){
        if(users.size() != Table.NUM_PLAYERS){
            log.error("Table somehow contains more/less than 4 players!\n"
                            + "Processes should be aborted");
            return;
        }
        players = new Player[Table.NUM_PLAYERS];
        int first = ThreadLocalRandom.current().nextInt(0, 4);

        for (User user : users) {
            players[first++] = new Player(user.getId());
            first %= Table.NUM_PLAYERS;
        }

        initCards();
    }

    /**
     * TODO: Jokers have to be added somehow,
     *  .values() don't work! (gives 40 cards)
     *
     */
    private void initCards() {
        cards = new ArrayList<>(36);
        for (CardColor color : CardColor.values()) {
            for (CardValue val : CardValue.values()) {
                if(val == CardValue.JOKER || color == CardColor.NO_COLOR) continue;

                if(val == CardValue.SIX && (color == CardColor.SPADES || color == CardColor.CLUBS)){
                    cards.add(new JokerCard());
                    continue;
                }
                cards.add(new Card(val, color));
            }
        }
    }


    protected void shuffle(int numCards){

    }
}
