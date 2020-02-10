import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.util.Arrays;

class ValidationDisplay implements ActionListener {

  /** CONSTANTS **/
  public static final Dimension WINDOW_SIZE = new Dimension(320, 128);
  public static final String WINDOW_TITLE = "Please Log In";
  /** BUTTON LABELS **/
  public static final String LOG_IN = "Log In";



  /** INSTANCE VARIABLES **/
  private JFrame theFrame;
  private JButton butLogIn;
  private JTextField txtUserID;
  private JPasswordField txtPassword;

  private Lockheed theLockheed;



  /**
    * ValidationDisplay
    **/
  public ValidationDisplay() {
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


    JPanel pnlInput = new JPanel();
    pnlInput.setLayout(new GridLayout(2, 2, 5, 5));

    pnlInput.add(new JLabel("User ID"));
    txtUserID = new JTextField();
    pnlInput.add(txtUserID);

    pnlInput.add(new JLabel("Password"));
    txtPassword = new JPasswordField();
//    txtPassword.setEchoChar('*');
    pnlInput.add(txtPassword);




    JPanel pnlButtons = new JPanel();

    butLogIn = new JButton(LOG_IN);
    butLogIn.addActionListener(this);
    pnlButtons.add(butLogIn);

    theFrame.getContentPane().setLayout(new BorderLayout());
    theFrame.getContentPane().add(pnlInput, BorderLayout.CENTER);
    theFrame.getContentPane().add(pnlButtons, BorderLayout.SOUTH);
    theFrame.show();
  }

  /**
    * actionPerfromed
    **/
  public void actionPerformed(ActionEvent e) {
    String strCommand = e.getActionCommand();
    char[] chPassword;

    if (strCommand.equals(LOG_IN)) {
      //check password

      chPassword = txtPassword.getPassword();

      char[] chCorrectPassword = new char[] {'h', 'e', 'l', 'l', 'o' };


      if (Arrays.equals(chPassword, chCorrectPassword)) {
        new Lockheed();
        theFrame.hide();
        theFrame.dispose();
      } else {
        //txtPassword.setText("");
        JOptionPane.showMessageDialog(theFrame, "Your password was incorrect", "Invalid Password", JOptionPane.WARNING_MESSAGE);
      }

      //reset the passwords variable to all nulls for security purposes
      Arrays.fill(chPassword, 0, chPassword.length, '\0');
      Arrays.fill(chCorrectPassword, 0, chCorrectPassword.length, '\0');




    }

  }

}
