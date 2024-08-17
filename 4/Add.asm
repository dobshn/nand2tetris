// 프로그램: Add.asm
// 계산: RAM[2] = RAM[0] + RAM[1] + 17
// 사용법: RAM[0]과 RAM[1]에 값을 입력한다.

	// D = RAM[0]
	@R0
	D=M

	// D = D + RAM[1]
	@R1
	D=D+M

	// D = D + 17
	@17
	D=D+A

	// RAM[2] = D
	@R2
	M=D

(END)

	@END
	0;JMP
