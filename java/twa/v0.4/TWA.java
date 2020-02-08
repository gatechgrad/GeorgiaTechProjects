import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.StringTokenizer;
import java.util.Vector;


class TWA implements ActionListener {

  /** CONSTANTS **/
  public static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
  public static final Rectangle WINDOW = new Rectangle((SCREEN.width - 640) / 2,
                                          (SCREEN.height - 400) / 2,
                                          640, 530);
  public static final String TITLE = "Trade Wars Advantage";
  public static final String HOMEPAGE_URL = "http://www.prism.gatech.edu/~gte187k/twa";

                                          

  /** INSTANCE VARIABLES **/
  private Telnet theTelnet;
  private BBS currentBBS;
  private Screen theScreen;
  private Parser theParser;

  private Sector currentSector;

  private JFrame theFrame;
  private JMenuBar theMenuBar;
  private JMenu menuFile, menuHelp, menuTrade, menuAttack, menuMap,
                menuPlayers, menuPlanet, menuCheats;
  private JMenuItem miConnect, miDisconnect, miBBSList, miExit, miSave, miLoad,
                    miShowConsole, miStopScript,
                    miFAQ, miAbout, miHomepage,
                    miTradeList, miTrade, 
                    miColonize, miPlanetGenerator, miCitadelPreparation,
                    miDontSelectThis,
                    miViewSector, miViewMap, miViewPlanet, miHoloScan,
                    miGoto,
                    miInterrigationMap, miEitherProbeMap, miExplore,
                    miEnemyList, miAllyList,
                    miPlanetOverloadAttack, miPhotonAttack,
                    miPodding, miPlanetSacrifice;




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
  private final String SAVE = "Save Data";
  private final String LOAD = "Load Data";
  private final String FAQ = "FAQ";
  private final String ABOUT = "About";
  private final String HOMEPAGE = "TWA Home Page";
  private final String TRADELIST = "Port Pair List";
  private final String BEGINTRADE = "Begin Port Pair Trade";
  private final String ENEMYLIST = "Enemy List";  //a warning will flash when these players are online or near and they will appear red on the ship's computer
  private final String ALLYLIST = "Ally List"; //these players cannot be attack and are displayed blue on the ship's computer 
  private final String PLAYERS = "Players";
  private final String COLONIZE = "Colonize / Add Resources";
  private final String PLANET = "Planet";
  private final String CHEATS = "Cheats";
  private final String PLANET_GENERATOR = "Planet Generator";
  private final String CITADEL_PREPARATION = "Citadel Preparation";
  private final String VIEW_SECTOR = "View Sector";
  private final String VIEW_MAP = "View Map";
  private final String VIEW_PLANET = "View Planet";
  private final String INTERRIGATION_MAP = "Interrigation Mapping";
  private final String EITHER_PROBE_MAP = "Either Probe Mapping";
  private final String EXPLORE = "Explore";
  private final String PLANET_OVERLOAD = "Planet Overload";
  private final String PHOTON_ATTACK = "Photon Attack";
  private final String PODDING = "Podding";
  private final String PLANET_SACRIFICE = "Planet Sacrifice";
  private final String HOLO_SCAN = "Holo Scan";
  private final String SHOW_CONSOLE = "Show Console";
  private final String HIDE_CONSOLE = "Hide Console";
  private final String STOP_SCRIPT = "Stop Script";
  private final String MOVE_TO_SECTOR = "Move To Sector";


