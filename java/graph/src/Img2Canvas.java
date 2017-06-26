import java.awt.*;


class Img2Canvas extends Canvas {

private Image anImage;

public Img2Canvas(Image theImage) {
  anImage = theImage;
}

public void paint(Graphics g) {
  g.drawImage(anImage, 0, 0, this);

}








}
