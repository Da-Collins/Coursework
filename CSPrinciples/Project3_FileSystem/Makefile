all: wordfreak
tree.o: tree.c tree.h
	gcc -c -o tree.o tree.c
wordfreak.o: wordfreak.c
wordfreak: tree.o wordfreak.o
	gcc -o wordfreak tree.o wordfreak.o

clean:
	rm -f *.o
	rm -f wordfreak