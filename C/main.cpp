/*
Version 1.0
*/

#include "main.h"
#include "board.h"
#include "player.h"
#include "engine.h"
#include "game_status.h"
#include "ai.h"


int main(void)
{
	S_BOARD BoardState;
	int GetMove[2][2];  //Pawn position [0][0] == xp [0][1] == yp, Destination position [1][0] == xd [1][1] == yd
	int typePieces;
	int PMoveValue;
	int WinSide;
	//int SelectOption;
	int SelectOption = -2;
	int pos_x = -1;
	int pos_y = -1;
	int hp = 0;		//For human playera, value 0 mean that player donnot give pstion; 1 mean player choose pawn to move, 2 mean player set pawn destination
	int done = 0;
	int GoGameGoMenu;
	int IfGameLoadFromFile;
	int GameRestore;
	int FirstProgramStart = 1;
	int GameModeChange = 0;
	int ControlOptionModeChange = 0;
	S_PLAYER wPlayer;
	S_PLAYER bPlayer;

	ALLEGRO_DISPLAY *display = NULL;
	ALLEGRO_EVENT_QUEUE *event_queue = NULL;
	
	int widthBoard = 600;
	int heightBoard = 600;
	
	if (!al_init()) {
		al_show_native_message_box(NULL, NULL, NULL,
			"Failed to initialize allegro!", NULL, NULL);
		return -1;
	}
	
	display = al_create_display(widthBoard, heightBoard);
	
	if (!display) {
		al_show_native_message_box(NULL, NULL, NULL,
			"Failed to initialize display!", NULL, NULL);
		return -1;
	}
	
	al_init_font_addon();	//initialise
	al_init_ttf_addon();	//initialise
	
	ALLEGRO_FONT *font = al_load_font("arial.ttf", 24, 0);
	
	al_init_primitives_addon();
	al_install_mouse();
	al_install_keyboard();
	
	event_queue = al_create_event_queue();
	
	al_register_event_source(event_queue, al_get_display_event_source(display));
	al_register_event_source(event_queue, al_get_mouse_event_source());
	al_register_event_source(event_queue, al_get_keyboard_event_source());

	/*	BUILD GAME MENU	*/

	while(!done){
		al_clear_to_color(al_map_rgb(0, 0, 0));
		al_draw_text(font, al_map_rgb(34, 255, 0), 300, 100, ALLEGRO_ALIGN_CENTRE, "1. NEW GAME.");
		if(FirstProgramStart == 1){
			al_draw_text(font, al_map_rgb(176, 176, 176), 300, 140, ALLEGRO_ALIGN_CENTRE, "2. CONTINUE.");
			al_draw_text(font, al_map_rgb(176, 176, 176), 300, 180, ALLEGRO_ALIGN_CENTRE, "3. SAVE.");
		}
		else{
			al_draw_text(font, al_map_rgb(34, 255, 0), 300, 140, ALLEGRO_ALIGN_CENTRE, "2. CONTINUE.");
			al_draw_text(font, al_map_rgb(34, 255, 0), 300, 180, ALLEGRO_ALIGN_CENTRE, "3. SAVE.");
		}
		al_draw_text(font, al_map_rgb(34, 255, 0), 300, 220, ALLEGRO_ALIGN_CENTRE, "4. LOAD.");
		al_draw_text(font, al_map_rgb(34, 255, 0), 300, 260, ALLEGRO_ALIGN_CENTRE, "5. GAME OPTION.");
		al_draw_text(font, al_map_rgb(34, 255, 0), 300, 300, ALLEGRO_ALIGN_CENTRE, "6. EXIT.");
		al_flip_display();
		
		ALLEGRO_EVENT ev;
		al_wait_for_event(event_queue, &ev);
		if(ev.type == ALLEGRO_EVENT_KEY_DOWN){
			switch(ev.keyboard.keycode){
			case ALLEGRO_KEY_1:
				FirstProgramStart = 0;
				SelectOption = 1;
				IfGameLoadFromFile = 0;
				GameRestore = 0;
				break;
				
			case ALLEGRO_KEY_2:
				if(FirstProgramStart == 1){
					break;
				}
				else{
					SelectOption = 1;
					IfGameLoadFromFile = 0;
					GameRestore = 1;
				}
				break;	
			
			case ALLEGRO_KEY_3:
				if(FirstProgramStart == 1){
					break;
				}
				else{
					SelectOption = -1;
					SaveGameState(&BoardState);
				}
				break;
				
			case ALLEGRO_KEY_4:
				SelectOption = 1;
				OpenSavedState(&BoardState);
				IfGameLoadFromFile = 1;
				GameRestore = 1;
				ControlOptionModeChange = 1;
				FirstProgramStart = 0;
				break;
			
			case ALLEGRO_KEY_5:
				ControlOptionModeChange = 0;
				while(!ControlOptionModeChange){
					al_clear_to_color(al_map_rgb(0, 0, 0));
					al_draw_text(font, al_map_rgb(34, 255, 0), 300, 100, ALLEGRO_ALIGN_CENTRE, "1. PLAYER - PLAYER");
					al_draw_text(font, al_map_rgb(34, 255, 0), 300, 160, ALLEGRO_ALIGN_CENTRE, "2. PLAYER - COMPUTER");
					al_draw_text(font, al_map_rgb(34, 255, 0), 300, 220, ALLEGRO_ALIGN_CENTRE, "3. COMPUTER - PLAYER");
					al_flip_display();
					al_wait_for_event(event_queue, &ev);
					if(ev.type == ALLEGRO_EVENT_KEY_DOWN){
						switch(ev.keyboard.keycode){
							case ALLEGRO_KEY_1:
								BoardState.AIplayer = 2;
								ControlOptionModeChange = 1;
								break;
							case ALLEGRO_KEY_2:
								BoardState.AIplayer = 1;
								ControlOptionModeChange = 1;
								break;
							case ALLEGRO_KEY_3:
								BoardState.AIplayer = 0;
								ControlOptionModeChange = 1;
								break;
						}
					}
				}
				break;
				
			case ALLEGRO_KEY_6:
				SelectOption = -1;
				done = 1;
				break;
			}
		}
		else if(ev.type == ALLEGRO_EVENT_DISPLAY_CLOSE){
			done = 1;
			SelectOption = -1;
		}
		
		switch(SelectOption){
		case 1:
			SelectOption = -1;
			al_clear_to_color(al_map_rgb(0, 0, 0));
			
			/* **************	PREARE GAME	************************************************* */
			if((IfGameLoadFromFile == 0) && (GameRestore == 0) && (GameModeChange == 0)){
				BoardState.GameEnd = false;
				board_initialization(BoardState.board);
				BoardState.side = WHITE;
			}
			
			if(ControlOptionModeChange == 0){
				BoardState.AIplayer = 1;
			}
			
			print_board(BoardState.board, display, hp, pos_x, pos_y);
		
			PawnState(BoardState.board, WHITE, &wPlayer);
			PawnState(BoardState.board, BLACK, &bPlayer);
			
			GoGameGoMenu = 0;
			while (!BoardState.GameEnd && !GoGameGoMenu) {
				ALLEGRO_EVENT ev;
				al_wait_for_event(event_queue, &ev);
	
				WinSide = 0;
				if (BoardState.side == WHITE) {
					if (BoardState.AIplayer == 0) {
						AI_Move(BoardState.board, WHITE, GetMove, wPlayer, bPlayer);
						hp = 2;
					}
					else {
						if(ev.type == ALLEGRO_EVENT_DISPLAY_CLOSE){
							BoardState.GameEnd = true;
							done = 1;
						}
						else if(ev.type == ALLEGRO_EVENT_KEY_DOWN){
							switch(ev.keyboard.keycode){
								case ALLEGRO_KEY_ESCAPE:
									GoGameGoMenu = 1;
									break;
							}
						}
						else if(ev.type == ALLEGRO_EVENT_MOUSE_BUTTON_DOWN){
							if(ev.mouse.button & 1){
								pos_x = ev.mouse.x;
								pos_y = ev.mouse.y;
								GetHumanMove(GetMove, pos_x, pos_y, hp);
								++hp;
							}
						}
					}
				}
				else {
					if (BoardState.AIplayer == 1) {
						AI_Move(BoardState.board, BLACK, GetMove, bPlayer, wPlayer);
						hp = 2;
					}
					else {
						if(ev.type == ALLEGRO_EVENT_DISPLAY_CLOSE){
							BoardState.GameEnd = true;
							done = 1;
						}
						else if(ev.type == ALLEGRO_EVENT_KEY_DOWN){
							switch(ev.keyboard.keycode){
								case ALLEGRO_KEY_ESCAPE:
									GoGameGoMenu = 1;
									break;
							}
						}
						else if(ev.type == ALLEGRO_EVENT_MOUSE_BUTTON_DOWN){
							if(ev.mouse.button & 1){
								pos_x = ev.mouse.x;
								pos_y = ev.mouse.y;
								GetHumanMove(GetMove, pos_x, pos_y, hp);
								++hp;
							}
						}
					}
				}
						
				//***********************************************************************************************************************************************************************
			
				if(hp == 2){
					if (BoardState.board[GetMove[0][0]][GetMove[0][1]] == WP || BoardState.board[GetMove[0][0]][GetMove[0][1]] == BP) {
						typePieces = PAWN;
					}
					else if (BoardState.board[GetMove[0][0]][GetMove[0][1]] == WK || BoardState.board[GetMove[0][0]][GetMove[0][1]] == BK) {
						typePieces = KING;
					}

					if (BoardState.side == WHITE) {
						PMoveValue = move(BoardState.board, BoardState.side, typePieces, GetMove[0][0], GetMove[0][1], GetMove[1][0], GetMove[1][1], HUMAN, wPlayer);
						PawnState(BoardState.board, WHITE, &wPlayer);
						PawnState(BoardState.board, BLACK, &bPlayer);
						if (PMoveValue == 1) {
							BoardState.side = BLACK;
							ChangeToKing(BoardState.board);
						}
						else if (PMoveValue == 2) {
							for (int i = 1; i < 13; ++i) {
								if ((wPlayer.PiecesPosition[i].xp == GetMove[1][0]) && (wPlayer.PiecesPosition[i].yp == GetMove[1][1])) {
									if (wPlayer.PiecesPosition[i].OnBeating == 0) {
										BoardState.side = BLACK;
									}
								}
							}
						}
						else{
							al_show_native_message_box(NULL, NULL, NULL, "WRONG MOVE", NULL, NULL);
						}
					}
					else if (BoardState.side == BLACK) {
						PMoveValue = move(BoardState.board, BoardState.side, typePieces, GetMove[0][0], GetMove[0][1], GetMove[1][0], GetMove[1][1], HUMAN, bPlayer);
						PawnState(BoardState.board, WHITE, &wPlayer);
						PawnState(BoardState.board, BLACK, &bPlayer);
						if (PMoveValue == 1) {
							BoardState.side = WHITE;
							ChangeToKing(BoardState.board);
						}
						else if (PMoveValue == 2) {
							for (int i = 1; i < 13; ++i) {
								if ((bPlayer.PiecesPosition[i].xp == GetMove[1][0]) && (bPlayer.PiecesPosition[i].yp == GetMove[1][1])) {
									if (bPlayer.PiecesPosition[i].OnBeating == 0) {
										BoardState.side = WHITE;
									}
								}
							}
						}
						else{
							al_show_native_message_box(NULL, NULL, NULL, "WRONG MOVE", NULL, NULL);
						}
					}
					hp = 0;
					GetMove[0][0] = 0;
					GetMove[0][1] = 0;
					GetMove[1][0] = 0;
					GetMove[1][1] = 0;
				}

				/*	PRINT BOARD	*/
				print_board(BoardState.board, display, hp, pos_x, pos_y);
		
				/*	CHECK END	*/
				if (wPlayer.PiecesNumbers == 0) {
					//BLACK WIN
					WinSide = 2;
					BoardState.GameEnd = true;
				}
				else if (bPlayer.PiecesNumbers == 0) {
					//WHITE WIN
					WinSide = 1;
					BoardState.GameEnd = true;
				}

		
				if(WinSide == 1){
					al_show_native_message_box(NULL, NULL, NULL, "WHITE WIN!", NULL, NULL);
				}
				else if(WinSide == 2){
					al_show_native_message_box(NULL, NULL, NULL, "BLACK WIN!", NULL, NULL);
				}
			
			}
			break;
		}
	}

	al_destroy_display(display);
	
	return 0;
}
