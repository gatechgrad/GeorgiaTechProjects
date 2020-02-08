#include <stdio.h>
#include <allegro.h>
#include "band_dat.h"

#define PIC_TITLE_1 "title_1.pcx"
#define PIC_TITLE_3 "title_3.pcx"
#define PIC_TITLE_4 "title_4.pcx"
#define PIC_MENU_CURSOR "menu_cursor.pcx"

#define PIC_BUTTONBAR "buttonbar.pcx"
#define PIC_FIELD "field.pcx"
#define PIC_BLANK "blank.pcx"
#define PIC_COMMAND_SOFTWARE_LOGO "commandsoftwarelogo.pcx"
#define NUM_INSTRUMENTS 6
#define BUTTONS_PER_ROW 3
#define MARCHER_WIDTH 20
#define MARCHER_HEIGHT 20

/** STRING CONSTANTS **/
#define STR_NEW_GAME "NEW GAME"
#define STR_OPTIONS "OPTIONS"
#define STR_CONTINUE "LOAD GAME"
#define STR_EXIT "EXIT"
#define MENU_CHOICES 4
#define BEGIN_MENU 200

#define STR_SOUND_EFFECTS "SOUND EFFECTS VOLUME"
#define STR_MUSIC "MUSIC VOLUME"
#define STR_CONTROLS "CONTROLS"
#define STR_RETURN "RETURN"
#define OPTIONS_CHOICES 4
#define BEGIN_OPTIONS_MENU 50

#define DEBUG


/** GLOBAL VARIABLES **/
BITMAP *bm_buttonbar;
BITMAP *bm_field;
BITMAP *buffer;
BITMAP *layer_1;
BITMAP *layer_2;
BITMAP *layer_3;
BITMAP *layer_4;

BITMAP *bm_instruments[NUM_INSTRUMENTS];
BITMAP *bm_marcher[2];
DATAFILE *datafile;
PALLETE my_pallete;
FONT *fnt_outline;
FONT *fnt_inner;

/** FUNCTION PROTOTYPES **/
void moveMarcher(BITMAP *, int, int, int, int, int);
void displayLogo();
void displayTitleScreen();
void displayGameBoard();
void playGame();
void loadMarcher();
void optionsMenu();

/** STRUCTURES **/
typedef struct Marcher {
  int iRow;
  int iCol;
  int iFrame;
  int iMoves[ 256 ];
} Marcher;
 
/**
  * displayLogo - display the Command Software logo
  **/
void displayLogo() {
  BITMAP *bm_logo;
  SAMPLE *wav_logo;

  wav_logo = load_wav("saytrademark.wav");

  bm_logo = load_pcx(PIC_COMMAND_SOFTWARE_LOGO, my_pallete);
  clear(layer_1);
  draw_sprite(layer_1, bm_logo, 0, 0);

  blit(layer_1, screen, 0, 0, (SCREEN_W - 450) / 2, (SCREEN_H - 140) / 2,  450, 140);
  play_sample(wav_logo, 255, 128, 1000, FALSE);
  rest(3000);


  destroy_bitmap(bm_logo);
  clear_keybuf();


}


/**
  * displayTitleScreen - displays the title screen
  */
void displayTitleScreen() {
  MIDI *mid_title_music;
  int iKeyPressed;
  int i;

  Marcher theMarchers[50];

  clear(layer_1);
  clear(layer_4);

  layer_1 = load_pcx(PIC_TITLE_1, my_pallete);
  layer_3 = load_pcx(PIC_TITLE_3, my_pallete);
  layer_4 = load_pcx(PIC_TITLE_4, my_pallete);


  /* collapse all the layers and draw it to the screen */
  draw_sprite(buffer, layer_1, 0, 0);
  draw_sprite(buffer, layer_3, 0, 200);
  draw_sprite(buffer, layer_4, 0, 0);
  blit(buffer, screen, 0, 0, 0, 0, 640, 480);



  mid_title_music = load_midi("title.mid");
  play_midi(mid_title_music, TRUE);


  theMarchers[0].iRow = 0;
  theMarchers[0].iCol = 10;
  theMarchers[0].iFrame = 0;
  theMarchers[0].iMoves[0] = 2;
  theMarchers[0].iMoves[1] = 6;
  theMarchers[0].iMoves[2] = 2;
  theMarchers[0].iMoves[3] = 4;
  theMarchers[0].iMoves[4] = -1;


  while(!keypressed()) {

/*
    i = 0;
    while(theMarchers[0].iMoves[i] != -1) {
      switch(theMarchers[0].iMoves[i]) {
        case 2:
          moveMarcher(screen, theMarchers[0].iFrame, theMarchers[0].iCol * 20, theMarchers[0].iRow * 20, 2, 100);
          theMarchers[0].iRow++;
          break;
        case 4: 
          moveMarcher(screen, theMarchers[0].iFrame, theMarchers[0].iCol * 20, theMarchers[0].iRow * 20, 4, 100);
          theMarchers[0].iCol--;
          break;
        case 6: 
          moveMarcher(screen, theMarchers[0].iFrame, theMarchers[0].iCol * 20, theMarchers[0].iRow * 20, 6, 100);
          theMarchers[0].iCol++;
          break;
        case 8: 
          moveMarcher(screen, theMarchers[0].iFrame, theMarchers[0].iCol * 20, theMarchers[0].iRow * 20, 8, 100);
          theMarchers[0].iRow--;
          break;
        default:

      }
      i++;
    }
*/
  }
  destroy_midi(mid_title_music);


}

