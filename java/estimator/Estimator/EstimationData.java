package Estimator;

/**
 * This class will be written to disk when the user selects save.  It will encapsulate all the variables that need to be stored between executions and passed to other components.
 *
 * Notes:
 * 11/02/01 - This file includes data structures for Requirements, Design and Implementation components.
 */
 
 
import java.io.*;

public class EstimationData implements Serializable
{
    
    /**
     * Is the user using JAD?
     */
    protected boolean jad = false;
    
    /**
    * is the user doing KAT?
    */
    protected boolean kat = false;
    
    /**
     * number of JAD sessions to be held
     */
    protected int numJADSessions = -1;
    
    /**
     * number of product interviews to be conducted in JAD
     */
    protected int numJADProductInterviews = -1;
    
    /**
     * number of research interviews to be conducted in JAD
     */
    protected int numJADResearchInterviews = -1;
    
    /**
     * number of stakeholders present at JAD sessions
     */
    protected int numJADStakeholdersPresent = -1;
    
    /**
     * number of surveys to be conducted in KAT
     */
    protected int numKATSurveys = -1;
    
    /**
     * number of questions in the KAT survey
     */
    protected int numKATSurveyQuestions = -1;
    
    /**
     * number of interviews to be conducted in KAT
     */
    protected int numKATInterviews = -1;
    
    /**
     * number of questions in the KAT interviews
     */
    protected int numKATInterviewQuestions = -1;
    
    /**
     * number of observations to be conducted in KAT
     */
    protected int numKATObservations = -1;
    
    /**
     * number of pages the observation will be
     */
    protected int numKATObservationsPages = -1;
    
    /**
     * number of scenario tests to be performed
     */
    protected int numScenarioTests = -1;
    
    /**
     * number of prototype tests to be performed
     */
    protected int numPrototypeTests = 0;
    
    /**
     * will there be a requirements document generated?
     */
    protected boolean requirementsDocument = false;
    
    /**
     * number of candidate classes
     * (this wasn't in the original design but used in the design document)
     * added by Hyun Lim
     */ 
    protected int numCandidateClasses = -1;

    protected double JADHours = -1;

    protected double KATHours = -1;

    protected double estimatedLOC = -1;

    protected double documentationHours = -1;

    protected double manHours = -1;
    
    //------------ Instance Variables from design component-------//

    //Data gathered from Requirements DPU
    //private double numOfCandidateClasses;  /** old design variables **/
    //private double manHoursForRequirements;
    //Data gathered from user
	private double numOfClassDiagrams = -1;
	private double numOfSequenceDiagrams = -1 ;
	private double numOfStateDiagrams = -1;
	private double numOfHighLevelArchModels = -1;
	private double numOfScreenShots = -1;
    private double percentageOfReuse = -1;
    //Data calculated
    private double manHoursForDesign = -1;
    private double numOfRealClasses = -1;
	private double numOfFuncsPerClass = -1;

    
    /** --------Instance Variables for Implementation Component ------- */    
    
    /** Optain from user **/
    private int numLvlOfTesting = -1;
    private int numLvlOfDocumentation = -1;        
    /** Estimated by Componenent **/
    private int numLinesOfCode = -1;
    private double numManMonthsForImplementation = -1;
    private int numOfBugs = -1;
    
    
    /** Constructor **/    
    public EstimationData() 
    {
    }
        
    
    /**
     * returns jad    
     * @@return boolean     
     */
    public boolean getJad() 
    {
	return(this.jad);
    }

    public void setJad(boolean jad){
	this.jad = jad;
    }
    
    /**
     * returns kat    
     * @@return boolean     
     */
    public boolean getKat() 
    {
	return(this.kat);
    }

    public void setKat(boolean kat){
	this.kat = kat;
    }

    /**
     * returns numJADSessions    
     * @@return int     
     */
    public int getNumJADSessions() 
    {
	return(this.numJADSessions);
    }

    public void setNumJADSessions(int numJADSessions){
	this.numJADSessions = numJADSessions;
    }
    
    /**
     * returns numJADProductInterviews    
     * @@return int     
     */
    public int getNumJADProductInterviews() 
    {
	return(this.numJADProductInterviews);
    }

