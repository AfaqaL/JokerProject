import java.util.List;

public interface Table {
    // Constructor: Table(int id1, int id2, int id3, int id4);

    int[] getOrder();

    boolean shuffleCards();

    Card getSuperiorCard();

    void setSuperiorCard(int color);

    List<Card> getUserCards(int id);

    int getActiveUser();

    int declareNumber(int x);

    void putCard(Card card);

    int[] getRoundScores();

    boolean isStageFinished();

    int[] getStageScores();

    int[] getFinalScores();
}
