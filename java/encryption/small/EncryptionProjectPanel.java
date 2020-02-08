import java.awt.*;
import java.awt.event.*;


class EncryptionProjectPanel extends Panel implements ActionListener, ItemListener {

  /** CONSTANTS **/
  public static final String BUTENCRYPT_LABEL = "Encrypt";

  public static final String CAESAR_CHOICE = "Caesar Cipher";
  public static final String POLYALPHABETIC_CHOICE = "Polyalphabetic Substitution";
  public static final String VERNAM_CHOICE = "Vernam Cipher";
  public static final String COLUMNAR_CHOICE = "Columnar Transposition";
  public static final String DES_CHOICE = "Data Encryption Standard (DES)";
  public static final String MERKLEHELLMAN_CHOICE = "Merkle-Hellman";
  public static final String RSA_CHOICE = "Rivest-Shamir-Adelman (RSA)";
  public static final String ELGAMAL_CHOICE = "El Gamal/Digital Signature Algorithm";

  public static final String ASCII_CHOICE = "ASCII";
  public static final String BINARY_CHOICE = "Binary";
  public static final String HEX_CHOICE = "Hexadecimal";
  public static final String DECIMAL_CHOICE = "Decimal";


  /** GLOBAL VARIABLES **/
  private TextArea txtMessageText;
  private TextArea txtCipherCodeText;
  private TutorialDisplay txtTutorialText;
  private Button butEncrypt;
  private Choice choiceMethods;
  private Choice choiceDisplay;
  private Panel pnlOptions;
  private String strCipherText;


  private EncryptionMethod em;
  private EncryptionMethod emCaesar;
  private EncryptionMethod emPolyalphabetic;
  private EncryptionMethod emVernam;
  private EncryptionMethod emColumnar;
  private EncryptionMethod emDES;
  private EncryptionMethod emMerkleHellman;
  private EncryptionMethod emRSA;
  private EncryptionMethod emElGamal;


  /**
   * EncryptionProjectPanel - constructor
   */
  public EncryptionProjectPanel() {
    emCaesar = new EncryptionCaesarMethod();
    emPolyalphabetic = new EncryptionPolyalphabeticMethod();
    emVernam = new EncryptionVernamMethod();
    emColumnar = new EncryptionColumnarMethod();
    emDES = new EncryptionDESMethod();
    emMerkleHellman = new EncryptionMerkleHellmanMethod();
    emRSA = new EncryptionRSAMethod();
    emElGamal = new EncryptionElGamalMethod();

    em = emCaesar;
    setupPanel();

  }


