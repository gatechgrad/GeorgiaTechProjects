/* Levi D. Smith */
#include <stdio.h>
#include <stdlib.h>
#include <allegro.h>

#define TABLE_ROWS 20
#define TABLE_COLS 10

#define TABLE_INSET_X 200
#define TABLE_INSET_Y 50
#define NEXT_INSET_X 480
#define NEXT_INSET_Y 100
#define STATS_INSET_X 480
#define STATS_INSET_Y 300


#define CELL_WIDTH 20
#define CELL_HEIGHT 20

#define SELECTED_WIDTH 640
#define SELECTED_HEIGHT 480

#define MAX_BLOCK_SIZE 5
#define NUM_PIECES 7

#define LAST_SPEED_LEVEL 11

#define TABLE_HEIGHT TABLE_ROWS * CELL_HEIGHT
#define TABLE_WIDTH TABLE_COLS * CELL_WIDTH


#define DRAW_GRID

/** STRUCTURES **/
typedef struct Piece {
  int iShape[MAX_BLOCK_SIZE][MAX_BLOCK_SIZE];
  int iPositionX;
  int iPositionY;
  int iColor;

} Piece;

typedef struct Player {
  int iScore;
  int iLines;
  int iLevel;
} Player;

/** GLOBAL VARIABLES **/
Piece pieceList[NUM_PIECES];
int iBoard[TABLE_COLS][TABLE_ROWS]; /* board array holds color values of
                                       blocks, or zero for no block */
int iColors[NUM_PIECES + 1];
AUDIOSTREAM *stream;
Piece piece_current;
Piece piece_next;
Player player_one;
int iDropPiece;
int iPlaySound;
int iGameOver;
int iDelay;

/** THREADS **/
void time_handler() {
  int iDelayMin;

/*
  if ((LAST_SPEED_LEVEL - player_one.iLevel) > 1) {
    iDelayMin = LAST_SPEED_LEVEL - player_one.iLevel;

  } else {
    iDelayMin = 1;
  }
*/

  iDelay++;

  /*if (iDelay > 11 - player_one.iLevel) { */
  if (iDelay > 60) {
    iDelay = 0;
    iDropPiece = TRUE;
  }
  
}
END_OF_FUNCTION(time_handler);


/** FUNCTION PROTOTYPES **/
void draw_board(BITMAP *bmp_screen);
void play_game();
void check_joystick();
void shift_piece_left();
void shift_piece_right();
void drop_piece();
void check_for_completed_lines();
void add_block_to_board(Piece piece_to_add);
void repaint();
void draw_piece(BITMAP *bmp_screen, Piece piece_to_draw);
void draw_blocks(BITMAP *bmp_screen);
void draw_block(BITMAP *bmp_graphics, int x, int y, int iInsetX, int iInsetY, int iColor);
void reset();
void make_game_pieces();
void setup_allegro();
void setup_sound();
void beep();
void rotate_piece_left();
void array_copy(int array_from[MAX_BLOCK_SIZE][MAX_BLOCK_SIZE], int array_to[MAX_BLOCK_SIZE][MAX_BLOCK_SIZE]);
void drop_blocks(int iRow);
int main(int iArgCount, char *strArgs[]);



/**
 * draws the game board
 */
void draw_board(BITMAP *bmp_screen) {
  int i;
  int iDrawingColor;


  iDrawingColor = makecol(128, 0, 0);
  rectfill(bmp_screen, 0, 0, SCREEN_W, SCREEN_H, iDrawingColor);


  iDrawingColor = makecol(0, 0, 0);
  rectfill(bmp_screen, TABLE_INSET_X, TABLE_INSET_Y, TABLE_INSET_X + TABLE_WIDTH, TABLE_INSET_Y + TABLE_HEIGHT, iDrawingColor);



#ifdef DRAW_GRID

  iDrawingColor = makecol(64, 64, 64);
  for (i = 1; i < TABLE_ROWS; i++) {
    hline(bmp_screen, TABLE_INSET_X, i * CELL_HEIGHT + TABLE_INSET_Y, TABLE_INSET_X + TABLE_WIDTH, iDrawingColor);

  }

  for (i = 1; i < TABLE_COLS; i++) {
    vline(bmp_screen, TABLE_INSET_X + i * CELL_WIDTH, TABLE_INSET_Y, TABLE_INSET_Y + TABLE_HEIGHT, iDrawingColor);
  }

#endif

  /* table border */
  iDrawingColor = makecol(255, 255, 255);
  rect(bmp_screen, TABLE_INSET_X - 1, TABLE_INSET_Y - 1, TABLE_INSET_X + TABLE_WIDTH + 1, TABLE_INSET_Y + TABLE_HEIGHT + 1, iDrawingColor);



}

