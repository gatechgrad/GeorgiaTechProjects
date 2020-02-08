import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.util.Vector;


/**
 *  TextureViewer - displays all of the textures created by the user
 *  @author Levi D. Smith
 *  @version 0.1, December 30, 2000
 */
public class TextureViewer extends Viewer {

    //CONSTANTS
    public static final String WINDOW_TITLE = "Texture Viewer";

    //INSTANCE VARIABLES (DATA)
    private Vector vectTextures;
    

    /**
     * called when object is instantiated
     */
    public TextureViewer(Vector vectTextures, RPGGenerator theRPGG) {
        super(theRPGG);
        this.vectTextures = vectTextures;
        setupWindow();
        
    }

    /**
     * TextureViewer specific window setup
     */
    private void setupWindow() {
        listItems.setListData(vectTextures);

        this.setTitle(WINDOW_TITLE);
        this.show();
        
    }

    /**
     * finds the selected texture set
     */
    private TextureSet findSelectedTextureSet() {
        TextureSet textureSetToReturn;
        
        textureSetToReturn = null;

        if (this.selectionIsInBounds(vectTextures.size())) {
            textureSetToReturn = (TextureSet) vectTextures.elementAt(this.getListCurrentSelectedIndex());
        }

        return textureSetToReturn;
    }


    /**
     * actions to be performed to the main panel when an item on the list is clicked
     */
    protected void updateTableauPanel() {
        TextureSet tsToDisplay;
        int i;

        tsToDisplay = findSelectedTextureSet();

        //pnlTableau.setVisible(false);
        pnlTableau.invalidate();

        pnlTableau.removeAll();

        if (tsToDisplay != null) {
            Vector vectImages = tsToDisplay.getTextures();

            for (i = 0; i < vectImages.size(); i++) {
                ImageIcon iiToAdd = (ImageIcon) vectImages.elementAt(i);
                JLabel lblToAdd = new JLabel(iiToAdd.getDescription());
                lblToAdd.setIcon(iiToAdd);
                
                pnlTableau.add(lblToAdd);
            }

           /*
            Component[] components = pnlTableau.getComponents();
            for (i = 0; i < components.length; i++) {
                //components[i].repaint();
                components[i].invalidate();
                System.out.println("Repainting components");
            }
            System.out.println("Repainted components");
            */

            

            //pnlTableau.setVisible(true);
            pnlTableau.validate();


            this.repaint();

           
                        
        }

    }

    /**
     * action to take when edit button is pressed
     */
    protected void editButtonPressed() {

    }

    /**
     * action to take when new button is pressed
     */
    protected void newButtonPressed() {
        new NewTextureDialog(theRPGG.getFrame(), vectTextures);
        this.repaint();
    }

    /**
     * action to take when delete button is pressed
     */
    protected void deleteButtonPressed() {
        if (this.selectionIsInBounds(vectTextures.size())) {
                vectTextures.removeElementAt(getListCurrentSelectedIndex());
                this.repaint();
        }
    }
}
