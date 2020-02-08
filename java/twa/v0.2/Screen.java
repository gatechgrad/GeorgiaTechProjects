import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Random;

class Screen extends JComponent {

  /** CONSTANTS **/
  public static final int START = 1;
  public static final int TRAVELING = 2;
  public static final int IN_SECTOR = 3;


  public static final Dimension MODEL = new Dimension(640, 480);
  public static final Color FG_COLOR = Color.green;
  public static final Rectangle STATUS_LINE = new Rectangle(0, 0, 640, 20);


  /** INSTANCE VARIABLES **/
  private Dimension DrawingSize;
  private Image imgTitle;
  private Image[] imgConsole;
  private Image[] imgShip;


  public static Image[] imgPlanet;
  public static Image imgVPort; //to be accessed by Sector object
  public static Image imgPort;  //to be accessed by Sector object
  public static Image imgIcon;

  private int iState;
  private TWA theTWA;

  private Image imgWithDamage;
  private Image imgBuffer;
  private Graphics graphicsBuffer;

  /**
    * Screen
    **/
  public Screen(TWA theTWA) {
    this.theTWA = theTWA;
    DrawingSize = new Dimension(640, 480);
    loadImages();
    iState = START;

  }

  /**
    * loadImages
    **/
  private void loadImages() {
    int i;

    MediaTracker theMediaTracker = new MediaTracker(this);

    imgTitle = getJarImage("images/title.gif");

    //console images
    imgConsole = new Image[1];
    imgConsole[0] = getJarImage("images/console0.gif");

    imgShip = new Image[17];

    imgShip[1] = getJarImage("images/shipb.gif");
    imgShip[2] = getJarImage("images/shipc.gif");
    imgShip[3] = getJarImage("images/shipd.gif");
    imgShip[4] = getJarImage("images/shipe.gif");
    imgShip[5] = getJarImage("images/shipf.gif");
    imgShip[6] = getJarImage("images/shipg.gif");
    imgShip[7] = getJarImage("images/shiph.gif");
    imgShip[8] = getJarImage("images/shipi.gif");
    imgShip[9] = getJarImage("images/shipj.gif");
    imgShip[10] = getJarImage("images/shipk.gif");
    imgShip[11] = getJarImage("images/shipl.gif");
    imgShip[12] = getJarImage("images/shipm.gif");
    imgShip[13] = getJarImage("images/shipn.gif");
    imgShip[14] = getJarImage("images/shipo.gif");
    imgShip[15] = getJarImage("images/shipp.gif");
    imgShip[16] = getJarImage("images/shipr.gif");

    imgPlanet = new Image[7];

    imgPlanet[Planet.CLASS_M] = getJarImage("images/planetm.gif");
    imgPlanet[Planet.CLASS_K] = getJarImage("images/planetk.gif");
    imgPlanet[Planet.CLASS_O] = getJarImage("images/planeto.gif");
    imgPlanet[Planet.CLASS_L] = getJarImage("images/planetl.gif");
    imgPlanet[Planet.CLASS_C] = getJarImage("images/planetc.gif");
    imgPlanet[Planet.CLASS_H] = getJarImage("images/planeth.gif");
    imgPlanet[Planet.CLASS_U] = getJarImage("images/planetu.gif");


    imgIcon = getJarImage("images/icon.gif");
    imgVPort = getJarImage("images/vport.gif");
    imgPort = getJarImage("images/port.gif");

    theMediaTracker.addImage(imgTitle, 0);
    theMediaTracker.addImage(imgConsole[0], 0);
    for (i = 0; i <= 17; i++) {
      theMediaTracker.addImage(imgShip[i], 0);
    }
    for (i = 0; i <= 7; i++) {
      theMediaTracker.addImage(imgPlanet[i], 0);
    }

    theMediaTracker.addImage(imgIcon, 0);
    theMediaTracker.addImage(imgVPort, 0);
    theMediaTracker.addImage(imgPort, 0);


    try { theMediaTracker.waitForAll(); } catch (InterruptedException e) { }
  }

  /**
    * getJarImage - gets an Image from the Jar file if the program is in
    *               a JAR file or gets the Image from the current directory
    *               if the class files are extracted into a directory
    **/
  public Image getJarImage(String strName) {
    java.net.URL url;
    Image imgToReturn;

    imgToReturn = null;

    url = this.getClass().getResource(strName);
    imgToReturn = (new ImageIcon(url)).getImage();

    return imgToReturn;
  }

