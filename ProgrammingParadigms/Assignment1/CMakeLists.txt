cmake_minimum_required(VERSION 3.26)
project(Assignment1)

set(CMAKE_CXX_STANDARD 17)

add_executable(Assignment1 main.c
        linkedList.c
        linkedList.h
        genericLinkedList.h
        genericLinkedList.c
        tests.c
        tests.h
)
