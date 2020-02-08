#include <allegro.h>      
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "s_resources.h"
#include "s.h"
//#define DEBUG

#define FRAMES_PER_SEC 33
#define MAX_WEAPONS 5

int iXOffset = 0;
int iTheme = 0;
volatile int iGameTime;
int noJoystick;

/** DRAWING VARIABLES **/
PALLETE my_pallete;
BITMAP *player_sprites[5];
BITMAP *environment_sprites[2];
BITMAP *enemy_sprites[5];
BITMAP *item_sprites[4];
BITMAP *title_image;
BITMAP *background;
BITMAP *ammo_sprites[5];

/***********************/


SAMPLE *SFX[10];
MIDI *MUSIC[1];
DATAFILE *data;

/** GLOBAL VARIABLES **/
PALLETE my_pallete;
BITMAP *buffer;
Weapon weapons[5]; //I'll make it a global since I can't place it in Player
Item theItems[MAX_ITEMS];
Player *thePlayer;
Monster theMonsters[MAX_MONSTERS];
Projectile theProjectiles[MAX_PROJECTILES];
Map *mapCurrent;


int iFlashMessage;
int iMessageX;
char *strMessage;

int iDebug;
int iDisplayWidth, iDisplayHeight;




/** FUNCTION PROTOTYPES **/
extern void generateMap(Map *theMap);
extern void generateItems(Item theItems[MAX_ITEMS], Map *theMap);
extern void drawMiniMap(BITMAP *bmp_buffer, Map *theMap, int x, int y, int pX, int pY, Item theItems[MAX_ITEMS], Monster theMonsters[MAX_MONSTERS], int iDrawPlayer, int iDrawItems, int iDrawMonsters);
extern void generateMonsters(Monster theMonsters[MAX_MONSTERS]);
extern void runTitle();
extern void resetMaps();
extern void loadMap(Map *theMap, int iRoom, int iLeft, int iRight, int iUp, int iDown);
void setupPlayer();
void setupProjectiles(Projectile p[MAX_PROJECTILES]);
void runGame();
void repaint();
int handleAction();
void shoot();
int isFalling(int x, int y, int iWidth, Map *mapCurrent);
int canMove(int x, int y, int iWidth, int iHeight, Map *mapCurrent);
int canMoveUp(int x, int y, int iWidth, int iHeight, Map *mapCurrent);
void moveMonsters();
void moveProjectiles();
void setupAllegro();
void getOptions();
void loadImages();
void loadSounds();
void removeAllegro();
int main(int iArgCount, char *strArgs[]);
void gameOver();
void setupWeapons(Weapon theWeapons[MAX_WEAPONS]);
void displayFullMap(int iCurrentRoom);
void loadEnvironImages();
void checkItems();


void game_timer() {
   iGameTime++;
}

END_OF_FUNCTION(game_timer);


/**
 * setupPlayer
 */
void setupPlayer() {

  thePlayer = malloc(sizeof(Player));

  thePlayer->iXPosition = 100;
  thePlayer->iYPosition = 100;
  thePlayer->isJumping = FALSE;
  thePlayer->iCanJump = TRUE;
  thePlayer->isFalling = FALSE;
  thePlayer->iJumpRate = 16;
  thePlayer->iWalkRate = 5;
  thePlayer->iRunRate = 10;
  thePlayer->iJumpMax = TILE_SIZE * 5;
  thePlayer->iStepCount = 0;
  thePlayer->isFacingRight = TRUE;
  thePlayer->iWidth = 49;
  thePlayer->iHeight = 64;
  thePlayer->iGunHeight = 32;
  thePlayer->iShotDelay = 0;
  thePlayer->iHP = 40;
  thePlayer->iMaxHP = 40;
  thePlayer->isHit = 0;
  thePlayer->iMoney = 0;
  thePlayer->iEquippedWeapon = 0;
  thePlayer->iGems[ITEM_INFO] = 0;
  thePlayer->iGems[ITEM_HEALTH] = 0;
  thePlayer->iGems[ITEM_ATTACK] = 0;
  thePlayer->iGems[ITEM_MAXHP] = 0;

}


/**
 * setupProjectiles
 */
void setupProjectiles(Projectile p[MAX_PROJECTILES]) {
  int i;

  for (i = 0; i < MAX_PROJECTILES; i++) {
    p[i].doesExist = FALSE;
  }

}


/**
 * runGame
 */
void runGame() {

  int iKeepLooping;



  mapCurrent = malloc(sizeof(Map));
  resetMaps();


  loadMap(mapCurrent, 0, -1, -1, -1, -1);




  generateMonsters(theMonsters);
  generateItems(theItems, mapCurrent);




  setupPlayer();
  setupWeapons(weapons);
  setupProjectiles(theProjectiles);

  iKeepLooping = TRUE;

  install_int(game_timer, FRAMES_PER_SEC);
  iGameTime = 0;




  play_midi(MUSIC[0], TRUE);



  while(iKeepLooping == TRUE) {



    repaint();


      if (thePlayer->isHit > 0) {
        thePlayer->isHit--;
      }


      while(iGameTime > 0) {
        if (thePlayer->iShotDelay > 0) {
          thePlayer->iShotDelay = thePlayer->iShotDelay - 1;
        }


        if (noJoystick == FALSE) {
          poll_joystick();
        }

        iKeepLooping = handleAction();
        moveMonsters();
        moveProjectiles();
   
        iGameTime--;
    }
    



    if (thePlayer->iHP <= 0) {
      stop_midi();
      thePlayer->iHP = 0;
      repaint();
      gameOver();
      thePlayer->iHP = thePlayer->iMaxHP;
      play_midi(MUSIC[0], TRUE);

    }
  }


  stop_midi();
  remove_int(game_timer);


  runTitle();
}


/**
 * repaint
 */
