import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Random;
import java.awt.event.*;
import java.util.Vector;
import java.applet.AudioClip;

class Screen extends JComponent implements MouseListener, MouseMotionListener, KeyListener {

  /** CONSTANTS **/
  public static final int START = 1;
  public static final int TRAVELING = 2;
  public static final int IN_SECTOR = 3;
  public static final int ON_PLANET = 4;

  public static final int TOTAL_IMAGES = 8;



  public static final Dimension MODEL = new Dimension(640, 480);
  public static final Dimension COMPUTER = new Dimension(100, 100);
  public static final Color FG_COLOR = Color.green;
  public static final Color BKG_COLOR = Color.black;
  public static final Rectangle STATUS_LINE = new Rectangle(0, 0, 640, 20);
  public static final Font FONT = new Font("arial", Font.PLAIN, 12);

  public static final Color COLOR_1 = new Color(0, 255, 0);          //green
  public static final Color COLOR_16 = new Color(0, 255, 255);       //cyan
  public static final Color COLOR_256 = new Color(255, 255, 0);      //yellow
  public static final Color COLOR_4096 = new Color(255, 0, 0);       //red
  public static final Color COLOR_65536 = new Color(255, 255, 255);  //white


  /** INSTANCE VARIABLES **/
  private Dimension DrawingSize;
  private Image imgStars;

  private Image imgTitle;
  private Image[] imgConsole;


  public static Image[] imgPlanet;
  public static Image[] imgLand;

  public static Image imgVPort; //to be accessed by Sector object
  public static Image imgPort;  //to be accessed by Sector object
  public static Image imgIcon;

  public static Image imgTBBBSList;
  public static Image imgTBBack;
  public static Image imgTBColonize;
  public static Image imgTBEtherProbeScan;
  public static Image imgTBHoloScan;
  public static Image imgTBMap;
  public static Image imgTBPortPairTrade;
  public static Image imgTBRefresh;
  public static Image imgTBInterrogationMap;
  public static Image imgTBShowConsole;
  public static Image imgTBStopScript;


  public static Image[] imgShip;

  public static AudioClip soundStart;
  public static AudioClip soundPlanet;
  public static AudioClip soundPort;
  public static AudioClip soundPortPair;
  public static AudioClip soundTranswarp;
  public static AudioClip soundLevelup;
  public static AudioClip soundPlanetDestroy;
  public static AudioClip soundCommunicate;


  private int iState;
  private TWA theTWA;
  private String strMessage;
  private String strMessageFrom;

  private Image imgWithDamage;
  private Image imgBuffer;
  private Graphics graphicsBuffer;

  private ProgressBarFrame theProgressBar;

  /**
    * Screen
    **/
  public Screen(TWA theTWA) {
    this.theTWA = theTWA;
    setPreferredSize(new Dimension(640, 480));
    DrawingSize = new Dimension(640, 480);
    loadImages();
    iState = START;
    strMessage = "";
    strMessageFrom = "";

    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);
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

    imgLand = new Image[7];

    imgLand[Planet.CLASS_M] = getJarImage("images/landm.gif");
    imgLand[Planet.CLASS_K] = getJarImage("images/landk.gif");
    imgLand[Planet.CLASS_O] = getJarImage("images/lando.gif");
    imgLand[Planet.CLASS_L] = getJarImage("images/landl.gif");
    imgLand[Planet.CLASS_C] = getJarImage("images/landc.gif");
    imgLand[Planet.CLASS_H] = getJarImage("images/landh.gif");
    imgLand[Planet.CLASS_U] = getJarImage("images/landu.gif");

    imgLand[Planet.CLASS_U] = getJarImage("images/landu.gif");

    imgTBBBSList = getJarImage("images/toolbar/bbslist.gif");
    imgTBBack = getJarImage("images/toolbar/back.gif");
    imgTBColonize = getJarImage("images/toolbar/colonize.gif");
    imgTBEtherProbeScan = getJarImage("images/toolbar/etherprobescan.gif");
    imgTBHoloScan = getJarImage("images/toolbar/holoscan.gif");
    imgTBMap = getJarImage("images/toolbar/map.gif");
    imgTBPortPairTrade = getJarImage("images/toolbar/portpairtrade.gif");
    imgTBRefresh = getJarImage("images/toolbar/refresh.gif");
    imgTBInterrogationMap = getJarImage("images/toolbar/interrogationmap.gif");
    imgTBShowConsole = getJarImage("images/toolbar/showconsole.gif");
    imgTBStopScript = getJarImage("images/toolbar/stopscript.gif");



    imgIcon = getJarImage("images/icon.gif");
    imgVPort = getJarImage("images/vport.gif");
    imgPort = getJarImage("images/port.gif");

