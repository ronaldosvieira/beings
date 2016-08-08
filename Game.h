/*
 * Game.h
 *
 *  Created on: Jul 29, 2016
 *      Author: Ronaldo
 */

#ifndef GAME_H_
#define GAME_H_

#include <SFML/Graphics.hpp>

class Game {
public:
	Game();
	void run();

	virtual ~Game();

private:
	void processEvents();
	void handlePlayerInput(sf::Keyboard::Key key, bool isPressed);
	void update(sf::Time deltaTime);
	void render();

	sf::RenderWindow mWindow;
	sf::Texture mTexture;
	sf::Sprite mPlayer;
	float mIsMovingUp = false;
	float mIsMovingDown = false;
	float mIsMovingLeft = false;
	float mIsMovingRight = false;
	float playerSpeed = 100.f;
	sf::Time timePerFrame = sf::seconds(1.f / 60.f);
};

#endif /* GAME_H_ */
