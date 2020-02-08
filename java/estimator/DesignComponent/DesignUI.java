/*-
 * Classname DesingComponent
 * 1.0
 * 10/21/2001
 * Don't steal my code.
 * author: Chris Morris (chrismorris@acm.org)
 */




/**
 * The UI for the Design Estimator.  Graphically displays output
 * and takes in user input.  The input is used by the
 * DesignDataProcessingUnit to run its estimations.  Similarly, the
 * DesignDataProcessingUnit's output is displayed graphically by
 * this class, DesignUI
 *
 * @see DesignDataProcessingUnit, JPanel, ActionListener
 */

package DesignComponent;

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

public class DesignUI extends JPanel implements ActionListener, SubComponent{
	//------------ Constants ----------------//

	private static final boolean DEBUG = false;

	private static final String FILE_TYPE = "design";

	private static final String LOAD_BUTTON_LABEL = "Load Data";
	private static final String SAVE_BUTTON_LABEL = "Save Data";
	private static final String REQ_BUTTON_LABEL = "Enter Requirements Info";
	private static final String ESTIMATE_BUTTON_LABEL = "Estimate";

	private static final String SPACER = "         "; /** Used in GUI allignment*/
	private static final int VERTSPACE = 8;

	private static final String NORMAL_FONT_TYPE = "Arial";
	private static final int NORMAL_FONT_STYLE = Font.PLAIN;
	private static final int NORMAL_FONT_SIZE = 14;
	private static final int TITLE_FONT_SIZE = 24;
	private static final Color NORMAL_FONT_COLOR = Color.black;

	private static final String NUM_CLASS_DIAGRAMS = "Number of class diagrams: ";
	private static final String NUM_SEQUENCE_DIAGRAMS = "Number of sequence diagrams: ";
	private static final String NUM_STATE_DIAGRAMS = "Number of state diagrams: ";
	private static final String NUM_ARCH_DIAGRAMS = "Number of high level architecture diagrams: ";
	private static final String NUM_SCREEN_SHOTS = "Number of screen shots: ";

	private static final String [] QUESTIONS = new String[] {
			"Number of class diagrams: ",
	 		"Number of sequence diagrams: ",
	 		"Number of state diagrams: ",
	 		"Number of high level architecture diagrams: ",
	 		"Number of screen shots: ",
	};

	private static final String REUSE_PROMPT = "Select the description that is closest to the system you are designing:";

	private static final String [] REUSE_QUESTIONS = new String [] {
			"Starting from scratch",
			"Upgrading an existing system",
			"Recreating an existing system",
	};

	private static final String [] REQ_QUESTIONS = new String [] {
			"How many candidate classes were named in the requirements process?",
			"How many man hours were spent on the requirments process?"
	};


	private static final int REUSED_START_SELECTED = 0;


	//------------ Static Variables ----------//

	private static boolean standAlone = false;

	//------------ Instance Variables --------//

	/** Processing uint that does all the estimation work for the UI*/
	private DesignDataProcessingUnit myUnit;

	private JPanel mainPanel; /** the overall container Panel*/
	private JPanel resultsPanel; /** holds the results parts of the display */
	private JPanel enterdataPanel; /** holds the enter data parts of the display */
	private JPanel reqPanel; /** This holds the information about he requirnemnts */

	private JTextField [] artifacts;

	private JRadioButton [] reuseButtons;

	public boolean error = false; /* errors entering data */

	public boolean saving = false; /* allows reuse of the estimate code with out generating estimates */


	//------------ Constructors ------------//



	/**
	 * Constructs the DesignUI object taking in an EstimationData
	 *
	 * @param none
	 */
	public DesignUI(EstimationData data){
		setMyDataProcessingUnit(new DesignDataProcessingUnit(data));
		makeScreen();
	}

	/**
	 * Constructs the DesignUI object
	 *
	 * @param none
	 */



	public DesignUI(DesignDataProcessingUnit myUnit){
		//myParent = myView;
		setMyDataProcessingUnit(myUnit);
		makeScreen();


	}//end DesignUI(DesignDataProcessingUnit)




	//------------ Accessors ------------//
	/**
	 * Accesses the myDataProcessingUnit variable
	 *
	 * @param none
	 *
	 * @return DesignDataProcessingUnit - The processing
	 *	unit used by the UI
	 */
	public DesignDataProcessingUnit getMyDataProcessingUnit(){
		return myUnit;
	}//end getMyDesignDataProcessingUnit()

