#include "Monster.h"

Monster::Monster(void) {
	srand( (unsigned)time( NULL ) );

/*
	iPositionX = 50;
	iPositionY = 50;
	iWidth = 16;
	iHeight = 16;
	isAlive = false;
	iVelocityX = 2;
	iVelocityY = 0;

	srand( (unsigned)time( NULL ) );

	iPositionX = rand() % 256;
	iPositionY = rand() % 168;
*/

	imgFaceLeft = CL_TargaProvider::create("images/octorok001l.tga", NULL, true, true, 255, 0, 255);
	imgFaceRight = CL_TargaProvider::create("images/octorok001r.tga", NULL, true, true, 255, 0, 255);

	imgCurrent = imgFaceLeft;

}

void Monster::getMonsters(int iRow, int iCol) {
	int i;
	//kill all enemies before adding new ones :-)
	for (i = 0; i < MAX_MONSTERS; i++) {
		isAlive[i] = false;
	}


	if ((iRow == 6) && (iCol == 7)) {
		spawn(0, 80, 32);
		spawn(1, 80, 96);
		spawn(2, 96, 64);
		spawn(3, 128, 80);

		
	} else if ((iRow == 6) && (iCol == 8)) {
		spawn(0, 48, 16);
		spawn(1, 80, 16);
		spawn(2, 112, 16);
		spawn(3, 160, 16);
	} else if ((iRow == 6) && (iCol == 6)) {
		spawn(0, 194, 104);
		spawn(1, 82, 34);
		spawn(2, 82, 34);
		spawn(3, 82, 98);
	} else if ((iRow == 7) && (iCol == 8)) {
		spawn(0, 48, 16);
		spawn(1, 80, 16);
		spawn(2, 112, 16);
		spawn(3, 232, 52);

	}


}

bool Monster::getAlive(int i) {
	return isAlive[i];
}

int Monster::getBoundaryX1(int i) {
	return iPositionX[i];
}

int Monster::getBoundaryY1(int i) {
	return iPositionY[i];
}

int Monster::getBoundaryX2(int i) {
	return iPositionX[i] + iWidth[i];
}

int Monster::getBoundaryY2(int i) {
	return iPositionY[i] + iHeight[i];
}

void Monster::spawn(int iMonster, int x, int y) {

	if (iPositionX < 0) {
		srand( (unsigned)time( NULL ) );
		iPositionX[iMonster] = rand() % 256;
	}

	if (iPositionY < 0) {
		srand( (unsigned)time( NULL ) );
		iPositionY[iMonster] = rand() % 168;
	}

	iPositionX[iMonster] = x;
	iPositionY[iMonster] = y;
	iWidth[iMonster] = 16;
	iHeight[iMonster] = 16;
	isAlive[iMonster] = true;
	iHP[iMonster] = 1;
	iVelocityX[iMonster] = 2;
	iVelocityY[iMonster] = 0;


}

void Monster::move(int i) {

	iPositionX[i] += iVelocityX[i];
	iPositionY[i] += iVelocityY[i];

	if (iVelocityX[i] > 0) {
		imgCurrent = imgFaceRight;
	} else if (iVelocityX[i] < 0) {
		imgCurrent = imgFaceLeft;
	}
}

CL_Surface *Monster::getMonsterImage(int i) {
	return imgCurrent;
}

void Monster::changeDirection(int i) {
	int iDirection;
	
	iDirection = rand() % 4;
	
	switch(iDirection) {
		case 0:
			iVelocityX[i] = 0;
			iVelocityY[i] = -2;
			break;
		case 1:
			iVelocityX[i] = 2;
			iVelocityY[i] = 0;
			break;
		case 2:
			iVelocityX[i] = 0;
			iVelocityY[i] = 2;
			break;
		case 3:
			iVelocityX[i] = -2;
			iVelocityY[i] = 0;
			break;
	}

}

int Monster::getVelocityX(int i) {
	return iVelocityX[i];
}

int Monster::getVelocityY(int i) {
	return iVelocityY[i];
}

void Monster::addDamage(int iMonster, int iDamage) {
	iHP[iMonster] -= iDamage;

	if (iHP[iMonster] < 1) {
		isAlive[iMonster] = false;

	}
}
