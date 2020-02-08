package RequirementsComponent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import Estimator.*;
import java.util.Vector;

/**
 */
public class RequirementsUI extends JPanel implements ActionListener, ItemListener, SubComponent {

	/** CONSTANTS **/
	public static final Dimension 	SCREEN 		= Toolkit.getDefaultToolkit().getScreenSize();
	public static final Rectangle 	WINDOW 		= new Rectangle((SCREEN.width - 640) / 2, (SCREEN.height - 480) / 2, 640, 480);
	public static final Font 		FONT_H1 	= new Font("Arial", Font.PLAIN, 24);
	public static final Font 		FONT_H2 	= new Font("Arial", Font.BOLD, 14);
	public static final Font 		FONT_REGULAR 	= new Font("Arial", Font.PLAIN, 14);
	public static final Color 		STANDARD_COLOR 	= Color.black;

	private static final String 	SPACER = "         "; /** Used in GUI alignment*/
	private static final int 		VERTSPACE = 8;
	public static final String 		ERR_MSG_BLANK 		= "Please enter a value for field: ";
	public static final String 		ERR_MSG_NEGATIVE 	= "Please use only a positive number for field: ";
	public static final String 		ERR_MSG_NON_INTEGER 	= "Please use only an integer number for field: ";


	/*****************************************************************************
	*
	* DEFAULT VALUES
	*
	*/
	public static final boolean 	DEFAULT_JAD_USED = true;
	public static final boolean 	DEFAULT_KAT_USED = true;

	public static final int 		DEFAULT_KAT_NUM_SURVEYS 			= 5;
	public static final int 		DEFAULT_KAT_NUM_SURVEY_QUESTIONS 	= 10;
	public static final int 		DEFAULT_KAT_NUM_INTERVIEWS 			= 10;
	public static final int 		DEFAULT_KAT_NUM_INTERVIEW_QUESTIONS = 10;
	public static final int 		DEFAULT_KAT_NUM_OBSERVATIONS 		= 20;
  	public static final int 		DEFAULT_KAT_NUM_DOCUMENT_PAGES 		= 300;
	public static final int			DEFAULT_JAD_NUM_SESSIONS 			= 5;
	public static final int			DEFAULT_JAD_NUM_PRODUCT_INTERVIEWS 	= 5;
	public static final int			DEFAULT_JAD_NUM_RESEARCH_INTERVIEWS	= 5;
	public static final int			DEFAULT_JAD_NUM_STAKEHOLDERS		= 4;

	public static final int 		DEFAULT_VAL_NUM_SCENARIOS			= 20;
	public static final int			DEFAULT_VAL_NUM_PROTOTYPES			= 5;
	public static final int			DEFAULT_VAL_NUM_CANDIDATE_CLASSES	= 50;


	//Button labels
	public static final String SAVE = "Save Data";
	public static final String LOAD = "Load Data";
	public static final String ESTIMATE = "Refresh Estimation";

	/** INSTANCE VARIABLES **/

	private boolean isStandAlone;
	private boolean isFirstTime;

	private JButton butLoad;
	private JButton butSave;
	private JButton butEstimate;
	private JPanel ioPanel;
	private JPanel pnlKAT, pnlJAD, pnlMain, pnlValidation, pnlElicitationChoice;
	private JFrame frmRequirementsComponent;

	private int iCurrentScreen;
	private boolean useKAT;
	private boolean useJAD;

	private RequirementsDPU dpu;
	private EstimationData reqData;

	//Elicitation Techniques
	private JCheckBox cbKAT, cbJAD;

	//KAT ..note: txtKATSessions is not used at all..
	private JTextField txtKATSessions, txtKATSurveys, txtKATSurveyQuestions,
		txtKATInterviews, txtKATInterviewQuestions, txtKATObservations,
		txtKATDocumentPages;


	//JAD
	private JTextField txtJADSessions, txtJADDefinitionInterviews,
		txtJADResearchInterviews, txtJADStakeholders;

	//Validation ..note: txtProductTests is not used at all..
	private JTextField txtScenarios, txtPrototypeTests, txtProductTests, txtCandidateClasses;

	public static final int MAX_KAT_FIELDS = 6;			// how many input text fields for KAT
	public static final int MAX_JAD_FIELDS = 4;			// how many input text fields for JAD
	public static final int MAX_VALIDATION_FIELDS = 3; 	// how many input text fields for Validation
	public static final String EMPTY_STRING = "";		// for use when checking for blank input

	private JTextField[] aryTextKAT;				// holds references to the input text fields
	private JTextField[] aryTextJAD;				// holds references to the input text fields
	private JTextField[] aryTextValidation;			// holds references to the input text fields

	private String[] aryTextNameKAT;				// holds each input text field's name
	private String[] aryTextNameJAD;				// holds each input text field's name
	private String[] aryTextNameValidation;			// holds each input text field's name
	private int[] aryDefaultsKAT;
	private int[] aryDefaultsJAD;
	private int[] aryDefaultsValidation;


	/**
	* RequirementsUI - constructor
	*/
	public RequirementsUI() {
		isStandAlone = true;
		isFirstTime = true;

		useKAT = false;
		useJAD = false;

		//enableKAT(false);
		//enableJAD(false);


		dpu = new RequirementsDPU();
		reqData = new EstimationData();

		setupPanel();
		setupWindow();

		setupTextFieldArrays();
	}

