import java.awt.*;
import java.awt.event.*;

class StatusBox extends Canvas implements MouseListener, MouseMotionListener {
  /************************ CONSTANTS *************************/
  public static final Rectangle DIE = new Rectangle(20, 370, 50, 50);

  public static final int TOTAL_TIME = 60;


  /************************ CLASS VARIABLES *************************/

  private String strCurrentMessage; //String of message to be displayed within
                                    //the StatusBox display.  CurrentMessage
                                    //is sent from the parent Game object as
                                    //changes in status of the game are made
  private Question theQuestion;  //Question instance to be displayed within
                                 //the StatusBox display.  Question is
                                 //sent from the parent Game object as a player
                                 //moves into a new space.

  private int iDieDisplay; //what the die shows
  Mindtrial MTMainGame;

  //for drawing purposes
  private Image
    ImgBackground, ImgDie1, ImgDie2, ImgDie3, ImgDie4, ImgDie5, ImgDie6, ImgDieRoll,
    ImgCorrect, ImgIncorrect;


  private boolean printCorrect = false;
  private boolean printIncorrect = false;
  private boolean hasWon = false;

  private MediaTracker theMediaTracker;

  //variables for double buffering
  private boolean damage = true;
  private Image  bufferImage = null;
  private Graphics bufferGraphics = null;


  /************************ CLASS METHODS *************************/

  /**
    * StatusBox - constructor
    **/
  public StatusBox(Mindtrial MTMainGame) {
    this.setSize(160, 480); //short on time, make constant later

    this.MTMainGame = MTMainGame;
    iDieDisplay = 0;
    hasWon = false;

    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    theMediaTracker = new MediaTracker(this);
    ImgBackground = Toolkit.getDefaultToolkit().getImage("statusboxbkg.gif");
    ImgDie1 = Toolkit.getDefaultToolkit().getImage("die1.gif");
    ImgDie2 = Toolkit.getDefaultToolkit().getImage("die2.gif");
    ImgDie3 = Toolkit.getDefaultToolkit().getImage("die3.gif");
    ImgDie4 = Toolkit.getDefaultToolkit().getImage("die4.gif");
    ImgDie5 = Toolkit.getDefaultToolkit().getImage("die5.gif");
    ImgDie6 = Toolkit.getDefaultToolkit().getImage("die6.gif");
    ImgDieRoll = Toolkit.getDefaultToolkit().getImage("dieroll.gif");
    ImgCorrect = Toolkit.getDefaultToolkit().getImage("correct.gif");
    ImgIncorrect = Toolkit.getDefaultToolkit().getImage("incorrect.gif");



    theMediaTracker.addImage(ImgBackground, 0);
    theMediaTracker.addImage(ImgDie1, 0);
    theMediaTracker.addImage(ImgDie2, 0);
    theMediaTracker.addImage(ImgDie3, 0);
    theMediaTracker.addImage(ImgDie4, 0);
    theMediaTracker.addImage(ImgDie5, 0);
    theMediaTracker.addImage(ImgDie6, 0);
    theMediaTracker.addImage(ImgDieRoll, 0);
    theMediaTracker.addImage(ImgCorrect, 0);
    theMediaTracker.addImage(ImgIncorrect, 0);

    try {
      theMediaTracker.waitForAll();
    } catch (InterruptedException e) {}

    theQuestion = null;
  }


  /**
    * displayTimer - displays the timer while the Player is answering
    *                  the question
    *              - decrements the time every second until it reaches 0;
    *                  then the question is considered wrong
    **/
  private void displayTimer(Graphics g) {
    int i;

    i = TOTAL_TIME;


  }

  /**
    * exit - ends the game and exites the program; call Mindtrial.end()
    **/
  private void exit() {

  }

