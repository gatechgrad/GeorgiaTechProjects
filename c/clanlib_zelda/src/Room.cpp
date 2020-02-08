#include "Room.h"


Room::Room(void) {
	//8, 10
	loadRoomArray(8, 10);
	iLevel = 0;

}

Room::~Room(void) {

}


int Room::hasCollided(int x1, int x2, int y1, int y2, int iNextX, int iNextY) {
	int iReturn;
	int iStart, jStart, iEnd, jEnd;
	int i, j;

	iReturn = 0;

	iStart = (y1 + iNextY) / 8;
	iEnd = (y2 + iNextY - 1) / 8;

	jStart = (x1 + iNextX) / 8;
	jEnd = (x2 + iNextX - 1) / 8;

	for (i = iStart; i < iEnd + 1; i++) {
		for (j = jStart; j < jEnd + 1; j++) {
			if (currentRoomCollisionArray[i][j] > iReturn) {
				iReturn = currentRoomCollisionArray[i][j];
			}
		}
	}
	return iReturn;
}


void Room::getRoom(int iRow, int iCol) {
	int i, j;

	for (i = 0; i < 21; i++) {
		for (j = 0; j < 32; j++) {
			switch(iLevel) {
				case 0:
					currentRoomImagesArray[i][j] = theWorldImagesArray[iRow][iCol][i][j];
					currentRoomCollisionArray[i][j] = theWorldCollisionArray[iRow][iCol][i][j];
					break;
				case 1:
					currentRoomImagesArray[i][j] = theLVL001ImagesArray[iRow][iCol][i][j];
					currentRoomCollisionArray[i][j] = theLVL001CollisionArray[iRow][iCol][i][j];
					break;
				case 10:
					currentRoomImagesArray[i][j] = theCaveImagesArray[iRow][iCol][i][j];
					currentRoomCollisionArray[i][j] = theCaveCollisionArray[iRow][iCol][i][j];
					break;


				default:
					currentRoomImagesArray[i][j] = theWorldImagesArray[iRow][iCol][i][j];
					currentRoomCollisionArray[i][j] = theWorldCollisionArray[iRow][iCol][i][j];
					break;
			}
		}
	}
}

void Room::setLevel(int i) {
	iLevel = i;
}



CL_Surface *Room::getRoomImage() {

	return NULL;
}




