/**
 * map.c - includes all functions to randomly generate a room
 */

#include <allegro.h>
#include <stdlib.h>
#include "s.h"

#define STEPS_BASE 2
#define PILLARS_BASE 4

/** FUNCTION PROTOTYPES **/
void generateMap(Map *theMap);
int makeBorder(Map *theMap);
int makeStairs(Map *theMap, int i);
int makePillar(Map *theMap, int i);
int makePlatforms(Map *theMap, int i);
void makeExits(Map *theMap);
void loadMap(Map *theMap, int iRoom, int iLeft, int iRight, int iUp, int iDown);
int makePassageway(Map *theMap, int i);
int makeOverhang(Map *theMap, int i);
void displayFullMap(int iCurrentRoom);
void drawMiniMap(BITMAP *bmp_buffer, Map *theMap, int x, int y, int pX, int pY, Item theItems[MAX_ITEMS], Monster theMonsters[MAX_MONSTERS], int iDrawPlayer, int iDrawItems, int iDrawMonsters);

/** GLOBAL VARIABLES **/
int iRoomCount = 1;
Map rooms[MAX_MAPS];

void generateMap(Map *theMap) {
  int i;
  int iForm;

  srandom(time(NULL));
  theMap->iMapObjects[5][14] = 2;

  theMap->iHorizontalTiles = MAX_HORIZONTAL_TILES;
  theMap->iVerticalTiles = MAX_VERTICAL_TILES;
  theMap->isValid = TRUE;

  makeBorder(theMap);

  i = 4;

  while (i + 4 < MAX_HORIZONTAL_TILES) {

    i += 3 + random() % 3;

    iForm = random() % 5;

    switch(iForm) {
      case 0:
        i += makeStairs(theMap, i);
        break;
      case 1:
        i += makePillar(theMap, i);
        break;
      case 2:
        i += makePlatforms(theMap, i);
        break;
      case 3:
        i += makePassageway(theMap, i);
        break;
      case 4:
        i += makeOverhang(theMap, i);
        break;
      default:
        break;
    }
  }

  makeExits(theMap);

}

/**
 * makeBorder - creates the border of the room
 *                  ********************
 *                  *                  *
 *                  *                  *
 *                  *                  *
 *                  *                  *
 *                  *                  *
 *                  ********************
 */

int makeBorder(Map *theMap) {
  int i, j;

  for(i = 0; i < MAX_HORIZONTAL_TILES; i++) {
    for (j = 0; j < MAX_VERTICAL_TILES; j++) {
      theMap->iMapObjects[i][j] = 0;
    }
  }


  for (i = 0; i < MAX_HORIZONTAL_TILES; i++) {
    theMap->iMapObjects[i][0] = 1;
    theMap->iMapObjects[i][14] = 1;

    if ((random() % 3) == 0) {
      theMap->iMapObjects[i][1] = 1;
    }

  }


  for (j = 0; j < 15; j++) {
    theMap->iMapObjects[0][j] = 1;
    theMap->iMapObjects[99][j] = 1;
  }

  return i;
}

/**
 * makeStairs - makes a staircase
 *                  ********************
 *                  *                  *
 *                  *                  *
 *                  *       *          *
 *                  *      ***         *
 *                  *     *****        *
 *                  ********************
 */
int makeStairs(Map *theMap, int i) {
  int iHeight;
  int iLength;
  int k, n;


  iHeight = 3 + (random() % 3);
  iLength = 0;


  k = 0;

  while(k < iHeight) {
    for (n = 13; n > 13 - k; n--) {
      theMap->iMapObjects[i][n] = 1;
    }
    i++;
    k++;
    iLength++;
  }

  while(k >= 0) {
    for (n = 13; n > 13 - k; n--) {
      theMap->iMapObjects[i][n] = 1;
    }
    i++;
    k--;
    iLength++;
  }

  return iLength;
}


/**
 * makePillar - creates a pillar object
 *                  ********************
 *                  *                  *
 *                  *                  *
 *                  *                  *
 *                  *     *****        *
 *                  *     *****        *
 *                  ********************
 */
int makePillar(Map *theMap, int i) {
  int iHeight;
  int iWidth;
  int m, n;

  iHeight = 3 + (random() % 2);
  iWidth = 3 + (random() % 2);

  for (m = i; m < i + iWidth; m++) {
    for (n = 13; n > 13 - iHeight; n--) {
      theMap->iMapObjects[m][n] = 1;

    }
  }

  return iWidth;

}


