import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Sender extends JPanel implements ActionListener, KeyListener {

  /** CONSTANTS **/
  public static final String SEND = "Send";
  public static final String WINDOW_TITLE = "TWA Console";
  public static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
  public static final Rectangle WINDOW = new Rectangle(0, SCREEN.height - 75,
                                                       SCREEN.width, 50);

  /** INSTANCE VARIABLES **/
  private BufferedOutputStream theOutputStream;
  private JFrame myFrame;
  private JTextField txtCommand;
  private JButton butSend;
  private TWA theTWA;

  private boolean userCanInput;


  /**
    * Sender - sends the data
    **/
  public Sender(BufferedOutputStream theOutputStream, TWA theTWA) {
    this.theOutputStream = theOutputStream;
    this.theTWA = theTWA;
    userCanInput = true;

    makePanel();

  }

  /**
    * makePanel
    **/
  private void makePanel() {
    txtCommand = new JTextField();
    txtCommand.addKeyListener(this);

    this.setLayout(new BorderLayout());
    this.add(txtCommand, BorderLayout.CENTER);


  }


  /**
    * makeWindow -makes a window to send data
    **/
  private void makeWindow() {
    myFrame = new JFrame();
    txtCommand = new JTextField();
//    butSend = new JButton(SEND);

    txtCommand.addKeyListener(this);
//    butSend.addActionListener(this);

    myFrame.getContentPane().setLayout(new BorderLayout());
    myFrame.getContentPane().add(txtCommand, BorderLayout.CENTER);
//    myFrame.getContentPane().add(butSend, BorderLayout.SOUTH);

    myFrame.setTitle(WINDOW_TITLE);
    myFrame.setBounds(WINDOW.x, WINDOW.y, WINDOW.width, WINDOW.height);
    myFrame.setVisible(true);
  }

  /**
    * sendData
    **/
  public void sendData(String strDataToSend) {
    int i;

    try {
      for (i = 0; i < strDataToSend.length(); i++) {
        theOutputStream.write(strDataToSend.charAt(i));
      }
      theOutputStream.flush();
    } catch (IOException e) { }
  }

  public void sendData(int iDataToSend) {
    try {
      theOutputStream.write(iDataToSend);
      theOutputStream.flush();

    } catch (IOException e) { }
  }

  public void sendData(char chDataToSend) {
    try {
      theOutputStream.write(chDataToSend);
      theOutputStream.flush();

    } catch (IOException e) { }
  }


  /**
    * actionPerformed
    **/
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(SEND)) {

      sendData((txtCommand.getText() + "\r"));
      txtCommand.setText("");

    }
  }

  /**
    * setUnserInputEnabled
    **/
  public void setUserInputEnabled(boolean b) {
    userCanInput = b;
  }


  /**
    * keyTyped    - required by KeyListener
    * keyPressed
    * keyReleased
    **/
  public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        if (userCanInput) {
          sendData("\r");
        }
      } else {
        if (userCanInput) {
          sendData(txtCommand.getText());
        }
        txtCommand.setText("");
      }
  }

  public void keyPressed(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      if (userCanInput) {
        sendData((char) 8);
      }
    }

  }
  public void keyTyped(KeyEvent e) { }

}
