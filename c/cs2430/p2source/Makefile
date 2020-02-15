#
# Makefile for the fsh program
#
# PLEASE DO NOT EDIT THIS UNLESS YOU KNOW WHAT YOU'RE DOING!

UPATH	= /tnt3/13/cs2430/pub/bin/
OPATH	= /tnt3/13/cs2430/pub/fsh/objects/

# define sources and executables
INCL	= defs.h
SRC	= environ.c token.c builtin.c
OBJ	= $(SRC:.c=.o)
XOBJ	= $(OPATH)command.o $(OPATH)main.o
EXE	= fsh
ERR	= lint.err $(SRC:.c=.err) $(EXE).err
KIT	= Makefile $(INCL) $(SRC)

# define utility programs and options
CC	= $(UPATH)cc
CFLAGS	= -ansi -pedantic -g
CTAGS	= $(UPATH)ctags
LINT	= $(UPATH)lint
INDENT	= $(UPATH)indent -bl -c41 -i4 -l79 -pcs
TEST	= $(UPATH)test
TOUCH	= $(UPATH)touch

TURNIN	= $(UPATH)turnin prj2

# how to build object files
.c.o:
	$(CC) $(CFLAGS) -c $*.c > $*.err 2>&1
	@rm -f $*.err

# executable depends on objects only
$(EXE):	$(OBJ)
	$(CC) -o $@ $(CFLAGS) $(OBJ) $(XOBJ) > $@.err 2>&1
	@rm -f $(ERR)

# object files depend on includes
$(OBJ):	defs.h

# run lint on source files
lint:	$(SRC)
	$(LINT) $(SRC) > lint.err 2>&1
	@touch lint

# build tags database
tags:	$(SRC)
	$(CTAGS) $(INCL) $(SRC)

# beautify source files - some very wierd stuff here ...
pretty:	$(INCL) $(SRC)
	ls $? | xargs -p -n1 $(INDENT)
	@touch pretty

# turnin the program
turnin:	$(EXE)
	@$(TURNIN) $(KIT)

# turn on debugging
debug:
	@-$(TEST) ! -f .DEBUG && ($(MAKE) clean;$(TOUCH) .DEBUG)
	@$(MAKE)

# clean up object files and executables
clean:
	rm -f $(EXE) $(OBJ) $(ERR) $(EXE).shar

# clean up everything
clobber:	clean
	rm -f lint tags pretty lint.out a.out nohup.out core
