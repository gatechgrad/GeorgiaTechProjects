#include "menuitem.h"

menuitem::menuitem(char newMenuLetter, int newPrice, int newType, string newName, string newDescription) {
  cMenuLetter = newMenuLetter;
  iPrice = newPrice;
  iType = newType;
  strDescription = newDescription;
  strName = newName;
}

string menuitem::selectableString() {
  return "(1) 14.4 Modem $70 - crappy modem";
}

string menuitem::nonSelectableString() {
  return "( ) 14.4 Modem $70 - crappy modem";
}

int menuitem::getPrice() {
  return iPrice;

}

string menuitem::toString() {
  return "string";
}
