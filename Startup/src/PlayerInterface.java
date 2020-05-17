import java.util.ArrayList;

public interface PlayerInterface {
    /**
     * @return id of the player
     */
    String getId();

    @Override
    String toString();

    /**
     * Set the hand for a player
     */
    void setHand();

    /**
     * @return card combination owned by a player
     */
    ArrayList <Card> getCurrentHand();

    /**
     * @return player's score;
     */
    double getScore();
}
