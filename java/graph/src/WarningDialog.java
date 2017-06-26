import java.awt.*;
import java.awt.event.*;

class WarningDialog extends Dialog implements ActionListener, WindowListener {

public static final int WD_WIDTH = 300;
public static final int WD_HEIGHT = 100;
public static final String OK = "OK";

private String strWarning;
private Frame thisParent;

private Button
  ButOK;

public WarningDialog(String newWarning, Frame parent)  {
  super(parent);

  thisParent = parent;
  strWarning = newWarning;

  setPosition();
  setTitle("Warning");
  setResizable(false);
  setModal(true);
  setup();

  addWindowListener(this);
}

public void setup() {
  Toolkit myToolkit = Toolkit.getDefaultToolkit();
  GraphMaster theGM = (GraphMaster) thisParent;


  Image ImgSkull = theGM.getMyImage("skull.gif");

  Img2Canvas CanvasSkull = new Img2Canvas(ImgSkull);
  CanvasSkull.setSize(32, 32);

  Label LblWarning = new Label(strWarning);
    LblWarning.setFont(new Font("Dialog", Font.BOLD, 12));

  Panel warnPanel = new Panel(new BorderLayout(3,3));
    warnPanel.add(CanvasSkull, BorderLayout.WEST);
    warnPanel.add(LblWarning, BorderLayout.CENTER);

  ButOK = new Button(OK);
  ButOK.addActionListener(this);
  Panel butPanel = new Panel(new GridLayout(1, 3));
    butPanel.add(new Panel());
    butPanel.add(ButOK);
    butPanel.add(new Panel());


  setLayout(new BorderLayout());
  add(warnPanel, BorderLayout.CENTER);
  add(butPanel, BorderLayout.SOUTH);
  add(new Panel(), BorderLayout.WEST);

  myToolkit.beep();
  


}



public void setPosition() {
  int iLocX;
  int iLocY;

  iLocX = (thisParent.getSize().width / 2) - (WD_WIDTH / 2);
  iLocY = (thisParent.getSize().height / 2) - (WD_HEIGHT / 2);

  setBounds(iLocX, iLocY, WD_WIDTH, WD_HEIGHT);


}


public void actionPerformed(ActionEvent e) {
  String theAction = e.getActionCommand();

  if (theAction.equals(OK)) {
    shutDown();
  }

}

public void shutDown() {
  setVisible(false);
  dispose();
}


public void windowOpened(WindowEvent e) {
}

public void windowClosed(WindowEvent e) {
}

public void windowClosing(WindowEvent e) {
  shutDown();
}

public void windowActivated(WindowEvent e) {
}

public void windowDeactivated(WindowEvent e) {
}

public void windowIconified(WindowEvent e) {
}

public void windowDeiconified(WindowEvent e) {
}



public static void main (String argv[]) {
  Frame myFrame = new Frame();
  WarningDialog myWD = new WarningDialog("Hello World", myFrame);
  myWD.setVisible(true);

}


}
