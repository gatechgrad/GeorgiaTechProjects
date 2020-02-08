import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class PortPairDialog extends JDialog implements ActionListener, LineParser, ListSelectionListener {
  /** CONSTANTS **/
  public static final String OKAY = "Okay";
  public static final String CANCEL = "Cancel";
  public static final int SELL_PERCENT = 105;
  public static final int BUY_PERCENT = 95;

  public static final int STATE_NONE = 0;
  public static final int STATE_BUYING = 1;
  public static final int STATE_SELLING = 2;



  /** INSTANCE VARIABLES **/
  JButton butOkay, butCancel;
  JCheckBox jcFuelOre, jcOrganics, jcEquipment;
  JList listPortPairs;
  JTextField txtTimes, txtMinimumToBuy;
  ProgressBarFrame theProgressBar;

  int iTimes, iTotalTimes;
  int iBuyTries, iSellTries;
  int iBuyPrice, iSellPrice;
  int iTotalProfit;
  int iToBuy;
  int iState;
  int iMinimumToBuy;
  TWA theTWA;
  String strSector1, strSector2;
  boolean hasZeroed;

  boolean tradeFuelOre;
  boolean tradeOrganics;
  boolean tradeEquipment;


  /**
    * PortPairDialog
    **/
  public PortPairDialog(Frame owner, TWA theTWA) {
    super(owner);

    this.theTWA = theTWA;
    makeWindow();

  }

  /**
    * makeWindow
    **/
  private void makeWindow() {
    int i;
    JPanel pnlTrade = new JPanel(new GridLayout(3, 1));
    JPanel pnlButtons = new JPanel();
    JPanel pnlTimes = new JPanel(new GridLayout(4, 1));
    JPanel pnlTemp;
    String strTemp;

    Vector vectData = new Vector();
    if (theTWA.getCurrentSector().getPortPairs() != null) {
      for (i = 0; i < theTWA.getCurrentSector().getPortPairs().size(); i++) {

        if (
          (((Sector) theTWA.getCurrentSector().getPortPairs().elementAt(i)).getPort() != null)
 
        ) {
          strTemp = theTWA.getCurrentSector().getNumber() + " " + theTWA.getCurrentSector().getPort().getPortClass() + " <--> " +
                    ((Sector) theTWA.getCurrentSector().getPortPairs().elementAt(i)).getNumber() + " " +
                    ((Sector) theTWA.getCurrentSector().getPortPairs().elementAt(i)).getPort().getPortClass();
          vectData.add(strTemp);
        }
      }


      listPortPairs = new JList(vectData);
      listPortPairs.addListSelectionListener(this);
      listPortPairs.setForeground(Screen.FG_COLOR);
      listPortPairs.setBackground(Screen.BKG_COLOR);



      jcFuelOre = new JCheckBox("Fuel Ore", false);
      jcOrganics = new JCheckBox("Organics", false);
      jcEquipment = new JCheckBox("Equipment", false);

      pnlTrade.add(jcFuelOre);
      pnlTrade.add(jcOrganics);
      pnlTrade.add(jcEquipment);

      pnlTimes.add(new JLabel("Times to loop:"));
      txtTimes = new JTextField(6);
      txtTimes.setText(theTWA.getBBS().getGameData().getPortTimesToLoop() + "");
      pnlTemp = new JPanel();
      pnlTemp.add(txtTimes);
      pnlTimes.add(pnlTemp);

      pnlTimes.add(new JLabel("Minimum number to buy:"));
      txtMinimumToBuy = new JTextField(6);
      txtMinimumToBuy.setText(theTWA.getBBS().getGameData().getCurrentShip().getTotalHolds() + "");
      pnlTemp = new JPanel();
      pnlTemp.add(txtMinimumToBuy);
      pnlTimes.add(pnlTemp);


      butOkay = new JButton(OKAY);
      butOkay.addActionListener(this);
      pnlButtons.add(butOkay);
      butCancel = new JButton(CANCEL);
      butCancel.addActionListener(this);
      pnlButtons.add(butCancel);



      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(listPortPairs, BorderLayout.NORTH);
      this.getContentPane().add(pnlTrade, BorderLayout.EAST);
      this.getContentPane().add(pnlTimes, BorderLayout.CENTER);
      this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

      this.pack();
      setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
      this.show();

    } else {
      JOptionPane.showMessageDialog(this, "There are no port pairs available", "No Port Pairs", JOptionPane.WARNING_MESSAGE);

    }

  }

  /**
    * parseString
    **/
  public void parseString(String theString) {
    String strTemp;
    String strClass;
    int iPrice;
    int iBidPrice;
    boolean isFinished;
    int iProfit;


    isFinished = false;

    if (iState == STATE_SELLING) {

      if (theString.indexOf("How many holds of Fuel Ore do you want to sell [") >= 0) {
        theTWA.getTelnet().getSender().sendData(theString.substring(theString.indexOf('[') + 1, theString.indexOf(']')) + "\r");
        if (tradeFuelOre) {
          theProgressBar.increment("Trial " + (iTotalTimes - iTimes + 1) + "/" + iTotalTimes + ": ");
        }


      } else if (theString.indexOf("How many holds of Organics do you want to sell [") >= 0) {
        theTWA.getTelnet().getSender().sendData(theString.substring(theString.indexOf('[') + 1, theString.indexOf(']')) + "\r");
        if (tradeOrganics) {
          theProgressBar.increment("Trial " + (iTotalTimes - iTimes + 1) + "/" + iTotalTimes + ": ");
        }

      } else if (theString.indexOf("How many holds of Equipment do you want to sell [") >= 0) {
        theTWA.getTelnet().getSender().sendData(theString.substring(theString.indexOf('[') + 1, theString.indexOf(']')) + "\r");
        if (tradeEquipment) {
          theProgressBar.increment("Trial " + (iTotalTimes - iTimes + 1) + "/" + iTotalTimes + ": ");
        }

      } else if (
                (theString.indexOf("We'll buy them for") >= 0) &&
                (theString.indexOf("credits.") >= 0) ) {
        strTemp = theString.substring(19, theString.indexOf(' ', 19));

        iPrice = Parser.toInteger(strTemp, 0);
        iBidPrice = iPrice * (SELL_PERCENT - iSellTries) / 100;

        theTWA.getTelnet().getSender().sendData(iBidPrice + "\r");

        iSellPrice = iBidPrice;

        iProfit = iSellPrice - iBuyPrice;
        iTotalProfit += iProfit;

        theProgressBar.addText("Profit: " + iProfit);


        iSellTries++;
      } else if ( (theString.indexOf("Our final offer is")) >= 0)  {
        theTWA.getTelnet().getSender().sendData("\r"); //just take it for right now


      } else if ( (theString.indexOf("We are selling up to") >= 0) ) {

        iToBuy = 0;
        iState = STATE_BUYING;

      }


    } else if (iState == STATE_BUYING) {

      if (theString.indexOf("How many holds of Fuel Ore do you want to buy [") >= 0) {
        //System.out.println("How many buy FO");


        if (tradeFuelOre) {
          iToBuy = Parser.toInteger(theString.substring(theString.indexOf('[') + 1, theString.indexOf(']')), 0);


          if (iToBuy >= iMinimumToBuy) {
            theTWA.getTelnet().getSender().sendData(iToBuy + "\r");
          } else {
            JOptionPane.showMessageDialog(this, "Port is not selling the minimum amount to buy", "Script Error", JOptionPane.WARNING_MESSAGE);
            isFinished = true;
          }
        } else {
          theTWA.getTelnet().getSender().sendData("0\r");
        }
      } else if (theString.indexOf("How many holds of Organics do you want to buy [") >= 0) {
        //System.out.println("How many buy O");
        if (tradeOrganics) {
          iToBuy = Parser.toInteger(theString.substring(theString.indexOf('[') + 1, theString.indexOf(']')), 0);
          if (iToBuy >= iMinimumToBuy) {
            theTWA.getTelnet().getSender().sendData(iToBuy + "\r");
          } else {
            JOptionPane.showMessageDialog(this, "Port is not selling the minimum amount to buy", "Script Error", JOptionPane.WARNING_MESSAGE);
            isFinished = true;
          }
        } else {
          theTWA.getTelnet().getSender().sendData("0\r");
        }
      } else if (theString.indexOf("How many holds of Equipment do you want to buy [") >= 0) {
        //System.out.println("How many buy E");
        if (tradeEquipment) {
          iToBuy = Parser.toInteger(theString.substring(theString.indexOf('[') + 1, theString.indexOf(']')), 0);
          if (iToBuy >= iMinimumToBuy) {
            theTWA.getTelnet().getSender().sendData(iToBuy + "\r");
          } else {
            JOptionPane.showMessageDialog(this, "Port is not selling the minimum amount to buy", "Script Error", JOptionPane.WARNING_MESSAGE);
            isFinished = true;
          }
        } else {
          theTWA.getTelnet().getSender().sendData("0\r");
        }
      } else if (
                (theString.indexOf("We'll sell them for") >= 0) &&
                (theString.indexOf("credits.") >= 0) ) {
        //System.out.println("Sell them for");

        strTemp = theString.substring(20, theString.indexOf(' ', 20));
        iPrice = Parser.toInteger(strTemp, 0);
        iBidPrice = iPrice * (BUY_PERCENT + iBuyTries) / 100;

        //System.out.println("Price: " + iPrice + "\nMyBid: " + iBidPrice);
        theTWA.getTelnet().getSender().sendData(iBidPrice + "\r");

        iBuyPrice = iBidPrice;

        iBuyTries++;

      } else if (
                (theString.indexOf("Our final offer is")) >= 0)  {
        theTWA.getTelnet().getSender().sendData("\r"); //just take it for right now

      } else if (  (theString.indexOf("Command") > -1) &&
            (theString.indexOf("[" + strSector1 + "]") > -1)
         ) {

        iTimes--;
        if (iTimes <= 0) {
          isFinished = true;
        } else {
          theTWA.getTelnet().getSender().sendData("m" + strSector2 + "\r");
        }


         iState = STATE_NONE; //leave the buying state

      } else if (  (theString.indexOf("Command") > -1) &&
            (theString.indexOf("[" + strSector2 + "]") > -1)
         ) {

         theTWA.getTelnet().getSender().sendData("m" + strSector1 + "\r");
         iState = STATE_NONE; //leave the buying state


      }
    } else if (iState == STATE_NONE) {
      if (theString.indexOf("<T> Trade at this Port") > -1) {
        theTWA.getTelnet().getSender().sendData("t");
        //System.out.println("Sent a 't'");

      } else if (theString.startsWith("You don't have anything they want, and they don't have anything you can buy.")) {
        JOptionPane.showMessageDialog(this, "You must unload your cargo first", "Script Error", JOptionPane.WARNING_MESSAGE);
        isFinished = true;
      } else if (  (theString.indexOf("Command") > -1) &&
            (theString.indexOf("] (?=Help)? :") > -1)
         ) {
        //System.out.println("Found command... send a 'p'");
        theTWA.getTelnet().getSender().sendData("p");

      } else if (theString.indexOf("Docking...") >= 0) {


        hasZeroed = false;

        iBuyTries = 0;
        iSellTries = 0;

      } else if (
                (theString.indexOf("You have") >= 0) &&
                (theString.indexOf("credits and") >= 0) &&
                (theString.indexOf("empty cargo holds.") >= 0) ) {

        iState = STATE_SELLING;
      }

    }



    //remember to remove this from the parser list when this
    //script is finished
    if (isFinished) {
      quitParsing();
    }
  }

  /**
    * quitParsing
    **/
  public void quitParsing() {
      theTWA.getParser().getParsers().removeElement(this); //start getting lines from the parser
      //theScriptWindow.addText("Total Profit: " + iTotalProfit + "\n");

      theProgressBar.setText("Total Profit: " + iTotalProfit);
      theProgressBar.shutdown();

      theTWA.getScreen().repaint();
  }


  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand;
    int iIndex;

    strCommand = e.getActionCommand();

    if (strCommand.equals(OKAY)) {
      strSector1 = "";
      strSector2 = "";

      try {
        iIndex = listPortPairs.getSelectedIndex();
        iTimes = (new Integer(txtTimes.getText())).intValue();
        theTWA.getBBS().getGameData().setPortTimesToLoop(iTimes);


        
        iTotalTimes = iTimes;
        theProgressBar = new ProgressBarFrame(0, iTotalTimes);

          if (iIndex >= 0) {


            strSector1 = theTWA.getCurrentSector().getNumber() + "";
            strSector2 = ((Sector) theTWA.getCurrentSector().getPortPairs().elementAt(iIndex)).getNumber() + "";

        theProgressBar.setTitle("Port Pair Trade: " + strSector1 + " <-> " + strSector2);


            iBuyPrice = 0;
            iSellPrice = 0;
            iTotalProfit = 0;
            tradeFuelOre = false;
            tradeOrganics = false;
            tradeEquipment = false;
            iState = STATE_NONE;

            if (jcFuelOre.isSelected()) {
              tradeFuelOre = true;
            }

            if (jcOrganics.isSelected()) {
              tradeOrganics = true;
            }

            if (jcEquipment.isSelected()) {
              tradeEquipment = true;
            }

            iMinimumToBuy = Parser.toInteger(txtMinimumToBuy.getText(), 255);

            theTWA.getParser().getParsers().addElement(this); //start getting lines from the parser
            setVisible(false);
            dispose();

            //theScriptWindow = new ScriptWindow(theTWA.getFrame());
        theTWA.getTelnet().getSender().sendData("d"); //start it up

          }
      } catch (NumberFormatException e2) {
        JOptionPane.showMessageDialog(this, "Number of times must be an integer value", "Error", JOptionPane.WARNING_MESSAGE);
      }

    } else if (strCommand.equals(CANCEL)) {
      setVisible(false);
      dispose();
    }
  }

  /**
    * valueChanged
    **/
  public void valueChanged(ListSelectionEvent e) {
    if (e.getSource().equals(listPortPairs)) {
        String strClass1, strClass2;
        int iChecks;
        int iIndex;

        iIndex = listPortPairs.getSelectedIndex();
        if (
             (iIndex >= 0) &&
             (theTWA.getCurrentSector().getPort() != null) &&
             (((Sector) theTWA.getCurrentSector().getPortPairs().elementAt(iIndex)).getPort() != null)
           ) {
          strSector1 = theTWA.getCurrentSector().getPort().getPortClass();
          strSector2 = (((Sector) theTWA.getCurrentSector().getPortPairs().elementAt(iIndex)).getPort().getPortClass());

          iChecks = 0;
          //this should be a loop, but since there are only three items
          //I will go ahead and unroll it myself for simplicity
          if (strSector1.charAt(2) != strSector2.charAt(2)) {
            jcEquipment.setSelected(true);
            iChecks++;
          }

          if (strSector1.charAt(1) != strSector2.charAt(1)) {
            jcOrganics.setSelected(true);
            iChecks++;
          }

          if (
               (strSector1.charAt(0) != strSector2.charAt(0)) &&
               (iChecks < 2)

             ) {
            jcFuelOre.setSelected(true);
            iChecks++;
          }

        }
    }

  }

  /**
    * main
    **/
  public static void main(String argv[]) {
    JFrame myFrame = new JFrame();
    myFrame.setLocation(50, 50);
    new PortPairDialog(myFrame, null);

  }

    


}
