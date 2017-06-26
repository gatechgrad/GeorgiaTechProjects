import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class AddDialog extends Dialog implements ActionListener, WindowListener {

public static final int WIN_WIDTH = 300;
public static final int WIN_HEIGHT = 200;
public static final int OFFSET = 200;
public static final String OK = "OK";
public static final String CANCEL = "Cancel";
public static final String APPLY = "Apply";

private TextField
  TxtQuestion, TxtCorrect, TxtWrong1, TxtWrong2, TxtWrong3;
private Button
  ButOK, ButApply, ButCancel;
Vector VectClone;

public AddDialog(Vector newVectClone, Frame newFrame) {
  super(newFrame);
  VectClone = newVectClone;
  setLayout();
}



public void setLayout() {
  setBounds(OFFSET, OFFSET, WIN_WIDTH, WIN_HEIGHT);
  setTitle("Add A Question");


  Panel PnlInput = new Panel(new GridLayout(8, 1));

  Panel PnlButtons = new Panel(new GridLayout(1, 8));
    ButOK = new Button(OK);
    ButCancel = new Button(CANCEL);
    ButApply = new Button(APPLY);
    ButOK.addActionListener(this);
    ButCancel.addActionListener(this);
    ButApply.addActionListener(this);



    PnlButtons.add(new Panel());
    PnlButtons.add(new Panel());
    PnlButtons.add(new Panel());
    PnlButtons.add(new Panel());
    PnlButtons.add(new Panel());
    PnlButtons.add(ButOK);
    PnlButtons.add(ButCancel);
    PnlButtons.add(ButApply);

  setLayout(new BorderLayout());
  add(PnlInput, BorderLayout.CENTER);
  add(PnlButtons, BorderLayout.SOUTH);

  setVisible(true);
  addWindowListener(this);
}



public void actionPerformed(ActionEvent e) {
  String theAction = e.getActionCommand();
}


public void shutdown() {
  setVisible(false);
  dispose();
}

public void windowClosing(WindowEvent e) {
  shutdown();
}
public void windowClosed(WindowEvent e) {
}
public void windowOpened(WindowEvent e) {
}
public void windowIconified(WindowEvent e) {
}
public void windowDeiconified(WindowEvent e) {
}
public void windowActivated(WindowEvent e) {
}
public void windowDeactivated(WindowEvent e) {
}


}
