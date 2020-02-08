/*
 * Tn3270.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 *
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 */

package tn3270;

import	java.io.IOException;

/**
 * Defining an applet that uses IBM-3270 sessions:
 * <ul>
 * <pre>
 * import  tn3270.*;
 * public class Java3270 extends Applet implements Tn3270paint {
 *
 * }
 * </pre>
 * </ul>
 */
public class Tn3270 {

    /*
     * Variables to manage a 3270 session
     */

    private Tn3270aid		aid;
    private Tn3270cursor	cursor;
    private Tn3270display	display;
    private Tn3270paint		paint;
    private Tn3270stream	stream;
    private Tn3270tcp		tcp;

    int				displaySize;
    int				displayRows;
    int				displayCols;
    short			displayData[];
    short			displayAttr[];

    // Display Attribute
    final static short		ATTR_UPDATE    = 0x08;
    final static short		ATTR_PROTECTED = 0x10;

    // Special ascii codes
    final static short ASCII_FA = 0x01;
    final static short ASCII_FM = 0xb6;

    // Status
    final static String STATUS_SENDING	 = "SENDING";
    final static String STATUS_WAITING	 = "WAITING";
    final static String STATUS_SYSTEM	 = "SYSTEM";
    final static String STATUS_PROTECTED = "PROTECTED";
    final static String STATUS_NUMERIC	 = "NUMERIC";
    final static String STATUS_FULL	 = "FULL";
    final static String STATUS_INSERT	 = "INSERT ON";

    /* ---------------------------------------------------------------- *
     * Conversion table ISO 8859 1 <-> EBCDIC (Swiss french)		*
     * ---------------------------------------------------------------- */

    static short asc2ebc[] = {
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x40, 0x4f, 0x7f, 0x7b, 0x5b, 0x6c, 0x50, 0x7d,
	0x4d, 0x5d, 0x5c, 0x4e, 0x6b, 0x60, 0x4b, 0x61,
	0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7,
	0xf8, 0xf9, 0x7a, 0x5e, 0x4c, 0x7e, 0x6e, 0x6f,
	0x7c, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7,
	0xc8, 0xc9, 0xd1, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6,
	0xd7, 0xd8, 0xd9, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6,
	0xe7, 0xe8, 0xe9, 0x4a, 0xe0, 0x5a, 0x5f, 0x6d,
	0x79, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87,
	0x88, 0x89, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96,
	0x97, 0x98, 0x99, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6,
	0xa7, 0xa8, 0xa9, 0xc0, 0xbb, 0xd0, 0xa1, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0xb0, 0xb1, 0x9f, 0xb2, 0x6a, 0xb5,
	0xbd, 0x00, 0x00, 0x00, 0xba, 0x00, 0x00, 0xbc,
	0x90, 0x00, 0x00, 0x00, 0xbe, 0x00, 0x1e, 0x00,
	0x9d, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x64, 0x65, 0x62, 0x66, 0x63, 0x67, 0x9e, 0x68,
	0x74, 0x71, 0x72, 0x73, 0x78, 0x75, 0x76, 0x77,
	0x00, 0x69, 0xed, 0xee, 0xeb, 0xef, 0xec, 0x00,
	0x80, 0xfd, 0xfe, 0xfb, 0xfc, 0x00, 0x00, 0x00,
	0x44, 0x45, 0x42, 0x46, 0x43, 0x47, 0x9c, 0x48,
	0x54, 0x51, 0x52, 0x53, 0x58, 0x55, 0x56, 0x57,
	0x00, 0x49, 0xcd, 0xce, 0xcb, 0xcf, 0xcc, 0x00,
	0x70, 0xdd, 0xde, 0xdb, 0xdc, 0x00, 0x00, 0xdf
    };

