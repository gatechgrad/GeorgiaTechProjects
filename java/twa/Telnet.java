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
  public static final String BUFFER_SAVE = "Save All";
  public static final String BUFFER_SAVE_SELECTED = "Save Selected";
  public static final String FONT = "Font";
  public static final String EXIT = "Exit";


  /** INSTANCE VARIABLES **/
  private DataInputStream theInputStream;
  private BufferedOutputStream theOutputStream;
  private Socket theSocket;
  private int iState;
  private BBS theBBS;
  private TWA theTWA;
  boolean isCompact;


  private JFrame theFrame;
  private JFrame frameBuffer;
  private JTextArea txtBuffer;
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
//      theSender.grabFocus();
    }
  }


  public Telnet(String strHost, int iPort) {
    this.theBBS = new BBS("Stand Alone", strHost, iPort, "3.11");
    connect(theBBS.getHost(), theBBS.getPort());
    theSender = new Sender(theOutputStream, theTWA);
    theReceiver = new Receiver(theInputStream, theTWA, this);

    if (theSocket != null) {
      setupWindow();
//      theSender.grabFocus();

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
    butCompact.setEnabled(false);
    butCompact.addActionListener(this);
    isCompact = false;

    butBuffer = new JButton(BUFFER);


//until I find out how to get the buffer to work
    butBuffer.setEnabled(false);
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
          theFrame.dispose();
          System.exit(0);
        }
      };

      theFrame.addWindowListener(wl);
    } else {
      WindowListener wl = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          theFrame.hide();
        }
      };

      theFrame.addWindowListener(wl);

    }

    theFrame.getContentPane().setLayout(new BorderLayout());
    //theFrame.getContentPane().add(topBar(), BorderLayout.NORTH);
    theFrame.getContentPane().add(theReceiver, BorderLayout.CENTER);
    //theFrame.getContentPane().add(theSender, BorderLayout.SOUTH);

    JMenuBar theMenuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
      JMenuItem miFont = new JMenuItem(FONT);
      miFont.addActionListener(this);
      miFont.setMnemonic('F');
      menuFile.add(miFont);
      miFont.setEnabled(false);

      JMenuItem miBuffer = new JMenuItem(BUFFER);
      miBuffer.addActionListener(this);
      miBuffer.setMnemonic('B');
      menuFile.add(miBuffer);
      miBuffer.setEnabled(false);


      JMenuItem miExit = new JMenuItem(EXIT);
      miExit.addActionListener(this);
      miExit.setMnemonic('X');
      menuFile.add(miExit);
      miExit.setEnabled(false);

    theMenuBar.add(menuFile);

    theFrame.setJMenuBar(theMenuBar);

    theReceiver.updateFont("Courier New", 12);

    theFrame.setTitle("Console: " + theBBS.getName() + " (" + theBBS.getHost() + ")");
    theFrame.setResizable(false);

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
      JOptionPane.showMessageDialog(frameBuffer, "Could not connect to " + strHost, "Host unavailable", JOptionPane.WARNING_MESSAGE);
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
        theReceiver.noCarrier();
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
      makeBufferWindow();
    } else if (e.getActionCommand().equals(BUFFER_SAVE)) {
      saveBufferWindow(txtBuffer.getText());
    } else if (e.getActionCommand().equals(BUFFER_SAVE_SELECTED)) {
      saveBufferWindow(txtBuffer.getSelectedText());
    }
  }

  /**
    * makeBufferWindow
    **/
  private void makeBufferWindow() {
    frameBuffer = new JFrame("Session Buffer");
    frameBuffer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JScrollPane spBuffer = new JScrollPane();
    txtBuffer = new JTextArea(theReceiver.getBuffer());

    JMenuBar mbBuffer = new JMenuBar();
    JMenu menuFile = new JMenu("File");
      JMenuItem miSave = new JMenuItem(BUFFER_SAVE);
      miSave.addActionListener(this);
      miSave.setMnemonic('S');
      miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
      menuFile.add(miSave);

      JMenuItem miSaveSelected = new JMenuItem(BUFFER_SAVE_SELECTED);
      miSaveSelected.addActionListener(this);
      miSaveSelected.setMnemonic('L');
      miSaveSelected.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, Event.CTRL_MASK));
      menuFile.add(miSaveSelected);


    txtBuffer.setForeground(Screen.FG_COLOR);
    txtBuffer.setBackground(Screen.BKG_COLOR);
    if (theReceiver != null) {
      txtBuffer.setFont(theReceiver.getFont());
    }

    spBuffer.getViewport().add(txtBuffer);
    spBuffer.setPreferredSize(new Dimension(640, 480));

    mbBuffer.add(menuFile);
    frameBuffer.setJMenuBar(mbBuffer);

    frameBuffer.getContentPane().add(spBuffer);
    frameBuffer.pack();
    frameBuffer.setLocation(((TWA.SCREEN.width - frameBuffer.getWidth()) / 2), ((TWA.SCREEN.height - frameBuffer.getHeight()) / 2));

    frameBuffer.show();

  }

  /**
    * saveBufferWindow
    **/
  public void saveBufferWindow(String strToWrite) {
    JFileChooser fcSaveData = new JFileChooser();
    File fileDirectory;
    File fileSave;


    if ((strToWrite != null) && (!strToWrite.equals(""))) {
      fileDirectory = new File("./buffers");
      fileDirectory.mkdir();

      fcSaveData.setCurrentDirectory(new File("./buffers"));
      fcSaveData.setSelectedFile(new File(".txt"));
      fcSaveData.showSaveDialog(frameBuffer);
      fileSave = fcSaveData.getSelectedFile();
      if (fileSave != null) {
        FileWriter theFileWriter;
        PrintWriter thePrintWriter;

        try {
          theFileWriter = new FileWriter(fileSave);
          thePrintWriter = new PrintWriter(theFileWriter);
          thePrintWriter.print(strToWrite);
          thePrintWriter.close();
        } catch (IOException e) { }
      }
    } else {
      JOptionPane.showMessageDialog(frameBuffer, "No data to write", "Error", JOptionPane.WARNING_MESSAGE);
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
    * getFrame
    **/
  public JFrame getFrame() {
    return theFrame;
  }

  /**
    * isConnected
    **/
  public boolean isConnected() {
    boolean isConnected;
    isConnected = false;

    return isConnected;

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
