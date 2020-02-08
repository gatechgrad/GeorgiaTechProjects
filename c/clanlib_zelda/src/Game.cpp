#include "Game.h"

#define PLAY_AREA_X 32
#define PLAY_AREA_Y 32



Game::Game(char ch) {
	int x, y;
	int iSize;
    CL_Canvas *canvas;
    CL_Surface *canvas_surf;
	int iMapRow, iMapCol;
	int iCollideValue;
	int i;
	int iTime;

	CL_ResourceManager* res_manager;
	CL_SoundBuffer_Session sbs;

	res_manager = new CL_ResourceManager("./zelda.scr", false);
	CL_SoundBuffer *sb = CL_SoundBuffer::load("Samples/SFX_Sword", res_manager);



		x = 128; 
		y = 64;
		iSize = 2;

		
			iMapRow = 7;
			iMapCol = 7;
			theRoom = new Room();
			theRoom->getRoom(iMapRow, iMapCol);

			theMonsters = new Monster();


			// Set a videomode - 640x480x16bpp
			// false means that this program should not run full-screen
			//CL_Display::set_videomode(320, 240, 16, true);
			CL_Display::set_videomode(320, 240, 16, false);

			canvas = new CL_Canvas(200, 200);
            canvas_surf = CL_Surface::create(canvas, true);

			thePlayer = new Player();
			thePlayer->initalize();



			images[0] = new Tile("images/grass001.tga", true);
			images[1] = new Tile("images/grass001.tga", true);
			images[2] = new Tile("images/grass005.tga", true);
			images[3] = new Tile("images/grass006.tga", true);
			images[4] = new Tile("images/wall001.tga", true);
			images[5] = new Tile("images/rock001.tga", true);
			images[6] = new Tile("images/rock002.tga", true);
			images[7] = new Tile("images/rock003.tga", true);
			images[8] = new Tile("images/rock004.tga", true);
			images[9] = new Tile("images/water001.tga", true);
			images[10] = new Tile("images/water002.tga", true);
			images[11] = new Tile("images/water003.tga", true);
			images[12] = new Tile("images/bank001.tga", true);
			images[13] = new Tile("images/bank002.tga", true);
			images[14] = new Tile("images/bank003.tga", true);
			images[15] = new Tile("images/bank004.tga", true);
			images[16] = new Tile("images/bank005.tga", true);
			images[17] = new Tile("images/bank006.tga", true);
			images[18] = new Tile("images/bush001.tga", true);
			images[19] = new Tile("images/bush002.tga", true);
			images[20] = new Tile("images/bush003.tga", true);
			images[21] = new Tile("images/bush004.tga", true);

			images[22] = new Tile("images/cave001.tga", true);
			images[23] = new Tile("images/cave002.tga", true);
			images[24] = new Tile("images/cave003.tga", true);
			images[25] = new Tile("images/cave004.tga", true);
			images[26] = new Tile("images/cave005.tga", true);
			images[27] = new Tile("images/cave006.tga", true);
			images[28] = new Tile("images/cave007.tga", true);
			images[29] = new Tile("images/cave008.tga", true);
			images[30] = new Tile("images/cave009.tga", true);
			images[31] = new Tile("images/cave010.tga", true);
			images[32] = new Tile("images/cave011.tga", true);
			images[33] = new Tile("images/cave012.tga", true);
			images[34] = new Tile("images/cave013.tga", true);
			images[35] = new Tile("images/cave014.tga", true);
			images[36] = new Tile("images/cave015.tga", true);
			images[37] = new Tile("images/cave016.tga", true);

			images[38] = new Tile("images/wallfront001.tga", true);
			images[39] = new Tile("images/wallfront002.tga", true);
			images[40] = new Tile("images/wallfront003.tga", true);
			images[41] = new Tile("images/wallfront004.tga", true);
			images[42] = new Tile("images/wallfront005.tga", true);
			images[43] = new Tile("images/wallfront006.tga", true);
			images[44] = new Tile("images/wallfront007.tga", true);
			images[45] = new Tile("images/wallfront008.tga", true);

			images[46] = new Tile("images/cavefloor001.tga", true);
			images[47] = new Tile("images/cavefloor002.tga", true);
			images[48] = new Tile("images/cavefloor003.tga", true);
			images[49] = new Tile("images/cavefloor004.tga", true);

			images[50] = new Tile("images/smtree001.tga", true);
			images[51] = new Tile("images/smtree002.tga", true);
			images[52] = new Tile("images/smtree003.tga", true);
			images[53] = new Tile("images/smtree004.tga", true);
			images[54] = new Tile("images/smtree005.tga", true);
			images[55] = new Tile("images/smtree006.tga", true);
			images[56] = new Tile("images/smtree007.tga", true);
			images[57] = new Tile("images/smtree008.tga", true);
			images[58] = new Tile("images/smtree009.tga", true);
			images[59] = new Tile("images/smtree010.tga", true);
			images[60] = new Tile("images/smtree011.tga", true);
			images[61] = new Tile("images/smtree012.tga", true);
			images[62] = new Tile("images/smtree013.tga", true);
			images[63] = new Tile("images/smtree014.tga", true);
			images[64] = new Tile("images/smtree015.tga", true);
			images[65] = new Tile("images/smtree016.tga", true);

			images[66] = new Tile("images/LVL001_floor001.tga", true);
			images[67] = new Tile("images/LVL001_floor002.tga", true);
			images[68] = new Tile("images/LVL001_floor003.tga", true);
			images[69] = new Tile("images/LVL001_floor004.tga", true);

			images[70] = new Tile("images/LVL001_wall001.tga", true);

			images[71] = new Tile("images/LVL001_block001.tga", true);
			images[72] = new Tile("images/LVL001_block002.tga", true);
			images[73] = new Tile("images/LVL001_block003.tga", true);
			images[74] = new Tile("images/LVL001_block004.tga", true);

			images[75] = new Tile("images/lampoff001.tga", true);
			images[76] = new Tile("images/lampoff002.tga", true);
			images[77] = new Tile("images/lampoff003.tga", true);
			images[78] = new Tile("images/lampoff004.tga", true);


			// Run until someone presses escape
			while (!CL_Keyboard::get_keycode(CL_KEY_ESCAPE)) {
				iTime = clock();

				iCollideValue = 0;

				if (CL_Keyboard::get_keycode(CL_KEY_LEFT)) {
					iCollideValue = theRoom->hasCollided(thePlayer->getBoundaryX1(), thePlayer->getBoundaryX2(),
						thePlayer->getBoundaryY1(), thePlayer->getBoundaryY2(), -2, 0);
					if (iCollideValue == 0) {
						thePlayer->moveLeft();
					}
				} else if (CL_Keyboard::get_keycode(CL_KEY_RIGHT)) {
					iCollideValue = theRoom->hasCollided(thePlayer->getBoundaryX1(), thePlayer->getBoundaryX2(),
						thePlayer->getBoundaryY1(), thePlayer->getBoundaryY2(), 2, 0);
					if (iCollideValue == 0) {
						thePlayer->moveRight();
					}
				} else if (CL_Keyboard::get_keycode(CL_KEY_UP)) {
					iCollideValue = theRoom->hasCollided(thePlayer->getBoundaryX1(), thePlayer->getBoundaryX2(),
						thePlayer->getBoundaryY1(), thePlayer->getBoundaryY2(), 0, -2);
					if (iCollideValue == 0) {
						thePlayer->moveUp();
					}

				} else if (CL_Keyboard::get_keycode(CL_KEY_DOWN)) {
					iCollideValue = theRoom->hasCollided(thePlayer->getBoundaryX1(), thePlayer->getBoundaryX2(),
						thePlayer->getBoundaryY1(), thePlayer->getBoundaryY2(), 0, 2);
					if (iCollideValue == 0) {
						thePlayer->moveDown();
					}
				} else {
					thePlayer->stand();
				}

				if ((CL_Keyboard::get_keycode(CL_KEY_LCTRL)) && 
					(thePlayer->getSword()->isShooting() == false)) {
					thePlayer->shoot();
/*** TEST ***/
try {
	sbs = sb->play();
} catch (CL_Error e) { 
	cout << "Error" << endl;
}
/************/


					//sfx_sword->prepare(false, NULL);
					//sfx_sword->play(false, NULL);
				} else {
					thePlayer->getSword()->shootMove();
				}


				if (iCollideValue > 1) {
					switch(iCollideValue) {
						case 2:
							theRoom->setLevel(0);
							iMapRow = 7;
							iMapCol = 7;
							theRoom->getRoom(7, 7);
							thePlayer->setPosition(116, 64);
							break;

						case 3:
							theRoom->setLevel(1);
							iMapRow = 5;
							iMapCol = 2;
							theRoom->getRoom(5, 2);
							thePlayer->setPosition(116, 120);
							break;

						case 12:
							theRoom->setLevel(10);
							iMapRow = 7;
							iMapCol = 7;
							theRoom->getRoom(7, 7);
							thePlayer->setPosition(116, 120);
							break;

					}
				}

				//move to another room
				if (thePlayer->getBoundaryY2() > 166) {
					iMapRow++;
					theRoom->getRoom(iMapRow, iMapCol);
					thePlayer->setPosition(thePlayer->getXPosition(), 8);
					theMonsters->getMonsters(iMapRow, iMapCol);
				} else if (thePlayer->getBoundaryY1() < 2) {
					iMapRow--;
					theRoom->getRoom(iMapRow, iMapCol);
					thePlayer->setPosition(thePlayer->getXPosition(), 134);
					theMonsters->getMonsters(iMapRow, iMapCol);
				} else if (thePlayer->getBoundaryX2() > 254) {
					iMapCol++;
					theRoom->getRoom(iMapRow, iMapCol);
					thePlayer->setPosition(8, thePlayer->getYPosition());
					theMonsters->getMonsters(iMapRow, iMapCol);
				} else if (thePlayer->getBoundaryX1() < 2) {
					iMapCol--;
					theRoom->getRoom(iMapRow, iMapCol);
					thePlayer->setPosition(232, thePlayer->getYPosition());
					theMonsters->getMonsters(iMapRow, iMapCol);
				}

				//move monsters
				for (i = 0; i < MAX_MONSTERS; i++) {
					if (theMonsters->getAlive(i) == true) {

						iCollideValue = theRoom->hasCollided(theMonsters->getBoundaryX1(i), theMonsters->getBoundaryX2(i),
							theMonsters->getBoundaryY1(i), theMonsters->getBoundaryY2(i), theMonsters->getVelocityX(i), theMonsters->getVelocityY(i));

						if (theMonsters->getBoundaryX1(i) + theMonsters->getVelocityX(i) < 2) {
							iCollideValue = -1;
						}

						if (theMonsters->getBoundaryX2(i) + theMonsters->getVelocityX(i) > 254) {
							iCollideValue = -1;
						}

						if  (theMonsters->getBoundaryY1(i) + theMonsters->getVelocityY(i) < 2) {
							iCollideValue = -1;
						}

						if (theMonsters->getBoundaryY2(i) + theMonsters->getVelocityY(i) > 166) {
							iCollideValue = -1;
						}

						if (iCollideValue == 0) {
							theMonsters->move(i);
						} else {
							theMonsters->changeDirection(i);
						}

						if (thePlayer->getSword()->isShooting() &&
							thePlayer->getSword()->hasCollided(theMonsters->getBoundaryX1(i),
							theMonsters->getBoundaryX2(i), theMonsters->getBoundaryY1(i),
							theMonsters->getBoundaryY2(i))) {
							
							theMonsters->addDamage(i, 1);
						}
					}
				}


				update();

				CL_Display::flip_display();
				CL_System::keep_alive();

				//add delay 
				while ((clock() - iTime) < 15) {
					//sleep();
				}
			
			}


			//Clean-up and remove the logo from memory
			delete thePlayer;
			delete theRoom;
}

