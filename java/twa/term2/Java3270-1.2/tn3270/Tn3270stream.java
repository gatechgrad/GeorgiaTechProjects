/*
 * Tn3270stream.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 *
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 *
 * Documentation:
 *	3270 Information Display System - Data Stream
 *	Programmer's Reference (GA23-0059-05).
 */

package tn3270;

import	java.io.IOException;

class Tn3270stream {

    private Tn3270	tn;

    /*
     * Constructor: Tn3270stream.
     */
    Tn3270stream(Tn3270 tn) {

	this.tn = tn;
    }

    /* ----------------------------------------------------------------	*
     * 3270 Data Stream Definitions					*
     * ----------------------------------------------------------------	*/

    // 3270 Write commands
    final static short CMD_W   = 0x01;          // write
    final static short CMD_EW  = 0x05;          // erase/write
    final static short CMD_EWA = 0x0D;          // erase/write alternate
    final static short CMD_EAU = 0x0F;          // erase all unprotected
    final static short CMD_WSF = 0x11;		// write structured field

    final static short CMD_RB  = 0x02;          // read buffer
    final static short CMD_RM  = 0x06;          // read modified

    final static short CMD_NOP = 0x03;          // no-op

    final static short EBCDIC_W   = 0xF1;	// write
    final static short EBCDIC_EW  = 0xF5;	// erase/write
    final static short EBCDIC_EWA = 0x7E;	// erase/write alternate
    final static short EBCDIC_EAU = 0x6F;	// erase all unprotected
    final static short EBCDIC_WSF = 0xF3;	// write structured field

    final static short EBCDIC_RB  = 0xF2;	// read buffer
    final static short EBCDIC_RM  = 0xF6;	// read modified

    // 3270 orders
    final static short ORDER_EUA = 0x12;        // erase unprotected to address
    final static short ORDER_GE  = 0x08;        // graphic escape
    final static short ORDER_IC  = 0x13;        // insert cursor
    final static short ORDER_MF  = 0x2C;        // modify field
    final static short ORDER_PT  = 0x05;        // program tab
    final static short ORDER_RA  = 0x3C;        // repeat to address
    final static short ORDER_SA  = 0x28;        // set attribute
    final static short ORDER_SBA = 0x11;        // set buffer addres
    final static short ORDER_SF  = 0x1D;        // start field
    final static short ORDER_SFE = 0x29;        // start field extended

    // Extended attributes
    final static short XA_3270         = 0xC0;	// start field
    final static short XA_HIGHLIGHTING = 0x41;	// highlighting
    final static short XA_FOREGROUND   = 0x42;	// foreground color
    final static short XA_TRANSPARENCY = 0x46;	// Transparency

    /*
     * codeTable is used to translate buffer addresses to the 3270
     * datastream representation
     */
    static final short codeTable[] = {
	0x40, 0xC1, 0xC2, 0xC3, 0xC4, 0xC5, 0xC6, 0xC7,
	0xC8, 0xC9, 0x4A, 0x4B, 0x4C, 0x4D, 0x4E, 0x4F,
	0x50, 0xD1, 0xD2, 0xD3, 0xD4, 0xD5, 0xD6, 0xD7,
	0xD8, 0xD9, 0x5A, 0x5B, 0x5C, 0x5D, 0x5E, 0x5F,
	0x60, 0x61, 0xE2, 0xE3, 0xE4, 0xE5, 0xE6, 0xE7,
	0xE8, 0xE9, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
	0xF0, 0xF1, 0xF2, 0xF3, 0xF4, 0xF5, 0xF6, 0xF7,
	0xF8, 0xF9, 0x7A, 0x7B, 0x7C, 0x7D, 0x7E, 0x7F
    };

