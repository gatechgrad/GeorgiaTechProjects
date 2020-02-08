import java.util.Vector;
import java.io.*;

class GameData {
  /** INSTANCE VARIABLES **/
  private int iRank;
  private int iExp;
  private String strShipName;
  private String strName;
  private int iTurnsToWarp;
  private int iCurrentSector;
  private int iTurnsLeft;
  private int iInitialTurns;
  private int iTotalHolds;
  private int iFuelOre;
  private int iOrganics;
  private int iEquipment;
  private int iEmpty;
  private String strShipType;
  private int iFighters;
  private int iCredits;
  private int iGTorps;
  private Vector vectSectors;

  /**
    * GameData
    **/
  public GameData() {
    //I'll put some arbitary values here now to play with
    iCredits = 5000;
    iInitialTurns = 1000;
    iTurnsLeft = 250;
    iTurnsToWarp = 0;
    iFuelOre = 0;
    iOrganics = 0;
    iEquipment = 0;
    vectSectors = new Vector();
    strShipType = "Merchant Freighter";

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
  public int getTurnsToWarp() {
    return iTurnsToWarp;
  }
  public int getFuelOre() {
    return iFuelOre;
  }
  public int getOrganics() {
    return iOrganics;
  }
  public int getEquipment() {
    return iEquipment;
  }
  public String getShipType() {
    return strShipType;
  }
  public String getShipName() {
    return strShipName;
  }




  /**
    * add*() -addition methods
    **/
  public void addTurns(int iToAdd) {
    iTurnsLeft += iToAdd;
  }
  public void addFuelOre(int iToAdd) {
    iFuelOre += iToAdd;
  }
  public void addOrganics(int iToAdd) {
    iOrganics += iToAdd;
  }
  public void addEquipment(int iToAdd) {
    iEquipment += iToAdd;
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
  public void setTurns(int iSet) { iTurnsLeft = iSet; }
  public void setCredits(int iSet) { iCredits = iSet; }
  public void setFighters(int iSet) { iFighters = iSet; }
  public void setTurnsToWarp(int iSet) { iTurnsToWarp = iSet; }
  public void setShipName(String strName) { this.strShipName = strName; }
  public void setShipType(String strType) { this.strShipType = strType; }


  /**
    * decrementTurns
    **/
  public void decrementTurns() {
    iTurnsLeft -= iTurnsToWarp;
  }

  /**
    * writeGameData
    **/
  public void writeGameData(String strFileName) {
    FileWriter theFileWriter;
    PrintWriter thePrintWriter;
    int i;

    try {
      theFileWriter = new FileWriter(strFileName);
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
