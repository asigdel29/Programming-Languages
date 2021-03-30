#include <dirent.h> //used for directory analysis
#include <stdio.h> //used for defining standard input output
#include <stdlib.h> //used for defining variable types
#include <stdbool.h> //used for bool (boolean)begins function

//Adding Nodes for LinkedList Implementation
typedef struct node
{
    char* folder;
    struct node* next;
} node;
struct node *head;

void addNode(char *folder, struct node **node_ptr) { // Adding Node to link
    struct node *temp_ptr;
    struct node *temp_ptr2;
    if (*node_ptr == NULL) { //first link
        *node_ptr = (struct node *) malloc(sizeof(struct node));
        (*node_ptr)->folder = folder;
        (*node_ptr)->next = NULL;
    }
    else { //Other link
        for (struct node *i = *node_ptr; i->next != NULL; i = i->next) temp_ptr2 = i;
        temp_ptr = (struct node *) malloc(sizeof(struct node));
       (temp_ptr2)->next =  temp_ptr;
        (temp_ptr2)->next -> folder= folder;
        (temp_ptr2)->next ->next = NULL;
    }
}

//Traversing linked list and print
void showFiles(struct node **array, char *dirName, char *startString) {
    printf("\nFiles starting with %s in %s:\n", startString, dirName);
    for (int i = 0; i < 64; i++) {
        head = array[i];
        for (struct node *j = head; j != NULL; j = j->next) {
            printf("%s\n", j->folder);//Printing corresponding file names
        }
    }
}

//Functions used for resetting directory path before directory analysis
void clearList(struct node **head_ptr) { //Clearing linked list
    struct node *i = *head_ptr;
    if (i->next != NULL) {
        clearList(&(i->next));
        i->next = NULL;
    }
    free(i);
}
void clearArray(struct node *arr[64]) { //Clearing Array Entries
    for (int i = 0; i < 64; i++) {
        if (arr[i] != NULL) clearList(&arr[i]);
    }
}
void createArray(struct node **arr[64]) { //Creating array of length 26
    for (int i = 0; i < 64; i++)(*arr)[i] = NULL;
}
void resetData(struct node *arr[64]) { //Resetting data when user enters new directory
    createArray(&arr);
    clearArray(arr);
}

//Functions used for Analysis and Printing Files
int letterIndex(const char *file) {  //Return index by subtracting ASCII value
    int charAscii = (int)file[0];
    if (charAscii == 46 || (charAscii >= 48 && charAscii <= 57) || (charAscii >= 65 && charAscii <= 90) || (charAscii >= 97 && charAscii <= 122)) { // if input is valid
    //    if (charAscii == 46) return 62;// char is "."
        if (charAscii >= 48 && charAscii <= 57) return charAscii + 4;//char is number
        if (charAscii >= 65 && charAscii <= 90) return charAscii - 39;//char is upper case
        return charAscii - 97;//char is lower case
    }
    return 54321;//character is NULL
}
bool starts(const char *full, const char *start) { //Checking if the string starts with substring
    if(strncmp(full, start, strlen(start)) == 0) return true;
    return false;
}

//Main
int main() {
    struct node *Array[64];
    char dirName[100];
    char fileStart[100];
    int letterIdx = 0;
    while (true) {
        resetData(Array);//Resetting directories before search
        printf("\nEnter a folder name: ");
        scanf("%[^\n]s", dirName);
        fseek(stdin, 0, SEEK_END);
        printf(">");
        scanf("%[^\n]s", fileStart);
        fseek(stdin, 0, SEEK_END);
        if (!fileStart[0]) break;
        DIR *dir;//opening directory
        struct dirent *files;
        letterIdx = letterIndex(fileStart);
        if ((dir = opendir(dirName)) != NULL) { //if pointer to directory exists
            while ((files = readdir(dir)) != NULL) { //if there are still files to go through
                //printf("%s",files ->d_name);
                if (letterIndex(files->d_name) >= 0 && letterIndex(files->d_name) <= 62) { //valid first character
                    addNode(files->d_name, &Array[letterIndex(files->d_name)]);
                    // printf("%s\n ",&Array[letterIndex(files->d_name)]);
                }
                if (starts(files->d_name, fileStart)){
                    addNode(files->d_name, &Array[63]); // matching with input
            }
        }
            closedir(dir);//close directory
           showFiles(Array, dirName, fileStart);//print files
        } else perror("ERROR 404 Directory Not found\n");//error opening directory
        memset(dirName, 0, strlen(dirName));
        memset(fileStart, 0, strlen(fileStart));
    }
        return 0;
    }
