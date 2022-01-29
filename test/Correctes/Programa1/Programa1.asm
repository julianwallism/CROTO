section .data
t50:	dd	0x0
t52:	dd	0x0
t51:	dd	0x0
t10:	dd	0x0
t54:	dd	0x0
t53:	dd	0x0
t12:	dd	0x0
t11:	dd	0x0
t14:	dd	0x0
t13:	dd	0x0
t16:	dd	0x0
t15:	dd	0x0
t18:	dd	0x0
t17:	dd	0x0
t19:	dd	0x0
factorialcomptador:	dd	0x0
t21:	dd	0x0
t20:	dd	0x0
t23:	dd	0x0
t22:	dd	0x0
t25:	dd	0x0
t24:	dd	0x0
t27:	dd	0x0
t26:	dd	0x0
t29:	dd	0x0
t28:	dd	0x0
t0:	dd	0x0
t1:	dd	0x0
t2:	dd	0x0
t3:	dd	0x0
t4:	dd	0x0
t5:	dd	0x0
t6:	dd	0x0
t7:	dd	0x0
t8:	dd	0x0
t9:	dd	0x0
t30:	dd	0x0
t32:	dd	0x0
t31:	dd	0x0
t34:	dd	0x0
t33:	dd	0x0
t36:	dd	0x0
t35:	dd	0x0
t38:	dd	0x0
t37:	dd	0x0
t39:	dd	0x0
factorialret:	dd	0x0
mainb:	dd	0x0
maina:	dd	0x0
mainc:	dd	0x0
t41:	dd	0x0
t40:	dd	0x0
t43:	dd	0x0
t42:	dd	0x0
t45:	dd	0x0
t44:	dd	0x0
t47:	dd	0x0
t46:	dd	0x0
t49:	dd	0x0
mainm:	dd	0x0
t48:	dd	0x0
mayora:	dd	0x0
mayorb:	dd	0x0
mayorc:	dd	0x0
factorialn:	dd	0x0

section .text
global main

fmtout:	db	"%d", 10, 0
fmtin:	db	"%d", 0
extern printf
extern scanf
; lab0 _skip
	lab0 :	nop
; _pmb mayor
	mov	eax, [8+ esp]
	mov	[mayora], eax
	mov	eax, [12+ esp]
	mov	[mayorb], eax
	mov	eax, [16+ esp]
	mov	[mayorc], eax

; t2 = _copy mayora
	mov	eax, [mayora]
	mov dword	[t2], eax
; t3 = _copy mayorb
	mov	eax, [mayorb]
	mov dword	[t3], eax
; t1 = t2 _gt t3
	mov	eax, [t2]
	mov	ebx, [t3]
	cmp	eax, ebx
	jg	lab15
	mov dword [t1], 0
	jmp	lab16
	lab15:	nop
	mov dword [t1], -1
	lab16:	nop
; t5 = _copy mayora
	mov	eax, [mayora]
	mov dword	[t5], eax
; t6 = _copy mayorc
	mov	eax, [mayorc]
	mov dword	[t6], eax
; t4 = t5 _gt t6
	mov	eax, [t5]
	mov	ebx, [t6]
	cmp	eax, ebx
	jg	lab17
	mov dword [t4], 0
	jmp	lab18
	lab17:	nop
	mov dword [t4], -1
	lab18:	nop
; t0 = t1 _and t4
	mov	eax, [t1]
	mov	ebx, [t4]
	and	eax, ebx
	mov	[t0], eax
; _if t0 else lab1
	mov	eax, [t0]
	cmp	eax, 0
	je	lab1
; t7 = _copy mayora
	mov	eax, [mayora]
	mov dword	[t7], eax
; _rtn t7
	mov	eax, [t7]
	mov	[4+esp], eax
	ret
; _goto lab2
	jmp	lab2
; lab1 _skip
	lab1 :	nop
; lab2 _skip
	lab2 :	nop
; t10 = _copy mayorb
	mov	eax, [mayorb]
	mov dword	[t10], eax
; t11 = _copy mayora
	mov	eax, [mayora]
	mov dword	[t11], eax
; t9 = t10 _gt t11
	mov	eax, [t10]
	mov	ebx, [t11]
	cmp	eax, ebx
	jg	lab19
	mov dword [t9], 0
	jmp	lab20
	lab19:	nop
	mov dword [t9], -1
	lab20:	nop
