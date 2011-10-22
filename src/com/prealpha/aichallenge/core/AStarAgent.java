package com.prealpha.aichallenge.core;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public final class AStarAgent {
	private final Point start;

	private final Point end;

	private final Queue<Path> paths;

	public AStarAgent(GameMap map, Point start, final Point end) {
		this.start = start;
		this.end = end;
		paths = new PriorityQueue<Path>(1, new Comparator<Path>() {
			@Override
			public int compare(Path p1, Path p2) {
				return (int) Math.round(p1.getTotalDistance(end)
						- p2.getTotalDistance(end));
			}
		});
		paths.add(new Path(map, this.start));
	}

	Path findCurrentMin() {
		return paths.peek();
	}

	void advance() {
		Path minPath = findCurrentMin();
		Set<Path> children = minPath.getChildren();
		paths.addAll(children);
		paths.remove(minPath);
	}

	public Path getSmallestPath() {
		while (!findCurrentMin().getHead().equals(end)) {
			advance();
		}
		return findCurrentMin();
	}
}
