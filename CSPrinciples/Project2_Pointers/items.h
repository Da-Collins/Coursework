#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#ifndef ITEM_H
#define ITEM_H

//a struct to represent an Item
struct Item{
	char* name;
	char* description;
	struct Item *next;
};

//creates an item struct with defualt values
struct Item *itemEmpty();

//constructor for item struct
struct Item *item(char* name, char* description, struct Item *next);

//getters
char* getItemName(struct Item *item);

char* getDescription(struct Item *description);

struct Item *getItemNext(struct Item *item);

//removes an item from the current list
struct Item *dropItem(struct Item *head, char* name);

//adds and item to a given list
void addItem(struct Item* head, char* name, char* description);
#endif
