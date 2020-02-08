import java.util.Vector;

public class Parser implements LineParser {

  /** INSTANCE VARIABLES **/
  private TWA theTWA;
  private int iHops;
  private Vector vectParsers;

  /**
    * Parser
    **/
  public Parser(TWA theTWA) {
    this.theTWA = theTWA;
    iHops = -1;
    vectParsers = new Vector();

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

      theTWA.getCurrentSector().setPort(portTemp);
      theTWA.getScreen().repaint();
      theTWA.getScreen().soundPort.play();

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

      //theTWA.getBBS().getGameData().printSectors();
      //theTWA.getBBS().getGameData().writeGameData("test.dat");

    } else if (theString.startsWith("Warping to Sector")) {
      theTWA.getBBS().getGameData().decrementTurns();
      theTWA.getBBS().getGameData().incrementHops();
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Auto Warping to sector")) {
      theTWA.getBBS().getGameData().decrementTurns();
      theTWA.getScreen().repaint();



    } else if (theString.startsWith("Trader Name  :")) {

    } else if (theString.startsWith("Rank and Exp  :")) {

    } else if (theString.startsWith("Ship Name      :")) {
      theTWA.getBBS().getGameData().getCurrentShip().setName(theString.substring(17));


    /** SHIP TRANSPORT **/
    } else if (theString.startsWith("Your") && (theString.indexOf("has a transport range of") != -1) && (theString.indexOf("hops") != -1)) {
      theTWA.getBBS().getGameData().getCurrentShip().setType(theString.substring(6, theString.indexOf(' ', 6)));
    } else if (theString.startsWith("Ship Info      :")) {
//      System.out.println("Found ship info line");

      if        (theString.indexOf("Merchant Cruiser") != -1) {
        System.out.println("Found Merchant Cruiser");

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


    } else if (theString.startsWith("Date Built  :")) {

    } else if (theString.startsWith("NavHaz      : 1% (Space Debris/Asteroids)")) {
      System.out.println("NavHaz found");

    } else if (theString.startsWith("Turns to Warp  :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setTurnsToWarp((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Current Sector  :")) {

    } else if (theString.startsWith("You have") &&
               (theString.indexOf("credits and") >= 0) &&
               (theString.indexOf("empty cargo holds.") >= 0) ) {

      strTemp = theString.substring(9, theString.indexOf(' ', 9));
      theTWA.getBBS().getGameData().setCredits(Parser.toInteger(strTemp));

    } else if (theString.startsWith("One turn deducted,") &&
               (theString.indexOf("turns left.") >= 0) ) {
      strTemp = theString.substring(19, theString.indexOf(' ', 19));
      theTWA.getBBS().getGameData().setTurns(Parser.toInteger(strTemp));


    } else if (theString.startsWith("Turns left     :")) {
      //System.out.println("setting turns");
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().setTurns((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
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

        theTWA.getBBS().getGameData().getCurrentShip().setFuelOre(Parser.toInteger(strTemp));
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

        theTWA.getBBS().getGameData().getCurrentShip().setOrganics(Parser.toInteger(strTemp));
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

        theTWA.getBBS().getGameData().getCurrentShip().setEquipment(Parser.toInteger(strTemp));
      } else {
        theTWA.getBBS().getGameData().getCurrentShip().setEquipment(0);
      }









    } else if (theString.startsWith("Ship Name      :")) {

    } else if (theString.trim().startsWith("Times Blown Up :")) {
    } else if (theString.trim().startsWith("Corp           #")) {
    } else if (theString.trim().startsWith("Ship Name      :")) {
      strTemp = theString.substring(17);
      theTWA.getBBS().getGameData().getCurrentShip().setName(strTemp);
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Ship Info      :")) {
    } else if (theString.trim().startsWith("Date Built     :")) {
    } else if (theString.trim().startsWith("Shield points  :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setShields((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
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
      theTWA.getScreen().repaint();


    } else if (theString.trim().startsWith("Genesis Torps  :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setGTorps((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();
    } else if (theString.trim().startsWith("Atomic Detn.   :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setAtomicDetonators((new Integer(strTemp)).intValue());
      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Ether Probes   :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setEtherProbes((new Integer(strTemp)).intValue());
      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Mine Disruptors:")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setMineDisruptors((new Integer(strTemp)).intValue());
      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();
    } else if (theString.trim().startsWith("Psychic Probe  :")) {
    } else if (theString.trim().startsWith("LongRange Scan : Holographic Scanner")) {
    } else if (theString.trim().startsWith("TransWarp Power")) {
    } else if (theString.trim().startsWith("(Type 1 Jump):")) {
    } else if (theString.trim().startsWith("(Type 2 Jump):")) {
    } else if (theString.trim().startsWith("Interdictor ON :")) {

    } else if (theString.startsWith("Fighters       :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().getCurrentShip().setFighters((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Credits        :")) {
      strTemp = theString.substring(17);
//      System.out.println("Credits: " + strTemp);

      theTWA.getBBS().getGameData().setCredits(toInteger(strTemp));
//      System.out.println("Credits: " + toInteger(strTemp));

      theTWA.getScreen().repaint();

    } else if (theString.startsWith("You have") && theString.endsWith("turns this Stardate.")) {
      strTemp = theString.substring(9, 12);
      //System.out.println(strTemp);
      try {
        theTWA.getBBS().getGameData().setTurns((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();
      theTWA.getTelnet().getSender().sendData("iv\r");
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


    } else if ((theString.length() > 3) && (theString.charAt(0) == '(') && (theString.charAt(2) == ')')) {
      planetTemp = new Planet();
      planetTemp.setType(theString.charAt(1));
      planetTemp.setName(theString.substring(4, theString.length()));


      if (theTWA.getCurrentSector() != null) {
        theTWA.getCurrentSector().setPlanet(planetTemp);
      }

    /** FIGHTERS **/
    } else if (theString.trim().startsWith("Fighters:")) {
      String strNumber;
      strNumber = theString.substring(10, theString.indexOf(' ', 10));

      //strip commas from the number
      strTemp = "";
      for (i = 0; i < strTemp.length(); i++) {
        if (strNumber.charAt(i) != ',') {
          strTemp += strNumber.charAt(i);
        }
      }

      try {
        theTWA.getCurrentSector().setFighters((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.trim().startsWith("Ships   :")) {

    /** ARMID MINES **/
    } else if ((theString.trim().indexOf("(Type 1 Armid)")) != -1) {
      strTemp = theString.substring(10, theString.indexOf(' ', 10));

      try {
        theTWA.getCurrentSector().setArmidMines((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    /** LIMPET MINES **/
    } else if ((theString.trim().indexOf("(Type 2 Limpet)")) != -1) {
      strTemp = theString.substring(10, theString.indexOf(' ', 10));

      try {
        theTWA.getCurrentSector().setLimpetMines((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();




    /** ALIENS MINES **/
/*
    } else if ((theString.trim().indexOf("(Type 2 Limpet)")) != -1) {
              Aliens  : Menace 2nd Class Cesha Deemab, w/ 300 ftrs,

              in Etheiv Thuqoqiv (Antarian Merchant Freighter)
*/

    /** V INFORMATION **/
    } else if (theString.startsWith("Initial Turns per day")) {

      strTemp = theString.substring(22, theString.indexOf(',', 22));
//      System.out.println("Initial Turns: " + strTemp);

      try {
        theTWA.getBBS().getGameData().setInitialTurns((new Integer(strTemp)).intValue());
      } catch (NumberFormatException e) { }

      theTWA.getScreen().repaint();

    /** TRANSWARP **/
    } else if (theString.indexOf("TransWarp Drive Engaged!") > -1) {
      theTWA.getScreen().soundTranswarp.play();

    } else {
      //could not find the string
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
    * toInteger - removes the commas from a String and converts it to an
    *             integer
    **/
  public static int toInteger(String str) {
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
    } catch (NumberFormatException e) { }

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
