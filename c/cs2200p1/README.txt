			Project 1--CS 2200 (Fall 1999)

		         Posted: August 26, 1999
	      Due: Midnight, Thursday, Septembet 16, 1999

1 Purpose

This project is intended to give you experience designing and writing a
finite-state machine for a simple computer (i.e. a pseudo-hardware design
experience). 

2 Understanding the LC-99 Instruction-Set Architecture

The LC-99 (Little Computer, Spring 1999). The LC-99 is very simple,
but it is general enough to solve complex problems. For this project,
you will need to know the instruction set and instruction format of
the LC-99.

The LC-99 is an 8-register, 32-bit computer. All addresses are
word-addresses.  By assembly-language convention, register 0 will
always contain 0 (ie. the machine will not enforce this, but no
assembly-language program should ever change register 0 from its
initial value of 0).

There are 4 instruction formats (bit 0 is the least-significant bit).

R-type instructions (add, nand):
    bits 16-14: opcode
    bits 13-11: reg A
    bits 10-8:  reg B
    bits 7-3:   unused (should all be 0)
    bits 2-0:   reg D

I-type instructions (lw, sw, beq):
    bits 16-14: opcode
    bits 13-11: reg A
    bits 10-8:  reg B
    bits 7-0:   offsetField (an 8-bit, 2's complement number with a range of
		    -128 to 127)

J-type instructions (jalr):
    bits 16-14: opcode
    bits 13-11: reg A
    bits 10-8:  reg B
    bits 7-0:   unused (should all be 0)

O-type instructions (halt, noop):
    bits 16-14: opcode
    bits 13-0:  unused (should all be 0)


-------------------------------------------------------------------------------
Table 1: Description of Machine Instructions
-------------------------------------------------------------------------------
Assembly language 	Opcode in binary		Action
name for instruction	(bits 16, 15, 14)
-------------------------------------------------------------------------------
add (R-type format)	000 			add contents of regA with
						contents of regB, store
						results in regD.

nand (R-type format)	001			nand contents of regA with
						contents of regB, store
						results in regD.

lw (I-type format)	010			load regB from memory. Memory
						address is formed by adding
						offsetField with the contents of
						regA.

sw (I-type format)	011			store regB into memory. Memory
						address is formed by adding
						offsetField with the contents of
						regA.

beq (I-type format)	100			compare the contents of regA and
						regB;  if they are the same, then 
						branch to the address 
						PC+1+offsetField,
						where PC is the address of the
						beq instruction.  

jalr (J-type format)	101			First store PC+1 into regB,
						where PC is the address of the
						jalr instruction.  Then branch
						to the address now contained in
						regA.  Note that if regA is the
						same as regB, the processor
						will first store PC+1 into that
						register, then end up branching
						to PC+1.

halt (O-type format)	110			halt the machine (do nothing
						and let the simulator notice
						that the machine halted).

noop (O-type format)	111			do nothing.
-------------------------------------------------------------------------------

A couple of notes: Students often find the machine code for the branch
instruction difficult to interpret.  There are really two flavors of
branches.  When the offsetField is specified as a number in the
assembly language instruction, this number represents the offset that
is required to jump to the target location.  The target is calculated
with pc+1+offsetField.  If you are executing a loop and the start of
the loop is at address 5, while the branch instruction is at address
7, remember that to branch to the start of the loop requires
offsetField = target-(pc+1) = 5-(8) = -3.

In the assembly program example below, there is an example of the
other flavor of conditional branch instruction.  The second beq
instruction has a symbolic label in the third field ( beq 0 0 start).
In this case, the address represented by the symbolic label becomes
the branch target, and the machine code produced by the assembler
includes whatever value of offsetField is needed to generate
pc+1+offsetField = target.


3 LC-99 Assembly Language and Assembler

We will provide an assembler that translates an assembly-language
program into machine-level code that the LC-99 computer will
understand.  The assembler translates assembly language names for
instructions, such as beq, into their numeric equivalent (e.g. 100).
It also translates symbolic names for addresses into numeric
values. The final output of the assembler is a series of 32-bit
instructions (instruction bits 31-17 are always 0).