void repaint() {
  BITMAP *bmp_buffer;
  BITMAP *bmp_temp;
  char strTemp[256];
  int iGemCounter;

  int iColor;
  int i, j;
  int iPosition;

  bmp_buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(bmp_buffer);


  //drawing calls
  if (thePlayer->isJumping == TRUE) {
    iColor = makecol(0, 255, 0);
  } else if (thePlayer->isFalling == TRUE) {
    iColor = makecol(255, 0, 0);
  } else {
    iColor = makecol(0, 255, 255);
  }

  //draw background
  if (mapCurrent->iRoomNumber == 0) {

  } else if (mapCurrent->iRoomNumber == 2) {
    if ((iXOffset > 1000) && (iXOffset < 2000)) {
      set_trans_blender(0, 0, 0, 0);

      draw_lit_sprite(bmp_buffer, background, 0, 0, 255 - ((iXOffset - 1000) / 4));

    }

    if (iXOffset >= 2000) {
      draw_sprite(bmp_buffer, background, 0, 0);
    }
  } else {
    draw_sprite(bmp_buffer, background, 0, 0);
  }

  //draw environment blocks
  for (i = 0; i < MAX_HORIZONTAL_TILES; i++) {
    for (j = 0; j < MAX_VERTICAL_TILES; j++) {
      if (mapCurrent->iMapObjects[i][j] > 0) {

//          rectfill(bmp_buffer, (i * TILE_SIZE) - iXOffset, j * TILE_SIZE,
//            (i * TILE_SIZE) + TILE_SIZE - iXOffset, (j * TILE_SIZE) + TILE_SIZE, makecol(0, 0, 128));


          iPosition = i * TILE_SIZE - iXOffset;
          if (((iPosition + TILE_SIZE) > 0) && (iPosition < SCREEN_WIDTH)) {
            draw_sprite(bmp_buffer, environment_sprites[mapCurrent->iMapObjects[i][j] - 1], i * TILE_SIZE - iXOffset, j * TILE_SIZE);
          }
        }
      }
  }

  //draw weapon name
  sprintf(strTemp, "Weapon: %s x %d", weapons[thePlayer->iEquippedWeapon].strName, weapons[thePlayer->iEquippedWeapon].iAmmo);
  textout(bmp_buffer, font, strTemp, 400, 40, makecol(128, 255, 128));

  //draw projectiles
  for (i = 0; i < MAX_PROJECTILES; i++) {
    if (theProjectiles[i].doesExist == TRUE) {
      draw_sprite(bmp_buffer, theProjectiles[i].ammo_image, theProjectiles[i].iXPosition, theProjectiles[i].iYPosition);
/*
      rectfill(bmp_buffer, theProjectiles[i].iXPosition, theProjectiles[i].iYPosition,
        theProjectiles[i].iXPosition + 2, theProjectiles[i].iYPosition - 2, makecol(255, 0, 0));
*/
    }
  }
  
  //draw player
  if ((thePlayer->isHit) % 2 == 0) {
  if (thePlayer->isJumping || thePlayer->isFalling) {
    if (thePlayer->isFacingRight) {
      draw_sprite(bmp_buffer, player_sprites[0], thePlayer->iXPosition, thePlayer->iYPosition - thePlayer->iHeight);
    } else {
      draw_sprite_h_flip(bmp_buffer, player_sprites[0], thePlayer->iXPosition, thePlayer->iYPosition - thePlayer->iHeight);
    }
  } else {
    if(thePlayer->isFacingRight) {
      draw_sprite(bmp_buffer, player_sprites[thePlayer->iStepCount / FRAMES_PER_STEP], thePlayer->iXPosition, thePlayer->iYPosition - thePlayer->iHeight);
    } else {
      draw_sprite_h_flip(bmp_buffer, player_sprites[thePlayer->iStepCount / FRAMES_PER_STEP], thePlayer->iXPosition, thePlayer->iYPosition - thePlayer->iHeight);
    }

    thePlayer->iStepCount++;
    if ((thePlayer->iStepCount / FRAMES_PER_STEP) > 2) {
      thePlayer->iStepCount = 0;
    }
  }
  }

//  rectfill(bmp_buffer, thePlayer->iXPosition, thePlayer->iYPosition,
//    thePlayer->iXPosition + thePlayer->iWidth, thePlayer->iYPosition - thePlayer->iHeight, iColor);



  //draw enemies
  for (i = 0; i < MAX_MONSTERS; i++) {
    if (theMonsters[i].isDead == FALSE) {


      if (theMonsters[i].isFacingRight) {
        draw_sprite_h_flip(bmp_buffer, enemy_sprites[theMonsters[i].iCurrentImage], theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition - theMonsters[i].iHeight);
      } else {
        draw_sprite(bmp_buffer, enemy_sprites[theMonsters[i].iCurrentImage], theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition - theMonsters[i].iHeight);
      }


      if (theMonsters[i].iHit > 0) {
        set_trans_blender(192, 0, 0, 0);

        if (theMonsters[i].isFacingRight) {
          //ugly hack
          draw_sprite_h_flip(enemy_sprites[theMonsters[i].iCurrentImage], enemy_sprites[theMonsters[i].iCurrentImage], theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition - theMonsters[i].iHeight);
          draw_lit_sprite(bmp_buffer, enemy_sprites[theMonsters[i].iCurrentImage], theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition - theMonsters[i].iHeight, 128);
          draw_sprite_h_flip(enemy_sprites[theMonsters[i].iCurrentImage], enemy_sprites[theMonsters[i].iCurrentImage], theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition - theMonsters[i].iHeight);

        } else {
          draw_lit_sprite(bmp_buffer, enemy_sprites[theMonsters[i].iCurrentImage], theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition - theMonsters[i].iHeight, 128);
        }


        theMonsters[i].iHit--;
      }



#ifdef DEBUG
      sprintf(strTemp, "%d", theMonsters[i].iHP);
      textout(bmp_buffer, font, strTemp, theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition, makecol(255, 255, 255));
#endif

    }
  }

  //draw items
  for (i = 0; i < MAX_ITEMS; i++) {
    if (theItems[i].doesExist == TRUE) {
        draw_sprite(bmp_buffer, theItems[i].iCurrentImage, theItems[i].iXPosition - iXOffset, theItems[i].iYPosition - TILE_SIZE);

//      rectfill(bmp_buffer, theItems[i].iXPosition - iXOffset, theItems[i].iYPosition, theItems[i].iXPosition - iXOffset + TILE_SIZE, theItems[i].iYPosition + TILE_SIZE, makecol(0, 128, 128));

    }
  }


  //draw status
  iGemCounter = 1;

  if (thePlayer->iGems[ITEM_INFO] >= iGemCounter++) {
    rectfill(bmp_buffer, 20, 10, 20 + thePlayer->iMaxHP, 25, makecol(0, 64, 0));
    rectfill(bmp_buffer, 20, 10, 20 + thePlayer->iHP, 25, makecol(0, 192, 0));
    rect(bmp_buffer, 20, 10, 20 + thePlayer->iMaxHP, 25, makecol(0, 96, 0));
  }

  if (thePlayer->iGems[ITEM_INFO] >= iGemCounter++) {
    sprintf(strTemp, "Net Value: %d", thePlayer->iMoney);
    textout(bmp_buffer, font, strTemp, 20, 40, makecol(255, 255, 255));
  }

  if (thePlayer->iGems[ITEM_INFO] >= iGemCounter++) {
    textout(bmp_buffer, font, "Gems", 20, 60, makecol(255, 255, 255));

    sprintf(strTemp, "I: %d", thePlayer->iGems[0]);
    textout(bmp_buffer, font, strTemp, 70, 60, makecol(0, 0, 255));

    sprintf(strTemp, "H: %d", thePlayer->iGems[1]);
    textout(bmp_buffer, font, strTemp, 120, 60, makecol(0, 255, 0));

    sprintf(strTemp, "A: %d", thePlayer->iGems[2]);
    textout(bmp_buffer, font, strTemp, 170, 60, makecol(255, 0, 0));

    sprintf(strTemp, "M: %d", thePlayer->iGems[3]);
    textout(bmp_buffer, font, strTemp, 220, 60, makecol(255, 255, 0));

  }


  if (thePlayer->iGems[ITEM_INFO] == iGemCounter++) {
    drawMiniMap(bmp_buffer, mapCurrent, 400, 10, (thePlayer->iXPosition + iXOffset) / TILE_SIZE, ((thePlayer->iYPosition) / TILE_SIZE) - 2, theItems, theMonsters, FALSE, FALSE, FALSE);
  }

  if (thePlayer->iGems[ITEM_INFO] == iGemCounter++) {
    drawMiniMap(bmp_buffer, mapCurrent, 400, 10, (thePlayer->iXPosition + iXOffset) / TILE_SIZE, ((thePlayer->iYPosition) / TILE_SIZE) - 2, theItems, theMonsters, TRUE, FALSE, FALSE);
  }

  if (thePlayer->iGems[ITEM_INFO] == iGemCounter++) {
    drawMiniMap(bmp_buffer, mapCurrent, 400, 10, (thePlayer->iXPosition + iXOffset) / TILE_SIZE, ((thePlayer->iYPosition) / TILE_SIZE) - 2, theItems, theMonsters, TRUE, TRUE, FALSE);
  }

  if (thePlayer->iGems[ITEM_INFO] >= iGemCounter++) {
    drawMiniMap(bmp_buffer, mapCurrent, 400, 10, (thePlayer->iXPosition + iXOffset) / TILE_SIZE, ((thePlayer->iYPosition) / TILE_SIZE) - 2, theItems, theMonsters, TRUE, TRUE, TRUE);
  }


#ifdef DEBUG
  sprintf(strTemp, "%d", thePlayer->iHP);
  textout(bmp_buffer, font, strTemp, 20, 10, makecol(255, 255, 255));

  sprintf(strTemp, "XOffset: %d", iXOffset);
  textout(bmp_buffer, font, strTemp, 20, 50, makecol(255, 255, 255));

  sprintf(strTemp, "Left: %d, Right: %d", mapCurrent->iRoomLeft, mapCurrent->iRoomRight);
  textout(bmp_buffer, font, strTemp, 20, 70, makecol(255, 255, 255));
#endif

  //flash a message
  if (iFlashMessage > 0) {

    textout(bmp_buffer, font, strMessage, (SCREEN_WIDTH / 2) + 2, (SCREEN_HEIGHT / 2) + 2, makecol(0, 0, 0));
    textout(bmp_buffer, font, strMessage, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, makecol(255, 255, 255));
    iFlashMessage--;


  }

/*
    sprintf(strTemp, "isFalling: %d, T/F %d, y %d", thePlayer->iJumpRate - isFalling(thePlayer->iXPosition, thePlayer->iYPosition + thePlayer->iJumpRate, thePlayer->iWidth, mapCurrent), thePlayer->isFalling, thePlayer->iYPosition);
    textout(bmp_buffer, font, strTemp, 220, 60, makecol(255, 255, 0));
    rect(bmp_buffer, thePlayer->iXPosition, thePlayer->iYPosition + thePlayer->iJumpRate, thePlayer->iXPosition + thePlayer->iWidth, thePlayer->iYPosition + thePlayer->iJumpRate,makecol(0, 128, 128));
*/

  //end drawing calls


  //paste image to screen
  blit(bmp_buffer, screen, 0, 0, 0, 0, iDisplayWidth, iDisplayHeight);

  destroy_bitmap(bmp_buffer);


}


