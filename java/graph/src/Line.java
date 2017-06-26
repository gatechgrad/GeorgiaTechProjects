import java.awt.*;

class Line extends Canvas {

public static final Color LIGHT = new Color(255, 255, 255);
public static final Color DARK = new Color(0, 0 , 0);

private String title;
private int iLength = 1024; //Make this bigger if you need a line larger
                            //than 1024 pixels
private boolean hasTitle = false;


public Line() {
  title = "";

}

public Line(String newTitle) {
  title = newTitle;
  hasTitle = true;

}


public void paint (Graphics g) {

  if (hasTitle) {
    g.setColor(Color.white);
    g.drawLine (0, 5, 10, 5);
    g.drawLine (50, 5, iLength, 5);

    g.setColor(DARK);
    g.drawLine (0, 6, 10, 6);
    g.drawLine (50, 6, iLength, 6);
  } else {
    g.setColor(LIGHT);
    g.drawLine(0, 5, iLength, 5);
    g.setColor(DARK);
    g.drawLine(0, 6, iLength, 6);

  }
  

}







}
