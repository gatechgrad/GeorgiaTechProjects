import java.awt.*;
import java.awt.event.*;

class EncryptionDESMethod extends EncryptionMethod implements ActionListener {

  /** CONSTANTS **/
  public static final int KEY_SIZE = 64;
  public static final int PERMUTE_KEY_SIZE = 56;
  public static final String BUTRANDOM_LABEL = "Generate Random Key";


  /** GLOBAL VARIABLES **/
  private TextField txtKey;
  private int iKey[];
  private TutorialDisplay txt;

  private Choice choiceKey01, choiceKey02, choiceKey03, choiceKey04,
                 choiceKey05, choiceKey06, choiceKey07, choiceKey08;

  private Button butRandom;


  /**
   * constructor
   */
  public EncryptionDESMethod() {

  }

  /**
   * abstract class
   */
  public String encrypt(String s,  TutorialDisplay t) {
    String strCipherText;
    String strKey;
    int i;
    int iKey[];
    int iPermuteKey[];
    int iPC1[] = { 57, 49, 41, 33, 25, 17,  9,
                    1, 58, 50, 42, 34, 26, 18,
                   10,  2, 59, 51, 43, 35, 27,
                   19, 11,  3, 60, 52, 44, 36,
                   63, 55, 47, 39, 31, 23, 15,
                    7, 62, 54, 46, 38, 30, 22,
                   14,  6, 61, 53, 45, 37, 29,
                   21, 13, 5, 28, 20, 12, 4 };
    int iPC2[] = { 14, 17, 11, 24,  1,  5,
                    3, 28, 15,  6, 21, 10,
                   23, 19, 12,  4, 26,  8,
                   16,  7, 27, 20, 13,  2,
                   41, 52, 31, 37, 47, 55,
                   30, 40, 51, 45, 33, 48,
                   44, 49, 39, 56, 34, 53,
                   46, 42, 50, 36, 29, 32 };
    int iKeyC[][];
    int iKeyD[][];
    int iKeyK[][];
    int iTemp[];
    int iMessage[];
    int iEncryptPosition;

    this.txt = t;



    strKey = choiceKey01.getSelectedItem() + choiceKey02.getSelectedItem() +
             choiceKey03.getSelectedItem() + choiceKey04.getSelectedItem() + 
             choiceKey05.getSelectedItem() + choiceKey06.getSelectedItem() + 
             choiceKey07.getSelectedItem() + choiceKey08.getSelectedItem();

    txt.addText("\n===============\nDES Encryption using key: " + strKey + "\n\n");

    iKey = new int[KEY_SIZE];


    convertBinaryKey(strKey, iKey);
    //printArray(iKey);
    txt.addText("Binary key is " + getArrayString(iKey) + " (every 8th bit is dropped)\n");


    iPermuteKey = new int[PERMUTE_KEY_SIZE];

    txt.addText("Permuting key by the following matrix:\n" + getMatrixString(iPC1, 7) + "\n");
    permuteKey(iKey, iPermuteKey, iPC1);
    txt.addText("Permuted key is " + getArrayString(iPermuteKey) + "\n");



    //printArray(iPermuteKey);

    iKeyC = new int[17][28];
    iKeyD = new int[17][28];

    shiftPermutedKeys(iPermuteKey, iKeyC, iKeyD);
    txt.addText("\nSplitting key \n" + getArrayString(iKeyC[0]) + "\n" + getArrayString(iKeyD[0]) + "\n");
    txt.addText("\nCreating 16 Subkeys\n");
    txt.addText("Left shifting split keys\n");
    txt.addText("Key 1, 1 left shift:\n" + getArrayString(iKeyC[1]) + "\n" + getArrayString(iKeyD[1]) + "\n");
    txt.addText("Key 2, 1 left shift:\n" + getArrayString(iKeyC[2]) + "\n" + getArrayString(iKeyD[2]) + "\n");
    txt.addText("Key 3, 2 left shifts:\n" + getArrayString(iKeyC[3]) + "\n" + getArrayString(iKeyD[3]) + "\n");
    txt.addText("Key 4, 2 left shifts:\n" + getArrayString(iKeyC[4]) + "\n" + getArrayString(iKeyD[4]) + "\n");
    txt.addText("Key 5, 2 left shifts:\n" + getArrayString(iKeyC[5]) + "\n" + getArrayString(iKeyD[5]) + "\n");
    txt.addText("Key 6, 2 left shifts:\n" + getArrayString(iKeyC[6]) + "\n" + getArrayString(iKeyD[6]) + "\n");
    txt.addText("Key 7, 2 left shifts:\n" + getArrayString(iKeyC[7]) + "\n" + getArrayString(iKeyD[7]) + "\n");
    txt.addText("Key 8, 2 left shifts:\n" + getArrayString(iKeyC[8]) + "\n" + getArrayString(iKeyD[8]) + "\n");
    txt.addText("Key 9, 1 left shift:\n" + getArrayString(iKeyC[9]) + "\n" + getArrayString(iKeyD[9]) + "\n");
    txt.addText("Key 10, 2 left shifts:\n" + getArrayString(iKeyC[10]) + "\n" + getArrayString(iKeyD[10]) + "\n");
    txt.addText("Key 11, 2 left shifts:\n" + getArrayString(iKeyC[11]) + "\n" + getArrayString(iKeyD[11]) + "\n");
    txt.addText("Key 12, 2 left shifts:\n" + getArrayString(iKeyC[12]) + "\n" + getArrayString(iKeyD[12]) + "\n");
    txt.addText("Key 13, 2 left shifts:\n" + getArrayString(iKeyC[13]) + "\n" + getArrayString(iKeyD[13]) + "\n");
    txt.addText("Key 14, 2 left shifts:\n" + getArrayString(iKeyC[14]) + "\n" + getArrayString(iKeyD[14]) + "\n");
    txt.addText("Key 15, 2 left shifts:\n" + getArrayString(iKeyC[15]) + "\n" + getArrayString(iKeyD[15]) + "\n");
    txt.addText("Key 16, 1 left shift:\n" + getArrayString(iKeyC[16]) + "\n" + getArrayString(iKeyD[16]) + "\n");




    iKeyK = new int[16][48];
    iTemp = new int[56];

    txt.addText("\nReforming split keys to create subkeys\n");

    for (i = 0; i < 16; i++) {
      System.arraycopy(iKeyC[i + 1], 0, iTemp, 0, 28);
      System.arraycopy(iKeyD[i + 1], 0, iTemp, 28, 28);

      txt.addText("Key " + (i + 1) + ": " + getArrayString(iTemp) + "\n");

      permuteKey(iTemp, iKeyK[i], iPC2);

    }

    txt.addText("\nPermuting subkeys by the following matrix\n" + getMatrixString(iPC2, 6) + "\n");

    for (i = 0; i < 16; i++) {
      txt.addText("Key " + (i + 1) + ":\n" + getArrayString(iKeyK[i]) + "\n");
    }




    //Begin encrypting message
    iEncryptPosition = 0;
    strCipherText = "";

    while (iEncryptPosition < s.length()) {
      txt.addText("\n\nEncrypting Message\n");

    
      iMessage = new int[64];

      convertMessage(s.substring(iEncryptPosition), iMessage);  //load the message into the array
      txt.addText("\n64 Bit message block is " + getArrayString(iMessage) + "\n");


      strCipherText += encodeMessage(iMessage, iKeyK);
      iEncryptPosition += 8;
    }


/*
    iMessage = new int[64];

    convertMessage(s, iMessage);

    strCipherText = encodeMessage(iMessage, iKeyK);
*/

    return strCipherText;
    

  }