  /**
   * setupPanel
   */
  private void setupPanel() {
    Panel pnlMain;
    Panel pnlTemp;
    Panel pnlTemp2;
//    this.setSize(640, 480);
    this.setBackground(new Color(192, 192, 192));

    txtMessageText = new TextArea("", 3, 25, TextArea.SCROLLBARS_NONE);
    txtMessageText.setFont(EncryptionProject.MONOSPACE_FONT);
    txtMessageText.setBackground(Color.white);
    txtCipherCodeText = new TextArea("", 3, 50, TextArea.SCROLLBARS_NONE);
    txtCipherCodeText.setFont(EncryptionProject.CODE_FONT);
    txtCipherCodeText.setEditable(false);
    txtCipherCodeText.setBackground(Color.white);

    txtTutorialText = new TutorialDisplay();


    this.setLayout(new BorderLayout());

    pnlMain = new Panel(new GridLayout(3, 1));

    pnlTemp = new Panel(new BorderLayout());

    choiceMethods = new Choice();
    choiceMethods.add(CAESAR_CHOICE);
    choiceMethods.add(POLYALPHABETIC_CHOICE);
    choiceMethods.add(VERNAM_CHOICE);
    choiceMethods.add(COLUMNAR_CHOICE);
    choiceMethods.add(DES_CHOICE);
    choiceMethods.add(MERKLEHELLMAN_CHOICE);
    choiceMethods.add(RSA_CHOICE);
    choiceMethods.add(ELGAMAL_CHOICE);


    choiceMethods.addItemListener(this);
    pnlTemp2 = new Panel(new FlowLayout(FlowLayout.LEFT));
    pnlTemp2.add(new Label("Encryption Method:"));
    pnlTemp2.add(choiceMethods);
    pnlTemp.add(pnlTemp2, BorderLayout.NORTH);

    pnlTemp2 = new Panel(new FlowLayout(FlowLayout.LEFT));
    pnlTemp2.add(new Label("Message"));
    pnlTemp2.add(txtMessageText);
    pnlTemp.add(pnlTemp2, BorderLayout.CENTER);
    pnlMain.add(pnlTemp);


    pnlOptions = new Panel(new CardLayout());
    pnlOptions.add(CAESAR_CHOICE, emCaesar.getOptionsPanel());
    pnlOptions.add(POLYALPHABETIC_CHOICE, emPolyalphabetic.getOptionsPanel());
    pnlOptions.add(VERNAM_CHOICE, emVernam.getOptionsPanel());
    pnlOptions.add(COLUMNAR_CHOICE, emColumnar.getOptionsPanel());
    pnlOptions.add(DES_CHOICE, emDES.getOptionsPanel());
    pnlOptions.add(MERKLEHELLMAN_CHOICE, emMerkleHellman.getOptionsPanel());
    pnlOptions.add(RSA_CHOICE, emRSA.getOptionsPanel());
    pnlOptions.add(ELGAMAL_CHOICE, emElGamal.getOptionsPanel());

    pnlMain.add(pnlOptions);



    pnlTemp = new Panel(new FlowLayout(FlowLayout.LEFT));
    pnlTemp.add(new Label("Cipher Text"));

    pnlTemp2 = new Panel(new BorderLayout());

    choiceDisplay = new Choice();
    choiceDisplay.add(ASCII_CHOICE);
    choiceDisplay.add(HEX_CHOICE);
    choiceDisplay.add(DECIMAL_CHOICE);
    choiceDisplay.add(BINARY_CHOICE);
    choiceDisplay.addItemListener(this);
    pnlTemp2.add(choiceDisplay, BorderLayout.NORTH);
    pnlTemp2.add(txtCipherCodeText, BorderLayout.CENTER);
    pnlTemp.add(pnlTemp2);

    pnlTemp2 = new Panel();
    pnlTemp.add(pnlTemp2);

    pnlMain.add(pnlTemp);

    this.add(pnlMain, BorderLayout.CENTER);

    pnlTemp = new Panel();
    butEncrypt = new Button(BUTENCRYPT_LABEL);
    butEncrypt.addActionListener(this);
    pnlTemp.add(butEncrypt);
    this.add(pnlTemp, BorderLayout.SOUTH);

    this.add(txtTutorialText, BorderLayout.EAST);


  }

  /**
   * getHexString - converts the string of characters to a hex string
   */
  private String getHexString(String str) {
    int i;
    String strReturn;
    String strTemp;

    strReturn = "";

    for (i = 0; i < str.length(); i++) {
      //System.out.println(((int) str.charAt(i)) + " ");

      strTemp = (Integer.toHexString((int) str.charAt(i))).toUpperCase();

      if (strTemp.length() == 1) {
        strTemp = "0" + strTemp;
      }

      strReturn +=  strTemp + " ";


    }


    return strReturn;

  }

  /**
   * getDecimalString - converts the string of characters to a decimal string
   */
  private String getDecimalString(String str) {
    int i;
    String strReturn;

    strReturn = "";

    for (i = 0; i < str.length(); i++) {

      strReturn += ((int) str.charAt(i)) + " ";


    }


    return strReturn;

  }

  /**
   * getBinaryString - converts the string of characters to a binary string
   */
  private String getBinaryString(String str) {
    int i, j;
    String strReturn;
    String strTemp;

    strReturn = "";

    for (i = 0; i < str.length(); i++) {

      strTemp = Integer.toBinaryString((int) str.charAt(i));

      for (j = strTemp.length(); j < 8; j++) {
        strReturn += "0";

      }



      strReturn += strTemp + " ";


    }


    return strReturn;

  }

  /**
   * setCipherText - sets the cipher text area
   */
  private void setCipherText(String str) {
    String strToSet;
    int i;

    strToSet = "";
    for (i = 0; i < str.length(); i++) {
      if (((int) str.charAt(i)) > 31) {
        strToSet += str.charAt(i);
      } else {
        strToSet += " ";
      }
    }
//      txtCipherText.setText(strToSet);
  }


