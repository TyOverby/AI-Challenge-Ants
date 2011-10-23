/*
 * Scout.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Ilk;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

final class Scout extends PathFinder implements Ant {
	private final GameMap map;

	private Point position;

	private List<Point> path;

	private int index;

	private Order order;

	public Scout(GameMap map, Point position) {
		super(map);
		this.map = map;
		this.position = position;
	}

	@Override
	public Order getOrder() {
		if (path == null || index >= path.size()) {
			recalculate();
		}
		if (path == null || index >= path.size()) {
			return null;
		}

		Point target = path.get(index++);
		if (!map.getIlk(target).isPassable()) {
			recalculate();
			return getOrder();
		} else {
			Set<Aim> directions = map.getDirections(position, target);
			if (directions.size() != 1) {
				throw new IllegalStateException("invalid path");
			}
			Aim direction = directions.iterator().next();
			order = new Order(position, direction);
			return order;
		}
	}

	private void recalculate() {
		index = 0;
		path = findPath(position, getTarget());
	}

	private Point getTarget() {
		PriorityQueue<Point> targets = new PriorityQueue<Point>(1,
				new Comparator<Point>() {
					@Override
					public int compare(Point p1, Point p2) {
						int d1 = 0;
						int d2 = 0;
						for (Point hill : map.getMyHills()) {
							d1 += map.getManhattanDistance(p1, hill);
							d2 += map.getManhattanDistance(p2, hill);
						}
						return d2 - d1;
					}
				});
		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getCols(); j++) {
				Point point = new Point(i, j);
				if (map.getIlk(point).isPassable()) {
					int unknownCount = 0;
					for (Point adjacent : map.getAdjacent(point)) {
						if (map.getIlk(adjacent) == Ilk.UNKNOWN) {
							unknownCount++;
						}
					}
					if (unknownCount > 0) {
						targets.add(point);
					}
				}
			}
		}
		return targets.peek();
	}

	@Override
	public void orderConfirmed() {
		position = order.getTarget(map);
		order = null;
	}
}
