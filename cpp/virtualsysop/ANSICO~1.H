#ifndef ANSICOLOR_H
#define ANSICOLOR_H

#include "global.h"
#include <cstdio> //include classic c stdio.h


/* ANSI X3.64 color code definitions */
const int A_BLACK = 0;
const int A_RED = 1;
const int A_GREEN = 2;
const int A_YELLOW = 3;
const int A_BLUE = 4;
const int A_MAGENTA = 5;
const int A_CYAN = 6;
const int A_WHITE = 7;
const int A_CLEAR = 9;

const int FG = 30;   // foreground 
const int BKG = 40;  // background
const int A_BLINK = 5; // blinking code
const int BRIGHT = 1;
const int DARK = 0;

const int ESC = 27; // ASCII number value
                    // for escape

class ansicolor {
  public:
    ansicolor(bool useColor);
    void setColor(int iLevel, int iColor);
    void setColor(int iBright, int iLevel, int iColor);
    void setColorBlink(int iLevel, int iColor);
    void setColorBlink(int iAttribute, int iLevel, int iColor);
    void clearScreen();
    void cursorHome();
    void cursorLeft(int iTimes);
    void cursorRight(int iTimes);
    void cursorUp(int iTimes);
    void cursorDown(int iTimes);
    void cursorGoto(int x, int y);
    void resetColor();
    char read();
//  private:
    bool bUseColor;
};

#endif
