// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

(START)
	// n=0
	@n
	M=0

	// if (RAM[KBD] == 0) goto B
	@KBD
	D=M
	@B
	D;JEQ

	// else goto A
	@A
	0;JMP

(A)

	// if (n == 8K) goto START
	@n
	D=M
	@8192
	D=D-A
	@START
	D;JEQ

	// *(SCREEN + n) = -1
	@SCREEN
	D=A
	@n
	A=D+M
	M=-1

	// n = n + 1
	@n
	M=M+1

	// goto A
	@A
	0;JMP

(B)

	// if (n == 8K) goto START
	@n
	D=M
	@8192
	D=D-A
	@START
	D;JEQ

	// *(SCREEN + n) = 0
	@SCREEN
	D=A
	@n
	A=D+M
	M=0

	@n
	D=M
	@SCREEN
	A=A+D

	// n = n + 1
	@n
	M=M+1

	// goto B
	@B
	0;JMP