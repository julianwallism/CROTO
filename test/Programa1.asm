section .data
t4:	db	0x0
t5:	db	0x0
maine:	dd	0x0
t6:	db	0x0
t7:	db	0x0
mainb:	db	0x0
t8:	db	0x0
maina:	db	0x0
t9:	db	0x0
maind:	dd	0x0
mainc:	db	0x0
t10:	db	0x0
t12:	dd	0x0
t11:	db	0x0
t14:	dd	0x0
t13:	dd	0x0
t16:	dd	0x0
t15:	dd	0x0
t17:	dd	0x0
t0:	db	0x0
t1:	db	0x0
t2:	db	0x0
t3:	db	0x0

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

; t0 = _copy -1
	mov	eax, -1
	mov dword	[t0], eax
; maina = _copy t0
	mov	eax, [t0]
	mov dword	[maina], eax
; t1 = _copy 0
	mov	eax, 0
	mov dword	[t1], eax
; mainb = _copy t1
	mov	eax, [t1]
	mov dword	[mainb], eax
; t2 = _copy maina
	mov	eax, [maina]
	mov dword	[t2], eax
; _print t2
	mov	eax, [t2]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; t3 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t3], eax
; _print t3
	mov	eax, [t3]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; t6 = _copy maina
	mov	eax, [maina]
	mov dword	[t6], eax
; t7 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t7], eax
; t5 = t6 _and t7
	mov	eax, [t6]
	mov	ebx, [t7]
	and	eax, ebx
	mov	[t5], eax
; t9 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t9], eax
; t8 =  _not t9
	mov	eax, [t9]
	not	eax
	mov	[t8], eax
; t4 = t5 _or t8
	mov	eax, [t5]
	mov	ebx, [t8]
	or	eax, ebx
	mov	[t4], eax
; mainc = _copy t4
	mov	eax, [t4]
	mov dword	[mainc], eax
; t10 = _copy mainc
	mov	eax, [mainc]
	mov dword	[t10], eax
; _if t10 else lab1
	mov	eax, [t10]
	cmp	eax, 0
	je	lab1
; t11 = _copy 1
	mov	eax, 1
	mov dword	[t11], eax
; _print t11
	mov	eax, [t11]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; _goto lab2
	jmp	lab2
; lab1 _skip
	lab1 :	nop
; t12 = _copy 0
	mov	eax, 0
	mov dword	[t12], eax
; _print t12
	mov	eax, [t12]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; lab2 _skip
	lab2 :	nop
; t13 = _copy 9
	mov	eax, 9
	mov dword	[t13], eax
; maind = _copy t13
	mov	eax, [t13]
	mov dword	[maind], eax
; t14 = _copy 0
	mov	eax, 0
	mov dword	[t14], eax
; t15 = _copy maind
	mov	eax, [maind]
	mov dword	[t15], eax
; t16 = t14 _sub t15
	mov	eax, [t14]
	mov	ebx, [t15]
	sub	eax, ebx
	mov	[t16], eax
; maine = _copy t16
	mov	eax, [t16]
	mov dword	[maine], eax
; t17 = _copy maine
	mov	eax, [maine]
	mov dword	[t17], eax
; _print t17
	mov	eax, [t17]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
