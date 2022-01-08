global _start

section .data
maina:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0
fmt:	db	"%d", 10, 0
extern printf

section .text
	_start:
	lab0 :	nop

	mov dword	[t0], 3
	mov dword	[maina], t0
	mov dword	[t1], maina
	mov dword	[t2], 2
	mov	eax, [t1]
	mov	ebx, [t2]
	add	eax, ebx
	mov	[t3], eax
	mov	ebx, dword [t3]
	push	ebx
	push	fmt
	call	printf
	add	esp, 8
	ret
