/*
 * Tn3270tcp.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 *
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 */

package tn3270;

import	java.net.Socket;
import	java.io.InputStream;
import	java.io.OutputStream;
import	java.io.IOException;

class Tn3270tcp {

    /* ----------------------------------------------------------------	*
     * Constant definitions for the TELNET protocol.			*
     * ----------------------------------------------------------------	*/

    final static short IAC  = 255;	// interpret as command:
    final static short DONT = 254;	// you are not to use option
    final static short DO   = 253;	// please, you use option
    final static short WONT = 252;	// I won't use option
    final static short WILL = 251;	// I will use option
    final static short SB   = 250;	// interpret as subnegotiation
    final static short SE   = 240;	// end sub negotiation
    final static short EOR  = 239;	// end of record (transparent mode)

    /*
     * telnet options
     */
    final static short TELOPT_BINARY = 0;	// 8-bit data path
    final static short TELOPT_TTYPE  = 24;	// terminal type
    final static short TELOPT_EOR    = 25;	// end or record
    
    /*
     * sub-option qualifiers
     */
    final static short TELQUAL_IS   = 0;	// option is...
    final static short TELQUAL_SEND = 1;	// send option

    /*
     * telnet states
     */
    final static short STATE_DATA	= 0;	// receiving data
    final static short STATE_IAC	= 1;	// got an IAC
    final static short STATE_CMD	= 2;	// process telnet command
    final static short STATE_SUB_CMD	= 3;	// process telnet sub-command

   /* ----------------------------------------------------------------	*
    * Variables to manage a telnet socket				*
    * ----------------------------------------------------------------	*/

    private Tn3270		tn;
    private byte		modelNumber;
    private short		telnetCmd;
    private int			telnetState;
    private Socket		socket;
    private InputStream		inputStream;
    private OutputStream	outputStream;

    private short		inputBuf[];		// 3270 input buffer
    private int			inputBufLen;		// current position

    private short		inputSubBuf[];		// Sub-option buffer
    private int			inputSubBufLen;		// current position

    /*
     * Constructor: Tn3270tcp
     */
    Tn3270tcp(Tn3270 tn, byte modelNumber) {

	this.tn = tn;
	this.modelNumber = modelNumber;
	telnetState = STATE_DATA;

	inputBuf = new short[4096];
	inputBufLen = 0;

	inputSubBuf = new short[2];
	inputSubBufLen = 0;
    }
 
    /*
     * close:
     */
    void close() throws IOException {

	if (socket == null)
		return;

	socket.close();

	socket = null;
	inputStream = null;
	outputStream = null;
    }

    /*
     * open:
     */
    void open(String host, int port) throws IOException {

	close();

	socket = new Socket(host, port);
	inputStream = socket.getInputStream();
	outputStream = socket.getOutputStream();
    }

    /*
     * process:
     */
    void process(short netBuf[], int netBufLen) throws IOException {
	short		b;
	int		i;

	for(i = 0; i < netBufLen; ++i) {
		b = netBuf[i];

		switch (telnetState) {
		case STATE_DATA:		// normal data processing
			if (b == IAC)
				telnetState = STATE_IAC;
			else
				inputBuf[inputBufLen++] = b;
			break;
		case STATE_IAC:			// process a telnet command
			switch(b) {
			case IAC:		// escaped IAC, insert it
				inputBuf[inputBufLen++] = b;
				telnetState = STATE_DATA;
				break;
			case EOR:		// process accumulated input
				tn.streamProcessData(inputBuf, inputBufLen);
				inputBufLen = 0;
				telnetState = STATE_DATA;
				break;
			case WILL:
			case WONT:
			case DO:
			case DONT:
				telnetCmd = b;
				telnetState = STATE_CMD;
				break;
			case SB:		
				inputSubBufLen = 0;
				telnetState = STATE_SUB_CMD;
				break;
			}
			break;
		case STATE_CMD:
			processTelnetCmd(telnetCmd, b);
			telnetState = STATE_DATA;
			break;
		case STATE_SUB_CMD:	
			if (b != SE) {
				if (inputSubBufLen < 2)
					inputSubBuf[inputSubBufLen++] = b;
				break;
			}

			telnetState = STATE_DATA;

			if (inputSubBufLen != 2)
				break;

			if (inputSubBuf[0] != TELOPT_TTYPE)
				break;

			if (inputSubBuf[1] != TELQUAL_SEND)
				break;

			sendTerminalCmd();
			break;
		}
	}
    }

