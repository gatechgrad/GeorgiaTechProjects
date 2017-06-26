import java.awt.*;
import java.awt.event.*;

class ColorDialog extends Frame implements ActionListener, WindowListener, FocusListener, TextListener {

public static final int WIN_WIDTH = 300;
public static final int WIN_HEIGHT = 150;
public static final int TXT_SIZE = 1;
public static final String OK = "OK";
public static final String CANCEL = "Cancel";


private Button
  But0, But1, But2, But3, But4, But5, But6, But7, But8, But9, But10, But11,
  But12, But13, But14, But15, ButOK, ButCancel;

private Graph GraphToEdit;
private Color[] ColorArray;
private Frame parent;

private Label
  LblRed, LblGreen, LblBlue;

private TextField
  TxtRed, TxtGreen, TxtBlue;

public ColorDialog(Graph newGraph, Frame newParent) {
  super();
  GraphToEdit = newGraph;
  ColorArray = GraphToEdit.getColors();
  parent = newParent;

  makeWindow();

}

public void makeWindow() {
  setTitle("Change Graph Colors");
  setBounds(250, 250, WIN_WIDTH, WIN_HEIGHT);
  setBackground(new Color(192, 192, 192));
  setIconImage(((GraphMaster) parent).getIcon());
  setResizable(false);
  parent.setEnabled(false);

  ButOK = new Button(OK);
  ButCancel = new Button(CANCEL);
  ButOK.addActionListener(this);
  ButCancel.addActionListener(this);

  But0 = new Button();
  But1 = new Button();
  But2 = new Button();
  But3 = new Button();
  But4 = new Button();
  But5 = new Button();
  But6 = new Button();
  But7 = new Button();
  But8 = new Button();
  But9 = new Button();
  But10 = new Button();
  But11 = new Button();
  But12 = new Button();
  But13 = new Button();
  But14 = new Button();
  But15 = new Button();

  But0.setActionCommand("But0");
  But1.setActionCommand("But1");
  But2.setActionCommand("But2");
  But3.setActionCommand("But3");
  But4.setActionCommand("But4");
  But5.setActionCommand("But5");
  But6.setActionCommand("But6");
  But7.setActionCommand("But7");
  But8.setActionCommand("But8");
  But9.setActionCommand("But9");
  But10.setActionCommand("But10");
  But11.setActionCommand("But11");
  But12.setActionCommand("But12");
  But13.setActionCommand("But13");
  But14.setActionCommand("But14");
  But15.setActionCommand("But15");


  But0.addActionListener(this);
  But1.addActionListener(this);
  But2.addActionListener(this);
  But3.addActionListener(this);
  But4.addActionListener(this);
  But5.addActionListener(this);
  But6.addActionListener(this);
  But7.addActionListener(this);
  But8.addActionListener(this);
  But9.addActionListener(this);
  But10.addActionListener(this);
  But11.addActionListener(this);
  But12.addActionListener(this);
  But13.addActionListener(this);
  But14.addActionListener(this);
  But15.addActionListener(this);

  But0.setBackground(ColorArray[0]);
  But1.setBackground(ColorArray[1]);
  But2.setBackground(ColorArray[2]);
  But3.setBackground(ColorArray[3]);
  But4.setBackground(ColorArray[4]);
  But5.setBackground(ColorArray[5]);
  But6.setBackground(ColorArray[6]);
  But7.setBackground(ColorArray[7]);
  But8.setBackground(ColorArray[8]);
  But9.setBackground(ColorArray[9]);
  But10.setBackground(ColorArray[10]);
  But11.setBackground(ColorArray[11]);
  But12.setBackground(ColorArray[12]);
  But13.setBackground(ColorArray[13]);
  But14.setBackground(ColorArray[14]);
  But15.setBackground(ColorArray[15]);


  TxtRed = new TextField("0", TXT_SIZE);
  TxtGreen = new TextField("0", TXT_SIZE);
  TxtBlue = new TextField("0", TXT_SIZE);

  TxtRed.addFocusListener(this);
  TxtGreen.addFocusListener(this);
  TxtBlue.addFocusListener(this);

  TxtRed.addTextListener(this);
  TxtGreen.addTextListener(this);
  TxtBlue.addTextListener(this);

  LblRed = new Label("Red");
  LblGreen = new Label("Green");
  LblBlue = new Label("Blue");

  Font FontBold = new Font("Dialog", Font.BOLD, 12);
  LblRed.setFont(FontBold);
  LblGreen.setFont(FontBold);
  LblBlue.setFont(FontBold);

  setLabelColor();

  Panel ButtonPanel = new Panel(new GridLayout(1, 5, 5, 5));
    ButtonPanel.add(new Panel());
    ButtonPanel.add(new Panel());
    ButtonPanel.add(new Panel());
    ButtonPanel.add(ButOK);
    ButtonPanel.add(ButCancel);

  Panel ColorPanel = new Panel(new GridLayout(2, 8, 5, 5));
    ColorPanel.add(But0);
    ColorPanel.add(But1);
    ColorPanel.add(But2);
    ColorPanel.add(But3);
    ColorPanel.add(But4);
    ColorPanel.add(But5);
    ColorPanel.add(But6);
    ColorPanel.add(But7);
    ColorPanel.add(But8);
    ColorPanel.add(But9);
    ColorPanel.add(But10);
    ColorPanel.add(But11);
    ColorPanel.add(But12);
    ColorPanel.add(But13);
    ColorPanel.add(But14);
    ColorPanel.add(But15);

  Panel ControlPanel = new Panel( new GridLayout(3, 1, 5, 5));

    Panel RedPanel = new Panel(new GridLayout(1, 2, 5, 5));
    Panel GreenPanel = new Panel(new GridLayout(1, 2, 5, 5));
    Panel BluePanel = new Panel(new GridLayout(1, 2, 5, 5));
    RedPanel.add(LblRed);
    RedPanel.add(TxtRed);
    GreenPanel.add(LblGreen);
    GreenPanel.add(TxtGreen);
    BluePanel.add(LblBlue);
    BluePanel.add(TxtBlue);

    ControlPanel.add(RedPanel);
    ControlPanel.add(GreenPanel);
    ControlPanel.add(BluePanel);

  Panel TopPanel = new Panel();
    TopPanel.add(ColorPanel);
    TopPanel.add(new Panel());
    TopPanel.add(ControlPanel);
    TopPanel.add(new Panel());

  setLayout(new BorderLayout());
  add(ButtonPanel, BorderLayout.SOUTH);
  add(TopPanel, BorderLayout.CENTER);


  addWindowListener(this);
  setVisible(true);

}

public void setLabelColor() {
  int r, g, b;
  Color selectColor;
  Integer myInt;

  myInt = new Integer(TxtRed.getText());
  r = myInt.intValue();

  myInt = new Integer(TxtGreen.getText());
  g = myInt.intValue();

  myInt = new Integer(TxtBlue.getText());
  b = myInt.intValue();

  selectColor = new Color(r, g, b);

  LblRed.setForeground(selectColor);
  LblGreen.setForeground(selectColor);
  LblBlue.setForeground(selectColor);



}


public void shutdown() {
  setVisible(false);
  dispose();
  parent.setEnabled(true);
  parent.toFront();
}

public void update() {
  GraphToEdit.setColors(ColorArray);
  shutdown();
}

public void actionPerformed(ActionEvent e) {
  String theAction = e.getActionCommand();

  if (theAction.equals(CANCEL)) {
    shutdown();
  } else if (theAction.equals(OK)) {
    update();
  } else if (theAction.equals("But0")) {
    setColor(But0, 0);
  } else if (theAction.equals("But1")) {
    setColor(But1, 1);
  } else if (theAction.equals("But2")) {
    setColor(But2, 2);
  } else if (theAction.equals("But3")) {
    setColor(But3, 3);
  } else if (theAction.equals("But4")) {
    setColor(But4, 4);
  } else if (theAction.equals("But5")) {
    setColor(But5, 5);
  } else if (theAction.equals("But6")) {
    setColor(But6, 6);
  } else if (theAction.equals("But7")) {
    setColor(But7, 7);
  } else if (theAction.equals("But8")) {
    setColor(But8, 8);
  } else if (theAction.equals("But9")) {
    setColor(But9, 9);
  } else if (theAction.equals("But10")) {
    setColor(But10, 10);
  } else if (theAction.equals("But11")) {
    setColor(But11, 11);
  } else if (theAction.equals("But12")) {
    setColor(But12, 12);
  } else if (theAction.equals("But13")) {
    setColor(But13, 13);
  } else if (theAction.equals("But14")) {
    setColor(But14, 14);
  } else if (theAction.equals("But15")) {
    setColor(But15, 15);
  }

}

public void setColor(Button ButX, int i) {
  int r, g, b;
  Color selectColor;
  Integer myInt;

  myInt = new Integer(TxtRed.getText());
  r = myInt.intValue();
  TxtRed.setText(myInt.toString());

  myInt = new Integer(TxtGreen.getText());
  g = myInt.intValue();
  TxtGreen.setText(myInt.toString());

  myInt = new Integer(TxtBlue.getText());
  b = myInt.intValue();
  TxtBlue.setText(myInt.toString());

  selectColor = new Color(r, g, b);
  ButX.setBackground(selectColor);

  ColorArray[i] = selectColor;


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

public void windowDeactivated(WindowEvent e){
}



public void focusGained(FocusEvent e) {

  if (e.getComponent().equals(TxtRed)){ 
    TxtRed.selectAll();
  } else if (e.getComponent().equals(TxtGreen)){ 
    TxtGreen.selectAll();
  } else if (e.getComponent().equals(TxtBlue)){ 
    TxtBlue.selectAll();
  }



}

public void focusLost(FocusEvent e) {

  if (e.getComponent().equals(TxtRed)){ 
    TxtRed.select(0, 0);
  } else if (e.getComponent().equals(TxtGreen)){ 
    TxtGreen.select(0, 0);
  } else if (e.getComponent().equals(TxtBlue)){ 
    TxtBlue.select(0, 0);
  }


}

public void textValueChanged(TextEvent e) {
  setLabelColor();

}


}
