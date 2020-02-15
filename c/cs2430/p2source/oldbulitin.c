#include "defs.h"

#define ERR_USER( p )	FPRINTF(( stderr, "%s: " p "\n", Argv[ 0 ] ))

static void
Assign (int Argc, char *Argv[])
{
    char           *ArgPtr;

    if (Argc != 1)
    {
	ERR_USER ("Too many args");
    } else
    {
	if ((ArgPtr = strchr (Argv[0], '=')) == NULL)
	    ArgPtr = strchr (Argv[0], '\0');
	else
	    *ArgPtr++ = '\0';

	if (FAILS (EVset (Argv[0], ArgPtr)))
	{
	    ERR_USER ("Can't assign");
	}
    }
}

static void
Set (int Argc, char *Argv[])
{
    if (Argc != 1)
    {
	ERR_USER ("Too many args");
    } else
	EVprint (UNSET);
}

int
Builtin (FullCommand * Buf)
{
    char           *Path;
    char           *Base;
    char           *Directory;


    int             Argc = 0;
    char          **Argv = Buf->Commands[0];

    while (Argv[Argc] != NULL)
	Argc++;

    if (Argc == 0)
	return FAILURE;

    if (strchr (Argv[0], '=') != NULL)
	Assign (Argc, Argv);

    else if (strcmp (Argv[0], "set") == 0)
	Set (Argc, Argv);

    else
	/*
	 * routines to change the directory
	 */
    if (strcmp (Argv[0], "cd") == 0)
    {
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
    } else
	/*
	 * Prints the current directory
	 */

    if (strcmp (Argv[0], "pwd") == 0)
    {

	printf ("%s\n", getwd ());

    } else
	return FAILURE;
    if (Buf->NumPipes ||
	strcmp (Buf->InFile, "") != 0 || strcmp (Buf->OutFile, "") != 0)
    {
	ERR_USER ("Illegal redirect or pipeline");
    }
    return SUCCESS;
}
