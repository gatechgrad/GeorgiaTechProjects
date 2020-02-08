/*-
 * Classname TestDataProcessingUnit
 * 1.0
 * 11/17/2001
*/

package TestComponent;
import java.io.*;
import java.util.*;
import Estimator.*;

public class TestDataProcessingUnit{
        //------------ DEBUGGING --------------//
        private static final boolean DEBUG = false;
        private static final boolean DEBUG_DATA = false;
	//------------ Constants --------------//
        public static final double BBWEIGHT = 2;
        public static final double TDWEIGHT = 1.5;
        public static final double BUWEIGHT = 1.5;
        public static final double SWEIGHT = 1.5;
        public static final double MSWEIGHT = 1;
        public static final double FTWEIGHT = 0.2;
        public static final double PFTWEIGHT = 0.3;
        public static final double PLTWEIGHT = 0.3;
        public static final double ATWEIGHT = 0.4;
        public static final double ITWEIGHT = 0.3;
        public static final int TESTCASECREATETIME = 2;

	//------------ Instance Variables -------//
          private EstimationData data = null;


	//------------ Constructors -------------//

	/**
	 * Processes the testing data entered by the user and
	 * the data given by the Implementation Component.
	 * If there is no data from the implementation component,
	 * a -1 value is passed and the data is gathered from the user.
	 */
	public TestDataProcessingUnit(double codingManHours, double estimatedBugs)
        {

          data = new EstimationData();

          data.setCodingManHours(codingManHours);
          data.setEstimatedBugsPer1000LOC(estimatedBugs);

	}//end TestDataProcessingUnit(double, double)

        /**
	 * Processes the testing data entered by the user and
	 * the data given by the Implementation Component.
	 * If there is no data from the Implementation component,
	 * a -1 value is passed and the data is gathered from the user.
	 */
	public TestDataProcessingUnit()
        {
          data = new EstimationData();

          data.setCodingManHours(-1);
          data.setEstimatedBugsPer1000LOC(-1);
	}//end TestDataProcessingUnit()

	public TestDataProcessingUnit (EstimationData myData)
        {
		data = myData;
	}


	//------------ Accessors -----------------//

	/**
	 * Accesses the EstimationData object being used.
	 *
	 * @param none
	 * @return EstimationData - reference to the object containing data
	 */
	public EstimationData getEstimator()
        {
          return data;
	}//end getEstimator()


        /**
	 * Modifies the EstimationData object being used.
	 *
	 * @param EstimationData newData - the new estimator data to be used
	 * @return void
	 */
	public void setEstimator(EstimationData newData)
        {
          data = newData;
	}//end setEstimator()
	//------------ Methods ------------------//

    public void integrationTestingHours()
    {
    // ***************************************************************
    // SETTING CONSTANTS
    // ***************************************************************

      int usedBigBang = 0;
      int usedTopDown = 0;
      int usedBottomUp = 0;
      int usedSandwich = 0;
      int usedModifiedSandwich = 0;

    // *****************************************************************
    // SETTING THE APPROPRIATE VALUES BASED ON WHAT WAS SELECTED
    // *****************************************************************
      if (data.getBigBangTestingUsed() == true)
      {
        usedBigBang = 1;
      }
      if (data.getTopDownTestingUsed() == true)
      {
        usedTopDown = 1;
      }
      if (data.getBottomUpTestingUsed() == true)
      {
        usedBottomUp = 1;
      }
      if (data.getSandwichTestingUsed() == true)
      {
        usedSandwich = 1;
      }
      if (data.getModifiedSandwichTestingUsed() == true)
      {
        usedModifiedSandwich = 1;
      }

    // ******************************************************************
    // CALCULATING THE ACTUAL INTEGRATION TESTING HOURS
    // ******************************************************************

      double result = ((usedBigBang*BBWEIGHT) + (usedTopDown*TDWEIGHT) +
      (usedBottomUp*BUWEIGHT) + (usedSandwich*SWEIGHT) +
      (usedModifiedSandwich*MSWEIGHT)) * (int)data.getCodingManHours();

    // ******************************************************************
    // SETTING THE RESULT TO THE INSTANCE VARIABLE IN ESTIMATIONDATA
    // ******************************************************************

      data.setIntegrationTestingHours(result);

    // ******************************************************************
    } // integrationTestingHours()

