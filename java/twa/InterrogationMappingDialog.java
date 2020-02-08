import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.StringTokenizer;

class InterrogationMappingDialog extends JDialog implements ActionListener, LineParser {
  /** CONSTANTS **/
  public static final String OKAY = "Okay";
  public static final String CANCEL = "Cancel";


  /** INSTANCE VARIABLES **/
  JButton butOkay, butCancel;
  JTextField txtFrom, txtTo, txtDestination;
  ProgressBarFrame theProgressBar;
  TWA theTWA;

  int iState;

  int iStartSector;
  int iStopSector;
  int iCurrentSector;
  int iDestinationSector;
  Sector sectorPrevious;

  boolean willIncrement;
  File fileSave;

  boolean startAnalyze;

  /**
    * InterrogationMappingDialog
    **/
  public InterrogationMappingDialog(Frame owner, TWA theTWA, boolean b) {
    super(owner);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    startAnalyze = b;

    this.theTWA = theTWA;

    if (startAnalyze) {
      analyzeData();
    } else {
      makeWindow();
    }

  }

  /**
    * makeWindow
    **/
  private void makeWindow() {
    JPanel pnlButtons;
    JPanel pnlChoices;

    pnlChoices = new JPanel(new GridLayout(3, 2));
      pnlChoices.add(new JLabel("Start at Sector: "));

      txtFrom = new JTextField(5);
      txtFrom.setText("1");
      pnlChoices.add(txtFrom);

      pnlChoices.add(new JLabel("Stop at Sector: "));

      txtTo = new JTextField(5);
      txtTo.setText("20000");
      pnlChoices.add(txtTo);

      pnlChoices.add(new JLabel("Destination Sector: "));

      txtDestination = new JTextField(5);
      txtDestination.setText("20000");
      pnlChoices.add(txtDestination);



    pnlButtons = new JPanel(new GridLayout(1, 2));

      pnlButtons.add(new JPanel());

      butOkay = new JButton(OKAY);
      butOkay.addActionListener(this);
      pnlButtons.add(butOkay);

      butCancel = new JButton(CANCEL);
      butCancel.addActionListener(this);
      pnlButtons.add(butCancel);


    this.getContentPane().add(pnlChoices, BorderLayout.CENTER);
    this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);


    this.setTitle("Interrogation Mapping");

