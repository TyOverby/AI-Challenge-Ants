/*
 * PathFinder.java
 * Copyright (C) 2011 Meyer Kizner, Ty Overby
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import java.util.List;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class PathFinder {
	private final GameMap map;

	private PathSegment[][] segments;

	private boolean[][] extended;

	protected PathFinder(GameMap map) {
		this.map = map;
	}

	protected final List<Point> findPath(Point start, Point end) {
		int rows = map.getRows();
		int cols = map.getCols();
		segments = new PathSegment[rows][cols];
		extended = new boolean[rows][cols];

		segments[start.getRow()][start.getCol()] = new PathSegment(start);
		while (segments[end.getRow()][end.getCol()] == null) {
			PathSegment shortSegment = findShortestSegment(end);
			if (shortSegment != null) {
				extend(shortSegment);
			} else {
				return null; // no path
			}
		}
		return segments[end.getRow()][end.getCol()].collapse();
	}

	private PathSegment findShortestSegment(Point end) {
		PathSegment shortSegment = null;
		double shortDistance = Double.POSITIVE_INFINITY;
		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getCols(); j++) {
				if (segments[i][j] != null && !extended[i][j]) {
					double distance = getDistance(segments[i][j], end);
					if (distance < shortDistance) {
						shortSegment = segments[i][j];
						shortDistance = distance;
					}
				}
			}
		}
		return shortSegment;
	}

	private void extend(PathSegment segment) {
		for (Point point : map.getAdjacent(segment.getLocation())) {
			if (map.getIlk(point).isPassable()
					&& segments[point.getRow()][point.getCol()] == null) {
				segments[point.getRow()][point.getCol()] = segment
						.extend(point);
			}
		}
		extended[segment.getLocation().getRow()][segment.getLocation().getCol()] = true;
	}

	private double getDistance(PathSegment segment, Point end) {
		int cost = segment.getGeneration();
		int heuristic = map.getManhattanDistance(segment.getLocation(), end);
		return cost + ((1025.0 / 1024.0) * heuristic);
	}
}
