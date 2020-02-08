import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.StringTokenizer;
import java.io.*;


public class CorpPasswordHackerDialog extends JDialog implements ActionListener, LineParser {

    //CONSTANTS
    public static final String OKAY_LABEL = "Okay";
    public static final String CANCEL_LABEL = "Cancel";
    public static final String GET_WORD_FILE_LABEL = "Get Word File";

    //INSTANCE VARIABLES
    private TWA theTWA;
    private StringTokenizer strWords;
    private int iCorpNumber;

    private JButton butOkay, butCancel, butGetWordFile;
    private JTextField txtCorpNumber;
    private JLabel lblWordFile;

    /**
     * default constructor
     */
    public CorpPasswordHackerDialog(Frame owner, TWA theTWA) {
        super(owner);

        strWords = new StringTokenizer("");

        this.theTWA = theTWA;
        setupWindow();
    }

    /**
     * creates the window
     */
    private void setupWindow() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel pnlMain;
        JPanel pnlButtons;
        JPanel pnlTemp;


        pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.add(new JLabel("Word File:"));
        lblWordFile = new JLabel();
        pnlTemp.add(lblWordFile);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.add(new JLabel("Corp Number:"));
        txtCorpNumber = new JTextField(5);
        pnlTemp.add(txtCorpNumber);
        pnlMain.add(pnlTemp);

        pnlButtons = new JPanel();

        butGetWordFile = new JButton(GET_WORD_FILE_LABEL);
        butGetWordFile.addActionListener(this);
        pnlButtons.add(butGetWordFile);


        butOkay = new JButton(OKAY_LABEL);
        butOkay.addActionListener(this);
        pnlButtons.add(butOkay);

        butCancel = new JButton(CANCEL_LABEL);
        butCancel.addActionListener(this);
        pnlButtons.add(butCancel);





        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pnlMain, BorderLayout.CENTER);
        this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

        
        
        this.pack();
        this.setLocation(((TWA.SCREEN.width - getWidth()) / 2), ((TWA.SCREEN.height - getHeight()) / 2));
        this.show();
        

    }

    /**
     * required by LineParser interface
     */
    public void parseString(String theString) {

        if (theString.indexOf("Which Corp number do you wish to join?") > -1) {
            theTWA.getTelnet().getSender().sendData("4\r");
        } else if (theString.indexOf("Enter the Password to join - ") > -1) {
            if (strWords.hasMoreTokens()) {
                theTWA.getTelnet().getSender().sendData(strWords.nextToken() + "\r");
            } else {
                quitParsing();
            }
        } else if (theString.indexOf("Corporate command") > -1) {
            theTWA.getTelnet().getSender().sendData("J");

        }

    }

    public void quitParsing() {

    }

    /**
     * required by ActionListener interface
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand;

        strCommand = e.getActionCommand();
        
        if (strCommand.equals(OKAY_LABEL)) {
            try {
                iCorpNumber = Integer.parseInt(txtCorpNumber.getText());
            } catch (NumberFormatException e2) {
            }
            theTWA.getParser().getParsers().addElement(this);


        } else if (strCommand.equals(GET_WORD_FILE_LABEL)) {
            getWordFile();

        } else if (strCommand.equals(CANCEL_LABEL)) {
            this.hide();
            this.dispose();
        }

    }

    /** 
     * loads the file of words
     */
    private void getWordFile() {
        JFileChooser jfcLoadData = new JFileChooser();
        int iChar;
        StringBuffer myBuffer = new StringBuffer();
        String strFile;
        

        jfcLoadData.setCurrentDirectory(new File("."));
        jfcLoadData.showOpenDialog(this);
        File fileLoad = jfcLoadData.getSelectedFile();

        try {
          FileReader fileToRead = new FileReader(fileLoad);

          iChar = fileToRead.read();
          while(iChar != -1) {
            myBuffer.append((char) iChar);
            iChar = fileToRead.read();
          }

          fileToRead.close();
          lblWordFile.setText(fileLoad.toString());

        } catch (IOException e) { }


        strFile = myBuffer.toString();
        //System.out.println(strFile);
        strWords = new StringTokenizer(strFile, " \n\r");


    }


}
