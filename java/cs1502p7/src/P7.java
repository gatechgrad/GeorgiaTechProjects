/**
 * CS1502: Program #7
 *
 * <PRE>
 * Star Viewer
 *
 * Revisions:  1.0  February 26, 1999
 *                  Created class P7 (driver class)
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, February 26, 1999
 */


import java.awt.*;
import java.awt.event.*;



//EXTRA CREDIT:  I did the second extra credit.  It works best when the
//               zoom is set at about 500%
//               I also gave the user the ability to change the color of
//               the stars and names, and also change the size of the
//               stars... They can be found under "Options"
//
//               I had some really cool ideas for the other extra credit,
//               but unfortunately I ran out of time.

//NOTE:  I am using a new coding style for this program (similar to the
//       one in the textbook... yes, it's still me...


class P7 extends Frame implements ActionListener, WindowListener {

//*** CONSTANTS
public static final boolean DEBUG = true;  //debuggin constant
public static final int WINDOWHEIGHT = 500; //the height of the window
public static final int WINDOWWIDTH = 500; //the width of the window

public static final Font
  SMALLFONT = new Font("arial", Font.PLAIN, 12), //a small font
  COOLFONT = new Font("arial", Font.BOLD, 12); //the basic font used

public static final Color
  BLACK = new Color(0,0,0), //color black
  FGCOLOR = new Color(128, 128, 255), //the window foreground color
  BGCOLOR = new Color(8, 8, 64), //the window background color
  BUTCOLOR = new Color(0, 0, 148); //the button color

//*** COMPONENTS
Button
  butDirUp = new Button("Up"),         //up button
  butDirDown = new Button("Down"),     //down button
  butDirLeft = new Button("Left"),     //left button
  butDirRight = new Button("Right"),   //right button
  butZoomIn = new Button("+"),         //zoom in button
  butZoomOut = new Button("-"),        //zoom out button
  butOptions = new Button("Options"),  //options button
  butNames = new Button ("Names"),     //name toggle button
  butLines = new Button ("Lines"),     //line toggle button
  butLoad = new Button("Load"),        //load button
  butQuiz = new Button("Quiz");        //quiz button

Label
  lblZoom = new Label("Zoom", Label.CENTER); //zoom label

TextField
  txtLocation = new TextField(),           //loaction field
  txtZoomPercent = new TextField("", 4);   //zoom percent field

//*** INSTANCE VARIABLES
int iZoom;              //the current zoom
int iScrollFactor;      //the current scroll factor
int iZoomFactor;        //the current zoom factor
boolean namesShowing;   //are the names showing
boolean linesShowing;   //are the lines showing

StarCanvas myStarCanvas; //the canvas where the stars are drawn

/**
 *  P7 - the constructor... sets the instance variables and adds the
 *       components
 *  Pre - new P7
 *  Post - instance variables are set
 *
 */
public P7() {

//set Components... this method cut coding in half

//set properties of components
setComponent(butDirUp);
setComponent(butDirLeft);
setComponent(butDirRight);
setComponent(butDirDown);
setComponent(butZoomIn);
setComponent(butZoomOut);
setComponent(lblZoom);
setComponent(butLoad);
setComponent(butQuiz);
setComponent(butOptions);
setComponent(butNames);
setComponent(butLines);

//make panels
Panel
  topPanel = new Panel(),
  topPanel2 = new Panel(),
  panel2 = new Panel(),
  panel3 = new Panel(),
  panel4 = new Panel(),
  panel5 = new Panel(),
  panel6 = new Panel(),
  panel7 = new Panel(),
  panel8 = new Panel(),
  panel9 = new Panel(),
  panel10 = new Panel(),
  panel11 = new Panel(),

  bottomPanel = new Panel();

iZoom = 100;
txtZoomPercent.setText(iZoom + "%");
txtZoomPercent.setBackground(new Color(255,255,255));
txtZoomPercent.setFont(COOLFONT);
txtLocation.setBackground(new Color(255,255,255));


iScrollFactor = 20;
iZoomFactor = 50;
namesShowing = true;

setTitle("Command.Com's Star Viewer");
setSize(WINDOWWIDTH, WINDOWHEIGHT);
setBackground(BGCOLOR);

setLayout(new BorderLayout(5,5));

topPanel.setBackground(FGCOLOR);
add(txtLocation, BorderLayout.SOUTH);
add(topPanel, BorderLayout.NORTH);

  topPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 15, 2));

