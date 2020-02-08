#pragma once

#include <ClanLib/core.h>
#include <ClanLib/application.h>
#include <ClanLib/display.h>
#include <ClanLib/sound.h>

#include "Player.h"
#include "Tile.h"
#include "Room.h"


class Game {
public:
	Game(char ch);
	~Game(void);
	void update();

	static const int SCREEN_OFFSET = 54;
private:
	Tile *images[79];
	Player *thePlayer;
	Room *theRoom;
	Monster *theMonsters;


};

