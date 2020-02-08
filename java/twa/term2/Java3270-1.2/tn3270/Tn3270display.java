/*
 * Tn3270display.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 *
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 */

package tn3270;

class Tn3270display {

    private Tn3270	tn;

    // Display Attribute
    final static short ATTR_NUMERIC     = 0x20;
    final static short ATTR_PROTECTED   = 0x10;
    final static short ATTR_UPDATE      = 0x08;
    final static short ATTR_MODIFIED    = 0x04;
    final static short ATTR_COLOR       = 0x03;
    
    private int		rows;
    private int		cols;
    private int		size;

    short		data[];
    short		attr[];

    private boolean	formatted;
    private boolean	kybdlock;
    private boolean	insert;

    /*
     * Constructor: Tn3270display.
     */
    Tn3270display(Tn3270 tn, int rows, int cols) {

	this.tn = tn;

	this.rows = rows;
	this.cols = cols;
	size = rows * cols;
	data = new short[size];
	attr = new short[size];

	formatted = false;
	kybdlock = true;
	insert = false;
    }

    /*
     * addChar:
     */
    private void addChar(int addr, int c) {

	data[addr] = (short)c;
	attr[addr] |= ATTR_UPDATE | ATTR_MODIFIED;
    }

    /*
     * clear:
     */
    void clear() {
	int		i;

	formatted = false;

	for(i = 0; i < size; ++i) {
		data[i] = 0;
		attr[i] = ATTR_UPDATE;
	}
    }

    /*
     * delete:
     */
    void delete() {
	int		i, end, eol;

	if (isLocked())
		return;

	i = tn.cursorGetPosition();
	if (data[i] == Tn3270.ASCII_FA || (attr[i] & ATTR_PROTECTED) != 0) {
		lock(Tn3270.STATUS_PROTECTED);
		return;
	}

	eol = i - (i % cols) + cols - 1;		// end of line

	if (formatted) {
		for(end = i + 1; end < size; ++end)
			if (data[end] == Tn3270.ASCII_FA) {
				--end;
				break;
			}

		if (eol < end)
			end = eol;
	} else
		end = eol;

	moveChar(i + 1, i, end - i);
	addChar(end, 0);

	setFieldModified(i);

	update();
    }

    /*
     * deleteAllInput:
     */
    void deleteAllInput() {
	int		i;

	if (isLocked())
		return;

	tn.streamEraseUnprotectedCharacter(0, size - 1);
	tn.streamResetModifiedDataTab();
	if ((i = findNextUnprotectedAfterFA(0)) == -1)
		i = 0;
	tn.cursorMove(i);

	update();
    }

    /*
     * deleteEndOfField:
     */
    void deleteEndOfField() {
	int		addr, end;

	if (isLocked())
		return;

	addr = tn.cursorGetPosition();
	if ((end = findNextFA(addr) - 1) < 0)
		end = size - 1;

	tn.streamEraseUnprotectedCharacter(addr, end);

	update();
    }

    /*
     * fieldMark:
     */
    void fieldMark() {

	insertChar(Tn3270.ASCII_FM);
    }

    /*
     * getNoColor:
     */
    int getNoColor(int addr) {
	int		no;

	if ((no = attr[addr] & ATTR_COLOR) == 3)
		return(no);

	if ((attr[addr] & ATTR_MODIFIED) != 0)
		return(1);

	return(no);
    }

    /*
     * insertChar:
     *     Handle an ordinary displayable character key.
     */
    void insertChar(int c) {
	short		ebcdic;
	int		i, end, next;

	if (isLocked())
		return;

	if (c < ' ' || c > 255)
		return;

	i = tn.cursorGetPosition();
	if (data[i] == Tn3270.ASCII_FA || (attr[i] & ATTR_PROTECTED) != 0) {
		lock(Tn3270.STATUS_PROTECTED);
		return;
	}

	if ((attr[i] & ATTR_NUMERIC) != 0)
		if ((c < '0' || c > '9') && c != '-' && c != '.') {
			lock(Tn3270.STATUS_NUMERIC);
			return;
		}

	if ((ebcdic = Tn3270.asc2ebc[c]) == 0)
		return;

	/*
	 * Insert char in display.
	 */
	if (!insert || data[i] == 0)
		addChar(i, c);
	else {
		// find next null or next fa
		end = i;
		for(;;) {
			if (++end == size)
				end = 0;

			if (data[end] == Tn3270.ASCII_FA) {
				lock(Tn3270.STATUS_FULL);
				return;
			}

			if (data[end] == 0)
				break;

			if (end == i)
				break;
		}

		if (end > i)
			moveChar(i, i + 1, end - i);
		else {
			moveChar(0, 1, end);
			addChar(0, data[size - 1]);
			moveChar(i, i + 1, size - i - 1);
		}
		addChar(i, c);
	}

	/*
	 * Move to the next character.
	 */
	if ((next = i + 1) == size)
		next = 0;

	if (data[next] == Tn3270.ASCII_FA) {
		if ((attr[next] & ATTR_NUMERIC) != 0 &&
		    (attr[next] & ATTR_PROTECTED) != 0)
			for(;;) {
				if (++next == size)
					next = 0;

				if (data[next] == Tn3270.ASCII_FA &&
				    (attr[next] & ATTR_PROTECTED) == 0)
					break;
			}

		if (++next == size)
			next = 0;
	}
	tn.cursorMove(next);

	setFieldModified(i);

	update();
    }
 