//Directional buttons
  panel2.setBackground(BLACK);
  topPanel.add(panel2);
  panel2.setLayout(new GridLayout(3,3, 5, 5));
    panel2.add(new Panel());
    panel2.add(butDirUp);
    panel2.add(new Panel());
    panel2.add(butDirLeft);
    panel2.add(new Panel());
    panel2.add(butDirRight);
    panel2.add(new Panel());
    panel2.add(butDirDown);
    panel2.add(new Panel());

//Zoom In/Out
  panel3.setBackground(BLACK);
  topPanel.add(panel3);
  panel3.setLayout(new GridLayout(2,1));
    panel3.add(panel6);
      panel6.add(butZoomOut);
      panel6.add(lblZoom);
      panel6.add(butZoomIn);

    panel3.add(panel7);
      panel7.add(txtZoomPercent);

//Names On/Off
  topPanel.add(panel4);
  panel4.setLayout(new GridLayout(3,1));
      panel4.add(butNames);
      panel4.add(butLines);

//Load Button
  topPanel.add(panel5);
  panel5.setLayout(new GridLayout(3,1));
    panel5.add(butLoad);
//    panel5.add(butQuiz);
    panel5.add(butOptions);

myStarCanvas = new StarCanvas(this);

//the canvas (bottomPanel)
add(myStarCanvas, BorderLayout.CENTER);

setVisible(true);
addWindowListener(this);
}


/**
 *  setComponent - since I had so many components, I just wrote a method
 *                 to automatically set them
 *  Pre - component must be passed in
 *  Post - component is set
 *  @param compName the component to be set
 *
 */

public void setComponent(Component compName) {
    compName.setForeground(FGCOLOR);
    compName.setFont(COOLFONT);
    if (compName instanceof Button) {
      Button buttonName = (Button) compName;
      buttonName.setBackground(BUTCOLOR);
      buttonName.addActionListener (this);
    }
}

/**
 *  actionPerformed - handles the action performed by the user
 *  Pre - user must do something
 *  Post - action is handled
 *  @param e the action performed
 *
 */
public void actionPerformed(ActionEvent e){
  String buttonPushed = e.getActionCommand ();

  if (buttonPushed.equals("Load")) {

    setVisible (false);
    dispose ();
    P7 myP7 = new P7();
  }

  if (buttonPushed.equals("+")) {
      myStarCanvas.setZoom(iZoomFactor);
      txtZoomPercent.setText(myStarCanvas.getIZoom() + "%");
  }

  if (buttonPushed.equals("-")) {
      myStarCanvas.setZoom(0 - iZoomFactor);
      txtZoomPercent.setText(myStarCanvas.getIZoom() + "%");
  }

  if (buttonPushed.equals("Up")) {
      myStarCanvas.setYOffset(iScrollFactor);
  }

  if (buttonPushed.equals("Down")) {
      myStarCanvas.setYOffset(0 - iScrollFactor);
  }

  if (buttonPushed.equals("Left")) {
      myStarCanvas.setXOffset(iScrollFactor);
  }

  if (buttonPushed.equals("Right")) {
      myStarCanvas.setXOffset(0 - iScrollFactor);
  }

  if (buttonPushed.equals("Names")) {
      myStarCanvas.toggleNames();
  }

  if (buttonPushed.equals("Lines")) {
      myStarCanvas.toggleLines();
  }

  if (buttonPushed.equals("Options")) {
    OptionDialog currentDialog = new OptionDialog(iZoomFactor,
      iScrollFactor, this, myStarCanvas);

    currentDialog.show();
  
  }

}


/**
 *  setTextField - sets the text field on the bottom of the screen
 *  Pre - string must be passed in to display
 *  Post - String is displayed on the bottom of the screen
 *  @param newText the string to be displayed
 *
 */
public void setTextField(String newText) {
  txtLocation.setText(newText);
}

/**
 *  window* - methods needed by WindowListener
 *  Pre - user must perform event
 *  Post - event is handled
 *  @param e the event performed
 *
 */
public void windowActivated(WindowEvent e) {
}

public void windowClosed(WindowEvent e) {
}

public void windowClosing (WindowEvent e) {
  quitP7();
}

/**
 *  quitP7 - shuts down the program
 *  Pre - window must be closing
 *  Post - program is shut down
 *
 */
public void quitP7(){
  setVisible (false);
  dispose ();
  System.exit (0);
}

public void windowDeactivated(WindowEvent e) {
}

public void windowDeiconified(WindowEvent e) {
}

public void windowIconified(WindowEvent e){
}

public void windowOpened(WindowEvent e){
}


/**
 *  main - makes a new P7
 *  Pre - nothing
 *  Post - new P7 is created
 *
 */

public static void main (String argv[]) {
  P7 myP7 = new P7();
}

}//endclass P7
