/******************************************************************************/
/* COPYRIGHT NOTICE (c)                                                       */
/* Copyright 1997 Lawrence Hsieh, Jason Hong, Khai Truong                     */
/* All Rights Reserved.                                                       */
/*                                                                            */
/* This file is for educational purposes only.                                */
/* By using this code you agree to indemnify all the authors from any         */
/* liability that might arise from its use.                                   */
/*                                                                            */
/* Selling the code for this program without prior written consent is         */
/* expressly forbidden.                                                       */
/*                                                                            */
/* Obtain permission before redistributing this software over the Internet or */
/* in any other medium.  In all cases copyright and header must remain        */
/* intact.                                                                    */
/******************************************************************************/

import java.io.*;
import java.util.StringTokenizer;

//==============================================================================
//                              util.java
//==============================================================================

/**
 * <PRE>
 * util.java
 *
 * Useful Java utilities for use in GaTech's Introduction to Programming.
 * Routines include input from standard in, and assertions.
 *
 * JDK Version:     JDK1.0.2
 * Revisions:  1.54 September 22, 1997, Khai Truong (khai@cc.gatech.edu)
 *                  Revised readLine() to return remaining text on current
 *                     line of input (if any exists, else reads next line).
 *             1.53 July 2, 1997, Khai Truong (khai@cc.gatech.edu)
 *                  Fixed erroneous calls in various read (binary)
 *                     routines.
 *                  Revised some incorrect Javadoc documentation.
 *             1.52 May 24, 1997, Jason Hong (hong@cc.gatech.edu)
 *                  Revised some incorrect Javadoc documentation
 *                  Removed some Javadoc documentation-start markers
 *                     for methods using DataInputStream to reduce amount
 *                     of information a student will have to search thru.
 *             1.51 May 19, 1997 Jason Hong and Larry Hsieh
 *                  Added copyright information
 *                  Added debug() routine
 *             1.42 February 26, 1997, Jason Hong (hong@cc.gatech.edu)
 *                  Updated documentation, added example usage
 *             1.41 February 19, 1997, Jason Hong (hong@cc.gatech.edu)
 *                  Added readlnWord() method
 *             1.4  January 6, 1997, Jason Hong (hong@cc.gatech.edu)
 *                  Added readWord() method (Thanks Khai and Larry)
 *                  Revised documentation for javadoc
 *                  Added binary and non-binary reads
 *             1.3  November 28, 1996, Jason Hong (hong@cc.gatech.edu)
 *                  Separated file routines into another class (AbstractFile)
 *                  Made all methods final for optimization
 *                  Revised documentation for javadoc
 *                  Renamed some variables for consistency
 *                  Modified error messages
 *                  Added error codes
 *                  Optimized ASSERT() method
 *                  Added another ASSERT() method
 *                  Added both ABORT() methods
 *             1.2  October 15, 1996, Jason Hong (hong@cc.gatech.edu)
 *                  Added Larry Hsieh's input and output routines for files
 *             1.1  October 10, 1996, Jason Hong (hong@cc.gatech.edu)
 *                  Added some input and output routines for stdin
 *             1.0  October 01, 1996, Jason Hong (hong@cc.gatech.edu)
 *                  Created the util.java for cs1502
 * </PRE>
 *
 *
 * <P>
 * <B>Example Usage:</B>
 * <PRE>
 * Given the input:
 * ABC
 * D E F
 * GHI
 * </PRE>
 *
 * <P>
 * <PRE>
 * public static void main(String[] argv)
 * {
 *    Character characterRead;
 *    characterRead = util.readCharacter();   // reads in 'A'
 *    characterRead = util.readCharacter();   // reads in 'B'
 *    characterRead = util.readCharacter();   // reads in 'C'
 *    characterRead = util.readCharacter();   // reads in 'D'
 *    characterRead = util.readlnCharacter(); // reads in 'E', goes to next line
 *    characterRead = util.readCharacter();   // reads in 'G'
 *    characterRead = util.readlnCharacter(); // reads in 'H', goes to next line
 *    characterRead = util.readCharacter();   // returns null
 * }
 * </PRE>
 *
 * <P>
 * <B>Example of what not to do:</B>
 * <PRE>
 * Given the input:
 * A
 * </PRE>
 *
 * <P>
 * <PRE>
 * public static void main(String[] argv)
 * {
 *    char chInput;
 *
 *    //// DON'T DO THIS!!!
 *    chInput = util.readCharacter().charValue();  // read's in 'A'
 *    chInput = util.readCharacter().charValue();  // NullPointerException
 * }
 * </PRE>
 *
 * The reason you should not do this is because <I>util.readCharacter()</I>
 * can return a null reference (at which point you cannot call method
 * <I>charValue()</I>.
 *
 * <P>
 * <B>Here is how to read until end of file:</B>
 * <PRE>
 * Character charInput;
 * char      chInput;
 *
 * while ( (charInput = util.readCharacter()) != null )
 * {
 *    chInput = charInput.charValue();
 *
 *    //// DO STUFF WITH THE INPUT HERE
 * }
 * </PRE>
 *
 *
 * @author <A HREF="mailto:hong@cc.gatech.edu">Jason Hong</A>
 * @author <A HREF="mailto:larryh@cc.gatech.edu">Larry Hsieh</A>
 * @author <A HREF="mailto:khai@cc.gatech.edu">Khai Truong</A>
 * @version Version 1.54, September 22, 1997
 */

