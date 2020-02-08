#include <allegro.h>
#include "s.h"

/** FUNCTION PROTOTYPES **/
void generateItems(Item theItems[MAX_ITEMS], Map *theMap);
void clearItems(Item theItems[MAX_ITEMS]);
extern int isFalling(int x, int y, int iWidth, Map *mapCurrent);

extern SAMPLE *SFX[10];
extern BITMAP *item_sprites[4];


void generateItems(Item theItems[MAX_ITEMS], Map *theMap) {
  int iItemCount;
  int iItemType;
  int i;

  srandom(time(NULL));
  clearItems(theItems);


  iItemCount = (MAX_ITEMS / 2) + (random() % (MAX_ITEMS / 2));

  for (i = 0; i < iItemCount; i++) {
    iItemType = random() % 4;


    theItems[i].doesExist = TRUE;
    theItems[i].iType = iItemType;
    theItems[i].iXPosition = 20 + (random() % (MAX_HORIZONTAL_TILES * TILE_SIZE));
    theItems[i].iYPosition = (TILE_SIZE * 2) + ((random() % 5) * TILE_SIZE);
    theItems[i].iWidth = 32;
    theItems[i].iHeight = 32;
    theItems[i].SFX = SFX[5];
    theItems[i].iCurrentImage = item_sprites[iItemType];

    while (isFalling(theItems[i].iXPosition, theItems[i].iYPosition, theItems[i].iWidth, theMap) == TRUE) {
      theItems[i].iYPosition++;
    }


  }

}

void clearItems(Item theItems[MAX_ITEMS]) {
  int i;

  for (i = 0; i < MAX_ITEMS; i++) {
    theItems[i].doesExist = FALSE;
  }
}
