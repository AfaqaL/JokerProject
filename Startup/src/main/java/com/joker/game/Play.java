package com.joker.game;

import java.util.List;
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

		for (int round = 0; round < TableNines.ROUNDS; round++) {
			table.shuffleCards();
			//server gets first player's 3 cards
			// TODO: add this functional
			List<Card> first3 = table.getFirst3();
			System.out.println(first3);
			//server gives superior color
			System.out.println("First player gives superior color: ");
			int color = scanner.nextInt();
			table.setSuperiorCard(color);
			
			//server gives 4 player declarations
            System.out.println(table.toString());
			int invDeclare = -1;
			for (int i = 0; i < 4; i++) {
				System.out.println(table.getActiveUser() + " player should declare" +
						(invDeclare == -1 ? "!" : (" anything but " + invDeclare)));
				invDeclare = table.declareNumber(scanner.nextInt());
			}
			//server gives first player move
            for (int i = 0; i < 9; i++) {
                System.out.println(table.toString());
                for (int j = 0; j < 4; j++) {
                    System.out.println(table.getActiveUser() + " player makes a move: (2 ints, value and color)");
                    int val = scanner.nextInt();
                    int col = scanner.nextInt();
                    table.putCard(new Card(val, col));
                }
            }
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
}
