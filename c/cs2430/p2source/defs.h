/* Begin definitions from the Weiss text, page 393 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <unistd.h>
#include <errno.h>
#include <signal.h>

/* Simple Shell */

/* A Simple Command Is An Array Of Up To Maxargc+1 Strings */
/* The Last String Is NULL */
/* This Can Be Used Directly For execvp */
/* Very Limited Error Checking To Keep Code Short */

#define MaxArgc		10
#define MaxPipes	10
#define FileNameLen	MaxLineLen
#define MaxLineLen	256

typedef char   *SimpleCommand[MaxArgc + 1];

typedef struct
{
    char            InFile[FileNameLen];
    char            OutFile[FileNameLen];
    int             NumPipes;
    SimpleCommand   Commands[MaxPipes];
    int             BackGround;
}               FullCommand;

enum
{
    Eoln, Error, From, To, Pipe, Word, Amper
};

/* Read A Line, return NULL on EOF */
#define GetLine( S )	( fgets( S, MaxLineLen, stdin ) )

/* End definitions from the Weiss book, page 393 */

enum
{
    UNSET, SET
};

enum
{
    FAILURE, SUCCESS
};

/*
 * These are convenience macros for testing the return values of the functions
 * used in this project. Since C does not have a true boolean type, it is best
 * to do explicit comparisons to predefined values to determine the success or
 * failure of a function.
 * 
 * These macros simplify that task, while at the same time abstracting away
 * specific details of how the return value is tested.
 */

#define SUCCEEDS( m )		( ( m ) == SUCCESS )
#define FAILS( m )		( ( m ) != SUCCESS )

/*
 * The ERR_MSG macro prints a message on stderr describing where an error
 * occured and what its cause was (if known)
 */

#define ERR_MSG( func )						\
    { 								\
	( void )fflush( stderr );				\
	( void )fprintf( stderr, __FILE__ ":%d:" #func ": %s\n",\
		__LINE__, strerror( errno ) );			\
    }

/*
 * The PRINTF_LIKE macro is a wrapper for functions (like printf(3s)) that
 * people tend to want to ignore the return value of. Its main purpose is to
 * check the return value, which keeps lint happy.
 */

#define PRINTF_LIKE( fn, args, test )	if( fn args test ) ERR_MSG( fn )

/*
 * The STR_LIKE macro is a wrapper for functions (like strcat(3c)) that people
 * tend to want to ignore the return value of. Its main purpose is to check
 * the return value, which keeps lint happy.
 */

#define STR_LIKE( fn, s1, s2 )		if( fn( s1, s2 ) != s1 )\
						ERR_MSG( fn )

/*
 * The STRN_LIKE macro is a wrapper for functions (like strncat(3c)) that
 * people tend to want to ignore the return value of. Its main purpose is to
 * check the return value, which keeps lint happy.
 */

#define STRN_LIKE( fn, s1, s2, n )	if( fn( s1, s2, n ) != s1 )\
						ERR_MSG( fn )

/*
 * PRINTF_LIKE functions - these require the (( ... )) "trick" because they
 * take a variable number of arguments
 */

#define PRINTF( args )		PRINTF_LIKE( printf, args, < 0 )
#define FPRINTF( args )		PRINTF_LIKE( fprintf, args, < 0 )
#define SPRINTF( args )		PRINTF_LIKE( sprintf, args, < 0 )

/*
 * STR_LIKE and STRN_LIKE functions - single parens work fine here
 */

#define STRCAT( s1, s2 )	STR_LIKE( strcat, (s1), (s2) )
#define STRCPY( s1, s2 )	STR_LIKE( strcpy, (s1), (s2) )

#define STRNCAT( s1, s2, n )	STRN_LIKE( strncat, (s1), (s2), (n) )
#define STRNCPY( s1, s2, n )	STRN_LIKE( strncpy, (s1), (s2), (n) )

/* DO NOT CHANGE ANYTHING ABOVE THIS LINE     */

/* Add local definitions that are specific to your project here */