	/**
	* constructor can be passed an estimation data to determine if it's standalone or not
	*/
	public RequirementsUI(EstimationData reqData)
	{
		isFirstTime = true;

		if ( reqData != null )
		{
			this.reqData = reqData;
			isStandAlone = false;
		}
		else
		{
			reqData = new EstimationData();
			isStandAlone = true;
		}

		dpu = new RequirementsDPU();
		dpu.setData(this.reqData);

		useKAT = false;
		useJAD = false;

		setupPanel();

		setupTextFieldArrays();
	}



	/**
	* setupTextFieldArrays
	*
	*  params: nothing
	*  returns: nothing
	*
	*  purpose: setup arrays of text input fields and their corresponding names
	*			these arrays are used during validation so that we can easily
	*			loop through the input fields and test them.
	*
	*  note: any text fields added or removed from this application will need
	*		 to be removed or added from these arrays in order for validation
	*		 to function properly.
	*/
	private void setupTextFieldArrays()
	{
		aryTextKAT = new JTextField[MAX_KAT_FIELDS];
		aryTextJAD = new JTextField[MAX_JAD_FIELDS];
		aryTextValidation = new JTextField[MAX_VALIDATION_FIELDS];

		aryTextNameKAT = new String[MAX_KAT_FIELDS];
		aryTextNameJAD = new String[MAX_JAD_FIELDS];
		aryTextNameValidation = new String[MAX_VALIDATION_FIELDS];
		aryDefaultsKAT = new int[MAX_KAT_FIELDS];
		aryDefaultsJAD = new int[MAX_JAD_FIELDS];
		aryDefaultsValidation = new int[MAX_VALIDATION_FIELDS];


		aryTextKAT[0] = txtKATSurveys;				aryTextNameKAT[0] = "KAT Surveys";
		aryTextKAT[1] = txtKATSurveyQuestions;		aryTextNameKAT[1] = "KAT Survey Questions";
		aryTextKAT[2] = txtKATInterviews;			aryTextNameKAT[2] = "KAT Interviews";
		aryTextKAT[3] = txtKATInterviewQuestions;	aryTextNameKAT[3] = "KAT Interview Questions";
		aryTextKAT[4] = txtKATObservations;			aryTextNameKAT[4] = "KAT Observations";
		aryTextKAT[5] = txtKATDocumentPages;		aryTextNameKAT[5] = "KAT Document Pages";

		aryTextJAD[0] = txtJADSessions;				aryTextNameJAD[0] = "JAD Sessions";
		aryTextJAD[1] = txtJADDefinitionInterviews;	aryTextNameJAD[1] = "JAD Definition Interviews";
		aryTextJAD[2] = txtJADResearchInterviews;	aryTextNameJAD[2] = "JAD Research Interviews";
		aryTextJAD[3] = txtJADStakeholders;			aryTextNameJAD[3] = "JAD Stakeholders";

		aryTextValidation[0] = txtScenarios;		aryTextNameValidation[0] = "Validation Scenarios";
		aryTextValidation[1] = txtPrototypeTests;	aryTextNameValidation[1] = "Validation Prototype Tests";
		aryTextValidation[2] = txtCandidateClasses;	aryTextNameValidation[2] = "Validation Candidate Classes";

		aryDefaultsKAT[0] = DEFAULT_KAT_NUM_SURVEYS;		aryDefaultsKAT[1] = DEFAULT_KAT_NUM_SURVEY_QUESTIONS;
		aryDefaultsKAT[2] = DEFAULT_KAT_NUM_INTERVIEWS;
		aryDefaultsKAT[3] = DEFAULT_KAT_NUM_INTERVIEW_QUESTIONS;
		aryDefaultsKAT[4] = DEFAULT_KAT_NUM_OBSERVATIONS;
		aryDefaultsKAT[5] = DEFAULT_KAT_NUM_DOCUMENT_PAGES;

		aryDefaultsJAD[0] = DEFAULT_JAD_NUM_SESSIONS;
		aryDefaultsJAD[1] = DEFAULT_JAD_NUM_PRODUCT_INTERVIEWS;
		aryDefaultsJAD[2] = DEFAULT_JAD_NUM_RESEARCH_INTERVIEWS;
		aryDefaultsJAD[3] = DEFAULT_JAD_NUM_STAKEHOLDERS;

		aryDefaultsValidation[0] = DEFAULT_VAL_NUM_SCENARIOS;
		aryDefaultsValidation[1] = DEFAULT_VAL_NUM_PROTOTYPES;
		aryDefaultsValidation[2] = DEFAULT_VAL_NUM_CANDIDATE_CLASSES;
	}



