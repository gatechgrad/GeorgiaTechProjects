/*
 * Java3270.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 * 
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 */

import	java.applet.*;
import	java.awt.*;
import	tn3270.*;
import	java.io.IOException;

public class Java3270 extends Applet implements Runnable, Tn3270paint {

    Tn3270		tn;
    boolean		tnShow;
    Thread		netInputThread;

    String		hostname;
    String		gateway;
    int			gatewayPort;

    final static int	COLOR_FG = 0;
    final static int	COLOR_INPUT = 1;
    final static int	COLOR_BOLD = 2;
    final static int	COLOR_BG = 3;

    Color		color_fg;
    Color		color_input;
    Color		color_bold;
    Color		color_bg;
    Color		button_bg;

    Font		font;

    Graphics		g;

    int			sx, sy, dx, dy, base_line;

    String		status_str;
    int			status_y;

    int			noButton = -1;
    int			noMessage = -1;
    String		StrMessage;

    // Messages definitions
    final static int	MSG_LOADING = 0;
    final static int	MSG_TRYING = 1;
    final static int	MSG_CONNECTED = 2;
    final static int    MSG_CLOSED_REMOTE = 3;
    final static int	MSG_STRING = 4;
    final static int	MSG_HELP = 5;

    /* ================================================================	*
     * Applet Methods							*
     * ================================================================ */

    /*
     * init:
     */
    public void init() {
    	FontMetrics	fm;
	String		font_name, str;
	int		font_style;
	int		font_size;

	hostname = "sibil.unige.ch";
	if ((str = getParameter("hostname")) != null)
		hostname = str;

	gateway = null;
	if ((str =getParameter("gateway")) != null)
		gateway = str;

	gatewayPort = -1;
	if ((str = getParameter("gatewayPort")) != null)
		gatewayPort = Integer.parseInt(str);

	font_name = "Courier";
	if ((str = getParameter("fontname")) != null)
		font_name = str;

	font_size = 12;
	if ((str = getParameter("fontsize")) != null)
		font_size = Integer.parseInt(str);

	font_style = Font.PLAIN;
	if ((str = getParameter("fontstyle")) != null) {
		if (str.compareTo("bold") == 0)
			font_style = Font.BOLD;
		else if (str.compareTo("plain") == 0)
			font_style = Font.PLAIN;
	}

	
	color_fg = Color.cyan;
	color_bg = new Color(0, 0, 128);
	button_bg = new Color(128, 128, 192);
	color_bold = Color.green;
	color_input = Color.orange;

	font = new Font(font_name, font_style, font_size);
	
	fm = getFontMetrics(font);
	sx = fm.charWidth(' ');
	sy = fm.getHeight();

	if ((str = getParameter("rowheight")) != null)
		sy = Integer.parseInt(str);

	dx = 5;
	dy = fm.getAscent() + 5;
	base_line = fm.getAscent();

	status_y = 24 * sy + 7;

	tnShow = false;

	if ((str = getParameter("information")) != null)
		if (str.compareTo("display") == 0)
			displayInformation(80 * sx + 11, 26 * sy + 18);

	resize(80 * sx + 11, 26 * sy + 18);
    }

    public void paint(Graphics g) {

	g.setFont(font);
	this.g = g;

	// Draw a border
	g.setColor(Color.black);
	g.drawRect(1, 1, size().width - 2, size().height - 2);
	g.setColor(Color.white);
	g.drawRect(0, 0, size().width - 2, size().height - 2);

	if (tnShow)
		paint3270();
	else
		paintWindowMessage();
    }

