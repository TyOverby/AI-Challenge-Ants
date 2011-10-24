/*
 * Scout.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Ilk;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

public final class Scout extends PathFinder implements Ant {
	private static final Set<Point> ACTIVE_TARGETS = new HashSet<Point>();

	private final GameMap map;

	private Point position;

	private List<Point> path;

	private Order order;

	public Scout(GameMap map, Point position) {
		super(map);
		this.map = map;
		this.position = position;
	}

	@Override
	public Order getOrder() {
		order = doGetOrder();
		if (order == null) {
			recalculate();
			order = doGetOrder();
		}
		return order;
	}

	private Order doGetOrder() {
		if (path != null) {
			int index = path.indexOf(position) + 1;
			if (index < path.size()) {
				Point target = path.get(index);
				if (map.getIlk(target).isPassable()) {
					Set<Aim> directions = map.getDirections(position, target);
					if (directions.size() == 1) {
						Aim direction = directions.iterator().next();
						return new Order(position, direction);
					}
				}
			}
		}
		return null;
	}

	private void recalculate() {
		if (path != null) {
			ACTIVE_TARGETS.remove(path.get(path.size() - 1));
		}
		Point target = getTarget();
		if (target != null) {
			path = findPath(position, target);
			if (path != null && !path.isEmpty()) {
				ACTIVE_TARGETS.add(path.get(path.size() - 1));
			}
		}
	}

	private Point getTarget() {
		PriorityQueue<Point> targets = new PriorityQueue<Point>(1,
				new Comparator<Point>() {
					@Override
					public int compare(Point p1, Point p2) {
						int d1 = map.getManhattanDistance(position, p1);
						int d2 = map.getManhattanDistance(position, p2);
						if (ACTIVE_TARGETS.contains(p1)) {
							d1 *= Game.MAX_MAP_SIZE;
						}
						if (ACTIVE_TARGETS.contains(p2)) {
							d2 *= Game.MAX_MAP_SIZE;
						}
						return d1 - d2;
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

	@Override
	public void die() {
		if (path != null && !path.isEmpty()) {
			ACTIVE_TARGETS.remove(path.get(path.size() - 1));
		}
	}
}
