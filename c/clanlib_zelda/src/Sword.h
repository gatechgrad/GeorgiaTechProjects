#include <ClanLib/core.h>
#include <ClanLib/application.h>
#include <ClanLib/display.h>

class Sword {
	public:
	
		Sword();
		int getBoundaryX1();
		int getBoundaryX2();
		int getBoundaryY1();
		int getBoundaryY2();

		void setPosition(int x, int y);
		bool isStabbing();
		bool isShooting();
		void shootMove();
		void shoot(int x, int y);
		CL_Surface *getSwordImage();

		int hasCollided(int x1, int x2, int y1, int y2);

	private:
		int iPower;
		int iHeight;
		int iWidth;
		int iPositionX;
		int iPositionY;
		int iShootVelocityX;
		int iShootVelocityY;
		bool bShooting;
		bool bStabbing;

		CL_Surface *imgSwordUp;
		CL_Surface *imgSwordDown;
		CL_Surface *imgSwordLeft;
		CL_Surface *imgSwordRight;
		CL_Surface *imgCurrent;
};

