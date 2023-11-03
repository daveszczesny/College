#lang racket

; A cons pair of two numbers
(cons 1 2)

; A list of 3 numbers, using only the cons function
(cons '1 (cons '2 (cons '3 empty)))

;A list containing a string, a number and a nested list of three numbers, using only the
; cons function
(cons 'String (cons '1 (list '(2 3 4))))

;A list containing a string, a number and a nested list of three numbers, using only the
; list function
(list 'String '1 (list 2 3 4))

; A list containing a string, a number and a nested list of three numbers, using only the
; append function
(append (list 'String) (append (list 1) (append (list '(2 3 4) ) empty ) ) )