The format for a line of assembly code is:

label<tab>instruction<tab>field0<tab>field1<tab>field2<tab>comments


The leftmost field on a line is the label field.  Valid labels contain
a maximum of 6 characters and can consist of letters and numbers. The
label is optional (the tab following the label field is not).  Labels
make it much easier to write assembly language programs, since
otherwise you would need to modify all address fields each time you
added a line to your assembly-language program!

After the optional label is a tab. Then follows the instruction field,
where the instruction can be any of the assembly-language instruction
names listed in the above table. After another tab comes a series of
fields. All fields are given as decimal numbers. The number of fields
depends on the instruction.

    R-type instructions (add, nand) instructions require 3 fields:
	field0 is regA, field1 is regB, and field2 is regD.

    I-type instructions (lw, sw, beq) require 3 fields:
	field0 is regA, field1 is regB, and field2 is either a numeric 
	value for offsetField or a symbolic address.  Numeric offsetFields
	can be positive or negative; symbolic addresses are discussed below.

    J-type instructions (jalr) require 2 fields:
	field0 is regA, and field1 is regB.

    O-type instructions (noop and halt) require no fields.


Symbolic addresses refer to labels:
**  For lw or sw instructions, the assembler computes offsetField 
to be equal to the address of the label.  This is typically used with a zero
base register.
**For beq instructions, the assembler should translate the label, and
this address becomes the target address of the branch.  The assembler
then generates the numeric offsetField needed to branch to that label.


After the last used field comes another tab, then any comments. The
comment field ends at the end of a line. 


In addition to LC-99 instructions, an assembly-language program may
contain directions for the assembler. The only assembler directive we
will use is .fill (note the leading period). .fill tells the assembler
to put a number into the place where the instruction would normally be
stored. .fill instructions use one field, which can be either a
numeric value or a symbolic address.  For example, ".fill 32" puts the
value 32 where the instruction would normally be stored.  .fill with a
symbolic address will store the address of the label.  In the example
below, ".fill start" will store the value 2, because the label "start"
is at address 2.


The assembler makes two passes over the assembly-language program. In
the first pass, it calculates the address for every symbolic label.
It assumes that the first instruction is at address 0.  In the second
pass, the assembler generates a machine-level instruction (in decimal)
for each line of assembly language.  For example, here is an
assembly-language program (that counts down from 5, stopping when it
hits 0).


	lw	0	1	five	load reg1 with 5 (uses symbolic address)
	lw	1	2	3	load reg2 with -1 (uses numeric address)
start	add	1	2	1	decrement reg1
	beq	0	1	2	goto end of program when reg1==0
	beq	0	0	start	go back to the beginning of the loop
	noop
done	halt				end of program
five	.fill	5	
neg1	.fill	-1
stAddr	.fill	start			will contain the address of start (2)


And here is the corresponding machine-level program:

(address 0): 33031 (hex 0x8107)
(address 1): 35331 (hex 0x8a03)
(address 2): 2561 (hex 0xa01)
(address 3): 65794 (hex 0x10102)
(address 4): 65789 (hex 0x100fd)
(address 5): 114688 (hex 0x1c000)
(address 6): 98304 (hex 0x18000)
(address 7): 5 (hex 0x5)
(address 8): -1 (hex 0xffffffff)
(address 9): 2 (hex 0x2)


Be sure you understand how the above assembly-language program got translated
to this machine-code file.

The assembler output contains only the machine code, not the address and hex
information shown above:

33031
35331
2561
65794
65789
114688
98304
5
-1
2



3.1  Running The Assembler

We will supply you with sample assembly programs and with corresponding
machine code.  However, if you want to write your own assembly language
programs, we will also supply you with an executable for the assembler.   
Run the assembler as follows:

    assemble assembly-code-file machine-code-file

