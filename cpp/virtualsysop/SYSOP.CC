#include "sysop.h"
sysop::sysop(ansicolor *aColorControl,
             string aName, string aBBS, string aDateC, string aDateL,
             int aScore, int aFree,
             int aPaying, int aActions,
             int aMorale, int aComputer,
             int aStorage, int a0Modems,
             int a1Modems, int a2Modems, int a3Modems,
             int a4Modems, int aSoftware,
             int aLines, int aEdu1,
             int aEdu2, int aCID,
             int aSec, int aMoney){
    //time_t aTime;
    iComputer = aComputer; 
    iStorage = aStorage;
    iModems[0] = a0Modems;
    iModems[1] = a1Modems;
    iModems[2] = a2Modems;
    iModems[3] = a3Modems;
    iModems[4] = a4Modems;
    iSoftware = aSoftware; 
    iUsersPaying = aPaying;
    iUsersFree = aFree;  
    iUserPayment = 0;
    iLines = aLines; 
    iExperience = 0;
    iEducationPublic = aEdu1;
    iEducationPersonal = aEdu2;
    iMoney = aMoney;
    iActions = aActions; 
    iScore = aScore;
    iMessages = 0; 
    iMembershipPlan = 0;
    iAdvertisement = 10;
    iCallerID = aCID; 
    iSecurity = aSec;
    strName = aName;
    strSystemName = aBBS;
    ColorControl = aColorControl;;
    strCreationDate = aDateC;
    strLastOnDate = aDateL;   
    iTurnsSinceVirusDetection = 0;
    iTraffic = 0;

  /* randomize numbers */
  //aTime = time(0);
  //srand(aTime);
}

void sysop::answerChat() {
  int iChatMessage;

  cout << "nswer Chat" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  switch(iChatRequest) {
    case 0:
      cout << "No chat request is pending.";
      break;
    case 1:
    iChatRequest = 0;
     iChatMessage = (rand() % 2);
     switch(iChatMessage) {
      case 0:
       cout << "You catch the user as they are about to hang "
            << "up and chat for a minute.";
       break;

      case 1:
      cout << "You answer the chat and type several prompting "
           << "messages, but the user never" << endl
           << "types anything back. Maybe it was line noise "
           << "during an automated call.";

      break;

      case 2:
       cout << "You answer the chat and the slow typing user tries "
            << "his best to type fast" << endl
            << "which makes lots of mistakes. You do you best to "
            << "decrypt his spellings and" << endl
            << "help the user find the file area that he wanted.";
       break;

     default:
       cout << "You catch the user as they are about to hang "
            << "up and chat for a minute.";
     }
     break;
    case 2:
      iChatRequest = 0;
      cout << "That user is writing a message to " 
           << "you right now. Maybe you should answer"
           << endl << "your mail.";
      break;

    default:
      cout << "No chat request is pending.";
  }
  cout << endl << endl;
}

void sysop::balance() {
  cout << "ank" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "Your bank reports your balance as being: $"
       << iMoney << ".00\n" << endl;
}

void sysop::goToStore() {
  char cChoice;
  bool blKeepLooping;
  store *myComputerStore;
  store *myDepartmentStore;

  blKeepLooping = true;
  iActions--;

  cout << "tore" << endl << endl;

  while (blKeepLooping) {
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Welcome to the shopping center" << endl;
  ColorControl->setColor(DARK, FG, A_YELLOW);
  cout << "____________________________________________________________"
       << endl;
  storeChoice('1', "Big Deal Computer Retailer", "computer");
  storeChoice('2', "Major Department Store Chain", "department");
  storeChoice('3', "Software Retail Outlet Store", "software");
  storeChoice('4', "Mr. Discount House", "discount");
  storeChoice('5', "Local Mom & Pop Store", "local");
  storeChoice('6', "Industrial BigBiz Sales Office", "corporation");
  storeChoice('7', "Phone Company", "phone");
  storeChoice('8', "Advertising Center", "advertising");
  storeChoice('9', "Food Price Grocery", "grocery");
  storeChoice('A', "Health Clinic", "other");
  storeChoice('E', "Exit", "");
  cout << endl;

  ColorControl->setColor(BRIGHT, FG, A_WHITE);
  cout << "Choice: ";
  cChoice = getCharacter();

  switch(cChoice) {

    case '1':
      myComputerStore = new computerstore(ColorControl, this);
      break;

    case '2':
      myDepartmentStore = new departmentstore(ColorControl, this);
      break;
/*
    case '3':
      aStore = new softwarestore(ColorControl, this);
      break;
    case '4':
      aStore = new discountstore(ColorControl, this);
      break;
    case '5':
      aStore = new localstore(ColorControl, this);
      break;
    case '6':
      aStore = new corporationstore(ColorControl, this);
      break;
    case '7':
      aStore = new phonestore(ColorControl, this);
      break;
    case '8':
      aStore = new advertisingstore(ColorControl, this);
      break;
    case '9':
      aStore = new grocerystore(ColorControl, this);
      break;
    case 'A':
      aStore = new otherstore(ColorControl, this);
      break;
*/
    default:
      blKeepLooping = false;
      ColorControl->cursorLeft(1);
      cout << "Exit";
  }
  //delete(aStore);
  cout << endl << endl;
 }
}

void sysop::storeChoice(char cSelection, string strStoreName, 
      string strStoreType) {
  int iSpace, i;
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "   " << cSelection << " - ";
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << strStoreName;
  iSpace = 31 - strStoreName.length();
  for (i = 0; i < iSpace; i++) {
    cout << " ";
  }
  ColorControl->setColor(DARK, FG, A_GREEN);
  if (strStoreType != "") {
    cout << "(" << strStoreType << ")";
  }
  cout << endl;
}

