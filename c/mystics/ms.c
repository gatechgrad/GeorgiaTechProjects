#include <stdio.h>
#include <stdlib.h>
#include <allegro.h>

#define TITLE_PIC "title.pcx"
#define MAX_TEAM_MEMBERS 5



/** ENUMS **/
enum job { Fighter = 0, Mage = 1, Knight = 2, Pirate = 3, Alchemist = 4 };
typedef enum job job;


/** STRUCTURES **/
typedef struct actor {
  char *strName;

  int iHitPoints;
  int iMagicPoints;
  int iAttackPoints;

  int iWait;
  job jobType;

} actor;



/** GLOBAL VARIABLES **/
PALLETE my_pallete;
BITMAP *buffer;

BITMAP *bm_fighter[1];
BITMAP *bm_knight[1];
BITMAP *bm_mage[1];
BITMAP *bm_pirate[1];
BITMAP *bm_alchemist[1];
BITMAP *bm_background[1];
BITMAP *bm_enemy[3][1];

actor party[MAX_TEAM_MEMBERS];

/** FUNCTION PROTOTYPES **/
void displayTitleScreen();
void setupAllegro();
void shutdown();
void draw_team(BITMAP *);
void draw_actor(BITMAP *, actor, int, int);
void shadow_print(BITMAP *, char *, int, int);




/**
  * displayTitleScreen
  **/