  /**
   * encodeMessage - performs the final steps
   */
  private String encodeMessage(int iMessage[], int iKeyK[][]) {
    int i, j;
    int iMessageIP[];
    int iIP[] = { 58, 50, 42, 34, 26, 18, 10,  2,
                  60, 52, 44, 36, 28, 20, 12,  4,
                  62, 54, 46, 38, 30, 22, 14,  6,
                  64, 56, 48, 40, 32, 24, 16,  8,
                  57, 49, 41, 33, 25, 17,  9,  1,
                  59, 51, 43, 35, 27, 19, 11,  3,
                  61, 53, 45, 37, 29, 21, 13,  5,
                  63, 55, 47, 39, 31, 23, 15,  7 };
    int iEBitSelection[] = { 32,  1,  2,  3,  4,  5,
                            4,  5,  6,  7,  8,  9,
                            8,  9, 10, 11, 12, 13,
                           12, 13, 14, 15, 16, 17,
                           16, 17, 18, 19, 20, 21,
                           20, 21, 22, 23, 24, 25,
                           24, 25, 26, 27, 28, 29,
                           28, 29, 30, 31, 32,  1 };
    int iPTable[] = { 16,  7, 20, 21, 29, 12, 28, 17,
                       1, 15, 23, 26,  5, 18, 31, 10,
                       2,  8, 24, 14, 32, 27,  3,  9,
                      19, 13, 30,  6, 22, 11,  4, 25 };

    int iKeyL[][];
    int iKeyR[][];
    int iEOp[];
    int iXOR[];
    int iSOutput[];
    int iFOutput[];
    int iFinalIP[] = { 40,  8,  48, 16, 56, 24, 64, 32,
                       39,  7,  47, 15, 55, 23, 63, 31,
                       38,  6,  46, 14, 54, 22, 62, 30,
                       37,  5,  45, 13, 53, 21, 61, 29,
                       36,  4,  44, 12, 52, 20, 60, 28,
                       35,  3,  43, 11, 51, 19, 59, 27,
                       34,  2,  42, 10, 50, 18, 58, 26,
                       33,  1,  41,  9, 49, 17, 57, 25 };
    int iConcatenated[];
    int iCodedMessage[];
    int iChar;
    String strReturn;



    iMessageIP = new int[64];
    permuteKey(iMessage, iMessageIP, iIP);

    txt.addText("Initial Permutation Table:\n" + getMatrixString(iIP, 4));
    txt.addText("E-Bit Selection Table:\n" + getMatrixString(iEBitSelection, 6));
    txt.addText("P Table:\n" + getMatrixString(iPTable, 4));
    txt.addText("Final Permutation Table:\n" + getMatrixString(iFinalIP, 4));


    txt.addText("\nPermuting message by Initial Permutation Table\n");
    txt.addText("\nPermuted message is " + getArrayString(iMessageIP) + "\n");


    //printArray(iMessage);
    //printArray(iMessageIP);

    //split the message
    iKeyL = new int[17][32];
    iKeyR = new int[17][32];




    txt.addText("Splitting Message into L0, R0\n");

    for (i = 0; i < 32; i++) {
      iKeyL[0][i] = iMessageIP[i];
      iKeyR[0][i] = iMessageIP[i + 32];

    }



    //printArray(iKeyL[0]);
    //printArray(iKeyR[0]);


    //loop
    for (i = 0; i < 16; i++) {

      txt.addText("\nCopying R" + i + " to L" + (i + 1) + "\n" + getArrayString(iKeyR[i]) + "\n");

      System.arraycopy(iKeyR[i], 0, iKeyL[i + 1], 0, 32);

      iEOp = new int[48];
      permuteKey(iKeyR[i], iEOp, iEBitSelection);

      txt.addText("Permuted R" + i + " by E-Bit Selection Table\n" + getArrayString(iEOp) + "\n");


      iXOR = new int[48];
      computeXOR(iEOp, iKeyK[i], iXOR);

      txt.addText("Calculating R" + i + " XOR (Key " + (i + 1) + "), Result:\n" + getArrayString(iXOR) + "\n");



      iSOutput = new int[32];


      for (j = 1; j < 9; j++) {
        txt.addText("Using table S" + j + " ");
        getSValue(iXOR, iSOutput, j);

      }

      txt.addText("Resulting message after S Table lookups\n" + getArrayString(iSOutput) + "\n");




      iFOutput = new int[32];

      permuteKey(iSOutput, iFOutput, iPTable);
      txt.addText("Permuting message by P Table, Result:\n" + getArrayString(iSOutput) + "\n");


      computeXOR(iKeyL[i], iFOutput, iKeyR[i + 1]);

      txt.addText("Computing L" + i + " XOR R" + i + " and storing result in R" + (i + 1) + ":\n" + getArrayString(iKeyR[i + 1]) + "\n");


    }

    //printArray(iKeyL[16]);
    //printArray(iKeyR[16]);

    iConcatenated = new int[64];
    System.arraycopy(iKeyR[16], 0, iConcatenated, 0, 32);
    System.arraycopy(iKeyL[16], 0, iConcatenated, 32, 32);

    txt.addText("Resulting conctenated encoded message:\n" + getArrayString(iConcatenated) + "\n");


    iCodedMessage = new int[64];
    permuteKey(iConcatenated, iCodedMessage, iFinalIP);
    txt.addText("Permuting message by Final Permutation Table, Result:\n" + getArrayString(iCodedMessage) + "\n");


//    System.out.println("Coded Message");
    //printArray(iCodedMessage);

/********** THIS WORKS      ****************/
/*
    //n = 1
    System.arraycopy(iKeyR[0], 0, iKeyL[1], 0, 32);
    iEOp = new int[48];
    permuteKey(iKeyR[0], iEOp, iEBitSelection);

    //printArray(iEOp);

    iXOR = new int[48];
    computeXOR(iEOp, iKeyK[0], iXOR);

    //printArray(iXOR);

    iSOutput = new int[32];
//    System.arraycopy(iXOR, 0, iSOutput, 0, 6);

    for (i = 1; i < 9; i++) {
      getSValue(iXOR, iSOutput, i);
    }
    //printArray(iSOutput);

    iFOutput = new int[32];

    permuteKey(iSOutput, iFOutput, iPTable);
    //printArray(iFOutput);

    computeXOR(iKeyL[0], iFOutput, iKeyR[1]);
    //printArray(iKeyR[1]);

    System.arraycopy(iKeyR[1], 0, iKeyL[2], 0, 32);
*/
/*******************************************/


    strReturn = "";

    for (i = 0; i < 8; i++) {
      iChar = (iCodedMessage[(i * 8) + 0] * 128) +
              (iCodedMessage[(i * 8) + 1] * 64) +
              (iCodedMessage[(i * 8) + 2] * 32) +
              (iCodedMessage[(i * 8) + 3] * 16) +
              (iCodedMessage[(i * 8) + 4] * 8) +
              (iCodedMessage[(i * 8) + 5] * 4) +
              (iCodedMessage[(i * 8) + 6] * 2) +
              (iCodedMessage[(i * 8) + 7] * 1);
      strReturn += (char) iChar;

    }


    return strReturn;

  }

