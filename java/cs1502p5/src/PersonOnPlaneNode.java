/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulator
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class PersonOnPlaneNode
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */


class PersonOnPlaneNode {
//*** CONSTANTS
public static final boolean DEBUG = false; //debug



//***INSTANCE VARIABLES
private int iWeight;// the weight of the person (in kilograms)
private String strName;// the name of this person
private String type; // this type of person
private PersonOnPlaneNode next; // the next person in the list

//*** CONSTRUCTOR
/**
  * PersonOnPlaneNode - initializes person's attributes
  *
  * Pre - new person is created.
  * Post - variables are initalized.
  *
  * @param newType this person's type
  */

public PersonOnPlaneNode(String newType) {
  type = newType;

  setName();
  setWeight();

}//endmethod PersonOnPlaneNode


//*** ACCESSOR METHODS
/**
  * getWeight - returns this person's mass
  *
  * Pre - none.
  * Post - weight is returned.
  *
  * @return the weight of this person
  */
public int getWeight() {
  return(iWeight);
}//endmethod getWeight

/**
  * getName - returns the name of this person
  *
  * Pre - none.
  * Post - the name of this person is returned.
  *
  * @return the name of this person
  */
public String getName() {
  return(strName);
}//endmethod getName

/**
  * getType - returns what type of person this is 
  *
  * Pre - none.
  * Post - returns the type.
  *
  * @return the type of person
  */
public String getType() {
  return(type);
}//endmethod getType


//*** MODIFIER METHODS
/**
  * setWeight - prompt the user and set Weight... reject bad data
  *
  * Pre - call from constructor.
  * Post - weight is set.
  */
public void setWeight() {
  boolean inputIsGood;

  do {
    System.out.println(strName + "'s mass, in kilograms :");

    try {
      iWeight = util.readInteger().intValue();
      inputIsGood = true;

      if ( iWeight <= 0) {
        System.out.println("Invalid input, please try again.");
        throw (new InvalidChoiceException());
      }

    }
    catch (NumberFormatException e) {
      System.out.println("Invalid input, please try again.");
      inputIsGood = false;
    }
    catch (InvalidChoiceException e) {
      //do nothing
      inputIsGood = false;
    }


  } while (inputIsGood == false);



}//endmethod setWeight

/**
  * setName - prompt the user and set Name
  *
  * Pre - call from constructor.
  * Post - name is set.
  */
public void setName() {
  System.out.println("Name of " + type + " :");
  strName = util.readLine();
}//endmethod newName



/**
  * toString - formats this data for Plane Status printing
  *
  * Pre - none.
  * Post - string is returned with this node's data.
  *
  * @return this node's data
  */

public String toString() {
  String stringToReturn;


  if (getType().equals("Coach passenger")) {
    stringToReturn = ("Passenger : " + strName);


  } else if (getType().equals("First Class passenger")) {
    stringToReturn = ("First Class Passenger : " + strName);

  } else {                     
    stringToReturn = (type + " : " + strName);
  }
  return (stringToReturn);
}




}//endclass PersonOnPlaneNode