/**
 * main game loop
 */
void play_game() {
  iGameOver = FALSE;
  iDropPiece = FALSE;

  draw_board(screen);

  piece_current = pieceList[random() % NUM_PIECES];
  piece_next = pieceList[random() % NUM_PIECES];
  piece_current.iPositionX = 3;
  piece_current.iPositionY = 0;

  while(!iGameOver) {

    if (keypressed()) {
      switch(readkey() >> 8) {
        case KEY_ESC:
          iGameOver = TRUE;
          break;
        case KEY_LEFT:
          shift_piece_left();
          break;
        case KEY_RIGHT:
          shift_piece_right();
          break;
        case KEY_DOWN:
          drop_piece();
          break;
        case KEY_R:
          reset();
          break;
        case KEY_SPACE:
          rotate_piece_left();
          break;
        case KEY_UP:
          rotate_piece_left();
          break;

      }
      clear_keybuf();


    }

    check_joystick();


    if (iDropPiece == TRUE) {
      drop_piece();
      iDropPiece = FALSE;
    }


    repaint();
	rest(8);

  }

  game_over();

}

/**
 * called when the game is over
 */
void game_over() {
  BITMAP *bmp_temp;
  int iKeepLooping;

  bmp_temp = create_bitmap(SCREEN_W, SCREEN_H);


  draw_board(bmp_temp);
  draw_player_stats(bmp_temp, player_one);
  textout(bmp_temp, font, "GAME OVER", TABLE_INSET_X + 50, TABLE_INSET_Y + 50, makecol(255, 255, 255));

  blit(bmp_temp, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);
  destroy_bitmap(bmp_temp);

  iKeepLooping = TRUE;

  while(iKeepLooping) {
      switch(readkey() >> 8) {
        case KEY_ESC:
          iKeepLooping = FALSE;
          break;
        case KEY_R:
          reset();
          play_game();
          break;


      }
      clear_keybuf();
  }





}


/**
 * checks to see if a joystick button was pressed
 */
void check_joystick() {

    poll_joystick();

    if (joy[0].stick[0].axis[0].d1) {
      shift_piece_left();
    }

    if (joy[0].stick[0].axis[0].d2) {
      shift_piece_right();
    }

    if (joy[0].stick[0].axis[1].d2) {
      drop_piece();
    }

    if (joy[0].button[0].b || joy[0].button[1].b || joy[0].button[2].b || joy[0].button[3].b) {
      rotate_piece_left();
    }

}

/**
 * moves the piece left
 */
void shift_piece_left() {
  int i, j;
  int iShiftPiece;

  iShiftPiece = TRUE;

  /* check to see if all blocks are in bound */
  for (i = 0; i < MAX_BLOCK_SIZE; i++) {
    for (j = 0; j < MAX_BLOCK_SIZE; j++) {
      if (piece_current.iShape[i][j] > 0) {
        if (piece_current.iPositionY + j - MAX_BLOCK_SIZE + 1 >= 0) {
          if (piece_current.iPositionX + i - 1 < 0) {
            iShiftPiece = FALSE;
          } else if (iBoard[piece_current.iPositionX + i - 1][piece_current.iPositionY + j - MAX_BLOCK_SIZE + 1] > 0) {
            iShiftPiece = FALSE;
          }
        }
      }
    }
  }



  if (iShiftPiece) {
    piece_current.iPositionX--;
  }
}

/**
 * moves the piece right
 */
