#include <SFML/Audio.hpp>
#include <SFML/Graphics.hpp>
#include <iostream>
#include "TileMap.cpp"

#define WINDOW_WIDTH 840
#define WINDOW_HEIGHT 640

using namespace std;

int main()
{
    // Create the main window
    sf::RenderWindow window(
		sf::VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), 
		"Example 3"
	);
	
	const int level[] = {
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
	};
	
	TileMap map;
	
	if (!map.load(
		"imgs/grass_random_grid.png", 
		sf::Vector2u(64, 64), level, 10, 10))
		return EXIT_FAILURE;
	
    // Load the background image
    /*sf::Texture background;
    if (!background.loadFromFile("imgs/natureza-13.jpg"))
        return EXIT_FAILURE;
	
    sf::Sprite bgSprite(background);
	bgSprite.setScale((float) WINDOW_WIDTH / background.getSize().x, 
					  (float) WINDOW_HEIGHT / background.getSize().y);*/

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
		
        // Draw the sprite
        window.draw(map);
		
        // Update the window
        window.display();
    }
    return EXIT_SUCCESS;
}