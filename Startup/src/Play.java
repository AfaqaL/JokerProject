import java.util.List;

public class Play {
    private int smth;
    public static void main(String[] args){
        Table table = null;
        int numRounds = table.getNumRounds();
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= numRounds; i++) {
            System.out.println("Current round: " + numRounds);
            table.shuffle();
            int numCards = table.getNumCards();
            for (int j = 1; j < 5; j++) {
                List<Card> myCards = table.getCards(j);

                System.out.print("User" + j + ": ");
                for (Card c : myCards) {
                    System.out.println(c);
                }
            }

            for (int j = 1; j < 5; j++) {
                int whoseTurn = table.getCurrentUser();
                System.out.println("User" + whoseTurn + "--->");
                int x = scanner.nextInt();

                table.declare(x);
            }

            for (int k = 0; k < numCards; k++) {
                for (int j = 1; j < 5; j++) {
                    int whoseTurn = table.getCurrentUser();
                    List<Card> myCards = table.getCards(j);

                    System.out.print("User" + j + " valid cards: ");
                    for (Card c : myCards) {
                        System.out.println(c);
                    }

                    System.out.println("User choose card: ");
                    int x = scanner.nextInt();
                    table.putCard(new Card(x));
                }
            }
            // calculate scores
            table.updateScores();
        }
    }
}
