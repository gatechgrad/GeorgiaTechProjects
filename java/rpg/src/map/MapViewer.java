import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.util.Vector;


/**
 *  MapViewer - displays all of the maps created by the user
 *  @author Levi D. Smith
 *  @version 0.1
 */
public class MapViewer extends Viewer {

    //CONSTANTS
    public static final String WINDOW_TITLE = "Map Viewer";

    
    //INSTANCE VARIABLES (components)
    private JList listMaps;
    private JPanel pnlImages;

    //INSTANCE VARIABLES (data)
    private Vector vectMaps;
    private Vector vectTextures;
    
    /**
     * constructor
     */
    public MapViewer(Vector vectMaps, Vector vectTextures, RPGGenerator theRPGG) {
        super(theRPGG);
        this.vectMaps = vectMaps;
        this.vectTextures = vectTextures;
        setupWindow();
        
    }

    /**
     * makes the main window
     */
    private void setupWindow() {
        
        /*
        listMaps = new JList(vectMaps);
        listMaps.addListSelectionListener(this);
        listMaps.setFixedCellWidth(CELL_WIDTH);
        listMaps.setPreferredSize(new Dimension(LIST_SIZE));
        
        JScrollPane pnlList = new JScrollPane(listMaps, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        */
        
        
        this.listItems.setListData(vectMaps);
        
        this.setTitle(WINDOW_TITLE);
        this.show();
       

    }

    /**
     * refreshes the map display
     */
    public void updateMapPanel() {

    }

    /**
     * finds the map which is currently selected in the list
     */
    public Map findSelectedMap() {
         Map mapToReturn;

         mapToReturn = null;

         int iCurrentIndex;

        iCurrentIndex = this.getListCurrentSelectedIndex();
        if (iCurrentIndex > -1) {
            mapToReturn = ((Map) vectMaps.elementAt(iCurrentIndex));
        }

        return mapToReturn;
    }

    

    /**
     * called when new button is pressed
     */
    protected void newButtonPressed() {

        new NewMapDialog(theRPGG, vectMaps, vectTextures);

        listMaps.removeAll();
        listMaps.setListData(vectMaps);


    }

    /**
     * called when edit button is pressed
     */
    protected void editButtonPressed() {
        Map mapCurrent;

        mapCurrent = findSelectedMap();

        if (mapCurrent != null) {
            theRPGG.addInternalFrame(new MapEditor(theRPGG, mapCurrent, vectTextures));
        } else {
            JOptionPane.showMessageDialog(theRPGG.getFrame(), "Please Choose A Map First", "Map Not Chosen", JOptionPane.WARNING_MESSAGE);
        }


    }

    /**
     * called when deleteButton is pressed
     */
    protected void deleteButtonPressed() {
        if ((listMaps.getSelectedIndex() > -1) &&
            (listMaps.getSelectedIndex() < vectMaps.size())
            ) {
            vectMaps.removeElementAt(listMaps.getSelectedIndex());

            listMaps.removeAll();
            listMaps.setListData(vectMaps);


        }


    }

    /**
     * called when the tableau panel needs to be updated
     */
    protected void updateTableauPanel() {
    }



}
