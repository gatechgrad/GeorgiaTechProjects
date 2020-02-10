import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

class DeviceDisplay implements ActionListener {

  /** CONSTANTS **/
  public static final Dimension WINDOW_SIZE = new Dimension(640, 480);
  public static final String WINDOW_TITLE = "Device List";
  /** BUTTON LABELS **/
  public static final String ADD_DEVICE = "Add Device";
  public static final String REMOVE_DEVICE = "Remove Device";
  public static final String MODIFY_DEVICE = "Modify Device";
  public static final String ADD_DEVICE_TYPE = "Add Device Type";



  /** INSTANCE VARIABLES **/
  private JFrame theFrame;
  private JTable theTable;
  private JButton butAddDevice;
  private JButton butRemoveDevice;
  private JButton butModifyDevice;
  private JButton butAddDeviceType;

  private Lockheed theLockheed;



  /**
    * DeviceDisplay
    **/
  public DeviceDisplay(Lockheed theLockheed) {
    this.theLockheed = theLockheed;

    setupWindow();
  }


  /**
    * setupWindow - creates the window frame, adds a window listener, and
    *               displays the window in the center of the screen
    **/
  public void setupWindow() {
    theFrame = new JFrame();

    theFrame.setSize(WINDOW_SIZE.width, WINDOW_SIZE.height);
    theFrame.setLocation(
              (LockheedConstants.SCREEN_SIZE.width - WINDOW_SIZE.width) / 2,
              (LockheedConstants.SCREEN_SIZE.height - WINDOW_SIZE.height) / 2
                        );
    theFrame.setTitle(WINDOW_TITLE);


    //simple WindowListener that closes the window when the Window is closed
    WindowListener theWindowListener = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        theFrame.hide();
        theFrame.dispose();
        System.exit(0);

      }
    };

    theFrame.addWindowListener(theWindowListener);




    LockheedTableModel theTableModel = new LockheedTableModel(theLockheed.getDevices());
    theTable = new JTable(theTableModel);

    JPanel pnlButtons = new JPanel();

    butAddDevice = new JButton(ADD_DEVICE);
    butAddDevice.addActionListener(this);
    pnlButtons.add(butAddDevice);

    butRemoveDevice = new JButton(REMOVE_DEVICE);
    butRemoveDevice.addActionListener(this);
    pnlButtons.add(butRemoveDevice);

    butModifyDevice = new JButton(MODIFY_DEVICE);
    butModifyDevice.addActionListener(this);
    pnlButtons.add(butModifyDevice);

    butAddDeviceType = new JButton(ADD_DEVICE_TYPE);
    butAddDeviceType.addActionListener(this);
    pnlButtons.add(butAddDeviceType);


    theFrame.getContentPane().setLayout(new BorderLayout());
    theFrame.getContentPane().add(new JScrollPane(theTable), BorderLayout.CENTER);
    theFrame.getContentPane().add(pnlButtons, BorderLayout.SOUTH);
    theFrame.show();



  }



  /**
    * set methods
    **/
  public void setTable(Vector vectDevices) {
    Vector vectColNames = new Vector();
    vectColNames.add("ID");
    vectColNames.add("Type");
    vectColNames.add("Name");
    theTable = new JTable(vectDevices, vectColNames);
  }


  /**
    * actionPerfromed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand = e.getActionCommand();

    if (strCommand.equals(ADD_DEVICE)) {
      new AddDeviceDialog(theFrame, theLockheed);

    } else if ( strCommand.equals(REMOVE_DEVICE)) {
      theLockheed.removeDevice(theTable.getSelectedRows());

    } else if ( strCommand.equals(MODIFY_DEVICE)) {

    } else if ( strCommand.equals(ADD_DEVICE_TYPE)) {
      new AddDeviceTypeDialog(theFrame, theLockheed);
    }

  }

  /**
    * refresh - redraws components which need to be updated
    **/
  public void refresh() {
    theTable.updateUI();

  }

}
