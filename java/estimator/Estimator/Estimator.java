package Estimator;

import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import RequirementsComponent.*;
import DesignComponent.*;
import ImplementationComponent.*;
import TestComponent.*;

import java.io.File;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.filechooser.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;



public class Estimator implements ActionListener {
	
	
	// ----- Constants
	
        private static final int VERTSPACE = 8;
        
	public static final boolean DEBUG = false;
	
	public static final String LOAD_BUTTON_LABEL = "Load Data";
	public static final String SAVE_BUTTON_LABEL = "Save Data";
	public static final String ESTIMATE_BUTTON_LABEL = "Estimate";
	public static final String REQ_NAME = "Requirements";
	public static final String DESIGN_NAME = "Design";
	public static final String IMP_NAME = "Implementation";
	public static final String TEST_NAME = "Testing";
	public static final String FILE_TYPE = "estimator";
	
	
	// ----- Instance variables
	
	JFrame myFrame;
	JTabbedPane myTabbedPane;
	EstimationData myData;
	
	boolean noError = true; // was there an error in the estimating?
	
	SubComponent [] myComponents; 
	
	
	//----- methods			
	
	
	/*
	* Default constructor.
	*/
	
	public Estimator () {
		myData = new EstimationData();
		makeComponents();
				
		myFrame = new JFrame("Estimator");
		myFrame.addWindowListener( new WindowAdapter() {
      		public void windowClosing(WindowEvent e) {
       	 		System.exit(0);
      		}
    	});
		myFrame.setContentPane(makeMainPanel());
		myFrame.setSize(800,600);
		myFrame.setVisible(true);
	}//Estimator

	
	public void makeComponents() {
                myComponents = new SubComponent [] {
                        new RequirementsUI(myData),
                        new DesignUI(myData),
                        new ImplementationUI(myData),
                        new TestUI(myData)
                };
                myComponents[0].setName(REQ_NAME);
		myComponents[1].setName(DESIGN_NAME);
		myComponents[2].setName(IMP_NAME);
                myComponents[3].setName(TEST_NAME);
	}//makeComponents
		

