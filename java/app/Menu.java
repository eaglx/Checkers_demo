//TODO: Graphic interface, AI.

package app;

import java.util.Scanner;
import data.Options;

public class Menu {

	public static void main(String[] args) {
		System.out.println("jCheckers v0.5");
		controlLoop();
		System.out.println("\nSee you soon!!!!!");
	}

	private static void controlLoop(){
		Scanner input=new Scanner(System.in);
		Options option=null;
		boolean loopExit=false;

		while(!loopExit){
			printOptions();
			System.out.print("Your choice: ");
			option=Options.createFromInt(input.nextInt());
			input.nextLine();
			switch(option){
			case NEWGAME:
				Game game=new Game();
				game.play(input);
				break;
			case EXIT:
				loopExit=true;
				break;
			}
		}
		input.close();
	}

	private static void printOptions(){
		System.out.println("Menu:");
		for(Options option: Options.values()){
			System.out.print("\t");
			System.out.println(option);
		}
	}
}
