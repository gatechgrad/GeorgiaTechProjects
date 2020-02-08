/*
 * util.h:
 */

#define		Alloc(type)		((type *)malloc(sizeof(type)))
#define		bcopy(a, b, c)		memmove(b, a, c)

#define		FALSE			0
#define		TRUE			1
#define		Bool			int

extern char	       *PRGNAME;

/*
 * function in util.c:
 */

/* misc ... */
extern void		daemon_start(Bool);

/* error ... */
extern void		set_debug_level(int);
extern void		set_debug_logfile(char *);
extern void		err_msg(char *, ...);
extern void		err_sys(char *, ...);
extern void		err_quit(char *, ...);
extern void		msg_debug(int,  char *, ...);

#ifdef DEBUG_IO
extern void		set_debug_io(Bool);
extern void		debug_data(char *, char *, int);
#endif
