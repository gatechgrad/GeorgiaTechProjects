import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class ProgressBarFrame {
  /** CONSTANTS **/



  /** INSTANCE VARIABLES **/
  JProgressBar theProgressBar;
  JLabel lblProgress, lblTimeLeft, lblTotalTime;
  int iProgressValue;
  int iMax;
  long iTime;
  Dimension dimFrameSize;
  JFrame frameProgress;

  /**
    * ProgressBarFrame
    **/
  public ProgressBarFrame(int iStart, int iMax) {

    this.iProgressValue = iStart;
    this.iMax = iMax;

    iTime = System.currentTimeMillis();

    dimFrameSize = new Dimension(200, 50);
    iProgressValue = 0;

    makeWindow();

  }

  /**
    * makeWindow
    **/
  private void makeWindow() {
    Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();

    theProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, iProgressValue, iMax);
    theProgressBar.setStringPainted(true);
    theProgressBar.setPreferredSize(new Dimension(300, 50));
    lblProgress = new JLabel("");
    lblTotalTime = new JLabel("Total Time: " + ((System.currentTimeMillis() - iTime) / 1000.0) + "s");
    lblTimeLeft = new JLabel("Estimated Time Remaining: " + "s");

    JPanel pnlTime = new JPanel(new GridLayout(2, 1));
    pnlTime.add(lblTotalTime);
    pnlTime.add(lblTimeLeft);

    frameProgress = new JFrame();




    frameProgress.getContentPane().setLayout(new BorderLayout());
    frameProgress.getContentPane().add(theProgressBar, BorderLayout.CENTER);
    frameProgress.getContentPane().add(lblProgress, BorderLayout.NORTH);
    frameProgress.getContentPane().add(pnlTime, BorderLayout.SOUTH);


    frameProgress.pack();


    frameProgress.setLocation( (dimScreen.width - frameProgress.getSize().width) / 2,
                               (dimScreen.height - frameProgress.getSize().height) / 2
                             );

    frameProgress.show();

  }

  /**
    * increment
    **/
  public void increment(String str) {
    lblProgress.setText(str);
    iProgressValue++;
    lblTotalTime.setText("Total Time: " + ((System.currentTimeMillis() - iTime) / 1000.0) + "s");
    lblTimeLeft.setText("Estimated Time Remaining: " + ((System.currentTimeMillis() - iTime) * (0.0 + iMax - iProgressValue) / (iProgressValue + 0.0) / 1000.0) + "s");

    theProgressBar.setValue(iProgressValue);

  }

  public void increment() {
    iProgressValue++;

    lblTotalTime.setText("Total Time: " + ((System.currentTimeMillis() - iTime) / 1000.0) + "s");
    lblTimeLeft.setText("Estimated Time Remaining: " + ((System.currentTimeMillis() - iTime) * (0.0 + iMax - iProgressValue) / (iProgressValue + 0.0) / 1000.0) + "s");

    theProgressBar.setValue(iProgressValue);
  }


  /**
    * setText
    **/
  public void setText(String str) {
    lblProgress.setText(str);
  }

  /**
    * addText
    **/
  public void addText(String str) {
    lblProgress.setText(lblProgress.getText() + str);
  }

  /**
    * shutdown
    **/
  public void shutdown() {
    frameProgress.setVisible(false);
    frameProgress.dispose();
  }

  /**
    * setTitle
    **/
  public void setTitle(String str) {
    frameProgress.setTitle(str);
  }




}
