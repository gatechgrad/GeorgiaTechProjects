/*
 * Tn3270aid.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 *
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 */

package tn3270;

import	java.io.IOException;

class Tn3270aid {

    private Tn3270	tn;

    // Definitions for AID key.
    final static short AID_NO    = 0x60;		// no AID generated
    final static short AID_CLEAR = 0x6D;
    final static short AID_ENTER = 0x7D;

    final static short AID_PA[] = {			// PA 1..3
			0x6C, 0x6E, 0x6B
    };

    final static short AID_PF[] = {                    // PF 1..24
			0xF1, 0xF2, 0xF3, 0xF4, 0xF5, 0xF6,
			0xF7, 0xF8, 0xF9, 0x7A, 0x7B, 0x7C,
			0xC1, 0xC2, 0xC3, 0xC4, 0xC5, 0xC6,
			0xC7, 0xC8, 0xC9, 0x4A, 0x4B, 0x4C
    };

    private short	aid;				// current Attention ID

    /*
     * Constructor: Tn3270aid
     */
    Tn3270aid(Tn3270 tn) {

	this.tn = tn;

	aid = AID_NO;
    }

    /*
     * clear:
     */
    void clear() throws IOException {

        if (tn.displayIsLocked())
		return;

	tn.displayClear();
	tn.cursorMove(0);
	tn.displayUpdate();
	key(AID_CLEAR);
    }

    /*
     * enter:
     */
    void enter() throws IOException {

	key(AID_ENTER);
    }

    /*
     * get:
     */
    short get() {

	return(aid);
    }

    /*
     * key:
     *     Handle an AID (Attention IDentifier) key.
     */
    private void key(short aid) throws IOException {

        if (tn.displayIsLocked())
		return;

	this.aid = aid;
	tn.displayLock(Tn3270.STATUS_SENDING);
	tn.streamReadModifiedCommand();

	tn.paintStatus(Tn3270.STATUS_WAITING);
    }

    /*
     * pa:
     */
    void pa(int no) throws IOException {

	if (tn.displayIsLocked())
		return;

	key(AID_PA[no]);
    }

    /*
     * pf:
     */
    void pf(int no) throws IOException {

        if (tn.displayIsLocked())
		return;

	key(AID_PF[no]);
    }

    /*
     * set:
     */
    void set(short aid) {

	this.aid = aid;
    }
}
