import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;

class Player {

  /********************** CONSTANTS **********************/
  public static final int NUM_CARDS_PER_PAGE = 11;

  /********************** CLASS VARIABLES **********************/
  private Vector playerDeck;            //the player's deck of cards
  private String strName;
  private String strPlayerColor;
  private Card[] theSelectedCards;        //the cards the player has
                                        //selected for a game
  private int iLevel;               //for computer opponents

  private Card cardMenuCard;

  private int iTotalCards;
  private boolean isComputerPlayer;
  private Rectangle[] rectNameBounds;

  /**
    * Player - constructor
    **/
  public Player(String newName) {
    isComputerPlayer = false;
    playerDeck = new Vector();
    strName = newName;
    iLevel = 1;
    rectNameBounds = new Rectangle[NUM_CARDS_PER_PAGE];
    theSelectedCards = new Card[Board.NUM_SELECTABLE_CARDS];
  }

  /**
    * setAI
    **/
  public void setAI(boolean isComputerPlayer) {
    this.isComputerPlayer = isComputerPlayer;
  }

  /**
    * getAI - returns wether or not this player uses artifical intelligence
    **/
  public boolean getAI() {
    return isComputerPlayer;
  }

  /**
    * setColor - sets the player's color
    **/
  public void setColor(String newColor) {
    strPlayerColor = newColor;
  }

  /**
    * winCard - adds the card that the player has won to his deck
    **/
  public void winCard(Card newCard) {
    int i;
    boolean foundCard;

    foundCard = false;
    newCard.setColor(strPlayerColor);

    //search the player's deck for this card
    for (i = 0; i < playerDeck.size(); i++) {
      if (((Card) playerDeck.elementAt(i)).getName().equals(newCard.getName())) {
        //if the player has this card, then increment the stock
        ((Card) playerDeck.elementAt(i)).addStock(1);
        foundCard = true;
      }
    }

    if (!foundCard) {
      //if the player does not have this card, then add it to the player's
      //list of cards
      newCard.addStock(1);
      playerDeck.addElement(newCard);
    }

  }


  /**
    * loseCard - removes the card that the player has lost
    **/
  public void loseCard(Card lostCard) {
    playerDeck.removeElement(lostCard);
  }

  /**
    * displayCards - displays the users cards
    **/
  public void displayCards() {
    int i;
    for (i = 0; i < playerDeck.size(); i++) {
      System.out.println((Card) playerDeck.elementAt(i));
    }
  }

  /**
    * setLevel - sets the player's high and low level
    **/
  public void setLevel(int newLevel) {
    iLevel = newLevel;
  }

  /**
    * getLevel - returns the player's level
    **/
  public int getLevel() {
    return iLevel;
  }

  /**
    * dropCards - removes all cards from the players deck
    **/
  public void dropCards() {
    playerDeck = new Vector();   //assign the player and empty deck, and
                                 //let Java worry about deallocating the
                                 //memory of the previous cards
  }

  /**
    * defaultCards
    **/
  public void defaultCards() {
    theSelectedCards = new Card[Board.NUM_SELECTABLE_CARDS];


    theSelectedCards[0] = ((Card) playerDeck.elementAt(0)).dupeCard();
    theSelectedCards[1] = ((Card) playerDeck.elementAt(1)).dupeCard();
    theSelectedCards[2] = ((Card) playerDeck.elementAt(2)).dupeCard();
    theSelectedCards[3] = ((Card) playerDeck.elementAt(3)).dupeCard();
    theSelectedCards[4] = ((Card) playerDeck.elementAt(4)).dupeCard();

    iTotalCards = Board.NUM_SELECTABLE_CARDS;
  }