void sysop::chargeUsers() {
  int iUsersStay, iUsersLeave, iUsersRemain;
  char cChoice;
  cout << "harge Users" << endl << endl;

  ColorControl->setColor(BRIGHT, FG, A_CYAN);

  if ((iUsersFree + iUsersPaying) < USERS_FOR_CHARGE) {
    cout << "The few users you have decide that "
         << "your small system is not worth paying for."
         << endl;
    cout << "They all say that they will not pay until "
         << "your system is larger. Maybe you" << endl;
    cout << "should put some work into the setup before "
         << "trying to charge for access. At" << endl;
    cout << "the very least you should try to attract "
         << "a few more users." << endl;
  } else {
    if (iMembershipPlan == 0) {
      cout << " Charging for access to your system may cause a "
           << "drop in the number of calls." << endl
           << " Are you sure that you want to charge for "
           << "access? (y/N)" << endl << endl;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << "Options" << endl;
      ColorControl->setColor(BRIGHT, FG, A_WHITE);
      cout << "  (0) None      -Free access to all" << endl;
      cout << "  (1) Lifetime  -Users pay only once" << endl;
      cout << "  (2) Yearly    -Users pay each year" << endl;
      cout << "  (3) Monthly   -Users pay each month"
           << endl;
      cout << "  (4) Weekly    -Users pay each week" << endl;
      cout << "  (5) Hourly    -Users pay for connect time only"
           << endl;
      cout << "  (6) Download  -Users pay for bytes received"
           << endl << endl;

      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << "Choice> ";
      // Quick hack... I'll try to fix this later
      iMembershipPlan = getCharacter() - 48;

      iUsersRemain = (int) (iUsersFree * 0.25);
      iUsersStay = (int) ((iUsersFree - iUsersRemain) * 0.90);
      iUsersLeave = (iUsersFree - iUsersRemain) - iUsersStay; 
      iUsersPaying += iUsersStay;
      iUsersFree -= (iUsersStay + iUsersLeave);
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << endl;
      cout << iUsersStay << " users decide to pay." << endl;
      cout << iUsersLeave << " users decide to leave." << endl;

    } else if (iUsersFree > 0) {
        ColorControl->setColor(BRIGHT, FG, A_WHITE);
        cout << "Would you like to change how you are "
             << "charging your new users? (y/N)";
        ColorControl->setColor(BRIGHT, FG, A_GREEN);
        cChoice = getCharacter();

       if (cChoice == 'Y' || cChoice == 'y') {
          cout << "es" << endl;
       } else { 
        if (cChoice == 'N' || cChoice == 'n') {
          cout << 'o' << endl;
        } else {
          cout << "No" << endl;
        }
  
        switch(rand() % 1) {
        case 0:  
          cout << "You spend several hours reworking the menus and "
               << "ANSI color of your screens" << endl
               << "and setup special sections for your paying users. "
               << "Then you remind them about" << endl
               << "the cost of running your board and request their "
               << "help in supporting it." << endl << endl;
        break;

        case 1:
          cout << "You rework some of your bulletins and remind "
               << "the free access users about the" << endl
               << "benefits of joining your service and helping to "
               << "support it." << endl << endl;
        break;
        
        default: 
          cout << "You rework some of your bulletins and remind "
               << "the free access users about the" << endl
               << "benefits of joining your service and helping to "
               << "support it." << endl << endl;
        } 
       
        cout << "You charge them for a " 
             << membershipToString(iMembershipPlan)
             << " membership." << endl << endl;
     
        iUsersRemain = (int) (iUsersFree * 0.25);
        iUsersStay = (int) ((iUsersFree - iUsersRemain) * 0.90);
        iUsersLeave = (iUsersFree - iUsersRemain) - iUsersStay; 
        iUsersPaying += iUsersStay;
        iUsersFree -= (iUsersStay + iUsersLeave);
        ColorControl->setColor(BRIGHT, FG, A_GREEN);
        cout << endl;
        cout << iUsersStay << " users decide to pay." << endl;
        cout << iUsersLeave << " users decide to leave." << endl;
       }
      } else {
        cout << "All of your users are already paying for access. "
             << "Try to get some more free" << endl
             << "users to call your system." << endl << endl;
      }
    }
  cout << endl;
}

void sysop::employWorkers() {
  cout << "mploy Workers" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  if (iLines < LINES_TO_EMPLOY) {
    cout << "You contact the local labor union and "
         << "they inform you that your BBS is" << endl
         << "too small. Please check back when you "
         << "are a little larger. You need at" << endl
         << "least " << LINES_TO_EMPLOY 
         << " lines to be able to hire employees "
         << "from this office." << endl << endl;
  } else {
  }


}

void sysop::report() {
  
  cout << "eport" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << " Report on the setup of " 
       << strSystemName << " BBS" << endl;
  ColorControl->setColor(DARK, FG, A_GREEN);
  cout << "___________________________________________________________________________" 
       << endl;
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "   Computer: "
       << computerToString(iComputer) << endl;
  cout << "    Storage: "
       << storageToString(iStorage) << endl;
  if (iModems[0] > 0) {
    cout << "     Modems: "
         << iModems[0] << " at " 
         << modemToString(0) << endl;
  }
  if (iModems[1] > 0) {
    cout << "Fast Modems: "
         << iModems[1] << " at "
         << modemToString(1) << endl;
  }
  if (iModems[2] > 0) {
    cout << "V.xx Modems: "
         << iModems[2] << " at "
         << modemToString(2) << endl;
  }
  cout << "   Software: "
       << "supports only one line" << endl;
  // << "supports 8 lines. (4 ports unused)" << endl;
  
  cout << "      Users: "
       << iUsersFree << " free, "
       << iUsersPaying << " paying." << endl;
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "Phone Lines: "
       << iLines;
  //<< "(X lines unused)" << endl;
       // << "5 (1 line unused.)"
  if (iLines < iSoftware) {
    cout << "(" << (iSoftware - iLines) 
         << " MORE LINES NEEDED)";
  }
  cout << endl;

  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << " Experience: "
       << "Absolute Beginner Sysop" << endl;

  /* print Education status */
  cout << "  Education: ";
  if (iEducationPublic > 0) {
    cout << iEducationPublic;
  } else {
    cout << "No";
  }
  cout << " Public, ";
  if (iEducationPersonal > 0) {
    cout << iEducationPersonal;
  } else {
    cout << "No";
  }
  cout << " Personal" << endl;

  /* print caller ID level */
  cout << "  Caller ID: ";
  if (iCallerID > 0) {
    cout << " Caller validation level " 
         << iCallerID << endl;
  } else {
    cout << "None." << endl;
  }
 
  /* print security level */ 
  cout << "   Security: ";
  if (iSecurity > 0) {
    cout << iSecurity << endl;
  } else {
    cout << "None." << endl;
  }

  /* print remaining actions */
  cout << "    Actions: "
       << iActions << " left today" << endl;
  if (iMessages > 0) {
  cout << "   Messages: " << iMessages <<
          " waiting to be answered" << endl;
  }
 
  /* Print the player's score */
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "      Score: ";
  iScore = getScore();
  if (iScore > 0) {
    cout << iScore << " points!" << endl;
  } else {
    cout << "Nothing! (Work harder!)" << endl << endl;
  }
}

