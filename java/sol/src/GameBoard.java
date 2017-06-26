import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import java.util.Vector;


class GameBoard extends Canvas implements MouseListener, MouseMotionListener {

public static final Color MY_GREEN = new Color(0, 192, 0);
public static final String SPADE = "SPADE";
public static final String CLUB = "CLUB";
public static final String DIAMOND = "DIAMOND";
public static final String HEART = "HEART";
public static final String WILD = "WILD";
public static final String REDEAL = "REDEAL";


public static final int ACE = 1;
public static final int JACK = 11;
public static final int QUEEN = 12;
public static final int KING = 13;
public static final int NUM_CARDS = 52;
public static final int NUM_SUITS = 4;
public static final int CARDS_IN_SUIT = 13;
public static final int STACKS = 7;
public static final int HOLDER_XOFFSET = 300;
public static final int HOLDER_YOFFSET = 10;
public static final Point PILE_LOC = new Point (10, 10);

public static int SPACING = 20;
public static int CARD_SPACING = 20;
public static int TINY_SPACING = 2;

public static Point STACK_LOC = new Point(150, 130);
public static Dimension CARD_SIZE = new Dimension(65, 90);

private boolean hasStickyCard;
private boolean hasPileCard;
private Vector stickyCard;
private int iStickyCardStack;

private Stack TopHolder0;
private Stack TopHolder1;
private Stack TopHolder2;
private Stack TopHolder3;

private Stack Covered1;
private Stack Covered2;
private Stack Covered3;
private Stack Covered4;
private Stack Covered5;
private Stack Covered6;

private Stack[] HolderArray;
private Stack[] StackArray;

private Stack Pile;
private Vector Discard;

private Point MouseP;

public boolean damage = true;
//public abstract void render(Graphics g);
Image bufferImage = null;
private Graphics bufferGraphics = null;
private Thread t;
private Rectangle r = new Rectangle(0, 0, 0, 0);


public GameBoard() {
  setBackground(MY_GREEN);
  addMouseListener(this);
  addMouseMotionListener(this);

//  if (t == null) {
//    t = new Thread(this);
//  }



  MouseP = new Point(0, 0);
  hasStickyCard = false;
  hasPileCard = false;
  Deal();
}

public void Deal() {
  Card cards[] = shuffle(); //shuffle the cards
  int i;
  int iCount;

//If you want to see how the stack was shuffled
/*
  int i;
  for (i = 0; i < cards.length; i++) {
    System.out.println(cards[i]);
  }
*/

  Covered1 = new Stack();
  Covered2 = new Stack();
  Covered3 = new Stack();
  Covered4 = new Stack();
  Covered5 = new Stack();
  Covered6 = new Stack();

  HolderArray = new Stack[NUM_SUITS];
  StackArray = new Stack[STACKS];

  StackArray[0] = new Stack();
  StackArray[1] = new Stack();
  StackArray[2] = new Stack();
  StackArray[3] = new Stack();
  StackArray[4] = new Stack();
  StackArray[5] = new Stack();
  StackArray[6] = new Stack();

  HolderArray[0] = new Stack();
  HolderArray[1] = new Stack();
  HolderArray[2] = new Stack();
  HolderArray[3] = new Stack();

  Pile = new Stack();
  Discard = new Vector();

  Card redealCard = new Card(REDEAL, 0);
  Pile.push(redealCard);
  for (i = 28; i < NUM_CARDS; i++) {
    cards[i].setCovered(true);
    Pile.push(cards[i]);
  }


  for (i = 7; i < 28; i++) {
    cards[i].setCovered(true);
  }



/*
  for (i = 33; i < NUM_CARDS; i++) {
    Discard.add(cards[i]);
  }
*/

  HolderArray[0].push(new Card(SPADE, 0));
  HolderArray[1].push(new Card(CLUB, 0));
  HolderArray[2].push(new Card(DIAMOND, 0));
  HolderArray[3].push(new Card(HEART, 0));


  StackArray[0].push(cards[0]);


  StackArray[1].push(cards[7]);
  StackArray[1].push(cards[1]);

  StackArray[2].push(cards[8]);
  StackArray[2].push(cards[9]);
  StackArray[2].push(cards[2]);

  StackArray[3].push(cards[10]);
  StackArray[3].push(cards[11]);
  StackArray[3].push(cards[12]);
  StackArray[3].push(cards[3]);

  StackArray[4].push(cards[13]);
  StackArray[4].push(cards[14]);
  StackArray[4].push(cards[15]);
  StackArray[4].push(cards[16]);
  StackArray[4].push(cards[4]);

  StackArray[5].push(cards[17]);
  StackArray[5].push(cards[18]);
  StackArray[5].push(cards[19]);
  StackArray[5].push(cards[20]);
  StackArray[5].push(cards[21]);
  StackArray[5].push(cards[5]);

  StackArray[6].push(cards[22]);
  StackArray[6].push(cards[23]);
  StackArray[6].push(cards[24]);
  StackArray[6].push(cards[25]);
  StackArray[6].push(cards[26]);
  StackArray[6].push(cards[27]);
  StackArray[6].push(cards[6]);

}

public Card[] shuffle() {
  int i;
  int[] indexPositions = new int[NUM_CARDS];
  int[] usedNumbers = new int[NUM_CARDS];
  int iQuestionNum;

  Card[] cardPosition = new Card[NUM_CARDS];
  int iCardNumber;

  for (i = 0; i < NUM_CARDS; i++) {
    usedNumbers[i] = 0; //I wonder if this loop will be unrolled..
  }

  for (i = 0; i < NUM_CARDS; i++) {
    iQuestionNum = (int) (NUM_CARDS * Math.random());
    if (usedNumbers[iQuestionNum] == 0) {
      indexPositions[i] = iQuestionNum;
      usedNumbers[iQuestionNum] = 1;
    } else {
      while (usedNumbers[iQuestionNum] != 0) {
        iQuestionNum++;
        if (iQuestionNum >= NUM_CARDS) {
          iQuestionNum = 0;
        }
      }
      indexPositions[i] = iQuestionNum;
      usedNumbers[iQuestionNum] = 1;
    }
  }


  for (i = 0; i < NUM_CARDS; i++) {
    iCardNumber = (indexPositions[i] % CARDS_IN_SUIT) + 1;

    if((indexPositions[i] / CARDS_IN_SUIT) == 0) {
      cardPosition[i] = new Card(SPADE, iCardNumber);
    } else if((indexPositions[i] / CARDS_IN_SUIT) == 1) {
      cardPosition[i] = new Card(CLUB, iCardNumber);
    } else if((indexPositions[i] / CARDS_IN_SUIT) == 2) {
      cardPosition[i] = new Card(DIAMOND, iCardNumber);
    } else if((indexPositions[i] / CARDS_IN_SUIT) == 3) {
      cardPosition[i] = new Card(HEART, iCardNumber);
    }

  }

  return cardPosition;


}


public void render(Graphics g) {
  int i;
  int j;
  Point iCardLoc = new Point(STACK_LOC);

  cls(g);
  for (j = 0; j < STACKS; j++) {
    iCardLoc.setLocation((j * (SPACING + CARD_SIZE.width) + SPACING), STACK_LOC.y);
    if (StackArray[j].isEmpty() == false) {
      for (i = 0; i < StackArray[j].size(); i++) {
        ((Card) StackArray[j].elementAt(i)).setLocation(iCardLoc);

        if ((i == (StackArray[j].size() - 1))) {
          ((Card) StackArray[j].elementAt(i)).setClickableArea(CARD_SIZE.width, CARD_SIZE.height);
        } else if (((Card) StackArray[j].elementAt(i)).isCovered()) {
          ((Card) StackArray[j].elementAt(i)).setClickableArea(0, 0);
        } else {
          ((Card) StackArray[j].elementAt(i)).setClickableArea(CARD_SIZE.width, CARD_SPACING);
        }

          ((Card) StackArray[j].elementAt(i)).drawCard(g, iCardLoc);

          if (((Card) StackArray[j].elementAt(i)).getNumber() == 14) {
            iCardLoc.translate(0, 0);
          } else if (!((Card) StackArray[j].elementAt(i)).isCovered()) {
            iCardLoc.translate(0, CARD_SPACING);
          } else {
            iCardLoc.translate(0, TINY_SPACING);
          }
      }
    }
  }

  //draw the holder stacks

  for (j = 0; j < NUM_SUITS; j++) {
    iCardLoc.setLocation((j * (SPACING + CARD_SIZE.width) + HOLDER_XOFFSET), HOLDER_YOFFSET);

    for (i = 0; i < HolderArray[j].size(); i++) {
      ((Card) HolderArray[j].elementAt(i)).setLocation(iCardLoc);
      ((Card) HolderArray[j].elementAt(i)).setClickableArea(CARD_SIZE.width, CARD_SIZE.height);
      ((Card) HolderArray[j].elementAt(i)).drawCard(g, iCardLoc);
      iCardLoc.translate(0, TINY_SPACING);            

    }
  }


  //draw the pile
  iCardLoc.setLocation(PILE_LOC.x, PILE_LOC.y);
  
  for (i = 0; i < Pile.size(); i++) {
      ((Card) Pile.elementAt(i)).setLocation(iCardLoc);
      ((Card) Pile.elementAt(i)).setClickableArea(CARD_SIZE.width, CARD_SIZE.height);
      ((Card) Pile.elementAt(i)).drawCard(g, iCardLoc);
      iCardLoc.translate(TINY_SPACING, 0);   
  }


  //the discard pile

  iCardLoc.setLocation((2 * CARD_SIZE.width), PILE_LOC.y);
  
  for (i = 0; i < Discard.size(); i++) {
      ((Card) Discard.elementAt(i)).setLocation(iCardLoc);
      ((Card) Discard.elementAt(i)).setClickableArea(CARD_SIZE.width, CARD_SIZE.height);
      ((Card) Discard.elementAt(i)).drawCard(g, iCardLoc);
      iCardLoc.translate(TINY_SPACING, 0);   
  }




//  g.drawString((MouseP.x + ", " + MouseP.y), 200, 200);
  
  if (hasStickyCard) {
    MouseP.translate(-(CARD_SIZE.width / 2), -(CARD_SIZE.height / 2));

    for (i = 0; i <= (stickyCard.size() - 1); i++) {
      MouseP.translate(0, CARD_SPACING);
      ((Card) stickyCard.elementAt(i)).drawCard(g, MouseP);
    }
  }



}



public void mouseClicked(MouseEvent e) {
  int i, j, k;
  Card playedCard;
  boolean hasPlayed;
  MouseP.setLocation(e.getPoint());

  if (!hasStickyCard) {

    if (!(Pile.isEmpty()) && (((Card) Pile.peek()).isClicked(e.getPoint()))) {
      playedCard = (Card) Pile.pop();
      playedCard.setCovered(false);
      Discard.addElement(playedCard);
      if (playedCard.getNumber() == 0) {

        Pile.push(Discard.elementAt((Discard.size() - 1))); //leave the redeal card showing
        for (i = (Discard.size() - 2); i >= 0 ; i--) {
          ((Card) Discard.elementAt(i)).setCovered(true);
          Pile.push(Discard.elementAt(i));
        }


        Discard.removeAllElements();
      }
      repaint();

    } else if (!(Discard.isEmpty()) && ((Card) Discard.elementAt((Discard.size() - 1))).isClicked(e.getPoint())) {

      stickyCard = new Vector();

      stickyCard.addElement(Discard.elementAt(Discard.size() - 1));
      Discard.removeElementAt((Discard.size() - 1));

      hasStickyCard = true;
      hasPileCard = true;
      setCursor(new Cursor(Cursor.HAND_CURSOR));

    } else {

      for(i = 0; i < STACKS; i++) {
        for (j = 0; j < StackArray[i].size(); j++) {
      
          if (((Card) StackArray[i].elementAt(j)).isClicked(e.getPoint()) && (((Card) StackArray[i].elementAt(j)).getNumber() != 14) ) {
          //the user clicked on this card

            if (((Card) StackArray[i].elementAt(j)).isCovered() && ((StackArray[i].size() - 1) == j)) {
              ((Card) StackArray[i].elementAt(j)).setCovered(false);
              repaint();
            } else {

              stickyCard = new Vector();
              for (k = j; k < StackArray[i].size(); k++) {

//              System.out.println("Adding: " + k);
                stickyCard.addElement(StackArray[i].elementAt(k));
              }
              //System.out.println((Card) StackArray[i].elementAt(j));
    
              iStickyCardStack = i;


              //pop all of the cards off the stack until you get the sticky
              //card
              while (((Card) StackArray[iStickyCardStack].peek()) != ((Card) stickyCard.elementAt(0))) {
                StackArray[iStickyCardStack].pop();
              }
              StackArray[iStickyCardStack].pop();

              if (StackArray[iStickyCardStack].isEmpty()) {
                Card placeHolder = new Card(WILD, 14);
                placeHolder.setClickableArea(0, 0);
                StackArray[iStickyCardStack].push(placeHolder);
              }
              
              hasStickyCard = true;
              setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
          }
        }
      }

    }


  } else if (hasStickyCard) {
    hasStickyCard = false;
    hasPlayed = false;

    for(i = 0; i < STACKS; i++) {
      if (!(StackArray[i].isEmpty())) {
        //if the card that you placed your stack on is one less than the
        //top card in the stack you're holding, the push all of the
        //cards in your hand on that stack

        //playedCard is the card being checked for a click right now
        playedCard = (Card) StackArray[i].elementAt((StackArray[i].size() - 1));

        if (playedCard.isClicked(e.getPoint())) {
          if ((playedCard.getNumber() - 1) == (((Card) stickyCard.elementAt(0)).getNumber())
              && (playedCard.isRed() && ((Card) stickyCard.elementAt(0)).isBlack() ||
                  playedCard.isBlack() && ((Card) stickyCard.elementAt(0)).isRed() )
              ) {
            hasPlayed = true;
            for (j = 0; j < stickyCard.size(); j++) {
              StackArray[i].push((Card) stickyCard.elementAt(j));
            }

          }
        }
      }
    }

    //no need to check the upper holders if the cards have already been
    //played
    //also, you can only play one card in the holders at a time.
    if (!hasPlayed && (stickyCard.size() == 1)) {
      for(i = 0; i < NUM_SUITS; i++) {
          playedCard = (Card) HolderArray[i].elementAt((HolderArray[i].size() - 1));
          if (playedCard.isClicked(e.getPoint()) &&
             ((playedCard.getNumber() + 1) == ((Card) stickyCard.elementAt(0)).getNumber()) &&
             (playedCard.getSuit().equals( ((Card) stickyCard.elementAt(0)).getSuit()))
          ) {
            HolderArray[i].push(stickyCard.elementAt(0));
            hasPlayed = true;
          }
      }
    }

    


    if (!hasPlayed) {
    //return all of the cards back to the original stack
      if (hasPileCard) {
        Discard.insertElementAt(stickyCard.elementAt(0), Discard.size());
      } else {
        for (i = 0; i < stickyCard.size(); i++) {
          StackArray[iStickyCardStack].push(stickyCard.elementAt(i));
        }
      }
    }

    hasPileCard = false;
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    repaint();

  }

}
public void mouseEntered(MouseEvent e) {
}
public void mouseExited(MouseEvent e) {
}
public void mousePressed(MouseEvent e) {
}
public void mouseReleased(MouseEvent e) {
}

public void mouseDragged(MouseEvent e) {
}
public void mouseMoved(MouseEvent e) {
  if (hasStickyCard) {
    MouseP.setLocation(e.getPoint());
    repaint();
  }
}


public void paint (Graphics g) {
  update(g);
}

public void cls(Graphics g) {
  g.setColor(MY_GREEN);
  g.fillRect(0, 0, getBounds().width, getBounds().height);
}

public void update(Graphics g) {
  if (r.width != getBounds().width || r.height != getBounds().height) {
    bufferImage = createImage(getBounds().width, getBounds().height);
    bufferGraphics = bufferImage.getGraphics();
    r = getBounds();
    damage = true;

  }

  render(bufferGraphics);
  damage = false;
  g.drawImage(bufferImage, 0, 0, this);

}

}
