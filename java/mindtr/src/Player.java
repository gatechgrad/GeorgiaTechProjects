import java.awt.*;

class Player {
  /************************ CONSTANTS *******************************/
  public static final int TOTAL_WEDGES = 7;
  public static final int PIECE_RADIUS = 20;


  /************************ CLASS VARIABLES *************************/

  private Color playerColor; //Color value representing the color of the
                             //player as it will be displayed to the user.
  private String strName; //String representing the name of the player.  The
                          //name is taken from the user when the Player class
                          //is instantiated in the Menu class.  The name is
                          //used for display purposes.
  private int iDifficulty; //Integer (1 - 3) of the difficulty level of the
                           //player.  Each integer value corresponds to
                           //either a NOVICE, NORMAL, or ADVANCED difficulty
                           //levels.  Players will only be asked questions
                           //with corresponding difficulties.

                           //Levi:  The NOVICE, NORMAL, and ADVANCED constants
                           //can be found in the Question class and can be
                           //accessed from there

  private int iLocation;  //integer representing which space the player is
                          //currently located.  Corresponds with index in
                          //spaces array located in Board object.
  private boolean wedgeIsEarned[] = new boolean[TOTAL_WEDGES];
                             //array (length 6) of booleans with each index
                             //representing a category.  Once a player earns
                             //a wedge, the index corresponding to the
                             //category of the wedge earned is turned to
                             //'true'.  Each 'true' represents a wedge earned.
                             //Once all indexes of the array are set to 'true'
                             //the player has won the game.


  /************************ CLASS METHODS *************************/

  /**
    * Player - constructor, sets all the data for a new Player
    **/
  public Player(String strName, Color playerColor, int iDifficulty) {
    int i;

    this.strName = strName;
    this.playerColor = playerColor;
    this.iDifficulty = iDifficulty;

    iLocation = 72;

    for (i = 0; i < TOTAL_WEDGES; i++) {
      wedgeIsEarned[i] = false;
    }

  }

  /**
    * addWedge - tells the Player which wedge piece it has earned
    *          - updates wedgesEarned array
    *          - sets the value of wedgesEarned[piece] <= true
    **/
  private void addWedge(int iPiece) {
    
  }

  /**
    * displayPiece - displays the piece on the board
    *              - uses color, location, and wedgesEarned
    *              - draws a canvas of the piece, which is a circle divided
    *                  into six equal parts; the piece will be displayed
    *                  using its Color; all earned pie wedges will be
    *                  displayed within the piece using their respective
    *                  colors
    **/
  private void displayPiece() {

  }


  /**
    * hasWon() - tests if the Player has won
    *          - uses wedgesEarned
    *          - checks to see if all 6 values  of wedgesEarned are true
    **/
  public boolean hasWon() {
    
    if(wedgeIsEarned[1] &&
       wedgeIsEarned[2] &&
       wedgeIsEarned[3] &&
       wedgeIsEarned[4] &&
       wedgeIsEarned[5] &&
       wedgeIsEarned[6]
       ) {

       
         return true;
    } else {
      return false;
    }
  }

  /**
    * getName() - accessor method... returns name
    **/
  public String getName() {
    return strName;
  }

  /**
    * drawPiece - draws the player's piece
    **/
  public void drawPiece(Graphics g, int x, int y) {

    g.setColor(playerColor);
    g.fillOval(x, y, PIECE_RADIUS, PIECE_RADIUS);
    g.setColor(Color.black);
    //g.drawString(("Location: " + iLocation), x, y);

    g.setColor(Color.gray);
    g.drawOval(x, y, PIECE_RADIUS, PIECE_RADIUS);



    if (wedgeIsEarned[1]) {
      g.setColor(Color.green);
      g.fillArc(x, y, PIECE_RADIUS, PIECE_RADIUS, 0, 60);
    }
    if (wedgeIsEarned[2]) {
      g.setColor(new Color(156, 159, 16));
      g.fillArc(x, y, PIECE_RADIUS, PIECE_RADIUS, 60, 60);
    }
    if (wedgeIsEarned[3]) {
      g.setColor(Color.cyan);
      g.fillArc(x, y, PIECE_RADIUS, PIECE_RADIUS, 120, 60);
    }
    if (wedgeIsEarned[4]) {
      g.setColor(Color.orange);
      g.fillArc(x, y, PIECE_RADIUS, PIECE_RADIUS, 180, 60);
    }
    if (wedgeIsEarned[5]) {
      g.setColor(Color.yellow);
      g.fillArc(x, y, PIECE_RADIUS, PIECE_RADIUS, 240, 60);
    }
    if (wedgeIsEarned[6]) {
      g.setColor(Color.red);
      g.fillArc(x, y, PIECE_RADIUS, PIECE_RADIUS, 300, 60);
    }



  }

  /**
    * getDifficulty
    **/
  public int getDifficulty() {
    return iDifficulty;
  }


  /**
    * getLocation
    **/
  public int getLocation() {
    return iLocation;
  }

  /**
    * setLocation
    **/
  public void setLocation(int iLocation) {
    this.iLocation = iLocation;
  }

  /**
    * setWedge
    **/
  public void setWedge(int iWedge) {
    wedgeIsEarned[iWedge] = true;

  }


  /**
    * toString - prints a String representing the object
    **/
  public String toString() {
    String strToReturn;
    int i;

    strToReturn = ("Name: " + strName + '\n' +
                   "Color: " + playerColor + '\n' +
                   "Difficulty: " + iDifficulty + '\n' +
                   "Location: " + iLocation + '\n');
    for (i = 0; i < TOTAL_WEDGES; i++) {
      strToReturn += ("Wedge " + i + " earned: " + wedgeIsEarned[i] + '\n');
    }

    return strToReturn;
  }

}