/**
 * gameOver - screen to display when game is over
 */
void gameOver() {
  textout(screen, font, "GAME OVER", 300, 200, makecol(255, 0, 0));

  thePlayer->iGems[ITEM_INFO] = 0;
  thePlayer->iGems[ITEM_HEALTH] = 0;
  thePlayer->iGems[ITEM_ATTACK] = 0;
  thePlayer->iGems[ITEM_MAXHP] = 0;
  thePlayer->iMoney = 0;

  while (!key[KEY_SPACE]) {

  }


}


/**
 * handleAction
 */
int handleAction() {
  int iContinueLooping = TRUE;
  int isStanding = TRUE;

  thePlayer->isRunning = FALSE;

  if (key[KEY_R]) {
    generateMap(mapCurrent);
    generateMonsters(theMonsters);
    generateItems(theItems, mapCurrent);

  }

  if (key[KEY_W]) {
    (thePlayer->iEquippedWeapon)++;

    if (thePlayer->iEquippedWeapon > MAX_WEAPONS) {
      thePlayer->iEquippedWeapon = 0;
    }
  }


  if (key[KEY_1]) {
    iTheme = 0;
    loadEnvironImages();
  }

  if (key[KEY_2]) {
    iTheme = 1;
    loadEnvironImages();
  }

  if (key[KEY_3]) {
    iTheme = 2;
    loadEnvironImages();
  }


  if (key[KEY_M]) {
    displayFullMap(mapCurrent->iRoomNumber);
  }



  if (key[KEY_LEFT] || joy[0].stick[0].axis[0].d1) {
    if (canMove(thePlayer->iXPosition - thePlayer->iWalkRate + iXOffset, thePlayer->iYPosition, thePlayer->iWidth, thePlayer->iHeight, mapCurrent) == TRUE) {
      if (thePlayer->isRunning == TRUE) {
        thePlayer->iXPosition -= thePlayer->iRunRate;
      } else {
        thePlayer->iXPosition -= thePlayer->iWalkRate;
      }
    }

    if (thePlayer->isFacingRight == FALSE) {
//      thePlayer->iStepCount++;
    } else {
//      thePlayer->iStepCount = 1;
      thePlayer->isFacingRight = FALSE;
    }
    isStanding = FALSE;

  }

  if (key[KEY_RIGHT] || joy[0].stick[0].axis[0].d2) {
    if (canMove(thePlayer->iXPosition + thePlayer->iWalkRate + iXOffset, thePlayer->iYPosition, thePlayer->iWidth, thePlayer->iHeight, mapCurrent) == TRUE) {
      if (thePlayer->isRunning) {
        thePlayer->iXPosition += thePlayer->iRunRate;
      } else {
        thePlayer->iXPosition += thePlayer->iWalkRate;
      }
    }

    if (thePlayer->isFacingRight == TRUE) {
//      thePlayer->iStepCount++;
    } else {
//      thePlayer->iStepCount = 1;
      thePlayer->isFacingRight = TRUE;
    }

    isStanding = FALSE;
  }

  if (isStanding) {
    thePlayer->iStepCount = 0;
  }

/*
  if (thePlayer->iStepCount > MAX_ANIM_STEPS) {
    thePlayer->iStepCount = 1;

  }
*/
  if (thePlayer->isJumping == FALSE) {
    if (isFalling(thePlayer->iXPosition, thePlayer->iYPosition + thePlayer->iJumpRate, thePlayer->iWidth, mapCurrent) < 0) {
      thePlayer->isFalling = TRUE;
    } else {
      thePlayer->isFalling = FALSE;
    }

  }

  if (joy[0].button[0].b || key[KEY_CONTROL]) {
    if (thePlayer->iShotDelay == 0) {
      shoot();
    }

  }

  //drop the player if they're falling
  if (thePlayer->isJumping == TRUE) {
    thePlayer->iYPosition -= thePlayer->iJumpRate;
//  } else if (thePlayer->isFalling == TRUE) {
  } else {
    if (isFalling(thePlayer->iXPosition, thePlayer->iYPosition + thePlayer->iJumpRate, thePlayer->iWidth, mapCurrent) < 0) {
      thePlayer->iYPosition += thePlayer->iJumpRate;
    } else {
      thePlayer->iYPosition += (thePlayer->iJumpRate - isFalling(thePlayer->iXPosition, thePlayer->iYPosition + thePlayer->iJumpRate, thePlayer->iWidth, mapCurrent));
    }
  }


  if (joy[0].button[2].b || key[KEY_SPACE]) {

    if ((thePlayer->iJumpHeight < thePlayer->iJumpMax) && (thePlayer->isFalling == FALSE) && (canMoveUp(thePlayer->iXPosition, thePlayer->iYPosition - thePlayer->iJumpRate, thePlayer->iWidth, thePlayer->iHeight, mapCurrent)  == TRUE) && (thePlayer->iCanJump == TRUE)) {
      thePlayer->iJumpHeight += thePlayer->iJumpRate;
      thePlayer->isJumping = TRUE;
      thePlayer->isFalling = FALSE;
    } else {
      //max jump height reached or hit an obstacle from above
      thePlayer->iJumpHeight = 0;
      thePlayer->isFalling = TRUE;
      thePlayer->iCanJump = FALSE;
      thePlayer->isJumping = FALSE;
    }
  } else {
    if (thePlayer->isFalling == FALSE) {
      thePlayer->iCanJump = TRUE;
    }
    thePlayer->isJumping = FALSE;
    thePlayer->iJumpHeight = 0;
  }



  if (key[KEY_ESC]) {
    iContinueLooping = FALSE;
  }


  checkItems();

  if (thePlayer->iXPosition > R_SCROLL_POSITION && ((iXOffset + SCREEN_WIDTH) < (TILE_SIZE * MAX_HORIZONTAL_TILES))) {
    iXOffset += thePlayer->iXPosition - R_SCROLL_POSITION;
    thePlayer->iXPosition = R_SCROLL_POSITION;
  }

  if ((thePlayer->iXPosition < L_SCROLL_POSITION) && (iXOffset > 0)) {
    iXOffset -= L_SCROLL_POSITION - thePlayer->iXPosition;
    thePlayer->iXPosition = L_SCROLL_POSITION;
  }

  //player moves out of the room
  if ((thePlayer->iXPosition + iXOffset) < 0) {
    loadMap(mapCurrent, mapCurrent->iRoomLeft, -1, mapCurrent->iRoomNumber, -1, -1);

    iXOffset = (mapCurrent->iHorizontalTiles * TILE_SIZE) - SCREEN_WIDTH;
    thePlayer->iXPosition = SCREEN_WIDTH - (3 * TILE_SIZE);


/*
    iXOffset = 0;
    thePlayer->iXPosition = (mapCurrent->iHorizontalTiles - 2) * TILE_SIZE;
*/
    generateMonsters(theMonsters);
    generateItems(theItems, mapCurrent);


  }

  if ((thePlayer->iXPosition + iXOffset) > ((mapCurrent->iHorizontalTiles - 2) * TILE_SIZE)) {
    loadMap(mapCurrent, mapCurrent->iRoomRight, mapCurrent->iRoomNumber, -1, -1, -1);
    iXOffset = 0;
    thePlayer->iXPosition = (2) * TILE_SIZE;
    generateMonsters(theMonsters);
    generateItems(theItems, mapCurrent);

  }


  return iContinueLooping;
}

