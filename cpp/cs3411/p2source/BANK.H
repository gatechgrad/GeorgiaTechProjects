#include "Global.h"
#include "Customer.h"

#define SUCCESS 0 
#define FAILURE 1

/*
 * all commands are defined here
 * (just in case a command name
 * needs to be changed for some 
 * reason)
 */
const string OPEN = "open";
const string CLOSE = "close";
const string QUIT = "quit";
const string WITHDRAW = "withdraw";
const string DEPOSIT = "deposit";
const string TRANSFER = "transfer";
const string HELP = "help";
const string ADVANCE = "advance";
const string BALANCE = "balance";

//command line constants
const string PROMPT = "cmd> ";
const string OUTPUT_LEADER = ">> ";

//types of accounts
/*
static const string CHECKING = "checking";
static const string SAVING = "saving";
static const string MONEYMARKET = "moneymarket";
*/

class Bank {

public:
  Bank(string newName);
	void menu(void);
  void helpMessage();
  void closeAccount();
  void deposit(string strAccount, float Amount);
  void transfer(string strFrom, string strTo, float fAmount);
  void withdraw(string strAccount, float Amount);
  void openAccount(const string accountType, 
		const string customerName, float fAmount);
  void advanceDays(int iDays);
  void showBalance(string strCustomer);
  void invalidCommand(string strCommand);
  void printIt();
  void deleteEverything();
	vector<Customer> CustomerList;

private:
	string strBankName;

};

