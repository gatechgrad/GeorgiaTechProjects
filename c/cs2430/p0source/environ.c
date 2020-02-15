#include "defs.h"

#define MAXVAR	100	/* Maximum number of variables that can be stored */
#define SYM_NAME_SIZE 20 /* The maximum length of a system symbol name */

/*
 * A bit-field structure that defines all attributes for a variable
 */

struct attrib
{
    unsigned int Exported : 1;
    unsigned int isLowercase : 1; 

};

/*
 * This is a type definition for a structure that stores a symbol's
 * name, its associated value (both are character strings), and
 * attributes (a bit-field structure).
 */

typedef struct
{
    char *SymName;		/* symbol name string */
    char *SymVal;		/* symbol value string*/
    struct attrib SymAttr;	/* symbol attributes */
} SymEntry;

/*
 * A pointer to the process environment - see environ(5) for details
 */

extern char **environ;

/*
 * This defines an array of structures, one for each symbol in the
 * symbol table. The size of the array is determined by the value of
 * MAXVAR. Generally speaking, hard-coded limits on stuff like this
 * are bad, since the number of SymEntries may be dynamic.
 */

static SymEntry SymTab[ MAXVAR ];


/* function prototypes (to keep lint from complaining) */

static unsigned int assign( char **Name, char *Val );
unsigned int EVunset( char *SymName );
int ToLower (char Ch);



/*
 * EVfind( )
 *
 * Searches sequentially through the SymTab array for an entry whose
 * SymName matches the parameter. If the requested SymName is found,
 * then a pointer to its SymEntry is returned, otherwise a pointer to
 * the first empty SymEntry (as indicated by a NULL SymName) is returned.
 * If the supplied SymName is not found, and no empty SymEntry is found,
 * then EVfind( ) returns NULL.
 *
 * SymName  - points to the variable name to find
 */
static SymEntry *
EVfind( char *SymName )
{
    SymEntry *SymP, *ThisP;

    /*
     * Step through the array using pointer arithmetic
     */
    for( SymP = NULL, ThisP = SymTab; ThisP - SymTab < MAXVAR; ThisP++ )
    /*
     * check for NULL entry - if it's the first one we have
     * encountered, then save its address, otherwise continue
     */
        if( ThisP->SymName == NULL )
    {
        if( SymP == NULL )  /* true only for first NULL entry */
        SymP = ThisP;
        }
        else
    /*
     * check for a match - if successful, then break out of the loop
     */
    if( strcmp( ThisP->SymName, SymName ) == 0 )
    {
        SymP = ThisP;
        break;
        }
    return SymP;
}

static unsigned int
unassign( char **SymName )
{

    if( *SymName == NULL ) {
        return SUCCESS;
    } else { 
        free(*SymName);    
        *SymName = NULL;

    }
    return SUCCESS;
}


unsigned int
EVunset( char *SymName )
{

    SymEntry *SymP;

    if( ( SymP = EVfind( SymName ) ) == NULL )
        return FAILURE;     /* no space in SymTab */

    /*
     * delete SymName from SymTab
     */
    if( SUCCEEDS( unassign( &SymP->SymName ) ) ) 
      return SUCCESS;
    else
      return FAILURE; 
}

unsigned int
EVlower( char *SymName )
{
   int i = 0;
   SymEntry *SymP;

    if( ( SymP = EVfind( SymName ) ) == NULL )
        return FAILURE;     /* no space in SymTab */

    /*
     * add SymName to SymTab if it's not there
     */
    if( SymP->SymName == NULL )
        if( FAILS( assign( &SymP->SymName, SymName ) ) ||
        FAILS( assign( &SymP->SymVal, "" ) ) )
        return FAILURE;

    /*
     * set Lowercase attribute
     */
    SymP->SymAttr.isLowercase = SET;


    for (i = 0; i < strlen(SymP->SymVal); i++) {

        #ifdef DEBUG 
 	  	     printf("*** Passing in %c\n", SymP->SymVal[i]);
   		     printf("*** Returned %c\n", ToLower(SymP->SymVal[i]));
        #endif


        SymP->SymVal[i] = ToLower(SymP->SymVal[i]);                
	}	
		
		#ifdef DEBUG
        PRINTF(( "*** %s=%s\n", SymP->SymName,
                SymP->SymVal == NULL ? "" : SymP->SymVal ));

		#endif

    return SUCCESS;

}

