import java.awt.*;
import java.awt.event.*;

class EncryptionRSAMethod extends EncryptionMethod  {

  /** CONSTANTS **/


  /** GLOBAL VARIABLES **/

  public EncryptionRSAMethod() {

  }

  /**
   * abstract class
   */
  public String encrypt(String s, TutorialDisplay txt) {
    String strCipherText;

    return "RSA Encryption";
  }

  /**
   * abstract class
   */
  public Panel getOptionsPanel() {
    Panel p;
    Panel pnlTemp;

    p = new Panel(new BorderLayout());

    p.add(new Label("RSA Options"));



    p.setBackground(new Color(255, 192, 255));




    return p;

  }


}
