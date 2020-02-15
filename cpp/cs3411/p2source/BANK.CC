#include "Bank.h"
 
Bank::Bank(string newName) {
  strBankName = newName;
  menu();
//  deleteEverything();
}
/**
	* menu   - a simple menu that keeps loopint until the user enters
	* (void)   QUIT.  all of the "if (!(istrstream >> string))" lines
	*          verify that the input does not have enough or any
	*          extra tokens.
	**/
void Bank::menu(void) {
	bool isLooping;
  string strLine;
  string strToken;

	string strName;
	string strName2;
	string strAccountType;
  float fAmount;
  int iDays;

	isLooping = true;

  cout << "\n\nWelcome to " << strBankName << "\n" << endl;

	while (isLooping) {

    cout << "\n" << PROMPT;
    getline(cin, strLine);
    istrstream myStringStream(strLine.c_str());

      myStringStream >> strToken;

	    if (strToken == OPEN) {
				if (!( myStringStream >> strAccountType)) {
          invalidCommand(strLine);
				} else if (!(myStringStream >> strName)) {
          invalidCommand(strLine);
				} else if (!( myStringStream >> fAmount)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> strToken)) {
					openAccount(strAccountType, strName, fAmount);
				} else {
          invalidCommand(strLine); 
				}

      } else if (strToken == BALANCE) {
        if (!(myStringStream >> strName)) {
          invalidCommand(strLine);
				} else if (!(myStringStream >> strToken)) {
					showBalance(strName);
				} else {
					invalidCommand(strLine);
        }
			} else if (strToken == CLOSE) {

   		} else if (strToken == WITHDRAW) {
        if(!(myStringStream >> strName)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> fAmount)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> strToken)) {
					withdraw(strName, fAmount);
        } else {
					invalidCommand(strLine);
        }

	  	} else if (strToken == DEPOSIT) {
        if(!(myStringStream >> strName)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> fAmount)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> strToken)) {
					deposit(strName, fAmount);
        } else {
					invalidCommand(strLine);
        }

	  	} else if (strToken == TRANSFER) {
        if(!(myStringStream >> strName)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> strName2)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> fAmount)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> strToken)) {
					transfer(strName, strName2, fAmount);
        } else {
					invalidCommand(strLine);
        }

	  	} else if (strToken == ADVANCE) {
        if(!(myStringStream >> iDays)) {
					invalidCommand(strLine);
        } else if (!(myStringStream >> strToken)) {
					advanceDays(iDays);
        } else {
					invalidCommand(strLine);
        }
	
	  	} else if (strToken == QUIT) {
        if (!(myStringStream >> strToken)) {
				  isLooping = false;
        } else {
          invalidCommand(strLine);
				}

			} else if (strToken == HELP) {
        if (!(myStringStream >> strToken)) {
				  helpMessage();
        } else {
          invalidCommand(strLine);
				}

		  } else {
        invalidCommand(strLine);

			}
  }
}



/**
	* openAccount - opens a new customer account
	* (string,
	*  string,
	*  float)
	**/
