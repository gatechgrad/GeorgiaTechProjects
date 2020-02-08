import javax.swing.*;
import java.awt.*;

class Card {

  /********************** CONSTANTS **********************/
  public static final Rectangle CARD_BOUNDS = new Rectangle(0, 0, 100, 300);
  public static final String ELEM_EARTH = "Earth";
  public static final String ELEM_AERO = "Aero";
  public static final String ELEM_FIRE = "Fire";
  public static final String ELEM_ICE = "Ice";
  public static final String ELEM_WATER = "Water";
  public static final String ELEM_HOLY = "Holy";
  public static final String ELEM_POISON = "Poison";
  public static final String ELEM_THUNDER = "Thunder";
  public static final String ELEM_NONE = "None";

  public static final String CARD_RED = "Red";
  public static final String CARD_BLUE = "Blue";
  public static final String CARD_NEUTRAL = "Neutral";



  /********************** CLASS VARIABLES **********************/
  private int iLeft, iRight, iTop, iBottom; //Card numbers
  private int iLevel;                       //Card level
  private String strName;                   //Card name
  private String strAttribute;              //Card attribute
  private Image ImgRedCard;                    //Card image
  private Image ImgBlueCard;                    //Card image
  private Board theBoard;
  private Rectangle rectCardBounds;
  private boolean isHighlighted;
  private String strCardColor;
  private int iStock;

  public Card(int newTop, int newLeft, int newBottom, int newRight,
              int newLevel, String newAttribute, String newName,
              Board parentBoard) {
    String strFileName;
    iLeft = newLeft;
    iRight = newRight;
    iTop = newTop;
    iBottom = newBottom;
    strName = newName;
    iLevel = newLevel;
    strAttribute = newAttribute;
    theBoard = parentBoard;
    iStock = 0;


    isHighlighted = false;
    rectCardBounds = new Rectangle(0, 0, 0 ,0);


    //load the Card image
    MediaTracker tempMediaTracker = new MediaTracker(theBoard);


    strFileName = strName.replace(' ', '_');
    strFileName += ".gif";

    try {
      ImgRedCard = Toolkit.getDefaultToolkit().createImage(TripleTriad.THE_RESOURCES.getResource("red_" + strFileName));
      ImgBlueCard = Toolkit.getDefaultToolkit().createImage(TripleTriad.THE_RESOURCES.getResource("blue_" + strFileName));
    } catch (NullPointerException e) {
      System.out.println("Null pointer exception");
    }

/*
    ImgRedCard = Toolkit.getDefaultToolkit().createImage(theResources.getResource("red_" + strFileName));
    ImgBlueCard = Toolkit.getDefaultToolkit().createImage(theResources.getResource("blue_" + strFileName));
*/
    tempMediaTracker.addImage(ImgRedCard, 0);
    tempMediaTracker.addImage(ImgBlueCard, 0);
    try { tempMediaTracker.waitForAll(); } catch (InterruptedException e) { }
    

  }

  /**
    * setColor - sets the card color
    **/
  public void setColor(String newColor) {
    strCardColor = newColor;
  }

  /**
    * getColor - returns the card's color
    **/
  public String getColor() {
    return strCardColor;
  }

  /**
    * dupeCard - makes a clone of the card
    **/
  public Card dupeCard() {
    Card theDupeCard;

    theDupeCard = new Card(iTop, iLeft, iBottom, iRight, iLevel, strAttribute,
                           strName, theBoard);

    theDupeCard.setColor(strCardColor);
    return theDupeCard;

  }


  public void drawCard(Graphics g, int x, int y, int iWidth, int iHeight,
                       int iHighlightOffset) {
    Graphics2D g2 = (Graphics2D) g;
    if (isHighlighted) {
      x = iHighlightOffset;
    }
    try {
      if (strCardColor.equals(CARD_RED)) {
        g2.drawImage(ImgRedCard, x, y, iWidth, iHeight, theBoard);
      } else if (strCardColor.equals(CARD_BLUE)) {
        g2.drawImage(ImgBlueCard, x, y, iWidth, iHeight, theBoard);
      } else {
        System.out.println("Error: card has no color");
      }
    } catch (NullPointerException e) {
      g2.setColor(Color.white);
      g2.fillRect(x, y, iWidth, iHeight);
      g2.setColor(Color.black);
      g2.drawRect(x, y, iWidth, iHeight);
      g2.drawString(strName, (x + 2), (y + 20));

    }


    rectCardBounds = new Rectangle(x, y, iWidth, iHeight);


  }

  /**
    * mouseIsOver - returns true if the point is in this card
    **/
  public boolean mouseIsOver(Point pntToCheck) {
    if (rectCardBounds.contains(pntToCheck)) {
      return true;
    } else {
      return false;
    }
  }

  /**
    * setHightlighted - sets the highlight attribute
    **/
  public void setHighlighted(boolean newHighlightAttr) {
    isHighlighted = newHighlightAttr;
  }



  /**
    * getName - returns the name of the card
    **/
  public String getName() {
    return strName;
  }

  /**
    * getPosition - returns the top left coordinates of the card
    **/
  public Point getPosition() {
    return (new Point(rectCardBounds.x, rectCardBounds.y));

  }

  /**
    * getLeft - returns left number
    **/
   public int getLeft() {
     return iLeft;
   }

  /**
    * getRight - returns right number
    **/
   public int getRight() {
     return iRight;
   }

  /**
    * getTop - returns top number
    **/
   public int getTop() {
     return iTop;
   }

  /**
    * getBottom - returns bottom number
    **/
   public int getBottom() {
     return iBottom;
   }

  /**
    * getLevel - returns the card level
    **/
   public int getLevel() {
     return iLevel;
   }

  /**
    * getStock - returns how many of this card is available
    **/
  public int getStock() {
    return iStock;
  }

  /**
    * addStock - adds the number passed in to the amount of stock
    **/
  public void addStock(int iAdd) {
    iStock += iAdd;
  }


  /**
    * toString - returns a string representing the card
    **/
  public String toString() {
    String strToReturn = "";

    strToReturn += ("************************************\n");
    strToReturn += ("* Card: " + strName + "\n");
    strToReturn += ("*     " + iTop + "\n");
    strToReturn += ("*   " + iLeft + "  " + iRight + "\n");
    strToReturn += ("*     " + iBottom + '\n');
    strToReturn += ("* Element: " + strAttribute + '\n');
    strToReturn += ("* Level: " + iLevel + "\n");
    strToReturn += ("************************************\n\n");

    return strToReturn;
  }


}
