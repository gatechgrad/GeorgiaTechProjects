DECLARE SUB Create ()
DECLARE SUB Feed ()
DECLARE SUB Stats ()
DECLARE SUB Settings ()
DECLARE SUB main ()
DECLARE SUB Scores ()
DECLARE SUB Thanks ()
CLS
COLOR 9
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT "[0m[34m                   OPENING ANSI[37m"
PRINT "[0m[32m                    PRESS A KEY[37m"
SLEEP

1 CLS
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT "                 CYBER CREATURES v1.00"
PRINT ""
PRINT "               Presented by: Command.Com"
PRINT "                    Jan 16, 1998"
PRINT ""
PRINT ""
PRINT "          This game is REGISTERED/UNREGISTERED"
PRINT "                    to XXXXXXXXXXXXXX"
PRINT ""
PRINT ""

PRINT ""
PRINT ""
PRINT "                 <E>nter Your Ranch"
PRINT "                 <R>ancher Rankings"
PRINT "                 <V>iew Settings"
PRINT "                 <Q>uit Back to BBS"

INPUT Choice$
Choice$ = UCASE$(Choice$)
IF Choice$ = "E" THEN main
IF Choice$ = "R" THEN Scores
IF Choice$ = "V" THEN Settings
IF Choice$ = "Q" THEN Thanks

GOTO 1

SUB Create
CLS
PRINT "                         ษอออออออออออออออออป"
PRINT "ษออออออออออออออออออออออออน Create Creature ฬออออออออออออออออออออออออออป"
PRINT "บ                        ศออออออออัออออออออผ                          บ "
PRINT "บ   Available Elements            ณ          Alchemy Shards           บ "
PRINT "บ H  C  Si  Rn  Pu  Hg  W         ณ                                   บ "
PRINT "บ                                 ณ   Fire Shard        Water Shard   บ "
PRINT "บ                                 ณ   Wind Shard        Earth Shard   บ "
PRINT "บ                                 ณ                                   บ "
PRINT "บ New elemets created through gameณ           Special                 บ "
PRINT "บ                                 ณ                                   บ "
PRINT "ศอออออออออออออออออออออออออออออออออฯอออออออออออออออออออออออออออออออออออผ                                                                     ผ "

INPUT "Which Element to Use"; Choice$
Element$ = UCASE$(Choice$)
INPUT "Which Alchemy Shard to Use"; Choice$
Alchemy$ = UCASE$(Choice$)
INPUT "What will you name your new monster"; Choice$
Mon1$ = UCASE$(Choice$)

IF Element$ = "H" THEN Element$ = "Bird"
IF Element$ = "C" THEN Element$ = "Humanoid"
IF Element$ = "Si" THEN Element$ = "Android"
IF Element$ = "Rn" THEN Element$ = "Radioactive"
IF Element$ = "Pu" THEN Element$ = "Evil"
IF Element$ = "W" THEN Element$ = "Golem"


CLS
PRINT "                         ษอออออออออออออออออป"
PRINT "ษออออออออออออออออออออออออน Create Creature ฬออออออออออออออออออออออออออป"
PRINT "บ                        ศออออออออัออออออออผ                          บ "
PRINT "บ   Picture Goes Here....                                             บ "
PRINT "บ                                                                     บ "
PRINT "บ                                                                     บ "
PRINT "บ                                                                     บ "
PRINT "บ                                                                     บ "
PRINT "บ                    "; Alchemy$; "/"; Element$; "  Created!!!!                                                บ "
PRINT "บ                                 ณ                                   บ "
PRINT "ศอออออออออออออออออออออออออออออออออฯอออออออออออออออออออออออออออออออออออผ                                                                     ผ "

PRINT "Press Any Key to continue"
SLEEP


END SUB

SUB Feed
INPUT Choice$; "What do you want to give"; Name1$
IF



END SUB

