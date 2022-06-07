#include "items.h"
#include "rooms.h"
#include "keyword.h"
#include <ctype.h>
#include <time.h>

//A struct representing the player avatar
struct Avatar{
	struct Item *inventory;
	struct Room *location;
	struct Room *start;
	int items;
	int omens;
};

//The avatar that represents the game state
struct Avatar *player = NULL;

int over = 0;
//initializes the player struct with the begginning values
void initPlayer(){
	player = malloc(sizeof(struct Avatar));
	struct Room *start = room("The Pineapple", 0, 0, itemEmpty(), NULL, NULL, NULL, NULL, NULL, NULL);
	player->location = start;
	player->start = start;
	player->inventory = itemEmpty();
	player->omens = 0;
	player->items = 0;
}

//Creates the Items for the game
//Returns the head of the items list(A dummy node)
struct Item *createItems(){
	struct Item *dummy = itemEmpty();
	addItem(dummy, "formulapiece1", "A Piece of the secret Formula!");
	addItem(dummy, "formulapiece2", "A Piece of the secret Formula!");
	addItem(dummy, "formulapiece3", "A Piece of the secret Formula!");
	return dummy;
}

//Creates an array of rooms
//Returns an array of newly created room pointers representing the different possible rooms
struct Room **createRooms(){
	struct Room **rooms = malloc(7*sizeof(struct Room));
	for(int i = 0; i < 7; i++){
		rooms[i] = emptyRoom();
	}
	return rooms;
}

//Creates The table of commands
//Returns the head od the list containing the valid commands the player can enter
struct Keyword *makeCommands(){
	struct Keyword *table = keyword("look", NULL);
	addKeyword(table, "inventory");
	addKeyword(table, "drop");
	addKeyword(table, "take");
	addKeyword(table, "go");
	addKeyword(table, "north");
	addKeyword(table, "east");
	addKeyword(table, "west");
	addKeyword(table, "up");
	addKeyword(table, "down");
	addKeyword(table, "formulapiece1");
	addKeyword(table, "formulapiece2");
	addKeyword(table, "formulapiece3");
	return table;
}

//Begins the game.
//The player struct is initialized and returned the items are placed into their rooms 
//along with omens and events
//Returns the newly created avatar struct
void beginGame(struct Item *items, struct Room **rooms, struct Keyword *table){
	char *names[] = {"Chum Bucket", "Krusty Krab", "Patrick's Rock", "Squidward's House", "Sandy's Dome", "Bikini Bottom Streets", "Mr. Krab's House"};
	for(int i = 0; i < 7; i++){
		if(i < 3){
			struct Item *curr = getItemNext(items);
			dropItem(items, getItemName(curr));
			addItem(getItems(rooms[i]), getItemName(curr), getDescription(curr));
		}
		else if(i < 5){
			rooms[i]->omen = 1;
		}
		else{
			rooms[i]->event = 1;
		}
		setRoomName(rooms[i], names[i]);
	}
}

//checks to make sure wether or not a given input command is valid
//If the command is go or drop it ensure the second part of the command is valid as well
//Returns wether or not the command is valid 1 if yes 0 if no
int isValidInput(char input[], struct Keyword *table){
	char in[100];
	strcpy(in, input);
	char *command = strtok(in, " \n");
	while(command != NULL){
		char lower[100];
		strcpy(lower, command);
		for(int i = 0; command[i]; i++){
			lower[i] = tolower(lower[i]);
		}
		if(lookUp(table, lower) == 0){
			return 0;
		}
		command = strtok(NULL, " \n");
	}
	return 1;
}

//Prints out the players current room using room's to string method
void look(){
	roomToString(player->location);
}

//Displays the items the avatar has in their inventory
void inventory(){
	struct Item *curr = player->inventory->next;
	char *inven = malloc(100*sizeof(char));
	inven = strcat(inven, "Inventory: ");
	while(curr != NULL){
		inven = strcat(inven, "\n");
		inven = strcat(inven, getItemName(curr));
		inven = strcat(inven, ":   ");
		inven = strcat(inven, getDescription(curr));
		curr = curr->next;
	}
	printf("%s\n", inven);
}