  /**
   * actionPerformed - required by ActionListener interface
   */
  public void actionPerformed(ActionEvent e) {
    String strCommand;
    String strMessage;
    String strDisplay;

    strCommand = e.getActionCommand();

    if (strCommand.equals(BUTENCRYPT_LABEL)) {
//      txtCipherText.setText("Encrypted Text Goes Here");
      strMessage = txtMessageText.getText();

      strCipherText = em.encrypt(strMessage, txtTutorialText);
      setCipherText(strCipherText);

      strDisplay = choiceDisplay.getSelectedItem();


      if (strDisplay.equals(DECIMAL_CHOICE)) {
        txtCipherCodeText.setText(getDecimalString(strCipherText));
      } else if (strDisplay.equals(ASCII_CHOICE)) {
        txtCipherCodeText.setText(strCipherText);
        txtCipherCodeText.setFont(EncryptionProject.MONOSPACE_FONT);
      } else if (strDisplay.equals(BINARY_CHOICE)) {
        txtCipherCodeText.setText(getBinaryString(strCipherText));
        txtCipherCodeText.setFont(EncryptionProject.CODE_FONT);
      } else if (strDisplay.equals(HEX_CHOICE)) {
        txtCipherCodeText.setText(getHexString(strCipherText));
        txtCipherCodeText.setFont(EncryptionProject.CODE_FONT);

      }


    }
  }


  /**
   * itemStateChanged - required by ItemListener interface
   */
  public void itemStateChanged(ItemEvent e) {
    String strChoice;
    String strMessage;



    if (e.getSource().equals(choiceMethods)) {
      strChoice = choiceMethods.getSelectedItem();
//    System.out.println(strChoice);

      if (strChoice.equals(CAESAR_CHOICE)) {
        em = emCaesar;
      } else if (strChoice.equals(POLYALPHABETIC_CHOICE)) {
        em = emPolyalphabetic;
      } else if (strChoice.equals(VERNAM_CHOICE)) {
        em = emVernam;
      } else if (strChoice.equals(COLUMNAR_CHOICE)) {
        em = emColumnar;
      } else if (strChoice.equals(DES_CHOICE)) {
        em = emDES;
      } else if (strChoice.equals(MERKLEHELLMAN_CHOICE)) {
        em = emMerkleHellman;
      } else if (strChoice.equals(RSA_CHOICE)) {
        em = emRSA;
      } else if (strChoice.equals(ELGAMAL_CHOICE)) {
        em = emElGamal;
      }

      ((CardLayout) pnlOptions.getLayout()).show(pnlOptions, strChoice);
        
    }

    if (e.getSource().equals(choiceDisplay)) {


      strChoice = choiceDisplay.getSelectedItem();

      if (strChoice.equals(DECIMAL_CHOICE)) {
          txtCipherCodeText.setText(getDecimalString(strCipherText));
          txtCipherCodeText.setFont(EncryptionProject.CODE_FONT);
      } else if (strChoice.equals(BINARY_CHOICE)) {
          txtCipherCodeText.setText(getBinaryString(strCipherText));
          txtCipherCodeText.setFont(EncryptionProject.CODE_FONT);
      } else if (strChoice.equals(ASCII_CHOICE)) {
          txtCipherCodeText.setText(strCipherText);
          txtCipherCodeText.setFont(EncryptionProject.MONOSPACE_FONT);
      } else if (strChoice.equals(HEX_CHOICE)) {
          txtCipherCodeText.setText(getHexString(strCipherText));
          txtCipherCodeText.setFont(EncryptionProject.CODE_FONT);
      }
    }


  }

  /**
   * main - if run as an application
   */
  public static void main(String args[]) {
    Panel thePanel;
    Frame theFrame;
    WindowListener wl;

    thePanel = new EncryptionProject();

    theFrame = new Frame();
    wl = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }

    };
    theFrame.addWindowListener(wl);

    theFrame.add(thePanel);

    theFrame.setLocation(200, 200);

    theFrame.pack();
    theFrame.setVisible(true);

  }
}
