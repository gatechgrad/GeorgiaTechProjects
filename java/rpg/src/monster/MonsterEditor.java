import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.io.File;

import java.util.Vector;

public class MonsterEditor extends JInternalFrame implements ActionListener {

    //CONSTANTS
    public static final int FIELD_SIZE = 20;
    public static final String OKAY_BUTTON = "Okay";
    public static final String APPLY_BUTTON = "Apply";    
    public static final String CANCEL_BUTTON = "Cancel";
    public static final String ADD_IMAGE_BUTTON = "Add Image";
    
    public static final String WINDOW_TITLE = "Monster Editor";


    //INSTANCE VARIABLES (COMPONENTS)
    private JTextField txtMonsterName;
    private JTextField txtMonsterMaxHP;
    private JTextField txtMonsterMaxMP;
    private JTextField txtMonsterLevel;
    private JTextField txtMonsterMoney;
    private JTextField txtMonsterExperience;



    private JButton butOkay;
    private JButton butApply;
    private JButton butCancel;
    private JButton butAddImage;

    private JComboBox jcbImageSet;
    private JComboBox jcbImages;

    private Viewer theViewer;
    
    //INSTANCE VARIABLES (DATA)
    private Monster theMonster;

    
    /**
     * called when MonsterEditor is instantiated
     */
    public MonsterEditor(Viewer theViewer, Monster theMonster) {
        this.theViewer = theViewer;
        this.theMonster = theMonster;
        setupWindow();
        
    }

    /**
     * makes the main window
     */
    private void setupWindow() {
        
        JPanel pnlMain = new JPanel();
        JPanel pnlTemp;
        
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlMain.setAlignmentY(Box.TOP_ALIGNMENT);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Name:"));
        txtMonsterName = new JTextField(theMonster.getName(), FIELD_SIZE);
        pnlTemp.add(txtMonsterName);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Max HP:"));
        txtMonsterMaxHP = new JTextField(String.valueOf(theMonster.getMaxHP()), FIELD_SIZE);
        pnlTemp.add(txtMonsterMaxHP);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Max MP:"));
        txtMonsterMaxMP = new JTextField(String.valueOf(theMonster.getMaxMP()), FIELD_SIZE);
        pnlTemp.add(txtMonsterMaxMP);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Level:"));
        txtMonsterLevel = new JTextField(String.valueOf(theMonster.getLevel()), FIELD_SIZE);
        pnlTemp.add(txtMonsterLevel);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Money:"));
        txtMonsterMoney = new JTextField(String.valueOf(theMonster.getMoney()), FIELD_SIZE);
        pnlTemp.add(txtMonsterMoney);
        pnlMain.add(pnlTemp);

        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Experience:"));
        txtMonsterExperience = new JTextField(String.valueOf(theMonster.getExperience()), FIELD_SIZE);
        pnlTemp.add(txtMonsterExperience);
        pnlMain.add(pnlTemp);



        pnlTemp = new JPanel();
        pnlTemp.setLayout(new BoxLayout(pnlTemp, BoxLayout.X_AXIS));
        pnlTemp.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTemp.add(new JLabel("Image Set:"));
        jcbImageSet = new JComboBox();
        jcbImageSet.insertItemAt(Monster.getImageSetName(0), 0);
        jcbImageSet.insertItemAt(Monster.getImageSetName(1), 1);
        jcbImageSet.insertItemAt(Monster.getImageSetName(2), 2);
        jcbImageSet.insertItemAt(Monster.getImageSetName(3), 3);
        jcbImageSet.setSelectedIndex(0);
        pnlTemp.add(jcbImageSet);
        

        pnlMain.add(pnlTemp);



        JPanel pnlButtons = new JPanel();

        butOkay = new JButton(OKAY_BUTTON);
        butOkay.addActionListener(this);
        pnlButtons.add(butOkay);
        
        butApply = new JButton(APPLY_BUTTON);
        butApply.addActionListener(this);
        pnlButtons.add(butApply);
        
        butCancel = new JButton(CANCEL_BUTTON);
        butCancel.addActionListener(this);
        pnlButtons.add(butCancel);
        
        butAddImage = new JButton(ADD_IMAGE_BUTTON);
        butAddImage.addActionListener(this);
        pnlButtons.add(butAddImage);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pnlMain, BorderLayout.CENTER);
        this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

        this.setTitle(WINDOW_TITLE + " - " + theMonster.getName());

        this.pack();
        this.show();
       

    }

    /**
     * returns the updated monster
     */
    public Monster getValue() {
        return theMonster;
    }

    /**
     * updates the monster object with the values specified in the JTextFields
     */
    public void updateMonster() {
        theMonster.setName(txtMonsterName.getText());


        try {
            theMonster.setMaxHP(Integer.parseInt(txtMonsterMaxHP.getText()));
            theMonster.setMaxMP(Integer.parseInt(txtMonsterMaxMP.getText()));
            theMonster.setLevel(Integer.parseInt(txtMonsterLevel.getText()));
            theMonster.setMoney(Integer.parseInt(txtMonsterMoney.getText()));
            theMonster.setExperience(Integer.parseInt(txtMonsterExperience.getText()));
        } catch (NumberFormatException e) {
        }
    }

    /**
     * adds an image to the selected image set
     */
    public void addImage() {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("."));
        jfc.showOpenDialog(this);

        File fileImage = jfc.getSelectedFile();
        ImageIcon imgToAdd = new ImageIcon(fileImage.toString());

        if (jcbImageSet.getSelectedIndex() >= 0) {
          theMonster.addImage(imgToAdd, jcbImageSet.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(this, "Please Select an Image Set", "No Set Chosen", JOptionPane.WARNING_MESSAGE);
        }
        
       

    }

    
    /**
     * required by ActionListener interface
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand = e.getActionCommand();
            
        if (strCommand.equals(OKAY_BUTTON)) {
            updateMonster();
            repaint();
            
            this.setVisible(false);
            this.dispose();
        } else if (strCommand.equals(ADD_IMAGE_BUTTON)) {
            addImage();
        }
    }

    
}