//Drops a specified item out of the avatars inventory and onto the floor in the room they are
//currently in
void drop(char* name){
	struct Item *inventory = player->inventory;
	struct Item *room = player->location->items;
	struct Item *dropped = dropItem(inventory, name);
	addItem(room, name, getDescription(dropped));
	printf("Dropped: %s\n", name);
	player->items = player->items - 1;
}

//Takes an item out of the players current room and places it in their inventory
void take(char* name){
	struct Item *dropped = dropItem(player->location->items, name);
	addItem(player->inventory, name, getDescription(dropped));
	printf("Picked up: %s\n", name);
	player->items = player->items + 1;
}

//Generates a random room to put as the exit of another room
struct Room *generateRoom(struct Room **rooms){
	int empty = 0;
	for(int i = 0; i < 7; i++){
		if(rooms[i] == NULL){
			empty = empty +1;
		}
	}
	if(empty == 7){
		over = 1;
		return emptyRoom();
	}
	int num = rand() % 7;
	struct Room *random = rooms[num];
	while(random == NULL){
		num = rand() % 7;
		random = rooms[num];
	}	
	rooms[num] = NULL;
	if(getOmen(random) == 1){
		printf("Plankton shows up and you scare him off, he drops an omen that will repair the formula\n");
		player->omens = player->omens + 1;
	}
	return random;
}

//if the room contains an event it teleports it next to the starting room if possible
int event(struct Room *room){
	setEvent(room, 0);
	struct Room *start = player->start;
	if(getExitNorth(start) == NULL){
		setExitNorth(start, room);
		setExitSouth(room, start);
		player->location = room;
		return 1;
	}
	else if(getExitSouth(start) == NULL){
		setExitSouth(player->start, room);
		setExitNorth(room, start);
		player->location = room;
		return 1;
	}
	else if(getExitEast(start) == NULL){
		setExitEast(start, room);
		setExitWest(room, start);
		player->location = room;
		return 1;
	}
	else if(getExitWest(start) == NULL){
		setExitWest(start, room);
		setExitEast(room, start);
		player->location = room;
		return 1;
	}	
	else if(getExitUp(start) == NULL){
		setExitUp(start, room);
		setExitDown(room, start);
		player->location = room;
		return 1;
	}	
	else if(getExitDown(start) == NULL){
		setExitDown(start, room);
		setExitUp(room, start);
		player->location = room;
		return 1;
	}
	return 0;
}

//moves the player in their desired direction randomly putting a new room in the direction 
//they chose if one has not already been discovered in that direction
void go(char* input, struct Room **rooms){
	struct Room *location = player->location;
	if(strcmp(input, "north") == 0){
		if(getExitNorth(location) != NULL){
			player->location = getExitNorth(location);
			return;
		}	
		struct Room *exit = generateRoom(rooms);
		if(over == 1){
			return;
		}
		if(getEvent(exit) == 1 && event(exit) == 1){
			printf("An Alaskan Bull Worm moved you right next to your house!\n");
			return;
		}
		setExitNorth(location, exit);
		setExitSouth(exit, location);
		player->location = getExitNorth(location);
	}
	else if(strcmp(input, "east") == 0){
		if(getExitEast(location) != NULL){
			player->location = getExitEast(location);
			return;
		}
		struct Room *exit = generateRoom(rooms);
		if(over == 1){
			return;
		}
		if(getEvent(exit) == 1 && event(exit) == 1){
			printf("An Alaskan Bull Worm moved you right next to your house!\n");
			return;
		}
		setExitEast(location, exit);
		setExitWest(exit, location);
		player->location = getExitEast(location);
	}
	else if(strcmp(input, "west") == 0){
		if(getExitWest(location) != NULL){
			player->location = getExitWest(location);
			return;
		}
		struct Room *exit = generateRoom(rooms);
		if(over == 1){
			return;
		}
		if(getEvent(exit) == 1 && event(exit) == 1){
			printf("An Alaskan Bull Worm moved you right next to your house!\n");
			return;
		}
		setExitWest(location, exit);
		setExitEast(exit, location);
		player->location = getExitWest(location);
	}
	else if(strcmp(input, "up") == 0){
		if(getExitUp(location) != NULL){
			player->location = getExitUp(location);
			return;
		}
		struct Room *exit = generateRoom(rooms);
		if(over == 1){
			return;
		}
		if(getEvent(exit) == 1 && event(exit) == 1){
			printf("An Alaskan Bull Worm moved you right next to your house!\n");
			return;
		}
		setExitUp(location, exit);
		setExitDown(exit, location);
		player->location = getExitUp(location);
	}
	else if(strcmp(input, "down") == 0){
		if(getExitDown(location) != NULL){
			player->location = getExitDown(location);
			return;
		}
		struct Room *exit = generateRoom(rooms);
		if(over == 1){
			return;
		}
		if(getEvent(exit) == 1 && event(exit) == 1){
			printf("An Alaskan Bull Worm moved you right next to your house!\n");
			return;
		}
		setExitDown(location, exit);
		setExitUp(exit, location);
		player->location = getExitDown(location);
	}
}

