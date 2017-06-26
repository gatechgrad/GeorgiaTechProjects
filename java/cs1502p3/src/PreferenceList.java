/**
 * CS1502: Program #3
 *
 * <PRE>
 * Saturday Night at the Movies
 *
 * Revisions:  1.0  January 29, 1999
 *                  Created class PreferenceList
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, January 29, 1999
 */


class PreferenceList {

   //*** CONSTANTS
   public static final boolean DEBUG = false; //debug constant


   //*** INSTANCE VARIABLES
   private String strMovieTitle;
   private PreferenceNode next;
   private PreferenceNode currentPtr;


   // *** CONSTRUCTORS
  /**
    * PreferenceList - initalizes this list
    * Pre - list must be created.
    * Post - the list is set to null.
    */
   public PreferenceList() {
      currentPtr = null;
   }

  /**
    * PreferenceList - initalizes the list
    * Pre - list must be created.
    * Post - list is initalized.
    * @param pointer The pointer to set the list
    */
   public PreferenceList ( PreferenceNode pointer) {
      currentPtr = pointer;
   }


   //*** MODIFIERS
  /**
    * addToList - adds a new node to the PreferenceList
    * Pre - list must be initalized.
    * Post - new node is added.
    * @param newNode The data to be added into this node
    */
   public void addToList (PreferenceNode newNode) {
      PreferenceNode tempPtr;
      
      if (currentPtr == null) {
         currentPtr = newNode; 
      }
      else {
         tempPtr = currentPtr;
         while (tempPtr.getNextNode() != null)
            tempPtr = tempPtr.getNextNode();
         tempPtr.setNextNode(newNode);
      }
   }

   //***ACCESSORS
   /**
    * traverseList - traverses list when DEBUG is on.
    * Pre - there must be a list.
    * Post - calls traverseListHelper to do the work.
    */
   public void traverseList () {
      traverseListHelper(currentPtr);
   }

   /**
    * traverseListHelper - does the traversal work.
    * Pre - a call from traverseLIst.
    * Post - the toStrings are printed.
    */
   private void traverseListHelper (PreferenceNode tempPtr) {
      if (tempPtr != null) {
         getString(tempPtr);
         traverseListHelper (tempPtr.getNextNode());
      }
   }



  /**
    * getCurrentPtr - returns the current pointer
    * Pre - none.
    * Post - pointer is returned.
    * @return The current pointer
    */
   public PreferenceNode getCurrentPtr() {
      return (currentPtr);
   }

  /**
    * getString - prints the toString of this node
    * Pre - toString must be in PreferenceNode.
    * Post - toString is printed.
    * @param printThis the node to print
    */
   public void getString (PreferenceNode printThis) {
      System.out.println (printThis);
   }


  /**
    * getNextNode - returns the next node
    * Pre - none.
    * Post - next node is returned.
    * @return The next node
    */
   public PreferenceNode getNextNode() {
      return(next);
   }

  /**
    * getMovieTitle - returns the movie title
    * Pre - title must be set.
    * Post - title is returned.
    * @return The movie title
    */
   public String getMovieTitle() {
      return(strMovieTitle);
   }

  /**
    * setNextNode - sets the next node
    * Pre - none.
    * Post - node data is set.
    * @param nextNode The data to place in the node
    */
   public void setNextNode(PreferenceNode nextNode) {
      next = nextNode;
   }

  /**
    * setMovieTitle - sets the title of the movie
    * Pre - none.
    * Post - movietitle is set.
    * @newMovieTitle the String to be set
    */
   public void setMovieTitle (String newMovieTitle) {
      strMovieTitle = newMovieTitle;
   }



}
