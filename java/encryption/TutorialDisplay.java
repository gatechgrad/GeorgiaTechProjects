import java.awt.*;
import java.awt.event.*;

class TutorialDisplay extends Panel implements ActionListener {

  public static final String DISABLE = "Disable";
  public static final String ENABLE = "Enable";
  public static final String WINDOW = "Win";
  public static final String CLEAR = "Clear";


  boolean isDisabled;


  private TextArea txt;
  private Button butDisable, butWindow, butClear;

//  private StringBuffer strData;

  public TutorialDisplay() {
    setup();
    txt.setFont(EncryptionProject.CODE_FONT);
  }


  private void setup() {
    Panel pnlButtons;


    txt = new TextArea("", 20, 22, TextArea.SCROLLBARS_VERTICAL_ONLY);
    txt.setBackground(Color.black);
    txt.setForeground(Color.white);

    txt.setEditable(false);
//    strData = new StringBuffer("");

    isDisabled = false;


    this.setBackground(new Color(192, 192, 192));
    this.setLayout(new BorderLayout());
    this.add(new Label("Console"), BorderLayout.NORTH);
    this.add(txt, BorderLayout.CENTER);

    pnlButtons = new Panel(new FlowLayout(FlowLayout.LEFT));
    butDisable = new Button(DISABLE);
    butDisable.addActionListener(this);
    pnlButtons.add(butDisable);
    butWindow = new Button(WINDOW);
    butWindow.addActionListener(this);
    pnlButtons.add(butWindow);
    butClear = new Button(CLEAR);
    butClear.addActionListener(this);
    pnlButtons.add(butClear);
    this.add(pnlButtons, BorderLayout.SOUTH);


  }

  public void addText(String s) {
    if (isDisabled == false) {
//      strData.append(s);
      txt.append(s + " ");

//      if (strData.length() > 20000) {
//        strData.setLength(20000);
//      }


      if (txt.getText().length() > 20000) {
        txt.setText(txt.getText().substring(txt.getText().length() - 20000, txt.getText().length()));
      }

    }

  }


  public void actionPerformed(ActionEvent e) {
    String strCommand;

    strCommand = e.getActionCommand();

    if (strCommand.equals(DISABLE)) {
      isDisabled = true;
      txt.setBackground(Color.red);
      butDisable.setLabel(ENABLE);
      

    } else if (strCommand.equals(ENABLE)) {
      isDisabled = false;
      txt.setBackground(Color.black);
      butDisable.setLabel(DISABLE);


    } else if (strCommand.equals(CLEAR)) {
//      strData = new StringBuffer("");
      txt.setText("");


    } else if (strCommand.equals(WINDOW)) {
      final Frame tdFrame = new Frame();
      WindowListener wl = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          tdFrame.setVisible(false);
          tdFrame.dispose();
        }
      };
      tdFrame.addWindowListener(wl);

      tdFrame.add(this);
      tdFrame.setLocation(128, 128);
      tdFrame.setSize(640, 480);
      tdFrame.setVisible(true);

      txt.setFont(new Font("monospaced", Font.PLAIN, 14));
      this.add(new Panel(), BorderLayout.SOUTH);

    }

  }

}
