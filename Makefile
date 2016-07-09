all:
	cp SFML/bin/* .
	g++ -c example1.cpp -I SFML/include
	g++ example1.o -o example1 -L SFML/lib -lsfml-graphics -lsfml-window -lsfml-system
	g++ -c example2.cpp -I SFML/include
	g++ example2.o -o example2 -L SFML/lib -lsfml-audio -lsfml-graphics -lsfml-window -lsfml-system
	g++ -c example3.cpp -I SFML/include
	g++ example3.o -o example3 -L SFML/lib -lsfml-audio -lsfml-graphics -lsfml-window -lsfml-system
	
clean:
	rm *.o *.exe