package ImplementationComponent;

/*
 * ImplementationDPU.java
 * Date: 11/01/2001
 * Version: 1.0
 * Author: Roy Clarkson (roy@clarkson.org) 
 *
 */
 
import Estimator.*;
import java.io.*;
import java.util.*;
import java.lang.Math.*;

public class ImplementationDPU{
    
    //------------ DEBUGGING --------------//
        private static final boolean DEBUG = false;
        
	//------------ Constants --------------//
        private static final int [] PRODUCTIVITY_TABLE = new int [] {
            754,
            987,
            1220,
            1597,
            1974,
            2584,
            3194,
            4181,
            5186,
            6765,
            8362,
            10946,
            13530,
            17711,
            21892,
            28657,
            35422,
            46368,
            57314,
            75025,
        };
        
        private static final double [] B_TABLE = new double [] {
            .16,
            .18,
            .28,
            .34,
            .37,
            .39,
        };

	//------------ Static Variables ---------//

	//------------ Instance Variables -------//
    private EstimationData data = null;

	//------------ Constructors -------------//	
	
	public ImplementationDPU(EstimationData estimationData) {
		data = estimationData;
	}
        
        public ImplementationDPU() {
            data = new EstimationData();
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
	
	
	//------------ Modifiers -----------------//

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
    
    public void calcLOC(){
        int result = (35*(int)data.getDesignMethods()) + (30*(int)data.getDesignClasses());
        data.setLOC( result );
    }
    
    public void calcMonthHours(){
        double mt, e, temp, pp, b, hours;
        
        pp = ( data.getLvlTesting() + data.getLvlDoc() );
        pp = ( 21 - pp );
        pp = PRODUCTIVITY_TABLE[(int)pp-1];
        
        
        mt = (0.68 * Math.pow((data.getLinesOfCode()/pp), 0.43));
        
        hours = mt * 152;
        
        b = B_TABLE[0];
        if (data.getLinesOfCode() > 15000)
            b = B_TABLE[1];
        if (data.getLinesOfCode() > 25000)
            b = B_TABLE[2];
        if (data.getLinesOfCode() > 35000)
            b = B_TABLE[3];
        if (data.getLinesOfCode() > 45000)
            b = B_TABLE[4];
        if (data.getLinesOfCode() > 65000)
            b = B_TABLE[5];
        
        e = Math.pow(mt, 3);
        e = ( 180 * b * e );
        e = 152 * e;   // 1 Man-month is equal to 152 working hours, SOURCE: COCOMO
        e = e / 2;     // This is the factor suggested by the design document
        data.setManMonths(e);      
        
    }
    
    public void calcBugs(){
        data.setBugs( 11-data.getLvlTesting() );
    }
    
	public void estimate() {
	    calcLOC();
	    calcMonthHours();
	    calcBugs();
	}
        
        public void estimate(boolean userLoc) {
            if (!userLoc) {
                calcLOC();
            }
	    calcMonthHours();
	    calcBugs();
	}
	
	
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
                FileOutputStream ostream = new FileOutputStream(new File(name));
	        ObjectOutputStream p = new ObjectOutputStream(ostream);
	        p.writeObject(data);
	        p.flush();
	        ostream.close();
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
 	    }
 	    catch(Exception e){System.out.print("file read ERROR: " + e);}
 	}
 	

  //------------------ Main ------------------//
  
  public static void main(String[] argv){
    /* not used */
  }

}//end DesignDataProcessingUnit