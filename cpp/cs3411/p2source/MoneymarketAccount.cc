#include "MoneyMarketAccount.h"
#include <string>

void MoneyMarketAccount::withdraw(float fAmount) {
  if (fAmount > fBalance) {
    cout << "*** Insufficient funds" << endl;
  } else {
    if (fAmount > 0.0) {
      fBalance -= (fAmount + WITHDRAW_PENALTY);
    } else {
      cout << "Please Enter a positive amount to withdraw" << endl;
    }
  }
}

void MoneyMarketAccount::calculateInterest(int iDays) {
  fBalance *= (1 + fInterestRate / DAYS_IN_YEAR * iDays); 
}

string MoneyMarketAccount::getType() {
  return MONEYMARKET;
}

