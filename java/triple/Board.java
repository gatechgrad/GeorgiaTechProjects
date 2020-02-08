import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

class Board extends JComponent implements MouseMotionListener, MouseListener {

  /********************** CONSTANTS **********************/
  public static final int NUM_SPACES = 3;
  public static final int NUM_PLAYERS = 2;

  public static final String WINDOW_TITLE = "Triple Triad";
  public static final Rectangle SCREEN_BOUNDS =
    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
  public static final Rectangle INIT_WINDOW_BOUNDS = new Rectangle(50, 50, 400, 300);
  public static final String CURSOR_NAME = "FF Hand";
  public static final Font BOARD_FONT = new Font("Dialog", Font.BOLD, 36);

  public static final int DEBUG = 4;
  public static final int DEBUG_ALL = 1;
  public static final boolean IS_DOUBLE_BUFFERED = true;

  public static final int NUM_SELECTABLE_CARDS = 5;

  public static final Color RECT_COLOR = new Color(104, 104, 104);
  public static final Color RECT_FG_COLOR = new Color(128, 128, 128);
  public static final Color RECT_BKG_COLOR = new Color(52, 52, 52);
  public static final Color FONT_FG_COLOR = new Color(255, 255, 255);
  public static final Color FONT_BKG_COLOR = new Color(32, 32, 32);
  public static final Color FONT_DISABLED_COLOR = new Color(192, 192, 192);

/*
  public static final Sound CARD_SOUND = new Sound("shuffle.au");
  public static final Sound FLIP_CARD_SOUND = new Sound("flip.au");
  public static final Sound BACKGROUND_SOUND = new Sound(("shuffle.mid"));
  public static final Sound VICTORY_SOUND = new Sound(("victory.mid"));
*/

  /********************** CLASS VARIABLES **********************/
  private Vector theCards;
  private Card[][] theSpaces;
  private Image ImgBoard;
  private Rectangle rectWindowBounds;
  private Dimension dimDrawingBounds;
  private Insets insetWindow;
  private Insets insetImgBoard;
  private boolean isNewPlayer;
  private Player playerOne, playerTwo;
  private boolean gameIsRunning;
  private Rectangle rectPlayingBoard;
  public Cursor cursorFFHand;
  private Card cardDragged;
  private Point pntCardOffset;
  private int iDragCardPosition;
  private Point pntDragPosition;
  private Player currentPlayer;
  private Dimension dimCard;
  private Rules theRules;
  private boolean displayWinner;
  private boolean isChoosingCards;
  private boolean isDisplayingRules;
  private Random aRandom;
  private long longWait;
  private Font theFont;

  private boolean damage = true;
  private Image  bufferImage = null;
  private Graphics bufferGraphics = null;