    /*
     * keyDown:
     */
    public boolean keyDown(Event evt, int key) {

	if ((g = getGraphics()) == null)
		return(true);
	g.setFont(font);

	if (tnShow == false) {
		if (noMessage != MSG_HELP)
			return(true);

		if (key == 10) {
			tnShow = true;
			noMessage = -1;

			paint3270();
		}
		return(true);
	}


	synchronized(tn) {
		try {
			switch(key) {
			case 3:					// <Ctrl-C>
				tn.keyClear();
				return(true);
			case 10:				// <Return>
				tn.keyEnter();
				return(true);
			case Event.F1:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(10);
				else
				if (evt.modifiers == Event.META_MASK)
					tn.keyPA(0);
				else
					tn.keyPF(0);
				return(true);
			case Event.F2:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(11);
				else
				if (evt.modifiers == Event.META_MASK)
					tn.keyPA(1);
				else
					tn.keyPF(1);
				return(true);
			case Event.F3:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(12);
				else
				if (evt.modifiers == Event.META_MASK)
					tn.keyPA(2);
				else
					tn.keyPF(2);
				return(true);
			case Event.F4:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(13);
				else
					tn.keyPF(3);
				return(true);
			case Event.F5:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(14);
				else
					tn.keyPF(4);
				return(true);
			case Event.F6:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(15);
				else
					tn.keyPF(5);
				return(true);
			case Event.F7:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(16);
				else
					tn.keyPF(6);
				return(true);
			case Event.F8:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(17);
				else
					tn.keyPF(7);
				return(true);
			case Event.F9:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(18);
				else
					tn.keyPF(8);
				return(true);
			case Event.F10:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(19);
				else
					tn.keyPF(9);
				return(true);
			case Event.F11:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(20);
				else
					tn.keyPF(10);
				return(true);
			case Event.F12:
				if (evt.modifiers == Event.SHIFT_MASK)
					tn.keyPF(21);
				else
					tn.keyPF(11);
				return(true);
			}
		}
		catch(IOException e) {
			stop(null);
			return(true);
		}

		switch(key) {
		case 2:						// <Ctrl-B>
			tn.keyBackwardTab();
			return(true);
		case 127:					// <Del>
		case 5:						// <Ctrl-E>
			tn.keyDelete();
			return(true);
		case 9:						// <Tab>
			if (evt.modifiers == Event.SHIFT_MASK) {
				tn.keyBackwardTab();
				return(true);
			}
		case 6:						// <Ctrl-F>
			tn.keyForwardTab();
			return(true);
		case 8:						// <BackSpace>
			tn.keyRubout();
			return(true);
		case Event.LEFT:
		case 11:					// <Ctrl-K>
			tn.keyLeft();
			return(true);
		case Event.RIGHT:
		case 12:					// <Ctrl-L>
			tn.keyRight();
			return(true);
		case 13:					// <Ctrl-M>
			tn.keyFieldMark();
			return(true);
		case 14:					// <Ctrl-N>
			tn.keyNewLine();
			return(true);
		case Event.END:
		case 16:					// <Ctrl-P>
			tn.keyDeleteEndOfField();
			return(true);
		case Event.HOME:
		case 17:					// <Ctrl-Q>
			tn.keyHome();
			return(true);
		case 18:					// <Ctrl-R>
			tn.keyReset();
			return(true);
		case Event.UP:
		case 23:					// <Ctrl-W>
			tn.keyUp();
			return(true);
		case Event.DOWN:
		case 26:					// <Ctrl-Z>
			tn.keyDown();
			return(true);
		default:
			tn.keyChar(key);
		}
	}

	return(true);
    }

    /*
     * mouseDown:
     */
    public boolean mouseDown(Event evt, int x, int y) {
	int		n;

	if ((g = getGraphics()) == null)
		return(true);
	g.setFont(font);

	if (tnShow == false)
		return(true);

	if ((n = get3270Position(x, y)) != -1) {
		tn.setCursorPosition(n);
		return(true);
	}

	noButton = getButton(x, y);
	if ((noButton = getButton(x, y)) == -1)
		return(true);

	paintButton(buttonStr[noButton], buttonX[noButton], false);
	return(true);
    }

    /*
     * mouseDrag:
     */
    public boolean mouseDrag(Event evt, int x, int y) {
	int		n;

	if ((g = getGraphics()) == null)
		return(true);
	g.setFont(font);


	if (tnShow == false)
		return(true);

	if ((n = get3270Position(x, y)) != -1) {
		tn.setCursorPosition(n);
		return(true);
	}

	if ((n = getButton(x, y)) == noButton)
		return(true);

	if (noButton != -1)
		paintButton(buttonStr[noButton], buttonX[noButton], true);

	if ((noButton = n) == -1)
		return(true);

	paintButton(buttonStr[noButton], buttonX[noButton], false);

	return(true);
    }

