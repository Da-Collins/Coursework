#include "rooms.h"

//Creates an empty room struct
//Returns a Room with defualt values
struct Room *emptyRoom(){
	struct Room *room = malloc(sizeof(struct Room));
	room->items = item("dummy", "dummy", NULL);
	return room;
}

//Creates a room struct
//Param: The attributes of a room
//returns a room with the specified attributes
struct Room *room(char* name, int omen, int event, struct Item *item, struct Room *north, struct Room *south, struct Room *east,
	struct Room *west, struct Room *up, struct Room *down){
	struct Room *room = malloc(sizeof(struct Room));
	room->name = name;
	room->omen = omen;
	room->event = event;
	room->items = item;
	room->north = north;
	room->south = south;
	room->east = east;
	room->west = west;
	room->up = up;
	room->down = down;
	return room;
}

//Getters and setters for every attribute of room
char* getRoomName(struct Room *room){
	return room->name;
}

void setRoomName(struct Room *room, char* name){
	room->name = name;
}

int getOmen(struct Room *room){
	return room->omen;
}

void setOmen(struct Room *room, int omen){
	room->omen = omen;
}

int getEvent(struct Room *room){
	return room->event;
}

void setEvent(struct Room *room, int event){
	room->event = event;
}

struct Item *getItems(struct Room *room){
	return room->items;
}

struct Room *getExitNorth(struct Room *room){
	return room->north;
}

struct Room *getExitSouth(struct Room *room){
	return room->south;
}

struct Room *getExitEast(struct Room *room){
	return room->east;
}

struct Room *getExitWest(struct Room *room){
	return room->west;
}

struct Room *getExitUp(struct Room *room){
	return room->up;
}

struct Room *getExitDown(struct Room *room){
	return room->down;
}

void setExitNorth(struct Room *room, struct Room *exit){
	room->north = exit;
}

void setExitSouth(struct Room *room, struct Room *exit){
	room->south = exit;
}

void setExitEast(struct Room *room, struct Room *exit){
	room->east = exit;
}

void setExitWest(struct Room *room, struct Room *exit){
	room->west = exit;
}

void setExitUp(struct Room *room, struct Room *exit){
	room->up = exit;
}

void setExitDown(struct Room *room, struct Room *exit){
	room->down = exit;
}

//Prints a string representation of a room
//Param: room: the room to be turned into a string
void roomToString(struct Room *room){
	char* name = getRoomName(room);	
	struct Item *curr = getItems(room)->next;
	char* items = malloc(100*sizeof(char));
	while(curr != NULL){
		items = strcat(items, getItemName(curr));
		items = strcat(items, "   ");
		curr = curr->next;
	}
	char* north = "???";
	if(getExitNorth(room) != NULL){
		north = getRoomName(getExitNorth(room));
	}
	char* south = "???";
	if(getExitSouth(room) != NULL){
		south = getRoomName(getExitSouth(room));
	}
	char* east = "???";
	if(getExitEast(room) != NULL){
		east = getRoomName(getExitEast(room));
	}
	char* west = "???";
	if(getExitWest(room) != NULL){
		west = getRoomName(getExitWest(room));
	}
	char* up = "???";
	if(getExitUp(room) != NULL){
		up = getRoomName(getExitUp(room));
	}
	char* down = "???";
	if(getExitDown(room) != NULL){
		down = getRoomName(getExitDown(room));
	}
	printf("Room: %s\nItems: %s\nNorth: %s\nSouth: %s\nEast: %s\nWest: %s\nUp: %s\nDown:%s\n", name, items, north, south, east, west, up, down);
}
		
