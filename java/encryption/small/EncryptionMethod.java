import java.awt.*;

public abstract class EncryptionMethod {

  String strMessage;

  public EncryptionMethod() {

  }

  public void setText(String s) {
    strMessage = s;
  }

  public abstract String encrypt(String s, TutorialDisplay txt);
  public abstract Panel getOptionsPanel();
}
