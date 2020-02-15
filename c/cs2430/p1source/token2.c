#include "defs.h"

static int      iAmper, iFrom, iTo;	/* counts the number of Ampers, From,
					 * and To signs */
static int      iTokenSize;

char           *
GetToken (char *TheLine, char *Str, int *Token)
{

    enum
    {
	Neutral, InQuote, InWord, InDoubleQuotes
    }               State = Neutral;
    int             c;
    int             i;
    char            TheHOMEString[MaxLineLen];


    *Token = Word;
    *Str = '\0';
    iTokenSize = 0;

    while (*TheLine != '\0')

	switch (State)
	{
	case Neutral:


	    switch (c = *TheLine++)
	    {
	    case ' ':
	    case '\t':
		continue;
	    case '&':

		if (iAmper == 0)
		{
		    *Token = Amper;
		    iAmper++;
		    return TheLine;
		} else
		{
		    *Token = Error;
		    iAmper = 0;
		    iFrom = 0;
		    iTo = 0;
		    return TheLine;
		}

		continue;

	    case '|':
		*Token = Pipe;
		return TheLine;
	    case '\n':
		*Token = Eoln;
		iAmper = 0;
		iFrom = 0;
		iTo = 0;
		return TheLine;
	    case '<':

		if (iFrom == 0)
		{
		    *Token = From;
		    iFrom++;

		} else
		{
		    *Token = Error;
		    iAmper = 0;
		    iFrom = 0;
		    iTo = 0;
		    return TheLine;
		}


		continue;

	    case '>':
		if (iTo == 0)
		{
		    *Token = To;
		    iTo++;

		} else
		{
		    *Token = Error;
		    iAmper = 0;
		    iFrom = 0;
		    iTo = 0;
		    return TheLine;
		}

		continue;

	    case '~':


		if (EVget ("HOME") == NULL)
		{

		    return FAILURE;
		} else
		{
		    STRCPY (TheHOMEString, EVget ("HOME"));

		    for (i = 0; i < (strlen (TheHOMEString)); i++)
		    {
			iTokenSize++;

			if (iTokenSize < MaxLineLen)
			{
			    *(Str++) = TheHOMEString[i];
			} else
			{
			    return TheLine;
			}


		    }
		}

		State = InWord;
		continue;

	    case '\'':
		State = InQuote;
		continue;

	    case '\"':
		State = InDoubleQuotes;
		continue;


	    default:
		if (iTokenSize < MaxLineLen)
		{
		    iTokenSize++;
		    *(Str++) = c;
		    State = InWord;

		}
		continue;
	    }

	case InQuote:

	    switch (c = *TheLine++)
	    {

	    case '\'':
		State = InWord;
		continue;


	    case '\n':
		*Token = Error;
		return TheLine;
		continue;

	    default:
		if (iTokenSize < MaxLineLen)
		{
		    iTokenSize++;
		    *(Str++) = c;

		}
		continue;
	    }


	case InWord:


	    switch (c = *(TheLine++))
	    {
	    case '&':
	    case '|':
	    case '<':
	    case '>':
	    case '\n':
		TheLine--;
		/* FALLTHROUGH - this is an intentional fall-through */
	    case ' ':
	    case '\t':
		*Str++ = '\0';

		return TheLine;
	    case '\'':
		State = InQuote;
		continue;

	    case '\"':
		State = InDoubleQuotes;
		continue;

	    case '~':

		if (EVget ("HOME") == NULL)
		{
		    return FAILURE;
		} else
		{
		    STRCPY (TheHOMEString, EVget ("HOME"));

		    for (i = 0; i < (strlen (TheHOMEString)); i++)
		    {
			iTokenSize++;

			if (iTokenSize < MaxLineLen)
			{
			    *(Str++) = TheHOMEString[i];
			}
		    }
		}
		continue;

	    default:

		if (iTokenSize < MaxLineLen)
		{
		    iTokenSize++;
		    *(Str++) = c;

		}
		continue;
	    }


	case InDoubleQuotes:
	    switch (c = *TheLine++)
	    {

	    case '\"':
		State = InWord;
		continue;

	    case '\n':
		*Token = Error;
		return TheLine;


	    case '\\':

		switch (c = *TheLine++)
		{

		case 'b':
		    *Str++ = '\b';
		    continue;

		case 'f':
		    *Str++ = '\f';
		    continue;

		case 'n':
		    *Str++ = '\n';
		    continue;

		case 'r':
		    *Str++ = '\r';
		    continue;

		case 't':
		    *Str++ = '\t';
		    continue;

		case 'v':
		    *Str++ = '\v';
		    continue;

		case '\\':
		    *Str++ = '\\';
		    continue;

		case '\"':
		    *Str++ = '\"';
		    continue;

		case 'o':
		    continue;

		case '\n':
		    *Token = Error;

		    return TheLine;

		default:
		    /* handled by next default */
		    continue;
		}
		continue;


		/* cut and paste from my own work */
	    case '~':

		if (EVget ("HOME") == NULL)
		{
		    return FAILURE;
		} else
		{
		    STRCPY (TheHOMEString, EVget ("HOME"));

		    for (i = 0; i < (strlen (TheHOMEString)); i++)
		    {
			iTokenSize++;

			if (iTokenSize < MaxLineLen)
			{
			    *(Str++) = TheHOMEString[i];
			}
		    }
		}
		continue;

	    default:
		*Str++ = c;
		continue;
	    }

	}
    return NULL;
}
