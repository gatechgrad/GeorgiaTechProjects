#include <stdio.h>
#include <stdlib.h>
#include <allegro.h>

#include "allegro.h"


/* CONSTANTS */
#define MAP_ROWS 100
#define MAP_COLS 100
#define INITIAL_PERCENT 5
#define CELL_WIDTH 2
#define CELL_HEIGHT 2
#define DELAY_DEFAULT 100

/* FUNCTION PROTOTYPES */
void start_game(void);
void setup_allegro(void);
void initialize_map(int iMap[MAP_ROWS][MAP_COLS], int iRows, int iCols, int iPercent);
void reset_map(int iMap[MAP_ROWS][MAP_COLS]);
int main(void);
void get_options(void);

/* GLOBAL VARIABLES */
int iResolutionX;
int iResolutionY;
int iGraphicsMode;
int iColorDepth;


/**
 * starts the game
 */
void start_game(void) {
    int iKeyPressed;
    BITMAP *bmpBuffer;
    int iMap[MAP_ROWS][MAP_COLS];
    int iNewMap[MAP_ROWS][MAP_COLS];
    int i, j;
    int iColor;
    int iNeighborsAlive;
    int iKeepLooping;
    int iIntensity;
    int iColorChoice;
    int iDelay;
    int iStop;
    char *str;

    iDelay = DELAY_DEFAULT;



    bmpBuffer = create_bitmap(SCREEN_W, SCREEN_H);

    iKeyPressed = -1;
    iColorChoice = 0;

    iKeepLooping = TRUE;
    iStop = FALSE;
    
    reset_map(iMap);
    reset_map(iNewMap);
    initialize_map(iMap, MAP_ROWS, MAP_COLS, INITIAL_PERCENT);

    while (iKeepLooping) {

       if (!iStop) {
        
        clear(bmpBuffer);


        for (i = 0; i < MAP_COLS; i++) {
            for (j = 0; j < MAP_ROWS; j++) {
                if (iMap[i][j] > 0) {

                    iIntensity = 64 + iMap[i][j] * 32;
                    if (iIntensity > 255) {
                        iIntensity = 255;
                    }

                    switch (iColorChoice) {
                        case 0:
                            iColor = makecol(0, iIntensity, iIntensity);
                            break;
                        case 1:
                            iColor = makecol(iIntensity, 0, 0);
                            break;
                        case 2:
                            iColor = makecol(iIntensity, iIntensity, 0);
                            break;
                        case 3:
                            iColor = makecol(0, 0, iIntensity);
                            break;
                        case 4:
                            iColor = makecol(0, iIntensity, 0);
                            break;
                        case 5:
                            iColor = makecol(iIntensity, 0, iIntensity);
                            break;
                        default:
                            iColorChoice = 0;
                            iColor = makecol(0, iIntensity, iIntensity);
                    }



                    rectfill(bmpBuffer, i * CELL_WIDTH, j * CELL_HEIGHT, ((i + 1) * CELL_WIDTH) - 1, ((j + 1) * CELL_HEIGHT) - 1, iColor);
                }
            }
        }


        for (i = 0; i < MAP_ROWS; i++) {
            for (j = 0; j < MAP_COLS; j++) {
                iNeighborsAlive = 0;

                /* check left neighbors */
                if (iMap[get_cell_number(i - 1, 0, MAP_ROWS)][get_cell_number(j - 1, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i - 1, 0, MAP_ROWS)][get_cell_number(j - 1, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }

                if (iMap[get_cell_number(i - 1, 0, MAP_ROWS)][get_cell_number(j, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i - 1, 0, MAP_ROWS)][get_cell_number(j, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }

                if (iMap[get_cell_number(i - 1, 0, MAP_ROWS)][get_cell_number(j + 1, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i - 1, 0, MAP_ROWS)][get_cell_number(j + 1, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }

                if (iMap[get_cell_number(i, 0, MAP_ROWS)][get_cell_number(j - 1, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i, 0, MAP_ROWS)][get_cell_number(j - 1, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }

                if (iMap[get_cell_number(i, 0, MAP_ROWS)][get_cell_number(j + 1, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i, 0, MAP_ROWS)][get_cell_number(j + 1, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }

                if (iMap[get_cell_number(i + 1, 0, MAP_ROWS)][get_cell_number(j - 1, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i + 1, 0, MAP_ROWS)][get_cell_number(j - 1, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }

                if (iMap[get_cell_number(i + 1, 0, MAP_ROWS)][get_cell_number(j, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i + 1, 0, MAP_ROWS)][get_cell_number(j, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }

                if (iMap[get_cell_number(i + 1, 0, MAP_ROWS)][get_cell_number(j + 1, 0, MAP_COLS)]) {
                    if (iMap[get_cell_number(i + 1, 0, MAP_ROWS)][get_cell_number(j + 1, 0, MAP_COLS)] > 0) {
                        iNeighborsAlive++;
                    }
                }





                

                /* rules for a current cell */
                if (iMap[i][j] > 0) {
                    if (iNeighborsAlive < 2) {
                        iNewMap[i][j] = 0;

                    } else if (iNeighborsAlive > 3) {
                        iNewMap[i][j] = 0;
                    } else {
                        iNewMap[i][j] = iMap[i][j] + 1;
                    }

                } else if (iMap[i][j] <= 0) {
                    if (iNeighborsAlive > 2) {
                        iNewMap[i][j] = 1;
                    } else {
                        iNewMap[i][j] = 0;
                    }

                }


            }
        }


        for (i = 0; i < MAP_ROWS; i++) {
            for (j = 0; j < MAP_COLS; j++) {
                iMap[i][j] = iNewMap[i][j];
            }
        }

        iColor = makecol(0, 128, 128);

        

        textout(bmpBuffer, font, "Game of Life", 210, 16, iColor);
        textout(bmpBuffer, font, "R: Reset", 210, 48, iColor);
        textout(bmpBuffer, font, "C: Colors", 210, 64, iColor);
        textout(bmpBuffer, font, "U: Speed Up", 210, 80, iColor);
        textout(bmpBuffer, font, "D: Speed Down", 210, 96, iColor);
        textout(bmpBuffer, font, "P: Pause/Resume", 210, 112, iColor);
        textout(bmpBuffer, font, "ESC: Exit", 210, 128, iColor);
        
        
        blit(bmpBuffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);
        rest(iDelay);
       }

        if (keypressed()) {

            iKeyPressed = readkey();

            switch (iKeyPressed >> 8) {
                case KEY_R:
                    reset_map(iMap);
                    reset_map(iNewMap);
                    initialize_map(iMap, MAP_ROWS, MAP_COLS, INITIAL_PERCENT);
                    break;
                case KEY_C:
                    iColorChoice++;
                    if (iColorChoice == 6) {
                        iColorChoice = 0;
                    }
                    break;
                case KEY_P:
                    if (iStop == FALSE) {
                      iStop = TRUE;
                    } else {
                        iStop = FALSE;
                    }
                    
                    break;
                case KEY_U:
                    iDelay -= 20;
                    if (iDelay < 0) {
                        iDelay = 0;
                    }
                    break;
                case KEY_D:
                    iDelay += 20;
                    if (iDelay > 2000) {
                        iDelay = 2000;
                    }
                    break;
                case KEY_ESC:
                    iKeepLooping = FALSE;
                default:
                    
            }
        }

        clear_keybuf();
    }

    destroy_bitmap(bmpBuffer);
}


/**
 * loads map with random data
 */
void initialize_map(int iMap[MAP_ROWS][MAP_COLS], int iRows, int iCols, int iPercent) {
    int i, j;

    for (i = 0; i < iRows; i++) {
        for (j = 0; j < iCols; j++) {
            if ((random() % 100) < iPercent) {
                iMap[i][j] = 1;
            }
        }
    }

}

/**
 *  returns iMax - 1 if i is less than iMin,
 *          iMin if i is greater than or equal to iMax,
 *          or i if i is between iMin and iMax
 */
int get_cell_number(int i, int iMin, int iMax) {
    int iToReturn;

    if (i < iMin) {
        iToReturn = iMax - 1;

    } else if (i >= iMax) {
        iToReturn = iMin;
    } else {
        iToReturn = i;
    }

    return iToReturn;


}

/**
 * resets a map to all zeros
 */

void reset_map(int iMap[MAP_ROWS][MAP_COLS]) {
    int i, j;


    for (i = 0; i < MAP_ROWS; i++) {
        for (j = 0; j < MAP_COLS; j++) {
            iMap[i][j] = 0;
        }
    }
}




/**
 * sets up Allegro
 */
void setup_allegro(void) {
    allegro_init();
    install_keyboard();
    install_timer();
    set_color_depth(iColorDepth);
    
    set_gfx_mode(iGraphicsMode, iResolutionX, iResolutionY, 0, 0);


    set_pallete(desktop_pallete);


    text_mode(-1);


}

/**
 * get_options
 */
void get_options() {

  int iChoice;


  printf("Choose Graphics Mode:\n");
  printf("1) AutoDetect\n");
  printf("2) VGA\n");
  printf("3) Mode X\n");
  printf("4) VESA 1\n");
  printf("5) VESA 2B\n");
  printf("6) VESA 2L\n");
  printf("7) VESA 3\n");
  printf("8) VBEAF\n");
  printf("9) XTENDED\n");
  scanf("%d", &iChoice);

  switch(iChoice) {
    case 1:
      iGraphicsMode = GFX_AUTODETECT;
      break;
    case 2:
      iGraphicsMode = GFX_VGA;
      break;
    case 3:
      iGraphicsMode = GFX_MODEX;
      break;
    case 4:
      iGraphicsMode = GFX_VESA1;
      break;
    case 5:
      iGraphicsMode = GFX_VESA2B;
      break;
    case 6:
      iGraphicsMode = GFX_VESA2L;
      break;
    case 7:
      iGraphicsMode = GFX_VESA3;
      break;
    case 8:
      iGraphicsMode = GFX_VBEAF;
      break;
    case 9:
      iGraphicsMode = GFX_XTENDED;
      break;
    default:
      iGraphicsMode = GFX_VGA;

  }



  printf("Choose Resolution:\n");
  printf("1) 320 x 200\n");
  printf("2) 640 x 480\n");
  printf("3) 800 x 600\n\n");
  scanf("%d", &iChoice);

  switch(iChoice) {
    case 1:
      iResolutionX = 320;
      iResolutionY = 200;
      break;
    case 2:
      iResolutionX = 640;
      iResolutionY = 480;
      break;
    case 3:
      iResolutionX = 800;
      iResolutionY = 600;
      break;
    default:
      iResolutionX = 320;
      iResolutionY = 200;
  }


  printf("Choose Color Depth:\n");
  printf("1) 8 bit\n");
  printf("2) 15 bit\n");
  printf("3) 16 bit\n");
  printf("4) 24 bit\n");
  printf("5) 32 bit\n\n");
  scanf("%d", &iChoice);

  switch(iChoice) {
    case 1:
      iColorDepth = 8;
      break;
    case 2:
      iColorDepth = 15;
      break;
    case 3:
      iColorDepth = 16;
      break;
    case 4:
      iColorDepth = 24;
      break;
    case 5:
      iColorDepth = 32;
      break;
    default:
      iColorDepth = 8;
  }

}


/**
 * called when program is executed
 */
int main(void) {
    get_options();

    setup_allegro();
    start_game();



    return 0;
}

