package com.joker.game;

import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.services.game.GameServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GameBasic implements Table{

    protected enum TableState{
        CALL_SUPERIOR,
        DECLARE,
        PLAY
    }

    private static final Logger log = LoggerFactory.getLogger(GameBasic.class);

    protected List<Card> cards;
    protected Player[] players;
    protected int[][] declaresGrid;
    protected int[][] scoresGrid;
    protected int[][] sumsGrid;

    protected boolean[] respAlreadySent;

    boolean playedCardFlag;
    protected boolean[] playedCardsSent;

    protected TableResponse tableResp;
    /*
        player index, who should start (become
        currActivePlayer) on the next round
        values: [0 - 4]
     */
    protected int currFirstPlayer;
    /*
        player index who should take an action
        values: [0 - 4]
     */
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

    /*
        contains first 3 cards of -currFirstPlayer- in DTO state
     */
    protected ArrayList<CardDTO> threeCardList;

    private int version = 0;
    private final Lock lock = new ReentrantLock();
    /*
        ----------------------------------------------------------------------
        Method Implementations
        ----------------------------------------------------------------------
     */

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

        respAlreadySent = new boolean[]{ false, false, false, false };

        playedCardFlag = false;
        playedCardsSent = new boolean[]{ false, false, false, false };
        initCards();
    }


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

    protected void shuffle(){
        threeCardList = new ArrayList<>(3);
        shuffle(9, true);
    }

    protected void shuffle(int nCards){
        Card superiorCard = cards.get(NUM_PLAYERS * nCards);
        setSuperiorCard(superiorCard);
        shuffle(nCards, false);
    }

    protected void shuffle(int numCards, boolean needThrees){
        Collections.shuffle(cards);
        int cardIdx = 0;
        List<List<Card>> playerCards;
        for (int i = 0; i < Table.NUM_PLAYERS; i++) {
            playerCards = new ArrayList<>(5);
            for (int j = 0; j < 5; j++) {
                playerCards.add(new ArrayList<>(9));
            }
            for (int j = cardIdx; j < cardIdx + numCards; j++) {
                if(needThrees){
                    if (i == currFirstPlayer && threeCardList.size() < 3){
                        CardDTO dtoCard = cards.get(j).convertToTransferObj();
                        threeCardList.add(dtoCard);
                    }
                }
                Card curr = cards.get(j);
                int idx = ((curr instanceof JokerCard) ? 4 : curr.color.ordinal());
                playerCards.get(idx).add(curr);
            }
            players[i].setDealtCards(playerCards);
            cardIdx += numCards;
        }
    }

    /**
     * finds the player index by the given id
     * @param id by which the player was initialized
     * @return index in players array
     */
    @Override
    public synchronized int getIndex(long id){
        for(int i = 0; i < players.length; i++){
            if(players[i].getId() == id) return i;
        }

        log.error("invalid player id");
        return -1;
    }

    @Override
    public int getVersion() {
        lock.lock();
        int retVal = version;
        lock.unlock();
        return retVal;
    }

    protected void increaseVersion(){
        lock.lock();
        ++version;
        lock.unlock();
    }

    /**
     * Helper function for returning table response
     * Used for controlling round/stage score updates
     * !
     * Sets flag for .checkResponseFlag() method when scores
     * grid had been updated and also sent to the caller
     *
     * @param idx which player has called .getTable()
     */
    protected void updateSentFlag(int idx) {
        if(tableResp.getRoundFinished().get(idx)){
            respAlreadySent[idx] = true;
        }
    }

    /**
     * Helper function for returning table response
     * Used for controlling round/stage score updates
     * !
     * checks whether scores have been sent or not, if yes
     * sets their booleans to false so they don't get re-drawn
     * again on the next .getTable() from the same user
     *
     * @param idx which player has called .getTable()
     */
    protected void checkResponseFlag(int idx) {
        if(respAlreadySent[idx]){
            tableResp.getRoundFinished().set(idx, false);
            tableResp.getStageFinished().set(idx, false);
            respAlreadySent[idx] = false;
            for (int i = 0; i < 4; i++) {
                players[i].resetTaken();
                tableResp.getTaken().set(i, 0);
            }
        }
    }


}
