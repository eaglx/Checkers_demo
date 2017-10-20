#include "ai.h"
#include "engine.h"

//Check if pieces is beating
bool Check_AI_Beating(int board[10][10], int side, int xp, int yp, int xd, int yd)
{
	if ((board[xd][yd] != EMPTY) || (board[xd][yd] == BORDER)) {
		return false;
	}

	if (side == WHITE) {
		if ((board[(xp + xd) / 2][(yp + yd) / 2] == BP) || (board[(xp + xd) / 2][(yp + yd) / 2] == BK)) {
			return true;
		}
	}
	else if (side == BLACK) {
		if ((board[(xp + xd) / 2][(yp + yd) / 2] == WP) || (board[(xp + xd) / 2][(yp + yd) / 2] == WK)) {
			return true;
		}
	}
	return false;
}

//Check if some pieces is on beating position, four side
void AI_Beating(int board[10][10], int i, int side, int GetMove[2][2], S_PLAYER SPlayer) 
{
	if (Check_AI_Beating(board, side, SPlayer.PiecesPosition[i].xp, SPlayer.PiecesPosition[i].yp, SPlayer.PiecesPosition[i].xp - 2, SPlayer.PiecesPosition[i].yp - 2)) {
		GetMove[0][0] = SPlayer.PiecesPosition[i].xp;
		GetMove[0][1] = SPlayer.PiecesPosition[i].yp;
		GetMove[1][0] = SPlayer.PiecesPosition[i].xp - 2;
		GetMove[1][1] = SPlayer.PiecesPosition[i].yp - 2;
	}
	if (Check_AI_Beating(board, side, SPlayer.PiecesPosition[i].xp, SPlayer.PiecesPosition[i].yp, SPlayer.PiecesPosition[i].xp - 2, SPlayer.PiecesPosition[i].yp + 2)) {
		GetMove[0][0] = SPlayer.PiecesPosition[i].xp;
		GetMove[0][1] = SPlayer.PiecesPosition[i].yp;
		GetMove[1][0] = SPlayer.PiecesPosition[i].xp - 2;
		GetMove[1][1] = SPlayer.PiecesPosition[i].yp + 2;
	}
	if (Check_AI_Beating(board, side, SPlayer.PiecesPosition[i].xp, SPlayer.PiecesPosition[i].yp, SPlayer.PiecesPosition[i].xp + 2, SPlayer.PiecesPosition[i].yp - 2)) {
		GetMove[0][0] = SPlayer.PiecesPosition[i].xp;
		GetMove[0][1] = SPlayer.PiecesPosition[i].yp;
		GetMove[1][0] = SPlayer.PiecesPosition[i].xp + 2;
		GetMove[1][1] = SPlayer.PiecesPosition[i].yp - 2;
	}
	if (Check_AI_Beating(board, side, SPlayer.PiecesPosition[i].xp, SPlayer.PiecesPosition[i].yp, SPlayer.PiecesPosition[i].xp + 2, SPlayer.PiecesPosition[i].yp + 2)) {
		GetMove[0][0] = SPlayer.PiecesPosition[i].xp;
		GetMove[0][1] = SPlayer.PiecesPosition[i].yp;
		GetMove[1][0] = SPlayer.PiecesPosition[i].xp + 2;
		GetMove[1][1] = SPlayer.PiecesPosition[i].yp + 2;
	}
}

//Check free pawn or king and move it to specified position
void Get_AI_Move(int board[10][10], int side, int iteaction, int i, S_PLAYER SPlayer, int TempArray[2][2], int type) 
{
    TempArray[0][0] = SPlayer.PiecesPosition[i].xp;
    TempArray[0][1] = SPlayer.PiecesPosition[i].yp;
    
    if(type == PAWN){
        if (side == WHITE) {
            if (iteaction == 1) { 
                TempArray[1][0] = SPlayer.PiecesPosition[i].xp - 1;
                TempArray[1][1] = SPlayer.PiecesPosition[i].yp - 1;
            }
            else if (iteaction == 2) {
                TempArray[1][0] = SPlayer.PiecesPosition[i].xp - 1;
                TempArray[1][1] = SPlayer.PiecesPosition[i].yp + 1;
            }
        }
        else if (side == BLACK) {
            if (iteaction == 1) {
                TempArray[1][0] = SPlayer.PiecesPosition[i].xp + 1;
                TempArray[1][1] = SPlayer.PiecesPosition[i].yp - 1;
            }
            else if (iteaction == 2) {
                TempArray[1][0] = SPlayer.PiecesPosition[i].xp + 1;
                TempArray[1][1] = SPlayer.PiecesPosition[i].yp + 1;
            }
        }
    }
    else if(type == KING){
       TempArray[1][1] =  ((iteaction - 1)/8)+1;
       TempArray[1][0] =  ((iteaction - ((TempArray[1][1]-1)*8))); 
    }
}

void AI_Move(int board[10][10], int side, int GetMove[2][2], S_PLAYER SPlayer, S_PLAYER SEnemy) 
{
    int temporary[2][2];
    int type;
    int i = 1;
    //Check if AI have beating move
    /*while (SPlayer.PiecesPosition[i].xp){
        if (SPlayer.PiecesPosition[i].OnBeating != 0) {
            AI_Beating(board, i, side, GetMove, SPlayer);
            return;
        }
        i++;
    }*/
    while (i <= 12){
        if (SPlayer.PiecesPosition[i].OnBeating != 0) {
            AI_Beating(board, i, side, GetMove, SPlayer);
            return;
        }
        ++i;
    }
    i = 1;
    //Check if AI have move and do it
    //while (SPlayer.PiecesPosition[i].xp) {
    while (i <= 12) {
        if (board[SPlayer.PiecesPosition[i].xp][SPlayer.PiecesPosition[i].yp] == WP || board[SPlayer.PiecesPosition[i].xp][SPlayer.PiecesPosition[i].yp] == BP) {
            type = PAWN;
             for (int j = 1;j<3;j++) {
             Get_AI_Move(board, side, j, i, SPlayer, temporary, type);
                if (try_move(board, side, type, temporary[0][0], temporary[0][1], temporary[1][0],
                    temporary[1][1], COMPUTER, SPlayer)) {
                    GetMove[0][0] = temporary[0][0];
                    GetMove[0][1] = temporary[0][1];
                    GetMove[1][0] = temporary[1][0];
                    GetMove[1][1] = temporary[1][1];
                    return;
                }   
            }
        }
        else {
            type = KING;
            for (int j = 1; j < 64; j++) {
             Get_AI_Move(board, side, j, i, SPlayer, temporary, type);
                if (try_move(board, side, type, temporary[0][0], temporary[0][1], temporary[1][0], temporary[1][1], COMPUTER, SPlayer)) {
                    GetMove[0][0] = temporary[0][0];
                    GetMove[0][1] = temporary[0][1];
                    GetMove[1][0] = temporary[1][0];
                    GetMove[1][1] = temporary[1][1];
                    return;
                }   
            }
        }
       ++i;
    }

}


//Build game Tree, class (change to structure)
/*
//Node
class CTreeNode
{
    CBoardState         m_board; // Situation on board
    CTreeNode*          m_pParent; // pointer on parent
    QList <CTreeNode*>  m_children; // pointers om childrem 
 
// must have
 
    quint8              m_ucLevel; // tree level, where we are
    qint32              m_uiValue; // value board
};

//Tree
class CTree
{
    CTreeNode *m_root;
};
*/
