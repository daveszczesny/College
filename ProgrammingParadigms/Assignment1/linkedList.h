#ifndef ASSIGNMENT1_LINKED_LIST
#define ASSIGNMENT1_LINKED_LIST

typedef struct listElementStruct listElement;

//Creates a new linked list element with given content of size
//Returns a pointer to the element
listElement* createEl(char* data, size_t size);

//Prints out each element in the list
void traverse(listElement* start);

//Inserts a new element after the given el
//Returns the pointer to the new element
listElement* insertAfter(listElement* after, char* data, size_t size);

//Delete the element after the given el
void deleteAfter(listElement* after);

// Returns length of linked list
int length(listElement* list);

// Adds a new element at the top of stack
void push(listElement** list, char* data, size_t size);

// Removes and returns element at the top of stack
listElement* pop(listElement ** list);

// Add a new element in front of head.
void enqueue(listElement ** list, char* data, size_t size);

// Remove last element in linked list
listElement * dequeue(listElement * list);

#endif