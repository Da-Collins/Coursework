#include "keyword.h"

//Creates a new keyword struct
//Param: the attributes of a keyword
//Returns a keyword with the specified attributes
struct Keyword *keyword(char* name, struct Keyword *next){
	struct Keyword *keyword = malloc(sizeof(struct Keyword));
	keyword->name = name;
	keyword->next = next;
	return keyword;
}

//getters for the keyword attributes
char* getKeywordName(struct Keyword *keyword){
	return keyword->name;
}

struct Keyword *getKeywordNext(struct Keyword *keyword){
	return keyword->next;
}

//Checks to see if a given keyword is in the list
//Param; head: the head of the list we're searching
//Param: name: the name of the keyword we're looking for
//Returns 1 if the keyword is in the list 0 otherwise
int lookUp(struct Keyword *head, char* name){
	struct Keyword *curr = head;
	while(curr != NULL){
		if(strcmp(getKeywordName(curr), name) == 0){
			return 1;
		}
		curr = getKeywordNext(curr);
	}
	return 0;
}

//Adds a keyword to a given list
//Param head: the head of the list we're adding to
//Param name: the name of the keyword we're adding
void addKeyword(struct Keyword *head, char* name){
	struct Keyword *add = keyword(name, NULL);
	struct Keyword *curr = head;
	while(getKeywordNext(curr) != NULL){
		curr = getKeywordNext(curr);
	}
	curr->next = add;
}
