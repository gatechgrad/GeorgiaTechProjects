#include "Account.h"

#ifndef SAVINGACCOUNT_H
#define SAVINGACCOUNT_H

const int A_WEEK = 7; //7 days in a week

class SavingAccount : public Account {
  
	public:
	  SavingAccount(float fBal, string strIdent) :
			Account(fBal, strIdent) { fInterestRate = 0.03; 
			//set the last withdrawl date to a week and a day
			//this way the customer doesn't have to wait a 
			//week to make his/her first withdrawl
			iDaysSinceLastWithdrawl = A_WEEK + 1; }

		virtual void withdraw(float fAmount);
    virtual void calculateInterest(int iDays);
		virtual string getType();
    int iDaysSinceLastWithdrawl;

};

#endif
