package RequirementsComponent;

//Source file: /net/hu1/bwd/rose/RequirementsDPU.java


/**
 * This class is responsible for the storage, retrieval, and processing of data for the Requirements component of the Software Process Estimator
 */
import java.io.*;
import Estimator.*;

public class RequirementsDPU 
{
    
    /**
     * This encapsulates all the Data for the Software Process Estimator
    */
    private EstimationData reqData;
    private boolean loadedcorrect;
    
    public RequirementsDPU() 
    {
	this.reqData = new EstimationData();
    }

    public boolean loadedCorrect(){
	return(this.loadedcorrect);
    }
    
    public void setLoadedCorrect(boolean loadedCorrect){
	this.loadedcorrect = loadedCorrect;
    }

    /**
     * This retrieves the Serialized Data at the given location  from disk and returns it for use by the DPU.    
     * @param filename
     * @return EstimationData
     * @roseuid 3BBC8F200092
     */
    public EstimationData retrieveData(String filename) 
    {
	try{
	    FileInputStream istream = new FileInputStream(filename);
	    ObjectInputStream p = new ObjectInputStream(istream);
	    
	    this.reqData = (EstimationData)p.readObject();
	    
	    istream.close();
	    loadedcorrect = true;
	}catch(IOException e){
	    e.printStackTrace();
	    System.out.println("IOException caught in Class RequirementsDPU Method retrieveData");
	    //System.exit(1);
	    loadedcorrect = false;
	}catch(Exception e){
	    e.printStackTrace();
	    System.out.println("Exception caught in Class RequirementsDPU Method retrieveData");
	    System.exit(1);
	}

	return(this.reqData);
    }
    
    /**
     * This will write the Data to disk for persistent storage.   
     * @return void
     * @roseuid 3BBC8F33001D
     */
    public void storeData(String filename) 
    {
	try{
	    FileOutputStream ostream = new FileOutputStream(filename);
	    ObjectOutputStream p = new ObjectOutputStream(ostream);
	    
	    p.writeObject(reqData);
	    
	    p.flush();
	    ostream.close();
	}catch(IOException e){
	    e.printStackTrace();
	    System.out.println("IOException caught in Class RequirementsDPU Method storeData");
	    System.exit(1);
	}catch(Exception e){
	    e.printStackTrace();
	    System.out.println("Exception caught in Class RequirementsDPU Method storeData");
	    System.exit(1);
	}
    }
    
    /**
     * This method will be replaced by individual methods for processing Data and returning a value once the specific processes are known.    
     * @return EstimationData
     * @roseuid 3BBC8F4A01E1
     */
    public EstimationData processData() 
    {
	double JADHours = 0;
	double KATHours = 0;

	double estimatedLOC = reqData.getNumCandidateClasses() * 400;
	if(this.reqData.getJad()){
	    JADHours = (reqData.getNumJADSessions() * 6) + (reqData.getNumJADProductInterviews() * 3) + (reqData.getNumJADResearchInterviews() * 3) + (reqData.getNumJADStakeholdersPresent() * .1);
	}
	if(this.reqData.getKat()){
	    KATHours = (reqData.getNumKATSurveys() * 3) + (reqData.getNumKATSurveyQuestions() * .10) + (reqData.getNumKATInterviews() * 2) + (reqData.getNumKATInterviewQuestions() * .10) + (reqData.getNumKATObservations() * 5) + (reqData.getNumKATObservationsPages() * 1) + (reqData.getNumScenarioTests() * 5) + (reqData.getNumPrototypeTests() * 20);
	}

	double documentationHours = (JADHours * 0.2) + (KATHours * 0.2) + 20;
	double manHours = JADHours + KATHours + documentationHours;

	reqData.setJADHours(JADHours);
	reqData.setKATHours(KATHours);
	reqData.setEstimatedLOC(estimatedLOC);
	reqData.setDocumentationHours(documentationHours);
	reqData.setManHours(manHours);



	return(this.reqData);
    }
    
    /**
     * This returns the Data to be used for calculations and display.    
     * @return EstimationData
     * @roseuid 3BBC93100312
     */
    public EstimationData getData() 
    {
	return(this.reqData);
    }

    public void setData(EstimationData reqData){
	this.reqData = reqData;
    }

    public static void main(String args[]){
	System.out.println("Foo\n");
	RequirementsDPU dpu = new RequirementsDPU();
	dpu.getData().setJADHours(20);
	dpu.storeData("test.tba");
	System.out.println(dpu.getData().getJADHours());
	dpu = new RequirementsDPU();
	dpu.retrieveData("test.tba");
	System.out.println(dpu.getData().getJADHours());
	dpu.getData().setJADHours(10);
	dpu.storeData("test.tba");
	dpu = new RequirementsDPU();
	dpu.retrieveData("test.tba");
	System.out.println(dpu.getData().getJADHours());
	dpu.retrieveData("test.tba");
	System.out.println(dpu.getData().getJADHours());
	
    }


}
