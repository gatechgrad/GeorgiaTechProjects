
                       Project Assignment - Part 3
                             Memory Manager

              Due Date: by 6:00 PM on Friday, June 4, 1999


   Readings:
        Study the material that was discussed in lecture on  Thursday,
        May 27.  for this project, you will be writing a storage allo-
        cator similar to what was described in lecture.

   Stub Code:
        You can retrieve the baseline source for the the storage allo-
        cator  by  doing  a    getjob  prj3  from  the CS 2430  workon
        environment. You should receive the following files:


             1README.ps    this file (Postscript version)
             1README.txt   this file (text version)
             Makefile      Makefile that uses your own environ.c
             malloc.c      source code for malloc() function


        You will also need the source files from your project Part  0.
        There  are  no sample executables for this part - you can test
        your output against the "original" output  from  your  project
        Part 0.


   Modifications:
        Make the following modifications to the existing version  of
        malloc.c:


        1. Add the function  realloc() to the others in the   malloc.c
           file. The calling convention for your version of  realloc()
           must be identical to  that  of  the  library  version  of
           realloc()  shown  in the man pages (otherwise the realloc()
           calls in your environ.c will not work properly).   You  may
           use  the  memcpy(3C) library call to move stuff around when
           necessary.


        2. Add the function  calloc() to the others in the    malloc.c
           file.  The calling convention for your version of  calloc()
           must be identical to  that  of  the  library  version  of
           calloc()  shown  in  the  man  pages.  You  may  use  the
           memset(3C) library call to initialize the allocated block.


          It should not be necessary  to  make  any  modifications  to
        either your environ.c or the supplied versions of malloc() and
        free().  There is one caveat for people who use a source-level
        debugger  (such  as  dbx).   To prevent the "new" versions of
        malloc(),  realloc(), etc. from  clashing  with  the  "system"
        versions  (which  have  the  same  names),  the  functions are
        automatically renamed by the Makefile  to  be    __malloc(),
        __realloc(),  etc. This happens globally for all source files,
        so you will need to remember what you're looking for when  you
        start  poking  around  with  the debugger (the use of which is
        strongly recommended for this part of the project). Because it
        is  applied uniformly, this renaming of these functions should
        not adversely affect the  operation  of  the  program.  So  if
        you're  not  using  a debugger, then you should not notice any
        change in how the functions work.


   Extra Credit:
        For 10% extra credit on this part  only, you must modify your
        environ.c  to  use  the  calloc() function to reserve and ini-
        tialize the space for the  SymTab[] array. This seems like  it
        out to require a major re-write of  environ.c, but the changes
        are actually minimal. You will have to modify the  declaration
        of  the    SymTab[]  array  as well as its initialization (in
        EVinit()), but not much else outside of that. Remember  that
        calloc()  initializes  everything  to zeroes (which is exactly
        what C does with static variables)  so  everything  else  will
        remain  consistent  (assuming  that  you call  EVinit() before
        attempting any other operations on the  SymTab[] array).


