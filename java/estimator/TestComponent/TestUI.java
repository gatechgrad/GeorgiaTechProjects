
/**
 * The UI for the Testing Estimator.  Graphically displays output
 * and takes in user input.  The input is used by the
 * TestDataProcessingUnit to run its estimations.  Similarly, the
 * TestDataProcessingUnit's output is displayed graphically by
 * this class, TestUI
 *
 * @see TestDataProcessingUnit, JPanel, ActionListener
 */

package TestComponent;

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

public class TestUI extends JPanel implements ActionListener, SubComponent, ItemListener{
    //------------ Constants ----------------//
    
    private static final boolean DEBUG = false;
    
    private static final String FILE_TYPE = "Test";
    
    private static final String LOAD_BUTTON_LABEL = "Load Data";
    private static final String SAVE_BUTTON_LABEL = "Save Data";
    private static final String IMP_BUTTON_LABEL = "Enter Implementation Info";
    private static final String ESTIMATE_BUTTON_LABEL = "Refresh Estimation";
    
    private static final String SPACER = "         "; /** Used in GUI allignment*/
    private static final int VERTSPACE = 8;
    private static final String VERTSPACE2 = "";
    
    private static final String NORMAL_FONT_TYPE = "Arial";
    private static final int NORMAL_FONT_STYLE = Font.PLAIN;
    private static final int NORMAL_FONT_SIZE = 14;
    private static final int TITLE_FONT_SIZE = 24;
    private static final Color NORMAL_FONT_COLOR = Color.black;
    
    private static final String NUM_TEST_CASES_CREATED = "Number of Test Cases Created: ";
    
    private static final String [] QUESTIONS = new String[] {
	"Number of test cases created: ",
    };
    
    private static final String REUSE_PROMPT = "System Testing";
    
    private static final String INTEGRATION_PROMPT = "Integration Testing";
    
    private static final String [] REUSE_QUESTIONS0 = new String [] {
	"Used Big Bang",
    };
    
    private static final String [] REUSE_QUESTIONS = new String [] {
	"Used Functional Testing",
	"Used Performance Testing",
	"Used Pilot Testing",
	"Used Acceptance Testing",
	"Used Installation Testing",
    };
    
    private static final String [] REUSE_QUESTIONS2 = new String []
    {
	"Top-Down",
	"Bottom-Up",
	"Sandwich",
	"Modified Sandwich",
    };
    
    private static final String [] IMP_QUESTIONS = new String [] {
	"How many man-hours for coding were spent on the implementation process?",
	"How many estimated bugs per 1000 lines of code on the implementation process?"
    };
    
    private static final int BIG_BANG_START_SELECTED = 0;
    
    private static final int SYSTEM_TESTING_START_SELECTED = 0;
    
    private static final int REUSED_START_SELECTED = 0;
    
    
    //------------ Static Variables ----------//
    
    private static boolean standAlone = false;
    
    //------------ Instance Variables --------//
    
    /** Processing uint that does all the estimation work for the UI*/
    private TestDataProcessingUnit myUnit;
    
    private JPanel mainPanel; /** the overall container Panel*/
    private JPanel resultsPanel; /** holds the results parts of the display */
    private JPanel enterdataPanel; /** holds the enter data parts of the display */
    private JPanel reqPanel; /** This holds the information about the implementation */
    
    private JTextField [] artifacts;
    
    private JCheckBox [] reuseButtons;

    private JRadioButton [] testingMethods;
    
    private JCheckBox bigBang;
    private JCheckBox functionalTesting;
    private JCheckBox performanceTesting;
    private JCheckBox pilotTesting;
    private JCheckBox acceptanceTesting;
    private JCheckBox installationTesting;
    
    public boolean error = false; /* errors entering data */
    
    public boolean saving = false; /* allows reuse of the estimate code with out generating estimates */
    
    
    //------------ Constructors ------------//
    
    
    
