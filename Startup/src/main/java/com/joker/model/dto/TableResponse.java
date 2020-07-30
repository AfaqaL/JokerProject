package com.joker.model.dto;

import com.joker.model.enums.PlayAction;

import java.util.List;

public class TableResponse {

    private long id;

    private boolean changed;

    private boolean first;
    /**
     * Transfer Type Card, containing info for
     * current superior card (in this round)
     */
    private CardDTO superior;


    /**
     * if size == 3 then it's for the first
     * player to set superior card
     * Cards of the Current Active user
     */
    private List<CardDTO> cards;

    /**
     * Cards which have been played already
     * Size is always 4 and cards are set according
     * to the player indexes, if the players have not
     * played their card, that index has null as a value
     */
    private List<CardDTO> playedCards;

    /**
     * List of calls for individual players
     * !
     * Size is always 4 and values are set according to
     * the player indexes, if they have not called yet,
     * then the value is set to -1
     */
    private List<Integer> declares;

    /**
     * List of how many cards has each player taken
     * !
     * Size is always 4 and values are set according to
     * the player indexes (no special value, always player
     * has either 0 or something taken for them)
     */
    private List<Integer> taken;

    private int currentRound;


    /**
     * returns the list of scores for the current round
     * !
     * Only gets updated after the round is finished
     */
    private List<Integer> scores;

    private List<Boolean> roundFinished;
    /**
     * Current Stage of game
     */
    private int currentStage;


    /**
     * contains total scores for the current stage
     * !
     * Only gets updated after round is finished, otherwise
     * contains -1 or previous stage's scores
     */
    private List<Integer> stageScores;

    private List<Boolean> stageFinished;

    /**
     * contains the total scores of each player after
     * the whole game is finished
     * !
     * only gets initialised after the game is finished
     * otherwise will be null !!!
     */
    private List<Integer> finalScores;

    private boolean gameFinished;

    /**
     * Contains the index of the player calling
     * GetTable method relative to other players
     * on the table (Values are 0-3)
     */
    private int playerIndex;

    /**
     * how many current player needs to call to fill for last
     * if toFill == -1 then it's last player's move
     */
    private int toFill;

    /**
     * how many the last player cant call (should be called
     * only when toFill is -1, will reset just in case)
     */
    private int invalidCall;

    /**
     * how many is left that no one needs to take
     * (value is negative if more than maximum was declared)
     */
    private int oddLeft;

    /**
     * Contains the state which the player is
     * currently in (Screen should be re-drawn
     * according to this field's value)
     */
    private PlayAction action;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CardDTO getSuperior() {
        return superior;
    }

    public void setSuperior(CardDTO superior) {
        this.superior = superior;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public List<CardDTO> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<CardDTO> playedCards) {
        this.playedCards = playedCards;
    }

    public List<Integer> getDeclares() {
        return declares;
    }

    public void setDeclares(List<Integer> declares) {
        this.declares = declares;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public List<Integer> getTaken() {
        return taken;
    }

    public void setTaken(List<Integer> taken) {
        this.taken = taken;
    }

    public int getToFill() {
        return toFill;
    }

    public void setToFill(int toFill) {
        this.toFill = toFill;
    }

    public PlayAction getAction() {
        return action;
    }

    public void setAction(PlayAction action) {
        this.action = action;
    }

    public int getInvalidCall() {
        return invalidCall;
    }

    public void setInvalidCall(int invalidCall) {
        this.invalidCall = invalidCall;
    }

    public List<Integer> getStageScores() {
        return stageScores;
    }

    public void setStageScores(List<Integer> stageScores) {
        this.stageScores = stageScores;
    }

    public List<Integer> getFinalScores() {
        return finalScores;
    }

    public void setFinalScores(List<Integer> finalScores) {
        this.finalScores = finalScores;
    }

    public int getOddLeft() {
        return oddLeft;
    }

    public void setOddLeft(int oddLeft) {
        this.oddLeft = oddLeft;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public List<Boolean> getRoundFinished() {
        return roundFinished;
    }

    public void setRoundFinished(List<Boolean> roundFinished) {
        this.roundFinished = roundFinished;
    }

    public List<Boolean> getStageFinished() {
        return stageFinished;
    }

    public void setStageFinished(List<Boolean> stageFinished) {
        this.stageFinished = stageFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public int getCurrentStage() { return currentStage; }

    public void setCurrentStage(int currentStage) { this.currentStage = currentStage; }

    public int getCurrentRound() { return currentRound; }

    public void setCurrentRound(int currentRound) { this.currentRound = currentRound; }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public boolean isFirst() { return first; }

    public void setFirst(boolean first) { this.first = first; }
}
