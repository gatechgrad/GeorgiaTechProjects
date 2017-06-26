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


public class PlayerMatrix {


private int iRows; // the rows of the matrix
private int iCols;// the columns of the matrix
private String strPlayer; // the player
private boolean hasWon = false; //has this player won??

private Post[][] postArray; // the array of posts

/**
  * PlayerMatrix - the constructor of this class... sets up variables
  * Pre: a new PlayerMatrix has to be created
  * Post: variables are set
  *
  * @param newRows the rows this matrix has
  * @param newCols the columns this matrix has
  * @param newPlayer who's matrix is this
  */

public PlayerMatrix(int newRows, int newCols, String newPlayer) {
  iRows = newRows;
  iCols = newCols;

  strPlayer = newPlayer;

  postArray = new Post[iRows][iCols];

  matrixSetup ();
}


/**
  * matrixSetup - sets the posts in the matrix
  * Pre: call from constructor
  * Post: posts are setup properly
  *
  */

public void matrixSetup() {

  for (int i = 0; i < iRows; i++) {
    for (int j = 0; j < iCols; j++) {

      if ((i == 0) | (i == iCols) && (strPlayer.equals("Player One"))
          && (j != iCols)) {
        postArray[i][j] = new Post(true, true, false, false);

      } else if ((i == 0) | (i == iCols) && (strPlayer.equals("Player One"))
          && (j == iCols - 2 )) {
        postArray[i][j] = new Post(false, false, true, true);




      } else if ((j == 0) | (j == iRows) && (strPlayer.equals("Player Two"))
          && (i != iCols - 2 )) {
        postArray[i][j] = new Post(false, false, true, true);

      } else {

      postArray[i][j] = new Post();
      }
    }
  }
}



/**
  * getPostArray - returns this array
  * Pre: nothing
  * Post: returns the 2D array
  *
  * @return the array in this class
  */

public Post[][] getPostArray() {
  return(postArray);
}


/**
  * HasWon - checks to see if this player has won the game
  *          I was really close to getting this to work
  * Pre: player makes a move
  * Post: returns wheter the player has won or not
  * @return has the player won??
  *
  */
public boolean HasWon() {

  HasWonHelper(0, 0, "left");

  if (hasWon == true) {
    System.out.println("*** Somebody won!!");
  }
  return(hasWon);
}


/**
  * HasWonHelper - does the traversing of the array
  * Pre: call from HasWon
  * Post: sets hasWon to true if the player has won
  *
  * @param i the current row
  * @param j the current column
  * @param comingFrom the direction the check is coming from (so it
  *        won't double check a cell
  */
public void HasWonHelper (int i, int j, String comingFrom) {

    System.out.println ("*** Entered Has Won Helper");
    System.out.println ("*** Current Cell :" + i + ", " + j);


    if ( i == getRows()) {
      hasWon = true;
    } else {

      if (postArray[i][j].fenceOnLeft() && (comingFrom != "left")) {
        System.out.println ("*** Go left");
        HasWonHelper(i - 1, j, "right");
      }
      
      if (postArray[i][j].fenceBelow() && (comingFrom != "down")) {
        System.out.println ("*** Go down");
        HasWonHelper(i, j + 1, "up");
      }

      if (postArray[i][j].fenceOnRight() && (comingFrom != "right")) {
        System.out.println ("*** Go right");
        HasWonHelper(i + 1, j, "left");
      }

      if (postArray[i][j].fenceAbove() && (comingFrom != "up")) {
        System.out.println ("*** Go down");
        HasWonHelper(i, j - 1, "down");
      }
    }
}


/**
  * getRows - returns the rows in this matrix
  * Pre: nothing
  * Post: the number of rows is returned
  *
  * @return the number of rows
  */
public int getRows() {
  return(iRows);
}


/**
  * getCols - returns the number of columns in this matrix
  * Pre: nothing
  * Post: returns the number of columns
  *
  * @return the number of columns
  */

public int getCols() {
  return(iCols);
}

}
