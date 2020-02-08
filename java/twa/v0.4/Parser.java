import java.util.Vector;

public class Parser implements LineParser {

  /** CONSTANTS **/
  public static final int STATE_NORMAL = 0;
  public static final int STATE_GETTING_PLANETS = 1;
  public static final int STATE_GETTING_TRADERS = 2;


  /** INSTANCE VARIABLES **/
  private TWA theTWA;
  private int iHops;
  private int iState;
  private Vector vectParsers;

  /**
    * Parser
    **/
  public Parser(TWA theTWA) {
    this.theTWA = theTWA;
    iHops = -1;
    vectParsers = new Vector();
    iState = STATE_NORMAL;

  }


  /**
    * parseString
    **/
  public void parseString(String theString) {
    String strTemp, strTemp2;
    int i, j;
    int iSector;
    Sector sectorTemp;
    Planet planetTemp;

    /*** WARPS TO SECTOR ***/
    if (theString.startsWith("Warps to Sector")) {
      //System.out.println("Display adjacent Sectors");

      Vector vectTemp = new Vector();

      strTemp2 = theString.substring(22);

      i = 0;
      j = strTemp2.indexOf('-');

      while ((i != -1) && (j != -1)) {

        if (j != -1) {
          strTemp = strTemp2.substring(i, j - 1);
          //System.out.println("***" + strTemp + "***");
          try {
            if ((strTemp.charAt(0) == '(') && (strTemp.charAt(strTemp.length() - 1) == ')')) {
                iSector = (new Integer(strTemp.substring(1, strTemp.length() - 1))).intValue();
                sectorTemp = new Sector(iSector, theTWA);
                sectorTemp.setExplored(false);
            } else {
                iSector = (new Integer(strTemp)).intValue();
                sectorTemp = new Sector(iSector, theTWA);
                sectorTemp.setExplored(true);
            }
            theTWA.getBBS().getGameData().addSector(sectorTemp);
            vectTemp.addElement(theTWA.getBBS().getGameData().findSector(iSector));

          } catch (NumberFormatException e) { }
          
        } 

        i = j + 2;
        j = strTemp2.indexOf('-', j + 1);
      } 

      strTemp = strTemp2.substring(i, strTemp2.length());
      //System.out.println("***" + strTemp + "***");
      try {
        if ((strTemp.charAt(0) == '(') && (strTemp.charAt(strTemp.length() - 1) == ')')) {
          iSector = (new Integer(strTemp.substring(1, strTemp.length() - 1))).intValue();
          sectorTemp = new Sector(iSector, theTWA);
          sectorTemp.setExplored(false);
        } else {
          iSector = (new Integer(strTemp)).intValue();
          sectorTemp = new Sector(iSector, theTWA);
          sectorTemp.setExplored(true);
        }

        theTWA.getBBS().getGameData().addSector(sectorTemp);
        vectTemp.addElement(theTWA.getBBS().getGameData().findSector(iSector));

      } catch (NumberFormatException e) { }



      iState = STATE_NORMAL;


      theTWA.getCurrentSector().setAdjacentSectors(vectTemp);
      theTWA.getScreen().repaint();

    /*** PORTS ***/
    } else if (theString.startsWith("Ports   :")) {
      String strPortName;
      String strClass;

      //System.out.println("Found a port");


      strPortName = theString.substring(10, theString.indexOf(',', 10));
      //System.out.println(strPortName);
      strClass = theString.substring(theString.indexOf('(') + 1, theString.lastIndexOf(')'));
      //System.out.println("Class: " + strClass);


      Port portTemp = new Port(strPortName, strClass);

      iState = STATE_NORMAL;

      theTWA.getCurrentSector().setPort(portTemp); //update the port even if one already exists
      theTWA.getScreen().repaint();

    /*** SECTOR ***/
    } else if (theString.startsWith("Sector  :")) {
      strTemp = theString.substring(theString.indexOf(':') + 2, theString.indexOf("in")).trim();

      //System.out.println("Display Current Sector: " + strTemp);

      try {
        iSector = (new Integer(strTemp)).intValue();
      } catch (NumberFormatException e) {
        iSector = 0;
      }

      sectorTemp = new Sector(iSector, theTWA);
      
      theTWA.getBBS().getGameData().addSector(sectorTemp);
      ((Sector) theTWA.getBBS().getGameData().findSector(iSector)).setExplored(true);
      ((Sector) theTWA.getBBS().getGameData().findSector(iSector)).setHopNumber(theTWA.getBBS().getGameData().getTotalHops());
      theTWA.setCurrentSector(theTWA.getBBS().getGameData().findSector(iSector));

      theTWA.getScreen().setState(Screen.IN_SECTOR);
      theTWA.getScreen().repaint();

      iState = STATE_NORMAL;


    } else if (theString.startsWith("Warping to Sector")) {
      theTWA.getBBS().getGameData().decrementTurns();
      theTWA.getBBS().getGameData().incrementHops();

      iState = STATE_NORMAL;

      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Auto Warping to sector")) {
      theTWA.getBBS().getGameData().decrementTurns();

      iState = STATE_NORMAL;

      theTWA.getScreen().repaint();



    } else if (theString.startsWith("Trader Name  :")) {

      iState = STATE_NORMAL;

    } else if (theString.startsWith("Rank and Exp  :")) {

      iState = STATE_NORMAL;

    } else if (theString.startsWith("Ship Name      :")) {
      theTWA.getBBS().getGameData().getCurrentShip().setName(theString.substring(17));
      iState = STATE_NORMAL;


    /** SHIP TRANSPORT **/
    } else if (theString.startsWith("Your") && (theString.indexOf("has a transport range of") != -1) && (theString.indexOf("hops") != -1)) {
      theTWA.getBBS().getGameData().getCurrentShip().setType(theString.substring(5, theString.indexOf(' ', 5)));
      iState = STATE_NORMAL;

    } else if (theString.startsWith("Ship Info      :")) {
//      System.out.println("Found ship info line");

      if        (theString.indexOf("Merchant Cruiser") != -1) {
        //System.out.println("Found Merchant Cruiser");

        theTWA.getBBS().getGameData().getCurrentShip().setType("Merchant Cruiser");
      } else if (theString.indexOf("Scout Marauder") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Scout Marauder");
      } else if (theString.indexOf("Missile Frigate") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Missile Frigate");
      } else if (theString.indexOf("BattleShip") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("BattleShip");
      } else if (theString.indexOf("Corporate FlagShip") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Corporate FlagShip");
      } else if (theString.indexOf("Colonial Transport") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Colonial Transport");
      } else if (theString.indexOf("CargoTran") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("CargoTran");
      } else if (theString.indexOf("Merchant Freighter") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Merchant Freighter");
      } else if (theString.indexOf("Imperial StarShip") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Imperial StarShip");
      } else if (theString.indexOf("Havoc Gunstar") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Havoc Gunstar");
      } else if (theString.indexOf("StarMaster") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("StarMaster");
      } else if (theString.indexOf("Constellation") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Constellation");
      } else if (theString.indexOf("T'Khasi Orion") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("T'Khasi Orion");
      } else if (theString.indexOf("Tholian Sentinel") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Tholian Sentinel");
      } else if (theString.indexOf("Taurean Mule") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Taurean Mule");
      } else if (theString.indexOf("Interdictor Cruiser") != -1) {
        theTWA.getBBS().getGameData().getCurrentShip().setType("Interdictor Cruiser");
      }

      iState = STATE_NORMAL;


    } else if (theString.startsWith("Date Built  :")) {
      iState = STATE_NORMAL;

    } else if (theString.startsWith("NavHaz      : 1% (Space Debris/Asteroids)")) {
      System.out.println("NavHaz found");
      iState = STATE_NORMAL;

    } else if (theString.startsWith("Turns to Warp  :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setTurnsToWarp((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;

      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Current Sector  :")) {
      iState = STATE_NORMAL;

    } else if (theString.startsWith("You have") &&
               (theString.indexOf("credits and") >= 0) &&
               (theString.indexOf("empty cargo holds.") >= 0) ) {

      strTemp = theString.substring(9, theString.indexOf(' ', 9));
      theTWA.getBBS().getGameData().setCredits(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getCredits()));
      iState = STATE_NORMAL;

    } else if (theString.startsWith("One turn deducted,") &&
               (theString.indexOf("turns left.") >= 0) ) {
      strTemp = theString.substring(19, theString.indexOf(' ', 19));
      theTWA.getBBS().getGameData().setTurns(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getTurnsLeft()));
      iState = STATE_NORMAL;


    } else if (theString.startsWith("Turns left     :")) {
      //System.out.println("setting turns");
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().setTurns((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }

      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Total Holds    :")) {
     int k;

      if (theString.indexOf("Fuel Ore=") > -1) {
        k = theString.indexOf(' ', theString.indexOf("Fuel Ore=") + 9);
        if (k > 0) {
          strTemp = theString.substring(theString.indexOf("Fuel Ore=") + 9, k);
        } else {
          strTemp = theString.substring(theString.indexOf("Fuel Ore=") + 9);
        }

        theTWA.getBBS().getGameData().getCurrentShip().setFuelOre(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getCurrentShip().getFuelOre()));
      } else {
        theTWA.getBBS().getGameData().getCurrentShip().setFuelOre(0);
      }

      if (theString.indexOf("Organics=") > -1) {
        k = theString.indexOf(' ', theString.indexOf("Organics=") + 9);
        if (k > 0) {
          strTemp = theString.substring(theString.indexOf("Organics=") + 9, k);
        } else {
          strTemp = theString.substring(theString.indexOf("Organics=") + 9);
        }

        theTWA.getBBS().getGameData().getCurrentShip().setOrganics(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getCurrentShip().getOrganics()));
      } else {
        theTWA.getBBS().getGameData().getCurrentShip().setOrganics(0);
      }

