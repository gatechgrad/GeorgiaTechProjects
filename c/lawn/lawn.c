#include <stdio.h>
#include <stdlib.h>
#include <allegro.h>

#define FALSE 0
#define TRUE 1

#define UNCUT_GRASS 0
#define CUT_GRASS 1

#define ROWS 14
#define COLS 20

/** STRUCTS **/
typedef struct player {
  int iMoney;
  int iDamage;
  int iLevel;

  volatile int iTime;

  int iSquareRate;
  int iCompleteBonus;

  int iLocationX;
  int iLocationY;

  int iLives;

} player;



/** GLOBAL VARIABLES **/
PALLETE my_pallete;
BITMAP *buffer;
BITMAP *bmp_current_mower;
BITMAP *bmp_mower[1][4];
BITMAP *bmp_grass[2];

int obstacles[COLS][ROWS];
int grass[COLS][ROWS];
player playerOne;

/** FUNCTION PROTOTYPES **/


/**
  * setupAllegro
  **/
void setupAllegro() {
  allegro_init();
  install_keyboard();
  install_timer();

  set_color_depth(16);
  set_gfx_mode(GFX_AUTODETECT, 640, 480, 0, 0);

  text_mode(-1);

  buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(buffer);

}

/**
  * timeHandler
  **/
void timeHandler() {
  playerOne.iTime--;
  if (playerOne.iTime < 0) {
    outOfTime();
  }
}
END_OF_FUNCTION(timeHandler);


/**
  * outOfTime
  **/
void outOfTime() {
  playerOne.iLives--;
  if (playerOne.iLives < 0) {
    gameOver();
  } else {
    loadLevel();
  }
}

/**
  * gameOver
  **/
void gameOver() {
  exit(0);
}

/**
  * loadGame
  **/
void loadGame() {
  bmp_mower[0][0] = load_pcx("mower_l.pcx", my_pallete);
  bmp_mower[0][1] = load_pcx("mower_r.pcx", my_pallete);
  bmp_mower[0][2] = load_pcx("mower_u.pcx", my_pallete);
  bmp_mower[0][3] = load_pcx("mower_d.pcx", my_pallete);

  bmp_grass[UNCUT_GRASS] = load_pcx("grass01.pcx", my_pallete);
  bmp_grass[CUT_GRASS] = load_pcx("grass02.pcx", my_pallete);

  bmp_current_mower = bmp_mower[0][0];

  setupPlayer(playerOne);

  LOCK_VARIABLE(playerOne.iTime);
  LOCK_FUNCTION(timeHandler);

}

/**
  * setupPlayer
  **/
void setupPlayer(player thePlayer) {
  thePlayer.iMoney = 0;
  thePlayer.iLives = 3;
}

/**
  * loadLevel
  **/
void loadLevel() {
  int i;
  int j;

  //plant grass
  for (i = 0; i < COLS; i++) {
    for (j = 0; j < ROWS; j++) {
      grass[i][j] = UNCUT_GRASS;
    }
  }

  //place obstacles


  playerOne.iLocationX = 1;
  playerOne.iLocationY = 1;

  playerOne.iSquareRate = 5;
  playerOne.iCompleteBonus = 500;
  playerOne.iTime = 40;

  runGame();


}

/**
  * loadObstacle
  **/
void loadObstacle(int iNumber, int iCol, int iRow) {
  obstacles[iCol][iRow] = iNumber;
  grass[iCol][iRow] = CUT_GRASS;

}

/**
  * runGame
  **/
void runGame() {
  int iKeepLooping;
  int iHasWon;

  iKeepLooping = TRUE;
  iHasWon = FALSE;

  while((iKeepLooping == TRUE) && (iHasWon == FALSE)) {
    drawBoard();
    if (keypressed()) {
      iKeepLooping = handleKey(readkey() >> 8);
    }

    if(grass[playerOne.iLocationX][playerOne.iLocationY] == UNCUT_GRASS) {
       grass[playerOne.iLocationX][playerOne.iLocationY] = CUT_GRASS;
       playerOne.iMoney += playerOne.iSquareRate;
    }

    iHasWon = checkForWin();

  }

  if (iHasWon == TRUE) {
    playerOne.iMoney += playerOne.iCompleteBonus;
    loadLevel();
  }



}


/**
  * checkForWin
  **/
int checkForWin() {
  int iHasWon;
  int i, j;

  iHasWon = TRUE;

  //if an uncut square is found, the set iHasWon to false
  for (i = 0; i < COLS; i++) {
    for (j = 0; j < ROWS; j++) {
      if (grass[i][j] == UNCUT_GRASS) {
        iHasWon = FALSE;
      }

    }
  }



  return iHasWon;


}



/**
  * handleKey
  **/
int handleKey(int iKey) {
  int iContinueLooping;

  iContinueLooping = TRUE;


  switch(iKey) {
    case KEY_ESC:
      iContinueLooping = FALSE;  //stop looping
      break;

    case KEY_LEFT:
      if (playerOne.iLocationX - 1 > -1) {
        playerOne.iLocationX--;
        bmp_current_mower = bmp_mower[0][0];
      }
      break;
    case KEY_RIGHT:
      if (playerOne.iLocationX + 1 < COLS) {
        playerOne.iLocationX++;
        bmp_current_mower = bmp_mower[0][1];
      }
      break;
    case KEY_UP:
      if (playerOne.iLocationY - 1 > -1) {
        playerOne.iLocationY--;
        bmp_current_mower = bmp_mower[0][2];
      }
      break;
    case KEY_DOWN:
      if (playerOne.iLocationY + 1 < ROWS) {
        playerOne.iLocationY++;
        bmp_current_mower = bmp_mower[0][3];
      }
      break;
    default:
	  break;

  }

  return iContinueLooping;

}


/**
  * drawBoard
  **/
void drawBoard() {
  int i, j;

//  char *strStatusLine;
	char strStatusLine[256];

  clear(buffer);

  for (i = 0; i < COLS; i++) {
    for (j = 0; j < ROWS; j++) {

      switch(grass[i][j]) {
        case UNCUT_GRASS:
          draw_sprite(buffer, bmp_grass[UNCUT_GRASS], i * 32, j * 32);
          break;
        case CUT_GRASS:
          draw_sprite(buffer, bmp_grass[CUT_GRASS], i * 32, j * 32);
          break;
        default:
		  break;

      }
    }
  }


  draw_sprite(buffer, bmp_current_mower, playerOne.iLocationX * 32, playerOne.iLocationY * 32);


  sprintf(strStatusLine, "Level: %d   Money: $%d  Time: %d  Damage: %d", playerOne.iLevel, playerOne.iMoney, playerOne.iTime, playerOne.iDamage);
  textout(buffer, font, strStatusLine, 0, 460, makecol(255, 255, 255));

  blit(buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);


}



/**
  * shutdown
  **/
void shutdown() {
  destroy_bitmap(buffer);

  destroy_bitmap(buffer);
  destroy_bitmap(bmp_mower[0][0]);
  destroy_bitmap(bmp_mower[0][1]);
  destroy_bitmap(bmp_mower[0][2]);
  destroy_bitmap(bmp_mower[0][3]);

  destroy_bitmap(bmp_grass[UNCUT_GRASS]);
  destroy_bitmap(bmp_grass[CUT_GRASS]);

}


/**
  * main
  **/
int main(void) {
  setupAllegro();

  loadGame();

  install_int_ex(timeHandler, SECS_TO_TIMER(1));
  loadLevel();

  shutdown();
  return 0;
}