//determines which command the player wants to execute and calls that sepcific method
void makeMove(char* input, struct Room **rooms){	
	char *command = strtok(input, " \n");
	char lower[100];
	strcpy(lower, command);
	for(int i = 0; lower[i]; i++){
		lower[i] = tolower(lower[i]);
	}
	if(strcmp(lower, "look") == 0){
		look();
	}
	else if(strcmp(lower, "inventory") == 0){
		inventory();
	}
	else if(strcmp(lower, "drop") == 0){
		command = strtok(NULL, " \n");
		for(int i = 0; command[i]; i++){
			command[i] = tolower(command[i]);
		}
		drop(command);
	}
	else if(strcmp(lower, "take") == 0){
		command = strtok(NULL, " \n");
		for(int i = 0; command[i]; i++){
			command[i] = tolower(command[i]);
		}
		take(command);
	}
	else if(strcmp(lower, "go") == 0){
		command = strtok(NULL, " \n");
		for(int i = 0; command[i]; i++){
			command[i] = tolower(command[i]);
		}
		go(command, rooms);
	}
}

//Checks to see wether either end condition has been satisfied returns 1 if yes 0 otherwise
void isOver(){
	if(player->omens >= 1 && player->items >=2 && strcmp(getRoomName(player->location), "The Pineapple") == 0){
		over = 1;
	}
}

//Checks to see if the player has satisfied the win condition 1 if yes 0 if no
int isWin(){	
	if(player->omens >= 1 && player->items >=2 && strcmp(getRoomName(player->location), "The Pineapple") == 0){
		return 1;
	}
	return 0;
}

//The main method to run the game
int main(){
	//initialize everything
	srand(time(0));
	initPlayer();
	struct Room **rooms = createRooms();
	struct Item *items = createItems();
	struct Keyword *table = makeCommands();
	beginGame(items, rooms, table);
	char** buffer = malloc(1000*sizeof(char*));
	int commands = 0;
	printf("\nCommands:\nlook: look at the current room to see items and where exits lead\ninventory: check which items you have\ntake \"item\":takes an item from the current room\ndrop\"item\":drops an item from your inventory\ngo \"direction\": moves you to the room in that direction(north, east, west, up, down)\n\n");
	printf("You awake to a knock on the door, its your boss Mr. Krabs.  He shouts \"Spongebob me boy I've lost the Krabby\nPatty Secret Formula you have to find it!\".  He runs off back to the Krusty Krab to count his money leaving\nyou to do all the work.\n\n");
	//play until completion
	while(over == 0){	
		printf("What do you want to do?:\n");
		char* input = malloc(100*sizeof(char));
		fgets(input, 100, stdin);
		buffer[commands] = strdup(input);
		if(isValidInput(buffer[commands], table) == 0){
			printf("invalid command, please enter a valid command\n");
			continue;
		}	
		makeMove(buffer[commands], rooms);
		commands++;
		free(input);	
		printf("\nItems: %i\nOmens: %i\n\n", player->items, player->omens);
		if(player->location->items->next != NULL){
			printf("There a piece of the formula in here!\n\n");
		}
		isOver();
	}
	//display wether the player won or lost
	if(isWin()){
		printf("Congrats you recovered the formula!\n");
	}
	else{
		printf("The Formula is lost forever you failed\n");
	}
	//free everything
	free(rooms);
	free(items);
	free(table);
	free(player);
	free(buffer);
	return 0;
}

