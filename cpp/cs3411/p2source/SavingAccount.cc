#include "SavingAccount.h"

void SavingAccount::withdraw(float fAmount) {
  if (fAmount > fBalance) {
    cout << "*** Insufficient funds" << endl;
  } else {

  	if (fAmount > 0.0) {
  	  if (iDaysSinceLastWithdrawl >= A_WEEK) {
        fBalance -= fAmount;
				iDaysSinceLastWithdrawl = 0;
      } else {
    	  cout << "You must wait at least a week to withdraw "
    	       << "more money" << endl;
    	}
    } else {
      cout << "Please Enter a positive amount to withdraw" << endl;
	  }
  }
}

void SavingAccount::calculateInterest(int iDays) {
  fBalance *= (1 + fInterestRate / DAYS_IN_YEAR * iDays); 
  iDaysSinceLastWithdrawl += iDays;
}

string SavingAccount::getType() {
	return "saving";
}

