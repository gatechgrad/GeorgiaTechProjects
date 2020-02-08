import java.awt.*;
import java.awt.event.*;

class EncryptionCaesarMethod extends EncryptionMethod implements ActionListener {

  /** CONSTANTS **/
  public static final String BUTINCR_LABEL = ">>";
  public static final String BUTDECR_LABEL = "<<";


  /** GLOBAL VARIABLES **/
  private Button butIncr, butDecr;
  private TextField txtKey;
  private Label lblLetters, lblMapping;

  private int iKey;

  public EncryptionCaesarMethod() {

  }

  /**
   * sets the key and encryption mapping
   */
  private void setKey() {
    String strMapping;
    int i;
    int iLetter;

    lblLetters.setText("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");

    strMapping = "";
    for (i = 0; i < 26; i++) {
      iLetter = ((int) 'A') + i + iKey;


      if (iLetter > ((int) 'Z')) {
        iLetter = iLetter - ((int) 'Z') + ((int) 'A') - 1;
      }

      strMapping += ((char) iLetter) + " ";
    }

    lblMapping.setText(strMapping);

    txtKey.setText("" + iKey);


  }


  /**
   * abstract class
   */
  public String encrypt(String s, TutorialDisplay txt) {
    String strCipherText;
    int i;
    int iLetter;
    int iKey;

    try {
      iKey = (new Integer(txtKey.getText())).intValue();
    } catch (NumberFormatException e) {
      iKey = -1;
    }

    strCipherText = "";


    if ((iKey > -1) && (iKey < 26)) {

      txt.addText("\n===============\nCaesar Cipher using shift " + iKey + "\n\n");


      s = s.toUpperCase();

      for (i = 0; i < s.length(); i++) {
        if (Character.isLetter(s.charAt(i))) {
          iLetter = ((int) s.charAt(i));
          iLetter += iKey;

/*
          if (iLetter > 90) {
            iLetter = iLetter - 89 + 65;
          }
*/

          if (iLetter > ((int) 'Z')) {
            iLetter = iLetter - ((int) 'Z') + ((int) 'A') - 1;
          }

          strCipherText += (char) iLetter;

          txt.addText(s.charAt(i) + " -> " + ((char) iLetter) + "\n");


        } else {

          switch (s.charAt(i)) {
            case ' ':
              strCipherText += " ";
              break;
            case '\n':
              strCipherText += "\n";
              break;
            default:
              strCipherText += "-";
              break;
          }
        }
      
      }
    } else {
      strCipherText = "Invalid Key: Key must be an integer between 1 and 25";

    }

    return strCipherText;
  }

  /**
   * abstract class
   */
  public Panel getOptionsPanel() {
    Panel p;
    Panel pnlTemp;
    Panel pnlTemp2;

    p = new Panel(new BorderLayout());

    pnlTemp = new Panel();

    butDecr = new Button(BUTDECR_LABEL);
    butDecr.addActionListener(this);
    pnlTemp.add(butDecr);

    pnlTemp.add(new Label("Shift:"));
    txtKey = new TextField(5);
    txtKey.setBackground(Color.white);
    txtKey.setEditable(false);
    pnlTemp.add(txtKey);

    butIncr = new Button(BUTINCR_LABEL);
    butIncr.addActionListener(this);
    pnlTemp.add(butIncr);

    p.add(pnlTemp, BorderLayout.CENTER);


    p.setBackground(Color.cyan);


    pnlTemp = new Panel(new GridLayout(2, 1));
    lblLetters = new Label();
    lblLetters.setFont(EncryptionProject.MONOSPACE_FONT);
    lblMapping = new Label();
    lblMapping.setFont(EncryptionProject.MONOSPACE_FONT);
    pnlTemp.add(lblLetters);
    pnlTemp.add(lblMapping);

    pnlTemp2 = new Panel();
    pnlTemp2.add(pnlTemp);

    p.add(pnlTemp2, BorderLayout.SOUTH);

    iKey = 1;

    setKey();


    return p;

  }


  /**
   * actionPerformed
   */
  public void actionPerformed(ActionEvent e) {
    String strCommand;

    strCommand = e.getActionCommand();

    if (strCommand.equals(BUTINCR_LABEL)) {
      iKey++;

      if (iKey > 25) {
        iKey = 0;
      }

      setKey();
    } else if (strCommand.equals(BUTDECR_LABEL)) {
      iKey--;

      if (iKey < 0) {
        iKey = 25;
      }

      setKey();
    }

  }




}
