#include <ClanLib/core.h>
#include <ClanLib/application.h>
#include <ClanLib/display.h>
#include <stdlib.h>
#include <time.h>
#define MAX_MONSTERS 10



class Monster {

public:
	Monster();

	int getBoundaryX1(int i);
	int getBoundaryX2(int i);
	int getBoundaryY1(int i);
	int getBoundaryY2(int i);
	int getVelocityX(int i);
	int getVelocityY(int i);
	bool getAlive(int i);

	void spawn(int iMonster, int x, int y);
	void move(int i);
	void changeDirection(int i);
	CL_Surface *getMonsterImage(int i);
	void getMonsters(int iRow, int iCol);
	void addDamage(int iMonster, int iDamage);

private:
	int iPositionX[MAX_MONSTERS];
	int iPositionY[MAX_MONSTERS];
	int iWidth[MAX_MONSTERS];
	int iHeight[MAX_MONSTERS];
	bool isAlive[MAX_MONSTERS];
	int iHP[MAX_MONSTERS];

	int iVelocityX[MAX_MONSTERS];
	int iVelocityY[MAX_MONSTERS];

	CL_Surface *imgFaceLeft;
	CL_Surface *imgFaceRight;
	CL_Surface *imgCurrent;
};

