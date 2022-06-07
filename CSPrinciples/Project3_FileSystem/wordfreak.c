#include "tree.h"

//stores the length of the longest word
int longestWord = 0;

//The Hash table representing the 26 BST's
struct BSTNode **hashtable;

//allocates memory for the hash table
void initTable(){
	hashtable = malloc(26 * sizeof(struct BSTNode *));
}

//takes in a string and returns it in its lower case version
char* lowerCase(char* word){
	for(int i = 0; word[i]; i++){
		word[i] = tolower(word[i]);
	}
	return word;
}

//the hash function that puts each word in the proper BST
void hash(char* word){
	word = lowerCase(word);
	//takes the ascii value of the first char and converts it to its respective index
	int index = word[0] - 97;
	if(index <0 || index > 25){
		return;
	}
	//fills empty bsts
	if(hashtable[index] ==  NULL){
		hashtable[index] = bstNode(word, 1, NULL, NULL);
		return;
	}
	add(hashtable[index], word);
}

//if a bst is not empty traverses it and prints to output.txt
void formatOutput(int output, int tree){
	if(hashtable[tree] != NULL){
		inOrderTraversal(hashtable[tree], longestWord, output);
	}
}

int main(int argc, char** argv){
	//initialize the hash table
	initTable();
	//the buffer that will be used to read from files
	char* buffer = malloc(sizeof(char));
	//open output.txt in write only mode
	int output = open("output.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644);
	//handle words/files passed through stdin
	int stdBytes = stdBytes = read(0, buffer, 1);
	char *words = malloc(10000*sizeof(char));
	while(stdBytes > 0){
		//take puts all alphabet characters into word until a non alpha is found
		int c = *buffer;	
		if(isalpha(c) != 0){
			strcat(words, buffer);
		}
		//passes the word into the hash function
		else if(strlen(words) > 0){
			if(strlen(words) > longestWord){
				longestWord = strlen(words);
			}
			char* cpy = malloc(10000* sizeof(char)); 
			cpy = strcpy(cpy, words);
			hash(cpy);
			memset(words, 0, strlen(words)+1);
		}
		//clear buffer
		memset(buffer, 0, strlen(buffer)+1);
		stdBytes = read(0, buffer, 1);
	}
	//puts the final word into the bst
	hash(words);
	memset(buffer, 0, strlen(buffer)+1);
	//handle files passed through argv
	if(argc > 1){
		for(int i = 1; i < argc; i++){
			int argu = open(argv[i], O_RDONLY);
			int bytes = read(argu, buffer, 1);
			char* word = malloc(10000 * sizeof(char));
			while(bytes > 0){
				//the code within this loop is the same as the one for stdin
				int c = *buffer;	
				if(isalpha(c) != 0){
					strcat(word, buffer);
				}
				else if(strlen(word) > 0){
					if(strlen(word) > longestWord){
						longestWord = strlen(word);
					}
					char* cpy = malloc(10000* sizeof(char)); 
					cpy = strcpy(cpy, word);
					hash(cpy);
					memset(word, 0, strlen(word)+1);
				}
				memset(buffer, 0, strlen(buffer)+1);
				bytes =	read(argu, buffer, 1);
			}
			hash(words);
			free(word);
			close(argu);
			memset(buffer, 0, strlen(buffer)+1);
		}
	}
	//handle the WORDFREAK enviroment variable
	char* wordfreak = getenv("WORD_FREAK");
	if(wordfreak != NULL){
		int env = open(wordfreak, O_RDONLY);
		int envBytes = read(env, buffer, 1);
		while(envBytes > 0){
			//the code within this loop as the same as the two above
			int c = *buffer;	
			if(isalpha(c) != 0){
				strcat(words, buffer);
			}
			else if(strlen(words) > 0){
				if(strlen(words) > longestWord){
					longestWord = strlen(words);
				}
				char* cpy = malloc(10000* sizeof(char)); 
				cpy = strcpy(cpy, words);
				hash(cpy);
				memset(words, 0, strlen(words)+1);
			}
			memset(buffer, 0, strlen(buffer)+1);
			envBytes = read(env, buffer, 1);
		}
		hash(words);
		close(env);
		memset(buffer, 0, strlen(buffer) + 1);
	}
	//pass all trees into formatoutput
	for(int i = 0; i < 26; i++){
		formatOutput(output, i);
	}
	//free/close everything
	close(output);
	free(words);	
	free(hashtable);
	free(buffer);
	return 0;
}
