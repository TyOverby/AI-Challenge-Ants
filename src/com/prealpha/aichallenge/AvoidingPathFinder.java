/*
 * AvoidingPathFinder.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.Point;

class AvoidingPathFinder extends PathFinder {
	private final Game game;

	private final Point avoid;

	public AvoidingPathFinder(Game game, Point avoid) {
		super(game.getMap());
		this.game = game;
		this.avoid = avoid;
	}

	@Override
	protected double getDistance(PathSegment segment, Point end) {
		int cost = segment.getGeneration();
		double heuristic = (1025.0 / 1024.0)
				* game.getMap()
						.getManhattanDistance(segment.getLocation(), end);
		double penalty = game.getMap()
				.getDistance(segment.getLocation(), avoid);
		return (cost + heuristic) / penalty;
	}
}
