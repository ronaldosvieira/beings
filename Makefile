all:
	cp SFML/bin/* .
	g++ -c main.cpp -I SFML/include
	g++ main.o -o sfml-app -L SFML/lib -lsfml-graphics -lsfml-window -lsfml-system
	./sfml-app
	
compile:
	cp SFML/bin/* .
	g++ -c main.cpp -I SFML/include
	g++ main.o -o sfml-app -L SFML/lib -lsfml-graphics -lsfml-window -lsfml-system