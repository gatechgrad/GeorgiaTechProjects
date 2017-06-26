import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class InputBox extends Dialog implements ActionListener, WindowListener, TextListener, KeyListener {

public static final String ADD = "Add";
public static final String REMOVE = "Remove";
public static final String OK = "OK";
public static final String CANCEL = "Cancel";
public static final String APPLY = "Apply";
public static final int TXT_LENGTH = 10;
public static final int TXT_NUM_LENGTH = 7;
public static final int DIALOG_OFFSET = 50;
public static final int DIALOG_WIDTH=450;
public static final int DIALOG_HEIGHT=300;
public static final Color BKG_COLOR = new Color(192, 192, 192);
public static final int LIST_ROWS = 10;
public static final int LIST_DISP_SIZE = 8;
public static final int SPACING = 2;

private Graph GraphToEdit;
private Frame ParentFrame;

private Button
  ButAdd, ButOK, ButCancel, ButRemove, ButApply;
private TextField
  TxtName, TxtDataValue, TxtTitle;

private List dataList;
private Vector VectList;



public InputBox(Graph newGraph, Frame parent) {
  super(parent);

  ParentFrame = parent;
  GraphToEdit = newGraph;

  setupList();
  makeDialog();


  Point PWinPos;
  int iDialogX, iDialogY;

  setTitle("Edit/Create a Graph");


  PWinPos = parent.getLocation();
  iDialogX = PWinPos.x + DIALOG_OFFSET;
  iDialogY = PWinPos.y + DIALOG_OFFSET;


  setBounds(iDialogX, iDialogY, DIALOG_WIDTH, DIALOG_HEIGHT);
  setModal(true);
  addWindowListener(this);
}

public void makeDialog() {

  Panel inputPanel = new Panel();
    Label LblTitle = new Label("Enter the title of the graph");

    Label LblName = new Label("Enter the name of an element");
    Label LblDataValue = new Label("Enter the value of an element");
    ButAdd = new Button(ADD);
    TxtName = new TextField(TXT_LENGTH);
    TxtTitle = new TextField(TXT_LENGTH);
    TxtDataValue = new TextField(TXT_NUM_LENGTH);

    TxtName.addTextListener(this);
    TxtDataValue.addTextListener(this);
    TxtTitle.addTextListener(this);

    TxtDataValue.addKeyListener(this);
    TxtName.addKeyListener(this);

    TxtName.setText("");
    TxtDataValue.setText("");
    TxtTitle.setText(GraphToEdit.getGraphTitle());

    inputPanel.setLayout(new BorderLayout(10, 10));

    Panel titlePanel = new Panel(new BorderLayout(3, 3));
    Panel title2Panel = new Panel(new GridLayout(2, 1, 3, 3));
    Panel addPanel = new Panel(new GridLayout(8, 1, 3, 3));


    title2Panel.add(LblTitle);
    title2Panel.add(TxtTitle);

    titlePanel.add(new Panel(), BorderLayout.WEST);
    titlePanel.add(title2Panel, BorderLayout.CENTER);

    addPanel.setBackground(new Color(224, 224, 224));
//    addPanel.setForeground(new Color(255, 0, 0));
    addPanel.add(new Label("Add Element"));
    addPanel.add(LblName);
    addPanel.add(TxtName);
    addPanel.add(LblDataValue);
    addPanel.add(TxtDataValue);

    inputPanel.add(titlePanel, BorderLayout.NORTH);
    inputPanel.add(addPanel, BorderLayout.CENTER);
    inputPanel.add(new Panel(), BorderLayout.WEST);

  Panel buttonPanel = new Panel();
    buttonPanel.setLayout(new GridLayout(1, 9, 1, 1));
    ButOK = new Button(OK);
    ButApply = new Button(APPLY);
    ButCancel = new Button(CANCEL);
    ButAdd = new Button(ADD);
    ButRemove = new Button(REMOVE);

    ButAdd.setEnabled(false);
    ButApply.setEnabled(false);
    if (dataList.getItemCount() == 0) {
      ButRemove.setEnabled(false);
    }



    buttonPanel.add(ButAdd);
    buttonPanel.add(ButRemove);
    buttonPanel.add(new Panel());
    buttonPanel.add(new Panel());
    buttonPanel.add(new Panel());
    buttonPanel.add(ButOK);
    buttonPanel.add(ButCancel);
    buttonPanel.add(ButApply);

    ButAdd.addActionListener(this);
    ButRemove.addActionListener(this);
    ButOK.addActionListener(this);
    ButCancel.addActionListener(this);
    ButApply.addActionListener(this);

  Panel dataPanel = new Panel(new BorderLayout());
    dataPanel.add((new Label("Current Data")), BorderLayout.NORTH);
    dataPanel.add(dataList, BorderLayout.CENTER);
    dataPanel.add(new Panel(), BorderLayout.EAST);

  Panel topPanel = new Panel(new BorderLayout(20, 20));
    topPanel.add(inputPanel, BorderLayout.CENTER);
    topPanel.add(dataPanel, BorderLayout.EAST);

  setLayout(new BorderLayout(20, 20));
  setBackground(BKG_COLOR);
  add(topPanel, BorderLayout.CENTER);
  add(buttonPanel, BorderLayout.SOUTH);






}


public void setupList() {
  int i;
  int j;
  String DisplayString;

  dataList = new List(LIST_ROWS);
  dataList.setFont(new Font("Courier", Font.PLAIN, 12));
  VectList = new Vector();
  VectList = GraphToEdit.getData();



  for (i = 0; i < VectList.size(); i++) {
    DisplayString = ((GraphElement) VectList.elementAt(i)).getName();
    if (DisplayString.length() > LIST_DISP_SIZE) {
       DisplayString = DisplayString.substring(0, LIST_DISP_SIZE);
    }

    for (j = (DisplayString.length() - 1); j < (LIST_DISP_SIZE + SPACING); j++) {
      DisplayString = DisplayString + " ";
    }

    dataList.add(DisplayString + ((GraphElement) VectList.elementAt(i)).getDataValue());
  }


}


/*
 * updateData - replaces the graph data list with the temporary data list
 */
public void updateData() {
  GraphToEdit.setGraphTitle(TxtTitle.getText());
  GraphToEdit.setData((Vector) VectList.clone());  
  ButApply.setEnabled(false);

}


public void setGraph(Graph newGraph) {
  GraphToEdit = newGraph;
}


/*
 * addGraphElement - adds an element to the temporary list
 */
public void addGraphElement(String name, float value) {
  int i;
  boolean duplicate = false;

  for (i = 0; i < VectList.size(); i++) {
    if ( ((GraphElement) VectList.elementAt(i)).getName().equals(name)) {
      WarningDialog myWD = new WarningDialog("There is already an element with this name", ParentFrame); 
      myWD.setVisible(true);
      duplicate = true;
    }

  }

  if (!duplicate) {
    String DisplayString;
    int j;

    GraphElement myGraphEl = new GraphElement(name, value);
    VectList.addElement(myGraphEl);

    DisplayString = name;
    if (DisplayString.length() > LIST_DISP_SIZE) {
       DisplayString = DisplayString.substring(0, LIST_DISP_SIZE);
    }
    for (j = (DisplayString.length() - 1); j < (LIST_DISP_SIZE + SPACING); j++) {
      DisplayString = DisplayString + " ";
    }
    dataList.add(DisplayString + ((GraphElement) VectList.elementAt(i)).getDataValue());
  }
  //make the necessary changes to the buttons
  if (ButRemove.isEnabled() == false) {
    ButRemove.setEnabled(true);
  }

  if (ButApply.isEnabled() == false) {
    ButApply.setEnabled(true);
  }

  TxtDataValue.setText("");
  TxtName.setText("");
  TxtName.requestFocus();
}

/*
 * removeGraphElement - removes an element from the temporary changes list
 */
public void removeGraphElement(int iLocation) {

  if (iLocation != -1) {
    VectList.removeElementAt(iLocation);
    dataList.remove(iLocation);

    if (dataList.getItemCount() == 0) {
      ButRemove.setEnabled(false);
    }

    if (ButApply.isEnabled() == false) {
      ButApply.setEnabled(true);
    }
  }
}



public void actionPerformed(ActionEvent e) {
  String strAction;
  String TxtNameValue, TxtDataValueValue;

  strAction = e.getActionCommand();

  if (strAction.equals(CANCEL)) {
    setVisible(false);
    dispose();
  } else if (strAction.equals(ADD)) {
     TxtDataValueValue = TxtDataValue.getText();
     TxtNameValue = TxtName.getText();

     if (isNumber(TxtDataValueValue)) {
        addGraphElement(TxtNameValue, (new Float(TxtDataValueValue)).floatValue());
     } else {
        WarningDialog WarnNumError = new WarningDialog("Data Value must be a number", ParentFrame);
        WarnNumError.setVisible(true);
     }

  } else if (strAction.equals(OK)) {
    updateData();
    setVisible(false);
    dispose();

  } else if (strAction.equals(REMOVE)) {
    TxtDataValueValue = TxtDataValue.getText();
    TxtNameValue = TxtName.getText();


    removeGraphElement(dataList.getSelectedIndex());
  } else if (strAction.equals(APPLY)) {
    updateData();
  }

}

public void windowActivated(WindowEvent e) {
}

public void windowDeactivated(WindowEvent e) {
}

public void windowOpened(WindowEvent e) {
}

public void windowClosed(WindowEvent e) {
}

public void windowClosing(WindowEvent e) {
  setVisible(false);
  dispose();

}

public void windowIconified(WindowEvent e) {
}

public void windowDeiconified(WindowEvent e) {
}



public void textValueChanged(TextEvent e) {
  if ((TxtName.getText().equals("")) || (TxtDataValue.getText().equals(""))) {
    ButAdd.setEnabled(false);
  } else {
    ButAdd.setEnabled(true);
  }

  if (e.getSource().equals(TxtTitle)) {
    ButApply.setEnabled(true);
  }

}


private boolean isNumber(String testString) {
  boolean isNumber;
  boolean hasDot;
  int i;

  isNumber = true;
  hasDot = false;

  for (i = 0; i < testString.length(); i++) {
    if (!Character.isDigit(testString.charAt(i)) && !(testString.charAt(i) == '.')) {
      isNumber = false;
    } else if (testString.charAt(i) == '.') {
      if (hasDot == true) {
        isNumber = false;
      } else {
        hasDot = true;
      }

    }

  }

  return isNumber;
  
}

public void keyPressed(KeyEvent e) {
}

public void keyReleased(KeyEvent e) {
//  System.out.println("Num: " + e.getKeyCode() + "\nChar: " + e.getKeyChar());
  if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (ButAdd.isEnabled())) {
    String TxtDataValueValue = TxtDataValue.getText();
    String TxtNameValue = TxtName.getText();

    addGraphElement(TxtNameValue, (new Float(TxtDataValueValue)).floatValue());
  }
}

public void keyTyped(KeyEvent e) {
}


}
