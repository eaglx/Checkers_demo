package logic;

import data.Board;
import data.Figures;
import data.Sides;
import data.Player;

public class Engine {
	public Engine(){
		;
	}

	private int tryMoveKing(Board board, Sides side, int xp, int yp, int xd, int yd, Player player){
		int i, max=0, multiplier=1;
		int[] paramets=new int[2];
		int XPD=xp-xd;
		int YPD=yp-yd;
		Figures typKing, typKingVs, typPownVs;

		if(side==Sides.WHITE){
			typKing=Figures.WK;
			typKingVs=Figures.BK;
			typPownVs=Figures.BP;
		}else{
			typKing=Figures.BK;
			typKingVs=Figures.WK;
			typPownVs=Figures.WP;
		}

		if(board.getBoard(((xp*10)+yp))!=typKing){
			return 0;
		}

		//Build triangle; is for messure move
		int lx=xd-xp;//????????????????????????WHAT_WHAT_WHAT??????????????????
		int ly=yd-yp;
		if(((lx<0)&&(ly>0)) || ((lx>0)&&(ly<0))){
			lx=lx*(-1);
		}

		if(!(lx==ly)){
			return 0;
		}
		//System.out.println("XPD = "+XPD+" YPD = "+YPD);
		if((XPD<0)&&(YPD<0)){
			//Move down, right
			paramets[0]=1;
			paramets[1]=1;
		}else if((XPD<0)&&(YPD>0)){
			//Move down, left
			paramets[0]=1;
			paramets[1]=-1;
		}else if((XPD>0)&&(YPD<0)){
			//Move up, right
			paramets[0]=-1;
			paramets[1]=1;
		}else if((XPD>0)&&(YPD>0)){
			//Move up, left
			paramets[0]=-1;
			paramets[1]=-1;
		}

		for(i=1; i<8; i++){
			//System.out.println("i = "+i);
			if(((xp+(i*paramets[0]))==xd) && ((yp+(i*paramets[1]))==yd)){
				break;
			}else{
				//System.out.println("%%%%%___@^^^1");
				if((xp+(i*paramets[0])>=0) && (xp+(i*paramets[0])<=8)
						&& (yp+(i*paramets[1])>=0) && (yp+(i*paramets[1])<=8)){
					//System.out.println("%%%%%___@^^^2");
					if((board.getBoard((((xp+(i*paramets[0]))*10)+(yp+(i*paramets[1]))))==Figures.BP)
							|| (board.getBoard((((xp+(i*paramets[0]))*10)+(yp+(i*paramets[1]))))==Figures.BK)
							|| (board.getBoard((((xp+(i*paramets[0]))*10)+(yp+(i*paramets[1]))))==Figures.WP)
							|| (board.getBoard((((xp+(i*paramets[0]))*10)+(yp+(i*paramets[1]))))==Figures.WK)){
						//System.out.println("%%%%%___@^^^3");
						max+=1;
						multiplier=i;
					}
				}else{
					return 0;
				}
			}
		}
		//System.out.println("max = "+max+" multiplier = "+multiplier);
		if(max>1){
			//System.out.println("#####_____1");
			return 0;
		}else{
			if(max==1){
				//System.out.println("#####_____2");
				//Beating
				if((xp+(multiplier*paramets[0])>=0) && (yp+(multiplier*paramets[1])<=8)){
					if((board.getBoard(((xp+(multiplier*paramets[0]))*10)+(yp+(multiplier*paramets[1])))==typKingVs)
							|| (board.getBoard(((xp+(multiplier*paramets[0]))*10)+(yp+(multiplier*paramets[1])))==typPownVs)){
						return 2;
					}else{
						return 0;
					}
				}
			}else if(max==0){
				//System.out.println("#####_____3");
				//Move
				int countB=0;
				for(i=1; i<13; i++){
					if(player.getPieces(i).isOnBeating()!=false){
						++countB;
					}
				}
				if(countB==0){
					return 1;
				}else{
					return 0;
				}
			}
		}

		return 0;
	}

