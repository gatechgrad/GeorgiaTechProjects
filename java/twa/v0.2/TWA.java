import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TWA implements ActionListener {

  /** CONSTANTS **/
  public static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
  public static final Rectangle WINDOW = new Rectangle((SCREEN.width - 640) / 2,
                                          (SCREEN.height - 400) / 2,
                                          640, 530);
  public static final String TITLE = "Trade Wars Advantage";
  public static final int DEBUG = 1;
                                          

  /** INSTANCE VARIABLES **/
  private Telnet theTelnet;
  private BBS currentBBS;
  private Screen theScreen;
  private Parser theParser;

  private Sector currentSector;

  private JFrame theFrame;
  private JMenuBar theMenuBar;
  private JMenu menuFile, menuHelp, menuTrade, menuAttack, menuMap, menuPlayers;
  private JMenuItem miConnect, miDisconnect, miBBSList, miExit,
                    miFAQ, miAbout, miHomepage,
                    miTradeList, miTrade,
                    miEnemyList, miAllyList;

  /** DEBUGGING STUFF **/
  JComboBox debugComboBox;
  JTextField debugTextField;
  JButton debugOkayButton;


  //menu labels
  private final String FILE = "File";
  private final String HELP = "Help";
  private final String TRADE = "Trade";
  private final String ATTACK = "Attack";
  private final String MAP = "Map";
  private final String CONNECT = "Connect";
  private final String DISCONNECT = "Disconnect";
  private final String BBSLIST = "BBS List";
  private final String EXIT = "Exit";
  private final String FAQ = "FAQ";
  private final String ABOUT = "About";
  private final String HOMEPAGE = "TWA Home Page";
  private final String TRADELIST = "Port Pair List";
  private final String BEGINTRADE = "Begin Port Pair Trade";
  private final String ENEMYLIST = "Enemy List";  //a warning will flash when these players are online or near and they will appear red on the ship's computer
  private final String ALLYLIST = "Ally List"; //these players cannot be attack and are displayed blue on the ship's computer 
  private final String PLAYERS = "Players";

  /**
    * TWA
    **/
  public TWA() {


    setupWindow();
    currentBBS = null;


  }

  /**
    * handleCommand - handles the command line passed in
    **/
  public void handleCommand(String strCommand) {


  }

  /**
    * setupWindow - sets up the helper window
    **/
  private void setupWindow() {
    theFrame = new JFrame();
    theScreen = new Screen(this);

    WindowListener waWindow = new WindowAdapter () {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };

    theFrame.setFont(new Font("Terminal", Font.PLAIN, 12));

    theFrame.addWindowListener(waWindow);
    theFrame.setTitle(TITLE);
    theFrame.setIconImage(theScreen.imgIcon);

    miExit = new JMenuItem(EXIT);


    theFrame.getContentPane().add(theScreen);

    menuFile = new JMenu(FILE);
    menuFile.setMnemonic('F');
      miConnect = new JMenuItem(CONNECT);
      miConnect.addActionListener(this);
      miConnect.setMnemonic('C');
      miConnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
      menuFile.add(miConnect);
      miConnect.setEnabled(false);

      miDisconnect = new JMenuItem(DISCONNECT);
      miDisconnect.addActionListener(this);
      miDisconnect.setMnemonic('D');
      miDisconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
      menuFile.add(miDisconnect);
      miDisconnect.setEnabled(false);

      menuFile.addSeparator();

      miBBSList = new JMenuItem(BBSLIST);
      miBBSList.addActionListener(this);
      miBBSList.setMnemonic('L');
      miBBSList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuFile.add(miBBSList);

      menuFile.addSeparator();

      miExit = new JMenuItem(EXIT);
      miExit.addActionListener(this);
      miExit.setMnemonic('X');
      miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
      menuFile.add(miExit);

    menuHelp = new JMenu(HELP);
    menuHelp.setMnemonic('H');
      miFAQ = new JMenuItem(FAQ);
      miFAQ.addActionListener(this);
      miFAQ.setMnemonic('F');
      miFAQ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
      menuHelp.add(miFAQ);

      miAbout = new JMenuItem(ABOUT);
      miAbout.addActionListener(this);
      miAbout.setMnemonic('A');
      miAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
      menuHelp.add(miAbout);

      miHomepage = new JMenuItem(HOMEPAGE);
      miHomepage.addActionListener(this);
      miHomepage.setMnemonic('H');
      miHomepage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
      menuHelp.add(miHomepage);

    menuTrade = new JMenu(TRADE);
    menuTrade.setMnemonic('T');
      miTradeList = new JMenuItem(TRADELIST);
      miTradeList.addActionListener(this);
      miTradeList.setMnemonic('L');
      miTradeList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuTrade.add(miTradeList);

      miTrade = new JMenuItem(BEGINTRADE);
      miTrade.addActionListener(this);
      miTrade.setMnemonic('T');
      miTrade.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
      menuTrade.add(miTrade);

    menuMap = new JMenu(MAP);
    menuMap.setMnemonic('M');

    menuAttack = new JMenu(ATTACK);
    menuAttack.setMnemonic('A');

    menuPlayers = new JMenu(PLAYERS);
    menuPlayers.setMnemonic('H');
      miEnemyList = new JMenuItem(ENEMYLIST);
      miEnemyList.addActionListener(this);
      miEnemyList.setMnemonic('E');
      miEnemyList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
      menuPlayers.add(miEnemyList);

      miAllyList = new JMenuItem(ALLYLIST);
      miAllyList.addActionListener(this);
      miAllyList.setMnemonic('A');
      miAllyList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
      menuPlayers.add(miAllyList);



    theMenuBar = new JMenuBar();
      theMenuBar.add(menuFile);
//      theMenuBar.setHelpMenu(menuHelp); //ummm... it says it's not implemented yet
      theMenuBar.add(menuTrade);
      theMenuBar.add(menuPlayers);
      theMenuBar.add(menuMap);
      theMenuBar.add(menuAttack);
      theMenuBar.add(menuHelp);

    theFrame.setJMenuBar(theMenuBar);

    theFrame.setBounds(WINDOW.x, WINDOW.y, WINDOW.width + 8,
                       WINDOW.height);


    //the picture is distorted because of the menu and title bars
    Insets theInsets = theFrame.getContentPane().getInsets();

    theFrame.setVisible(true);

    
  }

  /**
    * beginConnect
    **/
  public void beginConnect() {
      theParser = new Parser(this);
      theTelnet = new Telnet(currentBBS, this);
      theScreen.setState(Screen.START);
  }

  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strActionCommand;

    strActionCommand = e.getActionCommand();

    if (strActionCommand.equals(CONNECT)) {
      beginConnect();
    } else if (strActionCommand.equals(BBSLIST)) {
      BBSList theBBSList = new BBSList(this);

    } else if (strActionCommand.equals(EXIT)) {
      theFrame.setVisible(false);
      System.exit(0);
    }

    /** DEBUGGING STUFF **/
    /** I DID THIS SLOPPY SINCE IT IS ONLY DEBUG CODE **/
    if (strActionCommand.equals("Debug Okay")) {
      int iValue;
      String strChoice;
      try {
        iValue = (new Integer(debugTextField.getText())).intValue();
      } catch (NumberFormatException e2) {
        iValue = 0;
      }
      strChoice = debugComboBox.getSelectedItem().toString();

      if (strChoice.equals("Add Turns")) {
        getBBS().getGameData().addTurns(iValue);
      } else if (strChoice.equals("Add Fuel Ore")) {
        getBBS().getGameData().addFuelOre(iValue);
      } else if (strChoice.equals("Add Organics")) {
        getBBS().getGameData().addOrganics(iValue);
      } else if (strChoice.equals("Add Equipment")) {
        getBBS().getGameData().addEquipment(iValue);
      }
      theScreen.repaint();
    }

  }
    

  /**
    * setBBS
    **/
  public void setBBS(BBS theBBS) {
    this.currentBBS = theBBS;
    miConnect.setEnabled(true);
    miDisconnect.setEnabled(true);

    theScreen.repaint();
    setupDebug();
  }

  /**
    * setupDebug
    **/
  private void setupDebug() {
    if (DEBUG == 1) {
      JDialog dialogDebug = new JDialog(theFrame);
      dialogDebug.setBounds(50, 50, 150, 100);

      String[] strDebugChoices = { "Add Turns", "Add GTorps", "Add Fuel Ore", "Add Organics", "Add Equipment" };
      debugComboBox = new JComboBox(strDebugChoices);
      debugTextField = new JTextField(5);
      debugOkayButton = new JButton("Debug Okay");
      debugOkayButton.addActionListener(this);

      dialogDebug.getContentPane().setLayout(new BorderLayout());
      dialogDebug.getContentPane().add(debugComboBox, BorderLayout.CENTER);
      dialogDebug.getContentPane().add(debugTextField, BorderLayout.EAST);
      dialogDebug.getContentPane().add(debugOkayButton, BorderLayout.SOUTH);
      dialogDebug.setVisible(true);
   
    }

  }

  /** ACCESSOR METHODS **/

  /**
    * getBBS
    **/
  public BBS getBBS() {
    return currentBBS;
  }

  /**
    * getParser
    **/
  public Parser getParser() {
    return theParser;
  }

  /**
    * getScreen
    **/
  public Screen getScreen() {
    return theScreen;
  }

  /**
    * getTelnet
    **/
  public Telnet getTelnet() {
    return theTelnet;
  }

  /**
    * getCurrentSector
    **/
  public Sector getCurrentSector() {
    return currentSector;
  }


  /**
    * setTelnet - used by BBSList
    **/
  public void setTelnet(Telnet theTelnet) {
    this.theTelnet = theTelnet;
  }

  /**
    * setCurrentSector
    **/
  public void setCurrentSector(Sector theSector) {
    if (theSector != null) {
      currentSector = currentBBS.getGameData().findSector(theSector.getNumber());
    }
  }



  /**
    * main
    **/
  public static void main(String[] args) {
    new TWA();

  }

}
