import java.awt.*;
import java.awt.event.*;

class AnswerPanel extends Panel implements ActionListener {


public static final String SUBMIT = "   SUBMIT   ";
public static final String CONTINUE = "CONTINUE";
public static final int NUM_ANSWERS = 4;



CheckboxGroup Choices = new CheckboxGroup();
Checkbox
  FirstChoice, SecondChoice, ThirdChoice, FourthChoice;
Button
  ButSubmit;
QMW theQMW;



AnswerPanel(QMW newQMW) {
  setLayout(new BorderLayout());
  setFont(new Font("Dialog", Font.PLAIN, 12));

  ButSubmit = new Button(SUBMIT);
  ButSubmit.addActionListener(this);
  theQMW = newQMW;

}

public void setQuestion(Question newQuestion) {
  int[] indexPositions = mixUp();

  /*
  System.out.println("Final Results");
  System.out.println("0: " + indexPositions[0]);
  System.out.println("1: " + indexPositions[1]);
  System.out.println("2: " + indexPositions[2]);
  System.out.println("3: " + indexPositions[3]);
  */

   
  String[] Answers = newQuestion.getAnswers();
  Panel PnlChoices = new Panel(new GridLayout(4, 1, 5, 5));

    FirstChoice = new Checkbox(Answers[indexPositions[0]], false, Choices);
    SecondChoice = new Checkbox(Answers[indexPositions[1]], false, Choices);
    ThirdChoice = new Checkbox(Answers[indexPositions[2]], false, Choices);
    FourthChoice = new Checkbox(Answers[indexPositions[3]], false, Choices);




    PnlChoices.add(FirstChoice);
    PnlChoices.add(SecondChoice);
    PnlChoices.add(ThirdChoice);
    PnlChoices.add(FourthChoice);

  Panel PnlButtons = new Panel(new FlowLayout(FlowLayout.LEFT));
    PnlButtons.add(ButSubmit);

  add(PnlChoices, BorderLayout.CENTER);
  add(PnlButtons, BorderLayout.SOUTH);
}


public int[] mixUp() {
  int i;
  int[] indexPositions = new int[NUM_ANSWERS];
  int[] usedNumbers = new int[NUM_ANSWERS];
  int iQuestionNum;

  for (i = 0; i < NUM_ANSWERS; i++) {
    usedNumbers[i] = 0; //I wonder if this loop will be unrolled..
//    System.out.println("" + usedNumbers[i]);
  }


  for (i = 0; i < NUM_ANSWERS; i++) {
    iQuestionNum = (int) (NUM_ANSWERS * Math.random());
//    iQuestionNum = 2;
//    System.out.println("" + iQuestionNum);

    if (usedNumbers[iQuestionNum] == 0) {
      indexPositions[i] = iQuestionNum;
      usedNumbers[iQuestionNum] = 1;
    } else {
      while (usedNumbers[iQuestionNum] != 0) {
        iQuestionNum++;
        if (iQuestionNum >= NUM_ANSWERS) {
          iQuestionNum = 0;
        }
      }
      
      indexPositions[i] = iQuestionNum;
      usedNumbers[iQuestionNum] = 1;

    }

  }

  return indexPositions;

}


public void checkAnswer() {
//  System.out.println("My Answer: " + Choices.getSelectedCheckbox().getLabel() + "\nCorrect Answer: " +
//    theQMW.getQuestion().getCorrectAnswer() );

  if (theQMW.getQuestion().getCorrectAnswer().equals(Choices.getSelectedCheckbox().getLabel())) {
    theQMW.update(true);
  } else {
    theQMW.update(false);
  }

  ButSubmit.setLabel(CONTINUE);

}


public void actionPerformed(ActionEvent e) {
  String TheAction = e.getActionCommand();

  if (TheAction.equals(SUBMIT)) {
    checkAnswer();
  }

  if (TheAction.equals(CONTINUE)) {
//    theQMW.removeAll();
    theQMW.setQuestion();
    ButSubmit.setLabel(SUBMIT);

  }

}


}