Note that the format for running the command must use command-line
arguments for the file names (rather than standard input and standard
output).

The first argument is the file name where the assembly-language
program is stored, and the second argument is the file name where the
output (the machine-code) is written. 



4 The LC-99 Datapath

For this assignment, we will use a different datapath than the one
discussed in the book.  This is the bus-based datapath shown in the
handout that is available on the class web page.  This bus-based
datapath uses two buses, a 32-bit data bus that carries most
information and a 32-bit address/data bus that is used primarily to 
send addresses to the memory, but can also be used to carry data. You
will have to use this bus to read operands from the register file.

It is VERY important that you understand how the bus-based datapath
works.  It is quite different from the datapath in the book, which
shows components connected by dedicated collections of wires.

All the components that sit on a bus are electrically connected.  It
is VERY important that only one component _drive_ or send output
signals to the bus at a time.  If multiple components try to drive the
bus simultaneously, errors will result.  Although only one component
can drive the bus at a time, since all the components on the bus are
electrically connected, it is possible for multiple components
simultaneously to _latch_ or store the value currently being driven
onto the bus.  It is important to keep straight the concepts of
driving the bus (asserting a value on a component's output lines) and
latching the bus (capturing or storing the value on a component's
input lines).

The lines and arrows in the project diagram show the paths that data
can take through the system.  The diagram also shows the control
signals that are required for the datapath.  Note that many components
have separate latch and drive control lines.

The control unit (not shown) can read the current value of instrReg
and the aluResult register to make decisions on what next state to go
to. The control unit can read these values without using the system
bus.

The ALU can perform addition, subtraction, and nand. Note that the ALU
is not a register: it contains only combinational logic.  To save the
result of an ALU operation, we must latch the result into the
aluResult register.  Note also that there is _NO_ aluOperand register.
Hence, you will have to make sure that the ALU has both its operands in 
the same bus cycle. The datapath contains a sign-extend unit to extend the 
8-bit offset into a 32-bit value for use in arithmetic operations.  To 
simulate this sign-extension in your program, you may use the provided 
function convertNum, as in convertNum(instrReg & 0xFF).


5 Finite-State Machine Description

A finite-state machine describes in detail the order of events
necessary to carry out the computer's instructions. As described in
Chapter 5 of the course textbook, a "big-picture" look at the sequence
of a generic instruction would be 1) fetch the instruction from the
memory, 2) decode the instruction and decide what to do, 3) execute
the instruction, then repeat. Your job is to break down each of these
steps to form a detailed finite-state machine that shows all data
transfers of each instruction.

You will use C to describe the finite-state machine. Starting with the
simulator template that we provide, fill in the logic to implement the
finite-state machine to control the LC-99 datapath.

You will specify each state you enter in the state diagram.  You will
also specify the relevant control signals to make your state machine
work.  In addition, you will change the state data structure for the
machine to reflect the changes that would happen on each bus cycle.

Do things in the following order:  
1)  First, assert the control signals
2)  Next, call the printState routine within each state
3)  Finally, make the changes to the machine state after you call printState.  
(See the example states in the program template.)




6 Coding Limitations

Because C is much more powerful than an ordinary finite-state machine,
we will place strict limits on how you will describe your finite-state
machine in C.  This will only apply for the part of the program that
executes instructions (e.g. not for the code that loads the
program). The code that executes instructions must obey the following
rules:

1. Only use if...else-if...else goto for conditional execution (you
may not use for, while, do, case, ?:). For example:

    if ((instrReg>>14) == ADD) {
        goto add;
    }

The only code inside the body of the if statement should be a goto
(and possibly debugging printfs).  

In general, you will only use if statements for two purposes:  to decide
which state to goto next based on the opcode of the instruction, and 
to decide whether to change the PC based on the branch condition.

The goto represents the next-state function, hence there should be no
code in a state after a goto section.

2. Every distinct state in the finite-state machine should have a
label before the group of C statements that describe the actions in
that state.  If you share states among instructions (see number 6
below), you may want to assign multiple labels to the shared states.
Do not assign labels to anything except states.