    /**
     * Constructs the DesignUI object taking in an EstimationData
     *
     * @param none
     */
    public TestUI(EstimationData data)
        {
	    setMyDataProcessingUnit(new TestDataProcessingUnit(data));
	    makeScreen();
	}
    
    /**
     * Constructs the DesignUI object
     *
     * @param none
     */
    public TestUI(TestDataProcessingUnit myUnit)
    {
	setMyDataProcessingUnit(myUnit);
	makeScreen();
    }//end TestUI(TestDataProcessingUnit)
    
    
    //------------ Accessors ------------//
    /**
     * Accesses the myDataProcessingUnit variable
     *
	 * @param none
	 *
	 * @return TestDataProcessingUnit - The processing
	 *	unit used by the UI
	 */
    public TestDataProcessingUnit getMyDataProcessingUnit()
    {
	return myUnit;
    }//end TestDataProcessingUnit()
    
    //------------ Modifiers -----------//
    
    /**
     * Sets myDataProcessingUnit to the TestDataProcessingUnit passed in.
     *
     * @param TestDataProcessingUnit newUnit - The new
     *	TestDataProcessingUnit to be used.
     */
    public void setMyDataProcessingUnit (TestDataProcessingUnit newUnit)
    {
	this.myUnit = newUnit;
    }//setMyDataProcessingUnit(TestDataProcessingUnit)
    
    //------------ Methods -------------//
    