void Game::update() {
	int i, j;

	
	CL_Display::clear_display(0.0f, 0.0f, 0.0f, 1.0f);


				for (i = 0; i < Room::iRows; i++) {
					for (j = 0; j < Room::iCols; j++) {
						if ((images[theRoom->currentRoomImagesArray[i][j]])->getImage() != NULL) {
							(images[theRoom->currentRoomImagesArray[i][j]])->getImage()->put_screen((j * 8) + PLAY_AREA_X, (i * 8) + PLAY_AREA_Y);
						}
					}
				}

				(thePlayer->getPlayerImage())->put_screen(thePlayer->getXPosition() - 2 + PLAY_AREA_X, thePlayer->getYPosition() - 16 + PLAY_AREA_Y);
				//CL_Display::fill_rect(thePlayer->getBoundaryX1(), thePlayer->getBoundaryY1(),thePlayer->getBoundaryX2(), thePlayer->getBoundaryY2(), 1, 0, 0, 0.5);
				//CL_Display::fill_rect(thePlayer->getSword()->getBoundaryX1(), thePlayer->getSword()->getBoundaryY1(), thePlayer->getSword()->getBoundaryX2(), thePlayer->getSword()->getBoundaryY2(), 0, 0.5, 0.5, 1.0);
				if (thePlayer->getSword()->isShooting()) {
					(thePlayer->getSword()->getSwordImage())->put_screen(thePlayer->getSword()->getBoundaryX1() + PLAY_AREA_X, thePlayer->getSword()->getBoundaryY1() + PLAY_AREA_Y);
				}


				//draw monsters
				for (i = 0; i < MAX_MONSTERS; i++) {
					if (theMonsters->getAlive(i) == true) {
						/*
						CL_Display::fill_rect(theMonsters->getBoundaryX1(i) + PLAY_AREA_X, 
												theMonsters->getBoundaryY1(i) + PLAY_AREA_Y,
												theMonsters->getBoundaryX2(i) + PLAY_AREA_X,
												theMonsters->getBoundaryY2(i) + PLAY_AREA_Y,
												0, 0.5, 0.5, 1.0);
						*/
						(theMonsters->getMonsterImage(i))->put_screen(theMonsters->getBoundaryX1(i) + PLAY_AREA_X, 
											theMonsters->getBoundaryY1(i) + PLAY_AREA_Y);
					}
				}


				//Draw the menu
				CL_Display::fill_rect(32, 0, 288, PLAY_AREA_Y, 0, 0, 0, 1.0);
//				CL_Font *fnt = CL_Font::create(new CL_Font_Description(new CL_InputSource()));
//				fnt->print_left(32, 16, "Zelda++ Demo v0.002; Levi D. Smith; September 8, 2002");
//				CL_Display::fill_rect(32, 0, 96, 32, 0.5, 0.5, 0.5, 1.0);
//				CL_Display::fill_rect(32 + (iMapRow * 4), 0 + (iMapCol * 4), 36 + (iMapRow * 4),  4 + (iMapCol * 4), 0.0, 1.0, 0.0, 1.0);



}


Game::~Game(void) {
}