    /*
     * processData:
     *     Interpret an incoming 3270 datastream:
     */
    void processData(short buf[], int buflen) throws IOException {

	switch(buf[0]) {
	case EBCDIC_W:
		buf[0] = CMD_W;
		break;
	case EBCDIC_EW:
		buf[0] = CMD_EW;
		break;
	case EBCDIC_EWA:
		buf[0] = CMD_EWA;
		break;
	case EBCDIC_EAU:
		buf[0] = CMD_EAU;
		break;
	case EBCDIC_WSF:
		buf[0] = CMD_WSF;
		break;
	case EBCDIC_RB:
		buf[0] = CMD_RB;
		break;
	case EBCDIC_RM:
		buf[0] = CMD_RM;
		break;
	}

	switch(buf[0]) {
	case CMD_W:
	case CMD_EW:
	case CMD_EWA:
	case CMD_EAU:
		tn.paintStatus(Tn3270.STATUS_SYSTEM);
		writeOperation(buf, buflen);
		break;
	case CMD_WSF:
		writeStructuredField(buf, buflen);
		break;
	case CMD_RB:
		tn.paintStatus(Tn3270.STATUS_SENDING);
		readBufferCommand();
		break;
	case CMD_RM:
		tn.paintStatus(Tn3270.STATUS_SENDING);
		readModifiedCommand();
		break;
	case CMD_NOP:
		break;
	default:
		throw new IOException("unknown 3270 command");
	}

	tn.displayUpdate();
    }

    /*
     * 3270 Read Operation
     * ----------------------------------------------------------------
     *
     *     A read operation sends an inbound data stream (from the terminal
     *     to the application program) with an AID byte as the first byte
     *     of the inbound data stream. The inboud data stream usually
     *     consists of an AID followed by the cursor address (2 bytes).
     *
     *     The inbound data stream format is as follows:
     *          ----- ---------------- ------
     *         | AID | Cursor address | Data |
     *          ----- ---------------- ------
     */

    /*
     * readBufferCommand:
     *     When the display receives the Read Buffer command, the entire
     *     contents of the character buffer are sent to the application
     *     program. The transfer of data begins from buffer address 0.
     *
     *     The format of the Read Buffer Field data stream is as follows:
     *          ----- ---------------- ---------- ----------- ------
     *         | AID | Cursor Address | SF order | Attribute | Data |
     *          ----- ---------------- ---------- ----------- ------
     */
    private void readBufferCommand() throws IOException {
	short		obuf[], c;
	int		i, n;

	obuf = new short[tn.displaySize * 2 + 20];
	n = 0;

	obuf[n++] = tn.aidGet();
	obuf[n++] = codeTable[(tn.cursorGetPosition() >> 6) & 0x3F];
	obuf[n++] = codeTable[tn.cursorGetPosition() & 0x3F];

	for(i = 0; i < tn.displaySize; ++i)
		if ((c = tn.displayData[i]) == Tn3270.ASCII_FA) {
			obuf[n++] = ORDER_SF;
			obuf[n++] = tn.displayGetFA(i);
		} else
			obuf[n++] = Tn3270.asc2ebc[c];

	tn.tcpWrite(obuf, n);
    }

