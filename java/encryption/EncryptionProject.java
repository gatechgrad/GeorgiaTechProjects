import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class EncryptionProject extends Applet {

  /** CONSTANTS **/
  public static final Font MONOSPACE_FONT = new Font("Monospaced", Font.PLAIN, 12);
  public static final Font MONOSPACE_LARGE_FONT = new Font("Monospaced", Font.PLAIN, 14);
  public static final Font CODE_FONT = new Font("Monospaced", Font.PLAIN, 10);


  /**
   * main - if run as an application
   */
  public static void main(String args[]) {
    Panel thePanel;
    Frame theFrame;
    WindowListener wl;

    thePanel = new EncryptionProjectPanel();

    theFrame = new Frame();
    wl = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }

    };
    theFrame.addWindowListener(wl);

    theFrame.add(thePanel);

    theFrame.setLocation(200, 200);

    theFrame.pack();
    theFrame.setVisible(true);

  }

  /**
   * init - if run as an applet
   */
  public void init() {
    EncryptionProjectPanel epp;

    epp = new EncryptionProjectPanel();
    this.setBackground(Color.lightGray);
    this.add(epp);

  }
}
