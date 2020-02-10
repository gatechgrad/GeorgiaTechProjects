#include "virtualsysop.h"


const int SCREEN_LINES = 24;

virtualsysop::virtualsysop(string strNewName) {
  ColorControl->setColor(DARK, BKG, A_BLACK);
  ColorControl->clearScreen();
  ColorControl->cursorHome();
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << endl
       << " Virtual Sysop: The Virtual System Operator Simulation"
       << endl;
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "_____________________________________________________________" 
       << endl;
  newGame(strNewName);

  ColorControl->resetColor();
  delete ColorControl;
}

void virtualsysop::load(string strNewName) {
  char chInput;
  string str;
  string lName, lBBS, lDateCreate, lDateLastOn; 
  int lScore, lFree, lPaying, lActions, lMorale, lComputer,
      lStorage, l0Modem, l1Modem, l2Modem, l3Modem,
      l4Modem, lSoftware, lLines, lEdu1, lEdu2, 
      lCID, lSec;

  ifstream in("players.dat");
  if (!in) {
    cout << "ERROR: players.dat is missing" << endl;
  }
  
  while(in) {
    getline(in, str);

    istrstream myStringStream(str.c_str(), '|');
    myStringStream >> lName;
    myStringStream >> lBBS;
    myStringStream >> lDateCreate;
    myStringStream >> lDateLastOn;
    myStringStream >> lScore;
    myStringStream >> lFree; 
    myStringStream >> lPaying; 
    myStringStream >> lActions; 
    myStringStream >> lMorale; 
    myStringStream >> lComputer;
    myStringStream >> lStorage; 
    myStringStream >> l0Modem; 
    myStringStream >> l1Modem; 
    myStringStream >> l2Modem; 
    myStringStream >> l3Modem;
    myStringStream >> l4Modem; 
    myStringStream >> lSoftware; 
    myStringStream >> lLines; 
    myStringStream >> lEdu1; 
    myStringStream >> lEdu2; 
    myStringStream >> lCID; 
    myStringStream >> lSec;

    while(in) {
      str = getCharacter();
      cout << str << endl;
/*
      sysop *aSysop = new sysop(ColorControl, lName, lBBS, lDateCreate, 
                         lDateLastOn, lScore,
                         lFree, lPaying, lActions, lMorale, lComputer,
                         lStorage, l0Modem, l1Modem, l2Modem, l3Modem,
                         l4Modem, lSoftware, lLines, lEdu1, lEdu2, 
                         lCID, lSec);
    //PlayerList.push_back(*aSysop);
*/
    }
  
    in.close(); 

    //CurrentPlayer  = aSysop;

    cout << "Use ANSI color??" << endl;
    cin >> chInput;

    if ((chInput == 'y') || (chInput == 'Y')) {
      ColorControl = new ansicolor(true); 
    } else {
      ColorControl = new ansicolor(false);
    }


    mainMenu();

  }
}

void virtualsysop::newGame(string strNewName) {
  string strName, strBBSName;

  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "You have just spent a long time and a good bit of money "
       << "to get your computer" << endl
       << "and software ready to go on-line. Someone gave you an "
       << "old IBM XT that they were" << endl
       << "going to toss in the trash. You got a shareware BBS "
       << "program and a cheap modem" << endl
       << "and now you are ready to start accepting calls." << endl
       << endl;
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "The local system operators' group welcomes you as a "
       << "member of their community!" << endl
       << "Most of them wish you the best of luck with your BBS "
       << "and users." << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_WHITE);
  cout << "The local directory of Bulletin Board Systems needs "
       << "some information from you." << endl
       << "In order to keep our records straight we need to know "
       << "the name that you call" << endl
       << "your BBS in this game. The list has space for up to "
       << "30 characters." << endl << endl;
  do {
    ColorControl->setColor(BRIGHT, FG, A_CYAN);
    cout << "What is your BBS called?";
    ColorControl->setColor(DARK, FG, A_CYAN);
    cout << "[..............................]";
    //ColorControl->setColor(DARK, BKG, A_BLACK);
    ColorControl->setColor(BRIGHT, FG, A_CYAN);
    ColorControl->cursorLeft(31);

    getline(cin, strBBSName);
    if (strBBSName.length() > 30) {
      cout << endl << "That name is too long" << endl;
    }

  } while (strBBSName.length() > 30);
  cout << endl;


  strName = strNewName;

  string date1 = "0/0/00";
  string date2 = "0/1/00";

  CurrentPlayer = new sysop(ColorControl, strName, strBBSName, date1, date2);
  mainMenu();
}