    static short ebc2asc[] = {
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0xb6, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x20, 0x00, 0xe2, 0xe4, 0xe0, 0xe1, 0xe3, 0xe5,
	0xe7, 0xf1, 0x5b, 0x2e, 0x3c, 0x28, 0x2b, 0x21,
	0x26, 0xe9, 0xea, 0xeb, 0xe8, 0xed, 0xee, 0xef,
	0xec, 0x00, 0x5d, 0x24, 0x2a, 0x29, 0x3b, 0x5e,
	0x2d, 0x2f, 0xc2, 0xc4, 0xc0, 0xc1, 0xc3, 0xc5,
	0xc7, 0xd1, 0xa6, 0x2c, 0x25, 0x5f, 0x3e, 0x3f,
	0xf8, 0xc9, 0xca, 0xcb, 0xc8, 0xcd, 0xce, 0xcf,
	0xcc, 0x60, 0x3a, 0x23, 0x40, 0x27, 0x3d, 0x22,
	0xd8, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
	0x68, 0x69, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0xb0, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f, 0x70,
	0x71, 0x72, 0x00, 0x00, 0xe6, 0xb8, 0xc6, 0xa4,
	0x00, 0x7e, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78,
	0x79, 0x7a, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0xa2, 0xa3, 0xa5, 0x00, 0x00, 0xa7, 0x00, 0x00,
	0x00, 0x00, 0xac, 0x7c, 0xaf, 0xa8, 0xb4, 0x00,
	0x7b, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47,
	0x48, 0x49, 0x00, 0xf4, 0xf6, 0xf2, 0xf3, 0xf5,
	0x7d, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50,
	0x51, 0x52, 0x00, 0xfb, 0xfc, 0xf9, 0xfa, 0xff,
	0x5c, 0x00, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58,
	0x59, 0x5a, 0x00, 0xd4, 0xd6, 0xd2, 0xd3, 0xd5,
	0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
	0x38, 0x39, 0x00, 0xdb, 0xdc, 0xd9, 0xda, 0x00
    };

    /**
     * @param <I>modelNumber</I> defines the 3270 screensize:
     *		(2: 24x80), (3: 32x80), (4: 43x80), (5: 27x132)
     * @param <I>paint</I> represents the current Applet. The Tn3270paint class
     *		defines an interface for the Tn3270 in order to call back
     *		the necessary painting fonctions defined in the applet.
     */
    public Tn3270(int modelNumber, Tn3270paint paint) {

	this.paint = paint;

	displayRows = 24;
	displayCols = 80;

	switch(modelNumber) {
	case 3:
		displayRows = 32;
		break;
	case 4:
		displayRows = 43;
		break;
	case 5:
		displayRows = 27;
		displayCols = 132;
		break;
	}

	displaySize = displayRows * displayCols;

	aid = new Tn3270aid(this);
	cursor = new Tn3270cursor(this);
	display = new Tn3270display(this, displayRows, displayCols);
	stream = new Tn3270stream(this);
	tcp = new Tn3270tcp(this, (byte)modelNumber);

	displayData = display.data;
	displayAttr = display.attr;
    }

    /*
     * TCP-IP
     */
    public void closeConnection() throws IOException {

	tcp.close();
    }

    /**
     * @param <i>host</i> TCP-IP IBM-3270 address
     * @param <i>port</i> TCP-IP port
     */
    public void openConnection(String host, int port) throws IOException {

	tcp.open(host, port);
    }

    /**
     * process incoming data
     */
    public void processInputStream(short netBuf[], int netBufLen)
    throws IOException {

	tcp.process(netBuf, netBufLen);
    }

    /**
     * read incoming data from IBM-3270 sessions
     */
    public int readInputStream(short buf[]) throws IOException {

	return(tcp.read(buf));
    }

    /*
     * Cursor 
     */
    public void keyBackwardTab() {

	cursor.backwardTab();
    }

    public void keyDown() {

	cursor.down();
    }

    public void keyForwardTab() {

	cursor.forwardTab();
    }

    public void keyHome() {

	cursor.home();
    }

    public void keyLeft() {

	cursor.left();
    }

    public void keyNewLine() {

	cursor.newLine();
    }

    public void keyRight() {

	cursor.right();
    }

    public void keyUp() {

	cursor.up();
    }

    /**
     * @param addr set cursor position in respect of top-left corner
     */
    public void setCursorPosition(int addr) {

	cursor.moveToPosition(addr);
    }

    /*
     * AID functions
     */
    public void keyClear() throws IOException {

	aid.clear();
    }

    public void keyEnter() throws IOException {

	aid.enter();
    }

