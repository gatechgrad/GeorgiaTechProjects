#include <ClanLib/core.h>
#include <ClanLib/application.h>
#include <ClanLib/display.h>

#include "Sword.h"

#define LEFT 1
#define RIGHT 2
#define UP 3
#define DOWN 4

class Player {
public:
	Player(void);
	~Player(void);
	void initalize();
	int getXPosition();
	int getYPosition();

	void moveLeft();
	void moveRight();
	void moveUp();
	void moveDown();
	void stand();

	CL_Surface *getPlayerImage();

	//Bounding box for player
	int getBoundaryX1();
	int getBoundaryX2();
	int getBoundaryY1();
	int getBoundaryY2();
	int getMapRow();
	int getMapCol();


	void setPosition(int x, int y);

	Sword *getSword();
	void shoot();

private:
	int iPositionX;
	int iPositionY;

	int iSwordPositionX;
	int iSwordPositionY;

	bool isStanding;

	int iDirection;

	Sword *theSword;


	CL_Surface *imgFaceLeft;
	CL_Surface *imgFaceLeft2;
	CL_Surface *imgFaceLeft3;

	CL_Surface *imgFaceRight;
	CL_Surface *imgFaceRight2;
	CL_Surface *imgFaceRight3;

	CL_Surface *imgFaceUp;
	CL_Surface *imgFaceUp2;
	CL_Surface *imgFaceUp3;

	CL_Surface *imgFaceDown;
	CL_Surface *imgFaceDown2;
	CL_Surface *imgFaceDown3;

	CL_Surface *imgCurrent;


};

