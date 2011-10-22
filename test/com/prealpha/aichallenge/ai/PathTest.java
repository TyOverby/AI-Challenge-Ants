package com.prealpha.aichallenge.ai;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public final class PathTest {
	private static final int ROWS = 20;
	private static final int COLS = 20;

	private GameMap map;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		// since I don't want to make this constructor public
		Constructor<?> constructor = GameMap.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		map = (GameMap) constructor.newInstance(ROWS, COLS);
	}

	@Test
	public void testPathDistance() {
		Point start = new Point(0, 0);
		Point end = new Point(0, 5);

		Path path = new Path(map, start);
		assertEquals(0, path.getTraveledDistance());
		assertEquals(5, path.getEstimatedRemainingDistance(end), 0.001);
		assertEquals(5, path.getTotalDist(end), 0.001);
	}

	@Test
	public void testPathDistanceTwo() {
		Point start = new Point(0, 0);
		Point next = new Point(0, -1);
		Point end = new Point(0, 5);

		List<Point> pathset = new ArrayList<Point>();
		pathset.add(start);
		pathset.add(next);

		Path path = new Path(map, pathset);

		assertEquals(1, path.getTraveledDistance());
		assertEquals(6, path.getEstimatedRemainingDistance(end), 0.001);
		assertEquals(7, path.getTotalDist(end), 0.001);
	}

	@Test
	public void testGetHead() {
		Point start = new Point(0, 0);

		Path path = new Path(map, start);

		assertEquals(start, path.getHead());
	}

	@Test
	public void testGetHeadTwo() {
		Point start = new Point(0, 0);
		Point next = new Point(0, 1);

		Path path = new Path(map, start);
		path.addPoint(next);

		assertEquals(next, path.getHead());
	}

	@Test
	public void testPathBranch() {
		Point start = new Point(0, 0);
		Path curPath = new Path(map, start);

		Set<Path> newPaths = new HashSet<Path>();
		newPaths.add(new Path(map, start, new Point(0, 1)));
		newPaths.add(new Path(map, start, new Point(0, COLS - 1)));
		newPaths.add(new Path(map, start, new Point(1, 0)));
		newPaths.add(new Path(map, start, new Point(ROWS - 1, 0)));

		assertEquals(newPaths, curPath.getNewPathsFromHead());
	}
}
