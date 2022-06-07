#include <semaphore.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <stdio.h>

static sem_t connected_lock;
static sem_t operators;
static sem_t id_lock;
static int NUM_OPERATORS = 5;
static int NUM_LINES = 8;
static int connected = 0;
int next_id = 0;


void* phoneCall(void* vargp){
	//give each thread a unique id within critical section
	sem_wait(&id_lock);
	int id = ++next_id;
	sem_post(&id_lock);
	//coninuously try to connect by checking if all lines are full in a critical section
	printf("Thread %i is attempting to connect...\n", id);
	while(1){
		sem_wait(&connected_lock);
		if(connected < NUM_LINES){
			++connected;
			sem_post(&connected_lock);
			break;
		}
		sem_post(&connected_lock);
		printf("Thread %i failed to connect all lines busy, trying again\n", id);
		usleep(500000);
	}
	//wait until an operator becomes availible then order medicine
	printf("Thread %i connects to an available line, call ringing...\n", id);
	sem_wait(&operators);
	printf("Thread %i is speaking to an operator\n", id);
	printf("Thread %i has ordered the medicine\n", id);
	sleep(1);
	sem_post(&operators);
	//decrement connected to signify the caller has hung up
	sem_wait(&connected_lock);
	--connected;
	sem_post(&connected_lock);
	printf("Thread %i has hung up\n", id);
		
}


int main(int argc, char** argv){
	//Endure the user provided proper input
	if(argc == 1){
		printf("Please provide the number of phonecalls you wish to make\n");
		return 0;
	}
	//initialize the three semaphores
	sem_init(&operators, 0 , 5);
	sem_init(&connected_lock, 0 , 1);
	sem_init(&id_lock, 0, 1);
	//get number of calls and initialize array of threads
	int numCalls = atoi(argv[1]);
	pthread_t calls[numCalls];
	//create the threads using the phoneCall function
	for(int i = 0; i < numCalls; i++){
		pthread_create(&calls[i], NULL, phoneCall, NULL);
	}
	//join all created threads as they finish
	for(int i = 0; i < numCalls; i++){	
		pthread_join(calls[i], NULL);
	}
	//destroy all semaphores	
	sem_destroy(&operators);
	sem_destroy(&connected_lock);
	sem_destroy(&id_lock);
	return 0;
}
