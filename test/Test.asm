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
t16:	dd	0x0
t15:	dd	0x0
t17:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0
factoriala:	dd	0x0

section .text
global _main

fmt:	db	"%d", 10, 0
extern printf
	lab0 :	nop ; _skip

	mov	eax, [8+ esp]
	mov	[factoriala], eax ; _pmb

	mov	eax, 1
	mov dword	[t0], eax ; _copy

	mov	eax, [t0]
	mov dword	[factorialres], eax ; _copy

	lab1 :	nop ; _skip

	mov	eax, [factoriala]
	mov dword	[t1], eax ; _copy

	mov	eax, 1
	mov dword	[t2], eax ; _copy

	mov	eax, [t1]
	mov	ebx, [t2]
	cmp	eax, ebx
	jg	lab4
	mov dword [t3], 0
	jmp	lab5
	lab4:	nop
	mov dword [t3], -1
	lab5:	nop ; _gt

	mov	eax, [t3]
	cmp	eax, 0
	je	lab2 ; _if

	mov	eax, [factorialres]
	mov dword	[t4], eax ; _copy

	mov	eax, [factoriala]
	mov dword	[t5], eax ; _copy

	mov	eax, 1
	mov dword	[t6], eax ; _copy

	mov	eax, [t5]
	mov	ebx, [t6]
	sub	eax, ebx
	mov	[t7], eax ; _sub

	mov	eax, [t7]
	push	eax ; _param

	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 4 ; _call

	mov	eax, eax
	mov dword	[t8], eax ; _copy

	mov	eax, [t4]
	mov	ebx, [t8]
	imul	eax, ebx
	mov	[t9], eax ; _prod

	mov	eax, [t9]
	mov dword	[factorialres], eax ; _copy

	mov	eax, [factoriala]
	mov dword	[t10], eax ; _copy

	mov	eax, 1
	mov dword	[t11], eax ; _copy

	mov	eax, [t10]
	mov	ebx, [t11]
	sub	eax, ebx
	mov	[t12], eax ; _sub

	mov	eax, [t12]
	mov dword	[factoriala], eax ; _copy

	mov	eax, [factoriala]
	mov dword	[t13], eax ; _copy

	mov	eax, [t13]
	push	eax
	push	fmt
	call	printf
	add	esp, 8 ; _print

	jmp	lab1 ; _goto

	lab2 :	nop ; _skip

	mov	eax, [factorialres]
	mov dword	[t14], eax ; _copy

	mov	eax, [t14]
	mov	[4+esp], eax
	ret ; _rtn

	lab3 :	nop ; _skip

	_main:
 ; _pmb

	mov	eax, 7
	mov dword	[t15], eax ; _copy

	mov	eax, [t15]
	push	eax ; _param

	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 4 ; _call

	mov	eax, eax
	mov dword	[t16], eax ; _copy

	mov	eax, [t16]
	mov dword	[mainres], eax ; _copy

	mov	eax, [mainres]
	mov dword	[t17], eax ; _copy

	mov	eax, [t17]
	push	eax
	push	fmt
	call	printf
	add	esp, 8 ; _print

	ret ; _rtn

