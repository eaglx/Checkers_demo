#include "game_status.h"

void SaveGameState(S_BOARD *game)
{
	FILE *pFile;
	
	if((pFile = fopen("save.dat", "wb")) == NULL){
		fputs("Cannot open file to save!!!\n", stderr);
		return;
	}
	
	fwrite(game, sizeof(S_BOARD), 1, pFile);
	fclose(pFile);
}


void OpenSavedState(S_BOARD *game)
{
	FILE *pFile;
	
	if((pFile = fopen("save.dat", "rb")) == NULL){
		fputs("Cannot open file to save!!!\n", stderr);
		return;
	}
	
	fread(game, sizeof(S_BOARD), 1, pFile);
	fclose(pFile);
}