  /**
    * initialize - creates and displays the StatusBox
    *            - displays elements of StatsBox, picture of gamepiece of
    *                the current player's turn with text saying whose turn
    *                it is, quesiton box which will simply saw "Welcome to
    *                Mindtrial" until a question has been chosen, a clickable
    *                dice, and buttons for ending and restarting the game
    **/
  private void initialize() {

  }

  /**
    * listenForResults - waits for the user to select an answer from the
    *                    given choices
    *                  - uses Question
    *                  - once the quesiton has been displayed, listen for a
    *                      mouse click slectinga choice and return that
    *                      String; also listen for exit and newGame buttons
    *                      being pressed
    * @return String
    **/
  private String listenForResults() {

    return "";
  }

  /**
    * newGame - restarts the game
    *         - close all displays and create a new instance of Menu, call
    *             Game.end()
    **/
  private void newGame() {

  }

  /**
    * rollDice - waits for the user to click on the dice icon
    *        - returns true when it is clicked; otherwise continue waiting
    **/
  private boolean rollDice() {

    return false;
  }


  /**
    * paint -
    **/
  public void paint(Graphics g) {
    update(g);

  }

  /**
    * updateDisplay -
    **/
  public void update(Graphics g) {

    bufferImage = createImage(getBounds().width, getBounds().height);
    bufferGraphics = bufferImage.getGraphics();
    damage = true;

    drawStatusBox(bufferGraphics);
    damage = false;
    g.drawImage(bufferImage, 0, 0, this);


  }


  /**
    * drawStatusBox - this does the main drawing of the StatusBox
    **/
  private void drawStatusBox(Graphics g) {
    g.drawImage(ImgBackground, 0, 0, 160, 480, this);


    g.drawString("It is " + MTMainGame.getCurrentPlayer().getName() + "'s turn", 20, 40);
    //also need to write name, draw die and display question and answer

    switch(iDieDisplay) {
      case 0:
        g.drawImage(ImgDieRoll, DIE.x, DIE.y, DIE.width, DIE.height, this);
        break;
      case 1:
        g.drawImage(ImgDie1, DIE.x, DIE.y, DIE.width, DIE.height, this);
        break;
      case 2:
        g.drawImage(ImgDie2, DIE.x, DIE.y, DIE.width, DIE.height, this);
        break;
      case 3:
        g.drawImage(ImgDie3, DIE.x, DIE.y, DIE.width, DIE.height, this);
        break;
      case 4:
        g.drawImage(ImgDie4, DIE.x, DIE.y, DIE.width, DIE.height, this);
        break;
      case 5:
        g.drawImage(ImgDie5, DIE.x, DIE.y, DIE.width, DIE.height, this);
        break;
      case 6:
        g.drawImage(ImgDie6, DIE.x, DIE.y, DIE.width, DIE.height, this);
        break;
      default:

    }

    if (theQuestion != null) {
      theQuestion.displayQuestion(g, 20, 60);

    }

    if (printCorrect) {
      g.drawImage(ImgCorrect, 30, 300, 100, 60, this);
      printCorrect = false;
    }

    if (printIncorrect) {
      g.drawImage(ImgIncorrect, 30, 300, 100, 60, this);
      printIncorrect = false;
    }

    if (hasWon) {
      g.drawString(((((Player)MTMainGame.getCurrentPlayer()).getName()) + " has obtained all six"), 30, 70);
      g.drawString("wedges and won the game", 30, 100);
      
      g.drawString("Click for a new Game", 30, 150);
      if(Mindtrial.DEBUG == 1) {
        System.out.println("Won Game");
      
      
      }
    }

  }



  /**
    * setDieDisplay - sets the number the die shows
    **/
  public void setDieDisplay(int i) {
    iDieDisplay = i;
  }


  /**
    * getDieDisplay - returns die value
    **/
  public int getDieDisplay() {
   return iDieDisplay;
  }

  /**
    * setQuestion -sets a Question to be displayed
    **/
  public void setQuestion(Question theQuestion) {
    this.theQuestion = theQuestion;
  }

