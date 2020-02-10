#include "store.h"

store::store(ansicolor *aColorControl, sysop *newSysop) {
  //strName and strNumber should be
  //set in subclass constructor
  strName = "No name specified";
  strNumber = "No number specified";
  ColorControl = aColorControl;
  TheSysop = newSysop;

  cout << endl << endl;
}


void store::displayMenu() {
  char chChoice;

  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << ' ' << strName << " For information call " << strNumber 
       << endl;
  ColorControl->setColor(DARK, FG, A_YELLOW);
  cout << "______________________________________________________________________"
       << endl;


  for (vector<menu>::iterator i = menuList.begin();
         i != menuList.end(); i++) {
    cout << i->toString() << endl;
  }

  chChoice = getCharacter();

  for (vector<menu>::iterator i = menuList.begin();
         i != menuList.end(); i++) {
    if (tolower(chChoice) == tolower(i->getChoiceLetter())) {
      cout << i->toString() << endl;

    }
  }

}

/*================ METHODS FOR COMPUTER STORE ================*/
computerstore::computerstore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  string strMenuName, strItemName, strItemDescription;

  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";


  strMenuName = "odems";
  menu menuModem('M', strMenuName);

    strItemName = "2400";
    strItemDescription = "Plain no frills 2400bps not auto answer!";
    menuitem miModem1('1', 70,   0, strItemName, strItemDescription);

    strItemName = "2400";
    strItemDescription = "Nice no frills 2400bps auto answer";
    menuitem miModem2('2', 100,  0, strItemName, strItemDescription);

    strItemName = "2400 v.42";
    strItemDescription = "Nice no frills 2400bps auto answer";
    menuitem miModem3('3', 150,  0, strItemName, strItemDescription );

    strItemName = "9600";
    strItemDescription = "Off brand 9600bps modem, req 286";
    menuitem miModem4('4', 550,  1, strItemName, strItemDescription );

    strItemName = "9600 v.32";
    strItemDescription = "Nice world standard v.32 9600bps, req 386";
    menuitem miModem5('5', 800,  1, strItemName, strItemDescription);

    strItemName = "9600ds";
    strItemDescription = "Dual standard 9600bps modem, req 386";
    menuitem miModem6('6', 1000, 1, strItemName, strItemDescription);

    strItemName = "14400bps v.32bis";
    strItemDescription = "Good high speed modem, req fast 386";
    menuitem miModem7('7', 1100, 2, strItemName, strItemDescription);

    strItemName = "19.2kbps v.32ter";
    strItemDescription = "Fast non-standard, req 486";
    menuitem miModem8('8', 1200, 3, strItemName, strItemDescription);

    strItemName = "24.0kbps v.FC";
    strItemDescription = "Fast pre-standard, req 486";
    menuitem miModem9('9', 1300, 4, strItemName, strItemDescription);

    strItemName = "28.8kbps v.Fast";
    strItemDescription = "Fast standard, req 486";
    menuitem miModem10('A', 1400, 5, strItemName, strItemDescription);

    menuModem.addMenuItem(miModem1);
    menuModem.addMenuItem(miModem2);
    menuModem.addMenuItem(miModem3);
    menuModem.addMenuItem(miModem4);
    menuModem.addMenuItem(miModem5);
    menuModem.addMenuItem(miModem6);
    menuModem.addMenuItem(miModem7);
    menuModem.addMenuItem(miModem8);
    menuModem.addMenuItem(miModem9);
    menuModem.addMenuItem(miModem10);


  strMenuName = "torage";
  menu menuStorage('S', strMenuName);

    strItemName = "30meg 60ms";
    strItemDescription = "XT or higher";
    menuitem miStorage1('1', 500,    0, strItemName, strItemDescription);

    strItemName = "100meg 29ms";
    strItemDescription = "XT or higher";
    menuitem miStorage2('2', 1000,   1, strItemName, strItemDescription);

    strItemName = "300meg 16ms";
    strItemDescription = "AT/286 or higher";
    menuitem miStorage3('3', 2000,   2, strItemName, strItemDescription);

    strItemName = "600meg 15ms";
    strItemDescription = "AT/286 or higher";
    menuitem miStorage4('4', 6000,   3, strItemName, strItemDescription);

    strItemName = "1.2 Gig 3ms";
    strItemDescription = "386 or higher";
    menuitem miStorage5('5', 80000,  4, strItemName, strItemDescription);

    strItemName = "6 Gig 3ms";
    strItemDescription = "486 or higher";
    menuitem miStorage6('6', 150000, 5, strItemName, strItemDescription);

    strItemName = "Raid";
    strItemDescription = "Mini v10 or higher";
    menuitem miStorage7('7', 250000, 6, strItemName, strItemDescription);

    menuStorage.addMenuItem(miStorage1);
    menuStorage.addMenuItem(miStorage2);
    menuStorage.addMenuItem(miStorage3);
    menuStorage.addMenuItem(miStorage4);
    menuStorage.addMenuItem(miStorage5);
    menuStorage.addMenuItem(miStorage6);
    menuStorage.addMenuItem(miStorage7);

  strMenuName = "omputer";
  menu menuComputer('C', strMenuName);

    strItemName = "XT";
    strItemDescription = "none";
    menuitem miComputer1('1', 0,    0, strItemName, strItemDescription);

    strItemName = "286";
    strItemDescription = "XT or higher";
    menuitem miComputer2('2', 1000, 1, strItemName, strItemDescription);

    strItemName = "386";
    strItemDescription = "AT/286 or higher";
    menuitem miComputer3('3', 1400, 2, strItemName, strItemDescription);

    strItemName = "486/25";
    strItemDescription = "386 or higher";
    menuitem miComputer4('4', 4400, 3, strItemName, strItemDescription);

    strItemName = "486/50";
    strItemDescription = "486 or higher";
    menuitem miComputer5('5', 6400, 4, strItemName, strItemDescription);

    strItemName = "486/50 v9";
    strItemDescription = "486 v4 through 486 v9 or higher";
    menuitem miComputer6('6', -1,   5, strItemName, strItemDescription);

    strItemName = "586/99";
    strItemDescription = "XT or higher";
    menuitem miComputer7('7', -1,   6, strItemName, strItemDescription);

    menuComputer.addMenuItem(miStorage1);
    menuComputer.addMenuItem(miStorage2);
    menuComputer.addMenuItem(miStorage3);
    menuComputer.addMenuItem(miStorage4);
    menuComputer.addMenuItem(miStorage5);
    menuComputer.addMenuItem(miStorage6);
    menuComputer.addMenuItem(miStorage7);


  menuList.push_back(menuModem);
  menuList.push_back(menuStorage);
  menuList.push_back(menuComputer);


  displayMenu();
}


