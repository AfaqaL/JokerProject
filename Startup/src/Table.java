import java.util.List;

public interface Table {

    /**
     * @return number of rounds in the table
     */
    int getNumRounds();

    /**
     * Random cards and save in players objects
     */
    void shuffle();

    /**
     * @return number of cards in this round
     */
    int getNumCards();

    /**
     * @param id - id of the player
     * @return list of valid cards for the user#id
     */
    List<Card> getCards(int id);

    /**
     * @return id of the current user(whose turn)
     */
    int getCurrentUser();

    /**
     * Saves declared number for current user
     * in the grid
     * @param x - the number declared by player
     */
    void declare(int x);

    /**
     * Gets card and puts card
     * @param card - the card chosen by the player
     */
    void putCard(Card card);

    /**
     * Calculates and updates scores
     */
    void updateScores();

    /**
     * @return String shows which round and which play;
     */
    String getCurrentRound();

    /**
     * Shuffle deck and determine who will be first;
     * @return id -> id of player who starts;
     */
    int determineFirst();

    /**
     * @return superior -> card color
     */
    int getSuperior();

    /**
     * @param p1 -> player declares superior
     */
     void declareSuperior(Player p1);

    /**
     * @param round -> round to be played;
     */
    void playRound(int round);

    /**
     * Every player plays one card;
     */
    void playTurn();
    /**
     * @return id of the player who won current turn
     */
    int winnerOfTheturn();

    /**
     * @return the player and place they have won;
     */
    String eventualScores();
}
