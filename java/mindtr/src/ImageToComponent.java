import java.awt.*;

class ImageToComponent extends Canvas {

  private Image ImgSplash;
  private MediaTracker theMediaTracker;
  private int iWidth, iHeight;

  public ImageToComponent(String strFileName, int iWidth, int iHeight) {
    this.iWidth = iWidth;
    this.iHeight = iHeight;

    theMediaTracker = new MediaTracker(this);
    ImgSplash = Toolkit.getDefaultToolkit().getImage(strFileName);

    theMediaTracker.addImage(ImgSplash, 0);
    try {
      theMediaTracker.waitForAll();
    } catch (InterruptedException e) {}
    this.setBackground( new Color(191, 255, 255));

  }


  public void paint(Graphics g) {
    g.drawImage(ImgSplash, 0, 0, iWidth, iHeight, this);
  }


}
