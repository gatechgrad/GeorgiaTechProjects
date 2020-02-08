/*
 * Tn3270cursor.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 *
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 */

package tn3270;

class Tn3270cursor {

    private Tn3270	tn;

    private int		cursorAddr;
    private boolean	cursorPaint;
    private int		x, y, color;
    private byte	c;

    /*
     * Constructor: Tn3270cursor.
     */
    Tn3270cursor(Tn3270 tn) {

	this.tn = tn;

	cursorAddr = x = y = 0;
	c = ' ';
	cursorPaint = false;
    }

    /*
     * backwardTab:
     */
    void backwardTab() {
	int		i;

	if (tn.displayIsLocked())
		tn.displayUnlock();

	if ((i = tn.displayFindPrevUnprotectedAfterFA(cursorAddr)) == -1)
		i = tn.displayFindPrevUnprotectedAfterFA(tn.displaySize - 1);

	cursorAddr = (i == -1) ? 0: i;
	erase();
	paint();
    }

    /*
     * down:
     */
    void down() {

	if (tn.displayIsLocked())
		return;

	if ((cursorAddr += tn.displayCols) >= tn.displaySize)
		cursorAddr -= tn.displaySize;

	erase();
	paint();
    }

    /*
     * erase:
     */
    void erase() {

	if (cursorPaint == false)
		return;

        cursorPaint = false;
	tn.paintCursor(x, y, c, color, false);
    }

    /*
     * forwardTab:
     */
    void forwardTab() {
	int		i;

	if (tn.displayIsLocked())
		tn.displayUnlock();

	if ((i = tn.displayFindNextUnprotectedAfterFA(cursorAddr)) == -1)
		i = tn.displayFindNextUnprotectedAfterFA(0);

	cursorAddr = (i == -1) ? 0 : i;

	erase();
	paint();
    }

    /*
     * getPosition:
     */
    final int getPosition() {

	return(cursorAddr);
    }

    /*
     * home:
     */
    void home() {
	int		i;

	if (tn.displayIsLocked())
		tn.displayUnlock();

	i = tn.displayFindNextUnprotectedAfterFA(0);
	cursorAddr = (i == -1) ? 0 : i;

	erase();
	paint();
    }

    /*
     * left:
     */
    void left() {

	if (tn.displayIsLocked())
		return;

	if (--cursorAddr < 0)
		cursorAddr = tn.displaySize - 1;

	erase();
	paint();
    }

    /*
     * move:
     */
    final void move(int addr) {

	cursorAddr = addr;
    }

    /*
     * moveToPosition:
     */
    void moveToPosition(int addr) {

	if (tn.displayIsLocked())
		tn.displayUnlock();

	if (addr < 0 || addr >= tn.displaySize)
		return;

	move(addr);
	erase();
	paint();
    }

    /*
     * newLine:
     */
    void newLine() {
	int		i;

	if (tn.displayIsLocked())
		tn.displayUnlock();

	i = cursorAddr - (cursorAddr % tn.displayCols);		// 1st col
	if ((i += tn.displayCols) == tn.displaySize)		// down
		i = 0;

	if ((i = tn.displayFindNextUnprotectedAfterFA(i)) == -1)
		i = tn.displayFindNextUnprotectedAfterFA(0);

	cursorAddr = (i == -1) ? 0 : i;

	erase();
	paint();
    }

    /*
     * paint:
     */
    void paint() {

	if (cursorPaint)
		return;

        cursorPaint = true;
	x = cursorAddr % tn.displayCols;
	y = cursorAddr / tn.displayCols;
	switch(c = (byte)tn.displayData[cursorAddr]) {
	case 0:
	case Tn3270.ASCII_FA:
		c = ' ';
	}

	color = tn.displayGetNoColor(cursorAddr);
	tn.paintCursor(x, y, c, color, true);
    }

    /*
     * repaint:
     */
    void repaint() {

	cursorPaint = false;
	paint();
    }

    /*
     * right:
     */
    void right() {

	if (tn.displayIsLocked())
		return;

	if (++cursorAddr == tn.displaySize)
		cursorAddr = 0;

	erase();
	paint();
    }

    /*
     * up:
     */
    void up() {

	if (tn.displayIsLocked())
		return;

	if ((cursorAddr -= tn.displayCols) < 0)
		cursorAddr += tn.displaySize;

	erase();
	paint();
    }
}
