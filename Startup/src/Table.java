import java.util.List;

public interface Table {
    int NUM_STAGES = 4;
    int NUM_PLAYERS = 4;

    /**
     * shuffles cards for that round and then
     * deals them to the players
     * @return true if the game is still going, false otherwise
     */
    boolean shuffleCards();

    /**
     * after every round sets every counter to
     * zero and sets next player as a dealer
     */
    void startRound();
    
    /**
     * gives server first 3 cards of the player
     * who calls the superior card
     * @return List of 3 cards to call superior from
     */
    List<Card> getFirst3();

    /**
     * at the beginning of a round sets which color
     * card is the superior one, so other cards can
     * get their priorities set easily
     * @param color which is the superior card in this round
     */
    void setSuperiorCard(int color);

    /**
     * gives server the list of cards for the player,
     * containing valid and invalid cards for that turn
     * @return list of valid and invalid cards
     */
    List<Card> getUserCards();

    /**
     * notifies the server which user can take action
     * @return id of the currently active player
     */
    int getActiveUser();

    /**
     * takes a declaration request from server about
     * how much a certain player wants to call that round
     * @param x how much current player wants to call
     * @return for the 4th player, returns the value which can not be declared, -1 otherwise
     */
    int declareNumber(int x);

    /**
     * takes a put card request from server and
     * gives that info to the player class
     * @param card player requested card from the server to be put
     */
    void putCard(Card card);

    /**
     * calculates each players taken score after every round
     * @return array of size 4 with each players score in it
     */
    int[] getRoundScores();

    /**
     * if a stage is finished or not
     * @return true if 4 rounds have been played, false otherwise
     */
    boolean isStageFinished();

    /**
     * after every 4 round calculates total score
     * of every player in that stage
     * @return array of size 2/4 with each team/player score in that stage
     */
    int[] getStageScores();

    /**
     * calculates all stage score
     * @return array of size 2/4 with each team/player total scores
     */
    int[] getFinalScores();
}