  /**
    * TWA
    **/
  public TWA() {
    setupWindow();
    Screen.soundStart.play();
    currentBBS = null;


    /** TESTING **/
/*
    currentBBS = new BBS("INDEX", "indexbbs.com", 23, "3.11");
    Sector sectorTemp = new Sector(1, this);
    Sector sectorTemp2 = new Sector(5, this);

    Vector vectTemp = new Vector();
    Vector vectTemp2 = new Vector();

    vectTemp.add(new Sector(2, this));
    vectTemp.add(new Sector(3, this));
    vectTemp.add(new Sector(4, this));
    vectTemp.add(sectorTemp2);

    vectTemp2.add(new Sector(6, this));
    vectTemp2.add(new Sector(7, this));
    vectTemp2.add(new Sector(8, this));
    vectTemp2.add(sectorTemp);

    getBBS().getGameData().addSector(sectorTemp);
    getBBS().getGameData().addSector(sectorTemp2);
    sectorTemp2.setAdjacentSectors(vectTemp2);


    setCurrentSector(getBBS().getGameData().findSector(1));
    getCurrentSector().setAdjacentSectors(vectTemp);
*/

    /*************/

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
        shutdown();
        //System.exit(0);
      }
    };

    theFrame.setFont(new Font("Courier New", Font.PLAIN, 12));

    theFrame.addWindowListener(waWindow);
    theFrame.setTitle(TITLE);
    theFrame.setIconImage(theScreen.imgIcon);

    miExit = new JMenuItem(EXIT);


    theFrame.getContentPane().add(theScreen);

    menuFile = new JMenu(FILE);
    menuFile.setMnemonic('F');
      miSave = new JMenuItem(SAVE);
      miSave.addActionListener(this);
      miSave.setMnemonic('S');
      miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
      menuFile.add(miSave);
      miSave.setEnabled(false);

      miLoad = new JMenuItem(LOAD);
      miLoad.addActionListener(this);
      miLoad.setMnemonic('L');
      miLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
      menuFile.add(miLoad);
      miLoad.setEnabled(false);

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
      miBBSList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
      menuFile.add(miBBSList);

      menuFile.addSeparator();

      miShowConsole = new JMenuItem(SHOW_CONSOLE);
      miShowConsole.addActionListener(this);
      miShowConsole.setMnemonic('H');
      miShowConsole.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
      menuFile.add(miShowConsole);
      miShowConsole.setEnabled(false);

      miStopScript = new JMenuItem(STOP_SCRIPT);
      miStopScript.addActionListener(this);
      miStopScript.setMnemonic('H');
      miStopScript.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
      menuFile.add(miStopScript);
      miStopScript.setEnabled(false);

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
//      miFAQ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
      menuHelp.add(miFAQ);
      miFAQ.setEnabled(false);

      miAbout = new JMenuItem(ABOUT);
      miAbout.addActionListener(this);
      miAbout.setMnemonic('A');
//      miAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
      menuHelp.add(miAbout);
      miAbout.setEnabled(false);

      miHomepage = new JMenuItem(HOMEPAGE);
      miHomepage.addActionListener(this);
      miHomepage.setMnemonic('H');
//      miHomepage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
      menuHelp.add(miHomepage);
      miHomepage.setEnabled(false);

    menuTrade = new JMenu(TRADE);
    menuTrade.setMnemonic('T');
      miTradeList = new JMenuItem(TRADELIST);
      miTradeList.addActionListener(this);
      miTradeList.setMnemonic('L');
      miTradeList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuTrade.add(miTradeList);
      miTradeList.setEnabled(false);

      miTrade = new JMenuItem(BEGINTRADE);
      miTrade.addActionListener(this);
      miTrade.setMnemonic('P');
      miTrade.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
      menuTrade.add(miTrade);
      miTrade.setEnabled(false);

    menuMap = new JMenu(MAP);
    menuMap.setMnemonic('M');

      miGoto = new JMenuItem(MOVE_TO_SECTOR);
      miGoto.addActionListener(this);
      miGoto.setMnemonic('L');
      miGoto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK));
      menuMap.add(miGoto);
      miGoto.setEnabled(false);

      miViewSector = new JMenuItem(VIEW_SECTOR);
      miViewSector.addActionListener(this);
      miViewSector.setMnemonic('L');
//      miViewSector.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuMap.add(miViewSector);
      miViewSector.setEnabled(false);

      miViewMap = new JMenuItem(VIEW_MAP);
      miViewMap.addActionListener(this);
      miViewMap.setMnemonic('m');
      miViewMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
      menuMap.add(miViewMap);
      miViewMap.setEnabled(false);

      miViewPlanet = new JMenuItem(VIEW_PLANET);
      miViewPlanet.addActionListener(this);
      miViewPlanet.setMnemonic('L');
