#ifndef SERVER_H
#define SERVER_H

#include "global.h"
#include "virtualsysop.h"

const int PASSWORD_TRIES = 3;
const string PASSWORDS_FILE = "svpasswd.dat";
class server {

  public:
    server();
  private:
    void logIn();
    void listGames();
};

#endif SERVER_H