    /*
     * read:
     */
    int read(short buf[]) throws IOException {
	byte		netBuf[] = new byte[buf.length];
	int		i, n;

	n = inputStream.read(netBuf);

	for(i = 0; i < n; ++i)
		if ((buf[i] = netBuf[i]) < 0)
			buf[i] += 256;

	return(n);
    }

    /*
     * write:
     */
    void write(short buf[], int n) throws IOException {
	byte		netBuf[] = new byte[n + 2];
	int		i;

	for(i = 0; i < n; ++i)
		netBuf[i] = (byte)buf[i];

	netBuf[n] = (byte)IAC;
	netBuf[n + 1] = (byte)EOR;

	outputStream.write(netBuf);
    }
    
    /*
     * processTelnetCmd:
     */
    private void processTelnetCmd(short telnetCmd, short telnetOption)
    throws IOException {
	short		cmd;
	int		i;

	switch(telnetCmd) {
	case WILL:			// telnet WILL DO OPTION command
	case DO:			// telnet PLEASE DO OPTION command
		switch(telnetOption) {
		case TELOPT_BINARY:
		case TELOPT_EOR:
		case TELOPT_TTYPE:
			cmd = (telnetCmd == WILL) ? DO : WILL;

			if (setHistoryCmd(cmd, telnetOption))
				return;

			sendCmd(cmd, telnetOption);
			return;
		}

		cmd = (telnetCmd == WILL) ? DONT : WONT;

		sendCmd(cmd, telnetOption);
		return;
	case WONT:			// telnet WONT DO OPTION command
	case DONT:			// telnet PLEASE DON'T DO OPTION command
		switch(telnetOption) {
		case TELOPT_BINARY:
		case TELOPT_EOR:
		case TELOPT_TTYPE:
			throw new IOException("processCmd: " + telnetCmd);
		}
		return;
	}
    }

    /*
     * sendCmd:
     */
    private void sendCmd(short telnetCmd, short telnetOption)
    throws IOException {
	byte		buf[] = new byte[3];

	buf[0] = (byte)IAC;
	buf[1] = (byte)telnetCmd;
	buf[2] = (byte)telnetOption;

	outputStream.write(buf);
    }

    /*
     * sendTerminalCmd:
     */
    private void sendTerminalCmd() throws IOException {
	byte		buf[] = new byte[16];

	buf[0]  = (byte)IAC;
	buf[1]  = (byte)SB;
	buf[2]  = (byte)TELOPT_TTYPE;
	buf[3]  = (byte)TELQUAL_IS;
	buf[4]  = 'I';
	buf[5]  = 'B';
	buf[6]  = 'M';
	buf[7]  = '-';
	buf[8]  = '3';
	buf[9]  = '2';
	buf[10] = '7';
	buf[11] = '8';
	buf[12] = '-';
	buf[13] = (byte)('0' + modelNumber);
	buf[14] = (byte)IAC;
	buf[15] = (byte)SE;

	outputStream.write(buf);
    }

    /*
     * setHistoryCmd:
     */
    private boolean	will_history[] = { false, false, false };
    private boolean	do_history[] = { false, false, false };

    private boolean setHistoryCmd(short telnetCmd, short telnetOption) {
	boolean		history[];
	int		i;

	history = (telnetCmd == WILL) ? will_history : do_history;

	switch(telnetOption) {
	case TELOPT_BINARY:
		i = 0;
		break;
	case TELOPT_EOR:
		i = 1;
		break;
	default:
		i = 2;
		break;
	}

	if (history[i])
		return(true);

	history[i] = true;
	return(false);
    }
}
