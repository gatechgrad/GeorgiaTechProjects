import java.awt.*;
import java.awt.image.ImageObserver;

public class Port {

  /** CONSTANTS **/
  public static final int BAR_MAX_WIDTH = 40;
  public static final int PORT_MAX = 2700;

  public static final Color BUY_COLOR1 = new Color(0, 192, 0);
  public static final Color BUY_COLOR2 = new Color(0, 64, 0);
  public static final Color SELL_COLOR1 = new Color(0, 192, 192);
  public static final Color SELL_COLOR2 = new Color(0, 64, 64);


  /** INSTANCE VARIABLES **/
  private int iFuelOreBuying, iOrganicsBuying, iEquipmentBuying,
              iFuelOreSelling, iOrganicsSelling, iEquipmentSelling;
  private int iClass;
  private String strName;
  private String strClass;



  /**
    * Port
    **/
  public Port(String strName, String strClass) {

    this.strName = strName;
    this.strClass = strClass;
  }

  /**
    * get*() -accessors
    **/
  public int getFuelOreBuying() { return iFuelOreBuying; }
  public int getOrganicsBuying() { return iOrganicsBuying; }
  public int getEquipmentBuying() { return iEquipmentBuying; }

  public int getFuelOreSelling() { return iFuelOreSelling; }
  public int getOrganicsSelling() { return iOrganicsSelling; }
  public int getEquipmentSelling() { return iEquipmentSelling; }

  public String getName() { return strName; }
  public String getPortClass() { return strClass; }

  /**
    * drawView
    **/
  public void drawView(Graphics g, int x, int y, ImageObserver theObserver) {
    int iBarLength;
    String strTemp;
    int j;

          g.drawImage(Screen.imgVPort, x + 2, y, 50, 50, theObserver);

          g.setColor(BUY_COLOR1);
          iBarLength = (this.getFuelOreBuying() > PORT_MAX) ? BAR_MAX_WIDTH : this.getFuelOreBuying() * BAR_MAX_WIDTH / PORT_MAX;
          g.fillRect(x + 55, y + 10, iBarLength, 5);
          iBarLength = (this.getOrganicsBuying() > PORT_MAX) ? BAR_MAX_WIDTH : this.getOrganicsBuying() * BAR_MAX_WIDTH / PORT_MAX;
          g.fillRect(x + 55, y + 15, iBarLength, 5);
          iBarLength = (this.getEquipmentBuying() > PORT_MAX) ? BAR_MAX_WIDTH : this.getEquipmentBuying() * BAR_MAX_WIDTH / PORT_MAX;
          g.fillRect(x + 55, y + 20, iBarLength, 5);

          g.setColor(BUY_COLOR2);
          iBarLength = (this.getFuelOreBuying() > PORT_MAX) ? BAR_MAX_WIDTH : this.getFuelOreBuying() * BAR_MAX_WIDTH / PORT_MAX;
          g.drawRect(x + 55, y + 10, iBarLength, 5);
          iBarLength = (this.getOrganicsBuying() > PORT_MAX) ? BAR_MAX_WIDTH : this.getOrganicsBuying() * BAR_MAX_WIDTH / PORT_MAX;
          g.drawRect(x + 55, y + 15, iBarLength, 5);
          iBarLength = (this.getEquipmentBuying() > PORT_MAX) ? BAR_MAX_WIDTH : this.getEquipmentBuying() * BAR_MAX_WIDTH / PORT_MAX;
          g.drawRect(x + 55, y + 20, iBarLength, 5);

          g.setColor(SELL_COLOR1);
          iBarLength = (this.getFuelOreSelling() > PORT_MAX) ? BAR_MAX_WIDTH : this.getFuelOreSelling() * BAR_MAX_WIDTH / PORT_MAX;
          g.fillRect(x + 55, y + 10, iBarLength, 5);
          iBarLength = (this.getOrganicsSelling() > PORT_MAX) ? BAR_MAX_WIDTH : this.getOrganicsSelling() * BAR_MAX_WIDTH / PORT_MAX;
          g.fillRect(x + 55, y + 15, iBarLength, 5);
          iBarLength = (this.getEquipmentSelling() > PORT_MAX) ? BAR_MAX_WIDTH : this.getEquipmentSelling() * BAR_MAX_WIDTH / PORT_MAX;
          g.fillRect(x + 55, y + 20, iBarLength, 5);

          g.setColor(SELL_COLOR2);
          iBarLength = (this.getFuelOreSelling() > PORT_MAX) ? BAR_MAX_WIDTH : this.getFuelOreSelling() * BAR_MAX_WIDTH / PORT_MAX;
          g.drawRect(x + 55, y + 10, iBarLength, 5);
          iBarLength = (this.getOrganicsSelling() > PORT_MAX) ? BAR_MAX_WIDTH : this.getOrganicsSelling() * BAR_MAX_WIDTH / PORT_MAX;
          g.drawRect(x + 55, y + 15, iBarLength, 5);
          iBarLength = (this.getEquipmentSelling() > PORT_MAX) ? BAR_MAX_WIDTH : this.getEquipmentSelling() * BAR_MAX_WIDTH / PORT_MAX;
          g.drawRect(x + 55, y + 20, iBarLength, 5);


          y += 75;
          g.setFont(new Font("serif", Font.PLAIN, 10));
          strTemp = this.getPortClass();
          if (strTemp.length() == 3) {
            for (j = 0; j < 3; j++) {
              if (strTemp.charAt(j) == 'B') {
                g.setColor(BUY_COLOR1);
                g.drawString("B", x += 10, y);
              } else if (strTemp.charAt(j) == 'S') {
                g.setColor(SELL_COLOR1);
                g.drawString("S", x += 10, y);
              }
            }
          }

          if (strTemp.equals("(Special)")) {
            g.drawString("Special", x, y);
          }



  }


