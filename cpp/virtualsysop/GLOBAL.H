#ifndef GLOBAL_H
#define GLOBAL_H


#include <iostream>
#include <string>
#include <strstream>
#include "ansicolor.h"
#include <fstream>



inline char getCharacter();
//void pressAnyKey();
#define __msdos__
#undef __unix__

#ifdef __unix__
  inline char getCharacter() {
    string str;
    getline(cin, str);
    return str[0]; 
  }

/*
  void pressAnyKey() {
    string str;
    getline(cin, str);
  }
*/

#endif __unix__

#ifdef __msdos__
  #include <conio.h>

  inline char getCharacter() {
    char chInput;
    cout << endl;
    chInput = getche();
    return chInput;
  }
#endif __msdos__

#endif GLOBAL_H