/**
  * displayGameBoard - displays the game board
  **/
void displayGameBoard() {
  int i, j;


  bm_buttonbar = load_pcx(PIC_BUTTONBAR, my_pallete);
  bm_field = load_pcx(PIC_FIELD, my_pallete);

  /** load the musical instrument images **/
  bm_instruments[0] = load_pcx("but_trombone.pcx", my_pallete);
  bm_instruments[1] = load_pcx("but_trombone.pcx", my_pallete);
  bm_instruments[2] = load_pcx("but_clarinet.pcx", my_pallete);
  bm_instruments[3] = load_pcx("but_trumpet.pcx", my_pallete);
  bm_instruments[4] = load_pcx("but_tuba.pcx", my_pallete);
  bm_instruments[5] = load_pcx("but_saxophone.pcx", my_pallete);

  clear(layer_1);
  draw_sprite(layer_1, bm_field, 0, 0);
  draw_sprite(layer_1, bm_buttonbar, 416, 0);

  /** draw the instruments **/
  i = 0;
  j = 0;
  /** loop this later **/
  draw_sprite(layer_1, bm_instruments[0], (0 * 64) + 432, (0 * 64) + 300);
  draw_sprite(layer_1, bm_instruments[1], (1 * 64) + 432, (0 * 64) + 300);
  draw_sprite(layer_1, bm_instruments[2], (2 * 64) + 432, (0 * 64) + 300);
  draw_sprite(layer_1, bm_instruments[3], (0 * 64) + 432, (1 * 64) + 300);
  draw_sprite(layer_1, bm_instruments[4], (1 * 64) + 432, (1 * 64) + 300);
  draw_sprite(layer_1, bm_instruments[5], (2 * 64) + 432, (1 * 64) + 300);

  blit(layer_1, screen, 0, 0, 0, 0, 640, 480);



}

/**
  * playGame - lets the user play the game
  **/
void playGame() {
  int iKeyPressed;
  char *strMessage;
  int x, y;

  MIDI *mid_game_music;

  mid_game_music = load_midi("game01.mid");
  play_midi(mid_game_music, TRUE);


  #ifdef DEBUG
    x = 0;
    y = 0;
  #endif

  do {
    iKeyPressed = readkey();
    sprintf(strMessage, "%c", iKeyPressed & 0xFF);

    textout(layer_1, font, strMessage, x, y, makecol(255, 0, 0));

  #ifdef DEBUG
    x += 32;
    if ((iKeyPressed >> 8) == KEY_ENTER) {
      x = 0;
      y += 32;
    }
  #endif

    blit(layer_1, screen, 0, 0, 0, 0, 640, 480);


  } while ((iKeyPressed >> 8) != KEY_ESC);

  clear_keybuf();
  destroy_midi(mid_game_music);

}

/**
  * loadMarcher
  **/
void loadMarcher() {
  bm_marcher[0] = load_pcx("marcher01.pcx", my_pallete);
  bm_marcher[1] = load_pcx("marcher02.pcx", my_pallete);

}

/**
  * moveMarcher
  **/
