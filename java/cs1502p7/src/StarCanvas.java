/**
 * CS1502: Program #7
 *
 * <PRE>
 * Star Viewer
 *
 * Revisions:  1.0  February 26, 1999
 *                  Created class StarCanvas 
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, February 26, 1999
 */

//NOTE:  I copied my float to int code a few times in this class
//       The reson why I didn't write a method for this is because
//       the conversion depends on wether it is an x coordinate or
//       a y coordinate (because of the scaling factor)

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.io.*;
//import java.math.*;

public class StarCanvas extends Canvas implements MouseMotionListener {

//*** CONSTANTS
public static final boolean DEBUG = false; //toggles debugging statements

public static final int WINDOWHEIGHT = 500; //the height of the window
public static final int WINDOWWIDTH = 500;  //the width of the window

public static final int STAR_NAME = 0; //the position in the array of name
public static final int STAR_X = 1; //the position in the array of x
public static final int STAR_Y = 2; //the position in the array of y
public static final int STAR_1ST_NEIGHBOR = 3; //the position in the array
                                               //of the first neighbor
public static final int XCENTER = 375; //the starting x offset
public static final int YCENTER = 200; //the starting y offset
public static final float XDISTORTION = 15; //x scaling factor
public static final float YDISTORTION = 1; //y scaling factor

//*** INSTANCE VARIABLES

private Vector starVector; //the vector which holds all of the stars

private int xOffset; //how far off to place the star on x axis
private int yOffset; //how far off to place the star on the y axis

private int iZoom; //the current zoom
private boolean NamesAreOn; //are names on
private boolean LinesAreOn; //are lines on
private boolean GridIsOn;  //is the grid on
private boolean QuizIsOn;  //is the quiz on
private int iStarSize;  //how large is the star

private Color
  pointColor = new Color (255, 255, 255), //the color of a star
  lineColor = new Color(0, 255, 0), //the color of a line
  fontColor = lineColor.darker();  //the color of the names

private P7
  controlPanel; //the panel with the control buttons

//*** METHODS

/**
 *  StarCanvas - the constructor for this class... sets instance variables
 *  Pre - a new StarCanvas
 *  Post - variables are set
 *  @param controlPanel the frame with the control buttons and textfield
 *
 */
public StarCanvas(P7 controlPanel) {
  super();
  iZoom = 100;
  setBackground(new Color(0,0,0));
  xOffset = XCENTER;
  yOffset = YCENTER;
  NamesAreOn = true;
  LinesAreOn = true;
  GridIsOn = false;
  this.controlPanel = controlPanel;
  iStarSize = 2;
  getStars();

  addMouseMotionListener(this);
}


/**
 *  getStars - adds stars to the star vector
 *  Pre - must have StarReader in directory
 *  Post - stars are added to the star vector
 *
 */
public void getStars() {
  int i = 0;
  starVector = new Vector();

  FileDialog myFileDialog = new FileDialog(controlPanel, "Load File",
    FileDialog.LOAD);
  myFileDialog.setVisible(true);

    String strFilename = myFileDialog.getFile();


  try {
    StarReader sr = new StarReader (strFilename);
    Object[] o = sr.nextStar ();     

      while (o != null) {
        // process the array 'o'

       starVector.addElement(o);
       if (DEBUG) System.out.println("*** new star");

       if (DEBUG) System.out.println(o[0]);
       if (DEBUG) System.out.println(o[1]);
       if (DEBUG) System.out.println(o[2]);
       if (DEBUG) System.out.println(o[3]);

        o = sr.nextStar ();
      } 
  }

  catch (IOException e) {     
    // do nothing
  }
}


/**
 *  setZoom - resets the zoom
 *  Pre - call from P7
 *  Post - zoom is set properly
 *  @param iDifference the number to add/subtract from the zoom
 *
 */
public void setZoom (int iDifference) {
  if ((iZoom + iDifference) > 0) {
    iZoom += iDifference;
  }
  repaint();
}


/**
 *  setXOffset - sets the xoffset
 *  Pre - none
 *  Post - xOffset is set
 *  @param iDifference the number to add to the offset
 *
 */
public void setXOffset(int iDifference) {
  xOffset += iDifference;
  repaint();
}

/**
 *  setYOffset - same as setXOffset, except for the y coordinate
 *  Pre - none
 *  Post - yOffset is set
 *  @param iDifference the difference
 *
 */
public void setYOffset(int iDifference) {
  yOffset += iDifference;
  repaint();
}

/**
 *  toggleLines - turns lines on/off
 *  Pre - none
 *  Post - sets LinesAreOn to the opposite of the current state
 *
 */
public void toggleLines() {
  LinesAreOn = !LinesAreOn;
  repaint();
}

/**
 *  toggleNames - toggles the names
 *  Pre - none
 *  Post - sets NamesAreOn to it's complement
 *
 */
public void toggleNames() {
  NamesAreOn = !NamesAreOn;
  repaint();
}

/**
 *  paint - draws the star data to the canvas
 *  Pre - data must be filled/valid
 *  Post - star data is drawn to the screen
 *  @param g used to draw things
 *
 */
public void paint(Graphics g) {
  int iStarX;
  int iStarY;

  Object[] currentStar = null;

  for (int i = 0; i < starVector.size() - 1; i++) {

    currentStar = (Object[]) starVector.elementAt(i);

    String strStarName = (String) currentStar[0];

    if (DEBUG) System.out.println(currentStar[1]);
    if (DEBUG) System.out.println(currentStar[2]);

    Float flStarX =  ( (Float) (currentStar[1]));
    Float flStarY =  ( (Float) (currentStar[2]));

    flStarX = new Float(flStarX.floatValue() * XDISTORTION);
    flStarY = new Float(flStarY.floatValue() * YDISTORTION);

    iStarX = flStarX.intValue();
    iStarY = flStarY.intValue();

    iStarX = (int) (iStarX * (iZoom * 0.01) ) - xOffset;
    iStarY = (int) (iStarY * (iZoom * 0.01) ) - yOffset;

    if (DEBUG) System.out.println(iStarX);
    if (DEBUG) System.out.println(iStarY);

    if (LinesAreOn) {
      for (int j = 3; j < currentStar.length; j++) {
        int[] secondPoint = new int[2];

        secondPoint = getAdjStar((String) currentStar[j]);

        g.setColor(lineColor);

        if (DEBUG) {
          System.out.println(0 - iStarX);
          System.out.println(0 - iStarY);
          System.out.println(0 - secondPoint[0]);
          System.out.println(0 - secondPoint[1]);
        }

        //We have to flip the image across the x-y axis
        g.drawLine(0 - iStarX, 0 - iStarY, 0 - secondPoint[0],
          0 - secondPoint[1]);

      }

      g.setColor(lineColor);

    }

  if (GridIsOn) {

    for (int x = 0; x < WINDOWWIDTH; x += 5) {
      for (int y = 0; y < WINDOWHEIGHT; y += 5) {
        g.drawLine(x, y, x, y + WINDOWHEIGHT);
        g.drawLine(y, x, y + WINDOWWIDTH, x);

      }
    }


  }

  if (QuizIsOn) {
//   Under construction

  }


    g.setColor(pointColor);

    g.fillOval(0 - iStarX, 0 - iStarY, iStarSize, iStarSize);
    
    g.setColor(fontColor);

    if (NamesAreOn) {
      g.drawString(strStarName, 0 - iStarX, 0 - iStarY);
    }
  }
}


/**
 *  getAdjStar - returns the corrdinates of an adjacent star, used for
 *               drawing lines
 *  Pre - call from paint
 *  Post - adjacent star's coordinates are returned
 *  @param strName the star that we're looking for
 *  @return a 2 cell array containing the star's coordinates
 *
 */
public int[] getAdjStar (String strName) {

//ASSUMPTIONS:  The adjacency is actually a real star in the vector
//           :  No two stars have the same name

  int[] xyCoord = new int[2];
  //add 'dummy' values to satisfy compiler
  xyCoord[0] = 0;
  xyCoord[1] = 0;

  for (int i = 0; i < starVector.size(); i++) {
    Object[] currentStar = (Object[]) starVector.elementAt(i);
    String strToCompareName = (String) currentStar[0];

    if (strToCompareName.equals(strName)) {

      Float flStarX =  ( (Float) (currentStar[1]));
      Float flStarY =  ( (Float) (currentStar[2]));

      flStarX = new Float(flStarX.floatValue() * XDISTORTION);
      flStarY = new Float(flStarY.floatValue() * YDISTORTION);

      int iStarX = flStarX.intValue();
      int iStarY = flStarY.intValue();

      iStarX = (int) (iStarX * (iZoom * 0.01) ) - xOffset;
      iStarY = (int) (iStarY * (iZoom * 0.01) ) - yOffset;

        xyCoord[0] = iStarX;
        xyCoord[1] = iStarY;

      } else {
        //keep looping
      }
    }
    return(xyCoord);
}


/**
 *  setColor - sets the color of the lines and names
 *  Pre - call from Option Dialog
 *  Post - color is changed
 *  @param newColor the new color
 *
 */
public void setColor (Color newColor){
  lineColor = newColor.brighter().brighter();
  fontColor = newColor.darker();
  repaint();
}

/**
 *  setStarSize - sets the star size
 *  Pre - call from OptionDialog
 *  Post - star size is set
 *  @param newStarSize the new size of the stars
 *
 */
public void setStarSize (int newStarSize) {
  iStarSize = newStarSize;

}

/**
 *  getIZoom - returns the current zoom
 *  Pre - none
 *  Post - returns the current zoom
 *  @return the current zoom
 *
 */
public int getIZoom() {
  return (iZoom);
}


/**
 *  mouseDragged - does nothing
 *  Pre - none
 *  Post - none
 *  @param e the event
 *
 */
public void mouseDragged(MouseEvent e) {

}

/**
 *  mouseMoved - checks to see if the cursor is over a star... if so, then
 *               it sends a string containing the star's data to the
 *               controlPanel
 *  Pre - pointer must be moved
 *  Post - star data printed to the textfield
 *  @param e the mouse Event
 *
 */
public void mouseMoved(MouseEvent e) {

  int xCoord = e.getX();
  int yCoord = e.getY();

  for (int i = 0; i < starVector.size() - 1; i++) {
    Object[] currentStar = (Object[]) starVector.elementAt(i);

    Float flStarX =  ( (Float) (currentStar[1]));
    Float flStarY =  ( (Float) (currentStar[2]));

    flStarX = new Float(flStarX.floatValue() * XDISTORTION);
    flStarY = new Float(flStarY.floatValue() * YDISTORTION);

    int iStarX = flStarX.intValue();
    int iStarY = flStarY.intValue();

    iStarX = (int) (iStarX * (iZoom * 0.01) ) - xOffset;
    iStarY = (int) (iStarY * (iZoom * 0.01) ) - yOffset;

    iStarX = (0 - iStarX);
    iStarY = (0 - iStarY);

    if (DEBUG) System.out.println(xCoord + " " + yCoord);
    if (DEBUG) System.out.println(iStarX + " " + iStarY);

    if ( ((xCoord > iStarX - 5) && (xCoord < iStarX + 5))

        &&

         ((yCoord > iStarY - 5) && (yCoord < iStarY + 5))) {
      String starName = (String) currentStar [STAR_NAME];
      controlPanel.setTextField(starName + ": " + flStarX + " hours, " +
          flStarY + " degrees");
      if (DEBUG) System.out.println( xCoord + " " + yCoord + " starName");
      if (DEBUG) System.out.println( iStarX + " " + iStarY + " starName");


    }
  }

}




}//endclass P7





