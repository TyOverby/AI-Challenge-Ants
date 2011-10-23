/*
 * BasicPathFinder.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

class BasicPathFinder extends PathFinder {
	private final GameMap map;

	public BasicPathFinder(GameMap map) {
		super(map);
		this.map = map;
	}

	@Override
	protected double getDistance(PathSegment segment, Point end) {
		int cost = segment.getGeneration();
		int heuristic = map.getManhattanDistance(segment.getLocation(), end);
		return cost + ((1025.0 / 1024.0) * heuristic);
	}
}