	/**
	* validateTextFieldsForEstimation
	*
	*  params: nothing
	*  returns: true when all text fields have passed validation tests
	* 			false when any text field contains invalid input.
	*
	*  purpose: test text input fields for invalid input from user such as
	*			negatives, non-integers, and blank inputs.
	*
	*  note: this method differs from validateTextFieldsForSaving() because
	*		  this method is called when the user presses the "Refresh Estimation"
	*		  button, we need to check for blank input this time because blank input
	*		  is bad for the algorithms.
	*/
	private boolean validateTextFieldsForEstimation()
	{
		boolean isValid = true;

		if (useKAT)
			for ( int i = 0; i < MAX_KAT_FIELDS; i++ )
			{
				if ( aryTextKAT[i] == null )
					System.out.println("kat text field " + i + " is null");
				else
				{

					if ( aryTextKAT[i].getText().trim().equals(EMPTY_STRING) )
					{
						// validate for empty string
						// System.out.println("kat text field " + i + " is empty");
						JOptionPane.showMessageDialog(null, ERR_MSG_BLANK + aryTextNameKAT[i], "Blank Input Validation", JOptionPane.ERROR_MESSAGE);
						isValid = false;
					}
					else
					{
						// validate for positive integer
						try
						{
							if ( Integer.parseInt( aryTextKAT[i].getText().trim() ) < 0 )
							{
								JOptionPane.showMessageDialog(null, ERR_MSG_NEGATIVE + aryTextNameKAT[i], "Positive Integer Validation", JOptionPane.ERROR_MESSAGE);
								isValid = false;
							}
						}
						catch( Exception e )
						{
							JOptionPane.showMessageDialog(null, ERR_MSG_NON_INTEGER + aryTextNameKAT[i], "Integer Validation", JOptionPane.ERROR_MESSAGE);
							isValid = false;
						}

					} // end if
				} // end if
			} // end for


		if (useJAD)
			for ( int i = 0; i < MAX_JAD_FIELDS; i++ )
			{
				if ( aryTextJAD[i] == null )
					System.out.println("jad text field " + i + " is null");
				else
				{
					if ( aryTextJAD[i].getText().trim().equals(EMPTY_STRING) )
					{
						// validate for empty string
						// System.out.println("jad text field " + i + " is empty");
						JOptionPane.showMessageDialog(null, ERR_MSG_BLANK + aryTextNameJAD[i], "Blank Input Validation", JOptionPane.ERROR_MESSAGE);
						isValid = false;
					}
					else
					{
						// validate for positive integer
						try
						{
							if ( Integer.parseInt( aryTextJAD[i].getText().trim() ) < 0 )
							{
								JOptionPane.showMessageDialog(null, ERR_MSG_NON_INTEGER + aryTextNameJAD[i], "Positive Integer Validation", JOptionPane.ERROR_MESSAGE);
								isValid = false;
							}
						}
						catch( Exception e )
						{
							JOptionPane.showMessageDialog(null, ERR_MSG_NEGATIVE + aryTextNameJAD[i], "Integer Validation", JOptionPane.ERROR_MESSAGE);
							isValid = false;
						}

					} // end if
				} // end if
			} // end for



		for ( int i = 0; i < MAX_VALIDATION_FIELDS; i++ )
		{

			if ( aryTextValidation[i] == null )
				System.out.println("validation text field " + i + " is null");
			else
			{
				if ( aryTextValidation[i].getText().trim().equals(EMPTY_STRING) )
				{
					// validate for empty string
					// System.out.println("validation text field " + i + " is empty");
					JOptionPane.showMessageDialog(null, ERR_MSG_BLANK + aryTextNameValidation[i], "Blank Input Validation", JOptionPane.ERROR_MESSAGE);
					isValid = false;
				}
				else
				{
					// validate for positive integer
					try
					{
						if ( Integer.parseInt( aryTextValidation[i].getText().trim() ) < 0 )
						{
							JOptionPane.showMessageDialog(null, ERR_MSG_NON_INTEGER + aryTextNameValidation[i], "Positive Integer Validation", JOptionPane.ERROR_MESSAGE);
							isValid = false;
						}
					}
					catch( Exception e )
					{
						JOptionPane.showMessageDialog(null, ERR_MSG_NEGATIVE + aryTextNameValidation[i], "Integer Validation", JOptionPane.ERROR_MESSAGE);
						isValid = false;
					}

				} // end if
			} // end if
		} // end for

		return isValid;
	}



	/**
	 * validateTextFieldsForSaving
	 *
	 *  params: nothing
	 *  returns: true when all text fields have passed validation tests
	 * 			false when any text field contains invalid input.
	 *
	 *  purpose: test text input fields for invalid input from user such as
	 *			negatives and non-integers.
	 *
	 *	note: this validate method is different than validateTextFieldsForEstimation()
	 *			because this is called only when the user presses the "Save" button
	 *			which means we can save blank input (as -1), but still check for
	 *			negatives and non-integers when the input is not blank.
	 *
	 */
	private boolean validateTextFieldsForSaving()
	{
		boolean isValid = true;

		if (useKAT)
			for ( int i = 0; i < MAX_KAT_FIELDS; i++ )
			{
				if ( aryTextKAT[i] == null )
					System.out.println("kat text field " + i + " is null");
				else
				{
					if ( ! aryTextKAT[i].getText().trim().equals(EMPTY_STRING) )
					{
						// validate for positive integer
						try
						{
							if ( Integer.parseInt( aryTextKAT[i].getText().trim() ) < 0 )
							{
								JOptionPane.showMessageDialog(null, ERR_MSG_NEGATIVE + aryTextNameKAT[i], "Positive Integer Validation", JOptionPane.ERROR_MESSAGE);
								isValid = false;
							}
						}
						catch( Exception e )
						{
							JOptionPane.showMessageDialog(null, ERR_MSG_NON_INTEGER + aryTextNameKAT[i], "Integer Validation", JOptionPane.ERROR_MESSAGE);
							isValid = false;
						}

					} // end if


				} // end if
			} // end for


		if (useJAD)
			for ( int i = 0; i < MAX_JAD_FIELDS; i++ )
			{
				if ( aryTextJAD[i] == null )
					System.out.println("jad text field " + i + " is null");
				else
				{
					if ( ! aryTextJAD[i].getText().trim().equals(EMPTY_STRING) )
					{
						// validate for positive integer
						try
						{
							if ( Integer.parseInt( aryTextJAD[i].getText().trim() ) < 0 )
							{
								JOptionPane.showMessageDialog(null, ERR_MSG_NEGATIVE + aryTextNameJAD[i], "Positive Integer Validation", JOptionPane.ERROR_MESSAGE);
								isValid = false;
							}
						}
						catch( Exception e )
						{
							JOptionPane.showMessageDialog(null, ERR_MSG_NON_INTEGER + aryTextNameJAD[i], "Integer Validation", JOptionPane.ERROR_MESSAGE);
							isValid = false;
						}

					} // end if
				} // end if

			} // end for


		for ( int i = 0; i < MAX_VALIDATION_FIELDS; i++ )
		{

			if ( aryTextValidation[i] == null )
				System.out.println("validation text field " + i + " is null");
			else
			{
				if ( ! aryTextValidation[i].getText().trim().equals(EMPTY_STRING) )
				{
					// validate for positive integer
					try
					{
						if ( Integer.parseInt( aryTextValidation[i].getText().trim() ) < 0 )
						{
							JOptionPane.showMessageDialog(null, ERR_MSG_NEGATIVE + aryTextNameValidation[i], "Positive Integer Validation", JOptionPane.ERROR_MESSAGE);
							isValid = false;
						}
					}
					catch( Exception e )
					{
						JOptionPane.showMessageDialog(null, ERR_MSG_NON_INTEGER + aryTextNameValidation[i], "Integer Validation", JOptionPane.ERROR_MESSAGE);
						isValid = false;
					}

				} // end if
			} // end if

		} // end for


		return isValid;
	}




