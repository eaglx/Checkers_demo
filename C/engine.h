#ifndef _ENGINE_H
#define _ENGINE_H

#include "main.h"
#include "board.h"

void PawnState(int board[10][10], int side, S_PLAYER *player);		//Set player pieces state
int move(int board[10][10], int &side, int typePieces, int xp, int yp, int xd, int yd, int player, S_PLAYER SPlayer);	//make move
int try_move(int board[10][10], int &side, int typePieces, int xp, int yp, int xd, int yd, int player, S_PLAYER SPlayer);	//check if move is correct

#endif // !_ENGINE_H
