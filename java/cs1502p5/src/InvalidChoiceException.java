/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulator
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class InvalidChoiceException
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */


class InvalidChoiceException extends Exception {
public static final boolean DEBUG = false; //debug statement


/**
  * InvalidChoiceException - run when this exception is called
  *
  * Pre - exception is called.
  * Post - none.
  */
  public InvalidChoiceException() {
    if (DEBUG) System.out.println("*** InvalidChoiceException");
  }

/**
  * InvalidChoiceException - same as before, except error message is printed
  *
  * Pre - exception is called.
  * Post - error message is printed.
  */
  public InvalidChoiceException(String strErrorMessage) {
    System.out.println(strErrorMessage);
  }



}//endclass InvalidChoiceException
