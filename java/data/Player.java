package data;

import data.Pieces;

public class Player {
	private static final int howManyPieces=12;

	private int piecesNumbers;
	private int blockedPiecesNumbers;
	private int[][] getMove=new int[2][2];
	private Pieces[] piecesPosition;

	public int getHowManyPieces(){
		return howManyPieces;
	}

	public int getPiecesNumbers() {
		return piecesNumbers;
	}

	public int getBlockedPiecesNumbers() {
		return blockedPiecesNumbers;
	}

	public Pieces getPieces(int number){
		return piecesPosition[number];
	}

	public int getGetMove(int number1, int number2) {
		return getMove[number1][number2];
	}

	public void setGetMove(int[][] getMove) {
		this.getMove = getMove;
	}

	public Player(){
		int i;
		this.piecesPosition=new Pieces[howManyPieces+1];
		for(i=0; i<=howManyPieces; i++){
			this.piecesPosition[i]=new Pieces();
		}
		//Pawn which number 0 don't exist;
		this.piecesPosition[0].setPosition(-1);
		this.piecesPosition[0].setCanMove(false);
		this.piecesPosition[0].setOnBeating(false);
	}

	public void countBlockedAndAllPieces(){
		int i;
		Pieces piece=null;

		this.blockedPiecesNumbers=0;
		this.piecesNumbers=0;
		for(i=1; i<=howManyPieces; i++){
			piece=getPieces(i);
			if(piece.getPosition()!=-1){
				this.piecesNumbers++;
				if(!piece.isCanMove()){
					this.blockedPiecesNumbers++;
				}
			}
		}
	}

	public void testPlayer(){
		int i;
		for(i=0; i<(howManyPieces+1); i++){
			System.out.println(i+">Position:"+this.piecesPosition[i].getPosition()
					+" >canMove:"+this.piecesPosition[i].isCanMove()
					+" >onBeating:"+this.piecesPosition[i].isOnBeating());
		}
	}

}
