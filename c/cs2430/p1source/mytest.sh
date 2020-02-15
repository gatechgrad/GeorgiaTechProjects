#!/bin/ksh

# INSTRUCTIONS FOR MODIFYING THIS SCRIPT:
#
# Add any new lines of input that you want to test at the END
# of this script. You can also add new environment variables
# between the "cat" and "endcat" lines


EVdefine() {
cat <<- endcat
	HOME=/foo/bar/baz/hi  
	LOGNAME=weiss
	MAIL=/usr/spool/mail/weiss
	PATH=/bin:/usr/bin:/usr/weiss/bin:
	BEGIN=The\ l
	MIDDLE=ivi
	END=ng\ end.
	FOO=foo\ bar\ \|\ fubar
	BAR=bar
endcat
}

eval env - $(EVdefine) ./showtok << '' 2>&1
~foobar baz
"The bell tolls ...\007"
"Nobel \0Prize"
"      <out\r\t\b\binside"
"\b___\v\b|___\v\b|___\v\b|___\v  Watch that last step!\r\t\t  "
embed$sign embed"\"quote" embed'\'pop'
quote" me on "that
this" is "'not'" a "bug.
'abc
def
ghi'
"abc\ndef\nghi"
$BEGIN$MIDDLE$END
${BEGIN}${MIDDLE}${END}
$BEGIN$MIDDLEd l$MIDDLE$END
${BEGIN}${MIDDLE}d l${MIDDLE}${END}
"A funny joke - ${BAR}, ${BAR}, ${BAR}\b\b\b\H\b\b\b\b\b\b\H\b\b\b\b\b\b\H\b\b"
"This number is changing:\t${SECONDS}${RANDOM}"
"This number is changing:\t${SECONDS}${RANDOM}"
"This number is changing:\t${SECONDS}${RANDOM}"
"$FOO"
"${FOO}"
Finally I got the last $_
This variable is not ${_xyzzy_:-set}
I ${FOO:=foo} in your general direction
$FOO
${FOO}
Looks like I foo-ed your ${BAR:+foo}
