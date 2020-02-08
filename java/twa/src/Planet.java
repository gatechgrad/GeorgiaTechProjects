import java.awt.*;
import java.awt.image.ImageObserver;

class Planet {

  /** CONSTANTS **/
  public static final int CLASS_M = 0;
  public static final int CLASS_K = 1;
  public static final int CLASS_O = 2;
  public static final int CLASS_L = 3;
  public static final int CLASS_C = 4;
  public static final int CLASS_H = 5;
  public static final int CLASS_U = 6;

  public static final Dimension SECTOR_DRAWING_SIZE = new Dimension(100, 50);

  /** INSTANCE VARIABLES **/
  private String strName;
  private char chClass;
  private String strType;
  private int iNumber;
  private int iFuelOre;
  private int iOrganics;
  private int iEquipment;
  private int iFighters;
  private int iColoFuelOre;
  private int iColoOrganics;
  private int iColoEquipment;
  private int iToBuildFuelOre;
  private int iToBuildOrganics;
  private int iToBuildEquipment;
  private int iDailyProductFuelOre;
  private int iDailyProductOrganics;
  private int iDailyProductEquipment;
  private int iMaxFuelOre;
  private int iMaxOrganics;
  private int iMaxEquipment;
  private int iMaxFighters;
  private boolean isShielded;


  private boolean isHighlighted;
  private Rectangle rectPlanet;

  /**
    * Planet
    **/
  public Planet() {
    strName = "Temp";
    chClass = 'H';
    strType = "";
    iNumber = -1;
    isHighlighted = false;

    isShielded = false;
    iFuelOre = 0;
    iOrganics = 0;
    iEquipment = 0;
    iFighters = 0;
    iColoFuelOre = 0;
    iColoOrganics = 0;
    iColoEquipment = 0;
    iToBuildFuelOre = 0;
    iToBuildOrganics = 0;
    iToBuildEquipment = 0;
    iMaxFuelOre = 0;
    iMaxOrganics = 0;
    iMaxEquipment = 0;
    iMaxFighters = 0;
    iDailyProductFuelOre = 0;
    iDailyProductOrganics = 0;
    iDailyProductEquipment = 0;
  }

  /**
    * set
    **/
  public void setName(String str) { strName = str; }
  public void setType(char ch) { chClass = ch; }
  public void setShielded(boolean b) { isShielded = b; }

  /**
    * drawPlanet
    **/
  public void drawPlanet(Graphics g, int x, int y, ImageObserver theObserver) {
    rectPlanet = new Rectangle(x, y, 150, 50);

    if (isHighlighted) {
      g.setColor(Screen.FG_COLOR);
      g.fillRect(rectPlanet.x, rectPlanet.y, rectPlanet.width, rectPlanet.height);
    } else {
/*      g.setColor(Screen.BKG_COLOR);
      g.fillRect(rectPlanet.x, rectPlanet.y, rectPlanet.width, rectPlanet.height);
*/
    }


    if (isShielded) {
      g.setColor(Color.cyan);
      g.fillRect(x, y, 50, 50);
    }



    switch(chClass) {
      case 'M':
        g.drawImage(Screen.imgPlanet[CLASS_M], x, y, 50, 50, theObserver);
        break;
      case 'K':
        g.drawImage(Screen.imgPlanet[CLASS_K], x, y, 50, 50, theObserver);
        break;
      case 'O':
        g.drawImage(Screen.imgPlanet[CLASS_O], x, y, 50, 50, theObserver);
        break;
      case 'L':
        g.drawImage(Screen.imgPlanet[CLASS_L], x, y, 50, 50, theObserver);
        break;
      case 'C':
        g.drawImage(Screen.imgPlanet[CLASS_C], x, y, 50, 50, theObserver);
        break;
      case 'H':
        g.drawImage(Screen.imgPlanet[CLASS_H], x, y, 50, 50, theObserver);
        break;
      case 'U':
        g.drawImage(Screen.imgPlanet[CLASS_U], x, y, 50, 50, theObserver);
        break;
      default:
        g.drawImage(Screen.imgPlanet[CLASS_M], x, y, 50, 50, theObserver);
    }

    g.setFont(new Font(Screen.FONT.getFontName(), Font.PLAIN, 10));

    if (isHighlighted) {
      g.setColor(Screen.BKG_COLOR);
    } else {
      g.setColor(Screen.FG_COLOR);
    }
    g.drawString(strName, x + 50, y + 14);
    g.drawString("Class: " + chClass, x + 50, y + 28);

  }