void sysop::titleOfBBS() {
  char chInput;
  string strNewName;

  cout << "itle" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_MAGENTA);
  cout << "You are in the list as: ";

  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << '\"' << strSystemName << '\"' << endl << endl;

  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "Do you want to change the title of "
       << "your BBS? (y/N)";

  chInput = getCharacter();
  ColorControl->setColor(BRIGHT, FG, A_WHITE);
  switch(chInput) {
    case 'y':
    case 'Y':
      cout << "The local directory of Bulletin "
           << "Board Systems has some limits on "
           << "what they are" << endl
           << "able to include in their list. The "
           << "list has space for up to " << MAX_SYS_NAME_LEN 
           << "characters." << endl << endl;
      cout << "What is your BBS called? "
           << "[..............................]";
      ColorControl->cursorLeft((MAX_SYS_NAME_LEN + 1));
      
      //cin >> chInput;

      getline(cin, strNewName);
      if (strNewName.length() <= MAX_SYS_NAME_LEN) {
        strSystemName = strNewName;
        ColorControl->setColor(BRIGHT, FG, A_GREEN);
        cout << "Title has been changed to: "
             << strSystemName << endl;
      } else {
        cout << "That name is too long" << endl;
      } 
      break;

    default:
      cout << "The title of your BBS was not changed" << endl;
  }

}

void sysop::virusDetection() {
}

void sysop::inspect() {
  cout << "nspect" << endl << endl;
/*
 Your nearest competition:
 Report on the setup of Firehawk Online BBS
   Computer: 50Mhz 486 with 16meg RAM
    Storage: 6,000meg 3ms (cached) with 8meg buffering
     Modems: 4 at 2400bps
Fast Modems: 16 at 9600bps
V.xx Modems: 21 at 19.2Kbps or faster
   Software: supports 64 lines. (23 ports unused)
      Users: 17 free, 3,879 paying.

 This is the latest available information about your nearest competition.
 No warranty is made on the reliability of this information. Use at your
 own risk. The cost of this report has been deducted from your account.
*/

/*
 ansicolor.setColor(BRIGHT, FG, A_CYAN);
 Your nearest competition:
 Report on the setup of Legendary Boundaries BBS  (WOMBAT_NET node 131.131)
 ansicolor.setColor(DARK, FG, A_CYAN);
DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
  ansicolor.setColor(BRIGHT, FG, A_YELLOW);
   Computer: Mainframe with OS emulator and virtual memory version 168
    Storage: 83 Gigabyte Raid subsystems
     Modems: 36,186 at 2400bps
Fast Modems: 19,554 at 9600bps
V.xx Modems: 500 at 19.2Kbps or faster
   Software: supports 99,999 lines. (43,759 ports unused)
      Users: 2,742 free, 1,677,014 paying.

  ansicolor.setColor(BRIGHT, FG, A_YELLOW);
 This is the latest available information about your nearest competition.
 No warranty is made on the reliability of this information. Use at your
 own risk. The cost of this report has been deducted from your account.
*/
}


void sysop::work() {
  int iRandom;
 
  cout << "ork" << endl << endl;
 if (iActions > 0) {
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  
  iRandom = (rand() % 50);
  if (((iRandom == 0) && (iMembershipPlan > 0))) {
    ColorControl->setColor(BRIGHT, FG, A_YELLOW);
    cout << "   * Some subscription renewals arrive in the mail. *"
         << endl;
    cout << "     2 free users start paying too."
         << endl << endl;
    iMoney += iUsersPaying * 50;
    ColorControl->setColor(BRIGHT, FG, A_CYAN);
  }

 
  iMessages += (int)((iUsersFree + iUsersPaying) * .1);
  iActions --;
  iTurnsSinceVirusDetection++;

   /* Check for lose a turn */
   iRandom = (rand() % 250);
   if (iRandom == 0) {
     ColorControl->setColor(BRIGHT, FG, A_CYAN);
     cout << "You work a little faster, but it takes more "
          << "energy. Mistakes are likely."  << endl << endl;
     iActions--;
   }

  /* get a work event */
  processEvent();

   /* Check for virus attack */
   iRandom = (rand() % 25);
   if ((iRandom == 0) && (iTurnsSinceVirusDetection > 
       TURNS_FOR_VIRUS)) {
     virusAttack();
   }

   /* Check for chat request */
   iRandom = (rand() % 30);
   if ((iRandom == 0) && (iChatRequest == 0)) {
    ColorControl->setColor(BRIGHT, FG, A_MAGENTA);
    cout << "* A Chat Request has been made." << endl << endl;
    iChatRequest++;
   } else if (iChatRequest == 1 || iChatRequest == 2) {
     ColorControl->setColor(BRIGHT, FG, A_MAGENTA);
     cout << "* A Chat Request is still pending." << endl << endl;
     iChatRequest++;
   } else if (iChatRequest == 3) {
     iChatRequest = 0; 
   }

   /* Check for traffic problems */
   if (iTraffic > 80) {
     ColorControl->setColor(BRIGHT, FG, A_YELLOW);
     cout << "You hear complaints about busy signals from "
          << "your users." << endl;
     cout << "The 2400 baud users are asking for more lines. "
          << endl;
     if ((rand() % 20) == 1) {
       int iUsersLeave;
       iUsersLeave = iUsersFree + (iUsersPaying / 5);
       cout << iUsersLeave << " users get mad and stop calling "
            << "because they can't get through." << endl << endl;
       ColorControl->setColor(BRIGHT, FG, A_RED);
       cout << "* WARNING: Paying users are leaving your board!"
            << endl << endl;
     }
   }

 
 } else {
   outOfTurns();
 }
}


/**
  * virusAttack - kills modems and lowers user number
  *
  **/
void sysop::virusAttack() {
  int iUsersLeave;
  iUsersLeave = findNewUsers(20);
  iUsersFree -= iUsersLeave;
  if (iModems[0] > 0) {
    iModems[0] /= 2;
  }
  if (iModems[1] > 0) {
    iModems[1] /= 2;
  }
  if (iModems[2] > 0) {
    iModems[2] /= 2;
  }
  if (iModems[3] > 0) {
    iModems[3] /= 2;
  }
  if (iModems[4] > 0) {
    iModems[4] /= 2;
  }

  ColorControl->setColor(BRIGHT, FG, A_RED);
  cout << " ********** VIRUS ATTACK! **********" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_YELLOW); 
  cout << "A virus infects your system. It toggles a bit on "
       << "your I/O board, and" << endl
       << "sends high current pulses through your modems, "
       << "destroying some of them." << endl << endl;
  cout << "Hope you have enough cash to buy new ones!" << endl
       << endl;

  /* If the player has no modems left, then give the player
   * one to use
   */
  if ( (iModems[0] == 0) &&
       (iModems[1] == 0) &&
       (iModems[2] == 0) &&
       (iModems[3] == 0) &&
       (iModems[4] == 0)) { 
  cout << "Your close friend feels sorry that you lost your "
       << "modem and lets" << endl 
       << "you use his modem for a while." << endl << endl;
    iModems[0] = 1;
  }

  cout << "Some of your users hear about this and are "
       << "sympathetic, but you do lose" << endl
       << iUsersLeave << " users due to the incident." 
       << endl << endl;
  cout << "You vow never to forget to run your virus protection "
       << "again!" << endl << endl;
}

