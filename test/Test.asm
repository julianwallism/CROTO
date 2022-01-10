section .data
t0:	db	0x0

section .text
global main

fmt:	db	"%d", 10, 0
extern printf
	lab0 :	nop ; _skip

	main:
 ; _pmb

	mov	eax, -1
	mov dword	[t0], eax ; _copy

	mov	eax, [t0]
	push	eax
	push	fmt
	call	printf
	add	esp, 8 ; _print

	ret ; _rtn

