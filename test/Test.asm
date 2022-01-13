section .data
mainr:	dd	0x0
t2:	dd	0x0
sumaa:	dd	0x0
sumab:	dd	0x0

section .text
global main

; lab0 _skip
	lab0 :	nop
; _pmb suma
	mov	eax, [12+ esp]
	mov	[sumab], eax
; t2 = t0 _add t1
	mov	eax, t0
	mov	ebx, t1
	add	eax, ebx
	mov	[t2], eax
; _rtn t2
	mov	eax, [t2]
	mov	[4+esp], eax
	ret
; lab1 _skip
	lab1 :	nop
	main:
; _pmb main

; _param t3
	mov	eax, t3
	push	eax
; _param t4
	mov	eax, t4
	push	eax
; _call suma
	sub	esp, 4
	call	lab0
	pop	eax
	add	esp, 8
; mainr = _copy t5
	mov	eax, t5
	mov dword	[mainr], eax
; _rtn
	ret
