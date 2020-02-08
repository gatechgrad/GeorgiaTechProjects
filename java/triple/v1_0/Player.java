import java.util.Vector;
import java.awt.*;

class Player {

  /********************** CONSTANTS **********************/


  /********************** CLASS VARIABLES **********************/
  private Vector playerDeck;            //the player's deck of cards
  private String strName;
  private String strPlayerColor;
  private Card[] theSelectedCards;        //the cards the player has
                                        //selected for a game
  private int iTotalCards;

  public Player(String newName, String newColor) {
    playerDeck = new Vector();
    strName = newName;
    strPlayerColor = newColor;

  }

  /**
    * winCard - adds the card that the player has won to his deck
    **/
  public void winCard(Card newCard) {
    newCard.setColor(strPlayerColor);
    playerDeck.addElement(newCard);
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
    * dropCards - removes all cards from the players deck
    **/
  public void dropCards() {
    playerDeck = new Vector();   //assign the player and empty deck, and
                                 //let Java worry about deallocating the
                                 //memory of the previous cards
  }

  /**
    * selectCards
    **/
  public void selectCards() {
    theSelectedCards = new Card[Board.NUM_SELECTABLE_CARDS];


    theSelectedCards[0] = ((Card) playerDeck.elementAt(0)).dupeCard();
    theSelectedCards[1] = ((Card) playerDeck.elementAt(1)).dupeCard();
    theSelectedCards[2] = ((Card) playerDeck.elementAt(2)).dupeCard();
    theSelectedCards[3] = ((Card) playerDeck.elementAt(3)).dupeCard();
    theSelectedCards[4] = ((Card) playerDeck.elementAt(4)).dupeCard();

    iTotalCards = Board.NUM_SELECTABLE_CARDS;
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
