#include "player.h"

void GetHumanMove(int GetMove[2][2], int pos_x, int pos_y, int x)
{
	if((pos_x > 0) && (pos_x < 75)){
		GetMove[x][1] = 1;
	}
	if((pos_x > 75) && (pos_x < 150)){
		GetMove[x][1] = 2;
	}
	if((pos_x > 150) && (pos_x < 225)){
		GetMove[x][1] = 3;
	}
	if((pos_x > 225) && (pos_x < 300)){
		GetMove[x][1] = 4;
	}
	if((pos_x > 300) && (pos_x < 375)){
		GetMove[x][1] = 5;
	}
	if((pos_x > 375) && (pos_x < 450)){
		GetMove[x][1] = 6;
	}
	if((pos_x > 450) && (pos_x < 525)){
		GetMove[x][1] = 7;
	}
	if((pos_x > 525) && (pos_x < 600)){
		GetMove[x][1] = 8;
	}
	
	if((pos_y > 0) && (pos_y < 75)){
		GetMove[x][0] = 1;
	}
	if((pos_y > 75) && (pos_y < 150)){
		GetMove[x][0] = 2;
	}
	if((pos_y > 150) && (pos_y < 225)){
		GetMove[x][0] = 3;
	}
	if((pos_y > 225) && (pos_y < 300)){
		GetMove[x][0] = 4;
	}
	if((pos_y > 300) && (pos_y < 375)){
		GetMove[x][0] = 5;
	}
	if((pos_y > 375) && (pos_y < 450)){
		GetMove[x][0] = 6;
	}
	if((pos_y > 450) && (pos_y < 525)){
		GetMove[x][0] = 7;
	}
	if((pos_y > 525) && (pos_y < 600)){
		GetMove[x][0] = 8;
	}
}
