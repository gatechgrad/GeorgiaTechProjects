import java.awt.*;
import java.awt.event.*;

class EncryptionPolyalphabeticMethod extends EncryptionMethod  {

  /** CONSTANTS **/


  /** GLOBAL VARIABLES **/
    private TextField txtKeyOdd;
    private TextField txtKeyEven;
    public EncryptionPolyalphabeticMethod() {

  }

    private String formatKey(String str) {
        String strReturn;
        String strInput;
        int i;
        int iStringLength;
        boolean charFound;
        char c;

        strInput = str.toUpperCase();
        strReturn = "";

        for (i = 0; i < strInput.length(); i++) {
            if ((!Character.isLetter(strInput.charAt(i))) ||
                (strReturn.indexOf(strInput.charAt(i)) > -1))  {
                //duplicate character, skip it
            } else {
                strReturn += strInput.charAt(i);
            }

        }

        iStringLength = strReturn.length();
        for (i = iStringLength; i < 26; i++) {
            charFound = false;

            c = 'A';
            while (charFound == false) {
                if (strReturn.indexOf(c) < 0) {
                    charFound = true;
                    strReturn += c;
                }

                c++;

            }
        }

        return strReturn;
    }


  /**
   * encrypt
   */
  public String encrypt(String s, TutorialDisplay txt) {
      String strKeyOdd;
      String strKeyEven;
      String strCipherText;
	  String s2;
      int i = 0;
     

      txt.addText("\n===============\nPolyalphabetic Encryption\n\n");
      strKeyOdd = txtKeyOdd.getText();
      strKeyEven = txtKeyEven.getText();
      strKeyOdd = formatKey(strKeyOdd);
      strKeyEven = formatKey(strKeyEven);

	  txt.addText("Key for odd characters: " + strKeyEven + "\n");
	  txt.addText("Key for even characters: " + strKeyOdd + "\n");

	  txtKeyOdd.setText(strKeyOdd);
	  txtKeyEven.setText(strKeyEven);

	  s2 = s.toUpperCase();
      strCipherText = "";
      for (i = 0; i < s2.length(); i++)
      {
          if (i%2 == 1)
          {
            strCipherText += strKeyOdd.charAt( (int)s2.charAt(i) - 'A' );
          }
          else
          {
              strCipherText += strKeyEven.charAt( (int)s2.charAt(i) - 'A' );
          }
      }
      return strCipherText;
  }

  /**
   * abstract class
   */
  public Panel getOptionsPanel() {
    Panel p;
    Panel pnlTemp;

    p = new Panel(new BorderLayout());

    txtKeyEven = new TextField(26);
    txtKeyOdd = new TextField(26);
    Label lbl = new Label("Enter key for Odd Characters");
    Label lbl2 = new Label("Enter Key for Even Characters");
    Panel pnl = new Panel();
    pnl.add(lbl);
    pnl.add(txtKeyEven);
    p.add(pnl, BorderLayout.NORTH);
    pnl = new Panel();
    pnl.add(lbl2);
    pnl.add(txtKeyOdd);
    p.add(pnl, BorderLayout.CENTER);


    p.setBackground(new Color(192, 255, 192));




    return p;

  }


}
