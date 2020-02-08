#
# Makefile for Solaris 2.4
#

CFLAGS		= -O
LDFLAGS		= -lsocket -lnsl

PROGRAMS	= appletgateway

AG_OBJECTS	= tcp_clt.o tcp_srv.o util.o

.KEEP_STATE:

all: ${PROGRAMS}

appletgateway: ${AG_OBJECTS}
	$(LINK.c) -o $@ ${AG_OBJECTS} ${LDLIBS}

clean:
	rm -f .make.state core ${PROGRAMS} ${AG_OBJECTS}
