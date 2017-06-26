/**
 * CS1502: Program #3
 *
 * <PRE>
 * Saturday Night at the Movies
 *
 * Revisions:  1.0  January 29, 1999
 *                  Created class PreferenceNode
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, January 29, 1999
 */

class PreferenceNode {

   public static final boolean DEBUG = false; //I wonder what this is for??
                                              //Hint: turns debugging on

   //*** INSTANCE VARIABLES
   private String strMovieTitle;
   private PreferenceNode next;


   // *** Constructor
  /**
    * PreferenceNode - initalizes the node
    * Pre - none.
    * Post - node is initalized.
    * @param newMovieTitle the name of the movie
    */
   public PreferenceNode (String newMovieTitle) {
      strMovieTitle = newMovieTitle;
      next = null;
   }



   //*** ACCESSORS

  /**
    * getMovieTitle - returns the name of the movie
    * Pre - the name of the movie must be set.
    * Post - movie name is returned.
    * @return The name of this movie
    */
   public String getMovieTitle () {
      return (strMovieTitle);
   }

  /**
    * toString - String representing this class
    * Pre - none.
    * Post - String is returned.
    * @return The string to be printed
    */
   public String toString () {
      String strToReturn;
      strToReturn = ("Movie Title: " + strMovieTitle);
      return(strToReturn);
   }


  /**
    * getNextNode - returns the next node
    * Pre - next node must be present.
    * Post - next node is returned.
    * @return The next node
    */
   public PreferenceNode getNextNode() {
      return(next);
   }

   //*** MODIFIERS

  /**
    * setNextNode - sets the next node
    * Pre - none.
    * Post - next node is set.
    * @param nextNode The data to be set
    */
   public void setNextNode (PreferenceNode nextNode) {
      next = nextNode;
   }

  /**
    * setMovieTitle - sets the title of the movie
    * Pre - none.
    * Post - title of movie is set.
    * @param newMovieTitle The title of the movie
    */
   public void setMovieTitle (String newMovieTitle) {
      strMovieTitle = newMovieTitle;
   }

} //class PreferenceNode