void shift_piece_right() {
  int i, j;
  int iShiftPiece;

  iShiftPiece = TRUE;

  /* check to see if all blocks are in bound */
  for (i = 0; i < MAX_BLOCK_SIZE; i++) {
    for (j = 0; j < MAX_BLOCK_SIZE; j++) {
      if (piece_current.iShape[i][j] > 0) {
        if (piece_current.iPositionY + j - MAX_BLOCK_SIZE + 1 >= 0) {
          if (piece_current.iPositionX + i + 2 > TABLE_COLS) {
            iShiftPiece = FALSE;
          } else if (iBoard[piece_current.iPositionX + i + 1][piece_current.iPositionY + j - MAX_BLOCK_SIZE + 1] > 0) {
            iShiftPiece = FALSE;
          }
        }
      }
    }
  }



  if (iShiftPiece) {
    piece_current.iPositionX++;
  }


}


/**
 * drops the piece down one row
 */
void drop_piece() {
  int i, j;
  int iStackPiece;

  iStackPiece = FALSE;

  for (i = 0; i < MAX_BLOCK_SIZE; i++) {
    for (j = 0; j < MAX_BLOCK_SIZE; j++) {
      if (piece_current.iShape[i][j] > 0) {
        if ( (piece_current.iPositionY + j - MAX_BLOCK_SIZE + 3 > 0) &&
             ((piece_current.iPositionY + j - MAX_BLOCK_SIZE + 3) > TABLE_ROWS)
           ) {
          iStackPiece = TRUE;
        } else if ( (piece_current.iPositionY + j - MAX_BLOCK_SIZE + 2 > 0) &&
                    (iBoard[piece_current.iPositionX + i][piece_current.iPositionY + j - MAX_BLOCK_SIZE + 2] > 0)
                  ) {
          iStackPiece = TRUE;
        }

      }
    }
  }


  if (iStackPiece) {

    add_block_to_board(piece_current);
    check_for_completed_lines();

    piece_current = piece_next;
    piece_next = pieceList[random() % NUM_PIECES];
    piece_current.iPositionX = 3;
    piece_current.iPositionY = 0;
  } else {
    piece_current.iPositionY++;
  }
}

/**
 * rotates the piece
 */
void rotate_piece_left() {
  int iNewArray[MAX_BLOCK_SIZE][MAX_BLOCK_SIZE];
  int i, j;

  for (i = 0; i < MAX_BLOCK_SIZE; i++) {
    for (j = 0; j < MAX_BLOCK_SIZE; j++) {
      iNewArray[i][j] = piece_current.iShape[MAX_BLOCK_SIZE - 1 - j][i];
    }
  }

  array_copy(iNewArray, piece_current.iShape);
  /* free(iNewArray); */

}

/**
 * copies one shape array into another
 */
void array_copy(int array_from[MAX_BLOCK_SIZE][MAX_BLOCK_SIZE], int array_to[MAX_BLOCK_SIZE][MAX_BLOCK_SIZE]) {
  int i, j;

  for (i = 0; i < MAX_BLOCK_SIZE; i++) {
    for (j = 0; j < MAX_BLOCK_SIZE; j++) {
      array_to[i][j] = array_from[i][j];

    }
  }
}



/**
 * checks the board for completed lines
 */
void check_for_completed_lines() {
  int i, j;
  int iLineComplete;
  int iTotalLinesComplete;

  iTotalLinesComplete = 0;

  for (i = 0; i < TABLE_ROWS; i++) {

    iLineComplete = TRUE;
    for (j = 0; j < TABLE_COLS; j++) {
      if(iBoard[j][i] <= 0)  {
        iLineComplete = FALSE;
      }
    }

    if (iLineComplete) {
      iTotalLinesComplete++;
      player_one.iLines++;
      player_one.iLevel = (player_one.iLines / 10 > player_one.iLines) ? player_one.iLines / 10 : player_one.iLines;
      for (j = 0; j < TABLE_COLS; j++) {
        iBoard[j][i] = 0;
      }
      drop_blocks(i);
    }
  }

  switch(iTotalLinesComplete) {
    case 1:
      player_one.iScore += 100;
      break;
    case 2:
      player_one.iScore += 200;
      break;
    case 3:
      player_one.iScore += 300;
      break;
    case 4:
      player_one.iScore += 1000;
      break;
    default:
      player_one.iScore += 0;
  }

}

/**
 * drops all of the blocks above the row specified one row
 */
void drop_blocks(int iRow) {
  int i, j;

  for (i = iRow - 1; i >= 0; i--) {
    for (j = 0; j < TABLE_COLS; j++) {
      iBoard[j][i + 1] = iBoard[j][i];
    }
  }
      
}

