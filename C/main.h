#ifndef _MAIN_H
#define _MAIN_H

#include <cstdio>
#include <cstdlib>
#include <allegro5/allegro.h>
#include <allegro5/allegro_primitives.h>
#include <allegro5/allegro_font.h>
#include <allegro5/allegro_ttf.h>
#include <allegro5/allegro_native_dialog.h>


enum { EMPTY = 0, WP, BP, WK, BK, BLOCKFIELD, BORDER };
enum { WHITE = 0, BLACK };
enum { HUMAN = 0, COMPUTER };
enum { PAWN = 0, KING };

typedef struct {
	int xp;
	int yp;
	int OnBeating;  //If 0 - no beating.
	int CanMove;	//If 0, pieces is blocked, 1 can move
}S_PIECES;

typedef struct {
	int PiecesNumbers;
	S_PIECES PiecesPosition[13];
}S_PLAYER;

typedef struct {
	int board[10][10];
	bool GameEnd;
	int side;
	int AIplayer;	//0 - play white, 1 - play black, 2 - no AI
}S_BOARD;


#endif // !_MAIN_H
