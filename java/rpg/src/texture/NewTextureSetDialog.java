import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

public class NewTextureSetDialog extends JDialog implements ActionListener {
    //CONSTANTS
    public static final String OKAY_LABEL = "Okay";
    public static final String CANCEL_LABEL = "Cancel";

    //INSTANCE VARIABLES (components)
    private JTextField txtName;
    private JButton butOkay, butCancel;
    
    //INSTANCE VARIABLES (data)
    private Vector vectTextures;
    

    /**
     * called when object is instantiated
     */
    public NewTextureSetDialog(JFrame frameOwner, Vector vectTextures) {
        super(frameOwner, true);
        this.vectTextures = vectTextures;
        setupWindow();
    }

    /**
     * creates the window
     */
    private void setupWindow() {

        JPanel pnlMain;

        pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.X_AXIS));
        
        pnlMain.add(new JLabel("Texture Set Name:"));
        txtName = new JTextField(20);
        pnlMain.add(txtName);
        
        JPanel pnlButtons = new JPanel();
        addButton(OKAY_LABEL, pnlButtons);
        addButton(CANCEL_LABEL, pnlButtons);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pnlMain, BorderLayout.CENTER);
        this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(RPGGeneratorConstants.getCenteredWindowLocation(this));
        this.show();


    }

    /**
     * creates and adds a button to the panel
     */
    private void addButton(String strLabel, JPanel thePanel) {
        JButton theButton;
        theButton = new JButton(strLabel);
        theButton.addActionListener(this);
        thePanel.add(theButton);

    }

    /**
     * adds a texture set to the textures vector
     */
    private void addTextureSet() {
        TextureSet theTextureSet = new TextureSet(txtName.getText());
        vectTextures.add(theTextureSet);
        
    }


    /**
     * called when an action is performed on a listening component
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand;
        strCommand = e.getActionCommand();
        if (strCommand.equals(OKAY_LABEL)) {
            if (!txtName.getText().equals("")) {
                addTextureSet();
                this.hide();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please Enter a Name First", "Name Not Chosen", JOptionPane.WARNING_MESSAGE);
                
            }
        } else if (strCommand.equals(CANCEL_LABEL)) {
            this.hide();
            this.dispose();
        }
    }
}