    this.pack();
    setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
    this.show();

  }


  /**
    * parseString
    **/
  public void parseString(String theString) {

    if (theString.startsWith(":")) {
      theTWA.getTelnet().getSender().sendData("F");
      addToFile(theString);

    } else if (theString.startsWith("FM >")) {
      theTWA.getTelnet().getSender().sendData(iCurrentSector + "\r");
    } else if (theString.startsWith("  TO >")) {
      theTWA.getTelnet().getSender().sendData(iStopSector + "\r");

      if (willIncrement) {
        iCurrentSector++;
      } else {
        iCurrentSector--;
      }
    } else if (!theString.equals("")) {
      addToFile(theString);
    }

    if (iCurrentSector == iStopSector) {
      quitParsing();
      theTWA.getTelnet().getSender().sendData("Q");
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
    * addToFile
    **/
  private void addToFile(String theString) {
      FileWriter theFileWriter;
      PrintWriter thePrintWriter;
      int i;

      try {
        theFileWriter = new FileWriter(fileSave.getPath(), true);
        thePrintWriter = new PrintWriter(theFileWriter);

        thePrintWriter.println(theString);

        thePrintWriter.close();
      } catch (IOException e) { }
    

  }

  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand;

    strCommand = e.getActionCommand();

    if (strCommand.equals(OKAY)) {
      iStartSector = Parser.toInteger(txtFrom.getText(), 1);
      iStopSector = Parser.toInteger(txtTo.getText(), 1);
      iDestinationSector = Parser.toInteger(txtDestination.getText(), 1);


      iCurrentSector = iStartSector;

      if (iStartSector < iStopSector) {
        willIncrement = true;
      } else {
        willIncrement = false;
      }


      JFileChooser fcSaveData = new JFileChooser();
      File fileDirectory;

      if (theTWA.getBBS() != null) {
        fileDirectory = new File("./gamedata");
        fileDirectory.mkdir();

        fcSaveData.setCurrentDirectory(new File("./gamedata"));
        fcSaveData.setSelectedFile(new File(theTWA.getBBS().getName() + iStartSector + ".int"));
        fcSaveData.showSaveDialog(this);
        fileSave = fcSaveData.getSelectedFile();
      } else {
          JOptionPane.showMessageDialog(this, "No game loaded", "Error", JOptionPane.WARNING_MESSAGE);
      }

      theTWA.getParser().getParsers().addElement(this); //start getting lines from the parser

      theTWA.getTelnet().getSender().sendData( (char) 200);
      theTWA.getTelnet().getSender().sendData( (char) 201);
      theTWA.getTelnet().getSender().sendData( (char) 202);
      theTWA.getTelnet().getSender().sendData( (char) 203);
      theTWA.getTelnet().getSender().sendData( (char) 204);
      theTWA.getTelnet().getSender().sendData( (char) 205);


      setVisible(false);
      dispose();
    } else if (strCommand.equals(CANCEL)) {
      setVisible(false);
      dispose();
    }

  }


  /**
    * analyzeData
    **/
  public void analyzeData() {
      JFileChooser fcLoadData = new JFileChooser();
      int iChar;
      int iSector;
      String strMappingData;
      StringBuffer myBuffer = new StringBuffer();
      String strLine;
      String strTemp;
      Sector sectorPrevious;
      Sector sectorCurrent;
      Sector sectorTemp;
      boolean startOver;

//      theProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 533000);
//      theProgressBar.getAccessableContext().setAccessableName("Loading File");


      strTemp = "";

      if (theTWA.getBBS() != null) {
        fcLoadData.setCurrentDirectory(new File("./gamedata"));
        fcLoadData.showOpenDialog(theTWA.getFrame());
        File fileLoad = fcLoadData.getSelectedFile();
        if (fileLoad != null) {
          try {
            FileReader fileToRead = new FileReader(fileLoad);


            //read all of the characters from the file and put them into the
            //StringBuffer, myBuffer
            System.out.print("Reading");

            theProgressBar = new ProgressBarFrame(0, (int) fileLoad.length());
            theProgressBar.setText("Reading file");


            iChar = fileToRead.read();
            while(iChar != -1) {

              myBuffer.append((char) iChar);
              iChar = fileToRead.read();
              theProgressBar.increment();
            }
      
            fileToRead.close();
      
          } catch (IOException e) { }

          System.out.print("\nAnalyzing");


          sectorCurrent = null;
          sectorPrevious = null;

          strMappingData = myBuffer.toString();

          StringTokenizer tokenLine = new StringTokenizer(strMappingData, "\n");

          startOver = false;

          while(tokenLine.hasMoreTokens()) {

            if (!startOver) {
              strLine = tokenLine.nextToken().trim();
            } else {
              strLine = strTemp;  //found an unexpected ":" line... use it as the next line
            }
            if (strLine.equals(":")) {
              sectorCurrent = null;
              sectorPrevious = null;

              int i;
              i = 0;

              if (tokenLine.hasMoreTokens()) {
                strLine = tokenLine.nextToken().trim();
                while (  (i < 1) && (!strLine.equals(":"))  ) {   //if the line equals ":", it will be dealt with below
                  System.out.print(strLine + "->");

                  if (tokenLine.hasMoreTokens()) {
                    strLine = tokenLine.nextToken().trim();
                  }

                  i++;
                }
              }
            } else {

              if (!strTemp.equals(":")) {

                StringTokenizer stCells = new StringTokenizer(strLine, " >()");

                while (stCells.hasMoreTokens()) {
                  strTemp = stCells.nextToken().trim();
//                  System.out.print(strTemp);

                  try {

                    iSector = (new Integer(strTemp)).intValue();
                    sectorTemp = new Sector(iSector, theTWA);
                    sectorTemp.setExplored(false);
                    theTWA.getBBS().getGameData().addSector(sectorTemp);

                    if (sectorCurrent != null) {
                      sectorPrevious = sectorCurrent;
                    }
                    sectorCurrent = theTWA.getBBS().getGameData().findSector(iSector);

                    if (sectorPrevious != null) {
                      //sectorCurrent.addAdjacentSector(sectorPrevious);
                      sectorPrevious.addAdjacentSector(sectorCurrent);
                      System.out.print(".");

                    }
                  } catch (NumberFormatException e) {
                    stCells = new StringTokenizer("", "X"); //kill the Tokenizer for this line

                  }

                }
                System.out.print("!\n");

              } else {
                  startOver = true;
              }
            }
          }




        }
      }
  }


    


}


