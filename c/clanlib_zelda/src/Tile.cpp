#include "Tile.h"

Tile::Tile(char *strFileName, bool b) {
	isPassable = b;
	img = CL_TargaProvider::create(strFileName, NULL);
}

Tile::~Tile(void) {
	delete img;
}

CL_Surface *Tile::getImage() {
	return img;


}

bool Tile::getPassable() {
	return isPassable;
}