//      miViewPlanet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuMap.add(miViewPlanet);
      miViewPlanet.setEnabled(false);

      miInterrigationMap = new JMenuItem(INTERRIGATION_MAP);
      miInterrigationMap.addActionListener(this);
      miInterrigationMap.setMnemonic('L');
//      miInterrigationMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuMap.add(miInterrigationMap);
      miInterrigationMap.setEnabled(false);

      miEitherProbeMap = new JMenuItem(EITHER_PROBE_MAP);
      miEitherProbeMap.addActionListener(this);
      miEitherProbeMap.setMnemonic('L');
//      miEitherProbeMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuMap.add(miEitherProbeMap);
      miEitherProbeMap.setEnabled(false);

      miExplore = new JMenuItem(EXPLORE);
      miExplore.addActionListener(this);
      miExplore.setMnemonic('L');
//      miExplore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuMap.add(miExplore);
      miExplore.setEnabled(false);

      miHoloScan = new JMenuItem(HOLO_SCAN);
      miHoloScan.addActionListener(this);
      miHoloScan.setMnemonic('S');
      miHoloScan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
      menuMap.add(miHoloScan);
      miHoloScan.setEnabled(false);


    menuAttack = new JMenu(ATTACK);
    menuAttack.setMnemonic('A');
      miPlanetOverloadAttack = new JMenuItem(PLANET_OVERLOAD);
      miPlanetOverloadAttack.addActionListener(this);
      miPlanetOverloadAttack.setMnemonic('L');
      miPlanetOverloadAttack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuAttack.add(miPlanetOverloadAttack);
      miPlanetOverloadAttack.setEnabled(false);

      miPhotonAttack = new JMenuItem(PHOTON_ATTACK);
      miPhotonAttack.addActionListener(this);
      miPhotonAttack.setMnemonic('L');
//      miPhotonAttack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
      menuAttack.add(miPhotonAttack);
      miPhotonAttack.setEnabled(false);




    menuPlayers = new JMenu(PLAYERS);
    menuPlayers.setMnemonic('Y');
      miEnemyList = new JMenuItem(ENEMYLIST);
      miEnemyList.addActionListener(this);
      miEnemyList.setMnemonic('E');
//      miEnemyList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
      menuPlayers.add(miEnemyList);
      miEnemyList.setEnabled(false);

      miAllyList = new JMenuItem(ALLYLIST);
      miAllyList.addActionListener(this);
      miAllyList.setMnemonic('A');
//      miAllyList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
      menuPlayers.add(miAllyList);
      miAllyList.setEnabled(false);

    menuPlanet = new JMenu(PLANET);
    menuPlanet.setMnemonic('p');
      miColonize = new JMenuItem(COLONIZE);
      miColonize.addActionListener(this);
      miColonize.setMnemonic('C');
      miColonize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
      menuPlanet.add(miColonize);
      miColonize.setEnabled(false);

      miPlanetGenerator = new JMenuItem(PLANET_GENERATOR);
      miPlanetGenerator.addActionListener(this);
      miPlanetGenerator.setMnemonic('G');
//      miPlanetGenerator.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
      menuPlanet.add(miPlanetGenerator);
      miPlanetGenerator.setEnabled(false);

      miCitadelPreparation = new JMenuItem(CITADEL_PREPARATION);
      miCitadelPreparation.addActionListener(this);
      miCitadelPreparation.setMnemonic('G');
//      miCitadelPreparation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
      menuPlanet.add(miCitadelPreparation);
      miCitadelPreparation.setEnabled(false);


    menuCheats = new JMenu(CHEATS);
    menuCheats.setMnemonic('c');
      miPodding = new JMenuItem(PODDING);
      miPodding.addActionListener(this);
      miPodding.setMnemonic('o');
