/**
 * CS1502: Program #8
 *
 * <PRE>
 * Fence Game
 *
 * Revisions:  1.0  March 5, 1999
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, March 5, 1999
 */


import java.awt.*;
import java.awt.event.*;


class FenceGame extends Frame implements ActionListener, WindowListener {

//*** CONSTANTS
public static final boolean DEBUG = false; //debugging constant

//*** INSTANCE VARIABLES
private Dialog winnerDialog; //displays winner
private TextField txtWhosTurn = new TextField("Player One's Turn", 20);
                          //textfield telling who's turn it is
private int iWindowHeight; //the window height
private int iWindowWidth; //the window width
private Button butReset = new Button("Start Game"); 
private PlayingField myPlayingField; //the canvas with the posts


/**
  * FenceGame - the constructor... sets up variables
  * Pre: new FenceGame created
  * Post: variables are initalized and components are set
  *
  */

public FenceGame() {

  iWindowHeight = 400;
  iWindowWidth = 400;

  setSize(iWindowHeight, iWindowWidth);
  setTitle("Command.Com's Fence Game");
  setResizable(false);

  myPlayingField = new PlayingField(this);
  Panel pnlTop = new Panel();
  Panel pnlTopLeft = new Panel();
  Panel pnlTopRight = new Panel();

  setLayout(new BorderLayout());

  add(pnlTop, BorderLayout.NORTH);
  Color myColor = new Color ( 0, 128, 0);
    pnlTop.setBackground(myColor);

    pnlTop.add(pnlTopLeft);
      pnlTopLeft.add(txtWhosTurn);
      txtWhosTurn.setEnabled(false);
      txtWhosTurn.setBackground(new Color(255, 255, 255));
      txtWhosTurn.setForeground(new Color(255, 0, 0));
      txtWhosTurn.addActionListener(this);
    pnlTop.add(pnlTopRight);
    butReset.addActionListener(this);
      pnlTopRight.add(butReset);
  add(myPlayingField, BorderLayout.CENTER);
  butReset.setActionCommand("RESET");

  setVisible(true);
  addWindowListener(this);
}

/**
  * actionPerformed - detects if the user presses the reset button
  * Pre: user performs an action
  * Post: action is handled
  *
  * @param e the action the user performs
  */
public void actionPerformed(ActionEvent e){
  String strSource = e.getActionCommand();

  if (strSource.equals("RESET")) {
    if (DEBUG) System.out.println("RESETTING");
    FenceGame myFenceGame = new FenceGame();
    butReset = new Button("RESET");
  }
}

/**
  * getWindowHeight - returns window height
  * Pre: nothing
  * Post: window height is returned
  *
  * @return the window height
  */
public int getWindowHeight() {
  return (iWindowHeight);
}

/**
  * getWindowWidth - returns window width
  * Pre: nothing
  * Post: window width is returned
  *
  * @return the window width
  */
public int getWindowWidth() {
  return (iWindowWidth);
}

/**
  * getTextField - returns the textfield
  * Pre: nothing
  * Post: the textfield is returned
  *
  * @return the window height
  */
public TextField getTextField() {
  return (txtWhosTurn);
}


/**
  * windowClosing - shuts down the program
  * Pre: nothing
  * Post: calls method to shut down program
  *
  * @param e the window event
  */
public void windowClosing (WindowEvent e) {
  shutdown();
}

/**
  * shutdown - quits the program
  * Pre: call from window closing
  * Post: program is closed
  *
  */

public void shutdown() {
  setVisible(false);
  dispose();
  System.exit(0);
}

/**
  * window* - does nothing
  * Pre: nothing
  * Post: nothing
  *
  * @param e again, nothing
  */
public void windowDeactivated (WindowEvent e) {
}
public void windowClosed(WindowEvent e) {
}
public void windowActivated (WindowEvent e) {
}
public void windowDeiconified (WindowEvent e) {
}
public void windowIconified (WindowEvent e) {
}
public void windowOpened (WindowEvent e) {
}


}
