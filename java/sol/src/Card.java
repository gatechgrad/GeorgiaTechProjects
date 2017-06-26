import java.awt.*;
import java.net.URL;

class Card {

public static Dimension CARD_SIZE = new Dimension(65, 90);
public static int IMAGE_SIZE = 30;
public static int SM_IMAGE_SIZE = 10;
//public static final String THIS_DIRECTORY = "C:\\sol\\";
public static final String THIS_DIRECTORY = "http://www.prism.gatech.edu/~gte187k/sol/";

private Image SPADE_PIC;
private Image CLUB_PIC;
private Image DIAMOND_PIC;
private Image HEART_PIC;

private int iNumber;
private String Suit;
private Point Location;
private Dimension ClickableArea;
private boolean isCovered;
private Image SuitPic;


public Card(String Suit, int iNumber) {
  Location = new Point(0,0);  
  this.Suit = Suit;
  this.iNumber = iNumber;
  isCovered = false;


try {
  SPADE_PIC = Toolkit.getDefaultToolkit().getImage(new URL(THIS_DIRECTORY + "spade.gif"));
  CLUB_PIC = Toolkit.getDefaultToolkit().getImage(new URL(THIS_DIRECTORY + "club.gif"));
  DIAMOND_PIC = Toolkit.getDefaultToolkit().getImage(new URL(THIS_DIRECTORY + "diamond.gif"));
  HEART_PIC = Toolkit.getDefaultToolkit().getImage(new URL(THIS_DIRECTORY + "heart.gif"));
} catch (Exception e) {
  System.out.println(e);
};



  if (Suit.equals("SPADE")) {
    SuitPic = SPADE_PIC;
  } else if (Suit.equals("CLUB")) {
    SuitPic = CLUB_PIC;
  } else if (Suit.equals("DIAMOND")) {
    SuitPic = DIAMOND_PIC;
  } else if (Suit.equals("HEART")) {
    SuitPic = HEART_PIC;
  } else {
    SuitPic = HEART_PIC;
  }
}


public String getSuit() {
  return Suit;
}

public int getNumber() {
  return iNumber;
}

public String toString() {
  return("Suit: " + Suit + "\nNumber: " + iNumber);

}


public boolean isRed() {
  if (Suit.equals("DIAMOND") || Suit.equals("HEART") || Suit.equals("WILD")) {
    return true;
  } else {
    return false;
  }
}

public boolean isBlack() {
  if (Suit.equals("SPADE") || Suit.equals("CLUB") || Suit.equals("WILD")) {
    return true;
  } else {
    return false;
  }
}

public void setClickableArea(int width, int height) {
  ClickableArea = new Dimension(width, height);
}

public void setLocation(Point p) {
  Location.setLocation(p);
}


public boolean isClicked(Point p) {
  if ( (p.x > Location.x) && (p.y > Location.y) && (p.x < (Location.x + ClickableArea.width)) && (p.y < (Location.y + ClickableArea.height))) {
//    System.out.println("Suit: " + Suit + "\nNumber: " + iNumber + "was clicked\n");
    return true;
  } else {
    return false;
  }
}

public void setCovered(boolean b) {
  isCovered = b;
}

public boolean isCovered() {
  return isCovered;
}

public void drawCard(Graphics g, Point p) {
  String alphaNum;


  if (isCovered()) {
    g.setColor(Color.red);
    g.fillRect(p.x, p.y, CARD_SIZE.width, CARD_SIZE.height);
    g.setColor(Color.black);
    g.drawRect(p.x, p.y, CARD_SIZE.width, CARD_SIZE.height);

  } else if (getNumber() == 14) {
    //do nothing... it's a blank space

  } else {

    g.setColor(Color.white);
    g.fillRect(p.x, p.y, CARD_SIZE.width, CARD_SIZE.height);
    g.setColor(Color.black);
    g.drawRect(p.x, p.y, CARD_SIZE.width, CARD_SIZE.height);

    if (isRed()) {
      g.setColor(Color.red);
    }  else if (isBlack()) {
      g.setColor(Color.black);
    } else {
      g.setColor(Color.black);
    }

    g.drawImage(SuitPic, (p.x + 5), (p.y + 16), SM_IMAGE_SIZE, SM_IMAGE_SIZE, null);
    g.drawImage(SuitPic, (p.x + CARD_SIZE.width - SM_IMAGE_SIZE - 10), (p.y + CARD_SIZE.height - SM_IMAGE_SIZE - 16), SM_IMAGE_SIZE, SM_IMAGE_SIZE, null);


    if ((getNumber() >= 2) && (getNumber() <= 10)) {
      alphaNum = "" + getNumber();
    } else if (getNumber() == 1) {
      alphaNum = "A";
      g.drawImage(SuitPic, (p.x + (CARD_SIZE.width / 2) - (IMAGE_SIZE / 2) ), (p.y + (CARD_SIZE.height / 2) - (IMAGE_SIZE / 2) ), IMAGE_SIZE, IMAGE_SIZE, null);
    } else if (getNumber() == 11) {
      alphaNum = "J";
    } else if (getNumber() == 12) {
      alphaNum = "Q";

    } else if (getNumber() == 13) {
      alphaNum = "K";
    } else if (getNumber() == 0) {
      alphaNum = "";
    } else {
      alphaNum = "Error";
    }

    g.setFont(new Font("Dialog", Font.BOLD, 12));
    g.drawString(alphaNum, (p.x + 5), (p.y + 15));
    g.drawString(alphaNum, (p.x - 20 + CARD_SIZE.width), (p.y - 5 + CARD_SIZE.height));

//    g.drawString(getSuit(), (p.x + 20), (p.y + 40));

  }
}


}