void moveMarcher(BITMAP *theBitmap, int iFrame, int x, int y, int iDirection, int iSpeed) {
  int i;



  for(i = 0; i < MARCHER_WIDTH; i++) {
    draw_sprite(theBitmap, bm_marcher[iFrame], x, y);

    rest(iSpeed);


    switch(iDirection) {
      case 2:
        y++;
        break;
      case 4:
        x--;
        break;
      case 6:
        x++;
        break;
      case 8:
        y--;
        break;
      default:

    }

    if (iFrame == 0) {
      iFrame = 1;
    } else {
      iFrame = 0;
    }
  }
}

/**
  * mainMenu - new game, continue, or exit
  **/
void mainMenu() {
  int iKeyPressed;
  int iFontBorderColor, iFontInnerColor, iFontHightlightColor;
  int x, y;
  int iSpacing;
  int iMenuChoice;
  BITMAP *bm_menu_cursor;
  SAMPLE *wav_select;
  SAMPLE *wav_enter;

  wav_select = load_wav("menu_select.wav");
  wav_enter = load_wav("menu_enter.wav");


  x = 175;
  y = BEGIN_MENU;
  iSpacing = 40;
  iFontBorderColor = makecol(0, 0, 0);
  iFontInnerColor = makecol(218, 0, 0);
  iMenuChoice = 0;

  layer_1 = load_pcx(PIC_TITLE_1, my_pallete);
  layer_3 = load_pcx(PIC_TITLE_4, my_pallete);
  layer_4 = load_pcx(PIC_BLANK, my_pallete);

  bm_menu_cursor = load_pcx(PIC_MENU_CURSOR, my_pallete);

  textout(layer_4, fnt_outline, STR_NEW_GAME, x, y, iFontBorderColor);
  textout(layer_4, fnt_outline, STR_OPTIONS, x, y += iSpacing, iFontBorderColor);
  textout(layer_4, fnt_outline, STR_CONTINUE, x, y += iSpacing, iFontBorderColor);
  textout(layer_4, fnt_outline, STR_EXIT, x, y += iSpacing, iFontBorderColor);

  y = BEGIN_MENU;
  textout(layer_4, fnt_inner, STR_NEW_GAME, x, y, iFontInnerColor);
  textout(layer_4, fnt_inner, STR_OPTIONS, x, y += iSpacing, iFontInnerColor);
  textout(layer_4, fnt_inner, STR_CONTINUE, x, y += iSpacing, iFontInnerColor);
  textout(layer_4, fnt_inner, STR_EXIT, x, y += iSpacing, iFontInnerColor);


  y = BEGIN_MENU;
  play_sample(wav_select, 255, 128, 1000, FALSE);


  do {
    draw_sprite(buffer, layer_1, 0, 0);
    draw_sprite(buffer, layer_3, 0, 0);
    draw_sprite(buffer, layer_4, 0, 0);
    draw_sprite(buffer, bm_menu_cursor, 64, y + (iMenuChoice * iSpacing));
    blit(buffer, screen, 0, 0, 0, 0, 640, 480);


    iKeyPressed = readkey();


    switch(iKeyPressed >> 8) {
      case KEY_DOWN:
        iMenuChoice++;
        if (iMenuChoice >= MENU_CHOICES) {
          iMenuChoice = 0;
        }
       play_sample(wav_select, 255, 128, 1000, FALSE);

        break;
      case KEY_UP:
        iMenuChoice--;
        if (iMenuChoice < 0) {
          iMenuChoice = MENU_CHOICES - 1;
        }
       play_sample(wav_select, 255, 128, 1000, FALSE);

        break;
      default:

    }

  } while ((iKeyPressed >> 8) != KEY_ENTER);

  switch(iMenuChoice) {
    case 0:
      play_sample(wav_enter, 255, 128, 1000, FALSE);
      displayGameBoard();
      playGame();
      break;
    case 1:
      play_sample(wav_select, 255, 128, 1000, FALSE);
      optionsMenu();
      break;
    case 4:
      /* do nothing an quit quietly */
      break;
    default:

  }

}


/**
  * optionsMenu
  **/
