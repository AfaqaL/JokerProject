import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JokerCard extends Card {
    public static final int UNDER_MODE = 0;
    public static final int TAKE_MODE = 1;
    public static final int GIVE_HIGHEST_MODE = 2;
    public static final int OVER_MODE = 3;

    public int mode;

    /**
     * Sets up a joker card
     */
    public JokerCard() {
        super(JOKER, -100);
    }

    public void setMode(int mode, int color){
        this.mode = mode;
        this.color = color;
    }

    @Override
    boolean isValid(Card first) {
        return true;
    }

    @Override
    public int compare(Card taker, int superior) {
        if (this.mode == OVER_MODE)
            return 1;
        else // UNDER_MODE
            return -1;
    }

    public List<Integer> getValidModes(int turn) {
        if (turn == 1) {
            return Arrays.asList(TAKE_MODE, GIVE_HIGHEST_MODE);
        } else {
            return Arrays.asList(UNDER_MODE, OVER_MODE);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return ((JokerCard)obj).value == JOKER;
    }
}
