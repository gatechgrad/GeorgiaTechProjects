#include "defs.h"

/*
 * The only sections I was able to complete were C1, C2, B2, and B3... I
 * attempted implementing B4, B5, B6, and A2, but I was not able to 
 * get them working correctly so I took them out of my code before 
 * submission
 * 
 */



static int      iAmper, iFrom, iTo;	/* counts the number of Ampers, From
					                 * and To signs */
static int      iTokenSize;

char 
* GetToken (char *TheLine, char *Str, int *Token)
{

    enum
    {
	Neutral, InQuote, InWord, InDoubleQuotes
    }               State = Neutral;
    int             c;
    int             i, j;			/* for counting */
    char            TheHOMEString[MaxLineLen];	/* to hold the value of 
						                         * the HOME variable */


	char tempArray[MaxLineLen]; 
	char envValue[MaxLineLen];

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
		    /*
		     * set all counters back to zero after returning the line
		     */

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

		/*
		 * If the first char is a ~, then expand it and proceed to
		 * process the word
		 */
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

	    /* words in single quotes take all characters as literal */
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

		case '$':
        printf("dollar case");	
		i = 0;
	
		while (*TheLine != '\0') {		
			printf("\ndump %c", *TheLine);	

	
			tempArray[i] = *TheLine;
			printf("%c", *TheLine);
			*TheLine++;
			i++;

		}
		tempArray[i] = '\0';
		printf("%s dump2", tempArray);
		printf("I'm getting ready to dump");
        printf("%s", EVget(tempArray));


		STRCPY(TheHOMEString, EVget(tempArray));		

		printf("Copied: %s", TheHOMEString);

		for (i = 0; i < (strlen(envValue)); i++) {
		printf("Slim Shady\n");
			iTokenSize++;
			if (iTokenSize < MaxLineLen) {
				*(Str++) = envValue[i];
			}

		}



		continue;

		/*
		 * if there is a ~ in the word, then expand it... it will be
		 * truncated if it expands over MaxLineLen
		 */
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

		/*
		 * process characters followed by a backslash in a special way
		 */
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