class util
{
   //===========================================================================
   //                           CONSTANTS
   //===========================================================================

   //// The current version of this file
   static final double  VERSION        = 1.54;

   //// Should ASSERT be compiled or not?
   static final boolean COMPILE_ASSERT = true;

   //// Is this for an Applet or not?
   static final boolean COMPILE_APPLET = false;

   //// Error return values
   static final int     ERR_GENERAL    = 1;     // A general error
   static final int     ERR_ASSERT     = 2;     // An ASSERT failure

   //===========================================================================


   //===========================================================================
   //                         NONLOCAL VARIABLES
   //===========================================================================

   //// For reading data from standard in (stdin)
   private static DataInputStream
      stdinDataInputStream = new DataInputStream(System.in);

   //// For reading in words of data (Strings of chars, not words of data)
   private static StringTokenizer strtokLine = new StringTokenizer("");

   //// To clear out the StringTokenizer strtokLine
   private static final StringTokenizer
      strtokEmptyLine = new StringTokenizer("");

   //===========================================================================


   //===========================================================================
   //                         DEBUG MODE METHODS
   //===========================================================================

   /**
    * Allows the ability to redirect standard input to a input file
    * called "input.txt"  . This is a workaround the Symantec Cafe (v1.51)
    * inability to use standard input.
    *
    * <P>
    * If "input.txt" exists stdinDataInputStream is redirected from
    * standard input.  If file doesn't exists, a warning message is
    * displayed and sdinDataInputStream remains standard input.
    *
    * <P>
    * Note:     This allows for scripted input and the ability to debug
    *           since I/O is not possible in debug mode.
    */
   final public static void debug()
   {
      try
      {
         stdinDataInputStream = new
            DataInputStream(new FileInputStream("input.txt"));
      } // of try
         catch (FileNotFoundException e)
         {
            System.out.println("Unable to located input.txt");
            System.out.println("Cannot enter debug mode!");
            System.out.println("Using regular standard input");
         } // of catch
   } // of debug

   //===========================================================================


   //===========================================================================
   //                      VERIFICATION METHODS
   //===========================================================================

   /**
    * Print out an error Message and exit immediately.
    * The program will exit with value ERR_GENERAL after printing
    *    the error message passed in.
    *
    * @param   strErrorMessage is the error message to print out.
    */
   final public static void ABORT(String strErrorMessage)
   {
      ABORT(strErrorMessage, ERR_GENERAL);

   } // of ABORT

   //===========================================================================

   /**
    * Print out an error Message and exit immediately with a return value.
    * The program will exit with the value passed in after printing
    *    the error message passed in.
    *
    * @param   strErrorMessage is the error message to print out.
    * @param   iErrorValue is the error value to return on exit.
    */
   final public static void ABORT(String strErrorMessage, int iErrorValue)
   {
      //// forces buffered output to be printed
      System.out.flush();

      //// print out the error message
      System.err.println("\n" + strErrorMessage);
      new Throwable().printStackTrace();
      System.err.println("\nAborting program\n");
      System.err.flush();

      //// Applets cannot call System.exit(), so throw a runtime
      ////    exception instead (which methods do not explicitly have to
      ////    declare to catch)
      if (COMPILE_APPLET)
         throw new RuntimeException(strErrorMessage);
      else
         System.exit(iErrorValue);

   } // of ABORT

   //===========================================================================

