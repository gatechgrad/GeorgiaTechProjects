import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.awt.*;
import java.awt.event.*;


class Band extends JComponent {

  /**** CONSTANTS ****/
  public static final String WIN_TITLE = "The Band Game";
  public static final Rectangle WIN_PROPERTIES = new Rectangle (50, 50, 640, 330);


  Image ImgField;
  Image ImgButtonBar;

  /**
   * Band Constructor
   */
  public Band() {
    setDoubleBuffered(true);
    loadImages();
    makeWindow();
  }

  /**
   * getImages -gets the Images from the disk
   */
  public void loadImages() {
    ImgField = Toolkit.getDefaultToolkit().getImage("field.gif");
    ImgButtonBar = Toolkit.getDefaultToolkit().getImage("buttonbar.gif");

  }

  /**
   * Makes a new window to play the game in
   */
  private void makeWindow() {
    JFrame myFrame = new JFrame();
    WindowListener l = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };

    myFrame.addWindowListener(l);
    myFrame.setSize(640, 480);
    myFrame.setBounds(WIN_PROPERTIES.x, WIN_PROPERTIES.y,
                      WIN_PROPERTIES.width, WIN_PROPERTIES.height);
    myFrame.setTitle(WIN_TITLE);
    myFrame.getContentPane().add(this);
    myFrame.setVisible(true);

  }

  public void paint(Graphics g) {
    g.setColor(Color.green);
    g.drawImage(ImgField, 0, 0, 440, 330, null);
    g.drawImage(ImgButtonBar, 441, 0, 200, 330, null);
  }


  public static void main(String argv[]) {
    new Band();
  }

}
