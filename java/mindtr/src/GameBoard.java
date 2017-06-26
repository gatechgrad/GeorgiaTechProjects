import java.awt.*;
import java.awt.event.*;

class GameBoard extends Frame implements WindowListener {
  /************************ CONSTANTS *******************************/
  public static final Rectangle WINDOW = new Rectangle(25, 25, 640, 500);
  public static final Rectangle SCREEN =
    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
  public static final Color BACKGROUND_COLOR = new Color(191, 255, 255);


  /************************ CLASS VARIABLES *******************************/
  private Mindtrial MTMainGame;
  

  /**
    * GameBoard - constructor... this simply makes a window, and adds
    *             Board and StatusBox to it
    **/
  public GameBoard(Mindtrial MTMainGame) {

    this.MTMainGame = MTMainGame;

    this.setIconImage( Toolkit.getDefaultToolkit().getImage("icon.gif") );
    this.setSize(WINDOW.width, WINDOW.height);
    this.setBounds(  ((SCREEN.width / 2) - (WINDOW.width / 2)),
                     ((SCREEN.height / 2) - (WINDOW.height / 2)),
                      WINDOW.width, WINDOW.height);   //sets location and
                                                      //size of window<center)
    this.setResizable(false);
    this.setTitle(Mindtrial.TITLE);
    this.setBackground(BACKGROUND_COLOR);
    this.addWindowListener(this);

    this.add(MTMainGame.getBoard(), BorderLayout.WEST);
    this.add(MTMainGame.getStatusBox(), BorderLayout.CENTER);

    this.setVisible(true);

  }


  /**
    * Seven methods required by WindowListener
    **/
  public void windowOpened(WindowEvent e) {
  }
  public void windowClosed(WindowEvent e) {
  }
  public void windowClosing(WindowEvent e) {
    shutWindow();
  }
  public void windowIconified(WindowEvent e) {
  }
  public void windowDeiconified(WindowEvent e) {
  }
  public void windowActivated(WindowEvent e) {
  }
  public void windowDeactivated(WindowEvent e) {
  }


  /**
    * shutWindow - call this method to close this window and return
    *              to menu screen
    **/
  public void shutWindow() {
    this.setVisible(false);
    this.dispose();

    //only needed for data generation
    //MTMainGame.getBoard().printThis.close();

    //MTMainGame.end();

  }


}
