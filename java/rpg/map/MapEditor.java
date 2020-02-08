import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Vector;


/**
 * allows the user to edit a map
 */
public class MapEditor extends JInternalFrame implements ActionListener {
    
    //CONSTANTS
    public static final String WINDOW_TITLE = "Map Editor";
    
    //INSTANCE VARIABLES (data)
    private Map theMap;
    private Vector vectTextures;
    private ImageIcon imgCurrent;

    //INSTANCE VARIABLES (components)

    private JComboBox jcbTextureSets;
    private JComboBox jcbTextures;
    private JComboBox jcbNPCs;

    private RPGGenerator theRPGG;


    /**
     * called when object is created
     */
    public MapEditor(RPGGenerator theRPGG, Map theMap, Vector vectTextures) {
        super("Map Editor", true, true, true, true);
        
        this.theRPGG = theRPGG;
        this.theMap = theMap;
        this.vectTextures = vectTextures;
        setupWindow();
    }

    /**
     * creates the map editor window
     */
    private void setupWindow() {
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        

        this.getContentPane().setLayout(new BorderLayout());
        
        CellDisplay theCellDisplay = new CellDisplay(theMap, this);

        JLabel lblPanel;
        JPanel pnlControls = new JPanel();
        pnlControls.setLayout(new BoxLayout(pnlControls, BoxLayout.Y_AXIS));
        
        JPanel pnlTextures = new JPanel();
        pnlTextures.setLayout(new BoxLayout(pnlTextures, BoxLayout.Y_AXIS));
        pnlTextures.setBorder(BorderFactory.createTitledBorder("Textures"));
        jcbTextureSets = new JComboBox(vectTextures);
        jcbTextureSets.addActionListener(this);
        pnlTextures.add(jcbTextureSets);
        jcbTextures = new JComboBox();
        pnlTextures.add(jcbTextures);
        jcbTextures.addActionListener(this);

        JPanel pnlNPCs = new JPanel();
        pnlNPCs.setLayout(new BoxLayout(pnlNPCs, BoxLayout.Y_AXIS));
        pnlNPCs.setBorder(BorderFactory.createTitledBorder("NPCs"));


        pnlControls.add(pnlTextures);
        pnlControls.add(pnlNPCs);



        JScrollPane spCellDisplay = new JScrollPane(theCellDisplay);
                
        
        this.getContentPane().add(spCellDisplay, BorderLayout.CENTER);
        this.getContentPane().add(pnlControls, BorderLayout.EAST);
        
        this.setTitle(WINDOW_TITLE + " - " + theMap.getName());
        this.pack();
        this.show();
    }

    /**
     * returns the selected texture
     */
    public ImageIcon getCurrentTexture() {
        return imgCurrent;
    }

    /**
     * called when an action is performed by the user
     */
    public void actionPerformed(ActionEvent e) {
        int i;
        String strCommand = e.getActionCommand();

        if (e.getSource().equals(jcbTextureSets)) {
            if (jcbTextureSets.getSelectedIndex() > -1) {
                jcbTextures.removeAllItems();
                for (i = 0; i < ((TextureSet) vectTextures.elementAt(jcbTextureSets.getSelectedIndex())).getTextures().size(); i++ ) {
                    jcbTextures.addItem((ImageIcon) ((TextureSet) vectTextures.elementAt(jcbTextureSets.getSelectedIndex())).getTextures().elementAt(i));
                }
                
            }
            
        } else if (e.getSource().equals(jcbTextures)) {
            imgCurrent = (ImageIcon) jcbTextures.getSelectedItem();
        }

    }
}