    imgStars = null; //will be created when update is called


    //progress bar
    theProgressBar = new ProgressBarFrame(0, 37);

    //sounds
    theProgressBar.increment("Loading start sound");
    soundStart = getJarSound("sounds/startup.wav");

    theProgressBar.increment("Loading planet sound");
    soundPlanet = getJarSound("sounds/planet.wav");

    theProgressBar.increment("Loading start sound");
    soundPort = getJarSound("sounds/port.wav");

    theProgressBar.increment("Loading port pair sound");
    soundPortPair = getJarSound("sounds/portpair.wav");

    theProgressBar.increment("Loading transwarp sound");
    soundTranswarp = getJarSound("sounds/transwarp.wav");

    theProgressBar.increment("Loading level up sound");
    soundLevelup = getJarSound("sounds/levelup.wav");

    theProgressBar.increment("Loading destroy sound");
    soundPlanetDestroy = getJarSound("sounds/planetdestroy.wav");

    theProgressBar.increment("Loading communicate sound");
    soundCommunicate = getJarSound("sounds/communicate.wav");


    //wait for images
    theMediaTracker.addImage(imgTitle, 1);
    theMediaTracker.addImage(imgConsole[0], 2);
    for (i = 0; i < 17; i++) {
      theMediaTracker.addImage(imgShip[i], 3 + i);
    }
    for (i = 0; i < 7; i++) {
      theMediaTracker.addImage(imgPlanet[i], 20 + i);
      theMediaTracker.addImage(imgLand[i], 27 + i);
    }

    theMediaTracker.addImage(imgIcon, 35);
    theMediaTracker.addImage(imgVPort, 36);
    theMediaTracker.addImage(imgPort, 37);


    try {
      theProgressBar.increment("Loading title screen");
      theMediaTracker.waitForID(1);

      theProgressBar.increment("Loading console");
      theMediaTracker.waitForID(2);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(3);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(4);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(5);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(6);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(7);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(8);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(9);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(10);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(11);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(12);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(13);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(14);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(15);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(16);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(17);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(18);

      theProgressBar.increment("Loading ship image");
      theMediaTracker.waitForID(19);

      theProgressBar.increment("Loading planet image");
      theMediaTracker.waitForID(20);

      theProgressBar.increment("Loading planet image");
      theMediaTracker.waitForID(21);

      theProgressBar.increment("Loading planet image");
      theMediaTracker.waitForID(22);

      theProgressBar.increment("Loading planet image");
      theMediaTracker.waitForID(23);

      theProgressBar.increment("Loading planet image");
      theMediaTracker.waitForID(24);

      theProgressBar.increment("Loading planet image");
      theMediaTracker.waitForID(25);

      theProgressBar.increment("Loading planet image");
      theMediaTracker.waitForID(26);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(27);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(28);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(29);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(30);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(31);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(32);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(33);

      theProgressBar.increment("Loading land image");
      theMediaTracker.waitForID(34);

      theProgressBar.increment("Loading icon");
      theMediaTracker.waitForID(35);

      theProgressBar.increment("Loading mini port image");
      theMediaTracker.waitForID(36);

      theProgressBar.increment("Loading port image");
      theMediaTracker.waitForID(37);

      theProgressBar.shutdown();




    } catch (InterruptedException e) { }
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
    * getJarSound
    **/
  public AudioClip getJarSound(String strName) {
    java.net.URL url;
    AudioClip soundToReturn;

    soundToReturn = null;

    url = this.getClass().getResource(strName);
    soundToReturn = java.applet.Applet.newAudioClip(url);

    return soundToReturn;
  }


  /**
    * makeStars
    **/
  private Image makeStars() {
    int i;
    int x, y;
    int iChance;
    Color colorStar = Color.white;

    Image imgToReturn = this.createImage(MODEL.width, MODEL.height);
    Graphics g = imgToReturn.getGraphics();
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

    return imgToReturn;
  }

  /**
    * drawTitle
    **/
  private void drawTitle(Graphics g) {
    g.drawImage(imgTitle, 0, 0, this);
//    g.drawImage(imgTitle, (640 - 300) / 2,
//                (480 - 186) / 2, this);

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

    x = 22;
    y = 367;


    g.drawImage(
       theTWA.getBBS().getGameData().getCurrentShip().drawShipView(this),
       x, y, this);


  }


