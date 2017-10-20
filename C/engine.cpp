#include "engine.h"

void check_beating(int board[10][10], int side, int xp, int yp, int xd, int yd, int &i)
{
	int XPD = xp - xd;
	int YPD = yp - yd;

	if (board[xd][yd] != EMPTY) {
		return;
	}

	if (side == WHITE) {
		if ((board[(xp + xd) / 2][(yp + yd) / 2] == BP) || (board[(xp + xd) / 2][(yp + yd) / 2] == BK)) {
			if (
				(XPD == 2 && YPD == 2) || (XPD == 2 && YPD == -2) ||
				(XPD == -2 && YPD == 2) || (XPD == -2 && YPD == -2)) {
				++i;
			}
		}
	}
	else if (side == BLACK) {
		if ((board[(xp + xd) / 2][(yp + yd) / 2] == WP) || (board[(xp + xd) / 2][(yp + yd) / 2] == WK)) {
			if (
				(XPD == 2 && YPD == 2) || (XPD == 2 && YPD == -2) ||
				(XPD == -2 && YPD == 2) || (XPD == -2 && YPD == -2)) {
				++i;
			}
		}
	}
}

//0 - false move, 1 - only move, 2 - possible beating
int try_move_pown(int board[10][10], int &side, int type, int xp, int yp, int xd, int yd, int player, S_PLAYER SPlayer) 
{
	int XPD = xp - xd;
	int YPD = yp - yd;
	if (side == WHITE) {
		if (board[xp][yp] != WP) {
			return 0;
		}
		if ((board[(xp + xd) / 2][(yp + yd) / 2] == BP) || (board[(xp + xd) / 2][(yp + yd) / 2] == BK)) {	//Check if pawn is beating
			if (
				(XPD == 2 && YPD == 2) || (XPD == 2 && YPD == -2) ||
				(XPD == -2 && YPD == 2) || (XPD == -2 && YPD == -2)) {
				return 2;
			}
		}
		else if (((XPD == 1) && (YPD == 1)) ||
			((XPD == 1) && (YPD == -1))) {	//Check if pawn can move
			int count_b = 0;
			for (int k = 1;k<13;k++) {
				if (SPlayer.PiecesPosition[k].OnBeating != 0) {
					++count_b;
				}
			}
			if (count_b == 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			return 0;
		}
	}
	else if (side == BLACK) {
		if (board[xp][yp] != BP) {
			return 0;
		}
		if ((board[(xp + xd) / 2][(yp + yd) / 2] == WP) || (board[(xp + xd) / 2][(yp + yd) / 2] == WK)) {	//check if pawn is beating
			if (
				(XPD == 2 && YPD == 2) || (XPD == 2 && YPD == -2) ||
				(XPD == -2 && YPD == 2) || (XPD == -2 && YPD == -2)) {
				return 2;
			}
		}
		else if (((XPD == -1) && (YPD == 1)) ||
			((XPD == -1) && (YPD == -1))) {	//Check if pawn can move
			int count_b = 0;
			for (int k = 1;k<13;k++) {
				if (SPlayer.PiecesPosition[k].OnBeating != 0) {
					++count_b;
				}
			}
			if (count_b == 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			return 0;
		}
	}
}

//0 - false move, 1 - only move, 2 - possible beating
int try_move_king(int board[10][10], int &side, int type, int xp, int yp, int xd, int yd, int player, S_PLAYER SPlayer) 
{

	int i, max, multiplier;
	int paramets[2];
	int XPD = xp - xd;
	int YPD = yp - yd;
	int typ_king, typ_king_vs, typ_pown_vs;

	if (side == WHITE) {
		typ_king = WK;
		typ_king_vs = BK;
		typ_pown_vs = BP;
	}
	else {
		typ_king = BK;
		typ_king_vs = WK;
		typ_pown_vs = WP;
	}
	if (board[xp][yp] != typ_king) {
		return 0;
	}
	//********************************************************
	//Build tiangle (is for messure move)
	int lx, ly;
	lx = xd - xp;
	ly = yd - yp;
	if (((lx < 0) && (ly>0)) || ((lx > 0) && (ly < 0))) {
		lx *= -1;
	}
	if (!(lx == ly)) {
		return 0;
	}
	//********************************************************

	if ((XPD < 0) && (YPD < 0)) {
		//Move down, right
		paramets[0] = 1;
		paramets[1] = 1;
	}
	else if ((XPD < 0) && (YPD > 0)) {
		//Move down, left
		paramets[0] = 1;
		paramets[1] = -1;
	}
	else if ((XPD > 0) && (YPD < 0)) {
		//Move up, right
		paramets[0] = -1;
		paramets[1] = 1;
	}
	else if ((XPD > 0) && (YPD > 0)) {
		//Move up, left
		paramets[0] = -1;
		paramets[1] = -1;
	}

	max = 0;
	for (i = 1; i < 8; ++i) {
		if (((xp + (i*paramets[0])) == xd) && ((yp + (i*paramets[1])) == yd)) {
			break;
		}
		else {
			if((xp + (i*paramets[0]) >= 0) && (xp + (i*paramets[0]) <= 8) && (yp + (i*paramets[1]) >= 0) && (yp + (i*paramets[1]) <= 8)){
				if ((board[xp + (i*paramets[0])][(yp + (i*paramets[1]))] == BP) || (board[xp + (i*paramets[0])][(yp + (i*paramets[1]))] == BK) ||
					(board[xp + (i*paramets[0])][(yp + (i*paramets[1]))] == WP) || (board[xp + (i*paramets[0])][(yp + (i*paramets[1]))] == WK)
					) {
					++max;
					multiplier = i;
				}
			}
			else{
				return 0;
			}
		}
	}
	if (max>1) {
		return 0;
	}
	else {
		if (max == 1) {
			//CHECK IF BEATING
			if((xp + multiplier*paramets[0] >= 0) && (yp + multiplier*paramets[1] <= 8)){
				if ((board[xp + multiplier*paramets[0]][yp + multiplier*paramets[1]] == typ_king_vs) || (board[xp + multiplier*paramets[0]][yp + multiplier*paramets[1]] == typ_pown_vs)) {
					return 2;
				}
				else {
					return 0;
				}
			}
		}
		else if (max == 0) {
			int count_b = 0;
			for (int k = 1;k<13;k++) {
				if (SPlayer.PiecesPosition[k].OnBeating != 0) {
					++count_b;
				}
			}
			if (count_b == 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}

}

int try_move(int board[10][10], int &side, int typePieces, int xp, int yp, int xd, int yd, int player, S_PLAYER SPlayer) 
{
	if((xd >= 0) && (xd <= 9) && (yd >= 0) && (yd <= 9)){	
		if (board[xd][yd] != EMPTY) {
			return 0;
		}
	}
	else{
		return 0;
	}
	
	if (typePieces == PAWN) {
		return try_move_pown(board, side, typePieces, xp, yp, xd, yd, player, SPlayer);
	}
	else if (typePieces == KING) {
		return try_move_king(board, side, typePieces, xp, yp, xd, yd, player, SPlayer);
	}
}

void PawnState(int board[10][10], int side, S_PLAYER *player)
{
	int k = 1;
	player->PiecesNumbers = 0;
	player->PiecesPosition[0].xp = -1;
	player->PiecesPosition[0].yp = -1;
	player->PiecesPosition[0].OnBeating = 0;

	if (side == WHITE) {
		for (int i = 1; i < 9; ++i) {
			for (int j = 1; j < 9; ++j) {
				if ((board[i][j] == WP) || (board[i][j] == WK)) {
					player->PiecesPosition[k].xp = i;
					player->PiecesPosition[k].yp = j;
					++player->PiecesNumbers;
					player->PiecesPosition[k].OnBeating = 0;
					check_beating(board, side, i, j, i - 2, j - 2, player->PiecesPosition[k].OnBeating);
					check_beating(board, side, i, j, i - 2, j + 2, player->PiecesPosition[k].OnBeating);
					check_beating(board, side, i, j, i + 2, j - 2, player->PiecesPosition[k].OnBeating);
					check_beating(board, side, i, j, i + 2, j + 2, player->PiecesPosition[k].OnBeating);
					++k;
				}
			}
		}
	}
	else if (side == BLACK) {
		for (int i = 1; i < 9; ++i) {
			for (int j = 1; j < 9; ++j) {
				if ((board[i][j] == BP) || (board[i][j] == BK)) {
					player->PiecesPosition[k].xp = i;
					player->PiecesPosition[k].yp = j;
					++player->PiecesNumbers;
					player->PiecesPosition[k].OnBeating = 0;
					check_beating(board, side, i, j, i - 2, j - 2, player->PiecesPosition[k].OnBeating);
					check_beating(board, side, i, j, i - 2, j + 2, player->PiecesPosition[k].OnBeating);
					check_beating(board, side, i, j, i + 2, j - 2, player->PiecesPosition[k].OnBeating);
					check_beating(board, side, i, j, i + 2, j + 2, player->PiecesPosition[k].OnBeating);
					++k;
				}
			}
		}
	}

	if (k < 13) {
		while (k < 13) {
			player->PiecesPosition[k].xp = -1;
			player->PiecesPosition[k].yp = -1;
			player->PiecesPosition[k].OnBeating = 0;
			++k;
		}
	}
}

int move(int board[10][10], int &side, int typePieces, int xp, int yp, int xd, int yd, int player, S_PLAYER SPlayer)
{
	int PMoveValue = try_move(board, side, typePieces, xp, yp, xd, yd, player, SPlayer);
	if (PMoveValue) {
		//DELATEING OBJECTS
		int paramets[2];
		int XPD = xp - xd;
		int YPD = yp - yd;
		if ((XPD < 0) && (YPD < 0)) {
			//Move down, right
			paramets[0] = 1;
			paramets[1] = 1;
		}
		else if ((XPD < 0) && (YPD > 0)) {
			//Move down, left
			paramets[0] = 1;
			paramets[1] = -1;
		}
		else if ((XPD > 0) && (YPD < 0)) {
			//Move up, right
			paramets[0] = -1;
			paramets[1] = 1;
		}
		else if ((XPD > 0) && (YPD > 0)) {
			//Move up, down
			paramets[0] = -1;
			paramets[1] = -1;
		}
		for (int i = 1;i<8;i++) {
			if (xp + i*paramets[0] != xd) {
				board[xp + i*paramets[0]][yp + i*paramets[1]] = EMPTY;
			}
			else {
				break;
			}
		}
		board[xd][yd] = board[xp][yp];
		board[xp][yp] = EMPTY;
	}
	return PMoveValue;
}
