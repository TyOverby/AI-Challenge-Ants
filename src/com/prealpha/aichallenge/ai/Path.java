package com.prealpha.aichallenge.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.protocol.Map;
import com.prealpha.aichallenge.protocol.Point;

public final class Path {
	private final Map map;

	private final List<Point> nodes;

	public Path(Map map, Point startingNode) {
		this(map, Collections.singletonList(startingNode));
	}

	Path(Map map, Point... nodes) {
		this(map, Arrays.asList(nodes));
	}

	public Path(Map map, List<Point> nodes) {
		this.map = map;
		this.nodes = new ArrayList<Point>(nodes);
	}

	public void addPoint(Point p) {
		nodes.add(p);
	}

	public Set<Path> getNewPathsFromHead() {
		Set<Path> toReturn = new HashSet<Path>();

		Point curNode = this.getHead();

		Set<Point> newPoints = map.getAdjacent(curNode);

		for (Point p : newPoints) {
			toReturn.add(branch(p));
		}

		return toReturn;
	}

	public Point getHead() {
		return nodes.get(nodes.size() - 1);
	}

	private Path branch(Point p) {
		Path toReturn = new Path(map, nodes);
		toReturn.addPoint(p);
		return toReturn;
	}

	public int getTraveledDistance() {
		return nodes.size() - 1;
	}

	public double getEstimatedRemainingDistance(Point endPoint) {
		return map.getDistance(nodes.get(nodes.size() - 1), endPoint);
	}

	public double getTotalDist(Point endPoint) {
		return getTraveledDistance() + getEstimatedRemainingDistance(endPoint);
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
		String toReturn = "";
		for (Point point : nodes) {
			toReturn += String.format("(%s) ", point);
		}
		return toReturn.trim();
	}
}
