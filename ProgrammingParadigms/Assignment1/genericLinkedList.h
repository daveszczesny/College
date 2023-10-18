//
// Created by dawid on 07/10/2023.
//

#ifndef ASSIGNMENT1_GENERICLINKEDLIST_H
#define ASSIGNMENT1_GENERICLINKEDLIST_H

// pointer function
typedef void (*PrintFunction)(void*);
typedef struct genericListElementStruct genericElement;

// creates a new linked list element with given content, size, and print function
// returns a pointer to the element
genericElement * gen_createEl(void * data, size_t size, PrintFunction printFunction);

// Prints out each element in the linkedlist
void gen_traverse(genericElement * start);

//Inserts a new element after the given el
//Returns the pointer to the new element
genericElement * gen_insertAfter(genericElement * after, void * data, size_t size, PrintFunction printFunction);

//Delete the element after the given el
void gen_deleteAfter(genericElement * after);

// Returns length of linked list
int gen_length(genericElement * list);


// Add element to the top of the stack
void gen_push(genericElement ** list, void * data, size_t size, PrintFunction printFunction);

// Remove element from top of stack
genericElement * gen_pop(genericElement ** list);

// Add a new element in front of head.
void gen_enqueue(genericElement ** list, void * data, size_t size, PrintFunction printFunction);

// Remove last element in linked list
genericElement * gen_dequeue(genericElement * list);

// Print functions
void printInt(void * data);
void printStr(void * data);
void printChar(void * data);


#endif //ASSIGNMENT1_GENERICLINKEDLIST_H