void Room::loadRoomArray(int iMapRow, int iMapCol) {
	ifstream fin;
	fin.open("zelda.xml", ios::in);
	char *str;
	char *delim = " ";
	char *strQuote;
	char *strValue;
	int i, j;
	int iLoadLevel;


	str = (char *) malloc(2048);

	//** Spefific to Game **
	int theRoomImages[21][32];
	int iRoomRow, iRoomCol;
	int theRoomCollision[21][32];
	int iCellRow, iCellCol;
	int iImage, iCollision;

	for (i = 0; i < 21; i++) {
		for (j = 0; j < 32; j++) {
			theRoomImages[i][j] = 1;
			theRoomCollision[i][j] = 0;
		}
	}


	while (fin) {
		fin >> str;
		
		//*** MAIN ***
		if (strstr(str, "<zelda>") != NULL) {
			cout << "Found Zelda" << endl;
		}

		if (strstr(str, "</zelda>") != NULL) {
			cout << "Found Zelda Close" << endl;
		}


		//*** LEVEL ***
		if (strstr(str, "<level") != NULL) {
			cout << "Found Level" << endl;
			//** ROWS **
			fin >> str;
			strValue = str + 6;
			strQuote = strchr(strValue, '"');
			strQuote[0] = '\0';

			cout << "Rows: " << strValue << endl;

			//** COLS **
			fin >> str;
			strValue = str + 6;
			strQuote = strchr(strValue, '"');
			strQuote[0] = '\0';

			cout << "Cols: " << strValue << endl;

			//** NAME **
			fin >> str;
			strValue = str + 6;
			strQuote = strchr(strValue, '"');
			strQuote[0] = '\0';
			cout << "Name: " << strValue << endl;

			if (strcmp(strValue, "Overworld") == 0) {
				iLoadLevel = 0;
			} else if (strcmp(strValue, "Level001") == 0) {
				iLoadLevel = 1;
			} else if (strcmp(strValue, "Cave") == 0) {
				iLoadLevel = 10;

			}

		}

		if (strstr(str, "</level>") != NULL) {
			cout << "Found Level Close" << endl;
		}

		//*** ROOM ***
		if (strstr(str, "<room") != NULL) {

			cout << "Found Room" << endl;
			//** ROWS **
			fin >> str;
			strValue = str + 5;
			strQuote = strchr(strValue, '"');
			strQuote[0] = '\0';

			//cout << "Row: " << strValue << endl;
			iRoomRow = atoi(strValue);

			//** COLS **
			fin >> str;
			strValue = str + 5;
			strQuote = strchr(strValue, '"');
			strQuote[0] = '\0';

			//cout << "Col: " << strValue << endl;
			iRoomCol = atoi(strValue);
		}

		if (strstr(str, "</room>") != NULL) {
			switch (iLoadLevel) {
				case 0:
					copyArray(theRoomImages, theWorldImagesArray[iRoomRow][iRoomCol]);
					copyArray(theRoomCollision, theWorldCollisionArray[iRoomRow][iRoomCol]);
					break;
				case 1:
					copyArray(theRoomImages, theLVL001ImagesArray[iRoomRow][iRoomCol]);
					copyArray(theRoomCollision, theLVL001CollisionArray[iRoomRow][iRoomCol]);
					break;
				case 10:
					copyArray(theRoomImages, theCaveImagesArray[iRoomRow][iRoomCol]);
					copyArray(theRoomCollision, theCaveCollisionArray[iRoomRow][iRoomCol]);
					break;

			}
			for (i = 0; i < 21; i++) {
				for (j = 0; j < 32; j++) {
					theRoomImages[i][j] = 1;
					theRoomCollision[i][j] = 0;
				}
			}
		}


		//*** CELL ***
		if (strstr(str, "<cell") != NULL) {
			//cout << "Found Cell" << endl;
			//** ROWS **
			fin >> str;
			strValue = str + 5;
			strQuote = strchr(strValue, '"');
			strQuote[0] = '\0';

//			cout << "Row: " << strValue << endl;
			iCellRow = atoi(strValue);

			//** COLS **
			fin >> str;
			strValue = str + 5;
			strQuote = strchr(strValue, '"');
			strQuote[0] = '\0';

//			cout << "Col: " << strValue << endl;
			iCellCol = atoi(strValue);
		}

		if (strstr(str, "</cell>") != NULL) {
			theRoomImages[iCellRow][iCellCol] = iImage;
			theRoomCollision[iCellRow][iCellCol] = iCollision;

		}

		//*** TEXTURE ***
		if (strstr(str, "<texture_id>") != NULL) {
			//cout << "Found Texture" << endl;
			//fin >> str;
			strValue = str + 12;
			strQuote = strchr(strValue, '<');
			strQuote[0] = '\0';

			//cout << "T: " << strValue << endl;
			iImage = atoi(strValue);
		}

		if (strstr(str, "</texture_id>") != NULL) {
			//cout << "Found Texture Close" << endl;
		}

		//*** COLLISION VALUE ***
		if (strstr(str, "<collision_value>") != NULL) {
			strValue = str + 17;
			strQuote = strchr(strValue, '<');
			strQuote[0] = '\0';

			//cout << "T: " << strValue << endl;
			iCollision = atoi(strValue);
		}

		if (strstr(str, "</collision>") != NULL) {
			//cout << "Found Collision Close" << endl;
		}


	}

	fin.close();
}


void Room::copyArray(int iArrayOne[21][32], int iArrayTwo[21][32]) {
	int i, j;
	for (i = 0; i < 21; i++) {
		for (j = 0; j < 32; j++) {
			  iArrayTwo[i][j] = iArrayOne[i][j];
		}
	}
}

