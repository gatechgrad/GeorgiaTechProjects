#ifndef MENUITEM_H
#define MENUITEM_H

#include "global.h"

class menuitem {
  public:
    menuitem(char newMenuLetter, int newPrice, int iType, string strName, string newDescription);
    string selectableString();
    string nonSelectableString();
    int getPrice();
    string toString();
  private:
    int iPrice, iType;
    char cMenuLetter;
    string strDescription, strName;
   
};

#endif MENUITEM_H
