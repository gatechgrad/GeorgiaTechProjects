#include "Sword.h"

Sword::Sword() {
	iWidth = 4;
	iHeight = 4;

	imgSwordUp = CL_TargaProvider::create("images/sword001u.tga", NULL, true, true, 255, 0, 255);
	imgSwordDown = CL_TargaProvider::create("images/sword001d.tga", NULL, true, true, 255, 0, 255);
	imgSwordLeft = CL_TargaProvider::create("images/sword001l.tga", NULL, true, true, 255, 0, 255);
	imgSwordRight = CL_TargaProvider::create("images/sword001r.tga", NULL, true, true, 255, 0, 255);

	imgCurrent = imgSwordUp;

}

int Sword::getBoundaryX1() {
	return iPositionX;
}

int Sword::getBoundaryX2() {
	return iPositionX + iWidth;
}

int Sword::getBoundaryY1() {
	return iPositionY;
}

int Sword::getBoundaryY2() {
	return iPositionY + iHeight;
}

void Sword::setPosition(int x, int y) {
	iPositionX = x;
	iPositionY = y;
}

bool Sword::isStabbing() {
	return bStabbing;
}

bool Sword::isShooting() {
	return bShooting;
}

void Sword::shoot(int x, int y) {
	iShootVelocityX = x;
	iShootVelocityY = y;

	if ((iShootVelocityX > 0) &&
		(iShootVelocityY == 0)) {
		imgCurrent = imgSwordRight;
	} else if ((iShootVelocityX < 0) &&
		(iShootVelocityY == 0)) {
		imgCurrent = imgSwordLeft;
	} else if ((iShootVelocityX == 0) &&
		(iShootVelocityY > 0)) {
		imgCurrent = imgSwordDown;
	} else if ((iShootVelocityX == 0) &&
		(iShootVelocityY < 0)) {
		imgCurrent = imgSwordUp;
	}

	bShooting = true;
}

void Sword::shootMove() {

	if (bShooting == true) {
		iPositionX += iShootVelocityX;
		iPositionY += iShootVelocityY;
	}

	if ((iPositionX < 0) ||
		(iPositionX > 256) ||
		(iPositionY < 0) ||
		(iPositionY > 168) ) {
		bShooting = false;
		iShootVelocityX = 0;
		iShootVelocityY = 0;
	}

}

int Sword::hasCollided(int x1, int x2, int y1, int y2) {
	int iReturn;
	int i, j;

	iReturn = 0;

	for (i = x1; i < x2; i++) {
		for (j = y1; j < y2; j++) {
			if ((iPositionX < x2) &&
				(iPositionX > x1) &&
				(iPositionY < y2) &&
				(iPositionY > y1) ) {
				iReturn = 1;
			}
		}
	}
	return iReturn;
}

CL_Surface *Sword::getSwordImage() {
	return imgCurrent;

}
