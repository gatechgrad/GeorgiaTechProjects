import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class FindBubblesDialog extends JDialog implements ActionListener {
  /** CONSTANTS **/
  public static final String OKAY = "Okay";
  public static final String CANCEL = "Cancel";



  /** INSTANCE VARIABLES **/
  JButton butOkay, butCancel;
  JTextArea txtData;
  String strData;

  TWA theTWA;

  /**
    * FindBubblesDialog
    **/
  public FindBubblesDialog(Frame owner, TWA theTWA) {
    super(owner);

    this.theTWA = theTWA;

    strData = "";
    findBubbles();

    makeWindow();
  }

  /**
    * makeWindow
    **/
  private void makeWindow() {
    JPanel pnlTrade = new JPanel(new GridLayout(3, 1));
    JPanel pnlButtons = new JPanel();

      butOkay = new JButton(OKAY);
      butOkay.addActionListener(this);
      pnlButtons.add(butOkay);
      butCancel = new JButton(CANCEL);
      butCancel.addActionListener(this);
      pnlButtons.add(butCancel);

      txtData = new JTextArea(strData, 20, 50);



      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(txtData, BorderLayout.CENTER);
      this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

      this.pack();
      setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
      this.show();
  }

  /**
    * findBubbles
    **/
  private void findBubbles() {
    int i, j;
    int iPreviousNumber;
    Sector sectorTemp;

    for (i = 0; i < 20000; i++) {

      if (theTWA.getBBS().getGameData().findSector(i) != null) {
        if (theTWA.getBBS().getGameData().findSector(i).getAdjacentSectors().size() == 1) {
          System.out.println("Dead End: " + i);
          strData += i;

          iPreviousNumber = i;
          sectorTemp = (Sector) theTWA.getBBS().getGameData().findSector(i).getAdjacentSectors().elementAt(0);

          while (sectorTemp.getAdjacentSectors().size() == 2) {
            strData += " -> " + sectorTemp.getNumber();

            for (j = 0; j < sectorTemp.getAdjacentSectors().size(); j++) {
              if (  ((Sector) sectorTemp.getAdjacentSectors().elementAt(j)).getNumber() != iPreviousNumber) {
                iPreviousNumber = sectorTemp.getNumber();
                sectorTemp = (Sector) sectorTemp.getAdjacentSectors().elementAt(j);
              }
            }

          }

        strData += "\n";

        }
      }
    }


  }

  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand = e.getActionCommand();


    if (strCommand.equals(OKAY)) {
    } else if (strCommand.equals(CANCEL)) {
      setVisible(false);
      dispose();
    }


  }

    


}
