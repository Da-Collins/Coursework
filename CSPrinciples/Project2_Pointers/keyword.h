#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef KEYWORD_H
#define KEYWORD_H

//a struct to define a keyword
struct Keyword{
	char* name;
	struct Keyword *next;
};

//creates a keyword struct
struct Keyword *keyword(char* name, struct Keyword *next);

//getters
char* getKeywordName(struct Keyword *keyword);

struct Keyword *getKeywordNext(struct Keyword *keyword);

//tells wether a certain keyword is valid
int lookUp(struct Keyword *head, char* name);

//adds a keyword to the table of commands
void addKeyword(struct Keyword *head, char*name);

#endif