  /**
    *  testAnswer
    **/
  private void testAnswer(int x, int y) {
    int iWedge;
    boolean answerIsCorrect = false;
    boolean foundAnswer = false;

    if (Question.ANSWER1.contains(x, y))  {
      if (theQuestion.checkAnswer(theQuestion.getAnswers()[0])) {
        answerIsCorrect = true;
      } else {
        answerIsCorrect = false;
      }
      foundAnswer = true;
    }
    if (Question.ANSWER2.contains(x, y))  {
      if (theQuestion.checkAnswer(theQuestion.getAnswers()[1])) {
        answerIsCorrect = true;
      } else {
        answerIsCorrect = false;
      }
      foundAnswer = true;
    }
    if (Question.ANSWER3.contains(x, y))  {
      if (theQuestion.checkAnswer(theQuestion.getAnswers()[2])) {
        answerIsCorrect = true;
      } else {
        answerIsCorrect = false;
      }
      foundAnswer = true;
    }
    if (Question.ANSWER4.contains(x, y))  {
      if (theQuestion.checkAnswer(theQuestion.getAnswers()[3])) {
        answerIsCorrect = true;
      } else {
        answerIsCorrect = false;
      }
      foundAnswer = true;
    }
    if (Question.ANSWER5.contains(x, y))  {
      if (theQuestion.checkAnswer(theQuestion.getAnswers()[4])) {
        answerIsCorrect = true;
      } else {
        answerIsCorrect = false;
      }
      foundAnswer = true;
    }



    if (foundAnswer) {
      //reset everything
      theQuestion = null;

      if (answerIsCorrect) {
        printCorrect = true;
        //System.out.println("Correct");

        iWedge = MTMainGame.getBoard().getSpace(MTMainGame.getCurrentPlayer().getLocation()).getWedgeValue();
        if (iWedge > 0) {
               MTMainGame.getCurrentPlayer().setWedge(iWedge);
               MTMainGame.getBoard().repaint();
               hasWon = MTMainGame.getCurrentPlayer().hasWon();
        }

      } else {
        //System.out.println("Incorrect");
        printIncorrect = true;
        MTMainGame.setNextPlayer();

      }
      iDieDisplay = 0;
      repaint();

    }
  }



  /**
    * mouseMoved
    * mouseDragged
    **/
  public void mouseMoved(MouseEvent e) {
    int x, y;

    x = e.getX();
    y = e.getY();

    if (iDieDisplay != 0 ) {
      if (Question.ANSWER1.contains(x,y) ||
          Question.ANSWER2.contains(x,y) ||
          Question.ANSWER3.contains(x,y) ||
          Question.ANSWER4.contains(x,y) ||
          Question.ANSWER5.contains(x,y)) {

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
      }  else {
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }

    if (theQuestion == null) {
      if (DIE.contains(x,y)) {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
      }  else {
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }

    }
  }
  public void mouseDragged(MouseEvent e) {
  }

  /**
    * mouseClicked
    * mouseEntered
    * mouseExited
    * mousePressed
    * mouseReleased
    **/
  public void mouseClicked(MouseEvent e) {
  }
  public void mouseEntered(MouseEvent e) {
  }
  public void mouseExited(MouseEvent e) {
  }
  public void mousePressed(MouseEvent e) {
  }
  public void mouseReleased(MouseEvent e) {
    int x, y;
    if ((Mindtrial.DEBUG == 2) || (Mindtrial.DEBUG == 1)) {System.out.println("Mouse Clicked");}

    x = e.getX();
    y = e.getY();


    if (hasWon) {
      new Mindtrial();
      MTMainGame.getGameBoard().shutWindow();
     
    }

    if ((DIE.contains(x, y)) &&
        (iDieDisplay == 0)) {
      iDieDisplay = MTMainGame.rollDice();
      repaint();
      MTMainGame.getBoard().setClickable(true);

    } 


    if (theQuestion != null) {
      testAnswer(x, y);
    }



  }



}
