#include "Customer.h"

Customer::Customer(string newName, string newID) {
  strName = newName;
	strID = newID;
}
/**
	* addAccount - adds a new account to the list of the
	*              user's accounts
	*
	**/
void Customer::addAccount(string strAcctType, string strAcctID, 
		float fBal) {

	if (strAcctType == "checking") {

	  CheckingAccount *myAcct = new CheckingAccount(fBal, strAcctID);
    cout << strID << " (" << strName << ") " << myAcct->toString() 
				 << endl;
		AccountList.push_back(myAcct);
	} else if (strAcctType == "saving") {

	  SavingAccount *myAcct = new SavingAccount(fBal, strAcctID);
    cout << strID << " (" << strName << ") " << myAcct->toString() 
				 << endl;
		AccountList.push_back(myAcct);

  } else if (strAcctType == "moneymarket") {

	  MoneyMarketAccount *myAcct = 
				 new MoneyMarketAccount(fBal, strAcctID);
    cout << strID << " (" << strName << ") " << myAcct->toString() 
				 << endl;
		AccountList.push_back(myAcct);

	} else {
    cout << "*** invalid account type" << endl;
	}


}

string Customer::toString() {
	string strToReturn;
	strToReturn = strID + " (" + strName + ")";
	return (strToReturn);
}

string Customer::getName() {
  return strName;
}

string Customer::getID() {
  return strID;
}

void Customer::printAccounts() {
  vector< Account* >::iterator j;

	for ( j = AccountList.begin(); j != AccountList.end(); j++) {
  cout << strID << " (" << strName << ") " << (*j)->toString() << endl;
  }
}

