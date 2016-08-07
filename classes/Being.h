/*
 * Being.h
 *
 *  Created on: Aug 1, 2016
 *      Author: Ronaldo
 */

#ifndef CLASSES_BEING_H_
#define CLASSES_BEING_H_

namespace Model {

class Being {
public:
	Being();
	virtual ~Being();

	virtual void iterate() = 0;

private:
	bool isAlive;
};

} /* namespace Model */

#endif /* CLASSES_BEING_H_ */