void sysop::processEvent() {
  int iRandom, iNewUsers, iUsersLeave, iCases;
 
  iCases = 58;
  
  iRandom = rand() % iCases;
  
switch(iRandom) {
    case 0:
      cout << "You try to rework the layout of your "
           << "menus and your users hate it.";
      break;

    case 1:
      if (iUsersFree > 0) {
        cout << "You scan the user log for fake "
             << "accounts and erase them.";
        iUsersFree --;
        break;
      } else {
        processEvent();
      }
      break;
    case 2:
      cout << "A device driver conflict sends your "
           << "software into a loop. You do some quick"
           << endl << "patching to get everything back "
           << "on-line before your users notice.";
      break;

    case 3:
      cout << "You decide to spend some time reading "
           << "through the message bases on your BBS"
           << endl
           << "and answering the messages that seem "
           << "interesting. This impresses the users and"
           << endl
           << "they start writing more messages and "
           << "telling more people about all of the new"
           << endl
           << "activity on your board. This helps make your "
           << "users happy and attract new users"
           << endl << endl;
  
      ColorControl->setColor(BRIGHT, FG, A_GREEN); 
      iNewUsers = findNewUsers(5); 
      iUsersFree += iNewUsers;
      cout << "You have " << iNewUsers << " new users join.";
      break;

    case 4:
      cout << "You decide to call some of the other boards in "
           << "the area and leave a few" << endl;
      cout << "messages telling people to call your BBS. This "
           << "makes the sysops of some of" << endl;
      cout << "those boards mad and they erase your messages "
           << "and leave bad ones in place" << endl;
      cout << "of them. Over all your messages get to a few "
           << "new users and they call you."  << endl << endl;

      ColorControl->setColor(BRIGHT, FG, A_GREEN); 
      iNewUsers = findNewUsers(5); 
      iUsersFree = iNewUsers;
      cout << "You have " << iNewUsers << " new users join.";
      break;

    case 5:
      cout << "Your cat jumps up on your keyboard while you are "
           << "away and it locks the BBS"  << endl
           << "until you get home. You toss a box of floppy "
           << "disks at the cat!";
      break;
      
    case 6:
      cout << "You start feeling like this is a real grind and "
           << "you wonder why you are" << endl
           << "spending all of your time and effort working on "
           << "keeping this computer" << endl
           << "running for a bunch of childish users. They want "
           << "everything from you and" << endl
           << "don't want to pay for it. It is, after all, your "
           << "computer and your life!" << endl
           << "What do these cheap skates want from you anyway?!  "
           << "Gripe, snarl, gripe." << endl << endl;
      ColorControl->setColor(BRIGHT, FG, A_RED);
      cout << "      **** SYSOP BURNOUT WARNING! ****" << endl << endl;

      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << "You are able to snap out of it before any real "
           << "damage is done... this time.";
      break;
  
    case 7:
      cout << "You help out a new user on your board with a few "
           << "shareware programs and he" << endl
           << "thanks you by uploading several new versions of "
           << "other programs along with" << endl
           << "a few pictures that you didn't have.";
      break;
 
    case 8:
      cout << "You clean the heads on your floppy drive after "
           << "finding out that your backup" << endl;
      cout << "won't load.";
      break;

    case 9: 
      cout << "You find several users in the log files that have "
           << "been hacking into your" << endl
           << "account files. You track them down and cut off all "
           << "of their access and force" << endl
           << "your users to change all their passwords." << endl
           << endl;

      cout << "This makes some users unhappy but they don't stop "
           << "calling your board.";
      break;

    case 10:
      cout << "A user complains about the look of your menus and "
           << "you change them a little." << endl
           << "The other users like the new look and start using "
           << "more time on-line.";
      iTraffic += (iUsersFree + iUsersPaying) / 2; 
      break;

    case 11:
      cout << "You try out a new on-line game and the users hate it.";
      break;

    case 12: 
      cout << "You make a small mistake on your opening screen "
           << "and your users laugh at you.";
      break;

    case 13: 
      cout << "You try to get a few of the system operators " 
           << "together for a meeting. They end" << endl
           << "up not being able to make it because of conflicting "
           << "schedules.";
      break;

    case 14:
      cout << "You spend another quiet night at home working on " 
           << "the BBS.";
      break;

    case 15:
      cout << "You check through the new uploads and recompress them.";
      break;

    case 16:
      cout << "You spend some time writing letters to the new users. "
           << "They like it and" << endl
           << "the volume of your mail goes up.";
           iMessages = (iUsersFree + iUsersPaying) / 2;
      break;

    case 17:
      cout << "You run a contest on your board to see who can "
           << "write the most messages. The" << endl
           << "problem is that you end up with more mail in "
           << "your mailbox!";
      iMessages = (iUsersFree + iUsersPaying);
      break;

    case 18:
      cout << "You help out a new user on your board with a few "
           << "shareware programs and he" << endl
           << "turns out to be the head of a very large "
           << "corporation. When his computer" << endl
           << "department upgrades the equipment in his office he "
           << "donates his old computer" << endl
           << "to your BBS." << endl << endl
           << "It couldn't have come at a better time because "
           << "your old system died when you" << endl
           << "tried to move it." << endl << endl;
      ColorControl->setColor(BRIGHT, FG, A_YELLOW);
      iComputer++;
      iStorage++;
      cout << "Your new system is a " << computerToString(iComputer)
           << " with " << storageToString(iStorage) << endl << endl;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      iNewUsers = (iUsersFree * iTraffic / 100);
      iUsersFree += iNewUsers; 
      cout << "Your users hear about this and the word spreads "
           << "attracting " << iNewUsers << " new users!";
      break; 

    case 19:
      cout << "A large slice of pizza gets stuck to your keyboard "
           << "by mistake. It takes you" << endl
           << "a little extra effort to get it cleaned up.";
      break;

    case 20:
      cout << "A freak lightning storm knocks out your "
           << "phone line." << endl
           << "Better get service soon!" << endl;
      ColorControl->setColor(BRIGHT, FG, A_RED);
      iLines--;
 
      if (iLines == 0) {
        cout << "* YOU HAVE NO PHONE LINES!  ALL USERS "
             << "HAVE LEFT YOUR SYSTEM!" << endl << endl;
        ColorControl->setColor(BRIGHT, FG, A_YELLOW);
        cout << "Temporary phone service has been restored "
             << "after a lot of work from you!";
        iUsersFree = 1;
        iUsersPaying = 0;
        iLines = 1;
      }

      break;
    case 21:
      cout << "You help a new user download a file on "
           << "your board and he reports you to the" << endl
           << "police for piracy. It turns out to be a mistake "
           << "in his understanding of the" << endl
           << "shareware ideals but the news frightens off a few "
           << "of your users." << endl;
      iUsersFree -= findNewUsers(-5);
      break;
    
    case 22:
      if ((iUsersFree + iUsersPaying) > 0) {
        iUsersLeave = findNewUsers(-10);
        iUsersFree -= iUsersLeave;
        cout << "A new board opens across town from you and offers "
             << "just about everything you" << endl
             << "have on your board. After a little checking it "
             << "turns out that the sysop got" << endl
             << "all of his files and menu ideas from your board." 
             << endl << endl;
        ColorControl->setColor(BRIGHT, FG, A_GREEN);
        cout << iUsersLeave << " users leave your system to join the "
             << "new system.";

      } else {
        processEvent();
      }
      break;

    case 23:
      cout << "You spend a few late nights working on some " 
           << "menu changes, again.";
      break;

    case 24:
      iActions += numberGame();
    break;
    
    case 25:
      cout << "You call some other boards and download a few new "
           << "files to list on-line.";
      break;

    case 26:
      cout << "You check the phone wires over for poor connections "
           << "and clean the contacts.";
      break;

    case 27:
      if (iModems[0] > 1) {
        cout << "You give one of your modems to a small private school "
             << "and their computer" << endl
             << "class joins your system." << endl << endl;
        iModems[0]--;
        iUsersFree += 50;
      } else {
        processEvent();
      }
      break;

    case 28:
      cout << "You spend some time working on the software "
           << "configuration, again.";

      break;
    case 29:
      cout << "Due to some investments in software "
           << "companies and your help in busting a" << endl
           << "ring of pirate software hackers, you get "
           << "a donation in the form of a hard" << endl
           << "drive upgrade for your BBS." << endl << endl;
      iNewUsers = findNewUsers(10);
      iUsersFree += iNewUsers;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << "Your users hear about this and the "
           << "word spreads attracting " << iNewUsers
           << " new users!"; 
      break;

    case 30:
      cout << "One mad user starts a rumor about you "
           << "and your BBS. It is totally false but" << endl
           << "some of the other users still believe "
           << "it. You do your best to disprove the" << endl
           << "lie but a few users still leave your "
           << "system." << endl << endl;
      iUsersLeave = (iUsersPaying) / 4;
      iUsersPaying -= iUsersLeave;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << iUsersLeave << " paying users stop calling.";
      break;
    
    case 31:
      cout << "It has been a long day at the keyboard. "
           << "You deserve a break." << endl << endl
           << "A good friend decides to help you out on "
           << "your system because you look tired.";
      break;

    case 32:
      cout << "You post a text file on your system that explains "
           << "how to use the functions" << endl
           << "and your users love it. They start using more of "
           << "their on-line time.";
      break;

    case 33:
      cout << "You help out a new user and he is happy. He starts "
           << "to advertise your BBS.";
      break;

    case 34:
     if (iUsersPaying > 0) {
      cout << "You try loading another on-line game to keep your "
           << "users happy. They find a" << endl
           << "backdoor in the program and start erasing files on "
           << "your drive." << endl << endl;
      iUsersLeave = iUsersPaying / 64;
      iUsersPaying -= iUsersLeave;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << "News of this gets out and " << iUsersLeave 
           << " paying users stop calling your system.";
     } else {
       processEvent();
     }
  
      break;
  
    case 35:
      cout << "You make another backup of your main BBS "
           << "directory. It takes forever.";
      break;

    case 36:
      cout << "In a freak act of kindness, your Aunt comes by "
           << "and turns off your computer and" << endl
           << "yells in your face for forgetting to turn off "
           << "your \"toys.\"" << endl << endl
           << "You rush to get your system back on-line." 
           << endl;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << "Only 1 user decided to leave your system.";
      iUsersFree --;
      break;

    case 37:
      cout << "You try to organize a meeting for your users "
           << "and they love the idea." << endl << endl;
      iNewUsers = findNewUsers(4); 
      iUsersFree += iNewUsers;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << iNewUsers << " new users join.";
      break;

    case 38:
      cout << "You call the author of your BBS software to "
           << "see if he will add a feature" << endl
           << "that you want. He thinks it is a great idea "
           << "and thanks you for calling.";
      break;

    case 39:
      cout << "You defragment your file directories and erase "
           << "some duplicate files."; 
      break;

    case 40:
      cout << "You are worn out from all the work and your "
           << "brain feels fried - extra crispy." << endl
           << "Several of your friends force you to take a day "
           << "off. It does a world of good" << endl
           << "and you attack the BBS with a fresh burst of "
           << "energy.";
      break;
  
    case 41:
      cout << "You spill a can of beer into your keyboard and "
           << "spend the rest of the night" << endl
           << "taking it apart, washing all the little parts "
           << "and blow drying them with a" << endl
           << "hairdryer." << endl << endl
           << "Strangely enough, it still works!";

      break;

    case 42:
      cout << "A user calls you very early one morning from "
           << "another country and wants" << endl
           << "to trade some of your files. You decide to "
           << "take a chance and send him" << endl
           << "a box of disks. Over a month later you get two "
           << "boxes of disks back with" << endl
           << "the most exciting and unusual software you have "
           << "ever seen." << endl << endl
           << "You put it on-line and your users love it!"
           << endl << endl;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      iNewUsers = findNewUsers(20);
      iUsersFree += iNewUsers;
      cout << iNewUsers << " new users join your system just "
           << "to get the new files."; 
      break;

      case 43:
        cout << "A new update to your software is now "
             << "available. It fixes quite a few bugs" << endl
             << "and offers some new features." << endl << endl;
        ColorControl->setColor(BRIGHT, FG, A_GREEN);
        cout << "The problem is that you don't have enough "
             << "money to buy it.";
        break;

    case 44:
      cout << "You clean the heads on your floppy drive "
           << "after finding out that your backup" << endl
           << "won't load.";
      break;

    case 45:
      cout << "Another late night working on the computer. Lots "
           << "of work and not much to show" << endl
           << "for it. The system seems to be running a lot "
           << "better though.";
      break;

    case 46:
      cout << "You work out a file trade with an old friend. Your "
           << "users like the new stuff." << endl;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      iNewUsers = findNewUsers(1);
      iUsersFree += iNewUsers;
      cout << iNewUsers << " new users join.";
      break;
 
    case 47:
      cout << "You re-compress one of the file directories and "
           << "erase the corrupt files."; 
      break;
    case 48:
      cout << "You spend several hours packing up a file trade "
           << "for another sysop.";
      break;
    case 49:
      cout << "Due to some investments in software companies "
           << "and your help in busting a" << endl
           << "ring of pirate software hackers, you get a donation "
           << "in the form of a hard" << endl
           << "drive upgrade for your BBS." << endl << endl;
      iStorage++;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      iNewUsers = findNewUsers(1);
      iUsersFree += iNewUsers;
      cout << "Your users hear about this and the word spreads "
           << "attracting " << iNewUsers << " new users!";
      break;
     
    case 50:
      ColorControl->setColor(BRIGHT, FG, A_RED);
      cout << "    *** K";
      ColorControl->setColor(BRIGHT, FG, A_WHITE);
      cout << '&';
      ColorControl->setColor(BRIGHT, FG, A_RED);
      cout << "yboard Failur";
      ColorControl->setColor(BRIGHT, FG, A_WHITE);
      cout << '&';
      ColorControl->setColor(BRIGHT, FG, A_RED);
      cout << " ***" << endl << endl;
    
      ColorControl->setColor(BRIGHT, FG, A_CYAN);
      cout << "You damag&d s&v&ral k&ys on your k&yboard by "
           << "pounding on it too hard." << endl
           << "You try to answ&r a f&w m&ssag&s and your us&rs "
           << "think that it is r&ally" << endl
           << "bad lin& nois&. It costs you $20 to fix.";
      iMoney -= 20;
      break;

    case 51:
      cout << "You discover a Trojan horse program in your file "
           << "list and quickly erase it" << endl
           << "before any of your users download it.";
      break;

    case 52:
      cout << "You open a new message section for a special "
           << "interest group on your system" << endl
           << "and they are so impressed that they send you "
           << "a donation." << endl << endl;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      iMoney += iMoney;
      cout << "Your balance is now $" << iMoney 
           << ".00"; 
      break;
 
    case 53:
      cout << "A new update to your software is now available. It "
           << "fixes quite a few bugs" << endl
           << "and offers some new features." << endl << endl;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      iMoney -= 50;
      cout << "You decide to buy it and your users are very happy "
           << "you did." << endl
           << "The upgrade only cost $50.00";
      break;

    case 54:
      cout << "An old school buddy calls you on the phone after "
           << "several years of living in" << endl
           << "another state. He asks if you would mind putting "
           << "his new software on your" << endl
           << "system. You agree and it turns out to be the most "
           << "popular software on the" << endl
           << "market!" << endl << endl;
      iNewUsers = findNewUsers(25);
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << "When news of this gets out " << iNewUsers 
           << " new users join your BBS.";
      break;

    case 55:
      cout << "You spend some time working on the software "
           << "configuration, again.";
      break;

    case 56:
      cout << "You move a few of your files around in the "
           << "directories.";
      break;

    case 57:
      cout << "You call the author of your BBS software to see "
           << "if he will add a feature" << endl
           << "that you want. He thinks it is a great idea and "
           << "thanks you for calling.";
      break;

    case 58:
      cout << "You spend some time working with the new users on "
           << "your system and they turn" << endl
           << "around and help the other users. The word gets "
           << "out that your BBS is very" << endl
           << "friendly." << endl << endl;
        iNewUsers = findNewUsers(15);
        iUsersFree += iNewUsers;
      ColorControl->setColor(BRIGHT, FG, A_GREEN);
      cout << iNewUsers << " new users join";
      break;
    default:
      cout << "Error: no such Work case" << endl;
  }
  cout << endl << endl;
}

