#include "menu.h"
menu::menu(char newChoice, string newMenuName) {
  chChoice = newChoice;
  strMenuName = newMenuName;
}

void menu::display() {
  for (vector<menuitem>::iterator i = menuitemList.begin();
         i != menuitemList.end(); i++) {
    cout << i->toString();
  }

}

/*
void menu::buy(sysop *ShoppingSysop) {
  for (vector<menuitem>::iterator i = menuitemList.begin();
         i != menuitemList.end(); i++) {
    if (ShoppingSysop->getMoney() >= i->getPrice()) {
      cout << i->selectableString();
    } else {
      cout << i->nonSelectableString();
    }
  }


}
*/

void menu::addMenuItem(menuitem newMenuItem) {
  menuitemList.push_back(newMenuItem);

}

string menu::toString() {
  string strToReturn;
  strToReturn = strMenuName;
  return strToReturn;
}

char menu::getChoiceLetter() {
  return chChoice;
}
