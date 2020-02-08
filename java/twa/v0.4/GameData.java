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
  private String strPlanetFrom;
  private String strPlanetTo;
  private String strPlanetFuel;
  private String strSectorFrom;
  private boolean twarpWhenLocked;
  private boolean stopWhenNotLocked;




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
    twarpWhenLocked = true;
    stopWhenNotLocked = true;

    iPortTimesToLoop = 10;
    iPlanetTimesToLoop = 10;

    strSectorFrom = "1";
    strPlanetFrom = "";
    strPlanetTo = "";
    strPlanetFuel = "";
    strToMove = ColonizeDialog.COLONISTS;
    strColonistJob = ColonizeDialog.FUEL_ORE;


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
  public String getSectorFrom() { return strSectorFrom; }
  public String getPlanetFrom() { return strPlanetFrom; }
  public String getPlanetTo() { return strPlanetTo; }
  public String getPlanetFuel() { return strPlanetFuel; }
  public String getToMove() { return strToMove; }
  public String getColonistJob() { return strToMove; }

  public boolean getTWarpWhenLocked() { return twarpWhenLocked; }
  public boolean getStopWhenNotLocked() { return stopWhenNotLocked; }


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
  public void setSectorFrom(String str) { strSectorFrom = str; }
  public void setPlanetFrom(String str) { strPlanetFrom = str; }
  public void setPlanetTo(String str) { strPlanetTo = str; }
  public void setPlanetFuel(String str) { strPlanetFuel = str; }


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

      /** WRITE ADJACENT SECTORS **/
      for (i = 0; i < vectSectors.size(); i++) {
        if ( ((Sector) vectSectors.elementAt(i)).getAdjacentSectors().size() > 0) {
          thePrintWriter.println( ((Sector) vectSectors.elementAt(i)).fileAdjString());
        }
      }



      thePrintWriter.close();
    } catch (IOException e) { }
    
  }

  /**
    * sort - sorts sectors by number and returns a new vector
    **/
  public Vector sort(Vector vectSectors) {
    int i;
    Sector sectorTemp, sectorTemp2;
    boolean changeMade;

    //Bubble sort

    do {
      changeMade = false;
      for (i = 0; i < vectSectors.size() - 1; i++) {
        if (  ((Sector) vectSectors.elementAt(i)).getNumber() >
            ((Sector) vectSectors.elementAt(i + 1)).getNumber() ) {
          sectorTemp = (Sector) vectSectors.elementAt(i);
          sectorTemp2 = (Sector) vectSectors.elementAt(i + 1);
          vectSectors.removeElementAt(i);
          vectSectors.removeElementAt(i);


          vectSectors.insertElementAt(sectorTemp, i);
          vectSectors.insertElementAt(sectorTemp2, i);
          changeMade = true;
        }
      }
    } while (changeMade == false);

    return vectSectors;

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