   /**
    * Validate a predicate.<BR>
    * If predicate evaluates to true, then the function call passes through
    *    with no side effects. If the predicate is false, then
    *    stack will be printed out, and the program will exit immediately.
    * <P>
    * Note:    Recompile util.java with the COMPILE_ASSERT flag set to false
    *             in order to compile without ASSERT statements.
    * <P>
    * Note:    Instead of calling ABORT, you can tailor this to your needs.
    *             This is just one implementation of ASSERT. The default
    *             action on a failed ASSERT() here is to exit. You may
    *             choose to do something more useful, like save the program,
    *             go back to a known good state, put in a value that is
    *             harmless, etc.
    * <P>
    * Sample:  util.ASSERT(iValue > 0);
    *          util.ASSERT(cValue == 'a' || cValue == 'A');
    *          util.ASSERT(iValue++) is WRONG because of side effects
    *
    * @param   predicate is an expression that evaluates to a boolean to test.
    *             You should not pass in an expression with side effects.
    */
   final public static void ASSERT(boolean predicate)
   {
      // if COMPILE_ASSERT if false, the optimizer will remove this
      if (COMPILE_ASSERT == true)
      {
         if (predicate == false)
            ABORT("Assertion error", ERR_ASSERT);
      } // of if COMPILE_ASSERT

   } // of ASSERT

   //===========================================================================

   /**
    * Validate a predicate.
    * <P>
    * Note:    See the other ASSERT method.
    *
    * @param   predicate is an expression that evaluates to a boolean to test.
    *             You should not pass in an expression with side effects.
    * @param   strErrorMessage is the error message to print out if the
    *             assertion should fail.
    */
   final public static void ASSERT(boolean predicate, String strErrorMessage)
   {
      // if COMPILE_ASSERT if false, the optimizer will remove this
      if (COMPILE_ASSERT == true)
      {
         if (predicate == false)
            ABORT("Assertion error:" + strErrorMessage, ERR_ASSERT);
      } // of if COMPILE_ASSERT

   } // of ASSERT

   //===========================================================================


   //===========================================================================
   //                    MISCELLANEOUS INPUT METHODS
   //===========================================================================

   /**
    * Clear the StringTokenizer to end of line.
    * This makes it read from the next line the next time a read is done.
    */
   private static void clearToEndOfLine()
   {
      strtokLine = new StringTokenizer("");

   } // of clearToEndOfLine

   //===========================================================================


   //===========================================================================
   //                       BYTE INPUT METHODS
   //===========================================================================

   /**
    * Read in a byte from standard input.
    * <P>
    * Note:     This needs to be fixed to return a Byte object once JVM 1.1
    *              comes out.
    *
    * @return  Returns the byte read in if successful (as a Character).<BR>
    *          Returns null if end of file has been reached.<BR>
    *          Exits program on a read exception (IOException).<BR>
    */
   final public static Character readByte()
   {
      return (readByte(stdinDataInputStream));
   } // of readByte

   //===========================================================================