/**
 * checkItems - checks to see if the player has picked up an item,
 *              and handles the item appropriately
 */
void checkItems() {
  int i;

  //check to see if player has picked up an item
  for (i = 0; i < MAX_ITEMS; i++) {
    if (theItems[i].doesExist == TRUE) {
      if (isHit(thePlayer->iXPosition + iXOffset, thePlayer->iYPosition, thePlayer->iWidth, thePlayer->iHeight,
                theItems[i].iXPosition, theItems[i].iYPosition, theItems[i].iWidth, theItems[i].iHeight) == TRUE) {


        switch (theItems[i].iType) {
          case ITEM_HEALTH:
//            thePlayer->iMoney = thePlayer->iMoney + 500;
            thePlayer->iMoney += 500;
            thePlayer->iHP += 5;
            thePlayer->iGems[ITEM_HEALTH] = thePlayer->iGems[ITEM_HEALTH] + 1;
            theItems[i].doesExist = FALSE;
            play_sample(theItems[i].SFX, 255, 128, 1000, FALSE);

            if (thePlayer->iHP > thePlayer->iMaxHP) {
              thePlayer->iHP = thePlayer->iMaxHP;
            }
            break;
          case ITEM_INFO:
            (thePlayer->iGems[ITEM_INFO])++;

            switch(thePlayer->iGems[ITEM_INFO]) {
              case 1:
                flashMessage(0, 20, "Health Meter Displayed", makecol(0, 96, 0), makecol(0, 0, 0));
                break;
              case 2:
                flashMessage(0, 20, "Total Value Displayed", makecol(0, 96, 0), makecol(0, 0, 0));
                break;
              case 3:
                flashMessage(0, 20, "Gem Totals Displayed", makecol(0, 96, 0), makecol(0, 0, 0));
                break;
              case 4:
                flashMessage(0, 20, "Room Map Displayed", makecol(0, 96, 0), makecol(0, 0, 0));
                break;
              case 5:
                flashMessage(0, 20, "Player Position Displayed", makecol(0, 96, 0), makecol(0, 0, 0));
                break;
              case 6:
                flashMessage(0, 20, "Monster Position Displayed", makecol(0, 96, 0), makecol(0, 0, 0));
                break;
              case 7:
                flashMessage(0, 20, "Item Position Displayed", makecol(0, 96, 0), makecol(0, 0, 0));
                break;






            }


            thePlayer->iMoney += 500;
            theItems[i].doesExist = FALSE;
            play_sample(theItems[i].SFX, 255, 128, 1000, FALSE);

            break;

          case ITEM_ATTACK:
            (thePlayer->iGems[ITEM_ATTACK])++;
            thePlayer->iMoney += 500;
            theItems[i].doesExist = FALSE;
            play_sample(theItems[i].SFX, 255, 128, 1000, FALSE);

            break;

          case ITEM_MAXHP:
            (thePlayer->iGems[ITEM_MAXHP])++;
            thePlayer->iMaxHP = 40 + (thePlayer->iGems[ITEM_MAXHP] / 10);
            thePlayer->iMoney += 500;
            theItems[i].doesExist = FALSE;
            play_sample(theItems[i].SFX, 255, 128, 1000, FALSE);

            break;

          default:
            break;


        }
      }
    }
  }


}


