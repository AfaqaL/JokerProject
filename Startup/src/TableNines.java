import java.util.List;

public class TableNines extends BasicTable {
    private Player[] players;
    private int currFirstPlayer;
    private static final int ROUNDS = 16;
    private static final int TURNS_PER_STAGE = 4;
    private int currTurn;


    public TableNines(int id1, int id2, int id3, int id4){
        super(id1,id2,id3,id4);
        currFirstPlayer = 0;
    }

    @Override
    public int[] getOrder() {
        return new int[0];
    }

    @Override
    public boolean shuffleCards() {
        return false;
    }

    @Override
    public Card getSuperiorCard() {
        return null;
    }

    @Override
    public void setSuperiorCard(int color) {

    }

    @Override
    public List<Card> getUserCards(int id) {
        return null;
    }

    @Override
    public int getActiveUser() {
        return 0;
    }

    @Override
    public int declareNumber(int x) {
        return 0;
    }

    @Override
    public void putCard(Card card) {

    }

    @Override
    public int[] getRoundScores() {
        return new int[0];
    }

    @Override
    public boolean isStageFinished() {
        return false;
    }

    @Override
    public int[] getStageScores() {
        return new int[0];
    }

    @Override
    public int[] getFinalScores() {
        return new int[0];
    }
}
