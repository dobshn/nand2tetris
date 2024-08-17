// 프로그램: Sum1ToN.asm
// 계산: 1 + 2 + 3 + ... + n
// 사용법: n >= 1을 RAM[0]에 넣는다

	// i = 1
	@i
	M=1

	// sum = 0
	@sum
	M=0

(LOOP)

	// if (i > n) then goto STOP
	@
	D=M
	@R0
	D=D-M
	@STOP
	D;JGT

	// sum = sum + i
	@sum
	D=M
	@i
	D=D+M
	@sum
	M=D

	// i = i + 1
	@i
	M=M+1
 	
	// goto LOOP
	@LOOP
	0;JMP

(STOP)

	// R1 = sum
	@sum
	D=M
	@R1
	M=D

(END)

	@END
	0;JMP
