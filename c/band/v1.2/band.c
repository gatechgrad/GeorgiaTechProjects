#include <stdio.h>
#include <ctype.h>
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
#define LEFT_MARGIN 175

/** STRING CONSTANTS **/
#define STR_NEW_GAME "New game"
#define STR_OPTIONS "Options"
#define STR_CONTINUE "Load game"
#define STR_EXIT "Exit"
#define MENU_CHOICES 4
#define BEGIN_MENU 200

#define STR_SOUND_EFFECTS "SOUND EFFECTS VOLUME"
#define STR_MUSIC "MUSIC VOLUME"
#define STR_CONTROLS "CONTROLS"
#define STR_RETURN "RETURN"
#define OPTIONS_CHOICES 4
#define BEGIN_OPTIONS_MENU 50

#define DEBUG

/** STRUCTS **/
typedef struct {
  char *strSchoolName;
  char *strSchoolMascot;
  int iBrass;
  int iLowBrass;
  int iWoodWind;
  int iPrecussion;
  int iFunds;

  int iFontInnerColor;
  int iFontOuterColor;
} Player;

typedef struct Marcher {
  int iRow;
  int iCol;
  int iFrame;
  int iMoves[ 256 ];
} Marcher;



/** GLOBAL VARIABLES **/
BITMAP *bm_buttonbar;
BITMAP *bm_field;
BITMAP *bm_but_bkg;
BITMAP *bm_but_highlight;

BITMAP *buffer;
BITMAP *layer_1;
BITMAP *layer_2;
BITMAP *layer_3;
BITMAP *layer_4;

BITMAP *bm_instruments[NUM_INSTRUMENTS];
BITMAP *bm_menu_buttons[8];
BITMAP *bm_marcher[2];
DATAFILE *datafile;
PALLETE my_pallete;
FONT *fnt_outline;
FONT *fnt_inner;
Player thePlayer;



/** FUNCTION PROTOTYPES **/
void moveMarcher(BITMAP *, int, int, int, int, int);
void displayLogo();
void displayTitleScreen();
void displayGameBoard(int);
void playGame();
void loadMarcher();
void optionsMenu();
void getPlayerData();
char *getString(BITMAP *bm_but_bkg, int x, int y);
void drawLayeredFont(BITMAP *bm_but_bkg, FONT *font_1, FONT *font_2, char *strText, int x, int y, int iColor_1, int iColor_2, int iXOffset, int iYOffset);
void getBitmaps();

 
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

  readkey();

  /* destroy_midi(mid_title_music); */


}

/**
  * displayGameBoard - displays the game board
  **/
