package cpu;

import java.util.ArrayList;

import data.Board;
import data.Figures;
import data.Player;
import data.Sides;
import logic.Engine;

public class Ai{
	private static final int depthLimit=6;
	private static final int[] positionBoard=
		{	81, 83, 85, 87, 72, 74, 76, 78,
			61, 63, 65, 67, 52, 54, 56, 58, 
			41, 43, 45, 47, 32, 34, 36, 38,
			21, 23, 25, 27, 12, 14, 16, 18	};
	private Sides max;
	private Sides min;
	private Engine engine=new Engine();

	public Figures[] getAiMove(Board b, Sides side){
		int i, x=-1;
		AiData root=new AiData();
		
		root.setBoard(b);
		max=side;		
		if(max==Sides.WHITE){
			min=Sides.BLACK;
		}else{
			min=Sides.WHITE;
		}
		
		root.setValue(miniMax(root, depthLimit, max));
		//System.out.println("Root value = "+root.getValue());
		for(i=0; i<root.getLeaves().size(); i++){
			//System.out.println("Leaves "+i+" value = "+root.getLeaves(i).getValue());
			if(root.getLeaves(i).getValue()==root.getValue()){
				x=i;
			}
		}
		if(x==-1){
			return root.getBoard().getBoard();
		}
		return root.getLeavesBoard(x);
	}
	
