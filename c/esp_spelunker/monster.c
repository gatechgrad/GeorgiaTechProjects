#include <allegro.h>
#include "s.h"

void generateMonsters(Monster theMonsters[MAX_MONSTERS]);
void clearAllMonsters(Monster theMonsters[MAX_MONSTERS]);


/**
 * generateMonsters - randomly places monsters on the screen
 */
void generateMonsters(Monster theMonsters[MAX_MONSTERS]) {
  int iMonsterCount;
  int i;

  srandom(time(NULL));
  clearAllMonsters(theMonsters);


  iMonsterCount = 5 + (random() % (MAX_MONSTERS - 5));

  for (i = 0; i < iMonsterCount; i++) {
    theMonsters[i].ID = 1;
    theMonsters[i].iHP = 5 + (random() % 5);
    theMonsters[i].iXPosition = 20 + (random() % 3000);
    theMonsters[i].iYPosition = (TILE_SIZE * 2) + ((random() % 5) * TILE_SIZE);
    theMonsters[i].isFacingRight = FALSE;
    theMonsters[i].iHeight = 46;
    theMonsters[i].iWidth = 64;
    theMonsters[i].iCurrentImage = 0;
    theMonsters[i].isDead = FALSE;
    theMonsters[i].iSpeed = 1 + (random() % 5);
    theMonsters[i].iAttack = 5;
    theMonsters[i].iCurrentImage = 0;
    theMonsters[i].iHit = 0;
//    theMonsters[i].iWalkImages = 3;
    


  }

}

/**
 * clearAllMonsters - removes all monsters from the screen
 */
void clearAllMonsters(Monster theMonsters[MAX_MONSTERS]) {
  int i;

  for (i = 0; i < MAX_MONSTERS; i++) {
    theMonsters[i].isDead = TRUE;
  }
}

