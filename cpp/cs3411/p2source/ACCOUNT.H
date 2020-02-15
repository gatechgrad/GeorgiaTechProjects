#include "Global.h" 
//#include "SavingAccount.h"
//#include "CheckingAccount.h"
//#include "MoneyMarketAccount.h"

#ifndef ACCOUNT_H
#define ACCOUNT_H

const int DAYS_IN_YEAR = 365;

const string MONEYMARKET = "moneymarket";
const string SAVING = "saving";
const string CHECKING = "checking";


class Account {

	public:
    Account(float fBal, string strIdent);
    virtual ~Account() { }
		virtual void withdraw(float fAmount);
		void deposit(float iAmount);
    virtual void calculateInterest(int iDays); // = 0;
		virtual string getType(); // = 0;
		float getBalance();
    string getID();
		string toString();
    float getInterestRate();

	protected:
		float fBalance;
		string strID;
    float fInterestRate;
};

#endif