    /*
     * readModifiedCommand:
     *     During a read modified operation, if an AID other than PA key
     *     or a Clear key is generated, all fields that have been modified
     *     by keyboard are transferred to the application program. A major
     *     feature of the read modified operation is null supression.
     *
     *     If the buffer is unformatted (contains no fields), the read
     *     data stream consists of the 3-byte read heading followed by
     *     all alphanumeric data in the buffer (nulls are suppressed).
     *     Data transfer starts at address 0 and continues to the end
     *     of the buffer.
     *
     *     The format of the Read Modified Field data stream is as follows:
     *          ----- ---------------- ----- ----------------------- ------
     *         | AID | Cursor Address | SBA | Attribute Address + 1 | Text |
     *          ----- ---------------- ----- ----------------------- ------
     */
    void readModifiedCommand() throws IOException {
	short		obuf[], aid, c;
	int		addr, i, n;

	obuf = new short[tn.displaySize * 2 + 20];
	n = 0;

	aid = tn.aidGet();
	obuf[n++] = aid;

	if (aid == Tn3270aid.AID_PA[0] || aid == Tn3270aid.AID_PA[1] ||
	    aid == Tn3270aid.AID_PA[2] || aid == Tn3270aid.AID_CLEAR) {
		tn.tcpWrite(obuf, n);
		return;
	}

	obuf[n++] = codeTable[(tn.cursorGetPosition() >> 6) & 0x3F];
	obuf[n++] = codeTable[tn.cursorGetPosition() & 0x3F];

	if ((addr = tn.displayFindNextFA(0)) == -1) {
		for(i = 0; i < tn.displaySize; ++i)
			if ((c = tn.displayData[i]) != 0)
				obuf[n++] = Tn3270.asc2ebc[c];

		tn.tcpWrite(obuf, n);
		return;
	}

	for(i = addr; i < tn.displaySize; ++i) {
		if (tn.displayData[i] != Tn3270.ASCII_FA)
			continue;

		if (! tn.displayIsFieldModified(i))
			continue;

		if (++i == tn.displaySize)
			break;
		obuf[n++] = ORDER_SBA;
		obuf[n++] = codeTable[(i >> 6) & 0x3F];
		obuf[n++] = codeTable[i & 0x3F];

		while(tn.displayData[i] != Tn3270.ASCII_FA) {
			if ((c = tn.displayData[i]) != 0)
				obuf[n++] = Tn3270.asc2ebc[c];

			if (++i == tn.displaySize)
				break;
		}

		--i;		// repoint to the FA US WEST
	}

	tn.tcpWrite(obuf, n);
    }

    /*
     * 3270 Write Operation
     * ---------------------------------------------------------------- 
     *
     *     The process of sending a write type command and perfoming that
     *     command is called a write operation.
     *
     *     The write commands are:
     *         - Write (W)                    A WCC, orders and data
     *         - Erase/Write (EW)             A WCC, orders and data
     *         - Erase/Write Alternate (EWA)  A WCC, orders and data
     *         - Erase All Unprotected (EAU)
     *
     *     Write and EW operations are identical except that EW causes
     *     complete erasure of the character buffer before the write
     *     operation is started and sets the current cursor position to
     *     the first buffer address.
     *
     *     The format of the write type command is:
     *           --------------- ----- -----------------
     *		| Write Command | WCC | Orders and Data |
     *           --------------- ----- -----------------
     *
     *     Write Control Character (WCC) Bit Definitions for Displays:
     *        - Bit 1 resets Modified Data Tag (MDT) bits in the field
     *          attributes. When set to 1, all MDT bits in the device's
     *          existing character buffer are reset before any data is
     *          written or orders are performed.
     *
     *        - Bit 2: Keyboard restore bit. When set to 1, this bit unlocks
     *          the keyboard ant it also resets the AID byte at the end of
     *          the operation specified.
     */

    private void writeOperation(short buf[], int buflen) throws IOException {

	if (buf[0] == CMD_EAU) {
		eraseAllUnprotectedCommand();
		return;
	}

	if ((buf[1] & 0x01) != 0)
		resetModifiedDataTab();

	switch(buf[0]) {
	case CMD_EWA:			// on 3278-2, same as erase/write.
	case CMD_EW:
		tn.displayClear();
		tn.cursorMove(0);
	}

	writeCommand(buf, buflen);

	if ((buf[1] & 0x02) != 0) {
		tn.aidSet(Tn3270aid.AID_NO);
		tn.displayUnlock();
	}
    }

    /*
     * eraseAllUnprotectedCommand:
     *     The Erase All Unprotected Command does the following:
     *         -  Clears all the unprotected character locations to nulls.
     *         -  Resets to 0 the MDT bit in the field attribute for each
     *            unprotected field.
     *         -  Unlocks the keyboard.
     *         -  Resets the AID.
     *         -  Repositions the cursor to the first character location,
     *            after the field attribute, in the first unprotected field
     *            of the character buffer.
     *
     *     If the entire buffer is protected, buffer data is not cleared and
     *     MDT bits are not reset. However, the keyboard is unlocked, the
     *     AID is reset, and the cursor is repositioned to the first buffer
     *     address.
     */
    private void eraseAllUnprotectedCommand() {
	int		addr;

	eraseUnprotectedCharacter(0, tn.displaySize - 1);
	resetModifiedDataTab();
	tn.aidSet(Tn3270aid.AID_NO);
	if ((addr = tn.displayFindNextUnprotectedAfterFA(0)) == -1)
		addr = 0;
	tn.cursorMove(addr);
	tn.displayUnlock();
    }
    
