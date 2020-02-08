import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Vector;
import java.util.StringTokenizer;

class BBSList implements ActionListener, ListSelectionListener {

  /** CONSTANTS **/
  private static final String BBS_LIST_FILE = "bbs.lst";
  private static final int NUM_TOKENS_PER_LINE = 4;

  private static final String OKAY = "Okay";
  private static final String CANCEL = "Cancel";
  private static final String CONNECT = "Connect";
  private static final String DELETE = "Delete";
  private static final String CHANGE = "Change";
  private static final String ADD = "Add";

  /** INSTANCE VARIABLES **/
  private TWA theTWA;
  private JList listNames;
  private JScrollPane theScrollPane;
  private JFrame theFrame;
  private Vector vectNames;
  private Vector vectBBSes;

  private JPanel pnlBBS;
  private JTextField txtName;
  private JTextField txtHost;
  private JTextField txtPort;
  private JTextField txtVersion;

  private JPanel pnlButtons;
  private JButton butOkay;
  private JButton butCancel;
  private JButton butConnect;
  private JButton butDelete;
  private JButton butChange;
  private JButton butAdd;




  /**
    * BBSList
    **/
  public BBSList(TWA theTWA) {
    this.theTWA = theTWA;
    readList();
    createWindow();
  }
  //for testing
  public BBSList() {
    this.theTWA = null;
    readList();
    createWindow();
  }


  /**
    * createWindow
    **/
  private void createWindow() {
    theFrame = new JFrame();



    theFrame.getContentPane().setLayout(new BorderLayout());
    pnlBBS = new JPanel(new GridLayout(4, 2));
    pnlButtons = new JPanel(new GridLayout(2, 6));

    txtName = new JTextField(50);
    txtHost = new JTextField(50);
    txtPort = new JTextField(50);
    txtVersion = new JTextField(50);
    pnlBBS.add(new JLabel("Host Name:  ", JLabel.RIGHT));
    pnlBBS.add(txtName);
    pnlBBS.add(new JLabel("Host Address:  ", JLabel.RIGHT));
    pnlBBS.add(txtHost);
    pnlBBS.add(new JLabel("Telnet Port:  ", JLabel.RIGHT));
    pnlBBS.add(txtPort);
    pnlBBS.add(new JLabel("TW Version:  ", JLabel.RIGHT));
    pnlBBS.add(txtVersion);

    butOkay = new JButton(OKAY);
    butCancel = new JButton(CANCEL);
    butConnect = new JButton(CONNECT);
    butDelete = new JButton(DELETE);
    butChange = new JButton(CHANGE);
    butAdd = new JButton(ADD);
    butOkay.addActionListener(this);
    butCancel.addActionListener(this);
    butConnect.addActionListener(this);
    butDelete.addActionListener(this);
    butChange.addActionListener(this);
    butAdd.addActionListener(this);


    pnlButtons.add(butAdd);
    pnlButtons.add(butChange);
    pnlButtons.add(butDelete);
    pnlButtons.add(new JPanel());
    pnlButtons.add(new JPanel());
    pnlButtons.add(butOkay);

    pnlButtons.add(new JPanel());
    pnlButtons.add(butConnect);
    pnlButtons.add(new JPanel());
    pnlButtons.add(new JPanel());
    pnlButtons.add(new JPanel());
    pnlButtons.add(butCancel);


    JPanel pnlBottom = new JPanel(new BorderLayout());
    pnlBottom.add(pnlBBS, BorderLayout.CENTER);
    pnlBottom.add(pnlButtons, BorderLayout.SOUTH);

    theFrame.getContentPane().add(listNames, BorderLayout.CENTER);
    theFrame.getContentPane().add(pnlBottom, BorderLayout.SOUTH);

    theFrame.setTitle("BBS List");
    theFrame.setBounds(TWA.WINDOW.x + 20, TWA.WINDOW.y + 100, TWA.WINDOW.width - 40, TWA.WINDOW.height - 200);
    theFrame.setVisible(true);

  }

  /**
    * readList
    **/
  private void readList() {
    BBS tempBBS;
    FileReader theFileReader;
    StringBuffer theStringBuffer;
    int iChar;
    String strData;
    String strLine;
    String[] strTokens = new String[NUM_TOKENS_PER_LINE];
    int i;

    theStringBuffer = new StringBuffer();

    vectNames = new Vector();
    vectBBSes = new Vector();

    try {
      theFileReader = new FileReader(BBS_LIST_FILE);

      iChar = theFileReader.read();
      while(iChar != -1) {
        theStringBuffer.append((char) iChar);
        iChar = theFileReader.read();
      }
      theFileReader.close();

    } catch (IOException e) { }

    strData = theStringBuffer.toString();

    StringTokenizer theFileTokenizer = new StringTokenizer(strData, "\n");
    while(theFileTokenizer.hasMoreTokens()) {
      strLine = theFileTokenizer.nextToken();
      StringTokenizer theLineTokenizer = new StringTokenizer(strLine, "|");
      if (theLineTokenizer.countTokens() == NUM_TOKENS_PER_LINE) {
        for (i = 0; i < NUM_TOKENS_PER_LINE; i++) {
          strTokens[i] = theLineTokenizer.nextToken();
        }

        try {
          tempBBS = new BBS(strTokens[0], strTokens[1],
                           (new Integer(strTokens[2])).intValue(),
                           (new Integer(strTokens[3])).intValue());
        } catch (NumberFormatException e) {
          tempBBS = new BBS(strTokens[0], strTokens[1], 23, 0);
        }
        vectBBSes.add(tempBBS);
        vectNames.add(strTokens[0]);

      } else {
        System.err.print("BBS List file format error");
      }
      
    }


    listNames = new JList(vectNames);
    listNames.setBackground(Color.black);
    listNames.setForeground(Color.green);
    listNames.addListSelectionListener(this);


  }

