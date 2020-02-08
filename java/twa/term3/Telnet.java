//############################################################################
//# FILE Telnet.java
//# VERSION 0.94
//# DATE: 4/27/96
//# AUTHOR:  Bret Dahlgren (bret@lm.com) based on dummy telnet terminal code
//#          by Jamie Cameron (jameson@ncb.gov.sg)
//#
//# Ver. 0.94   4/27/96 Couple of changes for IAC and a new parameter.
//#                   Fixed up IAC ECHO call and terminal emulation will
//#                   be sent if ANSI is not acceptable.  Probably will
//#                   have to revisit this. Also now send correct value
//#                   for terminal size.
//#                   NEW parameter:
//#                   <param name="fields" value="OFF">
//#                   If value == "OFF" the Host, Port, and Emulation fields
//#                   will not be shown locking those values to those passed
//#                   as parameters.
//# Ver. 0.93   4/12/96 The platform specific bugs are starting to show.
//#                   Windows NT and BSD/OS think the width of the characters
//#                   are twice what they really are. Cut them in half. If 
//#                   this bug is fixed the change will be bypassed.
//#                   NT was acting strangely by not showing the last two
//#                   characters on the screen.  I put in a
//#                   System.out.println("") and the problem went away.  This
//#                   is a stop gap measure and will be taken out when a
//#                   real solution is found or the bug is fixed.
//#                   I was able to fix up the top of the screen for BSD/OS
//#                   and Windows NT so all the buttons appear now.  Please
//#                   send me screen shots of your telnet with your os.name
//#                   if you can't see the connect and disconnect buttons. 
//# Ver. 0.91a  3/14/96 Telnet IAC added Authentication Option code.  Expect
//#                   a few of these changes while other people try it with
//#                   their particular telnet daemons.
//# Ver. 0.91   3/12/96 Found a way to get around the period problem.  
//#                   Use KeyUp to catch the period keys.
//# Ver. 0.90   2/28/96 Initial release.
//#
//# Copyright (c) 1996 Bret G. Dahlgren, All Rights Reserved.
//############################################################################
import java.net.*;
import java.awt.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;

//############################################################################
//# CLASS: Telnet
//#   Applet which sets up the Panal and canvas for the Telnet Client and the
//#   input text fields of Host, Port, and Emulator.
//#
//# HTML tag:
//#   <applet code="Telnet.class" width="XXX" height="XXX">
//#   <param name="host" value="XXXX">
//#   <param name="port" value="XXXX">
//#   <param name="emulation" value="XXXX">
//#   <param name="fields" value="XXX">
//#   </applet>
//#
//# HTML tag NOTES
//#   width - width of the applet.  For 80 chars (on Win95) in Courier 11 use 560.
//#   height - height of the applet.  For 25 lines (on Win95) in Courier 11 use 390.
//#   host - host to connect to initially.  For Netscape use you should have
//#          the name of the host the code is on so there is not a socket
//#          security error on startup.  May be host name or IP address.
//#   port - port to connect to initially.  Standard telnet port is 23.
//#   emulation - The emulation to use initially.  This emulation is loaded
//#          on the fly.  ANSI is loaded first even if you are not using it
//#          for initializing purposes.
//#   fields - boolean value.  if fields == "OFF" the host, port, and emulation
//#          fields will not appear on the applet.
//#
//# DESIGN NOTES:
//#   This applet still runs even if you leave the page.  I've noticed a need
//#   to leave the page while it's still receiving data to look at something
//#   else and come back to my same session.  So start() only starts a session
//#   on the first time it comes up.  If you resize the page or leave and come
//#   back the session will still be up.
//#
//# METHOD: init
//#   first procedure called.  Grabs parameters. Sets up Layout and creates
//#   the Canvas for the telnet session.
//#
//# METHOD: con
//#    Tells the telnet client to disconnect and reconnect the new host.
//#
//# METHOD: start
//#    Connects on initialization, but won't reconnect on every start() 
//#    call which happens on changing pages or even resizing Netscape.
//#
//# METHOD: action
//#  Gets the read from the two buttons and acts accordingly.
//#
//############################################################################
public class Telnet extends java.applet.Applet
{
 String hoststr, portstr,emstr; // parameters giving host & port
 String fieldson;
 Panel controls;
 TelnetClient tn;              // telnet component
 TextField hostfield;
 TextField portfield;
 TextField emfield;
 Button connect, disconnect;
 boolean firsttime = true;
 String UserOs = System.getProperty("os.name");

