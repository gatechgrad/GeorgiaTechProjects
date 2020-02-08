import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class ColonizeDialog extends JDialog implements ActionListener {
  /** CONSTANTS **/
  public static final String OKAY = "Okay";
  public static final String CANCEL = "Cancel";

  /** INSTANCE VARIABLES **/
  JButton butOkay, butCancel;
  JTextField txtFrom, txtTo, txtPlanetFrom, txtPlanetTo, txtRuns;
  JRadioButton rbTWYes, rbTWNo, rbFuelOre, rbOrganics, rbEquipment, rbColonists;
  ButtonGroup bgItem, bgTWarp;
  TWA theTWA;

  /**
    * ColonizeDialog
    **/
  public ColonizeDialog(Frame owner, TWA theTWA) {
    super(owner);

    this.theTWA = theTWA;
    makeWindow();

  }

  /**
    * makeWindow
    **/
  private void makeWindow() {
    JPanel pnlMiddle;
    JPanel pnlItems;
    JPanel pnlButtons;
    JPanel pnlTemp, pnlLeft, pnlRight;

    this.setBounds(50, 50, 200, 300);
    this.getContentPane().setLayout(new BorderLayout());

    pnlMiddle = new JPanel(new BorderLayout());
      pnlLeft = new JPanel(new GridLayout(5, 1));
      pnlRight = new JPanel(new GridLayout(5, 1));


      pnlTemp = new JPanel();
      pnlTemp.add(new JLabel("Transport From Sector:   ", SwingConstants.LEFT));
      pnlLeft.add(pnlTemp);

      pnlTemp = new JPanel();
      txtFrom = new JTextField(5);
      pnlTemp.add(txtFrom);
      pnlRight.add(pnlTemp);

      pnlTemp = new JPanel();
      pnlTemp.setAlignmentX(LEFT_ALIGNMENT);
      pnlTemp.add(new JLabel("Planet:", SwingConstants.LEFT));
      pnlLeft.add(pnlTemp);

      pnlTemp = new JPanel();
      txtPlanetFrom = new JTextField(5);
      pnlTemp.add(txtPlanetFrom);
      pnlRight.add(pnlTemp);


      pnlTemp = new JPanel();
      pnlTemp.add(new JLabel("Transport To Sector:   ", SwingConstants.LEFT));
      pnlLeft.add(pnlTemp);

      pnlTemp = new JPanel();
      txtTo = new JTextField(5);
      pnlTemp.add(txtTo);
      pnlRight.add(pnlTemp);

      pnlTemp = new JPanel();
      pnlTemp.add(new JLabel("Planet:", SwingConstants.LEFT));
      pnlLeft.add(pnlTemp);

      pnlTemp = new JPanel();
      txtPlanetTo = new JTextField(5);
      pnlTemp.add(txtPlanetTo);
      pnlRight.add(pnlTemp);

      pnlTemp = new JPanel();
      pnlTemp.add(new JLabel("Runs:", SwingConstants.LEFT));
      pnlLeft.add(pnlTemp);

      pnlTemp = new JPanel();
      txtRuns = new JTextField(5);
      pnlTemp.add(txtRuns);
      pnlRight.add(pnlTemp);


      pnlMiddle.add(pnlLeft, BorderLayout.CENTER);
      pnlMiddle.add(pnlRight, BorderLayout.EAST);


    pnlItems = new JPanel(new GridLayout(5, 2));
      bgItem = new ButtonGroup();


        rbColonists = new JRadioButton("Colonists");
        rbColonists.setMnemonic('c');

        rbFuelOre = new JRadioButton("Fuel Ore");
        rbFuelOre.setMnemonic('f');

        rbOrganics = new JRadioButton("Organics");
        rbOrganics.setMnemonic('o');

        rbEquipment = new JRadioButton("Equipment");
        rbEquipment.setMnemonic('e');


        bgItem.add(rbColonists);
        bgItem.add(rbFuelOre);
        bgItem.add(rbOrganics);
        bgItem.add(rbEquipment);


        bgTWarp = new ButtonGroup();

        rbTWYes = new JRadioButton("Yes");
        rbTWYes.setMnemonic('y');

        rbTWNo = new JRadioButton("No");
        rbTWNo.setMnemonic('n');

        bgTWarp.add(rbTWYes);
        bgTWarp.add(rbTWNo);

      pnlItems.add(new JLabel("Resource"));
      pnlItems.add(new JLabel("Use Transwarp"));
      pnlItems.add(rbColonists);
      pnlItems.add(rbTWYes);
      pnlItems.add(rbFuelOre);
      pnlItems.add(rbTWNo);
      pnlItems.add(rbOrganics);
      pnlItems.add(new JPanel());
      pnlItems.add(rbEquipment);

    pnlButtons = new JPanel(new GridLayout(1, 2));
      bgTWarp = new ButtonGroup();

      pnlButtons.add(new JPanel());

      butOkay = new JButton(OKAY);
      butOkay.addActionListener(this);
      pnlButtons.add(butOkay);

      butCancel = new JButton(CANCEL);
      butCancel.addActionListener(this);
      pnlButtons.add(butCancel);



    this.getContentPane().add(pnlMiddle, BorderLayout.NORTH);
    this.getContentPane().add(pnlItems, BorderLayout.CENTER);
    this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

    setVisible(true);

  }

  /**
    * startScript
    **/
  private void startScript() {
    int iSectorFrom;
    int iSectorTo;
    int iPlanetFrom;
    int iPlanetTo;
    int iRuns;
    int iFuelRequired;
    int iGroup;
    int i;
    boolean useTWarp;

    useTWarp = true;
    iGroup = 1;

    System.out.println("Starting script");

    try {
      iSectorFrom = (new Integer(txtFrom.getText())).intValue();
      iSectorTo = (new Integer(txtTo.getText())).intValue();
      iPlanetFrom = (new Integer(txtPlanetFrom.getText())).intValue();
      iPlanetTo = (new Integer(txtPlanetTo.getText())).intValue();
      iRuns = (new Integer(txtRuns.getText())).intValue();


      if(useTWarp) {
        theTWA.getTelnet().getSender().sendData("cf" + iSectorFrom + "\r" + iSectorTo + "\r");

        do {
          try { Thread.sleep(1000); } catch (InterruptedException e) { }
          System.out.println("Waiting for turn data");

          i = theTWA.getParser().getHops();
          System.out.println("" + i);
        } while ( i == -1);
        iFuelRequired = i;


        theTWA.getTelnet().getSender().sendData("f" + iSectorTo + "\r" + iSectorFrom + "\r");


        do {
          i = theTWA.getParser().getHops();
        } while ( i == -1);
        iFuelRequired += i;
        iFuelRequired *= 3;

        theTWA.getTelnet().getSender().sendData("q");

        theTWA.getTelnet().getSender().sendData("l" + iPlanetFrom + "\r");
        theTWA.getTelnet().getSender().sendData("tnl1\r");
        theTWA.getTelnet().getSender().sendData("tnl2\r");
        theTWA.getTelnet().getSender().sendData("tnl3\r");
        theTWA.getTelnet().getSender().sendData("snl" + iGroup + "\r");

        for (i = 0; i < iRuns; i++) {
          theTWA.getTelnet().getSender().sendData("tnt1");
          theTWA.getTelnet().getSender().sendData(iFuelRequired + "\r");
          theTWA.getTelnet().getSender().sendData("q");


          theTWA.getTelnet().getSender().sendData(iSectorFrom + "\ryyl" + iPlanetFrom);
          theTWA.getTelnet().getSender().sendData("t\r"); //for Terra

          theTWA.getTelnet().getSender().sendData(iSectorTo + "\ryy");
          theTWA.getTelnet().getSender().sendData("l" + iPlanetTo + "\rsnl" + iGroup + "\r");

        }
      }
    } catch (NumberFormatException e) { }


  }

  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand;

    strCommand = e.getActionCommand();

    if (strCommand.equals(OKAY)) {
      startScript();
      setVisible(false);
      dispose();
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
    new ColonizeDialog(myFrame, null);

  }

    


}
