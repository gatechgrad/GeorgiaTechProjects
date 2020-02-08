import java.util.Vector;

public class Parser {

  /** INSTANCE VARIABLES **/
  private TWA theTWA;

  /**
    * Parser
    **/
  public Parser(TWA theTWA) {
    this.theTWA = theTWA;

  }


  /**
    * parseString
    **/
  public void parseString(String theString) {
    String strTemp;
    int i, j;
    int iSector;
    Sector sectorTemp;
    Planet planetTemp;

    /*** WARPS TO SECTOR ***/
    if (theString.startsWith("Warps to Sector")) {
      //System.out.println("Display adjacent Sectors");

      Vector vectTemp = new Vector();

      theString = theString.substring(22);

      i = 0;
      j = theString.indexOf('-');

      while ((i != -1) && (j != -1)) {

        if (j != -1) {
          strTemp = theString.substring(i, j - 1);
          //System.out.println("***" + strTemp + "***");
          try {
            if ((strTemp.charAt(0) == '(') && (strTemp.charAt(strTemp.length() - 1) == ')')) {
                iSector = (new Integer(strTemp.substring(1, strTemp.length() - 1))).intValue();
                sectorTemp = new Sector(iSector);
                sectorTemp.setExplored(false);
            } else {
                iSector = (new Integer(strTemp)).intValue();
                sectorTemp = new Sector(iSector);
                sectorTemp.setExplored(true);
            }
            theTWA.getBBS().getGameData().addSector(sectorTemp);
            vectTemp.addElement(theTWA.getBBS().getGameData().findSector(iSector));

          } catch (NumberFormatException e) { }
          
        } 

        i = j + 2;
        j = theString.indexOf('-', j + 1);
      } 

      strTemp = theString.substring(i, theString.length());
      //System.out.println("***" + strTemp + "***");
      try {
        if ((strTemp.charAt(0) == '(') && (strTemp.charAt(strTemp.length() - 1) == ')')) {
          iSector = (new Integer(strTemp.substring(1, strTemp.length() - 1))).intValue();
          sectorTemp = new Sector(iSector);
          sectorTemp.setExplored(false);
        } else {
          iSector = (new Integer(strTemp)).intValue();
          sectorTemp = new Sector(iSector);
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

    /*** SECTOR ***/
    } else if (theString.startsWith("Sector  :")) {
      strTemp = theString.substring(theString.indexOf(':') + 2, theString.indexOf("in")).trim();

      //System.out.println("Display Current Sector: " + strTemp);

      try {
        iSector = (new Integer(strTemp)).intValue();
      } catch (NumberFormatException e) {
        iSector = 0;
      }

      sectorTemp = new Sector(iSector);
      theTWA.getBBS().getGameData().addSector(sectorTemp);
      ((Sector) theTWA.getBBS().getGameData().findSector(iSector)).setExplored(true);

      theTWA.setCurrentSector(theTWA.getBBS().getGameData().findSector(iSector));

      theTWA.getScreen().setState(Screen.IN_SECTOR);
      theTWA.getScreen().repaint();

      //theTWA.getBBS().getGameData().printSectors();
      theTWA.getBBS().getGameData().writeGameData("test.dat");

    } else if (theString.startsWith("Warping to Sector")) {
      theTWA.getBBS().getGameData().decrementTurns();
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Auto Warping to sector")) {
      theTWA.getBBS().getGameData().decrementTurns();
      theTWA.getScreen().repaint();



    } else if (theString.startsWith("Trader Name  :")) {

    } else if (theString.startsWith("Rank and Exp  :")) {

    } else if (theString.startsWith("Ship Name  :")) {

    } else if (theString.startsWith("Ship Info  :")) {

    } else if (theString.startsWith("Date Built  :")) {

    } else if (theString.startsWith("NavHaz      : 1% (Space Debris/Asteroids)")) {
      System.out.println("NavHaz found");

    } else if (theString.startsWith("Turns to Warp  :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().setTurnsToWarp((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Current Sector  :")) {

    } else if (theString.startsWith("Turns left     :")) {
      //System.out.println("setting turns");
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().setTurns((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Total Holds    :")) {

    } else if (theString.trim().startsWith("Times Blown Up :")) {
    } else if (theString.trim().startsWith("Corp           #")) {
    } else if (theString.trim().startsWith("Ship Name      :")) {
    } else if (theString.trim().startsWith("Ship Info      :")) {
    } else if (theString.trim().startsWith("Date Built     :")) {
    } else if (theString.trim().startsWith("Shield points  :")) {
    } else if (theString.trim().startsWith("Armid Mines  T1:")) {
    } else if (theString.trim().startsWith("Genesis Torps  :")) {
    } else if (theString.trim().startsWith("Atomic Detn.   :")) {
    } else if (theString.trim().startsWith("Ether Probes   :")) {
    } else if (theString.trim().startsWith("Mine Disruptors:")) {
    } else if (theString.trim().startsWith("Psychic Probe  :")) {
    } else if (theString.trim().startsWith("LongRange Scan : Holographic Scanner")) {
    } else if (theString.trim().startsWith("TransWarp Power")) {
    } else if (theString.trim().startsWith("(Type 1 Jump):")) {
    } else if (theString.trim().startsWith("(Type 2 Jump):")) {
    } else if (theString.trim().startsWith("Interdictor ON :")) {

    } else if (theString.startsWith("Fighters       :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().setFighters((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("Credits        :")) {
      strTemp = theString.substring(17);
      try {
        theTWA.getBBS().getGameData().setCredits((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();

    } else if (theString.startsWith("You have") && theString.endsWith("turns this Stardate.")) {
      strTemp = theString.substring(9, 12);
      //System.out.println(strTemp);
      try {
        theTWA.getBBS().getGameData().setTurns((new Integer(strTemp)).intValue());

      } catch (NumberFormatException e) { }
      theTWA.getScreen().repaint();
      theTWA.getTelnet().getSender().sendData("i\r");


    /** PLANETS **/
    } else if (theString.startsWith("Planets :")) {
      System.out.println("Found a planet");
      planetTemp = new Planet();
      planetTemp.setType(theString.charAt(12));
      planetTemp.setName(theString.substring(14, theString.length()));
      theTWA.getCurrentSector().setPlanet(planetTemp);


    } else if ((theString.length() > 3) && (theString.charAt(0) == '(') && (theString.charAt(2) == ')')) {
      System.out.println("Found a planet");
      planetTemp = new Planet();
      planetTemp.setType(theString.charAt(2));
      planetTemp.setName(theString.substring(4, theString.length()));


      if (theTWA.getCurrentSector() != null) {
        theTWA.getCurrentSector().setPlanet(planetTemp);
      }


    } else if (theString.trim().startsWith("Fighters:")) {

    } else if (theString.trim().startsWith("Ships   :")) {

    } else if (theString.trim().startsWith("Mines   :")) {

    } else {
      //could not find the string
    }

  }









}
