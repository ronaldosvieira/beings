all:
	cp SFML/bin/* .
	g++ -c example1.cpp -I SFML/include
	g++ example1.o -o example1 -L SFML/lib -lsfml-graphics -lsfml-window -lsfml-system
	./example1
	
compile:
	cp SFML/bin/* .
	g++ -c example1.cpp -I SFML/include
	g++ example1.o -o example1 -L SFML/lib -lsfml-graphics -lsfml-window -lsfml-system