void displayGameBoard(int iHighlight) {
  int i, j;
  char *strTemp;

#define BUTTON_SPACING 40


  strTemp = malloc(1024);





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


  draw_sprite(layer_1, bm_but_bkg, (0 * BUTTON_SPACING) + 452, (0 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_but_bkg, (1 * BUTTON_SPACING) + 452, (0 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_but_bkg, (0 * BUTTON_SPACING) + 452, (1 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_but_bkg, (1 * BUTTON_SPACING) + 452, (1 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_but_bkg, (0 * BUTTON_SPACING) + 452, (2 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_but_bkg, (1 * BUTTON_SPACING) + 452, (2 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_but_bkg, (0 * BUTTON_SPACING) + 452, (3 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_but_bkg, (1 * BUTTON_SPACING) + 452, (3 * BUTTON_SPACING) + 100);

  /** the highlighted one **/
  draw_sprite(layer_1, bm_but_highlight, ((iHighlight % 2) * BUTTON_SPACING) + 452, ((iHighlight / 2) * BUTTON_SPACING) + 100);

  draw_sprite(layer_1, bm_menu_buttons[0], (0 * BUTTON_SPACING) + 452, (0 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_menu_buttons[1], (1 * BUTTON_SPACING) + 452, (0 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_menu_buttons[2], (0 * BUTTON_SPACING) + 452, (1 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_menu_buttons[3], (1 * BUTTON_SPACING) + 452, (1 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_menu_buttons[4], (0 * BUTTON_SPACING) + 452, (2 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_menu_buttons[5], (1 * BUTTON_SPACING) + 452, (2 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_menu_buttons[6], (0 * BUTTON_SPACING) + 452, (3 * BUTTON_SPACING) + 100);
  draw_sprite(layer_1, bm_menu_buttons[7], (1 * BUTTON_SPACING) + 452, (3 * BUTTON_SPACING) + 100);

  sprintf(strTemp, "Members: %d", (thePlayer.iBrass + thePlayer.iLowBrass + thePlayer.iWoodWind + thePlayer.iPrecussion));
  drawLayeredFont(layer_1, fnt_inner, fnt_outline, strTemp , 432, 20, thePlayer.iFontInnerColor, thePlayer.iFontOuterColor, 0, 0);

  sprintf(strTemp, "Funds: $%d", thePlayer.iFunds);
  drawLayeredFont(layer_1, fnt_inner, fnt_outline, strTemp , 432, 50, thePlayer.iFontInnerColor, thePlayer.iFontOuterColor, 0, 0);

  blit(layer_1, screen, 0, 0, 0, 0, 640, 480);
}

/**
  * playGame - lets the user play the game
  **/
void playGame() {
  int iKeyPressed;
  int iChoice;
  int iKeepLooping;
  char *strMessage;
  int x, y;

  MIDI *mid_game_music;

  mid_game_music = load_midi("game01.mid");
  play_midi(mid_game_music, TRUE);


  layer_4 = load_pcx(PIC_BLANK, my_pallete);

  iChoice = 0;
  displayGameBoard(iChoice);

#define MENU_BUTTON_ROWS 4
#define MENU_BUTTON_COLS 2
#define MENU_BUTTONS 8

  iKeepLooping = 1;
  do {
    iKeyPressed = readkey();

    iKeyPressed = iKeyPressed >> 8;
    switch(iKeyPressed) {
      case KEY_LEFT:
        if ((iChoice - 1) >= 0) {
          iChoice--;
          displayGameBoard(iChoice);
        }
        break;
      case KEY_RIGHT:
        if ((iChoice + 1) < MENU_BUTTONS) {
          iChoice++;
          displayGameBoard(iChoice);
        }
        break;
      case KEY_UP:
        if ((iChoice - MENU_BUTTON_COLS) >= 0) {
          iChoice -= MENU_BUTTON_COLS;
          displayGameBoard(iChoice);
        }
        break;
      case KEY_DOWN:
        if ((iChoice + MENU_BUTTON_COLS) < MENU_BUTTONS) {
          iChoice += MENU_BUTTON_COLS;
          displayGameBoard(iChoice);
        }
        break;

      case KEY_ENTER:
        switch(iChoice) {
          case 7:
            iKeepLooping = 0;
            break;
        }
        break;
      default:
    }



  } while (iKeepLooping);

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
      getPlayerData();
      getBitmaps();
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
  * getBitmaps - loads the bitmaps
  **/
void getBitmaps() {
  bm_buttonbar = load_pcx(PIC_BUTTONBAR, my_pallete);
  bm_field = load_pcx(PIC_FIELD, my_pallete);

  /** load the musical instrument images **/
  bm_instruments[0] = load_pcx("but_trombone.pcx", my_pallete);
  bm_instruments[1] = load_pcx("but_trombone.pcx", my_pallete);
  bm_instruments[2] = load_pcx("but_clarinet.pcx", my_pallete);
  bm_instruments[3] = load_pcx("but_trumpet.pcx", my_pallete);
  bm_instruments[4] = load_pcx("but_tuba.pcx", my_pallete);
  bm_instruments[5] = load_pcx("but_saxophone.pcx", my_pallete);

  bm_menu_buttons[0] = load_pcx("but_formations.pcx", my_pallete);
  bm_menu_buttons[1] = load_pcx("but_store.pcx", my_pallete);
  bm_menu_buttons[2] = load_pcx("but_fundraiser.pcx", my_pallete);
  bm_menu_buttons[3] = load_pcx("but_schedule.pcx", my_pallete);
  bm_menu_buttons[4] = load_pcx("but_stats.pcx", my_pallete);
  bm_menu_buttons[5] = load_pcx("but_practice.pcx", my_pallete);
  bm_menu_buttons[6] = load_pcx("but_save.pcx", my_pallete);
  bm_menu_buttons[7] = load_pcx("but_exit.pcx", my_pallete);

  bm_but_bkg = load_pcx("but_highlight.pcx", my_pallete);
  bm_but_highlight = load_pcx("but_bkg.pcx", my_pallete);

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

  x = LEFT_MARGIN;
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
  * getPlayerData
  **/
void getPlayerData() {
  int iFontBorderColor;
  int iFontInnerColor;
  SAMPLE *wav_enter;
  BITMAP *bm_lowered;

  wav_enter = load_wav("menu_enter.wav");
  bm_lowered = load_pcx("lowered.pcx", my_pallete);

  iFontBorderColor = makecol(0, 0, 0);
  iFontInnerColor = makecol(218, 0, 0);

  thePlayer.strSchoolName = "";
  thePlayer.strSchoolMascot = "";

  layer_1 = load_pcx(PIC_TITLE_1, my_pallete);
  layer_3 = load_pcx(PIC_BLANK, my_pallete);
  layer_4 = load_pcx(PIC_BLANK, my_pallete);

  draw_sprite(layer_3, layer_1, 0, 0);

  draw_sprite(layer_3, bm_lowered, 50, 100);
  drawLayeredFont(layer_3, fnt_outline, fnt_inner, "Enter your school name", 25, 50, iFontBorderColor, iFontInnerColor, 0, 0);
  thePlayer.strSchoolName = getString(layer_3, 50, 100);
  drawLayeredFont(layer_3, fnt_outline, fnt_inner, thePlayer.strSchoolName, 50, 100, iFontBorderColor, iFontInnerColor, 0, 0);


  draw_sprite(layer_3, bm_lowered, 50, 250);
  drawLayeredFont(layer_3, fnt_outline, fnt_inner, "Enter your school mascot", 25, 200, iFontBorderColor, iFontInnerColor, 0, 0);
  thePlayer.strSchoolMascot = getString(layer_3, 50, 250);

  thePlayer.iBrass = 5;
  thePlayer.iLowBrass = 5;
  thePlayer.iWoodWind = 5;
  thePlayer.iPrecussion = 5;
  thePlayer.iFunds = 500;

  thePlayer.iFontInnerColor = makecol(218, 0, 0);
  thePlayer.iFontOuterColor = makecol(0, 0, 0);

  play_sample(wav_enter, 255, 128, 1000, FALSE);

}

/**
  * getString
  **/
char *getString(BITMAP *bm_but_bkg, int x, int y) {
  int iFontBorderColor;
  int iFontInnerColor;
  int iKeyPressed;
  char *strTemp;
  int i;


  iFontBorderColor = makecol(0, 0, 0);
  iFontInnerColor = makecol(218, 0, 0);
  strTemp = malloc(1024);
  *strTemp = '\0';

  clear(buffer);
  i = 0;
  do {

//    destroy_bitmap(layer_4);

    layer_4 = load_pcx(PIC_BLANK, my_pallete);
    textout(layer_4, fnt_outline, strTemp, x, y, iFontBorderColor);
    textout(layer_4, fnt_inner, strTemp, x, y, iFontInnerColor);

    draw_sprite(buffer, bm_but_bkg, 0, 0);
    draw_sprite(buffer, layer_4, 0, 0);
    blit(buffer, screen, 0, 0, 0, 0, 640, 480);

    iKeyPressed = readkey();
    if ((isalnum(iKeyPressed & 0xFF)) || (isspace(iKeyPressed & 0xFF))) {
      strTemp[i] = (iKeyPressed & 0xFF);
      strTemp[i + 1] = '\0';
      i++;
    } else if (((iKeyPressed >> 8) == KEY_BACKSPACE) && (i > 0)) {
      strTemp[i - 1] = '\0';
      i--;
    }
    

  } while((iKeyPressed >> 8) != KEY_ENTER);

  return((char *) strdup(strTemp));


}

/**
  * drawLayeredFont
  **/
void drawLayeredFont(BITMAP *bm_but_bkg, FONT *font_1, FONT *font_2, char *strText, int x, int y, int iColor_1, int iColor_2, int iXOffset, int iYOffset) {
  textout(bm_but_bkg, font_1, strText, x, y, iColor_1);
  textout(bm_but_bkg, font_2, strText, x + iXOffset, y + iYOffset, iColor_2);

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

  //do_dialog( get_school_dialog, -1);

  loadMarcher();
  displayLogo();
  displayTitleScreen();
  mainMenu();

  destroy_bitmap(layer_1);
  destroy_bitmap(layer_2);
  destroy_bitmap(layer_3);
  destroy_bitmap(layer_4);
  destroy_bitmap(buffer);
  unload_datafile(datafile);
  printf("Thanks for playing THE BAND GAME!!\n");

  return 0;
}