      if (theString.indexOf("Equipment=") > -1) {
        k = theString.indexOf(' ', theString.indexOf("Equipment=") + 10);
        if (k > 0) {
          strTemp = theString.substring(theString.indexOf("Equipment=") + 10, k);
        } else {
          strTemp = theString.substring(theString.indexOf("Equipment=") + 10);
        }

        theTWA.getBBS().getGameData().getCurrentShip().setEquipment(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getCurrentShip().getEquipment()));
      } else {
        theTWA.getBBS().getGameData().getCurrentShip().setEquipment(0);
      }

      if (theString.indexOf("Colonists=") > -1) {
        k = theString.indexOf(' ', theString.indexOf("Colonists=") + 10);
        if (k > 0) {
          strTemp = theString.substring(theString.indexOf("Colonists=") + 10, k);
        } else {
          strTemp = theString.substring(theString.indexOf("Colonists=") + 10);
        }

        theTWA.getBBS().getGameData().getCurrentShip().setColonists(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getCurrentShip().getColonists()));
      } else {
        theTWA.getBBS().getGameData().getCurrentShip().setColonists(0);
      }



      iState = STATE_NORMAL;
    } else if (theString.trim().startsWith("Ship Name      :")) {
      strTemp = theString.substring(17);
      theTWA.getBBS().getGameData().getCurrentShip().setName(strTemp);

      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Ship Info      :")) {
      iState = STATE_NORMAL;
    } else if (theString.trim().startsWith("Date Built     :")) {
      iState = STATE_NORMAL;
    } else if (theString.startsWith("Shield points  :")) {
      strTemp = theString.substring(17);


      theTWA.getBBS().getGameData().getCurrentShip().setShields(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getCurrentShip().getShields()));

      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.trim().indexOf("Armid Mines  T1:") != -1) {
      i = theString.trim().indexOf("Armid Mines  T1:") + 17;

      if ( (theString.indexOf(' ', i) < theString.length()) &&
           (theString.indexOf(' ', i) >= 0)
         ) {
        j = theString.indexOf(' ', i);
      } else {
        j = theString.length();
      }

      strTemp = theString.substring(i, j);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setArmidMines((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.trim().indexOf("Limpet Mines T2:") != -1) {

      i = theString.trim().indexOf("Limpet Mines T2:") + 17;

      if ( (theString.indexOf(' ', i) < theString.length()) &&
           (theString.indexOf(' ', i) >= 0)
         ) {
        j = theString.indexOf(' ', i);
      } else {
        j = theString.length();
      }

      strTemp = theString.substring(i, j);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setLimpetMines((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();


    } else if (theString.trim().startsWith("Genesis Torps  :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setGTorps((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();
    } else if (theString.trim().startsWith("Atomic Detn.   :")) {
      strTemp = theString.substring(17);
      theTWA.getBBS().getGameData().getCurrentShip().setAtomicDetonators(Parser.toInteger(strTemp, 0));
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Ether Probes   :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setEtherProbes((new Integer(strTemp)).intValue());
      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Mine Disruptors:")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setMineDisruptors((new Integer(strTemp)).intValue());
      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();
    } else if (theString.trim().startsWith("Psychic Probe  :")) {
      iState = STATE_NORMAL;
    } else if (theString.trim().startsWith("LongRange Scan : Holographic Scanner")) {
      iState = STATE_NORMAL;
    } else if (theString.trim().startsWith("TransWarp Power")) {
      iState = STATE_NORMAL;
    } else if (theString.trim().startsWith("(Type 1 Jump):")) {
      iState = STATE_NORMAL;
    } else if (theString.trim().startsWith("(Type 2 Jump):")) {
      iState = STATE_NORMAL;
    } else if (theString.trim().startsWith("Interdictor ON :")) {
      iState = STATE_NORMAL;

    } else if (theString.startsWith("Fighters       :")) {
      strTemp = theString.substring(17);
      theTWA.getBBS().getGameData().getCurrentShip().setFighters(Parser.toInteger(strTemp, theTWA.getBBS().getGameData().getCurrentShip().getFighters()));
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Credits        :")) {
      strTemp = theString.substring(17);
//      System.out.println("Credits: " + strTemp);

      theTWA.getBBS().getGameData().setCredits(toInteger(strTemp, theTWA.getBBS().getGameData().getCredits()));
//      System.out.println("Credits: " + toInteger(strTemp));

      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("You have") && theString.endsWith("turns this Stardate.")) {
      strTemp = theString.substring(9, 12);
      //System.out.println(strTemp);
      try {
        theTWA.getBBS().getGameData().setTurns((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();
      theTWA.getTelnet().getSender().sendData("iv");
      theTWA.getFrame().toFront();


    /** PLANETS **/
    } else if (theString.startsWith("Planets :")) {
      //System.out.println("Found a planet");
      planetTemp = new Planet();
      planetTemp.setType(theString.charAt(11));
      planetTemp.setName(theString.substring(14, theString.length()));

      theTWA.getCurrentSector().clearPlanets();
      theTWA.getCurrentSector().setPlanet(planetTemp);

      theTWA.getScreen().soundPlanet.play();
      iState = STATE_GETTING_PLANETS;

    } else if (  (theString.length() > 12) && (theString.charAt(10) == '(') && (theString.charAt(12) == ')') &&
                 (iState == STATE_GETTING_PLANETS)) {
      planetTemp = new Planet();
      planetTemp.setType(theString.charAt(11));
      planetTemp.setName(theString.substring(14, theString.length()));


      if (theTWA.getCurrentSector() != null) {
        theTWA.getCurrentSector().setPlanet(planetTemp);
      }

      iState = STATE_GETTING_PLANETS;

    /** FIGHTERS **/
    } else if (theString.trim().startsWith("Fighters:")) {
      strTemp = theString.substring(10, theString.indexOf(' ', 10));

      theTWA.getCurrentSector().setFighters(Parser.toInteger(strTemp, 0));

      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Ships   :")) {

    /** ARMID MINES **/
    } else if ((theString.trim().indexOf("(Type 1 Armid)")) != -1) {
      strTemp = theString.substring(10, theString.indexOf(' ', 10));

      try {
        theTWA.getCurrentSector().setArmidMines((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    /** LIMPET MINES **/
    } else if ((theString.trim().indexOf("(Type 2 Limpet)")) != -1) {
      strTemp = theString.substring(10, theString.indexOf(' ', 10));

      try {
        theTWA.getCurrentSector().setLimpetMines((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();




    /** ALIENS MINES **/
/*
    } else if ((theString.trim().indexOf("(Type 2 Limpet)")) != -1) {
              Aliens  : Menace 2nd Class Cesha Deemab, w/ 300 ftrs,

              in Etheiv Thuqoqiv (Antarian Merchant Freighter)
*/

    /** V INFORMATION **/
    } else if (theString.startsWith(" Initial Turns per day")) {

      strTemp = theString.substring(23, theString.indexOf(',', 23));
//      System.out.println("Initial Turns: " + strTemp);

      try {
        theTWA.getBBS().getGameData().setInitialTurns((new Integer(strTemp)).intValue());
      } catch (NumberFormatException e) { }

      iState = STATE_NORMAL;
      theTWA.getScreen().repaint();

    /** TRANSWARP **/
    } else if (theString.indexOf("TransWarp Drive Engaged!") > -1) {
      iState = STATE_NORMAL;
      theTWA.getScreen().soundTranswarp.play();

    /** PROMOTION **/
    } else if (theString.startsWith("You have been promoted to")) {
      iState = STATE_NORMAL;
      theTWA.getScreen().soundLevelup.play();
    /** PLANET DESTRUCTION **/
    } else if (theString.indexOf("awesomeimplosion.") > -1) {
      iState = STATE_NORMAL;
      theTWA.getScreen().soundPlanetDestroy.play();

    /** TWARP WHEN LOCKED **/
    } else if (theString.indexOf("All Systems Ready, shall we engage?") > -1) {
      if (theTWA.getBBS().getGameData().getTWarpWhenLocked()) {
        theTWA.getTelnet().getSender().sendData("y");
      }

    /** STOP WHEN NOT LOCKED **/
    } else if (theString.indexOf("Do you want to make this jump blind?") > -1) {
      if (theTWA.getBBS().getGameData().getStopWhenNotLocked()) {
        theTWA.getTelnet().getSender().sendData("n");
      }


    } else {
      //could not find the string
      iState = STATE_NORMAL;

    }

    for(i = 0; i < vectParsers.size(); i++) {
      ((LineParser) vectParsers.elementAt(i)).parseString(theString);
    }

  }

  /**
    * getParsers - gets a vector containing all the parsers
    **/
  public Vector getParsers() {
    return vectParsers;
  }

  /**
    * stopScripts - stops all scripts
    **/
  public void stopScripts() {
    int i;
    for (i = 0; i < vectParsers.size(); i++) {
      vectParsers.removeElementAt(i);
    }
    theTWA.getTelnet().getSender().setUserInputEnabled(true);

  }

  /**
    * toInteger - removes the commas from a String and converts it to an
    *             integer
    **/
  public static int toInteger(String str, int iDefault) {
    int i, iToReturn;
    String strTemp;

    iToReturn = 0;
    strTemp = "";
    for (i = 0; i < str.length(); i++) {
      if (Character.isDigit(str.charAt(i))) {
        strTemp += str.charAt(i);
      }
    }

    try {
      iToReturn = (new Integer(strTemp)).intValue();
    } catch (NumberFormatException e) {
      iToReturn = iDefault;
    }

    return iToReturn;
  }

  /**
    * getHops
    **/
  public int getHops() {
    int i;
    i = -1;

    if (iHops > -1) {
      i = iHops;
      iHops = -1;
    }
    return i;
  }




}
