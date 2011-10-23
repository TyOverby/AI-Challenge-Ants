/*
 * PathSegment.java
 * Copyright (C) 2011 Meyer Kizner, Ty Overby
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.prealpha.aichallenge.protocol.Point;

final class PathSegment {
	private final PathSegment parent;

	private final int generation;

	private final Point location;

	public PathSegment(Point location) {
		parent = null;
		generation = 0;
		this.location = location;
	}

	private PathSegment(PathSegment parent, Point location) {
		this.parent = parent;
		generation = parent.generation + 1;
		this.location = location;
	}

	public PathSegment getParent() {
		return parent;
	}

	public int getGeneration() {
		return generation;
	}

	public Point getLocation() {
		return location;
	}

	public PathSegment extend(Point target) {
		return new PathSegment(this, target);
	}

	public List<Point> collapse() {
		List<Point> path = new ArrayList<Point>(generation);
		PathSegment current = this;
		while (path.size() < generation) {
			path.add(current.location);
			current = current.parent;
		}
		Collections.reverse(path);
		return path;
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
		if (!(obj instanceof PathSegment)) {
			return false;
		}
		PathSegment other = (PathSegment) obj;
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