/*
 * ToLower - I modified the ToUpper code in the Weiss book to change the 
 *           character to lowercase instead of uppercase. (Figure 8.7)
 */

int ToLower (char Ch) {
    switch ( Ch ) {
        case 'A' : return 'a';
        case 'B' : return 'b';
        case 'C' : return 'c';
        case 'D' : return 'd';
        case 'E' : return 'e';
        case 'F' : return 'f';
        case 'G' : return 'g';
        case 'H' : return 'h';
        case 'I' : return 'i';
        case 'J' : return 'j';
        case 'K' : return 'k';
        case 'L' : return 'l';
        case 'M' : return 'm';
        case 'N' : return 'n';
        case 'O' : return 'o';
        case 'P' : return 'p';
        case 'Q' : return 'q';
        case 'R' : return 'r';
        case 'S' : return 's';
        case 'T' : return 't';
        case 'U' : return 'u';
        case 'V' : return 'v';
        case 'W' : return 'w';
        case 'X' : return 'x';
        case 'Y' : return 'y';
        case 'Z' : return 'z';

        default : return Ch;
    }
}

unsigned int
EVupdate( void )
{
    SymEntry *SymP;

    for (SymP = SymTab; SymP - SymTab < MAXVAR; SymP++) {

        if (SymP->SymName != NULL ) {

            #ifdef DEBUG
	        PRINTF(( "Checking -> %s=%s\n", SymP->SymName,
	            SymP->SymVal == NULL ? "" : SymP->SymVal ));
            #endif

           /* if the Exported attribute is not set, then
            * remove it
            */

	       if (SymP->SymAttr.Exported != SET) {
	           EVunset( SymP->SymName );    
           } 
		} else {
            return FAILURE;
       
        }


    } 


    return SUCCESS;
}


/*
 * assign( )
 *
 * reads in a Symbol name and a symbol value, then 
 * allocates space for it and places the data in the
 * allocated space.
 *
 * Name - the name of the symbol
 * Val - the value of the symbol
 */

static unsigned int
assign( char **Name, char *Val )
{
    void *TmpP;

    if( *Name == NULL )
    {
	if( ( *Name = malloc( strlen( Val ) + 1 ) ) == NULL )
	    return FAILURE;
    }
    else
    if( ( TmpP = realloc( *Name, strlen( Val ) + 1 ) ) == NULL )
        return FAILURE;
    else
	*Name = TmpP;

    if( strcpy( *Name, Val ) != *Name )
	return FAILURE;
    else
	return SUCCESS;
}

/*
 * EVset( )
 *
 * Attempts to find the supplied SymName in the SymTab. If it is found,
 * or if an empty SymEntry is found, then the supplied SymName and its
 * SymValue are put into the SymTab. If the SymName cannot be found or
 * inserted (due to a full SymTab), then EVset( ) returns FAILURE,
 * otherwise it returns SUCCESS.
 *
 * SymName	- points to the variable name to set
 * SymwValue	- points to the value to set
 */

unsigned int
EVset( char *SymName, char *SymVal )
{
    SymEntry *SymP;

    if( ( SymP = EVfind( SymName ) ) == NULL )
        return FAILURE;		/* no space in SymTab */

    /*
     * add SymName to SymTab 
     */
    if( SUCCEEDS( assign( &SymP->SymName, SymName ) ) &&
	    SUCCEEDS( assign( &SymP->SymVal, SymVal ) ) )
	return SUCCESS;
    else
	return FAILURE;
}


/*
 * EVexport( )
 *
 * Attempts to find the supplied SymName in the SymTab. If an empty
 * SymEntry is found, then the supplied SymName is put into the SymTab
 * with an empty string as its value. If the SymName is found, then
 * its SymValue is unchanged. In either case, the Exported attribute
 * for that SymName is then SET. If the SymName cannot be found or
 * inserted (due to a full SymTab), then EVexport( ) returns FAILURE,
 * otherwise it returns SUCCESS.
 *
 * SymName	- points to the variable name to export
 */


