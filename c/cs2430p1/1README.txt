

			Programming Assignment 1
			    Lexical Analyzer

	       Due Date: by 6:00 PM on Tuesday, May 4, 1999


	  For this assignment only, you are permitted to work  in
	teams of not more than two people. You must register your
	team using the  groupcon program in the  cs2430    workon
	environment.  Both members of the team must be registered
	by not later than 6:00 PM on Tuesday, April 27, 1999.  If
	you  do  not register yourself as part of a team, then it
	will be assumed that you  are  working  alone.  No  extra
	points will be given for people who choose to work alone.
	EACH MEMBER OF A PROJECT TEAM IS INDIVIDUALLY RESPONSIBLE
	FOR SEEING THAT A COPY OF THE ASSIGNMENT IS TURNED IN.


   Readings:
	Read the sections on  Implementing a Simple Shell in the Weiss
	book (pp. 392-398).

   Stub Code:
	You can retrieve the baseline source for the  GetToken() func-
	tion by doing a  getjob prj1 from the CS 2430  workon environ-
	ment. You should receive the following files:


	     1README.ps    this file (Postscript version)
	     1README.txt   this file (text version)
	     Makefile      Makefile that uses your own options.c
	     tokmain.c     source code for main() function
	     token.c       source code for GetToken() function
	     testtok.sh    a testing script


	The following sections describe  what  modifications  must  be
	made  to  the  existing  version  of   token.c, and what point
	values each set of modifications is worth.   Do not modify the
	prototype definition for   GetToken(),   otherwise the remain-
	ing parts of the project will not fit together properly.


   For a "C" (70%) -

	1. Add code in  GetToken() to detect and report the  following
	   error conditions:

	   a. More than one occurrence per line of the  From,  To, or
	      Amper tokens.  Note that the  foo shell will not support
	      the file append ( ">>" ) operation, so this is an  error
	      as well.

	   b. A newline character encountered inside any quoted string

	   c. More than  MaxLineLen characters in a single    Word  of
	      tokenized  output.  If  a    Word  expands to more than
	      MaxLineLen characters,  then  the  remaining  characters
	      beyond    MaxLineLen  are discarded (note that it is not
	      necessary to report this as an error - just  return  the
	      truncated  Word).

	2. If a tilde ( `~' ) character is encountered  on  the  input
	   line,  then  the  tilde  must  be replaced with the current
	   value of the  HOME environment variable (this requires  use
	   of  the   EVget() function from your Project 0 submission).
	   Make sure you pass the  HOME variable in from your  testing
	   environment  (i.e.  make sure it is defined and exported in
	   your  testtok.sh script).


   For a "B" (80%) -

	1. Complete all requirements for a "C".

	2. Add a new state to  GetToken() that will recognize when you
	   are inside a string that is delimited by double quotes (the
	    InQuote state is used  for  strings  delimited  by  single
	   quotes - this new state will be in addition to the  InQuote
	   state). The production version uses  InString, but you  can
	   use whatever identifier you want for your own program.


	3. Add code to the  GetToken() function to  determine  when  a
	   doubly-quoted  string  has  been detected, and to handle it
	   according to the following requirements.

	   Strings enclosed in double quotes are subject to additional
	   interpretation  for  certain  "special"  characters - these
	   special characters, when preceded by a backslash (as  shown
	   below), will have the following meanings:

		  Seq    Substitution
		  \b     backspace
		  \f     form-feed
		  \n     new-line
		  \r     carriage return
		  \t     tab
		  \v     vertical tab
		  \\     backslash
		  \"     double quote
		  \ooo   the 8-bit character whose ASCII code
			 is the 1-, 2- or 3-digit octal number
			 ooo, which must start with a zero.

	   Any other character not in the above list should be  copied
	   normally.

	4. Add expansion of environment variables.  Environment  vari-
	   able  expansion occurs whenever a variable name is preceded
	   by a `$' sign.  The part after the `$'  is  the  variable's
	   name,  and  is replaced by the value string as returned by
	   EVget() (the `$' sign  is  stripped  off  by  your  program
	   before passing the name to  EVget()).  Environment variable
	   expansion occurs anywhere on the input line, or in  doubly-
	   quoted  strings  (as noted above, singly-quoted strings are
	   treated as literals,  and  are  therefore  not  subject  to
	   expansion).   Environment variable names may consist of any
	   combination of characters from the  set  [0-9A-Za-z_].  The
	   name  may not start with a digit. Note that a `$' sign fol-
	   lowed by any character that does not signify the start of a
	   variable  name is to be interpreted as an ordinary `$' sign
	   (i.e. no expansion).

	5. Add expansion of the  $SECONDS variable, inside any context
	   in  which  environment  variables  are  also  expanded.  If
	   encountered,  $SECONDS is replaced by the number of seconds
	   that has elapsed since the program was started.

	6. Add expansion of the  $RANDOM variable, inside any  context
	   in  which  environment  variables  are  also  expanded.  If
	   encountered,   $RANDOM  is  replaced  by  a  random  number
	   between 0 and 32767, inclusive.


   For an "A" (90%) -

	1. Complete all requirements for a "B".

	2. Modify your environment variable name recognition algorithm
	   from  above  so  that  variable  names  may  be  optionally
	   enclosed in curly braces.  For example,   ${FOO}  would  be
	   recognized and replaced by the same value string as  $FOO.

	3. Add  Parameter Substitution to  your  environment  variable
	   replacement  algorithm.  The following additional substitu-
	   tions must be supported:

	    ${var:-param}
		if  var's value is "", then    param  is  substituted,
		otherwise the value of var is substituted. This format
		allows for substitution of a default value.

	    ${var:=param}
		if  var's value is "", then  param is assigned to  var
		and substituted. This format allows for assignment and
		substitution of a default value.

	    ${var:+param}
		if  var's value is not "", then  param is substituted,
		otherwise ""  is substituted.  This format allows  for 
		over-riding of the current value.


   For an "A+" (100%) -

	1. In the case where environment variable expansion is encoun-
	   tered  on  the input line and outside a quoted string, then
	   the resulting expanded  string  must  be  ``tokenized''  as
	   though it were read directly from the input line. Expansion
	   inside a doubly-quoted string would just  cause  the  value
	   string to be copied directly to the returned  Word, just as
	   before. This means that your  GetToken() routine will  have
	   to  keep  track  of  where  the variable expansion is being
	   done. For example, if you had set an environment variable
	   FOO  whose  value was  ``foo bar | fubar'', then any refer-
	   ence to  ${FOO} on the input line  (and  outside  a  quoted
	   string)  would  return  four separate tokens -  Word <foo>,
	   Word <bar>, Pipe, and  Word <fubar> A reference to   ${FOO}
	   inside  double  quotes would produce a single token (i.e.
	   ``cat ${FOO}'' would return  Word <cat foo bar | fubar>).

	2. Add expansion of the  $_ variable, inside  any  context  in
	   which   environment   variables  are  also  expanded.  When
	   expanded,  $_ is replaced  by  the  last    Word  that  was
	   returned from the  previous input line.

   For Extra Credit (110%) -
	Modify processing of tilde expansion  so  that  the  following
	conditions are met:

	1. Tilde expansion only occurs when the tilde  is  encountered
	   as the first character of a  Word.

	2. If the second character of the  Word is `/', then the tilde
	   is replaced with the value of the user's  HOME variable, as
	   returned by  EVget().

	3. If the second character of the  Word is  not  a  `/',  then
	   all  characters between the tilde and the first `/' (or the
	   end of the  Word, if no `/' is present) are interpreted  as
	   a  user  name, and the tilde and the user name are replaced
	   by that user's  HOME directory. If the  user  name  is  not
	   valid,  the  tilde and invalid user name are replaced by an
	   empty string. You  will  need  to  use  the    getpwnam(3c)
	   library call to complete this part.

	4. A tilde that does not meet any of the above  conditions  is
	   not expanded.

   Minimum Functionality -
	The following features and requirements  must be completed, to
	facilitate  testing  and grading of future assignments on this
	project: C1, C2, B4, A2.

