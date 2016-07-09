#include <SFML/Audio.hpp>
#include <SFML/Graphics.hpp>
#include <iostream>

#define WINDOWW 800
#define WINDOWH 600

#define TILEW 32
#define TILEH 32

using namespace std;

int main()
{
    // Create the main window
    sf::RenderWindow window(
		sf::VideoMode(WINDOWW, WINDOWH), 
		"Example 3"
	);
	
	int amountTilesX = WINDOWW / TILEW;
	int amountTilesY = WINDOWH / TILEH;
	
	sf::Vertex vertexList[amountTilesX + 1][amountTilesY + 1];
	
	for (int i = 0; i < amountTilesX + 1; ++i) {
		for (int j = 0; j < amountTilesY + 1; ++j) {
			vertexList[i][j] = sf::Vertex(sf::Vector2f(i * TILEW, j * TILEH), sf::Color::White);
		}
	}
	
    // Load the background image
    sf::Texture background;
	
	if (!background.loadFromFile(
		"imgs/grass_random_grid.png"))
		return EXIT_FAILURE;
	
	sf::VertexArray tiles[amountTilesY];
	
	for (int j = 0; j < amountTilesY; ++j) {
		tiles[j] = sf::VertexArray(
			sf::TrianglesStrip, 
			(amountTilesX + 1) * 2);
		
		for (int i = 0, k = 0; i < amountTilesX; ++i, k += 2) {
			tiles[j][k].position = &vertexList[i][j];
			tiles[j][k + 1].position = &vertexList[i][j + 1];
			
			/*tiles[j][k].texCoords = vertexList[i][j];
			tiles[j][k].texCoords = vertexList[i][j];*/
		}
	}

    // Start the game loop
    while (window.isOpen())
    {
        // Process events
        sf::Event event;
        while (window.pollEvent(event))
        {
            // Close window: exit
            if (event.type == sf::Event::Closed)
                window.close();
        }
		
        // Clear screen
        window.clear();
		
        // Draw the grass
		for (int i = 0; i < amountTilesY; ++i)
        	window.draw(tiles[i], &background);
		
        // Update the window
        window.display();
    }
    return EXIT_SUCCESS;
}