  /**
   * setupPanel - sets up the UI panel
   */
	private void setupPanel() {
		JPanel pnlMain;

		if(isFirstTime) {
			pnlKAT = setupKAT();
			pnlJAD = setupJAD();
			pnlElicitationChoice = setupElicitationChoice();
			pnlValidation = setupValidation();
			isFirstTime = false;
		}

		JScrollPane sp;

		pnlMain = createPanel(null, true);


		pnlMain.add(pnlElicitationChoice);
		pnlMain.add(Box.createVerticalStrut(25));

		if(useKAT) {
			pnlMain.add(pnlKAT);
			pnlMain.add(Box.createVerticalStrut(25));
		}

		if (useJAD) {
			pnlMain.add(pnlJAD);
			pnlMain.add(Box.createVerticalStrut(25));
		}

		pnlMain.add(pnlValidation);
		sp = new JScrollPane(pnlMain);
		sp.setPreferredSize(new Dimension(WINDOW.width, WINDOW.height));
		//sp.setPreferredSize(new Dimension(1024, 768));

		//set up the title label
		JLabel lblTitle = new JLabel("Data about the Requirements Process");
		lblTitle.setFont(FONT_H1);
		lblTitle.setForeground(STANDARD_COLOR);

		this.removeAll();

		this.setLayout(new BorderLayout());
		this.add(lblTitle, BorderLayout.NORTH);
		this.add(sp, BorderLayout.CENTER);
		this.repaint();
		this.validate();
	}

