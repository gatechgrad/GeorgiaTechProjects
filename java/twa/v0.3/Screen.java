import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Random;
import java.awt.event.*;
import java.util.Vector;
import java.applet.AudioClip;

class Screen extends JComponent implements MouseListener, MouseMotionListener {

  /** CONSTANTS **/
  public static final int START = 1;
  public static final int TRAVELING = 2;
  public static final int IN_SECTOR = 3;


  public static final Dimension MODEL = new Dimension(640, 480);
  public static final Color FG_COLOR = Color.green;
  public static final Color BKG_COLOR = Color.black;
  public static final Rectangle STATUS_LINE = new Rectangle(0, 0, 640, 20);
  public static final Font FONT = new Font("serif", Font.PLAIN, 12);

  public static final Color COLOR_1 = new Color(255, 0, 0);
  public static final Color COLOR_16 = new Color(255, 255, 0);
  public static final Color COLOR_256 = new Color(0, 255, 255);
  public static final Color COLOR_4096 = new Color(0, 255, 0);
  public static final Color COLOR_65536 = new Color(255, 255, 255);


  /** INSTANCE VARIABLES **/
  private Dimension DrawingSize;
  private Image imgTitle;
  private Image[] imgConsole;


  public static Image[] imgPlanet;
  public static Image imgVPort; //to be accessed by Sector object
  public static Image imgPort;  //to be accessed by Sector object
  public static Image imgIcon;
  public static Image[] imgShip;

  public static AudioClip soundStart;
  public static AudioClip soundPlanet;
  public static AudioClip soundPort;
  public static AudioClip soundPortPair;
  public static AudioClip soundTranswarp;


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
    setPreferredSize(new Dimension(640, 480));
    DrawingSize = new Dimension(640, 480);
    loadImages();
    iState = START;
    addMouseListener(this);
    addMouseMotionListener(this);
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

    soundStart = getJarSound("sounds/startup.wav");
    soundPlanet = getJarSound("sounds/planet.wav");
    soundPort = getJarSound("sounds/port.wav");
    soundPortPair = getJarSound("sounds/portpair.wav");
    soundTranswarp = getJarSound("sounds/transwarp.wav");

    theMediaTracker.addImage(imgTitle, 0);
    theMediaTracker.addImage(imgConsole[0], 0);
    for (i = 0; i < 17; i++) {
      theMediaTracker.addImage(imgShip[i], 0);
    }
    for (i = 0; i < 7; i++) {
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

    x = 22;
    y = 367;
    theTWA.getBBS().getGameData().getCurrentShip().drawShipView(g, x, y, this);


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
    g2.fillRect(300, 440, iCondition, 10);
    g2.drawString("TURNS: " + theTWA.getBBS().getGameData().getTurnsLeft() +
                  "/" + theTWA.getBBS().getGameData().getInitialTurns(), 182, 450);
    g2.setColor(colorOne);
    g2.drawRect(300, 440, 100, 10);

  }

  /**
    * drawShipComputerR
    **/
  private void drawShipComputerR(Graphics g) {
    theTWA.getBBS().getGameData().getCurrentShip().drawItemView(g, 522, 366, this);
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
      graphicsBuffer.setFont(new Font(FONT.getFontName(), Font.BOLD, FONT.getSize()));
      graphicsBuffer.setColor(Color.black);
      graphicsBuffer.drawString(theTWA.getBBS().getName(), 5, 18);

    }


    g.drawImage(imgBuffer, 0, 0, DrawingSize.width, DrawingSize.height, this);

  }


  /**
    * MouseListener methods
    **/
  public void mouseClicked(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) {
    int i;
    Vector vectAdjacentSectors;
    Sector sectorCurrent;

    if (theTWA.getCurrentSector() != null) {
      vectAdjacentSectors = theTWA.getCurrentSector().getAdjacentSectors();

      for (i = 0; i < vectAdjacentSectors.size(); i++) {
        sectorCurrent = (Sector) vectAdjacentSectors.elementAt(i);
        if (  sectorCurrent.viewIsClicked(e.getX(), e.getY()) ) {
          theTWA.getTelnet().getSender().sendData(sectorCurrent.getNumber() + "\r");
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
    Sector sectorCurrent;
    boolean willSetCursor;

    willSetCursor = false;

    if (theTWA.getCurrentSector() != null) {
      vectAdjacentSectors = theTWA.getCurrentSector().getAdjacentSectors();

      for (i = 0; i < vectAdjacentSectors.size(); i++) {
        sectorCurrent = (Sector) vectAdjacentSectors.elementAt(i);
        if (  sectorCurrent.viewIsClicked(e.getX(), e.getY()) ) {
          willSetCursor = true;
        }
      }

      if (willSetCursor) {
        theTWA.getFrame().setCursor(new Cursor(Cursor.HAND_CURSOR));
      } else {
        theTWA.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }


    }
  }
  public void mouseDragged(MouseEvent e) { }

}
