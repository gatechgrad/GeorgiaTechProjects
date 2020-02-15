#include "Account.h"

#ifndef MONEYMARKETACCOUNT_H 
#define MONEYMARKETACCOUNT_H

const float WITHDRAW_PENALTY = 20.00;

class MoneyMarketAccount : public Account {

	public:
	  MoneyMarketAccount(float fBal, string strIdent) :
			Account(fBal, strIdent) { fInterestRate = 0.06; }
    virtual void calculateInterest(int iDays);
	  virtual void withdraw(float fAmount);
    virtual string getType();
};

#endif
