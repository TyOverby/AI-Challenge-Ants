package com.prealpha.aichallenge.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.MyBot;
import com.prealpha.aichallenge.protocol.Point;

public class Path {
	private List<Point> nodes = new ArrayList<Point>();

	public Path(Point startingNode) {
		this.nodes.add(startingNode);
	}

	Path(Point... nodes) {
		this.nodes = Arrays.asList(nodes);
	}

	public Path(List<Point> nodes) {
		this.nodes = nodes;
	}

	public void addPoint(Point p) {
		nodes.add(p);
	}

	public Set<Path> getNewPathsFromHead() {
		Set<Path> toReturn = new HashSet<Path>();

		Point curNode = this.getHead();

		Set<Point> newPoints = curNode.getNeighbors();

		for (Point p : newPoints) {
			toReturn.add(this.branch(p));
		}

		return null;
	}

	public Point getHead() {
		return nodes.get(nodes.size() - 1);
	}

	private Path branch(Point p) {
		@SuppressWarnings("unchecked")
		Path toReturn = new Path(
				(List<Point>) ((ArrayList<Point>) nodes).clone());
		toReturn.addPoint(p);

		return toReturn;
	}

	public int getTravledDistance() {
		return nodes.size() - 1;
	}

	public float getEstimatedRemainingDistance(Point endPoint) {
		return MyBot.getGm().getDistance(nodes.get(nodes.size() - 1), endPoint);
	}

	public float getTotalDist(Point endPoint) {
		return getTravledDistance() + getEstimatedRemainingDistance(endPoint);
	}
}