  /**
   * getSValue - computes the S value for the six bit binary number
   */
  private void getSValue(int iArray[], int iOutput[], int iS) {
    int iReturn;
    int iRow, iCol;
    int iValue;
    int iOffset;

    int iSTable1[][] = { { 14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7 },
                         {  0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8 },
                         {  4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0 },
                         { 15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13 }  };
    int iSTable2[][] = { { 15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10 },
                         {  3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5 },
                         {  0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15 },
                         { 13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9 }  };
    int iSTable3[][] = { { 10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8 },
                         { 13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1 },
                         { 13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7 },
                         {  1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12 }  };
    int iSTable4[][] = { {  7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15 },
                         { 13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9 },
                         { 10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4 },
                         {  3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14 }  };
    int iSTable5[][] = { {  2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9 },
                         { 14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6 },
                         {  4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14 },
                         { 11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3 }  };
    int iSTable6[][] = { { 12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11 },
                         { 10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8 },
                         {  9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6 },
                         {  4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13 }  };
    int iSTable7[][] = { {  4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1 },
                         { 13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6 },
                         {  1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2 },
                         {  6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12 }  };
    int iSTable8[][] = { { 13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7 },
                         {  1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2 },
                         {  7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8 },
                         {  2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11 }  };



    iOffset = (iS - 1) * 6;
    iRow = (iArray[0 + iOffset] * 2) + (iArray[5 + iOffset] * 1);
    iCol = (iArray[1 + iOffset] * 8) + (iArray[2 + iOffset] * 4) + (iArray[3 + iOffset] * 2) + (iArray[4 + iOffset] * 1);

    switch(iS) {
      case 1:
        iValue = iSTable1[iRow][iCol];

        break;
      case 2:
        iValue = iSTable2[iRow][iCol];
        break;
      case 3:
        iValue = iSTable3[iRow][iCol];
        break;
      case 4:
        iValue = iSTable4[iRow][iCol];
        break;
      case 5:
        iValue = iSTable5[iRow][iCol];
        break;
      case 6:
        iValue = iSTable6[iRow][iCol];
        break;
      case 7:
        iValue = iSTable7[iRow][iCol];
        break;
      case 8:
        iValue = iSTable8[iRow][iCol];
        break;

      default:
        iValue = 0;
        break;
    }


    iOffset = (iS - 1) * 4;

    iOutput[0 + iOffset] = (iValue / 8);
    iOutput[1 + iOffset] = ((iValue % 8) / 4);
    iOutput[2 + iOffset] = (((iValue % 8) % 4) / 2);
    iOutput[3 + iOffset] = ((((iValue % 8) % 4) % 2) / 1);

    txt.addText("Result: " + iValue + "\n");


  }