  /**
    * drawStars
    **/
  private void drawStars(Graphics g) {
    int i;
    int x, y;
    int iChance;
    Color colorStar = Color.white;
    Graphics2D g2 = (Graphics2D) g;


    //I want 100 stars on the screen
    //I want a 1/10 chance of one being red
    //I want a 1/5 chance of one being blue
    //I want a 3/10 chance of one being cyan
    //I want a 1/2 chance of one being white
    Random aRandom = new Random();

    g.setColor(Color.black);
    g.fillRect(0, 0, MODEL.width, MODEL.height);

    for (i = 0; i < 100; i++) {
      x = (Math.abs(aRandom.nextInt()) % MODEL.width);
      y = (Math.abs(aRandom.nextInt()) % MODEL.height);

      iChance = (Math.abs(aRandom.nextInt()) % 100);
      if ((iChance >= 50) && (iChance < 100)) {
        colorStar = Color.white;
      } else if ((iChance >= 20) && (iChance < 50)) {
        colorStar = Color.cyan;
      } else if ((iChance >= 10) && (iChance < 20)) {
        colorStar = Color.blue;
      } else if ((iChance >= 0) && (iChance < 10)) {
        colorStar = Color.red;
      }

      g2.setColor(colorStar);

      g2.drawLine(x, y, x, y);

    }

  }

  /**
    * drawTitle
    **/
  private void drawTitle(Graphics g) {
    g.drawImage(imgTitle, (640 - 300) / 2,
                (480 - 186) / 2, this);

  }

  /**
    * drawConsole
    **/
  private void drawConsole(Graphics g) {

    g.drawImage(imgConsole[0], 0, MODEL.height - 128,
                640, 128, this);

  }

