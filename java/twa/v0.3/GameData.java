import java.util.Vector;
import java.io.*;

class GameData {
  /** INSTANCE VARIABLES **/
  private int iRank;
  private int iExp;
  private int iCurrentSector;
  private int iTurnsLeft;
  private int iInitialTurns;
  private int iHops;
  private int iCredits;
  private Vector vectSectors;
  private Ship shipCurrent;
  private int iPortTimesToLoop;
  private int iPlanetTimesToLoop;



  /**
    * GameData
    **/
  public GameData() {
    //I'll put some arbitary values here now to play with
    iCredits = 0;
    iInitialTurns = 1;
    iTurnsLeft = 1;
    vectSectors = new Vector();
    shipCurrent = new Ship();
    iHops++;
    iPortTimesToLoop = 10;
    iPlanetTimesToLoop = 10;

  }

  /**
    * get*() -accessor methods
    **/
  public int getCredits() {
    return iCredits;
  }
  public int getTurnsLeft() {
    return iTurnsLeft;
  }
  public int getInitialTurns() {
    return iInitialTurns;
  }

  public Ship getCurrentShip() {
    return shipCurrent;
  }

  public Vector getSectors() {
    return vectSectors;
  }

  public int getTotalHops() {
    return iHops;
  }

  public int getPortTimesToLoop() { return iPortTimesToLoop; }

  public int getPlanetTimesToLoop() { return iPlanetTimesToLoop; }

  /**
    * add*() -addition methods
    **/
  public void addTurns(int iToAdd) {
    iTurnsLeft += iToAdd;
    if (iTurnsLeft > iInitialTurns) {
      iInitialTurns = iTurnsLeft; //we havent found out how initial turns there were
    }
  }

  /**
    * addSector
    **/
  public void addSector(Sector sectorToAdd) {
    if (findSector(sectorToAdd.getNumber()) == null) { //don't add the sector more than once
      //System.out.println("Adding sector " + sectorToAdd.getNumber());

      vectSectors.addElement(sectorToAdd);
    } else {
      //System.out.println("Sector " + sectorToAdd.getNumber() + " not added");
    }
  }

  /**
    * findSector
    **/
  public Sector findSector(int iNumber) {
    int i;
    Sector sectorToReturn;

    sectorToReturn = null;

    //System.out.println("Searching Sector List");

    for (i = 0; i < vectSectors.size(); i++) {

      //System.out.println("Test: " + iNumber + " == " + ((Sector) vectSectors.elementAt(i)).getNumber());

      if (iNumber == ((Sector) vectSectors.elementAt(i)).getNumber()) {
        //System.out.println("Sector found in list");
        sectorToReturn = (Sector) vectSectors.elementAt(i);
      }

    }
    return sectorToReturn;
  }

  /**
    * set*() - sets variables
    **/
  public void setTurns(int iSet) {
    iTurnsLeft = iSet;
    if (iTurnsLeft > iInitialTurns) {
      iInitialTurns = iTurnsLeft; //we havent found out how initial turns there were
    }
  }
  public void setInitialTurns(int iSet) { iInitialTurns = iSet; }
  public void setCredits(int iSet) { iCredits = iSet; }
  public void setPortTimesToLoop(int iSet) { iPortTimesToLoop = iSet; }
  public void setPlanetTimesToLoop(int iSet) { iPlanetTimesToLoop = iSet; }

  /**
    * decrementTurns
    **/
  public void decrementTurns() {
    iTurnsLeft -= shipCurrent.getTurnsToWarp();
    if (iTurnsLeft > iInitialTurns) {
      iInitialTurns = iTurnsLeft; //we havent found out how initial turns there were
    }

  }

  /**
    * incrementHops
    **/
  public void incrementHops() {
    iHops++;
  }


  /**
    * writeGameData
    **/
  public void writeGameData(File theFile) {
    FileWriter theFileWriter;
    PrintWriter thePrintWriter;
    int i;

    try {
      theFileWriter = new FileWriter(theFile);
      thePrintWriter = new PrintWriter(theFileWriter);

      for (i = 0; i < vectSectors.size(); i++) {
        thePrintWriter.println("*** A Sector");
        thePrintWriter.println( ((Sector) vectSectors.elementAt(i)).fileString() );
      }

      thePrintWriter.close();
    } catch (IOException e) { }
    
  }


  /**
    * printSectors
    **/
  public void printSectors() {
    int i;

    for (i = 0; i < vectSectors.size(); i++) {
      System.out.println((Sector) vectSectors.elementAt(i));

    }
  }








}
