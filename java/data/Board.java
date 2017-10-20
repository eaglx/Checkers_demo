package data;

import data.Figures;

public class Board {
	private static final int BOARDSIZE=100;
	private static final int BOARDLONG=10;
	private Figures[]board=new Figures[BOARDSIZE];

	public int getBoardSize(){
		return BOARDSIZE;
	}

	public int getBoardLong(){
		return BOARDLONG;
	}

	public Figures[] getBoard(){
		return board;
	}
	
	public Figures getBoard(int number){
		return board[number];
	}
	
	public void setBoard(int number, Figures figure){
		this.board[number]=figure;
	}
	
	public void setBoard(Figures[] b){
		int i;
		for(i=0; i<BOARDSIZE; i++){
			this.board[i]=b[i];
		}
	}

	public Board(){
		if(!boardInitialize(this.board)){
			System.out.println("Cannont initialize board!!!!");
		}
	}

	private boolean boardInitialize(Figures[] board){
		int i,j;

		for(i=0;i<BOARDSIZE;i++){
			board[i]=Figures.BORDER;
		}
		for(i=1;i<(BOARDLONG-1);i++){
			for(j=1;j<(BOARDLONG-1);j++){
				if((i%2)!=0){
					if((j%2)==0){
						board[(i*BOARDLONG)+j]=Figures.EMPTY;
					}else{
						board[(i*BOARDLONG)+j]=Figures.BLOCKFIELD;
					}
				}else{
					if((j%2)!=0){
						board[(i*BOARDLONG)+j]=Figures.EMPTY;
					}else{
						board[(i*BOARDLONG)+j]=Figures.BLOCKFIELD;
					}
				}
			}
		}
		
		for(i=1;i<(BOARDLONG-1);i++){
			for(j=1;j<9;j++){
				if(((i+j)%2) == 1){
					if((i==1) || (i==2) || (i==3)){
						board[(i*BOARDLONG)+j]=Figures.BP;
					}else if((i==6) || (i==7) || (i==8)){
						board[(i*BOARDLONG)+j]=Figures.WP;
					}
				}
			}
		}
		
		/*
 		board[18]=Figures.WK;
		board[27]=Figures.BP;
		*/
		return true;
	}

	public void printBoard(){
		int i,j;

//		System.out.println("0123456789");
		System.out.println("12345678");
//		for(i=0; i<BOARDLONG; i++){
//			for(j=0; j<BOARDLONG; j++){
//				if(board[(i*BOARDLONG)+j]==Figures.BORDER)
//					System.out.print("#");
//				if(board[(i*BOARDLONG)+j]==Figures.EMPTY)
//					System.out.print(" ");
//				if(board[(i*BOARDLONG)+j]==Figures.BLOCKFIELD)
//					System.out.print("-");
//				if(board[(i*BOARDLONG)+j]==Figures.WP)
//					System.out.print("w");
//				if(board[(i*BOARDLONG)+j]==Figures.WK)
//					System.out.print("W");
//				if(board[(i*BOARDLONG)+j]==Figures.BP)
//					System.out.print("b");
//				if(board[(i*BOARDLONG)+j]==Figures.BK)
//					System.out.print("B");
//			}
//			System.out.print(" "+i+"\n");
//		}
		for(i=1; i<(BOARDLONG-1); i++){
			for(j=1; j<(BOARDLONG-1); j++){
				if(board[(i*BOARDLONG)+j]==Figures.BORDER)
					System.out.print("#");
				if(board[(i*BOARDLONG)+j]==Figures.EMPTY)
					System.out.print(" ");
				if(board[(i*BOARDLONG)+j]==Figures.BLOCKFIELD)
					System.out.print("-");
				if(board[(i*BOARDLONG)+j]==Figures.WP)
					System.out.print("w");
				if(board[(i*BOARDLONG)+j]==Figures.WK)
					System.out.print("W");
				if(board[(i*BOARDLONG)+j]==Figures.BP)
					System.out.print("b");
				if(board[(i*BOARDLONG)+j]==Figures.BK)
					System.out.print("B");
			}
			System.out.print(" "+i+"\n");
		}
//		System.out.println("0123456789");
		System.out.println("12345678");
	}

	public void changeToKing(){
		int i,j;

		for(i=1; i<(BOARDLONG-1); i++){
			j=1;
			if(board[(j*BOARDLONG)+i]==Figures.WP)
				board[(j*BOARDLONG)+i]=Figures.WK;
		}

		for(i=1; i<(BOARDLONG-1); i++){
			j=8;
			if(board[(j*BOARDLONG)+i]==Figures.BP)
				board[(j*BOARDLONG)+i]=Figures.BK;
		}
	}
}
