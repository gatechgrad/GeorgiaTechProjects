#include <stdio.h>
#include <stdlib.h>
#include <allegro.h>

int iTime;

void timeHandler();

/**
  * setupAllegro
  **/
void setupAllegro() {
  allegro_init();
  install_keyboard();
  install_timer();

/*
  set_color_depth(16);
  set_gfx_mode(GFX_AUTODETECT, 640, 480, 0, 0);

  text_mode(-1);
*/

}

/**
  * timeHandler
  **/
void timeHandler() {
  iTime--;
  printf("Time: %d", iTime);
}
END_OF_FUNCTION(timeHandler);


/**
  * loadGame
  **/
void loadGame() {
  LOCK_VARIABLE(iTime);
  LOCK_FUNCTION(timeHandler);
}




/**
  * main
  **/
int main(void) {
  setupAllegro();
  loadGame();
  iTime = 500;


  install_int_ex(timeHandler, SECS_TO_TIMER(1));

  while(!keypressed()) {

  }


  return 0;
}