    public void itemStateChanged(ItemEvent e)
    {
	int index = 0;

	if (e.getSource().equals(bigBang))
	    {
		if (bigBang.isSelected())
		    {
			myUnit.getEstimator().setBigBangTestingUsed(true);
		    }
		else
		    {
			myUnit.getEstimator().setBigBangTestingUsed(false);
		    }
	    }
	if (e.getSource().equals(functionalTesting))
	    {
		if (functionalTesting.isSelected())
		    {
			myUnit.getEstimator().setFunctionalTestingUsed(true);
		    }
		else
		    {
			myUnit.getEstimator().setFunctionalTestingUsed(false);
		    }		
	    }
	if (e.getSource().equals(performanceTesting))
	    {
		if (performanceTesting.isSelected())
		{
		    myUnit.getEstimator().setPerformanceTestingUsed(true);
		    }
		else
		    {
			myUnit.getEstimator().setPerformanceTestingUsed(false);
		    }
	    }
	if (e.getSource().equals(pilotTesting))
	    {
		if (pilotTesting.isSelected())
		    {
			myUnit.getEstimator().setPilotTestingUsed(true);
		    }
		else
		    {
			myUnit.getEstimator().setPilotTestingUsed(false);
		    }
	    }
	if (e.getSource().equals(acceptanceTesting))
	    {
		if (acceptanceTesting.isSelected())
		    {
			myUnit.getEstimator().setAcceptanceTestingUsed(true);
		    }
		else
		    {
			myUnit.getEstimator().setAcceptanceTestingUsed(false);
		    }
	    }
	if (e.getSource().equals(installationTesting))
	    {
		if (installationTesting.isSelected())
		    {
			myUnit.getEstimator().setInstallationTestingUsed(true);
		    }
		else
		    {
			myUnit.getEstimator().setInstallationTestingUsed(false);
		    }
	    }
	
    }
    
    
    public void actionPerformed(ActionEvent e)
    {
	String cmd = e.getActionCommand();
	if (cmd.equals(SAVE_BUTTON_LABEL))
	    {
		if (DEBUG) System.out.println ("save pressed");
		save();
	    }
	else if (cmd.equals(LOAD_BUTTON_LABEL))
	    {
		load();
		if (standAlone)
		    {
			mainPanel.remove(reqPanel);
			mainPanel.add(reqPanel = makeReqPanel(), BorderLayout.NORTH);
			mainPanel.validate();
		    }
		
		if (DEBUG) System.out.println ("load pressed");
		
		validate();
	    }
	else if (cmd.equals(ESTIMATE_BUTTON_LABEL))
	    {
		if (DEBUG) System.out.println ("estimate pressed");
		estimate();
	    }
	else if (cmd.equals(IMP_BUTTON_LABEL))
	    {
		if (DEBUG) System.out.println ("Imp pressed");
		getImpsData();
	    }
	else if (cmd.equals("Next"))
	    {
		/*
		  myParent.shiftViewForward();
		*/
	    }
	else if (cmd.equals("Back"))
	    {
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
		}//for
		return (toReturn);
	}//isAnyFilledIn
    
    
    /**
     * This method is a wrapper for getReqsData to make integration easier.
     */
    public void getPrevData() {
		getImpsData();
    }//getPrevData
    
    
    /**
     *
     * This method gets the requirnments data from the user and saves it into the data.
     * It does not update the screen.
     */
    private void getImpsData() {
	String inputValue;
	for (int i = 0; i < IMP_QUESTIONS.length; i++) {
	    do {
		inputValue = JOptionPane.showInputDialog(IMP_QUESTIONS[i]);
                                if (inputValue == null) {
                                    return;
                                }
	    } while (!checkValue(inputValue, IMP_QUESTIONS[i]));
	    switch (i) {
	    case 0: myUnit.getEstimator().setCodingManHours((new Integer(inputValue)).doubleValue());
		break;
	    case 1: myUnit.getEstimator().setEstimatedBugsPer1000LOC((new Integer(inputValue)).doubleValue());
		break;
	    default: System.out.println ("Error in switch statement");
		break;
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
	    chooser.setCurrentDirectory(new File("."));
	    ExampleFileFilter filter = new ExampleFileFilter();
	    filter.addExtension(FILE_TYPE);
	    filter.setDescription("Test Data");
	    chooser.setFileFilter(filter);
	    
    	    returnVal = chooser.showSaveDialog(null);
    	}//if
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
	    
	    saving = true;
	    
	    for (int i = 0; i < artifacts.length; i++)
                {
		    if (checkValue (artifacts[i].getText(),QUESTIONS[i]))
			{
			    if (artifacts[i].getText().trim().equals(""))
				artifacts[i].setText("-1");
			    
			    switch (i)
				{
				case 0:	myUnit.getEstimator().setNumofTestCasesCreated
					    (new Double (artifacts[i].getText()).doubleValue());
				    break; //from case 0
				    
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
		    }
		}//for
	    
	    if (!error) {

		if (bigBang.isSelected())
		    {
			myUnit.getEstimator().setBigBangTestingUsed(true);
		    }
                   
	    }//if
	    
	    if (!error) {

		if (functionalTesting.isSelected())
		    {  
			myUnit.getEstimator().setFunctionalTestingUsed(true);
		    }
		if (performanceTesting.isSelected())
		    {
			myUnit.getEstimator().setPerformanceTestingUsed(true);
		    }
		if (pilotTesting.isSelected())
		    {
			myUnit.getEstimator().setPilotTestingUsed(true);
		    }
		if (acceptanceTesting.isSelected())
		    {
			myUnit.getEstimator().setAcceptanceTestingUsed(true);
		    }
		if (installationTesting.isSelected())
		    {
			myUnit.getEstimator().setInstallationTestingUsed(true);
		    }
		
	    }//if
	    
	    if (!error) {
		int selected2 = 0;
		for (int i = 0; i < testingMethods.length ; i++) {
		    if (testingMethods[i].isSelected()) {
			selected2 = i;
		    }//if
		}//for
		
		
		//		switch (selected2) {
		//case 0: myUnit.getEstimator().setTopDownTestingUsed(true);
		//  break;
		//case 1: myUnit.getEstimator().setBottomUpTestingUsed(true);
		//  break;
		//case 2:	myUnit.getEstimator().setSandwichTestingUsed(true);
		//  break;
		//case 3:	myUnit.getEstimator().setModifiedSandwichTestingUsed(true);
		//   break;
		//default:	System.out.println ("Error in switch statement");
		//    break;
		//}//switch
		
		if (selected2 == 0) 
		    myUnit.getEstimator().setTopDownTestingUsed(true);
		else
		    myUnit.getEstimator().setTopDownTestingUsed(false);

		if (selected2 == 1)
		    myUnit.getEstimator().setBottomUpTestingUsed(true);
		else
		    myUnit.getEstimator().setBottomUpTestingUsed(false);
		
		if (selected2 == 2)
		    myUnit.getEstimator().setSandwichTestingUsed(true);
		else
		    myUnit.getEstimator().setSandwichTestingUsed(false);
		
		if (selected2 == 3)
		    myUnit.getEstimator().setModifiedSandwichTestingUsed(true);
		else
		    myUnit.getEstimator().setModifiedSandwichTestingUsed(false);
		    
		
		
		
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
	    chooser.setCurrentDirectory(new File("."));
	    ExampleFileFilter filter = new ExampleFileFilter();
	    filter.addExtension(FILE_TYPE);
	    filter.setDescription("Test Component Data");
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
		    setTo = "" + new Double(myUnit.getEstimator().getNumOfTestCasesCreated()).intValue();
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

		boolean usedFunctional = myUnit.getEstimator().getFunctionalTestingUsed();
		boolean usedPerformance = myUnit.getEstimator().getPerformanceTestingUsed();
		boolean usedPilot = myUnit.getEstimator().getPilotTestingUsed();
		boolean usedAcceptance = myUnit.getEstimator().getAcceptanceTestingUsed();
		boolean usedInstallation = myUnit.getEstimator().getInstallationTestingUsed();

		functionalTesting.setSelected(false);
		performanceTesting.setSelected(false);
		pilotTesting.setSelected(false);
		acceptanceTesting.setSelected(false);
		installationTesting.setSelected(false);

		if (usedFunctional == true)
		    {
			functionalTesting.setSelected(true);
		    }
		if (usedPerformance == true)
		    {
			performanceTesting.setSelected(true);
		    }
		if (usedPilot == true)
		    {
			pilotTesting.setSelected(true);
		    }
		if (usedAcceptance == true)
		    {
			acceptanceTesting.setSelected(true);
		    }
		if (usedInstallation == true)
		    {
			installationTesting.setSelected(true);
		    }
		boolean usedTopDown = myUnit.getEstimator().getTopDownTestingUsed();
		boolean usedBottomUp = myUnit.getEstimator().getBottomUpTestingUsed();
		boolean usedSandwich = myUnit.getEstimator().getSandwichTestingUsed();
		boolean usedModifiedSandwich = myUnit.getEstimator().getModifiedSandwichTestingUsed();
		for (int j=0; j < testingMethods.length; j++)
		    testingMethods[j].setSelected(false);
		if (usedTopDown == true)
		    testingMethods[0].setSelected(true);
		else
		    testingMethods[0].setSelected(false);

		if (usedBottomUp == true)
		    testingMethods[1].setSelected(true);
		else
		    testingMethods[1].setSelected(false);

		if (usedSandwich == true)
		    testingMethods[2].setSelected(true);
		else
		    testingMethods[3].setSelected(false);
		
		if (usedModifiedSandwich == true)
		    testingMethods[3].setSelected(true);
		else
		    testingMethods[3].setSelected(false);

		boolean usedBigBang = myUnit.getEstimator().getBigBangTestingUsed();

		bigBang.setSelected(false);
		if (usedBigBang == true)
		    {
			bigBang.setSelected(true);
		    }
	    }//for
       	}//if
       	else {
	    if (returnVal != JFileChooser.CANCEL_OPTION)
		JOptionPane.showMessageDialog(null, "Invalid file selection!", "Invalid File", JOptionPane.ERROR_MESSAGE);
       	}//else
    }//end load()
    
    /**
     * Call estimate() on the TestDataProccessingUnit. <p>
     * Refreshes the view so that the data reflected in the view <p>
     * is updated to show the data in the TestDataProcessingUnit.
     *
     * This method also calls estimate on the TestDataProcessingUnit
     *
     * @param none
     *
     * @return void
     */
    public boolean estimate()
    {
	error = false;
	if (!saving)
            {
		if (standAlone)
		    {
			if (myUnit.getEstimator().getCodingManHours() < 0 || myUnit.getEstimator().getEstimatedBugsPer1000LOC() < 0)
			    {
				getImpsData();
			    }//if Implementation is not filled in.
		    }
            }
	
	for (int i = 0; i < artifacts.length; i++)
	    {
		if (checkValue (artifacts[i].getText(),QUESTIONS[i]))
		    {
			switch (i)
			    {
			    case 0:	myUnit.getEstimator().setNumofTestCasesCreated(new Double (artifacts[i].getText()).doubleValue());
				break; //from case 0
			
			    default:
				System.out.println ("Error in switch statement");
				break; //from default
			    }//switch
			
		    }// if
		
		else
		    {
			error = true;
			break; //get out of for loop.
		    } // else
	    }//for

	if (!error) {
	    int selected0 = 0;
	    int selected = 0;
	    int selected2 = 0;
	    
	    for (int f = 0; f < testingMethods.length; f++)
		{
		    if (testingMethods[f].isSelected())
			{
                            selected2 = f;
			}
		}

	    if (bigBang.isSelected())
		{
		    myUnit.getEstimator().setBigBangTestingUsed(true);
		}
	    else
		{
		    myUnit.getEstimator().setBigBangTestingUsed(false);
		}
	    
	    //	    switch (selected2) {
	    // case 0: myUnit.getEstimator().setTopDownTestingUsed(true);
	    //	break;
	    //case 1: myUnit.getEstimator().setBottomUpTestingUsed(true);
	    //break;
	    //case 2: myUnit.getEstimator().setSandwichTestingUsed(true);
	    //break;
	    //case 3: myUnit.getEstimator().setModifiedSandwichTestingUsed(true);
	    //break;
	    //default:  System.out.println ("Error in switch statement");
	    //break;
	    //}//switch
	    
		if (selected2 == 0) 
		    myUnit.getEstimator().setTopDownTestingUsed(true);
		else
		    myUnit.getEstimator().setTopDownTestingUsed(false);

		if (selected2 == 1)
		    myUnit.getEstimator().setBottomUpTestingUsed(true);
		else
		    myUnit.getEstimator().setBottomUpTestingUsed(false);
		
		if (selected2 == 2)
		    myUnit.getEstimator().setSandwichTestingUsed(true);
		else
		    myUnit.getEstimator().setSandwichTestingUsed(false);
		
		if (selected2 == 3)
		    myUnit.getEstimator().setModifiedSandwichTestingUsed(true);
		else
		    myUnit.getEstimator().setModifiedSandwichTestingUsed(false);
		    

	    if (functionalTesting.isSelected())
		{
		    myUnit.getEstimator().setFunctionalTestingUsed(true);
		}
	    else
		{
		    myUnit.getEstimator().setFunctionalTestingUsed(false);
		}

	    if (performanceTesting.isSelected())
		{
		    myUnit.getEstimator().setPerformanceTestingUsed(true);
		}
	    else
		{
		    myUnit.getEstimator().setPerformanceTestingUsed(false);
		}

	    if (pilotTesting.isSelected())
		{
		    myUnit.getEstimator().setPilotTestingUsed(true);
		}
	    else
		{
		    myUnit.getEstimator().setPilotTestingUsed(false);
		}

	    if (acceptanceTesting.isSelected())
		{
		    myUnit.getEstimator().setAcceptanceTestingUsed(true);
		}
	    else
		{
		    myUnit.getEstimator().setAcceptanceTestingUsed(false);
		}

	    if (installationTesting.isSelected())
		{
		    myUnit.getEstimator().setInstallationTestingUsed(true);
		}
	    else
		{
		    myUnit.getEstimator().setInstallationTestingUsed(false);
		}
	    
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
	int x;
	if (!saving) {
	    if (toCheck.equals("")) { // Blank Boxes
		JOptionPane.showMessageDialog(null,
					      "An answer to \"" + question + "\" must be entered" , "Error in data",
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
					  "The answer to \"" + question + "\" must be an integer!" , "Error in data",
					  JOptionPane.ERROR_MESSAGE);
	    return(false);
	}//catch
	if (x < 0) {
	    JOptionPane.showMessageDialog(null,
					  "The answer to \"" + question + "\" must be a positive integer!" , "Error in data",
					  JOptionPane.ERROR_MESSAGE);
	    return (false);
	}//if
	
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
	JLabel titleReq = new JLabel ("Data from the Implementation process: ");
	titleReq.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,TITLE_FONT_SIZE));
	titleReq.setForeground(NORMAL_FONT_COLOR);
	reqPanel.add(titleReq);
	
	JLabel codingManHours = new JLabel (SPACER + "Total number of coding man hours are " + getCodingMan());
	codingManHours.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	codingManHours.setForeground(NORMAL_FONT_COLOR);
	
	JLabel estimatedBugs = new JLabel (SPACER + "Total number of estimated bugs per 1000 LOC is " + getEstimatedBugs());
	estimatedBugs.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	estimatedBugs.setForeground(NORMAL_FONT_COLOR);
	
	reqPanel.add(codingManHours);
	reqPanel.add(Box.createVerticalStrut(VERTSPACE));
	reqPanel.add(estimatedBugs);
	
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

	JPanel resultsPanel = new JPanel();
	resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
	
	if (standAlone) {
	    resultsPanel.add(makeReqPanel()); /**Adds the requirnments stuff */
	}//if
	
	
	JLabel title = new JLabel ("Estimates of Test process:");
	title.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,TITLE_FONT_SIZE));
	title.setForeground(NORMAL_FONT_COLOR);
	
