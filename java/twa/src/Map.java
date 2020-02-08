import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Map extends JComponent {

  /** CONSTANTS **/
  public static final Color COLOR_VISITED_FG = new Color(0, 255, 0);
  public static final Color COLOR_VISITED_BKG = new Color(0, 255, 0);

  /** INSTANCE VARIABLES **/
  private TWA theTWA;
  private int iCircleSize;
  private int iZoom;
  private int iSectorToDraw;
  private int iTimes;


  /**
    * Map
    **/
  public Map(int iSectorToDraw, TWA theTWA) {
    this.theTWA = theTWA;
    this.iSectorToDraw = iSectorToDraw;
    iCircleSize = 50;
    iTimes = MapDialog.DEFAULT_HOPS;

    this.setPreferredSize(new Dimension(640, 480));
    this.setForeground(Screen.FG_COLOR);
    this.setBackground(Screen.BKG_COLOR);

  }

  /**
    * update
    **/
  public void update(Graphics g) {
    Sector sectorToDraw;
    Point pntCenter;


    g.setColor(Screen.BKG_COLOR);
    g.fillRect(0, 0, getSize().width, getSize().height);
    g.setColor(COLOR_VISITED_FG);
    pntCenter = getCenter();


    if (iSectorToDraw > 0) {
      sectorToDraw = (Sector) theTWA.getBBS().getGameData().findSector(iSectorToDraw);
      if (sectorToDraw != null) {
        sectorToDraw.drawOnMap(g, pntCenter.x, pntCenter.y, 0, (2.0 * Math.PI), 0, iTimes, this);
      } else {
        g.drawString("Sector " + iSectorToDraw + " was not found in database.", pntCenter.x, pntCenter.y);
      }

    }
  }


  /**
    * repaint
    **/
  public void repaint(Graphics g) {
    update(g);
  }

  /**
    * paint
    **/
  public void paint(Graphics g) {
    update (g);
  }


  /**
    * getCenter - returns the center point
    **/
  private Point getCenter() {
    Dimension dimDrawingSize;

    dimDrawingSize = getSize();
    return (new Point(dimDrawingSize.width / 2, dimDrawingSize.height / 2));

  }

  /**
    * getCircleSize
    **/
  public int getCircleSize() {
    return 32;
  }

  /**
    * getSmallCircleSize
    **/
  public int getSmallCircleSize() {
    return 16;
  }

  /**
    * getLineLength
    **/
  public int getLineLength() {
    return 80;
  }

  /**
    * getSmallLength
    **/
  public int getSmallLineLength() {
    return 40;
  }


  /**
    * set
    **/
  public void setSectorToDraw(int i, int j) {
    iSectorToDraw = i;
    iTimes = j;
  }





}


