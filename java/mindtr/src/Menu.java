import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class Menu extends Frame implements WindowListener, ActionListener, KeyListener {
  /************************ CONSTANTS *******************************/
  public static final Rectangle WINDOW = new Rectangle(25, 25, 400, 300);
  public static final Rectangle SCREEN =
    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
  public static final Color BACKGROUND_COLOR = new Color(191, 255, 255);
  public static final int MAX_PLAYERS = 6;
  public static final int MAX_NAME_LEN = 8;

  public static final int SUCCESS = 0; //used when exiting the program
  public static final int FAILURE = 1;

  public static final String CHOICE_NOVICE = "NOVICE";
  public static final String CHOICE_NORMAL = "NORMAL";
  public static final String CHOICE_ADVANCED = "ADVANCED";

  public static final int PLAYERS_TO_START = 2;

  //button labels
  public static final String BUTTON_START = "Start Game";
  public static final String BUTTON_EXIT = "Exit";
  public static final String BUTTON_HELP = "Help";
  public static final String BUTTON_REMOVE = "Remove Player";
  public static final String BUTTON_ADD = "Add Player";

  /************************ CLASS VARIABLES *************************/
  private boolean musicIsOn;

  private Vector vectPlayers; //a temporary vector of players... this
                              //vector will be passed to the Mindtrial
                              //class upon 'start game'
  private Button
    butStart, butExit, butHelp, butRemove, butAddPlayer;
  private List
    ListPlayers;
  private TextField
    txtNameEntry;
  private Choice
    choiceDifficulty;
  private Mindtrial
    MTMainGame;

  /************************ CLASS METHODS *************************/

  /**
    * Menu - constructor
    **/
  public Menu(Mindtrial MTMainGame) {
    this.MTMainGame = MTMainGame;
    vectPlayers = new Vector();
    generateMenu();
  }

  /**
    * addPlayer - adds a new Player
    *           - uses Players lists and sets name, color, and difficulty
    *           - Creates and instance of Player and adds it to a list
    * @param String strName
    * @param Color theColor
    * @param int iDifficulty
    **/
  private void addPlayer() {
    Player newPlayer;
    int iDifficulty;

    //if you're bored, add code here to check that a name being added is
    //not already in the list
    if ((!(txtNameEntry.getText().equals("")))
        && (ListPlayers.getItemCount() < MAX_PLAYERS)) {

             if (choiceDifficulty.getSelectedItem().equals(CHOICE_NOVICE)) {
               iDifficulty = 1;               
      } else if (choiceDifficulty.getSelectedItem().equals(CHOICE_NORMAL)) {
               iDifficulty = 2;               
      } else if (choiceDifficulty.getSelectedItem().equals(CHOICE_ADVANCED)) {
               iDifficulty = 3;               
      } else {
               iDifficulty = 1;               
      }

      //make a new instance of a Player
      newPlayer = new Player(txtNameEntry.getText(), Color.black, iDifficulty);
      //add the player data to the vector
      vectPlayers.addElement(newPlayer);
      //add name to list component
      ListPlayers.add(txtNameEntry.getText());


      if ((Mindtrial.DEBUG == 3) || (Mindtrial.DEBUG == 1)) {printPlayers();}


      //now allow the user to remove a player
      butRemove.setEnabled(true);


      //set everything back to normal
      txtNameEntry.setText("");
      choiceDifficulty.select(0);

      //if the maximum number of Players have been reached, then disable
      //the 'Add Player' button
      if (ListPlayers.getItemCount() == MAX_PLAYERS) {
        butAddPlayer.setEnabled(false);
        txtNameEntry.setEditable(false);
      }
    }

    //if there is more than one player, then allow the player to start a 
    //game
    if(ListPlayers.getItemCount() >= PLAYERS_TO_START) {
      butStart.setEnabled(true);
    }


  }

  /**
    * displayButtons - displays the button
    *                - uses the String passed in for the name
    *          
    * @param String strName
    **/
  private void displayButtons(String strName) {

  }

  /**
    * generateMenu - displays the menu
    *              - uses strings to name the buttons
    *              - displays the graphic of menu, cals displayButtons()
    *                  to create buttons for starting game and adding
    *                  players and which color they are it also generates
    *                  text fields for user input
    **/
  private void generateMenu() {
    this.setIconImage( Toolkit.getDefaultToolkit().getImage(Mindtrial.MT_ICON) );
    this.setSize(WINDOW.width, WINDOW.height);
    this.setBounds(  ((SCREEN.width / 2) - (WINDOW.width / 2)),
                     ((SCREEN.height / 2) - (WINDOW.height / 2)),
                      WINDOW.width, WINDOW.height);   //sets location and
                                                      //size of window<center)
    this.setResizable(false);
    this.setTitle(Mindtrial.TITLE);
    this.setBackground(BACKGROUND_COLOR);
    this.addWindowListener(this);

    //set the layout managers
    this.setLayout(new BorderLayout());
    Panel bottomPanel = new Panel();

    bottomPanel.setLayout(new BorderLayout());
      Panel leftPanel = new Panel();
      Panel centerPanel = new Panel();
      Panel rightPanel = new Panel();

      leftPanel.setLayout(new GridLayout(5, 1, 10, 5));
        Label lblName = new Label("NAME: ");
        Label lblSkill = new Label("SKILL: ");

        choiceDifficulty = new Choice();
        choiceDifficulty.add(CHOICE_NOVICE);
        choiceDifficulty.add(CHOICE_NORMAL);
        choiceDifficulty.add(CHOICE_ADVANCED);

        butAddPlayer = new Button(BUTTON_ADD);
        butAddPlayer.addActionListener(this);

        txtNameEntry = new TextField();
        txtNameEntry.addKeyListener(this);

        leftPanel.add(lblName);
        leftPanel.add(txtNameEntry);
        leftPanel.add(lblSkill);
        leftPanel.add(choiceDifficulty);
        leftPanel.add(butAddPlayer);


      centerPanel.setLayout(new BorderLayout());
        Label lblPlayers = new Label("PLAYERS:");
        Panel ctopPanel = new Panel();
        Panel cbottomPanel = new Panel();

        butRemove = new Button(BUTTON_REMOVE);
        butRemove.addActionListener(this);
        butRemove.setEnabled(false);

        ListPlayers = new List(MAX_PLAYERS);

        ctopPanel.setLayout(new BorderLayout());
          Panel tempPanel = new Panel();
          tempPanel.add(lblPlayers);
          ctopPanel.add(tempPanel, BorderLayout.NORTH);
          tempPanel.add(ListPlayers);
          ctopPanel.add(tempPanel, BorderLayout.CENTER);

        cbottomPanel.add(butRemove);

        centerPanel.add(ctopPanel, BorderLayout.CENTER);
        centerPanel.add(cbottomPanel, BorderLayout.SOUTH);


      rightPanel.setLayout(new GridLayout(3, 1, 10, 30));
        butStart = new Button(BUTTON_START);
        butStart.addActionListener(this);
        butStart.setEnabled(false);

        butExit = new Button(BUTTON_EXIT);
        butExit.addActionListener(this);

        butHelp = new Button(BUTTON_HELP);
        butHelp.addActionListener(this);

        rightPanel.add(butStart);
        rightPanel.add(butExit);
        rightPanel.add(butHelp);


      bottomPanel.add(centerPanel, BorderLayout.CENTER);
      bottomPanel.add(leftPanel, BorderLayout.WEST);
      bottomPanel.add(rightPanel, BorderLayout.EAST);

    ImageToComponent titleImage = new ImageToComponent("mindtr.gif", WINDOW.width, (WINDOW.height / 4));
    titleImage.setSize(WINDOW.width, (WINDOW.height / 4));
    this.add(titleImage, BorderLayout.NORTH);
    this.add(bottomPanel, BorderLayout.CENTER);

    //finally show the window
    this.setVisible(true);


  }

  /**
    * mouseClicked - tests user input by the mouse
    *              - uses the mouse coordinates to determine what action
    *                  to take
    *              - checks to see if the mouse has been clicked on the
    *                  display and if it has clicked a button
    **/
  private boolean mouseClicked() {

    return false;
  }


  /**
    * removePlayer - removes a Player
    *              - uses Player List
    *              - removes the specified player from Player list
    * @param String strName
    **/
  private void removePlayer() {
    int i;
    String strSelectedName = ListPlayers.getSelectedItem();

    if (strSelectedName != null) {
      for (i = 0; i < vectPlayers.size(); i++) {
        if (((Player) vectPlayers.elementAt(i)).getName().equals(strSelectedName)) {
          vectPlayers.removeElementAt(i);
          ListPlayers.remove(strSelectedName);
        }
      }
    }

    //set add player text field and add player button to enabled
    //this can be optimized later (only enable with 5 players...
    //but the check itself would probably be more costly than enabling
    //the buttons
    butAddPlayer.setEnabled(true);
    txtNameEntry.setEditable(true);

    //if the user has removed all players, then disable the remove button
    if(ListPlayers.getItemCount() == 0) {
      butRemove.setEnabled(false);
    }

    //if there are less than two players, then disable the 'start game'
    //button
    if(ListPlayers.getItemCount() < PLAYERS_TO_START) {
      butStart.setEnabled(false);
    }

  }


  /**
    * printPlayers - prints a list of Players (for debugging purposes)
    **/
  private void printPlayers() {
    int i;

    System.out.println("*************PLAYER LIST**************************");

    for (i = 0; i < vectPlayers.size(); i++) {
      System.out.println("Player " + i + ": " + ((Player) vectPlayers.elementAt(i)));
    }

  }

  /**
    * runDemo - runs the game demo
    *         - tests if the demo button is clicked; if so, run the demo
    **/
  private void runDemo() {

  }

  /**
    * setMusic - turns the music and sound effects on and off
    *          - uses musicIsOn
    *          - if musicIsOn == true, set to false, else set to true
    **/
  private void setMusic() {

  }

  /**
    * showHelp - displays the help screen
    *          - tests if the help button is clicked; if so, display
    *              the help menu
    *          - help screen contains rules of the game
    **/
  private void showHelp() {

  }

  /**
    * startGame - creates the game and runs it
    *           - passes the Players list to Mindtrial
    *           - calls Mindtrial.begin()
    **/
  private void startGame() {
    this.setVisible(false);

    MTMainGame.setPlayers(vectPlayers);
    MTMainGame.runGame();

    this.dispose();

  }


  /**
    * getPlayers - returns the current list of Players
    *            - used to allow class Mindtrial to obtain a copy of the
    *                game Players
    **/
  public Vector getPlayers() {
    return vectPlayers; //Levi: I'm not sure if I need to clone this
                        //if it breaks, then come back and use the clone
                        //method
  }

  /**
    * actionPerformed - called when an action takes place
    **/
  public void actionPerformed(ActionEvent e) {
    String strAction = e.getActionCommand();

           if (strAction.equals(BUTTON_ADD)) {
             addPlayer();
    } else if (strAction.equals(BUTTON_START)) {
      startGame();
    } else if (strAction.equals(BUTTON_REMOVE)) {
      removePlayer();
    } else if (strAction.equals(BUTTON_HELP)) {
      Frame myFrame = new Frame();
      TextArea txtHelp = new TextArea("", 200, 300, TextArea.SCROLLBARS_VERTICAL_ONLY);
      myFrame.add(txtHelp);
      myFrame.setBounds(100, 100, 250, 350);
      myFrame.setTitle("Mindtrial Help");
 
      String strToDisplay;
      strToDisplay = "Object: \n \n  To move around the board answering questions, and to collect the 6 colored pie pieces by answering the question correctly at each of the headquarters. To win, a player must acquire the pieces and return to the central starting point where he/she will be asked the final question from a random category.\n \n Gameplay \n The first player rolls the die and moves the allotted number of spaces in any direction. A player cannot move both forward and back in the opposite direction in the same move. A question of the appropriate category will be asked. If answered correctly, the player gets to go again. If the question was from one of the category headquarters the player is awarded a pie piece of that category and gets to go again. The player continues in this way until a question is missed. Then the second player goes in the same way, and play continues until a winner is determined. \n Around the board are two kinds of spaces: normal questions and scoring questions at the headquarters. The normal ";
      strToDisplay += "questions are the most ample spaces. They make up the spokes from the central starting point and the majority of the spaces between the scoring corners. At each corner is a category headquarter. A correct answer here earns a scoring wedge. More than one playing piece can occupy a space. \n \n";
      strToDisplay += "\n Winning the Game \n\nOnce each category's piece has been acquired, the player must return to the center. Once he/she lands on this spot by exact count, a question from a random category will be asked. If answered correctly, that player wins. If incorrect, that player must move off of the center and return later, again by exact count, to try to answer another winning question. ";
      
      txtHelp.setText(strToDisplay);
      
      myFrame.setVisible(true);
      

    } else if (strAction.equals(BUTTON_EXIT)) {
      shutdown();
    }

  }


  /**
    * shutdown - shuts down the program
    **/
  private void shutdown() {
    this.setVisible(false);
    this.dispose();
    System.exit(SUCCESS);
  }

  /**
    * windowActivated
    * windowDeactivated
    * windowOpened
    * windowClosed        - seven methods required by WindowListener
    * windowClosing
    * windowIconified
    * windowDeiconified
    **/
  public void windowActivated(WindowEvent e) {
  }
  public void windowDeactivated(WindowEvent e) {
  }

  public void windowOpened(WindowEvent e) {
  }

  public void windowClosed(WindowEvent e) {
  }

  public void windowClosing(WindowEvent e) {
    shutdown();
  }

  public void windowIconified(WindowEvent e) {
  }

  public void windowDeiconified(WindowEvent e) {
  }

  /**
    * keyPressed   - required by keyListener
    * keyReleased 
    * keyTyped
    **/
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if ((Mindtrial.DEBUG == 2) || (Mindtrial.DEBUG == 1)) {System.out.println("Enter Key Pressed");}
      addPlayer();
      
    }

  }
  public void keyReleased(KeyEvent e) {
  }
  public void keyTyped(KeyEvent e) {
  }

}