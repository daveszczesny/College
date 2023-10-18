#include <stdio.h>
#include "linkedList.h"
#include "genericLinkedList.h"
#include "tests.h"


int main() {
    char* firstString = "First";
    char* secondString = "Second";
    int number = 25;
    char* thirdString = "Third";
    char character = 'k';

    genericElement * head = gen_createEl(firstString , sizeof(firstString), printStr);

    gen_traverse(head);
    gen_dequeue(head);
    gen_traverse(head);
    return 0;
}