    public void setNumJADProductInterviews(int numJADProductInterviews){
	this.numJADProductInterviews = numJADProductInterviews;
    }
    
    /**
     * returns numJADResearchInterviews    
     * @@return int     
     */
    public int getNumJADResearchInterviews() 
    {
	return(this.numJADResearchInterviews);
    }

    public void setNumJADResearchInterviews(int numJADResearchInterviews){
	this.numJADResearchInterviews = numJADResearchInterviews;
    }
    
    /**
     * returns numJADStakeHoldersPresent    
     * @@return int     
     */
    public int getNumJADStakeholdersPresent() 
    {
	return(this.numJADStakeholdersPresent);
    }

    public void setNumJADStakeholdersPresent(int numJADStakeholdersPresent){
	this.numJADStakeholdersPresent = numJADStakeholdersPresent;
    }
    
    /**
     * returns numKATSurveys    
     * @@return int     
     */
    public int getNumKATSurveys() 
    {
	return(this.numKATSurveys);
    }

    public void setNumKATSurveys(int numKATSurveys){
	this.numKATSurveys = numKATSurveys;
    }

    public void setNumKATSurveyQuestions(int numKATSurveyQuestions){
	this.numKATSurveyQuestions = numKATSurveyQuestions;
    }


    /**
     * returns numKATSurveyQuestions 
     * @@return int     
     */
    public int getNumKATSurveyQuestions() 
    {
	return(this.numKATSurveyQuestions);
    }



    /**
     * returns numKATInterviews    
     * @@return int     
     */
    public int getNumKATInterviews() 
    {
	return(this.numKATInterviews);
    }

    public void setNumKATInterviews(int numKATInterviews){
	this.numKATInterviews = numKATInterviews;
    }
    
    /**
     * returns numKATInterviewQuestions    
     * @@return int     
     */
    public int getNumKATInterviewQuestions() 
    {
	return(this.numKATInterviewQuestions);
    }

    public void setNumKATInterviewQuestions(int numKATInterviewQuestions){
	this.numKATInterviewQuestions = numKATInterviewQuestions;
    } 
    
    /**
     * returns numKATObservations    
     * @@return int     
     */
    public int getNumKATObservations() 
    {
	return(this.numKATObservations);
    }

    public void setNumKATObservations(int numKATObservations){
	this.numKATObservations = numKATObservations;
    }
    
    /**
     * returns numKATObservationsPages    
     * @@return int     
     */
    public int getNumKATObservationsPages() 
    {
	return(this.numKATObservationsPages);
    }

    public void setNumKATObservationsPages(int numKATObservationsPages){
	this.numKATObservationsPages = numKATObservationsPages;
    }
    
    /**
     * returns numScenarioTests    
     * @@return int
     */
    public int getNumScenarioTests() 
    {
	return(this.numScenarioTests);
    }

    public void setNumScenarioTests(int numScenarioTests){
	this.numScenarioTests = numScenarioTests;
    }
    
    /**
     * returns numPrototypeTests    
     * @@return int
     */
    public int getNumPrototypeTests() 
    {
	return(this.numPrototypeTests);
    }

    public void setNumPrototypeTests(int numPrototypeTests){
	this.numPrototypeTests = numPrototypeTests;
    }

    /**
     * returns requirementsDocument    
     * @@return boolean
     */
    public boolean getRequirementsDocument() 
    {
	return(this.requirementsDocument);
    }

    public void setRequirementsDocument(boolean requirementsDocument){
	this.requirementsDocument = requirementsDocument;
    }

    public int getNumCandidateClasses(){
	return(this.numCandidateClasses);
    }

    public void setNumCandidateClasses(int numCandidateClasses){
	this.numCandidateClasses = numCandidateClasses;
    }

    public double getJADHours(){
	return(this.JADHours);
    }

    public void setJADHours(double JADHours){
	this.JADHours = JADHours;
    }

    public double getKATHours(){
	return(this.KATHours);
    }

    public void setKATHours(double KATHours){
	this.KATHours = KATHours;
    }

    public double getEstimatedLOC(){
	return(this.estimatedLOC);
    }

    public void setEstimatedLOC(double estimatedLOC){
	this.estimatedLOC = estimatedLOC;
    }

    public double getDocumentationHours(){
	return(this.documentationHours);
    }