void displayTitleScreen() {
  BITMAP *bm_sword;
  BITMAP *bm_title;
  int y;

  bm_sword = load_pcx("sword.pcx", my_pallete);
  bm_title = load_pcx("title.pcx", my_pallete);


  y = 20;


    clear(buffer);
    draw_sprite(buffer, bm_title, (SCREEN_W - 362) / 2, 100);
    draw_sprite(buffer, bm_sword, (SCREEN_W - 76) / 2, 100);
    blit(buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

  readkey();

  destroy_bitmap(bm_sword);
  destroy_bitmap(bm_title);
}


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
  * battle
  **/
void battle() {
  int iKeyPressed;
  int iCurrentActor;
  char *strTemp;

  strTemp = malloc(1000);

  bm_fighter[0] = load_pcx("fighter.pcx", my_pallete);
  bm_knight[0] = load_pcx("knight.pcx", my_pallete);
  bm_mage[0] = load_pcx("mage.pcx", my_pallete);
  bm_pirate[0] = load_pcx("pirate.pcx", my_pallete);
  bm_alchemist[0] = load_pcx("alchemist.pcx", my_pallete);

  bm_enemy[0][0] = load_pcx("enemy01a.pcx", my_pallete);
  bm_enemy[1][0] = load_pcx("enemy02a.pcx", my_pallete);
  bm_enemy[2][0] = load_pcx("enemy03a.pcx", my_pallete);

  bm_background[0] = load_pcx("bkg01.pcx", my_pallete);


  party[0].strName = "Kajin";
  party[0].iHitPoints = 200;
  party[0].iMagicPoints = 200;
  party[0].iAttackPoints = 200;
  party[0].jobType = 0;

  party[1].strName = "Ni";
  party[1].iHitPoints = 200;
  party[1].iMagicPoints = 200;
  party[1].iAttackPoints = 200;
  party[1].jobType = 1;

  party[2].strName = "San";
  party[2].iHitPoints = 200;
  party[2].iMagicPoints = 200;
  party[2].iAttackPoints = 200;
  party[2].jobType = 2;

  party[3].strName = "Shi";
  party[3].iHitPoints = 200;
  party[3].iMagicPoints = 200;
  party[3].iAttackPoints = 200;
  party[3].jobType = 3;

  party[4].strName = "Go";
  party[4].iHitPoints = 200;
  party[4].iMagicPoints = 200;
  party[4].iAttackPoints = 200;
  party[4].jobType = 4;


  printf("battle begins");
  iCurrentActor = -1;


  /** game loop **/
  while(!keypressed()) {
    clear(buffer);
    blit(bm_background[0], buffer, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

    draw_team(buffer);


    draw_sprite(buffer, bm_enemy[0][0], 50, 32);
    draw_sprite(buffer, bm_enemy[1][0], 50, 64);
    draw_sprite(buffer, bm_enemy[2][0], 50, 96);

    sprintf(strTemp, "Current Actor: %d", iCurrentActor);
    shadow_print(buffer, strTemp, 0, 20);

    blit(buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

    if (keypressed()) {
      iKeyPressed = readkey();

      iKeyPressed = iKeyPressed >> 8;
      switch(iKeyPressed) {
        case KEY_LEFT:
          break;
        case KEY_RIGHT:
          break;
        case KEY_UP:
          break;
        case KEY_DOWN:
          break;
        case KEY_ENTER:
          if (iCurrentActor > -1) {
            party[iCurrentActor].iWait = 500;
            iCurrentActor = -1;
          }
          break;
        default:
          exit(0);
      }
    }

    progress_time();

    if (iCurrentActor < 0) {
      /* still waiting for an actor to become available */
      iCurrentActor = get_next_actor();
    }

  }
}



/**
  * draw_team - draws the team on the buffer
  **/
void draw_team(BITMAP *buffer) {
  int i;

  int x;
  int y;


  x = 400;  /** loop invariant **/
  y = 20;


  for(i = 0; i < MAX_TEAM_MEMBERS; i++) {


    if (party[i].iHitPoints > -1) {
      draw_actor(buffer, party[i], x, y += 64);


    }
  }


}

/**
  * draw_actor - draws one of the team members to the bitmap
  **/
void draw_actor(BITMAP *bm_drawing_area, actor actor_to_draw, int x, int y) {
  int iMagnitude;
  char *strTemp;

  strTemp = malloc(1000);



      shadow_print(bm_drawing_area, actor_to_draw.strName, x + 48, y);

      sprintf(strTemp, "HP %d/%d", actor_to_draw.iHitPoints, 200);
      shadow_print(bm_drawing_area, strTemp, x + 48, y + 8);

      sprintf(strTemp, "MP %d/%d", actor_to_draw.iMagicPoints, 50);
      shadow_print(bm_drawing_area, strTemp, x + 48, y + 16);

      if (actor_to_draw.iWait > 0) {

        iMagnitude = actor_to_draw.iWait * 100 / 500;
        rectfill(bm_drawing_area, x + 48, y + 24, x + 48 + iMagnitude, y + 32, makecol(0, 0, 128)); 
      } else {
        sprintf(strTemp, "Ready", actor_to_draw.iWait);
        shadow_print(bm_drawing_area, strTemp, x + 48, y + 24);
      }


      switch(actor_to_draw.jobType) {
        case 0:
          draw_sprite(bm_drawing_area, bm_fighter[0], x, y);
          break;
        case 1:
          draw_sprite(bm_drawing_area, bm_knight[0], x, y);
          break;
        case 2:
          draw_sprite(bm_drawing_area, bm_mage[0], x, y);
          break;
        case 3:
          draw_sprite(bm_drawing_area, bm_pirate[0], x, y);
          break;
        case 4:
          draw_sprite(bm_drawing_area, bm_alchemist[0], x, y);
          break;


        default:


      }

}


/**
  * progress_time
  **/
int progress_time() {
  int i;


  for (i = 0; i < MAX_TEAM_MEMBERS; i++) {
    if (party[i].iHitPoints > 0) {
      party[i].iWait--;
    }
  }

}

/**
  * get_next_actor
  **/
int get_next_actor() {
  int i;
  int iToReturn;

  iToReturn = -1;

  for (i = 0; i < MAX_TEAM_MEMBERS; i++) {
    if (
         (party[i].iHitPoints > 0) &&
         (party[i].iWait <= 0)
       ) {
      iToReturn = i;
    }
  }

  return iToReturn;
}

/**
  * shadow_print - prints to the bitmap with a font with shadow
  **/
void shadow_print(BITMAP *buffer, char *str, int x, int y) {

  textout(buffer, font, str, x + 1, y + 1, makecol(0, 0, 0));
  textout(buffer, font, str, x, y, makecol(255, 255, 255));


}



/**
  * shutdown
  **/
void shutdown() {
  destroy_bitmap(buffer);
}


/**
  * main
  **/
int main(void) {
  setupAllegro();
  displayTitleScreen();


  battle();
  shutdown();
  return 0;
}