/**
 * shoot - shoots the currently selected projectile
 */
void shoot() {
  int i;
  int iFoundCell;
  int iEmptyCell;

  int x, y;

  iFoundCell = FALSE;
  iEmptyCell = -1; //to keep the compiler from complaining

  for (i = 0; i < MAX_PROJECTILES; i++) {
    if ((theProjectiles[i].doesExist == FALSE) &&
        iFoundCell == FALSE) {
      iEmptyCell = i;
      iFoundCell = TRUE;
    }
  }


  if (iFoundCell == TRUE) {
    theProjectiles[iEmptyCell].doesExist = TRUE;
    theProjectiles[iEmptyCell].iPower = thePlayer->iGems[ITEM_ATTACK];
    theProjectiles[iEmptyCell].ammo_image = ammo_sprites[thePlayer->iEquippedWeapon];

    x = thePlayer->iXPosition;
    if (thePlayer->isFacingRight == TRUE) {
      x += thePlayer->iWidth;
    }

    y = thePlayer->iYPosition - thePlayer->iGunHeight;
    theProjectiles[iEmptyCell].iXPosition = x;
    theProjectiles[iEmptyCell].iYPosition = y;

    if (thePlayer->isFacingRight) {
      theProjectiles[iEmptyCell].iHorizontalVelocity = 5;
    } else {
      theProjectiles[iEmptyCell].iHorizontalVelocity = -5;
    }

    theProjectiles[iEmptyCell].iVerticalVelocity = 0;

    thePlayer->iShotDelay = FRAMES_PER_SEC / 3;



/*
    if (thePlayer->isFacingRight) {
      theProjectiles[iEmptyCell].iHorizontalVelocity = weapons[thePlayer->iEquippedWeapon].iVelocity;
    } else {
      theProjectiles[iEmptyCell].iHorizontalVelocity = weapons[thePlayer->iEquippedWeapon].iVelocity * -1;
    }

    theProjectiles[iEmptyCell].iVerticalVelocity = 0;

    thePlayer->iShotDelay = weapons[thePlayer->iEquippedWeapon].iFireDelay;

*/
    play_sample(SFX[0], 255, 128, 1000, FALSE);

  }


}


