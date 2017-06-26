/**
 * CS1502: Programming Assignment #2 - Winter 1999
 *
 * <PRE>
 * For Whom the Array of Bells Toll
 *
 * Revisions:       1.1  Jan 22, 1999
 *                  Modified class TollBooth (TollBooth class)
 *                  1.0  Jan 15, 1999
 *                  Created class TollBooth (TollBooth class)
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.1, Jan 22, 1999
 */

//NOTE:  I am modifying my TollBooth class from P1

public class TollBooth {

	// ====================== CONSTANTS =========================

   private static final int INITIALQ = 50; //the initial number of quarters
   private static final int INITIALN = 50; //the initial number of nickels
   private static final int QVALUE = 25; //the value of one quarter
   private static final int NVALUE = 5; //the value of one nickel

	// ================== INSTANCE VARIABLES ====================

	private int ID;             // unique identifier for tollbooth
	private int tollCost;       // amount, in cents, for the toll
	
	private int numQuarters;    // number of quarters in the machine
	private int numNickels;     // number of nickels in the machine
	
	// ===================== CONSTRUCTOR ========================
	
	/**
	 * TollBooth - Constructor for a TollBooth Object
	 *             Sets the ID instance variable to the one passed in
	 *
	 * @param newID  unique ID for this tollbooth
	 */
	public TollBooth ( int newID, int newTollCost ) {
		ID = newID;
		tollCost = newTollCost;
		numQuarters = INITIALQ;
		numNickels = INITIALN;
	}
	
	// ===================== ACCESSORS ========================

	/**
	 * getID - returns the identifier number of this tollbooth
	 *
	 * @return Identifier number
	 */
	public int getID () {
		return ( ID );
	}
	
	
	/**
	 * getNickels - returns number of nickels this toll booth has
	 *
	 * @return Number of nickels
	 */
	public int getNickels () {
		

		// *** YOUR CODE HERE ***
                return ( numNickels );

	}
	
	/**
	 * getQuarters - returns number of quarters this toll booth has
	 *
	 * @return  Number of quarters
	 */
	public int getQuarters () {
		
		// *** YOUR CODE HERE ***
                return ( numQuarters );

	}

	// ===================== MODIFIERS ========================

	/**
	 * addNickels - adds more nickels to the machine
	 *
	 * @param moreNickels Number of more nickels that will be added
	 */
	public void addNickels ( int moreNickels ) {
		
		// *** YOUR CODE HERE ***
                numNickels += moreNickels;


	}
	
	/**
	 * addQuarters - adds more quarterss to the machine
	 *
	 * @param moreQuarters Number of more quarters that will be added
	 */
	public void addQuarters ( int moreQuarters ) {
		
		// *** YOUR CODE HERE ***
                numQuarters += moreQuarters;
	}
	

	/**
         * setToll - sets the toll of the booth
	 *
         * @param newToll The new toll of the booth
	 */
        public void setToll ( int newToll ) {
		
                tollCost = newToll;

	}




	// ================== MAKE CHANGE METHODS ==================
	
	/**
	 * makeChangeXXX - prints the amount of change to return from
	 *                 a certain number of cents being inserted.
	 *                 Output prints num of quarters and nickels to
	 *                 return.
	 *
	 *   While: uses a while loop to calculate change
	 *   DoWhile: uses a do/while loop to calculate change
	 *   Recursive: uses recursion to calculate change
	 *
	 * Format of output:
	 *   (TollBooth 0)
	 *   Quarters - 2
	 *   Nickels - 1
	 *
	 * If not enough money is inserted, print out:
	 *   Not enough money
	 *
	 * @param moneyInserted  Amount, in cents, of money inserted
	 */
	 