/**
 * makePlatforms - create platform objects
 *                  ********************
 *                  *                  *
 *                  *          *****   *
 *                  *                  *
 *                  *                  *
 *                  *   *******        *
 *                  ********************
 */
int makePlatforms(Map *theMap, int i) {
  int iHeight;
  int iLength;
  int iWidth;
  int n;

  iHeight = 3 + (random() % 5);
  iLength = 2 + (random() % 7);
  iWidth = 0;

  for (n = 0; n < iLength; n++) {
    theMap->iMapObjects[i + n][MAX_VERTICAL_TILES - iHeight] = 2;
  }

  iWidth += iLength;

  iHeight += iHeight + (random() % 3);
  iLength = 2 + (random() % 2);

  for (n = 0; n < iLength; n++) {
    theMap->iMapObjects[i + n][MAX_VERTICAL_TILES - iHeight] = 2;
  }

  iWidth += iLength;

  return iWidth;
}

/**
 * makePassageway - creates a passageway form
 *                  ********************
 *                  *   ************   *
 *                  *   ************   *
 *                  *                  *
 *                  *                  *
 *                  *   ************   *
 *                  ********************
 */
int makePassageway(Map *theMap, int i) {
  int iFloor;
  int iCeiling;
  int iLength;
  int n, m;

  iFloor = 3 + (random() % 3);
  iCeiling = 2 + (random() % 2);
  iLength = 5 + (random() % 7);

  for (n = 0; n < iLength; n++) {
    for (m = 0; m < iFloor; m++) {
      theMap->iMapObjects[i + n][MAX_VERTICAL_TILES - m - 1] = 1;
    }
  }


  for (n = 0; n < iLength; n++) {
    for (m = 0; m < iCeiling; m++) {
      theMap->iMapObjects[i + n][m] = 1;
    }
  }


  return iLength;

}


/**
 * makeOverhang - creates an overhang form
 *                  ********************
 *                  *                  *
 *                  *                  *
 *                  *   *******        *
 *                  *   **             *
 *                  *   **             *
 *                  ********************
 */
int makeOverhang(Map *theMap, int i) {
  int iHeight;
  int iHeight2;
  int iLength;
  int iLength2;
  int iWidth;
  int n, m;

  iLength = 2 + (random() % 1);
  iLength2 = 4 + (random() % 2);
  iHeight = 2 + (random() % 2);
  iHeight2 = 1 + (random() % 2);

  for (n = 0; n < iLength; n++) {
    for (m = 0; m < (iHeight + iHeight2); m++) {
      theMap->iMapObjects[i + n][MAX_VERTICAL_TILES - m - 1] = 1;
    }
  }

  for (n = 0; n < iLength2; n++) {
    for (m = 0; m < iHeight2; m++) {
      theMap->iMapObjects[i + n + iLength][MAX_VERTICAL_TILES - iHeight - m - 1] = 1;
    }
  }


  iWidth = iLength + iLength2;

  return iWidth;



}


/**
 * minitaure representation of the map
 */
void drawMiniMap(BITMAP *bmp_buffer, Map *theMap, int x, int y, int pX, int pY, Item theItems[MAX_ITEMS], Monster theMonsters[MAX_MONSTERS], int iDrawPlayer, int iDrawItems, int iDrawMonsters) {
  int i, j;

  rectfill(bmp_buffer, x, y, x + (2 * MAX_HORIZONTAL_TILES), y + (2 * MAX_VERTICAL_TILES), makecol(0, 0, 0));
  rect(bmp_buffer, x, y, x + (2 * MAX_HORIZONTAL_TILES), y + (2 * MAX_VERTICAL_TILES), makecol(0, 0, 64));

  for (i = 0; i < MAX_HORIZONTAL_TILES; i++) {
    for (j = 0; j < MAX_VERTICAL_TILES; j++) {
      if (theMap->iMapObjects[i][j] > 0) {
        rectfill(bmp_buffer, x + (2 * i), y + (2 * j), x + (2 * i) + 1, y + (2 * j) + 1, makecol(0, 0, 96));
      }
    }
  }

  if (iDrawPlayer == TRUE) {
    rectfill(bmp_buffer, x + (2 * pX), y + (2 * pY), x + (2 * pX) + 1, y + (2 * pY) + 2, makecol(0, 0, 218));
  }

  if (iDrawItems == TRUE) {
    rectfill(bmp_buffer, x, y, x + 5, y + 5, makecol(192, 192, 0));
  }

  if (iDrawMonsters == TRUE) {
    rectfill(bmp_buffer, x + 10, y, x + 15, y + 5, makecol(192, 0, 0));
  }


}

