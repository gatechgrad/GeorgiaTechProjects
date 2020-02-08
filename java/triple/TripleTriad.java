import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class TripleTriad extends JFrame implements WindowListener, ActionListener {

  /********************** CONSTANTS **********************/
  public static final Rectangle SCREEN_BOUNDS =
    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
  public static final Rectangle INIT_WINDOW_BOUNDS = new Rectangle(50, 50, 400, 300);
  public static final String WINDOW_TITLE = "Triple Triad";

//  public static final String DIRECTORY = "http://www.geocities.com/command/";
//  public static final String DIRECTORY = "D:/triple/build/";
  public static final String DIRECTORY = "D:/ldsmith/presentations/java_gamedev/projects/GeorgiaTechProjects/java/triple/";


  public static final String MENU_GAME = "Game";
  public static final String MENU_HELP = "Help";
  public static final String MI_NEWGAME = "New Game";
  public static final String MI_EXIT = "Exit";
  public static final String MI_OPTIONS = "Options";
  public static final String MI_HELP = "Help Topics";

  public static final JarResources THE_RESOURCES = new JarResources(TripleTriad.DIRECTORY + "TripleTriad.jar");

  /*** CLASS VARS ***/
  private JMenuBar theMenuBar;
  private JMenu menuGame, menuHelp;
  private JMenuItem miNewGame, miExit, miOptions;

  private City cityBalamb, cityGalbadia, cityDollet, cityTrabia,
               cityCentra, cityFH, cityEsthar, cityLunar,
               cityTest;

  private Player balambRunningKid, balambCid, balambMrsDincht;

  private Board myBoard;

  /**
    * TripleTriad - constructor
    **/
  public TripleTriad() {
    //game data stuff

    cityBalamb = new City("Balamb");
    cityBalamb.setRules(new Rules(Rules.TRADE_ONE, true, false, false, false,
                                  false, false, false));
    cityGalbadia = new City("Galbadia");
    cityGalbadia.setRules(new Rules(Rules.TRADE_ONE, false, false, false, true,
                                  false, false, false));
    cityDollet = new City("Dollet");
    cityDollet.setRules(new Rules(Rules.TRADE_ONE, false, false, true, false,
                                  false, false, true));
    cityTrabia = new City("Trabia");
    cityTrabia.setRules(new Rules(Rules.TRADE_ONE, false, false, true, false,
                                  true, false, false));
    cityCentra = new City("Centra");
    cityCentra.setRules(new Rules(Rules.TRADE_ONE, false, false, false, true,
                                  true, true, false));
    cityFH = new City("FH");
    cityFH.setRules(new Rules(Rules.TRADE_ONE, false, true, false, false,
                                  false, false, true));
    cityEsthar = new City("Esthar");
    cityEsthar.setRules(new Rules(Rules.TRADE_ONE, false, false, false, false,
                                  false, true, true));
    cityLunar = new City("Lunar");
    cityLunar.setRules(new Rules(Rules.TRADE_ONE, true, true, true, true,
                                  true, true, true));

    cityTest = new City("A Test");
    cityTest.setRules(new Rules(Rules.TRADE_ONE, true, true, true, true,
                                  true, true, true));

    System.out.println("*** Made it this far");
    myBoard = new Board();

    //add players
    balambRunningKid = new Player("Running Kid");
      balambRunningKid.winCard(myBoard.getCard("Chicobo"));
      balambRunningKid.winCard(myBoard.getCard("MiniMog"));

    balambCid = new Player("Cid");
    balambMrsDincht = new Player("Mrs. Dincht");
    cityBalamb.addPlayer(balambRunningKid);
    cityBalamb.addPlayer(balambCid);
    cityBalamb.addPlayer(balambMrsDincht);


    myBoard.startGame(cityTest.getRules(), balambRunningKid);



    //window stuff
    addWindowListener(this);

    setSize(INIT_WINDOW_BOUNDS.width, INIT_WINDOW_BOUNDS.height);
    setBounds(  ((SCREEN_BOUNDS.width / 2) - (INIT_WINDOW_BOUNDS.width / 2)),
                ((SCREEN_BOUNDS.height / 2) - (INIT_WINDOW_BOUNDS.height / 2)),
                INIT_WINDOW_BOUNDS.width, INIT_WINDOW_BOUNDS.height);   //sets location and
                                                    //size of window<center)

    setTitle(WINDOW_TITLE);

    theMenuBar = new JMenuBar();
      menuGame = new JMenu(MENU_GAME);
      menuGame.add(makeMenuItem(MI_NEWGAME,  MI_NEWGAME, 'N', KeyEvent.VK_N));
      menuGame.add(makeMenuItem(MI_EXIT, MI_EXIT, 'X', KeyEvent.VK_X));

      menuHelp = new JMenu(MENU_HELP);
      menuHelp.add(makeMenuItem(MI_HELP, MI_HELP, 'H', KeyEvent.VK_H));
      theMenuBar.add(menuGame);
      theMenuBar.add(menuHelp);

    setJMenuBar(theMenuBar);

    getContentPane().add(myBoard);
    setVisible(true);
  }

  /**
    * makeMenuItem - creates a JMenuItem
    **/
  public JMenuItem makeMenuItem(String strName, String strCommand,
                                int iMnemonic, int iAcceleratorKey) {
    JMenuItem theMIToReturn;

    theMIToReturn = new JMenuItem(strName);
    theMIToReturn.addActionListener(this);
    theMIToReturn.setActionCommand(strCommand);

    if (iMnemonic != 0) {
      theMIToReturn.setMnemonic((char) iMnemonic);
    }

    if (iAcceleratorKey != 0) {
      theMIToReturn.setAccelerator(KeyStroke.getKeyStroke(iAcceleratorKey,
                                                           Event.CTRL_MASK));
    }

    return theMIToReturn;
  }

  /**
    * ActionListener methods
    **/
  public void actionPerformed(ActionEvent e) {
    String strActionCommand = e.getActionCommand();

    if (strActionCommand.equals(MI_NEWGAME)) {
      myBoard.startGame(cityTest.getRules(), balambRunningKid);
    } else if (strActionCommand.equals(MI_EXIT)) {
      shutdown();
    }

  }

  /**
    * shutdown
    **/
  private void shutdown() {
    System.exit(0);
  }

  /**
    * WindowListener methods
    **/
  public void windowOpened(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowClosing(WindowEvent e) {
    shutdown();
  }
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}



  public static void main(String argv[]) {
    TripleTriad myTripleTriad = new TripleTriad();

  }


}
