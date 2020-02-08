/*
 * util.c:
 */

#include	<stdio.h>
#include	<stdlib.h>
#include	<stdarg.h>
#include	<thread.h>

#include	<errno.h>
#include	<fcntl.h>
#include	<signal.h>
#include	<string.h>
#include	<unistd.h>

#include	<sys/param.h>
#include	<sys/types.h>
#include	<sys/stat.h>

#include	"util.h"

/* ====================================================================	*
 * Misc ...								*
 * ==================================================================== */

/*
 * Detach a daemon process from login session context.
 */
void
daemon_start(ignsigcld)
	Bool		ignsigcld;	/* nonzero -> handle SIGCLDs so	*
					 * zombies don't clog		*/
{
	register int	childpid, fd;

	/*
	 * If we were started by init (process 1) from the /etc/inittab file
	 * there's no need to detach.
	 */
	if (getppid() == 1)
		goto out;

	/*
	 * Ignore the terminal stop signals (BSD).
	 */
	signal(SIGTTOU, SIG_IGN);
	signal(SIGTTIN, SIG_IGN);
	signal(SIGTSTP, SIG_IGN);

	/*
	 * If we were not started in the backgroud, fork adn
	 * let the parend exit.
	 */
	if ((childpid = fork()) < 0)
		err_sys("can't fork first child");
	else if (childpid > 0)
		exit(0);		/* parent */

	/*
	 * First child process.
	 *
	 * Disassociate from controlling terminal and process group.
	 * Ensure the process can't reacquire a new controlling terminal.
	 */
	if (setpgrp() == -1)
		err_sys("can't change process group");

	signal(SIGHUP, SIG_IGN);	/* immune from pgrp leader death */

	if ((childpid = fork()) < 0)
		err_sys("can't fork second child");
	else if (childpid > 0)
		exit(0);		/* first child */

	/*
	 * Second child process.
	 */

out:
	/*
	 * Close any open files descriptors.
	 */
	for(fd = 0; fd < NOFILE; fd++)
		close(fd);
	errno = 0;

	/*
	 * Move the current directory to root, to make sure we
	 * aren't on a mounted filesystem.
	 */
	chdir("/");

	/*
	 * Clear any inherited file mode creation mask.
	 */
	umask(0);

	/*
	 * See if the caller isn't interested in the exit status of its
	 * children, and doesn't want to have them become zombies and
	 * clog up the system.
	 */
	if (ignsigcld)
		signal(SIGCLD, SIG_IGN);
}

/* ====================================================================	*
 * error ...								*
 * ==================================================================== */

extern int	sys_nerr;	/* # of error message strings in sys table */
extern char    *sys_errlist[];	/* the system error message table */

static int	debug_level = -1;
static FILE    *logfile = stderr;

static void	print_msg(char *);

/*
 * set_debug_level:
 */
void
set_debug_level(level)
	int		level;
{
	debug_level = level;
}

/*
 * set_debug_logfile:
 */
void
set_debug_logfile(filename)
	char	       *filename;
{
	if (logfile != stderr)
		fclose(logfile);

	if (filename == NULL) {
		logfile = stderr;
		return;
	}

	if ((logfile = fopen(filename, "w")) == NULL)
		logfile = stderr;
}

/*
 * Print a message and continue.
 *
 *	err_msg(str, arg1, arg2, ...)
 *
 * The string "str" must specify the conversion specification for any args.
 */
void
err_msg(char *fmt, ...)
{
	va_list		ap;
	char		emesgstr[8192];

	va_start(ap, fmt);
	vsprintf(emesgstr, fmt, ap);
	va_end(ap);

	print_msg(emesgstr);
}

/*
 * Fatal error.  Print a message and terminate.
 * Don't print te system's errno value.
 *
 *	err_quit(str, arg1, arg2, ...)
 *
 * The string "str" must specify the conversion specification for any args.
 */
void
err_quit(char *fmt, ...)
{
	va_list		ap;
	char		emesgstr[8192];

	va_start(ap, fmt);
	vsprintf(emesgstr, fmt, ap);
	va_end(ap);

	print_msg(emesgstr);
	exit(1);
}

/*
 * Fatal error related to a system call. Print a message and terminate.
 * Don't dump core, but do print the system's errno value and its
 * associated message.
 *
 *	err_sys(str, arg1, arg2, ...)
 *
 * The string "str" must specify the conversion specification for any args.
 */
void
err_sys(char *fmt, ...)
{
	va_list		ap;
	char		emesgstr[8192], *s;

	va_start(ap, fmt);
	vsprintf(emesgstr, fmt, ap);
	va_end(ap);

	s = emesgstr + strlen(emesgstr);

	if (errno != 0) {
		if (errno > 0 && errno < sys_nerr)
			sprintf(s, " (%s)", sys_errlist[errno]);
		else
			sprintf(s, ", (errno = %d)", errno);
	}

	print_msg(emesgstr);
	exit(1);
}

/*
 * Print a debuging message and continue.
 *
 *	msg_debug(str, arg1, arg2, ...)
 *
 * The string "str" must specify the conversion specification for any args.
 */
void
msg_debug(int level, char *fmt, ...)
{
	va_list		ap;
	char		emesgstr[8192];

	if (debug_level < level)
		return;

	va_start(ap, fmt);
	vsprintf(emesgstr, fmt, ap);
	va_end(ap);

	print_msg(emesgstr);
}

#ifdef DEBUG_IO

static Bool	debug_io = FALSE;

/*
 * set_debug_io:
 */
void
set_debug_io(b)
	Bool		b;
{
	debug_io = b;
}

/*
 * debug_data:
 */
void
debug_data(filename, buf, n)
	char	       *filename;
	char		buf[];
	int		n;
{
	char		string[80];
	int		fd;

	static int	no = -1;

	if (! debug_io)
		return;

	sprintf(string, "/tmp/%s.%d", filename, ++no);

	if ((fd = open(string, O_WRONLY + O_CREAT)) != -1) {
		write(fd, buf, n);
		close(fd);
	}
}
#endif

/*
 * print_msg:
 */
static void
print_msg(msg)
	char		msg[];
{
	fprintf(logfile, "%s: %s\n", PRGNAME, msg);
	fflush(logfile);
}