/**
 * isFalling - determines if an object is falling
 *           - returns how many more pixels the character can fall
 *           - returns negative if character can keep falling
 */
int isFalling(int x, int y, int iWidth, Map *mapCurrent) {
  int isFallingReturn;

  int k, n;
  int i1, i2, j;

  isFallingReturn = -1;

  i1 = (x + iXOffset) / TILE_SIZE;
  i2 = (x + iWidth + iXOffset - 1) / TILE_SIZE;
  j = (y) / TILE_SIZE;


  for (k = i1; k <= i2; k++) {
    if (mapCurrent->iMapObjects[k][j] > 0) {
      //isFallingReturn = FALSE;
      isFallingReturn = y - ((y / TILE_SIZE) * TILE_SIZE);
    }
  }

  return isFallingReturn;
}


/**
 * canMove - tests whether or not the player can move to the specified
 *           position
 */
int canMove(int x, int y, int iWidth, int iHeight, Map *mapCurrent) {

  int iReturn;
  int k, n;

  int i1;
  int i2;
  int j1;
  int j2;


  i1 = (x) / TILE_SIZE;
  i2 = (x + iWidth + 1) / TILE_SIZE;
  j1 = (y - 1) / TILE_SIZE;
  j2 = (y - iHeight) / TILE_SIZE;


  iReturn = TRUE;


  for (k = i1; k <= i2; k++) {
    for (n = j1; n >= j2; n--) {

      if (mapCurrent->iMapObjects[k][n] > 0) {

        iReturn = FALSE;
      }
    }
  }


  return iReturn;

}

/**
 * canMoveUp - tests if the player can move up
 */
int canMoveUp(int x, int y, int iWidth, int iHeight, Map *mapCurrent) {
  int iReturn;

  int k, n;
  int i1, i2, j;

  iReturn = TRUE;

  i1 = (x + iXOffset) / TILE_SIZE;
  i2 = (x + iWidth + iXOffset - 1) / TILE_SIZE;
  j = (y - iHeight) / TILE_SIZE;


  for (k = i1; k <= i2; k++) {
    if (mapCurrent->iMapObjects[k][j] > 0) {
      iReturn = FALSE;
    }
  }

  return iReturn;
}

/**
 * moveMonsters
 */
void moveMonsters() {
  int i;
  int x1, x2, y1, y2;

  for (i = 0; i < MAX_MONSTERS; i++) {
    if (theMonsters[i].isDead == FALSE) {

      theMonsters[i].iCurrentImage++;

      if (theMonsters[i].iCurrentImage > 4) {
        theMonsters[i].iCurrentImage = 0;
      }
      

      if (isFalling(theMonsters[i].iXPosition - iXOffset, theMonsters[i].iYPosition, theMonsters[i].iWidth, mapCurrent)) {
        theMonsters[i].iYPosition++;
      } else {


      if (theMonsters[i].isFacingRight == TRUE) {
          if (canMove(theMonsters[i].iXPosition + theMonsters[i].iSpeed,
              theMonsters[i].iYPosition, theMonsters[i].iWidth,
              theMonsters[i].iHeight, mapCurrent) == TRUE) {
            theMonsters[i].iXPosition += theMonsters[i].iSpeed;
          } else {
            theMonsters[i].isFacingRight = FALSE;
          }

        } else {

          if (canMove(theMonsters[i].iXPosition - theMonsters[i].iSpeed,
              theMonsters[i].iYPosition, theMonsters[i].iWidth,
              theMonsters[i].iHeight, mapCurrent) == TRUE) {
            theMonsters[i].iXPosition -= theMonsters[i].iSpeed;
          } else {
            theMonsters[i].isFacingRight = TRUE;
          }
        }
      }


      //has the monster hit the player??
      if (thePlayer->isHit <= 0) {


      /*
      x1 = theMonsters[i].iXPosition - iXOffset;
      x2 = x1 + theMonsters[i].iWidth;

      y1 = theMonsters[i].iYPosition - theMonsters[i].iHeight;
      y2 = theMonsters[i].iYPosition;

      if ((x1 >= thePlayer->iXPosition) &&
          (x1 < thePlayer->iXPosition + thePlayer->iWidth) &&
          (y1 > thePlayer->iYPosition - thePlayer->iHeight) &&
          (y1 < thePlayer->iYPosition)
         ) {
      */
      if (isHit(thePlayer->iXPosition + iXOffset, thePlayer->iYPosition, thePlayer->iWidth, thePlayer->iHeight,
                theMonsters[i].iXPosition, theMonsters[i].iYPosition, theMonsters[i].iWidth, theMonsters[i].iHeight) == TRUE) {
        thePlayer->iHP -= theMonsters[i].iAttack;
        thePlayer->isHit = 20;

        if (thePlayer->iHP > 0) {
          play_sample(SFX[2], 255, 128, 1000, FALSE);
        } else {
          play_sample(SFX[4], 255, 128, 1000, FALSE);
        }


        if (thePlayer->isFacingRight == TRUE) {
          if (canMove(thePlayer->iXPosition - TILE_SIZE + iXOffset, thePlayer->iYPosition, thePlayer->iWidth, thePlayer->iHeight, mapCurrent) == TRUE) {
            thePlayer->iXPosition -= TILE_SIZE;
          }
        } else {
          if (canMove(thePlayer->iXPosition + TILE_SIZE + iXOffset, thePlayer->iYPosition, thePlayer->iWidth, thePlayer->iHeight, mapCurrent) == TRUE) {

            thePlayer->iXPosition += TILE_SIZE;
          }
        }

      }
      }

    }
  }


}



/**
 * isHit - determines if one object lies inside of another object
 */