    public void systemTestingHours()
    {
    // ***************************************************************
    // SETTING VARIABLES
    // ***************************************************************

      int usedFunctional;
      int usedPerformance;
      int usedPilot;
      int usedAcceptance;
      int usedInstallation;

    // *****************************************************************
    // SETTING THE APPROPRIATE VALUES BASED ON WHAT WAS SELECTED
    // *****************************************************************
      if (data.getFunctionalTestingUsed() == true)
        usedFunctional = 1;
      else
        usedFunctional = 0;

      if (data.getPerformanceTestingUsed() == true)
        usedPerformance = 1;
      else
        usedPerformance = 0;

      if (data.getPilotTestingUsed() == true)
        usedPilot = 1;
      else
        usedPilot = 0;

      if (data.getAcceptanceTestingUsed() == true)
        usedAcceptance = 1;
      else
        usedAcceptance = 0;

      if (data.getInstallationTestingUsed() == true)
        usedInstallation = 1;
      else
        usedInstallation = 0;

    // ******************************************************************
    // CALCULATING THE ACTUAL INTEGRATION TESTING HOURS
    // ******************************************************************

      double result = ((usedFunctional*FTWEIGHT) + (usedPerformance*PFTWEIGHT) +
      (usedPilot*PLTWEIGHT) + (usedAcceptance*ATWEIGHT) +
      (usedInstallation*ITWEIGHT)) * (int)data.getCodingManHours();

    // ******************************************************************
    // SETTING THE RESULT TO THE INSTANCE VARIABLE IN ESTIMATIONDATA
    // ******************************************************************

      data.setSystemTestingHours(result);

    // ******************************************************************
    } // systemTestingHours()


    public void testingTotalHours ()
    {
    // ******************************************************************
    // PERFORMING CALCULATIONS
    // ******************************************************************

      double testingTotalHours = ((data.getIntegrationTestingHours() +
      data.getSystemTestingHours())  +
      ((data.getNumOfTestCasesCreated() * TESTCASECREATETIME)));   
      
      
      /********************************************************************\
      *The code above this is breaking the implementation.  the
      * getNumbOfTestCasesCreated is breaking it.
      *
      *********************************************************************/
      
      

    // ******************************************************************
    // SETTING THE RESULT TO THE INSTANCE VARIABLE IN ESTIMATIONDATA
    // ******************************************************************

      data.setTestingTotalHours(testingTotalHours);

    // ******************************************************************
    } // testingTotalHours()

    public void avgBugsAfterTesting ()
    {
    // ******************************************************************
    // SETTING VARIABLES USED TO CALCULATE AVG BUGS AFTER TESTING
    // ******************************************************************
      double requiredTestingHours = ((data.getCodingManHours()) * 3);
      double testingRatio;
      double totalTestingHours = data.getTestingTotalHours();

      if (totalTestingHours >= requiredTestingHours)
        testingRatio = (-(requiredTestingHours/totalTestingHours)) + 1;
      else
        testingRatio = (((totalTestingHours/(requiredTestingHours * (-1))) + 1) * -1);

      double result = (data.getEstimatedBugsPer1000LOC() -
      (testingRatio * data.getEstimatedBugsPer1000LOC()));

    // ******************************************************************
    // SETTING THE RESULT TO THE INSTANCE VARIABLE IN ESTIMATIONDATA
    // ******************************************************************

      data.setAvgBugsAfterTesting(result);

    // ******************************************************************

    } // avgBugsAfterTesting()

