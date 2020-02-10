#include "ansicolor.h"

ansicolor::ansicolor(bool useColor) {
  bUseColor = useColor;
}  

void ansicolor::setColor(int iBright, int iLevel, int iColor){

  if (iBright == BRIGHT) {
    printf("%c[%dm", ESC, (iLevel + A_CLEAR));  
    printf("%c[%d;%dm", ESC, iBright, (iLevel + iColor));  
  } else if (iBright == DARK) {

    //printf("%c[%dm", ESC, (DARK + A_BLACK));
    //printf("%c[%dm", ESC, (BKG + A_BLACK));

    printf("%c[%dm", ESC, (0));
    printf("%c[%dm", ESC, (BKG + A_BLACK));

    printf("%c[%dm", ESC, (iLevel + iColor));  
  }
}

void ansicolor::setColorBlink(int iLevel, int iColor){
  printf("%c[%d;%dm", ESC, A_BLINK, (iLevel + iColor));
}

void ansicolor::setColorBlink(int iAttribute, int iLevel, int iColor){
  printf("%c[%d;%d;%dm", ESC, A_BLINK, iAttribute, (iLevel + iColor));
}

void ansicolor::clearScreen(){
  printf("%c[2J", ESC);
}

void ansicolor::cursorHome(){
  printf("%c[0;0H", ESC);
}

void ansicolor::cursorLeft(int iTimes){
  printf("%c[%dD", ESC, iTimes);
}

void ansicolor::cursorRight(int iTimes){
  printf("%c[%dC", ESC, iTimes);
}

void ansicolor::cursorUp(int iTimes){
  printf("%c[%dA", ESC, iTimes);
}

void ansicolor::cursorDown(int iTimes){
  printf("%c[%dB", ESC, iTimes);
}

void ansicolor::cursorGoto(int x, int y) {
  printf("%c[%d;%dH", ESC, x, y);
}

void ansicolor::resetColor() {
  printf("%c[0m", ESC);
}