    public void setDocumentationHours(double documentationHours){
	this.documentationHours = documentationHours;
    }

    public double getManHours(){
	return(this.manHours);
    }

    public void setManHours(double manHours){
	this.manHours = manHours;
    }


	/***
	* Stuff from the design Component system
	* 
	*/




	

	/**
	 * Defaults all data to 0
	 */
	public void DesignInit(){
	    //all variables set to 0 as defualt values
        //numOfCandidateClasses = -1; old design variable
        //numCandidateClasses = -1; 
        manHours = -1;
        //manHoursForRequirements = -1; old design variable
        manHoursForDesign = -1;
	    numOfRealClasses = -1;
	    numOfFuncsPerClass = -1;
	    percentageOfReuse = -1;
	    numOfClassDiagrams = -1;
	    numOfSequenceDiagrams = -1;
	    numOfStateDiagrams = -1;
	    numOfHighLevelArchModels = -1;
	    numOfScreenShots = -1;
	}//end EstimatorData()

	//------------ Accessors -----------------//

	/**
	 * Accesses the total number of man hours for Design.
	 *
	 * @@param none
	 * @@return double - The total number of man hours for design
	 */
	public double getDesignManHours(){
	  return manHoursForDesign;
	}//end getManHours()

	/**
	 * Accesses the total number of real classes.
	 *
	 * @@param none
	 * @@return double - The total number of real classes
	 */
	public double getRealClasses(){
	  return numOfRealClasses;
	}//end getRealClasses()

	/**
	 * Accesses the average number of functions per class.
	 *
	 * @@param none
	 * @@return double - The average number of functions per class
	 */
	public double getClassFuncs(){
	  return numOfFuncsPerClass;
	}//end getClassFuncs()

	/**
	 * Accesses the percentage of reuse of the project.
	 *
	 * @@param none
	 * @@return double - The percentage of reuse
	 */
	public double getReuse(){
          return percentageOfReuse;
	}//end getReuse()

	/**
	 * Accesses the number of class diagrams.
	 *
	 * @@param none
	 * @@return double - The number of class diagrams
	 */
	public double getClassDiag(){
	  return numOfClassDiagrams;
	}//end getClassDiag()

	/**
	 * Accesses the number of sequence diagrams.
	 *
	 * @@param none
	 * @@return double - The number of sequence diagrams
	 */
	public double getSeqDiag(){
	  return numOfSequenceDiagrams;
	}//end getSeqDiag()

	/**
	 * Accesses the number of state diagrams.
	 *
	 * @@param none
	 * @@return double - The number of state diagrams
	 */
	public double getStateDiag(){
          return numOfStateDiagrams;
	}//end getStateDiag()

        /**
	 * Accesses the number of man-hours spent on requirements
	 *
	 * @@param none
	 * @@return double - The number of man-hours spent on requirements
	 */
	public double getRequirementsManHours(){
          return manHours;
	}//end getRequirementsManHours()

        /**
	 * Accesses the number of candidate classes.
	 *
	 * @@param none
	 * @@return double - The number of candidate classes
	 */
	public double getCandidateClasses(){
          return numCandidateClasses;
	}//end getCandidateClasses()

	/**
	 * Accesses the number of high level architecture models.
	 *
	 * @@param none
	 * @@return double - The number of high level architecture models
	 */
	public double getHLArchModels(){
	  return numOfHighLevelArchModels;
	}//end getHLArchModels()

	/**
	 * Accesses the number of screen shots.
	 *
	 * @@param none
	 * @@return double - The number of screen shots
	 */
	public double getScreenShots(){
          return numOfScreenShots;
	}//end getScreenShots()

	//------------ Modifiers ----------------//

	/**
	 * Modifies the total number of man hours.
	 *
	 * @@param double newHours - The new total number of man hours
	 * @@return none
	 */
	public void setDesignManHours(double newHours){
          manHoursForDesign = newHours;

	}//end setManHours(double)

	/**
	 * Modifies the total number of real classes.
	 *
	 * @@param double newRClasses - The new total number of real classes
	 * @@return none
	 */
	public void setRealClasses(double newRClasses){

            numOfRealClasses =  newRClasses;

	}//end setRealClasses(double)

