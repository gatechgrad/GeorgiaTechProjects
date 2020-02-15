#include "defs.h"

int 
main( void )
{
    /*
     * DO NOT CHANGE THESE LINES! -- They are used to define
     * the calling convention for the functions in environ.c
     */
    unsigned int EVset( char *SymName, char *SymVal );
    unsigned int EVexport( char *SymName );
    unsigned int EVinit( void );
    void EVprint( unsigned int PrAttr );

    unsigned int EVunset (char *SymName);
    unsigned int EVlower ( char *SymName);
    unsigned int EVupdate( void );



    if( FAILS( EVinit() ) )
    {
	ERR_MSG( EVinit );
	return EXIT_FAILURE;
    }

    PRINTF(( "Before update:\n" ));
    EVprint( SET );

    if( FAILS( EVset( "count", "0" ) ) )
	ERR_MSG( EVset );

    if( FAILS( EVset( "BOOK", "/usr/weiss/book" ) ) )
	ERR_MSG( EVset );

    if( FAILS( EVexport( "BOOK" ) ) )
	ERR_MSG( EVexport );


    if( FAILS( EVset("LEVI", "CommandCom" ) ) )
    ERR_MSG( EVset );

    if( FAILS( EVexport( "0" ) ) )
    ERR_MSG( EVexport );


    if( FAILS( EVset( "NOEXPORT", "Im not exported" ) ) )
    ERR_MSG( EVset );

    if( FAILS( EVlower( "NOEXPORT" ) ) )
    ERR_MSG(EVlower);


    if( FAILS( EVset( "LOWERCASE", "LOWERCASE" ) ) )
    ERR_MSG( EVset );

    if( FAILS( EVexport( "LOWERCASE" ) ) )
    ERR_MSG( EVexport );
   
    if( FAILS( EVunset( "IM NOT DEFINDED" ) ) )
    ERR_MSG( EVunset );

    if( FAILS( EVunset( "HOME" ) ) )
    ERR_MSG( EVunset );

    if( FAILS( EVlower( "I wanna BE IN lOwEr" ) ) )
    ERR_MSG( EVlower);

    if( FAILS( EVunset( "I wanna BE IN lOwEr" ) ) )
    ERR_MSG( EVunset);    

    EVlower( "LOWERCASE" );

    if( FAILS( EVset( "UNSET", "unsetme" ) ) )
    ERR_MSG( EVset );

    if( FAILS( EVexport( "UNSET" ) ) )
    ERR_MSG( EVexport );

    if( FAILS( EVset( "SHOWME", "please show me" ) ) )
    ERR_MSG( EVset );

    if( FAILS( EVexport( "SHOWME" ) ) )
    ERR_MSG( EVexport );


    PRINTF(( "\nBefore Unset:\n" ));
    EVprint( SET );

    EVupdate();    

    if( FAILS( EVunset( "UNSET" ) ) )
    ERR_MSG( EVunset);

    PRINTF(( "\nAfter update:\n" ));
    EVprint( SET );

    EVupdate();

    return EXIT_SUCCESS;
}