  /**
    * addBBS
    **/
  private void addBBS() {
    BBS tempBBS;
    int i;

    tempBBS = null; //have to do this to keep Java from bitching
    try {
      tempBBS = new BBS(txtName.getText(), txtHost.getText(),
                   (new Integer(txtPort.getText())).intValue(),
                   (new Integer(txtVersion.getText())).intValue());
    } catch (NumberFormatException e) { }

    if (tempBBS != null) {
      vectBBSes.add(tempBBS);
    }
    vectNames = new Vector(); //to ensure that the name list is correct
                              //I will rebuild it from the BBS list
    if (vectBBSes.size() > 0) {
      for (i = 0; i < vectBBSes.size(); i++) {
        tempBBS = (BBS) vectBBSes.elementAt(i);
        vectNames.add( (String) tempBBS.getName());
      }
    }

    clearAll();
    listNames.setListData(vectNames);
  }

  /**
    * changeBBS
    **/
  private void changeBBS() {
    BBS tempBBS;

    if (listNames.getSelectedIndex() != -1) {

      tempBBS = null; //I wish the compiler would shut up about initialization
                      //of variables
      try {
        tempBBS = new BBS(txtName.getText(), txtHost.getText(),
                     (new Integer(txtPort.getText())).intValue(),
                   (new Integer(txtVersion.getText())).intValue());
      } catch (NumberFormatException e) { }

      if (tempBBS != null) {
        vectBBSes.removeElementAt(listNames.getSelectedIndex());
        vectBBSes.insertElementAt(tempBBS, listNames.getSelectedIndex());

        vectNames.removeElementAt(listNames.getSelectedIndex());
        vectNames.insertElementAt(txtName.getText(), listNames.getSelectedIndex());
      }
    }

  }

  /**
    * removeBBS
    **/
  private void removeBBS() {

    vectBBSes.removeElementAt(listNames.getSelectedIndex());
    vectNames.removeElementAt(listNames.getSelectedIndex());
    listNames.setListData(vectNames);
    clearAll();
    butDelete.setEnabled(false);

  }

  /**
    * clearAll - clears all fields
    **/
  private void clearAll() {
    txtName.setText("");
    txtHost.setText("");
    txtPort.setText("");
    txtVersion.setText("");

  }

  /**
    * writeFile
    **/
  private void writeFile() {
    BBS tempBBS;
    FileWriter theFileWriter;
    PrintWriter thePrintWriter;
    String strData;
    int i;

    strData = "";
    for (i = 0; i < vectBBSes.size(); i++) {
      tempBBS = (BBS) vectBBSes.elementAt(i);
      strData += tempBBS.toString();
      strData += "\n";
    }

    try {
      theFileWriter = new FileWriter(BBS_LIST_FILE);
      thePrintWriter = new PrintWriter(theFileWriter);

      thePrintWriter.print(strData);
      thePrintWriter.close();


    } catch (IOException e) { }


  }

  /**
    * connectToBBS
    **/
  private void connectToBBS() {

    if (getSelectedBBS() != null) {
      theTWA.setBBS(getSelectedBBS());
      theTWA.beginConnect();

      theFrame.setVisible(false);
      theFrame.dispose();
    }
  }

  /**
    * getSelectedBBS
    **/
  private BBS getSelectedBBS() {
    BBS tempBBS;

    tempBBS = null;

    if (listNames.getSelectedIndex() != -1) {
        tempBBS = (BBS) vectBBSes.elementAt(listNames.getSelectedIndex());
    }
    return tempBBS;

  }


  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strActionCommand;
    strActionCommand = e.getActionCommand();

    if (strActionCommand.equals(OKAY)) {
      writeFile();

      if (getSelectedBBS() != null) {
        theTWA.setBBS(getSelectedBBS());
      }
      theFrame.setVisible(false);
      theFrame.dispose();
    } else if (strActionCommand.equals(CONNECT)) {
      connectToBBS();
    } else if (strActionCommand.equals(CHANGE)) {
      changeBBS();
    } else if (strActionCommand.equals(ADD)) {
      addBBS();
      writeFile();
    } else if (strActionCommand.equals(DELETE)) {
      removeBBS();
    } else if (strActionCommand.equals(DELETE)) {

    } else if (strActionCommand.equals(CANCEL)) {
      theFrame.setVisible(false);
      theFrame.dispose();
    }


  }

  /**
    * valueChanged
    **/
  public void valueChanged(ListSelectionEvent e) {
    BBS tempBBS;
    if (e.getSource().equals(listNames)) {
      if (listNames.getSelectedIndex() != -1) {
        tempBBS = (BBS) vectBBSes.elementAt(listNames.getSelectedIndex());
        txtName.setText(tempBBS.getName());
        txtHost.setText(tempBBS.getHost());
        txtPort.setText(tempBBS.getPort() + "");
        txtVersion.setText(tempBBS.getVersion() + "");
      }
    }

    if (!butDelete.isEnabled()) {
      butDelete.setEnabled(true);
    }
  }

  /**
    * getBBS
    **/
  public BBS getBBS() {
    BBS BBStoReturn;

    if (listNames.getSelectedIndex() > -1) {
      BBStoReturn = ((BBS) vectBBSes.elementAt(listNames.getSelectedIndex()));
    } else {
      return null;
    }

    return BBStoReturn;

  }

  /**
    * main
    **/
  public static void main(String argv[]) {
    new BBSList();
  }


}
