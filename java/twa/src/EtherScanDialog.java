import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

class EtherScanDialog extends JDialog implements ActionListener, LineParser {
  /** CONSTANTS **/
  public static final String OKAY = "Okay";
  public static final String CANCEL = "Cancel";


  /** INSTANCE VARIABLES **/
  JButton butOkay, butCancel;
  JTextField txtStartSector, txtStopSector;
  JCheckBox jcbSaveData;
  ScriptWindow theScriptWindow;
  TWA theTWA;

  int iState;

  int iStartSector;
  int iStopSector;
  int iCurrentSector;
  int iTravel;
  int iPrevious;

  boolean writeData;

  File fileSave;
  String strData;

  /**
    * EtherScanDialog
    **/
  public EtherScanDialog(Frame owner, TWA theTWA) {
    super(owner);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    strData = "";

    this.theTWA = theTWA;
    makeWindow();

  }

  /**
    * makeWindow
    **/
  private void makeWindow() {
    JPanel pnlMid = new JPanel(new GridLayout(2, 2));
    JPanel pnlSave = new JPanel();
    JPanel pnlButtons;


    pnlMid.add(new JLabel("Start scanning at sector: "));
    pnlMid.add(new JLabel("Stop scanning at sector: "));

    txtStartSector = new JTextField(5);
    pnlMid.add(txtStartSector);
    txtStopSector = new JTextField(5);
    pnlMid.add(txtStopSector);

    jcbSaveData = new JCheckBox("Save Data", true);
    pnlSave.add(jcbSaveData);

    pnlButtons = new JPanel(new GridLayout(1, 2));

      pnlButtons.add(new JPanel());

      butOkay = new JButton(OKAY);
      butOkay.addActionListener(this);
      pnlButtons.add(butOkay);

      butCancel = new JButton(CANCEL);
      butCancel.addActionListener(this);
      pnlButtons.add(butCancel);



    this.getContentPane().setLayout(new BorderLayout());

    this.getContentPane().add(pnlMid, BorderLayout.NORTH);
    this.getContentPane().add(pnlSave, BorderLayout.CENTER);
    this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);


    this.setTitle("Ether Probe Scan");

    this.pack();
    setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
    this.show();

  }


  /**
    * parseString
    **/
  public void parseString(String theString) {
    int iEtherProbesLeft;


    if (theString.indexOf("Probe Destroyed!") > -1) {
      strData += "DESTROYED|" + iTravel + "|TRAVELING_TO|" + iStopSector + "\n";
      theTWA.getScreen().soundPlanetDestroy.play();

      //System.out.println(strData);

      iCurrentSector++;
      if (iCurrentSector > iStopSector) {
        quitParsing();
      } else {
        theTWA.getTelnet().getSender().sendData("E");
      }

    } else if (theString.indexOf("Probe Self Destructs") > -1) {
      strData += "SELF_DESTRUCT|" + iTravel + "\n";
      //System.out.println(strData);

      iCurrentSector++;
      if (iCurrentSector > iStopSector) {
        quitParsing();
      } else {
        theTWA.getTelnet().getSender().sendData("E");
      }


    } else if (theString.indexOf("Probe entering sector :") > -1) {

      iTravel = Parser.toInteger(theString.substring(24), -1);

      /*
      theTWA.getBBS().getGameData().findSector(iTravel).addAdjacentSector(theTWA.getBBS().getGameData().findSector(iPrevious));
      iPrevious = iTravel;
      */

    } else if (
                 (theString.indexOf("SubSpace Ether Probe loaded in launch tube,") > -1) &&
                 (theString.indexOf("remaining.") > -1)
              ) {
      iEtherProbesLeft = Parser.toInteger(theString.substring(44, theString.indexOf(' ', 44)), -1);
      if (iEtherProbesLeft < 1) {
        quitParsing();
        JOptionPane.showMessageDialog(this, "No ether probes remain.", "Error", JOptionPane.WARNING_MESSAGE);
      }

    } else if (theString.indexOf("Please enter a destination for this probe :") > -1) {
        theTWA.getTelnet().getSender().sendData("" + iCurrentSector + "\r");
      
    }

  }



  /**
    * quitParsing
    **/
  public void quitParsing() {
    if (writeData) {
      FileWriter theFileWriter;
      PrintWriter thePrintWriter;
      int i;

      try {
        theFileWriter = new FileWriter(fileSave.getPath(), true);
        thePrintWriter = new PrintWriter(theFileWriter);

        System.out.println("Writing Data:\n" + strData);
        thePrintWriter.print(strData);

        thePrintWriter.close();
      } catch (IOException e) { }

    }

    theTWA.getParser().getParsers().removeElement(this); //start getting lines from the parser
    theTWA.getTelnet().getSender().setUserInputEnabled(true);
    theTWA.getScreen().repaint();
  }




  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand;

    strCommand = e.getActionCommand();

    if (strCommand.equals(OKAY)) {

      writeData = false;

      iStartSector = Parser.toInteger(txtStartSector.getText(), -1);
      iStopSector = Parser.toInteger(txtStopSector.getText(), -1);


      if (jcbSaveData.isSelected()) {
        JFileChooser fcSaveData = new JFileChooser();
        File fileDirectory;

        if (theTWA.getBBS() != null) {
          fileDirectory = new File("./gamedata");
          fileDirectory.mkdir();

          fcSaveData.setCurrentDirectory(new File("./gamedata"));
          fcSaveData.setSelectedFile(new File(theTWA.getBBS().getName() + iStartSector + ".eth"));
          fcSaveData.showSaveDialog(this);
          fileSave = fcSaveData.getSelectedFile();
          if (fileSave != null) {
            writeData = true;
          }
        } else {
            JOptionPane.showMessageDialog(this, "No game loaded", "Error", JOptionPane.WARNING_MESSAGE);
        }
      }



      if ((iStartSector > -1) && (iStopSector > -1)) {
        iCurrentSector = iStartSector;
        iTravel = theTWA.getCurrentSector().getNumber();
        iPrevious = iTravel;

        theTWA.getParser().getParsers().addElement(this); //start getting lines from the parser
        theTWA.getTelnet().getSender().sendData("E");
        setVisible(false);
        dispose();
      }




    } else if (strCommand.equals(CANCEL)) {
      setVisible(false);
      dispose();
    }

  }

  /**
    * main
    **/
  public static void main(String argv[]) {
    JFrame myFrame = new JFrame();
    myFrame.setBounds(50, 50, 50, 50);
    myFrame.setVisible(true);
    new EtherScanDialog(myFrame, null);

  }

    


}