    /*
     * mouseUp:
     */
    public boolean mouseUp(Event evt, int x, int y) {
	int		no;

	if ((g = getGraphics()) == null)
		return(true);
	g.setFont(font);

	if (tnShow == false) {
		if (noMessage != MSG_HELP)
			return(true);

		tnShow = true;
		noMessage = -1;

		paint3270();
		return(true);
	}

	if ((no = noButton) == -1)
		return(true);
	noButton = -1;

	paintButton(buttonStr[no], buttonX[no], true);

	synchronized(tn) {
		try {
			if (no < 13) {
				tn.keyPF(no);
				return(true);
			}

			if (14 < no && no < 27) {
				tn.keyPF(no - 3);
				return(true);
			}

			switch(no) {
			case 27: case 28: case 29:
				tn.keyPA(no - 27);
				return(true);
			case 33:
				tn.keyClear();
				return(true);
			}
		}
		catch(IOException e) {
			stop(null);
			return(true);
		}

		switch(no) {
		case 13:
			buttonFctLower = false;
			g.setColor(color_bg);
			g.fillRect(6, status_y + 3, size().width - 12, sy + 1);
			paint3270Buttons();
			return(true);
		case 14:
			buttonFctLower = true;
			g.setColor(color_bg);
			g.fillRect(6, status_y + 3, size().width - 12, sy + 1);
			paint3270Buttons();
			return(true);
		case 30:
			tn.keyDeleteAllInput();
			return(true);
		case 31:
			tn.keyToggleMode();
			return(true);
		case 32:
			tn.keyReset();
			return(true);
		case 34:
			help();
			return(true);
		}
	}

	return(true);
    }

    /*
     * gotFocus:
     */
    public boolean gotFocus(Event evt, Object what) {

	if ((g = getGraphics()) == null)
		return(true);
	g.setFont(font);

	if (tnShow == false)
		return(true);
	tn.repaintCursor();
	return(true);
    }

    /*
     * lostFocus:
     */
    public boolean lostFocus(Event evt, Object what) {

	if ((g = getGraphics()) == null)
		return(true);
	g.setFont(font);

	if (tnShow == false)
		return(true);

	tn.eraseCursor();
	return(true);
    }

    /*
     * start:
     */
    public void start() {

	setWindowMessage(MSG_LOADING);
	tn = new Tn3270(2, this);

	setWindowMessage(MSG_TRYING);

	try {
		if (gateway == null)
			tn.openConnection(hostname, 23);
		else
			tn.openConnection(gateway, gatewayPort);
	}
	catch(IOException e) {
		stop(null);
		return;
	}

	setWindowMessage(MSG_CONNECTED);
	tnShow = true;

	netInputThread = new Thread(this);
	netInputThread.start();
    }

    /*
     * stop:
     */
    public void stop(String str) {

	try {
		if (tn != null)
			tn.closeConnection();
	}
	catch(IOException e) {
	}

	tn = null;
	tnShow = false;

	if (str == null)
		setWindowMessage(MSG_CLOSED_REMOTE);
	else
		setWindowMessage(str);

	netInputThread.stop();
    }

    /* ================================================================	*
     * Implements Runnable: netInputThread				*
     * ================================================================ */

    public void run() {
	short		buf[] = new short[2048];
	int		n;

	try {
		for(;;) {
			if ((n = tn.readInputStream(buf)) == -1)
				break;

			synchronized(tn) {
				tn.processInputStream(buf, n);
			}

			System.out.flush();
		}

		if (n != -1)
			tn.closeConnection();
	}
	catch(IOException e) {
		stop(e.getMessage());
		return;
	}

	tn = null;
	tnShow = false;

	setWindowMessage(MSG_CLOSED_REMOTE);
    }

    /* ================================================================	*
     * Window 3270							*
     * ================================================================ */

    /*
     * paint3270:
     */
    private void paint3270() {

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);
	
	// Draw background
	g.setColor(color_bg);
	g.fillRect(3, 3, size().width - 6, size().height - 6);

	paint3270Buttons();
	paint3270Status();

