/**
 * CS1502: Program #3
 *
 * <PRE>
 * Saturday Night at the Movies
 *
 * Revisions:  1.0  January 29, 1999
 *                  Created class P3 (driver)
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, January 29, 1999
 */

//In some places, I copied a method that I had already written and
//then modified it to work in another area.  For example, I wrote
//fillCustomers(), then modified it to fillMovies(), and fillPreferences()
//and I reused other methods such as traverseList() and traverseListHelper(),
//getNextNode(), setNextNode(), etc. since they do the same thing for each
//kind of list.

//I also used d3.txt and slides 20 and 21 as guides while writing this
//program

public class P3 {

   // *** CONSTANTS
   public static final boolean DEBUG = false; //turns debugging on/off
   private static final String TERMINATE = "END"; //Terminating condition

   // *** INSTANCE VARIABLES
   private MoviesShowingList myMoviesShowingList;
   private CustomerList myCustomerList;
   private PreferenceList myPreferenceList;

   // *** MAIN
   public static void main (String argv[]) {
      P3 thisP3 = new P3();
    } // endmethod main

   // *** CONSTRUCTOR
   /**
    * P3 - makes a new P3.
    * Pre - none.
    * Post - a new P3 is created and data is filled and printed
    */
   public P3 () {
      myMoviesShowingList = new MoviesShowingList();
      myCustomerList = new CustomerList();
      fillMovies();
      fillCustomers();
      assignTickets();
      if (DEBUG) myCustomerList.traverseList();
      if (DEBUG) myMoviesShowingList.traverseList();
   } // endconstructor

   /**
    * fillCustomers - fills in the customer list after P3 is created.
    * Pre - P3 must be created.
    * Post - fills in customer name, then it calls a method in CustomerNode
    * to fill the customers preferences.
    */
   public void fillCustomers() {
      int iCounter = 1;
      String strChoice;

      do {
         System.out.println("Enter the name of customer #" + iCounter + " :");
         strChoice = util.readLine();

         if (!strChoice.equals ("END")){
            //if the user doesn't want to create another person, then don't
            //ask for preferences or add to the list
            //addToCustomerLL()

               CustomerNode myCustomerNode = new CustomerNode(strChoice,
                  iCounter);
               myCustomerList.addToList(myCustomerNode);
         }
         iCounter++;
      } while (!strChoice.equals (TERMINATE));
      if (DEBUG) System.out.println("*** Out of the Customer loop");
   }

   /**
    * fillMovies() - fills the movie list.
    * Pre - P3 must be created.
    * Post - MovieShowingList is filled with titles and seats.
    */
   public void fillMovies() {
      int iCounter = 1;
      int iSeats = 0;
      String strReadTitle;

      do {
         System.out.println("Enter the name of movie playing in theater #"
            + iCounter + " :");
         strReadTitle = util.readLine();
            if (!strReadTitle.equals ("END")) {
               //don't execute the next two lines if the user entered "END"
               //above
               System.out.println
                  ("Enter the number of seats in the theater   #"
                  + iCounter + " :");
               iSeats = util.readInteger().intValue();
               System.out.println("");

               iCounter++;

               if (DEBUG) System.out.println ("*** Read in data");
               MoviesShowingNode myNode = new MoviesShowingNode(strReadTitle,
                  iSeats);

               if (DEBUG) System.out.println
                  ("*** Created a new MovieShowingNode with data");
               myMoviesShowingList.addToList(myNode);

               if (DEBUG) System.out.println
                  ("*** Added the Node to the linked list");
            }
      } while (!strReadTitle.equals (TERMINATE));
      System.out.println("");
      if (DEBUG) System.out.println("*** Out of the theater loop");

   }

   /**
    * assignTickets - fills movieAssigned in all of the CustomerNodes.
    * Pre - must have a customer list and a movie list.
    * Post - movies are assigned, and a call is made to printResults.
    */
   public void assignTickets() {
      CustomerNode newCustomerPtr = myCustomerList.getCurrentPtr();
      MoviesShowingNode newMoviePtr = myMoviesShowingList.getCurrentPtr();
      PreferenceNode newPreferencePtr =
         newCustomerPtr.getPreferenceList().getCurrentPtr();
      if (DEBUG) System.out.println("\n*** Entered assignTickets");

      assignTicketsHelper(newCustomerPtr, newMoviePtr, newPreferencePtr);
      System.out.println(""); //separates data entry lines and results             
   printResults (newCustomerPtr);

   }

   
   /**
    * assignTicketsHelper - recurses through the three lists and
    * assigns tickets.
    * Pre - must be called from assignTickets.
    * Post - the customer nodes are filled with ticket data.
    * @param customerPtr The customer list that is being searched
    * @param moviePtr The movieShowing list that is being searched
    * @param preferencePtr The preference list that is being searched
    */
   public void assignTicketsHelper(CustomerNode customerPtr, MoviesShowingNode

      moviePtr, PreferenceNode preferencePtr) {

         //DEBUG statements
         if (DEBUG) System.out.println("\n*** Finding ticket for " +
            customerPtr.getCustomerName());

         if (DEBUG) System.out.println("*** Entered assignTicketsHelper");
         if (DEBUG) System.out.println("*** Preference: " +
            preferencePtr.getMovieTitle() + "   Movie: " +
            moviePtr.getMovieTitle());

         if (DEBUG) System.out.println("*** Movie: " + moviePtr.getMovieTitle()
            + "  Seats Available:  " + moviePtr.getNumberOfSeatsAvailable());

         //check to see if preference is the current movie
         if (preferencePtr.getMovieTitle().equals (moviePtr.getMovieTitle())) {

            //check for enough seats
            if (moviePtr.seatsAreAvailable()) {

               //assign movie to customer
               if (DEBUG) System.out.println("*** " +
                  customerPtr.getCustomerName() + " was assigned to " +
                  moviePtr.getMovieTitle());

                  customerPtr.assignMovie (moviePtr.getMovieTitle());
                  moviePtr.decrementNumberOfSeatsAvailable();

               //reset pointers for next customer
               //do nothing if this is the last customer node
               //(would result in NullPointer Exception)
               if (customerPtr.getNextNode() != null) {
                  customerPtr = customerPtr.getNextNode();
                  moviePtr = myMoviesShowingList.getCurrentPtr();
                  preferencePtr =
                     customerPtr.getPreferenceList().getCurrentPtr();

                  assignTicketsHelper (customerPtr, moviePtr, preferencePtr);
               }

            } else {
            
               moviePtr = myMoviesShowingList.getCurrentPtr();
               assignTicketsHelper (customerPtr, moviePtr,
                  preferencePtr.getNextNode());

            }

         } else {

         //preference does not equal current movie, get next movie
         assignTicketsHelper (customerPtr, moviePtr.getNextNode(),
            preferencePtr);
         }
   }


   /**
    * printResults - prints the results found in assignTickets.
    * Pre - must be called from assignTickets.
    * Post - assigned ticket data is printed.
    * @param listToPrint The customer list that will be printed
    */
   public void printResults(CustomerNode listToPrint) {
      if (DEBUG) System.out.println("*** Entered printResults");
      if (listToPrint != null) {
         System.out.println("Customer #" + listToPrint.getID() + " : " +
            listToPrint.getCustomerName() + "\nTicket for  : " +
            listToPrint.getAssignedMovie());
         printResults(listToPrint.getNextNode());
      }
   }

} // class P3
