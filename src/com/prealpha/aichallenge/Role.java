/*
 * Role.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.Point;

abstract class Role {
	private final Game game;

	private final Point end;

	private final Path[][] paths;

	private final boolean[][] extended;

	protected Role(Game game, Point start, Point end) {
		this.game = game;
		this.end = end;

		int rows = game.getMap().getRows();
		int cols = game.getMap().getCols();
		paths = new Path[rows][cols];
		paths[start.getRow()][start.getCol()] = new Path(start);
		extended = new boolean[rows][cols];
	}

	protected Path findPath() {
		while (paths[end.getRow()][end.getCol()] == null) {
			extend(findShortestPath());
		}
		return paths[end.getRow()][end.getCol()];
	}

	private Path findShortestPath() {
		Path shortPath = null;
		double shortDistance = Double.MAX_VALUE;
		for (int i = 0; i < game.getMap().getRows(); i++) {
			for (int j = 0; j < game.getMap().getCols(); j++) {
				if (paths[i][j] != null && !extended[i][j]) {
					double distance = paths[i][j].getDistance(game.getMap(),
							end);
					if (distance < shortDistance) {
						shortPath = paths[i][j];
						shortDistance = distance;
					}
				}
			}
		}
		return shortPath;
	}

	private void extend(Path path) {
		for (Point point : game.getMap().getAdjacent(path.getLocation())) {
			if (paths[point.getRow()][point.getCol()] == null) {
				paths[point.getRow()][point.getCol()] = path.extend(point);
			}
		}
		extended[path.getLocation().getRow()][path.getLocation().getCol()] = true;
	}
}
