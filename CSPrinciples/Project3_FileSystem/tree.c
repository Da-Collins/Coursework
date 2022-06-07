#include "tree.h"

//The implementation of my Binary search tree

//Creates a new BSTNode dynamically allocated
//Param: the attributes of BSTNode
//Returns a pointer to a new BSTNode with the given attributes
struct BSTNode *bstNode(char* name, int count, struct BSTNode *left, struct BSTNode *right){
	struct BSTNode *temp = malloc(sizeof(struct BSTNode));
	temp->name = name;
	temp->count = count;
	temp->left = left;
	temp->right = right;
	return temp;
}

//Sets the name of a node
void setName(struct BSTNode *node, char* name){
	node->name = name;
}

//sets the left child of a node
void setLeft(struct BSTNode *node, struct BSTNode *left){
	node->left = left;
}

//sets the right child of a node
void setRight(struct BSTNode *node, struct BSTNode *right){
	node->right = right;
}

//returns the name of a given node
char* getName(struct BSTNode *node){
	return node->name;
}

//returns the number of occurences of a given word
int getCount(struct BSTNode *node){
	return node->count;
}

//returns the left child of a given node
struct BSTNode *getLeft(struct BSTNode *node){
	return node->left;
}

//returns the right child of a given node
struct BSTNode *getRight(struct BSTNode *node){
	return node->right;
}

//increases the number of occurences of a given word by one
void incrementCount(struct BSTNode *node){
	node->count = node->count + 1;
}

//performs an in order traversal of a tree rooted at the node passed in. Takes the name
//and count of the current node and passes it into sprintf to format then writes to the file
//passed in(output.txt).
void inOrderTraversal(struct BSTNode *node, int longestWord, int file){
	if(getLeft(node) != NULL){
		inOrderTraversal(getLeft(node), longestWord, file);
	}
	char* spr = malloc(longestWord*10 * sizeof(char));
	sprintf(spr, "%-*s : %*i\n", longestWord, getName(node), 5, getCount(node)); 
	write(file, spr, strlen(spr)+1);
	free(spr);
	if(getRight(node) != NULL){
		inOrderTraversal(getRight(node), longestWord, file);
	}
	free(node);
} 

//Adds a word in to the tree for its respective letter. If a word comes after another
//alphabetically it is put to the right, if it comes before alphabetically it is put to
//the left. If the word already exists in the tree its count is increaed and no new node
//is added.  Takes in the head of the tree and the word to be added.
struct BSTNode *add(struct BSTNode *head, char *name){
	struct BSTNode *add = bstNode(name, 1, NULL, NULL);
	struct BSTNode *prev = bstNode("", 0, NULL, NULL);
	struct BSTNode *curr = head;
	int comp = strcmp(getName(head), name);
	while(curr != NULL){
		if(comp < 0){
			prev = curr;
			curr = getRight(curr);
		}
		else if(comp > 0){
			prev = curr;
			curr = getLeft(curr);
		}
		else{
			incrementCount(curr);
			return head;
		}
		if(curr != NULL){
			comp = strcmp(getName(curr), name);	
		}
	}	
	if(comp < 0){
		setRight(prev, add);
	}
	else{
		setLeft(prev, add);
	}
	
	return head;
}
