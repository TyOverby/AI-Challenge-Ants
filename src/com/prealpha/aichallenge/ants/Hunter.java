/*
 * Scout.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge.ants;

import java.util.Comparator;
import java.util.PriorityQueue;
import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Ilk;
import com.prealpha.aichallenge.protocol.Point;

public final class Hunter extends BaseAnt {
	public Hunter(GameMap map, Point position) {
		super(map,position);
	}

	/**
	 * Finds *ALL* the food
	 * @return The destination point
	 */
	protected Point getTarget() {
		// This Priority queue prioritizes points that are closer to the ant
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

		// Loop through all the points on the map to look for food!.
		// If it is, go there, otherwise, if there is no food, scout.
		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getCols(); j++) {
				Point point = new Point(i, j);
				if(map.getIlk(point)==Ilk.FOOD){
					targets.add(point);
				}
			}
		}
		return targets.peek();
	}
}