  /**
   * computeXOR - computes the XOR of the two arrays, and stores the
   *              result into the third array
   */
  private void computeXOR(int a1[], int a2[], int iResult[]) {
    int i;

    for (i = 0; i < a1.length; i++) {
      if ((a1[i] + a2[i]) == 1) {
        iResult[i] = 1;
      } else {
        iResult[i] = 0;
      }

    }

  }


  /**
   * convertMessage - converts the message into a binary array
   */
  private void convertMessage(String str, int iMessage[]) {
    int i;
    int iChar;

    for (i = 0; i < 8; i++) {
      if (str.length() - 1 < i) {
        iChar = 0;
      } else {
        iChar = (int) str.charAt(i);
      }

      //System.out.println(iChar + "");

      iMessage[(i * 8) + 0] = (iChar / 128);
      iMessage[(i * 8) + 1] = ((iChar % 128) / 64);
      iMessage[(i * 8) + 2] = ((iChar % 64) / 32);
      iMessage[(i * 8) + 3] = ((iChar % 32) / 16);
      iMessage[(i * 8) + 4] = ((iChar % 16) / 8);
      iMessage[(i * 8) + 5] = ((iChar % 8) / 4);
      iMessage[(i * 8) + 6] = ((iChar % 4) / 2);
      iMessage[(i * 8) + 7] = ((iChar % 2) / 1);

    }

    //System.out.println("Message:");
    //printArray(iMessage);


  }