  /**
    * drawLand
    **/
  public void drawLand(Graphics g, ImageObserver theObserver) {
    int i;
    int x, y;

    x = 0;
    y = 0;

    switch(chClass) {
      case 'M':
        g.drawImage(Screen.imgLand[CLASS_M], x, y, theObserver);
        break;
      case 'K':
        g.drawImage(Screen.imgLand[CLASS_K], x, y, theObserver);
        break;
      case 'O':
        g.drawImage(Screen.imgLand[CLASS_O], x, y, theObserver);
        break;
      case 'L':
        g.drawImage(Screen.imgLand[CLASS_L], x, y, theObserver);
        break;
      case 'C':
        g.drawImage(Screen.imgLand[CLASS_C], x, y, theObserver);
        break;
      case 'H':
        g.drawImage(Screen.imgLand[CLASS_H], x, y, theObserver);
        break;
      case 'U':
        g.drawImage(Screen.imgLand[CLASS_U], x, y, theObserver);
        break;
      default:
        g.drawImage(Screen.imgLand[CLASS_M], x, y, theObserver);
    }

    g.setFont(new Font(Screen.FONT.getFontName(), Font.PLAIN, 10));
    g.setColor(Color.green);
    g.drawString(strName, x + 50, y + 14);
    g.drawString("Class: " + chClass, x + 50, y + 28);

    g.setColor(Screen.BKG_COLOR);

    Rectangle rectPlanetStats = new Rectangle(x + 50, y + 50, 320, 200);
    for (i = 0; i < rectPlanetStats.height; i ++) {
      g.drawLine(rectPlanetStats.x, rectPlanetStats.y + i, rectPlanetStats.x + rectPlanetStats.width, rectPlanetStats.y + i);
    }

    //g.fillRect(rectPlanetStats.x, rectPlanetStats.y, rectPlanetStats.width, rectPlanetStats.height);

    g.setColor(Screen.FG_COLOR);
    g.drawString("Colonists", rectPlanetStats.x + 50, rectPlanetStats.y + 20);
    g.drawString("Colonists required to construct one", rectPlanetStats.x + 100, rectPlanetStats.y + 20);
    g.drawString("Daily Product", rectPlanetStats.x + 150, rectPlanetStats.y + 20);
    g.drawString("Planet Amount", rectPlanetStats.x + 200, rectPlanetStats.y + 20);
    g.drawString("Planet Maximum", rectPlanetStats.x + 250, rectPlanetStats.y + 20);


    g.setColor(Color.red);
    g.drawString("Fuel Ore", rectPlanetStats.x + 5, rectPlanetStats.y + 40);

    g.drawString("" + iColoFuelOre, rectPlanetStats.x + 50, rectPlanetStats.y + 40);
    g.drawString("" + iToBuildFuelOre, rectPlanetStats.x + 100, rectPlanetStats.y + 40);
    g.drawString("" + iDailyProductFuelOre, rectPlanetStats.x + 150, rectPlanetStats.y + 40);
    g.drawString("" + iFuelOre, rectPlanetStats.x + 200, rectPlanetStats.y + 40);
    g.drawString("" + iMaxFuelOre, rectPlanetStats.x + 250, rectPlanetStats.y + 40);

    g.setColor(Color.green);
    g.drawString("Organics", rectPlanetStats.x + 5, rectPlanetStats.y + 60);

    g.drawString("" + iColoOrganics, rectPlanetStats.x + 50, rectPlanetStats.y + 60);
    g.drawString("" + iToBuildOrganics, rectPlanetStats.x + 100, rectPlanetStats.y + 60);
    g.drawString("" + iDailyProductOrganics, rectPlanetStats.x + 150, rectPlanetStats.y + 60);
    g.drawString("" + iOrganics, rectPlanetStats.x + 200, rectPlanetStats.y + 60);
    g.drawString("" + iMaxOrganics, rectPlanetStats.x + 250, rectPlanetStats.y + 60);

    g.setColor(Color.blue);
    g.drawString("Equipment", rectPlanetStats.x + 5, rectPlanetStats.y + 80);

    g.drawString("" + iColoEquipment, rectPlanetStats.x + 50, rectPlanetStats.y + 80);
    g.drawString("" + iToBuildEquipment, rectPlanetStats.x + 100, rectPlanetStats.y + 80);
    g.drawString("" + iDailyProductEquipment, rectPlanetStats.x + 150, rectPlanetStats.y + 80);
    g.drawString("" + iEquipment, rectPlanetStats.x + 200, rectPlanetStats.y + 80);
    g.drawString("" + iMaxEquipment, rectPlanetStats.x + 250, rectPlanetStats.y + 80);




  }



