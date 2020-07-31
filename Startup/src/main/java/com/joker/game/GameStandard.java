package com.joker.game;

import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.GameMode;
import com.joker.model.enums.PlayAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameStandard extends GameBasic {

    private static final Logger log = LoggerFactory.getLogger(GameStandard.class);


    private enum CurrentMode{
        FIR_NINES,
        SEC_NINES,
        TO_EIGHTS,
        FROM_EIGHTS
    }
    private final int N_ROUNDS = 24;
    private int currMaxCards;
    CurrentMode mode;
    TableState state;
    private int alreadyDeclared;
    private int numPlayersDeclared;
    private Card first;
    private int bayonet;

    /*
        when its NINES mode, this integer gets increased
        to 4, when it reaches 4 the game mode changes
        back to Eights and value resets back to 0
     */
    private int ninesPlayed;


    public GameStandard(List<User> users, int bayonet) {
        super(users);
        mode = CurrentMode.TO_EIGHTS;
        state = TableState.DECLARE;
        currMaxCards = 1;
        alreadyDeclared = 0;
        numPlayersDeclared = 0;
        ninesPlayed = 0;
        this.bayonet = bayonet;
        initTableResp();
        initGrids();
        tableResp.setGameFinished(false);
        tableResp.setInvalidCall(-1);
        shuffle(currMaxCards);
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
        declaresGrid = new int[N_ROUNDS][NUM_PLAYERS];
        scoresGrid = new int[N_ROUNDS][NUM_PLAYERS];
        sumsGrid = new int[NUM_STAGES][NUM_PLAYERS];
    }

    @Override
    public synchronized void setSuperiorCard(Card card) {
        if(mode == CurrentMode.FIR_NINES || mode == CurrentMode.SEC_NINES){
            if(state != TableState.CALL_SUPERIOR)
                return;
        }
        tableResp.setSuperior(card.convertToTransferObj());
        superior = card.color;
        state = TableState.DECLARE;
    }

    @Override
    public synchronized void declareNumber(int x, long playerId) {
        int index = getIndex(playerId);
        if(index != currActivePlayer || state == TableState.CALL_SUPERIOR) {
            log.warn("Player broke their mice!");
            return;
        }
        players[currActivePlayer].setDeclared(x);
        tableResp.getDeclares().set(currActivePlayer, x);

        alreadyDeclared += x;

        if(numPlayersDeclared == 2){
            tableResp.setToFill(-1);
            tableResp.setInvalidCall(currMaxCards - alreadyDeclared);
        }else if(numPlayersDeclared == 3){
            tableResp.setOddLeft(currMaxCards - alreadyDeclared);
            tableResp.setAction(PlayAction.WAIT);
            state = TableState.PLAY;
            setAllValid();
        }else{
            tableResp.setToFill(currMaxCards - alreadyDeclared);
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

    private void resetDeclares() {
        List<Integer> declares = tableResp.getDeclares();
        for (int i = 0; i < declares.size(); i++) {
            declares.set(i, -1);
        }
    }

    @Override
    public synchronized void putCard(Card card, long playerId) {
        if(state != TableState.PLAY)
            return;
        int index = getIndex(playerId);
        if(index != currActivePlayer || state == TableState.CALL_SUPERIOR) {
            log.warn("Player broke their mice!");
            return;
        }


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
        played.set(currActivePlayer, card.convertToTransferObj());

        ++currActivePlayer;
        currActivePlayer %= NUM_PLAYERS;

        ++cardsPut;

        if(cardsPut == NUM_PLAYERS){
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

        if(totalCardsTaken == currMaxCards){
            setRoundScores(currMaxCards);
            currActivePlayer = currFirstPlayer;
            totalCardsTaken = 0;

            if(mode == CurrentMode.FIR_NINES || mode == CurrentMode.SEC_NINES){
                ++ninesPlayed;
                state = TableState.CALL_SUPERIOR;
                if(ninesPlayed == 4){
                    mode = CurrentMode.FROM_EIGHTS;
                    ninesPlayed = 0;
                    currMaxCards = 8;
                    shuffle(currMaxCards);
                    state = TableState.DECLARE;
                }
                shuffle();
            }else{
                currMaxCards += (mode == CurrentMode.TO_EIGHTS ? 1 : -1) ;
                if(currMaxCards == 9){
                    state = TableState.CALL_SUPERIOR;
                    mode = CurrentMode.FIR_NINES;
                    shuffle();
                }else if(currMaxCards == 0) {
                    state = TableState.CALL_SUPERIOR;
                    currMaxCards = 9;
                    mode = CurrentMode.SEC_NINES;
                }else{
                    shuffle(currMaxCards);
                }
            }

        }

        increaseVersion();
    }
    private void prepareHand(){
        tableResp.setToFill(currMaxCards);
        tableResp.setInvalidCall(-1);
        numPlayersDeclared = 0;
        alreadyDeclared = 0;
    }

    private void flagUpdateBooleans(List<Boolean> toUpdate) {
        for (int i = 0; i < toUpdate.size(); i++) {
            toUpdate.set(i, true);
        }
    }

    private void setRoundScores(int numCards) {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            scoresGrid[currRound][i] = players[i].getScore(numCards, bayonet);
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

        switch (currRound){
            case 8:
            case 12:
            case 20:
            case 24:
                setStageScores();
                return;
        }
    }


    private void setStageScores() {
        int offset = 0;
        int roundsInCurrStage = 0;
        switch (mode){
            case TO_EIGHTS:
                offset = 0;
                roundsInCurrStage = 8;
                break;
            case FIR_NINES:
                offset = 8;
                roundsInCurrStage = 4;
                break;
            case FROM_EIGHTS:
                offset = 12;
                roundsInCurrStage = 8;
                break;
            case SEC_NINES:
                offset = 20;
                roundsInCurrStage = 4;
                break;
            default:
                log.error("Unidentified game mode !");
                break;
        }

        int streakPlayerIdx = calculateStreakPlayerIndex();

        for (int i = 0; i < NUM_PLAYERS; i++) {
            for (int j = 0; j < roundsInCurrStage; j++) {
                sumsGrid[currStage][i] += scoresGrid[offset + j][i];
            }

            if(streakPlayerIdx > -1){
                int max = 0;
                int lastRound = roundsInCurrStage - 1;
                for (int j = 0; j < lastRound; j++) {
                    max = Math.max(scoresGrid[offset + j][i], max);
                }
                if(i == streakPlayerIdx){
                    scoresGrid[offset + lastRound][i] += max;
                    sumsGrid[currStage][i] += max;
                }else{
                    scoresGrid[offset + lastRound][i] -= max;
                    sumsGrid[currStage][i] -= max;
                }
            }else if(streakPlayerIdx == -2){
                int max = 0;
                int lastRound = roundsInCurrStage - 1;
                for (int j = 0; j < lastRound; j++) {
                    max = Math.max(scoresGrid[offset + j][i], max);
                }

                if(players[i].endedOnStreak()){
                    scoresGrid[offset + lastRound][i] += max;
                    sumsGrid[currStage][i] += max;
                }
            }
        }

        resetPlayerStreaks();

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

    private void resetPlayerStreaks() {
        for(Player p : players){
            p.resetStreak();
        }
    }

    /**
     * @return index of a player finishing on streak
     *  (-1 if no one ended on streak, -2 if multiple did)
     */
    private int calculateStreakPlayerIndex() {
        int res = -1;
        for (int i = 0; i < players.length; i++) {
            if(players[i].endedOnStreak()){
                if(res != -1) return -2;

                res = i;
            }
        }
        return res;
    }

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
    public synchronized TableResponse getTable(long playerId) {
        int idx = getIndex(playerId);

        tableResp.setFirst(cardsPut == 0);

        if (first != null) {

            players[idx].setValidCards(first, superior);
        }

        switch (mode){
            case FROM_EIGHTS:
            case TO_EIGHTS:
                tableResp.setCurrentRound(currRound % 8);
                break;
            case FIR_NINES:
            case SEC_NINES:
                tableResp.setCurrentRound(currRound % 4);
                break;
            default:
                log.error("Unknown game mode !");
                break;
        }
        tableResp.setCurrentStage(currStage);

        checkResponseFlag(idx);

        if(playedCardFlag) {
            checkPlayedCardFlag(idx);
        }

            if(state == TableState.CALL_SUPERIOR){
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
                    switch (state){
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
                            log.error("Switch error: Unknown table state ! (state name: \"" + state.name() + "\")");
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

    private void resetAfterTake(){
        for (CardDTO card : tableResp.getPlayedCards()){
            card.setColor(CardColor.NO_COLOR);
            card.setValue(CardValue.ACE);
        }
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