 public Telnet()
   {
   // Set up interface
   /*
   hoststr = getParameter("host");
   portstr = getParameter("port");
   emstr   = getParameter("emulation");
   fieldson = getParameter("fields");
   */

   hoststr = "indexbbs.com";
   portstr = "23";
   emstr   = "VT100";
   fieldson = "ON";


   if (hoststr == null) hoststr = "127.0.0.1";
   if (portstr == null) portstr = "23";
   if (emstr == null) emstr = "ANSI";
   if (fieldson == null) fieldson = "ON";

   // Create interface
     setLayout(new BorderLayout());
     add("Center",tn = new TelnetClient());
     add("North",controls = new Panel());
     controls.setLayout(new FlowLayout());

   if (fieldson.compareTo("OFF") == 0)
     {
     controls.add(connect = new Button("Connect"));
     controls.add(disconnect = new Button("Disconnect"));
     return;
     }

   if (UserOs.compareTo("Windows NT") == 0)
     {
     controls.add(new Label("Host "));
     controls.add(hostfield = new TextField(10));
     hostfield.setText(hoststr);
     controls.add(new Label("Port "));
     controls.add(portfield = new TextField(3));
     portfield.setText(portstr);
     controls.add(new Label("Emulation "));
     controls.add(emfield = new TextField(7));
     emfield.setText(emstr);
     controls.add(connect = new Button("Connect"));
     controls.add(disconnect = new Button("Disconnect"));
     }
   else if (UserOs.compareTo("BSD/OS") == 0)
     {
     controls.add(new Label("Host"));
     controls.add(hostfield = new TextField(10));
     hostfield.setText(hoststr);
     controls.add(new Label("Port"));
     controls.add(portfield = new TextField(2));
     portfield.setText(portstr);
     controls.add(new Label("Emulation"));
     controls.add(emfield = new TextField(4));
     emfield.setText(emstr);
     controls.add(connect = new Button("Connect"));
     controls.add(disconnect = new Button("Disconnect"));

     }
   else
     {
     controls.add(new Label("Host "));
     controls.add(hostfield = new TextField(15));
     hostfield.setText(hoststr);
     controls.add(new Label("Port "));
     controls.add(portfield = new TextField(3));
     portfield.setText(portstr);
     controls.add(new Label("Emulation "));
     controls.add(emfield = new TextField(12));
     emfield.setText(emstr);
     controls.add(connect = new Button("Connect"));
     controls.add(disconnect = new Button("Disconnect"));
     }

     start();
   }

 public void con()
   {
   int port;
   // Don't try if the telnet client doesn't exist.
   if (tn == null)
     return;
  
   tn.disconnect();

   if (fieldson.compareTo("OFF") == 0)
     {
     if (portstr.equals(""))
       port = 23;
     else 
       port =  Integer.parseInt(portstr);

     tn.connect(hoststr,port, emstr);
     return;
     }

   // Integer.parseInt blows up if the string is null, that's not right :-)
   if (portfield.getText().equals(""))
     port = 23;
   else {
     port =  Integer.parseInt(portfield.getText());
     }
   tn.connect(hostfield.getText(),port, emfield.getText());
   }

 public void start()
   {

   if (firsttime == true)
   con();

   firsttime = false;
   }

 public boolean action(Event e, Object arg)
   {
   if (e.target == connect)
     con();
   else if (e.target == disconnect)
     tn.disconnect();
   return true;
   }


  public static void main(String argv[]) {
    JFrame theFrame = new JFrame();
    theFrame.setSize(640, 480);
    theFrame.setLocation(50, 50);
    theFrame.add(new Telnet());
    theFrame.show();
  }


}


