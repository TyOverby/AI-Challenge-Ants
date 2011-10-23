/*
 * Path.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

final class Path {
	private final Path parent;

	private final int generation;

	private final Point location;

	public Path(Point location) {
		parent = null;
		generation = 0;
		this.location = location;
	}

	private Path(Path parent, Point location) {
		this.parent = parent;
		generation = parent.generation + 1;
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}

	public Path extend(Point target) {
		return new Path(this, target);
	}

	public double getDistance(GameMap map, Point target) {
		int cost = generation;
		Point turn = new Point(location.getRow(), target.getCol());
		double heuristic = map.getDistance(location, turn)
				+ map.getDistance(turn, target);
		return cost + (1.001 * heuristic);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Path)) {
			return false;
		}
		Path other = (Path) obj;
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		return true;
	}
}