	private int tryMovePawn(Board board, Sides side, int xp, int yp, int xd, int yd, Player player){
		int XPD=xp-xd;
		int YPD=yp-yd;

		if(side==Sides.WHITE){
			if(board.getBoard(((xp*board.getBoardLong())+yp))!=Figures.WP){
				return 0;
			}else if(((board.getBoard(((((xp+xd)/2)*board.getBoardLong())+((yp+yd)/2))))==Figures.BP)
				||((board.getBoard(((((xp+xd)/2)*board.getBoardLong())+((yp+yd)/2))))==Figures.BK)){	//Check if pawn is beating
				if(((XPD==2)&&(YPD==2)) || ((XPD==2)&&(YPD==-2))
					|| ((XPD==-2)&&(YPD==2)) || ((XPD==-2)&&(YPD==-2))){
					return 2;
				}
			}else if(((XPD==1)&&(YPD==1)) || ((XPD==1)&&(YPD==-1))){	//Check if pawn can move (no beating)
				int k;
				int countBeatingPieces=0;
				for(k=1; k<=player.getHowManyPieces(); k++){
					if(player.getPieces(k).isOnBeating() != false){
						++countBeatingPieces;
					}
				}
				if(countBeatingPieces==0){
					return 1;
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}else if(side==Sides.BLACK){
			if(board.getBoard(((xp*board.getBoardLong())+yp))!=Figures.BP){
				return 0;
			}else if(((board.getBoard(((((xp+xd)/2)*board.getBoardLong())+((yp+yd)/2))))==Figures.WP)
				||((board.getBoard(((((xp+xd)/2)*board.getBoardLong())+((yp+yd)/2))))==Figures.WK)){	//Check if pawn is beating
				if(((XPD==2)&&(YPD==2)) || ((XPD==2)&&(YPD==-2))
					|| ((XPD==-2)&&(YPD==2)) || ((XPD==-2)&&(YPD==-2))){
					return 2;
				}
			}else if(((XPD==-1)&&(YPD==1)) || ((XPD==-1)&&(YPD==-1))){	//Check if pawn can move (no beating)
				int k;
				int countBeatingPieces=0;
				for(k=1; k<=player.getHowManyPieces(); k++){
					if(player.getPieces(k).isOnBeating() != false){
						++countBeatingPieces;
					}
				}
				if(countBeatingPieces==0){
					return 1;
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}

		return 0;
	}

	private int tryMove(Board board, Sides side, Figures typePieces, int xp, int yp, int xd, int yd, Player player){
		if((xd>=0) && (xd<board.getBoardSize()) && (yd>=0) && (yd<board.getBoardSize())){
			if(board.getBoard(((xd*board.getBoardLong())+yd))!=Figures.EMPTY){
				return 0;
			}
		}else{
			return 0;
		}
		if((typePieces==Figures.WP) || (typePieces==Figures.BP)){
			return tryMovePawn(board, side, xp, yp, xd, yd, player);
		}else if((typePieces==Figures.WK) || (typePieces==Figures.BK)){
			return tryMoveKing(board, side, xp, yp, xd, yd, player);
		}
		return 0;
	}

	public int move(Board board, Sides side, int xp, int yp, int xd, int yd, Player player){ //It fuck up in names x_ is real y_ ;)
		int moveOk;
		if((xp<0) && (xp>=board.getBoardSize()) && (yp<0) && (yp>=board.getBoardSize())){
			return 0;
		}
		Figures typePieces=board.getBoard(((xp*board.getBoardLong())+yp));
		moveOk=tryMove(board, side, typePieces, xp, yp, xd, yd, player);	//Value 0 - failed move, value 1 - only move, value 2 - beating

		if(moveOk!=0){
			//Deleting Objects
			int i;
			int[] paramets=new int[2];
			int XPD=xp-xd;
			int YPD=yp-yd;

			if((XPD<0)&&(YPD<0)){
				//Move down, right
				paramets[0]=1;
				paramets[1]=1;
			}else if((XPD<0)&&(YPD>0)){
				//Move down, left
				paramets[0]=1;
				paramets[1]=-1;
			}else if((XPD>0)&&(YPD<0)){
				//Move up, right
				paramets[0]=-1;
				paramets[1]=1;
			}else if((XPD>0)&&(YPD>0)){
				//Move up, left
				paramets[0]=-1;
				paramets[1]=-1;
			}

			for(i=1; i<8; i++){
				if((xp+(i*paramets[0]))!=xd){
					board.setBoard(((xp+(i*paramets[0]))*board.getBoardLong()+(yp+(i*paramets[1]))), Figures.EMPTY);
				}else{
					break;
				}
			}
			board.setBoard(((xd*board.getBoardLong())+yd), board.getBoard(((xp*board.getBoardLong())+yp)));
			board.setBoard(((xp*board.getBoardLong())+yp), Figures.EMPTY);
		}

		return moveOk;
	}

	private boolean checkBlocked(Board board, Sides side, Figures typeFigure, int xp, int yp){
		if(((board.getBoard(((xp-1)*board.getBoardLong())+(yp-1))==Figures.EMPTY)&&(side==Sides.WHITE))||
			((board.getBoard(((xp-1)*board.getBoardLong())+(yp-1))==Figures.EMPTY)&&
					((typeFigure==Figures.WK) || (typeFigure==Figures.BK)))){
			return true;
		}else if(((board.getBoard(((xp-1)*board.getBoardLong())+(yp+1))==Figures.EMPTY)&&(side==Sides.WHITE))||
				((board.getBoard(((xp-1)*board.getBoardLong())+(yp+1))==Figures.EMPTY)&&
						((typeFigure==Figures.WK) || (typeFigure==Figures.BK)))){
				return true;
		}else if(((board.getBoard(((xp+1)*board.getBoardLong())+(yp-1))==Figures.EMPTY)&&(side==Sides.BLACK))||
				((board.getBoard(((xp+1)*board.getBoardLong())+(yp-1))==Figures.EMPTY)&&
						((typeFigure==Figures.WK) || (typeFigure==Figures.BK)))){
				return true;
		}else if(((board.getBoard(((xp+1)*board.getBoardLong())+(yp+1))==Figures.EMPTY)&&(side==Sides.BLACK))||
				((board.getBoard(((xp+1)*board.getBoardLong())+(yp+1))==Figures.EMPTY)&&
						((typeFigure==Figures.WK) || (typeFigure==Figures.BK)))){
				return true;
		}

		return false;
	}

	private boolean checkBeating(Board board, Figures typePieces, Sides side, int xp, int yp, int xd, int yd, int xm, int ym){
		int XPD=xp-xd;
		int YPD=yp-yd;

		if((xd>9) || (yd>9)){
			return false;
		}else if((xd<0) || (yd<0)){
			return false;
		}else if(board.getBoard(((xd*board.getBoardLong())+yd))!=Figures.EMPTY){
			return false;
		}

		if(side==Sides.WHITE){
			if((board.getBoard(((xm*board.getBoardLong())+ym))==Figures.BP) || (board.getBoard(((xm*board.getBoardLong())+ym))==Figures.BK)){
				if(typePieces==Figures.WP){
					if(((XPD==2)&&(YPD==2)) || ((XPD==2)&&(YPD==-2))){
							return true;
					}
				}else if(((XPD==2)&&(YPD==2)) || ((XPD==2)&&(YPD==-2))
					|| ((XPD==-2)&&(YPD==2)) || ((XPD==-2)&&(YPD==-2))){
					return true;
				}
			}
		}else if(side==Sides.BLACK){
			if((board.getBoard(((xm*board.getBoardLong())+ym))==Figures.WP) || (board.getBoard(((xm*board.getBoardLong())+ym))==Figures.WK)){
				if(typePieces==Figures.BP){
					if(((XPD==-2)&&(YPD==2)) || ((XPD==-2)&&(YPD==-2))){
							return true;
					}
				}else if(((XPD==2)&&(YPD==2)) || ((XPD==2)&&(YPD==-2))
					|| ((XPD==-2)&&(YPD==2)) || ((XPD==-2)&&(YPD==-2))){
					return true;
				}
			}
		}

		return false;
	}

	public void piecesState(Board board, Sides side, Player player){
		int i,j;
		int k=1;
		Figures typePieces;

		for(i=1; i<(board.getBoardLong()-1); i++){
			for(j=1; j<(board.getBoardLong()-1); j++){
				if(side==Sides.WHITE){
					if((board.getBoard((i*board.getBoardLong())+j)==Figures.WP) || (board.getBoard((i*board.getBoardLong())+j)==Figures.WK)){
						if((board.getBoard((i*board.getBoardLong())+j)==Figures.WK)){
							player.getPieces(k).setKing(true);
						}else{
							player.getPieces(k).setKing(false);
						}
						player.getPieces(k).setPosition((i*board.getBoardLong())+j);
						typePieces=board.getBoard(((i*board.getBoardLong())+j));
						if(checkBeating(board, typePieces, side, i, j, i-2, j-2, i-1, j-1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else if(checkBeating(board, typePieces, side, i, j, i-2, j+2, i-1, j+1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else if(checkBeating(board, typePieces, side, i, j, i+2, j-2, i+1, j-1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else if(checkBeating(board, typePieces, side, i, j, i+2, j+2, i+1, j+1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else{
							player.getPieces(k).setOnBeating(false);
							player.getPieces(k).setCanMove(checkBlocked(board, side, board.getBoard(((i*board.getBoardLong())+j)), i, j));
						}
						++k;
					}
				}else if(side==Sides.BLACK){
					if((board.getBoard((i*board.getBoardLong())+j)==Figures.BP) || (board.getBoard((i*board.getBoardLong())+j)==Figures.BK)){
						if((board.getBoard((i*board.getBoardLong())+j)==Figures.BK)){
							player.getPieces(k).setKing(true);
						}else{
							player.getPieces(k).setKing(false);
						}
						player.getPieces(k).setPosition((i*board.getBoardLong())+j);
						typePieces=board.getBoard(((i*board.getBoardLong())+j));
						if(checkBeating(board, typePieces, side, i, j, i-2, j-2, i-1, j-1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else if(checkBeating(board, typePieces, side, i, j, i-2, j+2, i-1, j+1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else if(checkBeating(board, typePieces, side, i, j, i+2, j-2, i+1, j-1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else if(checkBeating(board, typePieces, side, i, j, i+2, j+2, i+1, j+1)){
							player.getPieces(k).setOnBeating(true);
							player.getPieces(k).setCanMove(true);
						}else{
							player.getPieces(k).setOnBeating(false);
							player.getPieces(k).setCanMove(checkBlocked(board, side, board.getBoard(((i*board.getBoardLong())+j)), i, j));
						}
						++k;
					}
				}
			}
		}

		if(k<(player.getHowManyPieces()+1)){
			while(k<(player.getHowManyPieces()+1)){
				player.getPieces(k).setPosition(-1);
				player.getPieces(k).setKing(false);
				player.getPieces(k).setCanMove(false);
				player.getPieces(k).setOnBeating(false);
				++k;
			}
		}

		player.countBlockedAndAllPieces();
	}
}