/**
 * adds the block to the board
 */
void add_block_to_board(Piece piece_to_add) {
  int i, j;

    for (i = 0; i < MAX_BLOCK_SIZE; i++) {
      for (j = 0; j < MAX_BLOCK_SIZE; j++) {
        if (piece_current.iShape[i][j] > 0) {
          if ((piece_to_add.iPositionY + j - MAX_BLOCK_SIZE + 1) < 0) {
            iGameOver = TRUE;
          }
          iBoard[piece_to_add.iPositionX + i][piece_to_add.iPositionY + j - MAX_BLOCK_SIZE + 1] = piece_current.iColor;
        }
      }
    }

}


/**
 * redraws everything
 */
void repaint() {
  BITMAP *bmp_temp;
  bmp_temp = create_bitmap(SCREEN_W, SCREEN_H);


  draw_board(bmp_temp);
  draw_piece(bmp_temp, piece_current);
  draw_next_piece(bmp_temp, piece_next);
  draw_blocks(bmp_temp);
  draw_player_stats(bmp_temp, player_one);


  blit(bmp_temp, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

  destroy_bitmap(bmp_temp);

}


/**
 * draws the current piece on the board
 */
void draw_piece(BITMAP *bmp_screen, Piece piece_to_draw) {
  int i, j;
  char strTemp[1024];

  for (i = 0; i < MAX_BLOCK_SIZE; i++) {
    for (j = 0; j < MAX_BLOCK_SIZE; j++) {
      if (piece_to_draw.iShape[i][j] > 0) {
          draw_block(bmp_screen, piece_to_draw.iPositionX + i, piece_to_draw.iPositionY + j - MAX_BLOCK_SIZE + 1, TABLE_INSET_X, TABLE_INSET_Y, iColors[piece_to_draw.iColor]);
      }
    }
  }

  sprintf(strTemp, "X: %d, Y: %d", piece_current.iPositionX, piece_current.iPositionY);
  textout(bmp_screen, font, strTemp, 0, 0, makecol(255, 255, 255));
}


/**
 * draws the piece in the preview square
 */
void draw_next_piece(BITMAP *bmp_graphics, Piece piece_to_draw) {
  int i, j;

  rectfill(bmp_graphics, NEXT_INSET_X, NEXT_INSET_Y, NEXT_INSET_X + (MAX_BLOCK_SIZE * CELL_WIDTH), NEXT_INSET_Y + (MAX_BLOCK_SIZE * CELL_HEIGHT), makecol(0, 0, 0));
  rect(bmp_graphics, NEXT_INSET_X, NEXT_INSET_Y, NEXT_INSET_X + (MAX_BLOCK_SIZE * CELL_WIDTH), NEXT_INSET_Y + (MAX_BLOCK_SIZE * CELL_HEIGHT), makecol(255, 255, 255));


  for (i = 0; i < MAX_BLOCK_SIZE; i++) {
    for (j = 0; j < MAX_BLOCK_SIZE; j++) {
      if (piece_to_draw.iShape[i][j] > 0) {
          draw_block(bmp_graphics, i, j + MAX_BLOCK_SIZE, NEXT_INSET_X, NEXT_INSET_Y - (MAX_BLOCK_SIZE * CELL_HEIGHT), iColors[piece_to_draw.iColor]);
      }
    }
  }

}


/**
 * draws the blocks on the board
 */
void draw_blocks(BITMAP *bmp_screen) {
  int i, j;
  int k;


  for (i = 0; i < TABLE_COLS; i++) {
    for (j = 0; j < TABLE_ROWS; j++) {
      if (iBoard[i][j] > 0) {
        draw_block(bmp_screen, i, j, TABLE_INSET_X, TABLE_INSET_Y, iColors[iBoard[i][j]]);
        beep();

      }
    }
  }

}

/**
 * draws a block at the specified location
 */
void draw_block(BITMAP *bmp_graphics, int x, int y, int iInsetX, int iInsetY, int iColor) {
  if ( (x >= 0) && (y >= 0) && (x < TABLE_COLS) && (y < TABLE_ROWS)) {
    rectfill(bmp_graphics, iInsetX + (x * CELL_HEIGHT), iInsetY + (y * CELL_WIDTH), iInsetX + ((x + 1) * CELL_HEIGHT), iInsetY + ((y + 1) * CELL_WIDTH), iColor);
  }
}

/**
 * draws a table of the player's stats
 */
void draw_player_stats(BITMAP *bmp_graphics, Player the_player) {
  char strTemp[1024];
  int x, y;

  x = STATS_INSET_X;
  y = STATS_INSET_Y;

  rectfill(bmp_graphics, x, y, x + 128, y + 72, makecol(0, 0, 0));
  rect(bmp_graphics, x, y, x + 128, y + 72, makecol(255, 255, 255));


  sprintf(strTemp, "Score: %d", the_player.iScore);
  textout(bmp_graphics, font, strTemp, x + 20, y + 12, makecol(255, 255, 255));
  sprintf(strTemp, "Lines: %d", the_player.iLines);
  textout(bmp_graphics, font, strTemp, x + 20, y + 24, makecol(255, 255, 255));
  sprintf(strTemp, "Level: %d", the_player.iLevel);
  textout(bmp_graphics, font, strTemp, x + 20, y + 36, makecol(255, 255, 255));

}

/**
 * resets and starts a new game
 */
void reset() {
  int i, j;

  for (i = 0; i < TABLE_COLS; i++) {
    for (j = 0; j < TABLE_ROWS; j++) {
      iBoard[i][j] = 0;
    }
  }

  player_one.iLevel = 0;
  player_one.iScore = 0;
  player_one.iLines = 0;

}

/**
 * creates the game pieces
 */
void make_game_pieces() {
  iColors[0] = makecol(0, 0, 0);
  iColors[1] = makecol(128, 0, 0);
  iColors[2] = makecol(128, 128, 0);
  iColors[3] = makecol(128, 0, 128);
  iColors[4] = makecol(0, 128, 0);
  iColors[5] = makecol(0, 128, 128);
  iColors[6] = makecol(0, 0, 128);
  iColors[7] = makecol(128, 128, 128);


  /** T PIECE **/
  pieceList[0].iShape[0][0] = 0;
  pieceList[0].iShape[0][1] = 0;
  pieceList[0].iShape[0][2] = 0;
  pieceList[0].iShape[0][3] = 0;
  pieceList[0].iShape[0][4] = 0;

  pieceList[0].iShape[1][0] = 0;
  pieceList[0].iShape[1][1] = 0;
  pieceList[0].iShape[1][2] = 1;
  pieceList[0].iShape[1][3] = 0;
  pieceList[0].iShape[1][4] = 0;

  pieceList[0].iShape[2][0] = 0;
  pieceList[0].iShape[2][1] = 1;
  pieceList[0].iShape[2][2] = 1;
  pieceList[0].iShape[2][3] = 0;
  pieceList[0].iShape[2][4] = 0;

  pieceList[0].iShape[3][0] = 0;
  pieceList[0].iShape[3][1] = 0;
  pieceList[0].iShape[3][2] = 1;
  pieceList[0].iShape[3][3] = 0;
  pieceList[0].iShape[3][4] = 0;

  pieceList[0].iShape[4][0] = 0;
  pieceList[0].iShape[4][1] = 0;
  pieceList[0].iShape[4][2] = 0;
  pieceList[0].iShape[4][3] = 0;
  pieceList[0].iShape[4][4] = 0;

  pieceList[0].iColor = 1;

  /** RIGHT CROOKED PIECE **/
  pieceList[1].iShape[0][0] = 0;
  pieceList[1].iShape[0][1] = 0;
  pieceList[1].iShape[0][2] = 0;
  pieceList[1].iShape[0][3] = 0;
  pieceList[1].iShape[0][4] = 0;

  pieceList[1].iShape[1][0] = 0;
  pieceList[1].iShape[1][1] = 1;
  pieceList[1].iShape[1][2] = 0;
  pieceList[1].iShape[1][3] = 0;
  pieceList[1].iShape[1][4] = 0;

  pieceList[1].iShape[2][0] = 0;
  pieceList[1].iShape[2][1] = 1;
  pieceList[1].iShape[2][2] = 1;
  pieceList[1].iShape[2][3] = 0;
  pieceList[1].iShape[2][4] = 0;

  pieceList[1].iShape[3][0] = 0;
  pieceList[1].iShape[3][1] = 0;
  pieceList[1].iShape[3][2] = 1;
  pieceList[1].iShape[3][3] = 0;
  pieceList[1].iShape[3][4] = 0;

  pieceList[1].iShape[4][0] = 0;
  pieceList[1].iShape[4][1] = 0;
  pieceList[1].iShape[4][2] = 0;
  pieceList[1].iShape[4][3] = 0;
  pieceList[1].iShape[4][4] = 0;

  pieceList[1].iColor = 2;


  /** LEFT CROOKED PIECE **/
  pieceList[2].iShape[0][0] = 0;
  pieceList[2].iShape[0][1] = 0;
  pieceList[2].iShape[0][2] = 0;
  pieceList[2].iShape[0][3] = 0;
  pieceList[2].iShape[0][4] = 0;

  pieceList[2].iShape[1][0] = 0;
  pieceList[2].iShape[1][1] = 0;
  pieceList[2].iShape[1][2] = 1;
  pieceList[2].iShape[1][3] = 0;
  pieceList[2].iShape[1][4] = 0;

  pieceList[2].iShape[2][0] = 0;
  pieceList[2].iShape[2][1] = 1;
  pieceList[2].iShape[2][2] = 1;
  pieceList[2].iShape[2][3] = 0;
  pieceList[2].iShape[2][4] = 0;

  pieceList[2].iShape[3][0] = 0;
  pieceList[2].iShape[3][1] = 1;
  pieceList[2].iShape[3][2] = 0;
  pieceList[2].iShape[3][3] = 0;
  pieceList[2].iShape[3][4] = 0;

  pieceList[2].iShape[4][0] = 0;
  pieceList[2].iShape[4][1] = 0;
  pieceList[2].iShape[4][2] = 0;
  pieceList[2].iShape[4][3] = 0;
  pieceList[2].iShape[4][4] = 0;

  pieceList[2].iColor = 3;

  /** BOX PIECE **/
  pieceList[3].iShape[0][0] = 0;
  pieceList[3].iShape[0][1] = 0;
  pieceList[3].iShape[0][2] = 0;
  pieceList[3].iShape[0][3] = 0;

  pieceList[3].iShape[1][0] = 0;
  pieceList[3].iShape[1][1] = 1;
  pieceList[3].iShape[1][2] = 1;
  pieceList[3].iShape[1][3] = 0;

  pieceList[3].iShape[2][0] = 0;
  pieceList[3].iShape[2][1] = 1;
  pieceList[3].iShape[2][2] = 1;
  pieceList[3].iShape[2][3] = 0;

  pieceList[3].iShape[3][0] = 0;
  pieceList[3].iShape[3][1] = 0;
  pieceList[3].iShape[3][2] = 0;
  pieceList[3].iShape[3][3] = 0;

  pieceList[3].iShape[4][0] = 0;
  pieceList[3].iShape[4][1] = 0;
  pieceList[3].iShape[4][2] = 0;
  pieceList[3].iShape[4][3] = 0;
  pieceList[3].iShape[4][4] = 0;

  pieceList[3].iColor = 4;


  /** L PIECE **/
  pieceList[4].iShape[0][0] = 0;
  pieceList[4].iShape[0][1] = 0;
  pieceList[4].iShape[0][2] = 0;
  pieceList[4].iShape[0][3] = 0;

  pieceList[4].iShape[1][0] = 0;
  pieceList[4].iShape[1][1] = 0;
  pieceList[4].iShape[1][2] = 0;
  pieceList[4].iShape[1][3] = 0;

  pieceList[4].iShape[2][0] = 0;
  pieceList[4].iShape[2][1] = 1;
  pieceList[4].iShape[2][2] = 1;
  pieceList[4].iShape[2][3] = 1;

  pieceList[4].iShape[3][0] = 0;
  pieceList[4].iShape[3][1] = 0;
  pieceList[4].iShape[3][2] = 0;
  pieceList[4].iShape[3][3] = 1;

  pieceList[4].iShape[4][0] = 0;
  pieceList[4].iShape[4][1] = 0;
  pieceList[4].iShape[4][2] = 0;
  pieceList[4].iShape[4][3] = 0;
  pieceList[4].iShape[4][4] = 0;

  pieceList[4].iColor = 5;

  /** BACKWARDS L PIECE **/
  pieceList[5].iShape[0][0] = 0;
  pieceList[5].iShape[0][1] = 0;
  pieceList[5].iShape[0][2] = 0;
  pieceList[5].iShape[0][3] = 0;

  pieceList[5].iShape[1][0] = 0;
  pieceList[5].iShape[1][1] = 0;
  pieceList[5].iShape[1][2] = 0;
  pieceList[5].iShape[1][3] = 1;

  pieceList[5].iShape[2][0] = 0;
  pieceList[5].iShape[2][1] = 1;
  pieceList[5].iShape[2][2] = 1;
  pieceList[5].iShape[2][3] = 1;

  pieceList[5].iShape[3][0] = 0;
  pieceList[5].iShape[3][1] = 0;
  pieceList[5].iShape[3][2] = 0;
  pieceList[5].iShape[3][3] = 0;

  pieceList[5].iShape[4][0] = 0;
  pieceList[5].iShape[4][1] = 0;
  pieceList[5].iShape[4][2] = 0;
  pieceList[5].iShape[4][3] = 0;
  pieceList[5].iShape[4][4] = 0;

  pieceList[5].iColor = 6;

  /** LONG PIECE **/
  pieceList[6].iShape[0][0] = 0;
  pieceList[6].iShape[0][1] = 0;
  pieceList[6].iShape[0][2] = 0;
  pieceList[6].iShape[0][3] = 0;
  pieceList[6].iShape[0][4] = 0;

  pieceList[6].iShape[1][0] = 0;
  pieceList[6].iShape[1][1] = 0;
  pieceList[6].iShape[1][2] = 0;
  pieceList[6].iShape[1][3] = 0;
  pieceList[6].iShape[1][4] = 0;

  pieceList[6].iShape[2][0] = 0;
  pieceList[6].iShape[2][1] = 0;
  pieceList[6].iShape[2][2] = 0;
  pieceList[6].iShape[2][3] = 0;
  pieceList[6].iShape[2][4] = 0;

  pieceList[6].iShape[3][0] = 1;
  pieceList[6].iShape[3][1] = 1;
  pieceList[6].iShape[3][2] = 1;
  pieceList[6].iShape[3][3] = 1;
  pieceList[6].iShape[3][4] = 0;

  pieceList[6].iShape[4][0] = 0;
  pieceList[6].iShape[4][1] = 0;
  pieceList[6].iShape[4][2] = 0;
  pieceList[6].iShape[4][3] = 0;
  pieceList[6].iShape[4][4] = 0;


  pieceList[6].iColor = 7;


}



/**
 * calls all the allegro startup methods
 */
void setup_allegro() {
  allegro_init();
  install_keyboard();
  install_timer();
  set_color_depth(16);
  set_gfx_mode(GFX_AUTODETECT, SELECTED_WIDTH, SELECTED_HEIGHT, 0, 0);
  set_pallete(desktop_pallete);

  setup_sound();
  install_int(time_handler, 16);

   if (install_joystick(JOY_TYPE_AUTODETECT) != 0) {
      printf("\nError initialising joystick\n%s\n\n", allegro_error);
      exit(1);
   }


}

/**
 * sets up the sound system
 */
void setup_sound() {
  if (iPlaySound) {
    if (install_sound(DIGI_AUTODETECT, MIDI_NONE, NULL) != 0) {
      printf("Error initialising sound system\n%s\n", allegro_error);
    }

    stream = play_audio_stream(4096, 8, FALSE, 22050, 255, 128);
    if (!stream) {
       printf("Error creating audio stream!\n");
    }
  }
}


/**
 * plays a simple beep
 */
void beep() {
  unsigned char *p;

  if (iPlaySound) {
    p = get_audio_stream_buffer(stream);

    if (p) {
      p[0] = (0 >> 16) & 0xFF;
    }
  }

}



/**
 * starting point
 */
int main(int iArgCount, char *strArgs[]) {
  int i;

  iPlaySound = TRUE;

  for (i = 1; i < iArgCount; i++) {
    if (stricmp(strArgs[i], "-nosound") == 0) {
      iPlaySound = FALSE;
    }
  }

  setup_allegro();
  make_game_pieces();

  play_game();

  remove_int(time_handler);
  allegro_exit();



  return 0;
}