void optionsMenu() {
  int iKeyPressed;
  int iFontBorderColor, iFontInnerColor, iFontHightlightColor;
  int x, y;
  int iSpacing;
  int iMenuChoice;
  BITMAP *bm_menu_cursor;
  SAMPLE *wav_select;

  wav_select = load_wav("menu_select.wav");

  x = 175;
  y = BEGIN_OPTIONS_MENU;
  iSpacing = 40;
  iFontBorderColor = makecol(0, 0, 0);
  iFontInnerColor = makecol(218, 0, 0);
  iMenuChoice = 0;

  layer_1 = load_pcx(PIC_TITLE_1, my_pallete);
  layer_4 = load_pcx(PIC_BLANK, my_pallete);

  bm_menu_cursor = load_pcx(PIC_MENU_CURSOR, my_pallete);

  textout(layer_4, fnt_outline, "OPTIONS", x, y, iFontBorderColor);
  textout(layer_4, fnt_inner, "OPTIONS", x, y, iFontInnerColor);

  textout(layer_4, fnt_outline, STR_SOUND_EFFECTS, x, y += (2 * iSpacing), iFontBorderColor);
  textout(layer_4, fnt_outline, STR_MUSIC, x, y += iSpacing, iFontBorderColor);
  textout(layer_4, fnt_outline, STR_CONTROLS, x, y += iSpacing, iFontBorderColor);
  textout(layer_4, fnt_outline, STR_RETURN, x, y += iSpacing, iFontBorderColor);

  y = BEGIN_OPTIONS_MENU;
  textout(layer_4, fnt_inner, STR_SOUND_EFFECTS, x, y += (2 * iSpacing), iFontInnerColor);
  textout(layer_4, fnt_inner, STR_MUSIC, x, y += iSpacing, iFontInnerColor);
  textout(layer_4, fnt_inner, STR_CONTROLS, x, y += iSpacing, iFontInnerColor);
  textout(layer_4, fnt_inner, STR_RETURN, x, y += iSpacing, iFontInnerColor);


  y = BEGIN_OPTIONS_MENU + (2 * iSpacing);


  do {
    draw_sprite(buffer, layer_1, 0, 0);
    draw_sprite(buffer, layer_4, 0, 0);
    draw_sprite(buffer, bm_menu_cursor, 64, y + (iMenuChoice * iSpacing));
    blit(buffer, screen, 0, 0, 0, 0, 640, 480);


    iKeyPressed = readkey();


    switch(iKeyPressed >> 8) {
      case KEY_DOWN:
        iMenuChoice++;
        if (iMenuChoice >= OPTIONS_CHOICES) {
          iMenuChoice = 0;
        }
       play_sample(wav_select, 255, 128, 1000, FALSE);

        break;
      case KEY_UP:
        iMenuChoice--;
        if (iMenuChoice < 0) {
          iMenuChoice = OPTIONS_CHOICES - 1;
        }
       play_sample(wav_select, 255, 128, 1000, FALSE);

        break;
      default:

    }

  } while ((iKeyPressed >> 8) != KEY_ENTER);

  switch(iMenuChoice) {
    case 3:
      /* return to main menu */
      mainMenu();
      break;
    default:
      mainMenu();

  }


}


/**
  * setupAllegro - sets up the Allegro library
  **/
void setupAllegro() {
  char buf[256];
  allegro_init();
  install_keyboard();
  install_timer();

  set_color_depth(16);
  set_gfx_mode(GFX_AUTODETECT, 640, 480, 0, 0);

  if (install_sound(DIGI_AUTODETECT, MIDI_AUTODETECT, "band") != 0) {
      printf("Error initializing sound system\n%s\n", allegro_error);
  }

  /** from Allegro examples **/
  replace_filename(buf, "whatIsThis", "band.dat", sizeof(buf));
  datafile = load_datafile(buf);
  if (!datafile) {
     allegro_exit();
     printf("Error loading %s!\n\n", buf);
     exit(1);
  }


  font = datafile[BAND_OUTLINE].dat;
  fnt_outline = datafile[BAND_OUTLINE].dat;
  fnt_inner = datafile[BAND_INNER].dat;

  text_mode(-1);

  buffer = create_bitmap(SCREEN_W, SCREEN_H);
  layer_1 = create_bitmap(SCREEN_W, SCREEN_H);
  layer_2 = create_bitmap(SCREEN_W, SCREEN_H);
  layer_3 = create_bitmap(SCREEN_W, SCREEN_H);
  layer_4 = create_bitmap(SCREEN_W, SCREEN_H);

  set_volume(255, 255);

}

/**
  * main
  */
int main(void) {

  char *strBandName;
  setupAllegro();
  loadMarcher();
  displayLogo();
  displayTitleScreen();
  mainMenu();

  destroy_bitmap(bm_field);
  destroy_bitmap(bm_buttonbar);
  destroy_bitmap(layer_1);
  unload_datafile(datafile);
  printf("Thanks for playing THE BAND GAME!!\n");


  return 0;
}