3. You may only transfer data where there is a connection shown on the
register diagram. 

4. You can't use the new value of a register until the cycle after you
write it (this is the same as the clocking scheme discussed in
class). For example, if cycle 1 is "A=B", then you can't use the new
value of A until cycle 2. This is because A doesn't actually get
written until the end of cycle 1/beginning of cycle 2.  In particular,
remember that the control unit reads instrReg and aluResult, which are
registers.  

5. The only functions you may use are printf, convertNum, printState,
and clearControl.  

6. You should combine states where possible.  For example, if two 
different instruction types share a register fetch or an address
calculation, you may allow them to share states.  

7. You may use the normal arithmetic operations (+, -, *, /, %) as
well as the bit operations in C (&, |, <<, >>, ~).

8. You may use printfs wherever you want. You may also call your
function to print the machine's state wherever you need.

9. Naming conventions for states: when multiple states are required to
execute a particular instruction, please number the states operation1,
operation2, .....  For example, if it takes three states to execute a
nand, these should be nand1, nand2, and nand3.



7 Example states

To get you started, the template code file contains states to perform
the instruction fetch and the increment of the program counter.



8 Turning in the Project

You should submit the following:
  	1)  A state diagram showing all the states in your
	system and the transitions between states
	2)  A C program listing for your simulator

To submit your programs and output files, use the turnin program. 
The TA will post instructions for using the turnin program on the 
class newsgroup.

Each of your programs must be in a single file and must be able to be
compiled by running gcc. Do not expect us to specify additional flags
or use a makefile.

The official time of submission for your project will be the time the
last file is sent. If you send in anything after the due date, your
project will be considered late and will be evaluated according to the
late policy.


11 C Programming Tips

Here are a few programming tips for writing C programs to manipulate
bits and strings:

1. To indicate a hexadecimal constant in C, precede the number by 0x.
For example, 27 decimal is 0x1b in hexadecimal.

2. The value of the expression (a >> b) is the number "a" shifted
right by "b" bits. Neither a nor b are changed. E.g. (25 >> 2) is
6. Note that 25 is 11001 in binary, and 6 is 110 in binary.

3. The value of the expression (a << b) is the number "a" shifted left
by "b" bits. Neither a nor b are changed. E.g. (25 << 2) is 100. Note
that 25 is 11001 in binary, and 100 is 1100100 in binary.

4. To find the value of the expression (a & b), perform a logical AND
on each bit of a and b (i.e. bit 31 of a ANDED with bit 31 of b, bit
30 of a ANDED with bit 30 of b, etc.). E.g.  (25 & 11) is 9, since:

    11001 (binary) 
  & 01011 (binary)
---------------------
 =  01001 (binary), which is 9 decimal.

5. To find the value of the expression (a | b), perform a logical OR
on each bit of a and b (i.e. bit 31 of a ORED with bit 31 of b, bit 30
of a ORED with bit 30 of b, etc.). E.g.  (25 | 11) is 27, since:

    11001 (binary) 
  & 01011 (binary)
---------------------
 =  11011 (binary), which is 27 decimal.

6. ~a is the bit-wise complement of a (a is not changed).

Use these operations to create and manipulate machine-code. E.g. to
look at bit 3 of the variable a, you might do: (a>>3) & 0x1. To look
at bits (bits 15-12) of a 16-bit word, you could do: (a>>12) & 0xF.
To put a 6 into bits 5-3 and a 3 into bits 2-1, you could do: 
(6<<3) | (3<<1). If you're not sure what an operation is doing, print 
some intermediate results to help you debug.

7. Several string functions that you may find useful:
a) strtok returns a pointer to the next token in a string
b) strcmp(str1, str2) compares two null-terminated strings
c) strcpy(str1, str2) is used to copy the contents of str2 into str1
d) strcat(str1, str2) concatenates a copy of str2 to str1
-------------------------------------------------------------------------------


