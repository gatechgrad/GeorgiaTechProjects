/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulator
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class PilotNode
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */


class PilotNode extends PersonOnPlaneNode {

//*** CONSTANT
public static final boolean DEBUG = false; //debug


//***INSTANCE VARIABLES
private int iFlyingSpeed;// the flying speed of the pilot

//***CONSTRUCTOR
/**
  * PilotNode - initalizes the Pilot
  *
  * Pre - new pilot is created.
  * Post - super is initalized and flying speed is set.
  *
  * @param type the type of person this is
  */
public PilotNode(String type) {
  super(type);
  int iChoice;
  setFlyingSpeed();
}


//*** ACCESSOR METHODS
/**
  * getFlyingSpeed - returns the pilot's flying speed
  *
  * Pre - none.
  * Post - flying speed is returned.
  *
  * @return the pilots flying speed
  */
public int getFlyingSpeed() {
  return(iFlyingSpeed);
}//endmethod getFlyingSpeed


//*** MODIFIER METHODS

/**
  * setFlyingSpeed - prompts the user and sets the pilots flying speed
  *
  * Pre - pilot must be created.
  * Post - flying speed is set.
  */
public void setFlyingSpeed() {
  boolean inputIsGood;

  do {
    System.out.println(super.getName() +
      "'s flying speed, in kilometers per hour :");

    try {
      iFlyingSpeed = util.readInteger().intValue();
      inputIsGood = true;

      if ( iFlyingSpeed <= 0) {
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


}//endmethod setFlyingSpeed











}//endclass PilotNode