int isHit(int o1_X, int o1_Y, int o1_Width, int o1_Height, int o2_X, int o2_Y, int o2_Width, int o2_Height) {
  int iReturn;

  int iWithinY;

  iWithinY = FALSE;
  iReturn = FALSE;

  if ( (o1_Y < (o2_Y - o2_Height)) ||
       ((o1_Y - o1_Height) > o2_Y) ) {
  } else {
    iWithinY = TRUE;
  }

  if (iWithinY == TRUE) {
    if ( (o1_X > (o2_X + o2_Width)) ||
         ((o1_X + o1_Width) < o2_X)  ) {
    } else {
      iReturn = TRUE;
    }
  }

/*
  if ((o1_Y < o2_Y) && ((o1_Y + o1_Height) < o2_Y)) {
    iWithinY = FALSE;
  } else if ((o1_Y > (o2_Y + o2_Height)) && ((o1_Y + o1_Height) > (o2_Y + o2_Height))) {
    iWithinY = FALSE;
  }
*/
  return iReturn;

}



/**
 * moveProjectiles
 */
void moveProjectiles() {
  int i;
  int j;

  for (i = 0; i < MAX_PROJECTILES; i++) {
    if (theProjectiles[i].doesExist == TRUE) {
      theProjectiles[i].iXPosition += theProjectiles[i].iHorizontalVelocity;
      theProjectiles[i].iYPosition += theProjectiles[i].iVerticalVelocity;

      for (j = 0; j < MAX_MONSTERS; j++) {
        if (theMonsters[j].isDead == FALSE) {
          if (((theProjectiles[i].iXPosition + iXOffset) > theMonsters[j].iXPosition) &&
              ((theProjectiles[i].iXPosition + iXOffset) < (theMonsters[j].iXPosition + theMonsters[j].iWidth)) &&
              (theProjectiles[i].iYPosition > (theMonsters[j].iYPosition - theMonsters[j].iHeight)) &&
              (theProjectiles[i].iYPosition < theMonsters[j].iYPosition)
             ) {

            theMonsters[j].iHP = theMonsters[j].iHP - theProjectiles[i].iPower;
            theMonsters[j].iHit = 1; //how long to display the damaged image
            theProjectiles[i].doesExist = FALSE;


            if (theMonsters[j].iHP <= 0) {
              play_sample(SFX[1], 255, 128, 1000, FALSE);
  
              theMonsters[j].isDead = TRUE;
            }
  
          }

        }

      }

    }

    if ((theProjectiles[i].iXPosition < 0) ||
        (theProjectiles[i].iXPosition > SCREEN_WIDTH)) {

      theProjectiles[i].doesExist = FALSE;
    }

  }
}

/**
 * creates all weapons
 */
void setupWeapons(Weapon theWeapons[MAX_WEAPONS]) {
  theWeapons[0].iFireDelay = FRAMES_PER_SEC / 3;
  theWeapons[0].iBlastRadius = 1;
  theWeapons[0].isObtained = TRUE;
  theWeapons[0].iAmmo = 25;
  theWeapons[0].iVelocity = 5;
  theWeapons[0].strName = "Bullets";

  theWeapons[1].iFireDelay = FRAMES_PER_SEC * 3 / 2;
  theWeapons[1].iBlastRadius = 2;
  theWeapons[1].isObtained = FALSE;
  theWeapons[1].iAmmo = 0;
  theWeapons[1].iVelocity = 2;
  theWeapons[1].strName = "Missiles";

  theWeapons[2].iFireDelay = 20;
  theWeapons[2].iBlastRadius = 3;
  theWeapons[2].isObtained = FALSE;
  theWeapons[2].iAmmo = 0;
  theWeapons[2].iVelocity = 2;
  theWeapons[2].strName = "Grenades";

  theWeapons[3].iFireDelay = 20;
  theWeapons[3].iBlastRadius = 3;
  theWeapons[3].isObtained = FALSE;
  theWeapons[3].iAmmo = 0;
  theWeapons[3].iVelocity = 2;
  theWeapons[3].strName = "Homing Missiles";

  theWeapons[4].iFireDelay = 20;
  theWeapons[4].iBlastRadius = 3;
  theWeapons[4].isObtained = FALSE;
  theWeapons[4].iAmmo = 0;
  theWeapons[4].iVelocity = 2;
  theWeapons[4].strName = "Laser";

}

/**
 * setupAllegro
 */
void setupAllegro(int iArgCount, char *strArgs[]) {
  int i;
  int iValue;
  int iFound;

  iDisplayWidth = SCREEN_WIDTH;
  iDisplayHeight = SCREEN_HEIGHT;

  iDebug = FALSE;


  i = 1;
  while (i < iArgCount) {
    iFound = FALSE;

    if ((stricmp(strArgs[i], "-help") == 0) ||
        (stricmp(strArgs[i], "/help") == 0) ||
        (stricmp(strArgs[i], "/?") == 0) ||
        (stricmp(strArgs[i], "-?") == 0)
       ) {

       printf("Spelunker v0.010 written by Levi D. Smith\n");
       printf("Design by Jeff Stewart and ESP Spelunker Team\n\n");
       printf("Usage: s [-w width] [-h height] [-debug]\n\n");

       printf("Command line options:\n");
       printf("\t-debug\tTurn debugging on\n");
       printf("\t-w\tSet screen width\n");
       printf("\t-h\tSet screen height");

       iFound = TRUE;
       exit(0);


     }

    if ((stricmp(strArgs[i], "-debug") == 0) ||
        (stricmp(strArgs[i], "/debug") == 0)
       ) {
       iDebug = TRUE;
       iFound = TRUE;
     }

    if ((stricmp(strArgs[i], "-w") == 0) ||
        (stricmp(strArgs[i], "/w") == 0)
       ) {

       i++;
       printf("Setting display width to %d\n", atoi(strArgs[i]));

       iDisplayWidth = atoi(strArgs[i]);
       iFound = TRUE;
     }

    if ((stricmp(strArgs[i], "-h") == 0) ||
       (stricmp(strArgs[i], "/h") == 0)
       ) {

      i++;
      printf("Setting display height to %d\n", atoi(strArgs[i]));
      iDisplayHeight = atoi(strArgs[i]);
      iFound = TRUE;
    }



    if (iFound == FALSE) {
      printf("switch not found:  %s", strArgs[i]);
      exit(0);

    }

    i++;  
  }

  allegro_init();


  //getOptions();

  install_keyboard();
  install_timer();

  if(install_joystick(JOY_TYPE_4BUTTON) != 0) {
    printf("Error initializing joystick\n%s\n", allegro_error);
    noJoystick = TRUE;
  } else {
    noJoystick = FALSE;
  }


  set_color_depth(16);
  set_gfx_mode(GFX_AUTODETECT, iDisplayWidth, iDisplayHeight, 0, 0);

  if (install_sound(DIGI_AUTODETECT, MIDI_AUTODETECT, "Spelunker") != 0) {
      printf("Error initializing sound system\n%s\n", allegro_error);
      exit(1);
  }

  set_volume(255, 255);


  text_mode(-1);

  buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(buffer);

}


