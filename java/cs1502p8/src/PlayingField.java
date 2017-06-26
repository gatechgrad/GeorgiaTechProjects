/**
 * CS1502: Program #8
 *
 * <PRE>
 * Fence Game
 *
 * Revisions:  1.0  March 5, 1999
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, March 5, 1999
 */

import java.awt.*;
import java.awt.event.*;

public class PlayingField extends Canvas implements MouseListener {

//*** CONSTANTS
public static final int OFFSET = 25; //pixel offset
public static final int POSTSPACE = 50; //space between posts
public static final int POSTWIDTH = 3; //the width of a post
public static final boolean DEBUG = false; //debugging constant

//*** INSTANCE VARIABLES
private Toolkit myToolkit = Toolkit.getDefaultToolkit();
private Image imgRedpost; //a red post
private Image imgBluepost; //a blue post
private Image imgRedfence; //a red fence
private Image imgBluefence; //a blue fence
private Image imgRedvertfence; //a vertical red fence
private Image imgBluevertfence; //a vertical blue fence
private Image imgGrass = myToolkit.getImage("grass.gif"); //grass image

private PlayerMatrix playerOneMatrix = new PlayerMatrix(6, 5, "Player One");
  //player one's data
private PlayerMatrix playerTwoMatrix = new PlayerMatrix(5, 6, "Player Two");
  //player two's data

private FenceGame myFenceGame; //the current FenceGame

private String strWhosTurn; //who's turn is it
private boolean newDisplay; //for drawing grass and posts

/**
  * PlayingField - canvas constructor
  * Pre: new PlayingField is created
  * Post: variables are set
  *
  * @param currentGame the FenceGame that created this canvas
  */
public PlayingField(FenceGame currentGame) {
  super();
  myFenceGame = currentGame;

  strWhosTurn = "Player One";
  newDisplay = true;


//Images
  imgRedpost = myToolkit.getImage("redpost.gif");
  imgBluepost = myToolkit.getImage("bluepost.gif"); 
  imgRedfence = myToolkit.getImage("redfence.gif");
  imgBluefence = myToolkit.getImage("bluefence.gif");
  imgRedvertfence = myToolkit.getImage("redfence2.gif");
  imgBluevertfence = myToolkit.getImage("bluefence2.gif");
  imgGrass = myToolkit.getImage("grass.gif");

  addMouseListener(this);

  //I need it to paint here, but it doesn't want to do it...
  repaint();
}


/**
  * paint - paints all of the objects to the screen
  * Pre: window repaints
  * Post: paints all of the stuff to the canvas
  *
  * @param g the graphics object
  */
public void paint(Graphics g) {
  Post[][] currentArray;

    for (int i = 0; i < myFenceGame.getWindowWidth(); i += 50){
      for (int j = 0; j < myFenceGame.getWindowHeight(); j += 50){
        g.drawImage(imgGrass, i, j, 50, 50, null);
      }
    }

    for (int i = 0; i < playerOneMatrix.getRows(); i ++) {
      for (int j = 0; j < playerOneMatrix.getCols(); j ++) {
        g.drawImage(imgRedpost, ((i * POSTSPACE) + (OFFSET * 2) + POSTWIDTH),
          ((j * POSTSPACE) + (OFFSET * 2)), null);

        currentArray = playerOneMatrix.getPostArray();      
  
        if (currentArray[i][j].fenceOnRight()) {
          g.drawImage(imgRedfence, (i * POSTSPACE) + (OFFSET * 2) + POSTWIDTH,
            (j * POSTSPACE) + (OFFSET * 2), null);
        }

        if (currentArray[i][j].fenceBelow()) {
          g.drawImage(imgRedvertfence, (i * POSTSPACE) + (OFFSET * 2) +
            POSTWIDTH, (j * POSTSPACE) + (OFFSET * 2), null);
        }
    }
  }


  for (int i = 0; i < playerTwoMatrix.getRows(); i ++) {
    for (int j = 0; j < playerTwoMatrix.getCols(); j ++) {
      g.drawImage(imgBluepost, ((i * (POSTSPACE)) + (OFFSET * 3) + POSTWIDTH),
        ((j * POSTSPACE) + OFFSET), null);

      currentArray = playerTwoMatrix.getPostArray();      

      if (currentArray[i][j].fenceOnRight()) {
       g.drawImage(imgBluefence, ((i * POSTSPACE) + (OFFSET * 3) + POSTWIDTH),
         (j * POSTSPACE) + (OFFSET), null);
      }

      if (currentArray[i][j].fenceBelow()) {
        g.drawImage(imgBluevertfence, (i * POSTSPACE) + (OFFSET * 3) +
          POSTWIDTH, (j * POSTSPACE) + (OFFSET), null);
      }
    }
  }
}

/**
  * mouseClicked - actions performed when the user clicks on the canvas
  * Pre: user clicks on the canvas
  * Post: if the move is valid, a new fence is made
  *
  * @param e the mouse event
  */
public void mouseClicked(MouseEvent e) {

  int xCoord = e.getX();
  int yCoord = e.getY();

  //the compiler complained about not being initialized... that's why I
  //assigned this array here...
  Post[][] currentArray = playerOneMatrix.getPostArray();
                                
  Post[][] otherArray;

  if (strWhosTurn.equals("Player One")) {

      int iCell = (xCoord / 50); iCell--;
      int jCell = (yCoord / 50); jCell--;

    currentArray = playerOneMatrix.getPostArray();
    otherArray = playerTwoMatrix.getPostArray();

    if ((yCoord % 50 < 20) && (!otherArray[iCell][jCell].fenceBelow())) {

      if (DEBUG) System.out.println("Red (Horz): " + iCell + " " + jCell);
      currentArray[iCell][jCell].addRightFence();
      currentArray[iCell++][jCell].addLeftFence();

      strWhosTurn = "Player Two";
    }


    if ((xCoord % 50 < 20) && (!otherArray[iCell - 1][jCell + 1].fenceOnRight())) {

      if (DEBUG) System.out.println("Red (Vert): " + iCell + " " + jCell);
      currentArray[iCell][jCell].addDownFence();
      //new
      currentArray[iCell][jCell++].addUpFence();

      strWhosTurn = "Player Two";
    }

/*    if(playerOneMatrix.HasWon()) {
      if(DEBUG) System.out.println(strWhosTurn + " Won");
      myFenceGame.getTextField().setText(strWhosTurn + " Won");

    }
*/
    repaint();

  } else if (strWhosTurn.equals("Player Two"))  {
    xCoord -= 25;
    yCoord += 25;

    int iCell = (xCoord / 50); iCell--;
    int jCell = (yCoord / 50); jCell--;
    currentArray = playerTwoMatrix.getPostArray();
    otherArray = playerOneMatrix.getPostArray();

    if ((yCoord % 50 < 20) && (!otherArray[iCell + 1][jCell - 1].fenceBelow())) {

      if (DEBUG) System.out.println("Blue (Horz): " + iCell + " " + jCell);
      currentArray[iCell][jCell].addRightFence();
      currentArray[iCell++][jCell].addLeftFence();

      strWhosTurn = "Player One";
    }


    if ((xCoord % 50 < 20) && (!otherArray[iCell][jCell].fenceOnRight())) {

      if (DEBUG) System.out.println("Blue (Vert): " + iCell + " " + jCell);
      currentArray[iCell][jCell].addDownFence();
      currentArray[iCell][jCell++].addUpFence();

      strWhosTurn = "Player One";
    }

/*  if(playerTwoMatrix.HasWon()) {
    if (DEBUG) System.out.println(strWhosTurn + " Won");
    myFenceGame.getTextField().setText(strWhosTurn + " Won");

  }
*/
  
    repaint();
  }


  myFenceGame.getTextField().setText(strWhosTurn + "'s Turn");

}

public void mouseEntered(MouseEvent e) {
}
public void mouseExited(MouseEvent e) {
}
public void mousePressed(MouseEvent e) {
}
public void mouseReleased(MouseEvent e) {
}


}
