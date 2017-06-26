/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulator
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class MealList 
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */


class MealList {

//*** CONSTANTS
public static final boolean DEBUG = false; //debug


//*** INSTANCE VARIABLES

private MealNode headPtr; // pointer to the head of the mealList

//*** CONSTRUCTOR

/**
  * MealList - constructor for the list
  *
  * Pre - new list must be created.
  * Post - headPtr is set to null.
  */
public MealList () {
  headPtr = null;
}//endmethod MealList

//*** ACCESSOR METHODS
/**
  * getHeadPtr - returns the head pointer of the list
  *
  * Pre - none.
  * Post - head pointer is returned.
  *
  * @return headPtr the head of the list
  */
public MealNode getHeadPtr(){
  return (headPtr);
}//endmethod  getHeadPtr

/**
  * printMeals - prints all of the meals in the list
  *
  * Pre - none.
  * Post - all entries in the list are printed.
  */
public void printMeals() {
  printMealsHelper(headPtr);
}//endmethod printMeals()

/**
  * printMealsHelper - does the actual traversing and printing of the list
  *
  * Pre - call from printMeals.
  * Post - prints the entries.
  *
  * @param tempPtr current position in the list
  */
public void printMealsHelper(MealNode tempPtr) {
  if (tempPtr != null) {
    System.out.println(tempPtr.getMeals() + " - " + tempPtr.getName());
    printMealsHelper(tempPtr.getNextNode());

  }
}//endmethod printMealsHelper


//***MODIFIERS

/**
  * addToList - adds a new meal to the list
  *
  * Pre - mealnode must be passed in.
  * Post - meal is added to the list.
  *
  * @param mealToAdd the meal to be added to the list
  */
public void addToList(MealNode mealToAdd) {

MealNode mealPtr;

   if (headPtr == null)
      headPtr = mealToAdd;
   else {
      mealPtr = headPtr;
      while (mealPtr.getNextNode() != null)
         mealPtr = mealPtr.getNextNode();
      mealPtr.setNextNode(mealToAdd);
   }

}//endmethod addToList


}//endclass MealList
