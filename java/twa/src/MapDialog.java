import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

class MapDialog extends JDialog implements ActionListener {

  /** CONSTANTS **/
  public static final int DEFAULT_HOPS = 5;

  public static final String SECTOR_NUMBER = "Sector Number";
  public static final String ADJACENT_SECTORS = "Adjacent Sectors";
  public static final String PORTS = "Ports";
  public static final String PLANETS = "Planets";
  public static final String FIGS = "Fighters";
  public static final String MINES = "Mines";

  /** INSTANCE VARIABLES **/
  private TWA theTWA;
  private Map theMap;
  private JComboBox jcbSector, jcbSort;
  private JTextField txtTimes;
  private String strSortType;


  /**
    * MapDialog
    **/
  public MapDialog(Frame owner, TWA theTWA) {
    super(owner);
    this.theTWA = theTWA;
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    makeWindow();
  }

  /**
    * makeWindow
    **/
  private void makeWindow() {
    JPanel pnlControls = new JPanel(new GridLayout(2, 3));
    int i;
    String[] strAllSectorNumbers;
    Vector vectSectors;


//    vectSectors = theTWA.getBBS().getGameData().sort(theTWA.getBBS().getGameData().getSectors());
    vectSectors = theTWA.getBBS().getGameData().getSectors();

    strAllSectorNumbers = new String[vectSectors.size()];
    for (i = 0; i < vectSectors.size(); i++) {
      strAllSectorNumbers[i] = ((Sector) vectSectors.elementAt(i)).getNumber() + "";
    }

    if (theTWA.getCurrentSector() != null) {
      theMap = new Map(theTWA.getCurrentSector().getNumber(), theTWA);
    } else {
      theMap = new Map(1, theTWA);
    }

    pnlControls.add(new Label("Sort By:"));
    pnlControls.add(new Label("Sector:"));
    pnlControls.add(new Label("Max Hops To Map:"));


    jcbSector = new JComboBox(strAllSectorNumbers);
    jcbSector.setEditable(true);
    jcbSector.addActionListener(this);
    pnlControls.add(jcbSector);

    jcbSort = new JComboBox();
    jcbSort.setEditable(false);
    jcbSort.addActionListener(this);
    pnlControls.add(jcbSort);


    txtTimes = new JTextField(DEFAULT_HOPS + "", 6);
    txtTimes.addActionListener(this);
    pnlControls.add(txtTimes);


    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(pnlControls, BorderLayout.NORTH);
    this.getContentPane().add(theMap, BorderLayout.CENTER);

    this.pack();
    setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
    this.show();

  }

  /**
    * sortSectors
    **/
  private void sortSectors() {
    System.out.println("Sorting sectors by: " + strSortType);

  }



  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    int i;
    int iTimes;

    if (e.getSource().equals(jcbSector) || e.getSource().equals(jcbSort) || e.getSource().equals(txtTimes)) {

      if ( (!jcbSort.equals(strSortType)) || (jcbSort.getSelectedItem().toString() == null)) {
        strSortType = jcbSort.getSelectedItem().toString();
        sortSectors();

      }


      try {
        i = (new Integer(jcbSector.getSelectedItem().toString())).intValue();
        iTimes = Parser.toInteger(txtTimes.getText(), DEFAULT_HOPS);

        theMap.setSectorToDraw(i, iTimes);
        theMap.repaint();
      } catch (NumberFormatException e2) { }
    }

  }

}
