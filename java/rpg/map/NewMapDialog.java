import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

public class NewMapDialog extends JDialog implements ActionListener {
    //CONSTANTS
    public static final String OKAY_LABEL = "Okay";
    public static final String CANCEL_LABEL = "Cancel";
    public static final String HELP_LABEL = "Help";

    public static final String WINDOW_TITLE = "New Map";

    //INSTANCE VARIABLES (components)
    private JTextField txtRows, txtCols;
    private JTextField txtCellWidth, txtCellHeight;
    private JTextField txtName;

    private JButton butOkay, butCancel, butHelp;
    private RPGGenerator theRPGG;

    //INSTANCE VARIABLES (data)
    private Vector vectMapList;
    private Vector vectTextures;



    /**
     * default constructor
     */
    public NewMapDialog(RPGGenerator theRPGG, Vector vectMapList, Vector vectTextures) {
        super(theRPGG.getFrame(), true);

        this.theRPGG = theRPGG;
        this.vectMapList = vectMapList;
        this.vectTextures = vectTextures;

        setupWindow();
    }

    /**
     * creates the window
     */
    public void setupWindow() {
        JPanel pnlMain;
        JPanel pnlTemp;
        JPanel pnlButtons;

        pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        
        pnlTemp = new JPanel();
        pnlTemp.add(new JLabel("Name:"));
        txtName = new JTextField(20);
        pnlTemp.add(txtName);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.add(new JLabel("Rows:"));
        txtRows = new JTextField(5);
        pnlTemp.add(txtRows);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.add(new JLabel("Columns:"));
        txtCols = new JTextField(5);
        pnlTemp.add(txtCols);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.add(new JLabel("Cell Size:"));
        txtCellWidth = new JTextField(5);
        txtCellHeight = new JTextField(5);
        pnlTemp.add(txtCellWidth);
        pnlTemp.add(new JLabel(" x "));
        pnlTemp.add(txtCellHeight);
        pnlMain.add(pnlTemp);


        pnlButtons = new JPanel();
        
        butOkay = new JButton(OKAY_LABEL);
        butOkay.addActionListener(this);
        pnlButtons.add(butOkay);

        butCancel = new JButton(CANCEL_LABEL);
        butCancel.addActionListener(this);
        pnlButtons.add(butCancel);

        butHelp = new JButton(HELP_LABEL);
        butHelp.addActionListener(this);
        pnlButtons.add(butHelp);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pnlMain, BorderLayout.CENTER);
        this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);


        this.setTitle(WINDOW_TITLE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(RPGGeneratorConstants.getCenteredWindowLocation(this));
        this.show();


    }

    /**
     * called when user performs an action on a listening component
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand = e.getActionCommand();

        if (strCommand.equals(OKAY_LABEL)) {
            try {
                Map theNewMap = new Map(Integer.parseInt(txtRows.getText()),
                                        Integer.parseInt(txtCols.getText()),
                                        new Dimension(Integer.parseInt(txtCellWidth.getText()),
                                                      Integer.parseInt(txtCellHeight.getText())
                                                      )
                                        );
                theNewMap.setName(txtName.getText());
                vectMapList.add(theNewMap);
                new MapEditor(theRPGG, theNewMap, vectTextures);
                this.hide();
                this.dispose();

            } catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(this, "Non-numeric value found in a numeric field", "Error", JOptionPane.ERROR_MESSAGE);                
            }
        
        } else if (strCommand.equals(CANCEL_LABEL)) {

        } else if (strCommand.equals(HELP_LABEL)) {
        }
        

    }
}
