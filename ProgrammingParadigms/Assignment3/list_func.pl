takes(tom, ct331).
takes(mary, ct331).
takes(joe, ct331).
takes(tom, ct345).
takes(mary, ct345).
instructs(bob, ct331).
instructs(ann, ct345).

teaches(Instructor, Student) :-
    instructs(Instructor,Course),
    takes(Student, Course).


classmates(Student1, Student2) :-
    takes(Student1, Course),
    takes(Student2, Course).


contains1(Lst, El) :-
    [El | _] = Lst.

contains2(Lst1, Lst2) :-
    [ _ | Lst1 ] = Lst2.


% base case, list is empty, element not in list
isNotElementInList(_, []) :-
    !, true.

% element matches the head
isNotElementInList(El, [El | _]) :-
    !, false.

% Doesn't match head, recurse through tail
isNotElementInList(El, [_ | T]) :-
    isNotElementInList(El, T).



% base case, combine remaining lists
mergeLists([], List, List).

% Add head of list to merged, and recurse through tail
mergeLists([H|T], List3, [H|Merged]):-mergeLists(T, List3, Merged).

% If first list is now empty, move on to list2
mergeLists([], List2, List3, Merged):- mergeLists(List2, List3, Merged).

% Add head of list to merged, and recurse through tail
mergeLists([H|T], List2, List3, [H|Merged]):- mergeLists(T, List2, List3, Merged).


% Caller function
reverseList(List, Reversed) :- reverseList(List, [], Reversed).

% Base case, combine head and reversed list, when tail is empty
reverseList([], Rev, Rev).

% Insert head into the middle
reverseList([H|T], X, Reversed) :-
    reverseList(T, [H|X], Reversed).




% base case, El is the biggest number.
insertInOrder(El, [], [El]).

% El is less then Head, hence insert before
insertInOrder(El, [H | T], [El, H | T]) :-
    El < H.

% El, is bigger than H, recurse through list
insertInOrder(El, [H | T], [H | NewList]) :-
    El > H,
    insertInOrder(El, T, NewList).
