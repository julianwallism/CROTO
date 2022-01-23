section .data
t4:	dd	0x0
t5:	dd	0x0
t6:	dd	0x0
t7:	dd	0x0
t8:	dd	0x0
mainb:	dd	0x0
t9:	dd	0x0
maina:	dd	0x0
moduloquocient:	dd	0x0
t10:	dd	0x0
t12:	dd	0x0
t11:	dd	0x0
t14:	dd	0x0
t13:	dd	0x0
t16:	dd	0x0
t15:	dd	0x0
t18:	dd	0x0
t17:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0
moduloa:	dd	0x0
modulob:	dd	0x0

section .text
global main

fmtout:	db	"%d", 10, 0
fmtin:	db	"%d", 0
extern printf
extern scanf
; lab0 _skip
	lab0 :	nop
; _pmb modulo
	mov	eax, [8+ esp]
	mov	[moduloa], eax
	mov	eax, [12+ esp]
	mov	[modulob], eax

; t0 = _copy modulob
	mov	eax, [modulob]
	mov dword	[t0], eax
; t1 = _copy 0
	mov	eax, 0
	mov dword	[t1], eax
; t2 = t0 _le t1
	mov	eax, [t0]
	mov	ebx, [t1]
	cmp	eax, ebx
	jle	lab4
	mov dword [t2], 0
	jmp	lab5
	lab4:	nop
	mov dword [t2], -1
	lab5:	nop
; _if t2 else lab1
	mov	eax, [t2]
	cmp	eax, 0
	je	lab1
; t3 = _copy 0
	mov	eax, 0
	mov dword	[t3], eax
; t4 = _copy 1
	mov	eax, 1
	mov dword	[t4], eax
; t5 = t3 _sub t4
	mov	eax, [t3]
	mov	ebx, [t4]
	sub	eax, ebx
	mov	[t5], eax
; _rtn t5
	mov	eax, [t5]
	mov	[4+esp], eax
	ret
; _goto lab2
	jmp	lab2
; lab1 _skip
	lab1 :	nop
; lab2 _skip
	lab2 :	nop
; t6 = _copy moduloa
	mov	eax, [moduloa]
	mov dword	[t6], eax
; t7 = _copy modulob
	mov	eax, [modulob]
	mov dword	[t7], eax
; t8 = t6 _div t7
	xor	edx, edx
	mov	eax, [t6]
	mov	ebx, [t7]
	div	ebx
	mov	[t8], eax
; moduloquocient = _copy t8
	mov	eax, [t8]
	mov dword	[moduloquocient], eax
; t9 = _copy moduloa
	mov	eax, [moduloa]
	mov dword	[t9], eax
; t10 = _copy modulob
	mov	eax, [modulob]
	mov dword	[t10], eax
; t11 = _copy moduloquocient
	mov	eax, [moduloquocient]
	mov dword	[t11], eax
; t12 = t10 _prod t11
	mov	eax, [t10]
	mov	ebx, [t11]
	imul	eax, ebx
	mov	[t12], eax
; t13 = t9 _sub t12
	mov	eax, [t9]
	mov	ebx, [t12]
	sub	eax, ebx
	mov	[t13], eax
; _rtn t13
	mov	eax, [t13]
	mov	[4+esp], eax
	ret
; lab3 _skip
	lab3 :	nop
	main:
; _pmb main

; t14 = _copy maina
	mov	eax, [maina]
	mov dword	[t14], eax
; _scan t14
	push	t14
	push	fmtin
	call	scanf
	add	esp, 8
; maina = _copy t14
	mov	eax, [t14]
	mov dword	[maina], eax
; t15 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t15], eax
; _scan t15
	push	t15
	push	fmtin
	call	scanf
	add	esp, 8
; mainb = _copy t15
	mov	eax, [t15]
	mov dword	[mainb], eax
; t16 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t16], eax
; _param t16
	mov	eax, [t16]
	push	eax
; t17 = _copy maina
	mov	eax, [maina]
	mov dword	[t17], eax
; _param t17
	mov	eax, [t17]
	push	eax
; _call modulo
	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 8
; t18 = _copy eax
	mov	eax, eax
	mov dword	[t18], eax
; _print t18
	mov	eax, [t18]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
