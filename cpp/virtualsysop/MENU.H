#ifndef MENU_H
#define MENU_H

#include "global.h"

//#include "sysop.h"
#include "menuitem.h"
#include <vector>

class menu {
  public:
    menu(char newChoice, string newMenuName);
   // void buy(sysop *ShoppingSysop);
    void display();
    void addMenuItem(menuitem newMenuItem);
    string toString();
    char getChoiceLetter();
  private:
    vector<menuitem> menuitemList;
    char chChoice;
    string strMenuName;

};

#endif MENU_H
