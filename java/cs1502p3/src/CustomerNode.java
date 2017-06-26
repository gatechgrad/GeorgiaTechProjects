/**
 * CS1502: Program #3
 *
 * <PRE>
 * Saturday Night at the Movies
 *
 * Revisions:  1.0  January 29, 1999
 *                  Created class CustomerNode
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, January 29, 1999
 */

class CustomerNode {
   //*** CONSTANTS
   public static final boolean DEBUG = false; //debugging statement
   private static final String TERMINATE = "END"; //terminating String

   //*** INSTANCE VARIABLES
   private String strCustomerName;
   private int ID;
   private PreferenceList myPreferenceList;
   private CustomerNode next;
   private String strMovieAssigned;

   //*** CONSTRUCTORS
   /**
    * CustomerNode - sets up a new CustomerNode.
    * Pre - CustomerNode must be created.
    * Post - all variables initalized
    * @param newCustomerName the customer's name
    * @param newID the customer's number
    */
   public CustomerNode (String newCustomerName, int newID) {
      myPreferenceList = new PreferenceList();
      strCustomerName = newCustomerName;
      ID = newID;
      next = null;
      fillPreferences();
      if (DEBUG) myPreferenceList.traverseList();
   }

   /**
    * CustomerNode - sets up a new CustomerNode (if no params are passed in).
    * Pre - new CustomerNode must be created.
    * Post - the variables are initalized.
    */
   public CustomerNode () {
      next = null;
      fillPreferences();
      if (DEBUG) myPreferenceList.traverseList();

   }


   //*** ACCESSOR METHODS
   /**
    * getNextNode - returns the next node.
    * Pre - list must have data.
    * Post - next node is returned.
    * @return Next node is returned
    */
   public CustomerNode getNextNode() {
      return(next);
   }

   /**
    * getAssignedMovie - returns the movie assigned.
    * Pre - movie must be assigned.
    * Post - assigned movie is returned.
    * @return The movie the customer will see
    */
   public String getAssignedMovie () {
      return (strMovieAssigned);
   }


   /**
    * getCustomerName - returns the customer's name.
    * Pre - none.
    * Post - customer's name is returned.
    * @return The customer's name
    */
   public String getCustomerName () {
      return(strCustomerName);
   }

   /**
    * getID - returns the customer's ID number.
    * Pre - none.
    * Post - customer's ID number is returned.
    * @return The customer's number
    */
   public int getID () {
      return(ID);
   }

   /**
    * getPreferenceList - returns the customer's preferences.
    * Pre - preferences must be filled.
    * Post - preference list is returned.
    * @return The customer's preference list
    */
   public PreferenceList getPreferenceList () {
      return(myPreferenceList);
   }

   /**
    * toString - returns String representing the customer.
    * Pre - none.
    * Post - String is returned.
    * @return String representing this customer
    */
   public String toString () {
      String strToReturn;
      strToReturn = ("Customer Name: " + strCustomerName);
      return (strToReturn);
   }


   //*** MODIFIER METHODS

   /**
    * fillPreferences - fills this customer's preference list.
    * Pre - none.
    * Post - preferences are filled.
    */
   public void fillPreferences() {
      int iCounter = 1;
      String strChoice;
      System.out.println("For customer #" + ID + " :");

      do {
         System.out.println("Enter preference #" + iCounter + " :");
         strChoice = util.readLine();
         iCounter++;
         if (!strChoice.equals (TERMINATE)){
            //if the user doesn't want to create another person, then don't
            //ask for preferences or add to the list
            //addToCustomerLL()
               PreferenceNode myPreferenceNode = new PreferenceNode(strChoice);
               myPreferenceList.addToList(myPreferenceNode);
         }
      } while (!strChoice.equals (TERMINATE));
      System.out.println("");
   }

   /**
    * setNextNode - sets the next node.
    * Pre - none.
    * Post - next node is created.
    * @param nextCustomer The data to be placed in the next node
    */
   public void setNextNode(CustomerNode nextCustomer){
      next = nextCustomer;
   }

   /**
    * setCustomerName - sets this customer's name.
    * Pre - none.
    * Post - customer's name is set.
    * @param newCustomerName the name to be set
    */
   private void setCustomerName (String newCustomerName) {
      strCustomerName = newCustomerName;
   }
   
   /**
    * assignMovie - assigns a movie for the customer to see.
    * Pre - none.
    * Post - the customer is assigned a movie.
    * @param newMovieAssigned the movie the customer will see
    */
   public void assignMovie (String newMovieAssigned) {
      strMovieAssigned = newMovieAssigned;
   }



}
