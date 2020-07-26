package com.joker.game;

import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;

import java.util.List;

public class TableNines extends BasicTable {
    public static final int ROUNDS = 16;
    public static final int TURNS_PER_STAGE = 4;
    public static final int CARDS_PER_TURN = 9;

    private int currRound;
    private int playersMoved;
    private int currActivePlayer;
    private int alreadyDeclared;

    public TableNines(long id1,long id2,long id3,long id4) {
        super(id1, id2, id3, id4);
        setVariables();
        initGrids();
    }

    private void setVariables() {
        currFirstPlayer = 0;
        currRound = 0;
        playersMoved = 0;
        currActivePlayer = 0;
        alreadyDeclared = 0;
    }

    private void initGrids() {
        declaresGrid = new int[ROUNDS][NUM_PLAYERS];
        scoresGrid = new int[ROUNDS][NUM_PLAYERS];
        sumsGrid = new int[NUM_STAGES][NUM_PLAYERS];
    }

    @Override
    public void startRound() {
        currActivePlayer = ++currFirstPlayer;
        currRound++;
        alreadyDeclared = 0;
        //TODO: split
        currTaker = null;
        currTakerID = -1;
    }


    @Override
    public boolean shuffleCards() {
        if (currRound == ROUNDS) return false;

        super.shuffle(CARDS_PER_TURN);
        return true;
    }

    @Override
    public void setSuperiorCard(int color) {
        CardDTO card = new CardDTO();
        card.setValue(CardValue.ACE);
        card.setColor(CardColor.CLUBS);
        tableResp.setSuperior(card);
        superior = color;
    }

    @Override
    public List<Card> getUserCards() {
        return players[currActivePlayer].getPlayerCards();
    }

    @Override
    public int getActiveUser() {
        return currActivePlayer;
    }

    @Override
    public int declareNumber(int x) {
        players[currActivePlayer++].setDeclared(x);
        currActivePlayer %= 4;
        alreadyDeclared += x;

        return ++playersMoved == 3 ? CARDS_PER_TURN - alreadyDeclared : -1;
    }

    @Override
    public void putCard(Card card) {
        players[currActivePlayer].removeCard(card);
        if (currTaker == null) {
            currTaker = card;
            firstCardColor = card.color;
            currTakerID = currActivePlayer;
            for (int others = 0; others < 4; others++) {
                if (others != currActivePlayer)
                    players[others].setValidCards(card, superior);
            }
            //might need to delete this line
            playersMoved = 0;
        } else {
            int res = card.compare(currTaker, superior);
            if (res > 0) {
                currTaker = card;
                currTakerID = currActivePlayer;
            }
        }

        if (++playersMoved == 4) {
            players[currTakerID].increaseTaken();
            currActivePlayer = currTakerID;
            playersMoved = 0;
            return;
        }
        currActivePlayer++;
    }

    @Override
    public int[] getRoundScores() {
        int[] res = new int[4];
        for (int i = 0; i < 4; i++) {
            res[i] = players[i].getScore(CARDS_PER_TURN);
        }
        currRound++;
        updateSums(res);
        return res;
    }

    private void updateSums(int[] res) {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            sumsGrid[currStage][i] += res[i];
        }
    }

    @Override
    public boolean isStageFinished() {
        return currRound % 4 == 0;
    }

    @Override
    public int[] getStageScores() {
        return sumsGrid[currStage++];
    }

    @Override
    public int[] getFinalScores() {
        int[] res = new int[NUM_PLAYERS];
        for (int i = 0; i < NUM_STAGES; i++) {
            for (int j = 0; j < NUM_PLAYERS; j++) {
                res[j] = sumsGrid[i][j];
            }
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "player 0 has: " + players[0].getPlayerCards().toString();
        res += "\nplayer 1 has: " + players[1].getPlayerCards().toString();
        res += "\nplayer 2 has: " + players[2].getPlayerCards().toString();
        res += "\nplayer 3 has: " + players[3].getPlayerCards().toString();
        return res;
    }

    @Override
    public TableResponse getTable() {
        TableResponse resp = new TableResponse();
        List<Integer>
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }
}