  /**
   * convertBinaryKey - converts the Hex string to a binary key
   */
  private void convertBinaryKey(String s, int iKey[]) {
    int i;
    int k;

    for (i = 0; i < 16; i++) {
      k = i * 4;
      switch(s.charAt(i)) {
        case '0':
          iKey[k + 0] = 0;
          iKey[k + 1] = 0;
          iKey[k + 2] = 0;
          iKey[k + 3] = 0;
          break;
        case '1':
          iKey[k + 0] = 0;
          iKey[k + 1] = 0;
          iKey[k + 2] = 0;
          iKey[k + 3] = 1;
          break;
        case '2':
          iKey[k + 0] = 0;
          iKey[k + 1] = 0;
          iKey[k + 2] = 1;
          iKey[k + 3] = 0;
          break;
        case '3':
          iKey[k + 0] = 0;
          iKey[k + 1] = 0;
          iKey[k + 2] = 1;
          iKey[k + 3] = 1;
          break;
        case '4':
          iKey[k + 0] = 0;
          iKey[k + 1] = 1;
          iKey[k + 2] = 0;
          iKey[k + 3] = 0;
          break;
        case '5':
          iKey[k + 0] = 0;
          iKey[k + 1] = 1;
          iKey[k + 2] = 0;
          iKey[k + 3] = 1;
          break;
        case '6':
          iKey[k + 0] = 0;
          iKey[k + 1] = 1;
          iKey[k + 2] = 1;
          iKey[k + 3] = 0;
          break;
        case '7':
          iKey[k + 0] = 0;
          iKey[k + 1] = 1;
          iKey[k + 2] = 1;
          iKey[k + 3] = 1;
          break;
        case '8':
          iKey[k + 0] = 1;
          iKey[k + 1] = 0;
          iKey[k + 2] = 0;
          iKey[k + 3] = 0;
          break;
        case '9':
          iKey[k + 0] = 1;
          iKey[k + 1] = 0;
          iKey[k + 2] = 0;
          iKey[k + 3] = 1;
          break;
        case 'A':
          iKey[k + 0] = 1;
          iKey[k + 1] = 0;
          iKey[k + 2] = 1;
          iKey[k + 3] = 0;
          break;
        case 'B':
          iKey[k + 0] = 1;
          iKey[k + 1] = 0;
          iKey[k + 2] = 1;
          iKey[k + 3] = 1;
          break;
        case 'C':
          iKey[k + 0] = 1;
          iKey[k + 1] = 1;
          iKey[k + 2] = 0;
          iKey[k + 3] = 0;
          break;
        case 'D':
          iKey[k + 0] = 1;
          iKey[k + 1] = 1;
          iKey[k + 2] = 0;
          iKey[k + 3] = 1;
          break;
        case 'E':
          iKey[k + 0] = 1;
          iKey[k + 1] = 1;
          iKey[k + 2] = 1;
          iKey[k + 3] = 0;
          break;
        case 'F':
          iKey[k + 0] = 1;
          iKey[k + 1] = 1;
          iKey[k + 2] = 1;
          iKey[k + 3] = 1;
          break;
        default:
          iKey[k + 0] = 0;
          iKey[k + 1] = 0;
          iKey[k + 2] = 0;
          iKey[k + 3] = 0;
          break;


      }
      

    }
  }