int sysop::findNewUsers(int iPercent) {
  int iUsers;

  iUsers = ((iUsersFree + iUsersPaying) * iPercent / 100) + 1;
    return (iUsers);
}

void sysop::prompt() {
  ColorControl->setColor(BRIGHT, FG, A_WHITE);
  cout << "[" << iUsersFree << ":" << iUsersPaying 
       << ":" << iActions << "]: ";
}

void sysop::answerMail() {

  if (iMessages == 1) {
    cout << "There is 1 message waiting." << endl;
  } else if (iMessages > 1) {
    cout << "There are " << iMessages << " messages waiting."
         << endl;
  }

  if (iMessages > 0) {
    cout << "Would you like to answer mail now? (Y/n)" << endl;
    cout << "                0%----------------50%----------------100%"
         << endl;
    cout << "Answering Mail: _________________________________________"
         << endl << endl;
//  cout << "Your co-sysop answers some mail." << endl;
    ColorControl->setColor(BRIGHT, FG, A_GREEN);
    cout << "You answer all of the messages." << endl << endl;
    cout << "Poll network for inbound netmail? (y/N)"
         << endl;
    cout << "It takes a little extra effort to scan "
         << "since you are not a network member."
         << endl << endl;
    cout << "Network Mail poll... No netmail found "
         << "on any network." << endl;

/*
Problem Message Number: 1
This woman is upset about your questionnaire asking her age. She makes
it very clear that you don't need to know her age or her weight either!

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite

This user is very upset about your validation process.
He doesn't want to wait for you to upgrade his account.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite

You do your best to be nice and not cut the user down.

Problem Message Number: 8

A very angry user calls you, and your mother, every curse word
that you have ever heard and some that you haven't.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite
Problem Message Number: 7

This is a list of program titles that the user wants you to put on
floppy disk and mail to him. He thinks that you owe it to him because
he only has a 1200 baud modem and can't download them.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite

4 free users subscribe to your system.

Problem Message Number: 6

This is from a user who wants you to make a list of all of the black
users on your system. He questions the color of your skin because you
have not already made such a list.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite
Problem Message Number: 5

This user complains about your "dumb" looking menus
and wants you to use more graphics.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Rude

This paying user has left your system.

Problem Message Number: 4

This message is very rude and calls your BBS "worthless junk."

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Ignore

Stop answering mail? (y/N) No

Problem Message Number: 3

This user wants to know how old you are and if your mother is paying
for the BBS or if you are.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Lock-out

This paying user is no longer a member of your board.

Problem Message Number: 2

This user explains that he works for the local vice squad and wants you
to help him get into another BBS in town so he can find their X-rated
pictures.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite

5 free users subscribe to your system.

This is a new user who wants to know how old you are and if you go to
the same elementary school that he does.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite

You do your best to be nice and not cut the user down.
Problem Message Number: 1

This user is very upset about your validation process.
He doesn't want to wait for you to upgrade his account.

<D>elete, <I>gnore, <P>olite answer, <R>ude answer, <L>ock-out user: Polite

You do your best to be nice and not cut the user down.
Poll network for inbound netmail? (y/N) Yes
It takes a little extra effort to scan since you are not a network member.

Network Mail poll... No netmail found on any network.


*/
  }
}

