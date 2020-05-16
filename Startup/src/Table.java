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
     * @param id
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
     * @param x
     */
    void declare(int x);

    /**
     * Gets card and puts card
     * @param card
     */
    void putCard(Card card);

    /**
     * Calculates and updates scores
     */
    void updateScores();
}
