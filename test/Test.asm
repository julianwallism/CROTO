section .data
t4:	dd	0x0
t5:	dd	0x0
t6:	dd	0x0
t7:	dd	0x0
mainb:	dd	0x0
t8:	dd	0x0
maina:	dd	0x0
t9:	dd	0x0
t10:	dd	0x0
t11:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0

section .text
global main

fmtout:	db	"%d", 10, 0
fmtin:	db	"%d", 0
extern printf
extern scanf
; lab0 _skip
	lab0 :	nop
	main:
; _pmb main

; t0 = _copy maina
	mov	eax, [maina]
	mov dword	[t0], eax
; _scan t0
	push	t0
	push	fmtin
	call	scanf
	add	esp, 8
; maina = _copy t0
	mov	eax, [t0]
	mov dword	[maina], eax
; lab1 _skip
	lab1 :	nop
; t1 = _copy maina
	mov	eax, [maina]
	mov dword	[t1], eax
; t2 = _copy 34
	mov	eax, 34
	mov dword	[t2], eax
; t3 = t1 _lt t2
	mov	eax, [t1]
	mov	ebx, [t2]
	cmp	eax, ebx
	jl	lab3
	mov dword [t3], 0
	jmp	lab4
	lab3:	nop
	mov dword [t3], -1
	lab4:	nop
; _if t3 else lab2
	mov	eax, [t3]
	cmp	eax, 0
	je	lab2
; t4 = _copy maina
	mov	eax, [maina]
	mov dword	[t4], eax
; t5 = _copy 2
	mov	eax, 2
	mov dword	[t5], eax
; t6 = t4 _prod t5
	mov	eax, [t4]
	mov	ebx, [t5]
	imul	eax, ebx
	mov	[t6], eax
; maina = _copy t6
	mov	eax, [t6]
	mov dword	[maina], eax
; t7 = _copy 3
	mov	eax, 3
	mov dword	[t7], eax
; t8 = _copy maina
	mov	eax, [maina]
	mov dword	[t8], eax
; t9 = t7 _add t8
	mov	eax, [t7]
	mov	ebx, [t8]
	add	eax, ebx
	mov	[t9], eax
; mainb = _copy t9
	mov	eax, [t9]
	mov dword	[mainb], eax
; _goto lab1
	jmp	lab1
; lab2 _skip
	lab2 :	nop
; t10 = _copy maina
	mov	eax, [maina]
	mov dword	[t10], eax
; _print t10
	mov	eax, [t10]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; t11 = _copy 7
	mov	eax, 7
	mov dword	[t11], eax
; _print t11
	mov	eax, [t11]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; _rtn
	ret
