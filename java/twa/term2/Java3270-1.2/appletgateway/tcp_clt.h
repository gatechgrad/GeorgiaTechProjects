/*
 * tcp_clt.h:
 */

extern void	tcp_close(int fd);
extern int	tcp_gets(int sockfd, char* buf, int nbytes);
extern int	tcp_open(char* host, char* service, int port);
extern int	tcp_puts(int sockfd, char* buf);
extern int	tcp_read(int s, char* buf, int nbytes);
extern int	tcp_write(int fd, char* buf, int nbytes);