    /*
     * writeCommand:
     *     The Write command writes data into specified locations of the
     *     character buffer. Data is stored in successive buffer locations
     *     until an order is encountered in the data stream.
     *
     *     During the write operation, the buffer address is advanced one
     *     location as each character is stored.
     *
     *     The buffer location where the entry of data starts depends on the
     *     starting location specified by the SBA order that follows the WCC.
     *     If an SBA does not follow the WCC, the starting location is the
     *     buffer address where the cursor is positioned.
     */
     private void writeCommand(short buf[], int buflen) throws IOException {
	boolean		lastCmdIsOrder;
	short		c1, c2, c3, n;
	int		i, j, addr;

	addr = tn.cursorGetPosition();
	lastCmdIsOrder = true;
	for(i = 2; i < buflen; ++i) {
		switch (buf[i]) {
		case ORDER_MF:
			//  ----- ---------------------- ------ -------
			// | MF | Number of Attr Pairs | type | value | 
			//  ----- ---------------------- ------ -------
			n = buf[++i];
			i += n * 2;
			break;
		case ORDER_SA:
			//  ---- ---------------- -----------------
			// | SA | Attribute Type | Attribute Value |
			//  ---- ---------------- -----------------
			i += 2;
			break;
		case ORDER_SFE:
			//  ----- ---------------------- ------ -------
			// | SFE | Number of Attr Pairs | type | value | 
			//  ----- ---------------------- ------ -------
			n = buf[++i];
			for(j = 0; j < n; ++j)
				switch(buf[++i]) {
				case XA_3270:
					addr = startField(addr, buf[++i]);
					break;
				default:	// unsupported
					++i;
				}
			break;
		case ORDER_GE:
			break;
		case ORDER_SF:
			addr = startField(addr, buf[++i]);
			break;
		case ORDER_SBA:
			c1 = buf[++i];
			c2 = buf[++i];
			addr = setBufferAddress(c1, c2);
			break;
		case ORDER_IC:
			insertCursor(addr);
			break;
		case ORDER_PT:
			addr = programTab(addr, lastCmdIsOrder);
			break;
		case ORDER_RA:
			c1 = buf[++i];
			c2 = buf[++i];
			if ((c3 = buf[++i]) == ORDER_GE)
				c3 = buf[++i];
			addr = repeatToAddress(addr, c1, c2, c3);
			break;
		case ORDER_EUA:
			c1 = buf[++i];
			c2 = buf[++i];
			addr = eraseUnprotectedToAddress(addr, c1, c2);
			break;
		default:
			if (tn.displayData[addr] == Tn3270.ASCII_FA)
				tn.displayEraseFA(addr);

			tn.displayData[addr] = Tn3270.ebc2asc[buf[i]];
			tn.displayAttr[addr] |= tn.ATTR_UPDATE;

			if (++addr == tn.displaySize)
				addr = 0;
		}

		switch(buf[i]) {
		case ORDER_SFE:
		case ORDER_SA:
		case ORDER_SF:
		case ORDER_SBA:
		case ORDER_IC:
		case ORDER_PT:
		case ORDER_RA:
		case ORDER_EUA:
			lastCmdIsOrder = true;
		default:
			lastCmdIsOrder = false;
		}
	}
     }