/*
  if (CurrentPlayer->getSoftware() > 2 && cChoice == 1) {
    ColorControl->setColor(BRIGHT, FG, A_YELLOW);
    cout << "You spend a whole weekend trying to connect the "
         << "modem and make it work with" << endl
         << "the BBS software that you have. The store that you "
         << "got the modem from doesn't" << endl
         << "know anything about setting it up so you pay an "
         << "expert $20 to make it work." << endl;
    //CurrentPlayer->setMoney(-20);

void computerstore::printSelection(char cChoice, string strItemName,
      int iPrice, string strDescription) {
  unsigned int i, iSpacing;

  iSpacing = 20;
  cout << "   (";
  cout << cChoice;
  cout << ") ";
  cout << strItemName;

  for(i = 0; i < (iSpacing - strItemName.length()); i++) {
    cout << ' ';
  }
 
  cout << '$' << iPrice;
  cout << " - ";
  cout << strDescription << endl; 
}

*/

/*================ METHODS FOR DEPARTMENT STORE ================*/
departmentstore::departmentstore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  strName = "Major Department Store Chain";
  strNumber = "555-1212";

  string strMenuName, strItemName, strItemDescription;

  strMenuName = "odems";
  menu menuModem('M', strMenuName);

    strItemName = "2400";
    strItemDescription = "Plain no frills 2400bps not auto answer!";
    menuitem miModem1('1', 70,   0, strItemName, strItemDescription);

    strItemName = "2400";
    strItemDescription = "Nice no frills 2400bps auto answer";
    menuitem miModem2('2', 100,  0, strItemName, strItemDescription);

    strItemName = "2400 v.42";
    strItemDescription = "Nice no frills 2400bps auto answer";
    menuitem miModem3('3', 150,  0, strItemName, strItemDescription );

    strItemName = "9600";
    strItemDescription = "Off brand 9600bps modem, req 286";
    menuitem miModem4('4', 550,  1, strItemName, strItemDescription );

    strItemName = "9600 v.32";
    strItemDescription = "Nice world standard v.32 9600bps, req 386";
    menuitem miModem5('5', 800,  1, strItemName, strItemDescription);

    strItemName = "9600ds";
    strItemDescription = "Dual standard 9600bps modem, req 386";
    menuitem miModem6('6', 1000, 1, strItemName, strItemDescription);

    strItemName = "14400bps v.32bis";
    strItemDescription = "Good high speed modem, req fast 386";
    menuitem miModem7('7', 1100, 2, strItemName, strItemDescription);

    strItemName = "19.2kbps v.32ter";
    strItemDescription = "Fast non-standard, req 486";
    menuitem miModem8('8', 1200, 3, strItemName, strItemDescription);

    strItemName = "24.0kbps v.FC";
    strItemDescription = "Fast pre-standard, req 486";
    menuitem miModem9('9', 1300, 4, strItemName, strItemDescription);

    strItemName = "28.8kbps v.Fast";
    strItemDescription = "Fast standard, req 486";
    menuitem miModem10('A', 1400, 5, strItemName, strItemDescription);

    menuModem.addMenuItem(miModem1);
    menuModem.addMenuItem(miModem2);
    menuModem.addMenuItem(miModem3);
    menuModem.addMenuItem(miModem4);
    menuModem.addMenuItem(miModem5);
    menuModem.addMenuItem(miModem6);
    menuModem.addMenuItem(miModem7);
    menuModem.addMenuItem(miModem8);
    menuModem.addMenuItem(miModem9);
    menuModem.addMenuItem(miModem10);


  strMenuName = "torage";
  menu menuStorage('S', strMenuName);

    strItemName = "30meg 60ms";
    strItemDescription = "XT or higher";
    menuitem miStorage1('1', 500,    0, strItemName, strItemDescription);

    strItemName = "100meg 29ms";
    strItemDescription = "XT or higher";
    menuitem miStorage2('2', 1000,   1, strItemName, strItemDescription);

    strItemName = "300meg 16ms";
    strItemDescription = "AT/286 or higher";
    menuitem miStorage3('3', 2000,   2, strItemName, strItemDescription);

    strItemName = "600meg 15ms";
    strItemDescription = "AT/286 or higher";
    menuitem miStorage4('4', 6000,   3, strItemName, strItemDescription);

    strItemName = "1.2 Gig 3ms";
    strItemDescription = "386 or higher";
    menuitem miStorage5('5', 80000,  4, strItemName, strItemDescription);

    strItemName = "6 Gig 3ms";
    strItemDescription = "486 or higher";
    menuitem miStorage6('6', 150000, 5, strItemName, strItemDescription);

    strItemName = "Raid";
    strItemDescription = "Mini v10 or higher";
    menuitem miStorage7('7', 250000, 6, strItemName, strItemDescription);

    menuStorage.addMenuItem(miStorage1);
    menuStorage.addMenuItem(miStorage2);
    menuStorage.addMenuItem(miStorage3);
    menuStorage.addMenuItem(miStorage4);
    menuStorage.addMenuItem(miStorage5);
    menuStorage.addMenuItem(miStorage6);
    menuStorage.addMenuItem(miStorage7);

  strMenuName = "omputer";
  menu menuComputer('C', strMenuName);

    strItemName = "XT";
    strItemDescription = "none";
    menuitem miComputer1('1', 0,    0, strItemName, strItemDescription);

    strItemName = "286";
    strItemDescription = "XT or higher";
    menuitem miComputer2('2', 1000, 1, strItemName, strItemDescription);

    strItemName = "386";
    strItemDescription = "AT/286 or higher";
    menuitem miComputer3('3', 1400, 2, strItemName, strItemDescription);

    strItemName = "486/25";
    strItemDescription = "386 or higher";
    menuitem miComputer4('4', 4400, 3, strItemName, strItemDescription);

    strItemName = "486/50";
    strItemDescription = "486 or higher";
    menuitem miComputer5('5', 6400, 4, strItemName, strItemDescription);

    strItemName = "486/50 v9";
    strItemDescription = "486 v4 through 486 v9 or higher";
    menuitem miComputer6('6', -1,   5, strItemName, strItemDescription);

    strItemName = "586/99";
    strItemDescription = "XT or higher";
    menuitem miComputer7('7', -1,   6, strItemName, strItemDescription);

    menuComputer.addMenuItem(miStorage1);
    menuComputer.addMenuItem(miStorage2);
    menuComputer.addMenuItem(miStorage3);
    menuComputer.addMenuItem(miStorage4);
    menuComputer.addMenuItem(miStorage5);
    menuComputer.addMenuItem(miStorage6);
    menuComputer.addMenuItem(miStorage7);


  menuList.push_back(menuModem);
  menuList.push_back(menuStorage);
  menuList.push_back(menuComputer);


  displayMenu();
}


