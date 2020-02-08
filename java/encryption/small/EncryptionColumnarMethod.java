import java.awt.*;
import java.awt.event.*;

class EncryptionColumnarMethod extends EncryptionMethod  {

  /** CONSTANTS **/
  private TextField txtColumns;


  /** GLOBAL VARIABLES **/

  public EncryptionColumnarMethod() {

  }

  /**
   * abstract method
   */
  public String encrypt(String s,  TutorialDisplay txt) {
    String strCipherText;
    int iMatrix[][];
    int iRows, iCols;
    int i, j;





    //Actual Encryption
    strCipherText = "";

    try {
      iCols = (new Integer(txtColumns.getText())).intValue();
      iRows = (s.length() / iCols) + 1;

      if (iCols > 0) {



        //for the tutorial display
        j = 0;
        txt.addText("\n===============\nColumnar Transposition with " + iCols + " columns\n\n");

        for (i = 0; i < s.length(); i++) {
          j++;

          txt.addText(s.charAt(i) + " ");

          if (j >= iCols) {
            j = 0;
            txt.addText("\n");
          }


        }



        iMatrix = new int[iRows][iCols];

        //initalize matrix
        for (i = 0; i < iRows; i++) {
          for (j = 0; j < iCols; j++) {
            iMatrix[i][j] = -1;
          }
        }

        //copy string into matrix
        for (i = 0; i < iRows; i++) {
          for (j = 0; j < iCols; j++) {
            if (s.length() > ((i * iCols) + j)) {
              iMatrix[i][j] = (int) s.charAt(((i * iCols) + j));
            }
          }
        }

        for (j = 0; j < iCols; j++) {
          for (i = 0; i < iRows; i++) {
            if (iMatrix[i][j] > -1) {
              strCipherText += (char) iMatrix[i][j];

            }
          }
        }
      } else {
        strCipherText = "Number of Columns must be greater than zero";
      }



    } catch (NumberFormatException e) {
      strCipherText = "Number of Columns not Valid";
    }


    return strCipherText;
  }




  /**
   * abstract method
   */
  public Panel getOptionsPanel() {
    Panel p;
    Panel pnlTemp;

    p = new Panel(new FlowLayout(FlowLayout.LEFT));


    p.add(new Label("Number of columns to use: "));
    txtColumns = new TextField(2);
    txtColumns.setBackground(Color.white);
    txtColumns.setText("1");
    p.add(txtColumns);




    p.setBackground(new Color(192, 0, 0));




    return p;

  }


}
