#include <stdio.h>
#include <allegro.h>
#define PIC_TITLE "title.pcx"
#define PIC_BUTTONBAR "buttonbar.pcx"
#define PIC_FIELD "field.pcx"
#define PIC_COMMAND_SOFTWARE_LOGO "commandsoftwarelogo.pcx"
#define NUM_INSTRUMENTS 6
#define BUTTONS_PER_ROW 3


/** GLOBAL VARIABLES **/
BITMAP *bm_buttonbar;
BITMAP *bm_field;
BITMAP *buffer;
BITMAP *bm_instruments[NUM_INSTRUMENTS];

 
/**
  * displayLogo - display the Command Software logo
  **/
void displayLogo() {
  BITMAP *bm_logo;
  PALLETE my_pallete;
  SAMPLE *wav_logo;

  wav_logo = load_wav("saytrademark.wav");

  bm_logo = load_pcx(PIC_COMMAND_SOFTWARE_LOGO, my_pallete);
  buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(buffer);
  draw_sprite(buffer, bm_logo, 0, 0);

  blit(buffer, screen, 0, 0, (SCREEN_W - 450) / 2, (SCREEN_H - 140) / 2,  450, 140);
  play_sample(wav_logo, 255, 128, 1000, FALSE);
  rest(3000);


  destroy_bitmap(bm_logo);
  destroy_bitmap(buffer);



}


/**
  * displayTitleScreen - displays the title screen
  */
void displayTitleScreen() {
  BITMAP *bm_title;
  PALLETE my_pallete;
  MIDI *mid_title_music;

  bm_title = load_pcx(PIC_TITLE, my_pallete);

  buffer = create_bitmap(SCREEN_W, SCREEN_H);
  clear(buffer);
  draw_sprite(buffer, bm_title, 0, 0);

  blit(buffer, screen, 0, 0, 0, 0, 640, 480);

  destroy_bitmap(bm_title);
  destroy_bitmap(buffer);

  set_volume(255, 255);

  mid_title_music = load_midi("title.mid");
  play_midi(mid_title_music, TRUE);

  readkey();
  destroy_midi(mid_title_music);


}


/**
  * setupAllegro - sets up the Allegro library
  **/
void setupAllegro() {
  allegro_init();
  install_keyboard();
  install_timer();

  set_color_depth(16);
  set_gfx_mode(GFX_AUTODETECT, 640, 480, 0, 0);

  if (install_sound(DIGI_AUTODETECT, MIDI_AUTODETECT, "band") != 0) {
      printf("Error initializing sound system\n%s\n", allegro_error);
  }


}

/**
  * main
  */
int main(void) {

  char *strBandName;
  setupAllegro();
  displayLogo();
  displayTitleScreen();

  destroy_bitmap(bm_field);
  destroy_bitmap(bm_buttonbar);
  destroy_bitmap(buffer);


  return 0;
}
