/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulator
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class PassengerNode
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */

//I don't think I really needed this class.  At first I though we would have
//each customer's meal choices in array, but since it is not required I
//guess I could have did without this class


class PassengerNode extends PersonOnPlaneNode {

//*** CONSTANTS
public static final int COACHMEALS = 1;
public static final int FIRSTCLASSMEALS = 2;
public static final boolean DEBUG = false; //turns debugging on/off


//***INSTANCE VARIABLES
private String[] arrMeals; //an array of the user's meal choices

//***CONSTRUCTOR
/**
  * PassengerNode - initalizes a passenger
  *
  * Pre - new passenger has been created.
  * Post - new passenger is created.
  *
  * @param type what type of person this is
  */

public PassengerNode(String type) {
  super(type);
//  addMeals(); //turned this off since we don't have to store the users
                //meal choices
}


//*** ACCESSOR METHODS
/**
  * getMeals - returns the meal array
  *
  * Pre - array must have data.
  * Post - array is returned.
  *
  * @return the array of meals
  */
public String[] getMeals() {
  return(arrMeals);
}//endmethod getMeals

/**
  * getWeight - returns the weight of a passenger
  *
  * Pre - weight must be set.
  * Post - returns the person's weight.
  *
  * @return the person's weight
  */
public int getWeight() {
  return(super.getWeight());

}//endmethod getWeight



//*** MODIFIER METHODS
/**
  * addMeals - adds meals to the person's array
  *
  * Pre - the person must have a meal array.
  * Post - the meal array is filled with the user's choices.
  */

public void addMeals() {
  if (DEBUG) System.out.println("*** Entering addMeals ");

  int MAXMEALS;
         if(DEBUG) System.out.println("*** making MAXMEALS variable ");

         if(DEBUG) System.out.println("*** Type = " + super.getType());



         if (super.getType().equals("First Class passenger")) {

         if (DEBUG) System.out.println("*** First if ");

           MAXMEALS = FIRSTCLASSMEALS;
  } else if (super.getType().equals("Coach passenger")) {

          if (DEBUG) System.out.println("*** Second if ");

           MAXMEALS = COACHMEALS;
  } else {
           //this state should never be reached... it is
           //only in here so the file will compile
           System.out.println("Passenger type not supported");
           MAXMEALS = 0;
  }

  if (DEBUG)
    System.out.println("*** Making a new array with " + MAXMEALS +
      " cells");

  arrMeals = new String [ MAXMEALS ] ;

  for (int i = 0; i < MAXMEALS; i++) {
    if(DEBUG) System.out.println("*** Entered for loop");

    if (i == 0) {
      System.out.println("What would " + super.getName() + " like to eat :");
    } else {
      System.out.println("What else would " + super.getName() +
        " like to eat :");
    }

    if (DEBUG) System.out.println("*** Assigning to cell :" + i);
    arrMeals[i] = util.readLine();
  }


}//endmethod addMeals






}//endclass PassengerNode