	/**
	 * Modifies the average number of functions per class.
	 *
	 * @@param double newFPClasses - The new average number of functions per class
	 * @@return none
	 */
	public void setClassFuncs(double newFPClasses){

            numOfFuncsPerClass = newFPClasses;

	}//end setClassFuncs(double)

	/**
	 * Modifies the percentage of reuse of the project.
	 *
	 * @@param double newPerReuse - The new percentage of reuse
	 * @@return none
	 */
	public void setReuse(double newPerReuse){

	    percentageOfReuse = newPerReuse;

	}//end setReuse(double)

	/**
	 * Modifies the number of class diagrams.
	 *
	 * @@param double newClassDiag - The new number of class diagrams
	 * @@return none
	 */
	public void setClassDiag(double newClassDiag){

	    numOfClassDiagrams = newClassDiag;

	}//end setClassDiag(double)

	/**
	 * Modifies the number of sequence diagrams.
	 *
	 * @@param double newSeqDiag - The new number of sequence diagrams
	 * @@return none
	 */
	public void setSeqDiag(double newSeqDiag){

            numOfSequenceDiagrams = newSeqDiag;

	}//end setSeqDiag(double)

	/**
	 * Modifies the number of state diagrams.
	 *
	 * @@param double newStateDiag - The new number of state diagrams
	 * @@return none
	 */
	public void setStateDiag(double newStateDiag){

            numOfStateDiagrams = newStateDiag;

	}//end setStateDiag(double)

    	/**
         * Modifies the number of high level architecture models.
	 *
	 * @@param double newHLArchModel - The new number of high level architecture models
	 * @@return none
	 */
	public void setHLArchModels(double newHLArchModel){

            numOfHighLevelArchModels = newHLArchModel;

	}//end setHLArchModels(double)

	/**
	 * Modifies the number of screen shots.
	 *
	 * @@param double newScreenShots - The new number of screen shots
	 * @@return none
	 */
	public void setScreenShots(double newScreenShots){

            numOfScreenShots = newScreenShots;

	}//end setScreenShots(double)

        /**
	 * Modifies the number of man hours for requirements.
	 *
	 * @@param double newManHours - The new number of man hours for requirements
	 * @@return none
	 */
	public void setRequirementsManHours(double newManHours){

            manHours = newManHours;

	}//end setRequirementsManHours(double)

        /**
	 * Modifies the number of candidate classes
	 *
	 * @@param double newCandidateClasses - The new number of candidate classes
	 * @@return none
	 */
	public void setCandidateClasses(double newCandidateClasses){

            numCandidateClasses = new Double(newCandidateClasses).intValue();

	}//end setCandidateClasses(double)
	
	
	
	
	
	
	
	
	
	/*
	 *	Implementation Component stuff
	 *
	 */
	
	
	/** Set all Implemenation variables to -1 */
	public void ImplementationInit(){
	    
        /** Optain from user **/
        numLvlOfTesting = -1;
        numLvlOfDocumentation = -1;
        
        /** Estimated by Componenent **/
        numLinesOfCode = -1;
        numManMonthsForImplementation = -1;
        numOfBugs = -1;                
	}
	
	/** ------ Accessors  ---- */
	
    /**
	 * Accesses the total number of man months.
	 * 
	 * @@param none
	 * @@return int - The total number of man hours 
	 */
	public double getImplManMonths(){
	    return(this.numManMonthsForImplementation);	    
	}//end getManHours()
	
	/**
	 * Accesses the total number of classes.
	 * 
	 * @@param none
	 * @@return int - The total number of classes 
	 */
	public double getDesignClasses(){
		return(getRealClasses());		
	}//end getClasses()
	
	/**
	 * Accesses the average number of methods per class.
	 * 
	 * @@param none
	 * @@return int - The average number of methods per class 
	 */
	public double getDesignMethods(){
	    return(getClassFuncs());
	}//end getMethods()
	
	/**
	 * Accesses the lines of code.
	 * 
	 * @@param none
	 * @@return int - The lines of code
	 */
	public int getLinesOfCode(){
	    return(this.numLinesOfCode);	
	}//end getLOC()
	
/**
	 * Accesses the average number of bugs/1k LOC
	 * 
	 * @@param none
	 * @@return int - The average number bugs/1k LOC
	 */
	public int getBugs(){
	    return(this.numOfBugs);	
	}//end getBugs
	

