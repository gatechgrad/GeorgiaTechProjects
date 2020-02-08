import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.util.StringTokenizer;

class GotoDialog extends JDialog implements LineParser, ActionListener {
  /** CONSTANTS **/
  public static final String OKAY = "Okay";
  public static final String CANCEL = "Cancel";

  public static final int STATE_NONE = 0;
  public static final int STATE_GET_PATH = 1;


  /** INSTANCE VARIABLES **/
  JTextField txtSector;
  JTextField txtLimpets, txtArmid, txtFighters;

  ButtonGroup bgMode;
  JRadioButton rbSingleStep, rbExpress, rbStopAtPorts, rbUseTranswarp, rbLeaveTrail;
  JButton butOkay, butCancel;
  TWA theTWA;

  int iSector;
  Vector vectPath;

  int iFightersToLeave;
  int iArmidToLeave;
  int iLimpetsToLeave;

  int iState;

  /**
    * GotoDialog
    **/
  public GotoDialog(Frame owner, TWA theTWA) {
    super(owner);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    this.theTWA = theTWA;
    makeWindow();
  }


  /**
    * makeWindow
    **/
  private void makeWindow() {
    JPanel pnlLeft, pnlRight, pnlChoices, pnlTrail;

    pnlRight = new JPanel();
    pnlLeft = new JPanel(new BorderLayout());
    pnlChoices = new JPanel(new GridLayout(4, 1));

    txtSector = new JTextField(6);

    bgMode = new ButtonGroup();

      rbSingleStep = new JRadioButton("SingleStep");
      rbSingleStep.setMnemonic('s');
      bgMode.add(rbSingleStep);
      pnlChoices.add(rbSingleStep);

      rbExpress = new JRadioButton("Express");
      rbExpress.setMnemonic('e');
      bgMode.add(rbExpress);
      pnlChoices.add(rbExpress);

      rbStopAtPorts = new JRadioButton("Stop At Ports");
      rbStopAtPorts.setMnemonic('p');
      bgMode.add(rbStopAtPorts);
      pnlChoices.add(rbStopAtPorts);

      rbUseTranswarp = new JRadioButton("Use Transwarp");
      rbUseTranswarp.setMnemonic('t');
      bgMode.add(rbUseTranswarp);
      pnlChoices.add(rbUseTranswarp);

      rbLeaveTrail = new JRadioButton("Leave Trail");
      rbSingleStep.setMnemonic('L');
      bgMode.add(rbLeaveTrail);
      pnlChoices.add(rbLeaveTrail);
      rbLeaveTrail.setEnabled(false);



      pnlLeft.add(txtSector, BorderLayout.NORTH);
      pnlLeft.add(pnlChoices, BorderLayout.CENTER);

      butOkay = new JButton(OKAY);
      butOkay.addActionListener(this);
      butCancel = new JButton(CANCEL);
      butCancel.addActionListener(this);

      pnlRight.add(butOkay);
      pnlRight.add(butCancel);

    pnlTrail = new JPanel(new GridLayout(3, 2));
      pnlTrail.setEnabled(false);

      txtArmid = new JTextField(6);
      txtLimpets = new JTextField(6);
      txtFighters = new JTextField(6);

      txtArmid.setEnabled(false);
      txtLimpets.setEnabled(false);
      txtFighters.setEnabled(false);

      pnlTrail.add(new JLabel("Fighters to leave behind"));
      pnlTrail.add(txtFighters);
      pnlTrail.add(new JLabel("Armid Mines to leave behind"));
      pnlTrail.add(txtArmid);
      pnlTrail.add(new JLabel("Limpet Mines to leave behind"));
      pnlTrail.add(txtLimpets);

      pnlLeft.add(pnlTrail, BorderLayout.SOUTH);


      getContentPane().setLayout(new BorderLayout());
      getContentPane().add(pnlLeft, BorderLayout.CENTER);
      getContentPane().add(pnlRight, BorderLayout.EAST);






    this.pack();
    setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
    this.show();
  }