    /*
     * insertMode:
     */
    void insertMode(boolean on) {

	if (isLocked())
		return;

	insert = on;

	tn.paintStatus((on) ? Tn3270.STATUS_INSERT : null);
    }

    void insertMode() {

	insertMode(!insert);
    }

    /*
     * insertString:
     */
    void insertString(String str) {
	byte		value[] = new byte[256];
	int		i, n;

	n = str.length();
	str.getBytes(0, n, value, 0);

	for(i = 0; i < n; ++i)
		insertChar(value[i]);
    }

    /*
     * isLocked:
     */
    final boolean isLocked() {

	return(kybdlock);
    }

    /*
     * lock:
     */
    void lock(String msg) {

	kybdlock = true;
	tn.paintStatus(msg);
    }

    /*
     * moveChar:
     */
    private void moveChar(int src, int dst, int n) {
	int		i;

	if (dst < src) {
		for(i = 0; i < n; ++i) {
			data[dst] = data[src++];
			attr[dst++] |= ATTR_UPDATE;
		}
		return;
	}

	dst += n;
	src += n;
	for(i = 0; i < n; ++i) {
		data[--dst] = data[--src];
		attr[dst] |= ATTR_UPDATE;
	}
    }

    /*
     * repaint:
     */
    void repaint() {
	int		i;

	for(i = 0; i < size; ++i)
		attr[i] |= ATTR_UPDATE;

	update();
    }

    /*
     * rubout:
     */
    void rubout() {
	int		i;

	if (isLocked())
		return;

	if (!formatted) {
		tn.cursorLeft();
		delete();
		return;
	}

	if ((i = tn.cursorGetPosition() - 1) < 0)
		i = size - 1;

	if (data[i] != Tn3270.ASCII_FA && (attr[i] & ATTR_PROTECTED) == 0) {
		tn.cursorLeft();
		delete();
		return;
	}

	// Find previous unprotected character after a field attribute
	if ((i = findPrevUnprotectedAfterFA(i)) == -1)
                i = findPrevUnprotectedAfterFA(size - 1);

	if (i == -1)
		return;

	// find next fa
	if ((i = findNextFA(i)) == -1)
		i = findNextFA(0);

	// skip backward null character
	for(;;) {
		if (--i < 0)
			i = size - 1;

		if (data[i] != 0)
			break;
	}

	// move one char right
	if (++i == size)
		i = 0;

	tn.eraseCursor();
	tn.cursorMove(i);
	tn.cursorPaint();
    }

    /*
     * setFieldModified:
     */
    private void setFieldModified(int addr) {

	if (!formatted)
		return;

	while(data[addr] != Tn3270.ASCII_FA)
		if (--addr < 0)
			addr = size - 1;

	if ((attr[addr] & ATTR_MODIFIED) == 0)
		setBitModifiedInFA(addr, true);
    }

    /*
     * unlock:
     */
    void unlock() {

	kybdlock = false;

	tn.paintStatus((insert) ? Tn3270.STATUS_INSERT : null);
    }

    /*
     * update:
     */
    void update() {
	byte		buf[] = new byte[cols];
	int		x, y, color, len;
	int		addr, endOfLine, start;
	int		i, j, mask;

	tn.eraseCursor();

	mask = ATTR_COLOR + ATTR_MODIFIED;
	addr = 0;
	for(y = 0; y < rows; ++y) {
		endOfLine = addr + cols;
		x = 0;

		for(;;) {
			while(addr < endOfLine) {
				if ((attr[addr] & ATTR_UPDATE) != 0)
					break;
				++x; ++addr;
			}

			if (addr == endOfLine)
				break;

			color = attr[addr] & mask;
			start = addr;

			attr[addr++] &= ~ATTR_UPDATE;
			while(addr < endOfLine) {
				if ((attr[addr] & ATTR_UPDATE) == 0)
					break;

				if ((attr[addr] & mask) != color)
					break;

				attr[addr++] &= ~ATTR_UPDATE;
			}

			len = addr - start;
			for(i = 0, j = start; i < len; ++i, ++j)
				switch(data[j]) {
				case 0:
				case Tn3270.ASCII_FA:
					buf[i] = ' ';
					break;
				default:
					buf[i] = (byte)data[j];
				}

			tn.paintString(x, y, getNoColor(start), buf, len);

			if (addr == endOfLine)
				break;

			x += len;
		}
	}
	tn.cursorPaint();
    }

    /* ----------------------------------------------------------------	*
     * FA: Field Attribute						*
     * ----------------------------------------------------------------	*/