	/**
	* setupWindow - creates the windows for the program
	*/
	private void setupWindow() {
		JPanel pnlButtons;

		frmRequirementsComponent = new JFrame();

		WindowListener theWL = new WindowAdapter() {
		  	public void windowClosing(WindowEvent e) {
				System.exit(0);
		  	}
		};

		frmRequirementsComponent.setTitle("Software Estimator");
		frmRequirementsComponent.addWindowListener(theWL);
		frmRequirementsComponent.setLocation(WINDOW.x, WINDOW.y);

		pnlButtons = new JPanel();
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));

		butLoad = new JButton(LOAD);
		butLoad.addActionListener(this);
		pnlButtons.add(butLoad);
		pnlButtons.add(Box.createHorizontalGlue());

		butSave = new JButton(SAVE);
		butSave.addActionListener(this);
		pnlButtons.add(butSave);
		pnlButtons.add(Box.createHorizontalGlue());

		butEstimate = new JButton(ESTIMATE);
		butEstimate.addActionListener(this);
		pnlButtons.add(butEstimate);


		frmRequirementsComponent.getContentPane().setLayout(new BorderLayout());
		frmRequirementsComponent.getContentPane().add(this, BorderLayout.CENTER);
		frmRequirementsComponent.getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		frmRequirementsComponent.pack();
		frmRequirementsComponent.show();

	}

	/**
	* drawIOButtons
	*/
	private void drawIOButtons(JPanel mainPanel){
		butLoad = new JButton(LOAD);
		butLoad.addActionListener(this);
		mainPanel.add(butLoad);

		butSave = new JButton(SAVE);
		butSave.addActionListener(this);
		mainPanel.add(butSave);
	}

	/**
	* setupElicitationChoice - lets the user choose which elicitation
	*                         activities to use
	*/
	private JPanel setupElicitationChoice() {
		JPanel pnlMain;
		JPanel pnlTemp;
		JLabel lblTemp;
		JPanel pnlWrapper;

		pnlMain = createPanel("Elicitation Activites", true);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Please select the elicitation activities to be performed", Color.black, FONT_H2));
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);

		if ( reqData.getKat() )
			cbKAT = new JCheckBox("KAT (Knowledge Analysis of Tasks)", true);
		else
		  	cbKAT = new JCheckBox("KAT (Knowledge Analysis of Tasks)", false);

		cbKAT.setForeground(STANDARD_COLOR);
		cbKAT.addItemListener(this);
		cbKAT.setFont(FONT_REGULAR);

		pnlTemp.add(cbKAT);
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);
		pnlTemp = createPanel(null, false);

		if ( reqData.getJad() )
			cbJAD = new JCheckBox("JAD (Joint Application Development)", true);
		else
			cbJAD = new JCheckBox("JAD (Joint Application Development)", false);

		cbJAD.setForeground(STANDARD_COLOR);
		cbJAD.addItemListener(this);
		cbJAD.setFont(FONT_REGULAR);

		pnlTemp.add(cbJAD);
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlWrapper = createPanel(null, false);
		pnlWrapper.add(pnlMain);
		pnlWrapper.add(Box.createHorizontalGlue());

		return pnlWrapper;
	}


	/**
	* setupKAT - returns the KAT input panel
	*/
	private JPanel setupKAT() {
		JPanel pnlMain;
		JPanel pnlWrapper;
		JPanel pnlTextField;
		JPanel pnlTemp;
		JLabel lblTemp;

		pnlMain = createPanel("KAT Breakdown", true);


		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Surveys", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of Surveys being used", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtKATSurveys = new JTextField(5);
		txtKATSurveys.setMaximumSize(txtKATSurveys.getPreferredSize());
		pnlTemp.add(txtKATSurveys);
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);


		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of questions per survey", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtKATSurveyQuestions = new JTextField(5);
		txtKATSurveyQuestions.setMaximumSize(txtKATSurveyQuestions.getPreferredSize());
		pnlTemp.add(txtKATSurveyQuestions);
		pnlMain.add(pnlTemp);

		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Structured Interview", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of Interviews to be performed", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtKATInterviews = new JTextField(5);
		txtKATInterviews.setMaximumSize(txtKATInterviews.getPreferredSize());
		pnlTemp.add(txtKATInterviews);
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of questions that will be asked in each interview", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtKATInterviewQuestions = new JTextField(5);
		txtKATInterviewQuestions.setMaximumSize(txtKATInterviewQuestions.getPreferredSize());
		pnlTemp.add(txtKATInterviewQuestions);
		pnlMain.add(pnlTemp);

		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Direct Observation", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of observations to be performed", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtKATObservations = new JTextField(5);
		txtKATObservations.setMaximumSize(txtKATObservations.getPreferredSize());
		pnlTemp.add(txtKATObservations);
		pnlMain.add(pnlTemp);


		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Document Review", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of pages of information to be read", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtKATDocumentPages = new JTextField(5);
		txtKATDocumentPages.setMaximumSize(txtKATDocumentPages.getPreferredSize());
		pnlTemp.add(txtKATDocumentPages);
		pnlMain.add(pnlTemp);

		pnlWrapper = createPanel(null, false);
		pnlWrapper.add(pnlMain);
		pnlWrapper.add(Box.createHorizontalGlue());


		return pnlWrapper;
	}

	/**
	* enables or disables all textfields in KAT
	*/
	private void enableKAT(boolean b) {
		txtKATSurveys.setEnabled(b);
		txtKATSurveyQuestions.setEnabled(b);
		txtKATInterviews.setEnabled(b);
		txtKATInterviewQuestions.setEnabled(b);
		txtKATObservations.setEnabled(b);
		txtKATDocumentPages.setEnabled(b);
		useKAT = b;
	}


	/**
	* setupJAD - returns the JAD input panel
	*/
	private JPanel setupJAD() {
		JPanel pnlMain;
		JPanel pnlWrapper;
		JPanel pnlTemp;

		pnlMain = createPanel("JAD Breakdown", true);


		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("JAD Sessions", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of JAD sessions that will be performed", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtJADSessions = new JTextField(5);
		txtJADSessions.setMaximumSize(txtJADSessions.getPreferredSize());
		pnlTemp.add(txtJADSessions);
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Product Definition", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of interviews performed during Product Definition period", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtJADDefinitionInterviews = new JTextField(5);
		txtJADDefinitionInterviews.setMaximumSize(txtJADDefinitionInterviews.getPreferredSize());
		pnlTemp.add(txtJADDefinitionInterviews);
		pnlMain.add(pnlTemp);

		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Research", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of interviews performed during Research period", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtJADResearchInterviews = new JTextField(5);
		txtJADResearchInterviews.setMaximumSize(txtJADResearchInterviews.getPreferredSize());
		pnlTemp.add(txtJADResearchInterviews);
		pnlMain.add(pnlTemp);

		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Sessions", STANDARD_COLOR, FONT_H2));
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of stakeholders persent at JAD meetings", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtJADStakeholders = new JTextField(5);
		txtJADStakeholders.setMaximumSize(txtJADStakeholders.getPreferredSize());
		pnlTemp.add(txtJADStakeholders);
		pnlMain.add(pnlTemp);

		pnlWrapper = createPanel(null, false);
		pnlWrapper.add(pnlMain);
		pnlWrapper.add(Box.createHorizontalGlue());


		return pnlWrapper;
	}

	/**
	* enables or disables all in the JAD panel
	*/
	private void enableJAD(boolean b) {
		txtJADSessions.setEnabled(b);
		txtJADDefinitionInterviews.setEnabled(b);
		txtJADResearchInterviews.setEnabled(b);
		txtJADStakeholders.setEnabled(b);
		useJAD = b;
	}

	/**
	* setupValidation - returns the Validating Requirements panel
	*/
	private JPanel setupValidation() {
		JPanel pnlMain;
		JPanel pnlWrapper;
		JPanel pnlTemp;

		pnlMain = createPanel("Validating Requirements", true);


		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Scenario Tests", STANDARD_COLOR, FONT_H2));
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of scenario tests performed on gathered information", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtScenarios = new JTextField(5);
		txtScenarios.setMaximumSize(txtScenarios.getPreferredSize());
		pnlTemp.add(txtScenarios);
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Prototype Tests", STANDARD_COLOR, FONT_H2));
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Number of prototype tests performed on gathered information", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtPrototypeTests = new JTextField(5);
		txtPrototypeTests.setMaximumSize(txtPrototypeTests.getPreferredSize());
		pnlTemp.add(txtPrototypeTests);
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlMain.add(Box.createVerticalStrut(10));

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Candidate Classes", STANDARD_COLOR, FONT_H2));
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);

		pnlTemp = createPanel(null, false);
		pnlTemp.add(createLabel("Enter the number of candidate classes", STANDARD_COLOR, FONT_REGULAR));
		pnlTemp.add(Box.createRigidArea(new Dimension(10, 1)));
		txtCandidateClasses = new JTextField(5);
		txtCandidateClasses.setMaximumSize(txtCandidateClasses.getPreferredSize());
		pnlTemp.add(txtCandidateClasses);
		pnlTemp.add(Box.createHorizontalGlue());
		pnlMain.add(pnlTemp);


		pnlWrapper = createPanel(null, false);
		pnlWrapper.add(pnlMain);
		pnlWrapper.add(Box.createHorizontalGlue());


		return pnlWrapper;
	}

	/**
	* returns a panel containing the results
	*/
	private JPanel setupResults() {
		JPanel pnlMain;
		JPanel pnlButtons;
		JPanel pnlTemp;

		/*
		dpu.setData(reqData);
		reqData = dpu.processData();
		*/

		pnlMain = new JPanel();

		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		pnlMain.add(createLabel("Requirements Estimation Results:", STANDARD_COLOR, FONT_H1));
		double tempEstimatedLOC = ((double)Math.round(reqData.getEstimatedLOC()*10))/10;


		if (tempEstimatedLOC == -1) {
		  	pnlMain.add(createLabel(SPACER + "Estimated lines of Code: unestimated.", STANDARD_COLOR, FONT_REGULAR));
		} else {
		  	pnlMain.add(createLabel(SPACER + "Estimated lines of Code: " + (tempEstimatedLOC), STANDARD_COLOR, FONT_REGULAR));
		}



		pnlMain.add(Box.createVerticalStrut(VERTSPACE));


		if (useKAT) {

			double tempKATHours = ((double)Math.round(reqData.getKATHours()*10))/10;

			if (tempKATHours == -1) {

				pnlMain.add(createLabel(SPACER + "Required KAT Hours: unestimated.", STANDARD_COLOR, FONT_REGULAR));

			} else {

				pnlMain.add(createLabel(SPACER + "Required KAT Hours: " + (tempKATHours), STANDARD_COLOR, FONT_REGULAR));

			}
			pnlMain.add(Box.createVerticalStrut(VERTSPACE));



		}

		if (useJAD) {

			double tempJADHours = ((double)Math.round(reqData.getJADHours()*10))/10;

			if (tempJADHours == -1)  {

				pnlMain.add(createLabel(SPACER + "Required JAD Hours: unestimated.", STANDARD_COLOR, FONT_REGULAR));

			} else {

				pnlMain.add(createLabel(SPACER + "Required JAD Hours: " + (tempJADHours), STANDARD_COLOR, FONT_REGULAR));

			}
			pnlMain.add(Box.createVerticalStrut(VERTSPACE));



		}

		double tempDocumentationHours = ((double)Math.round(reqData.getDocumentationHours()*10))/10;

		if (tempDocumentationHours == -1) {

		  	pnlMain.add(createLabel(SPACER + "Documentation Hours: unestimated.", STANDARD_COLOR, FONT_REGULAR));

		} else {

		  	pnlMain.add(createLabel(SPACER + "Documentation Hours: " + (tempDocumentationHours), STANDARD_COLOR, FONT_REGULAR));

		}

		pnlMain.add(Box.createVerticalStrut(VERTSPACE));


		double tempManHours = ((double)Math.round(reqData.getManHours()*10))/10;

		if (tempManHours == -1) {
		  	pnlMain.add(createLabel(SPACER + "Total number of Man Hours: unestimated.", STANDARD_COLOR, FONT_REGULAR));

		} else {

		  	pnlMain.add(createLabel(SPACER + "Total number of Man Hours: " + (tempManHours), STANDARD_COLOR, FONT_REGULAR));
		}


		return pnlMain;

	}


	/**
	* actionPerformed - called when an action is detected
	*/
	public void actionPerformed(ActionEvent e) {
		boolean isValid = true;
		String strCommand = e.getActionCommand();

		if(strCommand.equals(SAVE)){

			if ( validateTextFieldsForSaving() )
			{

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory( new File(System.getProperty("user.dir")) );
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {

					saveToEstimationData();
					dpu.setData(reqData);
					dpu.storeData(chooser.getSelectedFile().getName());

				} // end if

			} // end if

		} // end if

		if(strCommand.equals(LOAD)){
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory( new File(System.getProperty("user.dir")) );
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				reqData = dpu.retrieveData(chooser.getSelectedFile().getName());
			}

			if(!dpu.loadedCorrect()){
				JOptionPane.showMessageDialog(null, "The file chosen is not valid", "File not loaded", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				dpu.setLoadedCorrect(true);
				load();
			}
		}

		if (strCommand.equals(ESTIMATE)) {
			if ( estimate() ) {
				showResults();
			}
		}
	}

	/**
	* called when a component state is changed
	*/
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(cbKAT)) {
			if (cbKAT.isSelected()) {
				enableKAT(true);
				reqData.setKat(true);
		  	} else {
				enableKAT(false);
				reqData.setKat(false);

			}

		} else if (e.getSource().equals(cbJAD)) {
		  	if (cbJAD.isSelected()) {
				enableJAD(true);
				reqData.setJad(true);

		  	} else {
				enableJAD(false);
				reqData.setJad(false);
		  	}
		}

		setupPanel();
  }


	/**
	* displays the results
	*/
	private void showResults() {
		JDialog dlgResults = new JDialog(frmRequirementsComponent, true);
		JPanel pnlResults = setupResults();
		dlgResults.getContentPane().add(pnlResults);
		dlgResults.pack();
		dlgResults.setLocation((SCREEN.width - dlgResults.getSize().width) / 2,
				   (SCREEN.height - dlgResults.getSize().height) / 2);
		dlgResults.show();
	}