  /**
    * drawShipComputerM
    **/
  private void drawShipComputerM(Graphics g) {
    int x, y;

    x = 171;
    y = 414;


    Graphics2D g2 = (Graphics2D) g;
    g2.setFont(new Font(FONT.getFontName(), Font.PLAIN, 10));

    int iCondition;
    Color colorOne, colorTwo;

    g2.setColor(FG_COLOR);
    g2.drawString("CREDITS: " + theTWA.getBBS().getGameData().getCredits(), x + 11, y + 15);

    if (theTWA.getParser().getParsers().size() > 0) {
      g2.setColor(FG_COLOR);
      g2.fillRect(x + 200, y + 5, 100, 12);
      g2.setColor(BKG_COLOR);
      g2.drawString("Script Running", x + 205, y + 15);


    }

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
    g2.fillRect(x + 11, y + 30, iCondition, 10);
    g2.drawString("TURNS: " + theTWA.getBBS().getGameData().getTurnsLeft() +
                  "/" + theTWA.getBBS().getGameData().getInitialTurns(), x + 11, y + 27);
    g2.setColor(colorOne);
    g2.drawRect(x + 11, y + 30, 100, 10);

  }

  /**
    * drawShipComputerR
    **/
  private void drawShipComputerR(Graphics g) {
    g.drawImage(theTWA.getBBS().getGameData().getCurrentShip().drawItemView(this), 522, 366, this);
  }

