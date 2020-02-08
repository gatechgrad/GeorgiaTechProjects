package Estimator;

/**
*
* This interface provides that stubs for the methods that all the methods must have to integrate 
* smoothly.
*
* author: Chris Morris (chrismorris@@acm.org)
*/

import javax.swing.JPanel;


public interface SubComponent {
	
	
	/**
	* This method should ensure that all of the values entered in the text boxes are OK. 
	* if they are not then it should produce an error message. If it hits an error it 
	* should also return false. If everything is ok then it should return true, do the 
	* estimations and enter the data into EstimationData
	*/
	public boolean estimate(); 
	

	/**
	* This method should get any data that is needed to do your estimations that is 
	* based on other components. For example the design component needs number of man 
	* hours and candidate classes. This method prompts for both of those and writes the 
	* entered values to the EstimationData. It should also handel the error checking of 
	* the data. 
	*/		
	public void getPrevData();
	
	
	
	/**
	*
	* This method should return a panel which is what you want displayed to the user 
	* about the results of your system. If a value has not been estimated then it 
	* should say so. For example lets say that the requirnments component was skipped 
	* by the user. The results panel for the requirnments will indicate most of the 
	* values are unestimated but it will indicate that the candidate classes and total 
	* manhours were computed, (since those were entered by the user). 
	*/	
	public JPanel getResultsPanel();
	
	
	/**
	* This method should check to see if the user has started to fill in any data for 
	* the component. If a user has then it should return true. If not it should return 
	* false. 
	*/
	public boolean isAnyFilledIn();

	
	/**
	*
	* This method loads the data from the current data estimator to the screen
	*/
	public void load();
	
	
	/**
	*
	* This method loads the data from the current data estimator to the screen
	*/	
	public void save();
	
	
	/*
	* Methods used from JPanel
	*
	*/
	
	public void setName(String name);
	public String getName();
	
	
	
}