#include "defs.h"

#define ERR_USER( p )	FPRINTF(( stderr, "%s: " p "\n", Argv[ 0 ] ))
#define FNULL ( ( VoidFP ) NULL )


static void     Cd (int Argc, char *Argv[]);
static void     Set (int Argc, char *Argv[]);
static void     Unset (int Argc, char *Argv[]);
static void     Pwd (int Argc, char *Argv[]);
static void     Enable (int Argc, char *Argv[]);
static void     Echo (int Argc, char *Argv[]);
static void     Exit (int Argc, char *Argv[]);
static void     Typeset (int Argc, char *Argv[]);
static void     Dirs (int Argc, char *Argv[]);
static void     Popd (int Argc, char *Argv[]);
static void     Pushd (int Argc, char *Argv[]);
static void     Export (int Argc, char *Argv[]);
static void     ToLower (int Argc, char *Argv[]);
static void     Whence (int Argc, char *Argv[]);
static void     Exec (int Argc, char *Argv[]);

/*** FROM ASSIGNMENT II NFO FILE ***/
typedef void    (*VoidFP) (int, char **);

typedef struct
{
    char           *Command;
    VoidFP          ComFunc;
}               CommStruct;

static          const
                CommStruct BuiltinCmds[] =
{

    {"cd", Cd},
    {"set", Set},
    {"unset", Unset},
    {"pwd", Pwd},
    {"enable", Enable},
    {"echo", Echo},
    {"exit", Exit},
    {"typeset", Typeset},
    {"dirs", Dirs},
    {"popd", Popd},
    {"pushd", Pushd},
    {"export", Export},
    {"tolower", ToLower},
    {"whence", Whence},
    {"exec", Exec},

    {NULL, FNULL}


};


/*** FROM WEISS PG 246 ***/

void
DoCommand (const char *Comm, int Argc, char *Argv[])
{
    int             i;

    const CommStruct *Ptr;

    i = 0;
    while ((BuiltinCmds[i].Command != NULL) && (strcmp (BuiltinCmds[i].Command, Comm) != 0))
    {

	/* printf("Checking: %s\n", BuiltinCmds[i].Command);  */
	i++;

    }


    if (BuiltinCmds[i].Command != NULL)
    {
	/* printf("The command is %s", BuiltinCmds[i].Command);   */

	BuiltinCmds[i].ComFunc (Argc, Argv);
    } else
    {
	ERR_USER ("No such file or directory");

    }

}


static void
Assign (int Argc, char *Argv[])
{
    char           *ArgPtr;

    if (Argc > 2 )
    {
	ERR_USER ("Too many args");
    } else
    {
	if ((ArgPtr = strchr (Argv[1], '=')) == NULL)
	    ArgPtr = strchr (Argv[1], '\0');
	else
	    *ArgPtr++ = '\0';

	if (FAILS (EVset (Argv[1], ArgPtr)))
	{
	    ERR_USER ("Can't assign");
	}
    }
}

static void
Set (int Argc, char *Argv[])
{

    char           *SymName;
    char           *SymVal;

    if (Argc > 2)
    {
	ERR_USER ("Too many args");
    } else if (Argc == 1)
    {
	EVprint (UNSET);
    } else if (Argc == 2)
    {

	/* printf("%s\n", Argv[1]);  */



	if ((SymVal = strchr (Argv[1], '=') == NULL))
	{
	    EVset (Argv[1], "");
	} else
	{

	    /*
	     * Here is where I must find a way to separate the token before
	     * the '=' from the token after the '='. I suppose I'll have to
	     * modify token.c to handle this.
	     */


    if( strchr( Argv[ 1 ], '=' ) != NULL )
    Assign( Argc, Argv );

	}

    }
}

/*
 * Cd - changes the directory
 */

static void
Cd (int Argc, char *Argv[])
{
    char           *Path;

    chdir (".");
    if (FAILS (EVset ("OLDPWD", getwd ())))
    {
        ERR_USER ("Can't assign OLDPWD");
    }


    if (Argc > 1)
    {

	Path = Argv[1];

    } else if ((Path = EVget ("HOME")) == NULL)
	Path = ".";
    if (chdir (Path) == -1)
    {
	ERR_USER ("Can't change directories");
    } else
    {

	if (FAILS (EVset ("PWD", getwd ())))
	{
	    ERR_USER ("Can't assign");
	}
    }
}

/*
 * Pwd - Prints the current directory
 */
static void
Pwd (int Argc, char *Argv[])
{

    /*
     * for some reason, if a new shell is started and pwd is the first
     * command, it will coredump unless I use chdir(".")
     */
    chdir (".");
    printf ("%s\n", getwd ());

}

/*
 * Unset - to be implemented
 */
static void
Unset (int Argc, char *Argv[])
{

    int             i;
    if (Argc < 2)
    {
	/* not enough values... do nothing */
    } else
    {

	for (i = 1; i < Argc; i++)
	{
	    EVunset (Argv[i]);
	}
    }



}

/*
 * Enable - to be implemented
 */
static void
Enable (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * Echo - to be implemented
 */
static void
Echo (int Argc, char *Argv[])
{
    int             i;

    if (Argv[1] == NULL)
    {
	printf ("\n");
    } else if ((strcmp (Argv[1], "-n")) != 0)
    {
	for (i = 1; i < Argc; i++)
	{
	    printf ("%s ", Argv[i]);
	}
	printf ("\n");
    } else if ((strcmp (Argv[1], "-n")) == 0)
    {
	for (i = 2; i < Argc; i++)
	{
	    printf ("%s ", Argv[i]);
	}
    }
}

/*
 * Exit - exits the shell
 */
static void
Exit (int Argc, char *Argv[])
{

    switch (Argc)
    {
	case 1:
	exit (EXIT_SUCCESS);
	break;
    case 2:
	exit (Argv[1]);
	break;
    default:
	ERR_USER ("Too many args");

    }
}

/*
 * Typeset - to be implemented
 */
static void
Typeset (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * Dirs - to be implemented
 */
static void
Dirs (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * Popd - to be implemented
 */
static void
Popd (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * Pushd - to be implemented
 */
static void
Pushd (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * Export - to be implemented
 */
static void
Export (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * ToLower - to be implemented
 */
static void
ToLower (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * Whence - to be implemented
 */
static void
Whence (int Argc, char *Argv[])
{

    /* Code goes here */
}

/*
 * Exec - to be implemented
 */
static void
Exec (int Argc, char *Argv[])
{

    /* Code goes here */
}

int
Builtin (FullCommand * Buf)
{
    int             Argc = 0;
    char          **Argv = Buf->Commands[0];


    while (Argv[Argc] != NULL)
	Argc++;

    if (Argc == 0)
	return FAILURE;

    DoCommand (Argv[0], Argc, Argv);


    if (Buf->NumPipes ||
	strcmp (Buf->InFile, "") != 0 || strcmp (Buf->OutFile, "") != 0)
    {
	ERR_USER ("Illegal redirect or pipeline");
    }
    return SUCCESS;
}