	JLabel integrationTestingHours = new JLabel (SPACER + "Integration Testing Hours: " + getIntegration());
	integrationTestingHours.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	integrationTestingHours.setForeground(NORMAL_FONT_COLOR);
	
	
	JLabel systemTestingHours = new JLabel (SPACER + "System Testing Hours: " + getSystem());
	systemTestingHours.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	systemTestingHours.setForeground(NORMAL_FONT_COLOR);
	
	JLabel totalManHours = new JLabel (SPACER + "Total Man Hours: " + getTotalMan());
	totalManHours.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	totalManHours.setForeground(NORMAL_FONT_COLOR);
	
	
	JLabel avgBugsAfterTesting = new JLabel (SPACER + "Bugs per 1000 lines of code: " + getAvgBugs());
	avgBugsAfterTesting.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	avgBugsAfterTesting.setForeground(Color.black);
	
	
	resultsPanel.add(title);
	resultsPanel.add(integrationTestingHours);
	resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
	resultsPanel.add(systemTestingHours);
	resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
	resultsPanel.add(totalManHours);
	resultsPanel.add(Box.createVerticalStrut(VERTSPACE));
	resultsPanel.add(avgBugsAfterTesting);

	return (resultsPanel);
    }//makeResultsPanel
    
    /**
     * Gets the current number of manhours from the data processing unit, and returns it as a string.
     *@param
     *@return String - number of man hours or "unestimated"
     */
    private String getIntegration() {
	String toReturn = "unestimated.";
	if (new Double(myUnit.getEstimator().getIntegrationTestingHours()).intValue() != -1) {
	    double temp = (new Double(myUnit.getEstimator().getIntegrationTestingHours())).doubleValue();
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
    private String getSystem() {
	String toReturn = "unestimated.";
	if (new Double(myUnit.getEstimator().getSystemTestingHours()).intValue() > -1) {
	    toReturn = "" + new Double(myUnit.getEstimator().getSystemTestingHours()).doubleValue() + ".";
	}//if
	return (toReturn);
    }//getFuncs
    
    /**
     * Gets the current percentage of reuse from the data processing unit, and returns it as a string.
     *@param
     *@return String - number of man hours or "unestimated"
     */
    private String getTotalMan() {
	String toReturn = "unestimated.";
	if (new Double(myUnit.getEstimator().getTestingTotalHours()).intValue()*100 > -1 ) {
	    toReturn = "" + (new Double(myUnit.getEstimator().getTestingTotalHours())).doubleValue();
	}//if
	return (toReturn);
    }//getReuse
    
    /**
     * Gets the current number of classes from the data processing unit, and returns it as a string.
     *@param
     *@return String - number of man hours or "unestimated"
     */
    private String getAvgBugs() {
	String toReturn = "unestimated.";
	if (new Double(myUnit.getEstimator().getAvgBugsAfterTesting()).intValue() > -1 ) {
	    toReturn = "" + new Double(myUnit.getEstimator().getAvgBugsAfterTesting()).doubleValue() + ".";
	}//if
	return (toReturn);
    }//getClasses
    
    
    /**
     * Used to get the current number of candidate classes.
     */
    private String getCodingMan() {
	String toReturn = "not entered.";
	if (new Double(myUnit.getEstimator().getCodingManHours()).intValue() > -1 ) {
	    toReturn = "" + new Double(myUnit.getEstimator().getCodingManHours()).doubleValue() + ".";
	}//if
	return (toReturn);
    }//getCandidateClasses
    
    
    /**
     * Used to get the total number of hours spent on the Requirnments.
     */
    private String getEstimatedBugs() {
	String toReturn = "not entered.";
	if (new Double(myUnit.getEstimator().getEstimatedBugsPer1000LOC()).intValue() > -1) {
	    toReturn = "" + new Double(myUnit.getEstimator().getEstimatedBugsPer1000LOC()).doubleValue() + ".";
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
	
	JLabel title = new JLabel ("Data about the Test process:");
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
	
	testingMethods = new JRadioButton [REUSE_QUESTIONS2.length];
	ButtonGroup testingMethodButtonGroup = new ButtonGroup();
	
	if (myUnit.getEstimator().getBigBangTestingUsed())
	    {
		bigBang = new JCheckBox ("Used Big Bang", true);
	    }
	else
	    {
		bigBang = new JCheckBox ("Used Big Bang", false);
	    }
		
	JPanel row0 = new JPanel();
	row0.setLayout(new FlowLayout(FlowLayout.LEFT));

	JLabel intTitle = new JLabel(INTEGRATION_PROMPT);
	intTitle.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	intTitle.setForeground(Color.black);
	row0.add(intTitle);
	enterData.add(row0);
	row0 = new JPanel();  /** Makes a new instance of row for outside loop world **/
	row0.setLayout(new FlowLayout(FlowLayout.LEFT));

	row0.add(new JLabel(SPACER));
	row0.add(bigBang);
	enterData.add(row0);

	for (int s = 0; s < REUSE_QUESTIONS2.length; s++)
	    {
		testingMethods[s] = new JRadioButton(REUSE_QUESTIONS2[s]);
		testingMethods[s].setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
		testingMethodButtonGroup.add(testingMethods[s]);
		if (s == REUSED_START_SELECTED)
		    testingMethods[s].setSelected(true);
		JPanel row2 = new JPanel();
		row2.setLayout(new FlowLayout(FlowLayout.LEFT));
		if (s == 0)
		    {
			enterData.add(row2);
			row2 = new JPanel();  /** Makes a new instance of row for outside loop world **/
			row2.setLayout(new FlowLayout(FlowLayout.LEFT));
		    }// if
		row2.add(new JLabel(SPACER));
		row2.add(testingMethods[s]);
		enterData.add(row2);
	    }//for
	
	if (myUnit.getEstimator().getFunctionalTestingUsed())
	    {
		functionalTesting = new JCheckBox ("Used Functional Testing", true);
	    }
	else
	    {
		functionalTesting = new JCheckBox ("Used Functional Testing", false);
	    }
	if (myUnit.getEstimator().getPerformanceTestingUsed())
	    {
		performanceTesting = new JCheckBox ("Used Performance Testing", true);
	    }
	else
	    {
		performanceTesting = new JCheckBox ("Used Performance Testing", false);
	    }
	if (myUnit.getEstimator().getPilotTestingUsed())
	    {
		pilotTesting = new JCheckBox ("Used Pilot Testing", true);
	    }
	else
	    {
		pilotTesting = new JCheckBox ("Used Pilot Testing", false);
	    }
	
	if (myUnit.getEstimator().getAcceptanceTestingUsed())
	    {
		acceptanceTesting = new JCheckBox ("Used Acceptance Testing", true);
	    }
	else
	    {
		acceptanceTesting = new JCheckBox ("Used Acceptance Testing", false);
	    }

	if (myUnit.getEstimator().getInstallationTestingUsed())
	    {
		installationTesting = new JCheckBox ("Used Installation Testing", true);
	    }
	else
	    {
		installationTesting = new JCheckBox ("Used Installation Testing", false);
	    }

	JPanel row2 = new JPanel();
	row2.setLayout(new FlowLayout(FlowLayout.LEFT));

	JLabel reuseTitle = new JLabel(REUSE_PROMPT);
	reuseTitle.setFont(new Font (NORMAL_FONT_TYPE,NORMAL_FONT_STYLE,NORMAL_FONT_SIZE));
	reuseTitle.setForeground(Color.black);
	row2.add(reuseTitle);
	enterData.add(row2);
	row2 = new JPanel();  /** Makes a new instance of row for outside loop world **/
	row2.setLayout(new FlowLayout(FlowLayout.LEFT));

	row2.add(new JLabel(SPACER));
	row2.add(functionalTesting);
	enterData.add(row2);
	
	row2 = new JPanel();
	row2.setLayout(new FlowLayout(FlowLayout.LEFT));
	row2.add(new JLabel(SPACER));
	row2.add(performanceTesting);
	enterData.add(row2);

	row2 = new JPanel();
	row2.setLayout(new FlowLayout(FlowLayout.LEFT));
	row2.add(new JLabel(SPACER));
	row2.add(pilotTesting);
	enterData.add(row2);

	row2 = new JPanel();
	row2.setLayout(new FlowLayout(FlowLayout.LEFT));	
	row2.add(new JLabel(SPACER));
	row2.add(acceptanceTesting);
	enterData.add(row2);

	row2 = new JPanel();
	row2.setLayout(new FlowLayout(FlowLayout.LEFT));	
	row2.add(new JLabel(SPACER));
	row2.add(installationTesting);
	enterData.add(row2);

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

	JButton load = new JButton (LOAD_BUTTON_LABEL);
	load.addActionListener(this);
	load.addItemListener(this);
	JButton save = new JButton (SAVE_BUTTON_LABEL);
	save.addActionListener(this);
	save.addItemListener(this);
	JButton req = new JButton (IMP_BUTTON_LABEL);
	req.addActionListener(this);
	JButton estimate = new JButton (ESTIMATE_BUTTON_LABEL);
	estimate.addActionListener(this);
	estimate.addItemListener(this);
	
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
	TestUI.standAlone = true;
	TestUI myTestUI = new TestUI (new TestDataProcessingUnit(-1,-1));

	JFrame myFrame = new JFrame ("Test Component");
	myFrame.addWindowListener( new WindowAdapter() {
      		public void windowClosing(WindowEvent e) {
		    System.exit(0);
      		}
	    });
	myFrame.getContentPane().add(myTestUI);
	myFrame.setSize(800,640);
	myFrame.setVisible(true);
	
	if (DEBUG) {
	    System.out.println(myTestUI.isAnyFilledIn());
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