void sysop::usersOnline() {
  int iAvailableLines;
  cout << "sage" << endl << endl;
  
  if (iModems[0] > 0) {
    if (iModems[0] > iLines) {
      iAvailableLines = iLines;
    } else {
      iAvailableLines = iModems[0];
    }
    
    ColorControl->setColor(BRIGHT, FG, A_CYAN);
    cout << "2400 baud users have access to "
         << iAvailableLines;
    if (iAvailableLines == 1) {
      cout << " line";
    } else {
      cout << "lines";
    }
    
    cout << " which is ";
    ColorControl->setColor(BRIGHT, FG, A_WHITE);
    cout << findModemTraffic();
    ColorControl->setColor(BRIGHT, FG, A_CYAN);
    cout << "% full" << endl;
  }

  if (iModems[1] > 0) {
    if (iModems[1] > iLines) {
      iAvailableLines = iLines;
    } else {
      iAvailableLines = iModems[1];
    }

    ColorControl->setColor(BRIGHT, FG, A_CYAN);
    cout << "Fast modem users have access to "
         << iAvailableLines;
    if (iAvailableLines == 1) {
      cout << " line";
    } else {
      cout << "lines";
    }
    
    cout << " which is ";
    ColorControl->setColor(BRIGHT, FG, A_WHITE);
    cout << findFastModemTraffic();
    ColorControl->setColor(BRIGHT, FG, A_CYAN);
    cout << "% full" << endl;

    if (findModemTraffic() > 80) {
      cout << "   * PLEASE GET MORE MODEMS VERY SOON! *" << endl;
    }
    if (findFastModemTraffic() > 80) {
      cout << "   * PLEASE GET MORE HIGH SPEED MODEMS VERY SOON! *" 
           << endl; 
    }
  }
  cout << endl;
}