  /**
    * selectRandomCards - selects random cards from the player's deck
    **/
  public void selectRandomCards() {
    int i, iRandom;
    int[] iCardPositions = new int[Board.NUM_SELECTABLE_CARDS];
    Random aRandom = new Random();

    iTotalCards = 0;

    theSelectedCards = new Card[Board.NUM_SELECTABLE_CARDS];


    for (i = 0; i < Board.NUM_SELECTABLE_CARDS; i++) {
      if (!playerDeck.isEmpty()) {
        iRandom = (Math.abs(aRandom.nextInt()) % playerDeck.size());

        theSelectedCards[i] = ((Card) playerDeck.elementAt(iRandom)).dupeCard();
        theSelectedCards[i].setColor(strPlayerColor);
        playerDeck.removeElementAt(iRandom); //remove card so it isn't selected again
        iCardPositions[i] = iRandom; //remember the spot so we can add the card back later
        iTotalCards++;
      }

    }

    //return the removed cards
    for (i = 0; i < Board.NUM_SELECTABLE_CARDS; i++) {
      if (theSelectedCards[i] != null) {
        playerDeck.insertElementAt(theSelectedCards[i].dupeCard(), i);
      }
    }

  }

  /**
    * setSelectableCard - sets a selected card
    **/
  public void setSelectableCard(Card theCard, int iPosition) {
    theSelectedCards[iPosition] = theCard;
    iTotalCards++;
  }



  /**
    * selectCards
    **/
  public void selectCards(Graphics g, int iDrawingWidth, int iDrawingHeight,
                          Font theFont) {
    Graphics2D g2 = (Graphics2D) g;
    Rectangle rectCardNames;
    int i, iStartingIndex;

    rectCardNames = new Rectangle((iDrawingWidth * 15 / 100), (iDrawingHeight * 10 / 100),
                                   (iDrawingWidth * 40 / 100), (iDrawingHeight * 80 / 100));

    g2.setFont(theFont);
    g2.setColor(Board.RECT_COLOR);
    g2.fillRect(rectCardNames.x, rectCardNames.y, rectCardNames.width, rectCardNames.height);
    g2.setColor(Board.RECT_BKG_COLOR);
    g2.drawRect(rectCardNames.x, rectCardNames.y, rectCardNames.width, rectCardNames.height);
    g2.setColor(Board.RECT_FG_COLOR);
    g2.drawLine(rectCardNames.x, (rectCardNames.y + 1), (rectCardNames.x + rectCardNames.width - 1), (rectCardNames.y + 1));
    g2.drawLine(rectCardNames.x, (rectCardNames.y + 1), rectCardNames.x, ((rectCardNames.y + 1) + rectCardNames.height));

    iStartingIndex = 0;

    rectNameBounds = new Rectangle[NUM_CARDS_PER_PAGE];
    for (i = iStartingIndex; i < playerDeck.size(); i++) {
      g2.setColor(Board.FONT_BKG_COLOR);
      g2.drawString(((Card) playerDeck.elementAt(i)).getName(),
                    (rectCardNames.x + (iDrawingWidth * 5 / 100) + 1),
                    (rectCardNames.y + (iDrawingHeight / 10) + (i * (theFont.getSize() + (theFont.getSize() * 25 / 100)) + 1) + 1));

      g2.drawString(((Card) playerDeck.elementAt(i)).getStock() + "",
                    (rectCardNames.x - (iDrawingWidth * 10 / 100) + rectCardNames.width),
                    (rectCardNames.y + (iDrawingHeight / 10) + (i * (theFont.getSize() + (theFont.getSize() * 25 / 100)) + 1) + 1));

      g2.setColor(Board.FONT_FG_COLOR);
      g2.drawString(((Card) playerDeck.elementAt(i)).getName(),
                    (rectCardNames.x + (iDrawingWidth * 5 / 100)),
                    (rectCardNames.y + (iDrawingHeight / 10) + (i * (theFont.getSize() + (theFont.getSize() * 25 / 100)) + 1)));
      g2.drawString(((Card) playerDeck.elementAt(i)).getStock() + "",
                    (rectCardNames.x - (iDrawingWidth * 10 / 100) + rectCardNames.width),
                    (rectCardNames.y + (iDrawingHeight / 10) + (i * (theFont.getSize() + (theFont.getSize() * 25 / 100)) + 1)));

      rectNameBounds[i] = new Rectangle((rectCardNames.x + (iDrawingWidth * 5 / 100) + 1),
                                        (rectCardNames.y + (iDrawingHeight / 10) + (i * (theFont.getSize() + (theFont.getSize() * 25 / 100)) + 1) + 1 - theFont.getSize()),
                                        rectCardNames.width, theFont.getSize() 
                                        );

    }

    //System.out.println(cardMenuCard);
    if (cardMenuCard != null) {
      cardMenuCard.drawCard(g2, (iDrawingWidth * 57 / 100),
                            (iDrawingHeight * 47 / 100),
                            (iDrawingWidth * 16 / 100),
                            (iDrawingHeight * 28 / 100), 0);
    }


    /*
    theSelectedCards[0] = ((Card) playerDeck.elementAt(0)).dupeCard();
      theSelectedCards[0].setColor(strPlayerColor);

    theSelectedCards[1] = ((Card) playerDeck.elementAt(1)).dupeCard();
      theSelectedCards[1].setColor(strPlayerColor);
    theSelectedCards[2] = ((Card) playerDeck.elementAt(2)).dupeCard();
      theSelectedCards[2].setColor(strPlayerColor);
    theSelectedCards[3] = ((Card) playerDeck.elementAt(3)).dupeCard();
      theSelectedCards[3].setColor(strPlayerColor);
    theSelectedCards[4] = ((Card) playerDeck.elementAt(4)).dupeCard();
      theSelectedCards[4].setColor(strPlayerColor);


    iTotalCards = Board.NUM_SELECTABLE_CARDS;
    */

  }

