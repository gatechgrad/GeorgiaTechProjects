import java.io.Serializable;
import java.awt.*;

/**
 * contains all of the data for a game map
 * @author Levi D. Smith
 * @version 1.00
 */
public class Map implements Serializable {

    //INSTANCE VARIABLES
    private Cell[][] theMap;
    private int iRows, iCols;
    private Dimension dimCellSize;

    private String strName;


    /**
     * default constructor
     */
    public Map(int iRows, int iCols, Dimension dimCellSize) {
        int i, j;

        theMap = new Cell[iRows][iCols];
        for (i = 0; i < iRows; i++) {
            for (j = 0; j < iCols; j++) {
                theMap[i][j] = new Cell();
            }
        }

        System.out.println("Finished initalizing map");


        this.iRows = iRows;
        this.iCols = iCols;
        this.dimCellSize = dimCellSize;

    }


    public void setCell(Cell theCell, int iRow, int iCol) {
        if (theMap != null) {
            theMap[iRow][iCol] = theCell;
        }
    }

    public void setName(String strName) {
        this.strName = strName;
    }



    public int getRows() { return iRows; }
    public int getCols() { return iCols; }
    public Dimension getCellSize() { return dimCellSize; }
    public String getName() { return strName; }

    /**
     * returns the specified cell
     * @param iRow the row of the cell to return
     * @param iCol the column of the cell to return
     * @return the cell to be returned
     */
    public Cell getCell(int iRow, int iCol) {
        Cell cellToReturn;

        cellToReturn = null;
        if (theMap != null) {
            cellToReturn = theMap[iRow][iCol];
        }
        return cellToReturn;
    }

    public int getTotalWidth() {
        return (iCols * dimCellSize.width);
    }

    public int getTotalHeight() {
        return (iRows * dimCellSize.height);
    }

    /**
     * a string representing the map
     */
    public String toString() {
        return strName;
    }

    
}
