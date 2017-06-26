import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


class QMW extends Frame implements WindowListener, ActionListener {

//Window Properties
public static final int WIN_WIDTH = 300;
public static final int WIN_HEIGHT = 300;
public static final int WIN_XOFFSET = 100;
public static final int WIN_YOFFSET = 100;
public static final Color BKG_COLOR = new Color(224, 224, 224);

public static final String FILE = "File";
public static final String QUESTIONS = "Questions";
public static final String MODE = "Mode";
public static final String QUIZ = "Quiz";
public static final String SAVE = "Save";
public static final String LOAD = "Load";
public static final String EXIT = "Exit      ";
public static final String ADD = "Add Question";
public static final String REMOVE = "Remove Question";
public static final String FIB = "Fill in the Blank";
public static final String MC = "Multiple Choice";
public static final String ESSAY = "Essay";
public static final String START = "Start Quiz";
public static final String STATS = "Statistics";

private QuestionPanel myQuestionPanel;
private AnswerPanel myAnswerPanel;
private Question myQuestion;

private MenuBar myMenuBar;
private Menu
  MenuFile, MenuQuestions, MenuMode, MenuQuiz;
private MenuItem
  MISave, MILoad, MIExit, MIAdd, MIRemove, MIFIB, MIMC, MIEssay,
  MIStart, MIStats;
private Vector VectQuestions;
private int iQNumber;

public QMW() {
  super();
  setSize(WIN_WIDTH, WIN_HEIGHT);
  setBounds(WIN_XOFFSET, WIN_YOFFSET, WIN_WIDTH, WIN_HEIGHT);
  setTitle("Quiz Master");
  setBackground(BKG_COLOR);
  setResizable(false);

  iQNumber = 0;
  
  setVector();
  setQuestion();
  setMenu();

  setVisible(true);
  addWindowListener(this);
}


public void setVector() {
  VectQuestions = new Vector();
  VectQuestions.addElement(new Question("What is the capital of Georgia", "Atlanta", "Savannah", "Macon", "Douglasville"));
  VectQuestions.addElement(new Question("What is the capital of Tennessee", "Nashville", "Knoxville", "Memphis", "Oak Ridge"));
  VectQuestions.addElement(new Question("What is the capital of Alabama", "Montgomery", "Wrong 1", "Wrong 2", "Wrong 3"));
  VectQuestions.addElement(new Question("What is the capital of U.S", "Washington D.C.", "Wrong 1", "Wrong 2", "Wrong 3"));

}


public void setQuestion() {
  myQuestionPanel = new QuestionPanel((iQNumber + 1));
  myAnswerPanel = new AnswerPanel(this);

  myQuestion = ((Question) VectQuestions.elementAt(iQNumber));


  myQuestionPanel.setQuestion(myQuestion.getQuestion());  
  myAnswerPanel.setQuestion(myQuestion);  

  removeAll();
  setTheLayout();

  iQNumber++;
}



public void setTheLayout() {
  setVisible(false);

  Panel PnlBottom = new Panel(new GridLayout(1, 2, 5, 5));
    PnlBottom.add(myAnswerPanel);
    PnlBottom.setBackground(new Color(192, 192, 192));

  setLayout(new GridLayout(2, 1, 5, 5));
    add(myQuestionPanel);
    add(PnlBottom);

  setVisible(true);
  

}



public void update(boolean wasCorrect) {

  if(wasCorrect == true) {
    myQuestionPanel.setCorrect("CORRECT");
  } else if (wasCorrect == false) {
    myQuestionPanel.setIncorrect("The correct answer is " + myQuestion.getCorrectAnswer());
  }


}


public void actionPerformed(ActionEvent e) {
  String theAction = e.getActionCommand();

  if (theAction.equals(ADD)) {

  } else if(theAction.equals(EXIT)) {
    shutdown();
  } else if(theAction.equals(ADD)) {
    AddDialog myAddDialog = new AddDialog(VectQuestions, this);
    System.out.println("Add Dialog");
    myAddDialog.setVisible(true);
  }

}

public void setMenu() {
  myMenuBar = new MenuBar();
  MenuFile = new Menu(FILE);
    MISave = new MenuItem(SAVE);
    MILoad = new MenuItem(LOAD);
    MIExit = new MenuItem(EXIT);

    MenuFile.add(MISave);
    MenuFile.add(MILoad);
    MenuFile.add(MIExit);

    MISave.addActionListener(this);
    MILoad.addActionListener(this);
    MIExit.addActionListener(this);

  MenuQuestions = new Menu(QUESTIONS);
    MIAdd = new MenuItem(ADD);
    MIRemove = new MenuItem(REMOVE);

    MIAdd.addActionListener(this);
    MIRemove.addActionListener(this);

    MenuQuestions.add(MIAdd);
    MenuQuestions.add(MIRemove);

  MenuMode = new Menu(MODE);
    MIFIB = new MenuItem(FIB);
    MIMC = new MenuItem(MC);
    MIEssay = new MenuItem(ESSAY);

    MIFIB.addActionListener(this);
    MIMC.addActionListener(this);
    MIEssay.addActionListener(this);

    MenuMode.add(MIFIB);
    MenuMode.add(MIMC);
    MenuMode.add(MIEssay);

  MenuQuiz = new Menu(QUIZ);
    MIStart = new MenuItem(START);
    MIStats = new MenuItem(STATS);

    MIStart.addActionListener(this);
    MIStats.addActionListener(this);

    MenuQuiz.add(MIStart);
    MenuQuiz.add(MIStats);


  myMenuBar.add(MenuFile);
  myMenuBar.add(MenuQuestions);
  myMenuBar.add(MenuMode);
  myMenuBar.add(MenuQuiz);

  setMenuBar(myMenuBar);
}


public Question getQuestion() {
  return myQuestion;  
}



public static void main(String argv[]) {
  QMW myQMW = new QMW();
}

public void shutdown() {
  setVisible(false);
  dispose();
  System.exit(0);
}


public void windowClosing(WindowEvent e) {
  shutdown();
}

public void windowClosed(WindowEvent e) {
}
public void windowOpened(WindowEvent e) {
}
public void windowIconified(WindowEvent e) {
}
public void windowDeiconified(WindowEvent e) {
}
public void windowActivated(WindowEvent e) {
}
public void windowDeactivated(WindowEvent e) {
}



}
