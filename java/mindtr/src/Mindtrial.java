import java.util.Vector;
import java.util.StringTokenizer;
import java.io.*;
import java.util.Random;

class Mindtrial {
  /************************ CONSTANTS *************************/
  public static final int DEBUG = 8;  //DEBUG = 1 means show all debugging
                                      //messages
                                      //other numbers can be used to display
                                      //specific messages
                                      //DEBUG = 0 means no debugging comments
  public static final String QUESTION_FILE = "QPack.mt"; //question file
  public static final int DIE_SIDES = 6;
  public static final int NUM_CATEGORIES = 6; // number of categories
  public static final String MT_ICON = "icon.gif"; //game icon
  public static final String TITLE = "Mind Trial"; //pretty obvious

  /************************ CLASS VARIABLES *************************/
  private Vector vectPlayers;
  private Board theBoard;
  private StatusBox theStatusBox;
  private int iWhosTurn;
  private Vector theQuestions;
  private boolean moveIsOkay;
  private GameBoard theGameBoard;
	private boolean endGame;
  /************************ CLASS METHODS *************************/

  /**
    * Mindtrial - constructor
    **/
  public Mindtrial() {
    initialize();
  }

  /**
    * askQuestion - handles displaying the question and checking the answer
    *             - communicates data from Question to StatusBox.  Question
    *                 to be displayed randomly selects a question from the
    *                 Questions list and test that the difficulty and
    *                 category are correct;  if they are, it displays the
    *                 question on StatusBox with the user input from
    *                 ListenForResults() and checks the answer; if it is
    *                 correct, return true; else false
    * @return boolean
    * @param int iDifficulty
    * @param int strCategory
    **/
  private void askQuestion(int iDifficulty, String strCategory) {
   boolean questionIsFound;
   Question theQuestionToAsk;
   int i;
		endGame = false;

   theQuestionToAsk = null;
   questionIsFound = false;


   i = 0;
   while((questionIsFound == false) && i < (theQuestions.size())) {
     if ( ((Question)theQuestions.elementAt(i)).getCategory().equals(strCategory)) {
       if ((Mindtrial.DEBUG == 8) || (Mindtrial.DEBUG == 1)) {System.out.println("Found a " + strCategory + " question");}
       theQuestionToAsk = (Question) theQuestions.elementAt(i);
       questionIsFound = true;
       theQuestions.removeElement((Object) theQuestionToAsk);
       theQuestions.addElement(theQuestionToAsk);
     }

     i++;
   }


     theStatusBox.setQuestion(theQuestionToAsk);
     theStatusBox.repaint();
     theBoard.repaint();

  }

  /**
    * end - ends the game
    *     - makes sure everthing is terminated correctly and game control
    *       is stopped
    **/
  public void end() {
    System.exit(0);
		endGame = true;
			P6Toolkit.pause();
  }

  /**
    * initalize - i	nitalizes all the data for the game
    *           - takes in the Players array and initializes Menu, Board,
    *               StatusBox, and Questions
    *           - creates Board and StatusBox objects; Board will display on
    *               the left three-quarters of the screen and StatusBox on the
    *               right; link the objects in the Players array
    *           - initializes all questions
    **/
  private void initialize() {
    //get questions from file
    StringBuffer myBuffer = new StringBuffer();
    String strQuestions;
    int iChar;
    int i;

    theBoard = new Board(this);
    theStatusBox = new StatusBox(this);

    String strQuestionType;
    String strQuestionMessage;
    String strCorrectAnswer;
    String[] arrAnswerChoices; //must include correctAnswer!!

    theQuestions = new Vector();

    //make the menu first
    Menu theMenu = new Menu(this);
    vectPlayers = theMenu.getPlayers();


    //read in the question and answer file
    try {
      FileReader fileToRead = new FileReader(QUESTION_FILE);


      //read all of the characters from the file and put them into the
      //StringBuffer, myBuffer
      iChar = fileToRead.read();
      while(iChar != -1) {
      myBuffer.append((char) iChar);
        iChar = fileToRead.read();
      }

      fileToRead.close();

    } catch (IOException e) { }


    strQuestions = myBuffer.toString();
    if ((Mindtrial.DEBUG == 2) || (Mindtrial.DEBUG == 1)) {System.out.println(strQuestions);}

    //now parse the information... thanks Sun for StringTokenizer!! 
    //tokenLine needs to be parsed into separate lines of information,
    //where each line contains all information for a question
    StringTokenizer tokenLine = new StringTokenizer(strQuestions, "\n");



    while(tokenLine.hasMoreTokens()) {

      StringTokenizer tokenString = new StringTokenizer(tokenLine.nextToken(), "|");


      // store tokens as strings or in the answer choices vector
      // the question category must be the first token
      // the question message must be the second token
      // the correct answer must be the thrid token
      // the remaining tokens are the incorrect answers including one correct
      //   answer



      //check to see if there are the right number of tokens on the line
      //3 comes from 1 token for type, 1 token for message, and 1 token for correct answer
      if (tokenString.countTokens() == (Question.NUM_ANSWERS + 3)) {
        //get tokens
        strQuestionType = tokenString.nextToken();
        strQuestionMessage = tokenString.nextToken();
        strCorrectAnswer = tokenString.nextToken();

        arrAnswerChoices = new String[Question.NUM_ANSWERS];
        for (i = 0; i < (Question.NUM_ANSWERS); i++) {
          arrAnswerChoices[i] = tokenString.nextToken().trim();
        }

        //make a new instance of Question with this data
        Question tempQuestion =
             new Question(strQuestionType, strQuestionMessage,
                          strCorrectAnswer, arrAnswerChoices);

        theQuestions.addElement(tempQuestion);                       
        if ((Mindtrial.DEBUG == 3) || (Mindtrial.DEBUG == 1)) {System.out.println(tempQuestion);}

      }
    }

  }

