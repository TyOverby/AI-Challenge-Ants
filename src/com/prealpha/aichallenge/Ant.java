/*
 * Ant.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import com.prealpha.aichallenge.protocol.Order;

public interface Ant {
	Order getOrder();

	void orderConfirmed();

	void die();
}
