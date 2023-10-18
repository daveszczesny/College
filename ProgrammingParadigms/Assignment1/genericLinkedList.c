//
// Created by dawid on 07/10/2023.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "genericLinkedList.h"

typedef struct genericListElementStruct{
    void * data;
    size_t size;
    struct genericListElementStruct * next;
    PrintFunction printFunction;

} genericElement;

// creates a new linked list element with given content, size, and print function
// returns a pointer to the element
genericElement * gen_createEl(void * data, size_t size, PrintFunction printFunction)
{
    // allocating memory for element
    genericElement * e = malloc(sizeof(genericElement));
    if(e == NULL){
        // if malloc() failed
        return NULL;
    }

    // set elements size, allocate memory for data, and copy to it
    e->size = size;
    e->data = malloc(size);
    if(e->data == NULL){
        free(e);
        return NULL;
    }
    memcpy(e->data, data, size);
    e->next = NULL;
    e->printFunction=printFunction;
    return e;
}

// Prints out each element in the linkedlist
void gen_traverse(genericElement * start)
{
    genericElement * current = start;
    while(current != NULL){
        current->printFunction(current->data);
        current = current->next;
    }
}

//Inserts a new element after the given el
//Returns the pointer to the new element
genericElement * gen_insertAfter(genericElement * el, void * data, size_t size, PrintFunction printFunction)
{
    genericElement * newEl = gen_createEl(data, size, printFunction);
    genericElement * next = el->next;
    newEl->next = next;
    el->next = newEl;
    return newEl;
}

//Delete the element after the given el
void gen_deleteAfter(genericElement * after)
{
    genericElement  * delete = after->next;
    genericElement  * newNext = delete->next;

    after->next = newNext;

    free(delete->data);
    free(delete);

}

// Returns length of linked list
int gen_length(genericElement * list)
{
    int count = 0;
    genericElement * current = list;
    while(current != NULL)
    {
        count++;
        current = current->next;
    }

    return count;
}

// Add element to the top of the stack
void gen_push(genericElement ** list, void * data, size_t size, PrintFunction printFunction)
{
    genericElement * newEl = gen_createEl(data, size, printFunction);
    newEl->next = *list;
    *list = newEl;
}

// Remove element from top of stack
genericElement * gen_pop(genericElement ** list)
{
    if(*list == NULL){
        return NULL;
    }

    genericElement * poppedElement = *list;
    *list = (*list)->next;

    poppedElement->next = NULL;
    return poppedElement;
}

// Add a new element in front of head.
void gen_enqueue(genericElement ** list, void * data, size_t size, PrintFunction printFunction)
{
    genericElement * newEl = gen_createEl(data, size, printFunction);
    newEl->next = *list;
    *list = newEl;
}

// Remove last element in linked list
genericElement * gen_dequeue(genericElement * list) {
    // Null check
    if(list == NULL)
    {
        return NULL;
    }else if(list->next == NULL){
        return list;
    }

    // Loop until we find second last element
    genericElement * current = list;
    while(current->next->next != NULL){
        current = current->next;
    }

    // reference the last element
    genericElement * dequeuedElement = current->next;
    current->next = NULL; // clear the second last element's next pointer

    // returned dequeue element
    return dequeuedElement;
}

// print functions
void printInt(void * data){
    printf("%d\n", *(int*)data);
}

void printStr(void * data){
    printf("%s\n", (char*)data);
}

void printChar(void * data){
    printf("%c\n", *(char*)data);
}