void virtualsysop::writeFile() {
  ofstream out("player.dat");

  cout << "Coredump 2" << endl;

  if (!out) {
    cout << "Cannot open file player.dat" << endl;
  }
 
  out << CurrentPlayer->fileString() << endl;
  out.close();

  CurrentPlayer->addToScoreList();

  ofstream out3("ziplist.ans");
  if (!out3) {
    cout << "Cannot open file ziplist.ans" << endl;
  }
  
  out3 << CurrentPlayer->zipList() << endl;
  out3.close();

  cout << "Coredump 1" << endl;

}

void virtualsysop::mainMenu() {
  bool keepLooping;
  char chInput;
  
	keepLooping = true;
  /*
  ansicolor->setColor(BRIGHT, FG, A_MAGENTA):
  cout << "* New phone lines have been connected." 
       << endl; 
  */
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "Checking all lines for status - OK." << endl;
  cout << "System hardware diagnostics   - OK." << endl;
  if (CurrentPlayer->getCallerID() > 0) {
    cout << "Caller validation diagnostics - OK." << endl;
  }
  if (CurrentPlayer->getMessages() > 0) {
    cout << "You have " << CurrentPlayer->getMessages()
         << " messages waiting." << endl;
  }
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "Press \"?\" for help." << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "OpMode." << endl << endl;

  ColorControl->setColor(DARK, BKG, A_BLACK);

	while(keepLooping) {
    CurrentPlayer->prompt();

    try {
      chInput = getCharacter();
      if (CurrentPlayer->getActions() == 0) {
        switch(chInput) {
          case 'w':
          case 'W':
          case 's':
          case 'S':
          case 'c':
          case 'C':
            throw chInput;
            break;
          default:
            //the user can do all other
            //actions with no turns 
          cout << "";
        }
      }
/*
    printf("%c[2;J", 27);
    printf("%c[30;10H", 27);
    printf("%c[6n", 27);
*/
    ColorControl->setColor(BRIGHT, FG, A_BLACK);
    ColorControl->cursorUp(1);
    ColorControl->cursorRight(CurrentPlayer->promptLength());

    switch (chInput) {
      case '$':
        CurrentPlayer->cheat();
        break;
      case '!':
        printInstructions();
        break;
      case '?':
        helpMenu();
        break;
      case 'a':
      case 'A':
        CurrentPlayer->answerChat();
        break;
      case 'b':
      case 'B':
        CurrentPlayer->balance();
        break;
      case 'c':
      case 'C':
        CurrentPlayer->chargeUsers();
        break;
      case 'e':
      case 'E':
        CurrentPlayer->employWorkers();
        break;
      case 'H':
      case 'h':
        keepLooping = false;
        writeFile();
        hangUp();
        break;
      case 'i':
      case 'I':
        CurrentPlayer->inspect();
        break;
      case 'l':
      case 'L':
        topTen();
        break;
      case 'm':
      case 'M':
        CurrentPlayer->answerMail();
        break;
      case 'r':
      case 'R':
        CurrentPlayer->report();
        break;
      case 's':
      case 'S':
        CurrentPlayer->goToStore();
        break;
      case 't':
      case 'T':
        CurrentPlayer->titleOfBBS();
        break;
      case 'u':
      case 'U':
        CurrentPlayer->usersOnline();
        break;
      case 'v':
      case 'V':
        CurrentPlayer->virusScan();
        break;
      case 'w':
      case 'W':
        CurrentPlayer->work();
        break;
      case 'z':
      case 'Z':
        break;
      default: 
        invalidCommand();

    }
   }  catch (char ch) {
     ColorControl->setColor(BRIGHT, FG, A_WHITE);
     cout << ">> You are out of actions! <<" << endl;
   }
  }
}