void makeExits(Map *theMap) {
  int i;


  if (theMap->iRoomLeft < 0) {
    if (iRoomCount < MAX_MAPS - 2) {
      theMap->iRoomLeft = iRoomCount;
      iRoomCount++;
      theMap->iMapObjects[theMap->iHorizontalTiles - 1][13] = 0;
      theMap->iMapObjects[theMap->iHorizontalTiles - 1][12] = 0;

    }
  } else {
    theMap->iMapObjects[theMap->iHorizontalTiles - 1][13] = 0;
    theMap->iMapObjects[theMap->iHorizontalTiles - 1][12] = 0;

  }

  if (theMap->iRoomRight < 0) {
    if (iRoomCount < MAX_MAPS - 2) {
      theMap->iRoomRight = iRoomCount;
      iRoomCount++;
      theMap->iMapObjects[0][13] = 0;
      theMap->iMapObjects[0][12] = 0;

    }
  } else {
    theMap->iMapObjects[0][13] = 0;
    theMap->iMapObjects[0][12] = 0;
  }



  //make sure exit is not blocked
  for (i = 1; i < MAX_VERTICAL_TILES - 1; i++) {
    theMap->iMapObjects[MAX_HORIZONTAL_TILES - 2][i] = 0;
    theMap->iMapObjects[MAX_HORIZONTAL_TILES - 3][i] = 0;

  }

}

/**
 * returns the map object specified
 */
void loadMap(Map *theMap, int iRoom, int iLeft, int iRight, int iUp, int iDown) {
  int i, j;


  if (rooms[iRoom].isValid == FALSE) {
    rooms[iRoom].iRoomLeft = iLeft;
    rooms[iRoom].iRoomRight = iRight;
    rooms[iRoom].iRoomUp = iUp;
    rooms[iRoom].iRoomDown = iDown;
    generateMap(&(rooms[iRoom]));
  }

  for (i = 0; i < MAX_HORIZONTAL_TILES; i++) {
    for (j = 0; j < MAX_VERTICAL_TILES; j++) {
      theMap->iMapObjects[i][j] = rooms[iRoom].iMapObjects[i][j];
    }
  }

  theMap->iHorizontalTiles = rooms[iRoom].iHorizontalTiles;
  theMap->iVerticalTiles = rooms[iRoom].iVerticalTiles;
  theMap->iRoomNumber = rooms[iRoom].iRoomNumber;
  theMap->iRoomLeft = rooms[iRoom].iRoomLeft;
  theMap->iRoomRight = rooms[iRoom].iRoomRight;
  theMap->iRoomUp = rooms[iRoom].iRoomUp;
  theMap->iRoomDown = rooms[iRoom].iRoomDown;





}

/**
 * resets all maps
 */
void resetMaps() {
  int i;

  for (i = 0; i < MAX_MAPS; i++) {
    rooms[i].isValid = FALSE;
    rooms[i].iRoomLeft = -1;
    rooms[i].iRoomRight = -1;
    rooms[i].iRoomUp = -1;
    rooms[i].iRoomDown = -1;
    rooms[i].iRoomNumber = i;

  }
}


/**
 * displayFullMap - draws a full map to the screen
 */
void displayFullMap(int iRoomNumber) {

  BITMAP *bmp_buffer;
  int i;

  int x = 300;
  int y = 200;

  bmp_buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(bmp_buffer);


  //needs to be done recursively
  //drawMiniMap(bmp_buffer, &(rooms[iRoomNumber]), x, y, 0, 0);

  if (rooms[iRoomNumber].iRoomLeft > 0) {
    //drawMiniMap(bmp_buffer, &(rooms[rooms[iRoomNumber].iRoomLeft]), x - (rooms[iRoomNumber].iHorizontalTiles * TILE_SIZE), y, 0, 0);
  }

  if (rooms[iRoomNumber].iRoomRight > 0) {
    //drawMiniMap(bmp_buffer, &(rooms[rooms[iRoomNumber].iRoomRight]), x + (rooms[iRoomNumber].iHorizontalTiles * TILE_SIZE), y, 0, 0);
  }



  //paste image to screen
  blit(bmp_buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);

  readkey();
  destroy_bitmap(bmp_buffer);

}
