/*
 * FILE: tcp_srv.c
 * ---------------
 */


#include	<stdio.h>
#include	<stdlib.h>
#include	<unistd.h>
#include	<errno.h>
#include	<signal.h>
#include	<string.h>

#include	<sys/types.h>
#include	<sys/socket.h>
#include	<netinet/in.h>
#include	<netdb.h>

#include	"util.h"
#include	"tcp_clt.h"

char	       *PRGNAME;

static int	gatewayport;
static char    *hostname;
static int	hostport;

static void	process_request(int, char *, int);

int
main(argc, argv)
	int		argc;
	char	       *argv[];
{
	struct sockaddr_in	cli_addr, serv_addr;
	struct servent	       *sp;
	int			childpid;
	int			client_fd, clilen;
	int			server_fd;

	PRGNAME = argv[0];

	if (argc != 4)
		err_quit("usage: %s gatewayport hostname hostport", argv[0]);

	gatewayport = atoi(argv[1]);
	hostname = argv[2];
	hostport = atoi(argv[3]);

	/*
	 * Start daemon.
	 */
	daemon_start(TRUE);
	set_debug_logfile("/tmp/appletgateway.log");

	/*
	 * Open a TCP socket (an Internet stream socket).
	 */

	if ( (server_fd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
		err_sys("can't open stream socket");

	/*
	 * Bind our local address so that the client can send to us.
	 */
	memset((char *) &serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);

	/*
	 * if ( (sp = getservbyname(PRGNAME, "tcp")) == NULL)
	 *	err_sys("unknown service: %s/tcp", PRGNAME);
	 *
	 *	serv_addr.sin_port = sp->s_port;
	 */
	serv_addr.sin_port = htons(gatewayport);

	if (bind(server_fd, (struct sockaddr *)&serv_addr,
					sizeof(serv_addr)) == -1)
		err_quit("cannot bind on port: %d", gatewayport);

	listen(server_fd, 5);

	for(;;) {
		/*
		 * Wait for a connexion from a client process.
		 */
		clilen = sizeof(cli_addr);
		client_fd = accept(server_fd, (struct sockaddr *) &cli_addr,
								     &clilen);

		if (client_fd < 0) {
			if (errno == EINTR) continue;
			err_sys("accept error");
			return(1);
		}

		if ((childpid = fork()) < 0)
			err_sys("can't fork");

		if (childpid == 0) {
			process_request(client_fd, hostname, hostport);
			exit(0);
		}

		close(client_fd);
	}
}

/*
 * process_request:
 */
static void
process_request(client_fd, hostname, hostport)
	int		client_fd;
	char	       *hostname;
	int		hostport;
{
	fd_set		readmask;
	char		buf[BUFSIZ];
	int		host_fd;
	int		n, nfds, nfound;

	if ((host_fd = tcp_open(hostname, NULL, hostport)) == -1)
		err_sys("tcp_open: %s %d", hostname, hostport);

	nfds = (client_fd > host_fd) ? client_fd + 1 : host_fd + 1;

	for(;;) {
		FD_ZERO(&readmask);
		FD_SET(client_fd, &readmask);
		FD_SET(host_fd, &readmask);

		nfound = select(nfds, &readmask, NULL, NULL, NULL);

		if (nfound < 0)
			err_quit("select error");

		if (FD_ISSET(host_fd, &readmask)) {
			if ((n = tcp_read(host_fd, buf, sizeof(buf))) <= 0)
				break;

			if (tcp_write(client_fd, buf, n) == -1)
				err_sys("tcp_write: client_fd");
		}

		if (FD_ISSET(client_fd, &readmask)) {
			if ((n = tcp_read(client_fd, buf, sizeof(buf))) <= 0)
				break;

			if (tcp_write(host_fd, buf, n) == -1)
				err_sys("tcp_write: host_fd");
		}
	}

	tcp_close(host_fd);
	tcp_close(client_fd);
}
