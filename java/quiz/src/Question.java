class Question {
public static final int NUM_ANSWERS = 4;

private String Question;
private String AnsA;
private String AnsB;
private String AnsC;
private String AnsCorrect;

public Question(String Question, String AnsCorrect, String AnsA, String AnsB, String AnsC) {
  this.Question = Question;  
  this.AnsCorrect = AnsCorrect;
  this.AnsA = AnsA;
  this.AnsB = AnsB;
  this.AnsC = AnsC;
}

/*
 * getQuestion() - returns the Question
 */
public String getQuestion() {
  return Question;
}


/*
 * getCorrectAnswer() - returns the correct answer
 */
public String getCorrectAnswer() {
  return AnsCorrect;
}


/*
 * getAnswers() - returns the Wrong answers
 */
public String[] getAnswers() {
  String[] wrongAnswers = new String[NUM_ANSWERS];

  wrongAnswers[0] = AnsCorrect;
  wrongAnswers[1] = AnsA;
  wrongAnswers[2] = AnsB;
  wrongAnswers[3] = AnsC;
  //add more if needed

  System.out.println("Wrong Answer1: " + wrongAnswers[1]);
  return wrongAnswers;

}


/*
 * isCorrect() - returns wether or not the passed in String is the
 *               correct answer
 */
public boolean isCorrect(String usersChoice) {

  if (usersChoice.equals(AnsCorrect)) {
    return true;
  } else {
    return false;
  }

}


}
