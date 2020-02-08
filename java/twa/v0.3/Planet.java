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

  /** INSTANCE VARIABLES **/
  private String strName;
  private char chClass;
  private String strType;
  private int iFuelOre;
  private int iOrganics;
  private int iEquipment;
  private int iFighters;

  /**
    * Planet
    **/
  public Planet() {
    strName = "Temp";
    chClass = 'H';
    strType = "Volcanic";

  }

  /**
    * set
    **/
  public void setName(String str) { strName = str; }
  public void setType(char ch) { chClass = ch; }

  /**
    * drawPlanet
    **/
  public void drawPlanet(Graphics g, int x, int y, ImageObserver theObserver) {
    g.setColor(Color.red);

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

    g.setFont(new Font("serif", Font.PLAIN, 10));
    g.setColor(Color.green);
    g.drawString(strName, x + 50, y + 14);
    g.drawString("Class: " + chClass, x + 50, y + 28);


  }


  /**
    * drawView
    **/
  public void drawView(Graphics g, int x, int y, ImageObserver theObserver) {

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
    * toString
    **/
  public String toString() {
    String strToReturn;

    strToReturn = "Planet:   " + strName + '\n';
    strToReturn += "  Class: " + chClass + '\n';
    strToReturn += "  Type:  " + strType + '\n';

    return strToReturn;
  }

}
