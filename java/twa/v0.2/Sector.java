import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;


public class Sector {

  /** CONSTANTS **/
  public static final int VIEWPORT_HEIGHT = 100;

  /** INSTANCE VARIABLES **/
  Vector vectAdjacentSectors;
  Port thePort;
  int iSectorNumber;
  int iFighters;
  int iMines;
  Vector vectPlanets;
  boolean isExplored;

  /**
    * Sector
    **/
  public Sector(int iSectorNumber) {
    this.iSectorNumber = iSectorNumber;
    vectAdjacentSectors = new Vector();
    vectPlanets = new Vector();
  }

  /**
    * setAdjacentSectors
    **/
  public void setAdjacentSectors(Vector vectAdjacentSectors) {
    this.vectAdjacentSectors = vectAdjacentSectors;
  }

  /**
    * setExplored
    **/
  public void setExplored(boolean b) {
    isExplored = b;
  }

  /**
    * isExplored
    **/
  public boolean isExplored() {
    return isExplored;
  }

  /**
    * getNumber
    **/
  public int getNumber() {
    return iSectorNumber;
  }

  /**
    * getPort
    **/
  public Port getPort() {
    return thePort;
  }

  /**
    * setPort
    **/
  public void setPort(Port thePort) { this.thePort = thePort; }

  /**
    * setPlanet
    **/
  public void setPlanet(Planet thePlanet) {
    vectPlanets.addElement(thePlanet);
  }

  /**
    * drawSector
    **/
  public void drawSector(Graphics g, ImageObserver theObserver) {
    int i, j;
    int x, y;
    int iBarLength;
    Sector sectorTemp;
    Port portTemp;
    Font fontViewPort = new Font("serif", Font.PLAIN, 10);
    String strTemp;

    g.setFont(fontViewPort);

    if (vectAdjacentSectors.size() > 0) {
      //System.out.println("vectAdjacentSectors.size: " + vectAdjacentSectors.size());

      g.drawRect(0, Screen.STATUS_LINE.y + Screen.STATUS_LINE.height, Screen.MODEL.width, VIEWPORT_HEIGHT);

      for (i = 0; i < vectAdjacentSectors.size(); i++) {
        g.setColor(Screen.FG_COLOR);
        

        sectorTemp = (Sector) vectAdjacentSectors.elementAt(i);
        x = Screen.MODEL.width / vectAdjacentSectors.size() * i;
        y = Screen.STATUS_LINE.y + Screen.STATUS_LINE.height;
        g.drawLine(x, y, x, y + VIEWPORT_HEIGHT);
        g.drawString("Sector: " + sectorTemp.getNumber(), x + 2, y - 2 + VIEWPORT_HEIGHT);
        if (!sectorTemp.isExplored()) {
          g.drawString("UNEXPLORED", x + 2, y + 50);
        }

        portTemp = sectorTemp.getPort();

        if (portTemp != null) {
          portTemp.drawView(g, x, y, theObserver);
        }

      }


      //draw sector number
      g.setColor(Screen.FG_COLOR);
      g.drawRect(500, 308, 130, 24);
      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Sector: " + iSectorNumber, 505, 328);


     if (thePort != null) {

      if (vectPlanets.size() == 0) {
        x = (Screen.MODEL.width - 130) / 2;
        y = 130;
      } else {
        x = 40;
        y = 130;
      }

      thePort.drawFull(g, x, y, theObserver);

      }

     }

    x = 500;
    y = 100;
    if (vectPlanets.size() > 0) {
      for (i = 0; i < vectPlanets.size(); i++) {
        ((Planet) vectPlanets.elementAt(i)).drawPlanet(g, x, y += 50, theObserver);
      }

    }
  }

  /**
    * fileString - string that will be written to file
    **/
  public String fileString() {
    String strToReturn;
    int i;

    strToReturn = "";
    strToReturn += iSectorNumber + "|";
    for (i = 0; i < vectAdjacentSectors.size() - 1; i++) {
      strToReturn += ((Sector) vectAdjacentSectors.elementAt(i)).getNumber() + "|";
    }

    strToReturn = "\n";
    if (thePort != null) {
      strToReturn += thePort.fileString();
    }

    return strToReturn;

  }

  /**
    * toString
    **/
  public String toString() {
    String strToReturn;
    int i;

    strToReturn = "Sector: " + iSectorNumber + "\nWarps: ";
    for (i = 0; i < vectAdjacentSectors.size() - 1; i++) {
      strToReturn += ((Sector) vectAdjacentSectors.elementAt(i)).getNumber() + " ";
    }
    strToReturn += "Port: " + thePort;
    strToReturn += "\n-----------------------------\n";

    return strToReturn;
  }

}