	//------------ Modifiers -----------//

	/**
	 * Sets myDataProcessingUnit to the DesignDataProcessingUnit passed in.
	 *
	 * @param DesignDataProcessingUnit newUnit - The new
	 *	DesignDataProcessingUnit to be used.
	 */
	public void setMyDataProcessingUnit (DesignDataProcessingUnit newUnit){
		this.myUnit = newUnit;
	}//setMyDataProcessingUnit(DesignDataProcessingUnit)

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
				//System.out.println ("woah!");
				mainPanel.remove(reqPanel);
				mainPanel.add(reqPanel = makeReqPanel(), BorderLayout.NORTH);
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
		else if (cmd.equals(REQ_BUTTON_LABEL)) {
			if (DEBUG) System.out.println ("req pressed");
			getReqsData();
			//makeResultsPanel();
		}
		else if (cmd.equals("Next")) {
			/*
			myParent.shiftViewForward();
			*/
		}
		else if (cmd.equals("Back")) {
			/*
			myParent.shiftViewBackward();
			*/
		}
	}//end actionPerformed(ActionEvent)


	/**
	* Used to see if the user has started to fill in data for this component.
	*@return boolean - this is true if the user has started to fill in data
	*/
	public boolean isAnyFilledIn () {
		boolean toReturn = false;
		for (int i = 0; i < artifacts.length ; i++) {
			if (!artifacts[i].getText().trim().equals(""))
				toReturn = true;
			if 	(!reuseButtons[0].isSelected())
				toReturn =true;
		}//for
		return (toReturn);
	}//isAnyFilledIn


	/**
	* This method is a wrapper for getReqsData to make integration easier.
	*/
	public void getPrevData() {
		getReqsData();
	}//getPrevData


	/**
	*
	* This method gets the requirnments data from the user and saves it into the data.
	* It does not update the screen.
	*/
	private void getReqsData() {
		String inputValue;
		for (int i = 0; i < REQ_QUESTIONS.length; i++) {
			do {
				inputValue = JOptionPane.showInputDialog(REQ_QUESTIONS[i]);
			} while (!checkValue(inputValue, REQ_QUESTIONS[i]) && (inputValue != null));
                        if (inputValue != null) {
			switch (i) {
				case 0: myUnit.getEstimator().setCandidateClasses((new Integer(inputValue)).doubleValue());
						break;
				case 1: myUnit.getEstimator().setRequirementsManHours((new Integer(inputValue)).doubleValue());
						break;
				default: System.out.println ("Error in switch statement");
						break;
			}
                        }
		}//for
		if (standAlone) {
			mainPanel.remove(reqPanel);
			mainPanel.add(reqPanel = makeReqPanel(), BorderLayout.NORTH);
			mainPanel.validate();
		}
	}//getReqsData


	/**
	 * Saves data accumaleted during user's session to a file.
	 *
	 * @param none
	 *
	 * @return void
	 */
	public void save(){
		int returnVal = JFileChooser.APPROVE_OPTION;
		JFileChooser chooser = new JFileChooser();
		if (standAlone) {
			chooser.setDialogTitle("Save As");
			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			ExampleFileFilter filter = new ExampleFileFilter();
    		filter.addExtension(FILE_TYPE);
       		filter.setDescription("Design Data");
    		chooser.setFileFilter(filter);

    	    returnVal = chooser.showSaveDialog(null);
    	}//if
    	if(returnVal == JFileChooser.APPROVE_OPTION) {

		saving = true;

    		for (int i = 0; i < artifacts.length; i++) {
			if (checkValue (artifacts[i].getText(),QUESTIONS[i])) {
				if (artifacts[i].getText().trim().equals(""))
					artifacts[i].setText("-1");
				switch (i) {
					case 0:	myUnit.getEstimator().setClassDiag(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 0
					case 1: myUnit.getEstimator().setSeqDiag(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 1
					case 2: myUnit.getEstimator().setStateDiag(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 2
					case 3: myUnit.getEstimator().setHLArchModels(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 3
					case 4: myUnit.getEstimator().setScreenShots(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 4
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

		if (!error) {
			int selected = 0;
			for (int i = 0; i < reuseButtons.length ; i++) {
				if (reuseButtons[i].isSelected()) {
					selected = i;
				}//if
			}//for


			switch (selected) {
				case 0: myUnit.getEstimator().setReuse(.10);
						break;
				case 1:	myUnit.getEstimator().setReuse(.20);
						break;
				case 2:	myUnit.getEstimator().setReuse(.25);
						break;
				default:	System.out.println ("Error in switch statement");
						break;
			}//switch




 				if (standAlone) {
    				String name = chooser.getSelectedFile().getName();
    				if (name.indexOf('.') == -1)
    					name = name + "." + FILE_TYPE;
    				myUnit.save(name);


    			}


    	}//if

	}//iff approve
	saving = false;
}//end save()

	/**
	 * Loads data from a file into the user's session.
	 *
	 * @param none
	 *
	 * @return void
	 */
	public void load(){
		int returnVal;
		JFileChooser chooser = new JFileChooser();
		if (standAlone) {
			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			ExampleFileFilter filter = new ExampleFileFilter();
    		filter.addExtension(FILE_TYPE);
       		filter.setDescription("Design Component Data");
    		chooser.setFileFilter(filter);
    		returnVal = chooser.showOpenDialog(this);
    		if (chooser.getSelectedFile() != null) {
    			if ((chooser.getSelectedFile().getName().indexOf("."+FILE_TYPE)) == -1) {
    				returnVal = JFileChooser.ERROR_OPTION;
    			}//if
    		}
    	}
    	else {
    		returnVal = JFileChooser.APPROVE_OPTION;
    	}
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
    		if (standAlone) {

       	  		myUnit.load(chooser.getSelectedFile().getName());
       	    }
       	    if (standAlone) {  //set Requirnments data


       	    }

       	  	for (int i = 0; i < artifacts.length; i++) {
       	  		String setTo = "";
 				switch (i) {
 					case 0:
  							setTo = "" + new Double(myUnit.getEstimator().getClassDiag()).intValue();
 							break;
 					case 1:
 							setTo = "" + new Double(myUnit.getEstimator().getSeqDiag()).intValue();
 							break;
 					case 2:
 							setTo = "" + new Double(myUnit.getEstimator().getStateDiag()).intValue();
 							break;
 					case 3:
 							setTo = "" + new Double(myUnit.getEstimator().getHLArchModels()).intValue();
 							break;
 					case 4:
 							setTo = "" + new Double(myUnit.getEstimator().getScreenShots()).intValue();
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
  				if (DEBUG) System.out.println (setTo);
  				double reuse = myUnit.getEstimator().getReuse();
  				for (int j=0; j < reuseButtons.length ; j++)
  					reuseButtons[j].setSelected(false); /*Undoes any current selections*/
  				if (reuse == .10) {
  					reuseButtons[0].setSelected(true);
  				}
  				else if (reuse == .20) {
  					reuseButtons[1].setSelected(true);
  				}
  				else if (reuse == .25) {
  					reuseButtons[2].setSelected(true);
  				}
       	  	}//for
       	}//if
       	else {
       		if (returnVal != JFileChooser.CANCEL_OPTION)
       			JOptionPane.showMessageDialog(null, "Invalid file selection!", "Invliad File", JOptionPane.ERROR_MESSAGE);
       	}//else
	}//end load()

	/**
	 * Call estimate() on the DesignDataProccessingUnit. <p>
	 * Refreshes the view so that the data reflected in the view <p>
	 * is updated to show the data in the DesignDataProcessingUnit.
	 *
	 * This method also calls estimate on the DesignDataProcessingUnit
	 *
	 * @param none
	 *
	 * @return void
	 */
	public boolean estimate(){
		error = false;
		if (!saving) {
			if (standAlone) {
				if (myUnit.getEstimator().getCandidateClasses() < 0 || myUnit.getEstimator().getRequirementsManHours() < 0) {
					getReqsData();
				}//if requirnments not filled in.
			}
		}
		for (int i = 0; i < artifacts.length; i++) {
			if (checkValue (artifacts[i].getText(),QUESTIONS[i])) {
				switch (i) {
					case 0:	myUnit.getEstimator().setClassDiag(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 0
					case 1: myUnit.getEstimator().setSeqDiag(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 1
					case 2: myUnit.getEstimator().setStateDiag(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 2
					case 3: myUnit.getEstimator().setHLArchModels(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 3
					case 4: myUnit.getEstimator().setScreenShots(new Double (artifacts[i].getText()).doubleValue());
							break; //from case 4
					default:
							System.out.println ("Error in switch statement");
							break; //from default
				}//switch

			}
			else {
				error = true;
				//return(false);
				break; //get out of for loop.
			}
		}//for

		if (!error) {
			int selected = 0;
			for (int i = 0; i < reuseButtons.length ; i++) {
				if (reuseButtons[i].isSelected()) {
					selected = i;
				}//if
			}//for


			switch (selected) {
				case 0: myUnit.getEstimator().setReuse(.10);
						break;
				case 1:	myUnit.getEstimator().setReuse(.20);
						break;
				case 2:	myUnit.getEstimator().setReuse(.25);
						break;
				default:	System.out.println ("Error in switch statement");
						break;
			}//switch

			if (!saving) {
				myUnit.estimate();


			/**
			Refreshes the information about requirnments on the main screen
			*/

				if (standAlone) {
					JOptionPane.showMessageDialog(null,makeResultsPanel() ,
					"Results", JOptionPane.INFORMATION_MESSAGE);
				}
			}//!saving
			return(!error);
		}//if !error
		return(false);
	}//end estimate()

	/**
	*
	*This checks to see if the answer is only a number and also positive.  If
	* it is it returns true.  If not it returns false and displays a dialog
	* informing the user of the error.
	*/
	private boolean checkValue(String toCheck, String question) {
		if (toCheck != null) {
                    int x;
                    if (!saving) {
                            if (toCheck.equals("")) { // Blank Boxes
                                    JOptionPane.showMessageDialog(null,
                                            "An answer to Design's \"" + question + "\" must be entered" , "Error in data",
                                    JOptionPane.ERROR_MESSAGE);
                            return(false);
                            }
                    }
                    try {
                            if (((saving) && (toCheck.trim().equals("")))) {
                                        x = 3;
                            }
                            else {
                                    x = Integer.parseInt(toCheck);
                            }
                    }//try
                    catch (NumberFormatException e) { //Non integer answers
                                    JOptionPane.showMessageDialog(null,
                                            "The answer to Design's \"" + question + "\" must be an integer!" , "Error in data",
                                            JOptionPane.ERROR_MESSAGE);
                                    return(false);
		}//catch
		if (x < 0) {
			JOptionPane.showMessageDialog(null,
				"The answer to Design's \"" + question + "\" must be a positive integer!" , "Error in data",
				JOptionPane.ERROR_MESSAGE);
			return (false);
		}//if

			return(true);
	}
        return(true);


	}//checkValue

	/**
	 * Creates the initial user interface screen to be used.
	 *
	 * @param none
	 *
	 * @return void
	 */
	public void makeScreen(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		if (standAlone) {
			mainPanel.add(reqPanel = makeReqPanel(), BorderLayout.NORTH);
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
	* is important to estimations of the dising process.
	*/
	private JPanel makeReqPanel() {
		JPanel reqPanel = new JPanel();
		reqPanel.setLayout(new BoxLayout(reqPanel,BoxLayout.Y_AXIS));
		JLabel titleReq = new JLabel ("Data from the Requirements process: ");
		titleReq.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,TITLE_FONT_SIZE));
		titleReq.setForeground(NORMAL_FONT_COLOR);
		reqPanel.add(titleReq);

		JLabel candClasses = new JLabel (SPACER + "Total number of candidate classes is " + getCandidateClasses());
		candClasses.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		candClasses.setForeground(NORMAL_FONT_COLOR);

		JLabel reqHours = new JLabel (SPACER + "Total number of man hours spent on the requirements is " + getRequirnmentsManHours());
		reqHours.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		reqHours.setForeground(NORMAL_FONT_COLOR);

		reqPanel.add(candClasses);
		reqPanel.add(Box.createVerticalStrut(VERTSPACE));
		reqPanel.add(reqHours);

		reqPanel.setBorder(BorderFactory.createMatteBorder(-1, -1, 5, -1, Color.black));

		return (reqPanel);
	}//makeReqPanel


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
	*@param none
	*@return JPanel - the resultsPanel
	*/
	public JPanel makeResultsPanel() {
		/*if (resultsPanel == null) {
			resultsPanel = new JPanel();
			resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
		}// if (resultsPanel == null)
		resultsPanel.removeAll();
		*/
		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));

		if (standAlone) {
			resultsPanel.add(makeReqPanel()); /**Adds the requirnments stuff */
		}//if




		JLabel title = new JLabel ("Estimates of deisgn process:");
		title.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,TITLE_FONT_SIZE));
		title.setForeground(NORMAL_FONT_COLOR);

		JLabel manHours = new JLabel (SPACER + "Total number of man hours spent on designing is " + getManHours());
		manHours.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		manHours.setForeground(NORMAL_FONT_COLOR);


		JLabel realClasses = new JLabel (SPACER + "Number of classes needed to implement the design is " + getClasses());
		realClasses.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		realClasses.setForeground(NORMAL_FONT_COLOR);

		JLabel funcs = new JLabel (SPACER + "Average number of functions per class is " + getFuncs());
		funcs.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		funcs.setForeground(NORMAL_FONT_COLOR);


		JLabel reuse = new JLabel (SPACER + "Percentage of the design which incorporates old code is " + getReuse());
		reuse.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		reuse.setForeground(Color.black);


		resultsPanel.add(title);
		resultsPanel.add(manHours);
		resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
		resultsPanel.add(realClasses);
		resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
		resultsPanel.add(funcs);
		resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
		resultsPanel.add(reuse);
		/* if (standAlone) {
			resultsPanel.add(Box.createVerticalStrut(new Long(Math.round(VERTSPACE -(.5 * VERTSPACE))).intValue()));
			resultsPanel.setBorder(BorderFactory.createMatteBorder(-1, -1, 10, -1, Color.black));
		}
		*/
		return (resultsPanel);
	}//makeResultsPanel

	/**
	* Gets the current number of manhours from the data processing unit, and returns it as a string.
	*@param
	*@return String - number of man hours or "unestimated"
	*/
	private String getManHours() {
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getDesignManHours()).intValue() > -1) {
			double temp = (new Double(myUnit.getEstimator().getDesignManHours())).doubleValue();
			DecimalFormat myFormat = new DecimalFormat ("0.0");
			toReturn = "" + myFormat.format(temp);
		}//if
		return (toReturn);
	}//getManHours

	/**
	* Gets the current number of functions from the data processing unit, and returns it as a string.
	*@param
	*@return String - number of man hours or "unestimated"
	*/
	private String getFuncs() {
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getClassFuncs()).intValue() > -1) {
			toReturn = "" + new Double(myUnit.getEstimator().getClassFuncs()).intValue() + ".";
		}//if
		return (toReturn);
	}//getFuncs

	/**
	* Gets the current percentage of reuse from the data processing unit, and returns it as a string.
	*@param
	*@return String - number of man hours or "unestimated"
	*/
	private String getReuse() {
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getReuse()).intValue()*100 > -1 ) {
			toReturn = "" + (new Double(myUnit.getEstimator().getReuse())).doubleValue()*100 + "%.";
		}//if
		return (toReturn);
	}//getReuse

	/**
	* Gets the current number of classes from the data processing unit, and returns it as a string.
	*@param
	*@return String - number of man hours or "unestimated"
	*/
	private String getClasses() {
		String toReturn = "unestimated.";
		if (new Double(myUnit.getEstimator().getClassDiag()).intValue() > -1 ) {
			toReturn = "" + new Double(myUnit.getEstimator().getClassDiag()).intValue() + ".";
		}//if
		return (toReturn);
	}//getClasses


	/**
	* Used to get the current number of candidate classes.
	*/
	private String getCandidateClasses() {
		String toReturn = "not entered.";
		if (new Double(myUnit.getEstimator().getCandidateClasses()).intValue() > -1 ) {
			toReturn = "" + new Double(myUnit.getEstimator().getCandidateClasses()).intValue() + ".";
		}//if
		return (toReturn);
	}//getCandidateClasses


	/**
	* Used to get the total number of hours spent on the Requirnments.
	*/
	private String getRequirnmentsManHours() {
		String toReturn = "not entered.";
		if (new Double(myUnit.getEstimator().getRequirementsManHours()).intValue() > -1) {
			toReturn = "" + new Double(myUnit.getEstimator().getRequirementsManHours()).intValue() + ".";
		}//if
		return (toReturn);
	}//getRequirnmentsManHours


	/**
	*
	* Makes the panel which holds all the compoents related to doing the estimations.
	*@param - none
	*@return the estimator panel.
	*/
	private JPanel makeEnterDataPanel() {
		JPanel enterData = new JPanel();
		enterData.setLayout(new BoxLayout(enterData,BoxLayout.Y_AXIS));

		JLabel title = new JLabel ("Data about the design process:");
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
			artifacts[i] = new JTextField(8);
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

		/*
		* This section of code adds the reuse buttons.
		*
		*/


		reuseButtons = new JRadioButton [REUSE_QUESTIONS.length];
		ButtonGroup reuseButtonGroup = new ButtonGroup();

		for (int i = 0; i < REUSE_QUESTIONS.length; i++) {
			reuseButtons[i] = new JRadioButton(REUSE_QUESTIONS[i]);
			reuseButtons[i].setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
			reuseButtonGroup.add(reuseButtons[i]);
			if (i == REUSED_START_SELECTED)
				reuseButtons[i].setSelected(true);
			JPanel row = new JPanel();
			row.setLayout(new FlowLayout(FlowLayout.LEFT));
			if (i == 0) {		/** Adds title for group before first reuse button **/
				JLabel reuseTitle = new JLabel(REUSE_PROMPT);
				reuseTitle.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
				reuseTitle.setForeground(Color.black);
				row.add(reuseTitle);
				enterData.add(row);
				row = new JPanel();  /** Makes a new instance of row for outside loop world **/
				row.setLayout(new FlowLayout(FlowLayout.LEFT));
			}//if
			row.add(new JLabel(SPACER));
			row.add(reuseButtons[i]);
			enterData.add(row);
		}//for

		//enterData.add(buttons);
		enterData.add(Box.createVerticalStrut(800)); /*Forces proper spacing */
		return(enterData);
	}//makeEnterDataPanel()


	/**
	*
	* Creates the panel which has the save, load, and estimate buttons.
	*@param none
	*@return JPanel - the control panel
	*/
	private JPanel makeControlPanel () {
                //Make the buttons
		JButton load = new JButton (LOAD_BUTTON_LABEL);
		load.addActionListener(this);
		JButton save = new JButton (SAVE_BUTTON_LABEL);
		save.addActionListener(this);
		JButton req = new JButton (REQ_BUTTON_LABEL);
		req.addActionListener(this);
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
			controlPanel.add(req);
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
	 * @param String[] argv - list of argument
	 * @return void
	 */


	public static void main(String[] argv){
		DesignUI.standAlone = true;
		DesignUI myDesignUI = new DesignUI (new DesignDataProcessingUnit(-1,-1));
		//DesignUI myDesignUI = new DesignUI (new EstimationData());
		JFrame myFrame = new JFrame ("Design Component");
		myFrame.addWindowListener( new WindowAdapter() {
      		public void windowClosing(WindowEvent e) {
       	 		System.exit(0);
      		}
    	});
		myFrame.getContentPane().add(myDesignUI);
		myFrame.setSize(800,600);
		myFrame.setVisible(true);

		if (DEBUG) {
			System.out.println(myDesignUI.isAnyFilledIn());
		}
	}//end main(String[] argv)




/* Below here is code which is from the swing demo which ships with JDK.
	It does the file filtering.
*/




/*
 * @(#)ExampleFileFilter.java	1.9 99/04/23
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
 * @version 1.9 04/23/99
 * @author Jeff Dinkins
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
     * @see #addExtension
     */
    public ExampleFileFilter() {
	this.filters = new Hashtable();
    }

    /**
     * Creates a file filter that accepts files with the given extension.
     * Example: new ExampleFileFilter("jpg");
     *
     * @see #addExtension
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
     * @see #addExtension
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
     * @see #addExtension
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
     * @see #addExtension
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
     * @see #getExtension
     * @see FileFilter#accepts
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
     * @see #getExtension
     * @see FileFilter#accept
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
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     * @see FileFilter#getDescription
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
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
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
     * @see getDescription
     * @see setDescription
     * @see isExtensionListInDescription
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
     * @see getDescription
     * @see setDescription
     * @see setExtensionListInDescription
     */
    public boolean isExtensionListInDescription() {
	return useExtensionsInDescription;
    }
}




}//end DesignUI