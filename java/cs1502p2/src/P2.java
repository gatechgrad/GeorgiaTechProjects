/**
 * CS1502: Programming Assignment #2 - Winter 1999
 *
 * <PRE>
 * For Whom the Array of Bells Tolls
 *
 * Revisions:  1.0  Jan 22, 1999
 *                  Created class P2 (driver class)
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Jan 22, 1999
 */

public class P2 {

        // *** CONSTANTS

        private static final int MAXTOLLBOOTHS = 10; //inital max tollbooths
        private static final boolean DEBUG = false; //debug on or off
        private static final int INITVALUE = 999; //inital value for ID and toll       
        private static final int TOLLCOST = 50; //toll cost (in cents)


        // *** INSTANCE VARIABLES

        private TollBooth[] tollBoothArray = new TollBooth [ MAXTOLLBOOTHS ];

	
        // *** CONSTRUCTOR

        /**
         * P2 - makes a new P2.
         * Pre - nothing.
         * Post - a new P2 object is created.
         *
         */

        public P2 () {
                        
                initArray ( tollBoothArray );
                mainMenu();

        }
	
        // *** MAIN

        /**
         * main - method that runs when file starts processing
         *
         * param argv[] Command line arguments array
         */

        public static void main ( String argv[] ) {

                P2 myP2 = new P2();
        }

	

        // *** INIT ARRAY


        /**
         * initArray - method that initalizes the TollBooth array
         * Pre - program must be started by user
         * Post - program starts running
         *
         * param plaza An array of TollBooths
         */

        private void initArray ( TollBooth[] plaza ) {

                int i;
                
                for (i = 0; i < plaza.length; i++) {
                        plaza[i] = new TollBooth ( INITVALUE, INITVALUE ); 
                }


        }

        // *** MENU METHODS
	
        /**
         *  mainMenu() - displays a menu, and then calls the appropriate
         *               method based on the user choice
         *  Pre - must be executed from method main.
         *  Post - menu is displayed and choice can be read in.
         */

        private void mainMenu() {
                int choice;
                int index;
                boolean continueLoop = true;

                System.out.println("Welcome to Winter 99 Program 2");
                System.out.println("------------------------------");

                while (continueLoop) {
                System.out.println("");


                        System.out.println("Choose a TollBooth ID from 0 to "
                                + (tollBoothArray.length - 1));

                        index = util.readInteger().intValue();
                        if (index < 0) {
                                index = 0;
                        }

                        if (index > (tollBoothArray.length - 1) ) {
                                index = (tollBoothArray.length - 1);
                        }

                        if (DEBUG) System.out.println("index = " + index);



                        
                        if (tollBoothArray[index].getID() == INITVALUE) 
                                makeNewTollBooth(index);

                

                        if (DEBUG) {
                                System.out.println("***  Status of all TollBooths:\n");

                                for (int i = 0; i < tollBoothArray.length; i++) {
                                        System.out.println ("*** " + tollBoothArray[i]);

                                }
                        }




                        System.out.println("Choose an option for TollBooth "
                                + index + ":");
                        System.out.println("(1) Simulate Car at TollBooth");
                        System.out.println("(2) Print Status of TollBooth");
                        System.out.println("(3) Print Total Quarters");
                        System.out.println("(4) Print Total Nickels");
                        System.out.println("(5) Change Toll Cost");
                        System.out.println("(6) Increase Tollplaza Size");
                        System.out.println("(0) Quit");
                        choice = util.readInteger().intValue();

                        if (choice == 1) simulate(index);
                        else if (choice == 2) printString(index);
                        else if (choice == 3) printTotalQ();
                        else if (choice == 4) printTotalN();
                        else if (choice == 5) changeToll();
                        else if (choice == 6) changePlazaSize();
                        else if (choice == 0) System.exit(0);
                }
                System.exit(0);

        }       


        /**
         *  makeNewTollBooth(int ID) - creates a new tollbooth
         *  Pre - array must be initalized
         *  Post - tollbooth is created
         *  @param ID The identification number of the Tollbooth to be added
         */

