#include <SFML/Audio.hpp>
#include <SFML/Graphics.hpp>
#include <iostream>
#include "TileMap.cpp"

#define WINDOW_WIDTH 1280
#define WINDOW_HEIGHT 640

using namespace std;

sf::Vector2f cartToIso(sf::Vector2f cart) {
	return sf::Vector2f(
		cart.x - cart.y,
		(cart.x + cart.y) / 2.0f
	);
}

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
		"imgs/grass-32.png",
		sf::Vector2u(32, 32), level, 10, 10))
		return EXIT_FAILURE;
	
    sf::Texture cube;
    if (!cube.loadFromFile("imgs/cube32.png"))
        return EXIT_FAILURE;
	
    sf::Sprite bgSprite(cube);
    bgSprite.setOrigin(cube.getSize().x / 2, 0);
    bgSprite.setPosition(cartToIso(sf::Vector2f(0 * 32, 1 * 32)) + sf::Vector2f(320, -8));
	/*bgSprite.setScale((float) WINDOW_WIDTH / cube.getSize().x,
					  (float) WINDOW_HEIGHT / cube.getSize().y);*/

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
        window.draw(bgSprite);
		
        // Update the window
        window.display();
    }
    return EXIT_SUCCESS;
}
