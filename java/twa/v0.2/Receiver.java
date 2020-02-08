import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

class Receiver extends JTextArea implements Runnable {

  /** CONSTANTS **/

  /** INSTANCE VARIABLES **/
  DataInputStream theInputStream;
  int iLinesPerScreen;
  int x, y, iReceived, iLines;
  int iSpacing;
  String[] strData;
  String strBuffer;
  String strLine;
  Thread threadFetcher;
  FontMetrics fm;
  Parser theParser;
  TWA theTWA;
  boolean displayText;

  Image imgBuffer;
  Graphics graphicsBuffer;

  /**
    * Receiver - gets the data
    **/
  public Receiver(DataInputStream theInputStream, TWA theTWA) {
    this.theInputStream = theInputStream;

    x = 0;
    y = 0;
    iReceived = 0;
    iLines = 0;
    iLinesPerScreen = 24;
    iSpacing = 14;

    this.theTWA = theTWA;
    theParser = theTWA.getParser();

    strData = new String[iLinesPerScreen];
    strBuffer = "";
    strLine = "";

    displayText = false; //display to the component or display text to console

    (threadFetcher = new Thread(this)).start();

    this.setBackground(Color.white);
    this.setForeground(Color.green);



  }


  /**
    * run
    **/
  public void run() {
    //System.out.println("Running");
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
        synchronized (strBuffer) {

          if (theInputStream != null) {

            iAvailable = theInputStream.available();
      
            if (iAvailable > 0) {
              //System.out.println("iAvailable: " + iAvailable);
              
              for (j = 0; j < iAvailable; j++) {
                theChar = (char) theInputStream.readByte();

                if ((theChar == '\r')) {

                  theParser.parseString(strLine.trim());

                  if (iLines > (iLinesPerScreen - 1)) {
                    for (i = 0; i < (iLinesPerScreen - 1); i++) {
                      strData[i] = strData[i + 1];
                    }
                    strData[iLinesPerScreen - 1] = strLine;

                  } else {
                    strData[iLines] = strLine; //it's a line on the first screen, no scrolling needed
                  }
                  strLine = "";
                  iLines++;

                }

                //if anyone wants to add color support, do it here
                //else I will ignore the color code characters
                if ((theChar == 27) && willDisplay) {
                  willDisplay = false;
                } else if (!willDisplay && theChar == 'm') {
                  willDisplay = true;
                } else if (willDisplay) {
                  strBuffer = strBuffer + theChar;
                  strLine = strLine + theChar;
                }
              }
              if (getGraphics() == null) {
                while(getGraphics() == null) {
                }
              }
              redraw();
            }



          } else {
            strData[0] = "1";
            strData[1] = "2";
            strData[2] = "3";
            strData[3] = "4";
            strData[4] = "5";
            strData[5] = "6";
            strData[6] = "7";
            strData[7] = "8";
            strData[8] = "9";
            strData[9] = "10";
            strData[10] = "Cannot Connect to Server";
            strData[11] = "12";
            strData[12] = "13";
            strData[13] = "14";
            strData[14] = "15";
            strData[15] = "16";
            strData[16] = "17";
            strData[17] = "18";
            strData[18] = "19";
            strData[19] = "20";
            strData[20] = "21";
            strData[21] = "22";
            strData[22] = "23";
            strData[23] = "24";
          
            isConnected = false;
          }
        }
      }

    } catch (EOFException e) {
    } catch (IOException e) { }
  }



/*
          theChar = (char) theInputStream.readByte();


          if ((theChar == '\r')) {

            theParser.parseString(strLine.trim());

            if (iLines > (iLinesPerScreen - 1)) {
              for (i = 0; i < (iLinesPerScreen - 1); i++) {
                strData[i] = strData[i + 1];
              }
              strData[iLinesPerScreen - 1] = strLine.trim();

            } else {
              strData[iLines] = strLine; //it's a line on the first screen, no scrolling needed
            }
            strLine = "";
            iLines++;

          }

          //if anyone wants to add color support, do it here
          //else I will ignore the color code characters
          if ((theChar == 27) && willDisplay) {
            willDisplay = false;
          } else if (!willDisplay && theChar == 'm') {
            willDisplay = true;
          } else if (willDisplay) {
            strBuffer = strBuffer + theChar;
            strLine = strLine + theChar;
            needToUpdate = true; //only need to paint when we have a letter
          }

          } else {
            strData[0] = "1";
            strData[1] = "2";
            strData[2] = "3";
            strData[3] = "4";
            strData[4] = "5";
            strData[5] = "6";
            strData[6] = "7";
            strData[7] = "8";
            strData[8] = "9";
            strData[9] = "10";
            strData[10] = "Cannot Connect to Server";
            strData[11] = "12";
            strData[12] = "13";
            strData[13] = "14";
            strData[14] = "15";
            strData[15] = "16";
            strData[16] = "17";
            strData[17] = "18";
            strData[18] = "19";
            strData[19] = "20";
            strData[20] = "21";
            strData[21] = "22";
            strData[22] = "23";
            strData[23] = "24";


        //    JOptionPane.showMessageDialog(this, "Connection lost to host",
        //    "Connection Lost", JOptionPane.WARNING_MESSAGE);
            isConnected = false;
          }

          if ((getGraphics() != null) && (needToUpdate)) {
            needToUpdate = false;
          }

 */

  /**
    * setFont - sets the terminal font
    *           returns the font height
    **/
  public int setFont(String theFont, int iFontSize) {
    this.setFont(new Font(theFont, Font.PLAIN, iFontSize));
    fm = this.getFontMetrics(this.getFont());
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
    * redraw
    **/
  private void redraw() {
    int i;
    String strToPrint;

    strToPrint = "";

    for (i = 0; i < iLinesPerScreen; i++) {
      if (strData[i] != null) {
        strToPrint += strData[i].trim();
      }
    }

    this.setText(strToPrint);

  }


  /**
    * main
    **/
  public static void main(String argv[]) {

  }


}
