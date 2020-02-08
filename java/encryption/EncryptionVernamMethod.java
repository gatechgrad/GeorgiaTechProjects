import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.util.*;
class EncryptionVernamMethod extends EncryptionMethod  {



  /** CONSTANTS **/





  /** GLOBAL VARIABLES **/



  public EncryptionVernamMethod() {



  }



  /**

   * abstract class

   */

  public String encrypt(String s, TutorialDisplay txt) {

      String strCipherText;
      Random r = new Random();
      char c;
      int iRandom;
      int iNumeric;
      int i;

      StringBuffer cipher = new StringBuffer(s.length());
      int iOneTimePad[] = new int[s.length()];

      txt.addText("\n===============\nVernam Cipher");


      for (i = 0; i < s.length(); ++i)
      {
          c = s.charAt(i);
          if (Character.isLetter(c)) {
              iRandom = (int)(r.nextDouble()* 100);

              txt.addText("Converting: " + c + "\n");
              txt.addText("Num. equivalent: " + (Character.toUpperCase(c) - 'A') + "\n");
              txt.addText("Random Num: " + iRandom + "\n");
              txt.addText("Sum: " + (Character.toUpperCase(c) - 'A' + iRandom) + "\n");
              txt.addText("Mod 26: " + ((Character.toUpperCase(c) - 'A' + iRandom)%26) + "\n");
              txt.addText("CipherText: " + ((char)((Character.toUpperCase(c) - 'A' + iRandom)%26 + 'A')) + "\n\n");
              iOneTimePad[i] = iRandom;


              cipher.append( (char)((Character.toUpperCase(c) - 'A' + iRandom)%26 + 'A'));
          } else {
              cipher.append(c);
          }



      }

      txt.addText("One Time Pad: ");
      for (i = 0; i < s.length(); i++) {
        txt.addText(iOneTimePad[i] + " ");
      }


      return cipher.toString();
  }



  /**

   * abstract class

   */

  public Panel getOptionsPanel() {

    Panel p;

    Panel pnlTemp;



    p = new Panel(new BorderLayout());



    p.add(new Label("No options available for Vernam Cipher"));







    p.setBackground(new Color(192, 192, 255));









    return p;



  }





}