SUB main
2 CLS
PRINT "                         ษอออออออออออออออออป"
PRINT "ษออออออออออออออออออออออออน Cyber Creatures ฬออออออออออออออออออออออออออป"
PRINT "บ                        ศออออออออัออออออออผ                          บ "
PRINT "บ Monster Control                 ณ                HP                 บ "
PRINT "บ                                 ณ    Picture     AP                 บ "
PRINT "บ Feed         Sleep              ณ                                   บ "
PRINT "บ Work         Disipline          ณ    Status:                        บ "
PRINT "บ Train                           ณ    Turns Left:                    บ "
PRINT "บ Clean                           ณ                                   บ "
PRINT "วฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤลฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤฤถ "
PRINT "บ                                 ณ                                   บ "
PRINT "บ Items          Create Creature  ณ   Post Announcement               บ "
PRINT "บ Store                           ณ   Newspaper                       บ "
PRINT "บ                                 ณ                                   บ "
PRINT "บ Battle                          ณ                                   บ "
PRINT "บ Statistics                      ณ                                   บ "
PRINT "บ Rankings                        ณ                                   บ "
PRINT "บ View Settings                   ณ                                   บ "
PRINT "บ Change Monster                  ณ                                   บ "
PRINT "บ Quit                            ณ                                   บ "
PRINT "บ                                 ณ                                   บ "
PRINT "ศอออออออออออออออออออออออออออออออออฯอออออออออออออออออออออออออออออออออออผ "

INPUT Choice$
Choice$ = UCASE$(Choice$)

IF Choice$ = "F" THEN Feed
IF Choice$ = "W" THEN main
IF Choice$ = "I" THEN main
IF Choice$ = "B" THEN main
IF Choice$ = "S" THEN Stats
IF Choice$ = "T" THEN main
IF Choice$ = "M" THEN main
IF Choice$ = "V" THEN Settings
IF Choice$ = "R" THEN Scores
IF Choice$ = "C" THEN Create
IF Choice$ = "Q" THEN Thanks

GOTO 2


END SUB

SUB News
CLS
PRINT "                         ษอออออออออออออออออป"
PRINT "ษออออออออออออออออออออออออน Daily News      ฬออออออออออออออออออออออออออป"
PRINT "บ                        ศออออออออัออออออออผ                          บ "
PRINT "  NEWS.TXT goes here...."

PRINT "<T>oday's News          <Y>esterday's News            E<x>it"
SLEEP
END SUB

SUB OnLine
CLS
PRINT "                         ษอออออออออออออออออป"
PRINT "ษออออออออออออออออออออออออน Ranchers Online ฬออออออออออออออออออออออออออป"
PRINT "บ                        ศออออออออัออออออออผ                          บ "
PRINT "  Name             Title               Arrived At"
PRINT ""
PRINT "  Command.Com       Master Rancher     12:00AM"
PRINT "  YOSHI             Great Rancher      12:10AM"
PRINT "  CC                Average Rancher    12:30AM"
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT ""
PRINT ""
SLEEP

END SUB

SUB Scores
CLS
PRINT "Rancher          EXP           Creature"
PRINT "----------------------------------------"
PRINT "CommandCom       1000          Dragon"
PRINT "YOSHI             750          Dinosaur"
PRINT "Rancher3           25          Dog"
SLEEP
END SUB

SUB Settings
CLS
PRINT "Cyber Creatures Settings"
PRINT "----------------------------"
PRINT "Fights Per Day"; FIGHTS
PRINT "Turns Per Day"; Turns
PRINT "Game has been running for "; DAYS; " days"


PRINT "This game is registered to "; SYSOP; " of "; BBSNAME
SLEEP
END SUB

SUB Stats

PRINT "Name"; Name1; "'s Statistics"
PRINT "("; HP; "/"; HPMax; ")"
PRINT "("; MP; "/"; MPMax; ")"
PRINT "Happiness"; HAPPY
PRINT "Cash:"; CASH
PRINT "Class:"; CLASS
PRINT "Sub Class:"; SCLASS
PRINT "Strength"; STR
PRINT "Defense"; DFN



END SUB

SUB Thanks
CLS
PRINT "Thank you for playing"
PRINT "Cyber Creatures"
END
END SUB

