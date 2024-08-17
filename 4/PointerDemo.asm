// 프로그램: PointerDemo.asm
// 시작 주소 R0부터 시작해서
// 처음 나오는 R1개 단어에 -1을 설정한다

	// n = 0
	@n
	M=0

(LOOP)

	// if (n == R1) then goto END
	@n
	D=M
	@R1
	D=D-M
	@END
	D;JEQ

	// *(R0 + n) = -1
	@R0
	D=M
	@n
	A=D+M
	M=-1

	// n = n + 1
	@n
	M=M+1

	// goto LOOP
	@LOOP
	0;JMP

(END)

	@END
	0;JMP
