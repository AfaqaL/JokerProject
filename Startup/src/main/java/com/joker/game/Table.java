package com.joker.game;

import com.joker.model.dto.TableResponse;

import java.util.List;

public interface Table {
    int NUM_STAGES = 4;
    int NUM_PLAYERS = 4;
    int MAX_CARDS_PLAYED = 4;

    /**
     * at the beginning of a round sets which color
     * card is the superior one, so other cards can
     * get their priorities set easily
     *
     * @param card which is the superior card in this round
     */
    void setSuperiorCard(Card card);

    /**
     * takes a declaration request from server about
     * how much a certain player wants to call that round
     *
     * @param x how much current player wants to call
     */
    void declareNumber(int x);

    /**
     * takes a put card request from server and
     * gives that info to the player class
     *
     * @param card player requested card from the server to be put
     */
    void putCard(Card card);

    /**
     * calculates each players taken score after every round
     *
     * @return array of size 4 with each players score in it
     */
    void setRoundScores();

    /**
     * after every 4 round calculates total score
     * of every player in that stage
     *
     * @return array of size 2/4 with each team/player score in that stage
     */
    void setStageScores();

    /**
     * calculates all stage score
     *
     * @return array of size 2/4 with each team/player total scores
     */
    void setFinalScore();

    /**
     * used for communication with JS
     * @return table state (contains current
     * table state, updated with every move)
     */
    TableResponse getTable(long playerId);

    int getVersion();

    int getIndex(long playerId);
}