	/**
	* Generates the main panel for this GUI.  This has a tabbed pane and
	* a control pane.
	* @@return JPanel - a panel that serves as the main pane for the interface
	*/
	public JPanel makeMainPanel() {
		JPanel toReturn = new JPanel();	
		toReturn.setLayout(new BorderLayout());
		toReturn.add(myTabbedPane = makeTabbedPane(), BorderLayout.CENTER);
		toReturn.add(makeControlPanel(), BorderLayout.SOUTH);
	
		return (toReturn);
	}//generateMainPanel	
		
	
	/**
	*
	* Creates the panel which has the save, load, and estimate buttons.
	*@@param none
	*@@return JPanel - the control panel
	*/
	private JPanel makeControlPanel () {
		//Make the buttons
		JButton load = new JButton (LOAD_BUTTON_LABEL);
		load.addActionListener(this);
		JButton save = new JButton (SAVE_BUTTON_LABEL);
		save.addActionListener(this);
		JButton estimate = new JButton (ESTIMATE_BUTTON_LABEL);
		estimate.addActionListener(this);
		
		//add the buttons
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.X_AXIS));
		controlPanel.add(load);
		controlPanel.add(Box.createHorizontalGlue());
		controlPanel.add(save);
		controlPanel.add(Box.createHorizontalGlue());
		controlPanel.add(estimate);
		controlPanel.setBorder(BorderFactory.createMatteBorder(10, -1, -1, -1, Color.black));
						
		return (controlPanel);
	}//makeControlPanel
	
		
		
	/**
	*
	* Adds all of the subpanels to the tabbed Pane and returns the tabbed pane.
	*@@return JTabbedPane - with all the SubComponts added.
	*/
	public JTabbedPane makeTabbedPane() {
		JTabbedPane toReturn = new JTabbedPane();
		for (int i =0 ; i < myComponents.length ; i++) { 
			toReturn.addTab(myComponents[i].getName(), (JPanel)myComponents[i]);
		}//for
	
		return (toReturn);
	}//generateTabbedPane


	/**
	* loads to data for the system.  Loads in the data unit and then calls the loads of 
	* each subsystem.  
	*/
	public void load() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Open");
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		ExampleFileFilter filter = new ExampleFileFilter();
    	filter.addExtension(FILE_TYPE);
       	filter.setDescription("Estimator Data");
    	chooser.setFileFilter(filter);
    	int returnVal = chooser.showOpenDialog(myFrame);
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
    		try {
    	 	  	 FileInputStream istream = new FileInputStream(chooser.getSelectedFile().getName());
    	 	  	 ObjectInputStream p = new ObjectInputStream(istream);
			     myData = (EstimationData)p.readObject();

	    		 istream.close();
             }
   		     catch(Exception e){
            	System.out.print("file read ERROR: " + e);
             }
			
						
			int index = myTabbedPane.getSelectedIndex();
			myFrame.getContentPane().removeAll();
			makeComponents();
			myFrame.setContentPane(makeMainPanel());
			myTabbedPane.setSelectedIndex(index);
			
			
			for (int i = 0 ; i < myComponents.length; i++) {
				myComponents[i].load();
				//((JPanel)myComponents[i]).validate();
			}//for
	   	}//if
      	myFrame.validate();
  	}//load


	/**
	* Saves all the data to a file with a .estimator extension.
	*/
	private void save() {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save As");
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension(FILE_TYPE);
            filter.setDescription("Estimator Data");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showSaveDialog(myFrame);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
    		for (int i = 0; i < myComponents.length ; i++) {
    			myComponents[i].save();	
    		}
                String name = chooser.getSelectedFile().getName();
                if (name.indexOf('.') == -1) 
                    name = name + "." + FILE_TYPE;
                myData.save(name);    		
            }//if	    
	}//end save()


	/**
	* This method calls estimate on all the appropriate methods ans generates
	* the results screen.
	*/
	public void estimate () {
		for (int i = 1; i < myComponents.length; i++) {
			if (myComponents[i-1].isAnyFilledIn() == false) {
				myComponents[i].getPrevData();
			}
		}//for
		for (int i = 0; i < myComponents.length; i++) {
			if (myComponents[i].isAnyFilledIn()) {
				if (noError) {
				 	noError = myComponents[i].estimate();
				}
			}//if
		}//for
	}//estimate


	/**
	* This makes the panel which returns the results panel
	*/
	public JPanel makeResultsPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BoxLayout(toReturn,BoxLayout.Y_AXIS));
		for (int i = 0; i < myComponents.length ; i++) {
			JPanel tempPanel = new JPanel();
                        tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.X_AXIS));
			tempPanel.add(myComponents[i].getResultsPanel());
			tempPanel.setBorder(BorderFactory.createMatteBorder(-1, -1, 5, -1, Color.black));
			toReturn.add(tempPanel);
		}//for
                
                double totalHours = 0;
                totalHours = myData.getManHours() + myData.getDesignManHours() + 
                                myData.getImplManMonths() + myData.getTestingTotalHours();
                
                
                
                JPanel tempPanel = new JPanel();
                tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.Y_AXIS));
                tempPanel.add(Box.createVerticalStrut(VERTSPACE));
                tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.X_AXIS));
                
                DecimalFormat myFormat = new DecimalFormat ("0.0");
                
                tempPanel.add(new JLabel("Total Hours: " + myFormat.format(totalHours)));
                toReturn.add(tempPanel);
        
		return (toReturn);
		
	}//makeResultsPanel


	
	/**
	* Handels events
	*/
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		if (cmd.equals(SAVE_BUTTON_LABEL)) {
			if (DEBUG) System.out.println ("save pressed");
			save();
		}
		else if (cmd.equals(LOAD_BUTTON_LABEL)) {
			if (DEBUG) System.out.println ("load pressed");
			load();
			myFrame.validate();
		}
		else if (cmd.equals(ESTIMATE_BUTTON_LABEL)) {
			if (DEBUG) System.out.println ("estimate pressed");
			estimate();
			if (noError) {
				JOptionPane.showMessageDialog(null,makeResultsPanel() ,
					"Results", JOptionPane.INFORMATION_MESSAGE);
			}
			noError = true;
		}	
		else {
			System.err.println ("Error: Unexpected Event");	
		} 
	}//end actionPerformed(ActionEvent)



	public static void main (String argv[]) {
		Estimator myEstiamtor = new Estimator();
	}//main


/**
 * A convenience implementation of FileFilter that filters out
 * all files except for those type extensions that it knows about.
 *
 * Extensions are of the type ".foo", which is typically found on
 * Windows and Unix boxes, but not on Macinthosh. Case is ignored.
 *
 * Example - create a new filter that filerts out all files
 * but gif and jpg image files:
 *
 *     JFileChooser chooser = new JFileChooser();
 *     ExampleFileFilter filter = new ExampleFileFilter(
 *                   new String{"gif", "jpg"}, "JPEG & GIF Images")
 *     chooser.addChoosableFileFilter(filter);
 *     chooser.showOpenDialog(this);
 *
 * @@version 1.9 04/23/99
 * @@author Jeff Dinkins
 */
