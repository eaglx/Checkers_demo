#ifndef _BOARD_H
#define _BOARD_H

#include "main.h"

void print_board(int board[10][10], ALLEGRO_DISPLAY *display, int hp, int pos_x, int pos_y);	//Print board in Allegro
void board_initialization(int board[10][10]);	//Set board
void ChangeToKing(int board[10][10]);		//Check if pawn is on rewarded field

#endif // !_BOARD_H
