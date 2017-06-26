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
public class NotesRecorded {

//*** CONSTANTS
public static final boolean DEBUG = false;

//*** INSTANCE VARIABLES
String strNotesRecorded;


//Here's the note pattern:  Each 3 characters in the string represent a note.
//The first character represents the actual note, the second tells if it is
//sharp, and the third tells the octave.  I know this is probably obscure,
//but I thought messing with vectors and linked lists would be a hassle.
//Also, it makes writing to a file a whole lot easier.
//For example: C, D sharpe, F (in the third octave), and B shapre (in the
//fourth octave) would be stored as:
//C 3D#3F 3B 4



public NotesRecorded () {
  strNotesRecorded = "";

}

public void recordNotes(String strNewNote) {

  //if statement to make sure 'Record' or 'Record File' is not added to
  //the note String
  if (strNewNote.length() == 3) {
    strNotesRecorded = (strNotesRecorded + strNewNote);
  }

  if (DEBUG) System.out.println("*** Notes Recorded: " + strNotesRecorded);

}


//just plays notes from the strNotesRecorded file
public void playRecorded() {
  if (DEBUG) {
    System.out.println("*** Entered playRecorded");
    System.out.println("*** Notes to play : " + strNotesRecorded);
  }

  for (int i = 0; i < strNotesRecorded.length() - 1; i += 3) {
    playRecordedNotes(strNotesRecorded.substring(i, i+3));
    P6Toolkit.pause();

  }



}


//plays notes from a passed in string
public void playFile(String strNotesFromFile) {

  for (int i = 0; i < strNotesFromFile.length() - 1; i += 3) {
    playRecordedNotes(strNotesFromFile.substring(i, i+3));
    P6Toolkit.pause();

  }


}



public void clearNotes() {
  strNotesRecorded = "";

}



public void makeFile(FileWriter fileToWrite) throws IOException{


PrintWriter printThis = new PrintWriter(new BufferedWriter
  (fileToWrite));


  System.out.println("*** Printing " + strNotesRecorded);
  printThis.print(strNotesRecorded);



  printThis.close();
}


//does the actual playing of the string passed in by playFile or playRecorded
public void playRecordedNotes(String note) {
  System.out.println("*** Entered playRecorded Notes");

  char chNote;
  int iOctave;
  boolean isSharp;

  chNote = note.charAt(0);

  iOctave = Character.getNumericValue(note.charAt(2));

  if (note.charAt(1) == '#') {
    isSharp = true;
  } else {
    isSharp = false;
  }
  System.out.println("*** Playing : " + note);

  try {
    P6Toolkit.play(chNote, iOctave, isSharp);
  }
  catch (java.io.IOException error) { 
  //do nothing
  System.out.println("something's wrong");
  }


}





}//endclass NotesRecorded
