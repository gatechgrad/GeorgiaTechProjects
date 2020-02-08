#define SCREEN_WIDTH 640
#define SCREEN_HEIGHT 480
#define MAX_MAPS 32
#define MAX_WEAPONS 5
#define MAX_ITEMS 10

#define TILE_SIZE 32

#define R_SCROLL_POSITION 416
#define L_SCROLL_POSITION 224
#define MAX_ANIM_STEPS 6
#define FRAMES_PER_STEP 2
#define MAX_MONSTERS 10
#define MAX_PROJECTILES 10
#define MAX_HORIZONTAL_TILES 100
#define MAX_VERTICAL_TILES 15

#define ITEM_INFO 0
#define ITEM_HEALTH 1
#define ITEM_ATTACK 2
#define ITEM_MAXHP 3

typedef struct Item {
  int iType;
  int iXPosition;
  int iYPosition;
  int iItemValue;
  int doesExist;
  int iWidth;
  int iHeight;
  SAMPLE *SFX;
  BITMAP *iCurrentImage;
} Item;


typedef struct Weapon {
  int iFireDelay;
  int iBlastRadius;
  int isObtained;
  int iVelocity;
  int iAmmo;
//  BITMAP *image;
  BITMAP *ammo_image;
  char *strName;
} Weapon;

typedef struct Player {
  int iMoney;
  int iXPosition;
  int iXVelocity;
  int iYPosition;
  int iJumpHeight;
  int isJumping;
  int isFalling;
  int iJumpRate;
  int iWalkRate;
  int iJumpMax;
  int iCanJump;
  int iRunRate;
  int isRunning;
  int iStepCount;
  int isFacingRight;
  int iWidth;
  int iHeight;
  int iGunHeight;
  int iShotDelay;
  int iHP;
  int iMaxHP;
  int isHit;
  int iGems[5];
  int iEquippedWeapon;

  int iCurrentFrame;
  int iWalkFrames;

} Player;

typedef struct Monster {
  int ID;
  int iHP;
  int iHit;
  int iXPosition;
  int iYPosition;
  int isFacingRight;
  int iHeight;
  int iWidth;
  int iCurrentImage;
//  int iWalkImages;
  int iSpeed;
  int isDead;
  int iAttack;
} Monster;

typedef struct Projectile {
  int iXPosition;
  int iYPosition;
  int iHorizontalVelocity;
  int iVerticalVelocity;
  int iWidth;
  int iHeight;
  int doesExist;
  int iPower;
  int iType;
  BITMAP *ammo_image;

}  Projectile;

typedef struct Map {
  int iHorizontalTiles;
  int iVerticalTiles;

  int iMapObjects[MAX_HORIZONTAL_TILES][MAX_VERTICAL_TILES];
  int iRoomNumber;
  int iRoomLeft;
  int iRoomRight;
  int iRoomUp;
  int iRoomDown;
  int isValid;


} Map;

