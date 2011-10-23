/*
 * AvoidingPathFinder.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.protocol.Aim;
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

	protected List<Point> explore(Point start) {
		Point base;
		if (avoid.equals(start)) {
			Set<Point> visible = getVisiblePoints(start);
			base = visible.iterator().next(); // essentially arbitrary
		} else {
			base = start;
		}

		Set<Aim> directions = game.getMap().getDirections(base, avoid);
		int farRowDelta = game.getMap().getRowDelta(base, avoid);
		int farColDelta = game.getMap().getColDelta(base, avoid);
		for (Aim aim : directions) {
			if (aim.getRowDelta() != 0) {
				farRowDelta *= -aim.getRowDelta();
			}
			if (aim.getColDelta() != 0) {
				farColDelta *= -aim.getColDelta();
			}
		}

		Point farTarget = new Point(normalizeRow(start.getRow() + farRowDelta),
				normalizeCol(start.getCol() + farColDelta));
		double farDistance = game.getMap().getDistance(start, farTarget);

		double distanceRatio = Math.sqrt(game.getViewRadius2()) / farDistance;
		int rowDelta = (int) Math.round(farRowDelta * distanceRatio);
		int colDelta = (int) Math.round(farColDelta * distanceRatio);

		Point target = new Point(normalizeRow(start.getRow() + rowDelta),
				normalizeCol(start.getCol() + colDelta));
		return findPath(start, target);
	}

	private Set<Point> getVisiblePoints(Point center) {
		Set<Point> visible = new HashSet<Point>();
		for (int i = 0; i < game.getMap().getRows(); i++) {
			for (int j = 0; j < game.getMap().getCols(); j++) {
				Point point = new Point(i, j);
				if (game.getMap().getDistance(center, point) < Math.sqrt(game
						.getViewRadius2())) {
					visible.add(point);
				}
			}
		}
		return visible;
	}

	private int normalizeRow(int row) {
		int rows = game.getMap().getRows();
		if (row < 0) {
			return row + rows;
		} else if (row >= rows) {
			return row - rows;
		} else {
			return row;
		}
	}

	private int normalizeCol(int col) {
		int cols = game.getMap().getCols();
		if (col < 0) {
			return col + cols;
		} else if (col >= cols) {
			return col - cols;
		} else {
			return col;
		}
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