    /*
     * eraseUnprotectedToAddress:
     *     The Erase Unprotected to Address (EUA) order stores nulls in
     *     all unprotected character locations, starting at the current
     *     buffer address and ending at, but not including, the specified
     *     stop address.
     *
     *     The stop address is identified by the two bytes immediately
     *     following the EUA order.
     *
     *     When the stop address is lower than the current buffer address,
     *     EUA wraps from the last buffer location to the first. When the
     *     stop address equals the current buffer address, all unprotected
     *     character locations in the buffer are erased.
     *
     *     The current buffer address after successful execution of EUA
     *     is the stop address.
     *
     *     Field attributes are not affected by EUA.
     */
    private int eraseUnprotectedToAddress(int start, short c1, short c2) {
	int		stop, last;

	stop = setBufferAddress(c1, c2);

	if ((last = stop - 1) < 0)
		last = tn.displaySize - 1;

	eraseUnprotectedCharacter(start, last);
	return(stop);
    }
     
    /*
     * insertCursor:
     *     The Insert Cursor order repositions the cursor to the location
     *     specified by the current buffer address.
     */
    private void insertCursor(int addr) {

	tn.cursorMove(addr);
    }

    /*
     * programTab:
     *     The Program Tab (PT) order advances the current buffer address to
     *     the address of the first character position of the next unprotected
     *     field. If PT is issued when the current buffer address is the
     *     location of a field attribute of an unprotected field, the buffer
     *     advances to the next location of that field (on location).
     *
     *     The display stops its search for an unprotected field at the last
     *     location in the character buffer. If a field attribute for an
     *     unprotected field is not found, the buffer address is set to 0. (If
     *     the display finds a field attribute for an unprotected filed in the
     *     last buffer location, the buffer address is also set to 0.)
     *
     *     In addition, if PT does not immediately follow a command, order or
     *     order sequence (such as after the WCC, IC, and RA respectively),
     *     nulls are inserted in the buffer from the current buffer address to
     *     the end of the field.
     */
    private int programTab(int addr, boolean lastCmdIsOrder) {
	int		end;

	if ((addr = tn.displayFindNextUnprotectedAfterFA(addr)) == -1)
		return(0);

	if (lastCmdIsOrder)
		return(addr);

	if ((end = tn.displayFindNextFA(addr) - 1) < 0)
		end = tn.displaySize - 1;

	eraseUnprotectedCharacter(addr, end);
        return(addr);
    }

    /*
     * repeatToAddress:
     *     The Repeat to Address (RA) order stores a specified character in
     *     all character buffer locations, starting at the current buffer
     *     address and ending at (but not including) the specified stop
     *     address.
     *
     *     The stop address is identified by the 2 bytes immediately following
     *     the RA order. The character to be repeated follows the stop address.
     *     For data streams that support GE sequences or 2-byte coded character
     *     sets, the Character-to-Be-Repeated field can be two bytes long.
     *
     *     When the stop address is lower than the current buffer address, RA
     *     wraps from the last buffer location to the first. When the stop
     *     address equals the current address, the specified character is
     *     stored in all buffer locations.
     *
     *     The current buffer address after completion of RA is the stop
     *     address, that is, on greater than the last buffer location stored
     *     into by RA.
     *
     *     Field attributes are overwritten by the RA order, if encountered.
     */
    private int repeatToAddress(int addr, short c1, short c2, short ch) {
	short		ascii;
	int		stop, last;

	stop = setBufferAddress(c1, c2);

	if ((last = stop - 1) < 0)
		last = tn.displaySize - 1;

	ascii = Tn3270.ebc2asc[ch];
	for(;;) {
		if (tn.displayData[addr] == Tn3270.ASCII_FA)
			tn.displayEraseFA(addr);

		tn.displayData[addr] = ascii;
		tn.displayAttr[addr] |= tn.ATTR_UPDATE;

		if (addr == last)
			break;

		if (++addr == tn.displaySize)
			addr = 0;
	}

	return(stop);
    }

    /*
     * setBufferAddress:
     *     The Set Buffer Address order specifies a new character buffer
     *     address from which operations are to start or continue.
     *
     *     When the flag bits are 00, the next 14 bits contain a buffer
     *     address in binary form. If the flag bits are 01 or 11, the
     *     next 14 bits are interpreted as a 2-character address (6 bits
     *     in each byte). The 6 low-order bits of each byte are joined to
     *     provide a 12-bit address.
     */
    private int setBufferAddress(short c1, short c2) {

	if ((c1 & 0xC0) == 0x00)
		return(((c1 & 0x3F) << 8) + c2);
	else
		return(((c1 & 0x3F) << 6) + (c2 & 0x3F));
    }

