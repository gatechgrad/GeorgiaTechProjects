/**
 * CS1502: Program #7
 *
 * <PRE>
 * Star Viewer
 *
 * Revisions:  1.0  February 26, 1999
 *                  Created class Option Dialog
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, February 26, 1999
 */

//This class was supposed to contain all of the features that my program
//was going to implement, but I only got two to actually work in the
//time that I had...

import java.awt.*;
import java.awt.event.*;

public class OptionDialog extends Dialog implements ActionListener,
  WindowListener {

//*** CONSTANTS
public static final boolean DEBUG = false;//debugging

//*** COMPONENTS
Button
  Okay = new Button ("Okay"), //okay button
  Cancel = new Button ("Cancel"); //cancel button

Label
  lblZoomFactor = new Label ("Zoom Factor : "),  //not used
  lblScrollFactor = new Label ("Scroll Factor : "),  //not used
  lblGridLines = new Label ("Grid lines :"),  //not used
  lblStarSize = new Label ("Star Size :"),  //star size label
  lblColor = new Label ("Color :");  //color label

TextField
  txtScrollFactor, //not used
  txtZoomFactor;  //not used

Choice
  starsizeChoice = new Choice(), //choices of the star size
  gridChoice = new Choice(),  //not used
  colorChoices = new Choice();  //color choices

StarCanvas myStarCanvas; //the current star canvas

/**
 *  OptionDialog - the constructor... sets variables, panels and components
 *  Pre - new Option Dialog
 *  Post - Dialog is made and variables are set
 *  @param iCurrentZoomFactor the current zoom factor
 *  @param iCurrentScrollFactor the current scroll factor
 *  @param parentFrame the P7 frame
 *  @param myStarCanvas the star canvas in the program
 *
 */
public OptionDialog (int iCurrentZoomFactor, int
    iCurrentScrollFactor, Frame parentFrame, StarCanvas myStarCanvas) {
  super (parentFrame, "Options", true);
  txtZoomFactor = new TextField(iCurrentZoomFactor);
  txtScrollFactor = new TextField(iCurrentScrollFactor + "%");

  setSize(300,300);

  Panel
    leftPanel = (new Panel()),
    rightPanel = (new Panel()),
    panel1 = (new Panel()),
    panel2 = (new Panel()),
    panel3 = (new Panel()),
    panel4 = (new Panel()),
    panel5 = (new Panel()),
    panel6 = (new Panel());

  setLayout(new BorderLayout());

  add(leftPanel, BorderLayout.CENTER);
    leftPanel.setLayout(new GridLayout(3,1));
//      leftPanel.add(panel1);
//        panel1.add(lblScrollFactor);
//        panel1.add(txtScrollFactor);
//      leftPanel.add(panel3);
//        panel3.add(lblZoomFactor);
//        panel3.add(txtZoomFactor);
      leftPanel.add(panel5);
        panel5.add(lblColor);
        panel5.add(colorChoices);

        colorChoices.add("Blue");
        colorChoices.add("Green");
        colorChoices.add("Cyan");
        colorChoices.add("Red");
        colorChoices.add("Magenta");
        colorChoices.add("Yellow");
        colorChoices.add("White");

      leftPanel.add(panel6);
          panel6.add(lblStarSize);
          panel6.add(starsizeChoice);
          starsizeChoice.add("Point");
          starsizeChoice.add("Small");
          starsizeChoice.add("Medium");
          starsizeChoice.add("Large");

  add(rightPanel, BorderLayout.EAST);
    rightPanel.setLayout (new GridLayout(7, 1, 5, 5));
    rightPanel.add(Okay);
    Okay.addActionListener(this);
    rightPanel.add(Cancel);
    Cancel.addActionListener(this);

  this.myStarCanvas = myStarCanvas;
  addWindowListener(this);
}



/**
 *  actionPerformed - handles the user's action
 *  Pre - user does something
 *  Post - action is handled
 *  @param e the action performed by the user
 *
 */
public void actionPerformed(ActionEvent e) {
  String buttonPressed = e.getActionCommand();

  if (buttonPressed.equals("Cancel")) {
    quitOptionDialog();
  }

  if (buttonPressed.equals("Okay")) {
    // do something
    String strColor = colorChoices.getSelectedItem();
    if (DEBUG) System.out.println("Color has been changed to :" + strColor);

    Color colorChange;

    if (strColor.equals("Blue")) {
      colorChange = new Color (0, 0, 255);
    } else if (strColor.equals("Green")) {
      colorChange = new Color(0, 255, 0);
    } else if (strColor.equals("Cyan")) {
      colorChange = new Color(0, 255, 255);
    } else if (strColor.equals("Red")) {
      colorChange = new Color(255, 0, 0);
    } else if (strColor.equals("Magenta")) {
      colorChange = new Color(255, 0, 255);
    } else if (strColor.equals("Yellow")) {
      colorChange = new Color(255, 255, 0);
    } else if (strColor.equals("White")) {
      colorChange = new Color(255, 255, 255);
    } else {
      colorChange = new Color (0, 255, 0);
    }

    String strStarSize = starsizeChoice.getSelectedItem();
    int iStarSize;

    if (strStarSize.equals("Point")) {
      iStarSize = 1;
    } else if (strStarSize.equals("Small")) {
      iStarSize = 2;
    } else if (strStarSize.equals("Medium")) {
      iStarSize = 5;
    } else if (strStarSize.equals("Large")) {
      iStarSize = 10;
    } else {
      iStarSize = 1;
    }

//    int newZoom = Integer.parseInt (txtZoomFactor.getText());
//    myStarCanvas.setZoomFactor(newZoom);
    myStarCanvas.setColor(colorChange);
    myStarCanvas.setStarSize(iStarSize);

    quitOptionDialog();
  }
}


/**
 *  window* - handles window listener commands
 *  Pre - user does something to the window
 *  Post - window event handled appropriately
 *  @param e the window event
 *
 */
public void windowActivated(WindowEvent e) {
}

public void windowClosed(WindowEvent e) {
}

public void windowClosing (WindowEvent e) {
  quitOptionDialog();
}

public void quitOptionDialog(){
  setVisible (false);
  dispose ();
}

public void windowDeactivated(WindowEvent e) {
}

public void windowDeiconified(WindowEvent e) {
}

public void windowIconified(WindowEvent e){
}

public void windowOpened(WindowEvent e){
}


}//endclass OptionDialog
