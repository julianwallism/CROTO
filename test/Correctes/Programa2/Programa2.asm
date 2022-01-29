section .data
t4:	dd	0x0
t5:	dd	0x0
t6:	dd	0x0
t7:	dd	0x0
t8:	dd	0x0
t9:	dd	0x0
fibonacciret:	dd	0x0
t10:	dd	0x0
t12:	dd	0x0
t11:	dd	0x0
t14:	dd	0x0
t13:	dd	0x0
mainresultado:	dd	0x0
t16:	dd	0x0
t15:	dd	0x0
t18:	dd	0x0
t17:	dd	0x0
t19:	dd	0x0
maina:	dd	0x0
fibonaccib:	dd	0x0
fibonaccia:	dd	0x0
t21:	dd	0x0
t20:	dd	0x0
t23:	dd	0x0
t22:	dd	0x0
t24:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0
fibonaccin:	dd	0x0

section .text
global main

fmtout:	db	"%d", 10, 0
fmtin:	db	"%d", 0
extern printf
extern scanf
; lab0 _skip
	lab0 :	nop
; _pmb fibonacci
	mov	eax, [8+ esp]
	mov	[fibonaccin], eax

; t0 = _copy 0
	mov	eax, 0
	mov dword	[t0], eax
; fibonaccia = _copy t0
	mov	eax, [t0]
	mov dword	[fibonaccia], eax
; t1 = _copy 1
	mov	eax, 1
	mov dword	[t1], eax
; fibonaccib = _copy t1
	mov	eax, [t1]
	mov dword	[fibonaccib], eax
; t2 = _copy fibonaccia
	mov	eax, [fibonaccia]
	mov dword	[t2], eax
; _print t2
	mov	eax, [t2]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; t3 = _copy fibonaccib
	mov	eax, [fibonaccib]
	mov dword	[t3], eax
; _print t3
	mov	eax, [t3]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; lab1 _skip
	lab1 :	nop
; t5 = _copy fibonaccin
	mov	eax, [fibonaccin]
	mov dword	[t5], eax
; t6 = _copy 1
	mov	eax, 1
	mov dword	[t6], eax
; t4 = t5 _gt t6
	mov	eax, [t5]
	mov	ebx, [t6]
	cmp	eax, ebx
	jg	lab6
	mov dword [t4], 0
	jmp	lab7
	lab6:	nop
	mov dword [t4], -1
	lab7:	nop
; _if t4 else lab2
	mov	eax, [t4]
	cmp	eax, 0
	je	lab2
; t7 = _copy fibonaccia
	mov	eax, [fibonaccia]
	mov dword	[t7], eax
; t8 = _copy fibonaccib
	mov	eax, [fibonaccib]
	mov dword	[t8], eax
; t9 = t7 _add t8
	mov	eax, [t7]
	mov	ebx, [t8]
	add	eax, ebx
	mov	[t9], eax
; fibonacciret = _copy t9
	mov	eax, [t9]
	mov dword	[fibonacciret], eax
; t10 = _copy fibonaccib
	mov	eax, [fibonaccib]
	mov dword	[t10], eax
; fibonaccia = _copy t10
	mov	eax, [t10]
	mov dword	[fibonaccia], eax
; t11 = _copy fibonacciret
	mov	eax, [fibonacciret]
	mov dword	[t11], eax
; fibonaccib = _copy t11
	mov	eax, [t11]
	mov dword	[fibonaccib], eax
; t12 = _copy fibonacciret
	mov	eax, [fibonacciret]
	mov dword	[t12], eax
; _print t12
	mov	eax, [t12]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; t13 = _copy fibonaccin
	mov	eax, [fibonaccin]
	mov dword	[t13], eax
; t14 = _copy 1
	mov	eax, 1
	mov dword	[t14], eax
; t15 = t13 _sub t14
	mov	eax, [t13]
	mov	ebx, [t14]
	sub	eax, ebx
	mov	[t15], eax
; fibonaccin = _copy t15
	mov	eax, [t15]
	mov dword	[fibonaccin], eax
; _goto lab1
	jmp	lab1
; lab2 _skip
	lab2 :	nop
; t16 = _copy fibonacciret
	mov	eax, [fibonacciret]
	mov dword	[t16], eax
; _rtn t16
	mov	eax, [t16]
	mov	[4+esp], eax
	ret
; lab3 _skip
	lab3 :	nop
	main:
; _pmb main

; t17 = _copy maina
	mov	eax, [maina]
	mov dword	[t17], eax
; _scan t17
	push	t17
	push	fmtin
	call	scanf
	add	esp, 8
; maina = _copy t17
	mov	eax, [t17]
	mov dword	[maina], eax
; t19 = _copy maina
	mov	eax, [maina]
	mov dword	[t19], eax
; t20 = _copy 1
	mov	eax, 1
	mov dword	[t20], eax
; t18 = t19 _gt t20
	mov	eax, [t19]
	mov	ebx, [t20]
	cmp	eax, ebx
	jg	lab8
	mov dword [t18], 0
	jmp	lab9
	lab8:	nop
	mov dword [t18], -1
	lab9:	nop
; _if t18 else lab4
	mov	eax, [t18]
	cmp	eax, 0
	je	lab4
; t21 = _copy maina
	mov	eax, [maina]
	mov dword	[t21], eax
; _param t21
	mov	eax, [t21]
	push	eax
; _call fibonacci
	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 4
; t22 = _copy eax
	mov	eax, eax
	mov dword	[t22], eax
; mainresultado = _copy t22
	mov	eax, [t22]
	mov dword	[mainresultado], eax
; _goto lab5
	jmp	lab5
; lab4 _skip
	lab4 :	nop
; t23 = _copy maina
	mov	eax, [maina]
	mov dword	[t23], eax
; mainresultado = _copy t23
	mov	eax, [t23]
	mov dword	[mainresultado], eax
; lab5 _skip
	lab5 :	nop
; t24 = _copy mainresultado
	mov	eax, [mainresultado]
	mov dword	[t24], eax
; _print t24
	mov	eax, [t24]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
