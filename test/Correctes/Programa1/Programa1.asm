section .data
maina:	db	0x0
t0:	db	0x0
t1:	db	0x0
t2:	db	0x0
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
; t1 = _copy maina
	mov	eax, [maina]
	mov dword	[t1], eax
; _if t1 else lab1
	mov	eax, [t1]
	cmp	eax, 0
	je	lab1
; t2 = _copy 10
	mov	eax, 10
	mov dword	[t2], eax
; _print t2
	mov	eax, [t2]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; _goto lab2
	jmp	lab2
; lab1 _skip
	lab1 :	nop
; t3 = _copy 0
	mov	eax, 0
	mov dword	[t3], eax
; _print t3
	mov	eax, [t3]
	push	eax
	push	fmtout
	call	printf
	add	esp, 8
; lab2 _skip
	lab2 :	nop
