import java.awt.*;

class Rules {

  /********************** CONSTANTS **********************/
  public static final int TRADE_ONE = 1;
  public static final int TRADE_DIFF = 2;
  public static final int TRADE_DIRECT = 3;
  public static final int TRADE_ALL = 4;



  /********************** CLASS VARIABLES **********************/
  private boolean isOpen;
  private boolean isSuddenDeath;
  private boolean isRandom;
  private boolean isSame;
  private boolean isPlus;
  private boolean isSameWall;
  private boolean isElemental;

  private int iTradeRule;

  /**
    * Rules - constructor
    **/
  public Rules(int newTradeRule, boolean newOpen, boolean newSuddenDeath,
               boolean newRandom, boolean newSame, boolean newPlus,
               boolean newSameWall, boolean newElemental) {

    isOpen = newOpen;
    isSuddenDeath = newSuddenDeath;
    isRandom = newRandom;
    isSame = newSame;
    isPlus = newPlus;
    isSameWall = newSameWall;
    isElemental = newElemental;

    iTradeRule = newTradeRule;

  }

  /**
    * getTradeRule - returns the trade rule
    **/
  public int getTradeRule() {
    return iTradeRule;
  }

  /**
    * getIsOpen - returns wether or not the open rule is used
    **/
  public boolean getIsOpen() {
    return isOpen;
  }

  /**
    * getIsSuddenDeath - returns wether or not the sudden death is used
    **/
  public boolean getIsSuddenDeath() {
    return isSuddenDeath;
  }

  /**
    * getIsRandom - returns wether or not the random rule is used
    **/
  public boolean getIsRandom() {
    return isRandom;
  }

  /**
    * getIsSame - returns wether or not the same rule is used
    **/
  public boolean getIsSame() {
    return isSame;
  }

  /**
    * getIsPlus - returns wether or not the plus rule is used
    **/
  public boolean getIsPlus() {
    return isPlus;
  }

  /**
    * getIsSameWall - returns wether or not the same wall rule is used
    **/
  public boolean getIsSameWall() {
    return isSameWall;
  }

  /**
    * getIsElemental - returns wether or not the elemental rule is used
    **/
  public boolean getIsElemental() {
    return isElemental;
  }


  /**
    * setTradeRule - sets the trade rule
    **/
  public void setTradeRules(int newTradeRule) {
    iTradeRule = newTradeRule;
  }

  /**
    * setOpen - sets the open rule
    **/
  public void setOpen(boolean newOpenRule) {
    isOpen = newOpenRule;
  }

  /**
    * setSuddenDeath - sets the sudden death rule
    **/
  public void setSuddenDeath(boolean newSuddenDeathRule) {
    isSuddenDeath = newSuddenDeathRule;
  }

  /**
    * setRandom - sets the random rule
    **/
  public void setRandom(boolean newRandomRule) {
    isRandom = newRandomRule;
  }

  /**
    * setSame - sets the same rule
    **/
  public void setSame(boolean newSameRule) {
    isSame = newSameRule;
  }

  /**
    * setPlus - sets the Plus rule
    **/
  public void setPlus(boolean newPlusRule) {
    isPlus = newPlusRule;
  }

  /**
    * setSameWall - sets the SameWall rule
    **/
  public void setSameWall(boolean newSameWallRule) {
    isSameWall = newSameWallRule;
  }

  /**
    * setElemental - sets the Elemental rule
    **/
  public void setElemental(boolean newElementalRule) {
    isElemental = newElementalRule;
  }


  /**
    * displayRules - displays the rules to the screen
    **/
  public void displayRules(Graphics g, int iDrawingWidth, int iDrawingHeight) {
    Graphics2D g2 = (Graphics2D) g;
    int i, iFontInset, iLineSpacing;
    Rectangle rectRules;

    rectRules = new Rectangle((iDrawingWidth / 3),
                              (iDrawingHeight / 10),
                              (iDrawingWidth / 3),
                              (iDrawingHeight * 80 / 100));

    iFontInset = ((rectRules.width * 5 / 100) + rectRules.x);
    iLineSpacing = (rectRules.height * 5 / 100);


    g2.setColor(Board.RECT_FG_COLOR);
    g2.fillRect(rectRules.x, rectRules.y, rectRules.width, rectRules.height);


    i = 1;
    g2.setColor(Board.FONT_FG_COLOR);

    g2.drawString("Rules", iFontInset, (rectRules.y + (i * iLineSpacing)));
    i++;

    if (isOpen) {
      g2.drawString("Open", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;
    }

    if (isSuddenDeath) {
      g2.drawString("Sudden Death", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;
    }

    if (isRandom) {
      g2.drawString("Random", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;
    }
    if (isSame) {
      g2.drawString("Same", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;
    }
    if (isPlus) {
      g2.drawString("Plus", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;
    }
    if (isSameWall) {
      g2.drawString("Same Wall", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;
    }
    if (isElemental) {
      g2.drawString("Open", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;
    }

    switch(iTradeRule) {
      case TRADE_ONE:
        g2.drawString("Trade Rule: One", iFontInset, (rectRules.y + (i * iLineSpacing)));
        break;
      case TRADE_DIFF:
        g2.drawString("Trade Rule: Diff", iFontInset, (rectRules.y + (i * iLineSpacing)));
        break;
      case TRADE_DIRECT:
        g2.drawString("Trade Rule: Direct", iFontInset, (rectRules.y + (i * iLineSpacing)));
        break;
      case TRADE_ALL:
        g2.drawString("Trade Rule: All", iFontInset, (rectRules.y + (i * iLineSpacing)));
        break;

      default:
        g2.drawString("Error: invalid trade rule", iFontInset, (rectRules.y + (i * iLineSpacing)));
    }

    i++;


      g2.drawString("Play", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;

      g2.drawString("Quit", iFontInset, (rectRules.y + (i * iLineSpacing)));
      i++;


  }


}
