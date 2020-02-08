
/**
 * The UI for the Design Estimator.  Graphically displays output 
 * and takes in user input.  The input is used by the 
 * ImplementationDPU to run its estimations.  Similarly, the
 * ImplementationDPU's output is displayed graphically by 
 * this class, ImplementationUI
 * 
 * @@see ImplementationDPU, JPanel, ActionListener
 */

package ImplementationComponent;

import Estimator.*;
import java.io.File;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.filechooser.*;
import javax.swing.*;
import java.awt.event.*; 
import java.awt.BorderLayout; 
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
 
public class ImplementationUI extends JPanel implements ActionListener, SubComponent{
	//------------ Constants ----------------//
	
	private static final boolean DEBUG = false;
	
	private static final String FILE_TYPE = "design";
	
	private static final String LOAD_BUTTON_LABEL = "Load Data";
	private static final String SAVE_BUTTON_LABEL = "Save Data";
	private static final String DESIGN_BUTTON_LABEL = "Enter Design Info";
	private static final String ESTIMATE_BUTTON_LABEL = "Refresh Estimatation";
	
	private static final String SPACER = "         "; /** Used in GUI allignment*/
	private static final int VERTSPACE = 8;
	
	private static final String NORMAL_FONT_TYPE = "Arial";
	private static final int NORMAL_FONT_STYLE = Font.PLAIN;
	private static final int NORMAL_FONT_SIZE = 14;
	private static final int TITLE_FONT_SIZE = 24;
	private static final Color NORMAL_FONT_COLOR = Color.black;
	
	private static final String TESTING_SLIDER_LABEL = "Level of Unit Testing = ";
	private static final String DOCS_SLIDER_LABEL = "Level of Documentation = ";
	
	private static final String [] QUESTIONS = new String[] {
			"Level of Unit Testing to be completed (1-10, 10 is highest level): ",
	 		"Level of Documentation to be completed (1-10, 10 is highest level): ",
	};
	
	private static final String locQ = "Lines of Code *";
	

	
	private static final String [] DESIGN_QUESTIONS = new String [] {
			"How many classes were named in the design process?",
			"How many total methods were named in the design process?"	
	};
	
		
		
	
	//------------ Static Variables ----------//
	
	private static boolean standAlone = false;
	
	//------------ Instance Variables --------//
	
	/** Processing uint that does all the estimation work for the UI*/
	private ImplementationDPU myUnit;
	
	private JPanel mainPanel; /** the overall container Panel*/
	private JPanel resultsPanel; /** holds the results parts of the display */
	private JPanel enterdataPanel; /** holds the enter data parts of the display */
	private JPanel designPanel; /** This holds the information about he requirnemnts */
	
	private JTextField [] artifacts;
        
        private JTextField newLOC;
	
	private JRadioButton [] reuseButtons;
	
	public boolean error = false; /* errors entering data */
	
	public boolean saving = false; /* allows reuse of the estimate code with out generating estimates */
	
			
	//------------ Constructors ------------//
	
	
	
	/**
	 * Constructs the ImplementationUI object taking in an EstimationData
	 * 
	 * @@param none
	 */
	public ImplementationUI(EstimationData data){
		setMyImplementationDPU(new ImplementationDPU(data));
		makeScreen();
	}
	
	/**
	 * Constructs the ImplementationUI object
	 * 
	 * @@param none
	 */
	 
	 
	 
	public ImplementationUI(ImplementationDPU myUnit){
		setMyImplementationDPU(myUnit);
		makeScreen();
		

	}//end ImplementationUI(ImplementationDPU)
	
	
	
	
	//------------ Accessors ------------//
	/**
	 * Accesses the myImplementationDPU variable
	 * 
	 * @@param none
	 * 
	 * @@return ImplementationDPU - The processing 
	 *	unit used by the UI 
	 */
	public ImplementationDPU getMyImplementationDPU(){
		return myUnit;
	}//end getMyImplementationDPU()
	
	//------------ Modifiers -----------//
	
	/**
	 * Sets myImplementationDPU to the ImplementationDPU passed in.
	 * 
	 * @@param ImplementationDPU newUnit - The new 
	 *	ImplementationDPU to be used.
	 */
	public void setMyImplementationDPU (ImplementationDPU newUnit){
		this.myUnit = newUnit;
	}//setMyImplementationDPU(ImplementationDPU)
	