  /**
    * movePiece - tests if the target Space is the correct distance from the
    *               current Space
    *           - uses current.AdjacentSpaces to iterate through the path
    *               and sets it ot next
    *           - starts with current and iteratively calls
    *               MovePiece(roll - 1, next, target); do this for each
    *               element of current.AdjacentSpaces until the return value
    *               is not equal to null; if roll == 0 and current == target,
    *               return target;  if roll == 0 and current is not equal to
    *               target, return null
    * @param int roll
    * @param Space currentSpace
    * @param Space targetSpace
    */
  public void movePiece(Space targetSpace) {
    int iRoll;
    int iPreviousLocation;
    int i;
    Space currentSpace;
    moveIsOkay = false;

    iRoll = theStatusBox.getDieDisplay();
    currentSpace = theBoard.getSpace(getCurrentPlayer().getLocation());

    if ((Mindtrial.DEBUG == 7) || (Mindtrial.DEBUG == 1)) {System.out.println("currentSpace == " + currentSpace.getLocation());}
    if ((Mindtrial.DEBUG == 7) || (Mindtrial.DEBUG == 1)) {System.out.println("currentSpace == " + targetSpace.getLocation());}


    if (false) {

    } else {
      getCurrentPlayer().setLocation(targetSpace.getLocation());
      askQuestion(getCurrentPlayer().getDifficulty(), targetSpace.getCategory());
      theBoard.setClickable(false);
    }



  }







  /**
    * rollDice - gives the dice roll
    *          - uses a random function
    *          - waits for StatusBox.RollDice()
    *          - returns an random integer from 1 to 6
    * @return int
    **/
  public int rollDice() {
    int iRandom = 1;

    Random aRandom = new Random();

      iRandom = (Math.abs(aRandom.nextInt()) % DIE_SIDES) + 1;

      if ((Mindtrial.DEBUG == 4) || (Mindtrial.DEBUG == 1)) {System.out.println("Die reads: " + iRandom);}


    return iRandom;
  }

  /**
    * runGame - does the main work of the game
    *         - if F1 is clicked at any time calls Menu.showHelp()
    *         - iterates through Players using WhosTurn; takes data from
    *             Board and Questions and updates StatusBox
    *         - for each play, calls rollDice(), movePiece(), and
    *             askQuestion(); movePiece() sets target with the active
    *             Space from Board; then calls Board.  UpdateDisplay(Players);
    *             if askQuestion() returns false, go to the next Player; if
    *             askQuesiton() reutrns true target.PieValue; if PieValue is 
    *             not equal to zero, set 
    *             Players[WhosTurn].wedgesEarned[PieValue] to be true
    **/
  public void runGame() {
    int iCurrentsPlayerIndex;
    theGameBoard = new GameBoard(this);  
    //playBGMusic();
    


  }
  
  
 /* public void playBGMusic(){
		try {
			P6Toolkit.play('B', 4, false);
				P6Toolkit.pause();
			
		}//end try statement
		catch (Exception e) {
		}//end catch statement
	}//end playBGMusic method
  /*

  /**
    * promptGame - at game completion prompt user for another game
    */
  private void promptGame() {

  }


  /**
    * getBoard - returns the Board object
    **/
  public Board getBoard() {
    return theBoard;
  }

  /**
    * getStatusBox - returns the StatusBox object
    **/
  public StatusBox getStatusBox() {
    return theStatusBox;
  }

  /**
    * setPlayers - sets the player vector
    **/
  public void setPlayers(Vector vectPlayers) {
    this.vectPlayers = vectPlayers;
  }

  /**
    * getPlayers - returns a vector of Players, for class Board
    **/
  public Vector getPlayers() {
    return vectPlayers;
  }

  /**
    * getCurrentPlayer - returns the current player
    **/
  public Player getCurrentPlayer() {
    return ((Player) vectPlayers.elementAt(iWhosTurn));
  }

  /**
    * setNextPlayer
    **/
  public void setNextPlayer() {
    iWhosTurn++;    
    if (iWhosTurn >= vectPlayers.size()) {
      iWhosTurn = 0;
    }
  }

  /**
    * getGameBoard()
    **/
  public GameBoard getGameBoard() {
    return theGameBoard;
  }

  /**
    * main
    **/
  public static void main(String argv[]) {
    new Mindtrial();
  }

}