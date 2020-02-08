import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;


public class Sector {

  /** CONSTANTS **/
  public static final int VIEWPORT_HEIGHT = 100;
  public static final int LEFT_VIEW = 150;


  /** INSTANCE VARIABLES **/
  Vector vectAdjacentSectors;
  Port thePort;
  int iSectorNumber;
  int iFighters;
  String strFightersOwner;
  int iArmidMines;
  String strArmidMinesOwner;
  int iLimpetMines;
  int iHopNumber;
  String strLimpetMinesOwner;
  Vector vectPlanets;
  Vector vectShips;
  Vector vectPortPairs;
  boolean isExplored;
  TWA theTWA;
  Rectangle rectView;

  /**
    * Sector
    **/
  public Sector(int iSectorNumber, TWA theTWA) {
    this.theTWA = theTWA;
    this.iSectorNumber = iSectorNumber;
    vectAdjacentSectors = new Vector();
    vectPortPairs = new Vector();
    vectPlanets = new Vector();
    iHopNumber = -1;
  }

  /**
    * setAdjacentSectors
    **/
  public void setAdjacentSectors(Vector vectAdjacentSectors) {
    int i;
    Port portTemp;
    String strClass1, strClass2; //the port classes

    this.vectAdjacentSectors = vectAdjacentSectors;
    for (i = 0; i < vectAdjacentSectors.size(); i++) {
      portTemp = ((Sector) vectAdjacentSectors.elementAt(i)).getPort();


      if ((portTemp != null) && (thePort != null)) {

        System.out.println(thePort.getPortClass() + " " + portTemp.getPortClass());



        if        (thePort.getPortClass().equals("SBB") && portTemp.getPortClass().equals("BSB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("SBB") && portTemp.getPortClass().equals("BBS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("SBB") && portTemp.getPortClass().equals("BSS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BSB") && portTemp.getPortClass().equals("BBS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BSB") && portTemp.getPortClass().equals("SBB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BSB") && portTemp.getPortClass().equals("SBS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BBS") && portTemp.getPortClass().equals("BSB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BBS") && portTemp.getPortClass().equals("SBB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BBS") && portTemp.getPortClass().equals("SSB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("SSB") && portTemp.getPortClass().equals("SBS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("SSB") && portTemp.getPortClass().equals("BBS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("SSB") && portTemp.getPortClass().equals("BSS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));

        } else if (thePort.getPortClass().equals("SBS") && portTemp.getPortClass().equals("BSB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("SBS") && portTemp.getPortClass().equals("SSB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BSS") && portTemp.getPortClass().equals("SBS")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BSS") && portTemp.getPortClass().equals("SBB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));
        } else if (thePort.getPortClass().equals("BSS") && portTemp.getPortClass().equals("SSB")) {
          setPortPair((Sector) vectAdjacentSectors.elementAt(i));


        }


      }
    }

  }

  /**
    * setPortPair
    **/
  public void setPortPair(Sector sectorToAdd) {
    int i;
    boolean isDuplicate;

    isDuplicate = false; //don't want to add sector to port pair list if it's
                         //already in the port pair list


    System.out.println("Attempting to add port pair");

    for (i = 0; i < vectPortPairs.size(); i++) {
      if (  ((Sector) vectPortPairs.elementAt(i)).getNumber() == sectorToAdd.getNumber()) {
        isDuplicate = true;
      }
    }


    if (!isDuplicate) {
      System.out.println("Adding port pair");

      vectPortPairs.add(sectorToAdd);
    }

  }

  /**
    * setHopNumber
    **/
  public void setHopNumber(int iSet) {
    iHopNumber = iSet;
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
    * getPortPairs
    **/
  public Vector getPortPairs() {
    return vectPortPairs;
  }

  public Vector getAdjacentSectors() {
    return vectAdjacentSectors;
  }

  /**
    * set
    **/
  public void setPort(Port thePort) { this.thePort = thePort; }
  public void setArmidMines(int i) { iArmidMines = i; }
  public void setLimpetMines(int i) { iLimpetMines = i; }
  public void setFighters(int i) { iFighters = i; }

  /**
    * clearPlanets
    **/
  public void clearPlanets() {
    vectPlanets = new Vector();
  }

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
    int iLinesToPrint;

    g.setFont(fontViewPort);
    g.setColor(Screen.FG_COLOR);

    if (vectAdjacentSectors.size() > 0) {
      g.drawRect(0, Screen.STATUS_LINE.y + Screen.STATUS_LINE.height, Screen.MODEL.width, VIEWPORT_HEIGHT);

      for (i = 0; i < vectAdjacentSectors.size(); i++) {
        g.setColor(Screen.FG_COLOR);
        sectorTemp = (Sector) vectAdjacentSectors.elementAt(i);

        x = Screen.MODEL.width / vectAdjacentSectors.size() * i;
        y = Screen.STATUS_LINE.y + Screen.STATUS_LINE.height;


        sectorTemp.drawView(g, new Rectangle(x, y, Screen.MODEL.width / vectAdjacentSectors.size(), VIEWPORT_HEIGHT), theObserver);
        //sectorTemp.drawView(g, x, y, theObserver);
      }


      //draw sector number
      iLinesToPrint = 1;
      x = 500;
      y = 300;

      g.setColor(Screen.FG_COLOR);
      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Sector: " + iSectorNumber, x + 5, y);

      if (vectAdjacentSectors.size() == 1) {
        g.drawString("Bubble Sector", x + 5, y + 22);
        iLinesToPrint++;
      }

      g.drawRect(x, y - 18, 130, iLinesToPrint * 22);

     //draw the port
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

    //draw the planets
    if (vectPlanets.size() > 0) {
      x = 200;
      y = 80;

      for (i = 0; i < vectPlanets.size(); i++) {
        ((Planet) vectPlanets.elementAt(i)).drawPlanet(g, x, y += 50, theObserver);
      }

    }


    x = 500;
    y = 130;
    g.setColor(Screen.FG_COLOR);

    //draw the figs
    if (iFighters > 0) {

      g.setFont(new Font("serif", Font.PLAIN, 10));
      g.drawString("Fighters: " + iFighters, x, y);
      g.drawString("Owned By: " + strFightersOwner, x, y += 12);

      y += 12;
      g.setColor(Color.green);

      j = 0;
      for (i = 0; i < iFighters; i++) {
        g.drawLine(x += 2, y, x, y);

        if ((i % 50) == 49) {
          y += 2;
          x = 500;
        }
      }

    }

    x = 500;

    if (iArmidMines > 0) {
      g.setFont(new Font("serif", Font.PLAIN, 10));
      y += 14;
      g.drawString("Armid Mines: " + iArmidMines, x, y);
      g.drawString("Owned By: " + strArmidMinesOwner, x, y += 12);

      y += 14;
      y += drawMines(g, iArmidMines, x, y);


    }


    if (iLimpetMines > 0) {
      g.setFont(new Font("serif", Font.PLAIN, 10));
      y += 14;
      g.drawString("Limpet Mines: " + iLimpetMines, x, y);
      g.drawString("Owned By: " + strLimpetMinesOwner, x, y += 12);

      y += 14;
      y += drawMines(g, iLimpetMines, x, y);
    }

  }


  /**
    * drawMines
    **/
  private int drawMines(Graphics g, int iMines, int x, int y) {
    int iSpaceRequired;
    int i;
    int iLeftBound;

    iLeftBound = x;
    iSpaceRequired = 0;

    g.setColor(Screen.COLOR_65536);
    for (i = 0; i < iMines / 65536; i++) {
      if (x > iLeftBound + LEFT_VIEW) {
        x = iLeftBound;
        iSpaceRequired += 6;
      }

      g.fillOval(x += 6, y + iSpaceRequired, 2, 2);
      g.drawLine(x - 2, y + iSpaceRequired - 2, x + 2, y + iSpaceRequired + 2);
      g.drawLine(x - 2, y + iSpaceRequired + 2, x + 2, y + iSpaceRequired - 2);
    }
    iMines = iMines % 65536;

    g.setColor(Screen.COLOR_4096);
    for (i = 0; i < iMines / 4096; i++) {
      if (x > iLeftBound + LEFT_VIEW) {
        x = iLeftBound;
        iSpaceRequired += 6;
      }

      g.fillOval(x += 6, y + iSpaceRequired, 2, 2);
      g.drawLine(x - 2, y + iSpaceRequired - 2, x + 2, y + iSpaceRequired + 2);
      g.drawLine(x - 2, y + iSpaceRequired + 2, x + 2, y + iSpaceRequired - 2);
    }
    iMines = iMines % 4096;

    g.setColor(Screen.COLOR_256);
    for (i = 0; i < iMines / 256; i++) {
      if (x > iLeftBound + LEFT_VIEW) {
        x = iLeftBound;
        iSpaceRequired += 6;
      }

      g.fillOval(x += 6, y + iSpaceRequired, 2, 2);
      g.drawLine(x - 2, y + iSpaceRequired - 2, x + 2, y + iSpaceRequired + 2);
      g.drawLine(x - 2, y + iSpaceRequired + 2, x + 2, y + iSpaceRequired - 2);
    }
    iMines = iMines % 256;

    g.setColor(Screen.COLOR_16);
    for (i = 0; i < iMines / 16; i++) {
      if (x > iLeftBound + LEFT_VIEW) {
        x = iLeftBound;
        iSpaceRequired += 6;
      }

      g.fillOval(x += 6, y + iSpaceRequired, 2, 2);
      g.drawLine(x - 2, y + iSpaceRequired - 2, x + 2, y + iSpaceRequired + 2);
      g.drawLine(x - 2, y + iSpaceRequired + 2, x + 2, y + iSpaceRequired - 2);
    }
    iMines = iMines % 16;

    g.setColor(Screen.COLOR_1);
    for (i = 0; i < iMines / 1; i++) {
      if (x > iLeftBound + LEFT_VIEW) {
        x = iLeftBound;
        iSpaceRequired += 6;
      }

      g.fillOval(x += 6, y + iSpaceRequired, 2, 2);
      g.drawLine(x - 2, y + iSpaceRequired - 2, x + 2, y + iSpaceRequired + 2);
      g.drawLine(x - 2, y + iSpaceRequired + 2, x + 2, y + iSpaceRequired - 2);
    }
    iMines = iMines % 1;


    return iSpaceRequired;
  }


  /**
    * drawView
    **/
  public void drawView(Graphics g, Rectangle rectArea, ImageObserver theObserver) {
    int i;
    boolean isAPortPair;

    rectView = rectArea;

    g.drawLine(rectArea.x, rectArea.y, rectArea.x, rectArea.y + VIEWPORT_HEIGHT);
    g.drawString("Sector: " + iSectorNumber, rectArea.x + 2, rectArea.y - 2 + VIEWPORT_HEIGHT);

    if (!isExplored()) {
      g.drawString("UNEXPLORED", rectArea.x + 2, rectArea.y + 50);
    }

    if (thePort != null) {
      thePort.drawView(g, rectArea.x, rectArea.y, theObserver);
    }

    if (vectPlanets.size() > 0) {
      for (i = 0; i < vectPlanets.size(); i++) {
        ((Planet) vectPlanets.elementAt(i)).drawView(g, rectArea.x + 55, rectArea.y + (i * 12) + 2, theObserver);
      }
    }


    //Hops ago
    if (iHopNumber > -1) {
      g.setColor(Screen.FG_COLOR);
      g.fillRect(rectArea.x + rectArea.width - 50, rectArea.y + 2, 49, g.getFont().getSize());
      g.setColor(Screen.BKG_COLOR);
      g.drawString("" + (theTWA.getBBS().getGameData().getTotalHops() - iHopNumber), rectArea.x + rectArea.width - 50, rectArea.y + g.getFont().getSize() + 2);

//      System.out.println("Total Hops: "  + theTWA.getBBS().getGameData().getTotalHops());
//      System.out.println("Current Sector Hops: " + iHopNumber);
    }

    //is a port pair
    isAPortPair = false;
    for (i = 0; i < theTWA.getCurrentSector().getPortPairs().size(); i++) {
      if (  ((Sector) theTWA.getCurrentSector().getPortPairs().elementAt(i)).getNumber() == getNumber()) {
        g.setColor(Color.green);
        g.fillRect(rectArea.x + rectArea.width - 50, rectArea.y + rectArea.height - 14, 50, 14);
        g.setColor(Color.black);
        g.drawString("Port Pair", rectArea.x + rectArea.width - 50, rectArea.y + rectArea.height - 2);
      }
    }
  }

  /**
    * viewIsClicked
    **/
  public boolean viewIsClicked(int x, int y) {

    return rectView.contains(x, y);
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