unsigned int
EVexport( char *SymName )
{
    SymEntry *SymP;

    if( ( SymP = EVfind( SymName ) ) == NULL )
        return FAILURE;		/* no space in SymTab */

    /*
     * add SymName to SymTab if it's not there
     */
    if( SymP->SymName == NULL )
        if( FAILS( assign( &SymP->SymName, SymName ) ) ||
		FAILS( assign( &SymP->SymVal, "" ) ) )
	    return FAILURE;

    /*
     * set Exported attribute
     */
    if( FAILS (SymP->SymAttr.Exported = SET) )
        return FAILURE;
    return SUCCESS;
}


/*
 * EVget( )
 *
 * Attempts to find the supplied SymName in the SymTab. If it is found,
 * then EVset( ) returns a pointer to its SymValue, otherwise it returns
 * NULL.
 *
 * SymName	- points to the variable name to get
 */

char *
EVget( char *SymName )
{
    SymEntry *SymP;

    if( ( SymP = EVfind( SymName ) ) == NULL || SymP->SymName == NULL )
	return NULL;
    else
	return SymP->SymVal;
}


/*
 * EVinit( )
 *
 * Initializes the SymTab by copying information from the program's
 * environment (as pointed to by the external variable "environ",
 * which is the address of an array of pointers to characters).
 *
 * The entries in the array are all assumed to be strings of the form
 * "FOO=BAR", where FOO is the name of the variable, and BAR is its
 * value. Each entry from the environ array is scanned to find the
 * embedded '=', and then pointers to the name and value strings
 * are passed to EVset( ).
 *
 * Because the array contains only those variables which were exported
 * from the calling environment, and since the Exported attribute is
 * "inherited" from process to process, then we need to set the Exported
 * attribute in our SymTab. The call to EVexport( ) does this.
 *
 * This function has a bunch of things wrong with it, which have been
 * documented below. You will need to fix those.
 */

unsigned int
EVinit( void )
{
    /*
     * BAD - uses fixed limits and a hard-coded constant
     * GOOD- I added a constant at the top of this file 
     */
    char SymName[ SYM_NAME_SIZE ];	

    int i, SymNamelen;

    /*
     * BAD - suppose "environ" is NULL?
     * GOOD- I added an if statement to check for such an occasion 
	 */
   
	 if (environ != NULL ) {

	    for( i = 0; environ[ i ] != NULL; i++ )
    	{
       		SymNamelen = strcspn( environ[ i ], "=" );

		/*
		 * BAD - suppose SymNameLen is >= 20?
         * GOOD- I used sizeof to fix this	
     	 */
	   	    STRNCPY( SymName, environ[ i ], SYM_NAME_SIZE );
	        SymName[ SymNamelen ] = '\0';

		/*
		 * BAD - suppose there is no '=' in the environ string?
         * GOOD- I tried adding an 'if' statement, but it didn't help
		 */
	  	      if( FAILS( EVset( SymName, &environ[ i ][ SymNamelen+1 ] ) ) || FAILS( EVexport( SymName ) ) ) 
			    return FAILURE;
              } 


		}

    return SUCCESS;
}


/*
 * EVprint( )
 *
 * A convenience function that prints out the contents of the SymTab
 * If PrAttr is SET, then each variable and all its information are
 * printed, otherwise just the SymName and SymValue are printed.
 */

void
EVprint( unsigned int PrAttr )
{
    SymEntry *SymP;

    /*
     * Step through the array using pointer arithmetic
     */
    for( SymP = SymTab; SymP - SymTab < MAXVAR; SymP++ )
        if( SymP->SymName != NULL )
	{
	    /*
	     * Print attributes?
	     */
	    if( PrAttr == SET ) {
			PRINTF(( "%3s ", SymP->SymAttr.Exported == SET ? "[E]" : "" ));
			PRINTF(( "%3s ", SymP->SymAttr.isLowercase == SET ? "[L]" : ""));	
		} 
        /*
	     * Print symbol name and value
	     */
	    PRINTF(( "%s=%s\n", SymP->SymName,
		    SymP->SymVal == NULL ? "" : SymP->SymVal ));
	}
}
