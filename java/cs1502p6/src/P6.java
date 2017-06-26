import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * CS1502: Program #6
 *
 * <PRE>
 * Program 6, Electronic Keyboard
 *
 * Revisions:  1.0  Feb 9, 1999
 *                  Created class Typewriter
 * </PRE>
 *
 * @author <A HREF="mailto:gte187k@prism.gatech.edu">Levi D. Smith</A>
 * @version Version 1.0, Feb 9, 1999
 */

  //NOTE: I am using P6Toolkit.java writen by the CS1502 TAs
  //      I copied my play notes method in P6 to RecordNotes
  //      I used pg 340 out of the text as a guide while writing my file I/O

public class P6 extends Frame implements ActionListener, WindowListener {

//*** CONSTANTS
public static final boolean DEBUG = false;
public static final int OCTAVES = 2; //'Automagically' adds more keys
public static final int STARTINGOCTAVE = 3; //the octave to start at
public static final int WINDOWWIDTH = 300 * OCTAVES;
public static final int WINDOWHEIGHT = 200;
public static final String [] ARRNOTES = {"C ", "C#", "D ", "D#", "E ", "F ", "F#", "G ", "G#", "A ", "A#", "B "};

//The different kinds of buttons
public static final String RECORD = "Record";
public static final String PLAY = "Play";
public static final String RECORDFILE = "Record File";
public static final String LOADFILE = "Load File";
public static final String SUBMIT = "Submit";

//*** INSTANCE VARIABLES
private TextField txtNoteDisplay;
private TextField txtNotesToPlay;
private boolean isRecording;
private NotesRecorded myNotesRecorded;
private Button playButton;
private Button recordButton;
private Button playFileButton;
private Button recordFileButton;
private FileDialog myRecordDialog;



public P6 () {
  myNotesRecorded = new NotesRecorded();
  isRecording = false;
  Font myFont = new Font("arial", Font.BOLD, 12);
  myRecordDialog = new FileDialog(this, "Save File As...", FileDialog.SAVE);

  playButton = new Button (PLAY);
  recordButton = new Button (RECORD);
  recordButton.setActionCommand(RECORD);
  Button submitButton = new Button (SUBMIT);
  playFileButton = new Button (LOADFILE);
  recordFileButton = new Button (RECORDFILE);
  recordFileButton.setActionCommand(RECORDFILE);


  txtNoteDisplay = new TextField("", 2);
  txtNotesToPlay = new TextField("", 10);
  Label lblCurrentNote = new Label ("Current Note :");
  Label lblNotesToPlay = new Label ("Notes to Play :");

  Panel topPanel = new Panel();
  Panel bottomPanel = new Panel();
  Panel topPanel1 = new Panel();
  Panel topPanel2 = new Panel();
  Panel topPanel3 = new Panel();
  Panel topPanel4 = new Panel();
  Panel topPanel5 = new Panel();
  Panel topPanel6 = new Panel();
  Panel topPanel7 = new Panel();


  setBackground(new Color(128,128,128));
  setLayout (new GridLayout (2,1));

  //top Panel
  add(topPanel);
    topPanel.setLayout (new GridLayout(2, 1));  
    topPanel.add(topPanel1);
      topPanel1.setLayout(new GridLayout(1, 2));
        txtNoteDisplay.setEditable(false);
        txtNoteDisplay.setBackground(new Color(255,255,255));
        txtNoteDisplay.setFont(myFont);
        lblCurrentNote.setFont(myFont);
        txtNotesToPlay.setBackground(new Color(255,255,255));
        txtNotesToPlay.setFont(new Font("arial", Font.PLAIN, 12));
        lblNotesToPlay.setFont(myFont);
        
        topPanel1.add(topPanel3);
          topPanel3.add(lblCurrentNote);
          topPanel3.add(txtNoteDisplay);
        topPanel1.add(topPanel4);
          topPanel4.add(lblNotesToPlay);
          topPanel4.add(txtNotesToPlay);
          submitButton.setFont(new Font("arial", Font.PLAIN, 10));
          submitButton.addActionListener(this);
          topPanel4.add(submitButton);



    topPanel.add(topPanel2);
      topPanel2.setLayout(new GridLayout (2,6, WINDOWWIDTH/50, 0));

      topPanel2.add(new Panel());

      playButton.setFont(myFont);
      playButton.setEnabled(false);
      playButton.addActionListener (this);
      topPanel2.add(playButton);

      recordButton.setFont(myFont);
      recordButton.addActionListener (this);
      topPanel2.add(recordButton);


      playFileButton.setFont(myFont);
      playFileButton.addActionListener (this);
      topPanel2.add(playFileButton);

      recordFileButton.setFont(myFont);
      recordFileButton.addActionListener (this);
      topPanel2.add(recordFileButton);

      topPanel2.add(new Panel());


      //bottom row is blank
      topPanel2.add(new Panel());
      topPanel2.add(new Panel());
      topPanel2.add(new Panel());
      topPanel2.add(new Panel());
      topPanel2.add(new Panel());
      topPanel2.add(new Panel());


  //bottom Panel
  add(bottomPanel);
  addKeys(bottomPanel);
  setResizable(false);

  setTitle("Command.Com's Electronic Keyboard");
  setSize(WINDOWWIDTH, WINDOWHEIGHT);
  setVisible (true);
//getFile();

  this.addWindowListener(this);



}

public void addKeys(Panel currentPanel) {
  int iCurrentOctave;
  currentPanel.setLayout(new GridLayout(1, (OCTAVES * (ARRNOTES.length - 1)) + 2));
  currentPanel.add(new Panel());
  for (int j = 0; j < OCTAVES; j++){
    for (int i = 0; i < ARRNOTES.length; i++) {
      iCurrentOctave = (STARTINGOCTAVE + j);

      Button currentButton = new Button(ARRNOTES[i]);



      if (ARRNOTES[i].charAt(1) == '#') {
       currentButton.setForeground(new Color(255,255,255));
       currentButton.setBackground(new Color(0,0,0));
      } else {
        currentButton.setForeground(new Color(0,0,0));
        currentButton.setBackground(new Color(255,255,255));
      }

      currentButton.setActionCommand (ARRNOTES[i] + iCurrentOctave);
      currentButton.addActionListener (this);
      currentPanel.add(currentButton);
    }
  }

  //The extra C button
  iCurrentOctave = (STARTINGOCTAVE + OCTAVES);
  Button currentButton = new Button(ARRNOTES[0]);

  currentButton.setForeground(new Color(0,0,0));
  currentButton.setBackground(new Color(255,255,255));

  currentButton.setActionCommand (ARRNOTES[0] + iCurrentOctave);
  currentButton.addActionListener (this);
  currentPanel.add(currentButton);

}


public void actionPerformed (ActionEvent e) {

  String strNoteToPlay = e.getActionCommand ();

  if (isRecording && !strNoteToPlay.equals(RECORD)) {
    myNotesRecorded.recordNotes(strNoteToPlay);
  }

  

  if ((strNoteToPlay.equals(RECORDFILE)) && (isRecording == false)) {
    isRecording = true;
    myNotesRecorded.clearNotes();
    playButton.setEnabled(false);
    recordButton.setEnabled(false);
    playFileButton.setEnabled(false);
    recordFileButton.setBackground(new Color(255, 128, 128));
    recordFileButton.setLabel("Stop");


  } else if ((strNoteToPlay.equals(RECORDFILE)) && (isRecording == true)) {
    isRecording = false;
    playButton.setEnabled(true);
    recordButton.setEnabled(true);
    playFileButton.setEnabled(true);
    recordFileButton.setBackground(new Color(192, 192, 192));
    recordFileButton.setLabel(RECORDFILE);


    writeFile();

  }


  if (strNoteToPlay.equals(LOADFILE)) {
    getFile();

  }


  if (strNoteToPlay.length() == 3) {  //make sure it is a note and not
                                    //RECORD or play (all notes are made of
    playNotes(strNoteToPlay);       //a 3 char String)
                                
  }

  //I have to fix the user's string to fit to my note pattern
  //for example:
  //CC#DD#EFF#GG#AA#B
  //would be changed to:
  //C 3C#3D 3D#3E 3F 3F#3G 3G#3A 3A#3B 3

  if (strNoteToPlay.equals(SUBMIT)){
    String strNotesToPlay = (txtNotesToPlay.getText() + " ");
    txtNotesToPlay.setText("");
    String strFixedString = "";

    //make a new string
    for (int i = 0; i < strNotesToPlay.length() - 1; i++) {
      if (strNotesToPlay.charAt(i+1) == '#') {
        strFixedString = (strFixedString + strNotesToPlay.substring(i, i+1)
          + STARTINGOCTAVE);
        i = i + 1;
      } else {
        strFixedString = (strFixedString + strNotesToPlay.charAt(i) +
          " " + STARTINGOCTAVE);
      }
    }
    System.out.println("*** Sending " + strFixedString);
    myNotesRecorded.playFile(strFixedString);

  }


  if ((strNoteToPlay.equals(RECORD)) && (isRecording == false)) {
    isRecording = true;
    myNotesRecorded.clearNotes();
    playButton.setEnabled(false);
    recordButton.setBackground(new Color(255, 128, 128));
    recordButton.setLabel("Stop");
    playFileButton.setEnabled(false);
    recordFileButton.setEnabled(false);

  } else if ((strNoteToPlay.equals(RECORD)) && (isRecording == true)) {
    isRecording = false;
    playButton.setEnabled(true);
    playFileButton.setEnabled(true);
    recordFileButton.setEnabled(true);
    recordButton.setBackground(new Color(192, 192, 192));
    recordButton.setLabel(RECORD);


  }

  if (strNoteToPlay.equals(PLAY)) {
    playButton.setBackground(new Color(0, 255, 0));
    recordButton.setEnabled(false);
    playFileButton.setEnabled(false);
    recordFileButton.setEnabled(false);
    
    myNotesRecorded.playRecorded();

    playButton.setBackground(new Color(192, 192, 192));
    recordButton.setEnabled(true);
    playFileButton.setEnabled(true);
    recordFileButton.setEnabled(true);


  }



}


public void playNotes(String note) {

  char chNote;
  int iOctave;
  boolean isSharp;

  if (DEBUG) System.out.println(note);

  chNote = note.charAt(0);
  

  iOctave = Character.getNumericValue(note.charAt(2));

  if (note.charAt(1) == '#') {
    isSharp = true;
  } else {
    isSharp = false;
  }

  if (DEBUG) {
    System.out.println("*** Note played " + chNote);
    System.out.println("***             " + iOctave);
    System.out.println("***             " + isSharp);
  }

  try {
    P6Toolkit.play(chNote, iOctave, isSharp);
    txtNoteDisplay.setText(note.substring(0, 2));
  }
  catch (java.io.IOException error) { //e was already taken so I used error
  //do nothing
  if (DEBUG) System.out.println
    ("Something went wrong OR RECORD / PLAY Button pressed");
  }


}



public void getFile() {

  myFilter filterToUse = new myFilter();

//  filterToUse.accept(new File("cscale.cck"), "KeyBoard Files");

  FileDialog myPlayDialog = new FileDialog
    (this, "Seclect Recording to Load...", FileDialog.LOAD);
  myPlayDialog.setFilenameFilter(filterToUse);
  myPlayDialog.setFile("mysong.cck");

  myPlayDialog.show();

  try {
    String fileName = myPlayDialog.getFile();
    FileReader fileToRead = new FileReader(fileName);
    readFile(fileToRead);
  }
  catch (IOException e) {
    System.out.println("*** Ut OH!!: Bad File");
  }
  catch (NullPointerException e) {
    System.out.println("*** Ut OH!!: No File was loaded");
  }


}


public void readFile(FileReader r) throws IOException{
//  String strToPlay;
  StringBuffer sb = new StringBuffer();
  int inputchar;

  inputchar = r.read();
  while (inputchar != -1) {
    sb.append((char) inputchar);
    inputchar = r.read();
  }

  System.out.println(sb);
  String strToPlay = sb.toString();

  myNotesRecorded.playFile(strToPlay);

}


public void writeFile() {
  FileDialog myWriteDialog = new FileDialog
    (this, "Save As...", FileDialog.SAVE);
  myWriteDialog.show();
  String fileName = myWriteDialog.getFile();



  try {
  FileWriter fileToRead = new FileWriter(fileName);

  myNotesRecorded.makeFile(fileToRead);
  }
  catch (IOException e) {
    System.out.println("*** Ut OH!!");
  }
}




private void quitP6 () {
  setVisible (false);
  dispose ();
  System.exit (0);
}

public static void main (String argv []) {
  P6 myP6 = new P6 ();
}

public void windowActivated (WindowEvent e) {
}

public void windowClosed (WindowEvent e) {
}                                         

public void windowClosing (WindowEvent e) {
  quitP6();
}

public void windowDeactivated (WindowEvent e) {
}

public void windowDeiconified (WindowEvent e) {
}

public void windowIconified (WindowEvent e) {
}

public void windowOpened (WindowEvent e) {
}


}//endclass P6
