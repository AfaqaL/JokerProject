package com.joker.game;

import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.PlayAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameNines extends GameBasic {

    private static final Logger log = LoggerFactory.getLogger(GameNines.class);

    private static final int NUM_ROUNDS = 16;
    private static final int ROUNDS_PER_STAGE = 4;
    private static final int CARDS_PER_ROUND = 9;

    /* how much have other players already declared [0 - 9] */
    private int alreadyDeclared;

    /* how many players did already declare */
    private int numPlayersDeclared;

    private TableState currTableState;

    private int bayonet;

    private Card first;

    public GameNines(List<User> users, int bayonet){
        super(users);

        initGrids();

        initTableResp();

        initVariables(bayonet);

        shuffle();
    }

    private void initVariables(int bayonet) {
        this.bayonet = bayonet;
        alreadyDeclared = 0;
        numPlayersDeclared = 0;
        currRound = 0;
        currStage = 0;
        currTableState = TableState.CALL_SUPERIOR;
        tableResp.setGameFinished(false);
        tableResp.setInvalidCall(-1);
    }

    private void initTableResp() {
        tableResp = new TableResponse();
        List<Integer> declares = new ArrayList<>(Arrays.asList(-1, -1, -1, -1));
        tableResp.setDeclares(declares);

        List<CardDTO> playedCards = new ArrayList<>();
        for (int i = 0; i < MAX_CARDS_PLAYED; i++) {
            CardDTO empty = new CardDTO();
            empty.setColor(CardColor.NO_COLOR);
            empty.setValue(CardValue.ACE);
            playedCards.add(empty);
        }
        tableResp.setPlayedCards(playedCards);

        List<Integer> scores = new ArrayList<>(Arrays.asList(-1, -1, -1 , -1));
        tableResp.setScores(scores);

        List<Integer> stageScores = new ArrayList<>(Arrays.asList(-1, -1, -1 , -1));
        tableResp.setStageScores(stageScores);

        List<Integer> taken = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        tableResp.setTaken(taken);

        List<Boolean> updateScores = new ArrayList<>(Arrays.asList(false, false, false, false));
        tableResp.setRoundFinished(updateScores);

        List<Boolean> updateStage = new ArrayList<>(Arrays.asList(false, false, false, false));
        tableResp.setStageFinished(updateStage);



    }

    private void initGrids() {
        declaresGrid = new int[NUM_ROUNDS][NUM_PLAYERS];
        scoresGrid = new int[NUM_ROUNDS][NUM_PLAYERS];
        sumsGrid = new int[NUM_STAGES][NUM_PLAYERS];
    }


    @Override
    public void setSuperiorCard(Card card) {
        superior = card.color;
        tableResp.setSuperior(card.convertToTransferObj());
        currTableState = TableState.DECLARE;
        increaseVersion();
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
            currTableState = TableState.PLAY;
            setAllValid();
        }else{
            tableResp.setToFill(CARDS_PER_ROUND - alreadyDeclared);
            tableResp.setInvalidCall(-1);
        }
        ++currActivePlayer;
        currActivePlayer %= 4;

        numPlayersDeclared++;

        increaseVersion();

    }

    private void setAllValid() {
        for (Player p : players){
            p.setValidityForAll(true);
        }
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
            resetDeclares();
            currTakerCard = card;
            first = card;
            currTaker = currActivePlayer;
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
            playedCardFlag = true;
            Arrays.fill(playedCardsSent, true);
            ++totalCardsTaken;
            List<Integer> taken = tableResp.getTaken();
            int val = taken.get(currTaker) + 1;
            taken.set(currTaker, val);
            currActivePlayer = currTaker;
            first = null;
            setAllValid();
        }

        if(totalCardsTaken == CARDS_PER_ROUND){
            setRoundScores();
            shuffle();
            currTableState = TableState.CALL_SUPERIOR;
        }

        increaseVersion();
    }

    private void resetDeclares() {
        List<Integer> declares = tableResp.getDeclares();
        for (int i = 0; i < declares.size(); i++) {
            declares.set(i, -1);
        }
    }

    @Override
    public void setRoundScores() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            scoresGrid[currRound][i] = players[i].getScore(CARDS_PER_ROUND, bayonet);
        }
        List<Integer> scores = tableResp.getScores();
        for (int i = 0; i < scores.size(); i++) {
            scores.set(i, scoresGrid[currRound][i]);
        }
        ++currRound;
        prepareHand();

        flagUpdateBooleans(tableResp.getRoundFinished());

        currFirstPlayer++;
        currFirstPlayer %= NUM_PLAYERS;
        if(currRound % 4 == 0){
            setStageScores();
        }
    }

    private void flagUpdateBooleans(List<Boolean> toUpdate) {
        for (int i = 0; i < toUpdate.size(); i++) {
            toUpdate.set(i, true);
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

        flagUpdateBooleans(tableResp.getStageFinished());

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
        tableResp.setGameFinished(true);
    }

    @Override
    public TableResponse getTable(long playerId) {
        int idx = getIndex(playerId);

        tableResp.setFirst(cardsPut > 0);

        if (first != null) {

            players[idx].setValidCards(first, superior);
        }

        tableResp.setCurrentRound(currRound);
        tableResp.setCurrentStage(currStage);

        checkResponseFlag(idx);

        if(playedCardFlag)
            checkPlayedCardFlag(idx);

        if(currTableState == TableState.CALL_SUPERIOR){
            if(idx == currFirstPlayer){
                tableResp.setCards(threeCardList);
                tableResp.setAction(PlayAction.DECLARE_SUPERIOR);

            }else{
                tableResp.setCards(new ArrayList<>());
                tableResp.setAction(PlayAction.WAIT);
            }
        }else{
            List<Card> ls = players[idx].getPlayerCards();
            List<CardDTO> dtoCards = new ArrayList<>(ls.size());
            for (Card c : ls){
                dtoCards.add(c.convertToTransferObj());
            }
            tableResp.setCards(dtoCards);
            if(currActivePlayer != idx){
                tableResp.setAction(PlayAction.WAIT);
            }else{
                PlayAction playerAct;
                switch (currTableState){
                    case CALL_SUPERIOR:
                        playerAct = PlayAction.DECLARE_SUPERIOR;
                        break;
                    case DECLARE:
                        playerAct = PlayAction.DECLARE;
                        break;
                    case PLAY:
                        playerAct = PlayAction.PUT;
                        break;
                    default:
                        log.error("Switch error: Unknown table state ! (state name: \"" + currTableState.name() + "\")");
                        playerAct = null;
                        break;
                }
                tableResp.setAction(playerAct);
            }
        }
        updateSentFlag(idx);
        tableResp.setPlayerIndex(idx);
        return tableResp;
    }

    private void checkPlayedCardFlag(int idx) {
        if(playedCardsSent[idx]){
            playedCardsSent[idx] = false;
        }else{
            for(boolean notSent : playedCardsSent){
                if(notSent)
                    return;
            }
            resetAfterTake();
            playedCardFlag = false;
        }
    }




}
