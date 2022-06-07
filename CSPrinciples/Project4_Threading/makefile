all: virus_cure

virus_cure.o: virus_cure.c

virus_cure: virus_cure.o
	gcc -pthread -o virus_cure virus_cure.o

clean:
	rm -f *.o
	rm -f virus_cure
