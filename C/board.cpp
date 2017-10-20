#include "board.h"


void print_board(int board[10][10], ALLEGRO_DISPLAY *display, int hp, int pos_x, int pos_y)
{
	int x, y;
	
	x = 0;
	y = 0;
	int StartColor = 0;
	for(int i = 1; i <= 8; ++i){
		for(int j = 1; j <= 8; ++j){
			ALLEGRO_COLOR useColor = (!StartColor) ? al_map_rgb(255, 255, 255) : al_map_rgb(26, 222, 52);
			al_draw_filled_rectangle(x, y, x + 75, y + 75,  useColor);
			x += 75;
			StartColor = !StartColor;
		}
		StartColor = !StartColor;
		x = 0;
		y += 75;
	}
	
	x = 0;
	y = 0;
	for(int i = 1; i <= 8; ++i){
		for(int j = 1; j <= 8; ++j){
			if(board[i][j] != EMPTY){
				if(board[i][j] == WP){
					al_draw_filled_circle(x + 37.5, y + 37.5, 20, al_map_rgb(255, 3, 3));
				}
				else if(board[i][j] == BP){
					al_draw_filled_circle(x + 37.5, y + 37.5, 20, al_map_rgb(3, 3, 3));
				}
				else if(board[i][j] == WK){
					al_draw_filled_circle(x + 37.5, y + 37.5, 20, al_map_rgb(255, 3, 3));
					al_draw_filled_circle(x + 37.5, y + 37.5, 10, al_map_rgb(253, 237, 14));
				}
				else if(board[i][j] == BK){
					al_draw_filled_circle(x + 37.5, y + 37.5, 20, al_map_rgb(3, 3, 3));
					al_draw_filled_circle(x + 37.5, y + 37.5, 10, al_map_rgb(253, 237, 14));
				}
			}
			x += 75;
		}
		x = 0;
		y += 75;
	}
	
	if(hp == 1){
		al_draw_circle(pos_x, pos_y, 35, al_map_rgb(253, 14, 213), 3);
	}

	al_flip_display();
}

void board_initialization(int board[10][10])
{
	for (int i = 0;i<10;i++) {
		for (int j = 0;j<10;j++) {
			board[i][j] = BORDER;
		}
	}
	for (int i = 1;i<9;i++) {
		for (int j = 1;j<9;j++) {
			if ((i + j) % 2 == 0) {
				board[i][j] = BLOCKFIELD;
			}
			else {
				board[i][j] = EMPTY;
			}
		}
	}
	for (int i = 1;i<9;i++) {
		for (int j = 1;j<9;j++) {
			if ((i + j) % 2 == 1) {
				if (i == 1 || i == 2 || i == 3) {
					board[i][j] = BP;
				}
				if (i == 6 || i == 7 || i == 8) {
					board[i][j] = WP;
				}
			}
		}
	}
}


void ChangeToKing(int board[10][10]) {
	for (int i = 1;i<9;i++) {
		int j = 1;
		if (board[j][i] == WP) {
			board[j][i] = WK;
		}
	}

	for (int i = 1;i<9;i++) {
		int j = 8;
		if (board[j][i] == BP) {
			board[j][i] = BK;
		}
	}
}