class ExampleFileFilter extends FileFilter {

    private  String TYPE_UNKNOWN = "Type Unknown";
    private  String HIDDEN_FILE = "Hidden File";

    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    /**
     * Creates a file filter. If no filters are added, then all
     * files are accepted.
     *
     * @@see #addExtension
     */
    public ExampleFileFilter() {
	this.filters = new Hashtable();
    }

    /**
     * Creates a file filter that accepts files with the given extension.
     * Example: new ExampleFileFilter("jpg");
     *
     * @@see #addExtension
     */
    public ExampleFileFilter(String extension) {
	this(extension,null);
    }

    /**
     * Creates a file filter that accepts the given file type.
     * Example: new ExampleFileFilter("jpg", "JPEG Image Images");
     *
     * Note that the "." before the extension is not needed. If
     * provided, it will be ignored.
     *
     * @@see #addExtension
     */
    public ExampleFileFilter(String extension, String description) {
	this();
	if(extension!=null) addExtension(extension);
 	if(description!=null) setDescription(description);
    }

    /**
     * Creates a file filter from the given string array.
     * Example: new ExampleFileFilter(String {"gif", "jpg"});
     *
     * Note that the "." before the extension is not needed adn
     * will be ignored.
     *
     * @@see #addExtension
     */
    public ExampleFileFilter(String[] filters) {
	this(filters, null);
    }

    /**
     * Creates a file filter from the given string array and description.
     * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     *
     * Note that the "." before the extension is not needed and will be ignored.
     *
     * @@see #addExtension
     */
    public ExampleFileFilter(String[] filters, String description) {
	this();
	for (int i = 0; i < filters.length; i++) {
	    // add filters one by one
	    addExtension(filters[i]);
	}
 	if(description!=null) setDescription(description);
    }

    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     * @@see #getExtension
     * @@see FileFilter#accepts
     */
    public boolean accept(File f) {
	if(f != null) {
	    if(f.isDirectory()) {
		return true;
	    }
	    String extension = getExtension(f);
	    if(extension != null && filters.get(getExtension(f)) != null) {
		return true;
	    };
	}
	return false;
    }

    /**
     * Return the extension portion of the file's name .
     *
     * @@see #getExtension
     * @@see FileFilter#accept
     */
     public String getExtension(File f) {
	if(f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
		return filename.substring(i+1).toLowerCase();
	    };
	}
	return null;
    }

    /**
     * Adds a filetype "dot" extension to filter against.
     *
     * For example: the following code will create a filter that filters
     * out all files except those that end in ".jpg" and ".tif":
     *
     *   ExampleFileFilter filter = new ExampleFileFilter();
     *   filter.addExtension("jpg");
     *   filter.addExtension("tif");
     *
     * Note that the "." before the extension is not needed and will be ignored.
     */
    public void addExtension(String extension) {
	if(filters == null) {
	    filters = new Hashtable(5);
	}
	filters.put(extension.toLowerCase(), this);
	fullDescription = null;
    }


    /**
     * Returns the human readable description of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @@see setDescription
     * @@see setExtensionListInDescription
     * @@see isExtensionListInDescription
     * @@see FileFilter#getDescription
     */
    public String getDescription() {
	if(fullDescription == null) {
	    if(description == null || isExtensionListInDescription()) {
 		fullDescription = description==null ? "(" : description + " (";
		// build the description from the extension list
		Enumeration extensions = filters.keys();
		if(extensions != null) {
		    fullDescription += "." + (String) extensions.nextElement();
		    while (extensions.hasMoreElements()) {
			fullDescription += ", " + (String) extensions.nextElement();
		    }
		}
		fullDescription += ")";
	    } else {
		fullDescription = description;
	    }
	}
	return fullDescription;
    }

    /**
     * Sets the human readable description of this filter. For
     * example: filter.setDescription("Gif and JPG Images");
     *
     * @@see setDescription
     * @@see setExtensionListInDescription
     * @@see isExtensionListInDescription
     */
    public void setDescription(String description) {
	this.description = description;
	fullDescription = null;
    }

    /**
     * Determines whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @@see getDescription
     * @@see setDescription
     * @@see isExtensionListInDescription
     */
    public void setExtensionListInDescription(boolean b) {
	useExtensionsInDescription = b;
	fullDescription = null;
    }

    /**
     * Returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @@see getDescription
     * @@see setDescription
     * @@see setExtensionListInDescription
     */
    public boolean isExtensionListInDescription() {
	return useExtensionsInDescription;
    }
}




}//Estiamtor
