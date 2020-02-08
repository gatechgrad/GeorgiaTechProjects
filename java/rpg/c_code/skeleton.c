#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <time.h>

#ifdef DJGPP
    #include <conio.h>
#endif

#include "allegro.h"
#include "skeleton.h"

typedef struct DIRTY_LIST {
    int count;
    RECT rect[2*(MAX_ALIENS+MAX_STARS+4)];
} DIRTY_LIST;

DIRTY_LIST dirty, old_dirty;


/**
 * adds an item to the dirty list
 */
void add_to_list(DIRTY_LIST *list, int x, int y, int w, int h) {
   list->rect[list->count].x = x;
   list->rect[list->count].y = y;
   list->rect[list->count].w = w;
   list->rect[list->count].h = h;
   list->count++; 
}



/**
 * draws the screen
 */
void draw_screen()
{
    int c;
    int i, j;
    int x, y;
    BITMAP *bmp;
    RLE_SPRITE *spr;
    char *animation_type_str;

    /* for dirty rectangle animation we draw onto the memory bitmap, but 
     * we can use information saved during the last draw operation to
     * only clear the areas that have something on them.
     */
    animation_type_str = "dirty rectangles";
    bmp = s;

    for (c=0; c<dirty.count; c++) {
        if ((dirty.rect[c].w == 1) && (dirty.rect[c].h == 1))
            putpixel(bmp, dirty.rect[c].x, dirty.rect[c].y, 0);
        else
            rectfill(bmp, dirty.rect[c].x, dirty.rect[c].y, 
                     dirty.rect[c].x + dirty.rect[c].w, 
                     dirty.rect[c].y+dirty.rect[c].h, 0);
    }

    old_dirty = dirty;
    dirty.count = 0;

    
    /* add the rectangle */
    add_to_list(&dirty, x-spr->w/2, SCREEN_H-42-spr->h/2, spr->w, spr->h);

    /* draw the aliens */
    for (c=0; c<alien_count; c++) {
        x = alien[c].x;
        y = alien[c].y;

        if (alien[c].state >= EXPLODE_FLAG) {
            spr = explosion[alien[c].state-EXPLODE_FLAG];
        } else {
            switch (c%3) {
                case 0: i = ASTA01; break;
                case 1: i = ASTB01; break;
                case 2: i = ASTC01; break;
                default: i = 0; break;
            }
            j = (retrace_count/(6-(c&3))+c)%15;
            if (c&1)
                spr = (RLE_SPRITE *)data[i+14-j].dat;
            else
                spr = (RLE_SPRITE *)data[i+j].dat;
        }

        draw_rle_sprite(bmp, spr, x-spr->w/2, y-spr->h/2);

        if (animation_type == DIRTY_RECTANGLE)
            add_to_list(&dirty, x-spr->h/2, y-spr->h/2, spr->w, spr->h);
    }

    /* draw the bullet */
    if (bullet_flag) {
        x = bullet_x;
        y = bullet_y;

        spr = (RLE_SPRITE *)data[ROCKET].dat;
        draw_rle_sprite(bmp, spr, x-spr->w/2, y-spr->h/2);

        if (animation_type == DIRTY_RECTANGLE)
            add_to_list(&dirty, x-spr->w/2, y-spr->h/2, spr->w, spr->h);
    }

    /* draw the score and fps information */
    if (fps)
        sprintf(score_buf, "Score: %ld - (%s, %ld fps)", (long)score, 
                animation_type_str, (long)fps);
    else
        sprintf(score_buf, "Score: %ld", (long)score);

    textout(bmp, font, score_buf, 0, 0, 7);

    if (animation_type == DIRTY_RECTANGLE)
        add_to_list(&dirty, 0, 0, text_length(font, score_buf), 8);

    if (animation_type == DOUBLE_BUFFER) {
        /* when double buffering, just copy the memory bitmap to the screen */
        blit(s, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);
    } else if ((animation_type == PAGE_FLIP) || (animation_type == RETRACE_FLIP)) { 
        /* for page flipping we scroll to display the image */
        scroll_screen(0, (current_page==0) ? 0: SCREEN_H);
    } else if (animation_type == TRIPLE_BUFFER) {
        /* make sure the last flip request has actually happened */
        do {
        } while (poll_scroll());

        /* post a request to display the page we just drew */
        request_scroll(0, current_page * SCREEN_H);
    } else {
        /* for dirty rectangle animation, only copy the areas that changed */
        for (c=0; c<dirty.count; c++)
            add_to_list(&old_dirty, dirty.rect[c].x, dirty.rect[c].y, 
                        dirty.rect[c].w, dirty.rect[c].h);

        /* sorting the objects really cuts down on bank switching */
        if (!gfx_driver->linear)
            qsort(old_dirty.rect, old_dirty.count, sizeof(RECT), rect_cmp);

        for (c=0; c<old_dirty.count; c++) {
            if ((old_dirty.rect[c].w == 1) && (old_dirty.rect[c].h == 1))
                putpixel(screen, old_dirty.rect[c].x, old_dirty.rect[c].y, 
                         getpixel(bmp, old_dirty.rect[c].x, old_dirty.rect[c].y));
            else
                blit(bmp, screen, old_dirty.rect[c].x, old_dirty.rect[c].y, 
                     old_dirty.rect[c].x, old_dirty.rect[c].y, 
                     old_dirty.rect[c].w, old_dirty.rect[c].h);
        }
    }
}



int main(void) {

}
