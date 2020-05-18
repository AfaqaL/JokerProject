import java.util.List;

public abstract class BasicTable implements Table {

    @Override
    public int[] getOrder() {
        return null;
    }

    protected boolean shuffleCards(int num) {
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
        return null;
    }

    @Override
    public int[] getFinalScores() {
        return null;
    }
}
