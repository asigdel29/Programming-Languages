#include <stdio.h>
#include <stdlib.h>


void invert_case(char * str){
    while(*str)
    {
        if(*str >= 'a' && *str <= 'z')
            *str = *str - 32;
        else if(*str >= 'A' && *str <= 'Z')
            *str = *str + 32;

        str++;
    }
}

void main() {
    char str[100];
    char * p ;
    p = str;
    printf("Enter a string:");
    gets(p);
    invert_case(p);
    printf("Reverse of the string: %s\n", p);


}
