#!/bin/ksh

# INSTRUCTIONS FOR MODIFYING THIS SCRIPT:
#
# Add shell variables to be exported between the "cat" and "endcat" lines

EVdefine() {
cat <<- endcat
	/*  HOME=/usr/weiss  */
	LOGNAME=weiss
	MAIL=/usr/spool/mail/weiss
	PATH=/bin:/usr/bin:/usr/weiss/bin:
	PS1=$
	PS2=>
	TZ=EST5EDT
endcat
}

env - $(EVdefine) ./showenv 2>&1
