/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulator
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class P5 (driver class)
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */

//NOTE:  I'm using a similar menu system as the one used in my P4
//       Other methods, such as getNextNode(), setNextNode(), etc.
//       are similar to all of the other ones that I have written in
//       previous programs.
//       Also, I did a lot of cut and paste work with my 'try' and
//       'catch' statemtents.  Although I modified all of them to
//       fit each circumstance, the methods are very similar.



class P5 {

//*** CONSTANTS
public static final boolean DEBUG = false; //turns debugging on/off


//*** INSTANCE VARIABLES
private Airplane myAirplane;



//*** CONSTRUCTOR
/**
  * P5 - makes a new Airplane and calls menu()
  *
  * Pre - none.
  * Post - P5 is initalized.
  */
public P5() {
  myAirplane = new Airplane();
  menu();
}//endmethod P5

/**
  * main - creates a new P5
  *
  * Pre - none.
  * Post - new P5 is created.
  */
public static void main (String argv[]) {
   P5 myP5 = new P5 ();
}//endmethod main


/**
  * menu - displays choices and calls method to read-in choices from the user
  *
  * Pre - call from P5 constructor.
  * Post - selected method is called.
  */
public void menu(){
   boolean keeplooping = true;

   System.out.println("Welcome to Winter 99 Program 5");
   System.out.println("------------------------------\n");

   while(keeplooping) {
      System.out.println("(1) Show Plane Status");      
      System.out.println("(2) Book a Passenger in First Class");      
      System.out.println("(3) Book a Passenger in Coach");      
      System.out.println("(4) Assign a Pilot");      
      System.out.println("(5) Assign a Flight Attendant");
      System.out.println("(6) Add In-Flight Meals");
      System.out.println("(7) Set Flight Plans");
      System.out.println("(8) Take Off!");
      System.out.println("(0) Quit");

      try {
        getChoice();
      }
      catch (InvalidChoiceException e) {
        //do nothing
      }
      catch (NumberFormatException e) {
        //do nothing
      }
   }
}//endmethod menu



/**
  * getChoice - reads the choice from the user and throws invalid choices
  *
  * Pre - call from menu.
  * Post - user's choice is read.
  */
public void getChoice() throws InvalidChoiceException {
  int iChoice;

  if (DEBUG) System.out.println("*** Before reading the value");
  //I have no idea why java is throwing a NullPointerException right here
  //with p5_100.dat  I typed in all of the data in p5_100.dat manually
  //and I didn't get the NullPointer error... strange...
  iChoice = util.readInteger().intValue();
  if (DEBUG) System.out.println("*** After reading the value");


  if ((iChoice >= 0) | (iChoice <= 8)) { 
    if (iChoice == 1)
      myAirplane.getPlaneStatus();
    else if (iChoice == 2)
      myAirplane.addAPassenger("First Class passenger");
    else if (iChoice == 3)
      myAirplane.addAPassenger("Coach passenger");
    else if (iChoice == 4)
      myAirplane.addAPilot();
    else if (iChoice == 5)
      myAirplane.addAFlightAttendant();
    else if (iChoice == 6)
      myAirplane.addMeal();
    else if (iChoice == 7)
      myAirplane.setDestination();
    else if (iChoice == 8)
      myAirplane.takeOff();
    else if (iChoice == 0)
      System.exit(0);
 
  } else {
    throw (new InvalidChoiceException());
  }


}

}//endclass P5
