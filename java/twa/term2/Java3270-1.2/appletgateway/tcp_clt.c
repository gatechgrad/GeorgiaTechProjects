/*
 * tcp_clt.c
 */

#include	<stdio.h>
#include	<unistd.h>

#include	<errno.h>
#include	<fcntl.h>
#include	<string.h>

#include	<sys/types.h>
#include	<sys/socket.h>
#include	<netinet/in.h>
#include	<netdb.h>
#include	<arpa/inet.h>

#include	"util.h"
#include	"tcp_clt.h"

/*
 * tcp_close:
 */
void
tcp_close(fd)
	int		fd;
{
	shutdown(fd, 2);
	close(fd);
}

/*
 * tcp_gets:
 */
int
tcp_gets(sockfd, buf, nbytes)
	int		sockfd;
	char		buf[];
	int		nbytes;
{
	int		ret;
	int		i = 0;

	for(;;) {
		if ((ret = read(sockfd, &buf[i], 1) <= 0)) {
			if (ret == -1 && errno == EINTR)
				continue;
			return(-1);
		}

		if (buf[i] == '\r')
			continue;

		if (buf[i] == '\n') {
			buf[i] = '\0';
			return(0);
		}

		if (++i == nbytes)
			return(-1);
	}
}

/*
 * Open a TCP connection.
 * Return socket descriptor if OK, else -1 on error
 */
int
tcp_open(host, service, port)
char	*host;		/* name or dotted-decimal addr of other system */
char	*service;	/* name of service being requested */
			/* can be NULL, if port > 0 */
int	port;		/* if == 0, nothing special - use port# of service */
			/* if < 0, bind a local reserved port */
			/* if > 0, it's the port# of server (host-byte-order) */
{
	struct sockaddr_in	srv_addr;
	unsigned long		inaddr;
	struct servent	       *sp;
	struct hostent	       *hp;
	int			fd;

	/*
	 * Initialize the server's Internet address structure.
	 * We'll store the actual 4-byte Internet address and the
	 * 2-byte port# below.
	 */

	memset((char *) &srv_addr, 0, sizeof(srv_addr));
	srv_addr.sin_family = AF_INET;

	if (service != NULL) {
		if ( (sp = getservbyname(service, "tcp")) == NULL)
			return(-1);

		if (port > 0)
			srv_addr.sin_port = htons(port);
							/* caller's value */
		else
			srv_addr.sin_port = sp->s_port;
							/* service's value */
	} else {
		if (port <= 0)
			return(-1);

		srv_addr.sin_port = htons(port);
	}

	/*
	 * First try to convert the host name as a dotted-decimal number.
	 * Only if that fails do we call gethostbyname().
	 */

	if ( (inaddr = inet_addr(host)) != INADDR_BROADCAST) {
						/* it's dotted-decimal */
		bcopy((char *) &inaddr, (char *) &srv_addr.sin_addr,
			sizeof(inaddr));
	} else {
		if ( (hp = gethostbyname(host)) == NULL)
			return(-1);

		bcopy(hp->h_addr, (char *) &srv_addr.sin_addr,
			hp->h_length);
	}

	if (port >= 0) {
		if ( (fd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
			return(-1);

	} else if (port < 0)
		return(-1);

	/*
	 * Connect to the server.
	 */

	if (connect(fd, (struct sockaddr *) &srv_addr, sizeof(srv_addr)) < 0) {
		close(fd);
		return(-1);
	}

	return(fd);	/* all OK */
}

/*
 * tcp_puts:
 */
int
tcp_puts(sockfd, buf)
	int		sockfd;
	char	       *buf;
{
	return(tcp_write(sockfd, buf, strlen(buf)));
}

/*
 * tcp_read:
 *	return:
 *		val > 0		-> data available
 *		val == 0	-> no data available
 *		val == -1	-> error
 * --------------------------------------------------------------------
 */
int
tcp_read(s, buf, nbytes)
	int		s;
	char	       *buf;
	int		nbytes;
{
	int		n;

	if ((n = read(s, buf, nbytes)) > 0)
		return(n);

	if (n == -1 && errno == EWOULDBLOCK)
		return(0);

	return(-1);
}

/*
 * tcp_write:
 * --------------------------------------------------------------------
 */
int
tcp_write(fd, buf, nbytes)
	int		fd;
	char	       *buf;
	int		nbytes;
{
	int		nleft, nwritten;

	nleft = nbytes;
	while(nleft > 0) {
		if ((nwritten = write(fd, buf, nleft)) == -1) {
			if (errno == EWOULDBLOCK)
				continue;

			return(-1);
		}

		nleft -= nwritten;
		buf += nwritten;
	}

	return(nbytes);
}
