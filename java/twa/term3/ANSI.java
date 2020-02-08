//############################################################################
//# FILE ANSI.java
//# VERSION 0.94
//# DATE: 4/27/96
//# AUTHOR:  Bret Dahlgren (bret@lm.com)
//#
//# Ver. 0.94  4/27/96 Added Scroll region command.  Corrected
//#                    bug that didn't reset default colors with
//#                    <ESC>[0m.  Added save and restore cursor
//#                    command.
//# Ver. 0.93  4/12/96 bug when cursor positioning went out of
//#                    range it didn't verify and would cause an
//#                    array out-of-bounds exception.  Now it's
//#                    constrained to the array of the screen.
//# Ver. 0.92  3/21/96 fixes foreground/background mess for the
//#                    cursor.  Two Color arrays now store the
//#                    foreground and background colors so the 
//#                    cursor won't screw it up. Also fixes
//#                    cases of more than 1 default value like [H
//#                    getting bad values. Fixes arrow keys and
//#                    operates properly on LF and CR.
//# Ver. 0.91  3/19/96 Cleaned up Telnet commands from printing.
//#                    Changed PgUp to Ctrl-Y because ESC [V didn't work.
//#                    Now can print up to char 254.
//#                    Added skip to ? in escape which is a VT100 command
//#                    not used in this code.
//# Ver. 0.90  2/29/96 Initial release.
//#
//# Copyright (c) 1996 Bret G. Dahlgren, All Rights Reserved.
//############################################################################
import java.awt.*;
import java.awt.image.*;

