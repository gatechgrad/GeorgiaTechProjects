import java.awt.*;
import java.awt.image.ImageObserver;
import javax.swing.*;

class Ship {

  /** CONSTANTS **/
  public static final int SHIELD_LINES = 5;

  /** INSTANCE VARIABLES **/
  private int iArmidMines;
  private int iLimpetMines;
  private int iFighters;
  private String strName;
  private String strType;
  private int iGTorps;
  private int iPhotons;
  private int iAtomicDetonators;
  private int iEtherProbes;
  private int iMineDisruptors;

  private int iFuelOre;
  private int iOrganics;
  private int iEquipment;
  private int iColonists;
  private int iEmpty;
  private int iTotalHolds;
  private int iTurnsToWarp;
  private int iShields;

  /**
    * Ship
    **/
  public Ship() {
    iTurnsToWarp = 0;
    iFuelOre = 0;
    iOrganics = 0;
    iEquipment = 0;
    iColonists = 0;
    strType = "";
    strName = "";

  }

  /**
    * get
    **/
  public int getTurnsToWarp() { return iTurnsToWarp; }
  public int getFuelOre() { return iFuelOre; }
  public int getOrganics() { return iOrganics; }
  public int getEquipment() { return iEquipment; }
  public int getColonists() { return iColonists; }
  public int getFighters() { return iFighters; }
  public int getShields() { return iShields; }

  public String getType() { return strType; }
  public String getName() { return strName; }



  /**
    * set
    **/
  public void setArmidMines(int i) { iArmidMines = i; }
  public void setLimpetMines(int i) { iLimpetMines = i; }
  public void setFighters(int iSet) { iFighters = iSet; }
  public void setShields(int i) { iShields = i; }
  public void setGTorps(int i) { iGTorps = i; }
  public void setAtomicDetonators(int i) { iAtomicDetonators = i; }
  public void setEtherProbes(int i) { iEtherProbes = i; }
  public void setMineDisruptors(int i) { iMineDisruptors = i; }
  public void setPhotons(int i) { iPhotons = i; }

  public void setTurnsToWarp(int iSet) { iTurnsToWarp = iSet; }
  public void setName(String strName) { this.strName = strName; }
  public void setType(String strType) { this.strType = strType; }
  public void setFuelOre(int iSet) { iFuelOre = iSet; }
  public void setOrganics(int iSet) { iOrganics = iSet; }
  public void setEquipment(int iSet) { iEquipment = iSet; }
  public void setColonists(int iSet) { iColonists = iSet; }

  /**
    * add
    **/
  public void addFuelOre(int iToAdd) { iFuelOre += iToAdd; }
  public void addOrganics(int iToAdd) { iOrganics += iToAdd; }
  public void addEquipment(int iToAdd) { iEquipment += iToAdd; }

  /**
    * burnFuel
    **/
  public void burnFuel(int iFuelBurnt) {
    iFuelOre -= iFuelBurnt;
  }