	private int funEdge(Figures[] board, Sides side){
		int i,rating=0;
		
		for(i=0; i<positionBoard.length; i++){
			if(i<4){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=6;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=30;
					}
				}else{
						if(board[positionBoard[i]]==Figures.BP){
							rating+=6;
						}else if(board[positionBoard[i]]==Figures.BK){
							rating+=30;
						}
					}
			}else if(i>27){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
								rating+=6;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=30;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=6;
					}else if(board[positionBoard[i]]==Figures.BK){
						rating+=30;
					}
				}
			}else if((i==7) || (i==8) || (i==15) ||
					(i==16) || (i==23) || (i==24)){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=6;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=30;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=6;
					}else if(board[positionBoard[i]]==Figures.BK){
						rating+=30;
					}
				}
			}else if((i>=4) && (i<7)){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=5;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=25;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=5;
					}else if(board[positionBoard[i]]==Figures.BK){
						rating+=25;
					}
				}
			}else if((i>=25) && (i<=27)){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=5;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=25;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=5;
					}else if(board[positionBoard[i]]==Figures.BK){
						rating+=25;
					}
				}
			}else if((i==11) || (i==12) || (i==19) || (i==20)){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=5;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=25;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=5;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=25;
					}
				}
			}else{
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=4;
					}else if(board[positionBoard[i]]==Figures.WK){
						rating+=20;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=4;
					}else if(board[positionBoard[i]]==Figures.BK){
						rating+=20;
					}
				}
			}
		}
		return rating;
	}
	
	private int promotionBeating(Player p){
		int rating=0;
		int i;
		
		for(i=1; i<13; i++){
			if(p.getPieces(i).isOnBeating()==true){
				rating+=30;
			}
		}
		
		return rating;
	}
	
	private int figuresCount(Figures[] board, Sides side){
		int i;
		int rating=0;
		
		if(side==Sides.WHITE){
			for(i=0; i<positionBoard.length; i++){
				if(board[positionBoard[i]]==Figures.WP){
					rating+=5;
				}else if(board[positionBoard[i]]==Figures.WK){
					rating+=10;
				}
			}
		}else if(side==Sides.BLACK){
			for(i=0; i<positionBoard.length; i++){
				if(board[positionBoard[i]]==Figures.BP){
					rating+=5;
				}else if(board[positionBoard[i]]==Figures.BK){
					rating+=10;
				}
			}
		}
		
		return rating;
	}
	
	private int ratingPawnLevel(Figures[] board, Sides side){
		int i,rating=0;
		
		for(i=0; i<positionBoard.length; i++){
			if((i>=0) && (i<=7)){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=0;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=20;
					}
				}
			}else if((i>=8) && (i<=15)){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=1;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=3;
					}
				}
			}else if((i>=16) && (i<=23)){
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=3;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=1;
					}
				}
			}else{
				if(side==Sides.WHITE){
					if(board[positionBoard[i]]==Figures.WP){
						rating+=20;
					}
				}else{
					if(board[positionBoard[i]]==Figures.BP){
						rating+=0;
					}
				}
			}
		}
		return rating;
	}
	
	private int rating(Figures[] board, Player p, Sides side){
		int sumMyPieces=0;
		int sumEnemyPieces=0;
		Sides enemy;
		
		sumMyPieces+=figuresCount(board, side);
		sumMyPieces+=promotionBeating(p);
		sumMyPieces+=funEdge(board, side);
		sumMyPieces+=ratingPawnLevel(board, side);
		if(side==Sides.WHITE){
			enemy=Sides.BLACK;
		}else{
			enemy=Sides.WHITE;
		}
		sumEnemyPieces+=figuresCount(board, enemy);
		sumEnemyPieces+=funEdge(board, enemy);
		return (sumMyPieces-sumEnemyPieces);
	}

	private boolean generateMove(Board board, Sides side, Player player, int xp, int yp, int xd, int yd){
		boolean test;
		int move=engine.move(board, side, xp, yp, xd, yd, player);
		if(move==1){
			test=true;
		}else{
			test=false;
		}
		return test;
	}
	
	private boolean generateBeat(Board board, Sides side, Player player, int xp, int yp, int xd, int yd){
		boolean test;
		
		if((xd<0) || (xd>9) || (yd<0) || (yd>9)){
			return false;
		}
		//System.out.println("XP = "+yp+" YP = "+xp);
		//System.out.println("XD = "+yd+" YD = "+xd);
		//System.out.println("Side = "+side);
		int move=engine.move(board, side, xp, yp, xd, yd, player);
		//System.out.println("move = "+move);
		if(move==2){
			test=true;
		}else{
			test=false;
		}
		return test;
	}
	
	private boolean allBeatKing(Board board, Sides side, Player player, int piecePosX, int piecePosY){
		boolean isBeat=false;
		/*
		System.out.println("X-2 = "+(piecePosY-2)+" Y-2 = "+(piecePosX-2));
		System.out.println("X-2 = "+(piecePosY+2)+" Y-2 = "+(piecePosX-2));
		System.out.println("X-2 = "+(piecePosY-2)+" Y-2 = "+(piecePosX+2));
		System.out.println("X-2 = "+(piecePosY+2)+" Y-2 = "+(piecePosX+2));
		*/
		if(generateBeat(board, side, player, piecePosX, piecePosY, piecePosX-2, piecePosY-2)){
			//System.out.println("###1");
			isBeat=true;
			allBeatKing(board, side, player, piecePosX-2, piecePosY-2);
		}else if(generateBeat(board, side, player, piecePosX, piecePosY, piecePosX-2, piecePosY+2)){
			//System.out.println("###2");
			isBeat=true;
			allBeatKing(board, side, player, piecePosX-2, piecePosY+2);
		}else if(generateBeat(board, side, player, piecePosX, piecePosY, piecePosX+2, piecePosY-2)){
			//System.out.println("###3");
			isBeat=true;
			allBeatKing(board, side, player, piecePosX+2, piecePosY-2);
		}else if(generateBeat(board, side, player, piecePosX, piecePosY, piecePosX+2, piecePosY+2)){
			//System.out.println("###4");
			isBeat=true;
			allBeatKing(board, side, player, piecePosX+2, piecePosY+2);
		}else{
			//System.out.println("###5");
			isBeat=false;
		}
		return isBeat;
	}
	
	private boolean allBeatPawn(Board board, Sides side, Player player, int piecePosX, int piecePosY){
		boolean isBeat=false;
		
		if(side==Sides.WHITE){
			if(!generateBeat(board, side, player, piecePosX, piecePosY, piecePosX-2, piecePosY-2)){
				if(generateBeat(board, side, player, piecePosX, piecePosY, piecePosX-2, piecePosY+2)){
					isBeat=true;
					allBeatPawn(board, side, player, piecePosX-2, piecePosY+2);
				}else{
					isBeat=false;
				}
			}else{
				isBeat=true;
				allBeatPawn(board, side, player, piecePosX-2, piecePosY-2);
			}
		}else{
			if(!generateBeat(board, side, player, piecePosX, piecePosY, piecePosX+2, piecePosY-2)){
				if(generateBeat(board, side, player, piecePosX, piecePosY, piecePosX+2, piecePosY+2)){
					isBeat=true;
					allBeatPawn(board, side, player, piecePosX+2, piecePosY+2);
				}else{
					isBeat=false;
				}
			}else{
				isBeat=true;
				allBeatPawn(board, side, player, piecePosX+2, piecePosY-2);
			}
		}
		return isBeat; 
	}
	
	private void generateAllMoves(ArrayList<AiData> list, Board board, Sides side, Player player){
		int i, temp, tempX, tempY;
		boolean finTestPawn;
		AiData S=null;
		boolean isBeat=false;
		
		for(i=1; i<13; i++){
			if(player.getPieces(i).isOnBeating()){
				temp=player.getPieces(i).getPosition();
				tempX=temp/10;
				tempY=temp%10;
				S=new AiData();
				S.setBoard(board);
				if(player.getPieces(i).isKing()){
					if(allBeatKing(S.getBoard(), side, player, tempX, tempY)){
						list.add(S);
						isBeat=true;
						S=null;
					}else{
						S=null;
					}
				}else if(allBeatPawn(S.getBoard(), side, player, tempX, tempY)){
					list.add(S);
					isBeat=true;
					S=null;
				}else{
					S=null;
				}
			}
		}
		
		if(isBeat){
			return;
		}
		
		for(i=1; i<13; i++){
			if(player.getPieces(i).isCanMove()){
				temp=player.getPieces(i).getPosition();
				tempX=temp/10;
				tempY=temp%10;
				finTestPawn=false;
				while(!finTestPawn){
					S=new AiData();
					S.setBoard(board);
					if(!player.getPieces(i).isKing()){	//PAWN*************************************
						if(side==Sides.WHITE){
							if(!generateMove(S.getBoard(), side, player, tempX, tempY, tempX-1, tempY-1)){
								if(generateMove(S.getBoard(), side, player, tempX, tempY, tempX-1, tempY+1)){
									list.add(S);
									S=null;
									finTestPawn=true;
								}else{
									S=null;
									finTestPawn=true;									
								}
							}else{
								list.add(S);
								S=null;
								S=new AiData();
								S.setBoard(board);
								if(generateMove(S.getBoard(), side, player, tempX, tempY, tempX-1, tempY+1)){
									list.add(S);
									S=null;
									finTestPawn=true;
								}else{
									S=null;
									finTestPawn=true;
								}
							}
						}else{
							if(!generateMove(S.getBoard(), side, player, tempX, tempY, tempX+1, tempY-1)){
								if(generateMove(S.getBoard(), side, player, tempX, tempY, tempX+1, tempY+1)){
									list.add(S);
									S=null;
									finTestPawn=true;
								}else{
									S=null;
									finTestPawn=true;
								}
							}else{
								list.add(S);
								S=null;
								S=new AiData();
								S.setBoard(board);
								if(generateMove(S.getBoard(), side, player, tempX, tempY, tempX+1, tempY+1)){
									list.add(S);
									S=null;
									finTestPawn=true;
								}else{
									S=null;
									finTestPawn=true;
								}
							}
						}
					}else{	//KING*****************************************************						
						//tempX is OY, tempY is OX. temp_ is King's position.						
						//Searching enemy, if enemy exist check if field behind is empty.
						//If no enemy find the farthest free field.
						//If cannot move one field.
						
						int[] paramets=new int[2];
						int multiMove=0;
						//Move down, right
						paramets[0]=1;
						paramets[1]=1;
						for(i=1; i<8; i++){
							//generateMove(S.getBoard(), side, player, tempX, tempY, tempX+i*paramets[0], tempY+i*paramets[1])
							//generateBeat(S.getBoard(), side, player, piecePosX, piecePosY, piecePosX+(multiMove*paramets[0]), piecePosY+(multiMove*paramets[0]))
							;
						}
						//Move down, left
						paramets[0]=1;
						paramets[1]=-1;
						for(i=1; i<8; i++){
							;
						}
						//Move up, right
						paramets[0]=-1;
						paramets[1]=1;
						for(i=1; i<8; i++){
							;
						}
						//Move up, left
						paramets[0]=-1;
						paramets[1]=-1;
						for(i=1; i<8; i++){
							;
						}
						
						//list.add(S);
						S=null;
						finTestPawn=true;
						//*******************************************************************
					}
				}
			}
		}
	}
	
	private int miniMax(AiData S, int depth, Sides whoMove){
		int value=0;
		int bestValue;
		//int i=0;
		
		engine.piecesState(S.getBoard(), whoMove, S.getPlayer());
		if(depth==0){
			S.setValue(rating(S.getBoard().getBoard(), S.getPlayer(), whoMove));
			return S.getValue();
		}
		//generating all possible moves
		generateAllMoves(S.getLeaves(), S.getBoard(), whoMove, S.getPlayer());
		if(S.getLeaves().size()==0){
			S.setValue(rating(S.getBoard().getBoard(), S.getPlayer(), whoMove));
			return S.getValue();
		}
		
		if(max==whoMove){
			bestValue=-1;
			for(AiData x: S.getLeaves()){
				value=miniMax(x, depth-1, min);
				x.setValue(value);
				//System.out.println("value "+i+" max = "+value);
				//++i;
				if(bestValue==-1){
					bestValue=value;
				}else if(bestValue>value){
					value=bestValue;
				}else{
					bestValue=value;
				}
			}
		}else{
			bestValue=1;			
			for(AiData x: S.getLeaves()){
				value=miniMax(x, depth-1, max);
				//System.out.println("value "+i+" min = "+value);
				//++i;
				if(bestValue==1){
					bestValue=value;
				}else if(bestValue<value){
					value=bestValue;
				}else{
					bestValue=value;
				}
			}
		}
		
		return value;
	}
}