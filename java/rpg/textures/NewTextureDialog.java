import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.io.*;



/**
 * dialog box to get information about a new texture
 * @author Levi D. Smith
 * @version 0.1, December 30, 2000
 */
 public class NewTextureDialog extends JDialog implements ActionListener {

     //CONSTANTS
     public static final String GET_IMAGE_LABEL = "Get Image";
     public static final String OKAY_LABEL = "Okay";
     public static final String CANCEL_LABEL = "Cancel";
     public static final String NEW_TEXTURE_SET_LABEL = "New Texture Set";
     
     //INSTANCE VARIABLES (COMPONENTS)
     private JFrame frameOwner;
     private JComboBox jcbTextureSet;
     private JTextField txtName;
     private JLabel lblTexturePicture;

     //INSTANCE VARIABLES (DATA)
     private Vector vectTextureSets;
     private ImageIcon imgPicture;
     
     /**
      * called when object is instantiated
      */
     public NewTextureDialog(JFrame frameOwner, Vector vectTextureSets) {
         super(frameOwner, true);
     
         this.vectTextureSets = vectTextureSets;
         setupWindow();
     }

     /**
      * creates the window
      */
     private void setupWindow() {
         
         JPanel pnlMain = new JPanel();
         pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

         JPanel pnlTemp;
         JPanel pnlTextureSet;
         pnlTextureSet = new JPanel();
         pnlTextureSet.setLayout(new BoxLayout(pnlTextureSet, BoxLayout.Y_AXIS));
         pnlTextureSet.setBorder(BorderFactory.createTitledBorder("Texture Set"));
         pnlTemp = new JPanel();
         pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
         pnlTemp.add(new JLabel("Texture Set: "));
         jcbTextureSet = new JComboBox(vectTextureSets);
         pnlTemp.add(jcbTextureSet);
         pnlTextureSet.add(pnlTemp);
         pnlTemp = new JPanel();
         pnlTemp.setAlignmentX(SwingConstants.LEFT);
         addButton(NEW_TEXTURE_SET_LABEL, pnlTemp);
         pnlTextureSet.add(pnlTemp);
         pnlMain.add(pnlTextureSet);

         pnlTemp = new JPanel();
         pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
         pnlTemp.add(new JLabel("Texture Name: "));
         txtName = new JTextField(20);
         pnlTemp.add(txtName);
         pnlMain.add(pnlTemp);

         pnlTemp = new JPanel();
         pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
         pnlTemp.setBorder(BorderFactory.createTitledBorder("Image"));
         lblTexturePicture = new JLabel();
         pnlTemp.add(lblTexturePicture);
         addButton(GET_IMAGE_LABEL, pnlTemp);
         pnlMain.add(pnlTemp);


         
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
     private void addButton(String strLabel, JPanel pnlOwner) {
         JButton butNew = new JButton(strLabel);
         butNew.addActionListener(this);
         pnlOwner.add(butNew);
     }

     /**
      * gets the image for the texture
      */
     private void loadImage() {
         int iValue;
         /*
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("."));
        jfc.showOpenDialog(this);

        File fileImage = jfc.getSelectedFile();
        imgPicture = new ImageIcon(fileImage.toString());
        lblTexturePicture.setIcon(imgPicture);
        */
        
        JFileChooser chooser = new JFileChooser();                           
        //chooser.setAccessory(new FilePreviewer(chooser));                     
                                                                              
        File swingFile = new File(".");                     
        
        if(swingFile.exists()) {                                              
            chooser.setCurrentDirectory(swingFile);                                 
            chooser.setSelectedFile(swingFile);                                     
        }                                                                     
                                                                              
        iValue = chooser.showOpenDialog(frameOwner);                   
        if(iValue == 0) {                                                     
            File theFile = chooser.getSelectedFile();                               
            if(theFile != null) {                                                   
                imgPicture = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
                lblTexturePicture.setIcon(imgPicture);

            }
        }
      }

     /**
      * if possible, adds the texture to the texture vector
      */
     private void addTexture() {
         TextureSet theTextureSet;

         theTextureSet = (TextureSet) vectTextureSets.elementAt(jcbTextureSet.getSelectedIndex());

         imgPicture.setDescription(txtName.getText());
         theTextureSet.addTexture(imgPicture);

         this.hide();
         this.dispose();
     }
 
    /**
     * called when an action is performed on a component
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand;
        strCommand = e.getActionCommand();

        if (strCommand.equals(OKAY_LABEL)) {
            addTexture();
        } else if (strCommand.equals(NEW_TEXTURE_SET_LABEL)) {
            new NewTextureSetDialog(frameOwner, vectTextureSets);
        } else if (strCommand.equals(GET_IMAGE_LABEL)) {
            loadImage();
        }
    }
 
 }
