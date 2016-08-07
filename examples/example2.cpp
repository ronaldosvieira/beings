#include <SFML/Audio.hpp>
#include <SFML/Graphics.hpp>
#include <iostream>

#define WINDOW_WIDTH 800
#define WINDOW_HEIGHT 600

using namespace std;

int main()
{
    // Create the main window
    sf::RenderWindow window(
		sf::VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), 
		"Example 2"
	);
	
    // Load the background image
    sf::Texture background;
    if (!background.loadFromFile("imgs/natureza-13.jpg"))
        return EXIT_FAILURE;
	
    sf::Sprite bgSprite(background);
	bgSprite.setScale((float) WINDOW_WIDTH / background.getSize().x, 
					  (float) WINDOW_HEIGHT / background.getSize().y);
	
	// Load a image with transparency
	sf::Texture texture;
    if (!texture.loadFromFile("imgs/cglegal.png"))
        return EXIT_FAILURE;
	
	texture.setSmooth(true);
    sf::Sprite imgSprite(texture);
	imgSprite.setOrigin(texture.getSize().x / 2,
					   texture.getSize().y / 2);
	imgSprite.setScale(0.9 * WINDOW_WIDTH / texture.getSize().x,
					  0.9 * WINDOW_WIDTH / texture.getSize().x);
	imgSprite.setPosition(WINDOW_WIDTH / 2,
						 WINDOW_HEIGHT / 2);
	imgSprite.setRotation(-10);

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
        window.draw(bgSprite);
        window.draw(imgSprite);
		
        // Update the window
        window.display();
    }
    return EXIT_SUCCESS;
}