  /**
    * drawMessage
    **/
  private void drawMessage(Graphics g) {
    int x;
    int y;

    x = 5;
    y = 300;

    g.setFont(new Font(FONT.getFontName(), FONT.PLAIN, 10));

    if (strMessage != "") {
      g.setColor(Color.cyan);
      g.drawString("Message from " + strMessageFrom + " via Federation Link", x, y);
      g.drawString(strMessage, x, y + g.getFont().getSize());
    }

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
    * setMessage
    **/
  public void setMessage(String strMessageMode, String strMessageFrom, String strMessage) {
    this.strMessage = strMessage;
    this.strMessageFrom = strMessageFrom;

  }

  /**
    * update
    **/
  public void update(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    DrawingSize = new Dimension(getWidth(), getHeight());
    //System.out.println(DrawingSize);

    imgBuffer = this.createImage(MODEL.width, MODEL.height);
    if (imgStars == null) {
      imgStars = makeStars();
      Screen.soundStart.play();
    }
    graphicsBuffer = imgBuffer.getGraphics();


    if (iState == START) {
      //graphicsBuffer.drawImage(imgStars, 0, 0, MODEL.width, MODEL.height, this);
      drawTitle(graphicsBuffer);
    } else if ((iState == TRAVELING) && (theTWA.getBBS() != null)) {
      //graphicsBuffer.drawImage(imgStars, 0, 0, MODEL.width, MODEL.height, this);
      graphicsBuffer.drawImage(imgStars, 0, 0, this);
      drawConsole(graphicsBuffer);
      drawShipComputerL(graphicsBuffer);
      drawShipComputerM(graphicsBuffer);
      drawShipComputerR(graphicsBuffer);
      drawMessage(graphicsBuffer);

    } else if ((iState == IN_SECTOR) && (theTWA.getBBS() != null)) {
      //graphicsBuffer.drawImage(imgStars, 0, 0, MODEL.width, MODEL.height, this);
      graphicsBuffer.drawImage(imgStars, 0, 0, this);
      if (theTWA.getCurrentSector() != null) {
        theTWA.getCurrentSector().drawSector(graphicsBuffer, this);
      }
      drawConsole(graphicsBuffer);
      drawShipComputerL(graphicsBuffer);
      drawShipComputerM(graphicsBuffer);
      drawShipComputerR(graphicsBuffer);
      drawMessage(graphicsBuffer);

    } else if ((iState == ON_PLANET) && (theTWA.getBBS() != null)) {
      //graphicsBuffer.drawImage(imgStars, 0, 0, MODEL.width, MODEL.height, this);
      graphicsBuffer.drawImage(imgStars, 0, 0, this);
      if (theTWA.getCurrentPlanet() != null) {
        theTWA.getCurrentPlanet().drawLand(graphicsBuffer, this);
      }
      drawConsole(graphicsBuffer);
      drawShipComputerL(graphicsBuffer);
      drawShipComputerM(graphicsBuffer);
      drawShipComputerR(graphicsBuffer);
      drawMessage(graphicsBuffer);

    }

    if (theTWA.getBBS() != null) {
      graphicsBuffer.setColor(new Color(0, 128, 128));
      graphicsBuffer.fillRect(STATUS_LINE.x, STATUS_LINE.y, STATUS_LINE.width, STATUS_LINE.height);
      graphicsBuffer.setFont(new Font(FONT.getFontName(), Font.BOLD, FONT.getSize()));
      graphicsBuffer.setColor(Color.black);
      graphicsBuffer.drawString(theTWA.getBBS().getName(), 5, 18);

    }


    //g.drawImage(imgBuffer, 0, 0, DrawingSize.width, DrawingSize.height, this);
    g.drawImage(imgBuffer, 0, 0, this);

  }


  /**
    * MouseListener methods
    **/
  public void mouseClicked(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) {
    int i;
    Vector vectAdjacentSectors;
    Vector vectPlanets;
    Sector sectorCurrent;
    Planet planetCurrent;
    boolean willSetCursor;

    willSetCursor = false;

    if (theTWA.getCurrentSector() != null) {
      vectAdjacentSectors = theTWA.getCurrentSector().getAdjacentSectors();

      for (i = 0; i < vectAdjacentSectors.size(); i++) {
        sectorCurrent = (Sector) vectAdjacentSectors.elementAt(i);
          sectorCurrent.setHighlighted(false);
      }

      vectPlanets = theTWA.getCurrentSector().getPlanets();

      for (i = 0; i < vectPlanets.size(); i++) {
        planetCurrent = (Planet) vectPlanets.elementAt(i);
          planetCurrent.setHighlighted(false);
      }

      repaint();
      theTWA.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) {
    int i;
    Vector vectAdjacentSectors;
    Sector sectorCurrent;
    Vector vectPlanets;
    Planet planetCurrent;

   if (! (theTWA.getFrame().getCursor().getType() == Cursor.WAIT_CURSOR)) {

    if (theTWA.getCurrentSector() != null) {
      vectAdjacentSectors = theTWA.getCurrentSector().getAdjacentSectors();

      for (i = 0; i < vectAdjacentSectors.size(); i++) {
        sectorCurrent = (Sector) vectAdjacentSectors.elementAt(i);
        if (  sectorCurrent.viewIsClicked(e.getX(), e.getY()) ) {
          theTWA.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
          theTWA.getTelnet().getSender().sendData(sectorCurrent.getNumber() + "\r");
        }
      }

    }


    if (theTWA.getCurrentSector() != null) {
      vectPlanets = theTWA.getCurrentSector().getPlanets();

      for (i = 0; i < vectPlanets.size(); i++) {
        planetCurrent = (Planet) vectPlanets.elementAt(i);
        if ( planetCurrent.planetIsClicked(e.getX(), e.getY()) ) {
          theTWA.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
          theTWA.getParser().landOnPlanet(i);
          theTWA.getTelnet().getSender().sendData("l");
        }
      }
    }

   }
  }

  /**
    * MouseMotionListener methods
    **/
  public void mouseMoved(MouseEvent e) {
    int i;
    Vector vectAdjacentSectors;
    Vector vectPlanets;
    Sector sectorCurrent;
    Planet planetCurrent;
    boolean willSetCursor;

    willSetCursor = false;

   if (! (theTWA.getFrame().getCursor().getType() == Cursor.WAIT_CURSOR)) {


    if (theTWA.getCurrentSector() != null) {
      vectAdjacentSectors = theTWA.getCurrentSector().getAdjacentSectors();

      for (i = 0; i < vectAdjacentSectors.size(); i++) {
        sectorCurrent = (Sector) vectAdjacentSectors.elementAt(i);
        if (  sectorCurrent.viewIsClicked(e.getX(), e.getY()) ) {
          sectorCurrent.setHighlighted(true);
          willSetCursor = true;
        } else {
          sectorCurrent.setHighlighted(false);
        }
      }

      vectPlanets = theTWA.getCurrentSector().getPlanets();

      for (i = 0; i < vectPlanets.size(); i++) {
        planetCurrent = (Planet) vectPlanets.elementAt(i);
        if (  planetCurrent.planetIsClicked(e.getX(), e.getY()) ) {
          planetCurrent.setHighlighted(true);
          willSetCursor = true;
        } else {
          planetCurrent.setHighlighted(false);
        }
      }


    }

    if (willSetCursor) {
      theTWA.getFrame().setCursor(new Cursor(Cursor.HAND_CURSOR));
      repaint();
    } else {
      theTWA.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      if (theTWA.getFrame().getCursor().getType() != Cursor.DEFAULT_CURSOR) {
        repaint();
      }
    }

   }
  }
  public void mouseDragged(MouseEvent e) { }


  /**
    * keyTyped    - required by KeyListener
    * keyPressed
    * keyReleased
    **/
  public void keyReleased(KeyEvent e) {  }

  public void keyPressed(KeyEvent e) {

    if (Character.isDigit(e.getKeyChar())) {
      new GotoDialog(theTWA.getFrame(), theTWA);
    }

  }
  public void keyTyped(KeyEvent e) { }


}
