//############################################################################
//# FILE Emulation.java
//# VERSION 0.94
//# DATE: 4/27/96
//# AUTHOR:  Bret Dahlgren (bret@lm.com)
//#
//# Ver. 0.94  4/27/96 Change to the scrollup and scrolldown calls
//#                  Had a need for scrolls within the screen for
//#                  VT100 compatability.  Scrolls now are not specific
//#                  for which rows to scroll.
//# Ver. 0.90  2/29/96 Initial release.
//#
//# Copyright (c) 1996 Bret G. Dahlgren, All Rights Reserved.
//############################################################################
import java.awt.*;
import java.awt.image.*;

//############################################################################
//# CLASS: Emulation
//#   The Emulation class is used as a base class for other classes that will
//#   manipulate the incoming data to display.  This class takes care of the
//#   actual displaying of the data according to coordinate position and colors
//#
//# USAGE NOTE:  This class is abstract.  Create subclasses from it.
//#
//# DESIGN NOTE:  I know some of the public methods and variables should not be
//#   for good OO code.  If I have time I'll try to change this.
//# 
//############################################################################
//# 
//# STATIC VARIABLE: owner
//#   Used to provide this class an easy way to retrieve TelnetClient values for
//#   images.  Probably could be better designed.
//# 
//# STATIC VARIABLES: width, height, char_w, char_h
//#   Canvas area to work with and character sizes.
//# 
//# STATIC METHOD: initClass
//#   method used to initialize this classes static variables
//#   *** Call this before Using this Class Type ***
//# 
//# CONSTRUCTOR:
//#   method to set default colors and coordinate position.
//# 
//# METHOD: clearcoord
//#   method to clear out a section of the canvas graphically.
//#
//# METHOD: scrolldown
//#   method to move the graphic image down a row between ScrollTop
//#   and ScrollBottom.
//#
//# METHOD: scrollup
//#   method to move the graphic image up a row between ScrollTop
//#   and ScrollBottom.
//#
//# METHOD: clearscreen
//#   method to clear the graphic canvas with the background color.
//#
//# METHOD: displaych
//#   method to draw a character on the canvas.
//#
//# METHOD: displaybytes
//#   method to be used in the subclass.  Nothing done here.
//#
//# METHOD: transmitch
//#   method to transmit char over the pipe.  Overridden to
//#   map to emulation characters.
//#
//############################################################################

public abstract class Emulation {


  protected static TelnetClient owner = null;
  protected static int width = 0;
  protected static int height = 0;
  protected static int char_w = 0;
  protected static int char_h = 0;



  static public void initClass (TelnetClient parent) {

    owner = parent;
    width = owner.width;
    height = owner.height;
    char_w = owner.w;
    char_h = owner.h;
    }


  public int column;
  public int row;
  public Color foreground;
  public Color background;

  public Emulation() {
  
    foreground = Color.white;
    background = Color.black;
    row = 0;
    column = 0;
    }

  public void clearcoord(int row1, int col1, int row2, int col2) {
  
    owner.bgr.setColor(background);

    if ( row1 == row2) {
   
      owner.bgr.fillRect(col1 * owner.chw, ( row1 )*owner.chh, (col2 - col1 + 1)*owner.chw, owner.chh);
      return;
      }

    owner.bgr.fillRect(col1 * owner.chw, ( row1 )*owner.chh, (char_w - col1)*owner.chw, owner.chh);

    if ( (row2-row1) > 1) {
      for (int i = row1+1; i < row2; i++){
          
        owner.bgr.fillRect(0, ( i )*owner.chh, char_w*owner.chw, owner.chh);
        }
      }

    owner.bgr.fillRect(0, ( row2 )*owner.chh, ( col2 + 1 )*owner.chw, owner.chh);
    }



  public void scrolldown(int ScrollTop, int ScrollBottom) {

    owner.bgr.copyArea(0, (ScrollTop) * owner.chh, char_w*owner.chw,
                          (ScrollBottom - ScrollTop)*owner.chh, 0, owner.chh);

    owner.bgr.setColor(background);

    owner.bgr.fillRect(0, (ScrollTop) * owner.chh, char_w*owner.chw, owner.chh);
    }

  public void scrollup(int ScrollTop, int ScrollBottom ) {

    owner.bgr.copyArea(0, (ScrollTop+1) * owner.chh,
                 char_w*owner.chw, (ScrollBottom - ScrollTop)*owner.chh, 0, -owner.chh);
    owner.bgr.setColor(background);

    owner.bgr.fillRect(0, (ScrollBottom)*owner.chh, char_w*owner.chw, owner.chh);

    }

  public void clearscreen() {

    owner.bgr.setColor(background);
    owner.bgr.fillRect(0,0,width,height);
    }

  public void displaych(char c) {

    owner.bgr.setColor(background);
    owner.bgr.fillRect(column*owner.chw, row*owner.chh, owner.chw, owner.chh);
    owner.bgr.setColor(foreground);
    owner.bgr.drawString(String.valueOf(c), column*owner.chw, (row+1)*owner.chh-owner.chd);

    }

  public void displaybytes(byte b[], int len) {
    }

  public void transmitch(char ch)
    {
    owner.transmitch(ch);
    }


  }
