import java.util.Scanner;

public class Play {

    //TODO: add correct cycles !
    public static void main(String[] args){
        //server gives 4 players
        Scanner scanner = new Scanner(System.in);
        System.out.println("First id: ");
        int id1 = scanner.nextInt();
        System.out.println("second id: ");
        int id2 = scanner.nextInt();
        System.out.println("third id: ");
        int id3 = scanner.nextInt();
        System.out.println("fourth id: ");
        int id4 = scanner.nextInt();

        Table table = new TableNines(id1, id2, id3, id4);

        //server gets first player's 3 cards
        // TODO: add this functional

        //server gives superior color
        System.out.println("First player gives superior color: ");
        int color = scanner.nextInt();
        table.setSuperiorCard(color);
        //server gives 4 player declarations

        for (int i = 0; i < 4; i++) {
            System.out.println("Curr player should declare!");
            table.declareNumber(scanner.nextInt());
        }
        //server gives first player move
        System.out.println("First player makes a move: (2 ints, value and color)");
        int val = scanner.nextInt();
        int col = scanner.nextInt();
        table.putCard(new Card(val, col));
        //..etc

        //first round is done
        //calculates scores
        int[] roundScore = table.getRoundScores();
        //server gets scores of that round
        for (int i = 0; i < 4; i++) {
            System.out.print(roundScore[i] + " ");
        }
        System.out.println();
        //first player changes

        //repeat
    }
}
