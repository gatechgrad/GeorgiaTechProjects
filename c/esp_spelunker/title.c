#include <allegro.h>
#define MAX_CHOICES 4
#define MAX_OPTION_CHOICES 5

#define MAX_SFX_VOLUME 250
#define MAX_MUSIC_VOLUME 250

#define SCROLL_HEIGHT 700
#define SCROLL_WINDOW 64

/** FUNCTION PROTOTYPES **/
extern void runGame();
void repaint_title();
void repaint_options();
void runTitle();
void options();


int iChoice;
int iOptionsChoice;
int iSFX;
int iMusic;
int iCreditScroll;
extern BITMAP *title_image;

BITMAP *bmp_credits;


void credit_timer() {
   iCreditScroll++;

  if (iCreditScroll + SCROLL_WINDOW > SCROLL_HEIGHT) {
    iCreditScroll = 0;
  }

}

END_OF_FUNCTION(credit_timer);



void repaint_title() {
  BITMAP *bmp_buffer;
  int x;
  int y;
  int y_space;

  int iNonSelected, iSelected;

  x = 100;
  y = 200;
  y_space = 20;

  iNonSelected = makecol(128, 128, 128);
  iSelected = makecol(224, 224, 224);


  bmp_buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(bmp_buffer);

  textout(bmp_buffer, font, "NEW GAME", x, y + (y_space * 0), iNonSelected);
  textout(bmp_buffer, font, "LOAD GAME", x, y + (y_space * 1), iNonSelected);
  textout(bmp_buffer, font, "OPTIONS", x, y + (y_space * 2), iNonSelected);
  textout(bmp_buffer, font, "EXIT", x, y + (y_space * 3), iNonSelected);


  y = 200;
  switch(iChoice) {
    case 0:
      textout(bmp_buffer, font, "NEW GAME", x, y + (y_space * 0), iSelected);
      break;
    case 1:
      textout(bmp_buffer, font, "LOAD GAME", x, y + (y_space * 1), iSelected);
      break;
    case 2:
      textout(bmp_buffer, font, "OPTIONS", x, y + (y_space * 2), iSelected);
      break;
    case 3:
      textout(bmp_buffer, font, "EXIT", x, y + (y_space * 3), iSelected);
      break;
    default:
      break;
  }


  draw_sprite(bmp_buffer, title_image, 50, 20);

  //scroll credits
  masked_blit(bmp_credits, bmp_buffer, 0, iCreditScroll, 0, 320, SCREEN_W, SCROLL_WINDOW);


  //paste image to screen
  blit(bmp_buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

  destroy_bitmap(bmp_buffer);

}

void createCredits() {
  int y, y_space;
  iCreditScroll = 0;

  y = SCROLL_WINDOW;
  y_space = 20;

  bmp_credits = create_bitmap(SCREEN_W, SCROLL_HEIGHT);
  clear_to_color(bmp_credits, makecol(255, 0, 255));


  textout(bmp_credits, font, "SPELUNKER CREDITS", 100, y += y_space, makecol(255, 255, 255));
  y += y_space;

  textout(bmp_credits, font, "Game Design", 100, y += y_space, makecol(255, 255, 255));
  textout(bmp_credits, font, "ESP Spelunker Team:", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Jeff Stewart", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Levi D. Smith", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Derek Hall", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Alan Stone", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Angel Marrero", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Alex Drake", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Jeffrey Crenshaw", 125, y += y_space, makecol(192, 192, 192));

  y += y_space;
  textout(bmp_credits, font, "Programming", 100, y += y_space, makecol(255, 255, 255));
  textout(bmp_credits, font, "Levi D. Smith", 125, y += y_space, makecol(192, 192, 192));

  y += y_space;
  textout(bmp_credits, font, "Artwork", 100, y += y_space, makecol(255, 255, 255));
  textout(bmp_credits, font, "Alex Drake", 125, y += y_space, makecol(192, 192, 192));
  textout(bmp_credits, font, "Levi D. Smith", 125, y += y_space, makecol(192, 192, 192));

  y += y_space;
  textout(bmp_credits, font, "Music", 100, y += y_space, makecol(255, 255, 255));
  textout(bmp_credits, font, "Public Domain", 125, y += y_space, makecol(192, 192, 192));

  y += y_space;
  textout(bmp_credits, font, "Sound Effects and Voice Acting", 100, y += y_space, makecol(255, 255, 255));
  textout(bmp_credits, font, "Levi D. Smith", 125, y += y_space, makecol(192, 192, 192));

  y += y_space;
  textout(bmp_credits, font, "Game Physics", 100, y += y_space, makecol(255, 255, 255));
  textout(bmp_credits, font, "Levi D. Smith", 125, y += y_space, makecol(192, 192, 192));



}

void repaint_options() {
  BITMAP *bmp_buffer;
  int x;
  int y;
  int y_space;

  int iNonSelected, iSelected;

  x = 100;
  y = 200;
  y_space = 20;

  iNonSelected = makecol(128, 128, 128);
  iSelected = makecol(224, 224, 224);


  bmp_buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(bmp_buffer);

  textout(bmp_buffer, font, "MUSIC VOLUME", x, y, iNonSelected);
  draw_slider(bmp_buffer, x + 100, y, iMusic, MAX_SFX_VOLUME);

  y += y_space;
  textout(bmp_buffer, font, "SOUND EFFECTS VOLUME", x, y, iNonSelected);
  draw_slider(bmp_buffer, x + 175, y, iSFX, MAX_SFX_VOLUME);

  y += y_space;
  textout(bmp_buffer, font, "GAMMA", x, y, iNonSelected);

  y += y_space;
  textout(bmp_buffer, font, "CONTROLS", x, y, iNonSelected);

  y += y_space;
  textout(bmp_buffer, font, "BACK TO MAIN", x, y, iNonSelected);


  y = 200;
  switch(iOptionsChoice) {
    case 0:
      textout(bmp_buffer, font, "MUSIC VOLUME", x, y + (y_space * 0), iSelected);
      break;
    case 1:
      textout(bmp_buffer, font, "SOUND EFFECTS VOLUME", x, y + (y_space * 1), iSelected);
      break;
    case 2:
      textout(bmp_buffer, font, "GAMMA", x, y + (y_space * 2), iSelected);
      break;
    case 3:
      textout(bmp_buffer, font, "CONTROLS", x, y + (y_space * 3), iSelected);
      break;
    case 4:
      textout(bmp_buffer, font, "BACK TO MAIN", x, y + (y_space * 4), iSelected);
      break;
    default:
      break;
  }


  draw_sprite(bmp_buffer, title_image, 50, 20);

  //paste image to screen
  blit(bmp_buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

  destroy_bitmap(bmp_buffer);

}



void runTitle() {
  int iKeepLooping;
  int iShowOptions;
  int iKey;
  int iRunGame;

  createCredits();


  iKeepLooping = TRUE;

  iChoice = 0;
  iShowOptions = FALSE;
  iRunGame = FALSE;

  clear_keybuf();

  install_int(credit_timer, 33);


  while(iKeepLooping == TRUE) {
    repaint_title();


    if (keypressed()) {
      iKey = readkey() >> 8;

      if (iKey == KEY_ENTER) {
        switch(iChoice) {
          case 0:
            iKeepLooping = FALSE;
            iRunGame = TRUE;
            clear_keybuf();
            break;
          case 2:
            iKeepLooping = FALSE;
            iShowOptions = TRUE;
            clear_keybuf();
            break;
          case 3:
            iKeepLooping = FALSE;
            break;
          default:
            break;
        }
      }

      if (iKey == KEY_UP) {
        iChoice--;

        if (iChoice < 0) {
          iChoice = (MAX_CHOICES - 1);
        }
      }

      if (iKey == KEY_DOWN) {
        iChoice++;

        if (iChoice > (MAX_CHOICES - 1)) {
          iChoice = 0;
        }
      }

    }

  }

  remove_int(credit_timer);


  if (iShowOptions == TRUE) {
    options();
  } else if (iRunGame == TRUE) {
    runGame();

  }

}

/**
 * displays the options screen
 */
void options() {
  int iKeepLooping;
  int iKey;

  iSFX = 150;
  iMusic = 100;

  iKeepLooping = TRUE;

  iOptionsChoice = 0;

  clear_keybuf();

  while(iKeepLooping == TRUE) {
    repaint_options();


    if (keypressed()) {
      iKey = readkey() >> 8;

      if (iKey == KEY_ENTER) {
        switch(iOptionsChoice) {
          case 4:
            iKeepLooping = FALSE;
            break;
          default:
            break;
        }
      }

      if (iKey == KEY_UP) {
        iOptionsChoice--;

        if (iOptionsChoice < 0) {
          iOptionsChoice = (MAX_OPTION_CHOICES - 1);
        }
      }

      if (iKey == KEY_DOWN) {
        iOptionsChoice++;

        if (iOptionsChoice > (MAX_OPTION_CHOICES - 1)) {
          iOptionsChoice = 0;
        }
      }

      if (iKey == KEY_LEFT) {
        if (iOptionsChoice == 0) {
          iMusic -= 5;

          if (iMusic < 0) {
            iMusic = 0;
          }
        } else if (iOptionsChoice == 1) {
          iSFX -= 5;

          if (iSFX < 0) {
            iSFX = 0;
          }
        }
      }

      if (iKey == KEY_RIGHT) {
        if (iOptionsChoice == 0) {
          iMusic += 5;

          if (iMusic > MAX_MUSIC_VOLUME) {
            iMusic = MAX_MUSIC_VOLUME;
          }
        } else if (iOptionsChoice == 1) {
          iSFX += 5;

          if (iSFX > MAX_SFX_VOLUME) {
            iSFX = MAX_SFX_VOLUME;
          }

        }
      }


    }

  }
  runTitle();
}

void draw_slider(BITMAP *bmp, int x, int y, int iValue, int iMaxValue) {
    rectfill(bmp, x, y, x + iValue, y + 10, makecol(128, 128, 128));
    rect(bmp, x, y, x + iMaxValue, y + 10, makecol(96, 96, 96));
}
