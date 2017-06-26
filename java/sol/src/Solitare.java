import java.awt.*;
import java.awt.event.*;

class Solitare extends Frame implements WindowListener, ActionListener {

public static final int WIN_WIDTH = 640;
public static final int WIN_HEIGHT = 480;
public static final Point LOCATION = new Point(50, 50);
public static final Color BKG_COLOR = new Color(192, 192, 192);

public static final String GAME = "Game";
public static final String NEWGAME = "New Game";
public static final String EXIT = "Exit";

private boolean isApplet = true;

private MenuBar theMenuBar;
private Menu
  MenuGame;
private MenuItem
  MINewGame, MIExit;

private GameBoard theGameBoard;

public Solitare() {
  setSize(WIN_WIDTH, WIN_HEIGHT);
  setBounds(LOCATION.x, LOCATION.y, WIN_WIDTH, WIN_HEIGHT);
  setBackground(BKG_COLOR);
  setTitle("Command.Com's Solitare");
  theGameBoard = new GameBoard();

  setMenuBar();

  setLayout(new BorderLayout());
  add(theGameBoard);

  addWindowListener(this);
  setVisible(true);


}

public void setMenuBar() {
  theMenuBar = new MenuBar();
  setMenuBar(theMenuBar);


  MenuGame = new Menu(GAME);
    MINewGame = new MenuItem(NEWGAME);
    MIExit = new MenuItem(EXIT);
    MenuGame.add(MINewGame);
    MenuGame.add(MIExit);
    MINewGame.addActionListener(this);
    MIExit.addActionListener(this);

  theMenuBar.add(MenuGame);

}

public void actionPerformed(ActionEvent e) {
  String actionCommand = e.getActionCommand();

  if (actionCommand.equals(EXIT)) {
    shutdown();
  } else if (actionCommand.equals(NEWGAME)) {
    remove(theGameBoard);
    theGameBoard = new GameBoard();
    add(theGameBoard);
    setVisible(true);
    System.out.println("New Game");
  }
}

public void shutdown() {
  setVisible(false);
  dispose();
  if (!isApplet) {
    System.exit(0);
  }
}


public void windowClosing(WindowEvent e) {
  shutdown();
}
public void windowClosed(WindowEvent e) {
}
public void windowOpened(WindowEvent e) {
}
public void windowIconified(WindowEvent e) {
}
public void windowDeiconified(WindowEvent e) {
}
public void windowActivated(WindowEvent e) {
}
public void windowDeactivated(WindowEvent e) {
}


public static void main(String Argv[]) {
  Solitare mySolitare = new Solitare();
  mySolitare.isApplet = false;

}



}