  /**
    * drawView
    **/
  public void drawView(Graphics g, int x, int y, ImageObserver theObserver) {
    if (isShielded) {
      g.setColor(Color.cyan);
      g.fillRect(x, y, 10, 10);
    }

    switch(chClass) {
      case 'M':
        g.drawImage(Screen.imgPlanet[CLASS_M], x, y, 10, 10, theObserver);
        break;
      case 'K':
        g.drawImage(Screen.imgPlanet[CLASS_K], x, y, 10, 10, theObserver);
        break;
      case 'O':
        g.drawImage(Screen.imgPlanet[CLASS_O], x, y, 10, 10, theObserver);
        break;
      case 'L':
        g.drawImage(Screen.imgPlanet[CLASS_L], x, y, 10, 10, theObserver);
        break;
      case 'C':
        g.drawImage(Screen.imgPlanet[CLASS_C], x, y, 10, 10, theObserver);
        break;
      case 'H':
        g.drawImage(Screen.imgPlanet[CLASS_H], x, y, 10, 10, theObserver);
        break;
      case 'U':
        g.drawImage(Screen.imgPlanet[CLASS_U], x, y, 10, 10, theObserver);
        break;
      default:
        g.drawImage(Screen.imgPlanet[CLASS_M], x, y, 10, 10, theObserver);
    }


  }

  /**
    * set*
    **/
  public void setNumber(int i) { iNumber = i; }
  public void setHighlighted(boolean b) { isHighlighted = b; }

  public void setFuelOre(int i) { iFuelOre = i; }
  public void setOrganics(int i) { iOrganics = i; }
  public void setEquipment(int i) { iEquipment = i; }

  public void setColoFuelOre(int i) { iColoFuelOre = i; }
  public void setColoOrganics(int i) { iColoOrganics = i; }
  public void setColoEquipment(int i) { iColoEquipment = i; }

  public void setToBuildFuelOre(int i) { iToBuildFuelOre = i; }
  public void setToBuildOrganics(int i) { iToBuildOrganics = i; }
  public void setToBuildEquipment(int i) { iToBuildEquipment = i; }

  public void setMaxFuelOre(int i) { iMaxFuelOre = i; }
  public void setMaxOrganics(int i) { iMaxOrganics = i; }
  public void setMaxEquipment(int i) { iMaxEquipment = i; }

  /**
    * get*
    **/
  public int getNumber() { return iNumber; }


  /**
    * planetIsClicked
    **/
  public boolean planetIsClicked(int x, int y) {
    boolean boolToReturn;

    if (rectPlanet != null) {
      boolToReturn = rectPlanet.contains(x, y);
    } else {
      boolToReturn = false;
    }

    return boolToReturn;
  }


  /**
    * toString
    **/
  public String toString() {
    String strToReturn;

    strToReturn = "Planet:   " + strName;
    strToReturn += "  Class: " + chClass;
    strToReturn += "  Type:  " + strType + '\n';

    return strToReturn;
  }

}
