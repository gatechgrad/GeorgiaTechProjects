import java.awt.*;
import java.util.StringTokenizer;

class Question {

  /************************ CONSTANTS *******************************/
  public static final int NUM_ANSWERS = 5;
  public static final int NOVICE = 1;
  public static final int NORMAL = 2;
  public static final int ADVANCED = 3;
  public static final int SPACE = 12;

  public static final Rectangle ANSWER1 = new Rectangle(30, 290, 130, 10); 
  public static final Rectangle ANSWER2 = new Rectangle(30, 305, 130, 10); 
  public static final Rectangle ANSWER3 = new Rectangle(30, 320, 130, 10); 
  public static final Rectangle ANSWER4 = new Rectangle(30, 335, 130, 10); 
  public static final Rectangle ANSWER5 = new Rectangle(30, 350, 130, 10); 

  /************************ CLASS VARIABLES *************************/


  private String strQuestionMessage;  //String of question that will be displayed
                               //to the user.
  private String strQuestionType; //String of which of the six categories
                                  //this question falls under
  private String
    strAnswers[] = new String[NUM_ANSWERS]; //Array (length 5) of strings of
                                            //all possible answers to the
                                            //question
  private String strCorrectAnswer; //String of the correct answer to the
                                   //question.  CorrectAnswer matches a string
                                   //in the Answers array.
  private int iDifficulty; //Integer (1 - 3) of the difficulty level of the
                           //question.  Each integer value corresponds to
                           //either a NOVICE, NORMAL, or ADVANCED difficulty
                           //levels.
  
  /************************ CLASS METHODS *************************/

  /**
    * Question - constructor
    **/
  public Question(String strQuestionType, String strQuestionMessage, String strCorrectAnswer,
                  String[] strAnswers) {
    this.strQuestionType = strQuestionType;
    this.strQuestionMessage = strQuestionMessage;
    this.strCorrectAnswer = strCorrectAnswer;
    this.strAnswers = strAnswers;



  }

  /**
    * displayQuestion 
    *                    
    *                    
    **/
  public void displayQuestion(Graphics g, int x, int y) {
    int i, iWidth;
    String strToPrint = "";
    String strTemp = "";
    StringTokenizer STmessage = new StringTokenizer(strQuestionMessage);
    Font myFont = new Font("Helvetica", Font.PLAIN, 12);
    FontMetrics FMtemp = Toolkit.getDefaultToolkit().getFontMetrics(myFont);

    g.setFont(myFont);

    g.drawString(strQuestionType, x, y);
    g.drawLine(x, (y + 2), (x + 100), (y + 2));

    i = 2;
    iWidth = 0;

    while(STmessage.hasMoreTokens()) {
      iWidth = 0;
      while((iWidth < 130) && STmessage.hasMoreTokens()) {
        strToPrint += strTemp;
        strTemp = (STmessage.nextToken() + " ");
        iWidth = FMtemp.stringWidth(strToPrint + strTemp);

      }
      g.drawString(strToPrint, x, (y + (i * SPACE)));
      strToPrint = "";
      i++;
    }

    i--;
    g.drawString(strTemp, iWidth, (y + (i * SPACE)));


    g.drawString(strAnswers[0], 30, 300);
    g.drawString(strAnswers[1], 30, 315);
    g.drawString(strAnswers[2], 30, 330);
    g.drawString(strAnswers[3], 30, 345);
    g.drawString(strAnswers[4], 30, 360);


  }

  /**
    * getQuestion - sets the current question from a linked list
    *             - tests difficulty and strQuestionType and sets strQuestion,
    *                 strAnswers, and strCorrectAnswer
    *             - takes in a question type and difficulty and randomly
    *                 pulls a question of that type, its answers, and its
    *                 correct answer from a data file and stores them into
    *                 variables; checks hasBeenUsed() until it finds a
    *                 question that has not already been used
    * @param String strType
    * @param int iDifficulty
    **/
  private void getQuestion(String strType, int iDifficulty) {


  }


  /**
    * initialize - create new Question object
    *            - set all attributs to passed parameters
    * @param String strType
    * @param int iDifficulty
    * @param String[] strAnswers
    * @param String strCorrectAnswer
    * @param String strExplanation
    **/
  private void initalize() {

  }

  /**
    * isCorrect - tests the answer to see if it is correct
    *           - uses strCorrectAnswer
    *           - checks if answer matches strCorrectAnswer; if so, returns
    *               true;  else, returns false
    * @param String strAnswer
    * @return boolean
    **/
  private boolean isCorrect(String strAnswer) {
    if (strAnswer.equals(strCorrectAnswer)) {
      return true;
    } else {
      return false;
    }
  }

  /**
    * getCategory - returns the category
    **/
  public String getCategory() {
    return strQuestionType;
  }

  /**
    * getAnswers
    **/
  public String[] getAnswers() {
    return strAnswers;
  }

  /**
    * checkAnswer
    **/
  public boolean checkAnswer(String strChoice) {
    if (strCorrectAnswer.equals(strChoice)) {
      return true;
    } else {
      return false;
    }
  }


  /**
    * toString - you can't pass CS1502 without knowing what this does
    **/
  public String toString() {
    String strToReturn;
    int i;

    strToReturn = ("*****************************************************\n" +
                   "* Type: " + strQuestionType + "\n" +
                   "* Message: " + strQuestionMessage + "\n" +
                   "* Correct Answer " + strCorrectAnswer + "\n");
    for (i = 0; i < NUM_ANSWERS; i++) {
      strToReturn = (strToReturn + "* Answer Choice (" + i + "): "
                     + strAnswers[i] + "\n");
    }
    strToReturn += "*****************************************************\n\n";


    return strToReturn;
  }

}
