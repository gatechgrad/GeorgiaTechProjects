/**
 * CS1502: Program #3
 *
 * <PRE>
 * Saturday Night at the Movies
 *
 * Revisions:  1.0  January 29, 1999
 *                  Created class MoviesShowingNode
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, January 29, 1999
 */

class MoviesShowingNode {

   // *** CONSTANTS
   public static final boolean DEBUG = false; //for debugging
   public static final int MINIMUMSEATS = 0; //if there are less than this
                                             //many seats, then the movie
                                             //is sold out


   // *** INSTANCE VARIABLES
   private int iSeatsAvailable;
   private String strMovieTitle;
   private MoviesShowingNode next;


   // *** CONSTRUCTOR
   /**
    * MoviesShowingNode - sets a new MoviesShowingNode.
    * Pre - new MoviesShowing Node must be created.
    * Post - all of the variables are set.
    * @param newMovieTitle the name of this movie
    * @param newSeatsAvailable the number of seats available for this movie
    */
   public MoviesShowingNode (String newMovieTitle, int newSeatsAvailable) {
      strMovieTitle = newMovieTitle;
      iSeatsAvailable = newSeatsAvailable;
      next = null;
   }



   //*** ACCESSORS
  /**
    * getNumberOfSeatsAvailable - returns the available seats, for use with
    * DEBUG only.
    * Pre - none.
    * Post - number of seats is returned.
    * @return The number of seats
    */
   public int getNumberOfSeatsAvailable () {
      return (iSeatsAvailable);
   }

  /**
    * getMovieTitle - returns the title of this movie
    * Pre - movie name must be set.
    * Post - movie name is returned.
    * @return The name of this movie
    */
   public String getMovieTitle () {
      return (strMovieTitle);
   }

  /**
    * seatsAreAvailable - if seats are available, then returns true
    * Pre - number of seats must be set.
    * Post - movie name is returned.
    * @return The name of this movie
    */
   public boolean seatsAreAvailable () {
      if (iSeatsAvailable > MINIMUMSEATS) {
         return (true);
      } else {
         return (false);
      }
   }


   //*** MODIFIERS
  /**
    * setNumberOfSeatsAvailable - sets the number of seats
    * Pre - none.
    * Post - number of seats is set.
    */
   public void setNumberOfSeatsAvailable ( int newSeatsAvailable) {
      iSeatsAvailable = newSeatsAvailable;
   }

  /**
    * decrementNumberOfSeats - subtracts one from the number of seats
    * Pre - none.
    * Post - one seat is taken away from the total available.
    */
   public void decrementNumberOfSeatsAvailable () {
      iSeatsAvailable--;
   }

  /**
    * toString - default string representing the Movie
    * Pre - none.
    * Post - string is returned.
    * @return the default String repersenting this movie
    */
   public String toString () {
      String strToReturn;
      strToReturn = ("Movie Title: " + strMovieTitle + "\nSeats Available: "
         + iSeatsAvailable);
      return(strToReturn);
   }

  /**
    * getNextNode - returns the next node
    * Pre - none.
    * Post - node is returned.
    * @return the next node
    */
   public MoviesShowingNode getNextNode() {
      return(next);
   }

  /**
    * setNextNode - sets the next MovieShowingNode
    * Pre - none.
    * Post - data for next node is set.
    * @param nextNode The data to be added into the node
    */
   public void setNextNode (MoviesShowingNode nextNode) {
      next = nextNode;
   }

  /**
    * setMovieTitle - sets the title of this movie
    * Pre - none.
    * Post - title is set.
    * @param newMovieTitle the title is set to this String
    */
   public void setMovieTitle (String newMovieTitle) {
      strMovieTitle = newMovieTitle;
   }




} //class MoviesShowingNode
