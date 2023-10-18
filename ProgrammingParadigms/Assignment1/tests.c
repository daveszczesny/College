//
// Created by dawid on 07/10/2023.
//

#include <stdio.h>
#include "tests.h"

void runTests()
{
    int integer;
    int * integerPointer;
    long longNumber;
    double* doublePointer;
    char** charDoublePointer;

    printf("int variable is %zu bytes\n", sizeof(integer));
    printf("int* variable is %zu bytes\n", sizeof(integerPointer));
    printf("longNumber variable is %zu bytes\n", sizeof(longNumber));
    printf("double* variable is %zu bytes\n", sizeof(doublePointer));
    printf("char** variable is %zu bytes\n", sizeof(charDoublePointer));
}