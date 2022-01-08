section .data
maina:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0

section .text
global main

fmt:	db	"%d", 10, 0
extern printf
	main:
	lab0 :	nop

	mov dword	t0, 3
	mov dword	t1, 4
	mov	eax, t0
	mov	ebx, t1
	add	eax, ebx
	mov	t2, eax
	mov dword	maina, t2
	mov dword	t3, maina
	mov	eax, t3
push	eax
push	fmt
call	printf
add	esp, 8
	ret