	public void makeChangeWhile ( int moneyInserted ) {
                util.ASSERT(moneyInserted > 0, "Cannot insert a negative amount of money");

		int totalChange = moneyInserted - tollCost;
		int qToReturn = 0;
		int nToReturn = 0;

		// if totalChange is negative (not enough money inserted),
		// print error message and set change left to 0
		if ( totalChange < 0 ) {
			System.out.println ( "Not enough money" );
			totalChange = 0;
		}

		// *** YOUR CODE HERE ***

                //a while loop to return quarters
                while ( totalChange >= QVALUE ) {
                        qToReturn++;
                        numQuarters--;
                        totalChange -= QVALUE;
                }



                //a while loop to return nickels
                while ( totalChange >= NVALUE ) {
                        nToReturn++;
                        numNickels--;
                        totalChange -= NVALUE;
                }


                 printResults (qToReturn, nToReturn);

	}
	
	
	public void makeChangeDoWhile ( int moneyInserted ) {
		
		// *** YOUR CODE HERE ***

		int totalChange = moneyInserted - tollCost;
                int qToReturn = 0;
                int nToReturn = 0;


                //using the same check code from above
                //the instructions tell us to use the same code

                if (totalChange < 0) {
                        System.out.println ( "Not enough money" );
			totalChange = 0;
                }
                


                if (totalChange >= QVALUE) {
                       do {

                                qToReturn++;
                                numQuarters--;
                                totalChange -= QVALUE;
                        } while ( totalChange >= QVALUE );
                }

                if (totalChange >= NVALUE) {
                        do {
                                nToReturn++;
                                numNickels--;
                                totalChange -= NVALUE;
                         } while ( totalChange >= NVALUE );

                 }

                printResults (qToReturn, nToReturn);

	}
	
	
	int quarters = 0;       // FOR USE WITH RECURSIVE
	int nickels = 0;        // MAKE CHANGE ONLY

	public void makeChangeRecursive ( int moneyInserted ) {
        //Calculate the number of nickels to return, then converts groups
        //of five nickels into quarters. 

 		int totalChange = moneyInserted - tollCost;

                //checking for enough money again...
                if (totalChange < 0) {
                        System.out.println ( "Not enough money" );
			totalChange = 0;
		}


                //NICKELS_IN_A_QUARTER is the number of nickels that are
                //in a quarter
                final int NICKELS_IN_A_QUARTER = 5;

                if (totalChange <= 0) {
                        //If there are five nickels to return, the next line
                        //will convert those five nickels into a quarter.
                        //Next, the modulus of the number of nickels and 5
                        //is taken to give the number of nickels left.
                        //For example, if the machine is supposed to return
                        //13 nickels, then:
                        //quarters = (int) 13/5 = 2;
                        //nickels = 13 % 5 = 3;
                        
                        quarters = (int) (nickels/NICKELS_IN_A_QUARTER);
                        nickels = nickels % NICKELS_IN_A_QUARTER;

                        //subtract coins from the total amount
                        numQuarters -= quarters;
                        numNickels -= nickels;

                        printResults (quarters, nickels);

                } else {
                        nickels++;
                        makeChangeRecursive (moneyInserted - NVALUE);
                }

                //reset the quarters counter and nickels counter back to zero
                quarters = 0;
                nickels = 0;


	}
	
        /**
         *  printResults
         *  Purpose: prints the results from a makeChange method
         *  Precondition: two values must be passed into the method
         *  Postcondition: prints the data of a makeChange method
         *
         *  @param qToPrint Number of quarters to be returned
         *  @param nToPrint Number of nickels to be returned
         */


        public void printResults (int qToPrint, int nToPrint) {

                util.ASSERT( qToPrint>= 0, "Cannot print a negative number of quarters for this method");
                util.ASSERT( nToPrint >= 0, "Cannot print a negative amount of nickels for this method");

                System.out.println ( "(TollBooth " + getID() + ")");
                System.out.println ( "Quarters - " + qToPrint);
                System.out.println ( "Nickels - " + nToPrint);
        }

	/**
	 * toString - returns a String representing this toll booth
	 *
	 * format (spaces count!):
	 *           (TollBooth 0)  Quarters: 5  Nickels: 5
	 *
	 * @return Representation of the class as a String
	 */
	public String toString () {
		String strToReturn;
		
		// *** YOUR CODE HERE ***
                strToReturn = ("(TollBooth " + getID() + ")  Quarters: " +
                        getQuarters() + "  Nickels: " + getNickels());


		return ( strToReturn );
	}
	
}  //end of class TollBooth
