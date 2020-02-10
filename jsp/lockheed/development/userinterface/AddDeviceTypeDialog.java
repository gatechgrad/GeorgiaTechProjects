import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class AddDeviceTypeDialog extends JDialog implements ActionListener {

  /** CONSTANTS **/
  public static final Dimension WINDOW_SIZE = new Dimension(320, 100);
  public static final String WINDOW_TITLE = "Add Device Type";

  public static final String OKAY_LABEL = "Okay";
  public static final String CANCEL_LABEL = "Cancel";


  /** INSTANCE VARIABLES **/
  private JTextField txtDeviceType;

  private JButton butOkay;
  private JButton butCancel;


  private Lockheed theLockheed;  //managing object


  /**
    * AddDeviceTypeDialog
    **/
  public AddDeviceTypeDialog(Frame ownerFrame, Lockheed theLockheed) {
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
    pnlInput.setLayout(new GridLayout(1, 2, 5, 5));

    pnlInput.add(new JLabel("Device Type"));
    txtDeviceType = new JTextField();
    pnlInput.add(txtDeviceType);

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
      theLockheed.addDeviceType(txtDeviceType.getText());

      this.hide();
      this.dispose();
    } else if (strCommand.equals(CANCEL_LABEL)) {
      this.hide();
      this.dispose();
    }


  }





}
