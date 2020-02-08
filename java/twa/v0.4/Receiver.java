import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

class Receiver extends JComponent implements Runnable, KeyListener {

  /** CONSTANTS **/
  public static final Color DEFAULT_FG_COLOR = Color.white;
  public static final Color DEFAULT_BKG_COLOR = Color.black;
  public static final int MAX_STRING_LEN = 1024;
  public static final char ESC = (char) 27;
  public static final String ESC_LBRACE = (char) 27 + "[";


  /** INSTANCE VARIABLES **/
  DataInputStream theInputStream;
  int iLinesPerScreen;
  int x, y, iReceived, iLines;
  int iSpacing;
  String[] strData;
  String strBuffer;
  String strLine;
  String strToParse;
  Thread threadFetcher;
  FontMetrics fm;
  Parser theParser;
  TWA theTWA;
  boolean displayText;
  Telnet theTelnet;
  Color colorForeground;
  Color colorBackground;
  boolean inTerminalCode;
  boolean backgroundOn;
  boolean isBold;
  boolean addToBuffer;
  StringTokenizer stToParse;

  Image imgBuffer;
  Graphics graphicsBuffer;

  /**
    * Receiver - gets the data
    **/
  public Receiver(DataInputStream theInputStream, TWA theTWA, Telnet theTelnet) {
    int i;

    this.theInputStream = theInputStream;
    this.theTelnet = theTelnet;


    x = 0;
    y = 0;
    iReceived = 0;
    iLines = 0;
    iLinesPerScreen = 24;

    this.theTWA = theTWA;

    if (theTWA != null) {
      theParser = theTWA.getParser();
    }

    strData = new String[iLinesPerScreen];
    for (i = 0; i < strData.length; i++) {
      strData[i] = "~";
    }
    strBuffer = "";
    strToParse = "";
    strLine = "";

    displayText = false; //display to the component or display text to console
    inTerminalCode = false;
    isBold = false;
    addToBuffer = true;


    colorForeground = DEFAULT_FG_COLOR;
    colorBackground = DEFAULT_BKG_COLOR;
    backgroundOn = false;
    addKeyListener(this);


    threadFetcher = new Thread(this);
    threadFetcher.setPriority(Thread.MIN_PRIORITY);
    threadFetcher.start();

  }


  /**
    * run
    **/
  public void run() {
    boolean isConnected;
    boolean willDisplay;
    boolean needToUpdate;
    char theChar;
    int i, j;
    int iAvailable;

    isConnected = true;
    willDisplay = true;
    needToUpdate = false;
    strLine = "";

    try {
      while (true && isConnected) {
        synchronized (strData) {

          if (theInputStream != null) {

            iAvailable = theInputStream.available();
      
            if (iAvailable > 0) {
              
              for (j = 0; j < iAvailable; j++) {
                theChar = (char) theInputStream.readByte();

                if (theChar > 127) {
                  theChar = '=';  //since Java doesn't support extended ASCII characters
                }

                //move down one line
                if (theChar == 10) {

                  strToParse = strToParse + "\n";

                  if (theParser != null) {
//                    theParser.parseString(strLine.trim());
//                    System.out.println("Parse this: " + strToParse);

                  }


                  if (iLines > (iLinesPerScreen - 1)) {
                    for (i = 0; i < (iLinesPerScreen - 1); i++) {
                      strData[i] = strData[i + 1];
                    }
                    strData[iLinesPerScreen - 2] = strData[iLinesPerScreen - 1];

                  } else {
                    strData[iLines] = strData[iLinesPerScreen - 1]; //it's a line on the first screen, no scrolling needed
                  }

                  strLine = "";
                  strData[iLinesPerScreen - 1] = "";
                  iLines++;

                } else if (theChar == 13) {
                  //carriage return
                  //do nothing
                } else if (theChar == 12) {

                } else if (theChar == 8) {
                  //delete
                  if (strData[iLinesPerScreen - 1].length() > 3) {
                    strLine = strLine.substring(0, strLine.length() - 1);
                    strData[iLinesPerScreen - 1] = strData[iLinesPerScreen - 1].substring(0, strData[iLinesPerScreen - 1].length() - 1);
                  }


                } else {
                  //add the character to the last line
                  strData[iLinesPerScreen - 1] = strData[iLinesPerScreen - 1] + theChar;

                  if (theChar == ESC) {
                    addToBuffer = false;
                  } else if (addToBuffer == false) {
                    if ( (theChar != '[') &&
                         (theChar != ';') &&
                         !(Character.isDigit(theChar)) ) {
                      addToBuffer = true;

                    }
                  } else if (addToBuffer) {
                    strLine = strLine + theChar;
                    strToParse = strToParse + theChar;

                  }
                }
              }

              /** Trying line parser **/
              if (theInputStream.available() == 0) {
//              System.out.println("Parse this: " + strToParse);
//              System.out.println("Chars available: " + theInputStream.available());

                stToParse = new StringTokenizer(strToParse, "\n");
                strBuffer += strToParse;

                while(stToParse.hasMoreTokens()) {
                  String strTemp = stToParse.nextToken();

//                  System.out.println("Line: " + strTemp.trim());
//                  System.out.println("Length: " + strTemp.trim().length());
                  theParser.parseString(strTemp);
                }

//              System.out.println("End packet");

                strToParse = "";
              }
              /************************/


              if (getGraphics() == null) {
                while(getGraphics() == null) {
                }
              }

              if (getGraphics() != null) {
                //update(getGraphics());
                repaint();
              }

            }



          } else {
            strData[10] = "Can not Connect to Server";
            isConnected = false;
          }
        }
      }

    } catch (EOFException e) {
    } catch (IOException e) { }
  }




