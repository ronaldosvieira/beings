/*
 * TextureHolder.h
 *
 *  Created on: Aug 8, 2016
 *      Author: Ronaldo
 */

#include <memory>

#ifndef TEXTUREHOLDER_H_
#define TEXTUREHOLDER_H_

namespace Textures {
	enum ID { Landscape, Airplane, Missile };
}

namespace Model {

class TextureHolder {
public:
	TextureHolder();
	virtual ~TextureHolder();

private:
	std::map<Textures::ID,
		std::unique_ptr<sf::Texture>> mTextureMap;
};

} /* namespace Model */

#endif /* TEXTUREHOLDER_H_ */
