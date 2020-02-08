import java.awt.*;
import java.awt.event.*;

class EncryptionMerkleHellmanMethod extends EncryptionMethod  {

  /** CONSTANTS **/


  /** GLOBAL VARIABLES **/

  public EncryptionMerkleHellmanMethod() {

  }

  /**
   * encrypt
   */
  public String encrypt(String s, TutorialDisplay txt) {
    String strCipherText;

    return "Merkle-Hellman Encryption";
  }

  /**
   * abstract class
   */
  public Panel getOptionsPanel() {
    Panel p;
    Panel pnlTemp;

    p = new Panel(new BorderLayout());

    p.add(new Label("Merkle-Hellman Options"));



    p.setBackground(Color.blue);




    return p;

  }


}
