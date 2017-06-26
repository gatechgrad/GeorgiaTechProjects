/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulator
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class MealNode (driver class)
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */


class MealNode {

//*** CONSTANT
public static final boolean DEBUG = false; //debug


//*** INSTANCE VARIABLES
private MealNode next; //the next in the list
private String strName; // the name of the meal
private int iNumOfMeals; // the number of this meal available

//*** CONSTRUCTOR
/**
  * MealNode - inintalizes MealNode
  *
  * Pre - none.
  * Post - variables are initalized.
  *
  * @param newName the name of the meal
  * @param newNumber the number of these meals
  */
public MealNode (String newName, int newNumber){
  next = null;
  setNumOfMeals(newNumber);
  setName(newName);
}//endmethod MealNode

//*** ACCESSOR METHODS
/**
  * getNextNode - returns the next node in the list
  *
  * Pre - next node must have a value.
  * Post - next node is returned.
  * @return the next node
  */
public MealNode getNextNode() {
  return(next);
}//endmethod getNextNode

/**
  * getName - returns the name of this meal.
  *
  * Pre - none.
  * Post - name is returned.
  *
  * @return the name of this meal
  */
public String getName() {
  return(strName);
}//endmethod getName

/**
  * getMeals - returns the number of this kind of meal
  *
  * Pre - none.
  * Post - number of meals is returned.
  *
  * @return the number of meals
  */
public int getMeals() {
  return(iNumOfMeals);
}//endmethod getMeals


//*** MODIFIER METHODS
/**
  * setNextNOde - sets the next node in the list
  *
  * Pre - none.
  * Post - next node is set newNode.
  *
  * @param newNode the node to be added to the list
  */
public void setNextNode(MealNode newNode) {
  next = newNode;
}//setNextNode

/**
  * setName - sets the name of this meal
  *
  * Pre - none.
  * Post - names (or renames) the current meal.
  *
  * @param newName the new name for this meal
  */
public void setName(String newName) {
  strName = newName;
}//endmethod setName

/**
  * setMeals - sets the number of this kind of meal
  *
  * Pre - none.
  * Post - number of meals is set.
  *
  * @param newNumber the number of this kind of meal
  */

public void setNumOfMeals(int newNumber) {
  iNumOfMeals = newNumber;
}


}//endclass MealNode
