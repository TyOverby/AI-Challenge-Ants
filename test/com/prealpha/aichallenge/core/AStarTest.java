package com.prealpha.aichallenge.core;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Ilk;
import com.prealpha.aichallenge.protocol.Point;

public final class AStarTest {
	private static final int ROWS = 20;

	private static final int COLS = 20;

	private GameMap map;

	private GameMap obstacleMap;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		// since I don't want to make this constructor public
		Constructor<?> constructor = GameMap.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		map = (GameMap) constructor.newInstance(ROWS, COLS);
		obstacleMap = (GameMap) constructor.newInstance(ROWS, COLS);
		for (Method method : GameMap.class.getDeclaredMethods()) {
			if (method.getName().equals("update")) {
				method.setAccessible(true);
				method.invoke(obstacleMap, new Point(5, 4), Ilk.WATER);
				method.invoke(obstacleMap, new Point(4, 5), Ilk.WATER);
			}
		}
	}

	/**
	 * Tests the smallest path with only a starting point
	 */
	@Test
	public void testCurrentMinOne() {
		Point start = new Point(0, 0);
		Point end = new Point(5, 0);
		AStarAgent agent = new AStarAgent(map, start, end);
		Path path = agent.findCurrentMin();
		assertEquals(new Path(map, start), path);
	}

	/**
	 * Tests the smallest path with only a starting point
	 */
	@Test
	public void testAdvanceOne() {
		Point start = new Point(0, 0);
		Point end = new Point(5, 0);
		AStarAgent agent = new AStarAgent(map, start, end);
		Path expected = new Path(map, start);
		assertEquals(expected, agent.findCurrentMin());
		for (int i = 1; i <= 5; i++) {
			expected = expected.branch(new Point(i, 0));
			agent.advance();
			assertEquals(expected, agent.findCurrentMin());
		}
	}

	@Test
	public void testEntire() {
		Point start = new Point(0, 0);
		Point end = new Point(5, 5);
		AStarAgent agent = new AStarAgent(map, start, end);
		Path path = agent.getSmallestPath();
		assertEquals(10, path.getTotalDistance(end), 0.001);
	}

	@Test
	public void testEntireWithObstacles() {
		Point start = new Point(0, 0);
		Point end = new Point(5, 5);
		AStarAgent agent = new AStarAgent(obstacleMap, start, end);
		Path path = agent.getSmallestPath();
		assertEquals(12, path.getTotalDistance(end), 0.001);
	}
}