	//------------ Methods -------------//
	
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		if (cmd.equals(SAVE_BUTTON_LABEL)) {
			if (DEBUG) System.out.println ("save pressed");
			save();
		}
		else if (cmd.equals(LOAD_BUTTON_LABEL)) {
			load();
			if (standAlone) {
				mainPanel.remove(designPanel);
				mainPanel.add(designPanel = makeDesignPanel(), BorderLayout.NORTH);
				mainPanel.validate();
			}
			if (DEBUG) System.out.println ("load pressed");
			
						
			validate();
		}
		else if (cmd.equals(ESTIMATE_BUTTON_LABEL)) {
			if (DEBUG) System.out.println ("estimate pressed");
			estimate();
			//myUnit.estimate();
			//makeResultsPanel();
			//validate();
		}
		else if (cmd.equals(DESIGN_BUTTON_LABEL)) {
			if (DEBUG) System.out.println ("design pressed");
			getDesignData();
		}
	}//end actionPerformed(ActionEvent)
	
	
	/**
	* Used to see if the user has started to fill in data for this component.
	*@@return boolean - this is true if the user has started to fill in data
	*/
	public boolean isAnyFilledIn () {
		boolean toReturn = false;
		for (int i = 0; i < artifacts.length ; i++) {
			if (!artifacts[i].getText().trim().equals(""))
				toReturn = true;
		}//for
		return (toReturn);
	}//isAnyFilledIn
	
	
	/**
	* This method is a wrapper for getDesignData to make integration easier.
	*/
	public void getPrevData() {
		getDesignData();
	}//getPrevData

	
	/**
	*
	* This method gets the requirnments data from the user and saves it into the data.
	* It does not update the screen.
	*/
	private void getDesignData() {
		String inputValue;
		for (int i = 0; i < DESIGN_QUESTIONS.length; i++) {
			do {
				inputValue = JOptionPane.showInputDialog(DESIGN_QUESTIONS[i]);
                                if (inputValue == null) {
                                    return;
                                }
                                
			} while (!checkValue(inputValue, DESIGN_QUESTIONS[i], "design"));
			switch (i) {
				case 0: myUnit.getEstimator().setDesignClasses((new Integer(inputValue)).intValue());
						break;
				case 1: myUnit.getEstimator().setDesignMethods((new Integer(inputValue)).intValue());
						break;
				default: System.out.println ("Error in switch statement");
						break;
			}
		}//for
		if (standAlone) {
			mainPanel.remove(designPanel);
			mainPanel.add(designPanel = makeDesignPanel(), BorderLayout.NORTH);
			mainPanel.validate();
		}
	}//getDesignData
	
	
	/**
	 * Saves data accumaleted during user's session to a file. 
	 *
	 * @@param none
	 * 
	 * @@return void
	 */
	public void save(){
		int returnVal = JFileChooser.APPROVE_OPTION;
		JFileChooser chooser = new JFileChooser();
		if (standAlone) {
			chooser.setDialogTitle("Save As");
			chooser.setCurrentDirectory(new File("."));
			ExampleFileFilter filter = new ExampleFileFilter();
    		filter.addExtension("implementation");
       		filter.setDescription("Implementation Data");
    		chooser.setFileFilter(filter);

    	    returnVal = chooser.showSaveDialog(null);
    	}//if
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
		saving = true;
		
    		for (int i = 0; i < artifacts.length; i++) {
			if (checkValue (artifacts[i].getText(),QUESTIONS[i], "questions")) {
				if (artifacts[i].getText().trim().equals(""))
					artifacts[i].setText("-1");
				switch (i) {
				case 0:	myUnit.getEstimator().setLvlTesting(new Integer (artifacts[i].getText()).intValue());

						break; //from case 0
				case 1:     myUnit.getEstimator().setLvlDoc(new Integer (artifacts[i].getText()).intValue()); 

						break; //from case 1
				default:
						System.out.println ("Error in switch statement");
						break; //from default	
			        }//switch
			        if (artifacts[i].getText().equals("-1"))
				       artifacts[i].setText("");
			}
			else {
				error = true;
				break;
				//break; //get out of for loop.
			}
		}//for
                
                if (checkValue(newLOC.getText(),locQ,"design")) {
                    if (newLOC.getText().trim().equals(""))
                        newLOC.setText("-1");
                    myUnit.getEstimator().setLOC(new Integer(newLOC.getText()).intValue());
                    if (newLOC.getText().trim().equals("-1"))
                        newLOC.setText("");
                }
                else {
                    error = true;
                }
		
		if (!error) {
		
 				if (standAlone) {
    				String name = chooser.getSelectedFile().getAbsolutePath();
    				if (name.indexOf('.') == -1) 
    					name = name + "." + "implementation";
                                myUnit.save(name);
    					
    			}
    			

    	}//if
	
	}//iff approve
	saving = false;
}//end save()
	
	/**
	 * Loads data from a file into the user's session.
	 * 
	 * @@param none
	 * 
	 * @@return void
	 */
	public void load(){
		int returnVal;
                String setTo = "";
		JFileChooser chooser = new JFileChooser();
		if (standAlone) {
			chooser.setCurrentDirectory(new File("."));
			ExampleFileFilter filter = new ExampleFileFilter();
    		        filter.addExtension("design");
       		        filter.setDescription("Design Data");
                        chooser.addChoosableFileFilter(filter);
                        filter = new ExampleFileFilter();
    		        filter.addExtension("implementation");
       		        filter.setDescription("Implementation Data");
                        chooser.addChoosableFileFilter(filter);
    		        returnVal = chooser.showOpenDialog(this);           
    		if ((returnVal != JFileChooser.CANCEL_OPTION) && (chooser.getSelectedFile() != null)) {
    			if ((chooser.getSelectedFile().getName().indexOf(".design") == -1) && (chooser.getSelectedFile().getName().indexOf(".implementation") == -1) && (returnVal != JFileChooser.ERROR_OPTION)) {
    				returnVal = JFileChooser.ERROR_OPTION;	
                                JOptionPane.showMessageDialog(null, "Invalid file selection!", "Invalid File", JOptionPane.ERROR_MESSAGE); 
                        }  //if 
    		}	
    	}
    	else {
    		returnVal = JFileChooser.APPROVE_OPTION;	
    	}	
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
    		if (standAlone) {
    			
       	  		myUnit.load(chooser.getSelectedFile().getAbsolutePath());
       	    }
       	    if (standAlone) {  //set Requirnments data
       	    	
       	    	
       	    }
       	    
       	  	for (int i = 0; i < artifacts.length; i++) {
       	  		setTo = "";
 				switch (i) {
 					case 0: 
  							setTo = "" + String.valueOf(myUnit.getEstimator().getLvlTesting());
 							break;
 					case 1: 
 							setTo = "" + String.valueOf(myUnit.getEstimator().getLvlDoc());
 							break;
 					default:  System.out.println ("Error with switch");					
 							break;
  				}//switch
  				if (!setTo.equals("-1")) { //Hack to cut out -1s
  					artifacts[i].setText(setTo);
  				}
  				else {
  					artifacts[i].setText("");
  				}
       	  	}//for
                
                setTo = "";
                setTo = String.valueOf(myUnit.getEstimator().getLinesOfCode());
                if (!setTo.equals("-1")) {
                    newLOC.setText(setTo);
                }
                else {
                    newLOC.setText("");
                }
       	}//if
       	else {
       	}//else
	}//end load()
	
	/**
	 * Call estimate() on the DesignDataProccessingUnit. <p>
	 * Refreshes the view so that the data reflected in the view <p>
	 * is updated to show the data in the ImplementationDPU.
	 * 
	 * This method also calls estimate on the ImplementationDPU 
	 *
	 * @@param none
	 * 
	 * @@return void
	 */
	public boolean estimate(){
		error = false;
                boolean locFilled = false;
                
             if (!newLOC.getText().trim().equals("")) {
                locFilled = true; 
                if (checkValue(newLOC.getText(),locQ, "design")) {
                    myUnit.getEstimator().setLOC(new Integer (newLOC.getText()).intValue());
                }
                else {
                    error = true;
                    return(false);
                }                    
             }


		for (int i = 0; i < artifacts.length; i++) {
			if (checkValue (artifacts[i].getText(),QUESTIONS[i], "questions")) {
				switch (i) {
					case 0:	myUnit.getEstimator().setLvlTesting(new Integer (artifacts[i].getText()).intValue());
							break; //from case 0
					case 1: myUnit.getEstimator().setLvlDoc(new Integer (artifacts[i].getText()).intValue()); 
							break; //from case 1
					default:
							System.out.println ("Error in switch statement");
							break; //from default	
				}//switch				
			}
			else {
				error = true;
				return(false);				
			}
		}//for
		


		if (!saving) {
			if (standAlone) {
				if ((myUnit.getEstimator().getDesignClasses() < 0) || (myUnit.getEstimator().getDesignMethods() < 0)) {
					getDesignData();
				}//if design data not filled in.	
			}
		}




		if (!error) {
			if (!saving) {
				myUnit.estimate(locFilled);			
			
			    /**
			    Refreshes the information about requirnments on the main screen
			    */
			
				if (standAlone) {
					JOptionPane.showMessageDialog(null,makeResultsPanel() ,
					"Results", JOptionPane.INFORMATION_MESSAGE);
				}
			}//!saving
			return(true);
		}//if !error
		return(false);
	}//end estimate()

	
	/**
	*
	*This checks to see if the answer is only a number and also positive.  If
	* it is it returns true.  If not it returns false and displays a dialog 
	* informing the user of the error.
	*/
	private boolean checkValue(String toCheck, String question, String type) {
		int x;
		if (toCheck.trim().equals("") && (!saving)) { // Blank Boxes
			JOptionPane.showMessageDialog(null, 
				"Implementation Component:  An answer to \"" + question + "\" must be entered" , "Error in data", 
				JOptionPane.ERROR_MESSAGE);
			return(false);
		}
		try {
                     if((saving) && (toCheck.trim().equals("")))
                        x = 1;
                     else
			x = Integer.parseInt(toCheck);	
		}//try
		catch (NumberFormatException e) { //Non integer answers
				JOptionPane.showMessageDialog(null, 
					"Implementation Component:  The answer to \"" + question + "\" must be an integer!" , "Error in data", 
					JOptionPane.ERROR_MESSAGE); 
				return(false);
		}//catch
		if ((type == "questions") && (x < 1)) {
			JOptionPane.showMessageDialog(null, 
				"Implementation Component:  The answer to \"" + question + "\" must be between 1 and 10!" , "Error in data", 
				JOptionPane.ERROR_MESSAGE); 			
			return (false);	
		}//if
		if ((type == "questions") && (x > 10)) {
			JOptionPane.showMessageDialog(null, 
				"Implementation Component:  The answer to \"" + question + "\" must be between 1 and 10!" , "Error in data", 
				JOptionPane.ERROR_MESSAGE); 			
			return (false);	
		}//if
		if ((type == "design") && (x < 0)) {
			JOptionPane.showMessageDialog(null, 
				"Implementation Component:  The answer to \"" + question + "\" must be positive!" , "Error in data", 
				JOptionPane.ERROR_MESSAGE); 			
			return (false);	
		}//if
		return(true);
	}//checkValue
	
	/**
	 * Creates the initial user interface screen to be used.
	 * 
	 * @@param none
	 * 
	 * @@return void
	 */
	public void makeScreen(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		if (standAlone) {
			mainPanel.add(designPanel = makeDesignPanel(), BorderLayout.NORTH);
		}//if
		mainPanel.add(makeEnterDataPanel(), BorderLayout.CENTER);
		if (standAlone) {
			mainPanel.add(makeControlPanel(),BorderLayout.SOUTH);
		}
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
	}//makeScreen()


	/**
	* This method returns a panel which displays what the data is that
	* is important to estimations of the implementation process.
	*/
	private JPanel makeDesignPanel() {
		JPanel designPanel = new JPanel();
		designPanel.setLayout(new BoxLayout(designPanel,BoxLayout.Y_AXIS));
		JLabel titleReq = new JLabel("Data from the Design process: ");
		titleReq.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,TITLE_FONT_SIZE));
		titleReq.setForeground(NORMAL_FONT_COLOR);
		designPanel.add(titleReq);
		
		JLabel numClasses = new JLabel(SPACER + "Total number of classes is " + getDesignClasses());
		numClasses.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		numClasses.setForeground(NORMAL_FONT_COLOR);
		
		JLabel numMethods = new JLabel (SPACER + "Total number of methods is " + getDesignMethods());
		numMethods.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		numMethods.setForeground(NORMAL_FONT_COLOR);
		
		designPanel.add(numClasses);
		designPanel.add(Box.createVerticalStrut(VERTSPACE));
		designPanel.add(numMethods);
		
		designPanel.setBorder(BorderFactory.createMatteBorder(-1, -1, 5, -1, Color.black));
		
		return (designPanel);
	}//makeDesignPanel
        
        
        private String getDesignClasses() {
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getDesignClasses()).intValue() != -1) {
			toReturn = "" + new Double(myUnit.getEstimator().getDesignClasses()).intValue() + ".";
		}
		return (toReturn);
	}
		
	private String getDesignMethods() {
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getDesignMethods()).intValue() != -1) {
			toReturn = "" + new Double(myUnit.getEstimator().getDesignMethods()).intValue() + ".";
		}
		return (toReturn);
	}


	/**
	*	This method is a wrapper for the makeResultsPanel() method to make integration smoother
	*/	
	public JPanel getResultsPanel() {
		return(makeResultsPanel());	
	}
	




	/**
	*
	* This method refresehs the labels on the Results Panel if it
	* already exisits.  If it does not exist, it creats the panel
	* and adds the label for the first time.
	*
	*@@param none
	*@@return JPanel - the resultsPanel
	*/
	public JPanel makeResultsPanel() {
            
            int manhours;

		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
		
		if (standAlone) {
			resultsPanel.add(makeDesignPanel()); /**Adds the design stuff */
		}//if
		
		
		
	
		JLabel title = new JLabel ("Estimates of Implementation process:");
		title.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,TITLE_FONT_SIZE));
		title.setForeground(NORMAL_FONT_COLOR);
		
		JLabel loc = new JLabel (SPACER + "Estimated Lines of Code is " + getLOC());
		loc.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		loc.setForeground(NORMAL_FONT_COLOR);
		
                try {
                manhours = Integer.parseInt(getManMonths());
                } catch (Exception e) { manhours = 0; }
                
                JLabel manMos = new JLabel (SPACER + "Estimated hours spent on Implementation " + getManMonths());
                JLabel note = new JLabel ("");
                
                if (manhours < 1520) {
                    manMos = new JLabel (SPACER + "Estimated hours spent on Implementation " + getManMonths() + " *");
                    note = new JLabel (SPACER + "*This estimate is reliable only when the total hours spent on implementation exceeds 1520.");
                }
                
		manMos.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		manMos.setForeground(NORMAL_FONT_COLOR);
		
		JLabel bugs = new JLabel (SPACER + "Estimated # Bugs per 1000 Lines of Code " + getBugs());
		bugs.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		bugs.setForeground(NORMAL_FONT_COLOR);
		
		resultsPanel.add(title);
		resultsPanel.add(loc);
		resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
		resultsPanel.add(manMos);
		resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
		resultsPanel.add(bugs);
                resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
                resultsPanel.add(note);

		/* if (standAlone) {
			resultsPanel.add(Box.createVerticalStrut(new Long(Math.round(VERTSPACE -(.5 * VERTSPACE))).intValue()));
			resultsPanel.setBorder(BorderFactory.createMatteBorder(-1, -1, 10, -1, Color.black));
		}
		*/
		return (resultsPanel); 
	}//makeResultsPanel
	
        
        
        private String getBugs(){
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getBugs()).intValue() != -1) {
			toReturn = "" + new Double(myUnit.getEstimator().getBugs()).intValue();
		}
		return (toReturn);
	}	
	
	private String getManMonths(){
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getImplManMonths()).intValue() != -1) {
			toReturn = "" + new Double(myUnit.getEstimator().getImplManMonths()).intValue();
		}
		return (toReturn);
	}
	
	private String getLOC(){
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getLinesOfCode()).intValue() != -1) {
			toReturn = "" + new Double(myUnit.getEstimator().getLinesOfCode()).intValue();
		}
		return (toReturn);
	}
        
        
        
	
	/**
	*
	* Makes the panel which holds all the compoents related to doing the estimations.
	*@@param - none
	*@@return the estimator panel.
	*/
	private JPanel makeEnterDataPanel() {
		JPanel enterData = new JPanel();
		enterData.setLayout(new BoxLayout(enterData,BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel ("Data for the Implementation process:");
		title.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,TITLE_FONT_SIZE));
		title.setForeground(NORMAL_FONT_COLOR);
		JPanel titleRow = new JPanel();
		titleRow.setLayout(new FlowLayout(FlowLayout.LEFT));
		titleRow.add(title);
		enterData.add(titleRow);
		
		artifacts = new JTextField [QUESTIONS.length];
		
		/*
			This for loop, goes through all the questions adding a JTextArea
			for each and then also puts the question on the display with the 
			text area
		*/
		
		for (int i = 0;i < artifacts.length;i++) {
			artifacts[i] = new JTextField(3);
			artifacts[i].setName(QUESTIONS[i]);
			
			JPanel row = new JPanel();
			row.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			JLabel ques = new JLabel(QUESTIONS[i]);
			ques.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
			ques.setForeground(NORMAL_FONT_COLOR);
			
			row.add(ques);
			row.add(artifacts[i]);
			
			enterData.add(row);
		}//for		
		
                JPanel row = new JPanel();
		row.setLayout(new FlowLayout(FlowLayout.LEFT));
                newLOC = new JTextField(7);
                newLOC.setName(locQ);
                JLabel locLabel = new JLabel(locQ);
                locLabel.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
                locLabel.setForeground(NORMAL_FONT_COLOR);
                
                row.add(locLabel);
                row.add(newLOC);
                
                enterData.add(row);
                
                
                row = new JPanel();
		row.setLayout(new FlowLayout(FlowLayout.LEFT));
                String locNote = "* If you wish to use your own value for estimated lines of code, enter it here.  To have the Estimator calculate this for you, leave this blank.";
                JLabel locNoteLabel = new JLabel(locNote);
                row.add(locNoteLabel);
                
                enterData.add(row);

		enterData.add(Box.createVerticalStrut(800)); /*Forces proper spacing */
		return(enterData);
	}//makeEnterDataPanel()
	

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
		JButton design = new JButton (DESIGN_BUTTON_LABEL);
		design.addActionListener(this);		
		JButton estimate = new JButton (ESTIMATE_BUTTON_LABEL);
		estimate.addActionListener(this);
		
		//add the buttons
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.X_AXIS));
		if (standAlone) {
			controlPanel.add(load);
			controlPanel.add(Box.createHorizontalGlue());
			controlPanel.add(save);
			controlPanel.add(Box.createHorizontalGlue());
			controlPanel.add(design);
			controlPanel.add(Box.createHorizontalGlue());
			controlPanel.add(estimate);
		}
		else {
			JButton next = new JButton ("Next");
			next.addActionListener(this);
			JButton back = new JButton ("Back");
			back.addActionListener(this);
			controlPanel.add(back);
			controlPanel.add(Box.createHorizontalGlue());
			controlPanel.add(next);
		}	
		controlPanel.setBorder(BorderFactory.createMatteBorder(10, -1, -1, -1, Color.black));
						
		return (controlPanel);
	}//makeControlPanel

		
	//------------ Main ----------------//
	
	/**
	 * Starts the Design Component.
	 * @@param String[] argv - list of argument 
	 * @@return void
	 */
	 
	 
	public static void main(String[] argv){
		ImplementationUI.standAlone = true;
		ImplementationUI myImplementationUI = new ImplementationUI (new ImplementationDPU());
		JFrame myFrame = new JFrame ("Implementation Component");
		myFrame.addWindowListener( new WindowAdapter() {
      		public void windowClosing(WindowEvent e) {
       	 		System.exit(0);
      		}
    	});
		myFrame.getContentPane().add(myImplementationUI);
		myFrame.setSize(800,600);
		myFrame.setVisible(true);
		
		if (DEBUG) {
			System.out.println(myImplementationUI.isAnyFilledIn());	
		}
	}//end main(String[] argv)




/* Below here is code which is from the swing demo which ships with JDK.  
	It does the file filtering.
*/	




/*
 * @@(#)ExampleFileFilter.java	1.9 99/04/23
 *
 * Copyright (c) 1998, 1999 by Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */




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


	
	
}//end ImplementationUI