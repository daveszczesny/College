#lang racket

(provide ins_beg)
(provide ins_end)
(provide cout_top_level)
(provide count_instances)
(provide count_instances_tr)
(provide count_instances_deep)

; function to insert to head of list
(define (ins_beg el lst)

    (append (list el) lst)

)

; function to insert to tail of list
(define (ins_end el lst)
    (append lst (list el))
)


; Helper function for cout_top_level
(define (cout_top_level_helper lst num)

  (cond
    [ (empty? lst) num]
    [ (cout_top_level_helper (cdr lst) (+ 1 num)) ]
   )
)

; cout_top_level function to count elements in list
(define (cout_top_level lst)
  (cout_top_level_helper lst '0)
 )


; function to count instances
(define (count_instances lst value count)
  (cond
    [ (empty? lst) count ]
    [  (= (car lst) value) (count_instances (cdr lst) value (+ 1 count))]
    [ (count_instances (cdr lst) value count) ]
  )
)


; tail recursive function
(define ( count_instances_tr lst value )
    (ci_tr lst value '0)
)

(define ( ci_tr lst value count )
    (cond
        [ (empty? lst) count]
        [ (= (car lst) value) (ci_tr (cdr lst) value (+ 1 count)) ]
        [ (ci_tr (cdr lst) value count) ]
    )
)







(define (count_instances_deep lst value)
  (cid_tr lst value 0)
)

(define (cid_tr lst value count)
  (cond
    [(empty? lst) count]
    [(list? (car lst))
     (cid_tr (cdr lst) value
             (+ count (count_instances_deep (car lst) value)))
     ]
    [(= (car lst) value)
     (cid_tr (cdr lst) value (+ 1 count))
     ]
    [else
     (cid_tr (cdr lst) value count)
     ]
  )
)



; Examples of code
(ins_beg '2 '(3 4 5)) ; returns '(2 3 4 5)
(ins_beg 'a '(b c d)) ; returns '(a b c d)
(ins_beg '(a b) '(c d e)) ; returns ( (a b) c d e)

(ins_end 'a '(b c d)) ; returns '(b c d a)
(ins_end '(a b) '(b c d)) ; returns '(b c d (a b))

(cout_top_level '(2 3 4)) ; returns '3

(count_instances '(2 2 3 4 5) '2 '0)

(count_instances_tr '(3 3 3 3 4 5) '3)

(count_instances_deep '(3 3 3 (3 3) 4) '3)