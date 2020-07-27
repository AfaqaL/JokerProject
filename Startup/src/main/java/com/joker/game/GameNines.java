package com.joker.game;

import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.PlayAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameNines extends GameBasic {
    private static final int NUM_ROUNDS = 16;
    private static final int ROUNDS_PER_STAGE = 4;
    private static final int CARDS_PER_ROUND = 9;

    /* how much have other players already declared [0 - 9] */
    private int alreadyDeclared;

    /* how many players did already declare */
    private int numPlayersDeclared;

    TableResponse tableResp;

    public GameNines(List<User> users, int bayonet){
        super(users);

        initGrids();

        initTableResp();

        initVariables();
    }

    private void initVariables() {
        alreadyDeclared = 0;
        numPlayersDeclared = 0;
        currRound = 0;
        currStage = 0;
    }

    private void initTableResp() {
        tableResp = new TableResponse();
        List<Integer> declares = new ArrayList<>(Arrays.asList(-1, -1, -1, -1));
        tableResp.setDeclares(declares);

        List<CardDTO> playedCards = new ArrayList<>();
        CardDTO empty = new CardDTO();
        empty.setColor(CardColor.NO_COLOR);
        empty.setValue(CardValue.ACE);
        for (int i = 0; i < MAX_CARDS_PLAYED; i++) {
            playedCards.add(empty);
        }
        tableResp.setPlayedCards(playedCards);

        List<Integer> scores = new ArrayList<>(Arrays.asList(-1, -1, -1 , -1));
        tableResp.setScores(scores);

        List<Integer> stageScores = new ArrayList<>(Arrays.asList(-1, -1, -1 , -1));
        tableResp.setStageScores(stageScores);


    }

    private void initGrids() {
        declaresGrid = new int[NUM_ROUNDS][NUM_PLAYERS];
        scoresGrid = new int[NUM_ROUNDS][NUM_PLAYERS];
        sumsGrid = new int[NUM_STAGES][NUM_PLAYERS];
    }

    @Override
    public void shuffleCards() {
        super.shuffle(CARDS_PER_ROUND);
    }

    @Override
    public void startRound() {

    }

    @Override
    public void setFirst3() {
        List<Card> three = players[currActivePlayer].getThreeCards();

        List<CardDTO> cards = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            CardDTO card = new CardDTO();
            card.setColor(three.get(i).color);
            card.setValue(three.get(i).value);
            cards.add(card);
        }
        tableResp.setCards(cards);
    }

    @Override
    public void setSuperiorCard(CardColor color) {
        superior = color;
    }

    @Override
    public List<Card> getUserCards() {
        return null;
    }

    @Override
    public int getActiveUser() {
        return 0;
    }

    /**
     * before every deal prepares the values for all players
     * (In this case it's called 16 times)
     */
    private void prepareHand(){
        tableResp.setToFill(CARDS_PER_ROUND);
        tableResp.setInvalidCall(-1);
        numPlayersDeclared = 0;
        alreadyDeclared = 0;
    }


    /**
     * before this method table info should be received and
     * toFill value should be displayed to the player
     * --Read toFill documentation for more details
     * @param x how much current player wants to call
     */
    @Override
    public void declareNumber(int x) {
        players[currActivePlayer].setDeclared(x);
        tableResp.getDeclares().set(currActivePlayer, x);

        alreadyDeclared += x;

        if(numPlayersDeclared == 2){
            tableResp.setToFill(-1);
            tableResp.setInvalidCall(CARDS_PER_ROUND - alreadyDeclared);
        }else if(numPlayersDeclared == 3){
            tableResp.setOddLeft(CARDS_PER_ROUND - alreadyDeclared);
            tableResp.setAction(PlayAction.WAIT);
        }else{
            tableResp.setToFill(CARDS_PER_ROUND - alreadyDeclared);
            tableResp.setInvalidCall(-1);
        }
        ++currActivePlayer;
        currActivePlayer %= 4;

        numPlayersDeclared++;

    }

    /**
     * after each 4-move (when a player takes) resets the
     * values for next move in the same round
     * (In this case it gets called 9 times each (16) round)
     */
    private void resetAfterTake(){
        for (CardDTO card : tableResp.getPlayedCards()){
            card.setColor(CardColor.NO_COLOR);
            card.setValue(CardValue.ACE);
        }
    }

    @Override
    public void putCard(Card card) {
        players[currActivePlayer].removeCard(card);

        if(cardsPut == 0){
            currTakerCard = card;
            currTaker = currActivePlayer;
            resetAfterTake();
        }else{
            int cmpRes = card.compare(currTakerCard, superior);
            if(cmpRes > 0){
                currTakerCard = card;
                currTaker = currActivePlayer;
            }
        }

        List<CardDTO> played = tableResp.getPlayedCards();
        CardDTO currentCard = played.get(currActivePlayer);
        currentCard.setValue(card.value);
        currentCard.setColor(card.color);

        ++currActivePlayer;
        currActivePlayer %= 4;

        ++cardsPut;

        if(cardsPut == 4){
            players[currTaker].increaseTaken();
            cardsPut = 0;
            ++totalCardsTaken;
        }

        if(totalCardsTaken == CARDS_PER_ROUND){
            setRoundScores();
        }
    }

    @Override
    public void setRoundScores() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            scoresGrid[currRound][i] = players[i].getScore(CARDS_PER_ROUND);
        }
        List<Integer> scores = tableResp.getScores();
        for (int i = 0; i < scores.size(); i++) {
            scores.set(i, scoresGrid[currRound][i]);
        }
        ++currRound;
        prepareHand();
        if(currRound % 4 == 0){
            setStageScores();
        }
    }


    @Override
    public void setStageScores() {
        int offset = currStage * ROUNDS_PER_STAGE;
        for (int i = 0; i < NUM_PLAYERS; i++) {
            for (int j = 0; j < ROUNDS_PER_STAGE; j++) {
                sumsGrid[currStage][i] += scoresGrid[offset + j][i];
            }
        }

        List<Integer> stageScores = tableResp.getStageScores();
        for (int i = 0; i < stageScores.size(); i++) {
            stageScores.set(i, sumsGrid[currStage][i]);
        }

        ++currStage;

        if(currStage == 4){
            setFinalScore();
        }
    }

    @Override
    public void setFinalScore() {
        List<Integer> finalScores = new ArrayList<>(4);
        for (int i = 0; i < NUM_PLAYERS; i++) {
            int res = 0;
            for (int j = 0; j < NUM_STAGES; j++) {
                res += sumsGrid[j][i];
                finalScores.add(res);
            }
        }
        tableResp.setFinalScores(finalScores);
    }

    @Override
    public TableResponse getTable(long playerId) {
                //TODO:
        return tableResp;
    }

    @Override
    public int getVersion() {
        return 0;
    }
}