/*================ METHODS FOR SOFTWARE STORE ================*/
softwarestore::softwarestore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}


/*================ METHODS FOR DISCOUNT STORE ================*/
discountstore::discountstore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}


/*================ METHODS FOR LOCAL STORE ================*/
localstore::localstore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}


/*================ METHODS FOR CORPORATION STORE ================*/
corporationstore::corporationstore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}


/*================ METHODS FOR PHONE STORE ================*/
phonestore::phonestore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}


/*================ METHODS FOR ADVERTISING STORE ================*/
advertisingstore::advertisingstore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}


/*================ METHODS FOR GROCERY STORE ================*/
grocerystore::grocerystore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop){
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}


/*================ METHODS FOR OTHER STORE ================*/
otherstore::otherstore(ansicolor *aColorControl, sysop *newSysop):
    store(aColorControl, newSysop) {
  cout << "computer store constructor called" << endl;
  strName = "Big Deal Computer Retailer";
  strNumber = "555-1212";
  displayMenu();
}















/*
Available Items:
   (P)rice list
   (M)odems
   (S)torage devices
   (C)omputers
   (E)xit

Choice: Computer

You have a 12Mhz 286 with 1meg RAM

The store accepts your old computer on trade-in (at a big discount) and
gives you a deal on a new 386 system.
----------------------
  (S)ervice call
  (R)eport on account
  (E)xit phone store

Choice: Add

You have 5 lines in service and 0 lines on order.

How many lines do you want to install? 2

This installation will cost $105.00
Monthly service will be $100.00 in addition to current service.

Do you want to order them? (Y/n) Yes

Your order has been placed into the computer. It often takes several days
to get someone out to your location to install the lines.

  (P)rice list
  (A)dd new lines
  (C)allerID
--------------------------
   ( ) 19.2kbps v.32ter   1,200- Fast non-standard, req 486
   ( ) 24.0kbps v.FC      1,300- Fast pre-standard, req 486
   ( ) 28.8kbps v.Fast    1,400- Fast standard, req 486
   (E)xit

Choice: 3

ansicolor.setColor(BRIGHT, FG, A_WHITE);
You can afford up to 2 modems.
ansicolor.setColor(BRIGHT, FG, A_GREEN);
Every modem you buy will take one action to install.
ansicolor.setColor(BRIGHT, FG, A_WHITE);
How many do you want now? 2

ansicolor.setColor(BRIGHT, FG, A_GREEN);
You buy 2 modems.

*/
