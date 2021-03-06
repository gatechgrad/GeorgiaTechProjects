/*
 * DemoApplet3270:
 *    This applet uses the Java3270 libraries to interrogate the SIBIL
 *    database on the IBM-3270.
 *
 *    The graphic inreface is composed of 3 elements:
 *		- a window for the results.
 *		- a window for the input.
 *		- a button to commence the search.
 *
 *    A thread is also created so as to carry out searches in "background".
 */

import java.awt.*;
import java.applet.Applet;
import java.net.Socket;
import java.io.IOException;

import tn3270.*;

public class DemoApplet3270 extends Applet implements Runnable,Tn3270paint {
    /*
     * implements Runnable:
     *		- creates a thread that will execute a method 'run'  
     *            defined in this object.
     *
     * implements Tn3270paint:
     *		- defines the 3 methods that process the events 
     *		  received from the IBM-3270:
     *			- paintString()
     *			- paintCursor()
     *			- paintStatus()
     *		  These 3 methods are called by the method
     *		  processInputStream.
     */
   
    /*
     * The method paintCursor returns the position of the cursor and 
     * stores it in variables x3270 and y3270.
     */
    int                 x3270, y3270;

    /*
     * The method paintStatus returns the status of the 3270 connection 
     * and stores it in the variable status3270.
     */
    String              status3270;

    /*
     * The contents of the 3270 screen is generated by the method 
     * paintString and is stored in the variable screen3270.
     */
    byte                screen3270[];

    Tn3270              tn;

    boolean             thread3270isActive;
    Thread              thread3270;


    TextArea            textArea;
    TextField           textField;
    Button              button;

    /*
     * DemoApplet3270: defintion of the graphical interface
     */
    public DemoApplet3270() {
        Panel           p;

        setLayout(new BorderLayout());
        textArea = new TextArea();
        add("Center", textArea);

        p = new Panel();
        textField = new TextField("Einstein,Albert", 30);
        textArea.setFont(new Font("Courier", Font.PLAIN, 12));

        button = new Button(" Search by Author");

	/*
         * As long as the connection is not open this button 
	 * will not work.
	 */
        button.disable();

        p.add("West", textField);
        p.add("East", button);
        add("South", p);

        thread3270isActive = true;
    }

    /*
     * Method that enables the start of a new search when the user 
     * clicks on the button
     */
    public boolean handleEvent(Event evt) {
        String          cmd;

        if (evt.target != button || evt.id != Event.ACTION_EVENT)
                return(false);

	/*
         * No new search possible, during the current search ...
	 */
        button.disable();

	/*
	 * Generation of the command to be sent to the IBM
	 */
        cmd = "1/s " + textField.getText();
        showStatus("Search command: " + cmd);
        textArea.setText("");

	/*
	 * The thread must be activated for a new search
         */
        thread3270isActive = true;
        thread3270.resume();

        try {
		/*
		 * Search command sent to the IBM.
		 */
                tn.insertString(cmd);
                tn.keyEnter();
                return(true);
        }
        catch(IOException e) {
        }

        return(true);
    }

    public void paintCursor(int x, int y, byte c, int color, boolean on) {

        if (on == false)
                return;
        x3270 = x;
        y3270 = y;
    }

    public void paintString(int x, int y, int color, byte buf[], int len) {
        int             i, dst;

        dst = y * 80 + x;
        for(i = 0; i < len; ++i) {
                screen3270[dst] = buf[i];
                ++dst;
        }
    }

    public void paintStatus(String str) {

        status3270 = str;
    }

    /*
     * Once the applet appears on the screen, open the connection 
     * to the IBM using an independant thread.
     */
    public void start() {

        thread3270isActive = true;
        thread3270 = new Thread(this);

	/*
	 * starts the method 'run'
	 */
        thread3270.start();
    }

   /*
    * when the applet is no longer on the screen, disconnect the 
    * connection to the IBM.
    */
    public void stop() {

        try {
                if (tn != null)
                        tn.closeConnection();
        }
        catch(IOException e) {
        }
        tn = null;
    }

    /*
     * Processing the connection with the IBM.
     */
    public void run() {

        screen3270 = new byte[1920];   /* 1920 = 80x24 */
        tn = new Tn3270(2, this);

        try {
            showStatus("connect to sibil.unige.ch ...");
            tn.openConnection("www.unige.ch", 8500);

	    /*
	     * The data from the IBM must be interpreted in order to 
	     * determine when the first screen is completely visualised
             * (data read by readDataUntil).
	     *
	     * This is the case when the cursor is at the position
	     * (25,21) on the screen and the character string 
             * "DATA ==>" is at position (39,21).
	     *
	     *		readDataUntil(25, 21, 39, 21, "DATA ==>");
	     *
	     * At that moment send <ENTER> to continue.
	     */
            showStatus("Waiting for the first screen ...");
            readDataUntil(25, 21, 39, 21, "DATA ==>");
            tn.keyEnter();

            showStatus("Waiting for the second screen ...");
            readDataUntil(6, 22, 4, 19, "<ENTER> druecken   pressez <ENTREE>");
            tn.keyEnter();

            showStatus("Waiting for the SIBIL menu ...");
            readDataUntil(6, 22, 5, 20, "Tapez l'un des chiffres");

            for(;;) {
                showStatus("OK ...");
                button.enable();

		/*
		 * Thread stopped awaiting next command.
		 *
		 * The thread is reactivated in the method handleEvent
		 */
                thread3270isActive = false;
                while(thread3270isActive == false)     // Bug Windows NT
                        thread3270.suspend();
                
                for(;;) {
                        readDataUntil(6, 22, 1, 22, "===>");
                        insertResult();

			/*
			 * If the character 'd' is at line 22 and
			 * column 6, read a new screen 
			 */
                        if (screen3270[22 * 80 + 6] != 'd')
                                break;

                        tn.insertString("d");
                        tn.keyEnter();
                }
            }
        }
        catch(IOException e) {
        }
    }

    /*
     * readDataUntil:
     *
     * This method reads data from the IBM untill the cursor is in
     * position (x,y) and the character string "str" is in  
     * position (col,row).
     */
    public void readDataUntil(int x, int y, int col, int row, String str)
    throws IOException {
        short           buf[] = new short[2048];
        byte            value[] = new byte[100];
        int             i, j, len, n;

        len = str.length();
        str.getBytes(0, len, value, 0);

        for(;;) {
		/*
		 * read data from the IBM
		 */
                n = tn.readInputStream(buf);

		/*
		 * analyse the events.
		 *
		 * This function calls the methods 
		 * paintString, paintCursor & paintStatus.
		 */
                tn.processInputStream(buf, n);

                if (x3270 != x || y3270 != y)
                        continue;
 
                j = row * 80 + col;
                for(i = 0; i < len; ++i) {
                        if (value[i] != screen3270[j])
                                break;
                        ++j;
                }
 
                if (i == len)
                        return;
        }
    }

    public void insertResult() {
        String          str;
        int             i;

        for(i = 6; i < 17; ++i) {
                str = new String(screen3270, 0, 80 * i, 79);

                textArea.appendText(str);
                textArea.appendText("\n");
        }
        
    }
}