; t13 = _copy mayorb
	mov	eax, [mayorb]
	mov dword	[t13], eax
; t14 = _copy mayorc
	mov	eax, [mayorc]
	mov dword	[t14], eax
; t12 = t13 _gt t14
	mov	eax, [t13]
	mov	ebx, [t14]
	cmp	eax, ebx
	jg	lab21
	mov dword [t12], 0
	jmp	lab22
	lab21:	nop
	mov dword [t12], -1
	lab22:	nop
; t8 = t9 _and t12
	mov	eax, [t9]
	mov	ebx, [t12]
	and	eax, ebx
	mov	[t8], eax
; _if t8 else lab3
	mov	eax, [t8]
	cmp	eax, 0
	je	lab3
; t15 = _copy mayorb
	mov	eax, [mayorb]
	mov dword	[t15], eax
; _rtn t15
	mov	eax, [t15]
	mov	[4+esp], eax
	ret
; _goto lab4
	jmp	lab4
; lab3 _skip
	lab3 :	nop
; lab4 _skip
	lab4 :	nop
; t16 = _copy mayorc
	mov	eax, [mayorc]
	mov dword	[t16], eax
; _rtn t16
	mov	eax, [t16]
	mov	[4+esp], eax
	ret
; lab5 _skip
	lab5 :	nop
; _pmb factorial
	mov	eax, [8+ esp]
	mov	[factorialn], eax

; t17 = _copy 1
	mov	eax, 1
	mov dword	[t17], eax
; factorialret = _copy t17
	mov	eax, [t17]
	mov dword	[factorialret], eax
; t19 = _copy factorialn
	mov	eax, [factorialn]
	mov dword	[t19], eax
; t20 = _copy 1
	mov	eax, 1
	mov dword	[t20], eax
; t18 = t19 _le t20
	mov	eax, [t19]
	mov	ebx, [t20]
	cmp	eax, ebx
	jle	lab23
	mov dword [t18], 0
	jmp	lab24
	lab23:	nop
	mov dword [t18], -1
	lab24:	nop
; _if t18 else lab6
	mov	eax, [t18]
	cmp	eax, 0
	je	lab6
; t21 = _copy factorialret
	mov	eax, [factorialret]
	mov dword	[t21], eax
; _rtn t21
	mov	eax, [t21]
	mov	[4+esp], eax
	ret
; _goto lab7
	jmp	lab7
; lab6 _skip
	lab6 :	nop
; t22 = _copy factorialn
	mov	eax, [factorialn]
	mov dword	[t22], eax
; factorialcomptador = _copy t22
	mov	eax, [t22]
	mov dword	[factorialcomptador], eax
; lab8 _skip
	lab8 :	nop
; t24 = _copy factorialcomptador
	mov	eax, [factorialcomptador]
	mov dword	[t24], eax
; t25 = _copy 1
	mov	eax, 1
	mov dword	[t25], eax
; t23 = t24 _gt t25
	mov	eax, [t24]
	mov	ebx, [t25]
	cmp	eax, ebx
	jg	lab25
	mov dword [t23], 0
	jmp	lab26
	lab25:	nop
	mov dword [t23], -1
	lab26:	nop
; _if t23 else lab9
	mov	eax, [t23]
	cmp	eax, 0
	je	lab9
; t26 = _copy factorialret
	mov	eax, [factorialret]
	mov dword	[t26], eax
; t27 = _copy factorialcomptador
	mov	eax, [factorialcomptador]
	mov dword	[t27], eax
; t28 = t26 _prod t27
	mov	eax, [t26]
	mov	ebx, [t27]
	imul	eax, ebx
	mov	[t28], eax
; factorialret = _copy t28
	mov	eax, [t28]
	mov dword	[factorialret], eax
; t29 = _copy factorialcomptador
	mov	eax, [factorialcomptador]
	mov dword	[t29], eax
; t30 = _copy 1
	mov	eax, 1
	mov dword	[t30], eax
; t31 = t29 _sub t30
	mov	eax, [t29]
	mov	ebx, [t30]
	sub	eax, ebx
	mov	[t31], eax
; factorialcomptador = _copy t31
	mov	eax, [t31]
	mov dword	[factorialcomptador], eax
; _goto lab8
	jmp	lab8
; lab9 _skip
	lab9 :	nop
