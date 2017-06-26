import java.awt.*;
import java.applet.*;

public class AppletPlug extends Applet {


public void init() {
  String strAppletInfo;


  try {
    Solitare mySolitare = new Solitare();
  } catch (ClassFormatError e) { 
    oldBrowser();
  } catch (Exception e) {
    oldBrowser();
  }

}

public void start() {

}

public void stop() {

}


public void oldBrowser() {
  setLayout(new GridLayout(2, 1));
  add(new Label("Your Java viewer does not support JDK 1.1.7b"));
  add(new Label("Please upgrade your viewer, then try loading this page again"));
}


}
