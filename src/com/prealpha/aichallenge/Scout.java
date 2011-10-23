/*
 * Scout.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

final class Scout extends AvoidingPathFinder {
	private final GameMap map;

	private Point position;

	private List<Point> path;

	private int index;

	public Scout(Game game, Point position) {
		super(game, position);
		map = game.getMap();
		this.position = position;
		index = -1;
	}

	public Order getOrder() {
		if (path == null || index >= path.size()) {
			recalculate();
		}
		if (path == null) {
			return null;
		}

		Point target = path.get(index++);
		if (map.getIlk(target).isPassable()) {
			recalculate();
			return getOrder();
		} else {
			Set<Aim> directions = map.getDirections(position, target);
			if (directions.size() != 1) {
				throw new IllegalStateException();
			}
			Order order = new Order(position, directions.iterator().next());
			position = target;
			return order;
		}
	}

	private void recalculate() {
		index = 0;
		path = explore(position);
	}
}
