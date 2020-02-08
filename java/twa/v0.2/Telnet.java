import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Vector;


class Telnet implements ActionListener {

  /** INSTANCE VARIABLES **/
  private DataInputStream theInputStream;
  private BufferedOutputStream theOutputStream;
  private Socket theSocket;
  private int iState;
  private BBS theBBS;
  private TWA theTWA;

  private JFrame theFrame;
  Sender theSender;
  Receiver theReceiver;

  JComboBox theFontList;
  JComboBox theFontSizeList;


  /**
    * Telnet - constructor
    **/
  public Telnet(BBS theBBS, TWA theTWA) {
    this.theBBS = theBBS;
    this.theTWA = theTWA;

    connect(theBBS.getHost(), theBBS.getPort());
    theSender = new Sender(theOutputStream);
    theReceiver = new Receiver(theInputStream, theTWA);
    theReceiver.setFont(new Font("Courier New", Font.PLAIN, 12));
    setupWindow();

  }


  public Telnet() {
    this.theBBS = new BBS("INDEX", "indexbbs.com", 23, 42);
    connect(theBBS.getHost(), theBBS.getPort());
    theSender = new Sender(theOutputStream);
    theReceiver = new Receiver(theInputStream, theTWA);
    setupWindow();

  }

  /**
    * topBar
    **/
  private JPanel topBar() {
    JPanel pnlToReturn = new JPanel();
    String[] strFontSizes = { "4", "6", "8", "9", "10", "11", "12", "14", "16", "18", "22", "24", "30", "36", "42", "48", "60", "72" };

    theFontList = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    theFontList.addActionListener(this);

    theFontSizeList = new JComboBox(strFontSizes);
    theFontSizeList.setEditable(true);
    theFontSizeList.addActionListener(this);
    theFontSizeList.setSelectedIndex(6);

    pnlToReturn.add(theFontList);
    pnlToReturn.add(theFontSizeList);


    return pnlToReturn;
  }


  /**
    * setupWindow
    **/
  private void setupWindow() {
    theFrame = new JFrame();



    theFrame.getContentPane().setLayout(new BorderLayout());
    theFrame.getContentPane().add(topBar(), BorderLayout.NORTH);
    theFrame.getContentPane().add(theReceiver, BorderLayout.CENTER);
    theFrame.getContentPane().add(theSender, BorderLayout.SOUTH);

    theFrame.setTitle("Connecting to " + theBBS.getName() + " (" + theBBS.getHost() + ")");
    theFrame.setBounds(TWA.WINDOW.x + 20, TWA.WINDOW.y + 20, TWA.WINDOW.width - 40, TWA.WINDOW.height - 40);
    theFrame.show();
    
  }

  /**
    * connect - connects to host
    **/
  private void connect(String strHost, int iPort) {
    try {
      theSocket = new Socket(strHost, iPort);
      theOutputStream = new BufferedOutputStream(theSocket.getOutputStream());
      theInputStream = new DataInputStream(theSocket.getInputStream());
      iState = 0;

    } catch (IOException e) { }

  }

  /**
    * disconnect - closes host
    **/
  private void disconnect() {
    try {
      if (theSocket != null) {
        theSocket.close();
      }
    } catch (IOException e) { }
  }


  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    int iHeight;
    int iScreenHeight;

    if (e.getSource().equals(theFontList) || e.getSource().equals(theFontSizeList)) {
      try {
        iHeight = (new Integer(theFontSizeList.getSelectedItem().toString())).intValue();
      } catch (NumberFormatException e2) {
        iHeight = 12;
      }

      iScreenHeight = theReceiver.setFont((String) theFontList.getSelectedItem(), iHeight);
      iScreenHeight *= theReceiver.getLinesPerScreen() + 1;
      theFrame.setSize(640, iScreenHeight + 70);

      //System.out.println(theFrame.getInsets());

    }
  }

  /**
    * getSender
    **/
  public Sender getSender() {
    return theSender;
  }

  /**
    * main - called when program is created
    **/
  public static void main(String[] argv) {
    new Telnet();

  }

}
