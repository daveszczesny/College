#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "linkedList.h"

typedef struct listElementStruct{
  char* data;
  size_t size;
  struct listElementStruct* next;
} listElement;

//Creates a new linked list element with given content of size
//Returns a pointer to the element
listElement* createEl(char* data, size_t size){
  listElement* e = malloc(sizeof(listElement));
  if(e == NULL){
    //malloc has had an error
    return NULL; //return NULL to indicate an error.
  }
  char* dataPointer = malloc(sizeof(char)*size);
  if(dataPointer == NULL){
    //malloc has had an error
    free(e); //release the previously allocated memory
    return NULL; //return NULL to indicate an error.
  }
  strcpy(dataPointer, data);
  e->data = dataPointer;
  e->size = size;
  e->next = NULL;
  return e;
}

//Prints out each element in the list
void traverse(listElement* start){
  listElement* current = start;
  while(current != NULL){
    printf("%s\n", current->data);
    current = current->next;
  }
}

//Inserts a new element after the given el
//Returns the pointer to the new element
listElement* insertAfter(listElement* el, char* data, size_t size){
  listElement* newEl = createEl(data, size);
  listElement* next = el->next;
  newEl->next = next;
  el->next = newEl;
  return newEl;
}


//Delete the element after the given el
void deleteAfter(listElement* after){
  listElement* delete = after->next;
  listElement* newNext = delete->next;
  after->next = newNext;
  //need to free the memory because we used malloc
  free(delete->data);
  free(delete);
}

//Returns the length of a linked list
int length(listElement* list)
{
    int count = 0;
    listElement * current = list;
    while(current != NULL)
    {
        count++;
        current = current->next;
    }

    return count;
}

// Add element to the top of the stack
void push(listElement** list, char* data, size_t size)
{
    listElement * newEl = createEl(data, size);
    newEl->next = *list;
    *list = newEl;
}

// Remove element from top of stack
listElement* pop(listElement ** list)
{
    // check if list is empty
    if(*list == NULL){return NULL;}

    listElement * poppedElement = *list;
    *list = (*list)->next;
    poppedElement->next = NULL;
    return poppedElement;
}

// Adds element to the front of linked list
void enqueue(listElement ** list, char* data, size_t size)
{
    // Create new element
    listElement * newEl = createEl(data, size);
    // set the list in front of it.
    newEl->next = *list;
    *list = newEl;
}

// Remove last element in linked list
listElement * dequeue(listElement * list)
{
    // Null check
    if(list == NULL)
    {
        return NULL;
    }else if(list->next == NULL){
        return list;
    }

    // Loop until we find second last element
    listElement * current = list;
    while(current->next->next != NULL){
        current = current->next;
    }

    // reference the last element
    listElement * dequeuedElement = current->next;
    current->next = NULL; // clear the second last element's next pointer

    // returned dequeue element
    return dequeuedElement;
}
