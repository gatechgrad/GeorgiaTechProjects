import java.util.Vector;
import java.util.Random;

public class MindSweep {

  Vector vectRows;
  BoardView bv;



  public MindSweep() {
    createBoard(9, 9);
    generateBombs(10);

/*
    flagCell(3, 0);
    flagCell(3, 1);
    flagCell(3, 2);
    flagCell(3, 3);
    flagCell(3, 4);

    selectCell(2, 2);
*/

    printBoard();

    bv = new BoardView(this);


  }

  public void createBoard(int iRows, int iCols) {
    int i;
    int j;
    Vector vectTemp;
    Cell cellTemp;


    vectRows = new Vector();
    for (i = 0; i < iRows; i++) {
      vectTemp = new Vector();

      for (j = 0; j < iCols; j++) {
        cellTemp = new Cell();
        vectTemp.add(cellTemp);
        

      }
      vectRows.add(vectTemp);
    }
  }

  public void generateBombs(int iBombs) {
    Random rand;
    int iRand;

    int i, j;

    int iRows;
    int iCols;

    iRows = getRows();
    iCols = getCols();

    boolean hasBomb;

    rand =  new Random();

    for (i = 0; i < iBombs; i++) {

      hasBomb = true;
      j = 0;
      iRand = -1;

      //find a random empty spot
      while ((hasBomb) && (j < iRows * iCols)) {
        iRand = rand.nextInt(iRows * iCols);
        hasBomb = getCell((iRand / iCols), (iRand % iCols)).getIsBomb();
        j++;
      }

      getCell((iRand / iCols), (iRand % iCols)).setBomb();
      


    }
    generateNumbers();

  }

  public void generateNumbers() {
    int i, j;
    int iCount;

    for (i = 0; i < getRows(); i++) {
      for (j = 0; j < getCols(); j++) {
        iCount = 0;

        if (j > 0) {
          if (getCell(i, j - 1).getIsBomb()) {
            iCount++;
          }
        }

        if ((j > 0) && (i > 0)) {
          if (getCell(i - 1, j - 1).getIsBomb()) {
            iCount++;
          }
        }

        if ((i > 0)) {
          if (getCell(i - 1, j).getIsBomb()) {
            iCount++;
          }
        }

        if ((j < getCols() - 1) && (i > 0)) {
          if (getCell(i - 1, j + 1).getIsBomb()) {
            iCount++;
          }
        }

        if (j < getCols() - 1) {
          if (getCell(i, j + 1).getIsBomb()) {
            iCount++;
          }
        }

        if ((j < getCols() - 1) && (i < getRows() - 1)) {
          if (getCell(i + 1, j + 1).getIsBomb()) {
            iCount++;
          }
        }

        if (i < (getRows() - 1)) {
          if (getCell(i + 1, j).getIsBomb()) {
            iCount++;
          }
        }

        if ((j > 0) &&  (i < (getRows() - 1))) {
          if (getCell(i + 1, j - 1).getIsBomb()) {
            iCount++;
          }
        }


        getCell(i, j).setNumber(iCount);



      }
    }
  }

  public void selectCell(int iRow, int iCol) {
    if (getCell(iRow, iCol).getIsBomb()) {
      youLose();
    } else {
      if (!getCell(iRow, iCol).getIsFlagged()) {
        unmarkCell(iRow, iCol);
      }
    }

  }