	try {
		synchronized(tn) {
			tn.repaintScreen();
		}
	}
	catch(Exception e) {
		paintWindowMessage();
	}
    }

    /*
     * paint3270Buttons:
     */
    private void paint3270Buttons() {
	int		i;

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);
	
	// Draw status lines
        g.setColor(Color.orange);
	g.drawLine(7, status_y, size().width - 8, status_y); 

	if (buttonFctLower)
		for(i = 0; i < 14; ++i)
			paintButton(buttonStr[i], buttonX[i], true);
	else
		for(i = 14; i < 27; ++i)
			paintButton(buttonStr[i], buttonX[i], true);

	for(i = 27; i < 35; ++i)
		paintButton(buttonStr[i], buttonX[i], true);
    }

    /*
     * get3270Position:
     */
    private int get3270Position(int x, int y) {
	int		col, row;

	if ((x -= 3) < 0 || (y -= 3) < 0)
		return(-1);

	if ((col = x / sx) > 79 || (row = y / sy) > 23)
		return(-1);

	return(80 * row + col);
    }

    /*
     * paint3270Status:
     */
    public void paint3270Status() {
	int		x, y, l;

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);
	
	x = 60 * sx;
	y = status_y + sy + 5;
	l = 11 * sx;

	if (status_str == null) {
		g.setColor(color_bg);
		g.fillRect(x, y, l + 1, sy + 1);
		return;
	}

	g.setColor(Color.lightGray);
	g.drawRect(x, y, l, sy);

	if (status_str.compareTo("INSERT ON") != 0)
		g.setColor(Color.red);
	else
		g.setColor(Color.blue);
	g.fillRect(x + 1, y + 1, l - 1, sy - 1);

	x += (11 - status_str.length()) * sx / 2;
	g.setColor(Color.white);
	g.drawString(status_str, x, y + base_line + 1);
    }

    /* ================================================================	*
     * Window Message							*
     * ================================================================ */

    /*
     * setWindowMessage:
     */
    private void setWindowMessage(int no) {

	noMessage = no;
	paintWindowMessage();
    }

    private void setWindowMessage(String str) {

	StrMessage = str;
	setWindowMessage(MSG_STRING);
	paintWindowMessage();
   }

    /*
     * paintWindowMessage:
     */
    private void paintWindowMessage() {
	String		message;
	int		x, y;

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);
	
	g.setColor(color_bg);
	g.fillRect(3, 3, size().width - 6, size().height - 6);

	switch(noMessage) {
	case MSG_LOADING:
		message = "Loading Tn3270 package ...";
		break;
	case MSG_TRYING:
		message = "Trying " + hostname + " ...";
		break;
	case MSG_CONNECTED:
		message = "Connected to " + hostname + ".";
		break;
	case MSG_CLOSED_REMOTE:
		message = "Connection closed by foreign host ...";
		break;
	case MSG_STRING:
		message = StrMessage;
		break;
	case MSG_HELP:
		paintWindowHelp();
		return;
	default:
		return;
	}

	x = ((80 - message.length()) / 2) * sx + dx;
	y = 10 * sy + dy;

	g.setColor(color_bg);
	g.fillRect(dx, y - base_line, sx * 80, sy);
	g.setColor(color_fg);
	g.drawString(message, x, y);
    }

    /* ================================================================	*
     * Help								*
     * ================================================================ */

     private void help() {

	tnShow = false;
	setWindowMessage(MSG_HELP);
     }

    /*
     * paintWindowHelp:
     */
    private void paintWindowHelp() {
	String		str;
	int		x, y, len;

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);
	
	g.setColor(Color.cyan);
	paintMsgHelpCenter("WELCOME TO JAVA-3270", 1);

        g.setColor(Color.green);
	g.drawLine(7, 2 * sy, size().width - 8, 2 * sy); 

	g.setColor(Color.yellow);
	paintMsgHelp("          Cursor-Up:  <Ctrl-W> or <Up>", 3);
	paintMsgHelp("        Cursor-Down:  <Ctrl-Z> or <Down>", 4);
	paintMsgHelp("        Cursor-Left:  <Ctrl-K> or <Left>", 5);
	paintMsgHelp("       Cursor-Right:  <Ctrl-L> or <Right>", 6);
	paintMsgHelp("        Forward Tab:  <Ctrl-F> or <Tab>", 7);
	paintMsgHelp("       Backward Tab:  <Ctrl-B> or <Shift-Tab>", 8);
	paintMsgHelp("        Cursor-Home:  <Ctrl-Q> or <Home>", 9);
	paintMsgHelp("    Cursor-New Line:  <Ctrl-N>", 10);
	paintMsgHelp("              Clear:  <Ctrl-C>", 12);
	paintMsgHelp("              Reset:  <Ctrl-R>", 13);
	paintMsgHelp("             Delete:  <Ctrl-E> or <Del>", 14);
	paintMsgHelp("   Delete Left Char:  <BackSpace>", 15);
	paintMsgHelp("Delete End of Field:  <Ctrl-P> or <End>", 16);
	paintMsgHelp("              Enter:  <Return>", 17);
	paintMsgHelp("         Field Mark:  <Ctrl-M>", 18);

	paintMsgHelp("            F1..F12:  <F1>..<F12>", 20);
	paintMsgHelp("           F11..F22:  <Shift-F1>..<Shift-F12>", 21);
	paintMsgHelp("           PA1..PA2:  <Meta-F1>..<Meta-F2>", 22);

	str = " Press <Return> key or a Mouse button to continue ... ";
	len = (str.length() + 2) * sx - 1;
	x = (size().width - len) / 2;
	y = 23 * sy + sy / 2;

	g.setColor(Color.gray);
	g.fill3DRect(x, y, len, sy + 2, true);

	g.setColor(Color.black);
	g.drawString(str, x + sx + 1, y + base_line + 2);
	g.setColor(Color.orange);
	g.drawString(str, x + sx, y + base_line + 1);

	// Draw signature
	y = size().height - sy - base_line + 2;
	g.setColor(Color.green);
	g.drawLine(7, y, size().width - 8, y); 

	str = "Version 1.2, © Jean-Marc Naef, Geneva, Switzerland";
	g.setColor(Color.cyan);
	g.drawString(str, (size().width - str.length() * sx) / 2, y + base_line + 3);
    }

    /*
     * paintMsgHelp:
     */
    private void paintMsgHelp(String str, int y) {

	g.drawString(str, 18 * sx, y * sy + sy / 2);
    }

    /*
     * paintMsgHelpCenter:
     */
    private void paintMsgHelpCenter(String str, int y) {

	g.drawString(str, (size().width - str.length() * sx) / 2, y * sy + sy / 2);
    }

    /*
     * displayInformation:
     */
    private void displayInformation(int width, int height) {
	String		list[];
	int		i;

	System.out.println("--------------------------------");
	System.out.println("Applet width:  " + width);
	System.out.println("Applet height: " + height);
	System.out.println("Names of availables fonts:");

	list = getToolkit().getFontList();
	for(i = 0; i < list.length; ++i)
		System.out.println("  - " + list[i]);
	
	System.out.println("--------------------------------");
    }

    /* ================================================================	*
     * Buttons								*
     * ================================================================ */

    boolean		buttonFctLower = true;

    static String	buttonStr[] = {
	"PF1", "PF2", "PF3", "PF4", "PF5", "PF6", "PF7", "PF8", "PF9", "PF10",
	"PF11", "PF12", "PF13", "PF13 - 24", "PF1-12", "PF13", "PF14",
	"PF15", "PF16", "PF17", "PF18", "PF19", "PF20", "PF21", "PF22",
	"PF23", "PF24", "PA1", "PA2", "PA3", "ERASE INPUT", "INSERT MODE",
	"RESET", "CLEAR", "HELP"
    };

    static int		buttonX[] = {
	0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 51, 57, 63, 69,
	0, 8, 14, 20, 26, 32, 38, 44, 50, 56, 62, 68, 74,
	80, 85, 90, 95, 108, 121, 128, 154
    };

    static int		buttonEnd[] = {
	5, 10, 15, 20, 25, 30, 35, 40, 45, 51, 57, 63, 69, 80,
	8, 14, 20, 26, 32, 38, 44, 50, 56, 62, 68, 74, 80,
	85, 90, 95, 108, 121, 128, 135, 160
    };

    /*
     * getButton
     */
    private int getButton(int x, int y) {
	int		i, pos;


	if ((x -= 7) < 0)
		return(-1);

	if ((pos = x / sx) > 79)
		return(-1);

	if ((y -= status_y + 4) < 0)
		return(-1);

	if ((y -= sy) >= 0) {
		if ((y -= 2) < 0)
			return(-1);

		if ((y -= sy) >= 0)
			return(-1);

		pos += 80;
	}

	if (pos > 79) {
		for(i = 27; i < 35; ++i)
			if (buttonX[i] <= pos && pos < buttonEnd[i])
				return(i);
		return(-1);
	}
		
	if (buttonFctLower) {
		for(i = 0; i < 14; ++i)
			if (pos < buttonEnd[i])
				return(i);
		return(-1);
	}

	for(i = 14; i < 27; ++i)
		if (pos < buttonEnd[i])
			return(i);
	return(-1);
    }

    /*
     * paintButton:
     */
    private void paintButton(String str, int x, boolean on) {
	int		px, py, len;

	if (str == null)
		return;

	py = status_y + 3 + (sy + 2) * (x / 80);

	px = (x % 80) * sx + 6;
	len = (str.length() + 2) * sx - 1;

	if (on) {
		g.setColor(button_bg);
		g.fill3DRect(px, py, len, sy + 1, true);

		g.setColor(Color.black);
		g.drawString(str, px + sx + 1, py + base_line + 2);
		g.setColor(Color.yellow);
		g.drawString(str, px + sx, py + base_line + 1);
		return;
	}

	g.setColor(color_bg);
	g.fill3DRect(px, py, len, sy + 1, false);

	g.setColor(Color.orange);
	g.drawString(str, px + sx, py + base_line + 1);
    }

    /*
     * paintCursor:
     */
    public void paintCursor(int x, int y, byte c, int color, boolean on) {
	int		cursor_x, cursor_y;
	byte		tab[] = new byte[1];

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);

	cursor_x = x * sx + dx;
	cursor_y = y * sy + dy;
	tab[0] = c;

	if (on) {
		/*
		 * Paint cursor.
		 */
		if (color == COLOR_BG) {
			g.setColor(color_input);
			g.drawRect(cursor_x, cursor_y - base_line, sx, sy - 1);
			return;
		}

		g.setColor(color_input);
		g.fillRect(cursor_x, cursor_y - base_line, sx + 1, sy);

		g.setColor(color_bg);
		g.drawBytes(tab, 0, 1, cursor_x, cursor_y);

		return;
	}

	/*
	 * Erase cursor.
	 */
	g.setColor(color_bg);
	g.fillRect(cursor_x, cursor_y - base_line, sx + 1, sy);

	switch(color) {
	case COLOR_FG:
		g.setColor(color_fg);
		break;
	case COLOR_INPUT:
		g.setColor(color_input);
		break;
	case COLOR_BOLD:
		g.setColor(color_bold);
		break;
	case COLOR_BG:
		return;
	}

	g.drawBytes(tab, 0, 1, cursor_x, cursor_y);
    }

    /*
     * paintStatus:
     */  
    public void paintStatus(String str) {

	if (str == status_str)
		return;

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);

	status_str = str;
	paint3270Status();
    }

    /*
     * paintString:
     */
    public void paintString(int x, int y, int color, byte tmp[], int len) {
	int		px, py;

	if ((g = getGraphics()) == null)
		return;
	g.setFont(font);

	// System.out.println("paintString: x=" + x + " y=" + y + " color=" +
	//		   color + " >" + new String(tmp, 0, 0, len) + "<");

	px = x * sx + dx;
	py = y * sy + dy;

	g.setColor(color_bg);
	g.fillRect(px, py - base_line, sx * len, sy);

	switch(color) {
	case COLOR_FG:
		g.setColor(color_fg);
		break;
	case COLOR_INPUT:
		g.setColor(color_input);
		break;
	case COLOR_BOLD:
		g.setColor(color_bold);
		break;
	case COLOR_BG:
		return;
	}

	g.drawBytes(tmp, 0, len, px, py);
    }
}