  /**
   * permuteKey - returns the permuted key according to the following
   *              table passed in:
   */
  private void permuteKey(int iKey[], int iPermuteKey[], int iPermuteTable[]) {
    int i;

    for (i = 0; i < iPermuteKey.length; i++) {
      iPermuteKey[i] = iKey[iPermuteTable[i] - 1];

    }                     

  }

  /**
   * shiftPermutedKeys - makes the 17 shifted keys
   *                     Left shift for each iteration:
   *                     1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
   */
  private void shiftPermutedKeys(int iPermutedKey[], int iKeyC[][], int iKeyD[][]) {
    int i;


    //split the permuted key into a left and right key of the same size
    for (i = 0; i < 28; i++) {
      iKeyC[0][i] = iPermutedKey[i];
      iKeyD[0][i] = iPermutedKey[i + 28];

    }

//    printArray(iKeyC[0]);
//    printArray(iKeyD[0]);

    shiftKeyLeft(iKeyC[0], iKeyC[1], 1);
    shiftKeyLeft(iKeyD[0], iKeyD[1], 1);

    shiftKeyLeft(iKeyC[1], iKeyC[2], 1);
    shiftKeyLeft(iKeyD[1], iKeyD[2], 1);

    shiftKeyLeft(iKeyC[2], iKeyC[3], 2);
    shiftKeyLeft(iKeyD[2], iKeyD[3], 2);

    shiftKeyLeft(iKeyC[3], iKeyC[4], 2);
    shiftKeyLeft(iKeyD[3], iKeyD[4], 2);

    shiftKeyLeft(iKeyC[4], iKeyC[5], 2);
    shiftKeyLeft(iKeyD[4], iKeyD[5], 2);

    shiftKeyLeft(iKeyC[5], iKeyC[6], 2);
    shiftKeyLeft(iKeyD[5], iKeyD[6], 2);

    shiftKeyLeft(iKeyC[6], iKeyC[7], 2);
    shiftKeyLeft(iKeyD[6], iKeyD[7], 2);

    shiftKeyLeft(iKeyC[7], iKeyC[8], 2);
    shiftKeyLeft(iKeyD[7], iKeyD[8], 2);

    shiftKeyLeft(iKeyC[8], iKeyC[9], 1);
    shiftKeyLeft(iKeyD[8], iKeyD[9], 1);

    shiftKeyLeft(iKeyC[9], iKeyC[10], 2);
    shiftKeyLeft(iKeyD[9], iKeyD[10], 2);

    shiftKeyLeft(iKeyC[10], iKeyC[11], 2);
    shiftKeyLeft(iKeyD[10], iKeyD[11], 2);

    shiftKeyLeft(iKeyC[11], iKeyC[12], 2);
    shiftKeyLeft(iKeyD[11], iKeyD[12], 2);

    shiftKeyLeft(iKeyC[12], iKeyC[13], 2);
    shiftKeyLeft(iKeyD[12], iKeyD[13], 2);

    shiftKeyLeft(iKeyC[13], iKeyC[14], 2);
    shiftKeyLeft(iKeyD[13], iKeyD[14], 2);

    shiftKeyLeft(iKeyC[14], iKeyC[15], 2);
    shiftKeyLeft(iKeyD[14], iKeyD[15], 2);

    shiftKeyLeft(iKeyC[15], iKeyC[16], 1);
    shiftKeyLeft(iKeyD[15], iKeyD[16], 1);

/*
    printArray(iKeyC[3]);
    printArray(iKeyD[3]);
    printArray(iKeyC[16]);
    printArray(iKeyD[16]);
*/



  }

  /**
   * shiftKeyLeft - copies the shifted key into the destination array
   */
  private void shiftKeyLeft(int iKeySource[], int iKeyDest[], int iShift) {
    int i;
    int iDest;

    for(i = 0; i < iKeyDest.length; i++) {
      if ((i + iShift) > (iKeySource.length - 1)) {
        iDest = i + iShift - iKeySource.length;
      } else {
        iDest = i + iShift;
      }

      iKeyDest[i] = iKeySource[iDest];

    }


  }
  

  /**
   * printArray -prints the contents of the array to the console
   */
  private void printArray(int iArray[]) {
    int i;

    for (i = 0; i < iArray.length; i++) {
      System.out.print(iArray[i] + "");

    }

    System.out.print("\n");

  }


  /**
   * getMatrixString - returns a string representing the array in matrix format
   */
  private String getMatrixString(int iArray[], int iCols) {
    String str;
    int i, j;

    str = "";

    j = 0;
    for (i = 0; i < iArray.length; i++) {
      if (iArray[i] < 10) {
        str += " ";
      }

      str += iArray[i] + " ";

      j++;

      if (j >= iCols) {
        str += "\n";
        j = 0;
      }



    }

    System.out.print("\n");



    return str;
  }


