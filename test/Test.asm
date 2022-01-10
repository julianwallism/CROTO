section .data
t4:	dd	0x0
t5:	dd	0x0
t6:	dd	0x0
factorialres:	dd	0x0
t7:	dd	0x0
t8:	dd	0x0
t9:	dd	0x0
mainres:	dd	0x0
t10:	dd	0x0
t12:	dd	0x0
t11:	dd	0x0
t14:	dd	0x0
t13:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0
factoriala:	dd	0x0

section .text
global main

fmt:	db	"%d", 10, 0
extern printf
	lab0 :	nop ; _skip

	mov	eax, [8+ esp]
	mov	[factoriala], eax ; _pmb

	mov	eax, factoriala
	mov dword	[t0], eax ; _copy

	mov	eax, [t0]
	push	eax
	push	fmt
	call	printf
	add	esp, 8 ; _print

	mov	eax, 1
	mov dword	[t1], eax ; _copy

	mov	eax, [t1]
	mov dword	[factorialres], eax ; _copy

	lab1 :	nop ; _skip

	mov	eax, factoriala
	mov dword	[t2], eax ; _copy

	mov	eax, 1
	mov dword	[t3], eax ; _copy

	mov	eax, [t2]
	mov	ebx, [t3]
	cmp	eax, ebx
	jg	lab4
	mov dword [t4], 0
	jmp	lab5
	lab4:	nop
	mov dword [t4], -1
	lab5:	nop ; _gt

	mov	eax, [t4]
	cmp	eax, 0
	je	lab2 ; _if

	mov	eax, [factorialres]
	mov dword	[t5], eax ; _copy

	mov	eax, [t5]
	push	eax
	push	fmt
	call	printf
	add	esp, 8 ; _print

	mov	eax, [factorialres]
	mov dword	[t6], eax ; _copy

	mov	eax, factoriala
	mov dword	[t7], eax ; _copy

	mov	eax, [t6]
	mov	ebx, [t7]
	imul	eax, ebx
	mov	[t8], eax ; _prod

	mov	eax, [t8]
	mov dword	[factorialres], eax ; _copy

	mov	eax, factoriala
	mov dword	[t9], eax ; _copy

	mov	eax, 1
	mov dword	[t10], eax ; _copy

	mov	eax, [t9]
	mov	ebx, [t10]
	sub	eax, ebx
	mov	[t11], eax ; _sub

	mov	eax, [t11]
	mov dword	[factoriala], eax ; _copy

	jmp	lab1 ; _goto

	lab2 :	nop ; _skip

	mov	eax, [factorialres]
	mov dword	[t12], eax ; _copy

	mov	eax, [t12]
	mov	[4+esp], eax
	ret ; _rtn

	lab3 :	nop ; _skip

	main:
 ; _pmb

	mov	eax, 3
	mov dword	[t13], eax ; _copy

	mov	eax, t13
	push	eax ; _param

	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 4 ; _call

	mov dword	[mainres], eax ; _copy

	mov	eax, 3
	mov dword	[t14], eax ; _copy

	mov	eax, [t14]
	push	eax
	push	fmt
	call	printf
	add	esp, 8 ; _print

	ret ; _rtn

