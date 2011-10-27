/*
 * Ant.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge.ants;

import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

public interface Ant {
	Order getOrder();

	void orderConfirmed();
	
	void setTarget(Point point);
	void addTarget(Point point);
	
	Point getLocation();

	void die();
}
