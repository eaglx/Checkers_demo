package app;

import java.io.IOException;
import java.util.Scanner;

import cpu.Ai;
import data.Board;
import data.Human;
import data.Sides;
import logic.Engine;

public class Game {
	private boolean gameEnd;
	private Sides sidePlay;
	private Sides sideAi1;
	private Sides sideAi2;
	private int pMoveValue, i;

	public void play(Scanner input){
		Board board=new Board();
		Engine engine=new Engine();
		Human playerWhite=new Human();
		Human playerBlack=new Human();
		Ai cpuPlayer1=new Ai();
		Ai cpuPlayer2=new Ai();

		//Prepare game***************************************************
		engine.piecesState(board, Sides.WHITE, playerWhite);
		engine.piecesState(board, Sides.BLACK, playerBlack);
//		playerWhite.testPlayer();
//		playerBlack.testPlayer();
		gameEnd=false;
		sidePlay=Sides.WHITE;
		sideAi1=/*Sides.NONE;*/Sides.WHITE;
		sideAi2=/*Sides.NONE;*/Sides.BLACK;
		board.printBoard();

		//Start game*****************************************************
		System.out.println("\t\t\tGame started!!!!!!!!!!!!!");
		while(!gameEnd){
//*************************************************************************************
			try {
		        System.in.read();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
//**************************************************************************************
			System.out.println("\t\t\t"+sidePlay+" MOVE NOW!");
			if(sidePlay==Sides.WHITE){
				if(sideAi1==Sides.WHITE){
					board.setBoard(cpuPlayer1.getAiMove(board, Sides.WHITE));
					pMoveValue=1;
				}else{
					playerWhite.setHumanMove(input);
					pMoveValue=engine.move(board, sidePlay, playerWhite.getHumanMove(0,0),
							playerWhite.getHumanMove(0,1), playerWhite.getHumanMove(1,0),
							playerWhite.getHumanMove(1,1), playerWhite);
				}
			}else if(sidePlay==Sides.BLACK){
				if(sideAi2==Sides.BLACK){
					board.setBoard(cpuPlayer2.getAiMove(board, Sides.BLACK));
					pMoveValue=1;
				}else{
					playerBlack.setHumanMove(input);
					pMoveValue=engine.move(board, sidePlay, playerBlack.getHumanMove(0,0),
						playerBlack.getHumanMove(0,1), playerBlack.getHumanMove(1,0),
						playerBlack.getHumanMove(1,1), playerBlack);
				}
			}
			engine.piecesState(board, Sides.WHITE, playerWhite);
			engine.piecesState(board, Sides.BLACK, playerBlack);
			if(pMoveValue==1){
				board.changeToKing();
				if(sidePlay==Sides.WHITE){
					sidePlay=Sides.BLACK;
				}else{
					sidePlay=Sides.WHITE;
				}
			}else if(pMoveValue==2){
				if(sidePlay==Sides.WHITE){
					for(i=1; i<13; i++){
						if((playerWhite.getPieces(i).getPosition()==((playerWhite.getHumanMove(1, 0)*10)+playerWhite.getHumanMove(1, 1)))){
							if(!playerWhite.getPieces(i).isOnBeating()){
								sidePlay=Sides.BLACK;
							}
						}
					}
				}else{
					for(i=1; i<13; i++){
					if((playerBlack.getPieces(i).getPosition()==((playerBlack.getHumanMove(1, 0)*10)+playerBlack.getHumanMove(1, 1)))){
						if(!playerBlack.getPieces(i).isOnBeating()){
							sidePlay=Sides.WHITE;
						}
					}
				}
				}
			}else if(pMoveValue==0){
				System.out.println("WRONG MOVE!");
			}
//			playerWhite.testPlayer();
//			playerBlack.testPlayer();
			board.printBoard();

			//Check if it game over;
			if(playerWhite.getPiecesNumbers()==0){
				System.out.println("Black win, white defeated");
				gameEnd=true;
			}else if(playerBlack.getPiecesNumbers()==0){
				System.out.println("White win, black defeated");
				gameEnd=true;
			}else if(playerWhite.getBlockedPiecesNumbers() == playerWhite.getPiecesNumbers()){
				System.out.println("Black win, white blocked");
				gameEnd=true;
			}else if(playerBlack.getBlockedPiecesNumbers() == playerBlack.getPiecesNumbers()){
				System.out.println("White win, black blocked");
				gameEnd=true;
			}
		}
		System.out.println("Press any key to contiunue ....");
		try{
			System.in.read();
		}catch(Exception e){

		}
	}
}
