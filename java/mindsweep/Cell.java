public class Cell {
  boolean isBomb;
  int iNumber;
  boolean unCovered;
  boolean isFlagged;

  public Cell() {
    isBomb = false;
    iNumber = 0;
    unCovered = false;
    isFlagged = false;
  }

  public boolean getIsBomb() {
    return isBomb;
  }

  public int getNumber() {
    return iNumber;
  }

  public boolean getUnCovered() {
    return unCovered;
  }

  public boolean getIsFlagged() {
    return isFlagged;
  }



  public void setBomb() {
    isBomb = true;
  }

  public void unsetBomb() {
    isBomb = false;
  }

  public void setNumber(int i) {
    iNumber = i;
  }

  public void setCovered() {
    unCovered = false;
  }

  public void setUnCovered() {
    unCovered = true;
  }

  public void setFlagged() {
    isFlagged = true;
  }

  public void setUnFlagged() {
    isFlagged = false;
  }



}
