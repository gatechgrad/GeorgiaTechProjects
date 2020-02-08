/*-
 * Classname DesignDataProcessingUnit
 * 1.0
 * 10/21/2001
 * Don't steal my code.
*/

package DesignComponent;
import java.io.*;
import java.util.*;
import Estimator.*;

public class DesignDataProcessingUnit{
        //------------ DEBUGGING --------------//
        private static final boolean DEBUG = false;
        private static final boolean DEBUG_DATA = false;
	//------------ Constants --------------//
        public static final double AVGTIMEPERCANDIDATECLASS = 15;
	public static final double TIMEPERSCREENSHOT = 4;
        public static final double DIFFICULTYCLASSDIAGRAM = 0.75;
        public static final double DIFFICULTYSEQDIAGRAM = 0.33;
        public static final double DIFFICULTYSTATEDIAGRAM = 0.50;
        public static final double DIFFICULTYHLADIAGRAM = 0.75;
        public static final double REALCANDIATEPERCENTAGE = 0.20;
        public static final double AVGFUNCTIONSPERCLASS = 15;
        public static final double REUSE_UPGRADE = 0.20;
        public static final double REUSE_RECREATE = 0.25;
        public static final double REUSE_SCRATCH = 0.10;

	//------------ Static Variables ---------//



	//------------ Instance Variables -------//
          private EstimationData data = null;
          /*
          //Data gathered from Requirements DPU
          private double numOfCandidateClasses;
          private double manHoursForRequirements;
          //Data gathered from user
	  private double numOfClassDiagrams;
	  private double numOfSequenceDiagrams;
	  private double numOfStateDiagrams;
	  private double numOfHighLevelArchModels;
	  private double numOfScreenShots;
          private double percentageOfReuse;
          //Data calculated
          private double manHoursForDesign;
          private double numOfRealClasses;
	  private double numOfFuncsPerClass;
          */

	//------------ Constructors -------------//

	/**
	 * Processes the design data entered by the user and
	 * the data given by the Requirements Component.
	 * If there is no data from the requirements component,
	 * a -1 value is passed and the data is gathered from the user.
	 */
	public DesignDataProcessingUnit(double candidateClasses, double reqManHours){

          data = new EstimationData();

          data.setCandidateClasses(candidateClasses);
          data.setRequirementsManHours(reqManHours);

	}//end DesignDataProcessingUnit(double, double)

        /**
	 * Processes the design data entered by the user and
	 * the data given by the Requirements Component.
	 * If there is no data from the requirements component,
	 * a -1 value is passed and the data is gathered from the user.
	 */
	public DesignDataProcessingUnit(){
          data = new EstimationData();

          data.setCandidateClasses(-1);
          data.setRequirementsManHours(-1);
	}//end DesignDataProcessingUnit()

	public DesignDataProcessingUnit (EstimationData myData) {
		data = myData;	
	}


	//------------ Accessors -----------------//

	/**
	 * Accesses the EstimationData object being used.
	 *
	 * @param none
	 * @return EstimationData - reference to the object containing data
	 */
	public EstimationData getEstimator(){
          return data;
	}//end getEstimator()
    
    
    

