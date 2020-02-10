#include "server.h"

server::server() {
  logIn();
}

void server::logIn() {
  int i;
  string strUserName, strUserPassword, strCurrentName, strLine, strToken;
  bool foundUser, correctPassword;

  foundUser = false;
  cout << "Enter your username:" << endl;
  getline(cin,strUserName);


  cout << "Searching Database..." << endl;
 
  ifstream filePasswords(PASSWORDS_FILE.c_str());
  if (filePasswords) {
    while (getline(filePasswords, strLine)) {
      istrstream issPlayers(strLine.c_str(), '|');
      issPlayers >> strToken;
      cout << "Reading --> " << strToken << endl;
      if (strToken == strUserName) {
        printf("Found user");
        foundUser = true; 
      }     
      getCharacter(); 
    } 
  }

  if (foundUser) {
    correctPassword = false;
    istrstream issPlayers(strLine.c_str(), '|');
    issPlayers >> strToken;
    issPlayers >> strToken;
    i = 1;
    while( (!correctPassword) && (i < 3)) {
      cout << "Enter your password:" << endl;
      cin >> strUserPassword;
      if (strUserPassword == strToken) {
        correctPassword = true;
      } else {
        cout << "Incorrect password" << endl; 
        i++;
      }
    }
 
    if (correctPassword) {
        cout << "Welcome!!" << endl; 
    } else {
      cout << "Too many tries... Exiting" << endl;
      exit(1); 
    }
  } else {
    cout << "Enter the password you wish to use: " << endl;
    getline(cin, strUserPassword);
    ofstream ofsPasswords(PASSWORDS_FILE.c_str());
    //ofstream ofsPasswords(PASSWORDS_FILE, ios::app, 644);
    ofsPasswords << strUserName << '|' << strUserPassword; 
  }

  virtualsysop *theGame = new virtualsysop(strUserName);
  cout << "coredump 7" << endl;
  delete(theGame);
  cout << "coredump 8" << endl;

}

void server::listGames() {

}

int main() {
  server *myServer = new server();
  delete(myServer);
  return 0;
}
