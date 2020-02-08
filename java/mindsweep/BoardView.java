import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoardView extends JComponent implements MouseListener {

  public static final int TILE_SIZE = 32;

  MindSweep theMS;
  JFrame theFrame;
  boolean showBombs;

  public BoardView(MindSweep ms) {
    theMS = ms;
    showBombs = false;
    setupComponent();
    setupWindow();

  }

  private void setupWindow() {

    theFrame = new JFrame();

    WindowListener wl = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        theFrame.hide();
        System.exit(0);
      }
    };
    theFrame.addWindowListener(wl);

    theFrame.getContentPane().add(this);
    theFrame.setTitle("GITCommand MindSweep");

    theFrame.pack();

    theFrame.show();



  }

  private void setupComponent() {
    this.setPreferredSize(new Dimension(theMS.getCols() * TILE_SIZE, theMS.getRows() * TILE_SIZE));
    this.addMouseListener(this);
  }

  public void update(Graphics g) {
    int i, j;

    g.setColor(Color.black);
    g.fillRect(0, 0,
               (theMS.getCols() * TILE_SIZE),
               (theMS.getRows() * TILE_SIZE));

    for (i = 0; i < theMS.getRows(); i++) {
      for (j = 0; j < theMS.getCols(); j++) {

        g.setColor(new Color(192, 192, 192));
        g.fillRect( (j * TILE_SIZE), (i * TILE_SIZE), TILE_SIZE - 1, TILE_SIZE - 1);

        if (theMS.getCell(i, j).getUnCovered()) {
          g.setColor(new Color(128, 128, 128));
          g.fillRect( (j * TILE_SIZE), (i * TILE_SIZE), TILE_SIZE - 1, TILE_SIZE - 1);

          if (theMS.getCell(i, j).getNumber() > 0) {

            switch(theMS.getCell(i, j).getNumber()) {
              case 1:
                g.setColor(new Color(0, 0, 255));
                break;
              case 2:
                g.setColor(new Color(0, 255, 0));
                break;
              case 3:
                g.setColor(new Color(255, 0, 0));
                break;
              default:
                g.setColor(new Color(0, 0, 0));
            }

            g.drawString(theMS.getCell(i, j).getNumber() + "", (j * TILE_SIZE) + 8, (i * TILE_SIZE) + 16);
          }


        }

        if (theMS.getCell(i, j).getIsFlagged()) {
          g.setColor(new Color(128, 0, 0));
          g.fillRect( (j * TILE_SIZE), (i * TILE_SIZE), TILE_SIZE - 1, TILE_SIZE - 1);

        }

        if ((showBombs) && (theMS.getCell(i, j).getIsBomb())) {
          g.setColor(new Color(0, 0, 0));
          g.fillOval( (j * TILE_SIZE), (i * TILE_SIZE), TILE_SIZE - 1, TILE_SIZE - 1);

        }





      }
    }

  }

  public void paint(Graphics g) {
    update(g);
  }

  public void showWin() {
JOptionPane.showMessageDialog(
                    this,
"You are a winner", "Winner", JOptionPane.WARNING_MESSAGE);


  }

  public void showLose() {
JOptionPane.showMessageDialog(
                    this,
"You are a loser", "Loser", JOptionPane.WARNING_MESSAGE);

    showBombs = true;
    repaint();

  }



  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
      theMS.flagCell(e.getY() / TILE_SIZE, e.getX() / TILE_SIZE);

    } else {
      theMS.selectCell(e.getY() / TILE_SIZE, e.getX() / TILE_SIZE);

    }



    repaint();
    theMS.printBoard();
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
  }

}