; lab7 _skip
	lab7 :	nop
; t32 = _copy factorialret
	mov	eax, [factorialret]
	mov dword	[t32], eax
; _rtn t32
	mov	eax, [t32]
	mov	[4+esp], eax
	ret
; lab10 _skip
	lab10 :	nop
	main:
; _pmb main

; t33 = _copy maina
	mov	eax, [maina]
	mov dword	[t33], eax
; _scan t33
	push	t33
	push	fmtin
	call	scanf
	add	esp, 8
; maina = _copy t33
	mov	eax, [t33]
	mov dword	[maina], eax
; t34 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t34], eax
; _scan t34
	push	t34
	push	fmtin
	call	scanf
	add	esp, 8
; mainb = _copy t34
	mov	eax, [t34]
	mov dword	[mainb], eax
; t35 = _copy mainc
	mov	eax, [mainc]
	mov dword	[t35], eax
; _scan t35
	push	t35
	push	fmtin
	call	scanf
	add	esp, 8
; mainc = _copy t35
	mov	eax, [t35]
	mov dword	[mainc], eax
; t38 = _copy maina
	mov	eax, [maina]
	mov dword	[t38], eax
; t39 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t39], eax
; t37 = t38 _gt t39
	mov	eax, [t38]
	mov	ebx, [t39]
	cmp	eax, ebx
	jg	lab27
	mov dword [t37], 0
	jmp	lab28
	lab27:	nop
	mov dword [t37], -1
	lab28:	nop
; t41 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t41], eax
; t42 = _copy mainc
	mov	eax, [mainc]
	mov dword	[t42], eax
; t40 = t41 _eq t42
	mov	eax, [t41]
	mov	ebx, [t42]
	cmp	eax, ebx
	je	lab29
	mov dword [t40], 0
	jmp	lab30
	lab29:	nop
	mov dword [t40], -1
	lab30:	nop
; t36 = t37 _and t40
	mov	eax, [t37]
	mov	ebx, [t40]
	and	eax, ebx
	mov	[t36], eax
; _if t36 else lab11
	mov	eax, [t36]
	cmp	eax, 0
	je	lab11
; t43 = _copy 99
	mov	eax, 99
	mov dword	[t43], eax
; _print t43
	mov	eax, [t43]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; _goto lab12
	jmp	lab12
; lab11 _skip
	lab11 :	nop
; t45 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t45], eax
; t46 = _copy mainc
	mov	eax, [mainc]
	mov dword	[t46], eax
; t44 = t45 _ne t46
	mov	eax, [t45]
	mov	ebx, [t46]
	cmp	eax, ebx
	jne	lab31
	mov dword [t44], 0
	jmp	lab32
	lab31:	nop
	mov dword [t44], -1
	lab32:	nop
; _if t44 else lab13
	mov	eax, [t44]
	cmp	eax, 0
	je	lab13
; t47 = _copy 0
	mov	eax, 0
	mov dword	[t47], eax
; _print t47
	mov	eax, [t47]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; _goto lab14
	jmp	lab14
; lab13 _skip
	lab13 :	nop
; lab14 _skip
	lab14 :	nop
; lab12 _skip
	lab12 :	nop
; t48 = _copy mainc
	mov	eax, [mainc]
	mov dword	[t48], eax
; _param t48
	mov	eax, [t48]
	push	eax
; t49 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t49], eax
; _param t49
	mov	eax, [t49]
	push	eax
; t50 = _copy maina
	mov	eax, [maina]
	mov dword	[t50], eax
; _param t50
	mov	eax, [t50]
	push	eax
; _call mayor
	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 12
; t51 = _copy eax
	mov	eax, eax
	mov dword	[t51], eax
; mainm = _copy t51
	mov	eax, [t51]
	mov dword	[mainm], eax
; t52 = _copy mainm
	mov	eax, [mainm]
	mov dword	[t52], eax
; _print t52
	mov	eax, [t52]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; t53 = _copy mainm
	mov	eax, [mainm]
	mov dword	[t53], eax
; _param t53
	mov	eax, [t53]
	push	eax
; _call factorial
	sub	esp, 4
	call	lab5
	pop	eax
	add	esp, 4
; t54 = _copy eax
	mov	eax, eax
	mov dword	[t54], eax
; _print t54
	mov	eax, [t54]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