    /*
     * startField:
     *     The Start Field order identifies to the display that the next byte
     *     is a field attribute. The display then stores the field attribute
     *     at the current buffer address and increments the buffer address by
     *     one.
     */
    private int startField(int addr, short fa) {

	tn.displayInsertFA(addr, fa);
	return((++addr == tn.displaySize) ? 0 : addr);
    }

    /*
     * writeStructuredField:
     *     The format of the WSF command is:
     *           --------- -------------------- -------------------- -----
     *		| WSF CMD | Structured Field 1 | Structured Field 2 | ... |
     *           --------- -------------------- -------------------- -----
     *
     *     Structured Field #:
     *		 ------------------ ------ ------------------------
     *		| Length (2 bytes) | SFID | Parameters and/or Data |
     *		 ------------------ ------ ------------------------
     */
    private void writeStructuredField(short buf[], int buflen)
    throws IOException {
	short		obuf[];
	int		cmnd, length, offset, nleft, pid, sfid, type;
	int		i, n;

	offset = 1;
	nleft = buflen - 1;

	while(nleft > 0) {
		if (nleft < 3)
			throw new IOException("WSF too small");

		length = (buf[offset] << 8) + buf[offset + 1];
		sfid = buf[offset + 2];

		switch(sfid) {
		case 0x01:
			/*
			 * Read Partion - p. 5-47
			 */
			if (length < 5)
				throw new IOException("WSF-RP too small");

			pid = buf[offset + 3];
			type = buf[offset + 4];

			if (type != 0x02)
				throw new IOException("WSF-RP not a query op");

			if (pid != 0xFF)
				throw new IOException("WSF-RP invalid PID");

			obuf = new short[75];
			obuf[0] = 0x88;		// AID

			/*
			 * Color - p. 6.31
			 */
			n = 1;
			obuf[n] = 0x00;		// Length 22 bytes
			obuf[n + 1] = 0x16;
			obuf[n + 2] = 0x81;	// SFID
			obuf[n + 3] = 0x86;	// QCODE: Color
			obuf[n + 4] = 0x00;	// FLAGS: no options
			obuf[n + 5] = 0x08;	// 8 colors
			obuf[n + 6]  = 0x00; obuf[n + 7]  = 0xF4;
			obuf[n + 8]  = 0xF1; obuf[n + 9]  = 0x00;
			obuf[n + 10] = 0xF2; obuf[n + 11] = 0x00;
			obuf[n + 12] = 0xF3; obuf[n + 13] = 0x00;
			obuf[n + 14] = 0xF4; obuf[n + 15] = 0x00;
			obuf[n + 16] = 0xF5; obuf[n + 17] = 0x00;
			obuf[n + 18] = 0xF6; obuf[n + 19] = 0x00;
			obuf[n + 20] = 0xF7; obuf[n + 21] = 0x00;

			/*
			 * Highlighting - 6.59
			 */
			n = 23;
			obuf[n] = 0x00;		// Length 13 bytes;
			obuf[n + 1] = 0x0D;
			obuf[n + 2] = 0x81;	// SFID
			obuf[n + 3] = 0x87;	// QCODE: Highlighting
			obuf[n + 4] = 0x04;	// 4 pairs
			obuf[n + 5]  = 0x00; obuf[n + 6]  = 0xF0;
			obuf[n + 7]  = 0xF1; obuf[n + 8]  = 0xF1;
			obuf[n + 9]  = 0xF2; obuf[n + 10] = 0xF2;
			obuf[n + 11] = 0xF4; obuf[n + 12] = 0xF4;

			/*
			 * Usable Area - 6.91
			 */
			n = 36;
			obuf[n] = 0x00;		// Length 23 bytes;
			obuf[n + 1] = 0x17;
			obuf[n + 2] = 0x81;	// SFID
			obuf[n + 3] = 0x81;	// QCODE: Usable Area
			obuf[n + 4] = 0x01;	// 12/14-bit addressing
			obuf[n + 5] = 0x00;	// no special character features
			obuf[n + 6] = 0x00;	// width
			obuf[n + 7] = (short)tn.displayCols;
			obuf[n + 8] = 0x00;	// height
			obuf[n + 9] = (short)tn.displayRows;
			obuf[n + 10] = 0x00;	// units (inches)
			obuf[n + 11] = 0x00;	// Xr
			obuf[n + 12] = 0x02;
			obuf[n + 13] = 0x00;
			obuf[n + 14] = 0x89;
			obuf[n + 15] = 0x00;	// Yr
			obuf[n + 16] = 0x02;
			obuf[n + 17] = 0x00;
			obuf[n + 18] = 0x85;
			obuf[n + 19] = 0x09;	// char width
			obuf[n + 20] = 0x10;	// char height
						// buffer size
			obuf[n + 21] = (short)(tn.displaySize >> 8);
			obuf[n + 22] = (short)(tn.displaySize & 0xFF);

			/*
			 * Character Sets - p. 6.23
			 */
			n = 59;
			obuf[n] = 0x00;		// Length 16 bytes;
			obuf[n + 1] = 0x10;
			obuf[n + 2] = 0x81;	// SFID
			obuf[n + 3] = 0x85;	// QCODE: Character Sets
			obuf[n + 4] = 0x00;	// no flags
			obuf[n + 5] = 0x00;	// more flags
			obuf[n + 6] = 0x09;	// char width
			obuf[n + 7] = 0x10;	// char height
			obuf[n + 8] = 0x00;	// Load PS format types
			obuf[n + 9] = 0x00;
			obuf[n + 10] = 0x00;
			obuf[n + 11] = 0x00;
			obuf[n + 12] = 0x03;	// length of each descriptor
			obuf[n + 13] = 0x00;	// SET
			obuf[n + 14] = 0x00;	// FLAGS
			obuf[n + 15] = 0x00;	// LCID

			tn.tcpWrite(obuf, 75);
			break;
		case 0x40:
			/*
			 * Outbound 3270DS - p. 5-41
			 */
			if (length < 5)
				throw new IOException("WSF-OBDS too small");

			pid = buf[offset + 3];
			cmnd = buf[offset + 4];

			if (pid != 0x00)
				throw new IOException("WSF-OBDS invalid PID");

			switch(cmnd) {
			case EBCDIC_W:
			case EBCDIC_EW:
			case EBCDIC_EWA:
			case EBCDIC_EAU:
				n = length - 4;
				obuf = new short[n];
				for(i = 0; i < n; ++i)
					obuf[i] = buf[i + 4];

				writeOperation(obuf, n);
				break;
			default:
				throw new IOException("WSF-OBDS unsupported");
			}
			break;
		default:
			throw new IOException("unsupported WFS ID: " + sfid);
		}

		offset += length;
		nleft -= length;
	}
    }

    /* ================================================================ *
     * ================================================================ */

    /*
     * eraseUnprotectedCharacter:
     */
    void eraseUnprotectedCharacter(int addr, int end) {

	for(;;) {
		if (tn.displayData[addr] != Tn3270.ASCII_FA &&
		    (tn.displayAttr[addr] & tn.ATTR_PROTECTED) == 0) {
			tn.displayData[addr] = 0;
			tn.displayAttr[addr] |= tn.ATTR_UPDATE;
		}

		if (addr == end)
			break;

		if (++addr == tn.displaySize)
			addr = 0;
        }
    }

    /*
     * resetModifiedDataTab:
     */
    void resetModifiedDataTab() {
	int		addr, next;

	if ((addr = tn.displayFindNextFA(0)) == -1)
		return;

	for(;;) {
		tn.displaySetBitModifiedInFA(addr, false);

		if ((next = tn.displayFindNextFA(addr + 1)) <= addr)
			return;

		addr = next;
	}
   }
}
