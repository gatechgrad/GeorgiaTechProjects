import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Board extends JComponent implements MouseMotionListener, MouseListener {

  /********************** CONSTANTS **********************/
  public static final int NUM_SPACES = 3;
  public static final String WINDOW_TITLE = "Triple Triad";
  public static final Rectangle SCREEN_BOUNDS =
    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
  public static final Rectangle INIT_WINDOW_BOUNDS = new Rectangle(50, 50, 400, 300);
  public static final String CURSOR_NAME = "FF Hand";

  public static final int DEBUG = 4;
  public static final int DEBUG_ALL = 1;

  public static final int NUM_SELECTABLE_CARDS = 5;


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

  public Board() {

    isNewPlayer = true;
    gameIsRunning = false;
    rectPlayingBoard = new Rectangle(0, 0, 0, 0);
    pntDragPosition = new Point(0, 0);
    pntCardOffset = new Point(0, 0);

    /*
    //make the cursor
    Toolkit myToolkit = Toolkit.getDefaultToolkit();
    Image ImgCursor = Toolkit.getDefaultToolkit().createImage("cursor.gif");
    try {
      cursorFFHand =
        myToolkit.createCustomCursor(ImgCursor, (new Point(10, 10)), Board.CURSOR_NAME);
    } catch (IndexOutOfBoundsException e) { }
    */


    addMouseMotionListener(this);
    addMouseListener(this);

    cardDragged = null;
    theCards = new Vector();
    theSpaces = new Card[NUM_SPACES][NUM_SPACES];
    rectWindowBounds =  new Rectangle(640, 480);

    MediaTracker tempMediaTracker = new MediaTracker(this);
    ImgBoard = Toolkit.getDefaultToolkit().getImage("board.gif");
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
    theCards.addElement( new Card(9, 2, 9, 3, 8, Card.ELEM_NONE, "Minimog", this) );
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

    if (isNewPlayer) {
      playerOne = new Player("Levi", Card.CARD_RED);
      playerTwo = new Player("Smith", Card.CARD_BLUE);

      setupNewPlayer(playerOne);
      setupNewPlayer(playerTwo);
    }

    runGame();
  }

  /**
    * setupNewPlayer - makes a new Player and gives the player a few cards
    **/
  public void setupNewPlayer(Player thePlayer) {
    newDeck(thePlayer);
  }

  /**
    * newDeck - gives the player a new deck
    **/
  public void newDeck(Player thePlayer) {
    System.out.println("*** dropping cards");
    thePlayer.dropCards();

    System.out.println("*** winning new cards");
    thePlayer.winCard(getCard("Zell"));
    thePlayer.winCard(getCard("Pupu"));
    thePlayer.winCard(getCard("Seifer"));
    thePlayer.winCard(getCard("Squall"));
    thePlayer.winCard(getCard("Shiva"));


  }

  /**
    * runGame - runs the game
    **/
  private void runGame() {
    int i, j; //loop counters

    theRules = new Rules(Rules.TRADE_ONE, true, false, false,
                         false, true, true, false);


    for (i = 0; i < NUM_SPACES; i++) {
      for (j = 0; j < NUM_SPACES; j++) {
        theSpaces[i][j] = null;
      }
    }

    playerOne.selectCards();
    playerTwo.selectCards();
    gameIsRunning = true;
    currentPlayer = playerOne;
  }

  /**
    * getCard - returns a copy of the card with the name passed in
    *         - if the card is not found, the return null
    **/
  private Card getCard(String strCardName) {
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

    return cardToReturn;

  }


  public void paint(Graphics g) {
    update(g);
  }

  public void update(Graphics g) {
    int i, j;
    Graphics2D g2 = (Graphics2D) g;
    String strNumCards;

    Point pntInitCardPosition;
    int iCardSpacing, iHighlightOffset;

    rectWindowBounds = new Rectangle(getX(), getY(), getSize().width, getSize().height);
    insetWindow = getInsets();
    dimDrawingBounds = new Dimension((rectWindowBounds.width - insetWindow.left - insetWindow.right),
                                     (rectWindowBounds.height - insetWindow.top - insetWindow.bottom));


    insetImgBoard = new Insets((int) (dimDrawingBounds.height * 0.0755556),
                               (int) (dimDrawingBounds.width * 0.248407),
                               (int) (dimDrawingBounds.height * 0.0444444),
                               (int) (dimDrawingBounds.width * 0.248407));

    rectPlayingBoard = new Rectangle( insetImgBoard.left,
                                      insetImgBoard.top,
                                      (dimDrawingBounds.width - insetImgBoard.left - insetImgBoard.right),
                                      (dimDrawingBounds.height - insetImgBoard.top - insetImgBoard.bottom));

    g2.drawImage(ImgBoard, 0, 0, dimDrawingBounds.width, dimDrawingBounds.height, this);


    if (gameIsRunning) {
      //draw each player's cards
      dimCard = new Dimension( (new Double(dimDrawingBounds.width *  0.159235).intValue()) ,
                               (new Double(dimDrawingBounds.height * 0.284444).intValue()) );

      iCardSpacing = (int) (dimDrawingBounds.height * 0.168888);

      //draw Player one's cards
      pntInitCardPosition = new Point( (new Double(dimDrawingBounds.width * 0.05)).intValue(),
                                       (new Double(dimDrawingBounds.height * 0.022)).intValue());
      iHighlightOffset = (insetImgBoard.left - dimCard.width);
      playerOne.displayCards(g2, true, pntInitCardPosition.x, pntInitCardPosition.y,
                             dimCard.width, dimCard.height, iCardSpacing, iHighlightOffset);

      g2.setColor(Color.white);
      g2.setFont(new Font("Dialog", Font.BOLD, 36));
      strNumCards = (playerOne.getTotalCards() + "");

      g2.drawString(strNumCards, (pntInitCardPosition.x + (dimCard.width / 2)),
                   (new Double (dimDrawingBounds.height * 0.95)).intValue());


      //draw Player two's cards
      pntInitCardPosition = new Point( (new Double(dimDrawingBounds.width * (0.840765 - 0.05))).intValue(),
                                       (new Double(dimDrawingBounds.height * 0.022)).intValue());
      iHighlightOffset = (dimDrawingBounds.width - insetImgBoard.right);
      playerTwo.displayCards(g2, true, pntInitCardPosition.x, pntInitCardPosition.y,
                             dimCard.width, dimCard.height, iCardSpacing, iHighlightOffset);


      g2.setColor(Color.white);
      g2.setFont(new Font("Dialog", Font.BOLD, 36));
      strNumCards = (playerTwo.getTotalCards() + "");

      g2.drawString(strNumCards, (pntInitCardPosition.x + (dimCard.width / 2)),
                   (new Double (dimDrawingBounds.height * 0.95)).intValue());


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

  }

  /**
    * findResults
    **/
  private void findResults() {
    runGame();
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

    System.out.println(x + ", " + y + " Current Card: " + theSpaces[x][y]);
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

      if (testSpace != null) {
        if (playedCard.getLeft() > testSpace.getRight()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();
        }
      }
    }

    //test the right card
    if ((x + 1) < NUM_SPACES) {
      testSpace = theSpaces[(x + 1)][y];
      if (testSpace != null) {
        if (playedCard.getRight() > testSpace.getLeft()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();

        }
      }
    }


    //test the top card
    if ((y - 1) >= 0) {
      testSpace = theSpaces[x][(y - 1)];
      if (testSpace != null) {
        if (playedCard.getTop() > testSpace.getBottom()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();
        }
      }
    }

    //test the bottom card
    if ((y + 1) < NUM_SPACES) {
      testSpace = theSpaces[x][(y + 1)];
      if (testSpace != null) {
        if (playedCard.getBottom() > testSpace.getTop()) {
          testSpace.setColor(currentPlayer.getColor());
          currentPlayer.incrementTotalCards();
          getOtherPlayer().decrementTotalCards();
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
  public void mouseClicked(MouseEvent e) {  }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) {
    int i;
    Point pntMousePosition = new Point(e.getX(), e.getY());
    Card[] tempCards;


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

  public void mouseReleased(MouseEvent e) {
    Point pntPlayingBoardPosition;
    boolean cardIsPlayed;

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


    if (rectPlayingBoard.contains(pntMousePosition)) {
      //setCursor(cursorFFHand);
    } else {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

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
                setCursor(new Cursor(Cursor.HAND_CURSOR));
              } else {
                tempCards[i].setHighlighted(false);
                //setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

              }
            } else {
              tempCards[i].setHighlighted(true);
              setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
          } else {
            tempCards[i].setHighlighted(true);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
          }
        } else {
          tempCards[i].setHighlighted(false);
          setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }
      }
    }
    repaint();

  }


  /**
    * main - called when program is started as an application
    **/
  public static void main(String argv[]) {
    Board myBoard = new Board();
    JFrame myFrame = new JFrame();
    WindowListener l = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }

    };




    myFrame.addWindowListener(l);

    myFrame.setSize(INIT_WINDOW_BOUNDS.width, INIT_WINDOW_BOUNDS.height);
    myFrame.setBounds(  ((SCREEN_BOUNDS.width / 2) - (INIT_WINDOW_BOUNDS.width / 2)),
                     ((SCREEN_BOUNDS.height / 2) - (INIT_WINDOW_BOUNDS.height / 2)),
                      INIT_WINDOW_BOUNDS.width, INIT_WINDOW_BOUNDS.height);   //sets location and
                                                      //size of window<center)

    myFrame.setTitle(WINDOW_TITLE);
    myFrame.getContentPane().add(myBoard);
    myFrame.setVisible(true);

  }


}
