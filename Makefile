all:
	cp SFML/bin/* .
	g++ -c examples/example1.cpp -o examples/bin/example1.o -I SFML/include 
	g++ examples/bin/example1.o -o examples/bin/example1 -L SFML/lib -lsfml-graphics -lsfml-window -lsfml-system
	g++ -c examples/example2.cpp -o examples/bin/example2.o -I SFML/include
	g++ examples/bin/example2.o -o examples/bin/example2 -L SFML/lib -lsfml-audio -lsfml-graphics -lsfml-window -lsfml-system
	g++ -c examples/example3.cpp -o examples/bin/example3.o -I SFML/include
	g++ examples/bin/example3.o -o examples/bin/example3 -L SFML/lib -lsfml-audio -lsfml-graphics -lsfml-window -lsfml-system
	g++ -c examples/example4.cpp -o examples/bin/example4.o -I SFML/include
	g++ examples/bin/example4.o -o examples/bin/example4 -L SFML/lib -lsfml-audio -lsfml-graphics -lsfml-window -lsfml-system
	g++ -c game.cpp -o bin/game.o -I SFML/include -std=c++11
	g++ bin/game.o -o bin/game -L SFML/lib -lsfml-audio -lsfml-graphics -lsfml-window -lsfml-system 

clean:
	rm *.o *.exe