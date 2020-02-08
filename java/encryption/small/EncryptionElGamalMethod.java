import java.awt.*;
import java.awt.event.*;

class EncryptionElGamalMethod extends EncryptionMethod  {

  /** CONSTANTS **/
  private TextField txtP, txtA, txtX;


  /** GLOBAL VARIABLES **/

  public EncryptionElGamalMethod() {

  }

  /**
   * encrypt
   */
  public String encrypt(String str, TutorialDisplay txt) {
    String strCipherText;

    int r, a, k, p;
    int s, k0, m, x;

    strCipherText = "Error";

    try {

      a = (new Integer(txtA.getText())).intValue();
      x = (new Integer(txtX.getText())).intValue();
      p = (new Integer(txtP.getText())).intValue();

      k = ((int) (Math.random() * (p - 1))) + 1;

      r = ((int) Math.pow(a, k)) % p;

      k0 = k % (p - 1);



      strCipherText = "k:" + k + "  r:" + r;
    } catch (NumberFormatException e) {

    }

    return strCipherText;
  }

  /**
   * abstract class
   */
  public Panel getOptionsPanel() {
    Panel p;
    Panel pnlTemp;

    p = new Panel(new FlowLayout(FlowLayout.LEFT));

    p.add(new Label("Prime Number (p):"));

    txtP = new TextField(3);
    txtP.setBackground(Color.white);
    p.add(txtP);

    p.add(new Label("Integer One (a):"));
    txtA = new TextField(3);
    txtA.setBackground(Color.white);
    p.add(txtA);


    p.add(new Label("Integer Two (x):"));
    txtX = new TextField(3);
    txtX.setBackground(Color.white);
    p.add(txtX);

    //p.add(new Label("El Gamal Options"));



    p.setBackground(Color.green);




    return p;

  }


}