//############################################################################
//# CLASS: TelnetClient
//#   Actual class which does the telnet work.  It connects to the given host
//#   and port.  It also loads the emulation class.
//#
//# METHOD: reshape
//#   called once in Netscape to size the applet.  Appletviewer can be resized
//#   and this is called everytime the size is changed.
//#
//# METHOD: connect
//#   called from Telnet to connect to the host and also loads the emulation
//#   class givenemul.
//#
//# METHOD: disconnect
//#   stops the TelnetClient thread and kills the connection.
//#
//# METHOD: clearch
//#   calls the emulation clearscreen and repaints the screen.
//#
//# METHOD: keyDown
//#   called on a key press.  calls the emulator.transmitch which will send
//#   it in the out stream after any changes the emulator might make.
//#
//# METHOD: keyUp
//#   used to catch the period key '.' because of the bug in keyDown.
//#
//# METHOD: mouseDown
//#   gets focus for keyDown when mouse is clicked in applet.
//#
//# METHOD: paint
//#   called when screen needs refreshed.
//#
//# METHOD: update
//#   overrides super.update so there is no flicker.
//#
//# METHOD: run
//#   reads and displays the input stream in an endless loop.
//#
//# METHOD: readch
//#   reads the stream one character at a time.  Used in rare occasion.
//#
//# METHOD: readstream
//#   reads the input stream into a byte array.
//#
//# METHOD: shutdown
//#   terminates the connection.
//#
//# METHOD: displaystream
//#   calls the emulator to display the stream.  if a telnet command 
//#   is seen telnetparse is called.  Note that in a byte array anything
//#   after 127 is negative so adjustments were made.
//#
//# METHOD: telnetparse
//#   responds to telnet commands that are sent on initial connection.
//#
//# METHOD: display
//#   displays a string to the telnet window.
//#
//# METHOD: displaych
//#   displays a character to the telnet window. the telnet code is not used
//#   anymore.
//#
//# METHOD: transmit
//#   transmits a string through the pipe.
//#
//# METHOD: transmitch
//#   transmits a character through the pipe.
//#
//############################################################################
class TelnetClient extends java.awt.Canvas implements Runnable
 {
  boolean debug = false;
  String host;                    // remote host
  int port;                       // remote port
  Socket s;                       // connection to remote host
  InputStream in;                 // data from other end
  OutputStream out;               // data to other end
  Thread th;                      // thread for reading data
  Font fn;                        // current font
  Image back;                // backing image
  int x, y;                   // cursor position (in chars)
  int chw,chh;                    // size of a char (in pixels)
  int chd;                        // offset of char from baseline
  int width,height;            // size of applet (in pixels)
  int w,h;                        // size of applet (in chars)
  Graphics gr,bgr;            // front and back graphics
  Emulation emulator;

  String term = "ANSI";  // what this terminal claims to be
  String Cterm;            // class emulator
  boolean firstterm = true;
  boolean echo;      // echo keys sent?
  boolean periodwork = false;
  String UserOs = System.getProperty("os.name");  // For platform specific bugs.


  public void reshape(int nx, int ny, int nw, int nh)
    {

    if (nw != width || nh != height) {
      width = nw;
      height = nh;
     
      // Open font
      gr = getGraphics();
      fn = new Font("Courier",Font.PLAIN,11);
      if (fn != null) gr.setFont(fn);
      FontMetrics fnm = gr.getFontMetrics();
      chw = fnm.getMaxAdvance();
      chh = fnm.getHeight();
      chd = fnm.getDescent();

      // kludge for Windows NT and others which have too big widths
      if (chw + 1 >= chh)
        {
        chw = (chw + 1) / 2;
        }


      // work out size of drawing area
      h = nh / chh;
      w = nw / chw;

      // setup backing image
      back = createImage(width, height);
      bgr = back.getGraphics();
      bgr.setFont(fn);

      Emulation.initClass(this);

      try {

        emulator = (Emulation)Class.forName("ANSI").newInstance();
        }

      catch(Exception e) {
        e.printStackTrace();
        }

      clearch();
      }

    super.reshape(nx,ny,nw,nh);
    }

  void connect(String givenhost, int givenport, String givenemul)
    {
    host = givenhost; port = givenport;

    if (givenemul != Cterm )
      {
      try {
        emulator = (Emulation)Class.forName(givenemul).newInstance();
        }
      catch(Exception e) {
        e.printStackTrace();
        }
      Cterm = givenemul;
      }

    if (debug) System.out.println("Height = "+String.valueOf(h));
    if (debug) System.out.println("Width  = "+String.valueOf(w));

    // reset display
    clearch();
    echo = false;
    requestFocus();
    firstterm = true;

    // Open connection
    try {
      try {
        try {
          if ((s = new Socket(host,port)) == null) {
            display("Failed to connect to host "+host+"\n");
            return;
            }
          }
        catch(UnknownHostException e) {
          display("Host " + host + " not found\n");
          return;
          }
        }
      catch(IOException e) {
        display("Failed to connect to host "+host+"\n");
        return;
        }
      }
    catch (Exception e) {
      display("Security violation on socket for host "+host+"\n");
      return;
      }

    try {
      in = s.getInputStream();
      out = s.getOutputStream();
      }
    catch(IOException e) {
      if (debug) System.out.println("Failed to get stream from socket");
      System.exit(5);
      }
    display("Connected to "+host+"\n");
    if (debug) System.out.println("Connected to host");

    // Begin thread for reading data
    th = new Thread(this);
    th.start();
    }

  void disconnect()
  {
  if (th != null) {
    display("\nDisconnected from "+host+"\n");
    th.stop();
    th = null;
    s = null; in = null; out = null;
    }
  }

  void clearch()
  {
  emulator.clearscreen();
  repaint();
  return;
  }

  // keyDown
  // Called on keypress
  public boolean keyDown(Event e, int k)
    {
    if (out != null) {
      int kp = e.key;

      if (kp == '.')
        periodwork = true;

      // if ((kp == '`') && (periodwork == false)) kp = '.'; // bug in keyDown

      if (debug) System.out.println("Pressed key " + String.valueOf(kp));

      emulator.transmitch((char)kp);

      if (echo) {
        if (debug) System.out.println("Echo'd "+String.valueOf(kp));
        displaych((char)kp);
        }
      }
    return true;
    }


// this is a kludge in order to get the period key when there is a bug in keyDown
  public boolean keyUp(Event e, int k)
    {
    if (periodwork == true) return true;

    if (k != '.') return true;

    if (out != null) {

      if (debug) System.out.println("Pressed key " + String.valueOf(k));

      emulator.transmitch((char)k);

      if (echo) {
        if (debug) System.out.println("Echo'd "+String.valueOf(k));
        displaych((char)k);
        }
      }
    return true;

    }



  public boolean mouseDown(Event e, int x, int y)
    {
    requestFocus();
    //repaint();
    return true;
    }

  public void paint(Graphics g)
    {
    g.drawImage(back,0,0,this);
    }

   public void update(Graphics g)
     {
     g.drawImage(back,0,0,this);
     }

   public void run()
     {
     byte b[] = new byte[2000];
     int len;

     while(true)
       {
       len = readstream(b);
       displaystream(b,len);
       }
     }

   char readch()
     {
     int c = 0;
     try {
       c = in.read();
     } catch(IOException e) {
       shutdown();
     }

     if (c == -1) shutdown();
     if (debug) System.out.println("Got char "+String.valueOf(c)+" = "+String.valueOf((char)c));
     return (char)c;
     }

   int readstream(byte[] b)
     {
     int len = 0;
     try {
       len = in.read(b);
     } catch(IOException e) {
       shutdown();
     }
     if (len == -1) shutdown();
     return len;
     }

   // shutdown
   // Terminate connection
   void shutdown()
     {
     display("\nConnection closed\n");
     s = null; in = null; out = null;
     Thread.currentThread().stop();
     }

   // Display a stream of data
   void displaystream( byte b[], int len)
     {
     for (int i = 0; i <len; i++)
       {
       if (b[i] == 255 - 256)
         telnetparse(b,i,len);
       }
     emulator.displaybytes(b, len);
     if (UserOs.compareTo("Windows NT") == 0)
       {
       System.out.println("");
      }

     repaint();
     }


   void telnetparse(byte b[], int index, int len)
     {
     char cmd;
     char opt;
 
     if ( index+1 > len - 1)
       {
       cmd = readch();
       if (cmd == 240)
           {
           return;
           }
       opt = readch(); 
       }
     else
       {
       if (b[index+1] < 0)
         cmd = (char)((int)b[index+1] + 256);
       else
         cmd = (char)b[index+1];

       if (cmd == 240)
           {
           return;
           }

       if (index+2 > len - 1)
         {
         opt = readch();
         }
       else
         {
         if (b[index+2] < 0)
           opt = (char)((int)b[index+2] + 256);
         else
           opt = (char)b[index+2];
         }
       }

     if (debug) System.out.println("read opt " + (int)opt + " and cmd " + (int)cmd);

       switch(opt) {
         case 1:  // echo
            if (cmd == 251)
              {
              transmitch((char)255);
              transmitch((char)253);
              transmitch((char)1);
              echo = false;
              }
            else if (cmd == 252)
              {
              transmitch((char)255);
              transmitch((char)254);
              transmitch((char)1);
              echo = true;
              }
            else if (cmd == 253)
              {
              transmitch((char)255);
              transmitch((char)251);
              transmitch((char)1);
              }

            break;

         case 3: // supress go-ahead
            transmitch((char)255);
            transmitch((char)253);
            transmitch((char)opt);
            break;

         case 32: // Terminal speed
            transmitch((char)255);
            transmitch((char)251);
            transmitch((char)32);
            transmitch((char)255);
            transmitch((char)250);
            transmitch((char)32);
            transmitch((char)0);
            transmit("38400,38400");
            transmitch((char)255);
            transmitch((char)240);

            break;

         case 35: // X display location
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)35);
            break;

         case 33: // Remote Flow control
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)33);
            break;

         case 31: // Window size
            if (cmd == 253) {
              // IAC WILL terminal-type
              transmitch((char)255);
              transmitch((char)251);
              transmitch((char)31);

              transmitch((char)255);
              transmitch((char)250);
              transmitch((char)31);
              char stemp =(char)(w / 256);
              transmitch(stemp);
              stemp = (char)(w % 256);
              transmitch(stemp);
              stemp = (char)(h / 256);
              transmitch(stemp);
              stemp = (char)(h % 256);
              transmitch(stemp);
              transmitch((char)255);
              transmitch((char)240);
              }
            break;

         case 39:
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)39);
            break;

         case 36: // Environment option
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)36);
            break;

         case 24: // terminal type
            if (cmd == 253) {
              // IAC WILL terminal-type
              transmitch((char)255);
              transmitch((char)251);
              transmitch((char)24);
          }
            else if (cmd == 250) {
              // IAC SB terminal-type IS <term> IAC SE
              transmitch((char)255);
              transmitch((char)250);
              transmitch((char)24);
              transmitch((char)0);
              if (firstterm == true)
                {
                transmit(term);
                firstterm = false;
                }
              else
                {
                transmit(Cterm);
                }

              transmitch((char)255);
              transmitch((char)240);
              }
            break;

         case 37: // Authentication
            if (cmd == 253) {
              // IAC WILL terminal-type
              transmitch((char)255);
              transmitch((char)251);
              transmitch((char)37);

              // IAC SB 0000 IAC SE
              transmitch((char)255);
              transmitch((char)250);
              transmitch((char)37);
              transmitch((char)0);
              transmitch((char)0);
              transmitch((char)0);
              transmitch((char)0);
              transmitch((char)255);
              transmitch((char)240);
              }
            break;

         default: // Catch all
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)opt);
            break;

         }
     }


   // display
   // Display a string in the telnet window
   void display(String str)
   {
   int i;
   for(i=0; i<str.length(); i++)
     displaych(str.charAt(i));
   }

   // displaych
   // Display one character at the current cursor position
   void displaych(char c)
     {

     if ( c < 128) {
       // Some printable character
       emulator.displaych(c);
       repaint();
       }
     else if (c == 255) {
       // Telnet IAC
       char cmd = readch();
       if (cmd == 240)
           {
           return;
           }
       char opt = readch();
      switch(opt) {
         case 1:  // echo
             if (cmd == 251) echo = false;
             else if (cmd == 252) echo = true;
             break;
    
         case 3:  // supress go-ahead
            break;
    
         case 32:
            transmitch((char)255);
            transmitch((char)251);
            transmitch((char)32);
            transmitch((char)255);
            transmitch((char)250);
            transmitch((char)32);
            transmitch((char)0);
            transmit("38400,38400");
            transmitch((char)255);
            transmitch((char)240);
    
            break;
    
         case 35:
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)35);
            break;
    
         case 33:
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)33);
            break;
    
         case 31:
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)31);
            break;
    
         case 39:
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)39);
            break;
    
         case 36:
            transmitch((char)255);
            transmitch((char)252);
            transmitch((char)36);
            break;
    
         case 24: // terminal type
            if (cmd == 253) {
              // IAC WILL terminal-type
              transmitch((char)255);
              transmitch((char)251);
              transmitch((char)24);
    
              // IAC SB terminal-type IS <term> IAC SE
              transmitch((char)255);
              transmitch((char)250);
              transmitch((char)24);
              transmitch((char)0);
              transmit(term);
              transmitch((char)255);
              transmitch((char)240);
              }
            else if (cmd == 250) {
              while(readch() != 240);
              }
            break;
         }
       }
     }

   void transmit(String str)
     {
     int i;

     for(i=0; i<str.length(); i++)
       transmitch(str.charAt(i));
     }

   void transmitch(char c)
     {
     if (c == '\n') transmitch('\r');
     try {
       out.write((int)c);
       out.flush();
       }
     catch(IOException e) { }
   
     if (debug) System.out.println("Sent char " + String.valueOf((int)c) + " = " + String.valueOf(c));
     }



}
