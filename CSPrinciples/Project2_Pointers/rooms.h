#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "items.h"

#ifndef ROOM_H
#define ROOM_H

//struct to represent a Room
struct Room{
	char* name;
	int omen;
	int event;
	struct Item *items;
	struct Room *north;
	struct Room *south;
	struct Room *east;
	struct Room *west;
	struct Room *up;
	struct Room *down;
};

//creates a room with defualt values
struct Room *emptyRoom();

//creates a room with specified values
struct Room *room(char* name, int omen, int event, struct Item *items, struct Room *north, struct Room *south, struct Room *east,
	 struct Room *west, struct Room *up, struct Room *down);

//getters and setters
char* getRoomName(struct Room *room);

void  setRoomName(struct Room *room, char* name);

int getOmen(struct Room *room);

void setOmen(struct Room *room, int omen);

int getEvent(struct Room *room);

void setEvent(struct Room *room, int event);

struct Item *getItems(struct Room *room);

struct Room *getExitNorth(struct Room *room);

struct Room *getExitSouth(struct Room *room); 

struct Room *getExitEast(struct Room *room); 

struct Room *getExitWest(struct Room *room); 

struct Room *getExitUp(struct Room *room); 

struct Room *getExitDown(struct Room *room); 

void setExitNorth(struct Room *room, struct Room *exit);

void setExitSouth(struct Room *room, struct Room *exit);

void setExitEast(struct Room *room, struct Room *exit);

void setExitWest(struct Room *room, struct Room *exit);

void setExitUp(struct Room *room, struct Room *exit);

void setExitDown(struct Room *room, struct Room *exit);

//returns the attributes of a room as a string
void roomToString(struct Room *room);
#endif 
