
#ifndef STORE_H
#define STORE_H


#include "global.h"
#include "menu.h"
#include "sysop.h"


class store {
  public:
    store(ansicolor *aColorControl, sysop *newSysop);
    virtual ~store() { }
  protected:
    void displayMenu();// = 0;
    string strName, strNumber;
    ansicolor *ColorControl;
    vector<menu> menuList;
    sysop *TheSysop;

};

class computerstore : public store {
  public:
    computerstore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~computerstore() { }

    menu *menuModem;
    menu *menuComputer;
    menu *menuStorage;

};

class departmentstore : public store {
  public:
    departmentstore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~departmentstore() { }
  private:
};

class softwarestore : public store {
  public:
    softwarestore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~softwarestore() { }
  private:
};

class discountstore : public store {
  public:
    discountstore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~discountstore() { }
  private:
};

class localstore : public store {
  public:
    localstore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~localstore() { }
  private:
};

class corporationstore : public store {
  public:
    corporationstore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~corporationstore() { }
  private:
};

class phonestore : public store {
  public:
    phonestore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~phonestore() { }
  private:
};

class advertisingstore : public store {
  public:
    advertisingstore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~advertisingstore() { }
  private:
};

class grocerystore : public store {
  public:
    grocerystore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~grocerystore() { }
  private:
};

class otherstore : public store {
  public:
    otherstore(ansicolor *aColorControl, sysop *newSysop);
    virtual ~otherstore() { }
  private:
};

#endif STORE_H