  /**
    * drawFull
    **/
  public void drawFull(Graphics g, int x, int y, ImageObserver theObserver) {
      int j;
      String strTemp;
      int iBarLength;

      g.drawImage(Screen.imgPort, x, y, theObserver);

      x -= 80;
      y += 50;
      g.drawString(this.getName(), x, y);

      y += 20;

      g.setColor(BUY_COLOR1);
      iBarLength = (this.getFuelOreBuying() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getFuelOreBuying() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.fillRect(x, y + 10, iBarLength, 10);
      iBarLength = (this.getOrganicsBuying() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getOrganicsBuying() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.fillRect(x, y + 20, iBarLength, 10);
      iBarLength = (this.getEquipmentBuying() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getEquipmentBuying() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.fillRect(x, y + 30, iBarLength, 10);

      g.setColor(BUY_COLOR2);
      iBarLength = (this.getFuelOreBuying() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getFuelOreBuying() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.drawRect(x, y + 10, iBarLength, 10);
      iBarLength = (this.getOrganicsBuying() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getOrganicsBuying() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.drawRect(x, y + 20, iBarLength, 10);
      iBarLength = (this.getEquipmentBuying() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getEquipmentBuying() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.drawRect(x, y + 30, iBarLength, 10);

      g.setColor(SELL_COLOR1);
      iBarLength = (this.getFuelOreSelling() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getFuelOreSelling() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.fillRect(x, y + 10, iBarLength, 10);
      iBarLength = (this.getOrganicsSelling() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getOrganicsSelling() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.fillRect(x, y + 20, iBarLength, 10);
      iBarLength = (this.getEquipmentSelling() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getEquipmentSelling() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.fillRect(x, y + 30, iBarLength, 10);

      g.setColor(SELL_COLOR2);
      iBarLength = (this.getFuelOreSelling() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getFuelOreSelling() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.drawRect(x, y + 10, iBarLength, 10);
      iBarLength = (this.getOrganicsSelling() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getOrganicsSelling() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.drawRect(x, y + 20, iBarLength, 10);
      iBarLength = (this.getEquipmentSelling() > PORT_MAX) ? BAR_MAX_WIDTH * 2 : this.getEquipmentSelling() * BAR_MAX_WIDTH * 2 / PORT_MAX;
      g.drawRect(x, y + 30, iBarLength, 10);

      x += 120;
      y += 100;
      strTemp = this.getPortClass();
      if (strTemp.length() == 3) {
        for (j = 0; j < 3; j++) {
          if (strTemp.charAt(j) == 'B') {
            g.setColor(BUY_COLOR1);
            g.drawString("B", x += 20, y);
          } else if (strTemp.charAt(j) == 'S') {
            g.setColor(SELL_COLOR1);
            g.drawString("S", x += 20, y);
          }
        }
      }

      if (strTemp.equals("(Special)")) {
        g.drawString("Special", x, y);
      }


  }


  /**
    * fileString
    **/
  public String fileString() {
    String strToReturn;

    strToReturn = strName + "|" + strClass;

    return strToReturn;
  }

}