    /**
      * Uses formulas to calculate estimation of testing
      * @roseuid 3BF34BF300A5
      */
      public void estimate()
      {
        if((data.getCodingManHours() < 0 ) || (data.getEstimatedBugsPer1000LOC() < 0))
        {
          //Set error values

          data.setIntegrationTestingHours(-1);
          data.setSystemTestingHours(-1);
          data.setTestingTotalHours(-1);
          data.setEstimatedBugsPer1000LOC(-1);
          System.out.print("\nDPU Error: invalid number of coding man hours or estimated bugs per 1000 LOC\n\n");

        }//end if

        else
        {
          integrationTestingHours();
          systemTestingHours();
          testingTotalHours();
          avgBugsAfterTesting();
        } //end else

      }//end estimate()

	/**
 	 * Serializes the DesignDataProcessingUnit to a
 	 * file.
 	 *
 	 * @param String name - The filename to which
 	 * the serialized object will be saved.
 	 *
 	 * @return void
 	 */
 	public void save (String name)
        {
          try
          {
            FileOutputStream ostream = new FileOutputStream(name);
	    ObjectOutputStream p = new ObjectOutputStream(ostream);

	    p.writeObject(data);

	    p.flush();
	    ostream.close();

            if(DEBUG)
            {
              System.out.print("File write successful\n");
            }

          } // try
          catch(Exception e)
          {
            System.out.print("file write ERROR save: " + e + "\n");
          }

 	} // save

	/**
 	 * Loads a serialized DesignDataProcessingUnit from a
 	 * file.
 	 *
 	 * @param String name - The filename from which
 	 * the serialized object will be loaded.
 	 *
 	 * @return void
 	 */
 	public void load(String name)
        {
          try
          {
    	    FileInputStream istream = new FileInputStream(name);
	    ObjectInputStream p = new ObjectInputStream(istream);

	    data = (EstimationData)p.readObject();

	    istream.close();

            if(DEBUG)
            {
              System.out.print("File read successful\n" + data);
            }

          } //try
          catch(Exception e)
          {
            System.out.print("file read ERROR: " + e);
          }

        } // load


  //------------------ Main ------------------//
  /**
   * DEBUGGING MAIN
   */
  public static void main(String[] argv)
  {
    double codingManHours  = 20;
    double estimatedBugsPer1000LOC = 30;
    TestDataProcessingUnit myDPU =
    new TestDataProcessingUnit(codingManHours, estimatedBugsPer1000LOC);

    //check for error checking in modifier methods
    if(DEBUG_DATA)
    {
      myDPU.getEstimator().setCodingManHours(-1);
      myDPU.getEstimator().setEstimatedBugsPer1000LOC(-1);
      myDPU.getEstimator().setNumofTestCasesCreated(-1);
      myDPU.getEstimator().setFunctionalTestingUsed(false);
      myDPU.getEstimator().setPerformanceTestingUsed(false);
      myDPU.getEstimator().setPilotTestingUsed(false);
      myDPU.getEstimator().setAcceptanceTestingUsed(false);
      myDPU.getEstimator().setInstallationTestingUsed(false);
    }
    else
    {
      myDPU.getEstimator().setNumofTestCasesCreated(21);
      myDPU.getEstimator().setFunctionalTestingUsed(true);
      myDPU.getEstimator().setPilotTestingUsed(true);
    }

    EstimationData data = myDPU.getEstimator();
    myDPU.estimate();
    System.out.print("Integration testing hours:\t" + data.getIntegrationTestingHours() +
      "\nSystem Testing Hours:\t\t" + data.getSystemTestingHours() +
     "\nTotal Man Hours:\t" + data.getTestingTotalHours() +
      "\nBugs Per 1000 LOC:\t\t" + data.getAvgBugsAfterTesting() + "\n\n");
    myDPU.save("KolliDPU.txt");
    myDPU.load("KolliDPU.txt");

  }

}//end TestDataProcessingUnit
