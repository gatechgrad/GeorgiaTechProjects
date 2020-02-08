import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

class Receiver extends JComponent implements Runnable, KeyListener {

  /** CONSTANTS **/
  public static final Color DEFAULT_FG_COLOR = Color.white;
  public static final Color DEFAULT_BKG_COLOR = Color.black;
  public static final int MAX_STRING_LEN = 1024;
  public static final int LINES_PER_SCREEN = 24;
  public static final char ESC = (char) 27;
  public static final String ESC_LBRACE = (char) 27 + "[";


  /** INSTANCE VARIABLES **/
  DataInputStream theInputStream;
  int iLinesPerScreen;
  int x, y, iReceived, iLines;
  int iSpacing;
  String strData;
  String strBuffer;
  String strLine;
  String strToParse;
  String strToPrint;
  String strCode;
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
  boolean startThread;
  boolean inCode;
  StringTokenizer stToParse;
  Point pntCurrentPosition;
  int iBufferSize;

  BufferedImage imgBuffer;



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
    iLinesPerScreen = LINES_PER_SCREEN;
    iBufferSize = 1024;


    pntCurrentPosition = new Point(0, 0);

    this.theTWA = theTWA;

    if (theTWA != null) {
      theParser = theTWA.getParser();
    }

    strData = "";
    strBuffer = "";
    strToParse = "";
    strLine = "";

    strToPrint = "";
    strCode = "";


    displayText = false; //display to the component or display text to console
    inTerminalCode = false;
    isBold = false;
    addToBuffer = true;
    inCode = false;

    colorForeground = DEFAULT_FG_COLOR;
    colorBackground = DEFAULT_BKG_COLOR;
    backgroundOn = false;
    addKeyListener(this);

