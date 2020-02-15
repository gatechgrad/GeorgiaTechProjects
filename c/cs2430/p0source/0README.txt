
		       Project Assignment - Part 0
			 Symbol Table Management

	     Due Date: by 6:00 PM on Tuesday, April 20, 1999
	       (Late programs will receive a grade of Zero)

   Purpose:
	This assignment is intended to provide a simple,  introductory
	"tour"  of some of the features of the C language, and to give
	some examples of how they  are  used.  In  particular,  it  is
	intended  to  illustrate ways of dealing with some of the more
	troublesome  and  less  easily  understood  aspects   of   the
	language. These would include the following topics:

	      Topic                           Section    Pages
	      Type definitions                2.10        29
	      Call by reference               6.5       132-134
	      Enumerated Types                7.7       159-161
	      Dynamic Allocation              7.9.1     163-164
	      Strings                         8.7       194-202
	      Pointer arrays                  8.14      209-214
	      Structure arrays and pointers   9.2       228-234
	      Bit-Field Structures            9.8.2     245-247

	A thorough understanding of all these  topics  is  not  really
	necessary  to  successfully complete this assignment. However,
	it is expected that sufficient time should  be  spent  looking
	over  the  examples  in  the supplied code and in the book, as
	this will lead to a better  understanding  of  these  features
	when they are discussed in more detail later.

   Readings:
	Read the sections cited above, plus  the  information  on  the
	environment of a process in  environ(5).

   Stub Code:
	The sample code (which can be compiled to create a  functional
	program)  should  have been unpacked along with this document.
	Here are the names of the files in the  archive  and  descrip-
	tions of their contents:

       0README.ps    this file (PostScript version)
       0README.txt   this file (text version)
       Makefile      rules for compiling this assignment
       defs.h        header file for #includes, typedefs, macros, etc.
       environ.c     source code for symbol table functions
       envmain.c     the "main" function for this assignment

	In addition to the above, the  following  executables  can  be
	found in the  ~cs2430/pub/fsh/bin directory:

   jkg_showenv   executable version of the final production code
   jkg_testenv   a shell ``wrapper'' for testing the production code
   showenv       executable version of the original (as distributed) code.
   testenv       a shell ``wrapper'' for testing the original code

	To run the programs in the bin directory, you should use the
	testenv  shell  scripts. These scripts are provided to facili-
	tate program testing by providing a ``clean'' interface to the
	  showenv  program.  They are primarily used to initialize the
	environment before calling  showenv.  If  you  have  too  many
	shell  variables  in  your  current environment, then the test
	programs will fail (there is  only  sufficient  space  for  25
	environment  variables  in  the original version - this number
	can be increased by changing the definition  for  MAXVAR  in
	defs.h).  The  testenv programs take care of this be unsetting
	extraneous variables, and by allowing you to define  your  own
	variables  to  be  passed to your version of the  showenv pro-
	gram. Instructions for editing the scripts are included in the
	scripts themselves.

	The  envmain.c routine will be used to drive the functions you
	write  as  part of  your submitted code. The files you turn in
	will be recompiled with a different  envmain.c  for  the  pur-
	poses of testing and grading. Make sure that you do not change
	any of the function prototype declarations within  envmain.c -
	these  define  the  interface  that is expected from your rou-
	tines.

       +-------------------------------------------------------------+
       | Programs that do not compile as a result of modifications   |
       | to the supplied prototypes will be considered uncompileable |
       | programs, and will receive a grade of ZERO (0).             |
       +-------------------------------------------------------------+

	You are encouraged to devise  more  stringent  and  exhaustive
	test  suites to make sure that your program works, but   don't
	assume that your program is working correctly just because  it
	matches  the  output  from the production version.  Your grade
	will be based on how well your program performs when  compiled
	with the grader's version of  envmain.c.

   Modifications:
	Make the following modifications to the existing version  of
	environ.c:


	0. Add a bit-field to the  struct attr  declaration  to  allow
	   for  variables  to be flagged as  Lowercase.  You will have
	   to change some of the other routines to provide for correct
	   interaction  with  these  types of variables (for example,
	   EVset() will have to be changed to properly handle  assign-
	   ments  to these variables). You must also modify  EVprint()
	   to show what variables are lowercase - the supplied produc-
	   tion  program  will  give  you  an  idea  of what output is
	   expected.


	1. Add a function with the following prototype:


	   unsigned int EVlower ( char *SymName )

	   This function will set the lowercase attribute for a  vari-
	   able  (and  modify  the  variable's value accordingly). The
	   declaration and usage of this function should be similar to
	   that of  EVexport().


	2. Add a function with the following prototype:

		   unsigned int EVunset ( char *SymName )

	   This function will remove a variable and its value from the
	   symbol  table.  The  declaration and usage of this function
	   should be similar to  EVset(), except that there is no need
	   to  pass  anything  other  than  a  pointer to the  SymName
	   string.  Note that unsetting a variable is not the same  as
	   just  assigning  it  an empty value - both the name and its
	   associated value must be removed from the symbol table.


	3. Add a function with the following prototype:

	       static unsigned int unassign ( char **SymName )

	   This function will deallocate space from  a  variable  that
	   was  unset via  EVunset(). The declaration of this function
	   should be similar to that of  assign(), except  that  there
	   is  no  need  to  pass anything other than a pointer to the
	   pointer to the  SymName string. To do  this  properly,  you
	   must use the  free(3c) system call, which deallocates space
	   that was previously allocated.  Note that    free(3c)  does
	   not  dereference  the  pointer to the deallocated space, so
	   you must explicitly assign it a value of  NULL (   NULL  is
	   the  value  that  is  assigned  to a pointer that no longer
	   points to anything).


	4. Add a function with the following prototype:

		       unsigned int EVupdate ( void )

	   This function rebuilds the process  environ array by  copy-
	   ing  the  information  from  the    SymTab  into  it. Each
	   SymEntry for which the   Exported  attribute  is    SET  is
	   copied  into  an  entry in the  environ array. The  environ
	   array entries are always nul-terminated strings of the form
	     FOO=BAR,  where  FOO is the variable's  SymName, and  BAR
	   is its  SymValue (see  environ(5) for more details).  The
	   EVupdate(  ) function will have to allocate and de-allocate
	   memory as necessary to update the array with the new infor-
	   mation from the  SymTab (Note - it will probably be easiest
	   just to rebuild the entire  environ  array  structure  from
	   scratch,  each time that  EVupdate( ) is called, but  watch
	   out for memory leaks!


	5. Modify the  EVinit( ) function to fix all the  things  that
	   are wrong with it (indicated in the source code).


   Extra Credit:
	For 10% extra credit on this project  part    only,  you  must
	modify  your  routines  to correctly deal with lowercase vari-
	ables that are inherited from the  calling  environment  (i.e.
	the  environment that was passed to your process by its parent
	process). Of the more popular "standard"  shells  (Bourne,  C,
	and  Korn  shells), only the Korn shell supports this feature.
	Your program must  recognize  inherited  lowercase  variables,
	internally  mark them as such, and export them as lowercase to
	any other processes that are created  from  within  that  same
	environment.

	In the Korn shell, lowercase variables are declared by using
	typeset  -u <list> (where  <list> is an optional list of shell
	variables to be set) and are identified in the environment  by
	a special shell variable whose name is  A__z. This variable is
	not normally visible from the shell level, but can be seen  by
	accessing  the  environment  directly (as the  showenv program
	does). The names of the variables are separated by the charac-
	ter  string  "=(  "  (there is a single space after the dollar
	sign).  For example, if you had declared the  variables    FOO
	and  BAR to be lowercase (by entering  typeset -u FOO BAR from
	the command prompt), then the  A__z variable  would  have  the
	value   ``( FOO=( BAR'' (the double quotes are shown for clar-
	ity - they would not actually be present in the name string).

	To get the extra credit,  your  program  must  check  for  the
	existence  of    A__z, extract out the list of lowercase vari-
	ables, and mark them as such in  the    SymTab[]  array.  Once
	those  variables  have been marked, the  A__z variable must be
	unset. The easiest place to do this  is  in  the    EVinit(  )
	function.

	In addition, your program must build a new copy of  A__z when-
	ever the environment is updated for exporting to a new program
	(this is done by  EVupdate( )).  This part is little trickier,
	because  you  must  make two passes over the  SymTab[] array -
	one to find out how much space to allocate for exported lower-
	case  variables,  and  then a second to actually build the new
	copy of  A__z.  After determining how much  space  is  needed,
	use    malloc(3c) or  realloc(3c) (as appropriate) to allocate
	space for  A__z (and its value) in  the  last  slot  of  the
	*environ[]  array.   You must then make a second pass to build
	the value string to be assigned to  A__z. This part is  diffi-
	cult  to test, so be prepared to ask for help if you get stuck
	for ideas.

