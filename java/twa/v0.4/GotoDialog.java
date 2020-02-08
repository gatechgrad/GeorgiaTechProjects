import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GotoDialog extends JDialog implements LineParser {
  /** CONSTANTS **/
  public static final String OKAY = "Okay";
  public static final String CANCEL = "Cancel";


  /** INSTANCE VARIABLES **/
  JTextField txtSector;
  JCheckBox jcUseTranswarp;
  ButtonGroup bgMode;
  JRadioButton rbSingleStep, rbExpress, rbStopAtPorts;
  JButton butOkay, butCancel;
  TWA theTWA;

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
    JPanel pnlLeft, pnlRight, pnlChoices;

    pnlRight = new JPanel();
    pnlLeft = new JPanel(new BorderLayout());
    pnlChoices = new JPanel();

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

      jcUseTranswarp = new JCheckBox("Use Transwarp");
      pnlChoices.add(jcUseTranswarp);


      pnlLeft.add(txtSector, BorderLayout.NORTH);
      pnlLeft.add(pnlChoices, BorderLayout.CENTER);

      butOkay = new JButton(OKAY);
      butCancel = new JButton(CANCEL);

      pnlRight.add(butOkay);
      pnlRight.add(butCancel);

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
  }



  /**
    * quitParsing
    **/
  public void quitParsing() {
    theTWA.getParser().getParsers().removeElement(this); //start getting lines from the parser
    theTWA.getTelnet().getSender().setUserInputEnabled(true);
    theTWA.getScreen().repaint();
  }



}