int sysop::numberGame() {
  int iFirstNumber, iSecondNumber, iGamble; 
  char cChoice;

  iGamble = 0;
  cout << "It has been a long day at the keyboard. You "
       << "deserve a break." << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "Would you like to try your hand at a friendly "
       << "game? (y/N)";

  cChoice = getCharacter();
  if ((cChoice == 'y') || (cChoice == 'Y')) {  

    cout << endl << endl;
    ColorControl->setColor(BRIGHT, FG, A_YELLOW);
    cout << "Here are the rules. I'll think of two different "
         << "numbers and show one to you." << endl
         << "You then tell me if the second number is more "
         << "or less than the first. If you" << endl
         << "are right then I'll give you more actions, but if "
         << "you are wrong I'll take" << endl
         << "some away. The numbers can be anywhere "
         << "from 0 to 100." << endl << endl;
    ColorControl->setColor(BRIGHT, FG, A_CYAN);

    cout << "Do you still want to play? (y/N)";
      cChoice = getCharacter();
      if ((cChoice == 'y') || (cChoice == 'Y')) {  
        cout << endl << endl;
        
          iFirstNumber = 50;
        //iFirstNumber = (rand() % 100) + 1;  //uncomment this line 
                                              //if you want the first 
                                              //number to be random
          iGamble = 10 + (rand() % 20);
          ColorControl->setColor(BRIGHT, FG, A_WHITE);
          cout << "We will play for " << iGamble 
               << " actions." << endl << endl;
          ColorControl->setColor(BRIGHT, FG, A_CYAN);
          cout << "The first number is " << iFirstNumber
               << " and I am thinking of a second." << endl << endl;
          ColorControl->setColor(BRIGHT, FG, A_GREEN);
          cout << "Is the second number (H)igher or (L)ower?";
          cChoice = getCharacter();
          if ((cChoice == 'H') || (cChoice == 'h')) {
            if (iSecondNumber > iFirstNumber) {
              cout << "Yes it is!  The actions have been added "
                   << "to your account.";
            } else {
              cout << "No it isn't!  The actions have been removed "
                   << "from your account.";
              iGamble = -iGamble; //return a negative amount
            }
          } else {
            if (iSecondNumber < iFirstNumber) {
              cout << "Yes it is!  The actions have been added "
                   << "to your account.";
            } else {
              cout << "No it isn't!  The actions have been removed "
                   << "from your account.";
              iGamble = -iGamble; //return a negative number
            }
          }
        }
    }
  return iGamble;
}

void sysop::virusScan() {
  int iTimeToScan;

  iTimeToScan = iStorage * 10;
  cout << "irus detection" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "Murphyscan(TM) scans all drives and files "
       << "for all KNOWN viruses!" << endl;
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "You have a " << storageToString(iStorage)
       << " drive  EST. SCAN TIME " << iTimeToScan
       << " SECONDS" << endl << endl;

  /*
  ansicolor.setColor(BRIGHT, FG, A_WHITE);
   TurboScan(TM) super fast virus detection option is available but may not
identify all infections accurately. Time savings MAY be 50% or more.
  ansicolor.setColor(BRIGHT, FG, A_CYAN);
Would you like to use the TurboScan option? (y/N) Yes

         [DDDDDDDDD] <<TurboScan!>>
Progress: [[[[[[[[[
  */
  cout << "         [DDDDDDDDDDD] <FullScan>" << endl;
  cout << "Progress: [[[[[[[[[[[" << endl << endl;

  cout << " >>> No virus found <<<" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "                      We at Murphycorp recommend you run"
       << endl
       << "                   Murphyscan(TM) every "
       << TURNS_FOR_VIRUS << " downloads."
       << endl
       << "                      Thank you for using Murphyscan(TM)!"
       << endl << endl;
  ColorControl->setColor(DARK, FG, A_GREEN); 
  cout << "*not responsible for damage resulting from misuse."
       << endl << endl;

}

void sysop::outOfTurns() {
  ColorControl->setColor(BRIGHT, FG, A_BLACK);
  cout << "  You are out of actions. This will "
       << "limit what you can do in the game." << endl
       << " For a list of available options press "
       << "the Question mark (?) key." << endl;
  ColorControl->setColor(BRIGHT, FG, A_WHITE);
  cout << ">> You are out of actions! <<" << endl;

}