    threadFetcher = new Thread(this);
    threadFetcher.setPriority(Thread.MIN_PRIORITY);
    startThread = true; //start the thread once the graphics object is available
  }

  /**
    * optimizations
    **/
  public boolean isOpaque() { return false; }
  public boolean isDoubleBuffered() { return false; }

  public boolean isFocusTraversable() { return true; }

  /**
    * getThreadFetcher
    **/
  public Thread getThreadFetcher() {
    return threadFetcher;
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
            if (iAvailable < 0) {
              System.out.println("iAvailable: " + iAvailable);
            }
      
            if (iAvailable > 0) {
              updateTerminalScreen();
            }



          } else {
            strData = "Can not Connect to Server";
            isConnected = false;
          }
        }
      }

    } catch (EOFException e) {
    } catch (IOException e) { }
  }

  /**
    * setupImageBuffer
    **/
  private void setupImageBuffer() {
    //System.out.println("Making image buffer");
    imgBuffer = (BufferedImage) this.createImage(getWidth(), getHeight());

    Graphics graphicsBuffer = imgBuffer.getGraphics();
//    Graphics graphicsBuffer = getGraphics();

    graphicsBuffer.setColor(colorBackground);
    graphicsBuffer.fillRect(0, 0, getWidth(), getHeight());
//    graphicsBuffer.setColor(colorForeground);
    graphicsBuffer.setColor(Color.red);
    graphicsBuffer.setFont(getFont());

  }

  /**
    * updateTerminalScreen
    **/
  public void updateTerminalScreen() {
    int i, j;
    int theChar;


    Graphics graphicsBuffer = imgBuffer.getGraphics();
//    Graphics graphicsBuffer = getGraphics();
    graphicsBuffer.setFont(this.getFont());


    //initialize variables
    i = 0;

 try {
    for (i = 0; i < theInputStream.available(); i++) {
      theChar = theInputStream.readByte();

      if ( ((char) theChar) > 127 ) {
        theChar = '=';
      }

      //Not in a terminal code
      if (!inCode) {
        if (theChar == 10) {
          printString(strToPrint, pntCurrentPosition.x, pntCurrentPosition.y, graphicsBuffer);
          //System.out.println(strToPrint);
          strToPrint = "";
          strToParse += '\n';

          pntCurrentPosition.x = 0;

          if (pntCurrentPosition.y >= LINES_PER_SCREEN - 1) {
            pntCurrentPosition.y = LINES_PER_SCREEN - 1;
            scroll(fm.getHeight());

          } else {
            pntCurrentPosition.y++;
          }

          } else if (theChar == 13) {
            //carriage return
            //do nothing
            printString(strToPrint, pntCurrentPosition.x, pntCurrentPosition.y, graphicsBuffer);
            strToPrint = "";

            pntCurrentPosition.x = 0;

          } else if (theChar == 12) {

          } else if (theChar == 8) {
            //delete
            deleteChar(pntCurrentPosition.x, pntCurrentPosition.y, graphicsBuffer);
            pntCurrentPosition.x--;

          } else if (theChar == ESC) {
            //print current string before going into code
            printString(strToPrint, pntCurrentPosition.x, pntCurrentPosition.y, graphicsBuffer);
            //System.out.println(strToPrint);
            pntCurrentPosition.x += strToPrint.length();
            strToPrint = "";

            strCode = "";
            strCode += (char) theChar;
            inCode = true;
          } else {
            strToPrint += (char) theChar;
            strToParse += (char) theChar;
          }



        //In a terminal code
        } else {

        if (   !Character.isDigit( (char) theChar) &&
                (theChar != '[') &&
                (theChar != ';')
           ) {

          strCode += (char) theChar;
          //deal with the terminal code
          dealWithTerminalCode(graphicsBuffer, strCode, pntCurrentPosition.x, pntCurrentPosition.y);
          inCode = false;
          strCode = "";
        } else {
          strCode += (char) theChar;
        }

      }

    }

    /** Trying line parser **/
    if (theInputStream.available() == 0) {

    stToParse = new StringTokenizer(strToParse, "\n");
    //strBuffer += strToParse;

     while(stToParse.hasMoreTokens()) {
       String strTemp = stToParse.nextToken();

       if (theParser != null) {
         try {
           theParser.parseString(strTemp);
         } catch (StringIndexOutOfBoundsException e) {

         } catch (NullPointerException e) {

         }
       }
     }

     strToParse = "";
     }
     /************************/




    if (theInputStream.available() == 0) {
      printString(strToPrint, pntCurrentPosition.x, pntCurrentPosition.y, graphicsBuffer);
      pntCurrentPosition.x += strToPrint.length();
      strToPrint = "";
    }

    repaint();

  } catch (IOException e) { }

  }

  /**
    * scroll
    **/
  private void scroll(int iMagnitude) {
    Graphics graphicsBuffer = imgBuffer.getGraphics();

//    imgBuffer = (BufferedImage) this.createImage(getWidth(), getHeight());

//    Graphics graphicsBuffer = getGraphics();
    graphicsBuffer.drawImage(imgBuffer, 0, 0 - iMagnitude, this);
    graphicsBuffer.setColor(colorBackground);
    graphicsBuffer.fillRect(0, getHeight() - iMagnitude, getWidth(), iMagnitude);

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
//    setSize(getPreferredSize());

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
    * keyTyped    - required by KeyListener
    * keyPressed
    * keyReleased
    **/
  public void keyReleased(KeyEvent e) {
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//      System.out.println("Enter pressed");
      theTelnet.getSender().sendData("\r");
    }

  }
  public void keyTyped(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      theTelnet.getSender().sendData((char) 8);
    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      theTelnet.getSender().sendData((char) 32);

    } else {
      theTelnet.getSender().sendData(e.getKeyChar());
    }
  }


  public void paint(Graphics g) {
    update(g);
  }

  public void repaint(Graphics g) {
    update (g);
  }

  /**
    * update()
    **/
  public void update(Graphics g) {
    if (startThread) {
      threadFetcher.start();
      startThread = false;
    }


    if (imgBuffer == null) {
      setupImageBuffer();
    }

    if ((getWidth() > 0) && (getHeight() > 0) && (imgBuffer != null)) {
      g.drawImage(imgBuffer, 0, 0, this);
    }

    drawCursor(g);

  }

  /**
    * printString
    **/
  private void printString(String str, int x, int y, Graphics g) {
    char[] chToPrint;
    int iCharWidth = fm.charWidth('@');
    int iCharHeight = fm.getHeight();

//    System.out.print(str);

    chToPrint = str.toCharArray();

    //draw the string
    if (backgroundOn) {
      g.setColor(colorBackground);
      g.fillRect(x * iCharWidth, ((y + 1) * iCharHeight) - iCharHeight + fm.getMaxDescent(), fm.stringWidth(str), iCharHeight);
    }

    g.setColor(colorForeground);


//which one is faster??
//    g.drawString(str, x, y);
    g.drawChars(chToPrint, 0, str.length(), x * iCharWidth, (y + 1) * iCharHeight);
    //repaint();

  }

  /**
    * drawCursor
    **/
  private void drawCursor(Graphics g) {
    int iCharWidth = fm.charWidth('@');
    int iCharHeight = fm.getHeight();

    g.setColor(colorForeground);
    g.fillRect(pntCurrentPosition.x * iCharWidth, ((pntCurrentPosition.y + 1) * iCharHeight) - iCharHeight + fm.getMaxDescent(), iCharWidth, iCharHeight);

  }

  /**
    * deleteChar
    **/
  private void deleteChar(int x, int y, Graphics g) {
    int iCharWidth = fm.charWidth('@');
    int iCharHeight = fm.getHeight();

    g.setColor(colorBackground);
    g.fillRect(x * iCharWidth, ((y + 1) * iCharHeight) - iCharHeight + fm.getMaxDescent(), iCharWidth, iCharHeight);
  }



  /**
    * dealWithTerminalCode
    **/
  private void dealWithTerminalCode(Graphics g, String strCode, int x, int y) {
    int iCurrentChar;
    int i, j;
    String strTemp;
    Font fontTemp;
    boolean keepLooping;
    int iMagnitude;

    int iCharHeight = fm.getHeight();
    int iCharWidth = fm.charWidth('@');

    iCurrentChar = strCode.indexOf('[', 0) + 1;
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
            colorForeground = DEFAULT_FG_COLOR;
            colorBackground = DEFAULT_BKG_COLOR;

          } else if (strTemp.equals("1")) {
            fontTemp = getFont();
            isBold = true;
          } else if (strTemp.equals("5")) {
          } else if (strTemp.equals("7")) {
          } else if (strTemp.equals("8")) {
  
          } else if (strTemp.equals("30")) {
            if (!isBold) {
              colorForeground = new Color(0, 0, 0); //black
            } else {
              colorForeground = new Color(128, 128, 128); //gray
            }

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
              colorForeground = new Color(192, 192, 192); //gray
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


      } else if (strCode.endsWith("J")) {
        if (strCode.indexOf('2') > -1) {
          //erase screen
          //System.out.println("Erase Screen");
          g.setColor(Color.black);
          g.fillRect(0, 0, getWidth(), getHeight());

          pntCurrentPosition.x = 0;
          pntCurrentPosition.y = 0;

        } else if (strCode.indexOf('1') > -1) {
          System.out.println("Erase from beginning to cursor");
        } else if (strCode.indexOf('0') > -1) {
          System.out.println("Erase from cursor to end of screen");
        } else {
          System.out.println("J Code not found: " + strCode);
        }


      } else if (strCode.endsWith("K")) {
          //System.out.println("Code ended with K");
          if (strCode.indexOf('2') > -1) {
            System.out.println("Erase line containng cursor");
          } else if (strCode.indexOf('1') > -1) {
            System.out.println("Erase from beginning of line to cursor");
          } else if (strCode.indexOf('0') > -1) {
            System.out.println("Erase from cursor to end of line");
          } else {
            //System.out.println("K Code not found: " + strCode);
            //System.out.println("CursorPosition: " + pntCurrentPosition);

            g.setColor(colorBackground);
            g.fillRect(pntCurrentPosition.x * iCharWidth, ((pntCurrentPosition.y + 1) * iCharHeight) - iCharHeight + fm.getMaxDescent(), getWidth() - (pntCurrentPosition.x * iCharWidth), iCharHeight);
          }

      } else if (strCode.endsWith("D")) {
//        System.out.println("Cursor Backward");
        if (strCode.indexOf("D") > -1) {
          iCurrentChar = strCode.indexOf('[', 0) + 1;
          iMagnitude = Parser.toInteger(  strCode.substring(iCurrentChar, strCode.indexOf('D')), pntCurrentPosition.x);
          pntCurrentPosition.x -= iMagnitude;
        }
      } else if (strCode.endsWith("B")) {
//        System.out.println("Cursor Down");
        if (strCode.indexOf("B") > -1) {
          iCurrentChar = strCode.indexOf('[', 0) + 1;
          iMagnitude = Parser.toInteger(  strCode.substring(iCurrentChar, strCode.indexOf('B')), pntCurrentPosition.y);
          pntCurrentPosition.y += iMagnitude;
        }

      } else if (strCode.endsWith("C")) {
//        System.out.println("Cursor Forward");

        if (strCode.indexOf("C") > -1) {
          iCurrentChar = strCode.indexOf('[', 0) + 1;
          iMagnitude = Parser.toInteger(  strCode.substring(iCurrentChar, strCode.indexOf('C')), pntCurrentPosition.x);
          pntCurrentPosition.x += iMagnitude;
        }
      } else if (strCode.endsWith("H")) {
//        System.out.println("Cursor Position");

        if ( ( strCode.indexOf(";") > -1) &&
             ( strCode.indexOf("H") > -1)
           ) {

          iCurrentChar = strCode.indexOf('[', 0) + 1;

          pntCurrentPosition.y = Parser.toInteger(  strCode.substring(iCurrentChar, strCode.indexOf(';')), pntCurrentPosition.x);
          pntCurrentPosition.x = Parser.toInteger(  strCode.substring(strCode.indexOf(';') + 1, strCode.indexOf("H")), pntCurrentPosition.y);

          pntCurrentPosition.y--;
          pntCurrentPosition.x--;
          //System.out.println(pntCurrentPosition);
        }
      } else if (strCode.endsWith("A")) {
//        System.out.println("Cursor Up");
        if (strCode.indexOf("A") > -1) {
          iCurrentChar = strCode.indexOf('[', 0) + 1;
          iMagnitude = Parser.toInteger(  strCode.substring(iCurrentChar, strCode.indexOf('A')), pntCurrentPosition.y);
          pntCurrentPosition.y -= iMagnitude;
        }
      } else if (strCode.endsWith("Z")) {
        System.out.println("Cursor Backward Tab");
      } else if (strCode.endsWith("T")) {
        System.out.println("Cancel Previous character");
      } else if (strCode.endsWith("G")) {
        System.out.println("Horizontal Absolute");
      } else if (strCode.endsWith("I")) {
        System.out.println("Horizontal Tab");
      } else if (strCode.endsWith("L")) {
        System.out.println("Insert Line");
      } else if (strCode.endsWith("E")) {
        System.out.println("Next Line");
      } else if (strCode.endsWith("F")) {
        System.out.println("Preceeding Line");
      } else if (strCode.endsWith("R")) {
        System.out.println("Position Report");
      } else if (strCode.endsWith("W")) {
        System.out.println("Tab Control");
      } else if (strCode.endsWith("Y")) {
        System.out.println("Cursor Vertical Tab");
      } else if (strCode.endsWith("c")) {
        System.out.println("Device Attributes");
      } else if (strCode.endsWith("o")) {
        System.out.println("Define Area Qualification");
      } else if (strCode.endsWith("P")) {
        System.out.println("Delete Character");
      } else if (strCode.endsWith("M")) {
        System.out.println("Delete Line");
      } else if (strCode.endsWith("6n")) {
//        System.out.println("Cursor Position Report");
        theTelnet.getSender().sendData(ESC_LBRACE + pntCurrentPosition.x + ";" + pntCurrentPosition.y + "R");
      } else if (strCode.endsWith("5n")) {
        System.out.println("Status Report");
        theTelnet.getSender().sendData(ESC_LBRACE + "0n");
      } else if (strCode.endsWith("c")) {
        System.out.println("What are you");
        theTelnet.getSender().sendData(ESC_LBRACE + "?1;7c");
      } else if (strCode.endsWith("O")) {
        System.out.println("Erase In Area");
      } else if (strCode.endsWith("X")) {
        System.out.println("Erase Character");
      } else if (strCode.endsWith("L")) {
        System.out.println("Insert Line");
      }


  }

  /**
    * noCarrier
    **/
  public void noCarrier() {
    int i;

    strData = "";
    strData = "NO CARRIER";

    repaint();
    Toolkit.getDefaultToolkit().beep();
  }

  /**
    * main
    **/
  public static void main(String argv[]) {

  }


}
