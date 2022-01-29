section .data
t4:	dd	0x0
t5:	dd	0x0
t6:	dd	0x0
mainb:	dd	0x0
maina:	dd	0x0
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

; t0 = _copy 2
	mov	eax, 2
	mov dword	[t0], eax
; maina = _copy t0
	mov	eax, [t0]
	mov dword	[maina], eax
; t1 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t1], eax
; _scan t1
	push	t1
	push	fmtin
	call	scanf
	add	esp, 8
; mainb = _copy t1
	mov	eax, [t1]
	mov dword	[mainb], eax
; t3 = _copy maina
	mov	eax, [maina]
	mov dword	[t3], eax
; t4 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t4], eax
; t2 = t3 _gt t4
	mov	eax, [t3]
	mov	ebx, [t4]
	cmp	eax, ebx
	jg	lab3
	mov dword [t2], 0
	jmp	lab4
	lab3:	nop
	mov dword [t2], -1
	lab4:	nop
; _if t2 else lab1
	mov	eax, [t2]
	cmp	eax, 0
	je	lab1
; t5 = _copy maina
	mov	eax, [maina]
	mov dword	[t5], eax
; _print t5
	mov	eax, [t5]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; _goto lab2
	jmp	lab2
; lab1 _skip
	lab1 :	nop
; t6 = _copy mainb
	mov	eax, [mainb]
	mov dword	[t6], eax
; _print t6
	mov	eax, [t6]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; lab2 _skip
	lab2 :	nop