    public void keyPA(int no) throws IOException {

	aid.pa(no);
    }

    public void keyPF(int no) throws IOException {

	aid.pf(no);
    }

    /*
     * Erasing characters
     */
    public void keyDelete() {

	display.delete();
    }

    public void keyDeleteAllInput() {

	display.deleteAllInput();
    }

    public void keyDeleteEndOfField() {

	display.deleteEndOfField();
    }

    public void keyRubout() {

	display.rubout();
    }

    /*
     * Inserting characters
     */
    public void insertString(String str) {

	display.insertString(str);
    }

    public void keyChar(int c) {

	display.insertChar(c);
    }

    public void keyFieldMark() {

	display.fieldMark();
    }

    /*
     * Insertion mode
     */
    public void keyInsertMode(boolean on) {

	display.insertMode(on);
    }

    public void keyToggleMode() {

	display.insertMode();
    }

    /*
     * Reset the screen
     */
    public void keyReset() {

	display.unlock();
    }

    /*
     * Repainting
     */
    public void eraseCursor() {

	cursor.erase();
    }

    public void repaintCursor() {

	cursor.repaint();
    }

    public void repaintScreen() {

	display.repaint();
    }

    /* ----------------------------------------------------------------	*
     * Private methods for Tn3270 package				*
     * ---------------------------------------------------------------- */

    /*
     * Tn3270aid.java
     */
    final short aidGet() {

	return(aid.get());
    }

    final void aidSet(short value) {

	aid.set(value);
    }

    /*
     * Tn3270cursor.java
     */

    final int cursorGetPosition() {

	return(cursor.getPosition());
    }

    final void cursorLeft() {

	cursor.left();
    }

    final void cursorMove(int addr) {

	cursor.move(addr);
    }

    final void cursorPaint() {

	cursor.paint();
    }

    /*
     * Tn3270display.java
     */

    final void displayClear() {

	display.clear();
    }

    final void displayEraseFA(int addr) {

	display.eraseFA(addr);
    }

    final int displayFindNextFA(int addr) {

	return(display.findNextFA(addr));
    }

    final int displayFindNextUnprotectedAfterFA(int addr) {

	return(display.findNextUnprotectedAfterFA(addr));
    }

    final int displayFindPrevUnprotectedAfterFA(int addr) {

	return(display.findPrevUnprotectedAfterFA(addr));
    }

    final short displayGetFA(int addr) {

	return(display.getFA(addr));
    }

    final int displayGetNoColor(int addr) {

	return(display.getNoColor(addr));
    }

    final void displayInsertFA(int addr, short c) {

	display.insertFA(addr, c);
    }

    final boolean displayIsFieldModified(int addr) {

	return(display.isFieldModified(addr));
    }

    final boolean displayIsLocked() {

	return(display.isLocked());
    }

    final void displayLock(String msg) {

	display.lock(msg);
    }

    final void displaySetBitModifiedInFA(int addr, boolean on) {

	display.setBitModifiedInFA(addr, on);
    }

    final void displayUnlock() {

	display.unlock();
    }

    final void displayUpdate() {

	display.update();
    }

    /*
     * Tn3270paint.java
     */
    final void paintCursor(int x, int y, byte c, int color, boolean on) {

	paint.paintCursor(x, y, c, color, on);
    }

    final void paintString(int x, int y, int color, byte buf[], int len) {

	paint.paintString(x, y, color, buf, len);
    }

    final void paintStatus(String str) {

	paint.paintStatus(str);
    }

    /*
     * Tn3270stream.java
     */

    final void streamEraseUnprotectedCharacter(int addr, int end) {

	stream.eraseUnprotectedCharacter(addr, end);
    }

    final void streamProcessData(short buf[], int buflen) throws IOException {

	stream.processData(buf, buflen);
    }

    final void streamReadModifiedCommand() throws IOException {

	stream.readModifiedCommand();
    }

    final void streamResetModifiedDataTab() {

	stream.resetModifiedDataTab();
    }

    /*
     * Tn3270tcp.java
     */

    final void tcpWrite(short buf[], int n) throws IOException {

	tcp.write(buf, n);
    }

}
