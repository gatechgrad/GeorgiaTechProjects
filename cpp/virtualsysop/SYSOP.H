#include "store.h"

#ifndef SYSOP_H
#define SYSOP_H

#include "global.h"

//initalization constants
const int TURNS_A_DAY = 150;
const int INIT_COMPUTER = 0;
const int INIT_STORAGE = 0;
const int INIT_MODEM = 1;
const int INIT_SOFTWARE = 0;
const int INIT_USERP = 0;
const int INIT_USERF = 9;
const int INIT_LINES = 1;
const int INIT_EXP = 0;
const int INIT_EDU_PUB = 0;
const int INIT_EDU_PSNL = 0;
const bool INIT_CALLID = false;
const bool INIT_SECURITY = false;
const int INIT_SCORE = 0;
const int INIT_MONEY = 200;
const int INIT_MESSAGES = 0;
const int INIT_AD = 10;
const int MODEM_TYPES = 5;
const int TURNS_FOR_VIRUS = 10;
const int USERS_PER_2400 = 175;
const int DIFFICULTY = 3;
const int USERS_FOR_CHARGE = 50;
const int LINES_TO_EMPLOY = 100;
const unsigned int MAX_SYS_NAME_LEN = 30;

class sysop {
  public:
  sysop(ansicolor *aColorControl,
             string aName, string aBBS, string aDateC, string aDateL,
             int aScore = INIT_SCORE, int aFree = INIT_USERF,
             int aPaying = INIT_USERP, int aActions = TURNS_A_DAY,
             int aAd = INIT_AD, int aComputer = INIT_COMPUTER,
             int aStorage = INIT_STORAGE, int a0Modems = INIT_MODEM,
             int a1Modems = 0, int a2Modems = 0, int a3Modems = 0,
             int a4Modems = 0, int aSoftware = INIT_SOFTWARE,
             int aLines = INIT_LINES, int aEdu1 = INIT_EDU_PUB,
             int aEdu2 = INIT_EDU_PSNL, int aCID = INIT_CALLID,
             int aSec = INIT_SECURITY, int aMoney = INIT_MONEY);
    int getScore();
    void answerChat();
    void balance();
    void chargeUsers();
    void employWorkers();
    void report();
    void titleOfBBS();
    void virusDetection();
    void work();
    void prompt();
    void answerMail();
    void usersOnline();
    string modemToString(int iModem);
    string computerToString(int iComputer);
    string storageToString(int iStorage);
    int iChatRequest;
    string getName();
    string getSystemName();
    int getLines();
    int getUsers();
    int getActions();
    void usage();
    void inspect();
    int promptLength();
    void processEvent();
    int getMessages();
    string fileString();
    void goToStore();  
    void storeChoice(char cSelection, string strStoreName, 
                     string strStoreType);
    void virusScan();
    void cheat();
    int getCallerID();
    string scoreString();
    string zipList();
    void addToScoreList();
    int getMoney();
  private:
    int iComputer, iStorage,
        iSoftware, iUsersPaying,
        iUsersFree, iUserPayment,
        iLines, iExperience,
        iEducationPublic, 
        iEducationPersonal,
        iMoney, iActions, iScore,
        iMessages, iMembershipPlan,
        iAdvertisement,
        iCallerID, iSecurity,
        iTurnsSinceVirusDetection,
        iTraffic;
    int iModems[MODEM_TYPES];
    string strName, strSystemName,
           strCreationDate, strLastOnDate;
    ansicolor *ColorControl;
    void virusAttack();
    void outOfTurns();
    int findNewUsers(int iPercent);
    int findModemTraffic();
    int findFastModemTraffic();
//    store *aStore;
    string membershipToString(int iPlan);
    int numberGame();
};

#endif SYSOP_H
