#include <ClanLib/core.h>
#include <ClanLib/application.h>
#include <ClanLib/display.h>

#pragma once

class Tile {
	public:
		Tile(char *strFileName, bool b);
		~Tile(void);

		CL_Surface *getImage();
		bool getPassable();
	private:
		CL_Surface *img;
		bool isPassable;
		


};