//############################################################################
//# CLASS: ANSI
//#   The ANSI class is a subclass of abstract class Emulation and is loaded
//#   on the fly by TelnetClient.  This class reads ANSI escape codes for 
//#   color and coordinate position and display the telnet data appropriately.
//#   It also maps special keys like up arrow, PgDn, to appropriate ANSI
//#   codes.
//#
//############################################################################
//# CONSTRUCTOR: 
//#   calls the super constructor and then sets up the color table and the
//#   character array.
//#
//# METHOD: cleararray
//#   puts spaces in the character array.
//#
//# METHOD: clearscreen
//#   resets the coordinates,clears the character array and screen.
//#
//# METHOD: changecolor
//#   Handles the ESC [ m parameters for foreground, background, and inverse colors.
//#
//# METHOD: scrollup
//#   Clears out the bottom line of the screen.  Should move up the other characters too.
//#
//# METHOD: clearcoord
//#   Puts spaces in appropriate places in the character array.
//#
//# METHOD: translate
//#   takes the ESC codes and converts them into their commands.
//#
//# METHOD: displaych
//#   displays normal characters and scans for special characters.
//#
//# METHOD: displaybytes
//#   takes a stream of data and acts on it accordingly.
//#
//# METHOD: transmitch
//#   checks for special keys that need to be converted to ANSI codes
//#   i.e. PageUp, up-arrow, F1, etc. before they are sent out
//#
//############################################################################
public class ANSI extends Emulation {




private boolean escapeon = false;
private boolean firstchar = false;
private boolean bracket = false;
private int ScrollTop = 0;
private int ScrollBottom = 24;
private int Parameter;
private int Value[] = new int[10];
private Color color[] = new Color[16];
private int currentForeground ;
private int currentBackground ;
private int columnhold;
private int rowhold;
private boolean brighthold = false;
private boolean inversehold = false;
private Color Foresave;
private Color Backsave;
private boolean bright = false;
private boolean inverse = false;
private char CharArray[][];
private Color Foregnd[][];
private Color Backgnd[][];
private Color HoldFore;
private Color HoldBack;


public void initClass () {


}


public ANSI() {
  super();

  color[0] = Color.black;
  color[1] = Color.red;
  color[2] = Color.green;
  color[3] = Color.yellow;
  color[4] = Color.blue;
  color[5] = Color.magenta;
  color[6] = Color.cyan;
  color[7] = Color.lightGray;

  for (int i = 8; i < 15; i++)
    {
    color[i] = color[i-8].brighter();
    }

  color[15] = Color.white;

  currentForeground = 7;
  currentBackground = 0;

  CharArray = new char[char_w][char_h];
  Foregnd = new Color[char_w][char_h];
  Backgnd = new Color[char_w][char_h];
  cleararray();

  }

public void cleararray() {

  for (int i = 0; i < char_w; i++)
    for (int j = 0; j < char_h; j++)
       {
       CharArray[i][j] = ' ';
       Foregnd[i][j] = foreground;
       Backgnd[i][j] = background;
       }

  }

public void clearscreen() {

  row = 0;
  column = 0;
  cleararray();
  super.clearscreen();

  }

public void changecolor(){

  for (int i = 0; i <= Parameter; i++)
    {
    if (Value[i] >= 40)
      {
      currentBackground = Value[i] - 40;

      background = color[currentBackground];
      }
    else if (Value[i] >= 30)
      {
      currentForeground = Value[i] - 30;
      if ( bright == true)
        foreground = color[currentForeground + 8];
      else
        foreground = color[currentForeground];
      }
    else
      switch (Value[i])
        {

        case 0:
               currentForeground = 7;
               currentBackground = 0;
               foreground = color[currentForeground];
               background = color[currentBackground];
               bright = false;
               inverse = false;
               break;

        case 1:
              bright = true;
              foreground = color[currentForeground + 8 ];
              break;

        case 7:
              inverse = true; 
              foreground = color[currentBackground];
              background = color[currentForeground];
              break;

        case 22:
              bright = false;
              foreground = color[currentForeground];
              break;

        case 27:
               background = color[currentBackground];
               inverse = false;
               if (bright == true)
                 foreground = color[currentForeground+8];
               else   
                 foreground = color[currentForeground];
               break;
               
        }
    }
  }

public void scrollup(int top, int bottom)
  {
  for (int i = 0; i < char_w; i++)
    {
    CharArray[i][bottom] = ' ';
    Foregnd[i][bottom] = foreground;
    Backgnd[i][bottom] = background;
    }

   super.scrollup(top, bottom);

  }

public void clearcoord(int row1, int col1, int row2, int col2) {

  int i,j;
  super.clearcoord(row1,col1,row2,col2);

  if ( row1 == row2) {
    for (i = col1;i <= col2;i++)
      {
      CharArray[i][row1] = ' ';
      Foregnd[i][row1] = foreground;
      Backgnd[i][row1] = background;
      }

    return;
    }

  for (i = col1; i < char_w; i++)
    {
    CharArray[i][row1] = ' ';
    Foregnd[i][row1] = foreground;
    Backgnd[i][row1] = background;
    }

  for (j = row1+1; i < row2; i++)
    {  
    for (i = 0; i < char_w - 1; i++)
      {
      CharArray[i][j] = ' ';
      Foregnd[i][j] = foreground;
      Backgnd[i][j] = background;
      }
    }

  for (i = 0; i <= col2; i++)
    {
    CharArray[i][row2] = ' ';
    Foregnd[i][row2] = foreground;
    Backgnd[i][row2] = background;
    }

  }

public void translate(char c) {

  if (c == '[') {
    bracket = true;
    return;
    }

  if ((c >='0') && (c <= '9')) {

    if (bracket == true)
      {
      Value[Parameter] *= 10;
      Value[Parameter] += c - 0x30;
      firstchar = false;
      return;
       }

     escapeon = false;
     switch (c)
       {
        case '7' : // store cursor position
                   columnhold = column;
                   rowhold = row;
                   brighthold = bright;
                   inversehold = inverse;
                   Foresave = foreground;
                   Backsave = background;
                   break;


        case '8': // restore cursor position
                   column = columnhold;
                   row = rowhold;
                   bright     = brighthold;
                   inverse    = inversehold;
                   foreground = Foresave;
                   background = Backsave;
                   break;
        }
      return;
    }


  switch (c)
    {
    case '?':  break; // Ignore VT100 '?1l' and '?1h'

    case ';':
               Parameter++;
               Value[Parameter] = 0;
               break;

    case 'A':
               escapeon = false;
               
               if (Value[0] == 0)
                  Value[0] = 1;
               if (row - Value[0] < 0)
                  row = 0;
               else
                  row -= Value[0];

               break;

    case 'B':
               escapeon = false;
                
               if (Value[0] == 0)
                 Value[0] = 1;
               if (row + Value[0] > char_h - 1)
                 row = char_h - 1;
               else
                 row += Value[0];

               break;

    case 'C':
               escapeon = false;
               
               if (Value[0] == 0)
                 Value[0] = 1;
               if (column + Value[0] > char_w - 1)
                 column = char_w - 1;
               else
                 column += Value[0];

               break;

    case 'D':
               escapeon = false;
              
               if (Value[0] == 0)
                 Value[0] = 1;
               if (column - Value[0] < 0)
                 column = 0;
               else
                 column -= Value[0];

               break;

    case 'f':
    case 'H':
               escapeon = false;
               if (Value[0] == 0)
                 Value[0] = 1;

               if (Value[1] == 0)
                 Value[1] = 1;

               if (Value[0] > char_h)
                 row = char_h - 1;
               else
                 row = Value[0] - 1;

               if (Value[1] > char_w)
                 column = char_w - 1;
               else
                 column = Value[1] - 1;

               break;

    case 'J':
               escapeon = false;
               if (Value[0] == 2)
                 {
                 column = 0;
                 row = 0;
                 }

               if ((column == 0) && (row == 0))
                 {
                 clearscreen();
                 break;
                 }

               if (Value[0] == 0)
                 {
                 clearcoord(row,column,char_h-1,char_w - 1);
                 break;
                 }

               if (Value[0] == 1)
                 {
                 clearcoord(0,0,row,column);
                 break;
                 }

                break;

    case 'K':
                escapeon = false;
                if (Value[0] == 0)
                  {
                  clearcoord(row,column,row,char_w - 1);
                  break;
                  }

                if (Value[0] == 1)
                  {
                  clearcoord(row,0,row,column);
                  break;
                  }

                if (Value[0] == 2)
                  {
                   clearcoord(row,0,row,char_w - 1);
                  break;
                  }

    case 'm':
                escapeon = false;
                changecolor();
                break;

    case 'r':
                escapeon = false;
                if (Value[0] == 0)
                  Value[0] = 1;

                if (Value[1] == 0)
                  Value[1] = 1;

                ScrollTop = Value[0] - 1;
                ScrollBottom = Value[1] - 1;
                break;

    default :
     // System.out.println("Unhandled ESC char = "+String.valueOf((int)c) + " " + String.valueOf((char)c));
                escapeon = false;
                break;
    }

  firstchar = false;

  }

public void displaych(char c) {

 // System.out.println("char = "+String.valueOf((int)c) + " " + String.valueOf((char)c));

  if (escapeon == true)
    {
    translate(c);
    return;
    }

  if (c == 27) {
    escapeon = true;
    Parameter = 0;
    Value[0] = 0;
    Value[1] = 0;  // cases of [H with no parameters
    Value[2] = 0;
    bracket = false;
    firstchar = true;
    return;
    }

  else if (c == 0x0a) {
    // Linefeed
    if (row == char_h-1) {
      scrollup(0,char_h-1);
      }
    else
    if (row == ScrollBottom)
       {
       scrollup(ScrollTop, ScrollBottom);
       }
    else
      row++;
    }
  
  else if (c == 0x0d) {
    // Carriage Return
    column = 0;
    }

  else if (c == '\t') {
    // Tab
    int coltemp = column;
    for ( int i = 8 ; i > coltemp%8; i--)
      {
      displaych(' ');
      }
    }

  else if (c == (char)8 ) {
    // Backspace
    if (column != 0) column--;
    }

  else if (c >= 32 && c < 255) {
    // Some printable character
    super.displaych(c);

    CharArray[column][row] = c;
    Foregnd[column][row] = foreground;
    Backgnd[column][row] = background;

    if (column == char_w - 1)
      {
      displaych((char)0x0a);
      displaych((char)0x0d);
      }
    else
      column++;
    }

  }

public void displaybytes ( byte b[], int len)
  {

  HoldFore = foreground;
  HoldBack = background;
  foreground = Foregnd[column][row];
  background = Backgnd[column][row];
  super.displaych(CharArray[column][row]);
  foreground = HoldFore;
  background = HoldBack;

  // if (true) System.out.println("i got " + String.valueOf(len)+ " chars to show you.");
  int i = 0;

  while( i < len)
    {
   // System.out.println("i got " + String.valueOf(b[i])+" "+ i);
    if (b[i] == 255-256)
      {
      i++;
      if (b[i] == 250-256)
        {
        while ((i < len) && (b[i] != 240-256))
          i++;

        i++;
        }
      else
        {
        i+=2;
        }
      }
    else
      {
      if (b[i] < 0)
        displaych((char)(((char)b[i]) + 256));
      else
        displaych((char)b[i]);
      i++;
      }
    }

  owner.bgr.setColor(Foregnd[column][row].brighter());
  owner.bgr.drawString("_", column*owner.chw, (row+1)*owner.chh-owner.chd);

  }

public void transmitch(char ch)
  {

  if (ch < 256)
    {
    super.transmitch(ch);
    return;
    }

  switch (ch)
    {
    case 1004: // up-arrow
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('A');
            return;

    case 1005: // down-arrow
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('B');
            return;

    case 1006: // left-arrow
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('D');
            return;

    case 1007:  // right-arrow
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('C');
            return;

    case 1000: // Home
            super.transmitch((char)1); // ^A
            return;

    case 1001: // End
            super.transmitch((char)5); // ^E
            return;

    case 1002: // PageUp
            super.transmitch((char)25); // ^Y
            return;

    case 1003: // PageDn
            super.transmitch((char)22); // ^V
            return;

    case 1008: // F1
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('P');
            return;

    case 1009: // F2
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('Q');
            return;

    case 1010: // F3
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('R');
            return;

    case 1011: // F4
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('S');
            return;

    case 1012: // F5
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('t');
            return;

    case 1013: // F6
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('u');
            return;

    case 1014: // F7
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('v');
            return;

    case 1015: // F8
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('I');
            return;

    case 1016: // F9
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('w');
            return;

    case 1017: // F10
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('x');
            return;

    case 1018: // F11
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('y');
            return;

    case 1019: // F12
            super.transmitch((char)27);
            super.transmitch('O');
            super.transmitch('z');
            return;
    }

  super.transmitch(ch);
  }

}