void Bank::openAccount(string accountType, 
  string customerName, float fAmount) {

	static int iCustomerCount = 1; //static int variables start at 0
	static int iAccountCount = 1;  //and live throughout the program
  string customerID;
	string accountID;
  string customerIDLetter = "c";
	string accountIDLetter = "a";
	bool foundName;
  unsigned int iListSize;

  foundName = false;

  //---------------- search for customer in list ---------------
	vector< Customer >::iterator i;

  for( i = CustomerList.begin(); i != CustomerList.end(); i++) {
    if (i->getName() == customerName) {
      foundName = true;
	//-----------------add account for found customer -------------
    //cout << "*** Updating Customer" << endl; 

    ostrstream accountIDNumber;
		accountIDNumber << iAccountCount << ends;
		accountID = (accountIDLetter + accountIDNumber.str());
    
		iListSize = i->AccountList.size(); //get the list size
    i->addAccount(accountType, accountID, fAmount);
    
		if (i->AccountList.size() > iListSize) {
      //an account was added, so increment the counter
			iAccountCount++;
		}

  //-----------------end add account for found customer ---------
  //-----------------end search for customer in list ------------
    }
  }

  if (!(foundName)) {
	//-----------------make a new Customer ------------------------
	customerID = "c0"; //default
	
  ostrstream customerIDNumber;
	customerIDNumber << iCustomerCount << ends;
	customerID = (customerIDLetter + customerIDNumber.str());
  
	//cout << "*** Making a new customer" << endl;
	Customer *aCustomer = new Customer(customerName, customerID);

//  cout << aCustomer->toString() << endl;

		accountID = "a0";
    ostrstream accountIDNumber;
		accountIDNumber << iAccountCount << ends;
		accountID = (accountIDLetter + accountIDNumber.str());

    aCustomer->addAccount(accountType, accountID, fAmount);
		//only add the customer if the account was 
		//successfully created
    if (aCustomer->AccountList.size() > 0) {
		  CustomerList.push_back(*aCustomer);
      iAccountCount++;
			iCustomerCount++;
		}
  //-----------------end make a new Customer ---------------------
  }
  //printIt();
}

/**
	* printIt - print a list of customers
	*
	**/
void Bank::printIt() {
	vector< Customer >::iterator i;

  for( i = CustomerList.begin(); i != CustomerList.end(); i++) {
    //cout << i->toString() << endl; 
    i->printAccounts();
	}
}

/**
	* advanceDays - advances the number of days elapsed 
	* (int)         I am using an MVC'ish (model/view/control)
	*               design here.  When the number of days is 
	*               changed, all accounts are notified, and they
	*               use that data (the number of days) in their
	*               own particular way
	* iDays - the number of days elapsed 
	**/
void Bank::advanceDays(int iDays) {
	vector< Customer >::iterator i;
	vector< Account* >::iterator j;

  for( i = CustomerList.begin(); i != CustomerList.end(); i++) {
		for ( j = (i->AccountList).begin(); j != (i->AccountList).end();
			j++) {
	  			(*j)->calculateInterest(iDays);
		}
  }

}

/**
	* showBalance - shows the balances for a customer or account
	*
	*
	**/
void Bank::showBalance(string strID) {
	bool foundID;
	vector< Customer >::iterator i;
  vector< Account* >::iterator j;

	foundID = false;
	
  for( i = CustomerList.begin(); i != CustomerList.end(); i++) {
    if (i->getID() == strID) {
      foundID = true;
			i->printAccounts();
    }
		for ( j = i->AccountList.begin(); j != i->AccountList.end(); j++) {
      if ((*j)->getID() == strID) {
        cout << i->toString() << " " << (*j)->toString() << endl;
			}
		}
  }

	if (!(foundID)) {
    cout <<  "*** Invalid ID: " << strID << endl;
	}

}

/**
	* closeAccount - closes and deletes an account
	*
	*
	**/
void Bank::closeAccount() {

}

/**
	* deposit - deposits money into an account
	*
	*
	**/
void Bank::deposit(string strAccount, float fAmount) {
	bool foundID;

	vector< Customer >::iterator i;
	vector< Account* >::iterator j;

	foundID = false;

  for( i = CustomerList.begin(); i != CustomerList.end(); i++) {
		for ( j = (i->AccountList).begin(); j != (i->AccountList).end();
			j++) {
        if ((*j)->getID() == strAccount) {
          foundID = true;
	  			(*j)->deposit(fAmount);
					cout << i->toString() << " " << (*j)->toString() << endl;

			  }
       //cout << (*j)->toString() << endl; 
	
		}
  }

	if (!(foundID)) {
    cout <<  "*** Invalid Account: " << strAccount << endl;

	}


}

/**
	* transfer - transfers money into another account
	*
	*
	**/