void virtualsysop::helpMenu() {
  ColorControl->setColor(BRIGHT, FG, A_BLACK);
  cout << " - Help" << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(!) Instructions...... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "What do I do here?" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(A)nswer chat......... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Keep in contact with the users" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(B)ank................ ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Check status of your account" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(C)harge users........ ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Set charge type and collect money" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(E)mploy workers...... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Hire workers to help run your BBS" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(H)ang-up lines....... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Polite way to leave the game" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(I)nspect Other BBS... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Check up on your competition" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(L)ist all boards..... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Full BBS list of all players with scores" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(M)ail Box check...... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Message base maintenance and status" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(R)eport.............. ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Check hardware, software, modems and users" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(S)tore............... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Buy or upgrade hardware, software or phones" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(T)itle of BBS........ ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "What do you call your bulletin board?" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(U)sers now on-line... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "How many users are on your BBS now?" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(V)irus detection..... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Keep your files clean and clear!" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(W)ork................ ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "DO THIS! Keep your BBS active!" << endl;
  ColorControl->setColor(BRIGHT, FG, A_GREEN);
	cout << "(Z)ippy list.......... ";
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "Show a quick list of your competition\n" << endl;
}

void virtualsysop::topTen() {
  string SCORE_FILE, strLine;

  SCORE_FILE = "scores.ans";

  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "                   Top 10 Systems and their Operators" 
       << endl;
  cout << " ----------------------------------------------------------------------" << endl;


  ifstream fileStream(SCORE_FILE.c_str());

  cout << "Coredump 4" << endl;


  if (fileStream) {
    while (getline(fileStream, strLine)) {
      ColorControl->setColor(BRIGHT, FG, A_YELLOW);
      cout << strLine << endl;
      getline(fileStream, strLine);
      cout << strLine << endl;
    }
  }  
  cout << "Coredump 3" << endl;


/*
 2-Firehawk Online.............................. Score: 3,800
   Sysop: PHILLIP FLORES with 41 lines, 3897 users.
 3-INDEX........................................ Score: 34
   Sysop: LEVI D. SMITH with 1 line, 104 users.
 4-OffLine...................................... Score: 12
   Sysop: NICKI GRAHAM with 1 line, 83 users.
*/
 
 
}

void virtualsysop::invalidCommand(){
  ColorControl->setColor(BRIGHT, FG, A_BLACK);
  cout <<  "\nHuh?" << endl;
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "If you need help, try pressing the Question mark (?) key.\n" 
       << endl;
}

void virtualsysop::hangUp() {
  ColorControl->setColor(BRIGHT, FG, A_BLACK);
  cout << "angup" << endl << endl;

  ColorControl->setColor(BRIGHT, FG, A_GREEN);
  cout << "Disconnect from your service? (y/N) Yes" << endl
       << endl;
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "Your line has been released to allow " 
       << "other users to access your service."
       << endl << endl;
  ColorControl->setColor(BRIGHT, FG, A_YELLOW);
  cout << "You have " << CurrentPlayer->getActions()
       << " actions left today." << endl;
  ColorControl->setColor(BRIGHT, FG, A_CYAN);
  cout << "Thank you for playing." << endl << endl;

  topTen();
  cout << "Coredump 4" << endl;

}

void virtualsysop::printInstructions() {
  string str;
  int iScreenLine;

  ifstream in("instructions.ans");
  if (!in) {
    cout << "ERROR: instructions.ans is missing" << endl;
  }
  
  cout << endl << endl;
  iScreenLine = 2; //Two blank lines to start with
  ColorControl->setColor(BRIGHT, FG, A_WHITE);
  while(in) {
    iScreenLine++;
    getline(in, str);
    cout << str << endl;
    if (iScreenLine == SCREEN_LINE_MAX) {
      iScreenLine = 0;
      cout << "Press Any Key to Continute" << endl;
      getCharacter(); 
    }
  }
}

