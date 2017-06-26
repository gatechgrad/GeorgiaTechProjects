/**
 * CS1502: Programming Assignment #1 - Winter 1999
 *
 * <PRE>
 * For Whom the Bell Tolls
 *
 * Revisions:  1.0  Jan 15, 1999
 *                  Created class P1 (Driver class)
 * </PRE>
 *
 * @Author: <A HREF="gte187k:cs1502@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Jan 15, 1999
 */

// *** REMEMBER TO REPLACE THE INFO ABOVE WITH YOUR NAME AND GT NUM ***

public class P1 {
	
	// ======================== CONSTANTS ============================
	
	private static final int TOLLCOST = 50;     // toll cost, in cents
	
	// =================== INSTANCE VARIABLES ========================
	
	private TollBooth myTollBooth;       // instance of the toll booth
	
	// ======================= CONSTRUCTOR ===========================
	
	/**
	 * P1 - creates a new P1
	 */
	public P1 () {
		
		myTollBooth = new TollBooth ( 0, TOLLCOST );
		mainMenu();
	}
	
	// ========================== MAIN ===============================
	
	/**
	 * main - method that is run when the class is executed
	 *
	 * @param argv[]  Array of command line arguments
	 */
	public static void main ( String argv[] ) {
		
		P1 myP1 = new P1();
	}
	
	// ===================== MENU METHODS ==========================
	
	/**
	 * mainMenu - Prints out main menu infinitely, reading
	 *            in choices, and calling the appropriate method
	 */
	public void mainMenu() {
		
		int choice;
		boolean continueLoop = true;
		
		System.out.println("Welcome to Winter 99 Program 1");
		System.out.println("------------------------------\n");
		
		while ( continueLoop ) {
			System.out.println("Choose an option");
			System.out.println("(1) Simulate Car at TollBooth");
			System.out.println("(2) Add Quarters to TollBooth");
			System.out.println("(3) Add Nickels to TollBooth");
			System.out.println("(4) Get Status of TollBooth");
			System.out.println("(0) Quit");
			choice = util.readInteger().intValue();
			
			if      ( choice == 1 ) simulate();
			else if ( choice == 2 ) addQ();
			else if ( choice == 3 ) addN();
			else if ( choice == 4 ) getString();
			else if ( choice == 0 ) continueLoop = false;
		}
		System.exit(0);
	}
	
	
	/**
	 * simulate - called when the user wants to simulate a car
	 *            at a particular toll booth.
	 *            Method asks for change to be inserted, and
	 *            prints out the change returned
	 */
	public void simulate() {
		
		int moneyToAdd;
		
		System.out.println("How much money to insert?");
		moneyToAdd = util.readInteger().intValue();
		
		System.out.println("While Loop reports:");
		myTollBooth.makeChangeWhile( moneyToAdd );

		System.out.println("Do While Loop reports:");
		myTollBooth.makeChangeDoWhile( moneyToAdd );

		System.out.println("Recursive reports:");
		myTollBooth.makeChangeRecursive( moneyToAdd );
	}
	
	
	/**
	 * addQ - adds Quarters to the toll booth
	 */
	public void addQ () {

                int moneyToAdd;

		System.out.println("How many quarters to add?");
		moneyToAdd = util.readInteger().intValue();

		// *** YOUR CODE HERE ***
                myTollBooth.addQuarters(moneyToAdd);

	}
	
	
	/**
	 * addN - adds Nickels to the toll booth
	 */
	public void addN () {
		
                int moneyToAdd;

		System.out.println("How many nickels to add?");
		moneyToAdd = util.readInteger().intValue();

		// *** YOUR CODE HERE ***
                myTollBooth.addNickels(moneyToAdd);

	}


	/**
 	 * getString - prints the toString of the toll booth
	 */
	public void getString() {
		
		System.out.println( myTollBooth );
	}

}  // end of class P1
