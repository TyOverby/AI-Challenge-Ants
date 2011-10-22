package com.prealpha.aichallenge.ai;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class AStarAgent {
	private final Point start, end;

	private final Queue<Path> paths;

	private final Set<Point> exploredPoints = new HashSet<Point>();

	public AStarAgent(GameMap map, Point start, final Point end) {
		this.start = start;
		this.end = end;
		paths = new PriorityQueue<Path>(1, new Comparator<Path>() {
			@Override
			public int compare(Path p1, Path p2) {
				return (int) Math.round(p1.getTotalDist(end)
						- p2.getTotalDist(end));
			}
		});

		paths.add(new Path(map, this.start));
		exploredPoints.add(this.start);
	}

	Path findCurrentMin() {
		return paths.peek();
	}

	void advance() {
		Path minPath = findCurrentMin();
		// Add all of its children
		paths.addAll(minPath.getNewPathsFromHead());
		paths.remove(minPath);
	}

	public Path getSmallestPath() {
		while (!findCurrentMin().getHead().equals(end)) {
			advance();
		}
		return findCurrentMin();
	}
}