  /**
    * updateFont - sets the terminal font
    *           returns the font height
    **/
  public int updateFont(String nextFont, int iFontSize) {
//    System.out.println("Font: " + nextFont + iFontSize);
    setFont(new Font(nextFont, Font.BOLD, iFontSize));
    fm = this.getFontMetrics(getFont());


    setPreferredSize(new Dimension(fm.charWidth(' ') * 80, (iLinesPerScreen + 1) * fm.getHeight()));
    setMaximumSize(getPreferredSize());
    setSize(getPreferredSize());

    iSpacing = fm.getHeight();

    repaint();
    return iSpacing;
  }

  /**
    * getLinesPerScreen
    **/
  public int getLinesPerScreen() {
    return iLinesPerScreen;
  }

  /**
    * getBuffer
    **/
  public String getBuffer() {
    return strBuffer;
  }

  /**
    * redraw
    **/
  public void redraw() {
    //update(getGraphics());
    repaint();
  }


  /**
    * keyPressed
    * keyReleased
    * keyTyped
    **/
  public void keyPressed(KeyEvent e) { }
  public void keyTyped(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      theTelnet.getSender().sendData("\r");
    } else {
      theTelnet.getSender().sendData(e.getKeyChar());
    }


  }
  public void keyReleased(KeyEvent e) {  }


  public void paint(Graphics g) {
    update(g);
  }

  /**
    * update()
    **/
  public void update(Graphics g) {
    int i, j;
    int iCurrentChar;
    String strToPrint;
    int x, y;
    int iAttribute, iBkgColor, iFgColor;
    boolean keepLooping;
    String strCode;
    String strTemp;


    strTemp = "";
    iSpacing = 14;

    if ((getWidth() > 0) && (getHeight() > 0)) {
      imgBuffer = this.createImage(getWidth(), getHeight());
      graphicsBuffer = imgBuffer.getGraphics();

      x = 0;
      y = iSpacing;
      graphicsBuffer.setColor(DEFAULT_BKG_COLOR);
      graphicsBuffer.fillRect(0, 0, getWidth(), getHeight());
      graphicsBuffer.setColor(colorForeground);
      graphicsBuffer.setFont(this.getFont());




      for (i = 0; i < iLinesPerScreen; i++) {

        strToPrint = "";

        iCurrentChar = 0;
        keepLooping = true;


        while (keepLooping) {
          if (strData[i].indexOf(ESC_LBRACE, iCurrentChar) > -1) {
            strToPrint = strData[i].substring(iCurrentChar, strData[i].indexOf(ESC_LBRACE, iCurrentChar));
            iCurrentChar = strData[i].indexOf(ESC_LBRACE, iCurrentChar);

            printString(strToPrint, x, y, graphicsBuffer);



          //deal with the color code
          iCurrentChar += 2;
          strCode = "";

          while ( (iCurrentChar < strData[i].length()) && ( (strData[i].charAt(iCurrentChar) == '0') ||
                 (strData[i].charAt(iCurrentChar) == '1') ||
                 (strData[i].charAt(iCurrentChar) == '2') ||
                 (strData[i].charAt(iCurrentChar) == '3') ||
                 (strData[i].charAt(iCurrentChar) == '4') ||
                 (strData[i].charAt(iCurrentChar) == '5') ||
                 (strData[i].charAt(iCurrentChar) == '6') ||
                 (strData[i].charAt(iCurrentChar) == '7') ||
                 (strData[i].charAt(iCurrentChar) == '8') ||
                 (strData[i].charAt(iCurrentChar) == '9') ||
                 (strData[i].charAt(iCurrentChar) == ';'))  ) {

            //This is really f'ed up
            try {

              if (iCurrentChar < strData[i].length()) {
                strCode += strData[i].charAt(iCurrentChar);
              }
            } catch (Exception e) { }

            iCurrentChar++;
          }

          try {

            if (iCurrentChar < strData[i].length()) {
              strCode += strData[i].charAt(iCurrentChar); //get the last character of terminal code
            }

          } catch (Exception e) { }


          //deal with the terminal code
          dealWithTerminalCode(graphicsBuffer, strCode, x, y);

          iCurrentChar++; //move out of the terminal code




          } else {
            try {
              strToPrint = strData[i].substring(iCurrentChar); //might be nothing if string is empty
              printString(strToPrint, x, y, graphicsBuffer);
            } catch (Exception e) { 

              //is throwing an exception because the line ends in
              //a keycode which ends with a number

              /*                    
              System.out.println("***Exception: strData[i]: " + strData[i]);
              System.out.println("           i: " + i);
              System.out.println("iCurrentChar: " + iCurrentChar);
              System.out.println("        size: " + strData[i].length());
              */

            }
            keepLooping = false;
          }

          x += fm.stringWidth(strToPrint);


       }
       y += iSpacing;
       x = 0;
      }
      g.drawImage(imgBuffer, 0, 0, getWidth(), getHeight(), this);
    }
  }

  /**
    * printString
    **/
  private void printString(String str, int x, int y, Graphics g) {
    char[] chToPrint;

    chToPrint = str.toCharArray();

    //draw the string
    if (backgroundOn) {
    g.setColor(colorBackground);
      g.fillRect(x, y - fm.getHeight() + fm.getMaxDescent(), fm.stringWidth(str), fm.getHeight());
    }

    g.setColor(colorForeground);
//    g.drawString(str, x, y);
    g.drawChars(chToPrint, 0, str.length(), x, y);

  }


  /**
    * dealWithTerminalCode
    **/
  private void dealWithTerminalCode(Graphics g, String strCode, int x, int y) {
    int iCurrentChar;
    int i;
    String strTemp;
    Font fontTemp;
    boolean keepLooping;

    iCurrentChar = 0;
    //System.out.println("Code: " + strCode);


      if (strCode.endsWith("m")) {
        keepLooping = true;
        while (keepLooping) {
          if (strCode.indexOf(';', iCurrentChar) != -1) {

            strTemp = strCode.substring(iCurrentChar, strCode.indexOf(';', iCurrentChar));
          } else {


            strTemp = strCode.substring(iCurrentChar, strCode.indexOf("m", iCurrentChar));
          }



          if (strTemp.equals("0")) {
            backgroundOn = false;
            isBold = false;
            colorForeground = new Color(255, 255, 255); //bright white

          } else if (strTemp.equals("1")) {
            fontTemp = getFont();
            isBold = true;
          } else if (strTemp.equals("5")) {
          } else if (strTemp.equals("7")) {
          } else if (strTemp.equals("8")) {
  
          } else if (strTemp.equals("30")) {
            colorForeground = new Color(0, 0, 0); //black
          } else if (strTemp.equals("31")) {
            if (!isBold) {
              colorForeground = new Color(128, 0, 0); //dark red
            } else {
              colorForeground = new Color(255, 0, 0); //bold red
            }
          } else if (strTemp.equals("32")) {
            if (!isBold) {
              colorForeground = new Color(0, 128, 0); //dark green
            } else {
              colorForeground = new Color(0, 255, 0); //bold green
            }
          } else if (strTemp.equals("33")) {
            if (!isBold) {
              colorForeground = new Color(128, 128, 0); //dark yellow
            } else {
              colorForeground = new Color(255, 255, 0); //bold yellow
            }
          } else if (strTemp.equals("34")) {
            if (!isBold) {
              colorForeground = new Color(0, 0, 128); //dark blue
            } else {
              colorForeground = new Color(0, 0, 255); //bold blue
            }
          } else if (strTemp.equals("35")) {
            if (!isBold) {
              colorForeground = new Color(128, 0, 128); //dark magenta
            } else {
              colorForeground = new Color(255, 0, 255); //bold magenta
            }
          } else if (strTemp.equals("36")) {
            if (!isBold) {
              colorForeground = new Color(0, 128, 128); //dark cyan
            } else {
              colorForeground = new Color(0, 255, 255); //bold cyan
            }
          } else if (strTemp.equals("37")) {
            if (!isBold) {
              colorForeground = new Color(128, 128, 128); //gray
            } else {
              colorForeground = new Color(255, 255, 255); //bright white
            }
          } else if (strTemp.equals("40")) {
            colorBackground = new Color(0, 0, 0); //black
            backgroundOn = true;
          } else if (strTemp.equals("44")) {
            colorBackground = new Color(0, 0, 128); //blue
            backgroundOn = true;
          } else if (strTemp.equals("41")) {
            colorBackground = new Color(128, 0, 0); //red
            backgroundOn = true;
          } else if (strTemp.equals("45")) {
            colorBackground = new Color(128, 0, 128); //magenta
            backgroundOn = true;
          } else if (strTemp.equals("42")) {
            colorBackground = new Color(0, 128, 0); //green
            backgroundOn = true;
          } else if (strTemp.equals("46")) {
            colorBackground = new Color(0, 128, 128); //cyan
            backgroundOn = true;
          } else if (strTemp.equals("43")) {
            colorBackground = new Color(128, 128, 0); //yellow
            backgroundOn = true;
          } else if (strTemp.equals("47")) {
            colorBackground = new Color(128, 128, 128); //white
            backgroundOn = true;

          }


          if (strCode.indexOf(';', iCurrentChar) != -1) {
            iCurrentChar = strCode.indexOf(';', iCurrentChar) + 1;
          } else {
            keepLooping = false;
          }

        }
      }


      if (strCode.endsWith("J")) {
        if (strCode.length() == 2) {
          if (strCode.charAt(0) == '2') {
            //erase screen
            /*
            System.out.println("Erase Screen ");
            for (i = 0; i < strData.length; i++) {
              strData[i] = "~";
            }

            iLines = 0;
            */
          }
        }
      }

     boolean printActions = true;
     if (printActions) {

      if (strCode.endsWith("Z")) {
        System.out.println("Cursor Backward Tab");
      }

      if (strCode.endsWith("T")) {
        System.out.println("Cancel Previous character");
      }

      if (strCode.endsWith("G")) {
        System.out.println("Horizontal Absolute");
      }

      if (strCode.endsWith("I")) {
        System.out.println("Horizontal Tab");
      }

      if (strCode.endsWith("L")) {
        System.out.println("Insert Line");
      }

      if (strCode.endsWith("E")) {
        System.out.println("Next Line");
      }

      if (strCode.endsWith("F")) {
        System.out.println("Preceeding Line");
      }

      if (strCode.endsWith("R")) {
        System.out.println("Position Report");
      }

      if (strCode.endsWith("W")) {
        System.out.println("Tab Control");
      }

      if (strCode.endsWith("D")) {
        System.out.println("Cursor Backward");
      }

      if (strCode.endsWith("B")) {
        System.out.println("Cursor Down");
      }

      if (strCode.endsWith("C")) {
        System.out.println("Cursor Forward");
      }


      if (strCode.endsWith("H")) {
        System.out.println("Cursor Position");
      }

      if (strCode.endsWith("A")) {
        System.out.println("Cursor Up");
      }

      if (strCode.endsWith("Y")) {
        System.out.println("Cursor Vertical Tab");
      }

      if (strCode.endsWith("c")) {
        System.out.println("Device Attributes");
      }

      if (strCode.endsWith("o")) {
        System.out.println("Define Area Qualification");
      }

      if (strCode.endsWith("P")) {
        System.out.println("Delete Character");
      }

      if (strCode.endsWith("M")) {
        System.out.println("Delete Line");
      }

      if (strCode.endsWith("n")) {
        System.out.println("Device Status Report");
      }

      if (strCode.endsWith("O")) {
        System.out.println("Erase In Area");
      }

      if (strCode.endsWith("X")) {
        System.out.println("Erase Character");
      }


      if (strCode.endsWith("H")) {
        System.out.println("Cursor Position");
      }













      if (strCode.endsWith("L")) {
        System.out.println("Insert Line");
      }

     }

  }

  /**
    * noCarrier
    **/
  public void noCarrier() {
    int i;

    for (i = 0; i < (iLinesPerScreen - 2); i++) {
      strData[i] = strData[i + 2];
    }
    strData[iLinesPerScreen - 2] = "";
    strData[iLinesPerScreen - 1] = "NO CARRIER";

    repaint();
    Toolkit.getDefaultToolkit().beep();
  }

  /**
    * main
    **/
  public static void main(String argv[]) {

  }


}