  /**
    * drawShipView
    **/
  public void drawShipView(Graphics g, int x, int y, ImageObserver theObserver) {
    int i;
    int iLevel;
    int iTimes;
    Color colorShield;


    g.setColor(Screen.FG_COLOR);
    g.setFont(Screen.FONT);

    if (strType.equals("Merchant Cruiser")) {
      g.drawImage(Screen.imgShip[1], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Scout Marauder")) {
      g.drawImage(Screen.imgShip[2], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Missile Frigate")) {
      g.drawImage(Screen.imgShip[3], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("BattleShip")) {
      g.drawImage(Screen.imgShip[4], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Corporate FlagShip")) {
      g.drawImage(Screen.imgShip[5], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Colonial Transport")) {
      g.drawImage(Screen.imgShip[6], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("CargoTran")) {
      g.drawImage(Screen.imgShip[7], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Merchant Freighter")) {
      g.drawImage(Screen.imgShip[8], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Imperial StarShip")) {
      g.drawImage(Screen.imgShip[9], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Havoc GunStar")) {
      g.drawImage(Screen.imgShip[10], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("StarMaster")) {
      g.drawImage(Screen.imgShip[11], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Constellation")) {
      g.drawImage(Screen.imgShip[12], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("T'Khasi Orion")) {
      g.drawImage(Screen.imgShip[13], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Tholian Sentinel")) {
      g.drawImage(Screen.imgShip[14], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Taurean Mule")) {
      g.drawImage(Screen.imgShip[15], x + 25, y + 15, 50, 50, theObserver);
    } else if (strType.equals("Interdictor Cruiser")) {
      g.drawImage(Screen.imgShip[16], x + 25, y + 15, 50, 50, theObserver);
    } else {
      g.drawImage(Screen.imgShip[9], x + 25, y + 15, 50, 50, theObserver);
      /*
      g.drawString("No ship Image", x + 5, y + 20);
      g.drawString("available", x + 5, y + 34);
      */
    }

    colorShield = Screen.FG_COLOR;

    if (iShields > SHIELD_LINES) {
      iLevel = (iShields - SHIELD_LINES - 1) / SHIELD_LINES;
      if ( (iLevel < 4) && (iLevel >= 0)) {
        colorShield = ( new Color((iLevel * 4) + 127, 0, 0));  //reds
      } else if ( (iLevel < 12) && (iLevel >= 4)) {
        iLevel -= 4;
        colorShield = ( new Color((iLevel * 16) + 127, (iLevel * 8) + 127, 0));  //yellows
      } else if ( (iLevel < 140) && (iLevel >= 12)) {
        iLevel -= 12;
        colorShield = ( new Color(0, 0, iLevel + 127));  //blues
      } else if ( (iLevel < 268) && (iLevel >= 140)) {
        iLevel -= 140;
        colorShield = ( new Color(0, iLevel + 127, 0));  //greens
      } else {
      }

      iTimes =  SHIELD_LINES;
      g.setColor(colorShield);

      for (i = 0; i < iTimes; i++) {
        //refresh after port trade
        //horizontal lines
        g.drawLine(x + 30 - (2 * i), y + 15 - (2 * i), x + 70 + (2 * i), y + 15 - (2 * i));
        g.drawLine(x + 30 - (2 * i), y + 65 + (2 * i), x + 70 + (2 * i), y + 65 + (2 * i));


        //vertical lines
        g.drawLine(x + 25 - (2 * i), y + 20 - (2 * i), x + 25 - (2 * i), y + 60 + (2 * i));
        g.drawLine(x + 75 + (2 * i), y + 20 - (2 * i), x + 75 + (2 * i), y + 60 + (2 * i));
      }
    }


    iLevel = iShields / SHIELD_LINES;
      if ( (iLevel < 4) && (iLevel >= 0)) {
        colorShield = ( new Color((iLevel * 4) + 127, 0, 0));  //reds
      } else if ( (iLevel < 12) && (iLevel >= 4)) {
        iLevel -= 4;
        colorShield = ( new Color((iLevel * 16) + 127, (iLevel * 8) + 127, 0));  //yellows
      } else if ( (iLevel < 140) && (iLevel >= 12)) {
        iLevel -= 12;
        colorShield = ( new Color(0, 0, iLevel + 127));  //blues
      } else if ( (iLevel < 268) && (iLevel >= 140)) {
        iLevel -= 140;
        colorShield = ( new Color(0, iLevel + 127, 0));  //greens
      } else {
      }


    g.setColor(colorShield);
    iTimes = iShields % SHIELD_LINES;
    if (  ((iTimes % SHIELD_LINES) == 0) &&
          (iShields > 0)
       ) {
      iTimes = SHIELD_LINES;
    }

    for (i = 0; i < iTimes; i++) {
      //refresh after port trade
      //horizontal lines
      g.drawLine(x + 30 - (2 * i), y + 15 - (2 * i), x + 70 + (2 * i), y + 15 - (2 * i));
      g.drawLine(x + 30 - (2 * i), y + 65 + (2 * i), x + 70 + (2 * i), y + 65 + (2 * i));


      //vertical lines
      g.drawLine(x + 25 - (2 * i), y + 20 - (2 * i), x + 25 - (2 * i), y + 60 + (2 * i));
      g.drawLine(x + 75 + (2 * i), y + 20 - (2 * i), x + 75 + (2 * i), y + 60 + (2 * i));
    }

    g.setColor(Screen.FG_COLOR);


    g.drawString(strName, x + 5, y + 86);
    g.drawString(strType, x + 5, y + 98);

  }

  /**
    * drawItemView
    **/
  public Image drawItemView(JComponent jcScreen) {
    int iMagnitude;

    Image imgTemp = jcScreen.createImage(Screen.COMPUTER.width, Screen.COMPUTER.height);
    Graphics g = imgTemp.getGraphics();
    Graphics2D g2 = (Graphics2D) g;

    int i;
    int x = 0;
    int y = 0;
    int y2;

    Color colorFuelOre1 = new Color(255, 0, 0);
    Color colorFuelOre2 = new Color(128, 0, 0);
    Color colorOrganics1 = new Color(0, 255, 0);
    Color colorOrganics2 = new Color(0, 128, 0);
    Color colorEquipment1 = new Color(0, 0, 255);
    Color colorEquipment2 = new Color(0, 0, 128);
    Color colorColonists1 = new Color(255, 255, 0);
    Color colorColonists2 = new Color(128, 128, 0);

    FontMetrics fm = g2.getFontMetrics(g2.getFont());

    g2.setColor(Color.black);
    g2.fillRect(0, 0, Screen.COMPUTER.width, Screen.COMPUTER.height);

    iMagnitude = iFuelOre / 10;
    g2.setColor(colorFuelOre1);
    g2.fillRect(x + 10, y + 5, iMagnitude * 3, 8);
    g2.setColor(colorFuelOre2);
    g2.drawRect(x + 10, y + 5, iMagnitude * 3, 8);
    for (i = 0; i < iMagnitude; i++) {
      g2.drawLine(x + 10 + (i * 3), y + 5, x + 10 + (i * 3), y + 13);
    }
    g2.setColor(colorFuelOre1);
    g2.drawString("" + iFuelOre, x + 10 + (iMagnitude * 3), y + 5 + g2.getFont().getSize() - fm.getMaxDescent());
    g2.drawString("F", x, y + 5 + g2.getFont().getSize() - fm.getMaxDescent());


    iMagnitude = iOrganics / 10;
    g2.setColor(colorOrganics1);
    g2.fillRect(x + 10, y + 15, iMagnitude * 3, 8);
    g2.setColor(colorOrganics2);
    g2.drawRect(x + 10, y + 15, iMagnitude * 3, 8);
    for (i = 0; i < iMagnitude; i++) {
      g2.drawLine(x + 10 + (i * 3), y + 15, x + 10 + (i * 3), y + 23);
    }
    g2.setColor(colorOrganics1);
    g2.drawString("" + iOrganics, x + 10 + (iMagnitude * 3), y + 15 + g2.getFont().getSize() - fm.getMaxDescent());
    g2.drawString("O", x, y + 15 + g2.getFont().getSize() - fm.getMaxDescent());

    iMagnitude = iEquipment / 10;
    g2.setColor(colorEquipment1);
    g2.fillRect(x + 10, y + 25, iMagnitude * 3, 8);
    g2.setColor(colorEquipment2);
    g2.drawRect(x + 10, y + 25, iMagnitude * 3, 8);
    for (i = 0; i < iMagnitude; i++) {
      g2.drawLine(x + 10 + (i * 3), y + 25, x + 10 + (i * 3), y + 33);
    }
    g2.setColor(colorEquipment1);
    g2.drawString("" + iEquipment, x + 10 + (iMagnitude * 3), y + 25 + g2.getFont().getSize() - fm.getMaxDescent());
    g2.drawString("E", x, y + 25 + g2.getFont().getSize() - fm.getMaxDescent());

    iMagnitude = iColonists / 10;
    g2.setColor(colorColonists1);
    g2.fillRect(x + 10, y + 35, iMagnitude * 3, 8);
    g2.setColor(colorColonists2);
    g2.drawRect(x + 10, y + 35, iMagnitude * 3, 8);
    for (i = 0; i < iMagnitude; i++) {
      g2.drawLine(x + 10 + (i * 3), y + 35, x + 10 + (i * 3), y + 43);
    }
    g2.setColor(colorColonists1);
    g2.drawString("" + iColonists, x + 10 + (iMagnitude * 3), y + 35 + g2.getFont().getSize() - fm.getMaxDescent());
    g2.drawString("C", x, y + 35 + g2.getFont().getSize() - fm.getMaxDescent());




    g2.setColor(Screen.FG_COLOR);
    g2.setFont(new Font(Screen.FONT.getFontName(), Font.PLAIN, 10));
    y2 = y + 50;

    if (iFighters > 0) {
      g2.drawString("Fighters: " + iFighters, x, y2 += g.getFont().getSize());
    }
    if (iArmidMines > 0) {
      g2.drawString("Armid Mines: " + iArmidMines, x, y2 += g.getFont().getSize());
    }
    if (iLimpetMines > 0) {
      g2.drawString("Limpet Mines: " + iLimpetMines, x, y2 += g.getFont().getSize());
    }
    if (iGTorps > 0) {
      g2.drawString("G Torpedoes: " + iGTorps, x, y2 += g.getFont().getSize());
    }
    if (iPhotons > 0) {
      g2.drawString("Photon Missiles: " + iPhotons, x, y2 += g.getFont().getSize());
    }
    if (iAtomicDetonators > 0) {
      g2.drawString("Atomic Detonators: " + iAtomicDetonators, x, y2 += g.getFont().getSize());
    }
    if (iEtherProbes > 0) {
      g2.drawString("Ether Probes: " + iEtherProbes, x, y2 += g.getFont().getSize());
    }
    if (iMineDisruptors > 0) {
      g2.drawString("Mine Disruptors: " + iMineDisruptors, x, y2 += g.getFont().getSize());
    }
    return imgTemp;
  }



}
