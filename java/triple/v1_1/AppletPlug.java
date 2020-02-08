import java.awt.*;
import java.applet.*;

public class AppletPlug extends Applet {


  public void init() {
    Panel thePanel = new Panel();


    try {
      new TripleTriad();
      thePanel.add(new Label("Applet loaded successfully"));

    } catch (Exception e) {
      thePanel.add(new Label("" + e));
    }

    add(thePanel);


  }

  public void start() {

  }

  public void stop() {
    
  }



}
