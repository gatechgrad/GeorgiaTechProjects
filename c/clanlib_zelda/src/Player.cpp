#include "Player.h"


Player::Player(void) {
	imgFaceLeft = CL_TargaProvider::create("images/link001.tga", NULL, true, true, 255, 0, 255);
	imgFaceLeft2 = CL_TargaProvider::create("images/link001a.tga", NULL, true, true, 255, 0, 255);
	imgFaceLeft3 = CL_TargaProvider::create("images/link001b.tga", NULL, true, true, 255, 0, 255);

	imgFaceRight = CL_TargaProvider::create("images/link002.tga", NULL, true, true, 255, 0, 255);
	imgFaceRight2 = CL_TargaProvider::create("images/link002a.tga", NULL, true, true, 255, 0, 255);
	imgFaceRight3 = CL_TargaProvider::create("images/link002b.tga", NULL, true, true, 255, 0, 255);

	imgFaceUp = CL_TargaProvider::create("images/link003.tga", NULL, true, true, 255, 0, 255);
	imgFaceUp2 = CL_TargaProvider::create("images/link003.tga", NULL, true, true, 255, 0, 255);
	imgFaceUp3 = CL_TargaProvider::create("images/link003.tga", NULL, true, true, 255, 0, 255);

	imgFaceDown = CL_TargaProvider::create("images/link004.tga", NULL, true, true, 255, 0, 255);
	imgFaceDown2 = CL_TargaProvider::create("images/link004.tga", NULL, true, true, 255, 0, 255);
	imgFaceDown3 = CL_TargaProvider::create("images/link004.tga", NULL, true, true, 255, 0, 255);

	imgCurrent = imgFaceUp;
	iDirection = UP;
	isStanding = true;

	theSword = new Sword();

}

CL_Surface *Player::getPlayerImage() {
	return imgCurrent;


}



void Player::initalize() {
	iPositionX = 112;
	iPositionY = 72;
}

int Player::getXPosition() {
	return iPositionX;
}

int Player::getYPosition() {
	return iPositionY;
}

void Player::stand() {
	isStanding = true;
	if ((imgCurrent == imgFaceLeft2) ||
		(imgCurrent == imgFaceLeft3)) {
		imgCurrent = imgFaceLeft;

	} else if ((imgCurrent == imgFaceRight2) ||
				(imgCurrent == imgFaceRight3)) {
		imgCurrent = imgFaceRight;

	} else if ((imgCurrent == imgFaceUp2) ||
				(imgCurrent == imgFaceUp3)) {
		imgCurrent = imgFaceUp;

	} else if ((imgCurrent == imgFaceDown2) ||
				(imgCurrent == imgFaceDown3)) {
		imgCurrent = imgFaceDown;
	}

}

void Player::moveLeft() {
	isStanding = false;
	iPositionX -= 2;

	if (imgCurrent == imgFaceLeft) {
		imgCurrent = imgFaceLeft2;
	} else if (imgCurrent == imgFaceLeft2) {
		imgCurrent = imgFaceLeft3;
	} else if (imgCurrent == imgFaceLeft3) {
		imgCurrent = imgFaceLeft;
	} else {
		imgCurrent = imgFaceLeft;
	}

	iDirection = LEFT;

	if (theSword->isShooting() == false) {
		theSword->setPosition(iPositionX, iPositionY);
	}
}

void Player::moveRight() {
	isStanding = false;
	iPositionX += 2;

	if (imgCurrent == imgFaceRight) {
		imgCurrent = imgFaceRight2;
	} else if (imgCurrent == imgFaceRight2) {
		imgCurrent = imgFaceRight3;
	} else if (imgCurrent == imgFaceRight3) {
		imgCurrent = imgFaceRight;
	} else {
		imgCurrent = imgFaceRight;
	}
	
	iDirection = RIGHT;
	if (theSword->isShooting() == false) {
		theSword->setPosition(iPositionX, iPositionY);
	}

}

void Player::moveUp() {
	isStanding = false;
	iPositionY -= 2;
	imgCurrent = imgFaceUp;
	iDirection = UP;

	if (theSword->isShooting() == false) {
		theSword->setPosition(iPositionX, iPositionY);
	}
}

void Player::moveDown() {
	isStanding = false;
	iPositionY += 2;
	imgCurrent = imgFaceDown;
	iDirection = DOWN;

	if (theSword->isShooting() == false) {
		theSword->setPosition(iPositionX, iPositionY);
	}

}

void Player::setPosition(int x, int y) {
	iPositionX = x;
	iPositionY = y;
}

Player::~Player() {
    delete imgFaceLeft;
	delete imgFaceRight;
    delete imgFaceUp;
    delete imgFaceDown;
}

int Player::getBoundaryX1() {
	return iPositionX;
}

int Player::getBoundaryX2() {
	return iPositionX + 16;
}


int Player::getBoundaryY1() {
	return iPositionY;
}

int Player::getBoundaryY2() {
	return iPositionY + 8;
}

Sword *Player::getSword() {
	return theSword;
}

void Player::shoot() {

	if (theSword->isShooting() == false) {
		theSword->setPosition(iPositionX, iPositionY);

		if (iDirection == UP) {
			theSword->shoot(0, -2);
		} else if (iDirection == DOWN) {
			theSword->shoot(0, 2);
		} else if (iDirection == LEFT) {
			theSword->shoot(-2, 0);
		} else if (iDirection == RIGHT) {
			theSword->shoot(2, 0);
		}
	}

}
