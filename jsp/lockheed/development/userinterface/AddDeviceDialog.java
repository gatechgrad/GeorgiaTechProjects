import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class AddDeviceDialog extends JDialog implements ActionListener {

  /** CONSTANTS **/
  public static final Dimension WINDOW_SIZE = new Dimension(320, 200);
  public static final String WINDOW_TITLE = "Add Device";

  public static final String OKAY_LABEL = "Okay";
  public static final String CANCEL_LABEL = "Cancel";


  /** INSTANCE VARIABLES **/
  private JComboBox jcbDeviceType;
  private JTextField txtName;
  private JTextField txtLocation;
  private JTextField txtDescription;

  private JButton butOkay;
  private JButton butCancel;


  private Lockheed theLockheed;  //managing object


  /**
    * AddDeviceDialog
    **/
  public AddDeviceDialog(Frame ownerFrame, Lockheed theLockheed) {
    super(ownerFrame, false);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    this.theLockheed = theLockheed;
    setupDialog();
  }

  /**
    * setupDialog - sets up the dialog box
    **/
  private void setupDialog() {
    JPanel pnlInput;
    JPanel pnlButtons;


    pnlInput = new JPanel();
    pnlInput.setLayout(new GridLayout(5, 2, 5, 5));

    pnlInput.add(new JLabel("Device Type"));
    jcbDeviceType = new JComboBox(theLockheed.getDeviceTypes());
    pnlInput.add(jcbDeviceType);

    pnlInput.add(new JLabel("Device ID"));
    pnlInput.add(new JLabel(Device.getCurrentID() + ""));

    pnlInput.add(new JLabel("Device Name"));
    txtName = new JTextField();
    pnlInput.add(txtName);

    pnlInput.add(new JLabel("Device Location"));
    txtLocation = new JTextField();
    pnlInput.add(txtLocation);


    pnlInput.add(new JLabel("Device Description"));
    txtDescription = new JTextField();
    pnlInput.add(txtDescription);


    pnlButtons = new JPanel();

    butOkay = new JButton(OKAY_LABEL);
    butOkay.addActionListener(this);
    pnlButtons.add(butOkay);

    butCancel = new JButton(CANCEL_LABEL);
    butCancel.addActionListener(this);
    pnlButtons.add(butCancel);




    this.setSize(WINDOW_SIZE.width, WINDOW_SIZE.height);
    this.setLocation(
            (LockheedConstants.SCREEN_SIZE.width - WINDOW_SIZE.width) / 2,
            (LockheedConstants.SCREEN_SIZE.height - WINDOW_SIZE.height) / 2
                    );
    this.setTitle(WINDOW_TITLE);

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(pnlInput, BorderLayout.CENTER);
    this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

    this.show();
  }

  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand = e.getActionCommand();

    if (strCommand.equals(OKAY_LABEL)) {
      Device  theDevice = new Device(jcbDeviceType.getSelectedItem().toString());
      theDevice.setName(txtName.getText());
      theDevice.setDescription(txtDescription.getText());
      theDevice.setLocation(txtLocation.getText());


      theLockheed.addDevice(theDevice);

      this.hide();
      this.dispose();
    } else if (strCommand.equals(CANCEL_LABEL)) {
      this.hide();
      this.dispose();
    }


  }





}
