import java.awt.image.*;
import java.awt.*;

class MyRGBImageFilter extends RGBImageFilter {


  public MyRGBImageFilter() {canFilterIndexColorModel = true;}

  public int filterRGB(int x, int y, int rgb) {

  int a = rgb & 0xff000000;
  int r = (((rgb & 0xff0000) + 0x18000000)/3)& 0xff0000;
  int g = (((rgb & 0x00ff00) + 0x018000)/3)& 0x00ff00;
  int b = (((rgb & 0x0000ff) + 0x000018)/3)& 0x0000ff;
  return a|r|g|b;

  }


}