        /**
	 * Modifies the EstimationData object being used.
	 *
	 * @param EstimationData newData - the new estimator data to be used
	 * @return void
	 */
	public void setEstimator(EstimationData newData){
          data = newData;
	}//end getEstimator()
	//------------ Methods ------------------//
        /**
	* This method takes the values in the instance variables and the
	* values in the constants and computes the result values. The
	* formulas associated with these calculations are:
	*
	*		Developing class diagrams
  	*		Making sequence diagrams
	*		Making state diagrams
	*		Making High Level Architecture Diagrams
	*	+	Making Screen Shots
	*	-----------------------------------------------------------
	*		Man-hours needed to develop the complete design
	*
	*
	*	Man hours needed to produce an artifact of TYPE =
	*		# real classes *
	*		difficulty level of development of artifact TYPE *
	*		quality of requirements
	*
	*
	*	Difficulty ratings of different types of the  different
	*	types of artifacts in the system.  Should be read as:
	*	1 man can make an artifact, of this type, which models
	*	one real class in this fraction of an hour.
	*		Class diagram: ¾ hour
	*		Sequence Diagram: 1/3 hour
	*		State Diagram: ½ hour
	*		High Level Architecture Diagram: ¾ hour
	*
	*	Quality of requirements specification =
	*	(# Candidate classes * 15 hours) /
	*	Total man hours spent on requirements development
	*
	*	Time it takes to make screen shots:
	*		4 man-hours per screen shot
        *
	*
	*	# of Real Classes = # Candidate classes * 20%
	*
	*
	* 	Functions per class =	15
	*
	*	The user chooses which most accurately describes
	* 	their program and then the simulator makes an estimate as
	*	to what percentage of reuse is typical.
	*		Upgrading an existing system: 20%
	*		Recreating a system: 25%
	*		Starting from scratch: 10%
	*
	*
	*
	*@param note
	*@return void
        */
	public void estimate() {
          //variable declaration
          double quality;
          double classDiagTime;
          double seqDiagTime;
          double stateDiagTime;
          double hlaModelTime;
          double screenShotTime;


          //initial calculations
         /*Check for errors in man hours spent on requirements
         and # of candidate classes */
         if((data.getCandidateClasses() < 0 ) || (data.getRequirementsManHours() < 0)){
         //Set error values

          data.setRealClasses(-1);
          data.setClassFuncs(-1);
          data.setReuse(-1);
          data.setDesignManHours(-1);
          System.out.print("\nDPU Error: invalid number of Candidate Classes or Man hours for Requirements\n\n");

         }//end if
         else{
          data.setRealClasses(data.getCandidateClasses() * REALCANDIATEPERCENTAGE);
          quality = (data.getCandidateClasses() * AVGTIMEPERCANDIDATECLASS)/data.getRequirementsManHours();
		 //data.setClassFuncs(AVGFUNCTIONSPERCLASS);
		 if (data.getSeqDiag() < 3) {
		 	data.setClassFuncs(AVGFUNCTIONSPERCLASS);
		 }
		 else {
		 	data.setClassFuncs(Math.round((.75) * data.getSeqDiag()));
		}
		 
		 

          if(DEBUG){
            System.out.print("\nTEST:\tReal Classes =\t\t" + data.getRealClasses() + "\n" +
              "\tQuality of Req =\t\t" + quality + "\n" +
              "\t# Class Diagrams =\t" + data.getClassDiag() + "\n" +
              "\t# Sequence Diagrams =\t" + data.getSeqDiag() + "\n" +
              "\t# State Diagrams =\t" + data.getStateDiag() + "\n" +
              "\t# HighLevelArch Models =\t" + data.getHLArchModels() + "\n" +
              "\t# Screen Shots =\t" + data.getScreenShots() + "\n" );
          }
          //time for each type of diagram
          classDiagTime = data.getClassDiag() * data.getRealClasses() * quality * DIFFICULTYCLASSDIAGRAM;
          seqDiagTime = data.getSeqDiag() * data.getRealClasses() * quality * DIFFICULTYSEQDIAGRAM;
          stateDiagTime = data.getStateDiag() * data.getRealClasses() * quality * DIFFICULTYSTATEDIAGRAM;
          hlaModelTime = data.getHLArchModels() * data.getRealClasses() * quality * DIFFICULTYHLADIAGRAM;

          //time for screen shots
          screenShotTime = data.getScreenShots() * TIMEPERSCREENSHOT;

          if(DEBUG){
            System.out.print("\tTime for:\tClass Diagrams =\t" + classDiagTime + "\n" +
              "\t\tSequence Diagrams =\t" + seqDiagTime+ "\n" +
              "\t\tState Diagrams =\t" + stateDiagTime + "\n" +
              "\t\tHighLevelArch Models =\t" + hlaModelTime + "\n" +
              "\t\tScreen Shots =\t\t" + screenShotTime + "\n");
          }

          //estimation of total man hours for design
          data.setDesignManHours(classDiagTime + seqDiagTime +
            stateDiagTime + hlaModelTime + screenShotTime);

          if(DEBUG){
            System.out.print("\tTotal Man Hours For Design =\t" + data.getDesignManHours() + "\n" +
            "----------------------------------" + "\n\n");
          }
         }//end else

	}//estimate ()

	/**
 	 * Serializes the DesignDataProcessingUnit to a
 	 * file.
 	 *
 	 * @param String name - The filename to which
 	 * the serialized object will be saved.
 	 *
 	 * @return void
 	 */
 	public void save(String name){
          try{
            FileOutputStream ostream = new FileOutputStream(name);
	    ObjectOutputStream p = new ObjectOutputStream(ostream);

	    p.writeObject(data);

	    p.flush();
	    ostream.close();

            if(DEBUG){
              System.out.print("File write successful\n");
            }

          }
          catch(Exception e) {System.out.print("file write ERROR save: " + e + "\n");}
 	}

	/**
 	 * Loads a serialized DesignDataProcessingUnit from a
 	 * file.
 	 *
 	 * @param String name - The filename from which
 	 * the serialized object will be loaded.
 	 *
 	 * @return void
 	 */
 	public void load(String name){
          try{
    	    FileInputStream istream = new FileInputStream(name);
	    ObjectInputStream p = new ObjectInputStream(istream);

	    data = (EstimationData)p.readObject();

	    istream.close();

            if(DEBUG){
              System.out.print("File read successful\n" + data);
            }

          }
          catch(Exception e){
            System.out.print("file read ERROR: " + e);}
 	  }

  //------------------ Main ------------------//
  /**
   * DEBUGGING MAIN
   */
  public static void main(String[] argv){
    double cClassesTest  = 20;
    double rManHoursTest = 300;
    DesignDataProcessingUnit myDPU =
      new DesignDataProcessingUnit(cClassesTest, rManHoursTest);

    //check for error checking in modifier methods
    if(DEBUG_DATA){
      myDPU.getEstimator().setCandidateClasses(-1);
      myDPU.getEstimator().setRequirementsManHours(-1);
      myDPU.getEstimator().setClassDiag(-1);
      myDPU.getEstimator().setSeqDiag(-1);
      myDPU.getEstimator().setStateDiag(-1);
      myDPU.getEstimator().setHLArchModels(-1);
      myDPU.getEstimator().setScreenShots(-1);
      myDPU.getEstimator().setReuse(-1);
    }
    else{
      myDPU.getEstimator().setClassDiag(2);
      myDPU.getEstimator().setSeqDiag(4);
      myDPU.getEstimator().setStateDiag(3);
      myDPU.getEstimator().setHLArchModels(1);
      myDPU.getEstimator().setScreenShots(9);
      myDPU.getEstimator().setReuse(DesignDataProcessingUnit.REUSE_SCRATCH);
    }

	EstimationData data = myDPU.getEstimator();
    myDPU.estimate();
    System.out.print("Total Man Hours:\t" + data.getDesignManHours() +
      "\nRealClasses:\t\t" + data.getRealClasses() +
      "\nFunctions per Class:\t" + data.getClassFuncs() +
      "\nReuse:\t\t" + data.getReuse() + "\n\n");
    myDPU.save("alexDPU.txt");
    myDPU.load("alexDPU.txt");

  }

}//end DesignDataProcessingUnit