  /**
    * findSelectedCard - takes in a point's coordinates and returns
    *                    the card that is placed in the card
    **/
  public Card findSelectedCard(int x, int y) {
    int i;

    Card cardToReturn;

    cardToReturn = null;
    cardMenuCard = null;


    for (i = 0; i < NUM_CARDS_PER_PAGE; i++) {
      if ((rectNameBounds[i] != null) && (rectNameBounds[i].contains(x, y))) {
        cardToReturn = (Card) playerDeck.elementAt(i);
        cardMenuCard = (Card) playerDeck.elementAt(i);
      }
    }
    return cardToReturn;
  }

  /**
    * getSelectedCards - return the array of Cards the player has selected
    **/
  public Card[] getSelectedCards() {
    return theSelectedCards;
  }

  /**
    * getTotalCards - returns the number of total cards
    **/
  public int getTotalCards() {

    return iTotalCards;

  }

  /**
    * getDeckSize - returns the size of the player's deck
    **/
  public int getDeckSize() {
    return playerDeck.size();
  }

  /**
    * incrementTotalCards - returns the number of total cards
    **/
  public void incrementTotalCards() {
    iTotalCards++;
  }

  /**
    * decrementTotalCards - returns the number of total cards
    **/
  public void decrementTotalCards() {
    iTotalCards--;
  }


  /**
    * getColor - returns the player's color
    **/
  public String getColor() {
    return strPlayerColor;
  }

  /**
    * getName - returns the player's name
    **/
  public String getName() {
    return strName;
  }

  /**
    * displayCards - displays the player's cards
    **/
  public void displayCards(Graphics g, boolean areShowing, int x, int y,
                           int iCardWidth, int iCardHeight, int iCardSpacing,
                           int iHighlightOffset) {
    int i;
    for (i = 0; i < Board.NUM_SELECTABLE_CARDS; i++) {
      if (theSelectedCards[i] != null) {
        theSelectedCards[i].drawCard(g, x, ((i * iCardSpacing) + y),
                                     iCardWidth, iCardHeight, iHighlightOffset);

      }

    }


  }

}