/**
 * getOptions
 */
void getOptions() {
  int iGraphicsMode;
  int iColorDepth;
  int iResolutionX;
  int iResolutionY;
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

  set_color_depth(iColorDepth);
  set_gfx_mode(iGraphicsMode, iResolutionX, iResolutionY, 0, 0);


}



/**
 * loadImages
 */
void loadImages() {
  data = load_datafile("s_resources.dat");


  player_sprites[0] = data[player_step000].dat;
  player_sprites[1] = data[player_step001].dat;
  player_sprites[2] = data[player_step002].dat;
  player_sprites[3] = data[player_step003].dat;
  player_sprites[4] = data[player_step003].dat;


  enemy_sprites[0] = data[enemy001].dat;
  enemy_sprites[1] = data[enemy002].dat;
  enemy_sprites[2] = data[enemy003].dat;
  enemy_sprites[3] = data[enemy004].dat;
  enemy_sprites[4] = data[enemy005].dat;

  item_sprites[0] = data[crystal001].dat;
  item_sprites[1] = data[crystal002].dat;
  item_sprites[2] = data[crystal003].dat;
  item_sprites[3] = data[crystal004].dat;

  ammo_sprites[0] = data[ammo001].dat;
  ammo_sprites[1] = data[ammo002].dat;
  ammo_sprites[2] = data[ammo001].dat;
  ammo_sprites[3] = data[ammo001].dat;
  ammo_sprites[4] = data[ammo001].dat;


  title_image = data[title].dat;

  loadEnvironImages();


  SFX[0] = data[blast].dat;
  SFX[1] = data[enemy_die001].dat;
  SFX[2] = data[hero001].dat;
  SFX[3] = data[hero002].dat;
  SFX[4] = data[hero003].dat;
  SFX[5] = data[crystal_health].dat;

  MUSIC[0] = data[music001].dat;

/*
  generate_332_palette(my_pallete);
  environment_sprites[0] = load_pcx("./images/environ001.pcx", my_pallete);
  environment_sprites[1] = load_pcx("./images/environ002.pcx", my_pallete);

  player_sprites[0] = load_pcx("./images/player_step000.pcx", my_pallete);
  player_sprites[1] = load_pcx("./images/player_step001.pcx", my_pallete);
  player_sprites[2] = load_pcx("./images/player_step002.pcx", my_pallete);
  player_sprites[3] = load_pcx("./images/player_step003.pcx", my_pallete);
  player_sprites[4] = load_pcx("./images/player_jump001.pcx", my_pallete);


  enemy_sprites[0] = load_pcx("./images/enemy001.pcx", my_pallete);
  enemy_sprites[1] = load_pcx("./images/enemy002.pcx", my_pallete);
*/
}

/**
 * loads the environment images
 */
void loadEnvironImages() {
  //destroy previous images
  /*
  destroy_bitmap(environment_sprites[0]);
  destroy_bitmap(environment_sprites[1]);
  destroy_bitmap(background);
  */

  //load new images
  switch(iTheme) {
    case 0:
      environment_sprites[0] = data[environ001].dat;
      environment_sprites[1] = data[environ002].dat;
      background = data[geo_bkg].dat;
      break;
    case 1:
      environment_sprites[0] = data[environ101].dat;
      environment_sprites[1] = data[environ102].dat;
      background = data[lake_bkg].dat;
      break;
    case 2:
      environment_sprites[0] = data[environ201].dat;
      environment_sprites[1] = data[environ202].dat;
      background = data[tech_bkg].dat;
      break;
    default:
      environment_sprites[0] = data[environ001].dat;
      environment_sprites[1] = data[environ002].dat;
      background = data[geo_bkg].dat;
      break;
  }


}

/**
 * loadSounds
 */
void loadSounds() {
//  SFX[0] = load_wav("./sounds/blast.wav");
}

/**
 * removeAllegro
 */
void removeAllegro() {
//  destroyImages();
  unload_datafile(data);

  remove_keyboard();
  remove_joystick();
  remove_timer();
  allegro_exit();

}

/**
 * destroyImages
 */
void destroyImages() {
  destroy_bitmap(environment_sprites[0]);
  destroy_bitmap(environment_sprites[1]);

  destroy_bitmap(player_sprites[0]);
  destroy_bitmap(player_sprites[1]);

  destroy_bitmap(enemy_sprites[0]);
  destroy_bitmap(enemy_sprites[1]);
  destroy_bitmap(enemy_sprites[2]);
  destroy_bitmap(enemy_sprites[3]);
  destroy_bitmap(enemy_sprites[4]);

/*
  destroy_bitmap(enemy_sprites[2]);
  destroy_bitmap(enemy_sprites[3]);
  destroy_bitmap(enemy_sprites[4]);
*/


  destroy_bitmap(title_image);

  destroy_bitmap(buffer);
  destroy_bitmap(background);


  destroy_sample(SFX[0]);
  destroy_sample(SFX[1]);

  destroy_midi(MUSIC[0]);

}

void flashMessage(int x, int iLength, char *str, int iColorOne, int iColorTwo) {
  iMessageX = 0;
  iFlashMessage = iLength;
  strMessage = str;
}


int main(int iArgCount, char *strArgs[]) {
  setupAllegro(iArgCount, strArgs);
  loadImages();
  loadSounds();
  runTitle();

  removeAllegro();

  return 0;
}
