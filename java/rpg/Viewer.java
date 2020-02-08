import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.Vector;


/**
 *  Viewer - superclass for all other viewers
 *           separates most of the GUI from the data
 *  @author Levi D. Smith
 *  @version 0.1, December 30, 2000
 */
public abstract class Viewer extends JInternalFrame implements ListSelectionListener, ActionListener {

    //CONSTANTS
    public static final int CELL_WIDTH = 128;
    public static final String DONE_BUTTON = "Done";
    public static final String NEW_BUTTON = "New";
    public static final String EDIT_BUTTON = "Edit";
    public static final String DELETE_BUTTON = "Delete";
    public static final String WINDOW_TITLE = "Viewer";

    public static final Dimension LIST_SIZE = new Dimension(128, 256);
    public static final Dimension TABLEAU_SIZE = new Dimension(192, 256);
    

    //INSTANCE VARIABLES (COMPONENTS)
    protected JList listItems;
    protected JPanel pnlButtons;
    protected JPanel pnlTableau;

    protected JButton butDone;
    protected JButton butNew;
    protected JButton butEdit;
    protected JButton butDelete;

    //INSTANCE VARIABLES (data)
    protected RPGGenerator theRPGG;
    
    /**
     * called when object is instantiated
     */
    public Viewer(RPGGenerator theRPGG) {
        super("Viewer", true, true, true, true);
        
        this.theRPGG = theRPGG;
        setupWindow();
    }

    /**
     * makes the main window
     */
    private void setupWindow() {
        
        pnlTableau = new JPanel();
         
        pnlTableau.setLayout(new BoxLayout(pnlTableau, BoxLayout.Y_AXIS));
        pnlTableau.setMinimumSize(TABLEAU_SIZE);
        pnlTableau.setAlignmentX(Box.LEFT_ALIGNMENT);
        pnlTableau.setAlignmentY(Box.TOP_ALIGNMENT);
        JScrollPane jspTableau = new JScrollPane(pnlTableau);
        

        listItems = new JList();
        listItems.addListSelectionListener(this);
        listItems.setFixedCellWidth(CELL_WIDTH);
        JScrollPane pnlList = new JScrollPane(listItems, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        
        JPanel pnlButtons = new JPanel();
        createButtonPanel(pnlButtons);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(jspTableau, BorderLayout.CENTER);
        this.getContentPane().add(pnlList, BorderLayout.EAST);
        this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

        this.setTitle(WINDOW_TITLE);

        this.pack();
        
    }

    /**
     * creates a default button panel; override for custom button bar
     */
    private void createButtonPanel(JPanel pnlOwner) {
        addButton(DONE_BUTTON, pnlOwner);
        addButton(NEW_BUTTON, pnlOwner);
        addButton(EDIT_BUTTON, pnlOwner);
        addButton(DELETE_BUTTON, pnlOwner);
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
     * actions to be performed to the main panel when an item on the list is clicked
     */
    abstract void updateTableauPanel();

        
    /**
     * required by ListSelecitonListener
     */
    public void valueChanged(ListSelectionEvent e) {
        listItems.ensureIndexIsVisible(listItems.getSelectedIndex());

        if (e.getSource().equals(listItems)) {
            updateTableauPanel();
        }
    }

    /**
     * checks if a legal value is selected
     *
     * @param iMax - the maximum number of items in the data vector
     */
    protected boolean selectionIsInBounds(int iMax) {
        boolean bResult;

        bResult = false;

        if ((listItems.getSelectedIndex() > -1) &&
            (listItems.getSelectedIndex() < iMax)
            ) {
            bResult = true;
        }

        return bResult;
    }

    /**
     * gets the current selected index
     */
    protected int getListCurrentSelectedIndex() {
        return listItems.getSelectedIndex();
    }

    /**
     * calls abstract methods implemented by subclasses according to the button pressed
     */
    public void actionPerformed(ActionEvent e) {
        String strCommand;
        strCommand = e.getActionCommand();

        if (strCommand.equals(DONE_BUTTON)) {
            this.dispose();
        } else if (strCommand.equals(EDIT_BUTTON)) {
            editButtonPressed();
        } else if (strCommand.equals(NEW_BUTTON)) {
            newButtonPressed();
        } else if (strCommand.equals(DELETE_BUTTON)) {
            deleteButtonPressed();
        }

        this.updateTableauPanel();
        this.repaint();

    }

    protected abstract void editButtonPressed();
    protected abstract void newButtonPressed();
    protected abstract void deleteButtonPressed();

    
}

