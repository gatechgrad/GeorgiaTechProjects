import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CellDisplay extends JComponent implements MouseMotionListener, MouseListener {

    //INSTANCE VARIABLES
    private Map theMap;

    private boolean gridIsOn;
    private Point pntHighlightedCell;
    private boolean isDragging;
    private MapEditor theMapEditor;

    /**
     * called when object is instantiated
     */
    public CellDisplay(Map theMap, MapEditor theMapEditor) {
        gridIsOn = true;
        isDragging = false;
        pntHighlightedCell = new Point();
        pntHighlightedCell.setLocation(-1, -1);

        this.theMap = theMap;
        this.theMapEditor = theMapEditor;
        this.setBackground(Color.cyan);
        this.setPreferredSize(new Dimension(theMap.getCols() * theMap.getCellSize().width,
                                            theMap.getRows() * theMap.getCellSize().height
                                            )
                              );
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public void paint(Graphics g) {
        update(g);
    }

    /*
    public void repaint(Graphics g) {
        update(g);
    }
    */


    /**
     * draws the component
     */
    public void update(Graphics g) {
        int i, j;

        for (i = 0; i < theMap.getRows(); i++) {
            for (j = 0; j < theMap.getCols(); j++) {
                if ((theMap != null) && (theMap.getCell(i, j) != null) && (theMap.getCell(i, j).getImage() != null)) {
                    g.drawImage(theMap.getCell(i, j).getImage(), i * theMap.getCellSize().width, 
                            j * theMap.getCellSize().height, theMap.getCellSize().width,
                            theMap.getCellSize().height, null);
                
                }
                
            }
        }

        g.setColor(Color.black);
        g.drawRect(0, 0, theMap.getTotalWidth() - 1, theMap.getTotalHeight() - 1);

        g.setColor(Color.gray);
        for (i = 0; i < theMap.getRows() + 1; i++) {
            g.drawLine(0, i * theMap.getCellSize().height, theMap.getCols() * theMap.getCellSize().width, i * theMap.getCellSize().height);
            for (j = 0; j < theMap.getCols() + 1; j++) {
                g.drawLine(j * theMap.getCellSize().width, 0, j * theMap.getCellSize().width, theMap.getRows() * theMap.getCellSize().height);
            }
        }

        if (!isDragging) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.blue);
        }

        Rectangle rectHighlight;
        rectHighlight = new Rectangle((int) pntHighlightedCell.getX() * theMap.getCellSize().width,
                                      (int) pntHighlightedCell.getY() * theMap.getCellSize().height,
                                      theMap.getCellSize().width,
                                      theMap.getCellSize().height
                                      );
        g.fillRect(rectHighlight.x, rectHighlight.y, rectHighlight.width, rectHighlight.height);
    }

    /**
     * returns a point representing the current cell
     */
    private Point getCurrentCell(int x, int y) {
        Point pntToReturn;

        pntToReturn = new Point(x / theMap.getCellSize().width, y / theMap.getCellSize().height);

        return pntToReturn;
    }



    /**
     * required by MouseMotionListener
     */
    public void mouseMoved(MouseEvent e) {

        isDragging = false;

        if (e.getX() < theMap.getTotalWidth() &&
            e.getY() < theMap.getTotalHeight() 
            ) {

            pntHighlightedCell.setLocation(getCurrentCell(e.getX(), e.getY()));

        } else {
            pntHighlightedCell.setLocation(-1, -1);
        }
        repaint();
    }
    public void mouseDragged(MouseEvent e) {
        isDragging = true;

        if (e.getX() < theMap.getTotalWidth() &&
            e.getY() < theMap.getTotalHeight() 
            ) {

            Point pntCurrentCell = getCurrentCell(e.getX(), e.getY());

            pntHighlightedCell.setLocation(pntCurrentCell);
            Cell cellCurrent = theMap.getCell(pntCurrentCell.x, pntCurrentCell.y);
            if (cellCurrent != null) {
                //cellCurrent.setImageIcon(new ImageIcon("d:/rpg/newgame/images/tree01.gif"));
                cellCurrent.setImageIcon(theMapEditor.getCurrentTexture());
            } else {
                System.out.println("cell is null");
            }

        } else {
            pntHighlightedCell.setLocation(-1, -1);
        }
        repaint();
    }
    
    /**
     * required by MouseListener
     */
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {
        pntHighlightedCell.setLocation(-1, -1);
        repaint();
    }

     
}