  /**
    * parseString
    **/
  public void parseString(String theString) {
    String strTemp;
    int iChoice; 


    if (theString.indexOf("Do you want to engage the TransWarp drive?") > -1) {
      if (rbUseTranswarp.isSelected()) {
        theTWA.getTelnet().getSender().sendData("y");
      } else {
        theTWA.getTelnet().getSender().sendData("n");
      }
    } else if (  (theString.indexOf("Command") > -1) &&
          (theString.indexOf("[" + theTWA.getCurrentSector().getNumber() + "]") > -1) &&
          (rbLeaveTrail.isSelected())
       ) {

       if (iFightersToLeave > 0) {
         theTWA.getTelnet().getSender().sendData("f" + iFightersToLeave + "\rc");
       }

       if (iArmidToLeave > 0) {
         theTWA.getTelnet().getSender().sendData("h1" + iArmidToLeave + "\rc");
       }

       if (iLimpetsToLeave > 0) {
         theTWA.getTelnet().getSender().sendData("h2" + iLimpetsToLeave + "\rc");
       }

       if (vectPath.size() > 2) {
         vectPath.removeElementAt(0);
         theTWA.getTelnet().getSender().sendData(vectPath.elementAt(0) + "\r");

       } else {
        JOptionPane.showMessageDialog(this, "No more sectors in path.", "Path is empty", JOptionPane.WARNING_MESSAGE);
       }

      


    } else if (theString.indexOf("Engage the Autopilot? (Y/N/Single step/Express) [Y]") > -1) {
      if (rbSingleStep.isSelected()) {
        theTWA.getTelnet().getSender().sendData("S");
      } else if (rbExpress.isSelected()) {
        theTWA.getTelnet().getSender().sendData("E");
      } else if (rbStopAtPorts.isSelected()) {
        theTWA.getTelnet().getSender().sendData("Y");
      } else if (rbLeaveTrail.isSelected()) {
        theTWA.getTelnet().getSender().sendData("N");

      }
    } else if (theString.indexOf("Stop in this sector (Y,N,E,I,R,S,D,P,?) (?=Help) [N] ?") > -1) {
      iChoice = JOptionPane.showConfirmDialog(this, "Stop in this sector??", "Stop??", JOptionPane.YES_NO_OPTION);

      if (iChoice == JOptionPane.YES_OPTION) {
        theTWA.getTelnet().getSender().sendData("Y");
        quitParsing();

      } else {
        theTWA.getTelnet().getSender().sendData("N");
      }

    } else if (  (theString.indexOf("The shortest path") > -1) &&
                 (theString.indexOf("from sector " + theTWA.getCurrentSector().getNumber() +  " to sector " + iSector + " is:") > -1)
              ) {

      vectPath = new Vector();
      iState = STATE_GET_PATH;
    } else if (iState == STATE_GET_PATH) {
      if (theString.indexOf("Engage the Autopilot? (Y/N/Single step/Express) [Y]") > -1) {
        iState = STATE_NONE;
      } else {
        StringTokenizer theST = new StringTokenizer(theString, " >");

        while (theST.hasMoreTokens()) {
          vectPath.add(theST.nextToken());
        }
      }


    } else if (  (theString.indexOf("Arriving sector :") > -1) &&
                 (theString.indexOf("Autopilot disengaging.") > -1)
              ) {
      quitParsing();
    } else if (theString.indexOf("TransWarp Drive shutting down.") > -1) {
      quitParsing();
    } else if (  (theString.indexOf("Command") > -1) &&
            (theString.indexOf("[" + iSector + "]") > -1)
         ) {
      quitParsing();


    }
  }



  /**
    * quitParsing
    **/
  public void quitParsing() {
    theTWA.getParser().getParsers().removeElement(this); //start getting lines from the parser
    theTWA.getTelnet().getSender().setUserInputEnabled(true);
    theTWA.getScreen().repaint();
  }

  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand = e.getActionCommand();

    if (strCommand.equals(OKAY)) {

      iSector = Parser.toInteger(txtSector.getText(), -1);
      iFightersToLeave = Parser.toInteger(txtFighters.getText(), 0);
      iArmidToLeave = Parser.toInteger(txtArmid.getText(), 0);
      iLimpetsToLeave = Parser.toInteger(txtLimpets.getText(), 0);

      iState = STATE_NONE;


      if (iSector != -1) {
        theTWA.getParser().getParsers().addElement(this); //start getting lines from the parser

        theTWA.getTelnet().getSender().sendData("m" + iSector + "\r");

        setVisible(false);
        dispose();
      }


    } else if (strCommand.equals(CANCEL)) {
        setVisible(false);
        dispose();
    }



  }



}
