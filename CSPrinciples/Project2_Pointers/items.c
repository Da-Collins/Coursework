#include "items.h"

//Creates an empty item struct
//Returns an dummy item that points to nothing
struct Item *itemEmpty(){
	struct Item *temp = malloc(sizeof(struct Item));
	temp->name = "dummy";
	temp->description = "dummy";
	temp->next = NULL;
	return temp;
}

//Creates an item struct
//Param: The attributes of the item to be created
//Returns a newly created Item struct
struct Item *item(char* name, char* description, struct Item *next){
	struct Item *temp = malloc(sizeof(struct Item));
	temp->name = name;
	temp->description = description;
	temp->next = next;
	return temp;
}

//Getters
char* getItemName(struct Item *item){
	return item->name;
}

char* getDescription(struct Item *item){
	return item->description;
}

struct Item *getItemNext(struct Item *item){
	return item->next;
}

//Removes an item from its list
//Parm head: the list we're removing from
//param name: name of the item to be removed
//Returns the item that was removed
struct Item *dropItem(struct Item *head, char* name){
	struct Item *curr = head;
	struct Item *prev = head;
	struct Item *dropped = NULL;
	while(curr != NULL){
		if(strcmp(getItemName(curr), name) == 0){
			dropped = curr;
			if(curr->next == NULL){
				prev->next = NULL;
				break;
			}
			prev->next = curr->next;
			break;
		}
		prev = curr;
		curr = curr->next;
	}
	return dropped;
}

//Adds an iem to given list
//Param head: the list we're adding to
//Param name: description: attributes of the item to be added
void addItem(struct Item *head, char* name, char* description){
	struct Item *add = item(name, description, NULL);
	struct Item *curr = head;
	while(getItemNext(curr) != NULL){
		curr = getItemNext(curr);
	}
	curr->next = add;
}		
