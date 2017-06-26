import java.awt.*;

class CoolPanel extends Container {

public static final Color LIGHT = Color.white;
public static final Color DARK = new Color(64, 64, 64);

private int iWidth;
private int iHeight;
private String title;
private boolean hasTitle;

public CoolPanel(int width, int height) {
  super();

  hasTitle = false;
  iWidth = width;
  iHeight = height;

}

public CoolPanel(String title, int width, int height) {
  super();

  hasTitle = true;
  iWidth = width;
  iHeight = height;
  this.title = title;

}


public void paint(Graphics g) {

  if (!hasTitle) {
    g.setColor(LIGHT);
    g.drawRect(2, 2, (iWidth + 1), (iHeight + 1));

    g.setColor(Color.black);
    g.fillRect(1, 1, 500, 500);

    System.out.println(iWidth + " " + iHeight);
  }
}










}