  public Board() {
    System.out.println("initializing Board");
    addMouseMotionListener(this);
    addMouseListener(this);

    aRandom = new Random();

    theCards = new Vector();
    theSpaces = new Card[NUM_SPACES][NUM_SPACES];
    rectWindowBounds =  new Rectangle(640, 480);

    //load the images
    MediaTracker tempMediaTracker = new MediaTracker(this);
    ImgBoard = Toolkit.getDefaultToolkit().createImage(TripleTriad.THE_RESOURCES.getResource("board.gif"));
    tempMediaTracker.addImage(ImgBoard, 0);
    try { tempMediaTracker.waitForAll(); } catch (InterruptedException e) { }

    //Level 1 Cards
    theCards.addElement( new Card(1, 5, 1, 4, 1, Card.ELEM_NONE, "Geezard", this) );
    theCards.addElement( new Card(5, 3, 1, 1, 1, Card.ELEM_NONE, "Funguar", this) );
    theCards.addElement( new Card(1, 5, 3, 3, 1, Card.ELEM_NONE, "Bite Bug", this) );
    theCards.addElement( new Card(6, 2, 1, 1, 1, Card.ELEM_NONE, "Red Bat", this) );
    theCards.addElement( new Card(2, 5, 1, 3, 1, Card.ELEM_NONE, "Blobra", this) );
    theCards.addElement( new Card(2, 4, 4, 1, 1, Card.ELEM_THUNDER, "Gayla", this) );
    theCards.addElement( new Card(1, 1, 4, 5, 1, Card.ELEM_NONE, "Gesper", this) );
    theCards.addElement( new Card(3, 1, 2, 5, 1, Card.ELEM_EARTH, "Fastitocalon-F", this) );
    theCards.addElement( new Card(2, 1, 6, 1, 1, Card.ELEM_NONE, "Blood Soul", this) );
    theCards.addElement( new Card(4, 2, 4, 2, 1, Card.ELEM_NONE, "Caterchipillar", this) );
    theCards.addElement( new Card(2, 6, 1, 1, 1, Card.ELEM_THUNDER, "Cockatrice", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 1 Cards Loaded"); }

    //Level 2 Cards
    theCards.addElement( new Card(7, 1, 3, 1, 2, Card.ELEM_NONE, "Grat", this) );
    theCards.addElement( new Card(6, 3, 2, 2, 2, Card.ELEM_NONE, "Buel", this) );
    theCards.addElement( new Card(5, 4, 3, 3, 2, Card.ELEM_NONE, "Mesmerize", this) );
    theCards.addElement( new Card(6, 3, 4, 1, 2, Card.ELEM_ICE, "Glacial Eye", this) );
    theCards.addElement( new Card(3, 3, 5, 4, 2, Card.ELEM_NONE, "Belhelmel", this) );
    theCards.addElement( new Card(5, 5, 2, 3, 2, Card.ELEM_AERO, "Thrustaevis", this) );
    theCards.addElement( new Card(5, 5, 3, 1, 2, Card.ELEM_POISON, "Anacondaur", this) );
    theCards.addElement( new Card(5, 2, 5, 2, 2, Card.ELEM_THUNDER, "Creeps", this) );
    theCards.addElement( new Card(6, 2, 5, 6, 2, Card.ELEM_THUNDER, "Grendel", this) );
    theCards.addElement( new Card(3, 7, 1, 2, 2, Card.ELEM_NONE, "Jell Eye", this) );
    theCards.addElement( new Card(5, 3, 5, 2, 2, Card.ELEM_NONE, "Grand Mantis", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 2 Cards Loaded"); }

    //Level 3 Cards
    theCards.addElement( new Card(6, 2, 3, 6, 3, Card.ELEM_NONE, "Forbidden", this) );
    theCards.addElement( new Card(6, 6, 1, 3, 3, Card.ELEM_EARTH, "Armadodo", this) );
    theCards.addElement( new Card(2, 5, 5, 5, 3, Card.ELEM_POISON, "Tri-Face", this) );
    theCards.addElement( new Card(7, 3, 1, 5, 3, Card.ELEM_EARTH, "Fastitocalon", this) );
    theCards.addElement( new Card(7, 3, 5, 1, 3, Card.ELEM_ICE, "Snow Lion", this) );
    theCards.addElement( new Card(5, 3, 3, 6, 3, Card.ELEM_NONE, "Ochu", this) );
    theCards.addElement( new Card(5, 4, 2, 6, 3, Card.ELEM_FIRE, "Sam08g", this) );
    theCards.addElement( new Card(4, 2, 7, 4, 3, Card.ELEM_FIRE, "Death Claw", this) );
    theCards.addElement( new Card(6, 3, 6, 2, 3, Card.ELEM_NONE, "Cactuar", this) );
    theCards.addElement( new Card(3, 4, 4, 6, 3, Card.ELEM_NONE, "Tonberry", this) );
    theCards.addElement( new Card(7, 5, 3, 2, 3, Card.ELEM_EARTH, "Abyss Worm", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 3 Cards Loaded"); }

    //Level 4 Cards
    theCards.addElement( new Card(2, 7, 6, 3, 4, Card.ELEM_NONE, "Turtapod", this) );
    theCards.addElement( new Card(6, 5, 4, 5, 4, Card.ELEM_NONE, "Vysage", this) );
    theCards.addElement( new Card(4, 7, 2, 6, 4, Card.ELEM_NONE, "T-rexaur", this) );
    theCards.addElement( new Card(2, 3, 6, 7, 4, Card.ELEM_FIRE, "Bomb", this) );
    theCards.addElement( new Card(1, 7, 4, 6, 4, Card.ELEM_THUNDER, "Blitz", this) );
    theCards.addElement( new Card(7, 6, 1, 3, 4, Card.ELEM_NONE, "Wendigo", this) );
    theCards.addElement( new Card(7, 4, 4, 4, 4, Card.ELEM_NONE, "Torama", this) );
    theCards.addElement( new Card(3, 6, 3, 7, 4, Card.ELEM_NONE, "Imp", this) );
    theCards.addElement( new Card(6, 3, 7, 2, 4, Card.ELEM_POISON, "Blue Dragon", this) );
    theCards.addElement( new Card(4, 6, 5, 5, 4, Card.ELEM_EARTH, "Adamantoise", this) );
    theCards.addElement( new Card(7, 3, 4, 5, 4, Card.ELEM_FIRE, "Hexadragon", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 4 Cards Loaded"); }

    //Level 5 Cards
    theCards.addElement( new Card(6, 5, 6, 5, 5, Card.ELEM_NONE, "Iron Giant", this) );
    theCards.addElement( new Card(2, 7, 5, 6, 5, Card.ELEM_NONE, "Behemoth", this) );
    theCards.addElement( new Card(7, 3, 5, 6, 5, Card.ELEM_WATER, "Chimera", this) );
    theCards.addElement( new Card(3, 1, 2, 10, 5, Card.ELEM_NONE, "Pupu", this) );
    theCards.addElement( new Card(6, 7, 6, 2, 5, Card.ELEM_NONE, "Elastoid", this) );
    theCards.addElement( new Card(5, 4, 7, 5, 5, Card.ELEM_NONE, "Gim47n", this) );
    theCards.addElement( new Card(7, 2, 4, 7, 5, Card.ELEM_POISON, "Malboro", this) );
    theCards.addElement( new Card(7, 4, 7, 2, 5, Card.ELEM_FIRE, "Ruby Dragon", this) );
    theCards.addElement( new Card(5, 6, 7, 3, 5, Card.ELEM_NONE, "Elnoyle", this) );
    theCards.addElement( new Card(4, 4, 7, 6, 5, Card.ELEM_NONE, "Tonberry King", this) );
    theCards.addElement( new Card(6, 7, 2, 6, 5, Card.ELEM_NONE, "Wedge, Biggs", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 5 Cards Loaded"); }

    //Level 6 Cards
    theCards.addElement( new Card(2, 4, 8, 8, 6, Card.ELEM_NONE, "Fujin, Raijin", this) );
    theCards.addElement( new Card(7, 4, 8, 3, 6, Card.ELEM_AERO, "Elvoret", this) );
    theCards.addElement( new Card(6, 3, 8, 7, 6, Card.ELEM_NONE, "X-Atm092", this) );
    theCards.addElement( new Card(7, 5, 8, 2, 6, Card.ELEM_POISON, "Granaldo", this) );
    theCards.addElement( new Card(1, 3, 8, 8, 6, Card.ELEM_NONE, "Gerogero", this) );
    theCards.addElement( new Card(8, 2, 8, 2, 6, Card.ELEM_NONE, "Iguion", this) );
    theCards.addElement( new Card(6, 5, 4, 8, 6, Card.ELEM_NONE, "Abadon", this) );
    theCards.addElement( new Card(4, 6, 5, 8, 6, Card.ELEM_NONE, "Trauma", this) );
    theCards.addElement( new Card(1, 8, 4, 8, 6, Card.ELEM_NONE, "Oilboyle", this) );
    theCards.addElement( new Card(6, 4, 8, 5, 6, Card.ELEM_NONE, "Shumi Tribe", this) );
    theCards.addElement( new Card(7, 1, 8, 5, 6, Card.ELEM_NONE, "Krysta", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 6 Cards Loaded"); }

    //Level 7 Cards
    theCards.addElement( new Card(8, 8, 4, 4, 7, Card.ELEM_NONE, "Propagator", this) );
    theCards.addElement( new Card(8, 4, 4, 8, 7, Card.ELEM_NONE, "Jumbo Cactuar", this) );
    theCards.addElement( new Card(8, 8, 5, 2, 7, Card.ELEM_THUNDER, "Tri-Point", this) );
    theCards.addElement( new Card(5, 8, 6, 6, 7, Card.ELEM_NONE, "Gargantua", this) );
    theCards.addElement( new Card(8, 3, 7, 6, 7, Card.ELEM_NONE, "MobileType8", this) );
    theCards.addElement( new Card(8, 8, 5, 3, 7, Card.ELEM_NONE, "Sphixara", this) );
    theCards.addElement( new Card(8, 4, 5, 8, 7, Card.ELEM_NONE, "Tiamat", this) );
    theCards.addElement( new Card(5, 5, 8, 7, 7, Card.ELEM_NONE, "Bgh251f2", this) );
    theCards.addElement( new Card(6, 7, 4, 8, 7, Card.ELEM_NONE, "RedGiant", this) );
    theCards.addElement( new Card(1, 7, 7, 8, 7, Card.ELEM_NONE, "Catoblepas", this) );
    theCards.addElement( new Card(7, 8, 2, 7, 7, Card.ELEM_NONE, "Ultima Weapon", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 7 Cards Loaded"); }

    //Level 8 Cards
    theCards.addElement( new Card(4, 9, 8, 4, 8, Card.ELEM_NONE, "Chubby Chocobo", this) );
    theCards.addElement( new Card(9, 3, 7, 6, 8, Card.ELEM_NONE, "Angelo", this) );
    theCards.addElement( new Card(3, 6, 9, 7, 8, Card.ELEM_NONE, "Gilgamesh", this) );
    theCards.addElement( new Card(9, 2, 9, 3, 8, Card.ELEM_NONE, "MiniMog", this) );
    theCards.addElement( new Card(9, 4, 8, 4, 8, Card.ELEM_NONE, "Chicobo", this) );
    theCards.addElement( new Card(2, 4, 9, 9, 8, Card.ELEM_THUNDER, "Quezacotl", this) );
    theCards.addElement( new Card(6, 9, 4, 7, 8, Card.ELEM_ICE, "Shiva", this) );
    theCards.addElement( new Card(9, 8, 2, 6, 8, Card.ELEM_FIRE, "Ifrit", this) );
    theCards.addElement( new Card(8, 2, 6, 9, 8, Card.ELEM_NONE, "Siren", this) );
    theCards.addElement( new Card(5, 9, 9, 1, 8, Card.ELEM_EARTH, "Sacred", this) );
    theCards.addElement( new Card(9, 9, 2, 5, 8, Card.ELEM_EARTH, "Minotaur", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 8 Cards Loaded"); }

    //Level 9 Cards
    theCards.addElement( new Card(8, 4, 10, 4, 9, Card.ELEM_NONE, "Carbuncle", this) );
    theCards.addElement( new Card(5, 3, 8, 10, 9, Card.ELEM_NONE, "Diablos", this) );
    theCards.addElement( new Card(7, 7, 1, 10, 9, Card.ELEM_WATER, "Leviathan", this) );
    theCards.addElement( new Card(8, 5, 3, 10, 9, Card.ELEM_NONE, "Odin", this) );
    theCards.addElement( new Card(10, 7, 7, 1, 9, Card.ELEM_AERO, "Pandemona", this) );
    theCards.addElement( new Card(7, 10, 6, 4, 9, Card.ELEM_NONE, "Cerberus", this) );
    theCards.addElement( new Card(9, 2, 4, 10, 9, Card.ELEM_HOLY, "Alexander", this) );
    theCards.addElement( new Card(7, 10, 7, 2, 9, Card.ELEM_FIRE, "Phoenix", this) );
    theCards.addElement( new Card(10, 6, 2, 8, 9, Card.ELEM_NONE, "Bahamut", this) );
    theCards.addElement( new Card(3, 10, 10, 1, 9, Card.ELEM_POISON, "Doomtrain", this) );
    theCards.addElement( new Card(4, 10, 9, 4, 9, Card.ELEM_NONE, "Eden", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 9 Cards Loaded"); }

    //Level 10 Cards
    theCards.addElement( new Card(10, 8, 2, 7, 10, Card.ELEM_NONE, "Ward", this) );
    theCards.addElement( new Card(6, 10, 6, 7, 10, Card.ELEM_NONE, "Kiros", this) );
    theCards.addElement( new Card(5, 9, 3, 10, 10, Card.ELEM_NONE, "Laguna", this) );
    theCards.addElement( new Card(10, 4, 6, 8, 10, Card.ELEM_NONE, "Selphie", this) );
    theCards.addElement( new Card(9, 2, 10, 6, 10, Card.ELEM_NONE, "Quistis", this) );
    theCards.addElement( new Card(2, 10, 9, 6, 10, Card.ELEM_NONE, "Irvine", this) );
    theCards.addElement( new Card(8, 6, 10, 5, 10, Card.ELEM_NONE, "Zell", this) );
    theCards.addElement( new Card(4, 10, 2, 10, 10, Card.ELEM_NONE, "Rinoa", this) );
    theCards.addElement( new Card(10, 3, 3, 10, 10, Card.ELEM_NONE, "Edea", this) );
    theCards.addElement( new Card(6, 4, 10, 9, 10, Card.ELEM_NONE, "Seifer", this) );
    theCards.addElement( new Card(10, 9, 6, 4, 10, Card.ELEM_NONE, "Squall", this) );
    if ((DEBUG == 2) || (DEBUG == DEBUG_ALL)) {System.out.println("Level 10 Cards Loaded"); }

  }

  /**
    * startGame - sets game variables to initial values
    **/
  public void startGame(Rules newRules, Player opponent) {
    isNewPlayer = true;
    isChoosingCards = false;
    isDisplayingRules = false;
    gameIsRunning = false;


    longWait = 0;

    rectPlayingBoard = new Rectangle(0, 0, 0, 0);
    pntDragPosition = new Point(0, 0);
    pntCardOffset = new Point(0, 0);

    theRules = newRules;

    cardDragged = null;


    //setup player two
    if (isNewPlayer) {
      playerTwo = new Player("Levi");
      playerTwo.setAI(false);
      playerTwo.setColor(Card.CARD_BLUE);

      newDeck(playerTwo);
    }

    //setup player one
    playerOne = opponent;
    playerOne.setColor(Card.CARD_RED);
    playerOne.setAI(true);


    runGame();
//    BACKGROUND_SOUND.loop();
  }

  /**
    * newDeck - gives the player a new deck
    **/
  public void newDeck(Player thePlayer) {
    thePlayer.dropCards();

    thePlayer.winCard(getCard("Geezard"));
    thePlayer.winCard(getCard("Funguar"));
    thePlayer.winCard(getCard("Red Bat"));
    thePlayer.winCard(getCard("Gayla"));
    thePlayer.winCard(getCard("Gesper"));
    thePlayer.winCard(getCard("Fastitocalon-F"));
    thePlayer.winCard(getCard("Caterchipillar"));
    thePlayer.winCard(getCard("Zell"));
    thePlayer.winCard(getCard("Quistis"));
    thePlayer.winCard(getCard("Squall"));
    thePlayer.winCard(getCard("Seifer"));


  }

  /**
    * runGame - runs the game
    **/
  private void runGame() {
    int i, j; //loop counters

    displayWinner = false;
    //theRules = new Rules(Rules.TRADE_ONE, true, false, false,
    //                     false, true, true, false);


    for (i = 0; i < NUM_SPACES; i++) {
      for (j = 0; j < NUM_SPACES; j++) {
        theSpaces[i][j] = null;
      }
    }

    isDisplayingRules = true;
    repaint();
  }

  /**
    * setupPlayers - sets up all the players in the game
    **/
  public void setupPlayers() {
    setupPlayer(playerOne);
    setupPlayer(playerTwo);
  }

  /**
    * setupPlayer - makes both players select cards
    **/
  public void setupPlayer(Player aPlayer) {
    int i;

    currentPlayer = aPlayer;

    if (aPlayer.getAI()) {
      aPlayer.selectRandomCards();

      //check to see if the computer opponent has enough cards
      if (currentPlayer.getDeckSize() < NUM_SELECTABLE_CARDS) {
        for (i = currentPlayer.getDeckSize(); i < NUM_SELECTABLE_CARDS; i++) {
          Card newCard = getCard(currentPlayer.getLevel()).dupeCard();
          newCard.setColor(currentPlayer.getColor());
          currentPlayer.setSelectableCard(newCard, i);
        }
      } 
    } 

  }


  /**
    * getCard - returns a copy of the card with the name passed in
    *         - if the card is not found, the return null
    **/
  public Card getCard(String strCardName) {
    int i;
    Card cardToReturn;

    cardToReturn = null;
    if ((DEBUG == 3) || (DEBUG == DEBUG_ALL)) {System.out.println("*** looking for card: " + strCardName);}

    for (i = 0; i < theCards.size(); i++) {
      if (  ((Card) theCards.elementAt(i)).getName().equals(strCardName)  ) {
        if ((DEBUG == 3) || (DEBUG == DEBUG_ALL)) {System.out.println("*** found card: " + ((Card) theCards.elementAt(i)).getName());}
        cardToReturn = ((Card) theCards.elementAt(i)).dupeCard();
      }
    }

    if (cardToReturn == null) {
      System.out.println("Error: card " + strCardName + " not found");
      cardToReturn = ((Card) theCards.elementAt(0)).dupeCard(); //try to set it to a reasonable state
    }

    return cardToReturn;

  }


  /**
    * getCard - returns a copy of the card with the level passed in
    *         - if the card is not found, the return null
    **/
  public Card getCard(int iLevel) {
    int i, iRandom;
    Card cardToReturn;
    Vector levelCards;


    levelCards = new Vector();
    cardToReturn = null;

    for (i = 0; i < theCards.size(); i++) {
      if (  ((Card) theCards.elementAt(i)).getLevel() == iLevel) {
        levelCards.addElement((Card) theCards.elementAt(i));
      }

    }


    /*
    for (i = 0; i < levelCards.size(); i++) {

      System.out.println(((Card) levelCards.elementAt(i)));

    }
    */

    iRandom = (Math.abs(aRandom.nextInt()) % levelCards.size());
    cardToReturn = ((Card) theCards.elementAt(iRandom));


    if (cardToReturn == null) {
      System.out.println("Level " + iLevel + " not found");
      cardToReturn = ((Card) theCards.elementAt(0)).dupeCard(); //try to set it to a reasonable state
    }

    return cardToReturn;

  }



  public void paint(Graphics g) {
    if (IS_DOUBLE_BUFFERED) {
      update(g);
    } else {
      drawBoard(g);
    }
  }

  /**
    * repaintNow - I had to write this method because Java refuses to
    *              call repaint multiple times in a loop
    **/
  public void repaintNow() {
    //I have to do the double buffering here
    bufferImage = createImage(getBounds().width, getBounds().height);
    bufferGraphics = bufferImage.getGraphics();

    drawBoard(bufferGraphics);
    getGraphics().drawImage(bufferImage, 0, 0, this);

  }

  public void update(Graphics g) {
    drawBoard(g);

  }


  public void drawBoard(Graphics g) {
    int i, j;
    Graphics2D g2 = (Graphics2D) g;
    String strNumCards;

    Point pntInitCardPosition;
    int iCardSpacing, iHighlightOffset;

    rectWindowBounds = new Rectangle(getX(), getY(), getSize().width, getSize().height);
    //insetWindow = getInsets();
    insetWindow = new Insets(0, 0, 0, 0);
    dimDrawingBounds = new Dimension((rectWindowBounds.width - insetWindow.left - insetWindow.right),
                                     (rectWindowBounds.height - insetWindow.top - insetWindow.bottom));


    theFont = new Font("Dialog", Font.PLAIN, (dimDrawingBounds.height * 5 / 100));
    insetImgBoard = new Insets((int) (dimDrawingBounds.height * 0.0755556),
                               (int) (dimDrawingBounds.width * 0.248407),
                               (int) (dimDrawingBounds.height * 0.0444444),
                               (int) (dimDrawingBounds.width * 0.248407));

    rectPlayingBoard = new Rectangle( insetImgBoard.left,
                                      insetImgBoard.top,
                                      (dimDrawingBounds.width - insetImgBoard.left - insetImgBoard.right),
                                      (dimDrawingBounds.height - insetImgBoard.top - insetImgBoard.bottom));

    g2.drawImage(ImgBoard, 0, 0, dimDrawingBounds.width, dimDrawingBounds.height, this);


    //draw each player's cards
    dimCard = new Dimension( (new Double(dimDrawingBounds.width *  0.159235).intValue()) ,
                               (new Double(dimDrawingBounds.height * 0.284444).intValue()) );

    iCardSpacing = (int) (dimDrawingBounds.height * 0.168888);

    //draw Player one's cards
    pntInitCardPosition = new Point( (new Double(dimDrawingBounds.width * 0.05)).intValue(),
                                       (new Double(dimDrawingBounds.height * 0.022)).intValue());
    iHighlightOffset = (insetImgBoard.left - dimCard.width);



    if (isDisplayingRules) {
      theRules.displayRules(g2, dimDrawingBounds.width, dimDrawingBounds.height);
    }



    if (gameIsRunning || isChoosingCards) {
      playerOne.displayCards(g2, true, pntInitCardPosition.x, pntInitCardPosition.y,
                             dimCard.width, dimCard.height, iCardSpacing, iHighlightOffset);

      g2.setFont(BOARD_FONT);
      strNumCards = (playerOne.getTotalCards() + "");

      g2.setColor(Color.black);
      g2.drawString(strNumCards, (2 + pntInitCardPosition.x + (dimCard.width / 2)),
                   (new Double (dimDrawingBounds.height * 0.95)).intValue() + 2);

      g2.setColor(Color.white);
      g2.drawString(strNumCards, (pntInitCardPosition.x + (dimCard.width / 2)),
                   (new Double (dimDrawingBounds.height * 0.95)).intValue());



      //draw Player two's cards
      pntInitCardPosition = new Point( (new Double(dimDrawingBounds.width * (0.840765 - 0.05))).intValue(),
                                       (new Double(dimDrawingBounds.height * 0.022)).intValue());
      iHighlightOffset = (dimDrawingBounds.width - insetImgBoard.right);
      playerTwo.displayCards(g2, true, pntInitCardPosition.x, pntInitCardPosition.y,
                             dimCard.width, dimCard.height, iCardSpacing, iHighlightOffset);


      g2.setFont(BOARD_FONT);
      strNumCards = (playerTwo.getTotalCards() + "");

      g2.setColor(Color.black);
      g2.drawString(strNumCards, (2 + pntInitCardPosition.x + (dimCard.width / 2)),
                   (new Double (dimDrawingBounds.height * 0.95)).intValue() + 2);

      g2.setColor(Color.white);
      g2.drawString(strNumCards, (pntInitCardPosition.x + (dimCard.width / 2)),
                   (new Double (dimDrawingBounds.height * 0.95)).intValue());
    }


    if (isChoosingCards) {
      playerTwo.selectCards(g2, dimDrawingBounds.width, dimDrawingBounds.height, theFont);
    }

    if (gameIsRunning) {

      //draw the cards on the board
      for (i = 0; i < NUM_SPACES; i++) {
        for (j = 0; j < NUM_SPACES; j++) {
          if (theSpaces[i][j] != null) {
            theSpaces[i][j].drawCard(g2, (rectPlayingBoard.x + (rectPlayingBoard.width / 3 * i)),
                                     (rectPlayingBoard.y + (rectPlayingBoard.height / 3 * j)),
                                     ((new Double(dimDrawingBounds.width * 0.159235)).intValue()),
                                     ((new Double(dimDrawingBounds.height * 0.284444))).intValue(), 0);
          }

        }
      }
      if (cardDragged != null) {
        cardDragged.drawCard(g2, pntDragPosition.x,
                            pntDragPosition.y, dimCard.width, dimCard.height, 0);
      }
    }



    if (displayWinner) {
      String strWinningPlayer;
      g2.setFont(BOARD_FONT);

      if (getWinningPlayer() != null) {
        strWinningPlayer = getWinningPlayer().getName();

        g2.setColor(Color.black);
        g2.drawString(strWinningPlayer + " has won the game", 0,
                      ((dimDrawingBounds.height / 2) + 2));

        g2.setColor(Color.white);
        g2.drawString(strWinningPlayer + " has won the game", 0,
                      (dimDrawingBounds.height / 2));
        System.out.println(getWinningPlayer().getName() + " has won the game");

        currentPlayer = getWinningPlayer();
        if(getOtherPlayer().getAI() == true) {
//          BACKGROUND_SOUND.stop();
//          VICTORY_SOUND.loop();
        }

      } else {
        g2.setColor(Color.black);
        g2.drawString("Draw", ((dimDrawingBounds.width / 2) + 2),
                      ((dimDrawingBounds.height / 2) + 2));

        g2.setColor(Color.white);
        g2.drawString("Draw", (dimDrawingBounds.width / 2),
                      (dimDrawingBounds.height / 2));

        System.out.println("Draw");

      }
      displayWinner = false;
    }

  }

  /**
    * dragCard - makes a card dragged
    **/
  private void dragCard(Card[] theCards, int iPosition, Point e) {
    cardDragged = theCards[iPosition];
    cardDragged.setHighlighted(false);
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    iDragCardPosition = iPosition; //if the player does not make a
                                   //legal move we can move the card
                                   //back to its original position
    theCards[iPosition] = null;

    pntCardOffset = new Point((cardDragged.getPosition().x - e.x),
                                 (cardDragged.getPosition().y - e.y) );


    pntDragPosition = cardDragged.getPosition();
//    CARD_SOUND.play();

  }

  /**
    * AISelect - makes the computer play a random card
    **/
  private void AISelect() {
    int iRow, iCol, iCard;
    int i;
    boolean keepLooping;

    for (i = 0; i < NUM_SELECTABLE_CARDS; i++) {
      if (currentPlayer.getSelectedCards()[i] != null) {
        currentPlayer.getSelectedCards()[i].setHighlighted(true);
        try {
          repaintNow();
          Thread.sleep(100);
        } catch (InterruptedException e) { }

        currentPlayer.getSelectedCards()[i].setHighlighted(false);

      }

    }

    //I can program AI here later, but for now, just pick a random space
    iRow = (Math.abs(aRandom.nextInt()) % NUM_SPACES);
    iCol = (Math.abs(aRandom.nextInt()) % NUM_SPACES);

    iDragCardPosition = (Math.abs(aRandom.nextInt()) % NUM_SELECTABLE_CARDS);


    //find a random card to play
    keepLooping = true;
    while(keepLooping) {
      if(currentPlayer.getSelectedCards()[iDragCardPosition] != null) {
        keepLooping = false;
      } else {
        iDragCardPosition++;
        if(iDragCardPosition >= NUM_SELECTABLE_CARDS) {
          iDragCardPosition = 0;
        }
      }
    }

    //find a random spot to play the card on
    keepLooping = true;
    while(keepLooping) {
      //System.out.println("Trying Space: " + iRow + " " + iCol);
      if (theSpaces[iRow][iCol] == null) {
        keepLooping = false;
        cardDragged = currentPlayer.getSelectedCards()[iDragCardPosition];
        currentPlayer.getSelectedCards()[iDragCardPosition] = null;
      } else {
        iRow++;
        if (iRow == NUM_SPACES) {
          iRow = 0;
          iCol++;
            if (iCol == NUM_SPACES) {
              iCol = 0;
            }
        }
      }
    }

//    CARD_SOUND.play();
    playCard(iRow, iCol);
    
  }

  /**
    * findResults
    **/
  private void findResults() {
    displayWinner = true;
    repaintNow();
    try {Thread.sleep(2000);} catch (InterruptedException e) { }

    startGame(theRules, playerOne);

  }

  /**
    * getWinningPlayer - returns the winning player
    **/
  private Player getWinningPlayer() {
    Player winningPlayer;

    if (playerOne.getTotalCards() > playerTwo.getTotalCards()) {
      winningPlayer = playerOne;
    } else if (playerTwo.getTotalCards() > playerOne.getTotalCards()) {
      winningPlayer = playerTwo;
    } else if (playerTwo.getTotalCards() == playerOne.getTotalCards()) {
      winningPlayer = null;
    } else {
      winningPlayer = null;
    }


    return winningPlayer;
  }

  /**
    * playCard - tries to play a card on the board
    *          - returns true if it is a legal move, false if it is an
    *            illegal move
    **/
  private boolean playCard(int x, int y) {
    if (DEBUG == 3 || DEBUG == DEBUG_ALL) { System.out.println("space: (" + x + ", " + y + ")"); }
    boolean continueGame;
    boolean isPlayed;
    int i, j; //loop counters

    continueGame = true;

    //System.out.println(x + ", " + y + " Current Card: " + theSpaces[x][y]);
    if (theSpaces[x][y] == null) {
      theSpaces[x][y] = cardDragged;

      flipCards(cardDragged, x, y);


      cardDragged = null;
      isPlayed = true;

      currentPlayer.getSelectedCards()[iDragCardPosition] = null;

      //which way should I check for end game??
      //I could either check if all spaces have been filled, or
      //check if both players have one or less cards... I guess
      //I check to see if all spaces are filled
      continueGame = false;
      for (i = 0; i < NUM_SPACES; i++) {
        for (j = 0; j < NUM_SPACES; j++) {
          if (theSpaces[i][j] == null) {
            continueGame = true;
            //I could break twice here
          }
        }
      }

    } else {
      currentPlayer.getSelectedCards()[iDragCardPosition] = cardDragged;
      cardDragged = null;
      isPlayed = false;
    }


    if (continueGame) {
      getNextPlayer();
      //check to see if the next player is a computer opponent
      if (currentPlayer.getAI()) {
        AISelect();
      }
    } else {
      findResults();
    }

    return isPlayed;

  }

  /**
    * flipCards - flips any needed cards
    **/
  private void flipCards(Card playedCard, int x, int y) {
    Card testSpace;

    //test the left card
    if ((x - 1) >= 0) {

      testSpace = theSpaces[(x - 1)][y];

      if ((testSpace != null) && (!testSpace.getColor().equals(currentPlayer.getColor()))) {
        if (playedCard.getLeft() > testSpace.getRight()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();
//          FLIP_CARD_SOUND.play();
        }
      }
    }

    //test the right card
    if ((x + 1) < NUM_SPACES) {
      testSpace = theSpaces[(x + 1)][y];
      if ((testSpace != null) && (!testSpace.getColor().equals(currentPlayer.getColor()))) {
        if (playedCard.getRight() > testSpace.getLeft()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();
//          FLIP_CARD_SOUND.play();

        }
      }
    }


    //test the top card
    if ((y - 1) >= 0) {
      testSpace = theSpaces[x][(y - 1)];
      if ((testSpace != null) && (!testSpace.getColor().equals(currentPlayer.getColor()))) {
        if (playedCard.getTop() > testSpace.getBottom()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();
//          FLIP_CARD_SOUND.play();

        }
      }
    }

    //test the bottom card
    if ((y + 1) < NUM_SPACES) {
      testSpace = theSpaces[x][(y + 1)];
      if ((testSpace != null) && (!testSpace.getColor().equals(currentPlayer.getColor()))) {
        if (playedCard.getBottom() > testSpace.getTop()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();
//          FLIP_CARD_SOUND.play();

        }
      }
    }

    repaint();

  }

  /**
    * getNextPlayer - sets currentPlayer to the next player
    **/
  private void getNextPlayer() {
    if (currentPlayer.equals(playerOne)) {
      currentPlayer = playerTwo;
    } else if (currentPlayer.equals(playerTwo)) {
      currentPlayer = playerOne;
    } else {
      System.out.println("Error: can not find player");
    }
  }

  /**
    * getOtherPlayer - returns the other player
    **/
  private Player getOtherPlayer() {
    Player playerToReturn;
    playerToReturn = null;

    if (currentPlayer.equals(playerOne)) {
      playerToReturn = playerTwo;
    } else if (currentPlayer.equals(playerTwo)) {
      playerToReturn = playerOne;
    } else {
      System.out.println("Error: can not find player");
    }

    return playerToReturn;
  }

  /**
    * mouseClicked - required by MouseListener
    * mouseEneterd
    * mouseExited
    * mousePressed
    * mouseReleased
    **/
  public void mouseClicked(MouseEvent e) {
  }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) {
    int i;
    Point pntMousePosition = new Point(e.getX(), e.getY());
    Card[] tempCards;


    if (gameIsRunning) {
      //check all cards to see if the mouse if over them
      //this could possibly be optimized later
      tempCards = currentPlayer.getSelectedCards();
      for (i = 0; i < NUM_SELECTABLE_CARDS; i++) {
        if (tempCards[i] != null) {
          if (tempCards[i].mouseIsOver(pntMousePosition)) {

            if (i < (tempCards.length - 1)) {

              if (tempCards[(i + 1)] != null) {
                if (!tempCards[(i + 1)].mouseIsOver(pntMousePosition)) {
                  dragCard(tempCards, i, e.getPoint());
                }
              } else {
                dragCard(tempCards, i, e.getPoint());
              }

            } else {
              dragCard(tempCards, i, e.getPoint());
            }
          }
        }
      }
      repaint();
    }
  }

  public void mouseReleased(MouseEvent e) {
    Point pntPlayingBoardPosition;
    boolean cardIsPlayed;
    int iStartingPlayer;

   if (isDisplayingRules) {
     isDisplayingRules = false;
     isChoosingCards = true;
     setupPlayers();

   } else if (isChoosingCards) {

     if ((currentPlayer.findSelectedCard(e.getX(), e.getY()) != null) &&
         (currentPlayer.findSelectedCard(e.getX(), e.getY()).getStock() > 0)) {
       currentPlayer.setSelectableCard(currentPlayer.findSelectedCard(e.getX(), e.getY()),
                                       (currentPlayer.getTotalCards()));
//       CARD_SOUND.play();

       //if both players have all cards, then start the game
       if ((playerOne.getTotalCards() == NUM_SELECTABLE_CARDS) &&
           (playerTwo.getTotalCards() == NUM_SELECTABLE_CARDS)) {
         isChoosingCards = false;
         gameIsRunning = true;

         //pick a random player to start the game
         iStartingPlayer = (Math.abs(aRandom.nextInt()) % NUM_PLAYERS);
         switch (iStartingPlayer) {
           case 0:
             currentPlayer = playerOne;
             break;
           case 1:
             currentPlayer = playerTwo;
             break;
           default:
             currentPlayer = playerTwo;
         }

         if (currentPlayer.getAI()) {
           AISelect();
         }
       }
     }






   } else if (gameIsRunning) {
    if(cardDragged != null) {
      boolean isTemp = false;
      if (rectPlayingBoard.contains(e.getPoint())) {
        pntPlayingBoardPosition = e.getPoint();

        pntPlayingBoardPosition.translate(-insetImgBoard.left,
                                          -insetImgBoard.top);
        playCard((pntPlayingBoardPosition.x / dimCard.width),
                 (pntPlayingBoardPosition.y / dimCard.height));

      } else {
        currentPlayer.getSelectedCards()[iDragCardPosition] = cardDragged;
        cardDragged = null;
      }
    }
    repaint();
   }


  }


  /**
    * mouseDragged - required by MouseMotionListener
    * mouseMoved
    **/
  public void mouseDragged(MouseEvent e) {
    pntDragPosition.setLocation(e.getPoint());
    pntDragPosition.translate(pntCardOffset.x, pntCardOffset.y);
    repaint();
  }

  public void mouseMoved(MouseEvent e) {
    int i;
    Point pntMousePosition = new Point(e.getX(), e.getY());
    Card[] tempCards;
    boolean changeCursor;

    changeCursor = false;


   if (isChoosingCards) {
     if (currentPlayer.findSelectedCard(e.getX(), e.getY()) != null) {
       //System.out.println(currentPlayer.findSelectedCard(e.getX(), e.getY()).getName());
       changeCursor = true;
     }
   }

   if (gameIsRunning) {

    //check all cards to see if the mouse if over them
    //this could possibly be optimized later

    tempCards = currentPlayer.getSelectedCards();
    for (i = 0; i < NUM_SELECTABLE_CARDS; i++) {
      if (tempCards[i] != null) {
        if (tempCards[i].mouseIsOver(pntMousePosition)) {
    
          if (i < (tempCards.length - 1)) {
            if (tempCards[(i + 1)] != null) {
              if (!tempCards[(i + 1)].mouseIsOver(pntMousePosition)) {
                tempCards[i].setHighlighted(true);
                changeCursor = true;
              } else {
                tempCards[i].setHighlighted(false);

              }
            } else {
              tempCards[i].setHighlighted(true);
              changeCursor = true;
            }
          } else {
            tempCards[i].setHighlighted(true);
            changeCursor = true;
          }
        } else {
          tempCards[i].setHighlighted(false);

        }
      }
    }

   }

   if (changeCursor) {
     setCursor(new Cursor(Cursor.HAND_CURSOR));
   } else {
     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
   }
   repaint();


  }

  /**
    * getFont - returns the font used
    **/
  public Font getFont() {
    return theFont;
  }

}