/**
* ensures that all values in fields are correct
*/
public boolean estimate() {

	boolean isValid = true;

	if ( ! isStandAlone )
		if ( ! isAnyFilledIn() )
		{
			// setup text fields with default values
			if ( DEFAULT_KAT_USED )
			{
				// set the kat checkbox on
				enableKAT(true);
				cbKAT.setSelected(true);

				// loop through kat textfields and populate with defaults

				for ( int i = 0; i < MAX_KAT_FIELDS; i++ )
					aryTextKAT[i].setText( aryDefaultsKAT[i] + "" );

			}


			if ( DEFAULT_JAD_USED )
			{
				// set the jad checkbox on
				enableJAD(true);
				cbJAD.setSelected(true);

				// loop through jad textfields and populate with defaults

				for ( int i = 0; i < MAX_JAD_FIELDS; i++ )
					aryTextJAD[i].setText( aryDefaultsJAD[i] + "" );
			}


			// loop through validation textfields and populate with defaults

			for ( int i = 0; i < MAX_VALIDATION_FIELDS; i++ )
				aryTextValidation[i].setText( aryDefaultsValidation[i] + "" );

		} // end if


	isValid = validateTextFieldsForEstimation();

	if (isValid)
		saveToEstimationData();

	dpu.setData(reqData);
	reqData = dpu.processData();
	return isValid;
}

	/**
	* returns a panel containing the results
	*/
	public JPanel getResultsPanel() {
		return setupResults();
	}

	/**
	* loads data into text fields
	*/
	public void load() {
		if ( reqData.getKat() ) {
			enableKAT(true);
			cbKAT.setSelected(true);

			if ( reqData.getNumKATSurveys() != -1 )
				this.txtKATSurveys.setText( reqData.getNumKATSurveys() + "" );

			if ( reqData.getNumKATSurveyQuestions() != -1 )
				this.txtKATSurveyQuestions.setText( reqData.getNumKATSurveyQuestions() + "" );

			if ( reqData.getNumKATInterviews() != -1 )
				this.txtKATInterviews.setText( reqData.getNumKATInterviews() + "");

			if ( reqData.getNumKATInterviewQuestions() != -1 )
				this.txtKATInterviewQuestions.setText( reqData.getNumKATInterviewQuestions() + "");

			if ( reqData.getNumKATObservations() != -1 )
				this.txtKATObservations.setText( reqData.getNumKATObservations() + "");

			if ( reqData.getNumKATObservationsPages() != -1 )
				this.txtKATDocumentPages.setText( reqData.getNumKATObservationsPages() + "");

		} else {

			enableKAT(false);
			cbKAT.setSelected(false);

		} // end if


		if ( reqData.getJad() ) {

			enableJAD(true);
			cbJAD.setSelected(true);

			if ( reqData.getNumJADSessions() != -1 )
				this.txtJADSessions.setText( reqData.getNumJADSessions() + "");

			if ( reqData.getNumJADProductInterviews() != -1 )
				this.txtJADDefinitionInterviews.setText( reqData.getNumJADProductInterviews() + "");

			if ( reqData.getNumJADResearchInterviews() != -1 )
				this.txtJADResearchInterviews.setText( reqData.getNumJADResearchInterviews() + "");

			if ( reqData.getNumJADStakeholdersPresent() != -1 )
				this.txtJADStakeholders.setText( reqData.getNumJADStakeholdersPresent() + "");

		} else {

			enableJAD(false);
			cbJAD.setSelected(false);

		} // end if


		if ( reqData.getNumScenarioTests() != -1 )
			this.txtScenarios.setText( reqData.getNumScenarioTests() + "");

		if ( reqData.getNumPrototypeTests() != -1 )
			this.txtPrototypeTests.setText( reqData.getNumPrototypeTests() + "");

		if ( reqData.getNumCandidateClasses() != -1 )
			this.txtCandidateClasses.setText( reqData.getNumCandidateClasses() + "");

	}



	/**
	* save
	*
	*  params: nothing
	*  returns: nothing
	*
	*  purpose: transfers text field values into the EstimationData object upon
	*			 successful validation of text field values
	*
	*/
	public void save()
	{
		if( validateTextFieldsForSaving() )
			saveToEstimationData();
	}


	/**
	* saveToEstimationData
	*
	*  params: nothing
	*  returns: nothing
	*
	*  purpose: transfers text field values into the EstimationData object
	*
	*  note: this method is ONLY to be called after the successful validation
	*   	  of the text field values which is determined by using the methods:
	* 		  validateTextFieldsForEstimation() or validateTextFieldsForSaving().
	*
	*/
	public void saveToEstimationData()
	{

		// save text field values into EstimationData object
		// use -1 if blank detected

		if (useKAT)
		{
			if ( txtKATSurveys.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumKATSurveys(-1);
			else
				reqData.setNumKATSurveys(Integer.parseInt(txtKATSurveys.getText().trim()));


			if ( txtKATSurveyQuestions.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumKATSurveyQuestions(-1);
			else
				reqData.setNumKATSurveyQuestions(Integer.parseInt(txtKATSurveyQuestions.getText().trim()));


			if ( txtKATInterviews.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumKATInterviews(-1);
			else
				reqData.setNumKATInterviews(Integer.parseInt(txtKATInterviews.getText().trim()));


			if ( txtKATInterviewQuestions.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumKATInterviewQuestions(-1);
			else
				reqData.setNumKATInterviewQuestions(Integer.parseInt(txtKATInterviewQuestions.getText().trim()));


			if ( txtKATObservations.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumKATObservations(-1);
			else
				reqData.setNumKATObservations(Integer.parseInt(txtKATObservations.getText().trim()));

			if ( txtKATDocumentPages.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumKATObservationsPages(-1);
			else
				reqData.setNumKATObservationsPages(Integer.parseInt(txtKATDocumentPages.getText().trim()));
		}

		if (useJAD)
		{
			if ( txtJADSessions.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumJADSessions(-1);
			else
				reqData.setNumJADSessions(Integer.parseInt(txtJADSessions.getText().trim()));

			if ( txtJADDefinitionInterviews.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumJADProductInterviews(-1);
			else
				reqData.setNumJADProductInterviews(Integer.parseInt(txtJADDefinitionInterviews.getText().trim()));

			if ( txtJADResearchInterviews.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumJADResearchInterviews(-1);
			else
				reqData.setNumJADResearchInterviews(Integer.parseInt(txtJADResearchInterviews.getText().trim()));

			if ( txtJADStakeholders.getText().trim().equals(EMPTY_STRING) )
				reqData.setNumJADStakeholdersPresent(-1);
			else
				reqData.setNumJADStakeholdersPresent(Integer.parseInt(txtJADStakeholders.getText().trim()));
		}


		if ( txtScenarios.getText().trim().equals(EMPTY_STRING) )
			reqData.setNumScenarioTests(-1);
		else
			reqData.setNumScenarioTests(Integer.parseInt(txtScenarios.getText().trim()));

		if ( txtPrototypeTests.getText().trim().equals(EMPTY_STRING) )
			reqData.setNumPrototypeTests(-1);
		else
			reqData.setNumPrototypeTests(Integer.parseInt(txtPrototypeTests.getText().trim()));

		if ( txtCandidateClasses.getText().trim().equals(EMPTY_STRING) )
			reqData.setNumCandidateClasses(-1);
		else
			reqData.setNumCandidateClasses(Integer.parseInt(txtCandidateClasses.getText().trim()));

		reqData.setKat(useKAT);
		reqData.setJad(useJAD);

	}




  /**
   * checks to see if any txt boxes have been filled in
   */
  public boolean isAnyFilledIn() {

    boolean isFilled = false;

    if (useKAT) {

	if ( ! txtKATSurveys.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtKATSurveyQuestions.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtKATInterviews.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtKATInterviewQuestions.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtKATObservations.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtKATDocumentPages.getText().trim().equals("") )
		isFilled = true;

    }

    if (useJAD) {

	if ( ! txtJADSessions.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtJADDefinitionInterviews.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtJADResearchInterviews.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtJADStakeholders.getText().trim().equals("") )
		isFilled = true;
    }
	if ( ! txtScenarios.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtPrototypeTests.getText().trim().equals("") )
		isFilled = true;
	if ( ! txtCandidateClasses.getText().trim().equals("") )
		isFilled = true;

	return isFilled;
  }

	/**
	* does nothing for req comp
	*/
	public void getPrevData() {
	 	return;
	}

	/**
	* creates a default panel
	*/
	private JPanel createPanel(String str, boolean bVertical) {
		JPanel pnl;

		pnl = new JPanel();

		if (bVertical) {
		  	pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		} else {
		  	pnl.setLayout(new BoxLayout(pnl, BoxLayout.X_AXIS));
		}
		pnl.setAlignmentY(Box.TOP_ALIGNMENT);
		pnl.setAlignmentX(Box.LEFT_ALIGNMENT);

		if (str != null) {
			  TitledBorder tb = new TitledBorder(null, str, TitledBorder.LEFT, TitledBorder.TOP);
			  tb.setTitleFont(new Font("Arial", Font.BOLD, 12));
			  tb.setTitleColor(STANDARD_COLOR);
			  pnl.setBorder(tb);
		}

		return pnl;

	}

	/**
	* creates a label
	*/
	private JLabel createLabel(String str, Color c, Font f) {
		JLabel lbl = new JLabel(str);
		lbl.setForeground(c);
		lbl.setFont(f);
		return lbl;
	}


	/**
	* starts program in application mode
	*/
	public static void main(String args[]) {
		new RequirementsUI();
	} // end main
} // end class
