#include "Account.h"

Account::Account(float fBal, string strIdent) {
	fBalance = fBal;
  strID = strIdent;
}

void Account::withdraw(float fAmount) {
  if (fAmount > fBalance) {
    cout << "*** Insufficient funds" << endl; 
  } else {
    if (fAmount > 0.0) {
      fBalance -= fAmount;
    } else {
      cout << "Please Enter a positive amount to withdraw" << endl;
    }
  }
}

void Account::calculateInterest(int iDays) {

}

void Account::deposit(float fAmount) {
	fBalance += fAmount;
//  cout << fBalance << endl;
}

/**
	* toString() - this toString just returns a
	*              string representing the object.
	*              it is NOT intended to be called
	*              when the object is printed in Java
	**/
string Account::toString() {
  string strTemp;
	string strToReturn;
  string strBalance;

	strToReturn = strID + " (" + getType() + " ";
	
	ostrstream myOSS;
	myOSS << getInterestRate() << ends;
	strToReturn = strToReturn + myOSS.str();

	//this "hack" was supplied by Brian McNamara on
	//the CS3411 newsgroup.  I have made some modifications
	//to the original code
	strToReturn += "%) $";
  char s[1024];

	//check for roundoff ex: the balance is 0.00001 
	if (((fBalance * 100) - ((100 * ((int) (fBalance))))) == 0.0) {
    strBalance = sprintf( s, "%.2f", fBalance );
  } else {
    strBalance = sprintf( s, "%.2f", (fBalance - 0.01));
	}
  strToReturn += strBalance;	
	return strToReturn;
}
 
float Account::getInterestRate() {
  return (fInterestRate * 100);
}

float Account::getBalance() {
  return fBalance;
}

string Account::getID() {
  return strID;
}

string Account::getType() {
 return "generic";
}