string sysop::computerToString(int iComputer) {
  string strToReturn;
  switch(iComputer) {
    case 0:
      strToReturn = "XT";
      break;
    case 1:
      strToReturn = "286";
      break;
    case 2:
      strToReturn = "386";
      break;
    case 3:
      strToReturn = "486/25";
      break;
    case 4:
      strToReturn = "486/50 v4";
      break;
    case 5:
      strToReturn = "486/50 v9";
      break;
    case 6:
      strToReturn = "586/99";
      break;
    default:
      strToReturn = "Generic Computer";

  }
  return strToReturn;
}

string sysop::modemToString(int iModem) {
  string strToReturn;
  switch(iModem) {
    case 0:
      strToReturn = "2400bps";
      break;
    case 1:
      strToReturn = "9600bps";
      break;
    case 2:
      strToReturn = "14.4kbps";
      break;
    case 3:
      strToReturn = "19.2kbps";
      break;
    case 4:
      strToReturn = "28.8kbps";
      break;
    case 5:
      strToReturn = "56kbps";
      break;
    default:
      strToReturn = "Generic Modem";
  }
  return strToReturn;

}

string sysop::membershipToString(int iPlan) {
  string strToReturn;
  switch(iPlan) {
    case 0:
      strToReturn = "None";
      break;
    case 1:
      strToReturn = "Lifetime";
      break;
    case 2:
      strToReturn = "Yearly";
      break;
    case 3:
      strToReturn = "Monthly";
      break;
    case 4:
      strToReturn = "Weekly";
      break;
    case 5:
      strToReturn = "Hourly";
      break;
    case 6:
      strToReturn = "Download";
      break;
    default:
      strToReturn = "None";
  }

  return strToReturn;
}

string sysop::storageToString(int iStorage) {
  string strToReturn;
 
  switch(iStorage) {
    case 0:
      strToReturn = "30meg 60ms";
      break;
    case 1:
      strToReturn = "100meg 29ms";
      break;
    case 2:
      strToReturn = "300meg 16ms";
      break;
    case 3:
      strToReturn = "600meg 15ms";
      break;
    case 4:
      strToReturn = "1.2Gig 11ms";
      break;
    case 5:
      strToReturn = "6 Gig 3ms";
      break;
    case 6:
      strToReturn = "Raid";
      break;
    default:
      strToReturn = "Generic Hard Drive";
  }
  return strToReturn;
}

string sysop::getName() {
  return strName;
}

string sysop::getSystemName() {
  return strSystemName;
}

int sysop::getUsers() {
  return (iUsersFree + iUsersPaying);
}

int sysop::getLines() {
  return iLines;
}

int sysop::getActions() {
  return iActions;
}

int sysop::getMessages() {
  return iMessages;
}

int sysop::getScore() {
  int iTempScore;
  
  iTempScore = 0;
  iTempScore += (iModems[0] * 1);
  iTempScore += (iModems[1] * 2);
  iTempScore += (iModems[2] * 3);
  iTempScore += (iModems[3] * 4);
  iTempScore += (iModems[4] * 5);
  iTempScore += (iComputer * 10);
  iTempScore += (iStorage * 8);
  iTempScore += (iSoftware * 10);
  iTempScore += (iLines * 20);
  iTempScore += (iUsersFree * 1);
  iTempScore += (iUsersPaying * 2);

return iTempScore;
}

string sysop::fileString() {
  string BR, strToReturn;
  BR = "|";
  ostrstream oss;
  oss << iActions << ends;
/*
      << BR << strSystemName << BR
      << strLastOnDate << BR << strCreationDate << BR
      << ends; 
*/
  //strToReturn = oss.str; 
  return "default"; 

/*
          + string(iScore) + BR + iUsersFree + BR + 
          iUsersPaying + BR + iActions + BR + iMorale + BR +
          iComputer + BR + iStorage + BR + iModems[0] + BR +
          iModems[1] + BR + iModems[2] + BR + iModems[3] + BR +
          iModems[4] + BR + iSoftware + BR + iLines + BR +
          iEducationPublic + BR + iEducationPersonal + BR +
          iCallerID + BR + iSecurity);
*/
}

int sysop::promptLength() {
  int iLength;
  string strTemp;
  ostrstream oss;

  iLength = 9;

  oss << iUsersFree << ends;
  strTemp = oss.str();
  iLength += strTemp.length();

  oss << iUsersPaying << ends;
  strTemp = oss.str();
  iLength += strTemp.length();

  oss << iActions << ends;
  strTemp = oss.str();
  iLength += strTemp.length();
  
  return iLength;
}

int sysop::findModemTraffic() {
  int iAvailableLines;
  if (iLines < iModems[0]) {
    iAvailableLines = iLines;
  } else {
    iAvailableLines = iModems[0];
  }
  
  return (100 * (iUsersFree + iUsersPaying) / 
          (iAvailableLines * USERS_PER_2400));
}

int sysop::findFastModemTraffic() {
  return 0;
}

int sysop::getCallerID() {
  return iCallerID;
}

string sysop::scoreString() {
  string strToReturn;

  ostrstream oss;
  oss << getScore() << ends;
  strToReturn = oss.str();
 
  return strToReturn; 
}

string sysop::zipList() {
  ostrstream oss;
  oss << 42 << ends;
  return "default"; 

}

void sysop::addToScoreList() {
  string SCORE_FILE, strLine;
  int iDots, i, iCounter;

  iCounter = 1;

  SCORE_FILE = "scores.ans";
  ifstream fileInStream(SCORE_FILE.c_str());
  ofstream fileOutStream(SCORE_FILE.c_str());

  if (fileOutStream) {
    if (fileInStream) {
      while(getline(fileInStream, strLine)) {
      fileOutStream << strLine << endl; 
    }

    fileOutStream << iCounter << "-";
    iCounter++;

    fileOutStream << strSystemName;

    iDots = 45 - 8;
    for (i = 0; i < iDots; i++) {
      fileOutStream << '.';
    }

    fileOutStream << " Score: ";
    fileOutStream << getScore() << endl;
    fileOutStream << "Sysop: " << strName << " with "
                  << iLines << " lines, " 
                  << (iUsersFree + iUsersPaying) 
                  << " users.\n" << endl;

    } else {
      cout << SCORE_FILE << " is missing" << endl;
    } 
  }
}

void sysop::cheat() {
  iUsersFree += 1024;
  iUsersPaying += 1024;
  iMoney += 1024; 
}

int sysop::getMoney() {
  return iMoney;
}