	/**
	 * Accesses the level of documentation to be done
	 * 
	 * @@param none
	 * @@return int - The level of documentation to be done
	 */
	public int getLvlDoc(){
	    return(this.numLvlOfDocumentation);
	}//end getLvlDoc
	
	/**
	 * Accesses the level of testing to be done
	 * 
	 * @@param none
	 * @@return int - The level of testing to be done
	 */
	public int getLvlTesting() {
	    return(this.numLvlOfTesting);
	}//end getLvlTesting()


    /** ---------- Modifiers ----------------/
    
    	/**
	*  Modifies the # of bugs/1k LOC
	*  
	* @@param int newBugs - The new # of bugs/1k LOC
	* @@return none
	*/
	public void setBugs(int newBugs){
        numOfBugs = newBugs;
	}//end setBugs(int)
	

	/**
	*  Modifies the lines of code
	*  
	* @@param int newLOC - The new lines of code
	* @@return none
	*/
	public void setLOC(int newLOC){
        numLinesOfCode = newLOC;
	}//end setLOC(int)


/**
	*  Modifies the level of documentation to be done
	*  
	* @@param int newLvlTesting - The new level of documentation to be done
	* @@return none
	*/
	public void setLvlDoc(int newLvlDoc){
        numLvlOfDocumentation = newLvlDoc;
	}//end setLvlDoc(int)


/**
	*  Modifies the level of testing to be done
	*  
	* @@param int newLvlTesting - The new level of testing to be done
	* @@return none
	*/
	public void setLvlTesting(int newLvlTesting){
        numLvlOfTesting = newLvlTesting;
	}//end setLvlTesting(int)

	/**
	 * Modifies the total number of man hours.
	 * 
	 * @@param int newHours - The new total number of man hours
	 * @@return none
	 */
	public void setManMonths(double newMonths){
		numManMonthsForImplementation = newMonths;
	}//end setManHours(int)
	
	/**
	 * Modifies the total number of classes.
	 * 
	 * @@param int newClasses - The new total number of classes 
	 * @@return none
	 */
	public void setDesignClasses(double newClasses){
		setRealClasses(newClasses);
	}//end setClasses(int)
	
	/**
	 * Modifies the number of methods.
	 * 
	 * @@param int newMethods - The new number of methods
	 * @@return none
	 */
	public void setDesignMethods(double newMethods){
		setClassFuncs(newMethods);
	}//end setMethods(int)
	
		
	
	
	/**-------------------------------------------------------------- */

	/**
 	 * Serializes the EstimationData to a
 	 * file.
 	 *
 	 * @@param String name - The filename to which
 	 * the serialized object will be saved.
 	 *
 	 * @@return void
 	 */
 	public void save(String name){
          try{
            FileOutputStream ostream = new FileOutputStream(name);
	    ObjectOutputStream p = new ObjectOutputStream(ostream);

	    p.writeObject(this);

	    p.flush();
	    ostream.close();

            

          }
          catch(Exception e) {System.out.print("file write ERROR save: " + e + "\n");}
 	}



/* From the testing EstimationData

	This may have created some integration erros.

 */

   private double numOfTestCasesCreated = -1;
   private boolean bigBangTestingUsed = false;
   private boolean bottomUpTestingUsed = false;
   private boolean topDownTestingUsed = false;
   private boolean sandwichTestingUsed = false;
   private boolean modifiedSandwichTestingUsed = false;
   private boolean functionalTestingUsed = false;
   private boolean performanceTestingUsed = false;
   private boolean pilotTestingUsed = false;
   private boolean acceptanceTestingUsed = false;
   private boolean installationTestingUsed = false;
   private double estimatedBugsPer1000LOC = -1;
   private double codingManHours = -1;

   private double integrationTestingHours = -1;
   private double systemTestingHours = -1;
   private double testingTotalHours = -1;
   private double avgBugsAfterTesting = -1;


    public double getCodingManHours ()
    {
      return this.numManMonthsForImplementation;
    }

    public void setCodingManHours (double codingManHours)
    {
      this.numManMonthsForImplementation = codingManHours;
    }

    public double getEstimatedBugsPer1000LOC ()
    {
      return this.numOfBugs;
    }

