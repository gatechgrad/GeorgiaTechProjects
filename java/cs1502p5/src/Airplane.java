/**
 * CS1502: Programming Assignment #5 - Winter 1999
 *
 * <PRE>
 * Airport Simulatr
 *
 * Revisions:  1.0  Feb 12, 1999
 *                  Created class Airplane (airplane class)
 * </PRE>
 *
 * @Author: <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 12, 1999
 */

import java.util.Vector;

class Airplane {

//*** CONSTANTS
public static final String DEFAULTNAME = "This plane does not have a name";
  //the default name of the plane
public static final int FA_TO_PASSENGER = 4; //a flight attendant is
                                             //needed for this many
                                             //passengers
public static final boolean DEBUG = false; //debug


//*** INSTANCE VARIABLES
private Vector myFlightList; //a list of people on this plane
private MealList myMealList; // a list of the meals on this flight
private MealList myMealsRequiredList;

private int iMass; //the mass of the people on the plane
private String strDestination; //the destination name
private int iDestination; //the destination length
private String strName;//the name of the airplane
private int iMaxPassengers;//the maximum number of passengers the plane can
                           //hold
private int iPilotsRequired;//the number of pilots required to fly the
                            //plane
private int iNumOfPilots; //current number of pilots
private int iNumOfPassengers; // current number of passengers
private int iNumOfFlightAttendants; //current number of flight attendants


//*** CONSTRUCTORS
/**
  * Airplane - sets up values associated with Airplane
  *
  * Pre - new Airplane must be created from P5.
  * Post - values are initalized.
  */
public Airplane() {
  setName();
  setMaxPassengers();
  setPilotsRequired();
  myFlightList = new Vector();
  iMass = 0;
  myMealList = new MealList();
  myMealsRequiredList = new MealList();
  iNumOfPilots = 0;
  iNumOfPassengers = 0;
  iNumOfFlightAttendants = 0;
}//endmethod Airplane



//*** ACCESSOR METHODS
/**
  * getFlightList - returns the vector myFlightlist
  *
  * Pre - none.
  * Post - myFlightList is returned.
  *
  * @return the people on the plane
  */
public Vector getFlightList() {
   return (myFlightList);
}//endmethod getFlightList

/**
  * getMealList - returns the linked list myMealList
  *
  * Pre - none.
  * Post - myMealList is returned.
  *
  * @return a list of the current meals on the plane
  */
public MealList getMealList() {
   return (myMealList);
}//endmethod getMealList

/**
  * getMass - returns the mass of the plane
  *
  * Pre - none.
  * Post - mass of plane is returned.
  *
  * @return the mass of the plane
  */
public int getMass() {
   return(iMass);
}//endmethod getMass

/**
  * getName - returns the name of the plane
  *
  * Pre - none.
  * Post - the name is returned
  *
  * @return the name of this plane
  */
public String getName() {
  return (strName);
} //endmethod getName

/**
  * getDestination - returns name of the destination
  *
  * Pre - destination must be set.
  * Post - destination name is returned.
  *
  * @return the destination of the plane
  */
public String getDestination() {
  return (strDestination);
}//endmethod getDestination

/**
  * getMaxPassengers - returns the maximum number of passengers the plane
  *                    can hold
  *
  * Pre - none.
  * Post - number is returned.
  *
  * @return the max number of passengers
  */
public int getMaxPassengers() {
  return(iMaxPassengers);
}//endmethod getMaxPassengers

/**
  * getPilotsRequired - returns number of pilots the plane can hold
  *
  * Pre - none.
  * Post - number of pilots is returned.
  *
  * @return the maximum/required number of pilots
  */
public int getPilotsRequired() {
  return(iPilotsRequired);
}//endmethod getPilotsRequired

/**
  * getNumOfPilots - returns the current number of pilots
  *
  * Pre - none.
  * Post - returns the current number of pilots.
  *
  * @return the current number of pilots
  */
public int getNumOfPilots() {
  return(iNumOfPilots);
}//endmethod getNumOfPilots

/**
  * getNumOfPassengers - returns the number of passengers
  *
  * Pre - none.
  * Post - returns the current number of passengers.
  *
  * @return the current number of passengers
  */
public int getNumOfPassengers() {
  return(iNumOfPassengers);
}//endmethod getNumOfPassengers

/**
  * getNumOfFlightAttendants - returns the number of FAs
  *
  * Pre - none.
  * Post - the number of FAs is returned.
  *
  * @return the number of FA currently on the plane
  */
public int getNumOfFlightAttendants() {
  return(iNumOfFlightAttendants);
}//endmethod getNumOfFlightAttendants



/**
  * getPlaneStatus - prints out the current status of the plane (Choice #1)
  *
  * Pre - none.
  * Post - Plane data is printed to the screen.
  *
  */
public void getPlaneStatus() {
  int tempMass = 0;
  System.out.println(strName + "'s roster :");

  for (int i = 0; i < getFlightList().size(); i++) {
    System.out.println(getFlightList().elementAt(i));
    PersonOnPlaneNode newNode =
      (PersonOnPlaneNode) getFlightList().elementAt(i);

    tempMass += newNode.getWeight();
  }

  setMass(tempMass);
  System.out.println("\nMeals on-board :\n");

  myMealList.printMeals();

  System.out.println("Current mass, in kilograms : " + getMass());
}//endmethod getPlaneStatus

/**
  * takeOff - prints wether the plane can take off or not
  *
  * Pre - for plane to take off, the four requirements must be met.
  * Post - if the plane can take off, it makes a call to PilateSpeaking,
  *        if not, it gives the user an error message.
  *
  */
public void takeOff() throws InvalidChoiceException {
  String strTakeOffErrorMessage = ("Sorry, " + getName() +
      " may not take off at this time.");

  if (getNumOfPilots() != getPilotsRequired())
    throw (new InvalidChoiceException(strTakeOffErrorMessage));

  int iFlightAttendantsRequired;

  //if the number of passengers is not divisible by zero, then round up
  if ((getNumOfPassengers() % FA_TO_PASSENGER) != 0)
    iFlightAttendantsRequired = ((getNumOfPassengers() / FA_TO_PASSENGER)
      + 1);
  else
    iFlightAttendantsRequired = (getNumOfPassengers() / FA_TO_PASSENGER);

  if (DEBUG) {
    System.out.println("*** Flight Attendants == " +
      getNumOfFlightAttendants());
    System.out.println("*** Passengers == " + getNumOfPassengers());
    System.out.println("*** Flight Attendants Required == " +
      iFlightAttendantsRequired);
  }

  if (getNumOfFlightAttendants() < iFlightAttendantsRequired)
    throw (new InvalidChoiceException(strTakeOffErrorMessage));

  try {
    checkMeals(myMealList.getHeadPtr(), myMealsRequiredList.getHeadPtr());
  }
  catch (InvalidChoiceException e) {
    throw (new InvalidChoiceException(strTakeOffErrorMessage));
  }

  if ((iDestination == 0) | (strDestination == null))
    throw (new InvalidChoiceException(strTakeOffErrorMessage));

  pilateSpeaking();

}//endmethod takeOff


//*** MODIFIER METHODS

/**
  * setMaxPassengers - sets the maximum passengers on the plane
  *
  * Pre - a call from the constructor.
  * Post - Max number of passengers is set.
  *
  */
public void setMaxPassengers() {
  boolean inputIsGood;

  do {
    System.out.println("Enter the maximum number of passengers that " +
      strName + " may hold :");

    try {
      iMaxPassengers = util.readInteger().intValue();
      inputIsGood = true;

      if ( iMaxPassengers <= 0) {
        System.out.println("Invalid input, please try again.");
        throw (new InvalidChoiceException());
      }
    }
    catch (NumberFormatException e) {
      System.out.println("Invalid input, please try again.");
      inputIsGood = false;
    }
    catch (InvalidChoiceException e) {
      //do nothing
      inputIsGood = false;
    }
  } while (inputIsGood == false);
}


/**
  * setPilotsRequired - sets the maximum/required number of pilots
  *
  * Pre - call from the constructor.
  * Post - max num of pilots is set.
  *
  */

public void setPilotsRequired() {

  boolean inputIsGood;

  do {
    System.out.println("Enter the number of pilots required to fly " +
      strName + " :");


    try {
      iPilotsRequired = util.readInteger().intValue();
      inputIsGood = true;
          
      if ( iPilotsRequired <= 0) {
        System.out.println("Invalid input, please try again.");
        throw (new InvalidChoiceException());
      }

    }
    catch (NumberFormatException e) {
      System.out.println("Invalid input, please try again.");
      inputIsGood = false;
    }
    catch (InvalidChoiceException e) {
      //do nothing
      inputIsGood = false;
    }
  } while (inputIsGood == false);
}// endmethod setPilotsRequired


/**
  * setMass - sets the mass of the plane
  *
  * Pre - none.
  * Post - new mass is set.
  *
  * @param massToAdd the new mass
  */
public void setMass(int massToAdd) {
  iMass = massToAdd;

}//endmethod addMass


/**
  * setName - prompts the user, and sets the name of the plane
  *
  * Pre - plane must be created.
  * Post - name is given to the plane.
  *
  * @return the people on the plane
  */
public void setName() {
  System.out.println("Enter the name of the plane :");
  strName = util.readLine();
}//endmethod setNmae

/**
  * setDestination - sets the name and distance of the destination (Choice 7)
  *
  * Pre - call from P5's menu.
  * Post - destination is set.
  *
  */
public void setDestination () {
  
  System.out.println("Enter " + strName + "'s Destination :");
  strDestination = util.readLine();

  boolean inputIsGood;

  do {
    System.out.println("Enter Distance to " + strDestination +
      ", in kilometers :");


    try {
      iDestination = util.readInteger().intValue();
      inputIsGood = true;

      if ( iDestination <= 0) {
        System.out.println("Invalid input, please try again.");
        throw (new InvalidChoiceException());
      }

    }
    catch (NumberFormatException e) {
      System.out.println("Invalid input, please try again.");
      inputIsGood = false;
    }
    catch (InvalidChoiceException e) {
      //do nothing
      inputIsGood = false;
    }
  } while (inputIsGood == false);
}//endmethod setDestination


/**
  * addAPilot - adds a pilot to the roster
  *
  * Pre - flight list must be created.
  * Post - pilot is added to the roster.
  *
  */
public void addAPilot() {
  try {

    if (DEBUG) System.out.println("*** iNumOfPilots == " + getNumOfPilots());
    if (DEBUG) System.out.println("*** getPilotsRequired == " +
      getPilotsRequired());


    if (getNumOfPilots() >= getPilotsRequired()) {
      System.out.println ("Sorry, " + getName() +
        " does not have any space for another pilot.");
      throw (new InvalidChoiceException());

    } else {
      PilotNode newNode = new PilotNode("Pilot");
      getFlightList().addElement(newNode);
      iNumOfPilots++;
    }
  }
  catch (InvalidChoiceException e) {
  //do nothing, again...
  }
}//endmethod addAPilot

/**
  * addAFlightAttendant - adds a FA to the roster
  *
  * Pre - myFlightList must be created.
  * Post - FA is added to the roster.
  *
  */
public void addAFlightAttendant() {
    PersonOnPlaneNode newNode = new PersonOnPlaneNode("Flight Attendant");
    getFlightList().addElement(newNode);
    iNumOfFlightAttendants++;
}//endmethod addAFlightAttendant

/**
  * addAPassenger - adds a coach or first class passenger to the plane
  *
  * Pre - list must be created.
  * Post - the passenger is added to the list.
  *
  * @return type the type of passenger to be added
  */
public void addAPassenger(String type) {

  try {

    if (getNumOfPassengers() >= getMaxPassengers()) {
      System.out.println ("Sorry, " + getName() +
        " does not have any space for another passenger.");
      throw (new InvalidChoiceException());

    } else {
      PassengerNode newNode = new PassengerNode(type);
      getFlightList().addElement(newNode);
      addNumberOfMeals(type, newNode.getName());
      iNumOfPassengers++;
    }
  }
  catch (InvalidChoiceException e) {
  //do nothing, again...
  }

}//endmethod addAPassenger



/**
  * addNumberOfMeals - adds to the myMealsRequiredList
  *
  * Pre - a call after a passenger has been created.
  * Post - passenger's choices are added to the list.
  *
  * @param type the type of passenger (FC gets 2 meals, Coach gets 1 meal)
  * @param strName the name of the passenger (for prompting)
  */
public void addNumberOfMeals(String type, String strName) {

  int MAXMEALS;
         if (type.equals("First Class passenger")) {
           MAXMEALS = 2;
  } else if (type.equals("Coach passenger")) {

           MAXMEALS = 1;
  } else {
           //this state should never be reached... it is
           //only in here so the file will compile
           System.out.println("Passenger type not supported");
           MAXMEALS = 0;
  }

  for (int i = 0; i < MAXMEALS; i++) {

    if (i == 0) {
      System.out.println("What would " + strName + " like to eat :");
    } else {
      System.out.println("What else would " + strName +
        " like to eat :");
    }
    String newMeal = util.readLine();

    //if headPtr == null, then add 1 Meal to list.  Else, traverse list
    //until that meal is found, and then add one more of that meal.
    //if that meal is not found, then add it to the end of the list.

    addNumberOfMealsHelper(myMealsRequiredList.getHeadPtr(), newMeal);

    if (DEBUG) {
    System.out.println("*** Current Demand:");
    myMealsRequiredList.printMeals();
    }
  }
}//endmethod addMeals

/**
  * addNumberOfMealsHelper - helps the previous method... it goes though
  *                          the list and either adds to an exist meal
  *                          quota or creates the meal on the list
  *
  * Pre - call from addNumberOfMeals.
  * Post - meal is added to the list.
  *
  * @param tempPtr the current position in the MealList
  * @param mealToAdd the name of the meal to be added
  */
public void addNumberOfMealsHelper(MealNode tempPtr, String mealToAdd) {
  if (tempPtr == null) {
    MealNode newNode = new MealNode(mealToAdd, 1);
    myMealsRequiredList.addToList(newNode);
  } else if (tempPtr.getName().equals(mealToAdd)) {
    tempPtr.setNumOfMeals(tempPtr.getMeals() + 1);
  } else {
      addNumberOfMealsHelper(tempPtr.getNextNode(), mealToAdd);
    
  }
}//addNumberOfMealsHelper

/**
  * checkMeals - checks to make sure there are enought meals before the
  *              plane leaves
  *
  * Pre - a call from takeOff.
  * Post - if there are not enough meals, then an exception is thrown.
  *
  * @param availablePtr the current position in myMealList
  * @param requiredPtr the current position in myMealsRequiredList
  */
public void checkMeals(MealNode availablePtr, MealNode requiredPtr)
  throws InvalidChoiceException {
  if (requiredPtr != null) {
    if (availablePtr == null) {
      throw (new InvalidChoiceException());
    }

    if ((requiredPtr.getName().equals(availablePtr.getName())) &&
      (requiredPtr.getMeals() < availablePtr.getMeals())) {
      if (DEBUG) System.out.println ("*** There are enough " +
        requiredPtr.getName());
      checkMeals(myMealList.getHeadPtr(), requiredPtr.getNextNode());
    } else if (availablePtr == null) {
      //this is probably 'junk' code

      throw (new InvalidChoiceException("Sorry, " + getName() +
        " may not take off at this time"));

    } else {
      checkMeals(availablePtr.getNextNode(), requiredPtr);
    }
  }
}//checkmeals

/**
  * addMeal - adds a new meal to the myMealList
  *
  * Pre - myMealList must be created.
  * Post - the meal that the user was prompted for is added to the list.
  *
  */
public void addMeal() {
  String mealName;
  int mealNumber;
  boolean inputIsGood;

    System.out.println("Name of Meal :");
    mealName = util.readLine();
  
  do {
    System.out.println("Number of " + mealName + " entrees :");

    try {
      mealNumber = util.readInteger().intValue();
      inputIsGood = true;

      if ( mealNumber <= 0) {
        System.out.println("Invalid input, please try again.");
        throw (new InvalidChoiceException());

      } else {
        MealNode newMealNode = new MealNode(mealName, mealNumber);
        myMealList.addToList(newMealNode);
      }

    }
    catch (NumberFormatException e) {
      System.out.println("Invalid input, please try again.");
      inputIsGood = false;
    }
    catch (InvalidChoiceException e) {
      //do nothing
      inputIsGood = false;
    }
  } while (inputIsGood == false);
}//endmethod addMeals


/**
  * pilateSpeaking - a message printed after takeOff
  *
  * Pre - all four conditions for takeOff must be met.
  * Post - fastest pilot is found and his message is printed to the screen.
  *
  */

public void pilateSpeaking() {
  //find a speedy pilot
  PilotNode speedyPilot = null;
  PilotNode currentPilot;
  double dFlightTime;

  for (int i = 0; i < getFlightList().size(); i++) {
    if (getFlightList().elementAt(i) instanceof PilotNode) {

      if (speedyPilot == null) {
        speedyPilot = (PilotNode) getFlightList().elementAt(i);
      } else {

        currentPilot = (PilotNode) getFlightList().elementAt(i);

        if (currentPilot.getFlyingSpeed() > speedyPilot.getFlyingSpeed())
          speedyPilot = currentPilot;


      }
    }
  }
  dFlightTime = ((double) iDestination / speedyPilot.getFlyingSpeed());

  System.out.println(speedyPilot.getName() + " : We're on our way to " +
    strDestination + ", which is " + iDestination + " kilometers away.");
  System.out.println(speedyPilot.getName() + " : At my speed of " +
    speedyPilot.getFlyingSpeed() +
    " kilometers per hour, the flight time will be");
  System.out.println(speedyPilot.getName() + " : " + dFlightTime +
    " hours.");                                        

  System.exit(0);

}



}//endclass Airplane






