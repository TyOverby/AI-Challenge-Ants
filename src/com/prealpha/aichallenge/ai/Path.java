package com.prealpha.aichallenge.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

/**
 * A sequence of {@link Point} instances in which each successive point is
 * directly adjacent to the last. Paths are immutable.
 * 
 * @author Meyer Kizner
 * 
 */
public final class Path {
	private final GameMap map;

	private final List<Point> nodes;

	public Path(GameMap map, Point... nodes) {
		this(map, Arrays.asList(nodes));
	}

	public Path(GameMap map, List<Point> nodes) {
		this.map = map;
		this.nodes = new ArrayList<Point>(nodes);

		// check that all points are adjacent
		Point last = this.nodes.get(0);
		for (int i = 1; i < this.nodes.size(); i++) {
			Point current = this.nodes.get(i);
			if (this.map.getDistance(last, current) != 1.0) {
				throw new IllegalArgumentException();
			}
			last = current;
		}
	}

	private Path(Path template) {
		map = template.map;
		nodes = new ArrayList<Point>(template.nodes);
	}

	public Point getHead() {
		return nodes.get(nodes.size() - 1);
	}

	public Path branch(Point target) {
		if (map.getDistance(getHead(), target) != 1.0) {
			throw new IllegalArgumentException();
		}
		Path branch = new Path(this);
		branch.nodes.add(target);
		return branch;
	}

	public Set<Path> getChildren() {
		Set<Path> children = new HashSet<Path>(4);
		Set<Point> adjacent = map.getAdjacent(getHead());
		for (Point point : adjacent) {
			if (map.getIlk(point).isPassable() && !nodes.contains(point)) {
				children.add(branch(point));
			}
		}
		return children;
	}

	/**
	 * Returns the length of this path. In A*, this is g(head).
	 * 
	 * @return the length of this path
	 */
	public int getLength() {
		return nodes.size() - 1;
	}

	/**
	 * Returns the minimum Manhattan distance from the head of the path to the
	 * specified end point. This is used as the heuristic, or h(head) in A*. The
	 * actual distance may be longer if there are obstacles present between the
	 * head and the end point.
	 * 
	 * @param end
	 *            the target point
	 * @return the minimum Manhattan distance from the head to {@code end}
	 */
	public double getHeuristicDistance(Point end) {
		Point turn = new Point(getHead().getRow(), end.getCol());
		return map.getDistance(getHead(), turn) + map.getDistance(turn, end);
	}

	/**
	 * Returns the total estimated distance along this path and then to the end
	 * point. Past the head, distances are estimated using
	 * {@link #getHeuristicDistance(Point)}. In A*, this is f(head).
	 * 
	 * @param end
	 *            the target point
	 * @return the total estimated distance along the path to {@code end}
	 */
	public double getTotalDistance(Point end) {
		return getLength() + getHeuristicDistance(end);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
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
		if (map == null) {
			if (other.map != null) {
				return false;
			}
		} else if (!map.equals(other.map)) {
			return false;
		}
		if (nodes == null) {
			if (other.nodes != null) {
				return false;
			}
		} else if (!nodes.equals(other.nodes)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String toReturn = "{";
		for (Point point : nodes) {
			toReturn += String.format("%s, ", point);
		}
		return toReturn.trim().substring(0, toReturn.length() - 2) + "}";
	}
}
