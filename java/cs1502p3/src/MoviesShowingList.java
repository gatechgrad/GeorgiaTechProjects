/**
 * CS1502: Program #3
 *
 * <PRE>
 * Saturday Night at the Movies
 *
 * Revisions:  1.0  January 29, 1999
 *                  Created class MoviesShowingList
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, January 29, 1999
 */

class MoviesShowingList {

   //*** CONSTANTS
   public static final boolean DEBUG = false; //debugging

   //*** INSTANCE VARIABLES
   private MoviesShowingNode currentPtr;


   // *** CONSTRUCTORS
   /**
    * MoviesShowingList - sets up a new list set to null.
    * Pre - new MoviesShowingList must be created.
    * Post - MoviesShowingList is initalized.
    */
   public MoviesShowingList() {
      currentPtr = null;
   }

   /**
    * MoviesShowingList - sets up a new MoviesShowingList.
    * Pre - a new MoviesShowingList must be created.
    * Post - MoviesShowingList is initalized and head pointer is set.
    * @param pointer The pointer where the MoviesShowingList will be set
    */
   public MoviesShowingList ( MoviesShowingNode pointer) {
      currentPtr = pointer;
   }


   //*** MODIFIERS
   /**
    * addToList - adds a new node to this list.
    * Pre - none.
    * Post - a new node is added.
    * @param newNode the data to be added to the new node
    */
   public void addToList (MoviesShowingNode newNode) {
      MoviesShowingNode tempPtr;
      if (currentPtr == null) {
         currentPtr = newNode; 
      }
      else {
         tempPtr = currentPtr;
         while (tempPtr.getNextNode() != null) {
            tempPtr = tempPtr.getNextNode();
         }
         tempPtr.setNextNode(newNode);
      }
   }//end method addToList


   /**
    * traverseList - visits each node in the list -- use with DEBUG.
    * Pre - list must be created.
    * Post - all nodes are visited.
    */
   public void traverseList () {
      traverseListHelper(currentPtr);
   }// end method traverseList

   /**
    * traverseListHelper - helps the previous method.
    * Pre - must be called from traverseList.
    * Post - the toString of each node is called.
    */
   private void traverseListHelper (MoviesShowingNode tempPtr) {
      if (tempPtr != null) {
         getString(tempPtr);
         traverseListHelper (tempPtr.getNextNode());
      }
   }// endmethod traverseListHelper

   //***ACCESSORS
   /**
    * getCurrentPtr - returns the current pointer.
    * Pre - none.
    * Post - pointer is returned.
    * @return The current pointer
    */
   public MoviesShowingNode getCurrentPtr() {
      return (currentPtr);
   }

   /**
    * getString - prints the toString of this node.
    * Pre - node must have a toString.
    * Post - current node is printed.
    * @param printThis The node to be printed
    */
   public void getString (MoviesShowingNode printThis) {
      System.out.println (printThis);
   }

}// end class MoviesShowingList
