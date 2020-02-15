#include "CheckingAccount.h"

void CheckingAccount::calculateInterest(int iDays) {
  fBalance *= (1 + fInterestRate / DAYS_IN_YEAR * iDays); 
}

string CheckingAccount::getType() {
  return CHECKING;
}