void Bank::transfer(string strFrom, string strTo, float fAmount) {
	bool foundFirstAccount;
  bool foundSecondAccount;
	vector< Customer >::iterator i;
	vector< Account* >::iterator j;
  vector< Customer >::iterator k;
	vector< Account* >::iterator l;

	foundFirstAccount = false;
  foundSecondAccount = false;
//-------------------Look for the first account ----------------
  for (i = CustomerList.begin(); i != CustomerList.end(); i++) { 
		for ( j = (i->AccountList).begin(); j != (i->AccountList).end();
			  j++) {
      if ((*j)->getID() == strFrom) {
        foundFirstAccount = true;
			  //------------------ Look for the second account ----------
        for (k = CustomerList.begin(); k != CustomerList.end(); k++) 
			  {
					for (l = (k->AccountList).begin(); l != 
			  		(k->AccountList).end(); l++) {

							if ((*l)->getID() == strTo) {
                foundSecondAccount = true;
								if ((*j)->getBalance() > fAmount) {
                  (*l)->deposit(fAmount);
								  (*j)->withdraw(fAmount);
					        cout << i->toString() << " " << (*j)->toString() 
											 << endl;
					        cout << k->toString() << " " << (*l)->toString() 
											 << endl;
							  } else {
                  cout << "*** Insufficient funds" << endl;
								}
								//break;

							}
            }        
          }     
					//------------------ End look for the second account -----
			}
		}
  }

	if (!(foundFirstAccount)) {
    cout <<  "*** Invalid Account: " << strFrom << endl;
	}
	if (!(foundSecondAccount)) {
    cout <<  "*** Invalid Account: " << strTo << endl;
	}

}

/**
	* withdraw - withdraws money from an account
	*
	*
	**/
void Bank::withdraw(string strAccount, float fAmount) {
	bool foundID;

	vector< Customer >::iterator i;
	vector< Account* >::iterator j;

	foundID = false;

  for( i = CustomerList.begin(); i != CustomerList.end(); i++) {
		for ( j = (i->AccountList).begin(); j != (i->AccountList).end();
			j++) {
        if ((*j)->getID() == strAccount) {
          foundID = true;
	  			(*j)->withdraw(fAmount);
					cout << i->toString() << " " << (*j)->toString() << endl;
			  }
		}
  }

	if (!(foundID)) {
    cout <<  "*** Invalid Account: " << strAccount << endl;
	}
}


/*
 * helpMessage - displays a list of commands 
 * to the user
 */
void Bank::helpMessage() {
	cout << ">>  " << ADVANCE << " <n-days>" << endl
			 << ">>  " << BALANCE << " <acct#> | <cust#>" << endl
			 << ">>  " << CLOSE << " <acct#> | <cust#>" << endl
			 << ">>  " << DEPOSIT << "<acct#> <amt>" << endl
			 << ">>  " << HELP << endl
			 << ">>  " << OPEN << " <acct-type> <cust-name> <init-deposit>" 
					<< endl
			 << ">>  " << QUIT << endl
			 << ">>  " << TRANSFER << " <from-acct#> <to-acct#> <amt>"
				 << endl
			 << ">>  " << WITHDRAW << " <acct#> <amt>" << endl
			 << ">>  <acct-type> = checking | saving | moneymarket"
				 << endl;
}

/**
	*  invalidCommand - tells the user that the command that they
	*                   have entered is invalid
	*  string strCommand - the invalid command that the user
	*                      typed
	**/
void Bank::invalidCommand(string strCommand) {
  cout << OUTPUT_LEADER << "*** invalid command: " 
			 << strCommand << endl;
}

/**
	* deleteEverything - deletes all of the customers and 
	*                    all customer accounts
	**/
void Bank::deleteEverything() {
	vector< Customer >::iterator i;
	vector< Account* >::iterator j;

  for( i = CustomerList.begin(); i != CustomerList.end(); i++) {
		for ( j = (i->AccountList).begin(); j != (i->AccountList).end();
			j++) {
   			delete j;
  	  }
//      delete i;
  }
}

int main(void) {
	Bank *theBank = new Bank("First Polymorphism Savings and Loan");
  delete theBank;

  return SUCCESS;
}
