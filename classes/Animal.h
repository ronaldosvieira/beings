/*
 * Animal.h
 *
 *  Created on: Aug 1, 2016
 *      Author: Ronaldo
 */

#ifndef CLASSES_ANIMAL_H_
#define CLASSES_ANIMAL_H_

#include "Being.h"

namespace Model {

class Animal: public Being {
public:
	Animal();
	virtual ~Animal();
};

} /* namespace Model */

#endif /* CLASSES_ANIMAL_H_ */
