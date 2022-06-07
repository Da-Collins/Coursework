#include <sys/stat.h>
#include <fcntl.h>
#include <ctype.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>

//This is the outline for my implementation of a BST

/*A struct to represent a node
/ Name: the name of the word
/ Count: the number of occurences
/ Left: The left child of the node
/ Right: the right child of the node
*/
struct BSTNode{
	char* name;
	int count;
	struct BSTNode *left;
	struct BSTNode *right;
};

//Initializes a new BST node
struct BSTNode *bstNode(char* name, int count, struct BSTNode *left, struct BSTNode *right);

//setters
void setName(struct BSTNode *node, char* name);

void setLeft(struct BSTNode *node, struct BSTNode *left);

void setRight(struct BSTNode *node, struct BSTNode *right);

//getters
char* getName(struct BSTNode *node);

int getCount(struct BSTNode *node);

struct BSTNode *getLeft(struct BSTNode *node);

struct BSTNode *getRight(struct BSTNode *node);

//increments the count of occurences
void incrementCount(struct BSTNode *node);

//traverses the tree using an in order traversal
void inOrderTraversal(struct BSTNode *node, int longestWord, int file);

//adds a word to the tree using alphabetical order
struct BSTNode *add(struct BSTNode *head, char* name);
