#include "Global.h"

#include "Account.h"
#include "SavingAccount.h"
#include "MoneyMarketAccount.h"
#include "CheckingAccount.h"

#ifndef CUSTOMER_H
#define CUSTOMER_H

static const int DEC_ACCURACY = 2;
class Customer {

  public:
    Customer(string name, string id);
		void addAccount(string strType, string strID, float fBal);
		void printAccounts();
		string getName();
		string getID();
    string toString();
  	string strName;
    string strID;

		vector< Account* > AccountList;
};

#endif