  /**
   * getArrayString - returns a string representing the array
   */
  private String getArrayString(int iArray[]) {
    String str;
    int i;

    str = "";

    for (i = 0; i < iArray.length; i++) {

      str += iArray[i];

    }
    return str;
  }

  /**
   * abstract class
   */
  public Panel getOptionsPanel() {
    Panel p;
    Panel pnlTemp;
    Panel pnlTemp2;

    p = new Panel();
    pnlTemp2 = new Panel(new BorderLayout());

    pnlTemp2.add(new Label("64 Bit Key:"), BorderLayout.NORTH);


    pnlTemp = new Panel(new FlowLayout(FlowLayout.LEFT));
    choiceKey01 = new Choice();
    createHexChoices(choiceKey01);
    pnlTemp.add(choiceKey01);
    choiceKey02 = new Choice();
    createHexChoices(choiceKey02);
    pnlTemp.add(choiceKey02);
    choiceKey03 = new Choice();
    createHexChoices(choiceKey03);
    pnlTemp.add(choiceKey03);
    choiceKey04 = new Choice();
    createHexChoices(choiceKey04);
    pnlTemp.add(choiceKey04);
    choiceKey05 = new Choice();
    createHexChoices(choiceKey05);
    pnlTemp.add(choiceKey05);
    choiceKey06 = new Choice();
    createHexChoices(choiceKey06);
    pnlTemp.add(choiceKey06);
    choiceKey07 = new Choice();
    createHexChoices(choiceKey07);
    pnlTemp.add(choiceKey07);
    choiceKey08 = new Choice();
    createHexChoices(choiceKey08);
    pnlTemp.add(choiceKey08);


    pnlTemp2.add(pnlTemp, BorderLayout.CENTER);

    butRandom = new Button(BUTRANDOM_LABEL);
    butRandom.addActionListener(this);
    pnlTemp = new Panel();
    pnlTemp.add(butRandom);
    pnlTemp2.add(pnlTemp, BorderLayout.SOUTH);




    p.setBackground(Color.yellow);

    p.add(pnlTemp2);


    return p;

  }

  /**
   * actionPerformed
   */
  public void actionPerformed(ActionEvent e) {
    String strCommand;

    strCommand = e.getActionCommand();

    if (strCommand.equals(BUTRANDOM_LABEL)) {
      setRandomKey();
    }

  }

  /**
   * setRandomKey - creates a random key
   */
  private void setRandomKey() {
    choiceKey01.select((int) (Math.random() * choiceKey01.getItemCount()));
    choiceKey02.select((int) (Math.random() * choiceKey02.getItemCount()));
    choiceKey03.select((int) (Math.random() * choiceKey03.getItemCount()));
    choiceKey04.select((int) (Math.random() * choiceKey04.getItemCount()));
    choiceKey05.select((int) (Math.random() * choiceKey05.getItemCount()));
    choiceKey06.select((int) (Math.random() * choiceKey06.getItemCount()));
    choiceKey07.select((int) (Math.random() * choiceKey07.getItemCount()));
    choiceKey08.select((int) (Math.random() * choiceKey08.getItemCount()));


  }


