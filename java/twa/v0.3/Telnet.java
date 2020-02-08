import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Vector;


class Telnet implements ActionListener {

  /** CONSTANTS **/
  public static final String COMPACT = "Compact";
  public static final String FULL = "Full";
  public static final String BUFFER = "Buffer";

  /** INSTANCE VARIABLES **/
  private DataInputStream theInputStream;
  private BufferedOutputStream theOutputStream;
  private Socket theSocket;
  private int iState;
  private BBS theBBS;
  private TWA theTWA;
  boolean isCompact;


  private JFrame theFrame;
  Sender theSender;
  Receiver theReceiver;

  JComboBox theFontList;
  JComboBox theFontSizeList;
  JButton butCompact;
  JButton butBuffer;


  /**
    * Telnet - constructor
    **/
  public Telnet(BBS theBBS, TWA theTWA) {
    this.theBBS = theBBS;
    this.theTWA = theTWA;

    theSocket = null;
    connect(theBBS.getHost(), theBBS.getPort());
    theSender = new Sender(theOutputStream, theTWA);
    theReceiver = new Receiver(theInputStream, theTWA, this);

    if (theSocket != null) {
      setupWindow();
    }
  }


  public Telnet(String strHost, int iPort) {
    this.theBBS = new BBS("Stand Alone", strHost, iPort, "3.11");
    connect(theBBS.getHost(), theBBS.getPort());
    theSender = new Sender(theOutputStream, theTWA);
    theReceiver = new Receiver(theInputStream, theTWA, this);

    if (theSocket != null) {
      setupWindow();
    }

  }

  /**
    * topBar
    **/
  private JPanel topBar() {
    JPanel pnlToReturn = new JPanel();
    String[] strFontSizes = { "4", "6", "8", "9", "10", "11", "12", "14", "16", "18", "22", "24", "30", "36", "42", "48", "60", "72" };

    theFontList = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    theFontList.setSelectedItem("Courier New");
    theFontList.addActionListener(this);

    theFontSizeList = new JComboBox(strFontSizes);
    theFontSizeList.setEditable(true);
    theFontSizeList.addActionListener(this);
    theFontSizeList.setSelectedIndex(6);

    butCompact = new JButton(COMPACT);
    butCompact.addActionListener(this);
    isCompact = false;

    butBuffer = new JButton(BUFFER);
    butBuffer.addActionListener(this);

    pnlToReturn.add(butBuffer);
    pnlToReturn.add(butCompact);
    pnlToReturn.add(theFontList);
    pnlToReturn.add(theFontSizeList);


    return pnlToReturn;
  }


  /**
    * setupWindow
    **/
  private void setupWindow() {
    theFrame = new JFrame();


    //if the terminal is standalone, then it needs to exit when
    //the window is closed
    if (theTWA == null) {
      WindowListener wl = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
        }
      };

      theFrame.addWindowListener(wl);


    }

    theFrame.getContentPane().setLayout(new BorderLayout());
    theFrame.getContentPane().add(topBar(), BorderLayout.NORTH);
    theFrame.getContentPane().add(theReceiver, BorderLayout.CENTER);
    theFrame.getContentPane().add(theSender, BorderLayout.SOUTH);

    theFrame.setTitle("Connecting to " + theBBS.getName() + " (" + theBBS.getHost() + ")");


    theFrame.pack(); //allocates space for internal components
    theFrame.setLocation(((TWA.SCREEN.width - theFrame.getWidth()) / 2), ((TWA.SCREEN.height - theFrame.getHeight()) / 2));
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

    } catch (IOException e) {
      JOptionPane.showMessageDialog(theFrame, "Could not connect to " + strHost, "Host unavailable", JOptionPane.WARNING_MESSAGE);
      theSocket = null;
    }

  }

  /**
    * disconnect - closes host
    **/
  public void disconnect() {
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

      if (theReceiver != null) {
        iScreenHeight = theReceiver.updateFont((String) theFontList.getSelectedItem(), iHeight);
        iScreenHeight *= theReceiver.getLinesPerScreen() + 1;
        theFrame.pack();
      }


      //System.out.println(theFrame.getInsets());

    } else if (e.getActionCommand().equals(COMPACT)) {
      isCompact = true;
      butCompact.setText(FULL);
      theFrame.setSize(300, 150);

      theReceiver.redraw();
    } else if (e.getActionCommand().equals(FULL)) {
      isCompact = false;
      butCompact.setText(COMPACT);
      theFrame.setSize(300, 150);
      theReceiver.redraw();

    } else if (e.getActionCommand().equals(BUFFER)) {
      JFrame frameBuffer = new JFrame("Session Buffer");
      JScrollPane spBuffer = new JScrollPane();
      JTextArea txtBuffer = new JTextArea(theReceiver.getBuffer());


      spBuffer.getViewport().add(txtBuffer);

      frameBuffer.getContentPane().add(spBuffer);
      frameBuffer.setLocation(50, 50);
      frameBuffer.pack();
      frameBuffer.show();
    }
  }

  /**
    * getSender
    **/
  public Sender getSender() {
    return theSender;
  }

  /**
    * getReceiver
    **/
  public Receiver getReceiver() {
    return theReceiver;
  }


  /**
    * getCompact - returns if the window is in compact mode
    **/
  public boolean getCompact() {
    return isCompact;
  }

  /**
    * main - called when program is created
    **/
  public static void main(String[] argv) {
    if (argv.length == 2) {
      new Telnet(argv[0], (new Integer(argv[1])).intValue());
    } else {
      System.out.println("");
      System.out.println("Telnet - connects to a remote system using terminal emulation");
      System.out.println("         written by Levi D. Smith");
      System.out.println("");
      System.out.println("usage: java Telnet host port");
      System.out.println("  host           the address of the system to connect");
      System.out.println("  port           the port of the system to connect (23 for BBSes)");
    }


  }

}