    public void setEstimatedBugsPer1000LOC(double estimatedBugsPer1000LOC)
    {
      this.numOfBugs = new Double(estimatedBugsPer1000LOC).intValue();
    }

   /**
    * @@return Integer
    * @@roseuid 3BF3498C016F
    */
    public double getNumOfTestCasesCreated()
    {
      return this.numOfTestCasesCreated;
    }

    public void setNumofTestCasesCreated(double numOfTestCasesCreated)
    {
      this.numOfTestCasesCreated = numOfTestCasesCreated;
    }


   /**
    * @@return Boolean
    * @@roseuid 3BF349970330
    */
    public boolean getBigBangTestingUsed()
    {
      return this.bigBangTestingUsed;
    }

    public void setBigBangTestingUsed(boolean bigBangTestingUsed)
    {
      this.bigBangTestingUsed = bigBangTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF3499F0021
    */
    public boolean getBottomUpTestingUsed()
    {
      return this.bottomUpTestingUsed;
    }

    public void setBottomUpTestingUsed(boolean bottomUpTestingUsed)
    {
      this.bottomUpTestingUsed = bottomUpTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349A9011D
    */
    public boolean getTopDownTestingUsed()
    {
      return this.topDownTestingUsed;
    }

    public void setTopDownTestingUsed(boolean topDownTestingUsed)
    {
      this.topDownTestingUsed = topDownTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349B20342
    */
    public boolean getSandwichTestingUsed()
    {
      return this.sandwichTestingUsed;
    }

    public void setSandwichTestingUsed(boolean sandwichTestingUsed)
    {
      this.sandwichTestingUsed = sandwichTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349BC0062
    */
    public boolean getModifiedSandwichTestingUsed()
    {
      return this.modifiedSandwichTestingUsed;
    }

    public void setModifiedSandwichTestingUsed(boolean modifiedSandwichTestingUsed)
    {
      this.modifiedSandwichTestingUsed = modifiedSandwichTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349C700C5
    */
    public boolean getFunctionalTestingUsed()
    {
      return this.functionalTestingUsed;
    }

    public void setFunctionalTestingUsed(boolean functionalTestingUsed)
    {
      this.functionalTestingUsed = functionalTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349D003AE
    */
    public boolean getPerformanceTestingUsed()
    {
      return this.performanceTestingUsed;
    }

    public void setPerformanceTestingUsed(boolean performanceTestingUsed)
    {
      this.performanceTestingUsed = performanceTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349D8007F
    */
    public boolean getPilotTestingUsed()
    {
      return this.pilotTestingUsed;
    }

    public void setPilotTestingUsed(boolean pilotTestingUsed)
    {
      this.pilotTestingUsed = pilotTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349DF0001
    */
    public boolean getAcceptanceTestingUsed()
    {
      return this.acceptanceTestingUsed;
    }

    public void setAcceptanceTestingUsed(boolean acceptanceTestingUsed)
    {
      this.acceptanceTestingUsed = acceptanceTestingUsed;
    }

   /**
    * @@return Boolean
    * @@roseuid 3BF349E6000E
    */
    public boolean getInstallationTestingUsed()
    {
      return this.installationTestingUsed;
    }

    public void setInstallationTestingUsed(boolean installationTestingUsed)
    {
      this.installationTestingUsed = installationTestingUsed;
    }

    public double getIntegrationTestingHours ()
    {
      return this.integrationTestingHours;
    }

    public void setIntegrationTestingHours (double integrationTestingHours)
    {
      this.integrationTestingHours = integrationTestingHours;
    }

    public double getSystemTestingHours ()
    {
      return this.systemTestingHours;
    }

    public void setSystemTestingHours (double systemTestingHours)
    {
      this.systemTestingHours = systemTestingHours;
    }

    public double getTestingTotalHours ()
    {
      return this.testingTotalHours;
    }

    public void setTestingTotalHours (double testingTotalHours)
    {
      this.testingTotalHours = testingTotalHours;
    }

    public double getAvgBugsAfterTesting ()
    {
      return this.avgBugsAfterTesting;
    }

    public void setAvgBugsAfterTesting (double avgBugsAfterTesting)
    {
      this.avgBugsAfterTesting = avgBugsAfterTesting;
    }





}