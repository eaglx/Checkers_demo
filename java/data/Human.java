package data;

import java.util.Scanner;

public class Human extends Player {
	public Human() {
		super();
	}

	public void setHumanMove(Scanner in){
		int[][] getMove = new int[2][2];

		System.out.print("Select pawn X and Y position: ");
		getMove[0][1]=in.nextInt();
		getMove[0][0]=in.nextInt();
		in.nextLine();
		System.out.print("Select destiny X and Y position: ");
		getMove[1][1]=in.nextInt();
		getMove[1][0]=in.nextInt();
		in.nextLine();
		setGetMove(getMove);
	}

	public int getHumanMove(int number1, int number2){
		return getGetMove(number1, number2);
	}
}
