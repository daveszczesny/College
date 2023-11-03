#lang racket

; function definitions
(provide ins_bt)
(provide tree)
(provide inorder)
(provide ispresent)
(provide ins_list)
(provide treesort)
(provide treesort_ho)

; helper function to create a basic binary node of element as root and null children
(define (tree el)
  (list '() el '())
  )

; function to insert element into a binary tree
; if binary tree is empty, it creates a binary tree
(define (ins_bt root el)
  (cond
  ; base case, if the tree is empty return the tree with the element as its root
    [ (empty? root) (tree el) ]
    ; if element is less than the current root
    [ (> (cadr root) el)
    ; create a list of
    ; left child => recursive function taken the first element of root and the inserting element
    ; root => is the current root
    ; right child => the current right child
      (list (ins_bt (car root) el)
            (cadr root)
            (caddr root)
            ) ]
    [
      ; else create a list of
      ; left child => current left child
      ; root => current root
      ; right child => recursive function taken the last element of root and the inserting element
     (list (car root)
           (cadr root)
           (ins_bt (caddr root) el))
     ]
    )
 )


; helper function to append the element at the tail of the list
(define (return_list lst el)
  (append lst (list el))
)

; Displays in ascending sorted order the contents of a binary tree
(define (transverse_inorder tree lst)
  (cond
    ; base case, if tree is empty return the sorted list
    [ (empty? tree) lst]
    ; Recursively transverse the left subtree and and add it all to a list,
    ; then add that list to the recursive transverse of the right subtree
    [ (transverse_inorder (caddr tree) (return_list (transverse_inorder (car tree) lst) (cadr tree)))]
   )
)

; helper function to add element to the head of a list
(define (return_desc_list lst el)
    (append (list el) lst)
)

; Displays in desending sorted order the contents of a binary tree
(define (transverse_desc_order tree lst)
  (cond
    ; base case, if tree is empty return the sorted list
    [ (empty? tree) lst]
    ; Recursively transverse the left subtree and and add it all to a list,
    ; then add that list to the recursive transverse of the right subtree
    [ (transverse_desc_order (caddr tree) (return_desc_list (transverse_desc_order (car tree) lst) (cadr tree)))]
   )
)

; funciton that takes in a tree and returns a sorted list in ascending order
(define (inorder tree)
  (transverse_inorder tree '())
)

; function that takes in a tree and returns a sorted list in descending order
(define (desc_order tree)
    (transverse_desc_order tree '())
)

; Checks if an item is in a binary tree
(define (ispresent item tree)
    (cond
      ; base case, checks if tree is empty, if it is returns false
      ; (should only be empty if element not found)
      [ (empty? tree) #f ] ; 
      ; element is found, return true
      [ (= (cadr tree) item) #t ]
      ; element is less than the left child, recurse through left child node
      [ (< item (cadr tree))
            ( ispresent item (car tree) ) ]
      ; element must be greater than the right child hence, recurse through right child node
      [ else (ispresent item (caddr tree)) ]
    )
)

; insert a list into a binary tree
; We will take first element in the list and use our pre-existing ins_bt function
; Inserts list elements into a binary tree (not balanced as it was not mentioned in the question)
(define (ins_list tree lst)
    (cond
      ; base case, if the list is empty return the tree
      [(empty? lst) tree]
      [(ispresent (car lst) tree) (ins_list tree (cdr lst))]
      ; if not, recursively call the function using our ins_bt function to add elements
      [else (ins_list (ins_bt tree (car lst)) (cdr lst)) ]
    )
)

; Tree sort algorithm
; Inserts list into a binary tree
; runs inorder transversal on the binary tree to return the sorted list in ascending order
(define (treesort lst)
  (inorder (ins_list '() lst))
)

; helper function to find the last digit of an element
(define (last_digit n)
    (modulo n 10)
)

; function to return a list in ascending order based on the last digit
(define (return_list_asc_last_digit lst el)
    (cond
      ; base case, list is empty, return the element as a list
      [ (empty? lst) (list el) ]
      ; if the last digit of the current element in the list is greater than or equal
      ; to the last digit of our element, insert the element at the beginning of the list
      [ (>= (last_digit (car lst)) (last_digit el)) (cons el lst) ]
      ; else add the first element of list to the recursve outcome of the function
      ; taking the remainer of the list and the element
      [ else (cons (car lst) (return_list_asc_last_digit (cdr lst) el)) ]
    )
)

; function to transverse a binary tree in ascending order based on the last digit
(define (transverse_asc_last_digit tree lst)
    (cond
      ; base case, if the tree is empty return the list
      [ (empty? tree) lst]
      ; recursively run the function with the left sub-tree and the current list, and add the result 
      ; to the existing list
      ; the call the function for the right sub-tree with the left sub-tree's list as its own
      [ (transverse_asc_last_digit (caddr tree) (return_list_asc_last_digit (transverse_asc_last_digit (car tree) lst) (cadr tree)))]

   )
)

; function to obtain a sorted list from a binary tree in ascending order based on the last digit
(define (asc_last_digit tree)
    ; call the transverse ascedning last digit function with the binary tree and a null starting list
    (transverse_asc_last_digit tree '())
)

; function to order a list in an order provided by the order function
(define (treesort_ho lst order_fn)
  ; call the order function with the ins_list (which builds a binary tree from a list)
  (order_fn (ins_list '() lst))
)


; Example of code
(display "Displays in sorted order the contents of a binary tree =>  ")
(inorder (ins_list '() (list 2 8 10 1 9)))

(display "Returns #t ir #f if a given item is present or absent in a binary tree (10 8 5 2 1) item: 2 =>  ")
(ispresent '2 (ins_list '() (list 10 8 5 2 1)))
(display "Returns #t ir #f if a given item is present or absent in a binary tree (10 8 5 2 1) item: 9 =>  ")
(ispresent '9 (ins_list '() (list 10 8 5 2 1)))

(display "Inserts an item into a binary tree (10 8 5 2 1), Inserting: 15 =>  ")
(ins_bt (ins_list '() (list 10 8 5 2 1)) '15)


(display "Inserting a list into a binary tree (10 11 2 1), Inserting: (15 5 6) =>  ")
(ins_list (ins_list '() (list 10 11 2 1)) (list 15 5 6))

(display "Tree sort algorithm on binary tree (10 11 2 1 15 6) =>  ")
(treesort (list 10 11 2 1 15 6))

(display "Higher order version of Tree sort algorithm on binary tree (10 11 7 8 2) in ascending order =>  ")
(treesort_ho (list 10 11 7 8 2) inorder)

(display "Higher order version of Tree sort algorithm on binary tree (10 11 7 8 2) in descending order =>  ")
(treesort_ho (list 10 11 7 8 2) desc_order)

(display "Higher order version of Tree sort algorithm on binary tree (10 11 7 8 2) in ascending order based on last digit =>  ")
(treesort_ho (list 10 11 7 8 2) asc_last_digit)