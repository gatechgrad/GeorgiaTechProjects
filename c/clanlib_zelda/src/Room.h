#include <stdio.h>
#include <stdlib.h>
//#include <iostream.h>
//#include <fstream.h>
#include <string.h>

#pragma once
#include <ClanLib/core.h>
#include <ClanLib/application.h>
#include <ClanLib/display.h>
#include "Monster.h"




class Room {



public:
	Room(void);
	~Room(void);
	void getRoom (int iRow, int iCol);
	void setLevel (int i);

/*
	Monster getMonster(int i);
	*/

	CL_Surface *getRoomImage();
	int iLevel;

	const static int iRows = 21;
	const static int iCols = 32;

	int hasCollided(int x1, int x2, int y1, int y2, int iNextX, int iNextY);
	void loadRoomArray(int iMapRow, int iMapCol);
	void copyArray(int iArrayOne[21][32], int iArrayTwo[21][32]);


	int theWorldImagesArray[10][20][21][32];
	int theWorldCollisionArray[10][20][21][32];

	int theLVL001ImagesArray[10][20][21][32];
	int theLVL001CollisionArray[10][20][21][32];

	int theCaveImagesArray[10][20][21][32];
	int theCaveCollisionArray[10][20][21][32];



	int currentRoomImagesArray[21][32];
	int currentRoomCollisionArray[21][32];

	void moveMonsters();


private:
	/*
	Monster theMonsters[10];
	*/




};