//      miPodding.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
      menuCheats.add(miPodding);
      miPodding.setEnabled(false);

      miPlanetSacrifice = new JMenuItem(PLANET_SACRIFICE);
      miPlanetSacrifice.addActionListener(this);
      miPlanetSacrifice.setMnemonic('G');
//      miPlanetSacrifice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
      menuCheats.add(miPlanetSacrifice);
      miPlanetSacrifice.setEnabled(false);



    theMenuBar = new JMenuBar();
      theMenuBar.add(menuFile);
//      theMenuBar.setHelpMenu(menuHelp); //ummm... it says it's not implemented yet
      theMenuBar.add(menuTrade);
      theMenuBar.add(menuPlayers);
      theMenuBar.add(menuMap);
      theMenuBar.add(menuPlanet);
      theMenuBar.add(menuAttack);
      theMenuBar.add(menuCheats);
      theMenuBar.add(menuHelp);

    theFrame.setJMenuBar(theMenuBar);


    theFrame.pack(); //allocates space for internal components
    theFrame.setLocation(((SCREEN.width - theFrame.getWidth()) / 2), ((SCREEN.height - theFrame.getHeight()) / 2));
    theFrame.show();
    
  }

  /**
    * beginConnect
    **/
  public void beginConnect() {

    if (theTelnet == null) {
      theParser = new Parser(this);
      theTelnet = new Telnet(currentBBS, this);
      theScreen.setState(Screen.START);

      //allow the user to click on menuitems
      miTrade.setEnabled(true);
      miTradeList.setEnabled(true);
      miHoloScan.setEnabled(true);
      miColonize.setEnabled(true);
      miShowConsole.setEnabled(true);
      miGoto.setEnabled(true);
      miViewMap.setEnabled(true);
      miStopScript.setEnabled(true);
//      miPodding.setEnabled(true);
    }
  }

  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strActionCommand;

    strActionCommand = e.getActionCommand();

    if (strActionCommand.equals(CONNECT)) {
      beginConnect();
    } else if (strActionCommand.equals(DISCONNECT)) {
      if (theTelnet != null) {
        theTelnet.disconnect();
        theTelnet = null;
        currentBBS = null;
        theScreen.setState(Screen.START);
      }

      miTrade.setEnabled(false);
      miTradeList.setEnabled(false);
      miHoloScan.setEnabled(false);
      miColonize.setEnabled(false);


    } else if (strActionCommand.equals(SAVE)) {
      saveData();
    } else if (strActionCommand.equals(LOAD)) {
      loadData();
    } else if (strActionCommand.equals(HOLO_SCAN)) {
      getTelnet().getSender().sendData("sh");

    } else if (strActionCommand.equals(SHOW_CONSOLE)) {
      if (theTelnet != null) {
        theTelnet.getFrame().show();
      } else {
        JOptionPane.showMessageDialog(theFrame, "Console is not available", "Error", JOptionPane.WARNING_MESSAGE);
      }

    } else if (strActionCommand.equals(STOP_SCRIPT)) {
      theParser.stopScripts();
    } else if (strActionCommand.equals(INTERRIGATION_MAP)) {
      System.out.println("Interrigating");
      getTelnet().getSender().sendData( (char) 200);
      getTelnet().getSender().sendData( (char) 201);
      getTelnet().getSender().sendData( (char) 202);
      getTelnet().getSender().sendData( (char) 203);
      getTelnet().getSender().sendData( (char) 204);
      getTelnet().getSender().sendData( (char) 205);


    } else if (strActionCommand.equals(BBSLIST)) {
      BBSList theBBSList = new BBSList(this);

    } else if (strActionCommand.equals(BEGINTRADE)) {
      new PortPairDialog(theFrame, this);
    } else if (strActionCommand.equals(COLONIZE)) {
      new ColonizeDialog(theFrame, this);

    } else if (strActionCommand.equals(TRADELIST)) {
      makePortPairList();
    } else if (strActionCommand.equals(VIEW_MAP)) {
      new MapDialog(theFrame, this);
    } else if (strActionCommand.equals(MOVE_TO_SECTOR)) {
      new GotoDialog(theFrame, this);

    } else if (strActionCommand.equals(PODDING)) {
      dontSelectThis();

    } else if (strActionCommand.equals(HOMEPAGE)) {
      System.out.println("Opening homepage");
      try {
        Runtime.getRuntime().exec(HOMEPAGE_URL);

      } catch (IOException e2) {
        JOptionPane.showMessageDialog(theFrame, "Could not load " + HOMEPAGE_URL, "Error", JOptionPane.WARNING_MESSAGE);
      }


    } else if (strActionCommand.equals(EXIT)) {
      shutdown();
    }
  }

  /**
    * shutdown
    **/
  private void shutdown() {
  /*
    if ((theTelnet != null) && theTelnet.isConnected()) {
      int iChoice; 
      iChoice = JOptionPane.showConfirmDialog(theFrame, "You are still connected to " + getBBS().getName() + ".\nExit anyway?", "Connection", JOptionPane.YES_NO_CANCEL_OPTION);
      System.out.println("Choice: " + iChoice);

      if (iChoice == JOptionPane.YES_OPTION) {
        theFrame.setVisible(false);
        theTelnet.disconnect();
        System.exit(0);

      }
    */

      theFrame.setVisible(false);
      System.exit(0);
  }

  /**
    * loadData
    **/
  private void loadData() {
      JFileChooser fcLoadData = new JFileChooser();
      int iChar;
      String strGameData;
      StringBuffer myBuffer = new StringBuffer();
      String strTemp;
      int iCurrentSector, iSector;
      Vector vectTemp;
      Sector sectorTemp;

      if (getBBS() != null) {
        fcLoadData.setCurrentDirectory(new File("./gamedata"));
        fcLoadData.showOpenDialog(theFrame);
        File fileLoad = fcLoadData.getSelectedFile();
        if (fileLoad != null) {
          try {
            miViewMap.setEnabled(true);
            FileReader fileToRead = new FileReader(fileLoad);


            //read all of the characters from the file and put them into the
            //StringBuffer, myBuffer
            iChar = fileToRead.read();
            while(iChar != -1) {
            myBuffer.append((char) iChar);
              iChar = fileToRead.read();
            }
      
            fileToRead.close();
      
          } catch (IOException e) { }


          strGameData = myBuffer.toString();

          StringTokenizer tokenLine = new StringTokenizer(strGameData, "\n");

          while(tokenLine.hasMoreTokens()) {

            StringTokenizer stCells = new StringTokenizer(tokenLine.nextToken(), "|");
            if (stCells.hasMoreTokens()) {
              strTemp = stCells.nextToken();


              /** ADJACENCIES **/
              if (strTemp.equals("ADJ")) {
                System.out.println("Making adjacencies");

                if (stCells.hasMoreTokens()) {
                  strTemp = stCells.nextToken();
                  try {
                    iCurrentSector = (new Integer(strTemp)).intValue();
                  } catch (NumberFormatException e) {
                    iCurrentSector = 0;
                  }
                  sectorTemp = new Sector(iCurrentSector, this);
                  getBBS().getGameData().addSector(sectorTemp);

                  vectTemp = new Vector();
                  while (stCells.hasMoreTokens()) {
                    strTemp = stCells.nextToken();

                    try {
                      iSector = (new Integer(strTemp)).intValue();
                      sectorTemp = new Sector(iSector, this);
                      sectorTemp.setExplored(false);
                      getBBS().getGameData().addSector(sectorTemp);
                      vectTemp.addElement(getBBS().getGameData().findSector(iSector));
                    } catch (NumberFormatException e) { }
                  }
                  getBBS().getGameData().findSector(iCurrentSector).setAdjacentSectors(vectTemp);

                }
              }

              /** PORTS **/

            }


          }
        }
      } else {
        JOptionPane.showMessageDialog(theFrame, "No game loaded", "Error", JOptionPane.WARNING_MESSAGE);
      }



  }


  /**
    * saveData
    **/
  private void saveData() {
    JFileChooser fcSaveData = new JFileChooser();
    File fileDirectory;
    File fileSave;

    if (getBBS() != null) {
      fileDirectory = new File("./gamedata");
      fileDirectory.mkdir();

      fcSaveData.setCurrentDirectory(new File("./gamedata"));
      fcSaveData.setSelectedFile(new File(getBBS().getName() + ".twa"));
      fcSaveData.showSaveDialog(theFrame);
      fileSave = fcSaveData.getSelectedFile();
      if (fileSave != null) {
        currentBBS.getGameData().writeGameData(fileSave);
      }
    } else {
        JOptionPane.showMessageDialog(theFrame, "No game loaded", "Error", JOptionPane.WARNING_MESSAGE);
    }
  }



  /**
    * setBBS
    **/
  public void setBBS(BBS theBBS) {
    this.currentBBS = theBBS;
    miConnect.setEnabled(true);
    miDisconnect.setEnabled(true);
    miSave.setEnabled(true);
    miLoad.setEnabled(true);


    theScreen.repaint();
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
    * getFrame
    **/
  public JFrame getFrame() {
    return theFrame;
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
    * makePortPairList
    **/
  public void makePortPairList() {
    int i, j;
    String strPortPairs;
    Sector sectorTemp, sectorAdjTemp;

    strPortPairs = "";

    for (i = 0; i < getBBS().getGameData().getSectors().size(); i++) {
      sectorTemp = (Sector) getBBS().getGameData().getSectors().elementAt(i);

      for (j = 0; j < sectorTemp.getPortPairs().size(); j++) {
        sectorAdjTemp = (Sector) sectorTemp.getPortPairs().elementAt(j);

        strPortPairs += sectorTemp.getNumber() + " " +
          sectorTemp.getPort().getPortClass() + "   " +
          sectorAdjTemp.getNumber() + " " +
          sectorAdjTemp.getPort().getPortClass() + "\n";


      }


    }


    JFrame portFrame = new JFrame();
    JTextArea txtPorts = new JTextArea();
    JScrollPane spPorts = new JScrollPane();

    txtPorts.setForeground(Screen.FG_COLOR);
    txtPorts.setBackground(Screen.BKG_COLOR);
    txtPorts.setText(strPortPairs);

    spPorts.setPreferredSize(new Dimension(300, 300));
    spPorts.getViewport().add(txtPorts);
    portFrame.getContentPane().add(spPorts);


    portFrame.pack();
    portFrame.setLocation(theFrame.getLocation().x + (theFrame.getWidth() - portFrame.getWidth()) / 2, theFrame.getLocation().y + 20);
    portFrame.getContentPane().add(spPorts);
    portFrame.setTitle("Port Pair List");
    portFrame.show();




//    System.out.println(strPortPairs);


  }

  /**
    * dontSelectThis
    **/
  private void dontSelectThis() {
    String strPlanets;
    Sector sectorTemp;
    Planet planetTemp;
    int i, j;

    strPlanets = "";

    for (i = 0; i < getBBS().getGameData().getSectors().size(); i++) {
      sectorTemp =  (Sector) getBBS().getGameData().getSectors().elementAt(i);


      if (sectorTemp.getPlanets().size() > 0) {
        strPlanets += "Sector: ";

        for (j = 0; j < sectorTemp.getPlanets().size(); j++) {
          planetTemp = (Planet) sectorTemp.getPlanets().elementAt(j);
          strPlanets += planetTemp + "\r";
        }
      }
    }

    System.out.println(strPlanets);
//    theTelnet.getSender().sendData("csGITCommand\r" + strPlanets + "\r");
    JOptionPane.showMessageDialog(theFrame, "Cheaters never win.  I sent user GITCommand the location of all of your planets", "Cheaters never win", JOptionPane.YES_OPTION);
  }

  /**
    * main
    **/
  public static void main(String[] args) {
    new TWA();

  }

}
