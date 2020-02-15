#include "defs.h"

char *
GetToken( char *TheLine, char *Str, int *Token );

static void
Prompt( int Num )
{
    PRINTF(( "[%d] ", Num ));
    if( fflush( stdout ) == EOF )
	ERR_MSG( fflush );
}

int
main( void )
{
    unsigned int EVinit( void );

    int CommandNum = 1, Token;
    char *TheLine;
    char Str[ MaxLineLen ];
    char OneLine[ MaxLineLen ];  

    if( FAILS( EVinit() ) )
    {
	ERR_MSG( EVinit );
	return EXIT_FAILURE;
    }

    for( Prompt( CommandNum++ ); GetLine( OneLine ); )
    {
	TheLine = OneLine;
	Token = Word;
	while( ( Token != Error ) && 
		( TheLine = GetToken( TheLine, Str, &Token ) ) )
	    switch( Token )
	    {
	    default:
	    case Error:
		PRINTF(( "Syntax error\n" ));
		break;
	    case From:
		PRINTF(( "From <%s>\n", Str ));
		break;
	    case To:
		PRINTF(( "To <%s>\n", Str ));
		break;
	    case Amper:
		PRINTF(( "Amper\n" ));
		break;
	    case Pipe:
		PRINTF(( "Pipe\n" ));
		break;
	    case Eoln:
		PRINTF(( "Eoln\n" ));
		break;
	    case Word:
		PRINTF(( "Word <%s>\n", Str ));
		break;
	    }
	Prompt( CommandNum++ );
    }
    return EXIT_SUCCESS;
}