   /**
    * Read in a byte from standard input and ignore the rest of the line.
    * <P>
    * Note:     This needs to be fixed to return a Byte object once JVM 1.1
    *              comes out.
    *
    * @return   Returns the byte read in if successful (as a Character).<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Character readlnByte()
   {
      return (readlnByte(stdinDataInputStream));
   } // of readlnByte

   //===========================================================================

   /*
    * Read in a byte from a DataInputStream.
    * <P>
    * Note:     This needs to be fixed to return a Byte object once JVM 1.1
    *              comes out
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the byte read in if successful (as a Character).<BR>

    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final protected static Character readByte(DataInputStream dataInputStream)
   {
      byte      byteInput = 0;

      ASSERT(dataInputStream != null, "Input stream is null");
      try
      {
         byteInput = dataInputStream.readByte();
      } // of try
         catch (EOFException e)
         {
            return(null);
         } // of catch
         catch (IOException e)
         {
            ABORT("Read failure\n" + e);
         } // of catch

      // otherwise, return the character read in
      return(new Character((char) byteInput));

   } // of readByte

   //===========================================================================

   /*
    * Read in a byte from a DataInputStream and ignore the rest of the line.
    * <P>
    * Note:     This needs to be fixed to return a Byte object once JVM 1.1
    *              comes out
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the byte read in if successful (as a Character).<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final protected static Character readlnByte(DataInputStream dataInputStream)
   {
      Character charReturnByte = readByte(dataInputStream);
      readLine(dataInputStream);
      return(charReturnByte);

   } // of readlnByte

   //===========================================================================


   //===========================================================================
   //                      CHARACTER INPUT METHODS
   //===========================================================================

   /**
    * Read in an ASCII character from standard input (ignores carriage
    *    returns and newlines).
    * <P>
    * Note:     Does not read Unicode.
    *
    * @return   Returns the character read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Character readCharacter()
   {
      return (readCharacter(stdinDataInputStream));
   } // of readCharacter

   //===========================================================================

   /**
    * Read in an ASCII character from standard input (ignores
    *              carriage returns and newlines).
    * <P>
    * Note:     Does not read Unicode.
    *
    * @return   Returns the character read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Character readlnCharacter()
   {
      return (readlnCharacter(stdinDataInputStream));
   } // of readlnCharacter

   //===========================================================================

   /*
    * Read in an ASCII character from a DataInputStream (ignores
    *              whitespace) and ignore the rest of the line.
    * <P>
    * Note:     Does not read Unicode.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the character read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final protected static Character
      readCharacter(DataInputStream dataInputStream)
   {
      char      chInput = '\u0000';

      ASSERT(dataInputStream != null, "Input stream is null");
      try
      {
         do
         {
            chInput = (char) dataInputStream.readByte();
         } while (Character.isSpace(chInput));
      } // of try
         catch (EOFException e)
         {
            return(null);
         } // of catch
         catch (IOException e)
         {
            ABORT("Read failure\n" + e);
         } // of catch

      // Return the character read in
      return(new Character(chInput));

   } // of readCharacter

   //===========================================================================

   /*
    * Read in a character from a DataInputStream (ignores newlines).
    *           All input after the first character is discarded.<BR>
    * <P>
    * Note:     Does not read Unicode.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the character read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Character
      readlnCharacter(DataInputStream dataInputStream)
   {
      Character charReturn = readCharacter(dataInputStream);
      readLine(dataInputStream);
      return(charReturn);

   } // of readlnCharacter

   //===========================================================================


   //===========================================================================
   //                       INTEGER INPUT METHODS
   //===========================================================================

   /**
    * Read in an Integer from standard input.
    *
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer readInteger()
   {
      return (readInteger(stdinDataInputStream));
   } // of readInteger

   //===========================================================================

   /**
    * Read in an Integer from standard input and ignore the rest of the line.
    *
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer readlnInteger()
   {
      return (readlnInteger(stdinDataInputStream));
   } // of readlnInteger

   //===========================================================================

   /*
    * Read in an Integer from specified DataInputStream.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer readInteger(DataInputStream dataInputStream)
   {
      String strInput;

      strInput = readWord(dataInputStream);
      if (strInput == null)
         return(null);
      else
         return(new Integer(strInput));

   } // of readInteger

   //===========================================================================

   /*
    * Read in an Integer from a DataInputStream and ignore the rest of the line.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer readlnInteger(DataInputStream dataInputStream)
   {
      Integer intReturn = readInteger(dataInputStream);
      clearToEndOfLine();
      return(intReturn);

   } // of readlnInteger

   //===========================================================================


   //===========================================================================
   //                     BINARY INTEGER INPUT METHODS
   //===========================================================================

   /**
    * Read in an Integer from standard input (binary).
    *
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer readBinaryInteger()
   {
      return (readBinaryInteger(stdinDataInputStream));
   } // of readBinaryInteger

   //===========================================================================

   /**
    * Read in an Integer from standard input (binary) and ignore the
    *    rest of the line.
    *
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer readlnBinaryInteger()
   {
      return (readlnBinaryInteger(stdinDataInputStream));
   } // of readlnBinaryInteger

   //===========================================================================

   /*
    * Read in an Integer from specified DataInputStream (binary).
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer
      readBinaryInteger(DataInputStream dataInputStream)
   {
      int iInput = 0;

      ASSERT(dataInputStream != null, "Input stream is null");
      try
      {
         iInput = dataInputStream.readInt();
      } // of try
         catch (EOFException e)
         {
            return(null);
         } // of catch
         catch (IOException e)
         {
            ABORT("Read failure\n" + e);
         } // of catch

      // Return the character read in
      return(new Integer(iInput));

   } // of readBinaryInteger

   //===========================================================================

   /*
    * Read in an Integer from specified DataInputStream (binary) and ignore
    * the rest of the line.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Integer read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Integer
      readlnBinaryInteger(DataInputStream dataInputStream)
   {
      Integer intReturn = readBinaryInteger(dataInputStream);
      readLine(dataInputStream);
      return(intReturn);

   } // of readlnBinaryInteger

   //===========================================================================


   //===========================================================================
   //                       FLOAT INPUT METHODS
   //===========================================================================

   /**
    * Read in a Float from standard input.
    *
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readFloat()
   {
      return (readFloat(stdinDataInputStream));
   } // of readFloat

   //===========================================================================

   /**
    * Read in a Float from standard input and ignore the rest of the line.
    *
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readlnFloat()
   {
      return (readlnFloat(stdinDataInputStream));
   } // of readlnFloat

   //===========================================================================

   /*
    * Read in a Float from a DataInputStream.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readFloat(DataInputStream dataInputStream)
   {
      String strInput;

      strInput = readWord(dataInputStream);
      if (strInput == null)
         return(null);
      else
         return(new Float(strInput));

   } // of readFloat

   //===========================================================================

   /*
    * Read in a Float from a DataInputStream and ignore the rest of the line.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readlnFloat(DataInputStream dataInputStream)
   {
      Float fReturn = readFloat(dataInputStream);
      clearToEndOfLine();
      return(fReturn);

   } // of readlnFloat

   //===========================================================================


   //===========================================================================
   //                       BINARY FLOAT INPUT METHODS
   //===========================================================================

   /**
    * Read in a Float from standard input (binary).
    *
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readBinaryFloat()
   {
      return (readBinaryFloat(stdinDataInputStream));
   } // of readBinaryFloat

   //===========================================================================

   /**
    * Read in a Float from standard input (binary) and ignore the rest
    *              of the line.
    *
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readlnBinaryFloat()
   {
      return (readlnBinaryFloat(stdinDataInputStream));
   } // of readlnBinaryFloat

   //===========================================================================

   /*
    * Read in a Float from a DataInputStream (binary).
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readBinaryFloat(DataInputStream dataInputStream)
   {
      float fInput = 0;

      ASSERT(dataInputStream != null, "Input stream is null");
      try
      {
         fInput = dataInputStream.readFloat();
      } // of try
         catch (EOFException e)
         {
            return(null);
         } // of catch
         catch (IOException e)
         {
            ABORT("Read failure\n" + e);
         } // of catch

      // Return the character read in
      return(new Float(fInput));

   } // of readBinaryFloat

   //===========================================================================

   /*
    * Read in a Float from a DataInputStream (binary) and ignore the rest
    *           of the line.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Float read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Float readlnBinaryFloat(DataInputStream dataInputStream)
   {
      Float fReturn = readBinaryFloat(dataInputStream);
      readLine(dataInputStream);
      return(fReturn);

   } // of readlnBinaryFloat

   //===========================================================================


   //===========================================================================
   //                      DOUBLE INPUT METHODS
   //===========================================================================

   /**
    * Read in a Double from standard input.
    *
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double readDouble()
   {
      return (readDouble(stdinDataInputStream));
   } // of readDouble

   //===========================================================================

   /**
    * Read in a Double from standard input and ignore the rest of the line.
    *
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double readlnDouble()
   {
      return (readlnDouble(stdinDataInputStream));
   } // of readlnDouble

   //===========================================================================

   /*
    * Read in a Double from a DataInputStream.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double readDouble(DataInputStream dataInputStream)
   {
      String strInput;

      ASSERT(dataInputStream != null, "Input stream is null");

      strInput = readWord(dataInputStream);
      if (strInput == null)
         return(null);
      else
         return(new Double(strInput));

   } // of readDouble

   //===========================================================================

   /*
    * Read in a Double from a DataInputStream and ignore the rest of the line.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double readlnDouble(DataInputStream dataInputStream)
   {
      Double intReturn = readDouble(dataInputStream);
      clearToEndOfLine();
      return(intReturn);

   } // of readlnDouble

   //===========================================================================


   //===========================================================================
   //                    BINARY DOUBLE INPUT METHODS
   //===========================================================================

   /**
    * Read in a Double from standard input (binary).
    *
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double readBinaryDouble()
   {
      return (readBinaryDouble(stdinDataInputStream));
   } // of readBinaryDouble

   //===========================================================================

   /**
    * Read in a Double from standard input (binary) and ignore the rest
    *              of the line.
    *
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double readlnBinaryDouble()
   {
      return (readlnBinaryDouble(stdinDataInputStream));
   } // of readlnBinaryDouble

   //===========================================================================

   /*
    * Read in a Double from a DataInputStream (binary).
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double readBinaryDouble(DataInputStream dataInputStream)
   {
      double dInput = 0;

      ASSERT(dataInputStream != null, "Input stream is null");

      try
      {
         dInput = dataInputStream.readDouble();
      } // of try
         catch (EOFException e)
         {
            return(null);
         } // of catch
         catch (IOException e)
         {
            ABORT("Read failure\n" + e);
         } // of catch

      // Return the character read in
      return(new Double(dInput));

   } // of readBinaryDouble

   //===========================================================================

   /*
    * Read in a Double from a DataInputStream (binary) and ignore the rest
    *    of the line.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the Double read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static Double
      readlnBinaryDouble(DataInputStream dataInputStream)
   {
      Double dReturn = readBinaryDouble(dataInputStream);
      readLine(dataInputStream);
      return(dReturn);

   } // of readlnBinaryDouble

   //===========================================================================


   //===========================================================================
   //                       STRING INPUT METHODS
   //===========================================================================

   /**
    * Read in a word (string of chars, not a word of data) from standard input
    * and stay on the same line.
    *
    * @return   Returns the String read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static String readWord()
   {
      return (readWord(stdinDataInputStream));
   } // of readWord

   //===========================================================================

   /*
    * Read in a word (string of chars, not a word of data) from a
    *    DataInputStream and stay on the same line.
    *
    * @param    dataInputStream is a DataInputStream.
    * @return   Returns the String read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static String readWord(DataInputStream dataInputStream)
   {
      String strInput = new String();

      ASSERT(dataInputStream != null, "Input stream is null");
      ASSERT(strtokLine != null, "String tokenizer is a null reference");

      //// Skip all blank lines
      while (!strtokLine.hasMoreTokens())
      {
         //// See if there's still more data left on this line
         try
         {
            // read in from stdin
            // method readLine returns null if EOF
            strInput = dataInputStream.readLine();
            if (strInput == null)
               return(null);
            strtokLine = new StringTokenizer(strInput);
         } // of try
            catch (IOException e)
            {
               ABORT("Read failure\n" + e);
            } // of catch
      }

      // otherwise, return the String
      return(strtokLine.nextToken());

   } // of readWord

   //===========================================================================

   /**
    * Read in a word (string of chars, not a word of data) from standard input
    * and go to the next line.
    *
    * @return   Returns the String read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static String readlnWord()
   {
      return(readlnWord(stdinDataInputStream));

   } // of readlnWord

   //===========================================================================

   /*
    * Read in a word (string of chars, not a word of data) from a
    *    DataInputStream and go to the next line.
    *
    * @param    dataInputStream is a DataInputStream already initialized.
    * @return   Returns the String read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static String readlnWord(DataInputStream dataInputStream)
   {
      String strReturn = readWord(dataInputStream);
      clearToEndOfLine();
      return(strReturn);

   } // of readlnWord

   //===========================================================================

   /**
    * Read in a line from standard input and go to the next line.
    *
    * @return   Returns the String read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static String readLine()
   {
      return (readLine(stdinDataInputStream));
   } // of readLine

   //===========================================================================

   /*
    * Read in a line from a DataInputStream and go to the next line.
    *
    * @param    dataInputStream is a DataInputStream.
    * @return   Returns the String read in if successful.<BR>
    *           Returns null if end of file has been reached.<BR>
    *           Exits program on a read exception (IOException).<BR>
    */
   final public static String readLine(DataInputStream dataInputStream)
   {
      String strInput = new String();

      ASSERT(dataInputStream != null, "Input stream is null");
      ASSERT(strtokLine != null, "String tokenizer is a null reference");

      if (strtokLine.hasMoreTokens())
         strInput = strtokLine.nextToken("").trim();
      if (strInput.length()<=0)
         try
         {
            // read in from stdin
            // method readLine returns null if EOF
            strInput = dataInputStream.readLine();
         } // of try
            catch (IOException e)
            {
               ABORT("Read failure\n" + e);
            } // of catch

      // otherwise, return the String
      clearToEndOfLine();
      return(strInput);

   } // of readline

   //===========================================================================


   //===========================================================================
   //                              MAIN
   //===========================================================================

   /**
    * Tells what version number this is.
    */
   public static void main(String[] argv)
   {
      System.err.println("This class is not meant to be run.");
      System.err.println("util.java version " + VERSION);
      System.exit(1);
   } // of main

   //===========================================================================

} // of class util

//==============================================================================