  /**
    * drawShipComputerL
    **/
  private void drawShipComputerL(Graphics g) {
    int x, y;
    int iWidth, iHeight, i;
    String strShip;

    x = 22;
    y = 367;

/*
    System.out.println("Drawing the computer");
    iWidth = imgShip[8].getWidth(this);
    iHeight = imgShip[8].getHeight(this);
    int[] iPixels = new int[iWidth * iHeight];

    try {
      PixelGrabber pg = new PixelGrabber(imgShip[8], 0, 0, iWidth, iHeight, iPixels, 0, iWidth);
      pg.grabPixels();

      for(i = 0; i < iWidth * iHeight / 2; i++) {
        if (iPixels[i] == Color.green.getRGB()) {
          iPixels[i] = Color.red.getRGB();      // change green pixels to red ones
        } else {
          iPixels[i] = (new Color(128, 0, 0)).getRGB();      // change green pixels to red ones
        }
      }
    } catch (InterruptedException e) { }


    imgWithDamage = createImage(new MemoryImageSource(iWidth, iHeight, iPixels, 0, iWidth));

    g.drawImage(imgWithDamage, 22, 367, 100, 100, this);
*/

    strShip = theTWA.getBBS().getGameData().getShipType();

    if (strShip.equals("Merchant Crusier")) {
      g.drawImage(imgShip[1], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Scout Marauder")) {
      g.drawImage(imgShip[2], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Missile Frigate")) {
      g.drawImage(imgShip[3], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("BattleShip")) {
      g.drawImage(imgShip[4], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Corporate FlagShip")) {
      g.drawImage(imgShip[5], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Colonial Transport")) {
      g.drawImage(imgShip[6], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("CargoTran")) {
      g.drawImage(imgShip[7], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Merchant Freighter")) {
      g.drawImage(imgShip[8], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Imperial StarShip")) {
      g.drawImage(imgShip[9], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Havoc GunStar")) {
      g.drawImage(imgShip[10], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("StarMaster")) {
      g.drawImage(imgShip[11], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Constellation")) {
      g.drawImage(imgShip[12], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("T'Khasi Orion")) {
      g.drawImage(imgShip[13], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Tholian Sentinel")) {
      g.drawImage(imgShip[14], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Taurean Mule")) {
      g.drawImage(imgShip[15], x + 25, y + 15, 50, 50, this);
    } else if (strShip.equals("Interdictor Cruiser")) {
      g.drawImage(imgShip[16], x + 25, y + 15, 50, 50, this);
    } else {
      g.drawString("No ship Image available", x, y);
    }




  }

  /**
    * drawShipComputerM
    **/
  private void drawShipComputerM(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    int iCondition;
    Color colorOne, colorTwo;

    g2.setColor(FG_COLOR);
    g2.drawString("CREDITS: " + theTWA.getBBS().getGameData().getCredits(), 182, 430);

    g2.drawString("TURNS: " + theTWA.getBBS().getGameData().getTurnsLeft() +
                  "/" + theTWA.getBBS().getGameData().getInitialTurns(), 182, 450);

    iCondition = 100 * theTWA.getBBS().getGameData().getTurnsLeft() / theTWA.getBBS().getGameData().getInitialTurns();
    if (iCondition > 50) {
      colorOne = new Color(0, 128, 0);
      colorTwo = Color.green;
    } else if (iCondition > 25) {
      colorOne = new Color(128, 128, 0);
      colorTwo = Color.yellow;
    } else {
      colorOne = new Color(128, 0, 0);
      colorTwo = Color.red;
    }

    g2.setColor(colorTwo);
    g2.fillRect(300, 430, iCondition, 20);
    g2.setColor(colorOne);
    g2.drawRect(300, 430, 100, 20);

  }

  /**
    * drawShipComputerR
    **/
  private void drawShipComputerR(Graphics g) {
    Rectangle rectShipComputerR = new Rectangle(520, 366, 100, 100);
    int iMagnitude;
    Graphics2D g2 = (Graphics2D) g;
    int i;

    Color colorFuelOre1 = new Color(255, 0, 0);
    Color colorFuelOre2 = new Color(128, 0, 0);
    Color colorOrganics1 = new Color(0, 255, 0);
    Color colorOrganics2 = new Color(0, 128, 0);
    Color colorEquipment1 = new Color(0, 0, 255);
    Color colorEquipment2 = new Color(0, 0, 128);


    iMagnitude = theTWA.getBBS().getGameData().getFuelOre() / 10;
    g2.setColor(colorFuelOre1);
    g2.fillRect(rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (iMagnitude * 3), 10, iMagnitude * 3);
    g2.setColor(colorFuelOre2);
    g2.drawRect(rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (iMagnitude * 3), 10, iMagnitude * 3);
    for (i = 0; i < iMagnitude; i++) {
      g2.drawLine(rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (i * 3), rectShipComputerR.x + 20, rectShipComputerR.y + 85 - (i * 3));
    }
    g2.drawString("" + theTWA.getBBS().getGameData().getFuelOre(), rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (iMagnitude * 3));


    iMagnitude = theTWA.getBBS().getGameData().getOrganics() / 10;
    g2.setColor(colorOrganics1);
    g2.fillRect(rectShipComputerR.x + 20, rectShipComputerR.y + 85 - (iMagnitude * 3), 10, iMagnitude * 3);
    g2.setColor(colorOrganics2);
    g2.drawRect(rectShipComputerR.x + 20, rectShipComputerR.y + 85 - (iMagnitude * 3), 10, iMagnitude * 3);
    for (i = 0; i < iMagnitude; i++) {
      g2.drawLine(rectShipComputerR.x + 20, rectShipComputerR.y + 85 - (i * 3), rectShipComputerR.x + 30, rectShipComputerR.y + 85 - (i * 3));
    }
    g2.drawString("" + theTWA.getBBS().getGameData().getOrganics(), rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (iMagnitude * 3));


    iMagnitude = theTWA.getBBS().getGameData().getEquipment() / 10;
    g2.setColor(colorEquipment1);
    g2.fillRect(rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (iMagnitude * 3), 10, iMagnitude * 3);
    g2.setColor(colorEquipment2);
    g2.drawRect(rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (iMagnitude * 3), 10, iMagnitude * 3);
    for (i = 0; i < iMagnitude; i++) {
      g2.drawLine(rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (i * 3), rectShipComputerR.x + 40, rectShipComputerR.y + 85 - (i * 3));
    }
    g2.drawString("" + theTWA.getBBS().getGameData().getEquipment(), rectShipComputerR.x + 10, rectShipComputerR.y + 85 - (iMagnitude * 3));





    g2.setColor(FG_COLOR);
    g2.drawString("F", rectShipComputerR.x + 10, rectShipComputerR.y + 95);
    g2.drawString("O", rectShipComputerR.x + 20, rectShipComputerR.y + 95);
    g2.drawString("E", rectShipComputerR.x + 30, rectShipComputerR.y + 95);


  }

  
  /**
    * repaint
    **/
  public void repaint(Graphics g) {
    update(g);
  }

  /**
    * paint
    **/
  public void paint(Graphics g) {
    update (g);
  }

  /**
    * setState
    **/
  public void setState(int iState) {
    this.iState = iState;
    repaint();
  }

  /**
    * update
    **/
  public void update(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    DrawingSize = new Dimension(getWidth(), getHeight());
    //System.out.println(DrawingSize);

    imgBuffer = this.createImage(MODEL.width, MODEL.height);
    graphicsBuffer = imgBuffer.getGraphics();


    if (iState == START) {
      drawStars(graphicsBuffer);
      drawTitle(graphicsBuffer);
    } else if ((iState == TRAVELING) && (theTWA.getBBS() != null)) {
      drawStars(graphicsBuffer);
      drawConsole(graphicsBuffer);
      drawShipComputerL(graphicsBuffer);
      drawShipComputerM(graphicsBuffer);
      drawShipComputerR(graphicsBuffer);
    } else if ((iState == IN_SECTOR) && (theTWA.getBBS() != null)) {
      drawStars(graphicsBuffer);
      drawConsole(graphicsBuffer);
      drawShipComputerL(graphicsBuffer);
      drawShipComputerM(graphicsBuffer);
      drawShipComputerR(graphicsBuffer);
      if (theTWA.getCurrentSector() != null) {
        theTWA.getCurrentSector().drawSector(graphicsBuffer, this);
      }
    }

    if (theTWA.getBBS() != null) {
      graphicsBuffer.setColor(new Color(0, 128, 128));
      graphicsBuffer.fillRect(STATUS_LINE.x, STATUS_LINE.y, STATUS_LINE.width, STATUS_LINE.height);
      graphicsBuffer.setColor(Color.black);
      graphicsBuffer.drawString(theTWA.getBBS().getName(), 5, 18);

    }


    g.drawImage(imgBuffer, 0, 0, DrawingSize.width, DrawingSize.height, this);

  }


}
