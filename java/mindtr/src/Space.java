import java.awt.*;

class Space {
  /************************ CONSTANTS *******************************/
  public static final int MAX_ADJACENT_SPACES = 6;
  public static final int NO_ADJ = 99;

  public static final String CATEGORY_GREEN = "Science";
  public static final String CATEGORY_YELLOW = "Geography";
  public static final String CATEGORY_CYAN = "Math";
  public static final String CATEGORY_BROWN = "History";
  public static final String CATEGORY_RED = "Grammar";
  public static final String CATEGORY_ORANGE = "Current Events";


  /************************ CLASS VARIABLES *************************/

  //not used - hardcoded into the gif
  private Color myColor;  //Color representing the color the space as it will
                          //be displayed to the user

  private int iPieValue;  //Integer value (0-6) representing the wedge the
                          //player gets if they correctly answer the question.
                          //Values 1 through 6 are assigned uniquely to six
                          //different Spaces indicating they they get a unique
                          //wedge by answering the question.  All other Spaces
                          //get a iPieValue of 0 indicating they get no wedge
                          //the they get the question right
  
  private String strCategory; //String representing the name of the category
                              //of that space.  Used to determine what
                              //question to ask once the player lands on that
                              //space

  //Well, they screwed up this in the design... what are we supposed
  //to fill the empty cells with??  We can't use zero, because zero
  //is one of the spaces... We can't test for null because all the cells
  //are initiated to zero (unless Sun changed that).  I guess I'll just use
  //99.  If we have time this needs to be replaced with a vector
  private int iAdjacentSpaces[] = new int[MAX_ADJACENT_SPACES];
                              //An array (length 6) of integers representing
                              //the locations of spaces on the board.  The
                              //array's length was set to six because the
                              //maximum number of spaces a space can have
                              //adjacent to it is six (center space);
  private int iLocation;  //Unique integer value (0 - 72) corresponding to
                          //its location on the Board object.  Each location
                          //integer directly matches the index of that space
                          //in the Spaces array stored in Board.

  //not used
  private Point PCenter;  //Coordinates of center of space.  The coordinates
                          //are used when displaying a gamepiece in the space
                          //which must occupy the center of the space.


  private Polygon PBounds;

  /************************ CLASS METHODS *************************/

  /**
    * Space - constructor, makes a new instance of Space
    **/
  public Space(int iPieValue, String strCategory, int[] iAdjacentSpaces,
               int iLocation, Polygon PBounds) {
    
    this.iPieValue = iPieValue;
    this.strCategory = strCategory;
    this.iAdjacentSpaces = iAdjacentSpaces;
    this.iLocation = iLocation;
    this.PBounds = PBounds;


  }

  /**
    * initialize - creates the Space
    *            - sets myColor and iPieValue
    *            - sets what color the space is and whether this space is a
    *                wedge space
    * @param String color
    * @param int iWedge
    **/
  private void initialize() {

  }


  /**
    * isInSpace - determines if the Space has the coordinates
    *           - uses corners
    *           - if the coordinates are in the Space, returns true; else,
    *               returns false
    * @param int x
    * @param int y
    **/
  public boolean isInSpace(int x, int y) {
    return PBounds.contains(x, y);
  }

  /**
    * isOccupied - tests for occupation by any Player
    *            - uses Players list from Mindtrial class
    *            - loops through the array and returns any
    *                Player that has Location equal to this.Location
    * @param Player[]
    * @return Player
    **/
  private Player isOccupied(Player[] playerArray) {

    return null;
  }

  /**
    * isWedgeSpace - determines whether to give the player a pie piece
    *              - returns iPieValue
    *              - returns which wedge piece the player gets for answering
    *                  the question correctly based on the space's color for
    *                  answering the question correctly
    * @return int
    **/
  private int isWedgeSpace() {

    return 0;
  }

  /**
    * getLocation
    **/
  public int getLocation() {
    return iLocation;
  }

  /**
    * getAdjacentArray
    **/
  public int[] getAdjacentArray() {
    return iAdjacentSpaces;
  }

  /**
    * getCategory()
    **/
  public String getCategory() {
    return strCategory;
  }

  /**
    * getWedgeValue
    **/
  public int getWedgeValue() {
    return iPieValue;
  }

  /**
    * getBounds
    **/
  public Rectangle getBounds() {
    return PBounds.getBounds();
  }

}