        private void makeNewTollBooth (int ID) {

                tollBoothArray[ID] = new TollBooth(ID, TOLLCOST); 

                System.out.println("New booth created at " +
                        tollBoothArray[ID].getID() + "\n");

        }




        /**
         *  printString(int ID) - executes the getString method of the
         *                        current TollBooth
         *  Pre - class Tollbooth must have a toString method
         *  Post - toString of the tollbooth is printed
         *  @param ID The identification number of the tollbooth to print
         */

        private void printString (int ID) {
                System.out.println (tollBoothArray[ID]);

        }


        /**
         *  simulate() - simulates a car at the current TollBooth
         *               (user enters an amount and then change is returned
         *                using the three different loops)
         *  Pre - the three loop methods must be in the TollBooth class
         *  Post - car at tollbooth is simulated
         *  @param ID The ID of the tollbooth to be simulated
         */

        private void simulate (int ID) {

                int moneyToAdd;

                System.out.println("How much money to insert?");
                moneyToAdd = util.readInteger().intValue();

                System.out.println("While Loop reports:");
                tollBoothArray[ID].makeChangeWhile(moneyToAdd);

                System.out.println("Do While Loop reports:");
                tollBoothArray[ID].makeChangeDoWhile(moneyToAdd);

                System.out.println("Recursive reports:");
                tollBoothArray[ID].makeChangeRecursive(moneyToAdd);



        }


        /**
         *  printTotalQ() - prints the total number of quarters in all of the
         *                  active tollbooths
         *  Pre - tollbooths must be initalized
         *  Post - total number of quarters are printed
         */

        private void printTotalQ() {
                int QuarterCounter = 0;
                
                for (int i = 0; i < tollBoothArray.length; i++) {
                        if (tollBoothArray[i].getID() != INITVALUE) {
                                QuarterCounter += tollBoothArray[i].getQuarters();        
                        }
                }

                System.out.println ("Total Quarters: " + QuarterCounter);


        }

        /**
         *  printTotalN() - prints the total number of nickels in all of the
         *                  active tollbooths
         *  Pre - tollbooths must be initalized
         *  Post - total number of nickels are printed
         */

        private void printTotalN () {
                int NickelCounter = 0;

                for (int i = 0; i < tollBoothArray.length; i++) {
                        if (tollBoothArray[i].getID() != INITVALUE) {
                                NickelCounter += tollBoothArray[i].getNickels();

                        }
                }
                System.out.println ("Total Nickels: " + NickelCounter);

        }


        /**
         *  changeToll() - changes the toll cost of the current tollbooth
         *               
         *  Pre - tollbooths must be initalized
         *  Post - toll of active booths is changed
         */

        private void changeToll() {

                System.out.println("What is the new toll?");
                int newTollCost = util.readInteger().intValue();

                for (int i = 0; i < tollBoothArray.length; i++) {
                        if (tollBoothArray[i].getID() != INITVALUE) {
                                tollBoothArray[i].setToll(newTollCost);
                        
                                if (DEBUG) {
                                    System.out.println(
                                    "*** Setting the toll cost of TollBooth "
                                    + i + " to " + newTollCost + " cents");

                                }
                        }
                }




        }

        /**
         *  changePlazaSize() - enlarges the array of tollbooths
         *             
         *  Pre - tollbooths are initialized
         *  Post - new array is made and initalized of the new plaza size
         */

        private void changePlazaSize() {

                System.out.println("What is the new size?");
                int newPlazaSize = util.readInteger().intValue();
               

                if (newPlazaSize > tollBoothArray.length) {
                        
                        TollBooth[] tempArray = new TollBooth[newPlazaSize];
                        initArray(tempArray);


                        for (int i = 0; i < tollBoothArray.length; i++){
                                tempArray[i] = tollBoothArray[i];

                                if (DEBUG) {

                                  System.out.println("*** Copying TollBooth "
                                        + i);
                                }
                        }

                tollBoothArray = tempArray;


                }
        }




} //class P2
