import java.awt.*;

class Correct extends Canvas {

private String theMessage;
private Color theColor;
private Font theFont;

public Correct() {
  theMessage = "";
  theColor = Color.black;
  theFont = new Font("Dialog", Font.PLAIN, 12);

}


public void paint(Graphics g) {
  g.setColor(theColor);

  g.drawString(theMessage, 20, 20);



}

public void setMessage(String newMessage, Color newColor, Font newFont) {
  theMessage = newMessage;
  theColor = newColor;
  theFont = newFont;
  setFont(newFont);
  repaint();
}




}
