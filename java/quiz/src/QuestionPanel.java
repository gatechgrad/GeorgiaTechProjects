import java.awt.*;
import java.awt.event.*;

class QuestionPanel extends Panel {

private int iNumber;
private Label
  LblQuestion, LblNumber;
private Correct myCorrect;


/*
 * QuestionPanel - constructor, sets up components and instance variables
 */
public QuestionPanel(int newNumber) {
  super();
  setLayout(new GridLayout(2, 1, 10, 10));

  iNumber = newNumber;
  myCorrect = new Correct();

  myCorrect.setSize(50, 200);

  LblQuestion = new Label();
  LblQuestion.setFont(new Font("Dialog", Font.PLAIN, 12));
}



/*
 * setQuestion - displays the question to the user
 */
public void setQuestion(String newQuestion) {
  removeAll();
  setLayout(new GridLayout(2, 1, 10, 10));

  LblQuestion = new Label(newQuestion, Label.CENTER);
  LblNumber = new Label(("Question " + iNumber), Label.LEFT);

  Panel PnlTop = new Panel(new BorderLayout());
    PnlTop.add(LblNumber, BorderLayout.NORTH);
    PnlTop.add(LblQuestion, BorderLayout.CENTER);

  myCorrect = new Correct();

  add(PnlTop);
  add(myCorrect);


}

public void setCorrect(String message) {
  myCorrect.setMessage(message, (new Color(0, 192, 0)), (new Font("Dialog", Font.BOLD, 24)));
}

public void setIncorrect(String message) {
  myCorrect.setMessage(message, (new Color(192, 0, 0)), (new Font("Dialog", Font.BOLD, 12)));
}



}
