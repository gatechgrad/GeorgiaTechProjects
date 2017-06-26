import java.awt.*;
import java.applet.*;

public class AppletPlug extends Applet {


public void init() {

  try {
    GraphMaster myGraphMaster = new GraphMaster();
  } catch (ClassFormatError e) { 
    oldBrowser(e.getMessage());
  } catch (Exception e) {
    oldBrowser(e.getMessage());
  }

}

public void start() {

}

public void stop() {

}


public void oldBrowser(String e) {
  setLayout(new GridLayout(3, 1));
  add(new Label("Your Java viewer does not support JDK 1.1.7b"));
  add(new Label("Please upgrade your viewer, then try loading this page again"));

  Label LblError = new Label("Error: " + e);
  LblError.setBackground(Color.black);
  LblError.setForeground(Color.red);
  LblError.setFont(new Font("Dialog", Font.BOLD, 12));
  add(LblError);

}


}
