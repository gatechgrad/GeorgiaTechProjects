#ifndef VIRTUALSYSOP_H
#define VIRTUALSYSOP_H

#include "global.h"
#include "sysop.h"

const int SCREEN_LINE_MAX = 23;

class virtualsysop {
  public:
    virtualsysop(string strNewName);
  private:
    void helpMenu();
    void newGame(string strNewName);
    void mainMenu();
    void topTen();
    void invalidCommand();
    void hangUp();
    void load(string strNewName);
    void writeFile();
    void printInstructions();
    ansicolor *ColorControl;
    sysop *CurrentPlayer;
    //vector<sysop *>  PlayerList;
};

#endif