  /**
   * createHexChoices - creates a choice list of hex choices
   */
  private void createHexChoices(Choice c) {
    c.add("00");
    c.add("01");
    c.add("02");
    c.add("03");
    c.add("04");
    c.add("05");
    c.add("06");
    c.add("07");
    c.add("08");
    c.add("09");
    c.add("0A");
    c.add("0B");
    c.add("0C");
    c.add("0D");
    c.add("0E");
    c.add("0F");
    c.add("10");
    c.add("11");
    c.add("12");
    c.add("13");
    c.add("14");
    c.add("15");
    c.add("16");
    c.add("17");
    c.add("18");
    c.add("19");
    c.add("1A");
    c.add("1B");
    c.add("1C");
    c.add("1D");
    c.add("1E");
    c.add("1F");
    c.add("20");
    c.add("21");
    c.add("22");
    c.add("23");
    c.add("24");
    c.add("25");
    c.add("26");
    c.add("27");
    c.add("28");
    c.add("29");
    c.add("2A");
    c.add("2B");
    c.add("2C");
    c.add("2D");
    c.add("2E");
    c.add("2F");
    c.add("30");
    c.add("31");
    c.add("32");
    c.add("33");
    c.add("34");
    c.add("35");
    c.add("36");
    c.add("37");
    c.add("38");
    c.add("39");
    c.add("3A");
    c.add("3B");
    c.add("3C");
    c.add("3D");
    c.add("3E");
    c.add("3F");
    c.add("40");
    c.add("41");
    c.add("42");
    c.add("43");
    c.add("44");
    c.add("45");
    c.add("46");
    c.add("47");
    c.add("48");
    c.add("49");
    c.add("4A");
    c.add("4B");
    c.add("4C");
    c.add("4D");
    c.add("4E");
    c.add("4F");
    c.add("50");
    c.add("51");
    c.add("52");
    c.add("53");
    c.add("54");
    c.add("55");
    c.add("56");
    c.add("57");
    c.add("58");
    c.add("59");
    c.add("5A");
    c.add("5B");
    c.add("5C");
    c.add("5D");
    c.add("5E");
    c.add("5F");
    c.add("60");
    c.add("61");
    c.add("62");
    c.add("63");
    c.add("64");
    c.add("65");
    c.add("66");
    c.add("67");
    c.add("68");
    c.add("69");
    c.add("6A");
    c.add("6B");
    c.add("6C");
    c.add("6D");
    c.add("6E");
    c.add("6F");
    c.add("70");
    c.add("71");
    c.add("72");
    c.add("73");
    c.add("74");
    c.add("75");
    c.add("76");
    c.add("77");
    c.add("78");
    c.add("79");
    c.add("7A");
    c.add("7B");
    c.add("7C");
    c.add("7D");
    c.add("7E");
    c.add("7F");
    c.add("80");
    c.add("81");
    c.add("82");
    c.add("83");
    c.add("84");
    c.add("85");
    c.add("86");
    c.add("87");
    c.add("88");
    c.add("89");
    c.add("8A");
    c.add("8B");
    c.add("8C");
    c.add("8D");
    c.add("8E");
    c.add("8F");
    c.add("90");
    c.add("91");
    c.add("92");
    c.add("93");
    c.add("94");
    c.add("95");
    c.add("96");
    c.add("97");
    c.add("98");
    c.add("99");
    c.add("9A");
    c.add("9B");
    c.add("9C");
    c.add("9D");
    c.add("9E");
    c.add("9F");
    c.add("A0");
    c.add("A1");
    c.add("A2");
    c.add("A3");
    c.add("A4");
    c.add("A5");
    c.add("A6");
    c.add("A7");
    c.add("A8");
    c.add("A9");
    c.add("AA");
    c.add("AB");
    c.add("AC");
    c.add("AD");
    c.add("AE");
    c.add("AF");
    c.add("B0");
    c.add("B1");
    c.add("B2");
    c.add("B3");
    c.add("B4");
    c.add("B5");
    c.add("B6");
    c.add("B7");
    c.add("B8");
    c.add("B9");
    c.add("BA");
    c.add("BB");
    c.add("BC");
    c.add("BD");
    c.add("BE");
    c.add("BF");
    c.add("C0");
    c.add("C1");
    c.add("C2");
    c.add("C3");
    c.add("C4");
    c.add("C5");
    c.add("C6");
    c.add("C7");
    c.add("C8");
    c.add("C9");
    c.add("CA");
    c.add("CB");
    c.add("CC");
    c.add("CD");
    c.add("CE");
    c.add("CF");
    c.add("D0");
    c.add("D1");
    c.add("D2");
    c.add("D3");
    c.add("D4");
    c.add("D5");
    c.add("D6");
    c.add("D7");
    c.add("D8");
    c.add("D9");
    c.add("DA");
    c.add("DB");
    c.add("DC");
    c.add("DD");
    c.add("DE");
    c.add("DF");
    c.add("E0");
    c.add("E1");
    c.add("E2");
    c.add("E3");
    c.add("E4");
    c.add("E5");
    c.add("E6");
    c.add("E7");
    c.add("E8");
    c.add("E9");
    c.add("EA");
    c.add("EB");
    c.add("EC");
    c.add("ED");
    c.add("EE");
    c.add("EF");
    c.add("F0");
    c.add("F1");
    c.add("F2");
    c.add("F3");
    c.add("F4");
    c.add("F5");
    c.add("F6");
    c.add("F7");
    c.add("F8");
    c.add("F9");
    c.add("FA");
    c.add("FB");
    c.add("FC");
    c.add("FD");
    c.add("FE");
    c.add("FF");

  }


}
