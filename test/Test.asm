global _start

section .data
t4:	dd	0x0
maina:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0
sumaa:	dd	0x0
sumab:	dd	0x0

section .text
	_start:
	lab0 :	nop
	mov	eax, 12[esp]
	mov	[sumab], eax
	mov	eax, a
	mov dword	[t0], eax
	mov	eax, b
	mov dword	[t1], eax
	mov	eax, [t0]
	mov	ebx, [t1]
	add	eax, ebx
	mov	[t2], eax
	mov	eax, [t2]
	mov	4[esp], eax
	ret
	lab1 :	nop

	mov	eax, 5
	mov dword	[t3], eax
	mov	eax, [t3]
	push	eax
	mov	eax, 3
	mov dword	[t4], eax
	mov	eax, [t4]
	push	eax
	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 8
	mov	eax, %rtn
	mov dword	[maina], eax
	ret
