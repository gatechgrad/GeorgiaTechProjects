#include "Account.h"

#ifndef CHECKINGACCOUNT_H
#define CHECKINGACCOUNT_H

class CheckingAccount : public Account {

	public:
	  CheckingAccount(float fBal, string strIdent) :
			Account(fBal, strIdent) { 
			fInterestRate = 0.015;}
    virtual void calculateInterest(int iDays);
    virtual string getType();
};

#endif