  private void unmarkCell(int iRow, int iCol) {
   if (!getCell(iRow, iCol).getIsFlagged()) {
    getCell(iRow, iCol).setUnCovered();

    if (getCell(iRow, iCol).getNumber() == 0) {

      if ((iRow > 0) && (iCol > 0)) {
        if (!getCell(iRow - 1, iCol - 1).getUnCovered()) {
          unmarkCell(iRow - 1, iCol - 1);
        }
      }

      if ((iRow > 0)) {
        if (!getCell(iRow - 1, iCol).getUnCovered()) {
          unmarkCell(iRow - 1, iCol);
        }
      }

      if ((iRow > 0) && (iCol < getCols() - 1)) {
        if (!getCell(iRow - 1, iCol + 1).getUnCovered()) {
          unmarkCell(iRow - 1, iCol + 1);
        }
      }


      if ( (iRow < getRows() - 1) && (iCol < getCols() - 1)) {
        if (!getCell(iRow + 1, iCol + 1).getUnCovered()) {
          unmarkCell(iRow + 1, iCol + 1);
        }
      }

      if (iRow < getRows() - 1) {
        if (!getCell(iRow + 1, iCol).getUnCovered()) {
          unmarkCell(iRow + 1, iCol);
        }
      }

      if ((iRow < getRows() - 1) && (iCol > 0)) { 
        if (!getCell(iRow + 1, iCol - 1).getUnCovered()) {
          unmarkCell(iRow + 1, iCol - 1);
        }
      }

      if (iCol > 0) {
        if (!getCell(iRow, iCol - 1).getUnCovered()) {
          unmarkCell(iRow, iCol - 1);
        }
      }

      if (iCol < getCols() - 1) {
        if (!getCell(iRow, iCol + 1).getUnCovered()) {
          unmarkCell(iRow, iCol + 1);
        }
      }

    }
    checkWin();
   }
  }

  public void flagCell(int iRow, int iCol) {
    if (getCell(iRow, iCol).getIsFlagged()) {
      getCell(iRow, iCol).setUnFlagged();
    } else {
      getCell(iRow, iCol).setFlagged();
      checkWin();
    }

  }



  public void youLose() {
    System.out.println("YOU LOSE");
    bv.showLose();

  }

  public void checkWin() {
    int i, j;
    boolean hasWon;

    hasWon = true;

    for (i = 0; i < getRows(); i++) {
      for (j = 0; j < getCols(); j++) {
        if (! (getCell(i, j).getUnCovered() || getCell(i, j).getIsBomb())) {
          hasWon = false;

        }

      }
    }

    if (hasWon) {
      youWin();
    }

  }

  public void youWin() {
    System.out.println("YOU WIN");
    bv.showWin();
    

  }



  public Cell getCell(int iRow, int iCol) {
    Cell cellReturn;

     cellReturn = (Cell) ((Vector) vectRows.elementAt(iRow)).elementAt(iCol);
     return cellReturn;

  }

  public int getRows() {
    int iReturn;

    iReturn = vectRows.size();

    return iReturn;
  }

  public int getCols() {
    int iReturn;

    iReturn = ((Vector) vectRows.elementAt(0)).size();

    return iReturn;

  }

  public void printBoard() {
    int i, j;
    Vector vectTemp;
    Cell cellTemp;

    for (i = 0; i < vectRows.size(); i++) {
      vectTemp = (Vector) vectRows.elementAt(i);
      for (j = 0; j < vectTemp.size(); j++) {
        cellTemp = (Cell) vectTemp.elementAt(j);

        if (cellTemp.getIsBomb()) {
          System.out.print("B ");
        } else {
          System.out.print(". ");
        }

      }
      System.out.println("");
    }

    System.out.println("-------------------------");

    for (i = 0; i < vectRows.size(); i++) {
      vectTemp = (Vector) vectRows.elementAt(i);
      for (j = 0; j < vectTemp.size(); j++) {
        cellTemp = (Cell) vectTemp.elementAt(j);

        if (cellTemp.getIsBomb()) {
          System.out.print("B ");
        } else {
          System.out.print(cellTemp.getNumber() + " ");
        }

      }
      System.out.println("");
    }

    System.out.println("-------------------------");

    for (i = 0; i < vectRows.size(); i++) {
      vectTemp = (Vector) vectRows.elementAt(i);
      for (j = 0; j < vectTemp.size(); j++) {
        cellTemp = (Cell) vectTemp.elementAt(j);

        if (cellTemp.getUnCovered()) {
          System.out.print("U ");
        } else if (cellTemp.getIsFlagged()) {
          System.out.print("F ");
        } else {
          System.out.print(". ");
        }

      }
      System.out.println("");
    }

    System.out.println("-------------------------");

  }

  public static void main(String args[]) {
    new MindSweep();

  }

}