    /*
     * eraseFA:
     */
    void eraseFA(int addr) {
	short		fa;
	int		i, prevFA;

	if (data[addr] != Tn3270.ASCII_FA)
		return;

	if ((i = addr - 1) < 0)
		i = size - 1;

	if ((prevFA = findPrevFA(i)) == -1)
		prevFA = findPrevFA(size - 1);

	if (prevFA == addr) {			// -> unformatted display
		formatted = false;
		for(i = 0; i < size; ++i)
			attr[i] = ATTR_UPDATE;
		return;
	}

	fa = attr[prevFA];
	fa |= ATTR_UPDATE;
	for(;;) {
		attr[addr] = fa;

		if (++addr == size)
			addr = 0;

		if (data[addr] == Tn3270.ASCII_FA)
			return;
	}
    }

    /*
     * getFA:
     */
    short getFA(int addr) {
	short		fa;

	fa = 0;
	if ((attr[addr] & ATTR_PROTECTED) != 0)
		fa = 0x20;

	if ((attr[addr] & ATTR_NUMERIC) != 0)
		fa |= 0x10;

	if ((attr[addr] & ATTR_MODIFIED) != 0)
		fa |= 0x01;

	fa |= (attr[addr] & ATTR_COLOR) << 2;

	return(fa);
    }

    /*
     * isFieldModified:
     */
    boolean isFieldModified(int addr) {

	return(((attr[addr] & ATTR_MODIFIED) != 0) ? true : false);
    }

    /*
     * findNextFA:
     */
    int findNextFA(int addr) {
	int		i;

	if (!formatted)
		return(-1);

	for(i = addr; i < size; ++i)
		if (data[i] == Tn3270.ASCII_FA)
			return(i);

	return(-1);
    }

    /*   
     * findNextUnprotectedAfterFA:
     *     Find the first character location, after the field attribute, in
     *     the first unprotected field from the given address until the last
     *     buffer location.
     *   
     *     If the entire buffer is protected or the display is not formatted,
     *     the return value is -1.
     */  
    int findNextUnprotectedAfterFA(int addr) {
        int             i; 
 
        if (!formatted) 
                return(-1); 

        for(i = addr; i < size; ++i) {
                if (data[i] != Tn3270.ASCII_FA)
                        continue; 
 
		if ((attr[i] & ATTR_PROTECTED) == 0) {
			if (i + 1 == size)
				break;

			if (data[i + 1] != Tn3270.ASCII_FA)
				return(i + 1);
		}
        }

	return(-1);
    }

    /*
     * findPrevUnprotectedAfterFA:
     *     Find the first character location, after the field attribute, in
     *     the first unprotected field from the given address until the first
     *     buffer location.
     *   
     *     If the entire buffer is protected or the display is not formatted,
     *     the return value is -1.
     */  
    int findPrevUnprotectedAfterFA(int addr) {
        int             i; 
 
        if (!formatted) 
                return(-1); 

	if (--addr < 0)
		return(-1);

	if (data[addr] == Tn3270.ASCII_FA)
		--addr;

        for(i = addr; i >= 0; --i) {
                if (data[i] != Tn3270.ASCII_FA)
                        continue; 
 
		if ((attr[i] & ATTR_PROTECTED) == 0)
			if (data[i + 1] != Tn3270.ASCII_FA)
				return(i + 1);
        }

        return(-1);
    }

    /*
     * findPrevFA:
     */
    private int findPrevFA(int addr) {
	int		i;

	if (!formatted)
		return(-1);

	for(i = addr; i >= 0; --i)
		if (data[i] == Tn3270.ASCII_FA)
			return(i);

	return(-1);
    }

    /*
     * insertFA:
     */
    void insertFA(int addr, short c) {
	short		fa;

	fa = ATTR_UPDATE;
	if ((c & 0x20) != 0)
		fa |= ATTR_PROTECTED;
	if ((c & 0x10) != 0)
		fa |= ATTR_NUMERIC;
	if ((c & 0x01) != 0)
		fa |= ATTR_MODIFIED;
	fa |= (c >> 2) & ATTR_COLOR;

	formatted = true;
	data[addr] = Tn3270.ASCII_FA;

	for(;;)  {
		attr[addr] = fa;

		if (++addr == size)
			addr = 0;

		if (data[addr] == Tn3270.ASCII_FA)
			return;
	}
    }

    /*
     * setBitModifiedInFA:
     */
    void setBitModifiedInFA(int addr, boolean on) {

	if (on) {
		for(;;) {
			attr[addr++] |= ATTR_UPDATE | ATTR_MODIFIED;

			if (addr == size)
				addr = 0;

			if (data[addr] == Tn3270.ASCII_FA)
				break;
		}
		return;
	}

	for(;;) {
		attr[addr] |= ATTR_UPDATE;
		attr[addr++] &= ~ATTR_MODIFIED;

		if (addr == size)
			addr = 0;

		if (data[addr] == Tn3270.ASCII_FA)
			return;
	}
    }
}
