
			Programming Assignment 2
			   Command Invocation

	      Due Date: by 6:00 PM on Monday, May 24, 1999


	  For this assignment only, you are permitted to work  in
	teams  of  not  more  than two people. If you worked in a
	project team for Assignment 1, then you may continue with
	that  team,  but  you  MUST re-register the team for this
	project. If you did not work in a team for Assignment  1,
	but  wish  to  do  so  for this assignment, then you must
	register your team by using the  groupcon program in  the
	CS  2430  workon  environment.   In either case, you must
	register your team by not later than 6:00  PM  on  Monday
	May  17,  1999.   If you do not form a team by that time,
	then it will be assumed that you are  working  alone.  No
	extra  points will be given for people who choose to work
	alone. Note that E-mail notifications of team  membership
	will not be recognized - you  must use the  groupcon pro-
	gram to register your team. EACH MEMBER OF A PROJECT TEAM
	IS  INDIVIDUALLY  RESPONSIBLE FOR INSURING THAT A COPY OF
	THE ASSIGNMENT IS TURNED IN.


   Readings:
	Review the material on pages 392-398 of  the  Weiss  book.  In
	addition,  look over the material on pointers to functions (pp
	135-137), structures (pp 223-234), and how to combine them (pp
	245-246).

   Stub Code:
	You should have gotten the source  code  for  the    Builtin()
	function  with  your  getjob program for this assignment.  The
	following files should have been distributed:

	2README.ps    this file (PostScript version)
	2README.txt   this file (text version)
	Makefile      creates fsh
	builtin.c     contains source code for the Builtin() function

	The code for the other  functions  that  will  be  needed  (
	Parse(),    RunCommand(),  etc) has already been compiled. The
	object modules are available  in  the  ~cs2430/pub/fsh/objects
	directory  on  Acme.  The  supplied Makefile knows how to find
	these, so there is no need to make your own  copies  of  them.
	You will not have to modify these parts of the code.

	Note: After completing this part,  you  will  have  a  working
	shell!


   Modifications:
	You will have to add or modify some shell "builtin"  commands.
	These are commands that are executed directly by the shell, as
	opposed to being called as external  programs.  The  procedure
	for  adding these commands will be outlined below.    You must
	follow this procedure exactly to get full credit on this part.

	The actions of each of the builtin commands  will  be  carried
	out  by  a  single  function that is dedicated specifically to
	that command. The supplied version of  builtin.c does this for
	two  commands already -  set and variable assignment.  A third
	command (the    cd  command)  is  handled  directly  in  the
	Builtin()  function  itself  (you  will  have  to  modify this
	behavior as part of this assignment). Each  function  receives
	two  arguments  -    Argc  (a count of the number of arguments
	passed), and  Argv (an array of pointers to the  actual  argu-
	ment  strings).  Argument  indices  in the  Argv[] array range
	from 0 to  Argc-1.   Argv[ 0 ] always contains the name of the
	builtin command (  cd,  set, etc).

	The supplied version of  builtin.c uses a series of   if-then-
	else-if  ...  comparisons  to  find  out what command is being
	invoked. This is a very cumbersome mechanism that is difficult
	to  modify, particularly if the number of commands to be added
	is large. A much cleaner way of doing this  is  to  statically
	declare  a  global array of command names and their associated
	functions, where the names are stored as constant strings, and
	the  "functions"  are  actually  stored  as pointers (i.e. the
	addresses where the functions are stored).

	Here is a  sample  declaration  for  an  array  that  contains
	relevant  information  about  the  functions  as  supplied in
	builtin.c:


     #define FNULL ( ( VoidFP ) NULL )         /* pre-cast NULL function ptr */

     typedef void ( *VoidFP )( int, char ** ); /* typedef for a pointer to a */
					       /* function that returns void */
     typedef struct
     {
	  char *Command;           /* character string for command name    */
	  VoidFP ComFunc;          /* function address for command function */
     } CommStruct;

     static const CommStruct       /* struct CommStruct has cmd-func pairs */
     BuiltinCmds[] =               /* to initialize addresses for builtins */
     {
	  { "cd", Cd },            /* "cd" command          */
	  { "set", Set },          /* "set" command         */
	  { NULL, FNULL }          /* NULL - end of array   */
     };


	To determine whether or not a command is builtin, all you have
	to  do  is  write  a for loop to step through the  BuiltinCmds
	array until you either find the name string for  that  command
	or  hit  the   NULL at the end (similar to the example on page
	246 in the Weiss book).  If the command name string  is  found
	(for  example,  in  location  BuiltinCmds[ i ].Command ), then
	you call the function as  BuiltinCmds[ i ].ComFunc( Argc, Argv
	).  Adding new commands is very simple - just write a function
	to handle it and add another  struct entry to the array.

	You will have to modify the processing of builtin commands  to
	use  the    BuiltinCmds array as described above, and then add
	functions for several new commands.  Here are the descriptions
	(shamelessly copied from the man pages for  ksh) for the buil-
	tin functions that you must provide (in  builtin.c):


	For a "C" (70%) -

	     1. Add code to utilize  the    BuiltinCmds  structure  as
		described above.

	     2. Add code for the following builtin commands:

		 set { name[=value]  } ...

		     The  given    name(s)  are  set  to  the  given
		     value(s).   If  no names are given, the names and
		     values of all environment variables are printed.

		 unset { name }

		     The variables given by the list of   name(s)  are
		     unassigned, i.e., their values and attributes are
		     erased.  If no names are given, then  unset fails
		     quietly.

		 cd [ arg ]
		 cd old new

		     This command can be in either of two  forms.   In
		     the  first  form it changes the current directory
		     to  arg.  If  arg is omitted, then the  directory
		     is  changed  to the home directory, as defined by
		     the  HOME environment variable. The  second  form
		     substitutes  the  string  new for the string  old
		     in the current directory name and tries to change
		     to  this new directory. For either form, the  PWD
		     and  OLDPWD environment variables must be updated
		     to reflect the new directory context.

		 pwd

		     Print the name of the current  working  directory
		     (note  that  the    PWD  environment  variable is
		     user-changeable, and may not be a reliable  indi-
		     cator of what directory is the current one).

	For a "B" (80%) -

	     1. Do everything required for a "C".

	     2. Add code for the following builtin commands:

		 enable [ -anp ] { name }

		     Enable and disable builtin shell  commands.  This
		     allows  the execution of a disk command which has
		     the same name as a shell builtin without specify-
		     ing  a full file name.  If  -n is used, each name
		     is disabled; otherwise, names  are  enabled.  For
		     example,  to  use  the  pwd binary found via the
		     PATH instead of the shell builtin version,  run
		     enable  -n pwd.  If no  name arguments are given,
		     or if the  -p option is supplied, a list  of  all
		     shell  builtins  is printed. With no other option
		     arguments, the  list  consists  of  only  enabled
		     shell builtins. If  -n is supplied, only disabled
		     builtins are printed. If   -a  is  supplied,  the
		     list printed includes all builtins, with an indi-
		     cation of whether or not each is enabled.   Check
		     the  operation of  enable in Bash for sample out-
		     put.

		 echo [ -n ] [ arg ... ]

		     Prints arguments in list  (each  separated  by  a
		     single  space) to the standard output. The output
		     is terminated with  a  newline  unless  the    -n
		     option is used.

		 exit [ # ]

		     Causes the shell to exit  with  the  exit  status
		     specified  by  #. If  # is omitted, then the exit
		     status is  EXIT_SUCCESS.

		 typeset [ -lx ] { name[=value]  } ...

		     Sets attributes and values for environment  vari-
		     ables.  The  following  list of attributes may be
		     specified:

		     -l The given  name(s)  are  set  to  the  given
			value(s) and the  Lowercase attribute is  SET.

		     -x The given  name(s)  are  set  to  the  given
			value(s) and the  Export attribute is  SET.

		If no  name arguments are given but flags  are  speci-
		fied,  a  list  of  names  and values of the variables
		which have these attributes  SET  is  printed.  If  no
		names  or flags are given, the names and attributes of
		all variables are printed.


	For an "A" (90%) -

	     1. Do everything required for a "B".

	     2. For the next three commands, you will need  to  imple-
		ment  a directory stack. You must design and implement
		an appropriate internal data structure  to  make  this
		part  work.   Add  code for the following builtin com-
		mands:

		 dirs

		     Print the directory stack,  most  recent  to  the
		     left;  the  first  directory shown is the current
		     directory.

		 popd [ +n ]

		     Pop the directory stack, and cd to the  new  top
		     directory.  The  elements of the directory stack
		     are numbered from 0 starting at the top.

		     +n  Discard the nth entry in the stack.

		 pushd [ +n | dir ]

		     Push a directory onto the directory  stack.  With
		     no arguments, exchange the top two elements.

		     +n  Rotate the nth entry to the top of the  stack
			 and cd to it.

		     dir Push the current working directory  onto  the
			 stack  and cd to it. If dir is already on the
			 stack, then delete it  before  pushing  (i.e.
			 dir should not be on the stack more than once
			 after a pushd).

		     Note - Check the operation of  dirs,  pushd, and
		     popd in the C shell (csh) for sample behavior.

		 export

		     Equivalent to  typeset -x

		 tolower

		     Equivalent to  typeset -l

	For an "A+" (100%) -

	     1. Do everything required for an "A".

	     2. Add code for the following builtin commands:

		 whence [ -pva ]  command

		     Tells where a particular  command  may  be  found
		     (either as a shell builtin or an external execut-
		     able).  For builtins, the name of the command  is
		     printed.   For  external executables, each of the
		     colon-separated directories listed in the    PATH
		     variable  is  checked  in turn, and the full path
		     name of  the  first  occurence  of  the  file  is
		     printed,  if  it  is  found.  If   command is not
		     found, then nothing is printed.

		     -p Forces a search of the   PATH  variable,  even
			for command names that happen to be builtins.

		     -v Prints  some  additional  information  about
			command.

		     -a Prints   requested   information   about   all
			occurrences  of    command in the command path
			(the default is to print information about the
			first occurrence only).

		     Check the operation of  whence in the Korn  shell
		     or Z shell for sample output.

		 type

		     Equivalent to  whence -v


	     3. Add support for  the  environment  variable    CDPATH,
		which  defines the search path for the argument passed
		to the  cd command.  Within    CDPATH,  the  names  of
		alternative  search  directories  are  separated  by a
		colon (:), and the current directory may be  specified
		by  a dot (.) or by an empty path component, which can
		appear immediately after the equal sign or between the
		colon  delimiters anywhere else in the path list. Note
		that if the argument to  cd begins  with  a  /  then
		CDPATH is not used.  Otherwise, each directory in the
		CDPATH list is searched for the directory given by the
		argument.

	Extra Credit -

	     1. Do everything required for an "A+".

	     2. Add code for the following builtin command:

		 exec [ -cl ] [ -a name ] command { arguments }

		     If  command is specified, it replaces the  shell.
		     No  new process is created. The  arguments become
		     the arguments to  command.  If the  -l option  is
		     supplied, the shell prepends a dash to the zeroth
		      argument passed to  command.   The    -c  option
		     causes  command  to  be  executed  with  an empty
		     environment. If  -a is supplied, the shell passes
		       name  as  the  zeroth argument to the executed
		     command.  If  command cannot be executed for some
		     reason,  exec quietly fails.

	Minimum Functionality -
	     The following features and requirements    must  be  com-
	     pleted,  to  facilitate  testing  and  grading  of future
	     assignments on this project: C1, C2, B1, B2.

