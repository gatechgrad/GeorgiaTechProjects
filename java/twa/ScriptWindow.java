import java.awt.*;
import javax.swing.*;

class ScriptWindow extends JDialog {

  /** INSTANCE VARIABLES **/
  JTextArea txtConsole;
  JScrollPane spBuffer;

  /**
    * ScriptWindow
    **/
  public ScriptWindow(JFrame theFrame) {
    super(theFrame);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setupWindow();
  }

  /**
    * setupWindow
    **/
  private void setupWindow() {
    txtConsole = new JTextArea(30, 50);
    setTitle("Running Script");

    txtConsole.setMaximumSize(new Dimension(600, 150));
    txtConsole.setPreferredSize(new Dimension(600, 150));

    txtConsole.setForeground(Screen.FG_COLOR);
    txtConsole.setBackground(Screen.BKG_COLOR);
    txtConsole.setFont(new Font("monospaced", Font.PLAIN, 12));

    spBuffer = new JScrollPane();
    spBuffer.getViewport().add(txtConsole);

    getContentPane().add(spBuffer);

    pack();
    setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
    show();

  }

  /**
    * addText
    **/
  public void addText(String str) {
    txtConsole.setText(txtConsole.getText() + str);
    txtConsole.setSize(txtConsole.getPreferredSize());
    invalidate();
    spBuffer.getViewport().add(txtConsole);
    pack();
  }





}
